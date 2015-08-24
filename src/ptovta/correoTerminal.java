package ptovta;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;

public class correoTerminal extends javax.swing.JFrame {

    // 23 06 2015 Felipe Ruiz Garcia /*creador de la clase*/
    // NO TIENE PORQUE TENER MAIN 
    private correoRegistro email = new correoRegistro(0);

    public correoTerminal() {
        setTitle("Sincronización, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);

        //this.setLayout(new GridLayout(1,99,10,10));
        initComponents();

        // TAMAÑO DE LA VENTANA
        this.setSize(540, 400);

        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        this.setResizable(true);

        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        // CONEXION BASE DE DATOS
        Connection con = Star.conAbrBas(true, false);

        if (this.getIngresocorreoBD(con) == 1) {
            /// INSERTA LOS DATOS YA EXISTENTES EN LA BASE DE DATOS EN LA FORMA
            /// metodo que retorna un email con los datos de la base de datos

            email = getCorreoBaseDatos(con);
            setCorreoBaseDatos(email);
        }

        setHistorial(con);
//        consecutivos(con);

        // CIERRA BASE DE DATOS
        Star.iCierrBas(con);
    }

    // ESTABLECE INFORMACION // LOS MAS RECIENTES MOVIMIENTOS DE IMPORTACION Y EXPORTACION  
    public void setHistorial(Connection con) {
        // 20 jul 2015
        // INFORMACION DEL ULTIMO EXPORTADO

        // ULTIMAS VENTAS Y PARTIDAS EXPORTADAS
        int ultVentaExpo = getControlExportar(con, "id_ultimaVentaExpo");
        //int ultPartidaExpo = getControlExportar(con, "id_ultimaPartidaExpo");

        // ULTIMAS VENTAS Y PARTIDAS IMPORTADAS
        String ultVentaImpo = getControlExportar_String(con, "fechaImpo");
        //int ultPartidaImpo = getControlExportar(con, "id_ultimaPartidaImpo");
        ultimaImportacionSincronizacion.setText(ultVentaImpo);
        
        // IMPORTACION // SI NO HA REALIZADO NINGUN MOVIMIENTO
        if (ultVentaExpo == 0 ) {
            // WHEN OTHERS ITEMS BE ADDED // to developer

            ultimaExportacionFechaSincronizacion1.setText("Ninguno");
            ultimaExportacionTipoSincronizacion1.setText("Ninguno");
            ultimaExportacionVentaSincronizacion.setText("0");
            return;
        }

        // EXPORTACION // SI NO HA REALIZADO NINGUN MOVIMIENTO
//        if ( ultVentaExpo == 0 &&  ultPartidaExpo == 0 ){
//            
//            return;    
//        }        
        // ELEMENTOS A FIJAR
        ultimaExportacionFechaSincronizacion1.setText(getControlExportar_String(con, "fechaExpo"));
        //ultimaExportacionTipoSincronizacion1.setText(getControlExportar_String(con, "tipo"));
        
        if(getControlExportar(con, "tipo") == -1){
            ultimaExportacionTipoSincronizacion1.setText("Automatico");
        } else{
            ultimaExportacionTipoSincronizacion1.setText("Todo");
        }
        
        ultimaExportacionVentaSincronizacion.setText("" + ultVentaExpo );

    }

    public void setCorreoBaseDatos(correoRegistro correo) {

        correo1.setText(correo.getCorreoDesde());
        contra1.setText(correo.getContraseñaDesde());
        nombreMostrar1.setText(correo.getMuestraDesde());

        direccionServer1.setText(correo.getDireccionServidor());
        puerto1.setText(correo.getPuerto());

        correoCentral1.setText(correo.getCorreoA());

        /*En la forma de sincronizar mete el correo*/
        correoPaEnvio.setText(correo.getCorreoDesde());
        centralParaEnvio.setText(correo.getCorreoA());

    }

    public correoRegistro getCorreoBaseDatos(Connection con) {

        //Abre la base de datos                             
        //Connection  con = Star.conAbrBas(true, false);
        //Si hubo error entonces 
        if (con == null) {
            return null;
        }

        /* nombre de la tabla de registro */
        String nametabla = "correo_terminal";

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el guardado del correo\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                //Star.iCierrBas(con);
                return null;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para la sincronización\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            //Star.iCierrBas(con);
            return null;
        }

        /*preparo la base de datos */
        try {
            sQ = "SELECT correo, contra, muestracomo, direccionserver, correoa, puerto FROM lol.correo_terminal;";

            st = con.createStatement();
            rs = st.executeQuery(sQ);

            if (rs.next()) {

                correoRegistro e = new correoRegistro(0);

                e.setCorreoDesde(rs.getString("correo"));
                e.setContraseñaDesde(rs.getString("contra"));
                e.setMuestraDesde(rs.getString("muestracomo"));

                e.setDireccionServidor(rs.getString("direccionserver"));
                e.setPuerto("" + rs.getInt("puerto"));

                e.setCorreoA(rs.getString("correoa"));

                return e;
                //  exito, se hizo la query exitosamente
            }

            /* cierra conexión base de datos */
            // Star.iCierrBas(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al intentar guardar los datos en la tabla " + nametabla + "\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexión base de datos */
            // Star.iCierrBas(con);  
        }
        return null;

    } /* FIN DE LA FUNCION QUE SET EL CORREO DESDE LA BASE DE DATOS*/


