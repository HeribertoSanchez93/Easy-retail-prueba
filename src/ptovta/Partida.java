/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptovta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * <h1>Partida.java</h1>
 * Almacena los datos de una partida para su ingreso y recuperacion a base de datos.
 * 
 * @author Cesar Martínez
 * @version 1.0
 * @package ptovta
 * @since 15-04-2015
 */
public class Partida{
    public static final int TIPO_ENTRADA = 0;
    public static final int TIPO_SALIDA = 1;
    private int idPartida;
    private int idAlmacen;
    private int idMovtoComercial;
    private int idProducto;
    private int tipoMovimiento;
    private double cantidad;
    private String unidad;
    private double costoUnitario;
    private double costoPromedio;
    private double existenciaActual;
    private Date fechaMovimiento;

    public Partida(){
        this.idAlmacen = 1;
        this.idMovtoComercial = 0;
        this.idProducto = 0;
        this.idMovtoComercial = Partida.TIPO_ENTRADA;
        this.cantidad = 0;
        this.unidad = "";
        this.costoUnitario = 0;
        this.costoPromedio = 0;
        this.existenciaActual = 0;
        this.fechaMovimiento = Calendar.getInstance().getTime();
    }
    
    
    /**
     * @return the idAlmacen
     */
    public int getIdAlmacen() {
        return idAlmacen;
    }

    /**
     * @param idAlmacen the idAlmacen to set
     */
    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
    
    /**
     * Busca el id del almacen en base al nombre y lo guarda.
     * 
     * @param nombreAlmacen El nombre del almacen del movimiento
     */
    public void setIdAlmacen(String nombreAlmacen) {
        int idAlmacen = 0;
        ResultSet rs;
        Connection con = ConexionBD.getConexion();
        
        rs = ConexionBD.ejecutarQuery("SELECT A.id_id FROM almas AS A WHERE alma = ? LIMIT 1", nombreAlmacen);
        
        try{
            if(rs.next()){
                idAlmacen = rs.getInt("id_id");
                System.out.println(idAlmacen);
            }
        }catch(SQLException e){
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null,  Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Star.class.getClass().getResource(Star.sRutIconEr)));
        }
        
        
        this.idAlmacen = idAlmacen;
    }

    /**
     * @return the idMovtoComercial
     */
    public int getIdMovtoComercial() {
        return idMovtoComercial;
    }

    /**
     * @param idMovtoComercial the idMovtoComercial to set
     */
    public void setIdMovtoComercial(int idMovtoComercial) {
        this.idMovtoComercial = idMovtoComercial;
    }
    
    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    /**
     * Establece el id del producto usando el nombre, busca en base de datos el id correspondiente
     * @param nombreProducto Nombre del producto del que se obtendra el id
     */
    public void setIdProducto(String nombreProducto) {
        int idProd = 0;
        
        Connection con = ConexionBD.getConexion();
        
        ResultSet rs = ConexionBD.ejecutarQuery("SELECT P.id_id FROM prods AS P WHERE P.prod = ? LIMIT 1", nombreProducto);
        
        try{
            if(rs.next()){
                idProd = rs.getInt("id_id");
            }
        }catch(SQLException e){
            
        }
        
        this.idProducto = idProd;
        
    }

    /**
     * @return the tipoMovimiento
     */
    public int getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the costoUnitario
     */
    public double getCostoUnitario() {
        return costoUnitario;
    }

    /**
     * @param costoUnitario the costoUnitario to set
     */
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    /**
     * @return the costoPromedio
     */
    public double getCostoPromedio() {
        return costoPromedio;
    }

    /**
     * @param costoPromedio the costoPromedio to set
     */
    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    /**
     * @return the existenciaActual
     */
    public double getExistenciaActual() {
        return existenciaActual;
    }

    /**
     * @param existenciaActual the existenciaActual to set
     */
    public void setExistenciaActual(double existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    /**
     * @return the fechaMovimiento
     */
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * @param fechaMovimiento the fechaMovimiento to set
     */
    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * @return the idPartida
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * @param idPartida the idPartida to set
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }
    
    
}
