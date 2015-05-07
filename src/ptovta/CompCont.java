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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para mandar las compras a contabilidad*/
public class CompCont extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables gloabales originales*/
    private String          sCodCompOriG;
    private String          sNoSerOriG;
    private String          sFolOriG;
    private String          sProvOriG;
    private String          sNomOriG;
    private String          sSubtotOriG;
    private String          sImpueOriG;
    private String          sTotOriG;
    private String          sFechOriG;
    private String          sCtaProvOriG;
    
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
    
    
    
    
    /*Constructor*/
    public CompCont(String sUs, String sIns, String sCont, String sB, String sNom, String sUsrCon, String sContr) 
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
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEgre);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Compras a contabilidad, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(160);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(170);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todas las compras y cargalas en la tabla*/
        vCargComp();
        
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
                        sCodCompOriG    = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sNoSerOriG      = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                  
                        sFolOriG        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sProvOriG       = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sNomOriG        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sSubtotOriG     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sImpueOriG      = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sTotOriG        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sFechOriG       = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sCtaProvOriG    = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Resetea el cell editor*/
                        iContCellEd         = 1;
                        
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sCodCompOriG,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sNoSerOriG,         jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sFolOriG,           jTab.getSelectedRow(), 3);                       
                        jTab.setValueAt(sProvOriG,          jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sNomOriG,           jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sSubtotOriG,        jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sImpueOriG,         jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sTotOriG,           jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sFechOriG,          jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sCtaProvOriG,       jTab.getSelectedRow(), 10);
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
        jComAsien = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
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
        jBSal.setNextFocusableComponent(jComAsien);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 120, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cód.Compra", "No.Serie", "Folio", "Proveedor", "Nombre", "Subtotal", "Impuesto", "Total", "Fecha", "Cuenta Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 700, 320));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 52, 130, 18));

        jComAsien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pago de flete", "Pago de arrendamiento", "Pago de honorarios", "Compra de mercancia", "Compra gasto", "Compra costo por venta o por renta" }));
        jComAsien.setNextFocusableComponent(jTab);
        jComAsien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAsienKeyPressed(evt);
            }
        });
        jP1.add(jComAsien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 260, 20));

        jLabel1.setText("Asiento:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, -1));

        jBEgre.setBackground(new java.awt.Color(255, 255, 255));
        jBEgre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEgre.setForeground(new java.awt.Color(0, 102, 0));
        jBEgre.setText("Egreso");
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
        jP1.add(jBEgre, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 120, 30));

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
    private void vCargComp()
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
        
        /*Trae todas las compras de la base de datos*/
        try
        {
            sQ = "SELECT nom, codcomp, noser, nodoc, comprs.PROV, nomprov, subtot, impue, tot, fdoc, ctaconta FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE comprs.TIP IN('COMP')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sSubTot              = rs.getString("subtot");
                String sImpue               = rs.getString("impue");
                String sTot                 = rs.getString("tot");
                
                /*Dale formato de moneda a los totales*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sSubTot);                
                sSubTot                     = n.format(dCant);
                dCant                       = Double.parseDouble(sImpue);                
                sImpue                      = n.format(dCant);
                dCant                       = Double.parseDouble(sTot);                
                sTot                        = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("codcomp"), rs.getString("noser"), rs.getString("nodoc"), rs.getString("prov"), rs.getString("nom"), sSubTot, sImpue, sTot, rs.getString("fdoc"), rs.getString("ctaconta")};
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
        
    }/*Fin de private void vCargComp()*/
          
   
    /*Función para procesar la compra cuando es de flete*/
    private synchronized void vPagFlet()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";            
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, tot, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                                                            
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCta2    = "";
            String sCta3    = "";
            
            /*Contiene la retención del iva*/
            String sRet     = "";
            
            /*Obtiene las cuentas para fletes*/                
            try
            {
                sQ = "SELECT cta1flet, cta2flet, cta3flet, retiva FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1   = rs.getString("cta1flet");                    
                    sCta2   = rs.getString("cta2flet");                    
                    sCta3   = rs.getString("cta3flet");                    
                    sRet    = rs.getString("retiva");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si alguna de las cuentas no existe entonces*/
            if(sCta1.compareTo("")==0 || sCta2.compareTo("")==0 || sCta3.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de pago de flete no esta definida.", "Pago Flete", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la retención del iva no esta definida entonces*/
            if(sRet.compareTo("")==0 || sRet.compareTo("0")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La retención del IVA no esta definida.", "Pago Flete", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Flete", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "PAGO FLETE: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Crea la retención correcta en base al impuesto*/
            sRet    = Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sRet)/100));
            
            /*Manda a llamar join data para la interfaz de las compras*/
            try
            {
                Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "compflet", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCta2.replace("-", "").replace(" ", "").trim(), sCta3.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sRet, sTot, sConcep, sFol).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                            
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);            
                    
    }/*Fin de private synchronized void vPagFlet()*/
    
    
    /*Función para procesar la compra cuando es de mercancia*/
    private synchronized void vPagMerc()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";            
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, tot, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                                                            
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCta2    = "";            
            
            /*Obtiene las cuentas para mercancia*/                
            try
            {
                sQ = "SELECT cta1merc, cta2merc FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1   = rs.getString("cta1merc");                    
                    sCta2   = rs.getString("cta2merc");                                        
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si alguna de las cuentas no existe entonces*/
            if(sCta1.compareTo("")==0 || sCta2.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de pago de mercancia no esta definida.", "Pago Mercancia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Flete", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "COMPRA MERCANCIA: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Manda a llamar join data para la interfaz de las compras*/
            try
            {
                Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "compmerc", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCta2.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sTot, sConcep, sFol).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vPagMerc()*/
    
    
    /*Función para procesar la compra cuando es de gasto*/
    private synchronized void vPagGast()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";            
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, tot, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                                                            
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCtaIVA  = "";
            
            /*Obtiene las cuentas para mercancia*/                
            try
            {
                sQ = "SELECT cta2gast, ivapendac FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1   = rs.getString("cta2gast");                    
                    sCtaIVA = rs.getString("ivapendac");                                        
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si la cuenta 1 no existe entonces*/
            if(sCta1.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de compra de gasto no esta definida.", "Pago Gasto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la cuenta del IVA no existe entonces*/
            if(sCtaIVA.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del IVA pendiente por acreditar no esta definida.", "Pago Gasto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Gasto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "COMPRA GASTO: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Manda a llamar join data para la interfaz de las compras*/
            try
            {
                Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "compmerc", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCtaIVA.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sTot, sConcep, sFol).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                            
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vPagGast()*/
    
    
    /*Función para procesar la compra cuando es de compra venta*/
    private synchronized void vPagCV()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";            
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, tot, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                                                            
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCtaIVA  = "";
            
            /*Obtiene las cuentas para mercancia*/                
            try
            {
                sQ = "SELECT cta1cv, ivapendac FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1   = rs.getString("cta1cv");                    
                    sCtaIVA = rs.getString("ivapendac");                                        
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si la cuenta 1 no existe entonces*/
            if(sCta1.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de compra de costo venta no esta definida.", "Pago Costo Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la cuenta del iva pendiente por acreditar no existe entonces*/
            if(sCtaIVA.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del IVA pendiente por acreditar no esta definida.", "Pago Costo Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Costo Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "COMPRA GASTO/VENTA: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Manda a llamar join data para la interfaz de las compras*/
            try
            {
                (new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "compmerc", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCtaIVA.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sTot, sConcep, sFol)).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vPagCV()*/
    
    
    /*Función para procesar la compra cuando es de arrendamiento*/
    private synchronized void vPagArren()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";            
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");                    
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCta2    = "";
            String sCta3    = "";
            String sCtaIVA  = "";
            
            /*Contiene la retención del ISR*/
            String sRetISR  = "";
            
            /*Contiene la retención del IVA*/
            String sRetIVA  = "";
            
            /*Obtiene las cuentas para fletes*/                
            try
            {
                sQ = "SELECT ivapendac, cta1arr, cta2arr, cta3arr, retisr, retiva FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1   = rs.getString("cta1arr");                    
                    sCta2   = rs.getString("cta2arr");                    
                    sCta3   = rs.getString("cta3arr");                    
                    sRetISR = rs.getString("retisr");
                    sRetIVA = rs.getString("retiva");
                    sCtaIVA = rs.getString("ivapendac");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si alguna de las cuentas no existe entonces*/
            if(sCta1.compareTo("")==0 || sCta2.compareTo("")==0 || sCta3.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de pago de arrendamiento no esta definida.", "Pago Arrendamiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la retención del ISR no esta definida entonces*/
            if(sRetISR.compareTo("")==0 || sRetISR.compareTo("0")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La retención del ISR no esta definida.", "Pago Arrendamiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la retención del IVA no esta definida entonces*/
            if(sRetIVA.compareTo("")==0 || sRetIVA.compareTo("0")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La retención del IVA no esta definida.", "Pago Arrendamiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Arrendamiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la cuenta del IVA pendente de acreditar no esta definida entonces*/
            if(sCtaIVA.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del IVA pendiente de acreditar no esta definida.", "Pago Arrendamiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "PAGO ARRENDAMIENTO: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Crea la retención ISR correcta*/
            sRetISR     = Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sRetISR)/100));
            
            /*Crea la retención IVA correcta*/
            sRetIVA     = Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sRetIVA)/100));
            
            /*Crea el importe correcto para el abono del proveedor*/
            String sImpoProv    = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue) - Double.parseDouble(sRetISR) - Double.parseDouble(sRetIVA));
                        
            /*Manda a llamar join data para la interfaz de las compras de arrendamiento*/
            try
            {
                (new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "comparren", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCta2.replace("-", "").replace(" ", "").trim(), sCta3.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sRetISR, sImpoProv, sConcep, sFol, sCtaIVA.replace("-", "").replace(" ", "").trim(), sRetIVA)).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vPagArren()*/
    
    
    /*Función para procesar la compra cuando es de honorarios*/
    private synchronized void vPagHono()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables para obtener algunos datos de la compra*/
            String sCtaProv = "";            
            String sSubTot  = "";
            String sImpue   = "";                        
            String sFol     = "";
            String sFDoc    = "";
            String sProv    = "";
            String sNom     = "";            

            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Obtiene algunos datos de la compra*/                
            try
            {
                sQ = "SELECT ctaconta, subtot, impue, nodoc, fdoc, comprs.PROV, nom FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaProv= rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sFol    = rs.getString("nodoc");                                        
                    sFDoc   = rs.getString("fdoc");                                        
                    sProv   = rs.getString("prov");                                        
                    sNom    = rs.getString("nom");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Contiene las cuentas para afectar*/
            String sCta1    = "";
            String sCta2    = "";
            String sCta3    = "";
            
            /*Contiene la retención del ISR*/
            String sRetISR  = "";
            
            /*Contiene la retención del IVA*/
            String sRetIVA  = "";
            
            /*Obtiene las cuentas para honorarios*/                
            String sIvaAcred = "";
            try
            {
                sQ = "SELECT ctaivaacred, cta1hon, cta2hon, cta3hon, retisr, retiva FROM interbd";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCta1       = rs.getString("cta1hon");                    
                    sCta2       = rs.getString("cta2hon");                    
                    sCta3       = rs.getString("cta3hon");                    
                    sRetISR     = rs.getString("retisr");
                    sRetIVA     = rs.getString("retiva");
                    sIvaAcred   = rs.getString("ctaivaacred");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si alguna de las cuentas no existe entonces*/
            if(sCta1.compareTo("")==0 || sCta2.compareTo("")==0 || sCta3.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Alguna de las cuentas de pago de honorarios no esta definida.", "Pago Honorarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la retención del ISR no esta definida entonces*/
            if(sRetISR.compareTo("")==0 || sRetISR.compareTo("0")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La retención del ISR no esta definida.", "Pago Honorarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si la retención del IVA no esta definida entonces*/
            if(sRetIVA.compareTo("")==0 || sRetIVA.compareTo("0")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La retención del IVA no esta definida.", "Pago Honorarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Si el proveedor no tiene cuenta definida entonces*/
            if(sCtaProv.compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cuenta del proveedor no esta definida.", "Pago Honorarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Crea el concepto de la compra*/
            String sConcep              = "PAGO HONORARIOS: <" + sFol + "> " + sFDoc + " " + sProv + ": " + sNom;
            
            /*Crea la retención ISR correcta*/
            sRetISR     = Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sRetISR)/100));
            
            /*Crea la retención IVA correcta*/
            sRetIVA     = Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sRetIVA)/100));
            
            /*Crea el importe correcto para el abono del proveedor*/
            String sImpoProv    = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue) - Double.parseDouble(sRetISR) - Double.parseDouble(sRetIVA));
            
            /*Manda a llamar join data para la interfaz de las compras de honorarios*/
            try
            {
                (new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "compahon", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCta2.replace("-", "").replace(" ", "").trim(), sCta3.replace("-", "").replace(" ", "").trim(), sCtaProv.replace("-", "").replace(" ", "").trim(), sSubTot, sImpue, sRetISR, sImpoProv, sConcep, sFol, sRetIVA, sIvaAcred.replace("-", ""))).start();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                            
            }            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vPagHono()*/
    
    
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
        vCargComp();
        
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
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los clientes*/        
        try
        {                  
            sQ = "SELECT CONCAT_WS('', ser, codemp) AS codemp, nom, deposit  FROM emps WHERE CONCAT_WS('', ser, codemp) LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR nom LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR deposit LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el depósito en garantía*/
                String sDepGar   = rs.getString("deposit");
                
                /*Dale formato de moneda al depósito en garantía*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sDepGar);                
                sDepGar                     = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("codemp"), rs.getString("nom"), sDepGar};
                dm.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;                                      
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                
            return;
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

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

    
    /*Cuando se presiona una tecla en el combo de asiento*/
    private void jComAsienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAsienKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAsienKeyPressed

    
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
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una compra para hacer la póliza de egreso.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }

        /*Preguntar al usuario si esta seguro de querer hacer la(s) pÃ³liza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza Egreso", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                        
        /*Dependiendo de la selecciÃ³n del combo*/                
        if(jComAsien.getSelectedItem().toString().compareTo("Pago de flete")==0)
            vPagFlet();
        else if(jComAsien.getSelectedItem().toString().compareTo("Pago de arrendamiento")==0)
            vPagArren();
        else if(jComAsien.getSelectedItem().toString().compareTo("Pago de honorarios")==0)
            vPagHono();
        else if(jComAsien.getSelectedItem().toString().compareTo("Compra de mercancia")==0)
            vPagMerc();
        else if(jComAsien.getSelectedItem().toString().compareTo("Compra gasto")==0)
            vPagGast();
        else if(jComAsien.getSelectedItem().toString().compareTo("Compra costo por venta o por renta")==0)
            vPagCV();                
        
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
    private javax.swing.JComboBox jComAsien;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