    // Felipe Ruiz Garcia 29 JUN 2015
    // Especial dimension para Tablet's
    public void reajustarVentana() {

        int widthScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int heightScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

        int widthWindow = this.getWidth();
        int heightWindow = this.getHeight();

        System.out.println(widthScreen + " " + heightScreen);
        System.out.println(widthWindow + " " + heightWindow);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        SincronizarTabbed = new javax.swing.JTabbedPane();
        ConfiguracionesPane = new javax.swing.JPanel();
        probar = new javax.swing.JButton();
        correo1 = new javax.swing.JTextField();
        correo_label1 = new javax.swing.JLabel();
        contra_label1 = new javax.swing.JLabel();
        contra1 = new javax.swing.JPasswordField();
        nombreMostrar_label1 = new javax.swing.JLabel();
        nombreMostrar1 = new javax.swing.JTextField();
        direccionServer_label1 = new javax.swing.JLabel();
        direccionServer1 = new javax.swing.JTextField();
        puerto_label1 = new javax.swing.JLabel();
        puerto1 = new javax.swing.JTextField();
        correoCentral1_label = new javax.swing.JLabel();
        correoCentral1 = new javax.swing.JTextField();
        guardar = new javax.swing.JButton();
        estado = new javax.swing.JLabel();
        title5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        title6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        ExportarPane = new javax.swing.JPanel();
        ExportarTodo = new javax.swing.JButton();
        importar = new javax.swing.JButton();
        title2 = new javax.swing.JLabel();
        title3 = new javax.swing.JLabel();
        ExportarAutomatico = new javax.swing.JButton();
        ultimaExportacionVentaSincronizacion = new javax.swing.JLabel();
        ultimaExportacionFechaSincronizacion1 = new javax.swing.JLabel();
        ultimaExportacionTipoSincronizacion1 = new javax.swing.JLabel();
        ultimaSincronizacion2 = new javax.swing.JLabel();
        tipoSincronizacion2 = new javax.swing.JLabel();
        ultimaVentaSincronizacion1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        ultimaVentaSincronizacion2 = new javax.swing.JLabel();
        ultimaImportacionSincronizacion = new javax.swing.JLabel();
        ultimaVentaSincronizacion3 = new javax.swing.JLabel();
        SincronizarPane = new javax.swing.JPanel();
        correoPaEnvio = new javax.swing.JTextField();
        correoParaEnvio_Label = new javax.swing.JLabel();
        centralParaEnvio_Label = new javax.swing.JLabel();
        centralParaEnvio = new javax.swing.JTextField();
        title4 = new javax.swing.JLabel();
        sincronizarPersonalizado = new javax.swing.JButton();
        estadoSincronizar = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 328, -1, -1));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 562, -1, -1));

        SincronizarTabbed.setName(""); // NOI18N

        ConfiguracionesPane.setForeground(new java.awt.Color(240, 240, 240));
        ConfiguracionesPane.setToolTipText("");
        ConfiguracionesPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        probar.setText("Probar");
        probar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                probarActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(probar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 140, -1));

        correo1.setToolTipText("Correo electrónico de la terminal.");
        correo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                correo1FocusLost(evt);
            }
        });
        correo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correo1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(correo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 195, -1));

        correo_label1.setText("Correo emisor");
        ConfiguracionesPane.add(correo_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 170, -1));

        contra_label1.setText("Contraseña");
        ConfiguracionesPane.add(contra_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 170, -1));

        contra1.setToolTipText("Contraseña del correo electrónico de la terminal.");
        contra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contra1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(contra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 195, -1));

        nombreMostrar_label1.setText("Nombre a mostrar");
        ConfiguracionesPane.add(nombreMostrar_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 170, -1));

        nombreMostrar1.setToolTipText("Nombre que se muestra sobre su correo electrónico.");
        nombreMostrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreMostrar1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(nombreMostrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 195, -1));

        direccionServer_label1.setText("Servidor de correo");
        ConfiguracionesPane.add(direccionServer_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 140, -1));

        direccionServer1.setToolTipText("Dirección del servidor del correo electrónico.");
        direccionServer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionServer1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(direccionServer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 195, -1));

        puerto_label1.setText("Puerto de correo");
        ConfiguracionesPane.add(puerto_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 160, -1));

        puerto1.setToolTipText("Puerto del servidor para el envio de correos electrónicos.");
        puerto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puerto1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(puerto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 195, -1));

        correoCentral1_label.setText("Correo receptor");
        ConfiguracionesPane.add(correoCentral1_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 150, -1));

        correoCentral1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                correoCentral1FocusLost(evt);
            }
        });
        correoCentral1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoCentral1ActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(correoCentral1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 195, -1));

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        ConfiguracionesPane.add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 140, -1));
        ConfiguracionesPane.add(estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 210, 20));

        title5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        title5.setText("Receptor");
        ConfiguracionesPane.add(title5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        ConfiguracionesPane.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 400, 10));

        title6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        title6.setText("Emisor");
        ConfiguracionesPane.add(title6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        ConfiguracionesPane.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 470, 10));
        ConfiguracionesPane.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 400, 10));

        SincronizarTabbed.addTab("Configuraciones", ConfiguracionesPane);

        ExportarPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ExportarTodo.setText("Exportar Todo");
        ExportarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarTodoActionPerformed(evt);
            }
        });
        ExportarPane.add(ExportarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 230, 50));

        importar.setText("Importar");
        importar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarActionPerformed(evt);
            }
        });
        ExportarPane.add(importar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 230, 50));

        title2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        title2.setText("Exportar ventas");
        ExportarPane.add(title2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        title3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        title3.setText("Importar ventas");
        ExportarPane.add(title3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        ExportarAutomatico.setText("Exportar Automático");
        ExportarAutomatico.setToolTipText("Exporte solo las ventas que no haya exportado antes.");
        ExportarAutomatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarAutomaticoActionPerformed(evt);
            }
        });
        ExportarPane.add(ExportarAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 230, 50));

        ultimaExportacionVentaSincronizacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ExportarPane.add(ultimaExportacionVentaSincronizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 180, 20));

        ultimaExportacionFechaSincronizacion1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ExportarPane.add(ultimaExportacionFechaSincronizacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 180, 20));

        ultimaExportacionTipoSincronizacion1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ExportarPane.add(ultimaExportacionTipoSincronizacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 180, 20));

        ultimaSincronizacion2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ultimaSincronizacion2.setText("Última exportación :");
        ExportarPane.add(ultimaSincronizacion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, -1, -1));

        tipoSincronizacion2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tipoSincronizacion2.setText("Tipo de exportación :");
        ExportarPane.add(tipoSincronizacion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, -1, -1));

        ultimaVentaSincronizacion1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ultimaVentaSincronizacion1.setText("exportación automática :");
        ExportarPane.add(ultimaVentaSincronizacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, -1, -1));
        ExportarPane.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 340, 10));
        ExportarPane.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 340, 10));

        ultimaVentaSincronizacion2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ultimaVentaSincronizacion2.setText("Última venta de");
        ExportarPane.add(ultimaVentaSincronizacion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, -1, -1));

        ultimaImportacionSincronizacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ExportarPane.add(ultimaImportacionSincronizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 180, 20));

        ultimaVentaSincronizacion3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ultimaVentaSincronizacion3.setText("Última importación :");
        ExportarPane.add(ultimaVentaSincronizacion3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, -1, -1));

        SincronizarTabbed.addTab("Importar y Exportar", ExportarPane);

        SincronizarPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        correoPaEnvio.setEditable(false);
        correoPaEnvio.setBackground(new java.awt.Color(204, 204, 204));
        correoPaEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoPaEnvioActionPerformed(evt);
            }
        });
        SincronizarPane.add(correoPaEnvio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 250, 20));

        correoParaEnvio_Label.setText("Correo emisor :");
        SincronizarPane.add(correoParaEnvio_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 20));

        centralParaEnvio_Label.setText("Correo receptor :");
        SincronizarPane.add(centralParaEnvio_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 250, 20));

        centralParaEnvio.setEditable(false);
        centralParaEnvio.setBackground(new java.awt.Color(204, 204, 204));
        centralParaEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centralParaEnvioActionPerformed(evt);
            }
        });
        SincronizarPane.add(centralParaEnvio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 250, 20));

        title4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        title4.setText("Sincronización de ventas");
        SincronizarPane.add(title4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        sincronizarPersonalizado.setText("Sincronizar");
        sincronizarPersonalizado.setToolTipText("Envia el documento que usted le indique");
        sincronizarPersonalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarPersonalizadoActionPerformed(evt);
            }
        });
        SincronizarPane.add(sincronizarPersonalizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 470, 60));
        SincronizarPane.add(estadoSincronizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 330, 20));
        SincronizarPane.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 310, 10));

        SincronizarTabbed.addTab("Sincronizar", SincronizarPane);

        getContentPane().add(SincronizarTabbed, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 390));
        SincronizarTabbed.getAccessibleContext().setAccessibleName("Configuraciones");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String getRutaCarpeta(Connection con) {

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp = Star.sGetRutCarp(con);

        //Si hubo error entonces regresa
        if (sCarp == null) {
            return "ERROR";
        }

        /*Si el directorio de devoluciones no existe entonces crea la carpeta*/
        String sRut = sCarp + "\\Sincronizacion";
        if (!new File(sRut).exists()) {
            new File(sRut).mkdir();
        }
        return sRut;
    }

    public String getNombreArchivo(Connection con) {

        String nameFile = "";

        try {
            // RECUPERAR ID NUEVO
            String sQInsert = "SELECT DATE_FORMAT(NOW(),'%Y-%b-%d %h_%i %p')";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sQInsert);

            if (rs.next()) {
                nameFile = rs.getString("DATE_FORMAT(NOW(),'%Y-%b-%d %h_%i %p')");
                // nameFile.replace(":", "-");
                return nameFile;
            }

        } catch (SQLException expnSQL) {
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
        }
        return nameFile;
    }


    private void ExportarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarTodoActionPerformed
        // Ruiz Garcia Felipe de Jesus
        //        exportarVentas();

        /*Haz todo el proceso en un thread*/
        //final String ruta = seleccionarArchivoExportar();
        Connection con = Star.conAbrBas(false, false);

        final String ruta = getRutaCarpeta(con) + "\\" + getNombreArchivo(con) + ".xlsx";

        //CIERRA LA BASE DE DATOS
        Star.iCierrBas(con);

        (new Thread() {
            @Override
            public void run() {
                
                int n = 0;
                
                // ESCRIBE EL ARCHIVO
                escribeXLS(creaCuerpo(n), ruta, n);
                                
                // ABRE BASE DATOS
                Connection con = Star.conAbrBas(false, false);

                // FIJA EL HISTORIAL
                setHistorial(con);

                //CIERRA LA BASE DE DATOS
                Star.iCierrBas(con);

            }/*Fin de public void run()*/

        }).start();
    }//GEN-LAST:event_ExportarTodoActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        estado.setText("Cargando...");

        setEmail(email, "", "");

        if (!email.verifica()) {
            estado.setText("Formulario incompleto");

        } else {

            // CONEXION A BASE DATOS
            Connection con = Star.conAbrBas(true, false);

            // VALIDO
            if (escribeCorreo(email, getIngresocorreoBD(con), con)) {
                estado.setText("Guardado con éxito");
                correoPaEnvio.setText(email.getCorreoDesde());
                centralParaEnvio.setText(email.getCorreoA());
            } else {
                estado.setText("Error al guardar");
            }

            Star.iCierrBas(con);
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void correoCentral1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoCentral1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoCentral1ActionPerformed

    private void correoCentral1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_correoCentral1FocusLost
        
        if(correoCentral1.getText().compareTo("") == 0){
            correoCentral1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        if (!email_valido(correoCentral1.getText())) {

            /*Coloca el borde rojo*/
            correoCentral1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El correo electrónico es invalido.\nPor favor ingresa un correo electrónico valido.", "Correo electrónico invalido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //correoCentral1.grabFocus();
        } else {
            correoCentral1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        }

    }//GEN-LAST:event_correoCentral1FocusLost

    private void puerto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puerto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_puerto1ActionPerformed

    private void direccionServer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionServer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionServer1ActionPerformed

    private void nombreMostrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreMostrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreMostrar1ActionPerformed

    private void contra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contra1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contra1ActionPerformed

    private void correo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_correo1FocusLost
        
        if(correo1.getText().compareTo("") == 0){
            correo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        if (!email_valido(correo1.getText())) {

            /*Coloca el borde rojo*/
            correo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El correo electrónico es invalido.\nPor favor ingresa un correo electrónico valido.", "Correo electrónico invalido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //correo1.grabFocus();
        } else {
            correo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        }

    }//GEN-LAST:event_correo1FocusLost

    private void probarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_probarActionPerformed

        estado.setText("");
        estado.setText("Cargando...");

        if (!correoRegistro.hayInternet()) {
            estado.setText("No cuenta con conexión a Internet");
            } else {
            setEmail(email, "Easy Retail® Admin - Prueba de conexión de correo electrónico", creaMensaje("Prueba de conexión para la sincronización del sistema Easy Retail®"));
            email.setCorreoA(correo1.getText());
            
            if (!email.enviaCorreo()) {
                estado.setText("Error en la configuración de correo");

            } else {
                estado.setText("Correo configurado exitosamente");

            }
        }
    }//GEN-LAST:event_probarActionPerformed

    private void importarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarActionPerformed
        // Ruiz Garcia Felipe de Jesus
        //importarVentas();

        /*Haz todo el proceso en un thread*/
        final String ruta = seleccionarArchivoImportar();

        (new Thread() {
            @Override
            public void run() {
                leeCuerpo(ruta);
            }/*Fin de public void run()*/

        }).start();


    }//GEN-LAST:event_importarActionPerformed

    private void correoPaEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoPaEnvioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoPaEnvioActionPerformed

    private void centralParaEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centralParaEnvioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_centralParaEnvioActionPerformed

    private void sincronizarPersonalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sincronizarPersonalizadoActionPerformed


        estadoSincronizar.setText("");
        estadoSincronizar.setText("Cargando...");

        if (!email.verifica()) {
            estadoSincronizar.setText("No se a configurado correctamente un correo");
            return;
        }

        if (!correoRegistro.hayInternet()) {
            estadoSincronizar.setText("No cuenta con conexión a Internet");

        } else {

            // SI SI HAY INTERNET // PREPARAMOS EL MENSAJE
            //"<img src=\"http://easyretail.com.mx/ file ">\n" +
            // path pictures src=\"http://easyretail.com.mx/wp-content/MANUAL/1.png\"
            // <img src=\"http://easyretail.com.mx/wp-content/registrada.png\" id=\"image_bussines\">\n" +
            String mensaje = "Para sincronizar su sistema descargue el archivo xls adjunto en este mail.<br>"
                    + "<br>"
                    + "<br>"
                    + "Dentro de su sistema Easy Retail® <b>haga click en la pestaña sincronizar.</b>"
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/1.png\" >\n"
                    + "<br>"
                    + "<br>"
                    + "<br> A continuación se mostrara una ventana, <b> vaya a la pestaña Importar y Exportar</b>"
                    + "<b> y presione el botón Importar e indique la ruta del archivo que descargo anteriormente</b>.<br> "
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/2.png\" >\n"
                    + "<br>"
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/3.png\" >\n"
                    + "<br>"
                    + "<br>"
                    + "Se mostraran notificaciones una vez terminada la sincronización."
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/4.png\" >\n"
                    + "<br>"
                    + "<br>"
                    + "Podemos comprobar que la sincronización fue exitosa desde el modulo de <b>ventas</b>."
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/6.png\" >\n"
                    + "<br>"
                    + "<img src=\"http://easyretail.com.mx/wp-content/MANUAL/7.png\" >\n"
                    + "<br>"
                    + "<br>"
                    + "";

            email.setMensaje(creaMensaje(mensaje));
            email.setAsunto("Easy Retail® Admin - Sincronización");

            if (!email.enviaCorreo(seleccionarArchivoImportar())) {
                estadoSincronizar.setText("Error en la configuración de correo");

            } else {
                estadoSincronizar.setText("Correo enviado exitosamente");

            }
        }
    }//GEN-LAST:event_sincronizarPersonalizadoActionPerformed

    private void ExportarAutomaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarAutomaticoActionPerformed

        Connection con = Star.conAbrBas(false, false);

        final String ruta = getRutaCarpeta(con) + "\\" + getNombreArchivo(con) + ".xlsx";

        //CIERRA LA BASE DE DATOS
        Star.iCierrBas(con);

        // Ruiz Garcia Felipe de Jesus
        //        exportarVentas();
        /*Haz todo el proceso en un thread*/
        //final String ruta = seleccionarArchivoExportar();
        (new Thread() {
            @Override
            public void run() {
                
                // DETERMINAMOS EL TIPO DE EXPORTACION // -1 automatico
                int n = -1;
                
                // ESCRIBE EL ARCHIVO
                escribeXLS(creaCuerpo(n), ruta, n);

                // ABRE BASE DATOS
                Connection con = Star.conAbrBas(false, false);

                // FIJA EL HISTORIAL
                setHistorial(con);

                //CIERRA LA BASE DE DATOS
                Star.iCierrBas(con);

            }/*Fin de public void run()*/

        }).start();
    }//GEN-LAST:event_ExportarAutomaticoActionPerformed

    private void correo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correo1ActionPerformed

    // FILE SELECTION 
    //Felipe Ruiz Garcia /**  IMPORTAR */
    //Funcion Que retorna la ruta de un Archivo, o directorio
    //     JFileChooser
    public String seleccionarArchivoImportar() {

        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel a sincronizar");

        /*Si el usuario no presiono aceptar entonces regresa*/
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            estadoSincronizar.setText("");
            return null;
        }

        /* ruta del archivo, y luego*/
        /*Lee la ruta seleccionada con el nombre del archivo*/
        final String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Si no es un archivo de excel entonces*/
        if (!sRut.endsWith("xlsx") && !sRut.endsWith("xls")) {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no válido", JOptionPane.INFORMATION_MESSAGE, null);
            estadoSincronizar.setText("Archivo invalido");
            return null;
        }

        return sRut;
    }

    // Felipe Ruiz Garcia /**  EXPORTAR */
    // Funcion Que retorna la ruta de un directorio y devuelve el nombre
    public String seleccionarArchivoExportar() {

        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Ruta para guardar el archivo");

        /*Si el usuario presiono aceptar entonces*/
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        // /*Lee la ruta seleccionada*/ le concatena la extension xls
        String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

        return sRut;
    }

    // CREATE DOCUMENTS TO FILE XLS
    // Se crea el documento parte por parte 
    // Si va a ser un solo documento, una sola ruta
    public void escribeXLS(XSSFWorkbook wkbok, final String sRut, int tipoExportacion) {
        /*Escribe los datos en el archivo*/
        try {
            /*Escribe el archivo en el sistema de archivos local*/
            FileOutputStream out;
            out = new FileOutputStream(new File(sRut));
            wkbok.write(out);
            out.close();
        } catch (IOException expnIO) {
            //Procesa el error y regresa
            //Star.iCierrBas(con);
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;
        }
        // AQUI SE ACTUALIZA LA TABLA CONTROL EXPORTAR ASEGURANDONOS DE LA CORRECTA EXPORTACION
        // QUE EL ARCHIVO PUDO SER GUARDADO EN EL DISCO
        // DE LO CONTRARIO NO SE DEBE ACTUALIZAR LA TABLA
        
        // SI SE ESCRIBIO EL DOCUMENTO // SE EXPORTO CORRECTAMENTE 
        // ENTONCES GUARDA EL TIPO COMO [ n ] -1 or 0
        Connection con = Star.conAbrBas(true, false);
        
        // SI LA EXPORTACION ES AUTOMAICA ENTONCES ACTUALIZA CONTROL EXPORTAR
        if(tipoExportacion == -1 ){ 
            actualizaControlExportar(con);
        }
        
        // GUARDA EL TIPO DE EXPORTACION QUE ES // AUTOMATICA O TODO
        setControlExportar(con, "tipo", tipoExportacion);
        
        //CIERRA LA BASE DE DATOS
        Star.iCierrBas(con);

        
        //Esconde la forma de loading
        Star.vOcultLoadin();

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Archivo exportado en: " + sRut + " con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Preguntar al usuario si quiere abrir el archivo*/
        Object[] op = {"Si", "No"};
        int iRes = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if (iRes == JOptionPane.NO_OPTION || iRes == JOptionPane.CLOSED_OPTION) {
            return;
        }

        /*Abre el archivo*/
        try {
            Desktop.getDesktop().open(new File(sRut));
        } catch (IOException expnIO) {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
        }
    }

    public void leeCuerpo(final String sRut) {

        /*Crea la instancia hacia el archivo a importar*/
        FileInputStream file;
        try {
            file = new FileInputStream(new File(sRut));
        } catch (IOException expnIO) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;
        }

        /*Instancia el objeto de excel*/
        XSSFWorkbook wkbok;
        try {
            wkbok = new XSSFWorkbook(file);
        } catch (Exception expnIO) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;
        }

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if (con == null) {
            return;
        }

            // AQUI COMIENZA LA LECTURA DE CADA HOJA
        // por cada hoja que lea, tiene que escribirla en la base de datos
        // 22 DE JULIO 
        // MODIFICACION 
        // LEE LA HOJA VENTAS ; POR CADA VENTA LEIDA  VA Y BUSCA LAS PARTIDAS 
        // LAS PARTIDAS ENCONTRADAS ASOCIADAS A LA VENTA ACTUAL SE INSERTAN EN LA BASE DE DATOS
        // SE INSERTAN CON EL NUEVO ID :
        // Felipe Ruiz Garcia // 
        //XSSFSheet hojaActual = wkbok.getSheet("VENTAS");
                    /*Obtiene la primera hoja del libro*/
        //String nombreHoja = hojaActual.getSheetName();
        // FUNCTION FOR SALES
        leeHojaVentas(con, wkbok);

        // CERRAR LA TRANSICION
        
        // ACTUALIZAMOS EL HISTORIAL
        setControlExportar(con, "fechaImpo", 0);
        setHistorial(con);
        
        // FIN DE LA ESCRITURA
        //cierra la Base de datos
        
        Star.iCierrBas(con);
    }

    public XSSFWorkbook creaCuerpo(int typeExportacion) {

        // DEPENDIENDO EL VALOR RECIBIDO [ typeExportacion ]
        // ES EL TIPO DE EXPORTACION QUE HARA :
        // ==== 0 TODO ====
        // GUARDA ABSOLUTAMENTE TODOS LOS REGISTROS EN UN ARCHIVO XLS
        // DESPUES DE FINALIZAR CON LA CREACION DE LA HOJA XLS NO HAY ACTIVIDAD
        // ==== -1 AUTOMATIZADO ====
        // CONSULTA LA TABLA controlExportar
        // EXPORTA SOLO AQUELLOS REGISTROS CUYO INDICE SEAN MAYORES QUE LA CONSULTA
        // ACTUALIZA LA TABLA CON LOS ULTIMOS VALORES QUE EXPORTA
        //
        // VARIABLE DETERMINA SI ES NECESARIO ACTUALIZAR LA TABLA controlExportar
        // SI EL TIPO DE EXPORTACION ES TODO [ 0 ] NO ES NECESARIO LA ACTUALIZACION
        // SI EL TIPO DE EXPORTACION ES AUTOMATIZADO [ -1 ] ES NECESARIO ACTUALIZAR
        boolean actualizar = false;

        /*Crea el objeto de excel*/
        XSSFWorkbook wkbok = new XSSFWorkbook();

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, false);

        // DECLARACION DE VARIABLES PARA LA EXPORTACION DE REGISTROS
        int registrosVentas = typeExportacion;
        int registrosPartidas = typeExportacion;

        //Si hubo error entonces regresa
        if (con == null) {
            return null;
        }

        // TIPO DE EXPORTACION
        if (typeExportacion == -1) {
            // SI EL TIPO DE EXPORTACION ES AUTOMATIZADO [ -1 ] ES NECESARIO ACTUALIZAR
            actualizar = true;

            registrosVentas = getControlExportar(con, "id_ultimaventaExpo");
            registrosPartidas = getControlExportar(con, "id_ultimapartidaExpo");

        } else if (typeExportacion == 0) {
            // SI EL TIPO DE EXPORTACION ES TODO [ 0 ] NO ES NECESARIO LA ACTUALIZACION
            actualizar = false;

            registrosVentas = 0;
            registrosPartidas = 0;
        }

