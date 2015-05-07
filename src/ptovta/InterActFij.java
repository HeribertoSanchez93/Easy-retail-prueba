//Paquete
package ptovta;

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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Interfaz de activo fijo hacia contabilidad*/
public class InterActFij extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables gloabales originales*/
    private String          sActivOri;
    private String          sDescripOr;
    private String          sSerOri;
    private String          sTipActOri;
    private String          sConcepOri;
    private String          sDepSisOri;
    private String          sEsActOri;
    private String          sFAdquisOri;
    private String          sIniDepOri;
    private String          sFinDepOri;
    private String          sMesesDepOri;
    private String          sDepOri;
    private String          sDeduOri;
    private String          sCostIniOri;
    private String          sDepMensOri;
    private String          sDeduMensOri;
    private String          sAcumDepOri;
    private String          sValActuaOri;
    private String          sMesLlevDepOri;
    private String          sCtaDepOri;
    private String          sCtaGastOri;
    private String          sCtaDeduOri;
    
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
        
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Contiene los datos de conexión a contabilidad SKD*/
    private String          sNomConta;
    private String          sUsrConta;
    private String          sContraConta;
    
    /*Contiene los datos de conexión a contabilidad SQL*/
    private String          sInst;
    private String          sUsr;
    private String          sContra;
    private String          sBD;
    private String          sPort;
    
    
    
    
    /*Constructor*/
    public InterActFij(String sUs, String sIns, String sCont, String sB, String sNom, String sUsrCon, String sContr, String sPor) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Obtiene los datos de conexión a contabilidad SDK*/
        sNomConta   = sNom;
        sUsrConta   = sUsrCon;
        sContraConta= sContr;
        
        /*Obtiene los datos de conexión a contabilidad SQL*/
        sInst       = sIns;
        sUsr        = sUs;
        sContra     = sCont;
        sBD         = sB;
        sPort       = sPor;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEgre);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Intefaz activo fijo, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Esconde la columna del ID*/
        jTab.getColumnModel().getColumn(1).setMinWidth(0);
        jTab.getColumnModel().getColumn(1).setMaxWidth(0);
        
        /*Establece el tamaño de las columnas de la tabla*/               
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(7).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(11).setPreferredWidth(150);        
        jTab.getColumnModel().getColumn(12).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(13).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(14).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(15).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(16).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(17).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(18).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(19).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(20).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(21).setPreferredWidth(140);        
        jTab.getColumnModel().getColumn(22).setPreferredWidth(140);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todos los activos y cargalos en la tabla*/
        vCargAct("");
        
        /*Crea el listener para la tabla, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
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
                        sActivOri       = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sDescripOr      = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                  
                        sSerOri         = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sTipActOri      = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sConcepOri      = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDepSisOri      = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sEsActOri       = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sFAdquisOri     = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sIniDepOri      = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sFinDepOri      = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                        
                        sDepOri         = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sMesesDepOri    = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sDeduOri        = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sCostIniOri     = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sDepMensOri     = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sDeduMensOri    = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sAcumDepOri     = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sValActuaOri    = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sMesLlevDepOri  = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sCtaDepOri      = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        sCtaGastOri     = jTab.getValueAt(jTab.getSelectedRow(), 22).toString();
                        sCtaDeduOri     = jTab.getValueAt(jTab.getSelectedRow(), 23).toString();                                                                                                                                                                                                                        

                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Resetea el cell editor*/
                        iContCellEd         = 1;
                        
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sActivOri,          jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sDescripOr,         jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sSerOri,            jTab.getSelectedRow(), 4);                       
                        jTab.setValueAt(sTipActOri,         jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sConcepOri,         jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sDepSisOri,         jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sEsActOri,          jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sFAdquisOri,        jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sIniDepOri,         jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sFinDepOri,         jTab.getSelectedRow(), 11);
                        jTab.setValueAt(sDepOri,            jTab.getSelectedRow(), 12);
                        jTab.setValueAt(sMesesDepOri,       jTab.getSelectedRow(), 13);
                        jTab.setValueAt(sDeduOri,           jTab.getSelectedRow(), 14);
                        jTab.setValueAt(sCostIniOri,        jTab.getSelectedRow(), 15);
                        jTab.setValueAt(sDepMensOri,        jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sDeduMensOri,       jTab.getSelectedRow(), 17);
                        jTab.setValueAt(sAcumDepOri,        jTab.getSelectedRow(), 18);
                        jTab.setValueAt(sValActuaOri,       jTab.getSelectedRow(), 19);
                        jTab.setValueAt(sMesLlevDepOri,     jTab.getSelectedRow(), 20);
                        jTab.setValueAt(sCtaDepOri,         jTab.getSelectedRow(), 21);
                        jTab.setValueAt(sCtaGastOri,        jTab.getSelectedRow(), 22);
                        jTab.setValueAt(sCtaDeduOri,        jTab.getSelectedRow(), 23);
                    }                 
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VtasCont() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBEgre = new javax.swing.JButton();

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

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTab);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 120, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "ID", "Activo", "Descripción", "Serie", "Tipo activo", "Concepto", "Depreciación", "Activo?", "Fecha adquisición", "Inicio depreciación", "Fin depreciación", "Meses a depreciar", "%Depreciación", "%Deducible", "Costo inicial", "Depresiación mensual", "Dedución mensual", "Acum. depreciación", "Valor actual", "Meses depreciados", "Cta.Depreciación", "Cta.Gasto", "Cta.Deducción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 700, 360));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 420, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBEgre);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, 140, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 12, 130, 18));

        jBEgre.setBackground(new java.awt.Color(255, 255, 255));
        jBEgre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEgre.setForeground(new java.awt.Color(0, 102, 0));
        jBEgre.setText("Diario");
        jBEgre.setToolTipText("Generar póliza de diario");
        jBEgre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBEgre.setNextFocusableComponent(jBSal);
        jBEgre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEgreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEgreMouseExited(evt);
            }
        });
        jBEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEgreActionPerformed(evt);
            }
        });
        jBEgre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEgreKeyPressed(evt);
            }
        });
        jP1.add(jBEgre, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todas las compras de la base de datos y cargalas en la tabla*/
    private void vCargAct(String sBusc)
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los activos que no se han sincronizado a contabilidad*/
        try
        {
            sQ = "SELECT lug, id_id, CASE WHEN baj = 1 THEN totvalactbaj WHEN DATE(NOW()) >= DATE(ffindep) THEN 0 ELSE cost - (TIMESTAMPDIFF(MONTH, finidep, NOW()) * ((cost * ((porcendep/12)/100))  - ((cost * ((porcendep/12)/100))  * (porcendedu/100)))) END AS valactua, CASE WHEN DATE(NOW()) >= DATE(ffindep) THEN cost WHEN baj = 1 THEN totacumes ELSE TIMESTAMPDIFF(MONTH, finidep, NOW()) * ((cost * ((porcendep/12)/100))  - ((cost * ((porcendep/12)/100))  * (porcendedu/100))) END AS acumdepmens, CASE WHEN baj = 1 THEN totestadbaj WHEN DATE(NOW()) >= ffindep THEN 'COMPLETO' WHEN TIMESTAMPDIFF(MONTH, finidep, NOW()) <= (TIMESTAMPDIFF(MONTH, finidep, ffindep) / 2) THEN 'NUEVO' WHEN TIMESTAMPDIFF(MONTH, finidep, NOW()) > (TIMESTAMPDIFF(MONTH, finidep, ffindep) / 2) THEN 'PARCIAL' END AS estadsis, (cost * ((porcendep/12)/100)) * (porcendedu/100) AS deduunitmes, TIMESTAMPDIFF(MONTH, finidep, NOW()) * (cost * ((porcendep/12)*100)) - (cost * ((porcendep/12)*100)) * ((porcendedu/12)/100) AS valact, cost * ((porcendep/12)/100) AS depunitmes, prod, descrip, serprod, tipact, CASE WHEN baj = 1 THEN DATE(fbaj) ELSE '' END AS fbaj, estadusr, CASE WHEN baj = 0 THEN 'Si' ELSE 'No' END AS act, DATE(fadquisusr) AS fadqui, CASE WHEN baj = 1 THEN totmesbaj ELSE TIMESTAMPDIFF(MONTH, finidep, ffindep) END AS totmesdep, TIMESTAMPDIFF(MONTH, finidep, ffindep) AS mesdep, DATE(finidep) AS finidep, DATE(ffindep) AS ffindep, porcendep, porcendedu, cost, ctadepre, ctagast, ctadedu FROM actfij  WHERE exportconta = 0 AND baj = 1 " + sBusc;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sCost                = rs.getString("cost");
                String sValAct              = rs.getString("valactua");
                String sTotMesDep           = rs.getString("depunitmes");
                String sDeduUnitMes         = rs.getString("deduunitmes");
                String sAcum                = rs.getString("acumdepmens");
                
                /*Dale formato de moneda a los totales*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sCost);                
                sCost                       = n.format(dCant);
                dCant                       = Double.parseDouble(sValAct);                
                sValAct                     = n.format(dCant);
                dCant                       = Double.parseDouble(sTotMesDep);                
                sTotMesDep                  = n.format(dCant);
                dCant                       = Double.parseDouble(sDeduUnitMes);                
                sDeduUnitMes                = n.format(dCant);
                dCant                       = Double.parseDouble(sAcum);                
                sAcum                       = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("id_id"), rs.getString("prod"), rs.getString("descrip"), rs.getString("serprod"), rs.getString("tipact"), rs.getString("estadusr"), rs.getString("estadsis"), rs.getString("act"), rs.getString("fadqui"), rs.getString("finidep"), rs.getString("ffindep"), rs.getString("mesdep"), rs.getString("porcendep"), rs.getString("porcendedu"), sCost, sTotMesDep, sDeduUnitMes, sAcum, sValAct, rs.getString("totmesdep"), rs.getString("ctadepre"), rs.getString("ctagast"), rs.getString("ctadedu")};
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
                    
    }/*Fin de private void vCargAct(String sBusc)*/
    
         
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
               
    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
   
    
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
        
        /*Obtiene todos los clientes de la base de datos y cargalos en la tabla*/
        vCargAct("");
        
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
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todos los clientes de la base de datos y cargalos en la tabla*/
        vCargAct(" AND prod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR descrip LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fadquisusr LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR finidep LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ffindep LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estadusr LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR serprod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')");
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

            
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
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

               
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
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited
      
    
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
        this.getRootPane().setDefaultButton(jBEgre);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost
    
    
    /*Cuando se presiona una tecla en el botón de egreso*/
    private void jBEgreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEgreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEgreKeyPressed

    
    /*Cuando el mouse entra en el botón de egreso*/
    private void jBEgreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEgreMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBEgre.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEgreMouseEntered

    
    /*Cuando el mouse sale del botón de egreso*/
    private void jBEgreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEgreMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBEgre.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBEgreMouseExited

    
    /*Cuando se presiona el botón de egreso*/
    private void jBEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEgreActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un activo para hacer la póliza de diario.", "Póliza de diario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }

        /*Preguntar al usuario si esta seguro de querer hacer la(s) póliza(s) de diario*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza diario", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                      
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene las cuentas de afectación*/
        String sCtaDep      = "";
        String sCtaGast     = "";
        String sCtaDedu     = "";
        
        /*Contiene el porcentaje de deducción*/
        String sPorcenDedu  = "";
        
        /*Contiene el valor actual*/        
        String sValAct      = "";
        
        /*Contiene el id del activo*/
        String sId          = "";

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene algunos datos de ese activo*/
        try
        {
            sQ = "SELECT id_id, totvalactbaj, porcendedu, ctadepre, ctagast, ctadedu FROM actfij WHERE id_id = " + jTab.getValueAt(jTab.getSelectedRow(), 1);
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sCtaDep     = rs.getString("ctadepre");
                sCtaGast    = rs.getString("ctagast");
                sCtaDedu    = rs.getString("ctadedu");
                sPorcenDedu = rs.getString("porcendedu");
                sValAct     = rs.getString("totvalactbaj");
                sId         = rs.getString("id_id");
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
        
        /*Abre la base de datos de SQL*/        
        try 
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + sPort + ";user=" + sUsr + ";password=" + sContra + ";database=" + sBD);            
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }
        
        /*Comprueba si la cuenta de la depreciación existe*/
        try
        {
            sQ = "SELECT codigo FROM cuentas WHERE codigo = '" + sCtaDep.replace("-", "") + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta: " + sCtaDep + " de depreciación no esta definida en contabilidad.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }            
        
        /*Comprueba si la cuenta del gasto existe*/
        try
        {
            sQ = "SELECT codigo FROM cuentas WHERE codigo = '" + sCtaGast.replace("-", "") + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta: " + sCtaGast + " de gasto no esta definida en contabilidad.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }            
        
        /*Inicialmente la cuenta de depreciación o de deducible será de depreciación*/
        String sCtaFin  = sCtaDep;
        
        /*Si el deducible es mayor a 0 entonces*/
        if(Double.parseDouble(sPorcenDedu)>0)
        {            
            /*Comprueba si la cuenta de deducible existe*/
            try
            {
                sQ = "SELECT codigo FROM cuentas WHERE codigo = '" + sCtaDedu.replace("-", "") + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La cuenta: " + sCtaDedu + " de deducibles no esta definida en contabilidad.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }                        
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                       
            }            
            
            /*La cuenta será de deducible*/
            sCtaFin     = sCtaDedu;
                        
        }/*Fin de if(Double.parseDouble(sPorcenDedu)>0)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Manda a llamar join data para la interfaz del activo fijo*/
        try
        {
            Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "actfij", sUsrConta, sNomConta, sContraConta, sCtaFin.replace("-", "").replace(" ", "").trim(), sCtaGast.replace("-", "").replace(" ", "").trim(), sValAct, sId, Star.sInstancia, Star.sUsuario, Star.sContrasenia, Star.sBD, Star.sPort).start();                
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                                                            
        }            
        
    }//GEN-LAST:event_jBEgreActionPerformed

                      
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botjBEgre salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entjBEgre presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBEgre;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
