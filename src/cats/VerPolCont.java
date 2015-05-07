//Paquete
package cats;

//Importaciones
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.Login;




/*Clase que sirve como visor de las pólizas de contabilidad*/
public class VerPolCont extends javax.swing.JFrame 
{    
    /*Contiene la condición para el tipo de pólizas que se va a visualizar en la tabla*/
    private String          sCondTip        = "";
    
    /*Declara variables gloabales originales para la tabla de encabezados*/
    private String          sEjeOri;
    private String          sPerioOri;
    private String          sConcepOri;
    private String          sFechOri;
    private String          sCargOri;
    private String          sAbonOri;
    
    /*Declara variables gloabales originales para la tabla de partidas*/
    private String          sNoMovOri;
    private String          sCtaOri;
    private String          sNomOri;    
    
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
            
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
    public VerPolCont(String sUsr, String sIns, String sCont, String sBD, String sNomCont, String sUsrCont, String sContraCont) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Crea el listener para cuando se cambia de selección en la tabla de encabezados*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {            
                /*Si no hay selección en la tabla de encabezados regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Carga las partidas de la póliza en la tabla*/
                vCargPart();               
            }
        });
        
        /*Esconde la columna del id de latabla de encabezados de las pólizas*/
        jTab.getColumnModel().getColumn(7).setMinWidth(0);
        jTab.getColumnModel().getColumn(7).setMaxWidth(0);
        
        /*Obtiene los datos del otro formulario*/
        sUsrG       = sUsr;
        sInsG       = sIns;
        sContG      = sCont;
        sBDG        = sBD;
        sUsrConta   = sUsrCont;
        sNomConta   = sNomCont;
        sContraConta= sContraCont;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBActua);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Visor de pólizas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(3).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(180);
        
        /*Establece el tamaño de las columnas de la tabla de partidas*/        
        jTab2.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTab2.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTab2.getColumnModel().getColumn(3).setPreferredWidth(500);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        jTab2.setAutoResizeMode(0);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todas las pólizas de la base de datos y cargalas en la tabla*/
        vCargPol();
            
        /*Crea el listener para la tabla de encabezados, para cuando se modifique una celda colocarla nuevamente en su lugar*/
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
                        sEjeOri     = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sPerioOri   = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sConcepOri  = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sFechOri    = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sCargOri    = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        sAbonOri    = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores originales que tenía*/
                        jTab.setValueAt(sEjeOri,    jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sPerioOri,  jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sConcepOri, jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sFechOri,   jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sCargOri,   jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sAbonOri,   jTab.getSelectedRow(), 6);                        
                    }                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda colocarla nuevamente en su lugar*/
        pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTab2.getSelectedRow()==-1)
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
                        sNoMovOri   = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sCtaOri     = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sNomOri     = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sCargOri    = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sAbonOri    = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                                                  
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores originales que tenía*/
                        jTab.setValueAt(sNoMovOri,  jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sCtaOri,    jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sNomOri,    jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sCargOri,   jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sAbonOri,   jTab.getSelectedRow(), 5);                        
                    }                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla*/
        jTab2.addPropertyChangeListener(pro);
        
    }/*Fin de public Peso() */
        
    
    /*Carga las partidas de la póliza en la tabla*/
    private synchronized void vCargPart()
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
        
        /*Borra la tabla de partidas*/
        DefaultTableModel te = (DefaultTableModel)jTab2.getModel();                    
        te.setRowCount(0);

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas las partidas de la póliza seleccionada*/
        try
        {
            sQ = "SELECT nummovto, codigo, tipomovto, importe, nombre FROM movimientospoliza LEFT OUTER JOIN cuentas ON cuentas.ID = movimientospoliza.IDCUENTA WHERE idpoliza = " + jTab.getValueAt(jTab.getSelectedRow(), 7);
            st = con.createStatement();
            rs = st.executeQuery(sQ);   
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Contiene los cargos y abonos*/
                String sCarg    = "0";
                String sAbon    = "0";
                
                /*Obtiene el tipo de movimiento que es*/
                String sTip = rs.getString("tipomovto");
                
                /*Determina si tiene cargo o abono*/                
                if(sTip.compareTo("0")==0)                
                    sCarg       = rs.getString("importe");
                else
                    sAbon       = rs.getString("importe");                    
                
                /*Dale formato de moneda al carog y al abono*/                
                java.text.NumberFormat n  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant    = Double.parseDouble(sCarg);                
                sCarg           = n.format(dCant);
                dCant           = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);
            
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("nummovto"), rs.getString("codigo"), rs.getString("nombre"), sCarg, sAbon};
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
        
    }/*Fin de private synchronized void vCargPart()*/
                
                
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBActua = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComTip = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jBCance = new javax.swing.JButton();

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
        jBSal.setNextFocusableComponent(jComTip);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Ejercicio", "Periodo", "Concepto", "Fecha", "Cargos", "Abonos", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jTab2);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 760, 220));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBCance);
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 120, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("*Tipo:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, -1));

        jComTip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ingreso", "Egreso", "Diario" }));
        jComTip.setNextFocusableComponent(jTab);
        jComTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComTipActionPerformed(evt);
            }
        });
        jComTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTipKeyPressed(evt);
            }
        });
        jP1.add(jComTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 230, 20));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "No.Mov", "Cuenta", "Nombre", "Cargo", "Abono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setName(""); // NOI18N
        jTab2.setNextFocusableComponent(jBActua);
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTab2);

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 760, 160));

        jBCance.setBackground(new java.awt.Color(255, 255, 255));
        jBCance.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCance.setForeground(new java.awt.Color(0, 102, 0));
        jBCance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBCance.setText("Cancelar");
        jBCance.setToolTipText("Cancelar compra en contabilidad");
        jBCance.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCance.setNextFocusableComponent(jBSal);
        jBCance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCanceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCanceMouseExited(evt);
            }
        });
        jBCance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCanceActionPerformed(evt);
            }
        });
        jBCance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCanceKeyPressed(evt);
            }
        });
        jP1.add(jBCance, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 80, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todas las pólizas de la base de datos de contabildiad y cargalas en la tabla*/
    private void vCargPol()
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
        
        /*Trae todas las pólizas*/
        try
        {
            sQ = "SELECT ejercicio, periodo, concepto, fecha, cargos, abonos, id FROM polizas WHERE 1=1 " + sCondTip + " ORDER BY id DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);   
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene el total de cargos y el total de abonos*/
                String sCarg    = rs.getString("cargos");
                String sAbon    = rs.getString("abonos");
                
                /*Dale formato de moneda a los cargos y los abonos*/                
                java.text.NumberFormat n  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant    = Double.parseDouble(sCarg);                
                sCarg           = n.format(dCant);
                dCant           = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);
                
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("ejercicio"), rs.getString("periodo"), rs.getString("concepto"), rs.getString("fecha"), sCarg, sAbon, rs.getString("id")};
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
        
    }/*Fin de private void vCargPol()*/
    
         
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
                
    
    /*Cuando se presiona una  tecla en la tabla de encabezados*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
        
    
    /*Función para mostrar nuevamente los elementos actualizados en la tabla de encabezados*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla de encabezdos y partidas*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        dm =                    (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todas las pólizas y cargalas en la tabla*/
        vCargPol();                
    }
            
    
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

                        
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing
            
    
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
                                   
    
    /*Cuando se presiona una tecla en el combo de los tipos de cuenta*/
    private void jComTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTipKeyPressed
         
    
    /*Cuando se presiona una tecla en la tabla 2*/
    private void jTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab2KeyPressed

    
    /*Cuando sucede una acción en el combo del tipo de pólizas*/
    private void jComTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTipActionPerformed
        
        /*Determina la condición dependiendo del tipo de pólizas que se seleccionó*/
        if(jComTip.getSelectedItem().toString().compareTo("Ingreso")==0)
            sCondTip    = " AND tipopol = 1 ";
        else if(jComTip.getSelectedItem().toString().compareTo("Diario")==0)
            sCondTip    = " AND tipopol = 3 ";
        else if(jComTip.getSelectedItem().toString().compareTo("Egreso")==0)
            sCondTip    = " AND tipopol = 2 ";
        
        /*Función para cargar todos los elementos en la tabla nuevamente*/
        vCargT();
        
    }//GEN-LAST:event_jComTipActionPerformed

    
    /*Cuando el mouse entra en el botón de cancelar*/
    private void jBCanceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanceMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCance.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCanceMouseEntered

    
    /*Cuando el mouse sale del botón de cancelar*/
    private void jBCanceMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanceMouseExited

        /*Cambia el color del fondo del botón*/
        jBCance.setBackground(Star.colOri);

    }//GEN-LAST:event_jBCanceMouseExited

    
    /*Cuando se presiona el botón de cancelar*/
    private void jBCanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCanceActionPerformed

        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una póliza para hacer la póliza de cancelación.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de querer hacer la(s) póliza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza Egreso", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                           
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Manda a llamar join data para cancelar el array de pólizas*/
            try
            {   
                Process pro = new ProcessBuilder("joindata.exe", sInsG, sUsrG, sContG, sBDG, "cancepol", sUsrConta, sNomConta, sContraConta, jTab.getValueAt(iSel[x], 7).toString()).start();                
            }
            catch(IOException expnIO)
            {                
                //Procesa el error
                Star.iErrProc(expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                               
            }            
        }                    

    }//GEN-LAST:event_jBCanceActionPerformed

    
    //Cuando se presiona una tecla en el botón de cancelar
    private void jBCanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCanceKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCanceKeyPressed
   
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F5 presioan el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();                
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBCance;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jComTip;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTab;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