// AQUI COMIENZA EL ORDEN EN LAS FUNCIONES PARA CREAR LAS HOJAS DEL XLS
        
        int ultimaVentaExportada;
        int ultimaPartidaExportada;
        
        creaHojaVentas(con, wkbok, "VENTAS", registrosVentas, actualizar);
        creaHojaPartidas(con, wkbok, "PARTIDAS", registrosPartidas, actualizar);

// FIN DE LA ESCRITURA
        //cierra la Base de datos
        Star.iCierrBas(con);

        return wkbok;
    }

    // Crea la hoja del documento XLS // recibe la conexion a la base de datos y el objeto para xls 
     /* retorna la hoja Excel con la exportacion de ventas */
    public void creaHojaVentas(Connection con, XSSFWorkbook wkbok, String nameSheet, int MayorAEsteIndice, boolean actualiza) {

        /*Crea la hoja en blanco*/
        XSSFSheet sheet = wkbok.createSheet(nameSheet);

        //Declara variables locales
        int iCont = 1;

        /*Pon los encabezados en el archivo de excel*/
        Map<String, Object[]> d = new TreeMap<>();
        d.put(Integer.toString(iCont), new Object[]{"VTA", "NOREFER", "NOSER", "MON", "VEND", "CODCOT", "FOLFISC", "TRANSID", "SELL", "SELLSAT", "CERTSAT", "LUGEXP", "REGFISC", "CADORI", "CORT", "FORMPAG", "NOCORT", "CIERR", "VTADEVP", "PTOVTA", "CATGRAL", "FACTU", "TIPCAM", "TIPDOC", "NOTCRED", "NOTCREDPAG", "IMPNOTCRED", "TOTDESCU", "TOTCOST", "TOTCOSTPROM", "TOTUEPS", "TOTPEPS", "CODEMP", "SER", "METPAG", "CTA", "FEMI", "FENT", "SUBTOT", "IMPUE", "TOT", "TIC", "ESTAD", "MOTIV", "OBSERV", "TIMBR", "AUTRECIBDE", "AUTMARC", "AUTMOD", "AUTCOLO", "AUTPLACS", "AUTNOM", "AUTTARCIRC", "AUTNUMLIC", "AUTTEL", "AUTDIRPART", "AUTDIROFI", "AUTTELOFI", "AUTIMPO", "AUTMOTIV", "ESTAC", "SUCU", "NOCAJ", "EXPORT", "EXTR1", "EXTR2", "EXTR3", "FVENC", "FALT", "FMOD"});
        //                    d.put(Integer.toString(iCont), new Object[] {"VTA","NOREFER","NOSER","MON","VEND","CODCOT","FOLFISC","TRANSID","SELL","SELLSAT","CERTSAT","LUGEXP","REGFISC","CADORI","CORT","FORMPAG","NOCORT","CIERR","VTADEVP","PTOVTA","CATGRAL","FACTU","TIPCAM","TIPDOC","NOTCRED","NOTCREDPAG","IMPNOTCRED","TOTDESCU","TOTCOST","TOTCOSTPROM","TOTUEPS"});

        /*Aumenta en uno el contador de filas del libro*/
        ++iCont;

        //Declara variables de la base de datos
        Statement st;
        ResultSet rs;
        String sQ;

        // EXPORTACION AUTOMATICA []
        int indiceActual = 0;

        /*Trae todos los registros de los proveedores*/
        try {

            sQ = "SELECT * from vtas";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while (rs.next()) {

                // COMPARAMOS SI LA VENTA ACTUAL ES MENOR O IGUAL AL ULTIMO EXPORTADO
                // DE SER ASI, NO LO EXPORTAMOS
                if (actualiza) {

                    // EXPORTACION AUTOMATICA []
                    // PARA EXPORTAR SOLO LOS QUE NO HAN SIDO EXPORTADOS ANTERIOR MENTE
                    // OBTENEMOS EL INDICE DE LA VENTA ACTUAL
                    indiceActual = rs.getInt("VTA");

                    if (indiceActual <= MayorAEsteIndice) {
                        continue;
                    }
                }
                /*Agrega el registro en la fila de excel*/
                //                         d.put(Integer.toString(iCont), new Object[] {rs.getString("VTA"), rs.getString("NOREFER"), rs.getString("NOSER")});
                d.put(Integer.toString(iCont), new Object[]{rs.getString("VTA"), rs.getString("NOREFER"), rs.getString("NOSER"), rs.getString("MON"), rs.getString("VEND"), rs.getString("CODCOT"), rs.getString("FOLFISC"), rs.getString("TRANSID"), rs.getString("SELL"), rs.getString("SELLSAT"), rs.getString("CERTSAT"), rs.getString("LUGEXP"), rs.getString("REGFISC"), rs.getString("CADORI"), rs.getString("CORT"), rs.getString("FORMPAG"), rs.getString("NOCORT"), rs.getString("CIERR"), rs.getString("VTADEVP"), rs.getString("PTOVTA"), rs.getString("CATGRAL"), rs.getString("FACTU"), rs.getString("TIPCAM"), rs.getString("TIPDOC"), rs.getString("NOTCRED"), rs.getString("NOTCREDPAG"), rs.getString("IMPNOTCRED"), rs.getString("TOTDESCU"), rs.getString("TOTCOST"), rs.getString("TOTCOSTPROM"), rs.getString("TOTUEPS"), rs.getString("TOTPEPS"), rs.getString("CODEMP"), rs.getString("SER"), rs.getString("METPAG"), rs.getString("CTA"), rs.getString("FEMI"), rs.getString("FENT"), rs.getString("SUBTOT"), rs.getString("IMPUE"), rs.getString("TOT"), rs.getString("TIC"), rs.getString("ESTAD"), rs.getString("MOTIV"), rs.getString("OBSERV"), rs.getString("TIMBR"), rs.getString("AUTRECIBDE"), rs.getString("AUTMARC"), rs.getString("AUTMOD"), rs.getString("AUTCOLO"), rs.getString("AUTPLACS"), rs.getString("AUTNOM"), rs.getString("AUTTARCIRC"), rs.getString("AUTNUMLIC"), rs.getString("AUTTEL"), rs.getString("AUTDIRPART"), rs.getString("AUTDIROFI"), rs.getString("AUTTELOFI"), rs.getString("AUTIMPO"), rs.getString("AUTMOTIV"), rs.getString("ESTAC"), rs.getString("SUCU"), rs.getString("NOCAJ"), rs.getString("EXPORT"), rs.getString("EXTR1"), rs.getString("EXTR2"), rs.getString("EXTR3"), rs.getString("FVENC"), rs.getString("FALT"), rs.getString("FMOD")});

                /*Aumenta en uno el contador de filas del libro*/
                ++iCont;
            }

            // EXPORTACION AUTOMATICA []
            // OBTENEMOS EL INDICE DEL ULTIMO REGISTRO EXPORTADO
            // ACTUALIZAMOS LA TABLA controlExportar CON ID REGISTRO EXPORTADO
            // SI TIENE QUE ACTUALIZAR LA BASE DE DATOS ENTONCES EJECUTA 
//            if (actualiza) {
//
//                // EL OBJETO LOCAL Connection [ con ] ES TRANSICIONAL 
//                //SE CREA UN OBJETO Connection NO TRANSICIONAL PARA EL METODO setControlExportar
//                // conTemporal
//                Connection conTemporal = Star.conAbrBas(true, false);
//
//                setControlExportar(conTemporal, "id_ultimaventaExpo", indiceActual);
//
//                //Cierra la base de datos y regresa
//                if (Star.iCierrBas(conTemporal) == -1) {
//                    return;
//                }
//
//            }

        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;
        }

        /*Obtiene todos los datos insertados en el objeto de excel*/
        Set<String> keyset = d.keySet();

        /*Recorre todas las filas*/
        int rownum = 0;
        for (String key : keyset) {
            /*Crea la fila*/
            Row row = sheet.createRow(rownum++);

            /*Obtiene todos los datos de la fila*/
            Object[] objArr = d.get(key);

            /*Recorre todos los datos de la fila*/
            int cellnum = 0;
            for (Object ob : objArr) {
                try {
                    /*Obtiene el valor a insertar en la celda*/
                    String sVal = ob.toString();


                    /*Si esta en la celda 0 y la fila es mayor a 1*/
                    if (cellnum == 0 && rownum > 1) {
                        /*Si es un número entonces conviertelo a su valor absoluto*/
                        try {
                            Double.parseDouble(sVal);
                            sVal = Integer.toString((int) Double.parseDouble(sVal));
                        } catch (NumberFormatException expnNumForm) {
                        }
                    }

                    /*Crea la celda y establecele el valor*/
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue(sVal);

                } catch (Exception rsd) {

                }

            }
        }

        /*Escribe el final del fichero*/
        Row row = sheet.createRow(rownum);
        Cell cell = row.createCell(0);
        cell.setCellValue("FINARCHIVO");

        //HOJA CON LA INFORMACION
        // return sheet;
    }

    //  [ OBSOLETO ]
    // lee la hoja de excel de las ventas // Inserta la informacion en la tabla VTAS
    // NO DEBEN AGREGARSE LOS ID DE LA TABLA // RELACIONARSE CON LAS PARTIDAS
    public void leeHojaVentas_OBSOLETO(Connection con, XSSFSheet hojaVentas, String hojaImportada) {

        String ventasDuplicadas = "";
        int ventas_duplicadas = 0;

        //Inicia la transacción
        if (Star.iIniTransCon(con) == -1) {
            return;
        }

        //Declara variables de la base de datos
        Statement st;

                // consulta la hoja
        //inicia la creacion de la query a ejecutar
        /*Contador de fila*/
        //int iConta                  = 1;               
        int contadorFila = 1;

        /*Inicializa el contador de la celda por cada fila*/
        //int iContCell               = 1;
        int contadorCelda = 1;

        // TODAS LAS FILAS
                /*Recorre todas las FILAS de excel*/
        Iterator<Row> rowIt = hojaVentas.iterator();
        while (rowIt.hasNext()) {
            /*Recorre todas columnas del archivo*/
            Row row = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // fila == 1 Headers / encabezados
                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
            if (contadorFila == 1) {
                ++contadorFila;
                continue;
            }

            /*Variable para leer las celdas*/
            //String sIn;                   
            String valorCeldaActual;

            /*Contiene el valor de la celda*/
                    //String sAlma;
            //String valorCelda;
            // QUERY A LA TABLA
                    /*Inicializa la consulta*/
            //String sQInsert    = "INSERT INTO almas(alma, almadescrip, dir1, dir2, dir3, sucu, nocaj, estac, respon) VALUES('";                    
            //String sQInsert    = "INSERT INTO VTAS (VTA, NOREFER, NOSER, MON, VEND, CODCOT, FOLFISC, TRANSID, SELL, SELLSAT, CERTSAT, LUGEXP, REGFISC, CADORI, CORT, FORMPAG, NOCORT, CIERR, VTADEVP, PTOVTA, CATGRAL, FACTU, TIPCAM, TIPDOC, NOTCRED, NOTCREDPAG, IMPNOTCRED, TOTDESCU, TOTCOST, TOTCOSTPROM, TOTUEPS, TOTPEPS, CODEMP, SER, METPAG, CTA, FEMI, FENT, SUBTOT, IMPUE, TOT, TIC, ESTAD, MOTIV, OBSERV, TIMBR, AUTRECIBDE, AUTMARC, AUTMOD, AUTCOLO, AUTPLACS, AUTNOM, AUTTARCIRC, AUTNUMLIC, AUTTEL, AUTDIRPART, AUTDIROFI, AUTTELOFI, AUTIMPO, AUTMOTIV, ESTAC, SUCU, NOCAJ, EXPORT, EXTR1, EXTR2, EXTR3, FVENC, FALT, FMOD) VALUES(";                    
            String sQInsert = "INSERT INTO VTAS (NOREFER, NOSER, MON, VEND, CODCOT, FOLFISC, TRANSID, SELL, SELLSAT, CERTSAT, LUGEXP, REGFISC, CADORI, CORT, FORMPAG, NOCORT, CIERR, VTADEVP, PTOVTA, CATGRAL, FACTU, TIPCAM, TIPDOC, NOTCRED, NOTCREDPAG, IMPNOTCRED, TOTDESCU, TOTCOST, TOTCOSTPROM, TOTUEPS, TOTPEPS, CODEMP, SER, METPAG, CTA, FEMI, FENT, SUBTOT, IMPUE, TOT, TIC, ESTAD, MOTIV, OBSERV, TIMBR, AUTRECIBDE, AUTMARC, AUTMOD, AUTCOLO, AUTPLACS, AUTNOM, AUTTARCIRC, AUTNUMLIC, AUTTEL, AUTDIRPART, AUTDIROFI, AUTTELOFI, AUTIMPO, AUTMOTIV, ESTAC, SUCU, NOCAJ, EXPORT, EXTR1, EXTR2, EXTR3, FVENC, FALT, FMOD) VALUES(";

// "NOREFER","NOSER","MON","VEND","CODCOT","FOLFISC","TRANSID","SELL","SELLSAT","CERTSAT","LUGEXP","REGFISC","CADORI","CORT","FORMPAG","NOCORT","CIERR","VTADEVP","PTOVTA","CATGRAL","FACTU","TIPCAM","TIPDOC","NOTCRED","NOTCREDPAG","IMPNOTCRED","TOTDESCU","TOTCOST","TOTCOSTPROM","TOTUEPS","TOTPEPS","CODEMP","SER","METPAG","CTA","FEMI","FENT","SUBTOT","IMPUE","TOT","TIC","ESTAD","MOTIV","OBSERV","TIMBR","AUTRECIBDE","AUTMARC","AUTMOD","AUTCOLO","AUTPLACS","AUTNOM","AUTTARCIRC","AUTNUMLIC","AUTTEL","AUTDIRPART","AUTDIROFI","AUTTELOFI","AUTIMPO","AUTMOTIV","ESTAC","SUCU","NOCAJ","EXPORT","EXTR1","EXTR2","EXTR3","FVENC","FALT","FMOD"
            // FALTA EL NOMBRE DE LA CELDA PARA COOMPARAR AQUELLAS QUE SON BINARIAS
            // A ESTAS NO DEBE PONERSELES COMILLAS 
            /*Recorre todas las celdas de la fila*/
            while (cellIterator.hasNext()) {
                /*Obtiene el objeto de la celda*/
                Cell cell = cellIterator.next();

                /*Determina el tipo de celda que es*/
                switch (cell.getCellType()) {
                    /*En caso de que sea de tipo string entonces*/
                    case Cell.CELL_TYPE_STRING:

                        /*Obtiene el valor de la celda*/
                        valorCeldaActual = cell.getStringCellValue();

                        /*Si es la celda 1 entonces*/
                        if (contadorCelda == 1) {
                            // CUANDO ES EL FIN DEL ARCHIVO
                                    /*Si es el fin del archivo entonces*/
                            if (valorCeldaActual.compareTo("FINARCHIVO") == 0) {
                                //Esconde la forma de loading
                                Star.vOcultLoadin();

                                //  NO PUEDE CERRAR PORQUE NO ES EL UNICO DOCUMENTO
                                // EL CIERRE DEBE HACERSE AL FINAL DE LA FUNCION QUE LLAMO A ESTA
                                //Termina la transacción
                                if (Star.iTermTransCon(con) == -1) {
                                    return;
                                }

//                                        //Cierra la base de datos y regresa
//                                        if(Star.iCierrBas(con)==-1)                                
//                                            return;
                                String MENSAJE = "Exito en la importación de ";

                                // EL TOTAAL DE LAS PARTIDAS IMPORTADAS
                                int importadasConExito = contadorFila - 2;

                                if (ventas_duplicadas != 0) {
                                    importadasConExito -= ventas_duplicadas;

                                    ventasDuplicadas = ventasDuplicadas.substring(0, ventasDuplicadas.length() - 2);

                                    MENSAJE += importadasConExito + " " + hojaImportada
                                            + ".\nSe encontraron " + ventas_duplicadas + " ventas duplicadas [" + ventasDuplicadas + "]";

                                } else {
                                    MENSAJE += importadasConExito + " " + hojaImportada + ".";
                                }
                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, MENSAJE, hojaImportada, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                return;
                            } //  DE LO CONTRAARIO ESTARA LEYENDO LOS ID [ VTA ]DEL ARCHIVO 
                            //  HAY QUE CONTINUAR IGNORAR SU VALOR
                            /*Else no es el final del archivo entonces*/ else {
                                /*Guarda el código del almácen y completa la consulta*/
                                sQInsert += "'" + cell.getStringCellValue() + "', ";

                                //Muestra el loading
                                Star.vMostLoading("");
                            }

                        }/*Fin de if(iContCell==1)*/ // AQUI PARA LOS REGISTROS DE LA TABLA VTAS QUE SON DE TIPO BITS  
                        // LA INSERCCION DE ESTOS EN LA QUERY DEBE HACERSE SIN COMILLAS
                        // DE LO CONTRARIO, SE PRESENTAN COMPLICACIONES
                        //contadorCelda ==19 || contadorCelda ==21 || contadorCelda ==45 || contadorCelda == 63
                        else if (contadorCelda == 20 || contadorCelda == 22 || contadorCelda == 46 || contadorCelda == 64) {
                            sQInsert += cell.getStringCellValue() + ", ";

                        } //                                   else  if(iContCell==43)
                        //                                    {
                        //                                        sQInsert   += " " +cell.getStringCellValue() + ", ";                                        
                        //
                        //                                    }
                        //                                   
                        //                                
                        /*Else if: Descripción*/ else {
                            sQInsert += "'" + cell.getStringCellValue() + "', ";
                        }

//                                
//                                else if(iContCell==2)                                                                                                       
//                                    sQInsert   += cell.getStringCellValue() + "','";  
//                                
//                                /*Else if: Dirección 1*/
//                                else if(iContCell==3)                                                                    
//                                    sQInsert   += cell.getStringCellValue() + "','";
//                                /*Else if: Dirección 2*/
//                                else if(iContCell==4)                                                                    
//                                    sQInsert   += cell.getStringCellValue() + "','";
//                                /*Else if: Dirección 3*/
//                                else if(iContCell==5)                                                                    
//                                    sQInsert   += cell.getStringCellValue() + "','";
                        break;

                }/*Fin de switch(cell.getCellType())*/

                /*Aumenta en uno el contador de las celdas*/
                ++contadorCelda;

            }/*Fin de while(cellIterator.hasNext())*/

            /*Aumenta en uno el contador de las filas*/
            ++contadorFila;

            /*Resetea el contador de celdas*/
            contadorCelda = 1;

            // SOLO LA COMA
                    /*Quita los últimos carácteres inválidos*/
            sQInsert = sQInsert.substring(0, sQInsert.length() - 2);

//                      INNECESARIO PARA VENTAS
//                    /*Agrega el terminador de la consulta*/
//                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "', '')";
            sQInsert += ")";
//                    sQInsert = sQInsert.replace("'.',", "null,");
                    /*Inserta en la base de datos el registro*/
            try {
                st = con.createStatement();
                st.executeUpdate(sQInsert);
            } catch (SQLException expnSQL) {
                String mensaje = expnSQL.getMessage();

                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);

            }
        }/*Fin de while(rowIt.hasNext())*/

    }

    // lee la hoja de excel de las ventas // Inserta la informacion en la tabla VTAS
    // NO DEBEN AGREGARSE LOS ID DE LA TABLA // RELACIONARSE CON LAS PARTIDAS
    public void leeHojaVentas(Connection con, XSSFWorkbook libro) {

        XSSFSheet hojaVentas = libro.getSheet("VENTAS");

        String hojaImportada = "VENTAS";

        String ID_viejo = "";
        String ID_nuevo = "";

        boolean ventaDuplicada = false;
        int contadorDuplicadas = 0;

        //Inicia la transacción
        if (Star.iIniTransCon(con) == -1) {
            return;
        }

        //Declara variables de la base de datos
        Statement st;
        ResultSet rs;
        // consulta la hoja

        /*Contador de fila*/
        int contadorFila = 1;

        /*Inicializa el contador de la celda por cada fila*/
        int contadorCelda = 1;

        // TODAS LAS FILAS
                /*Recorre todas las FILAS de excel*/
        Iterator<Row> rowIt = hojaVentas.iterator();
        while (rowIt.hasNext()) {
            /*Recorre todas columnas del archivo*/
            Row row = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // fila == 1 Headers / encabezados
                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
            if (contadorFila == 1) {
                ++contadorFila;
                continue;
            }

            /*Variable para leer las celdas*/
            String valorCeldaActual;

            // VEMOS SI LA VENTA ( REGISTRO ACTUAL ) NO ESTA DUPLICADA
            Cell cell = row.getCell(0);
            valorCeldaActual = cell.getStringCellValue();

            if (valorCeldaActual.compareTo("FINARCHIVO") == 0) {
                //Esconde la forma de loading
                Star.vOcultLoadin();

                //  NO PUEDE CERRAR PORQUE NO ES EL UNICO DOCUMENTO
                // EL CIERRE DEBE HACERSE AL FINAL DE LA FUNCION QUE LLAMO A ESTA
                String MENSAJE = "EXITO EN LA IMPORTACIÓN DE ";

                // EL TOTAAL DE LAS PARTIDAS IMPORTADAS
                int importadasConExito = contadorFila - 2 - contadorDuplicadas;

                MENSAJE += importadasConExito + " " + hojaImportada + ".";

                if (contadorDuplicadas != 0) {
                    MENSAJE += "\nSE ENCONTRARON " + contadorDuplicadas + " " + hojaImportada + "  DUPLICADAS ";
                }


                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, MENSAJE, hojaImportada, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            cell = row.getCell(1);
            String norefer = cell.getStringCellValue();

            cell = row.getCell(2);
            String noser = cell.getStringCellValue();

            ventaDuplicada = duplicados(con, norefer, noser);

            if (ventaDuplicada) {
                ++contadorFila;
                ++contadorDuplicadas;
                continue;
            }
                    // QUERY A LA TABLA
                    /*Inicializa la consulta*/
            //String sQInsert    = "INSERT INTO VTAS (VTA, NOREFER, NOSER, MON, VEND, CODCOT, FOLFISC, TRANSID, SELL, SELLSAT, CERTSAT, LUGEXP, REGFISC, CADORI, CORT, FORMPAG, NOCORT, CIERR, VTADEVP, PTOVTA, CATGRAL, FACTU, TIPCAM, TIPDOC, NOTCRED, NOTCREDPAG, IMPNOTCRED, TOTDESCU, TOTCOST, TOTCOSTPROM, TOTUEPS, TOTPEPS, CODEMP, SER, METPAG, CTA, FEMI, FENT, SUBTOT, IMPUE, TOT, TIC, ESTAD, MOTIV, OBSERV, TIMBR, AUTRECIBDE, AUTMARC, AUTMOD, AUTCOLO, AUTPLACS, AUTNOM, AUTTARCIRC, AUTNUMLIC, AUTTEL, AUTDIRPART, AUTDIROFI, AUTTELOFI, AUTIMPO, AUTMOTIV, ESTAC, SUCU, NOCAJ, EXPORT, EXTR1, EXTR2, EXTR3, FVENC, FALT, FMOD) VALUES(";                    
            String sQInsert = "INSERT INTO VTAS (NOREFER, NOSER, MON, VEND, CODCOT, FOLFISC, TRANSID, SELL, SELLSAT, CERTSAT, LUGEXP, REGFISC, CADORI, CORT, FORMPAG, NOCORT, CIERR, VTADEVP, PTOVTA, CATGRAL, FACTU, TIPCAM, TIPDOC, NOTCRED, NOTCREDPAG, IMPNOTCRED, TOTDESCU, TOTCOST, TOTCOSTPROM, TOTUEPS, TOTPEPS, CODEMP, SER, METPAG, CTA, FEMI, FENT, SUBTOT, IMPUE, TOT, TIC, ESTAD, MOTIV, OBSERV, TIMBR, AUTRECIBDE, AUTMARC, AUTMOD, AUTCOLO, AUTPLACS, AUTNOM, AUTTARCIRC, AUTNUMLIC, AUTTEL, AUTDIRPART, AUTDIROFI, AUTTELOFI, AUTIMPO, AUTMOTIV, ESTAC, SUCU, NOCAJ, EXPORT, EXTR1, EXTR2, EXTR3, FVENC, FALT, FMOD) VALUES(";

            /*Recorre todas las celdas de la fila*/
            while (cellIterator.hasNext()) {
                /*Obtiene el objeto de la celda*/
                cell = cellIterator.next();

                /*Determina el tipo de celda que es*/
                switch (cell.getCellType()) {
                    /*En caso de que sea de tipo string entonces*/
                    case Cell.CELL_TYPE_STRING:

                        /*Obtiene el valor de la celda*/
                        valorCeldaActual = cell.getStringCellValue();

                        // SI EL CAMPO ES EL PRIMERO, O ES EL FINAL DEL ARCHIVO O ES ID DE VENTA [ vta ]
                                /*Si es la celda 1 entonces*/
                        if (contadorCelda == 1) {

                            //  DE LO CONTRAARIO ESTARA LEYENDO LOS ID [ VTA ]DEL ARCHIVO 
                            //  HAY QUE CONTINUAR IGNORAR SU VALOR
                            // ID VIEJO
                            /*Else no es el final del archivo entonces*/
                            ID_viejo = valorCeldaActual;

                        }/*Fin de if(iContCell==1)*/ // AQUI PARA LOS REGISTROS DE LA TABLA VTAS QUE SON DE TIPO BITS  
                        // LA INSERCCION DE ESTOS EN LA QUERY DEBE HACERSE SIN COMILLAS
                        // DE LO CONTRARIO, SE PRESENTAN COMPLICACIONES
                        // CELDAS QUE BINARIAS QUE DEBEN INCLUIRSE SIN COMILLAS
                        // (contadorCelda ==19 || contadorCelda ==21 || contadorCelda ==45 || contadorCelda == 63 )
                        else if (contadorCelda == 20 || contadorCelda == 22 || contadorCelda == 46 || contadorCelda == 64) {
                            sQInsert += cell.getStringCellValue() + ", ";

                        } /*Else if: Descripción*/ else {
                            sQInsert += "'" + cell.getStringCellValue() + "', ";
                        }

                        break;

                }/*Fin de switch(cell.getCellType())*/

                /*Aumenta en uno el contador de las celdas*/
                ++contadorCelda;

            }/*Fin de while(cellIterator.hasNext())*/

            /*Aumenta en uno el contador de las filas*/
            ++contadorFila;

            /*Resetea el contador de celdas*/
            contadorCelda = 1;

            // SOLO EL ESPACIO Y LA COMA
                    /*Quita los últimos carácteres inválidos*/
            sQInsert = sQInsert.substring(0, sQInsert.length() - 2);

            // INNECESARIO PARA VENTAS
            // /*Agrega el terminador de la consulta*/
            sQInsert += ")";

            /*Inserta en la base de datos el registro*/
            try {
                st = con.createStatement();
                st.executeUpdate(sQInsert);
            } catch (SQLException expnSQL) {
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            }

            try {
                // RECUPERAR ID NUEVO
                sQInsert = "SELECT  vta  FROM VTAS ORDER BY vta DESC LIMIT 1";
                st = con.createStatement();
                rs = st.executeQuery(sQInsert);

                if (rs.next()) {
                    ID_nuevo = rs.getString("vta");

                    leeHojaPartidas(con, libro.getSheet("PARTIDAS"), ID_viejo, ID_nuevo);
                }

            } catch (SQLException expnSQL) {
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            }

        }/*Fin de while(rowIt.hasNext())*/

    }

    // PRIMERO DEBO ENCONTRA LA PARTIDA DESEADA
    // LUEGO ESTA INSERTARLA
    // Y VOLVER A BUSCAR HASTA TERMINAR
    // NECESITO UN METODO QUE RECIBA UNA HOJA, UN ID VIEJO Y UN ID NUEVO
    // SE VA A LEER TODAS LAS FILAS EN BUSQUEDA DE QUE ENCUENTRE PARTIDA CON EL ID VIEJO
    // CON LA FILA RESULTANTE LA INSETA EN LA BASE DE DATOS CON EL ID NUEVO
    // SIN BUGS ? EL ARCHIVO NO SE MODIFICAAAAA 
    // SOLO SE MOFICA LA QUERY PARA LA INSERCCION
    public void leeHojaPartidas(Connection con, XSSFSheet hojaPartidas, String id_Viejo, String id_New) {

        /*Contador de fila*/
        int contadorFila = 1;

        // TODAS LAS FILAS
                /*Recorre todas las FILAS de excel*/
        Iterator<Row> rowIt = hojaPartidas.iterator();
        while (rowIt.hasNext()) {
            /*Recorre todas columnas del archivo*/
            Row row = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // fila == 1 Headers / encabezados
                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
            if (contadorFila == 1) {
                ++contadorFila;
                continue;
            }

            /*Variable para leer las celdas*/
            //String sIn;                   
            String valorCeldaActual = "";

            // CELDAS A PARTIR DE 0 
            // BUSCAMOS EL FIN DEL ARCHIVO
            Cell cell = row.getCell(0);
            valorCeldaActual = cell.getStringCellValue();
            if (valorCeldaActual.compareTo("FINARCHIVO") == 0) {

                ++contadorFila;
                continue;

            }

            // CELDA 1 ID VTAS             
            cell = row.getCell(1);

            /*Determina el tipo de celda que es*/
            switch (cell.getCellType()) {
                /*En caso de que sea de tipo string entonces*/
                case Cell.CELL_TYPE_STRING:

                    /*Obtiene el valor de la celda*/
                    valorCeldaActual = cell.getStringCellValue();

                    // EL ID  DE LA VENTA EN LAS PARTIDAS ES EL CAMPO 1
                                /*Si es la celda 1 entonces*/
                    if (valorCeldaActual.compareTo(id_Viejo) == 0) {
                        // ENVIA LA FILA PARA QUE LA INSERTE...
                        LeeInsertaPartida(con, cellIterator, id_New);
                    }
                    // AQUI PARA LOS REGISTROS DE LA TABLA VTAS QUE SON DE TIPO BITS  
                    // LA INSERCCION DE ESTOS EN LA QUERY DEBE HACERSE SIN COMILLAS
                    // DE LO CONTRARIO, SE PRESENTAN COMPLICACIONES

                    break;

            }/*Fin de switch(cell.getCellType())*/


            /*Aumenta en uno el contador de las filas*/
            ++contadorFila;

        }/*Fin de while(rowIt.hasNext())*/

    }

    // MMETODO QUE INSERTA LA PARTIDA QUE SE LE INDICA CON EL VTA QUE SE LE INDIQUE
    public void LeeInsertaPartida(Connection con, Iterator<Cell> cellIterator, String id_new) {

        //Inicia la transacción
        if (Star.iIniTransCon(con) == -1) {
            return;
        }

        //Declara variables de la base de datos
        Statement st;
        String sQ;

                // consulta la hoja
        //inicia la creacion de la query a ejecutar
        /*Inicializa el contador de la celda por cada fila*/
        //int iContCell               = 1;
        int contadorCelda = 1;

        /*Variable para leer las celdas*/
        //String sIn;                   
        String valorCeldaActual;

        /*Contiene el valor de la celda*/
        //String sAlma;
        //String valorCelda;
        // QUERY A LA TABLA
                    /*Inicializa la consulta*/
        String sQInsert = "INSERT INTO PARTVTA (prod, vta, tipdoc, cant, tipcam, devs, garan, eskit, kitmae, idkit, idlotped, list, unid, codimpue, alma, serprod, comenser, descrip, pre, descu, costprom, cost, idultcost, peps, idpeps, ueps, idueps, mon, lot, pedimen, fcadu, impo, impue, tall, colo, cantentre, entrenow, export, extr1, extr2, extr3, fentre, falt, fmod) VALUES(";
                    // PROD, VTA, TIPDOC, CANT, TIPCAM, DEVS, GARAN, ESKIT, KITMAE, IDKIT, IDLOTPED, LIST, UNID, CODIMPUE, ALMA, SERPROD, COMENSER, DESCRIP, PRE, DESCU, COSTPROM, COST, IDULTCOST, PEPS, IDPEPS, UEPS, IDUEPS, MON, LOT, PEDIMEN, FCADU, IMPO, IMPUE, TALL, COLO, CANTENTRE, ENTRENOW, EXPORT, EXTR1, EXTR2, EXTR3, FENTRE, FALT, FMOD    

        // FALTA EL NOMBRE DE LA CELDA PARA COOMPARAR AQUELLAS QUE SON BINARIAS
        // A ESTAS NO DEBE PONERSELES COMILLAS 
        /*Recorre todas las celdas de la fila*/
        while (cellIterator.hasNext()) {
            /*Obtiene el objeto de la celda*/
            Cell cell = cellIterator.next();

            /*Determina el tipo de celda que es*/
            switch (cell.getCellType()) {
                /*En caso de que sea de tipo string entonces*/
                case Cell.CELL_TYPE_STRING:

                    /*Obtiene el valor de la celda*/
                    valorCeldaActual = cell.getStringCellValue();

                    // EL ID DE ESTAS LAS VENTAS ES EL CAMPO 2
                                /*Si es la celda 1 entonces*/
//                    if (contadorCelda == 1) {
//                        // CUANDO ES EL FIN DEL ARCHIVO
//                                    /*Si es el fin del archivo entonces*/
//                        if (valorCeldaActual.compareTo("FINARCHIVO") == 0) {
//                            //Esconde la forma de loading
//                            Star.vOcultLoadin();
//
//                                //  NO PUEDE CERRAR PORQUE NO ES EL UNICO DOCUMENTO
//                            // EL CIERRE DEBE HACERSE AL FINAL DE LA FUNCION QUE LLAMO A ESTA
//                            //Termina la transacción
//                            if (Star.iTermTransCon(con) == -1) {
//                                return;
//                            }
//
////                                        //Cierra la base de datos y regresa
////                                        if(Star.iCierrBas(con)==-1)                                
////                                            return;
//                            String MENSAJE = "Exito en la importación de ";
//
//                            // EL TOTAAL DE LAS PARTIDAS IMPORTADAS
//                            int importadasConExito = contadorFila - 2;
//
//                            if (ventas_duplicadas != 0) {
//                                importadasConExito -= ventas_duplicadas;
//
//                                ventasDuplicadas = ventasDuplicadas.substring(0, ventasDuplicadas.length() - 2);
//
//                                MENSAJE += importadasConExito + " " + hojaImportada
//                                        + ".\nSe encontraron " + ventas_duplicadas + " partidas duplicadas [" + ventasDuplicadas + "]";
//
//                            } else {
//                                MENSAJE += importadasConExito + " " + hojaImportada + ".";
//                            }
//                            // LAS VENTAS DUPLICADAS ELIMINAR LAS ULTIMOS 2 CARAACTERS LA COMA Y EL ESPACIO 
//                                        /*Mensajea y regresa*/
//                            JOptionPane.showMessageDialog(null, MENSAJE, hojaImportada, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                            return;
//                        } /*Else no es el final del archivo entonces*/ else {
//                            /*Guarda el código del almácen y completa la consulta*/
//                            sQInsert += "'" + cell.getStringCellValue() + "', ";
//
//                            //Muestra el loading
//                            Star.vMostLoading("");
//                        }
//
//                    }/*Fin de if(iContCell==1)*/ // AQUI PARA LOS REGISTROS DE LA TABLA VTAS QUE SON DE TIPO BITS  
                    // LA INSERCCION DE ESTOS EN LA QUERY DEBE HACERSE SIN COMILLAS
                    // DE LO CONTRARIO, SE PRESENTAN COMPLICACIONES
                    //else  if(contadorCelda == 8 || contadorCelda == 38 )
                    //(contadorCelda == 9 || contadorCelda == 39)
                    if (contadorCelda == 2) {
                        sQInsert += "'" + id_new + "', ";

                    } else if (contadorCelda == 8 || contadorCelda == 38) {
                        sQInsert += cell.getStringCellValue() + ", ";

                    } /*Else if: Descripción*/ else {
                        sQInsert += "'" + cell.getStringCellValue() + "', ";
                    }

                    break;

            }/*Fin de switch(cell.getCellType())*/

            /*Aumenta en uno el contador de las celdas*/
            ++contadorCelda;

        }/*Fin de while(cellIterator.hasNext())*/
        /*Resetea el contador de celdas*/

        contadorCelda = 1;

        // SOLO LA COMA
                    /*Quita los últimos carácteres inválidos*/
        sQInsert = sQInsert.substring(0, sQInsert.length() - 2);

//                      INNECESARIO PARA VENTAS
//                    /*Agrega el terminador de la consulta*/
//                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "', '')";
        sQInsert += ")";
//                    sQInsert = sQInsert.replace("'.',", "null,");
                    /*Inserta en la base de datos el registro*/
        try {
            st = con.createStatement();
            st.executeUpdate(sQInsert);

        } catch (SQLException expnSQL) {

            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);

        }

    }

    // RECIBE UN FOLIO Y UNA SERIE
    // BUSCA CONCIDENCIAS DE AMBOS EN BASE DE DATOS // TABLA VENTAS [ vtas ]
    // SI ENCUENTRA COINCIDENCIAS [NOSER Y NOREFER] RETORNA TRUE 
    // SI NO ESTA DUPLICADO RETORNA FALSE
    public boolean duplicados(Connection con, String norefer, String noser) {

        //Declara variables de la base de datos
        Statement st;
        ResultSet rs;
        String sQ;

        /*Trae todos los registros de los proveedores*/
        try {
            sQ = "SELECT norefer, noser from VTAS ";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while (rs.next()) {

                String NOREFER = rs.getString("norefer");
                String NOSER = rs.getString("noser");

                if (NOREFER.compareToIgnoreCase(norefer) == 0 && NOSER.compareToIgnoreCase(noser) == 0) {
                    return true;
                }

            }
        } catch (SQLException expnSQL) {
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
        }

        return false;
    }

