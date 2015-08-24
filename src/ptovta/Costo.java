package ptovta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * <h1>Realiza el calculo del costo</h1>
 * <p>
 * Esta clase se encarga de hacer los calculos de costos en el sistema</p>
 *
 * Costos.java
 *
 * @author Cesar Martínez
 * @version 1.0
 * @package ptovta
 * @since 15-04-2015
 */
public final class Costo {
    
    public static class MetodoCosteo {

        public static final int METODO_PROMEDIO_P = 0;
        public static final int METODO_UEPS = 1;
        public static final int METODO_PEPS = 2;
        public static final int COSTO_IDENTIFICADO = 3;

    }
    private static final Object locker = new Object();
    private static volatile Costo instance;
    private Date fechaCorte;
    
    /*Constructor privado*/
    private Costo() {
    }
    
    /**
     * Regresa la instancia del objeto costo.
     * 
     * Este metodo asegura que solo exista una instancia al mismo tiempo en todo
     * el sistema.
     * 
     * @return la instancia previamente creada o si no existe una nueva.
     */
    public static Costo getInstance() {
        if (instance == null) {
            synchronized (locker) {
                if (instance == null) {
                    instance = new Costo();
                }
            }
        }
        return instance;
    }
    
   /**
    *Regresa el metodo de costeo del producto especificado como un entero. Siendo
    * el entero uno de los valores especificados dentro de la clase estatica <code>
    * MetodoCosteo</code>.
    * 
    * @param idProd el id del producto del que se requiere el metodo.
    * @return   un entero que especifica el metodo de costeo.
    * @see  MetodoCosteo
    */
    private int getMetodoCosteo(int idProd) {
        Connection con = ConexionBD.getConexion();
        ResultSet rs;
        String query = "";
        int metodo = -1;
        String nombreMet = "";
        
        //consulta el metodo de costeo del producto
        query = "SELECT P.metcost FROM prods AS P WHERE id_id = ? LIMIT 1";

        rs = ConexionBD.ejecutarQuery(query, idProd);

        try {
            if (rs.next()) {
                nombreMet = rs.getString("metcost");
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
        }
        
        //compara el nombre del metodo y regresa el entero correspondiente
        if (nombreMet.equals("prom")) {
            metodo = Costo.MetodoCosteo.METODO_PROMEDIO_P;
        } else if (nombreMet.equals("ueps")) {
            metodo = Costo.MetodoCosteo.METODO_UEPS;
        } else if (nombreMet.equals("peps")) {
            metodo = Costo.MetodoCosteo.METODO_PEPS;
        } else if (nombreMet.equals("ultcost")) {
            metodo = Costo.MetodoCosteo.COSTO_IDENTIFICADO;
        }
        return metodo;
    }

   /**
    *Establece la fecha de corte usada para el calculo del costo.
    *@param fechaCorte la fecha final usada para el calculo del costo. 
    */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

   /**
    *Ingresa cada una de las partidas de una entrada a los movimientos de inventario.
    * 
    * Toma los datos de la entrada indicada e inserta cada una de las partidas en 
    * la tabla er_movimientos_inventario para llevar el control del costo.
    *
    *@param tablaDocumentos string que representa el nombre de la tabla donde se guardan los documentos de entrada
    *@param tablaPartidas nombre de la tabla donde se almacenan las partidas del documento de entrada
    *@param codigoDocumento identificador del documento de entrada
    */
    public void insertarMovimientosEntrada(String tablaDocumentos, String tablaPartidas, String codigoDocumento) {
        double costProm = 0, existActual = 0;
        int idDocumentoComercial = 0;
        Date fechaDocumento = null;
        
        Connection con = ConexionBD.getConexion();
        
        //obtiene el id y la fecha del documento de entrada
        ResultSet rs = ConexionBD.ejecutarQuery("SELECT D.id_id, DATE(D.falt) AS fecha FROM " + tablaDocumentos + " AS D WHERE D.codComp = ? LIMIT 1", codigoDocumento);
        
        try {
            //asigna el resultado a las variables correspondientes
            if (rs.next()) {
                fechaDocumento = rs.getDate("fecha");
                idDocumentoComercial = rs.getInt("id_id");
            } else {
                JOptionPane.showMessageDialog(null, "El documento " + codigoDocumento + " no existe.");
                return;
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
            return;
        }

        
        //obtiene id, almacen, producto, cantidad, unidad y costo de cada una de las partidas del documento de entrada
        rs = ConexionBD.ejecutarQuery("SELECT M.id_id, M.alma, M.prod, M.cant,M.unid, M.cost FROM " + tablaPartidas + " AS M WHERE M.codcom = ? LIMIT 1", codigoDocumento);

        Partida entrada = new Partida();
        try {
            
            while (rs.next()) {
                //Asigna el resultado a un objeto tipo partida
                entrada.setIdPartida(rs.getInt("id_id"));
                entrada.setIdAlmacen(rs.getString("alma"));
                entrada.setCantidad(rs.getDouble("cant"));
                entrada.setCostoUnitario(rs.getDouble("cost"));
                entrada.setExistenciaActual(rs.getDouble("cant"));
                entrada.setFechaMovimiento(fechaDocumento);
                entrada.setIdProducto(rs.getString("prod"));
                
                //obtiene el ultimo costo promedio
                costProm = obtenerCostoPromedio(entrada.getIdProducto());
                
                //obtiene la existencia actual
                existActual = obtenerExistenciaActual(entrada.getIdProducto());
                
                //Calcula el costo promedio
                entrada.setCostoPromedio(((costProm * existActual) + (entrada.getCantidad() * entrada.getCostoUnitario())) / (existActual + entrada.getCantidad()));
                entrada.setTipoMovimiento(Partida.TIPO_ENTRADA);
                entrada.setIdMovtoComercial(idDocumentoComercial);
                entrada.setUnidad(rs.getString("unid"));
                
                //guarda la partida en la tabla de movimientos de inventario
                this.generarMovimientoInventario(entrada);
                ConexionBD.closeCon(con);
            }

        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));

            ConexionBD.closeCon(con);
            return;
        }

    }

