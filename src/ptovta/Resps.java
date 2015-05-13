//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;




/*Clase para controlar los respaldos*/
public class Resps extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Declara variables de instancia*/    
    private String                  sHOri;
    private int                     iContCellEd;
    private int                     iContFi;
    
    /*Declara variables origianles*/
    private String                  sEstacOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean                 bSel;
    
    
    
    
    /*Constructor sin argumentos*/
    public Resps() 
    {                                       
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Obtiene todas las confgral por usuarios y hrs de la base de datos y cargalos en la tabla de usuarios*/
        CargConfig();
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Respaldos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Establece el tamaño de las columnas de la tabla de estacs*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo de ruta de mysql bin*/
        jTBin.grabFocus();
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todas las rutas del usuario actual*/
        try
        {
            sQ = "SELECT rutmysq, res1path, res2path, res3path FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca los vaores en los controles */
            if(rs.next())
            {
                jTBin.setText   (rs.getString("rutmysq"));                                                
                jTResp1.setText (rs.getString("res1path"));                                                
                jTResp2.setText (rs.getString("res2path"));                                                
                jTResp3.setText (rs.getString("res3path"));                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                      
        }      
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para la tabla de usuarios, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Obtiene la fila seleccionada*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtén algunos datos originales*/                        
                        sEstacOri               = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sHOri                   = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                                                  

                        /*Aumenta el contador del celleditor*/
                        ++iContCellEd;      
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {                        
                        /*Establece el valor original para el usuario*/
                        jTab.getModel().setValueAt(sEstacOri,   jTab.getSelectedRow(), 1);
                        jTab.getModel().setValueAt(sHOri,       jTab.getSelectedRow(), 2);                                                                            
                        
                        /*Restablece el contador del celleditor*/
                        iContCellEd             = 1;
                    }                                                                                                                                                                  
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de usuarios*/
        jTab.addPropertyChangeListener(pr);
        
    }/*Fin de public Rutas() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTBin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTResp1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTResp2 = new javax.swing.JTextField();
        jBRespMan = new javax.swing.JButton();
        jBImpResp = new javax.swing.JButton();
        jBBin = new javax.swing.JButton();
        jBResp1 = new javax.swing.JButton();
        jBResp2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTHrs = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBNew = new javax.swing.JButton();
        jTUsr = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTResp3 = new javax.swing.JTextField();
        jBResp3 = new javax.swing.JButton();
        jBGuar = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBManArch = new javax.swing.JButton();
        jBLog = new javax.swing.JButton();
        jBImpBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
                formKeyPressed(evt);
            }
        });

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Ruta de respaldo 3:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 380, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jBLog);
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
                jBSalKeyPressed(evt);
            }
        });
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 140, 30));

        jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBin.setNextFocusableComponent(jBBin);
        jTBin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBinFocusLost(evt);
            }
        });
        jTBin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBinKeyPressed(evt);
            }
        });
        jP1.add(jTBin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 480, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Ruta de MySQL bin:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 380, -1));

        jTResp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTResp1.setNextFocusableComponent(jBResp1);
        jTResp1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTResp1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTResp1FocusLost(evt);
            }
        });
        jTResp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTResp1KeyPressed(evt);
            }
        });
        jP1.add(jTResp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 480, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Ruta de respaldo 1:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 380, -1));

        jTResp2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTResp2.setNextFocusableComponent(jBResp2);
        jTResp2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTResp2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTResp2FocusLost(evt);
            }
        });
        jTResp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTResp2KeyPressed(evt);
            }
        });
        jP1.add(jTResp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 480, 20));

        jBRespMan.setBackground(new java.awt.Color(255, 255, 255));
        jBRespMan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRespMan.setForeground(new java.awt.Color(0, 102, 0));
        jBRespMan.setText("Exportar BD");
        jBRespMan.setToolTipText("Generar un Respaldo Manual exclusivamente de la Base de Datos en este Momento");
        jBRespMan.setNextFocusableComponent(jBImpResp);
        jBRespMan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRespManMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRespManMouseExited(evt);
            }
        });
        jBRespMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRespManActionPerformed(evt);
            }
        });
        jBRespMan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRespManKeyPressed(evt);
            }
        });
        jP1.add(jBRespMan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, 140, 30));

        jBImpResp.setBackground(new java.awt.Color(255, 255, 255));
        jBImpResp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImpResp.setForeground(new java.awt.Color(0, 102, 0));
        jBImpResp.setText("Importar Archivos");
        jBImpResp.setToolTipText("Importar Respaldo");
        jBImpResp.setName(""); // NOI18N
        jBImpResp.setNextFocusableComponent(jBImpBD);
        jBImpResp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpRespMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpRespMouseExited(evt);
            }
        });
        jBImpResp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpRespActionPerformed(evt);
            }
        });
        jBImpResp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpRespKeyPressed(evt);
            }
        });
        jP1.add(jBImpResp, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 140, 30));

        jBBin.setBackground(new java.awt.Color(255, 255, 255));
        jBBin.setText("...");
        jBBin.setToolTipText("Buscar Ruta");
        jBBin.setNextFocusableComponent(jTResp1);
        jBBin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBinMouseExited(evt);
            }
        });
        jBBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBinActionPerformed(evt);
            }
        });
        jBBin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBinKeyPressed(evt);
            }
        });
        jP1.add(jBBin, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 40, 20));

        jBResp1.setBackground(new java.awt.Color(255, 255, 255));
        jBResp1.setText("...");
        jBResp1.setToolTipText("Buscar Ruta");
        jBResp1.setNextFocusableComponent(jTResp2);
        jBResp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBResp1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBResp1MouseExited(evt);
            }
        });
        jBResp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResp1ActionPerformed(evt);
            }
        });
        jBResp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBResp1KeyPressed(evt);
            }
        });
        jP1.add(jBResp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 40, 20));

        jBResp2.setBackground(new java.awt.Color(255, 255, 255));
        jBResp2.setText("...");
        jBResp2.setToolTipText("Buscar Ruta");
        jBResp2.setNextFocusableComponent(jTResp3);
        jBResp2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBResp2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBResp2MouseExited(evt);
            }
        });
        jBResp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResp2ActionPerformed(evt);
            }
        });
        jBResp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBResp2KeyPressed(evt);
            }
        });
        jP1.add(jBResp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 40, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("*Usuario:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Horas:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 60, -1));

        jTHrs.setText("1");
        jTHrs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTHrs.setNextFocusableComponent(jBNew);
        jTHrs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTHrsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTHrsFocusLost(evt);
            }
        });
        jTHrs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTHrsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTHrsKeyTyped(evt);
            }
        });
        jP1.add(jTHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 50, 20));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Horas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBGuar);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 470, 230));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Agregar Nuevo Usuario para Respaldo (Ctrl+N)");
        jBNew.setNextFocusableComponent(jBDel);
        jBNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewMouseExited(evt);
            }
        });
        jBNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewActionPerformed(evt);
            }
        });
        jBNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewKeyPressed(evt);
            }
        });
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 100, 20));

        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jBUsr);
        jTUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrFocusLost(evt);
            }
        });
        jTUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUsrKeyTyped(evt);
            }
        });
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 200, 20));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Usuario(s)");
        jBUsr.setNextFocusableComponent(jTHrs);
        jBUsr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUsrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUsrMouseExited(evt);
            }
        });
        jBUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsrActionPerformed(evt);
            }
        });
        jBUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUsrKeyPressed(evt);
            }
        });
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 30, 20));

        jBTab1.setBackground(new java.awt.Color(0, 153, 153));
        jBTab1.setToolTipText("Mostrar Tabla en Grande");
        jBTab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTab1ActionPerformed(evt);
            }
        });
        jBTab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTab1KeyPressed(evt);
            }
        });
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 480, 130, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jTab);
        jBTod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTodMouseExited(evt);
            }
        });
        jBTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTodActionPerformed(evt);
            }
        });
        jBTod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTodKeyPressed(evt);
            }
        });
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 232, 130, 18));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Ruta de respaldo 2:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 380, -1));

        jTResp3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTResp3.setNextFocusableComponent(jBResp3);
        jTResp3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTResp3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTResp3FocusLost(evt);
            }
        });
        jTResp3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTResp3KeyPressed(evt);
            }
        });
        jP1.add(jTResp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 480, 20));

        jBResp3.setBackground(new java.awt.Color(255, 255, 255));
        jBResp3.setText("Buscar Ruta");
        jBResp3.setNextFocusableComponent(jTUsr);
        jBResp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBResp3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBResp3MouseExited(evt);
            }
        });
        jBResp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResp3ActionPerformed(evt);
            }
        });
        jBResp3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBResp3KeyPressed(evt);
            }
        });
        jP1.add(jBResp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 40, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar Cambios (Ctrl+G)");
        jBGuar.setNextFocusableComponent(jBManArch);
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
                jBGuarKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 140, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Partida(s) (Ctrl+SUPR)");
        jBDel.setNextFocusableComponent(jTab);
        jBDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelMouseExited(evt);
            }
        });
        jBDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelActionPerformed(evt);
            }
        });
        jBDel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelKeyPressed(evt);
            }
        });
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 100, 20));

        jBManArch.setBackground(new java.awt.Color(255, 255, 255));
        jBManArch.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBManArch.setForeground(new java.awt.Color(0, 102, 0));
        jBManArch.setText("Exportar Archivos");
        jBManArch.setToolTipText("Generar Respaldo Manual de Archivos");
        jBManArch.setNextFocusableComponent(jBRespMan);
        jBManArch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBManArchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBManArchMouseExited(evt);
            }
        });
        jBManArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBManArchActionPerformed(evt);
            }
        });
        jBManArch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBManArchKeyPressed(evt);
            }
        });
        jP1.add(jBManArch, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 140, 30));

        jBLog.setBackground(new java.awt.Color(255, 255, 255));
        jBLog.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLog.setForeground(new java.awt.Color(0, 102, 0));
        jBLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logresp.png"))); // NOI18N
        jBLog.setText("Respaldos");
        jBLog.setToolTipText("Ver LOG de Respaldos");
        jBLog.setNextFocusableComponent(jTBin);
        jBLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLogMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLogMouseExited(evt);
            }
        });
        jBLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLogActionPerformed(evt);
            }
        });
        jBLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLogKeyPressed(evt);
            }
        });
        jP1.add(jBLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, 140, 30));

        jBImpBD.setBackground(new java.awt.Color(255, 255, 255));
        jBImpBD.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImpBD.setForeground(new java.awt.Color(0, 102, 0));
        jBImpBD.setText("Importar BD");
        jBImpBD.setNextFocusableComponent(jBSal);
        jBImpBD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpBDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpBDMouseExited(evt);
            }
        });
        jBImpBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpBDActionPerformed(evt);
            }
        });
        jBImpBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpBDKeyPressed(evt);
            }
        });
        jP1.add(jBImpBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona el botón de salir*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cerrar el formulario*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTBinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBinKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBinKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de ruta bin*/
    private void jTBinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBinFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBin.setSelectionStart(0);jTBin.setSelectionEnd(jTBin.getText().length());                
        
    }//GEN-LAST:event_jTBinFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de almacén*/
    private void jTBinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBinFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBin.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBin.getText().compareTo("")!=0)
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTBin.getText().length()> 255)
            jTBin.setText(jTBin.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTBinFocusLost
        
    
    /*Cuando se gana el foco del teclado en el campo de respaldo 1*/
    private void jTResp1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTResp1.setSelectionStart(0);jTResp1.setSelectionEnd(jTResp1.getText().length());                
        
    }//GEN-LAST:event_jTResp1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de respaldo 2*/
    private void jTResp2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTResp2.setSelectionStart(0);jTResp2.setSelectionEnd(jTResp2.getText().length());                
        
    }//GEN-LAST:event_jTResp2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de respaldo local*/
    private void jTResp1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTResp1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTResp1.getText().length()> 255)
            jTResp1.setText(jTResp1.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTResp1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de respaldo en red*/
    private void jTResp2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTResp2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTResp2.getText().length()> 255)
            jTResp2.setText(jTResp2.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTResp2FocusLost

    
    /*Cuando se presiona una tecla en el campo de respaldo local*/
    private void jTResp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResp1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTResp1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de reslpado en red*/
    private void jTResp2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResp2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTResp2KeyPressed

    
    /*Cuando se presiona el botón de respaldo manual*/
    private void jBRespManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRespManActionPerformed
                
        /*Si no se a definido la ruta para el bin entonces*/
        if(jTBin.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Define primeramente la ruta al directorio bin de Mysql.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }
        
        /*Si el exe de mysql no existe en la ruta entonces esta mal definida la ruta*/
        if(!new File(jTBin.getText() + "\\mysql.exe").exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No existe el servidor Mysql.exe en la ruta específicada. Específica la ruta correcta.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }
                
        /*Configura el fil chooser para escoger la rut a donde se guardara el respaldo*/
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Guardar Respaldo Manualmente");                
        
        /*Muestra el file choooser*/
        int retVal = fc.showSaveDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la ruta seleccionada*/
            String sRut        = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Lee la ruta al BIN*/
            String sRutBin      = jTBin.getText();
            
            /*Substring para obtener la unidad con todo y la diagonal invertida en las dos rutas*/
            String sUnid       = sRut.substring     (0, 3);
            String sUnidBin    = sRutBin.substring  (0, 3);

            /*Substring la ruta para ponerles comillas dobles a todos los nombres de archivos para que el bat corra correctamente a las dos rutas*/
            sRut        = sRut.substring    (3, sRut.length());
            sRutBin     = sRutBin.substring (3, sRutBin.length());

            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta*/                                                                
            StringTokenizer st1 = new StringTokenizer(sRut,"\\");
            sRut = "\"";
            while(st1.hasMoreTokens())
                sRut += st1.nextToken() + "\"\\\"";

            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta bin*/                                                                
            st1 = new StringTokenizer(sRutBin,"\\");
            sRutBin = "\"";
            while(st1.hasMoreTokens())
                sRutBin += st1.nextToken() + "\"\\\"";
            
            /*Quita la última diagonal invertida y dobles comillas de las dos rutas*/
            sRut    = sRut.substring    (0, sRut.length() - 2);
            sRutBin = sRutBin.substring (0, sRutBin.length() - 2);

            /*Junta la unidad con las dos rutas nuevamente*/
            sRut    = sUnid + sRut;
            sRutBin = sUnidBin + sRutBin;
            
            /*Concatena el nombre del archivo a la ruta*/
            sRut    += "\\" + fc.getSelectedFile().getName() + ".sql";     
                                    
            /*Crea la cadena completa que ejecutara la bases de datos*/
            String sCadComp = sRutBin + "\\mysqldump --user=" + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " > " + sRut;
            
            /*Borra el archivo bat si es que existe*/
            if(new File("resp.bat").exists())            
                new File("resp.bat").delete();
            
            /*Crealo*/
            File fil = new File("resp.bat");
            try 
            {
                fil.createNewFile();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;                                                                                                          
            }
            
            /*Escribe en el la cadena que ejecutara la base de datos por consola para realizar el respaldo*/
            FileWriter fw;
            try 
            {
                fw = new FileWriter(fil.getAbsoluteFile());
                BufferedWriter bw;
                bw = new BufferedWriter(fw);
                bw.write(sCadComp);
                bw.close();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;                                                                                          
            }

            /*Declara variables final para el thread*/
            final String sRutFi     = sRut;
            final String sRutDFi    = jTBin.getText().replace("\\", "\\\\");
            
            //Muestra el loading
            Star.vMostLoading("");
                        
            /*Exporta la base de datos en un thread*/
            (new Thread()
            {
                @Override
                public void run()
                {               
                    //Abre la base de datos
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    //Declara variables de la base de datos
                    Statement   st;                                
                    String      sQ; 

                    /*Corre el archivo bat*/
                    try 
                    {                             
                        /*Crea el objeto para hacer el respaldo*/
                        Process p = Runtime.getRuntime().exec("cmd /c start /b resp.bat");

                        /*Intenta esperar el proceso de respaldo por consola*/
                        int iE  = -1;
                        try
                        {
                            iE = p.waitFor();
                        }
                        catch(InterruptedException expnInterru)
                        {
                            /*Inserta en log que algo ha ido mal*/
                            try 
                            {                
                                sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                   pathamysq,                                                      estac,                                      sucu,                                     nocaj,                                        msj,                                                      `return`) " + 
                                                    "VALUES(1, '" +   sRutDFi.replace("'", "''") + "', '" +         sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnInterru.getMessage().replace("'", "''") + "', " +     iE + ")";                    
                                st = con.createStatement();
                                st.executeUpdate(sQ);
                             }
                             catch(SQLException expnSQL) 
                             { 
                                //Procesa el error y regresa
                                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                                return;                                                                                                                          
                            }    

                            /*Agrega enel log*/
                            Login.vLog(expnInterru.getMessage());

                            /*Mensajea y regresa*/
                            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en waitfor() por " + expnInterru.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                            return;
                        }    

                        /*Inserta en log que todo fue bien*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathdemysq,                               pathamysq,                                                          estac,                                          sucu,                                               nocaj,                                              `return`) " + 
                                                "VALUES(1, '" +   sRutDFi.replace("'", "''") + "', '" +     sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +          Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', " +      iE + ")";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                         }    
                    } 
                    catch(IOException expnIO) 
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                        return;                                                                                          
                    }

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;

                    /*Borra el archivo bat*/            
                    new File("resp.bat").delete();            
                    
                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                    
                }/*Fin de public void run()*/
            }).start();
            
        }/*Fin de if(retVal == JFileChooser.APPROVE_OPTION) */                                    
        
    }//GEN-LAST:event_jBRespManActionPerformed

    
    /*Cuando se presiona el botón de buscar rut bin*/
    private void jBBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBinActionPerformed
        
        /*Configura el file chooser para escogerl la ruta del directorio bin de mysql*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Direcotorio bin de MySQL");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        
        /*Muestra el fil choooser*/
        int retVal = fc.showOpenDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        String sRutMsj;
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la rut seleccionada*/
            sRut        = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Concatena la carpeta final seleccionada*/
            sRut        += "\\" + fc.getSelectedFile().getName();                        
            
            /*Almacena la ruta para los mensajes de éxito*/
            sRutMsj     = sRut;
                        
            /*Coloca la ruta en el campo de edición*/
            jTBin.setText(sRutMsj);                                    
        }
        
    }//GEN-LAST:event_jBBinActionPerformed

    
    /*Cuando se presiona una tecla en el botón de importar respaldo*/
    private void jBImpRespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpRespKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBImpRespKeyPressed

    
    /*Cuando se presiona una tecla en el botón de respaldo manual*/
    private void jBRespManKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRespManKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRespManKeyPressed

    
    /*Cuando se presiona una tecla en el botón de buscar rut bin*/
    private void jBBinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBinKeyPressed

    
    /*Cuando se presiona una tecla en el botón de buscar local*/
    private void jBResp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBResp1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBResp1KeyPressed

    
    /*Cuando se presiona el botón de buscar local*/
    private void jBResp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBResp1ActionPerformed
        
        /*Configura el file chooser para escogerl la ruta del directorio bin de mysql*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Direcotorio de Respaldo 1");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        
        /*Muestra el fil choooser*/
        int retVal = fc.showOpenDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        String sRutMsj;
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la rut seleccionada*/
            sRut        = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Concatena la carpeta final seleccionada*/
            sRut        += "\\" + fc.getSelectedFile().getName();                        
            
            /*Almacena la ruta para los mensajes de éxito*/
            sRutMsj     = sRut;
                        
            /*Coloca la ruta en el campo de edición*/
            jTResp1.setText(sRutMsj);                                    
        }
                
    }//GEN-LAST:event_jBResp1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar en red*/
    private void jBResp2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBResp2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBResp2KeyPressed

    
    /*Cuando se presiona el botón de búscar ruta para respaldo 2*/
    private void jBResp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBResp2ActionPerformed
        
        /*Configura el file chooser para escogerl la ruta del directorio bin de mysql*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Direcotorio de respaldo 2");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        
        /*Muestra el fil choooser*/
        int retVal = fc.showOpenDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        String sRutMsj;
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la rut seleccionada*/
            sRut        = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Concatena la carpeta final seleccionada*/
            sRut        += "\\" + fc.getSelectedFile().getName();                        
            
            /*Almacena la ruta para los mensajes de éxito*/
            sRutMsj     = sRut;
                        
            /*Coloca la ruta en el campo de edición*/
            jTResp2.setText(sRutMsj);                                    
        }
        
    }//GEN-LAST:event_jBResp2ActionPerformed

    
    /*Cuando se presiona el botón de importar respaldo*/
    private void jBImpRespActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpRespActionPerformed
        
        /*Si no se a definido la ruta para el bin entonces*/
        if(jTBin.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Define primeramente la ruta al directorio bin de Mysql.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }
        
        /*Si el exe de mysql no existe en la ruta entonces esta mal definida la ruta*/
        if(!new File(jTBin.getText() + "\\mysql.exe").exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No existe el servidor Mysql.exe en la ruta específicada. Específica la ruta correcta.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }

        /*Configura el fil chooser para escoger la rut a donde se guardara el respaldo*/
        final JFileChooser fc = new JFileChooser();        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Importar Respaldo Archivos");                        
        
        /*Muestra el file choooser*/
        int retVal = fc.showSaveDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la ruta seleccionada con el nombre de la carpeta que escribió*/
            String sRut        = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();
            
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
            String sCarp    = Star.sGetRutCarp(con);                    

            //Si hubo error entonces regresa
            if(sCarp==null)
                return;

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Declara variables fianal para el thread*/
            final String sRutCarFi  = sCarp;
            final String sRutFi     = sRut;
            
            //Muestra el loading
            Star.vMostLoading("");
            
            /*Copia el carpeta orgien al destino en un thread*/
            (new Thread()
            {
                @Override
                public void run()
                {               
                    //Abre la base de datos
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;
        
                    //Declara variables de la base de datos    
                    Statement   st;                                        
                    String      sQ;

                    /*Copia todo*/
                    try
                    {
                        /*Intenta copiar todo*/
                        org.apache.commons.io.FileUtils.copyDirectory(new File(sRutFi), new File(sRutCarFi));

                        /*Inserta en log que todo se copio con éxito*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathde,                                                       patha,                                                          estac,                                          sucu,                                           nocaj) " + 
                                                "VALUES(0, '" +   sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRutCarFi.replace("\\", "\\\\").replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                         }    
                    }
                    catch(IOException expnIO)
                    {
                        /*Inserta en log de Error por: el cuál no se pudo copiar*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathde,                                                       patha,                                                              estac,                                       sucu,                                      nocaj,                                         msj) " + 
                                                "VALUES(0, '" +   sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRutCarFi.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +    Star.sSucu.replace("'", "''") + "', '" +   Star.sNoCaj.replace("'", "''") + "', '" +      expnIO.getMessage().replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                        }    

                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                        return;                                                                                          
                    }

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                        
                }/*Fin de public void run()*/
            }).start();
            
        }/*Fin de if(retVal == JFileChooser.APPROVE_OPTION) */                                    
                
    }//GEN-LAST:event_jBImpRespActionPerformed

    
    /*Obtiene todas las configuraciones por usuario y horas de la base de datos y cargalos en la tabla de usuarios*/
    private void CargConfig()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los datos de la base de datos y cargalos en la tabla*/        
        try
        {
            sQ = "SELECT estacres, hrs FROM resp";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("estacres"), rs.getString("hrs")};
                tm.addRow(nu);
                                
                /*Aumentar en uno el contador de filas*/
                ++iContFi;                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                      
        }            
                     
        //Cierra la base de datos
        Star.iCierrBas(con);                 
                       
    }/*Fin de private void CargaConfiguracionesEnTabla()*/
        
    
    /*Cuando se gana el foco del teclado en el campo de las hrs*/
    private void jTHrsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTHrsFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTHrs.setSelectionStart(0);jTHrs.setSelectionEnd(jTHrs.getText().length());        

    }//GEN-LAST:event_jTHrsFocusGained

    
    /*Cuando se presiona una tecla en el campo de hrs*/
    private void jTHrsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTHrsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTHrsKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de hrs*/
    private void jTHrsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTHrsFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTHrs.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTHrs.getText().compareTo("")!=0)
            jTHrs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTHrs.getText();
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sTex.length()> 20)
            jTHrs.setText(jTHrs.getText().substring(0, 20));
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException exnNumForm)  
        {  
            jTHrs.setText("");
            return;
        }        
        
        /*Si el valor introducido es menor a 0 o mayor a 10 entonces*/
        if(Integer.parseInt(sTex)<= 0 || Integer.parseInt(sTex) > 10)                
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La hora debe ser mayor a 0 y menor a 10.", "Hora Inválida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Coloca el foco del teclado en el control*/
            jTHrs.grabFocus();
            
            /*Coloca en el campo cadena vacia*/
            jTHrs.setText("");
        }
        
    }//GEN-LAST:event_jTHrsFocusLost

    
    /*Cuando se presiona una tecla en el campo de usuarios*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                
        /*Si hay cadena vacia en el campo de usuario no puede continuar*/
        if(jTUsr.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();
            return;

        }

        /*Si hay cadena vacia en el campo de horas no puede continuar*/
        if(jTHrs.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTHrs.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de horas esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición*/
            jTHrs.grabFocus();         
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el usuario existe en la base de datos*/
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario: " + jTUsr.getText() + " no existe. Ingresa un usuario existente.", "Usuario", JOptionPane.INFORMATION_MESSAGE, null); 
                
                /*Coloca el borde rojo*/                               
                jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Pon el foco el teclado en el control y regresa*/
                jTUsr.grabFocus();
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                  
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;

        /*Recorre la tabla de los usuarios*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si el usuario que se quiere insertar ya existe en la tabla entonces*/
            if(jTab.getValueAt(x, 1).toString().compareTo(jTUsr.getText())==0)
            {
                /*Coloca el borde rojo*/                               
                jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "El código de El usuario: " + jTUsr.getText() + " ya existe para respaldo.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo del código de El usuario y regresa*/
                jTUsr.grabFocus();               
                return;
            }                           
        }
                            
        /*Agrega el registro nu en la tabla*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTUsr.getText(), jTHrs.getText()};
        tm.addRow(nu);

        /*Aumenta en uno el contador de filas en 1*/
        ++iContFi;

        /*Pon el foco del teclado en el campo de usuarios*/
        jTUsr.grabFocus();
       
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    
    
    /*Cuando se tipea una tecla en el campo de hrs*/
    private void jTHrsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTHrsKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
        /*Si el valor es mayor al permitido entonces*/
        try
        {
           Integer.parseInt(jTHrs.getText()); 
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Número de horas no válido.", "Horas", JOptionPane.INFORMATION_MESSAGE, null);                         
            jTHrs.grabFocus();            
        }               
        
    }//GEN-LAST:event_jTHrsKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());
        
    }//GEN-LAST:event_jTUsrFocusGained
       
    
    /*Cuando se presiona una tecla en el botón de usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se tipea una tecla en el campo de usuarios*/
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped
               
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUsrKeyTyped

    
    /*Cuando se arrastra el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de ver tabla*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
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

    
    /*Cuando se presiona el botón de seleccionar todo*/
    private void jBTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTodActionPerformed

        /*Si la tabla no contiene elementos entonces regresa*/
        if(jTab.getRowCount()==0)
            return;
        
        /*Si están seleccionados los elementos en la tabla entonces*/
        if(bSel)
        {
            /*Coloca la bandera y deseleccionalos*/
            bSel    = false;
            jTab.clearSelection();
        }
        /*Else deseleccionalos y coloca la bandera*/
        else
        {
            bSel    = true;
            jTab.setRowSelectionInterval(0, jTab.getModel().getRowCount()-1);
        }

    }//GEN-LAST:event_jBTodActionPerformed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTodKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control de usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBinMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBin.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBinMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBResp1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBResp1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBResp1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBResp2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBResp2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBResp2MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBRespManMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRespManMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRespMan.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRespManMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpRespMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpRespMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImpResp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpRespMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered


    /*Cuando el mouse sale del botón específico*/
    private void jBBinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBinMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBin.setBackground(colOri);
        
    }//GEN-LAST:event_jBBinMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBResp1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBResp1.setBackground(colOri);
        
    }//GEN-LAST:event_jBResp1MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBResp2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBResp2.setBackground(colOri);
        
    }//GEN-LAST:event_jBResp2MouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUsrMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBRespManMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRespManMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRespMan.setBackground(colOri);
        
    }//GEN-LAST:event_jBRespManMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpRespMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpRespMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImpResp.setBackground(colOri);
        
    }//GEN-LAST:event_jBImpRespMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se gana el foco del teclado en el campo de respaldo 3*/
    private void jTResp3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTResp1.setSelectionStart(0);jTResp1.setSelectionEnd(jTResp1.getText().length());        
        
    }//GEN-LAST:event_jTResp3FocusGained

    
    /*Cuando se presiona una tecla en el campo del respaldo 3*/
    private void jTResp3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResp3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTResp3KeyPressed

    
    /*Cuando se presiona una tecla en el botón de respaldo 3*/
    private void jBResp3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBResp3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBResp3KeyPressed

    
    /*Cuando se presiona el botón de respaldo 3*/
    private void jBResp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBResp3ActionPerformed
        
        /*Configura el file chooser para escogerl la ruta del directorio bin de mysql*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Direcotorio de Respaldo 3");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        
        /*Muestra el fil choooser*/
        int retVal = fc.showOpenDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        String sRutMsj;
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la rut seleccionada*/
            sRut        = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Concatena la carpeta final seleccionada*/
            sRut        += "\\" + fc.getSelectedFile().getName();                        
            
            /*Almacena la ruta para los mensajes de éxito*/
            sRutMsj     = sRut;
                        
            /*Coloca la ruta en el campo de edición*/
            jTResp3.setText(sRutMsj);                                    
        }
        
    }//GEN-LAST:event_jBResp3ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar ruta de respaldo 3*/
    private void jBResp3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp3MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBResp3.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBResp3MouseEntered

    
    /*Cuando el mouse sale del botón de búscar ruta para respaldos 3*/
    private void jBResp3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBResp3MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBResp3.setBackground(colOri);
        
    }//GEN-LAST:event_jBResp3MouseExited

    
    /*Cuando el mouse sale del botón de guardar*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse entra en el botón de guardar cambios*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Preguntar al usuario si esta seguro de querer guardar los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere guardar los datos?", "Guardar Datos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, true);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;               
        String      sQ; 
        
        /*Actualiza la ruta des respaldos y bin del usuario actual*/
        try 
        {                
            sQ = "UPDATE estacs SET "
                    + "rutmysq      = '" + jTBin.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "res1path     = '" + jTResp1.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "res2path     = '" + jTResp2.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "res3path     = '" + jTResp3.getText().replace("\\", "\\\\").replace("'", "''") + "' "
                    + "WHERE estac  = '" + Login.sUsrG.replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                  
        }    
        
        /*Borra toda la tabla de respaldos*/
        try 
        {                
            sQ = "DELETE FROM resp";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                  
        }    
        
        /*Recorre la tabla de usuarios*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Inserta en la base de datos los usuarios permitidos para respaldar*/
            try 
            {                
                sQ = "INSERT INTO resp (hrs,                                            estacres,                                                       estac,                                          sucu,                                           nocaj) " + 
                           "VALUES('" + jTab.getValueAt(x, 2).toString() + "', '" +     jTab.getValueAt(x, 1).toString().replace("'", "''") + "', '" +  Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                          
            }                
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;

        /*Mensajea y cierra la forma*/
        JOptionPane.showMessageDialog(null, "Cambios guardados con éxito.", "Respaldos", JOptionPane.INFORMATION_MESSAGE, null); 
        dispose();
        
        /*Llama al recolector de basura*/
        System.gc();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando el mouse entra en el botón de borrar partida*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse sale del botón de borrar partidas*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);

    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow() == -1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un registro de la tabla para borrar.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla de partidas y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borralo de la tabla*/
            tm.removeRow(iSel[x]);

            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        }        

    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el botón de generar un respaldo manual de los archivos*/
    private void jBManArchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBManArchKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBManArchKeyPressed

    
    /*Cuando el mouse entra en el botón de respaldo manual de archivos*/
    private void jBManArchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBManArchMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBManArch.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBManArchMouseEntered

    
    /*Cuando el mouse sale del botón de generar respaldo manual de archivos*/
    private void jBManArchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBManArchMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBManArch.setBackground(colOri);
        
    }//GEN-LAST:event_jBManArchMouseExited

    
    /*Cuando se presiona el botón de respaldo manual de ficheros*/
    private void jBManArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBManArchActionPerformed
        
        /*Si no se a definido la ruta para el bin entonces*/
        if(jTBin.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Define primeramente la ruta al directorio bin de Mysql.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }
        
        /*Si el exe de mysql no existe en la ruta entonces esta mal definida la ruta*/
        if(!new File(jTBin.getText() + "\\mysql.exe").exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No existe el servidor Mysql.exe en la ruta específicada. Específica la ruta correcta.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null); 
            
            /*Coloca el borde rojo*/                               
            jTBin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTBin.grabFocus();
            return;
        }

        /*Configura el fil chooser para escoger la rut a donde se guardara el respaldo*/
        final JFileChooser fc = new JFileChooser();        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Exportar Respaldo Archivos");                        
        
        /*Muestra el file choooser*/
        int retVal = fc.showSaveDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {
            /*Lee la ruta seleccionada con el nombre de la carpeta que escribió*/
            String sRut        = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();
            
            /*Agrega a la ruta seleccionada el nombre de la carpeta del sistema*/
            sRut                += "\\Easy Retail®";
            
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
            String sCarp    = Star.sGetRutCarp(con);                    

            //Si hubo error entonces regresa
            if(sCarp==null)
                return;

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Declara variables fianal para el thread*/
            final String sRutCarFi  = sCarp;
            final String sRutFi     = sRut;
            
            //Muestra el loading
            Star.vMostLoading("");
            
            /*Copia el carpeta orgien al destino en un thread*/
            (new Thread()
            {
                @Override
                public void run()
                {               
                    //Abre la base de datos
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    //Declara variables de la base de datos    
                    Statement   st;                                        
                    String      sQ;

                    /*Copia todo*/
                    try
                    {
                        /*Intenta copiar todo*/
                        org.apache.commons.io.FileUtils.copyDirectory(new File(sRutCarFi), new File(sRutFi));

                        /*Inserta en log que todo se copio con éxito*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathde,                                                           patha,                                                          estac,                                          sucu,                                           nocaj) " + 
                                                "VALUES(1, '" +   sRutCarFi.replace("\\", "\\\\").replace("'", "''") + "', '" +     sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                    
                         }    
                    }
                    catch(IOException expnIO)
                    {
                        /*Inserta en log de Error por: el cuál no se pudo copiar*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathde,                                                           patha,                                                          estac,                                      sucu,                                     nocaj,                                        msj) " + 
                                                "VALUES(1, '" +   sRutCarFi.replace("\\", "\\\\").replace("'", "''") + "', '" +     sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnIO.getMessage().replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                        }    

                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                        return;                                                                                          
                    }

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                        
                }/*Fin de public void run()*/
                
            }).start();
            
        }/*Fin de if(retVal == JFileChooser.APPROVE_OPTION) */                                    
        
    }//GEN-LAST:event_jBManArchActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver log de respaldos*/
    private void jBLogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLogKeyPressed
               
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLogKeyPressed

    
    /*Cuando el mouse entra en el botón del log*/
    private void jBLogMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLogMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLog.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLogMouseEntered

    
    /*Cuando el mouse sale del botón de log*/
    private void jBLogMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLogMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLog.setBackground(colOri);
        
    }//GEN-LAST:event_jBLogMouseExited

    
    /*Cuando se presiona el botón de ver log de respaldos*/
    private void jBLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLogActionPerformed
        
        /*Muestra la forma para ver el log de respaldos*/
        LogResp l = new LogResp();
        l.setVisible(true);
        
    }//GEN-LAST:event_jBLogActionPerformed

    
    /*Cuando se presiona una tecla ene l botón de importar base de datos*/
    private void jBImpBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpBDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBImpBDKeyPressed

    
    /*Cuando el mouse entra en el botón de importar respaldo*/
    private void jBImpBDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpBDMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImpBD.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpBDMouseEntered

    
    /*Cuando se pierde el mouse del teclado en el botón de importar base de datos*/
    private void jBImpBDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpBDMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImpBD.setBackground(colOri);
        
    }//GEN-LAST:event_jBImpBDMouseExited

    
    /*Cuando se presiona el botón de importar base de datos*/
    private void jBImpBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpBDActionPerformed
        
        /*Configura el fil chooser para escoger la ruta de donde se importara el respaldo*/
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Importar Respaldo BD");                
        
        /*Muestra el fil choooser*/
        int retVal = fc.showOpenDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(retVal == JFileChooser.APPROVE_OPTION) 
        {            
            /*Lee la ruta seleccionada*/
            String sRut     = fc.getCurrentDirectory().getAbsolutePath();
            
            /*Leel a ruta al BIN*/
            String sRutBin  = jTBin.getText();
            
            /*Substring para obtener la unidad con todo y la diagonal invertida en las dos rutas*/
            String sUnid    = sRut.substring(0, 3);
            String sUnidBin = sRutBin.substring(0, 3);

            /*Substring la ruta para ponerles comillas dobles a todos los nombres de directorios para que el bat corra correctamente*/
            sRut    = sRut.substring(3, sRut.length());

            /*Substring la ruta bin para ponerles comillas dobles a todos los nombres de directorios para que el bat corra correctamente*/
            sRutBin = sRutBin.substring(3, sRutBin.length());
            
            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta*/                                                                
            StringTokenizer st1 = new StringTokenizer(sRut,"\\");
            sRut = "\"";
            while(st1.hasMoreTokens())
                sRut += st1.nextToken() + "\"\\\"";

            /*Tokeniza la cadena bin por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta*/                                                                
            st1 = new StringTokenizer(sRutBin,"\\");
            sRutBin = "\"";
            while(st1.hasMoreTokens())
                sRutBin += st1.nextToken() + "\"\\\"";
            
            /*Quita la última diagonal invertida y dobles comillas a las dos rutas*/
            sRut    = sRut.substring    (0, sRut.length() - 2);
            sRutBin = sRutBin.substring (0, sRutBin.length() - 2);

            /*Junta la unidad con la ruta en los dos casos*/
            sRut    = sUnid + sRut;
            sRutBin = sUnidBin + sRutBin;
            
            /*Concatena el nombre del archivo a la ruta*/
            sRut    += "\\" + fc.getSelectedFile().getName();     
                                    
            /*Crea la segunda parte de la cadena del bat*/
            String sCadComp = "mysql -u " + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " < " + sRut;
            
            /*Borra el archivo bat si es que existe*/
            if(new File("resp.bat").exists())
                new File("resp.bat").delete();
            
            /*Crealo*/
            File fil = new File("resp.bat");
            try 
            {
                fil.createNewFile();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;                                                                                          
            }
            
            /*Escribe en el la cadena que ejecutara la base de datos por consola para realizar la importación del respaldo*/
            FileWriter fw;
            try 
            {
                fw = new FileWriter(fil.getAbsoluteFile());
                BufferedWriter bw;
                bw = new BufferedWriter(fw);
                /*Escribe la primera parte del bat que cambia al diretorio de mysql*/
                bw.write("cd " + sRutBin);
                /*Escribe nueva línea ya que si no no lee bien las dos instrucciones*/
                bw.newLine();
                /*Escribe la segunda parte de la instrucción de mysql para importar la base de datos    */
                bw.write(sCadComp);
                bw.close();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;                                                                                                          
            }
           
            /*Crea variables final para el thread*/
            final String sRutFi     = sRut;
            final String sRutAFi    = jTBin.getText().replace("\\", "\\\\");
            
            //Muestra el loading
            Star.vMostLoading("");
            
            /*Importa la base de datos en un thread*/
            (new Thread()
            {
                @Override
                public void run()
                {               
                    //Abre la base de datos
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    //Declara variables de la base de datos
                    Statement   st;                                
                    String      sQ; 

                    /*Corre el archivo bat*/
                    try 
                    {                             
                        /*Crea el objeto para hacer el respaldo*/
                        Process p = Runtime.getRuntime().exec("cmd /c start /b resp.bat");

                        /*Intenta esperar el proceso de respaldo por consola*/
                        int iE  = -1;
                        try
                        {
                            iE = p.waitFor();
                        }
                        catch(InterruptedException expnInterru)
                        {
                            /*Inserta en log que algo ha ido mal*/
                            try 
                            {                
                                sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                           pathamysq,                              estac,                                      sucu,                                     nocaj,                                        msj,                                                    `return`) " + 
                                                    "VALUES(0, '" +   sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +            sRutAFi.replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnInterru.getMessage().replace("'", "''") + "', " +   iE + ")";                    
                                st = con.createStatement();
                                st.executeUpdate(sQ);
                             }
                             catch(SQLException expnSQL) 
                             { 
                                //Procesa el error y regresa
                                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                                return;                                                                                                                          
                            }    

                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnInterru.getMessage(), Star.sErrInterru, con);                                
                            return;                                                                                                                      
                        }    

                        /*Inserta en log que todo fue bien*/
                        try 
                        {                
                            sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                   pathamysq,                                      estac,                                          sucu,                                               nocaj,                                          `return`) " + 
                                                "VALUES(0, '" +   sRutFi.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRutAFi.replace("'", "''") + "', '" +           Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', " +  iE + ")";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                         }    
                    } 
                    catch(IOException expnIO) 
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                        return;                                                                                                                  
                    }

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;

                    /*Borra el archivo bat*/            
                    new File("resp.bat").delete();     

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                    
                }/*Fin de public void run()*/
                
            }).start();            
                                                                               
        }/*Fin de if(retVal == JFileChooser.APPROVE_OPTION) */                                                                       
        
    }//GEN-LAST:event_jBImpBDActionPerformed

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTResp3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResp3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTResp3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTResp3FocusLost

   
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F2 presiona el botón de importar respaldo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBImpResp.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBin;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBImpBD;
    private javax.swing.JButton jBImpResp;
    private javax.swing.JButton jBLog;
    private javax.swing.JButton jBManArch;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBResp1;
    private javax.swing.JButton jBResp2;
    private javax.swing.JButton jBResp3;
    private javax.swing.JButton jBRespMan;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBUsr;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBin;
    private javax.swing.JTextField jTHrs;
    private javax.swing.JTextField jTResp1;
    private javax.swing.JTextField jTResp2;
    private javax.swing.JTextField jTResp3;
    private javax.swing.JTextField jTUsr;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