// [ OBSOLETO ]
    // LEE TODOS LOS VALORES DE LA SHEET RECIBIDA
    // CREA UNA QUERY POR CADA FILA LEIDA 
    // EJECUTA LA QUERY [INSERTA EN LA BASE DE DATOS]
    public void leeHojaPartidas_OBSOLETO(Connection con, XSSFSheet hojaPartidas, String hojaImportada, int idViejo, int idNew) {

        String ventasDuplicadas = "";
        int ventas_duplicadas = 0;

        //Inicia la transacción
        if (Star.iIniTransCon(con) == -1) {
            return;
        }

        //Declara variables de la base de datos
        Statement st;
        String sQ;

                // consulta la hoja
        //inicia la creacion de la query a ejecutar
        /*Contador de fila*/
        //int iConta                  = 1;               
        int contadorFila = 1;

        /*Inicializa el contador de la celda por cada fila*/
        //int iContCell               = 1;
        int contadorCelda = 1;

        // TODAS LAS FILAS
                /*Recorre todas las FILAS de excel*/
        Iterator<Row> rowIt = hojaPartidas.iterator();
        while (rowIt.hasNext()) {
            /*Recorre todas columnas del archivo*/
            Row row = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // fila == 1 Headers / encabezados
                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
            if (contadorFila == 1) {
                ++contadorFila;
                continue;
            }

            /*Variable para leer las celdas*/
            //String sIn;                   
            String valorCeldaActual;

            /*Contiene el valor de la celda*/
            //String sAlma;
            //String valorCelda;
            // QUERY A LA TABLA
                    /*Inicializa la consulta*/
            String sQInsert = "INSERT INTO PARTVTA (id_id, prod, vta, tipdoc, cant, tipcam, devs, garan, eskit, kitmae, idkit, idlotped, list, unid, codimpue, alma, serprod, comenser, descrip, pre, descu, costprom, cost, idultcost, peps, idpeps, ueps, idueps, mon, lot, pedimen, fcadu, impo, impue, tall, colo, cantentre, entrenow, export, extr1, extr2, extr3, fentre, falt, fmod) VALUES(";
                    // PROD, VTA, TIPDOC, CANT, TIPCAM, DEVS, GARAN, ESKIT, KITMAE, IDKIT, IDLOTPED, LIST, UNID, CODIMPUE, ALMA, SERPROD, COMENSER, DESCRIP, PRE, DESCU, COSTPROM, COST, IDULTCOST, PEPS, IDPEPS, UEPS, IDUEPS, MON, LOT, PEDIMEN, FCADU, IMPO, IMPUE, TALL, COLO, CANTENTRE, ENTRENOW, EXPORT, EXTR1, EXTR2, EXTR3, FENTRE, FALT, FMOD    

            // FALTA EL NOMBRE DE LA CELDA PARA COOMPARAR AQUELLAS QUE SON BINARIAS
            // A ESTAS NO DEBE PONERSELES COMILLAS 
            /*Recorre todas las celdas de la fila*/
            while (cellIterator.hasNext()) {
                /*Obtiene el objeto de la celda*/
                Cell cell = cellIterator.next();

                /*Determina el tipo de celda que es*/
                switch (cell.getCellType()) {
                    /*En caso de que sea de tipo string entonces*/
                    case Cell.CELL_TYPE_STRING:

                        /*Obtiene el valor de la celda*/
                        valorCeldaActual = cell.getStringCellValue();

                        // EL ID DE ESTAS LAS VENTAS ES EL CAMPO 2
                                /*Si es la celda 1 entonces*/
                        if (contadorCelda == 1) {
                            // CUANDO ES EL FIN DEL ARCHIVO
                                    /*Si es el fin del archivo entonces*/
                            if (valorCeldaActual.compareTo("FINARCHIVO") == 0) {
                                //Esconde la forma de loading
                                Star.vOcultLoadin();

                                //  NO PUEDE CERRAR PORQUE NO ES EL UNICO DOCUMENTO
                                // EL CIERRE DEBE HACERSE AL FINAL DE LA FUNCION QUE LLAMO A ESTA
                                //Termina la transacción
                                if (Star.iTermTransCon(con) == -1) {
                                    return;
                                }

//                                        //Cierra la base de datos y regresa
//                                        if(Star.iCierrBas(con)==-1)                                
//                                            return;
                                String MENSAJE = "Exito en la importación de ";

                                // EL TOTAAL DE LAS PARTIDAS IMPORTADAS
                                int importadasConExito = contadorFila - 2;

                                if (ventas_duplicadas != 0) {
                                    importadasConExito -= ventas_duplicadas;

                                    ventasDuplicadas = ventasDuplicadas.substring(0, ventasDuplicadas.length() - 2);

                                    MENSAJE += importadasConExito + " " + hojaImportada
                                            + ".\nSe encontraron " + ventas_duplicadas + " partidas duplicadas [" + ventasDuplicadas + "]";

                                } else {
                                    MENSAJE += importadasConExito + " " + hojaImportada + ".";
                                }
                                // LAS VENTAS DUPLICADAS ELIMINAR LAS ULTIMOS 2 CARAACTERS LA COMA Y EL ESPACIO 
                                        /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, MENSAJE, hojaImportada, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                return;
                            } /*Else no es el final del archivo entonces*/ else {
                                /*Guarda el código del almácen y completa la consulta*/
                                sQInsert += "'" + cell.getStringCellValue() + "', ";

                                //Muestra el loading
                                Star.vMostLoading("");
                            }

                        }/*Fin de if(iContCell==1)*/ // AQUI PARA LOS REGISTROS DE LA TABLA VTAS QUE SON DE TIPO BITS  
                        // LA INSERCCION DE ESTOS EN LA QUERY DEBE HACERSE SIN COMILLAS
                        // DE LO CONTRARIO, SE PRESENTAN COMPLICACIONES
                        //else  if(contadorCelda == 8 || contadorCelda == 38 )
                        else if (contadorCelda == 9 || contadorCelda == 39) {
                            sQInsert += cell.getStringCellValue() + ", ";

                        } /*Else if: Descripción*/ else {
                            sQInsert += "'" + cell.getStringCellValue() + "', ";
                        }

                        break;

                }/*Fin de switch(cell.getCellType())*/

                /*Aumenta en uno el contador de las celdas*/
                ++contadorCelda;

            }/*Fin de while(cellIterator.hasNext())*/

            /*Aumenta en uno el contador de las filas*/
            ++contadorFila;

            /*Resetea el contador de celdas*/
            contadorCelda = 1;

            // SOLO LA COMA
                    /*Quita los últimos carácteres inválidos*/
            sQInsert = sQInsert.substring(0, sQInsert.length() - 2);

//                      INNECESARIO PARA VENTAS
//                    /*Agrega el terminador de la consulta*/
//                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "', '')";
            sQInsert += ")";
//                    sQInsert = sQInsert.replace("'.',", "null,");
                    /*Inserta en la base de datos el registro*/
            try {
                st = con.createStatement();
                st.executeUpdate(sQInsert);

            } catch (SQLException expnSQL) {
                // SI ES UN REGISTRO DUPLICADO ENTONCES

                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);

            }
        }/*Fin de while(rowIt.hasNext())*/

    }

    // Crea la hoja del documento XLS // recibe la conexion a la base de datos y el objeto para xls 
     /* retorna la hoja Excel con la exportacion de Partidas */
    public void creaHojaPartidas(Connection con, XSSFWorkbook wkbok, String nameSheet, int MayorAEsteIndice, boolean actualiza) {

        /*Crea la hoja en blanco*/
        XSSFSheet sheet = wkbok.createSheet(nameSheet);

        //Declara variables locales
        int iCont = 1;

        /*Pon los encabezados en el archivo de excel*/
        Map<String, Object[]> d = new TreeMap<>();
        d.put(Integer.toString(iCont), new Object[]{"PROD", "VTA", "TIPDOC", "CANT", "TIPCAM", "DEVS", "GARAN", "ESKIT", "KITMAE", "IDKIT", "IDLOTPED", "LIST", "UNID", "CODIMPUE", "ALMA", "SERPROD", "COMENSER", "DESCRIP", "PRE", "DESCU", "COSTPROM", "COST", "IDULTCOST", "PEPS", "IDPEPS", "UEPS", "IDUEPS", "MON", "LOT", "PEDIMEN", "FCADU", "IMPO", "IMPUE", "TALL", "COLO", "CANTENTRE", "ENTRENOW", "EXPORT", "EXTR1", "EXTR2", "EXTR3", "FENTRE", "FALT", "FMOD"});
        //                    d.put(Integer.toString(iCont), new Object[] {"VTA","NOREFER","NOSER","MON","VEND","CODCOT","FOLFISC","TRANSID","SELL","SELLSAT","CERTSAT","LUGEXP","REGFISC","CADORI","CORT","FORMPAG","NOCORT","CIERR","VTADEVP","PTOVTA","CATGRAL","FACTU","TIPCAM","TIPDOC","NOTCRED","NOTCREDPAG","IMPNOTCRED","TOTDESCU","TOTCOST","TOTCOSTPROM","TOTUEPS"});

        /*Aumenta en uno el contador de filas del libro*/
        ++iCont;

        //Declara variables de la base de datos
        Statement st;
        ResultSet rs;
        String sQ;

        // EXPORTACION AUTOMATICA []
        int indiceActual = 0;

        /*Trae todos los registros de los proveedores*/
        try {
            sQ = "SELECT * from partvta";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while (rs.next()) {

                // COMPARAMOS SI LA VENTA ACTUAL ES MENOR O IGUAL AL ULTIMO EXPORTADO
                // DE SER ASI, NO LO EXPORTAMOS
                if (actualiza) {

                    // EXPORTACION AUTOMATICA []
                    // PARA EXPORTAR SOLO LOS QUE NO HAN SIDO EXPORTADOS ANTERIOR MENTE
                    // OBTENEMOS EL INDICE DE LA VENTA ACTUAL
                    indiceActual = rs.getInt("id_id");

                    if (indiceActual <= MayorAEsteIndice) {
                        continue;
                    }
                }
                /*Agrega el registro en la fila de excel*/
                //                         d.put(Integer.toString(iCont), new Object[] {rs.getString("VTA"), rs.getString("NOREFER"), rs.getString("NOSER")});
                d.put(Integer.toString(iCont), new Object[]{rs.getString("prod"), rs.getString("vta"), rs.getString("tipdoc"), rs.getString("cant"), rs.getString("tipcam"), rs.getString("devs"), rs.getString("garan"), rs.getString("eskit"), rs.getString("kitmae"), rs.getString("idkit"), rs.getString("idlotped"), rs.getString("list"), rs.getString("unid"), rs.getString("codimpue"), rs.getString("alma"), rs.getString("serprod"), rs.getString("comenser"), rs.getString("descrip"), rs.getString("pre"), rs.getString("descu"), rs.getString("costprom"), rs.getString("cost"), rs.getString("idultcost"), rs.getString("peps"), rs.getString("idpeps"), rs.getString("ueps"), rs.getString("idueps"), rs.getString("mon"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), rs.getString("impo"), rs.getString("impue"), rs.getString("tall"), rs.getString("colo"), rs.getString("cantentre"), rs.getString("entrenow"), rs.getString("export"), "", "", "", rs.getString("fentre"), rs.getString("falt"), rs.getString("fmod")});

                /*Aumenta en uno el contador de filas del libro*/
                ++iCont;
            }

            // EXPORTACION AUTOMATICA []
            // OBTENEMOS EL INDICE DEL ULTIMO REGISTRO EXPORTADO
            // ACTUALIZAMOS LA TABLA controlExportar CON ID REGISTRO EXPORTADO
            // SI TIENE QUE ACTUALIZAR LA BASE DE DATOS ENTONCES EJECUTA 
//            if (actualiza) {
//
//                // EL OBJETO LOCAL Connection [ con ] ES TRANSICIONAL 
//                //SE CREA UN OBJETO Connection NO TRANSICIONAL PARA EL METODO setControlExportar
//                Connection conTemporal = Star.conAbrBas(true, false);
//
//                setControlExportar(conTemporal, "id_ultimapartidaExpo", indiceActual);
//
//                // CIERRE DE LA BASE DE DATOS TEMPORAL
//                if (Star.iCierrBas(conTemporal) == -1) {
//                    return;
//                }
//
//            }
        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;
        }

        /*Obtiene todos los datos insertados en el objeto de excel*/
        Set<String> keyset = d.keySet();

        /*Recorre todas las filas*/
        int rownum = 0;
        for (String key : keyset) {
            /*Crea la fila*/
            Row row = sheet.createRow(rownum++);

            /*Obtiene todos los datos de la fila*/
            Object[] objArr = d.get(key);

            /*Recorre todos los datos de la fila*/
            int cellnum = 0;
            for (Object ob : objArr) {
                try {
                    /*Obtiene el valor a insertar en la celda*/
                    String sVal = ob.toString();

                    /*Si esta en la celda 0 y la fila es mayor a 1*/
                    if (cellnum == 0 && rownum > 1) {
                        /*Si es un número entonces conviertelo a su valor absoluto*/
                        try {
                            Double.parseDouble(sVal);
                            sVal = Integer.toString((int) Double.parseDouble(sVal));
                        } catch (NumberFormatException expnNumForm) {
                        }
                    }

                    /*Crea la celda y establecele el valor*/
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue(sVal);

                } catch (Exception rsd) {

                }

            }
        }

        /*Escribe el final del fichero*/
        Row row = sheet.createRow(rownum);
        Cell cell = row.createCell(0);
        cell.setCellValue("FINARCHIVO");

        //HOJA CON LA INFORMACION
        // return sheet;
    }
   
    public void actualizaControlExportar(Connection con){
        // QUERY TO GET LAST TRANSACTIONS
        //SELECT vta FROM VTAS order by vta desc limit 1
        //SELECT id_id FROM partvta order by id_id desc limit 1
        
                //Declara variables de la base de datos
        Statement st;
        String SQL = "SELECT vta FROM VTAS order by vta desc limit 1";
        String SQL2 = "SELECT id_id FROM partvta order by id_id desc limit 1";
        ResultSet rs;

        // SI SE RETORNA 0 QUIERE DECIR QUE FALLO LA QUERY O NO SE ENCONTRO VALOR EN LA TABLA
        int ultimaVta = 0;
        int ultimaPart = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);

            if (rs.next()) {
                ultimaVta = rs.getInt("vta");
                
            }
            
            st = con.createStatement();
            rs = st.executeQuery(SQL2);
            
            if (rs.next()) {
                ultimaPart = rs.getInt("id_id");
            }
            
            setControlExportar(con, "id_ultimaventaExpo", ultimaVta);
            setControlExportar(con, "id_ultimapartidaExpo", ultimaPart);
      
            
        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            //return;                        
        }
  
        // END FUNCTION
    }
    public void setEmail(correoRegistro e, String asunto, String mensaje) {

        e.setCorreoDesde(correo1.getText());
        e.setContraseñaDesde(contra1.getText());
        e.setMuestraDesde(nombreMostrar1.getText());

        e.setDireccionServidor(direccionServer1.getText());
        e.setPuerto(puerto1.getText());

        e.setCorreoA(correoCentral1.getText());

        e.setAsunto(asunto);
        e.setMensaje(mensaje);
    }

    // CUAL --> PARTIDA O VENTA
    public int getControlExportar(Connection con, String CUAL) {

        //Declara variables de la base de datos
        Statement st;
        String SQL = "SELECT " + CUAL + " FROM controlExportar ORDER BY id_id DESC LIMIT 1";
        ResultSet rs;

        // SI SE RETORNA 0 QUIERE DECIR QUE FALLO LA QUERY O NO SE ENCONTRO VALOR EN LA TABLA
        int retorna = 0;

        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);

            if (rs.next()) {
                retorna = rs.getInt(CUAL);
            }
        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            //return;                        
        }

        return retorna;
    }

    public String getControlExportar_String(Connection con, String CUAL) {

        //Declara variables de la base de datos
        Statement st;
        String SQL = "SELECT " + CUAL + " FROM controlExportar ORDER BY id_id DESC LIMIT 1";
        ResultSet rs;

        String retorna = "";

        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);

            if (rs.next()) {
                retorna = rs.getString(CUAL);
            }
        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            //return;                        
        }

        return retorna;
    }

    // CUAL --> PARTIDA O VENTA // RECIBE INT
    public void setControlExportar(Connection con, String CUAL, int aInsertar) {

        //Declara variables de la base de datos
        Statement st;
        String SQL = "UPDATE `controlExportar` SET `" + CUAL + "` ='" + aInsertar + "' WHERE `id_id`='1'";

        if (CUAL.compareToIgnoreCase("fechaImpo") == 0){
            SQL = "UPDATE `controlExportar` SET `" + CUAL + "` = now() WHERE `id_id`='1'";
        }

        try {
            st = con.createStatement();
            st.executeUpdate(SQL);
        } catch (SQLException expnSQL) {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            //return;                        
        }

    }

    public boolean email_valido(String e) {

        /*Expresion regular*/ /* felipe.ruiz@sos-soft.com */
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9|-]+(\\.[A-Za-z0-9|-]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(e);

        /* si el Email NO es valido */
        /* se le hace saber al usuario */
        if (!mat.find()) {
            return false;
        }
        /* Si el Email es valido entonces */
        return true;
    }