    /*
    *Obtiene las existencias actuales del producto.
    *
    *@param idProducto id del producto del cual se van a obtener la
    */
    private double obtenerExistenciaActual(int idProducto) {
        //Obtine la suma de las existencias actuales de la tabla de movimientos inventario
        ResultSet rs = ConexionBD.ejecutarQuery("SELECT SUM(M.existActual) AS EXISTENCIA FROM er_movimientos_Inventario AS M WHERE M.idProducto = ? AND M.tipoMovimiento = 0 LIMIT 1", idProducto);

        try {
            if (rs.next()) {
                return rs.getDouble("EXISTENCIA");
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
        }

        return 0;
    }

    /**
     * Busca la venta en la tabla vtas y actualiza el campo costo.
     * @param venta
     * @return regresa 1 si fue exitoso o 0 si no lo fue.
     */
    public int actualizarCostoVenta(String venta) {
        int idProd, metodo;
        Date fechaEmision = new Date();
        ResultSet rs;
        idProd = 0;
        metodo = 0;

        String query = "SELECT V.femi FROM vtas AS V WHERE V.vta = ? LIMIT 1";
        rs = ConexionBD.ejecutarQuery(query, venta);

        try {
            if (!rs.isClosed()) {
                if (rs.next()) {
                    fechaEmision = rs.getDate("femi");
                } else {
                    JOptionPane.showMessageDialog(null, "La venta no existe");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El resultset esta cerrado: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        this.setFechaCorte(fechaEmision);

        rs = null;

        query = "SELECT P.id_id, V.cant, V.alma, V.unid, P.prod FROM prods AS P JOIN partvta AS V ON P.prod = V.prod WHERE V.vta = ?";

        rs = ConexionBD.ejecutarQuery(query, venta);

        double costo = 0, cantidad = 0;

        try {
            while (rs.next()) {
                idProd = rs.getInt("P.id_id");
                metodo = this.getMetodoCosteo(idProd);
                cantidad = rs.getDouble("cant");

                switch (metodo) {
                    case Costo.MetodoCosteo.METODO_PROMEDIO_P:
                        costo = obtenerCostoPromedio(idProd);
                        break;
                    case Costo.MetodoCosteo.METODO_UEPS:
                        costo = calcularUepsPeps(idProd, cantidad,Costo.MetodoCosteo.METODO_UEPS);
                        break;
                    case Costo.MetodoCosteo.METODO_PEPS:
                        costo = calcularUepsPeps(idProd, cantidad,Costo.MetodoCosteo.METODO_PEPS);
                        break;
                    case Costo.MetodoCosteo.COSTO_IDENTIFICADO:
                        costo = calcularCostoIdentificado(idProd);
                    default:
                }
                
                ConexionBD.ejecutarUpdate("UPDATE partvta AS V SET V.cost = " + String.valueOf(cantidad * costo) + " WHERE V.vta = " + venta + " AND V.prod = '" + rs.getString("prod") + "' LIMIT 1");

                Partida movimientoSalida = new Partida();
                
                movimientoSalida.setCostoUnitario(costo);
                movimientoSalida.setCantidad(cantidad);
                movimientoSalida.setFechaMovimiento(fechaEmision);
                movimientoSalida.setIdAlmacen(rs.getString("alma"));
                movimientoSalida.setIdMovtoComercial(Integer.parseInt(venta));
                movimientoSalida.setTipoMovimiento(Partida.TIPO_SALIDA);
                movimientoSalida.setCostoPromedio(obtenerCostoPromedio(idProd));
                movimientoSalida.setIdProducto(idProd);
                movimientoSalida.setUnidad(rs.getString("V.unid"));
                movimientoSalida.setExistenciaActual(0);

                actualizarExistenciaActual(movimientoSalida);
                System.out.println("10");
                generarMovimientoInventario(movimientoSalida);
                System.out.println("11");

            }
            return 1;
        } catch (SQLException e) {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            return 0;
        }

    }
    
    /**
     * Busca la partida en la tabla er_movimientos_inventario y actualiza las existencias actuales de esa partida.
     * @param movimiento 
     */
    private void actualizarExistenciaActual(Partida movimiento) {
        List<Partida> entradas = new java.util.ArrayList<>();
        int metodo = this.getMetodoCosteo(movimiento.getIdProducto());
        ResultSet rs = null;
        String query = "";
        
        switch (metodo) {
            case Costo.MetodoCosteo.METODO_PROMEDIO_P:
            case Costo.MetodoCosteo.COSTO_IDENTIFICADO:
            case Costo.MetodoCosteo.METODO_PEPS:
                query = "SELECT M.id, M.idAlmacen, M.idMovtoComercial, M.idProducto, M.tipoMovimiento, M.cantidad, M.unidad, M.costoUnitario, M.costoPromedio, M.existActual, M.fechaMovimiento FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.tipoMovimiento = 0 AND M.existActual > 0 ORDER BY M.`timestamp` ASC;";
                break;
            case Costo.MetodoCosteo.METODO_UEPS:
                query = "SELECT M.id, M.idAlmacen, M.idMovtoComercial, M.idProducto, M.tipoMovimiento, M.cantidad, M.unidad, M.costoUnitario, M.costoPromedio, M.existActual, M.fechaMovimiento FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.tipoMovimiento = 0 AND M.existActual > 0 ORDER BY M.`timestamp` DESC;";
                break;
        }
        
        rs = ConexionBD.ejecutarQuery(query, movimiento.getIdProducto());
        Partida temp;

        try {
            while (rs.next()) {
                temp = new Partida();
                temp.setIdPartida(rs.getInt("id"));
                temp.setIdAlmacen(rs.getString("idAlmacen"));
                temp.setIdMovtoComercial(rs.getInt("idMovtoComercial"));
                temp.setIdProducto(rs.getInt("idProducto"));
                temp.setTipoMovimiento(rs.getInt("tipoMovimiento"));
                temp.setCantidad(rs.getDouble("cantidad"));
                temp.setUnidad(rs.getString("unidad"));
                temp.setCostoUnitario(rs.getDouble("costoUnitario"));
                temp.setCostoPromedio(rs.getDouble("costoPromedio"));
                temp.setExistenciaActual(rs.getDouble("existActual"));
                temp.setFechaMovimiento(rs.getDate("fechaMovimiento"));

                entradas.add(temp);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        double cant = movimiento.getCantidad();
        
        for (Partida mov : entradas) {
            if (mov.getExistenciaActual() <= cant) {
                cant = cant - mov.getExistenciaActual();
                //mov.setExistenciaActual(0);
            } else if (mov.getExistenciaActual() > cant) {
                mov.setExistenciaActual(mov.getExistenciaActual() - cant);
                cant = 0;
            }
            ConexionBD.ejecutarUpdate("UPDATE er_movimientos_inventario AS M SET M.existActual = " + mov.getExistenciaActual() + " WHERE id = " + mov.getIdPartida());
            if (cant == 0) {
                break;
            }
        }
    }
    
    /**
     * Registra la partida en la tabla de movimientos inventario.
     * @param movimiento 
     */
    public void generarMovimientoInventario(Partida movimiento) {

        String query = "INSERT INTO er_movimientos_inventario (idAlmacen, idMovtoComercial,"
                + " idProducto, tipoMovimiento, cantidad, unidad, costoUnitario, costoPromedio, existActual, "
                + "fechaMovimiento, `timestamp`) VALUES(" + movimiento.getIdAlmacen() + ", " + movimiento.getIdMovtoComercial()
                + ", " + movimiento.getIdProducto() + ", " + movimiento.getTipoMovimiento() + ", " + String.valueOf(movimiento.getCantidad())
                + ", '" + movimiento.getUnidad() + "', " + movimiento.getCostoUnitario() + ", " + movimiento.getCostoPromedio() + ", "
                + movimiento.getExistenciaActual() + ", DATE('" + movimiento.getFechaMovimiento() + "'), NOW())";

        //JOptionPane.showMessageDialog(null, query);
        ConexionBD.ejecutarUpdate(query);
    }
    
    /**
     * Calcula el costo unitario en base al metodo de Costo Producto.
     * 
     * @param producto
     * @return costo unitario
     */
    private double obtenerCostoPromedio(int producto) {

        ResultSet rs = ConexionBD.ejecutarQuery("SELECT M.costoPromedio FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.tipoMovimiento = 0 and M.fechaMovimiento <= ? ORDER BY M.fechaMovimiento DESC LIMIT 1;", producto, fechaCorte);

        try {
            if (!rs.isClosed()) {
                if (rs.next()) {
                    return rs.getDouble("costoPromedio");
                }
            } else {
                JOptionPane.showMessageDialog(null, "ResultSet is closed on class " + Thread.currentThread().getStackTrace()[2].getClassName() + " in line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
        }

        return 0;
    }

    /**
     * Calcula el costo unitario con el método de Costo identificado.
     * @param producto
     * @return 
     */
    private double calcularCostoIdentificado(int producto) {
        ResultSet rs;
        double ultimoCosto;

        rs = ConexionBD.ejecutarQuery("SELECT M.costoUnitario FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.tipoMovimiento = 0 ORDER BY `timestamp` DESC LIMIT 1", producto);

        try {
            if (rs.next()) {
                ultimoCosto = rs.getDouble("costoUnitario");
                return ultimoCosto;
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
        }

        return 0;
    }
    
    /*Calcula el costo unitario del movimiento cuando el metodo de costeo es peps o ueps
     *
     *@param producto id del producto que se esta calculando
     *@param cantidad cantidad de piezas del movimento a calcular
     *@param metodo de costeo - 1.-UEPS. 2.-PEPS
     *@return double costoUnitario regresa el costo unitario del movmiento.
     */
    private double calcularUepsPeps(int producto, double cantidad, int metodo) {
        List<Partida> entradas = new java.util.ArrayList<>();
        Partida temp;
        double costo;
        String query = "";
        
        if(metodo == Costo.MetodoCosteo.METODO_PEPS){
            query = "SELECT M.id, M.idAlmacen, M.idMovtoComercial, M.idProducto, M.tipoMovimiento, M.cantidad, M.unidad, M.costoUnitario, M.costoPromedio, M.existActual, M.fechaMovimiento FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.existActual > 0 AND M.tipoMovimiento = 0 ORDER BY M.fechaMovimiento;";
        }else if(metodo == Costo.MetodoCosteo.METODO_UEPS){
            query = "SELECT M.id, M.idAlmacen, M.idMovtoComercial, M.idProducto, M.tipoMovimiento, M.cantidad, M.unidad, M.costoUnitario, M.costoPromedio, M.existActual, M.fechaMovimiento FROM er_movimientos_inventario AS M WHERE M.idProducto = ? AND M.existActual > 0 AND M.tipoMovimiento = 0 ORDER BY M.fechaMovimiento DESC;";
        }
        
        ResultSet rs = ConexionBD.ejecutarQuery(query, producto);
        try {
            if (rs != null) {
                while (rs.next()) {
                    temp = new Partida();
                    temp.setIdPartida(rs.getInt("id"));
                    temp.setIdAlmacen(rs.getInt("idAlmacen"));
                    temp.setIdMovtoComercial(rs.getInt("idMovtoComercial"));
                    temp.setIdProducto(rs.getInt("idProducto"));
                    temp.setTipoMovimiento(rs.getInt("tipoMovimiento"));
                    temp.setCantidad(rs.getDouble("cantidad"));
                    temp.setUnidad(rs.getString("unidad"));
                    temp.setCostoUnitario(rs.getDouble("costoUnitario"));
                    temp.setCostoPromedio(rs.getDouble("costoPromedio"));
                    temp.setExistenciaActual(rs.getDouble("existActual"));
                    temp.setFechaMovimiento(rs.getDate("fechaMovimiento"));

                    entradas.add(temp);
                }
            } else {
                JOptionPane.showMessageDialog(null, "RESULTSET RETORNO NULL");
            }
        } catch (SQLException e) {
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));
        }

        double costoTotal = 0, costoUni = 0, cant = cantidad;

        for (Partida mov : entradas) {
            if (mov.getExistenciaActual() <= cant) {
                cant = cant - mov.getExistenciaActual();
                costoTotal = costoTotal + (mov.getExistenciaActual() * mov.getCostoUnitario());
                mov.setExistenciaActual(0);
            } else if (mov.getExistenciaActual() > cant) {
                mov.setExistenciaActual(mov.getExistenciaActual() - cant);
                costoTotal = costoTotal + ((mov.getExistenciaActual() - cant) * mov.getCostoUnitario());
                cant = 0;
            }
            if (cant == 0) {
                break;
            }

        }
        costoUni = costoTotal / cantidad;

        return costoUni;
    }

}
