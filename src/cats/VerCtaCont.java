//Paquete
package cats;

//Importaciones
import java.awt.Cursor;
import java.awt.Color;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.Login;




/*Clase para controlar las cuentas de la interfaz de contabilidad*/
public class VerCtaCont extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables gloabales originales*/
    private String          sCtaOri;
    private String          sNomOri;
    private String          sTipOri;
    private String          sNivOri;
    private String          sAfecOri;
    
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
        
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Contiene los datos de conexión globales a la base de datos de contabilidad*/
    private String          sUsrG;
    private String          sInsG;
    private String          sContG;
    private String          sBDG;
    
    /*Contiene los datos de conexión al SKD de contabilidad*/
    private String          sUsrConta;
    private String          sNomConta;
    private String          sContraConta;
    
    
    
    /*Constructor sin argumentos*/
    public VerCtaCont(String sUsr, String sIns, String sCont, String sBD, String sNomCont, String sUsrCont, String sContraCont) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Crea el grupo de los radio buttons*/
        javax.swing.ButtonGroup bG = new javax.swing.ButtonGroup();
        bG.add(jRCli);
        bG.add(jRProv);        
        
        /*Obtiene los datos del otro formulario*/
        sUsrG       = sUsr;
        sInsG       = sIns;
        sContG      = sCont;
        sBDG        = sBD;
        sUsrConta   = sUsrCont;
        sNomConta   = sNomCont;
        sContraConta= sContraCont;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Cuentas interfaz contabilidad, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de la cuenta*/
        jTCta.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/       
        jTab.getColumnModel().getColumn(1).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(350);
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todas las cuentas de la base de datos de la interfaz de contabilidad y cargalos en la tabla*/
        vCargCtas();
            
        /*Crea el listener para la tabla, para cuando se modifique una celda colocarla nuevamente en su lugar*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {                    
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {                                                
                        /*Obtén algunos datos originales de la fila*/                        
                        sCtaOri     = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sNomOri     = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sTipOri     = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sNivOri     = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sAfecOri    = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores originales que tenía*/
                        jTab.setValueAt(sCtaOri,    jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sNomOri,    jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sTipOri,    jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sNivOri,    jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sAfecOri,   jTab.getSelectedRow(), 5);                        
                    }                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de pes*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Peso() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTCta = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jTNom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComTip = new javax.swing.JComboBox();
        jComNiv = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTCtaPad = new javax.swing.JTextField();
        jBPag = new javax.swing.JButton();
        jTPag = new javax.swing.JTextField();
        jRProv = new javax.swing.JRadioButton();
        jRCli = new javax.swing.JRadioButton();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("*Nivel:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 70, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTCta);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 120, 30));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jTNom);
        jTCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaFocusLost(evt);
            }
        });
        jTCta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCtaKeyTyped(evt);
            }
        });
        jP1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, 20));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Peso (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTCtaPad);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 90, 20));

        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jComTip);
        jTNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomFocusLost(evt);
            }
        });
        jTNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomKeyPressed(evt);
            }
        });
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 270, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("*Cuenta:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cuenta", "Nombre", "Tipo", "Nivel", "Afectable", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 620, 230));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/busc5.png"))); // NOI18N
        jBBusc.setText("Buscar F3");
        jBBusc.setNextFocusableComponent(jTBusc);
        jBBusc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscMouseExited(evt);
            }
        });
        jBBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscActionPerformed(evt);
            }
        });
        jBBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscKeyPressed(evt);
            }
        });
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosT);
        jTBusc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBuscFocusLost(evt);
            }
        });
        jTBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscKeyPressed(evt);
            }
        });
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 340, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBActua);
        jBMosT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMosTMouseExited(evt);
            }
        });
        jBMosT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMosTActionPerformed(evt);
            }
        });
        jBMosT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMosTKeyPressed(evt);
            }
        });
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 320, 140, 20));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBPag);
        jBActua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBActuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBActuaMouseExited(evt);
            }
        });
        jBActua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActuaActionPerformed(evt);
            }
        });
        jBActua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBActuaKeyPressed(evt);
            }
        });
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 120, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 330, 120, 30));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 72, 130, 18));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("*Nombre:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("*Tipo:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 60, -1));

        jComTip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo Deudora", "Activo Acredora", "Capital Deudora", "Capital Acredora", "Estadísticas Deudora", "Estadísticas Acredora", "Orden Deudora", "Orden Acredora", "Pasivo Deudora", "Pasivo Acredora", "Resultado Deudora", "Resultado Acredora" }));
        jComTip.setNextFocusableComponent(jComNiv);
        jComTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTipKeyPressed(evt);
            }
        });
        jP1.add(jComTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 120, 20));

        jComNiv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Afectable", "Mayor", "Título", "Subtítulo" }));
        jComNiv.setName(""); // NOI18N
        jComNiv.setNextFocusableComponent(jBNew);
        jComNiv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComNivKeyPressed(evt);
            }
        });
        jP1.add(jComNiv, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 90, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Cuenta padre:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 140, -1));

        jTCtaPad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaPad.setNextFocusableComponent(jTNom);
        jTCtaPad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaPadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaPadFocusLost(evt);
            }
        });
        jTCtaPad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaPadKeyPressed(evt);
            }
        });
        jP1.add(jTCtaPad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 140, 20));

        jBPag.setBackground(new java.awt.Color(255, 255, 255));
        jBPag.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPag.setForeground(new java.awt.Color(0, 102, 0));
        jBPag.setText("Pago");
        jBPag.setToolTipText("Aplicar pago a cuenta");
        jBPag.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBPag.setNextFocusableComponent(jTPag);
        jBPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPagMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPagMouseExited(evt);
            }
        });
        jBPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPagActionPerformed(evt);
            }
        });
        jBPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPagKeyPressed(evt);
            }
        });
        jP1.add(jBPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 120, 30));

        jTPag.setText("$0.00");
        jTPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag.setNextFocusableComponent(jRCli);
        jTPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPagFocusLost(evt);
            }
        });
        jTPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPagKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPagKeyTyped(evt);
            }
        });
        jP1.add(jTPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 120, 20));

        jRProv.setBackground(new java.awt.Color(255, 255, 255));
        jRProv.setText("Proveedor");
        jRProv.setNextFocusableComponent(jBSal);
        jRProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRProvKeyPressed(evt);
            }
        });
        jP1.add(jRProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 120, 20));

        jRCli.setBackground(new java.awt.Color(255, 255, 255));
        jRCli.setSelected(true);
        jRCli.setText("Cliente");
        jRCli.setNextFocusableComponent(jRProv);
        jRCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRCliKeyPressed(evt);
            }
        });
        jP1.add(jRCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 150, 120, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todas las cuentas de la base de datos y cargalas en la tabla*/
    private void vCargCtas()
    {
        /*Abre la base de datos*/        
        Connection  con;
        try 
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInsG.trim() + ";user=" + sUsrG.trim() + ";password=" + sContG.trim() + ";database=" + sBDG.trim());            
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());
            return;                        
        }
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas las cuentas de la base de datos y cargalas en la tabla*/
        try
        {
            sQ = "SELECT id, codigo, nombre, tipo, ctamayor, afectable FROM cuentas ORDER BY id ASC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("codigo"), rs.getString("nombre"), rs.getString("tipo"), rs.getString("ctamayor"), rs.getString("afectable"), rs.getString("id")};
                te.addRow(nu);
                
                /*Aumentar en uno el contador de pes*/
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
        
    }/*Fin de private void vCargCtas()*/
         
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
   
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();                    
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de la cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de la cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());        
        
    }//GEN-LAST:event_jTCtaFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre de la cuenta*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto del nombre de la cuenta*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCta.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCta.getText().compareTo("")!=0)
            jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del nombre de la cuenta*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNom.getText().compareTo("")!=0)
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTNomFocusLost
           
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                                                       
        /*Si hay cadena vacia en el campo de la cuenta entonces*/
        if(jTCta.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cuenta esta vacia.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCta.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el campo del nombre de la cuenta entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del nombre de la cuenta esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTNom.grabFocus();                        
            return;            
        }
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Cuenta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
            
        /*Obtiene la cuenta padre*/
        String sCtaPad  = jTCtaPad.getText().replace("-", "").trim();
        if(sCtaPad.compareTo("")==0)
            sCtaPad     = ",";
        
        /*Manda a llamar join data para la interfaz para  intentar crear la cuenta*/
        try
        {            
            Process pro = new ProcessBuilder("joindata.exe", sInsG.trim(), sUsrG.trim(), sContG.trim(), sBDG.trim(), "newcta", sUsrConta.trim(), sNomConta.trim(), sContraConta, jTCta.getText().replace("-", "").trim(), jTNom.getText().trim(), jComTip.getSelectedItem().toString().trim(), jComNiv.getSelectedItem().toString().trim(), sCtaPad).start();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                    
        }                           
         
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se tipea una tecla en el campo de código de la cuenta*/
    private void jTCtaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCtaKeyTyped

        
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());
        
    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jTBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscKeyPressed

        /*Si se presionó enter entonces presiona el botón de búsqueda y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBBusc.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBuscKeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todas las cuentas y cargalas en la tabla*/
        vCargCtas();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();
            return;
        }                      
                
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInsG.trim() + ";user=" + sUsrG.trim() + ";password=" + sContG.trim() + ";database=" + sBDG.trim());            
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());
            return;                        
        }
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todas las cuentas*/        
        try
        {                  
            sQ = "SELECT id, codigo, nombre, tipo, ctamayor, afectable FROM cuentas WHERE LOWER(codigo) LIKE('%" + jTBusc.getText().toLowerCase().replace(" ", "%") + "%') OR LOWER(nombre) LIKE('%" + jTBusc.getText().toLowerCase().replace(" ", "%") + "%') OR LOWER(tipo) LIKE('%" + jTBusc.getText().toLowerCase().replace(" ", "%") + "%') OR LOWER(ctamayor) LIKE('%" + jTBusc.getText().toLowerCase().replace(" ", "%") + "%') OR afectable LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') ORDER BY id ASC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("codigo"), rs.getString("nombre"), rs.getString("tipo"), rs.getString("ctamayor"), rs.getString("afectable"), rs.getString("id")};
                te.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;                                      
            }                       
        }
        catch(SQLException  expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
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

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse sale del botón de bùscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
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
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered


    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited


    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited
    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost
                         
    
    
    /*Cuando se presiona una tecla en el combo de los tipos de cuenta*/
    private void jComTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTipKeyPressed

    
    /*Cuando se presiona una tecla en elcombo de los niveles*/
    private void jComNivKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComNivKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComNivKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta padre*/
    private void jTCtaPadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaPadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaPad.setSelectionStart(0);jTCtaPad.setSelectionEnd(jTCtaPad.getText().length());        
        
    }//GEN-LAST:event_jTCtaPadFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta padre*/
    private void jTCtaPadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaPadFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaPad.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaPad.getText().compareTo("")!=0)
            jTCtaPad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCtaPadFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta padre*/
    private void jTCtaPadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaPadKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaPadKeyPressed

    
    /*Cuando el mouse entra en el botón de pago*/
    private void jBPagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPagMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPag.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPagMouseEntered

    
    /*Cuando el mouse sale del botón de pago*/
    private void jBPagMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPagMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPag.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBPagMouseExited

    
    /*Cuando se presiona una tecla en el botón de pagar*/
    private void jBPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPagKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del pago*/
    private void jTPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag.setSelectionStart(0);jTPag.setSelectionEnd(jTPag.getText().length());        
        
    }//GEN-LAST:event_jTPagFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del pago*/
    private void jTPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPag.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPag.getText().compareTo("")!=0)
            jTPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                

        /*Si el campo es cadena vacia entonces*/
        if(jTPag.getText().compareTo("")==0)
        {
            /*Que sea 0 el campo y regresa*/
            jTPag.setText("$0.00");       
            return;
        }
        
        /*Si no se puede convertir a número entonces*/
        try
        {
            Double.parseDouble(jTPag.getText().replace("$", "").replace(",", ""));
        }
        catch(NumberFormatException expnNumForm)
        {                        
            /*Coloca 0 en el campo y regresa*/
            jTPag.setText("$0.00");
            return;
        }
                
        /*Dale formato de moneda al costo*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(jTPag.getText().replace("$", "").replace(",", ""));                
        jTPag.setText   (n.format(dCant));
            
    }//GEN-LAST:event_jTPagFocusLost

    
    /*Cuando se tipea una tecla en el campo del pago*/
    private void jTPagKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPagKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPagKeyTyped

    
    /*Cuando se presiona una tecla en el campo del pago*/
    private void jTPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPagKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPagKeyPressed

    
    /*Cuando se presiona una tecla en el radio button de cliente*/
    private void jRCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRCliKeyPressed

    
    /*Cuando se presiona una tecla en el radio del proveedor*/
    private void jRProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRProvKeyPressed

    
    /*Cuando se presiona el botón de pagar a cuenta*/
    private void jBPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPagActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una cuenta para asignar un pago.", "Pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }

        /*Si no a ingresado una cantidad valida entonces*/
        if(Double.parseDouble(jTPag.getText().replace("$", "").replace(",", ""))==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "Pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTPag.grabFocus();                       
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer hacer la(s) pÃ³liza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear el pago?", "Pago", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                  
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene las cuentas a afectar*/
        String sCta1    = "";
        String sCta2    = "";        
        String sCta3    = "";
        String sCta4    = "";

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene las cuentas necesarias para pago a cliente y proveedor*/                
        try
        {
            sQ = "SELECT ctabanc, ctaivapendpag, ivapendac, ctaivaacred FROM interbd";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resultados*/
            if(rs.next())
            {
                sCta1   = rs.getString("ctabanc").replace("-", "");                    
                sCta2   = rs.getString("ctaivapendpag").replace("-", "");
                sCta3   = rs.getString("ivapendac").replace("-", "");
                sCta4   = rs.getString("ctaivaacred").replace("-", "");
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
            
        /*Si la cuenta del banco no existe entonces*/
        if(sCta1.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La cuenta del banco no existe.", "Cuenta Banco", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si la cuenta del iva pendiente de pagar no existe entonces*/
        if(sCta2.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La cuenta del iva pendiente de pagar no existe.", "Cuenta IVA", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si la cuenta del iva por pagar no existe entonces*/
        if(sCta3.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La cuenta del iva por pagar no existe.", "Cuenta IVA", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si la cuenta del iva por acreditar no existe entonces*/
        if(sCta4.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La cuenta del iva por acreditar no existe.", "Cuenta IVA", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Determina el tipo de movimiento que será*/
        String sTip;
        if(jRCli.isSelected())
        {
            /*Será de tipo cliente*/
            sTip    = "pagcli";            
        }
        else
        {
            /*Será del tipo proveedor*/
            sTip    = "pagprov";
            
            /*Las cuentas serán otras*/
            sCta2   = sCta3;
            sCta3   = sCta4;            
        }
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            /*Manda a llamar join data para realizar el pago al cliente o proveedor*/
            try
            {            
                Process pro = new ProcessBuilder("joindata.exe", sInsG.trim(), sUsrG.trim(), sContG.trim(), sBDG.trim(), sTip, sUsrConta.trim(), sNomConta.trim(), sContraConta, jTab.getValueAt(iSel[x], 1).toString(), sCta1, sCta2, jTPag.getText().replace("$", "").replace(",", ""), "16", "Pago: " + jTPag.getText().trim() + " de : " + jTab.getValueAt(iSel[x], 2), sCta3).start();
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                
            }                           
        }
        
    }//GEN-LAST:event_jBPagActionPerformed
   
    
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona F5 presioan el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();                
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBPag;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JComboBox jComNiv;
    private javax.swing.JComboBox jComTip;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jP1;
    private javax.swing.JRadioButton jRCli;
    private javax.swing.JRadioButton jRProv;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTCtaPad;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPag;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