//    public boolean duplicado(String e) {
//
//        String expresion = "Duplicate entry '[0-9]+' for key 'PRIMARY'";
//
//        /*Expresion regular*/ /* felipe.ruiz@sos-soft.com */
//        Pattern pat = Pattern.compile(expresion);
//        Matcher mat = pat.matcher(e);
//
//        /* si el Email NO es valido */
//        /* se le hace saber al usuario */
//        if (!mat.find()) {
//            return false;
//        }
//        /* Si el Email es valido entonces */
//        return true;
//    }

    /* Guarda en la base de datos el correo electronico */
    /* con INSERT *//* update SOLO 1 REGISTRO / NO MAS */

    public boolean escribeCorreo(correoRegistro mail, int entro, Connection con) {

        //Abre la base de datos                             
        // Connection  con = Star.conAbrBas(true, false);
        //Si hubo error entonces 
        if (con == null) {
            return false;
        }

        /* nombre de la tabla de registro */
        String nametabla = "correo_terminal";

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el guardado del correo\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                //Star.iCierrBas(con);
                return false;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para la sincronización\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            //Star.iCierrBas(con);
            return false;
        }

        /*preparo la base de datos */
        try {
            if (entro == 0) {
                sQ = "insert into correo_terminal (id_id, correo, contra, muestracomo, direccionserver, correoa, puerto, ingresocorreo) values (null, \"" + mail.getCorreoDesde() + "\", \"" + mail.getContraseñaDesde() + "\", \"" + mail.getMuestraDesde() + "\",   \"" + mail.getDireccionServidor() + "\", \"" + mail.getCorreoA() + "\", " + mail.getPuerto() + ", 1);";
            } else if (entro == 1) {
                sQ = "update correo_terminal set correo = \"" + mail.getCorreoDesde() + "\", contra = \"" + mail.getContraseñaDesde() + "\", muestracomo = \"" + mail.getMuestraDesde() + "\", direccionserver = \"" + mail.getDireccionServidor() + "\", correoa = \"" + mail.getCorreoA() + "\", puerto = " + mail.getPuerto() + " where id_id = 1;";
            }

            st = con.createStatement();
            int x = st.executeUpdate(sQ);

            if (x == 1) {
                //  exito, se hizo la query exitosamente

                /* cierra conexión base de datos */
                //Star.iCierrBas(con);
                // return x == 0;  
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al intentar guardar los datos en la tabla " + nametabla + "\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexión base de datos */
            //Star.iCierrBas(con);  
        }
        return false;

    }

    /*Felipe Ruiz Garcia 1 07 2015*/ /* Bandera para registrar cuando ingresa un correo por primera vez */
    /*retorna el valor del campo de la base de datos Ingresocorreo */
    /*retorna *//*0 NO HA ENTRADO*//*1 YA ENTRO*//*3 ERROR EN CONSULTA */




    public int getIngresocorreoBD(Connection con) {

        //Abre la base de datos                             
        //Connection  con = Star.conAbrBas(true, false);
        //Si hubo error entonces 
        if (con == null) {
            return 3;
        }

        /* nombre de la tabla de registro */
        String nametabla = "correo_terminal";

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el guardado del correo\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                //Star.iCierrBas(con);
                return 3;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para la sincronización\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            //Star.iCierrBas(con);
            return 3;
        }

        /*preparo la base de datos */
        try {

            sQ = "select ingresocorreo from correo_terminal where id_id = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);

            if (rs.next()) {
                //  exito, se hizo la query exitosamente

                int x = rs.getInt("ingresocorreo");

                /* cierra conexión base de datos */
                //Star.iCierrBas(con);
                return x;

            } else {

                return 0;
                // CUANDO NO EXISTE REGISTRO ALGUNO
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al intentar guardar los datos en la tabla " + nametabla + "\n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexión base de datos */
            //Star.iCierrBas(con);  
        }

        return 0;
    }

    /*Felipe Ruiz Garcia 1 07 2015*/
    /*creaa mensaje html para la prueba */
    public String creaMensaje(String motivo) {

        String mensajeHtml = "<!DOCTYPE html>\n"
                + "	<html>\n"
                + "	<head>\n"
                + "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "		<title>Gracias :)</title>\n"
                + "\n"
                + "<style type=\"text/css\">\n"
                + "	body {\n"
                + "		background-color: #33ccff;\n"
                + "		color: white;\n"
                + "		padding: 20px;\n"
                + "		font-family: Arial, Verdana, sans-serif;\n"
                + "	}\n"
                + "\n"
                + "	.Main{\n"
                + "		background-color: #ffffff;\n"
                + "		color: black;\n"
                + "		padding: 20px;\n"
                + "	}\n"
                + "	.Main.Datos{\n"
                + "		font-size: 20px;\n"
                + "		color: black;\n"
                + "\n"
                + "	}\n"
                + "\n"
                + "	.Main.Datos.Neg{\n"
                + "\n"
                + "		font-weight: bold;\n"
                + "		font-size: 23px;\n"
                + "		color: black;\n"
                + "	}\n"
                + "\n"
                + "\n"
                + "	h1 {\n"
                + "		background-color: #ffffff;\n"
                + "		background-color: hsla(0,100%,100%,0.5);\n"
                + "		color: #64645A;\n"
                + "		padding: inherit;}\n"
                + "\n"
                + "		p {\n"
                + "			padding: 0px;\n"
                + "			margin: 0px;}\n"
                + "\n"
                + "			.eight {\n"
                + "				background-color: rgb(140,202,242);}\n"
                + "				.nine {\n"
                + "					background-color: rgb(114,193,240);}\n"
                + "					.ten {\n"
                + "						background-color: rgb(84,182,237);}\n"
                + "						.eleven {\n"
                + "							background-color: rgb(48,170,233);}\n"
                + "							.twelve {\n"
                + "								background-color: rgb(0,160,230);}\n"
                + "								.thirteen {\n"
                + "									background-color: rgb(0,149,226);}\n"
                + "									.fourteen {\n"
                + "										background-color: rgb(0,136,221);}\n"
                + "\n"
                + "\n"
                + "									</style>\n"
                + "								</head>\n"
                + "								<body style=\"background-color: #33ccff;color: white;padding: 20px;font-family: Arial, Verdana, sans-serif;\">\n"
                + "									<div class=\"Main\" style=\"background-color: #ffffff;color: black;padding: 20px;\">\n"
                + "										<div class=\"header\">\n"
                + "											<tr>\n"
                + "												<th>\n"
                + "													<img src=\"http://easyretail.com.mx/wp-content/uploads/2015/06/IdentificadorEeasy-Retail-admin-marca-registrada.png\" id=\"image_bussines\">\n"
                + "												</th>\n"
                + "												<th>\n"
                + "													<h1 id=\"title_bussines\">\n"
                + "														Easy Retail® Admin\n"
                + "														</h1>\n"
                + "														Software administrativo sencillo y a tiempo. \n"
                + "													</h1>\n"
                + "												</th>\n"
                + "											</tr>\n"
                + "										</div>\n"
                + "									</div>\n"
                + "									<br>\n"
                + "\n"
                + "									<div class=\"Main Datos\" style=\"background-color: #ffffff;color: black;padding: 20px;font-size: 20px;\">\n"
                + "										<h4></h4>"
                + "										<h4></h4>" + "<br><p style=\"padding: 0px;margin: 0px;\">" + motivo + "</p>"
                + "\n"
                + "										\n\n"
                + "										<div class=\"Neg\">\n"
                + "											<p style=\"padding: 0px;margin: 0px;\">Estamos a su servicio en el <b>01-800-890-0365</b> para todo México y en el <b>01-33-3617-2968</b> en Jalisco.\n"
                + "												<br style=\"padding: 0px;margin: 0px;\">\n"
                + "												Atentamente el equipo Easy Retail® - Sencillo y a tiempo.\n"
                + "											</p>	\n"
                + "										</div>\n"
                + "									</div>\n"
                + "\n"
                + "									<br>\n"
                + "								</body>\n"
                + "								</html>";

        return mensajeHtml;
    }

    public static void incia() {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(correoTerminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(correoTerminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(correoTerminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(correoTerminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new correoTerminal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ConfiguracionesPane;
    private javax.swing.JButton ExportarAutomatico;
    private javax.swing.JPanel ExportarPane;
    private javax.swing.JButton ExportarTodo;
    private javax.swing.JPanel SincronizarPane;
    private javax.swing.JTabbedPane SincronizarTabbed;
    private javax.swing.JTextField centralParaEnvio;
    private javax.swing.JLabel centralParaEnvio_Label;
    private javax.swing.JPasswordField contra1;
    private javax.swing.JLabel contra_label1;
    private javax.swing.JTextField correo1;
    private javax.swing.JTextField correoCentral1;
    private javax.swing.JLabel correoCentral1_label;
    private javax.swing.JTextField correoPaEnvio;
    private javax.swing.JLabel correoParaEnvio_Label;
    private javax.swing.JLabel correo_label1;
    private javax.swing.JTextField direccionServer1;
    private javax.swing.JLabel direccionServer_label1;
    private javax.swing.JLabel estado;
    private javax.swing.JLabel estadoSincronizar;
    private javax.swing.JButton guardar;
    private javax.swing.JButton importar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField nombreMostrar1;
    private javax.swing.JLabel nombreMostrar_label1;
    private javax.swing.JButton probar;
    private javax.swing.JTextField puerto1;
    private javax.swing.JLabel puerto_label1;
    private javax.swing.JButton sincronizarPersonalizado;
    private javax.swing.JLabel tipoSincronizacion2;
    private javax.swing.JLabel title2;
    private javax.swing.JLabel title3;
    private javax.swing.JLabel title4;
    private javax.swing.JLabel title5;
    private javax.swing.JLabel title6;
    private javax.swing.JLabel ultimaExportacionFechaSincronizacion1;
    private javax.swing.JLabel ultimaExportacionTipoSincronizacion1;
    private javax.swing.JLabel ultimaExportacionVentaSincronizacion;
    private javax.swing.JLabel ultimaImportacionSincronizacion;
    private javax.swing.JLabel ultimaSincronizacion2;
    private javax.swing.JLabel ultimaVentaSincronizacion1;
    private javax.swing.JLabel ultimaVentaSincronizacion2;
    private javax.swing.JLabel ultimaVentaSincronizacion3;
    // End of variables declaration//GEN-END:variables
}
