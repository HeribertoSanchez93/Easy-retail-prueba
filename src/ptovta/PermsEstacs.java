//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;




/*Clase para modificar los permisos de un usuario*/
public class PermsEstacs extends javax.swing.JFrame 
{                
   //Se encuentran los hijos
    private java.util.ArrayList<java.util.ArrayList<JCheckBox>> hijos = new java.util.ArrayList<>();
    
    /*Consructor sin argumentos*/
    public PermsEstacs() 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Permisos usuarios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        
        //Carga a los hijos
        cargaHijos();
        
        //Carga los usuarios
        cargaUsuarios();
        
        /*Pon el foco del teclado en el combobox de usuarios*/
        jcmbUsuarios.grabFocus();                
                        
    }/*Fin de public PermsEstacs() */

       
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBGuar = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jcmbUsuarios = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jcbClientes = new javax.swing.JCheckBox();
        jcbClieModificar = new javax.swing.JCheckBox();
        jcbClieNuevo = new javax.swing.JCheckBox();
        jcbClieBorrar = new javax.swing.JCheckBox();
        jcbClieVer = new javax.swing.JCheckBox();
        jcbClieEnviar = new javax.swing.JCheckBox();
        jcbClientesPermiso = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jcbProve = new javax.swing.JCheckBox();
        jcbProveModificar = new javax.swing.JCheckBox();
        jcbProveNuevo = new javax.swing.JCheckBox();
        jcbProveBorrar = new javax.swing.JCheckBox();
        jcbProveVer = new javax.swing.JCheckBox();
        jcbProveePermiso = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jcbConf = new javax.swing.JCheckBox();
        jcbConfDatos = new javax.swing.JCheckBox();
        jcbConfCorreos = new javax.swing.JCheckBox();
        jcbConfImpresoras = new javax.swing.JCheckBox();
        jcbconfSeries = new javax.swing.JCheckBox();
        jcbConfConfiguraciones = new javax.swing.JCheckBox();
        jcbConfCambiarIcono = new javax.swing.JCheckBox();
        jcbConfiguracionesPermiso = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jcbCotizaciones = new javax.swing.JCheckBox();
        jcbAbrirCotiza = new javax.swing.JCheckBox();
        jcbNuevaCotiza = new javax.swing.JCheckBox();
        jcbCancelarCotiza = new javax.swing.JCheckBox();
        jcbVerCotiza = new javax.swing.JCheckBox();
        jcbVentaCotiza = new javax.swing.JCheckBox();
        jcbReenviarCotiza = new javax.swing.JCheckBox();
        jcbCotizaPermiso = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jcbCompras = new javax.swing.JCheckBox();
        jcbCancelarCompra = new javax.swing.JCheckBox();
        jcbDevolCompra = new javax.swing.JCheckBox();
        jcbParcialCompra = new javax.swing.JCheckBox();
        jcbNotCompra = new javax.swing.JCheckBox();
        jcbVerCompra = new javax.swing.JCheckBox();
        jcbBorrarArchivoCompra = new javax.swing.JCheckBox();
        jcbCargarArchivoCompra = new javax.swing.JCheckBox();
        jcbArchivoCompra = new javax.swing.JCheckBox();
        jcbRecibirCompra = new javax.swing.JCheckBox();
        jcbNuevoCompra = new javax.swing.JCheckBox();
        jcbComprasPermiso = new javax.swing.JCheckBox();
        jPanelSistema = new javax.swing.JPanel();
        jcbSistema = new javax.swing.JCheckBox();
        jcbUsuarios = new javax.swing.JCheckBox();
        jcbDefinirUsr = new javax.swing.JCheckBox();
        jcbUsrConectados = new javax.swing.JCheckBox();
        jcbPermisosUsr = new javax.swing.JCheckBox();
        jcbClaves = new javax.swing.JCheckBox();
        jcbBaseDatos = new javax.swing.JCheckBox();
        jcbConexionesBD = new javax.swing.JCheckBox();
        jcbArchivoConf = new javax.swing.JCheckBox();
        jcbReparar = new javax.swing.JCheckBox();
        jcbReparador = new javax.swing.JCheckBox();
        jcbRestaurar = new javax.swing.JCheckBox();
        jcbReportes = new javax.swing.JCheckBox();
        jcbRepUsuarios = new javax.swing.JCheckBox();
        jcbRepRespaldos = new javax.swing.JCheckBox();
        jcbRepLog = new javax.swing.JCheckBox();
        jcbRepEstadisticas = new javax.swing.JCheckBox();
        jcbRevocacion = new javax.swing.JCheckBox();
        jcbActivar = new javax.swing.JCheckBox();
        jcbSistemaPermiso = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jcbModulos = new javax.swing.JCheckBox();
        jcbContabilidad = new javax.swing.JCheckBox();
        jcbConceptos = new javax.swing.JCheckBox();
        jcbCatalogo = new javax.swing.JCheckBox();
        jcbZonas = new javax.swing.JCheckBox();
        jcbGiros = new javax.swing.JCheckBox();
        jcbMonedas = new javax.swing.JCheckBox();
        jcbImpuestos = new javax.swing.JCheckBox();
        jcbModulosPermiso = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jcbInventario = new javax.swing.JCheckBox();
        jcbModificarProd = new javax.swing.JCheckBox();
        jcbProductos = new javax.swing.JCheckBox();
        jcbBorrarProd = new javax.swing.JCheckBox();
        jcbNuevoProd = new javax.swing.JCheckBox();
        jcbInventarioPermiso = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jcbPrevioCompra = new javax.swing.JCheckBox();
        jcbAbrirPrevio = new javax.swing.JCheckBox();
        jcbNuevaPrevio = new javax.swing.JCheckBox();
        jcbCancelarPrevio = new javax.swing.JCheckBox();
        jcbVerPrevio = new javax.swing.JCheckBox();
        jcbCompraPrevio = new javax.swing.JCheckBox();
        jcbSeriesPrevio = new javax.swing.JCheckBox();
        jcbPrevioPermiso = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jcbVentas = new javax.swing.JCheckBox();
        jcbCancelarVentas = new javax.swing.JCheckBox();
        jcbDevolVentas = new javax.swing.JCheckBox();
        jcbParcialVentas = new javax.swing.JCheckBox();
        jcbNotVentas = new javax.swing.JCheckBox();
        jcbVerVentas = new javax.swing.JCheckBox();
        jcbBorrarArchivoVentas = new javax.swing.JCheckBox();
        jcbCargarArchivoVentas = new javax.swing.JCheckBox();
        jcbArchivoVentas = new javax.swing.JCheckBox();
        jcbNuevaVentas = new javax.swing.JCheckBox();
        jcbEnviarVentas = new javax.swing.JCheckBox();
        jcbTimbrarVentas = new javax.swing.JCheckBox();
        jcbEntregarVentas = new javax.swing.JCheckBox();
        jcbComprobarVentas = new javax.swing.JCheckBox();
        jcbAcuseVentas = new javax.swing.JCheckBox();
        jcbObtenerXmlVentas = new javax.swing.JCheckBox();
        jcbFacturarVentas = new javax.swing.JCheckBox();
        jcbVentasPermiso = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GralKeyPressed(evt);
            }
        });

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jP1.setMaximumSize(new java.awt.Dimension(320, 500));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GralKeyPressed(evt);
            }
        });
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSalMouseExited(evt);
            }
        });
        jBSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalActionPerformed(evt);
            }
        });
        jBSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GralKeyPressed(evt);
            }
        });
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 110, 30));

        jBGuar.setBackground(new java.awt.Color(204, 204, 204));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarMouseExited(evt);
            }
        });
        jBGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarActionPerformed(evt);
            }
        });
        jBGuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GralKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 110, 30));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 210, 30));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(320, 2000));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(320, 2000));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(320, 798));

        jPanel1.setMaximumSize(new java.awt.Dimension(350, 2900));
        jPanel1.setMinimumSize(new java.awt.Dimension(350, 2900));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 3000));

        jcmbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbUsuariosActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbClientes.setText("Clientes");
        jcbClientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbClientes.setBorderPainted(true);
        jcbClientes.setOpaque(false);
        jcbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesActionPerformed(evt);
            }
        });

        jcbClieModificar.setText("Modificar");
        jcbClieModificar.setContentAreaFilled(false);
        jcbClieModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaClientes(evt);
            }
        });

        jcbClieNuevo.setText("Nuevo");
        jcbClieNuevo.setContentAreaFilled(false);
        jcbClieNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaClientes(evt);
            }
        });

        jcbClieBorrar.setText("Borrar");
        jcbClieBorrar.setContentAreaFilled(false);
        jcbClieBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaClientes(evt);
            }
        });

        jcbClieVer.setText("Ver");
        jcbClieVer.setContentAreaFilled(false);
        jcbClieVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaClientes(evt);
            }
        });

        jcbClieEnviar.setText("Enviar");
        jcbClieEnviar.setContentAreaFilled(false);
        jcbClieEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaClientes(evt);
            }
        });

        jcbClientesPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbClientesPermiso.setText("Puede otorgar permisos de clientes");
        jcbClientesPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbClientesPermiso.setBorderPainted(true);
        jcbClientesPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbClieNuevo)
                            .addComponent(jcbClieModificar)
                            .addComponent(jcbClieVer)
                            .addComponent(jcbClieBorrar)
                            .addComponent(jcbClieEnviar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jcbClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jcbClientesPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbClieNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbClieModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbClieVer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbClieBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbClieEnviar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbClientesPermiso)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbProve.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbProve.setText("Proveedores");
        jcbProve.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbProve.setBorderPainted(true);
        jcbProve.setOpaque(false);
        jcbProve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProveActionPerformed(evt);
            }
        });

        jcbProveModificar.setText("Modificar");
        jcbProveModificar.setContentAreaFilled(false);
        jcbProveModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaProvee(evt);
            }
        });

        jcbProveNuevo.setText("Nuevo");
        jcbProveNuevo.setContentAreaFilled(false);
        jcbProveNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaProvee(evt);
            }
        });

        jcbProveBorrar.setText("Borrar");
        jcbProveBorrar.setContentAreaFilled(false);
        jcbProveBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaProvee(evt);
            }
        });

        jcbProveVer.setText("Ver");
        jcbProveVer.setContentAreaFilled(false);
        jcbProveVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaProvee(evt);
            }
        });

        jcbProveePermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbProveePermiso.setText("Puede otorgar permisos de proveedores");
        jcbProveePermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbProveePermiso.setBorderPainted(true);
        jcbProveePermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbProveNuevo)
                            .addComponent(jcbProveModificar)
                            .addComponent(jcbProveVer)
                            .addComponent(jcbProveBorrar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbProveePermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbProve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbProve)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbProveNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbProveModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbProveVer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbProveBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbProveePermiso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbConf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbConf.setText("Configuraciones");
        jcbConf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbConf.setBorderPainted(true);
        jcbConf.setOpaque(false);
        jcbConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbConfActionPerformed(evt);
            }
        });

        jcbConfDatos.setText("Datos generales de la empresa");
        jcbConfDatos.setContentAreaFilled(false);
        jcbConfDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbConfCorreos.setText("Correos electronicos");
        jcbConfCorreos.setContentAreaFilled(false);
        jcbConfCorreos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbConfImpresoras.setText("Impresoras");
        jcbConfImpresoras.setContentAreaFilled(false);
        jcbConfImpresoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbconfSeries.setText("Series y consecutivos");
        jcbconfSeries.setContentAreaFilled(false);
        jcbconfSeries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbConfConfiguraciones.setText("Configuraciones generales");
        jcbConfConfiguraciones.setContentAreaFilled(false);
        jcbConfConfiguraciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbConfCambiarIcono.setText("Cambiar icono aplicación");
        jcbConfCambiarIcono.setContentAreaFilled(false);
        jcbConfCambiarIcono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaConfig(evt);
            }
        });

        jcbConfiguracionesPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbConfiguracionesPermiso.setText("Puede otorgar permisos de configuraciones");
        jcbConfiguracionesPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbConfiguracionesPermiso.setBorderPainted(true);
        jcbConfiguracionesPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbConfCorreos)
                            .addComponent(jcbConfDatos)
                            .addComponent(jcbconfSeries)
                            .addComponent(jcbConfImpresoras)
                            .addComponent(jcbConfCambiarIcono)
                            .addComponent(jcbConfConfiguraciones))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jcbConf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jcbConfiguracionesPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jcbConf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbConfCorreos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConfDatos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbconfSeries)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConfImpresoras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConfCambiarIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConfConfiguraciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbConfiguracionesPermiso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbCotizaciones.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbCotizaciones.setText("Cotizaciones");
        jcbCotizaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbCotizaciones.setBorderPainted(true);
        jcbCotizaciones.setOpaque(false);
        jcbCotizaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCotizacionesActionPerformed(evt);
            }
        });

        jcbAbrirCotiza.setText("abrir");
        jcbAbrirCotiza.setContentAreaFilled(false);
        jcbAbrirCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbNuevaCotiza.setText("Nueva");
        jcbNuevaCotiza.setContentAreaFilled(false);
        jcbNuevaCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbCancelarCotiza.setText("Cancelar");
        jcbCancelarCotiza.setContentAreaFilled(false);
        jcbCancelarCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbVerCotiza.setText("Ver");
        jcbVerCotiza.setContentAreaFilled(false);
        jcbVerCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbVentaCotiza.setText("Venta");
        jcbVentaCotiza.setContentAreaFilled(false);
        jcbVentaCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbReenviarCotiza.setText("Reenviar");
        jcbReenviarCotiza.setContentAreaFilled(false);
        jcbReenviarCotiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCoti(evt);
            }
        });

        jcbCotizaPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbCotizaPermiso.setText("Puede otorgar permisos de cotizaciones");
        jcbCotizaPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbCotizaPermiso.setBorderPainted(true);
        jcbCotizaPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbNuevaCotiza)
                            .addComponent(jcbAbrirCotiza)
                            .addComponent(jcbVerCotiza)
                            .addComponent(jcbCancelarCotiza)
                            .addComponent(jcbReenviarCotiza)
                            .addComponent(jcbVentaCotiza))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jcbCotizaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jcbCotizaPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbCotizaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbNuevaCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbAbrirCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbVerCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCancelarCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbReenviarCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbVentaCotiza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCotizaPermiso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbCompras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbCompras.setText("Compras");
        jcbCompras.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbCompras.setBorderPainted(true);
        jcbCompras.setOpaque(false);
        jcbCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbComprasActionPerformed(evt);
            }
        });

        jcbCancelarCompra.setText("Cancelar");
        jcbCancelarCompra.setContentAreaFilled(false);
        jcbCancelarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbDevolCompra.setText("Devolución");
        jcbDevolCompra.setContentAreaFilled(false);
        jcbDevolCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbParcialCompra.setText("Parcial");
        jcbParcialCompra.setContentAreaFilled(false);
        jcbParcialCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbNotCompra.setText("Not. crédito");
        jcbNotCompra.setContentAreaFilled(false);
        jcbNotCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbVerCompra.setText("Ver");
        jcbVerCompra.setContentAreaFilled(false);
        jcbVerCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbBorrarArchivoCompra.setText("Borrar");
        jcbBorrarArchivoCompra.setContentAreaFilled(false);
        jcbBorrarArchivoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
                deseleccionaArchivoCompras(evt);
            }
        });

        jcbCargarArchivoCompra.setText("Cargar");
        jcbCargarArchivoCompra.setContentAreaFilled(false);
        jcbCargarArchivoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
                deseleccionaArchivoCompras(evt);
            }
        });

        jcbArchivoCompra.setText("Ver Archivo");
        jcbArchivoCompra.setContentAreaFilled(false);
        jcbArchivoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbArchivoCompraActionPerformed(evt);
            }
        });

        jcbRecibirCompra.setText("Recibir orden");
        jcbRecibirCompra.setContentAreaFilled(false);
        jcbRecibirCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbNuevoCompra.setText("Nuevo");
        jcbNuevoCompra.setContentAreaFilled(false);
        jcbNuevoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaCompras(evt);
            }
        });

        jcbComprasPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbComprasPermiso.setText("Puede otorgar permisos de compras");
        jcbComprasPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbComprasPermiso.setBorderPainted(true);
        jcbComprasPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbRecibirCompra)
                            .addComponent(jcbNotCompra)
                            .addComponent(jcbNuevoCompra)
                            .addComponent(jcbParcialCompra)
                            .addComponent(jcbDevolCompra)
                            .addComponent(jcbCancelarCompra)
                            .addComponent(jcbVerCompra)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jcbArchivoCompra)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbBorrarArchivoCompra)
                                    .addComponent(jcbCargarArchivoCompra))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbComprasPermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbCompras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbCompras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbCancelarCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDevolCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbParcialCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbNuevoCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbNotCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbVerCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbArchivoCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCargarArchivoCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbBorrarArchivoCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRecibirCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbComprasPermiso)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanelSistema.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSistema.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanelSistema.setAutoscrolls(true);

        jcbSistema.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbSistema.setText("Sistema");
        jcbSistema.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbSistema.setBorderPainted(true);
        jcbSistema.setOpaque(false);
        jcbSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSistemaActionPerformed(evt);
            }
        });

        jcbUsuarios.setText("Usuarios");
        jcbUsuarios.setContentAreaFilled(false);
        jcbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbUsuariosActionPerformed(evt);
            }
        });

        jcbDefinirUsr.setText("Definir usuarios");
        jcbDefinirUsr.setContentAreaFilled(false);
        jcbDefinirUsr.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbDefinirUsr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbDefinirUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaUsuarios(evt);
            }
        });

        jcbUsrConectados.setText("Usuarios conectados");
        jcbUsrConectados.setContentAreaFilled(false);
        jcbUsrConectados.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbUsrConectados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaUsuarios(evt);
            }
        });

        jcbPermisosUsr.setText("Permisos usuarios");
        jcbPermisosUsr.setContentAreaFilled(false);
        jcbPermisosUsr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbPermisosUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaUsuarios(evt);
            }
        });

        jcbClaves.setText("Claves");
        jcbClaves.setContentAreaFilled(false);
        jcbClaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
            }
        });

        jcbBaseDatos.setText("Base de datos");
        jcbBaseDatos.setContentAreaFilled(false);
        jcbBaseDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbBaseDatosActionPerformed(evt);
            }
        });

        jcbConexionesBD.setText("Conexiones a bases de datos");
        jcbConexionesBD.setContentAreaFilled(false);
        jcbConexionesBD.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbConexionesBD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbConexionesBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaBD(evt);
            }
        });

        jcbArchivoConf.setText("Archivo de configuracion");
        jcbArchivoConf.setContentAreaFilled(false);
        jcbArchivoConf.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbArchivoConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaBD(evt);
            }
        });

        jcbReparar.setText("Reparar");
        jcbReparar.setContentAreaFilled(false);
        jcbReparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbRepararActionPerformed(evt);
            }
        });

        jcbReparador.setText("Reparador de errores");
        jcbReparador.setContentAreaFilled(false);
        jcbReparador.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbReparador.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbReparador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
            }
        });

        jcbRestaurar.setText("Restaurar sistema");
        jcbRestaurar.setContentAreaFilled(false);
        jcbRestaurar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
            }
        });

        jcbReportes.setText("Reportes");
        jcbReportes.setContentAreaFilled(false);
        jcbReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbReportesActionPerformed(evt);
            }
        });

        jcbRepUsuarios.setText("Usuarios");
        jcbRepUsuarios.setContentAreaFilled(false);
        jcbRepUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbRepUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbRepUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaReportes(evt);
            }
        });

        jcbRepRespaldos.setText("Respaldos");
        jcbRepRespaldos.setContentAreaFilled(false);
        jcbRepRespaldos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbRepRespaldos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbRepRespaldos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaReportes(evt);
            }
        });

        jcbRepLog.setText("Log correos");
        jcbRepLog.setContentAreaFilled(false);
        jcbRepLog.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbRepLog.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbRepLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaReportes(evt);
            }
        });

        jcbRepEstadisticas.setText("Estadisticas de correos");
        jcbRepEstadisticas.setContentAreaFilled(false);
        jcbRepEstadisticas.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jcbRepEstadisticas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jcbRepEstadisticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
                deseleccionaReportes(evt);
            }
        });

        jcbRevocacion.setText("Revocación");
        jcbRevocacion.setContentAreaFilled(false);
        jcbRevocacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
            }
        });

        jcbActivar.setText("Activar sistema");
        jcbActivar.setContentAreaFilled(false);
        jcbActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaSistema(evt);
            }
        });

        jcbSistemaPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbSistemaPermiso.setText("Puede otorgar permisos de sistema");
        jcbSistemaPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbSistemaPermiso.setBorderPainted(true);
        jcbSistemaPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanelSistemaLayout = new javax.swing.GroupLayout(jPanelSistema);
        jPanelSistema.setLayout(jPanelSistemaLayout);
        jPanelSistemaLayout.setHorizontalGroup(
            jPanelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSistemaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSistemaLayout.createSequentialGroup()
                        .addComponent(jcbSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanelSistemaLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbUsuarios)
                            .addComponent(jcbClaves)
                            .addComponent(jcbReparar)
                            .addComponent(jcbBaseDatos)
                            .addComponent(jcbReportes)
                            .addComponent(jcbRevocacion)
                            .addComponent(jcbActivar)
                            .addGroup(jPanelSistemaLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbArchivoConf)
                                    .addComponent(jcbConexionesBD)
                                    .addComponent(jcbRepEstadisticas)
                                    .addComponent(jcbRepLog)
                                    .addComponent(jcbRepRespaldos)
                                    .addComponent(jcbRepUsuarios)
                                    .addComponent(jcbReparador)
                                    .addComponent(jcbRestaurar)
                                    .addComponent(jcbPermisosUsr)
                                    .addComponent(jcbDefinirUsr)
                                    .addComponent(jcbUsrConectados))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanelSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbSistemaPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSistemaLayout.setVerticalGroup(
            jPanelSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSistemaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jcbSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDefinirUsr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbUsrConectados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbPermisosUsr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbClaves)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbReparar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbReparador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRestaurar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbBaseDatos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConexionesBD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbArchivoConf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbReportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRepUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRepRespaldos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRepLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbRepEstadisticas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbRevocacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbActivar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSistemaPermiso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbModulos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbModulos.setText("Modulos");
        jcbModulos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbModulos.setBorderPainted(true);
        jcbModulos.setOpaque(false);
        jcbModulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbModulosActionPerformed(evt);
            }
        });

        jcbContabilidad.setText("Contabilidad");
        jcbContabilidad.setContentAreaFilled(false);
        jcbContabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbConceptos.setText("Conceptos notas de créditos");
        jcbConceptos.setContentAreaFilled(false);
        jcbConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbCatalogo.setText("Catálogo garantías");
        jcbCatalogo.setContentAreaFilled(false);
        jcbCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbZonas.setText("Zonas");
        jcbZonas.setContentAreaFilled(false);
        jcbZonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbGiros.setText("Giros");
        jcbGiros.setContentAreaFilled(false);
        jcbGiros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbMonedas.setText("Monedas");
        jcbMonedas.setContentAreaFilled(false);
        jcbMonedas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbImpuestos.setText("Impuestos");
        jcbImpuestos.setContentAreaFilled(false);
        jcbImpuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaModulos(evt);
            }
        });

        jcbModulosPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbModulosPermiso.setText("Puede otorgar permisos de modulos");
        jcbModulosPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbModulosPermiso.setBorderPainted(true);
        jcbModulosPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbImpuestos)
                            .addComponent(jcbMonedas)
                            .addComponent(jcbGiros)
                            .addComponent(jcbZonas)
                            .addComponent(jcbCatalogo)
                            .addComponent(jcbConceptos)
                            .addComponent(jcbContabilidad))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbModulosPermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbModulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcbModulos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbContabilidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbConceptos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCatalogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbZonas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbGiros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbMonedas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbImpuestos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbModulosPermiso)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbInventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbInventario.setText("Inventario");
        jcbInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbInventario.setBorderPainted(true);
        jcbInventario.setOpaque(false);
        jcbInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbInventarioActionPerformed(evt);
            }
        });

        jcbModificarProd.setText("Modificar");
        jcbModificarProd.setContentAreaFilled(false);
        jcbModificarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaInventario(evt);
                deseleccionaProductos(evt);
            }
        });

        jcbProductos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbProductos.setText("Productos");
        jcbProductos.setContentAreaFilled(false);
        jcbProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProductosActionPerformed(evt);
            }
        });

        jcbBorrarProd.setText("Borrar");
        jcbBorrarProd.setContentAreaFilled(false);
        jcbBorrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaInventario(evt);
                deseleccionaProductos(evt);
            }
        });

        jcbNuevoProd.setText("Nuevo");
        jcbNuevoProd.setContentAreaFilled(false);
        jcbNuevoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaInventario(evt);
                deseleccionaProductos(evt);
            }
        });

        jcbInventarioPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbInventarioPermiso.setText("Puede otorgar permisos de Inventario");
        jcbInventarioPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbInventarioPermiso.setBorderPainted(true);
        jcbInventarioPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbProductos)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbModificarProd)
                                    .addComponent(jcbNuevoProd)
                                    .addComponent(jcbBorrarProd))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jcbInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jcbInventarioPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbInventario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbProductos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbNuevoProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbModificarProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbBorrarProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbInventarioPermiso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbPrevioCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbPrevioCompra.setText("Previo de compra");
        jcbPrevioCompra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbPrevioCompra.setBorderPainted(true);
        jcbPrevioCompra.setOpaque(false);
        jcbPrevioCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPrevioCompraActionPerformed(evt);
            }
        });

        jcbAbrirPrevio.setText("abrir");
        jcbAbrirPrevio.setContentAreaFilled(false);
        jcbAbrirPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbNuevaPrevio.setText("Nueva");
        jcbNuevaPrevio.setContentAreaFilled(false);
        jcbNuevaPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbCancelarPrevio.setText("Cancelar");
        jcbCancelarPrevio.setContentAreaFilled(false);
        jcbCancelarPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbVerPrevio.setText("Ver");
        jcbVerPrevio.setContentAreaFilled(false);
        jcbVerPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbCompraPrevio.setText("Compra");
        jcbCompraPrevio.setContentAreaFilled(false);
        jcbCompraPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbSeriesPrevio.setText("Series");
        jcbSeriesPrevio.setContentAreaFilled(false);
        jcbSeriesPrevio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        jcbPrevioPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbPrevioPermiso.setText("Puede otorgar permisos de previo de compra");
        jcbPrevioPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbPrevioPermiso.setBorderPainted(true);
        jcbPrevioPermiso.setOpaque(false);
        jcbPrevioPermiso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaPrevio(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbPrevioPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jcbPrevioCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbCompraPrevio)
                            .addComponent(jcbSeriesPrevio)
                            .addComponent(jcbNuevaPrevio)
                            .addComponent(jcbAbrirPrevio)
                            .addComponent(jcbVerPrevio)
                            .addComponent(jcbCancelarPrevio))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbPrevioCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbNuevaPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbAbrirPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbVerPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCancelarPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbSeriesPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCompraPrevio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jcbPrevioPermiso)
                .addGap(34, 34, 34))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbVentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbVentas.setText("Ventas");
        jcbVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcbVentas.setBorderPainted(true);
        jcbVentas.setOpaque(false);
        jcbVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbVentasActionPerformed(evt);
            }
        });

        jcbCancelarVentas.setText("Cancelar");
        jcbCancelarVentas.setContentAreaFilled(false);
        jcbCancelarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbDevolVentas.setText("Devolución");
        jcbDevolVentas.setContentAreaFilled(false);
        jcbDevolVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbParcialVentas.setText("Parcial");
        jcbParcialVentas.setContentAreaFilled(false);
        jcbParcialVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbNotVentas.setText("Not. crédito");
        jcbNotVentas.setContentAreaFilled(false);
        jcbNotVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbVerVentas.setText("Ver");
        jcbVerVentas.setContentAreaFilled(false);
        jcbVerVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbBorrarArchivoVentas.setText("Borrar");
        jcbBorrarArchivoVentas.setContentAreaFilled(false);
        jcbBorrarArchivoVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
                deseleccionaArchivoVentas(evt);
            }
        });

        jcbCargarArchivoVentas.setText("Cargar");
        jcbCargarArchivoVentas.setContentAreaFilled(false);
        jcbCargarArchivoVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
                deseleccionaArchivoVentas(evt);
            }
        });

        jcbArchivoVentas.setText("Ver Archivo");
        jcbArchivoVentas.setContentAreaFilled(false);
        jcbArchivoVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbArchivoVentasActionPerformed(evt);
            }
        });

        jcbNuevaVentas.setText("Nueva");
        jcbNuevaVentas.setContentAreaFilled(false);
        jcbNuevaVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbEnviarVentas.setText("Enviar");
        jcbEnviarVentas.setContentAreaFilled(false);
        jcbEnviarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbTimbrarVentas.setText("Timbrar");
        jcbTimbrarVentas.setContentAreaFilled(false);
        jcbTimbrarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbEntregarVentas.setText("Entregar");
        jcbEntregarVentas.setContentAreaFilled(false);
        jcbEntregarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbComprobarVentas.setText("Comprobar");
        jcbComprobarVentas.setContentAreaFilled(false);
        jcbComprobarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbAcuseVentas.setText("Acuse");
        jcbAcuseVentas.setContentAreaFilled(false);
        jcbAcuseVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbObtenerXmlVentas.setText("Obtener XML");
        jcbObtenerXmlVentas.setContentAreaFilled(false);
        jcbObtenerXmlVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbFacturarVentas.setText("Facturar");
        jcbFacturarVentas.setContentAreaFilled(false);
        jcbFacturarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deseleccionaVentas(evt);
            }
        });

        jcbVentasPermiso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbVentasPermiso.setText("Puede otorgar permisos de ventas");
        jcbVentasPermiso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbVentasPermiso.setBorderPainted(true);
        jcbVentasPermiso.setOpaque(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jcbVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbVerVentas)
                            .addComponent(jcbNotVentas)
                            .addComponent(jcbParcialVentas)
                            .addComponent(jcbDevolVentas)
                            .addComponent(jcbCancelarVentas)
                            .addComponent(jcbNuevaVentas)
                            .addComponent(jcbEnviarVentas)
                            .addComponent(jcbTimbrarVentas)
                            .addComponent(jcbEntregarVentas)
                            .addComponent(jcbComprobarVentas)
                            .addComponent(jcbAcuseVentas)
                            .addComponent(jcbObtenerXmlVentas)
                            .addComponent(jcbFacturarVentas)
                            .addComponent(jcbArchivoVentas)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbCargarArchivoVentas)
                                    .addComponent(jcbBorrarArchivoVentas))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jcbVentasPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbCancelarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDevolVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbParcialVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbNuevaVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbNotVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbVerVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbEnviarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbTimbrarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbEntregarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbComprobarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbAcuseVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbObtenerXmlVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbFacturarVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbArchivoVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCargarArchivoVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbBorrarArchivoVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jcbVentasPermiso)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jcmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcmbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    /*Cuando se presiona el botón de cancelar*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Cierra el formulario*/
        this.setVisible(false);     
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    
    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                    
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    
    /*Cuandose presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

//        if(jcmbUsuarios.getSelectedIndex()!=0){
            /*Preguntar al usuario si esta seguro de guardar la configuración*/
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Configuración", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
            {                        
                /*Pide la clave de seguridad 1*/
//                JPasswordField jpf = new JPasswordField();
//                JOptionPane.showConfirmDialog(null, jpf, "Clave de seguridad 1:", JOptionPane.OK_CANCEL_OPTION);

               //Berna
                //Revisa que checkbox's estan selecionados
                actualizaPermisos(jcbConf,jcbConfCorreos,jcbConfDatos,jcbconfSeries,jcbConfImpresoras,jcbConfCambiarIcono,jcbConfConfiguraciones, jcbConfiguracionesPermiso,
                                jcbUsuarios, jcbDefinirUsr, jcbUsrConectados, jcbPermisosUsr,jcbClaves, jcbReparar, jcbReparador, jcbRestaurar, jcbBaseDatos, jcbConexionesBD, jcbArchivoConf,jcbReportes, jcbRepUsuarios, jcbRepRespaldos, jcbRepLog, jcbRepEstadisticas, jcbRevocacion, jcbActivar,jcbSistemaPermiso,
                                jcbContabilidad, jcbConceptos, jcbCatalogo, jcbZonas, jcbGiros, jcbMonedas, jcbImpuestos, jcbModulosPermiso,
                                jcbCompras, jcbCancelarCompra, jcbDevolCompra, jcbParcialCompra, jcbNuevoCompra,jcbNotCompra, jcbVerCompra,jcbCargarArchivoCompra, jcbBorrarArchivoCompra, jcbRecibirCompra, jcbComprasPermiso,
                                jcbProve, jcbProveNuevo, jcbProveModificar, jcbProveVer, jcbProveBorrar, jcbProveePermiso,
                                jcbPrevioCompra, jcbNuevaPrevio, jcbAbrirPrevio, jcbVerPrevio, jcbCancelarPrevio, jcbSeriesPrevio, jcbCompraPrevio, jcbPrevioPermiso,
                                jcbInventario, jcbProductos, jcbNuevoProd, jcbModificarProd, jcbBorrarProd, jcbInventarioPermiso,
                                jcbClientes, jcbClieNuevo, jcbClieModificar, jcbClieVer, jcbClieBorrar, jcbClieEnviar, jcbClientesPermiso,
                                jcbVentas, jcbCancelarVentas, jcbDevolVentas, jcbParcialVentas, jcbNuevaVentas, jcbNotVentas, jcbVerVentas, jcbEnviarVentas, jcbTimbrarVentas, jcbEntregarVentas, jcbComprobarVentas, jcbAcuseVentas, jcbObtenerXmlVentas, jcbFacturarVentas, jcbCargarArchivoVentas, jcbBorrarArchivoVentas, jcbVentasPermiso,
                                jcbCotizaciones, jcbNuevaCotiza, jcbAbrirCotiza, jcbVerCotiza, jcbCancelarCotiza, jcbReenviarCotiza, jcbVentaCotiza, jcbCotizaPermiso);

                /*Mensajea de éxito*/
                JOptionPane.showMessageDialog(null, "Configuración guardada con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
//            }
        }else
            ;//mensaje de error
        
        
    }//GEN-LAST:event_jBGuarActionPerformed
    
    private void actualizaPermisos(javax.swing.JCheckBox... checks) {
        //Abre la conexion
        Connection con = Star.conAbrBas(true, false);

        try {
            java.sql.PreparedStatement ps = con.prepareStatement(""
                    + "UPDATE er_permisos "
                    + "SET "
                        +"permisoConf="+checks[0].isSelected()+
                        ",permisoCorreos="+checks[1].isSelected()+
                        ",permisoDatosEmpresa="+checks[2].isSelected()+
                        ",permisoSeries="+checks[3].isSelected()+
                        ",permisoImpresoras="+checks[4].isSelected()+
                        ",permisoCambiarIcono="+checks[5].isSelected()+
                        ",permisoConfiguracionesGenerales="+checks[6].isSelected()+
                        ",otorgaPermisosConfig="+checks[7].isSelected()+
                        ",permisoUsuarios="+checks[8].isSelected()+
                        ",permisoUsuariosDefinir="+checks[9].isSelected()+
                        ",permisoUsuariosConectados="+checks[10].isSelected()+
                        ",permisoUsuariosPermisos="+checks[11].isSelected()+
                        ",permisoClaves="+checks[12].isSelected()+
                        ",permisoReparar="+checks[13].isSelected()+
                        ",permisoRepararErrores="+checks[14].isSelected()+
                        ",permisoRepararRestaurar="+checks[15].isSelected()+
                        ",permisoBaseDatos="+checks[16].isSelected()+
                        ",permisoBaseDatosConexiones="+checks[17].isSelected()+
                        ",permisoBaseDatosArchivo="+checks[18].isSelected()+
                        ",permisoReportes="+checks[19].isSelected()+
                        ",permisoReportesUsuarios="+checks[20].isSelected()+
                        ",permisoReportesRespaldos="+checks[21].isSelected()+
                        ",permisoReportesLog="+checks[22].isSelected()+
                        ",permisoReportesEstadistica="+checks[23].isSelected()+
                        ",permisoRevocacion="+checks[24].isSelected()+
                        ",permisoActivarSistema="+checks[25].isSelected()+
                        ",otorgaPermisosSistema="+checks[26].isSelected()+
                        ",permisoContabilidad="+checks[27].isSelected()+
                        ",permisoConceptosNC="+checks[28].isSelected()+
                        ",permisoCatalogoGarantias="+checks[29].isSelected()+
                        ",permisoZonas="+checks[30].isSelected()+
                        ",permisoGiros="+checks[31].isSelected()+
                        ",permisoMonedas="+checks[32].isSelected()+
                        ",permisoImpuestos="+checks[33].isSelected()+
                        ",otorgaPermisosModulos="+checks[34].isSelected()+
                        ",permisoCompras="+checks[35].isSelected()+
                        ",permisoComprasCancelar="+checks[36].isSelected()+
                        ",permisoComprasDevolucion="+checks[37].isSelected()+
                        ",permisoComprasParcial="+checks[38].isSelected()+
                        ",permisoComprasNuevo="+checks[39].isSelected()+
                        ",permisoComprasNotCredito="+checks[40].isSelected()+
                        ",permisoComprasVer="+checks[41].isSelected()+
                        ",permisoComprasCargarArchivo="+checks[42].isSelected()+
                        ",permisoComprasBorrarArchivo="+checks[43].isSelected()+
                        ",permisoComprasRecibirOrden="+checks[44].isSelected()+
                        ",otorgaPermisosCompras="+checks[45].isSelected()+
                        ",permisoProvee="+checks[46].isSelected()+
                        ",permisoProveeNuevo="+checks[47].isSelected()+
                        ",permisoProveeModificar="+checks[48].isSelected()+
                        ",permisoProveeVer="+checks[49].isSelected()+
                        ",permisoProveeBorrar="+checks[50].isSelected()+
                        ",otorgaPermisosProvee="+checks[51].isSelected()+
                        ",permisoPrevio="+checks[52].isSelected()+
                        ",permisoPrevioNueva="+checks[53].isSelected()+
                        ",permisoPrevioAbrir="+checks[54].isSelected()+
                        ",permisoPrevioVer="+checks[55].isSelected()+
                        ",permisoPrevioCancelar="+checks[56].isSelected()+
                        ",permisoPrevioSeries="+checks[57].isSelected()+
                        ",permisoPrevioCompra="+checks[58].isSelected()+
                        ",otorgaPermisosPrevio="+checks[59].isSelected()+
                        ",permisoInventario="+checks[60].isSelected()+
                        ",permisoProductos="+checks[61].isSelected()+
                        ",permisoProductosNuevo="+checks[62].isSelected()+
                        ",permisoProductosModificar="+checks[63].isSelected()+
                        ",permisProductosBorrar="+checks[64].isSelected()+
                        ",otorgaPermisosInventario="+checks[65].isSelected()+
                        ",permisoClientes="+checks[66].isSelected()+
                        ",permisoClientesNuevo="+checks[67].isSelected()+
                        ",permisoClientesModificar="+checks[68].isSelected()+
                        ",permisoClientesVer="+checks[69].isSelected()+
                        ",permisoClientesBorrar="+checks[70].isSelected()+
                        ",permisoClientesEnviar="+checks[71].isSelected()+
                        ",otorgaPermisosclientes="+checks[72].isSelected()+
                        ",permisoVentas="+checks[73].isSelected()+
                        ",permisoVentasCancelar="+checks[74].isSelected()+
                        ",permisoVentasDevolucion="+checks[75].isSelected()+
                        ",permisoVentasParcial="+checks[76].isSelected()+
                        ",permisoVentasNueva="+checks[77].isSelected()+
                        ",permisoVentasNotCredito="+checks[78].isSelected()+
                        ",permisoVentasVer="+checks[79].isSelected()+
                        ",permisoVentasEnviar="+checks[80].isSelected()+
                        ",permisoVentasTimbrar="+checks[81].isSelected()+
                        ",permisoVentasEntregar="+checks[82].isSelected()+
                        ",permisoVentasComprobar="+checks[83].isSelected()+
                        ",permisoVentasAcuse="+checks[84].isSelected()+
                        ",permisoVentasObtenerXml="+checks[85].isSelected()+
                        ",permisoVentasFacturar="+checks[86].isSelected()+
                        ",permisoVentasCargarArchivo="+checks[87].isSelected()+
                        ",permisoVentasBorrarArchivo="+checks[88].isSelected()+
                        ",otorgaPermisosVentas="+checks[89].isSelected()+
                        ",permisoCotiza="+checks[90].isSelected()+
                        ",permisoCotizaNueva="+checks[91].isSelected()+
                        ",permisoCotizaAbrir="+checks[92].isSelected()+
                        ",permisoCotizaVer="+checks[93].isSelected()+
                        ",permisoCotizaCancelar="+checks[94].isSelected()+
                        ",permisoCotizaReenviar="+checks[95].isSelected()+
                        ",permisoCotizaVenta="+checks[96].isSelected()+
                        ",otorgaPermisosCotiza="+checks[97].isSelected()+
                    " WHERE "
                        + "er_permisos.FKIdUsuario = (SELECT id_id FROM estacs WHERE estac = '"+jcmbUsuarios.getSelectedItem().toString()+"')"
            );
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    /*Cuando se presiona una tecla en el panel*/
    private void GralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GralKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_GralKeyPressed

    
    /*Cuando se arrastra el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el mouse en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando el mouse entra en el campo del link de ayuda*/
    private void jLAyuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando el mouse sale del campo del link de ayuda*/
    private void jLAyuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));	
        
    }//GEN-LAST:event_jLAyuMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
    }//GEN-LAST:event_formMouseWheelMoved

    private void jcmbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbUsuariosActionPerformed
        // TODO add your handling code here:
        
        cargaPermisos();
    }//GEN-LAST:event_jcmbUsuariosActionPerformed

    private void jcbConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbConfActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(1, false, null);
        else
            seleccionaHijos(1, true, jcbConf);
    }//GEN-LAST:event_jcbConfActionPerformed

    private void jcbSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSistemaActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(2, false, null);
        else
            seleccionaHijos(2, true, jcbSistema);
    }//GEN-LAST:event_jcbSistemaActionPerformed

    private void jcbModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbModulosActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(3, false, null);
        else
            seleccionaHijos(3, true, jcbModulos);
    }//GEN-LAST:event_jcbModulosActionPerformed

    private void jcbComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbComprasActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(4, false, null);
        else{
            seleccionaHijos(4, true, jcbCompras);
            //deselecciono la compra, en previo de compra
            jcbCompraPrevio.setSelected(false);
        }
            
    }//GEN-LAST:event_jcbComprasActionPerformed

    private void jcbProveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProveActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(5, false, null);
        else
            seleccionaHijos(5, true, jcbProve);
    }//GEN-LAST:event_jcbProveActionPerformed

    private void jcbPrevioCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPrevioCompraActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(6, false, null);
        else
            seleccionaHijos(6, true, jcbPrevioCompra);
    }//GEN-LAST:event_jcbPrevioCompraActionPerformed

    private void jcbInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbInventarioActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(7, false, null);
        else
            seleccionaHijos(7, true, jcbInventario);
    }//GEN-LAST:event_jcbInventarioActionPerformed

    private void jcbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(8, false, null);
        else
            seleccionaHijos(8, true, jcbClientes);
    }//GEN-LAST:event_jcbClientesActionPerformed

    private void jcbVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbVentasActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(9, false, null);
        else{
            //deselecciono el permiso de venta, en cotizaciones
            jcbVentaCotiza.setSelected(false);
            
            seleccionaHijos(9, true, jcbVentas);
        }
            
    }//GEN-LAST:event_jcbVentasActionPerformed

    private void jcbCotizacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCotizacionesActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(10, false, null);
        else{
            seleccionaHijos(10, true, jcbCotizaciones);
        }
            
    }//GEN-LAST:event_jcbCotizacionesActionPerformed

    private void jcbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbUsuariosActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(11, false, null);
        else
            seleccionaHijos(11, true, jcbSistema);
    }//GEN-LAST:event_jcbUsuariosActionPerformed

    private void jcbRepararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbRepararActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(12, false, null);
        else
            seleccionaHijos(12, true, jcbSistema);
    }//GEN-LAST:event_jcbRepararActionPerformed

    private void jcbBaseDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbBaseDatosActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(13, false, null);
        else
            seleccionaHijos(13, true, jcbSistema);
    }//GEN-LAST:event_jcbBaseDatosActionPerformed

    private void jcbReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbReportesActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(14, false, null);
        else
            seleccionaHijos(14, true, jcbSistema);
    }//GEN-LAST:event_jcbReportesActionPerformed

    private void jcbArchivoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbArchivoCompraActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(15, false, null);
        else
            seleccionaHijos(15, true, jcbCompras);
    }//GEN-LAST:event_jcbArchivoCompraActionPerformed

    private void jcbProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProductosActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(16, false, null);
        else
            seleccionaHijos(16, true, jcbInventario);
    }//GEN-LAST:event_jcbProductosActionPerformed

    private void jcbArchivoVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbArchivoVentasActionPerformed
        // TODO add your handling code here:
        if(((JCheckBox)evt.getSource()).isSelected())
            seleccionaHijos(17, false, null);
        else
            seleccionaHijos(17, true, jcbVentas);
    }//GEN-LAST:event_jcbArchivoVentasActionPerformed

    private void deseleccionaConfig(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaConfig
        // TODO add your handling code here:
        //si desactivo la casilla, y el de configuraciones esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbConf.isSelected())
            jcbConf.setSelected(false);
    }//GEN-LAST:event_deseleccionaConfig

    private void deseleccionaSistema(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaSistema
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Sistema esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbSistema.isSelected())
            jcbSistema.setSelected(false);
    }//GEN-LAST:event_deseleccionaSistema

    private void deseleccionaModulos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaModulos
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Modulos esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbModulos.isSelected())
            jcbModulos.setSelected(false);
    }//GEN-LAST:event_deseleccionaModulos

    private void deseleccionaCompras(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaCompras
        
        //si desactivo la casilla, y el de Compras esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected()){
            //Si la deselecciono, tambien deselecciono Compra en previo de compra
            jcbCompraPrevio.setSelected(false);
            
            if(jcbCompras.isSelected())
                jcbCompras.setSelected(false);
        }    
    }//GEN-LAST:event_deseleccionaCompras

    private void deseleccionaProvee(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaProvee
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Proveedores esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbProve.isSelected())
            jcbProve.setSelected(false);
    }//GEN-LAST:event_deseleccionaProvee

    private void deseleccionaPrevio(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaPrevio
        
        //si lo seleccionaron, reviso que la compra nueva tenga permiso
        if(((JCheckBox)evt.getSource()).isSelected() && !jcbNuevoCompra.isSelected()){
            ((JCheckBox)evt.getSource()).setSelected(false);
            JOptionPane.showMessageDialog(this, "Debe de tener seleccionado el permiso de 'Nuevo' en Compra", "Error de Permiso", JOptionPane.WARNING_MESSAGE);
        }
            
        //si desactivo la casilla, y el de Previo de Compra esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbPrevioCompra.isSelected())
            jcbPrevioCompra.setSelected(false);
    }//GEN-LAST:event_deseleccionaPrevio

    private void deseleccionaInventario(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaInventario
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Inventario esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbInventario.isSelected())
            jcbInventario.setSelected(false);
    }//GEN-LAST:event_deseleccionaInventario

    private void deseleccionaClientes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaClientes
        
        //si desactivo la casilla, y el de Clientes esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbClientes.isSelected())
            jcbClientes.setSelected(false);
    }//GEN-LAST:event_deseleccionaClientes

    private void deseleccionaVentas(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaVentas
        
        //si desactivo la casilla, y el de Ventas esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected()){
            //deselecciono el permiso venta, en cotizaciones
            jcbVentaCotiza.setSelected(false);
            
            if(jcbVentas.isSelected())
                jcbVentas.setSelected(false);
        }
            
    }//GEN-LAST:event_deseleccionaVentas

    private void deseleccionaCoti(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaCoti
        //Si selecciono la venta, reviso que este seleccionado el permiso Nueva, en Ventas
        if(((JCheckBox)evt.getSource()).isSelected() && !jcbNuevaVentas.isSelected()){
           ((JCheckBox)evt.getSource()).setSelected(false);
           JOptionPane.showMessageDialog(this, "Debe de tener seleccionado el permiso de 'Nueva' en Ventas", "Error de Permiso", JOptionPane.WARNING_MESSAGE);
        }
        //si desactivo la casilla, y el de Cotizaciones esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbCotizaciones.isSelected())
            jcbCotizaciones.setSelected(false);
    }//GEN-LAST:event_deseleccionaCoti

    private void deseleccionaArchivoVentas(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaArchivoVentas
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Cotizaciones esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbVentas.isSelected())
            jcbVentas.setSelected(false);
        //si desactivo la casilla, y el de Ver Archivo esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbArchivoVentas.isSelected())
            jcbArchivoVentas.setSelected(false);
    }//GEN-LAST:event_deseleccionaArchivoVentas

    private void deseleccionaProductos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaProductos
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Inventario esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbInventario.isSelected())
            jcbInventario.setSelected(false);
        //si desactivo la casilla, y el de Productos esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbProductos.isSelected())
            jcbProductos.setSelected(false);
    }//GEN-LAST:event_deseleccionaProductos

    private void deseleccionaArchivoCompras(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaArchivoCompras
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Compras esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbCompras.isSelected())
            jcbCompras.setSelected(false);
        //si desactivo la casilla, y el de Ver Archivo esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbArchivoCompra.isSelected())
            jcbArchivoCompra.setSelected(false);
    }//GEN-LAST:event_deseleccionaArchivoCompras

    private void deseleccionaUsuarios(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaUsuarios
        // TODO add your handling code here:
         //si desactivo la casilla, y el de Sistema esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbSistema.isSelected())
            jcbSistema.setSelected(false);
        //si desactivo la casilla, y el de Usuarios esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbUsuarios.isSelected())
            jcbUsuarios.setSelected(false);
    }//GEN-LAST:event_deseleccionaUsuarios

    private void deseleccionaBD(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaBD
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Sistema esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbSistema.isSelected())
            jcbSistema.setSelected(false);
        //si desactivo la casilla, y el de Base de datos esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbBaseDatos.isSelected())
            jcbBaseDatos.setSelected(false);
    }//GEN-LAST:event_deseleccionaBD

    private void deseleccionaReportes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deseleccionaReportes
        // TODO add your handling code here:
        //si desactivo la casilla, y el de Sistema esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbSistema.isSelected())
            jcbSistema.setSelected(false);
        //si desactivo la casilla, y el de Reportes esta activado=lo desactivo
        if(!((JCheckBox)evt.getSource()).isSelected() && jcbReportes.isSelected())
            jcbReportes.setSelected(false);
    }//GEN-LAST:event_deseleccionaReportes
   
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelSistema;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jcbAbrirCotiza;
    private javax.swing.JCheckBox jcbAbrirPrevio;
    private javax.swing.JCheckBox jcbActivar;
    private javax.swing.JCheckBox jcbAcuseVentas;
    private javax.swing.JCheckBox jcbArchivoCompra;
    private javax.swing.JCheckBox jcbArchivoConf;
    private javax.swing.JCheckBox jcbArchivoVentas;
    private javax.swing.JCheckBox jcbBaseDatos;
    private javax.swing.JCheckBox jcbBorrarArchivoCompra;
    private javax.swing.JCheckBox jcbBorrarArchivoVentas;
    private javax.swing.JCheckBox jcbBorrarProd;
    private javax.swing.JCheckBox jcbCancelarCompra;
    private javax.swing.JCheckBox jcbCancelarCotiza;
    private javax.swing.JCheckBox jcbCancelarPrevio;
    private javax.swing.JCheckBox jcbCancelarVentas;
    private javax.swing.JCheckBox jcbCargarArchivoCompra;
    private javax.swing.JCheckBox jcbCargarArchivoVentas;
    private javax.swing.JCheckBox jcbCatalogo;
    private javax.swing.JCheckBox jcbClaves;
    private javax.swing.JCheckBox jcbClieBorrar;
    private javax.swing.JCheckBox jcbClieEnviar;
    private javax.swing.JCheckBox jcbClieModificar;
    private javax.swing.JCheckBox jcbClieNuevo;
    private javax.swing.JCheckBox jcbClieVer;
    private javax.swing.JCheckBox jcbClientes;
    private javax.swing.JCheckBox jcbClientesPermiso;
    private javax.swing.JCheckBox jcbCompraPrevio;
    private javax.swing.JCheckBox jcbCompras;
    private javax.swing.JCheckBox jcbComprasPermiso;
    private javax.swing.JCheckBox jcbComprobarVentas;
    private javax.swing.JCheckBox jcbConceptos;
    private javax.swing.JCheckBox jcbConexionesBD;
    private javax.swing.JCheckBox jcbConf;
    private javax.swing.JCheckBox jcbConfCambiarIcono;
    private javax.swing.JCheckBox jcbConfConfiguraciones;
    private javax.swing.JCheckBox jcbConfCorreos;
    private javax.swing.JCheckBox jcbConfDatos;
    private javax.swing.JCheckBox jcbConfImpresoras;
    private javax.swing.JCheckBox jcbConfiguracionesPermiso;
    private javax.swing.JCheckBox jcbContabilidad;
    private javax.swing.JCheckBox jcbCotizaPermiso;
    private javax.swing.JCheckBox jcbCotizaciones;
    private javax.swing.JCheckBox jcbDefinirUsr;
    private javax.swing.JCheckBox jcbDevolCompra;
    private javax.swing.JCheckBox jcbDevolVentas;
    private javax.swing.JCheckBox jcbEntregarVentas;
    private javax.swing.JCheckBox jcbEnviarVentas;
    private javax.swing.JCheckBox jcbFacturarVentas;
    private javax.swing.JCheckBox jcbGiros;
    private javax.swing.JCheckBox jcbImpuestos;
    private javax.swing.JCheckBox jcbInventario;
    private javax.swing.JCheckBox jcbInventarioPermiso;
    private javax.swing.JCheckBox jcbModificarProd;
    private javax.swing.JCheckBox jcbModulos;
    private javax.swing.JCheckBox jcbModulosPermiso;
    private javax.swing.JCheckBox jcbMonedas;
    private javax.swing.JCheckBox jcbNotCompra;
    private javax.swing.JCheckBox jcbNotVentas;
    private javax.swing.JCheckBox jcbNuevaCotiza;
    private javax.swing.JCheckBox jcbNuevaPrevio;
    private javax.swing.JCheckBox jcbNuevaVentas;
    private javax.swing.JCheckBox jcbNuevoCompra;
    private javax.swing.JCheckBox jcbNuevoProd;
    private javax.swing.JCheckBox jcbObtenerXmlVentas;
    private javax.swing.JCheckBox jcbParcialCompra;
    private javax.swing.JCheckBox jcbParcialVentas;
    private javax.swing.JCheckBox jcbPermisosUsr;
    private javax.swing.JCheckBox jcbPrevioCompra;
    private javax.swing.JCheckBox jcbPrevioPermiso;
    private javax.swing.JCheckBox jcbProductos;
    private javax.swing.JCheckBox jcbProve;
    private javax.swing.JCheckBox jcbProveBorrar;
    private javax.swing.JCheckBox jcbProveModificar;
    private javax.swing.JCheckBox jcbProveNuevo;
    private javax.swing.JCheckBox jcbProveVer;
    private javax.swing.JCheckBox jcbProveePermiso;
    private javax.swing.JCheckBox jcbRecibirCompra;
    private javax.swing.JCheckBox jcbReenviarCotiza;
    private javax.swing.JCheckBox jcbRepEstadisticas;
    private javax.swing.JCheckBox jcbRepLog;
    private javax.swing.JCheckBox jcbRepRespaldos;
    private javax.swing.JCheckBox jcbRepUsuarios;
    private javax.swing.JCheckBox jcbReparador;
    private javax.swing.JCheckBox jcbReparar;
    private javax.swing.JCheckBox jcbReportes;
    private javax.swing.JCheckBox jcbRestaurar;
    private javax.swing.JCheckBox jcbRevocacion;
    private javax.swing.JCheckBox jcbSeriesPrevio;
    private javax.swing.JCheckBox jcbSistema;
    private javax.swing.JCheckBox jcbSistemaPermiso;
    private javax.swing.JCheckBox jcbTimbrarVentas;
    private javax.swing.JCheckBox jcbUsrConectados;
    private javax.swing.JCheckBox jcbUsuarios;
    private javax.swing.JCheckBox jcbVentaCotiza;
    private javax.swing.JCheckBox jcbVentas;
    private javax.swing.JCheckBox jcbVentasPermiso;
    private javax.swing.JCheckBox jcbVerCompra;
    private javax.swing.JCheckBox jcbVerCotiza;
    private javax.swing.JCheckBox jcbVerPrevio;
    private javax.swing.JCheckBox jcbVerVentas;
    private javax.swing.JCheckBox jcbZonas;
    private javax.swing.JCheckBox jcbconfSeries;
    private javax.swing.JComboBox jcmbUsuarios;
    // End of variables declaration//GEN-END:variables

    //Carga los usuarios al comboBox
    private void cargaUsuarios() {
        //Abre la conexion
        Connection con = Star.conAbrBas(true, false);
        
        //Declara variables de la base de datos    
        java.sql.Statement   st;
        java.sql.ResultSet   rs;                        
        String      sQ;
        /*Obtiene todos los datos del usuario*/
        try
        {
            sQ  = "SELECT estac FROM estacs WHERE estac <> '" + Login.sUsrG + "' AND admcaj <= (SELECT admcaj FROM estacs WHERE estac = '" + Login.sUsrG + "')";
            st = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los datos y cargalos en sus campos*/
                jcmbUsuarios.addItem(rs.getString("estac"));
            }
            //si ingreso usuarios al combo, jalo los permisos del primer usuario
//            if(jcmbUsuarios.getItemCount() > 0){
//                cargaPermisos(jcmbUsuarios.getSelectedItem().toString());
//            }
                
        }catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                      
        }
    }

    private void cargaPermisos() {
        //habilita los campos, por si alguno fue desactivado
        habilitaCampos();
        
        //Abre la conexion
        Connection con = Star.conAbrBas(true, false);
        
        //Declara variables de la base de datos    
        java.sql.Statement   st;
        java.sql.ResultSet   rs;                        
        String      sQ;
        
        try {
            /*Obtiene todos los datos del usuario*/
            sQ  = "SELECT * FROM er_permisos WHERE FKIdUsuario = (SELECT id_id FROM estacs WHERE estac = '"+jcmbUsuarios.getSelectedItem().toString()+"')";
            st = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene los datos y cargalos en sus campos*/
                llenaPermisos(rs,jcbConf,jcbConfCorreos,jcbConfDatos,jcbconfSeries,jcbConfImpresoras,jcbConfCambiarIcono,jcbConfConfiguraciones, jcbConfiguracionesPermiso,
                                jcbUsuarios, jcbDefinirUsr, jcbUsrConectados, jcbPermisosUsr,jcbClaves, jcbReparar, jcbReparador, jcbRestaurar, jcbBaseDatos, jcbConexionesBD, jcbArchivoConf,jcbReportes, jcbRepUsuarios, jcbRepRespaldos, jcbRepLog, jcbRepEstadisticas, jcbRevocacion, jcbActivar,jcbSistemaPermiso,
                                jcbContabilidad, jcbConceptos, jcbCatalogo, jcbZonas, jcbGiros, jcbMonedas, jcbImpuestos, jcbModulosPermiso,
                                jcbCompras, jcbCancelarCompra, jcbDevolCompra, jcbParcialCompra, jcbNuevoCompra,jcbNotCompra, jcbVerCompra,jcbCargarArchivoCompra, jcbBorrarArchivoCompra, jcbRecibirCompra, jcbComprasPermiso,
                                jcbProve, jcbProveNuevo, jcbProveModificar, jcbProveVer, jcbProveBorrar, jcbProveePermiso,
                                jcbPrevioCompra, jcbNuevaPrevio, jcbAbrirPrevio, jcbVerPrevio, jcbCancelarPrevio,  jcbSeriesPrevio,jcbCompraPrevio,jcbPrevioPermiso,
                                jcbInventario, jcbProductos, jcbNuevoProd, jcbModificarProd, jcbBorrarProd, jcbInventarioPermiso,
                                jcbClientes, jcbClieNuevo, jcbClieModificar, jcbClieVer, jcbClieBorrar, jcbClieEnviar, jcbClientesPermiso,
                                jcbVentas, jcbCancelarVentas, jcbDevolVentas, jcbParcialVentas, jcbNuevaVentas, jcbNotVentas, jcbVerVentas, jcbEnviarVentas, jcbTimbrarVentas, jcbEntregarVentas, jcbComprobarVentas, jcbAcuseVentas, jcbObtenerXmlVentas, jcbFacturarVentas, jcbCargarArchivoVentas, jcbBorrarArchivoVentas, jcbVentasPermiso,
                                jcbCotizaciones, jcbNuevaCotiza, jcbAbrirCotiza, jcbVerCotiza, jcbCancelarCotiza, jcbReenviarCotiza, jcbVentaCotiza, jcbCotizaPermiso);
                   
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            /*Obtiene todos los datos del usuario*/
            sQ  = "SELECT otorgaPermisosConfig, otorgaPermisosSistema, otorgaPermisosModulos, otorgaPermisosCompras, otorgaPermisosProvee, otorgaPermisosPrevio, otorgaPermisosInventario, otorgaPermisosClientes, otorgaPermisosVentas, otorgaPermisosCotiza FROM er_permisos WHERE FKIdUsuario =  (SELECT id_id FROM estacs WHERE estac = '"+Login.sUsrG+"')";
            st = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.last())
            {
                //Desactiva los permisos a los cuales no tiene permiso el usuario actual
                revisaOtorgaPermisos(rs);
                   
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenaPermisos(ResultSet rs, JCheckBox... checks){
        for (int i = 0; i < checks.length; i++) {
            try {
                checks[i].setSelected((Integer.parseInt(rs.getString(i+3))==(1)));
            } catch (SQLException ex) {
                Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //revisa que si todos los checks de alguna categoria esta seleccionados, se seleccione el padre
        revisaPadresCheck();
    }

    private void revisaPadresCheck() {
        revisaSiSeleccionoPadres(jcbConf,jcbConfCorreos,jcbConfDatos,jcbconfSeries,jcbConfImpresoras,jcbConfCambiarIcono,jcbConfConfiguraciones);
        revisaSiSeleccionoPadres(jcbSistema,jcbUsuarios, jcbDefinirUsr, jcbUsrConectados, jcbPermisosUsr,jcbClaves, jcbReparar, jcbReparador, jcbRestaurar, jcbBaseDatos, jcbConexionesBD, jcbArchivoConf,jcbReportes, jcbRepUsuarios, jcbRepRespaldos, jcbRepLog, jcbRepEstadisticas, jcbRevocacion, jcbActivar);
        revisaSiSeleccionoPadres(jcbModulos,jcbContabilidad, jcbConceptos, jcbCatalogo, jcbZonas, jcbGiros, jcbMonedas, jcbImpuestos);
        revisaSiSeleccionoPadres(jcbArchivoCompra, jcbCargarArchivoCompra, jcbBorrarArchivoCompra);
        revisaSiSeleccionoPadres(jcbArchivoVentas, jcbCargarArchivoVentas, jcbBorrarArchivoVentas);
    }

    private void revisaSiSeleccionoPadres(JCheckBox padre, JCheckBox... hijos){        
        boolean selec = true;
        for(JCheckBox hijo:hijos)
            //si alguno de los hijos no esta seleccionado, terminamos en for
            if(!hijo.isSelected()){
                selec = false;
                break;
            }
        padre.setSelected(selec);
    }

    private void seleccionaHijos(int caso, boolean deselecciona, JCheckBox papa) {
        switch(caso){
            case 1: selecHijos( papa, deselecciona, hijos.get(0) );break;
            case 2: selecHijos( papa, deselecciona,  hijos.get(1) );break;
            case 3: selecHijos( papa,deselecciona,  hijos.get(2) ); break;
            case 4: selecHijos( papa,deselecciona,  hijos.get(3) ); break;
            case 5: selecHijos( papa,deselecciona,  hijos.get(4) ); break;
            case 6: selecHijos( papa,deselecciona,  hijos.get(5) ); break;
            case 7: selecHijos( papa,deselecciona,  hijos.get(6) ); break;
            case 8: selecHijos( papa,deselecciona,   hijos.get(7) ); break;
            case 9: selecHijos( papa,deselecciona,  hijos.get(8) ); break;
            case 10: selecHijos( papa, deselecciona,  hijos.get(9) );break;
            case 11: selecHijos( papa, deselecciona,  hijos.get(10) ); break;
            case 12: selecHijos( papa,deselecciona,  hijos.get(11) ); break;
            case 13: selecHijos( papa,deselecciona,  hijos.get(12) ); break;
            case 14: selecHijos( papa, deselecciona,  hijos.get(13) ); break;
            case 15: selecHijos( papa, deselecciona,  hijos.get(14) );break;
            case 16: selecHijos( papa,deselecciona,  hijos.get(15) ); break;
            case 17: selecHijos( papa, deselecciona,  hijos.get(16) );
        }
    }

    private void selecHijos(JCheckBox papa, boolean deselecciona,java.util.ArrayList<JCheckBox> hijos) {
            for(JCheckBox hijo: hijos)
                hijo.setSelected(!deselecciona);
            if(deselecciona)
                papa.setSelected(false);
    }

    private void revisaOtorgaPermisos(ResultSet rs) {
        //otorgaPermisosConfig, otorgaPermisosSistema, otorgaPermisosModulos, otorgaPermisosCompras, otorgaPermisosProvee, otorgaPermisosPrevio, 
       // otorgaPermisosInventario, otorgaPermisosClientes, otorgaPermisosVentas, otorgaPermisosCotiza
        try {
            if(rs.last()){
                if((Integer.parseInt(rs.getString("otorgaPermisosConfig"))==(0))){
                    desHabilita(jcbConf,  hijos.get(0));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosSistema"))==(0))){
                    desHabilita(jcbSistema,hijos.get(1));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosModulos"))==(0))){
                    desHabilita(jcbModulos, hijos.get(2));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosCompras"))==(0))){
                    desHabilita(jcbCompras,hijos.get(3));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosProvee"))==(0))){
                    desHabilita(jcbProve,  hijos.get(4));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosPrevio"))==(0))){
                    desHabilita(jcbPrevioCompra,  hijos.get(5));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosInventario"))==(0))){
                    desHabilita(jcbInventario,  hijos.get(6));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosClientes"))==(0))){
                    desHabilita(jcbClientes, hijos.get(7));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosVentas"))==(0))){
                    desHabilita(jcbVentas,  hijos.get(8));
                }
                if((Integer.parseInt(rs.getString("otorgaPermisosCotiza"))==(0))){
                    desHabilita(jcbCotizaciones, hijos.get(9));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargaHijos() {
        hijos.add(insertaHijos( jcbConfCorreos,jcbConfDatos,jcbconfSeries,jcbConfImpresoras,jcbConfCambiarIcono,jcbConfConfiguraciones, jcbConfiguracionesPermiso));
        hijos.add(insertaHijos( jcbUsuarios, jcbDefinirUsr, jcbUsrConectados, jcbPermisosUsr,jcbClaves, jcbReparar, jcbReparador, jcbRestaurar, jcbBaseDatos, jcbConexionesBD, jcbArchivoConf,jcbReportes, jcbRepUsuarios, jcbRepRespaldos, jcbRepLog, jcbRepEstadisticas, jcbRevocacion, jcbActivar, jcbSistemaPermiso));
        hijos.add(insertaHijos( jcbContabilidad, jcbConceptos, jcbCatalogo, jcbZonas, jcbGiros, jcbMonedas, jcbImpuestos, jcbModulosPermiso));
        hijos.add(insertaHijos( jcbCancelarCompra, jcbDevolCompra, jcbParcialCompra, jcbNuevoCompra,jcbNotCompra, jcbVerCompra,jcbArchivoCompra,jcbCargarArchivoCompra, jcbBorrarArchivoCompra, jcbRecibirCompra, jcbComprasPermiso));
        hijos.add(insertaHijos( jcbProveNuevo, jcbProveModificar, jcbProveVer, jcbProveBorrar, jcbProveePermiso));
        hijos.add(insertaHijos( jcbNuevaPrevio, jcbAbrirPrevio, jcbVerPrevio, jcbCancelarPrevio,jcbSeriesPrevio,jcbCompraPrevio,jcbPrevioPermiso));
        hijos.add(insertaHijos( jcbProductos, jcbNuevoProd, jcbModificarProd, jcbBorrarProd, jcbInventarioPermiso));
        hijos.add(insertaHijos( jcbClieNuevo, jcbClieModificar, jcbClieVer, jcbClieBorrar, jcbClieEnviar, jcbClientesPermiso));
        hijos.add(insertaHijos( jcbCancelarVentas, jcbDevolVentas, jcbParcialVentas, jcbNuevaVentas, jcbNotVentas, jcbVerVentas, jcbEnviarVentas, jcbTimbrarVentas, jcbEntregarVentas, jcbComprobarVentas, jcbAcuseVentas, jcbObtenerXmlVentas, jcbFacturarVentas, jcbArchivoVentas, jcbCargarArchivoVentas, jcbBorrarArchivoVentas, jcbVentasPermiso));
        hijos.add(insertaHijos( jcbNuevaCotiza, jcbAbrirCotiza, jcbVerCotiza, jcbCancelarCotiza, jcbReenviarCotiza, jcbVentaCotiza, jcbCotizaPermiso));
            
        hijos.add(insertaHijos(jcbUsuarios, jcbDefinirUsr, jcbUsrConectados, jcbPermisosUsr));
        hijos.add(insertaHijos(jcbReparar, jcbReparador, jcbRestaurar));
        hijos.add(insertaHijos(jcbConexionesBD, jcbArchivoConf));
        hijos.add(insertaHijos(jcbRepUsuarios, jcbRepRespaldos, jcbRepLog, jcbRepEstadisticas));
        hijos.add(insertaHijos(jcbCargarArchivoCompra, jcbBorrarArchivoCompra));
        hijos.add(insertaHijos(jcbNuevoProd, jcbModificarProd, jcbBorrarProd));
        hijos.add(insertaHijos(jcbCargarArchivoVentas, jcbBorrarArchivoVentas));
            
            
    }

    private ArrayList<JCheckBox> insertaHijos(JCheckBox... hijosSinPadre) {
        java.util.ArrayList<JCheckBox> hijosAux = new java.util.ArrayList<>();
        hijosAux.addAll(Arrays.asList(hijosSinPadre));
        
        return hijosAux;
    }

    private void desHabilita(JCheckBox papa, java.util.ArrayList<JCheckBox> hijos) {
        papa.setEnabled(false);
        for(JCheckBox hijo:hijos)
            hijo.setEnabled(false);
    }

    //Habilita todos los campos, en caso de que se hayan deshabilitado al revisar los otorga permisos
    private void habilitaCampos() {
        for(java.util.ArrayList<JCheckBox> hijosAux: hijos)
            for(JCheckBox hijo: hijosAux)
                if(!hijo.isSelected())
                    hijo.setSelected(true);
            
    }

    

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
