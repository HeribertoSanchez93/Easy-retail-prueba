//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;




/*Clase para ingresar una nota de crédito nueva*/
public class NewNotProv extends javax.swing.JFrame
{        
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables originales*/
    private String              sCantOri;
    private String              sFolOri;
    private String              sNoSerOri;
    private String              sCliOri;
    private String              sNomOri;
    private String              sDescripOri;
    private String              sSubTotOri;
    private String              sImpueOri;
    private String              sTotOri;
    private String              sCodCompOri;
    
    /*Declara variables privadas de clase*/    
    private JTable              jTabVe;
    private int                 iContCelEd;        
    private int                 iContFi;    
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    
    
    
    /*Consructor con argumento*/
    public NewNotProv(JTable jTabVent) 
    {                                                               
        /*Inicializa los componentes gráfcos*/
        initComponents();

        /*Listener para el combobox de monedas*/
        jComMon.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Trae todos los códigos de las monedas de la base de datos*/
                if(Star.iCargMonCom(con, jComMon)==-1)
                    return;
        
                //Cierra la base de datos
                Star.iCierrBas(con);                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Para que solo se pueda modificar el campo de fecha con el mouse*/        
        jDFech.getDateEditor().setEnabled(false);        
              
        /*Selecciona la fecha del día de hoy para los campos de fecha que lo necesitien*/
        Date f = new Date();
        jDFech.setDate(f);        
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Esconde el control del total de costo*/        
        jTTotCost.setVisible    (false);
        
        /*Esconde el control del concepto y código del concepto*/
        jTConcep.setVisible     (false);
        jTCodConcep.setVisible  (false);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(300);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Incializa la tabla del formulario de facturas*/
        jTabVe          = jTabVent;
        
        /*Obtiene el color original que deben tener los botones*/
        colOri          = jBSal.getBackground();
        
        /*Para que no se muevan las columnas en la tabla*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Esconde los campos que no serán visibles*/        
        jTSaldDispo.setVisible      (false);
        jTLimCred.setVisible        (false);
        jTDiaCre.setVisible         (false);
        
        /*Inicialmente no esta deseleccionada la tabla*/
        bSel            = false;
        
        /*Activa en el JTextArea que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicia el contador de filas de las partidas*/
        iContFi                 = 1;
        
        /*Establece el título de la ventana con el usuario, la fecha y hora*/                
        this.setTitle("Nueva nota de crédito, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Coloca el campo del código del cliente*/
        jTProv.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Listener para el combobox de series*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {               
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Borra los items en el combobox de series*/
                jComSer.removeAllItems();
                
                /*Agrega el elemento vacio*/
                jComSer.addItem("");

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas las series actualizadas y cargalas en el combobox*/
                try
                {
                    sQ = "SELECT ser FROM consecs WHERE tip = 'NOTP'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComSer.addItem(rs.getString("ser"));
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                            
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);         
                            
            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Trae todos los códigos de las monedas de la base de datos*/
        if(Star.iCargMonCom(con, jComMon)==-1)
            return;
        
        /*Agrega el elemento vacio en el combobox de series*/
        jComSer.addItem("");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'NOTP'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jComSer.addItem(rs.getString("ser"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }  

        /*Selecciona el elemento el elemento vacio en el control de las series*/
        jComSer.setSelectedItem("");
                
        /*Pon el foco del teclado en el código del cliente*/
        jTProv.grabFocus();                
        
        /*Incializa el contador del cell editor*/
        iContCelEd  = 1;
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda recalcular los totales*/
        PropertyChangeListener propC = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                    
                if(jTab.getSelectedRow()==-1)
                    return;

                /*Obtén la propiedad que a sucedio en el control*/
                String prop = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(prop)) 
                {                                                                                
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    String sImpOri              = "";
                    if(iContCelEd==1)
                    {
                        /*Obtén algunos datos originales*/                        
                        sCantOri                = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                                          
                        sFolOri                 = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sNoSerOri               = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sCliOri                 = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sNomOri                 = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        sDescripOri             = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sSubTotOri              = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sImpueOri               = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sTotOri                 = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sCodCompOri             = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Aumenta en uno el contador*/
                        ++iContCelEd;                                                                                                        
                    }
                    else if(iContCelEd >= 2)
                    {        
                        /*Reinicia el conteo*/
                        iContCelEd = 1;
                        
                        /*Coloca los valores originales que tenían*/
                        jTab.getModel().setValueAt(sCantOri,        jTab.getSelectedRow(), 1);
                        jTab.getModel().setValueAt(sFolOri,         jTab.getSelectedRow(), 2);
                        jTab.getModel().setValueAt(sNoSerOri,       jTab.getSelectedRow(), 3);
                        jTab.getModel().setValueAt(sCliOri,         jTab.getSelectedRow(), 4);
                        jTab.getModel().setValueAt(sNomOri,         jTab.getSelectedRow(), 5);
                        jTab.getModel().setValueAt(sDescripOri,     jTab.getSelectedRow(), 6);
                        jTab.getModel().setValueAt(sSubTotOri,      jTab.getSelectedRow(), 7);
                        jTab.getModel().setValueAt(sImpueOri,       jTab.getSelectedRow(), 8);
                        jTab.getModel().setValueAt(sTotOri,         jTab.getSelectedRow(), 9);
                        jTab.getModel().setValueAt(sCodCompOri,     jTab.getSelectedRow(), 10);
                        
                        /*Si el texto introducido no es númerico para el importe entonces*/
                        try
                        {
                            Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", ""));
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca el valor original que tenía*/
                            jTab.setValueAt(sImpOri, jTab.getSelectedRow(), 7);

                            /*Recalcula los totales leyendo toda la tabla de partidas y regresa*/
                            vRecTots();                            
                            return;
                        }          
                        
                        /*Convierte a valor absoluto el precio introducido, para quitar el negativo en caso de que lo tenga*/
                        String sImp     = Double.toString((double)Math.abs(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", ""))));                    

                        /*Si el importe     es 0 entonces*/
                        if(Double.parseDouble(sImp)== 0)
                        {
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "El importe es 0.", "Importe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                            /*Coloca el precio que estaba anteriormente y regresa*/
                            jTab.getModel().setValueAt(sImpOri, jTab.getSelectedRow(), 7);                           
                            return;
                        }
                        
                        /*Dale formato de moneda al importe*/                        
                        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        double dCant    = Double.parseDouble(sImp);                
                        sImp            = n.format(dCant);
                        
                        /*Vuelve a colocar el valor en la cant de la fila con el valor convertido a valor positivo*/
                        jTab.getModel().setValueAt(sImp, jTab.getSelectedRow(), 7);                        

                        /*Recalcula los totales leyendo toda la tabla de partidas*/
                        vRecTots();                                                 
                        
                    }/*Fin de else if(iContadorCellEditor >= 2)*/                                                                
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(propC);        
        
        /*Carga los impuestos*/
        vCargImp();
        
    }/*Fin de public NuevaCompra() */    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jPParts = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTFol = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTFilt = new javax.swing.JTextField();
        jBFilt = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jTNoSerF = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTProvF = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTSerF = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTUsrF = new javax.swing.JTextField();
        jTMetPagF = new javax.swing.JTextField();
        jTCtaF = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jTFechF = new javax.swing.JTextField();
        jTSubTotF = new javax.swing.JTextField();
        jTImpueF = new javax.swing.JTextField();
        jTTotF = new javax.swing.JTextField();
        jTEstadF = new javax.swing.JTextField();
        jTMotivF = new javax.swing.JTextField();
        jTFVencF = new javax.swing.JTextField();
        jTObservF = new javax.swing.JTextField();
        jTSucF = new javax.swing.JTextField();
        jTCajF = new javax.swing.JTextField();
        jBVePart = new javax.swing.JButton();
        jBConcep = new javax.swing.JButton();
        jTComp = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jComImp = new javax.swing.JComboBox();
        jLabel64 = new javax.swing.JLabel();
        jPClien = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTCo1 = new javax.swing.JTextField();
        jTCall = new javax.swing.JTextField();
        jTCol = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jTPai = new javax.swing.JTextField();
        jTCP = new javax.swing.JTextField();
        jTNoExt = new javax.swing.JTextField();
        jTNoInt = new javax.swing.JTextField();
        jTRFC = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jTEstad = new javax.swing.JTextField();
        jCGDats = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jTCo2 = new javax.swing.JTextField();
        jTProv = new javax.swing.JTextField();
        jBProv = new javax.swing.JButton();
        jTNom = new javax.swing.JTextField();
        jTCond = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTSer = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jTSaldDispo = new javax.swing.JTextField();
        jTLimCred = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jTDiaCre = new javax.swing.JTextField();
        jBTod = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTMetPag = new javax.swing.JTextField();
        jTCta = new javax.swing.JTextField();
        jCGuaPag = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jDFech = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jComMon = new javax.swing.JComboBox();
        jLNot = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTImpue = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jTSubTot = new javax.swing.JTextField();
        jTTotCost = new javax.swing.JTextField();
        jTConcep = new javax.swing.JTextField();
        jTCodConcep = new javax.swing.JTextField();

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

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setNextFocusableComponent(jBSal);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTProv);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 540, 110, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Qty.", "Folio", "Serie", "Proveedor", "Nombre", "Descripción", "Subtotal", "Impuesto", "Total", "Cod.Compra"
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
        jTab.setNextFocusableComponent(jBGuar);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 1080, 140));

        jPParts.setBackground(new java.awt.Color(255, 255, 255));
        jPParts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos de la Compra", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPParts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPPartsKeyPressed(evt);
            }
        });
        jPParts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Filtro Búsqueda:");
        jPParts.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        jTFol.setEditable(false);
        jTFol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFol.setNextFocusableComponent(jTNoSerF);
        jTFol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFolFocusLost(evt);
            }
        });
        jTFol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFolKeyPressed(evt);
            }
        });
        jPParts.add(jTFol, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 90, 20));

        jLabel33.setText("Usuario:");
        jPParts.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 80, -1));

        jTFilt.setBackground(new java.awt.Color(255, 255, 153));
        jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFilt.setNextFocusableComponent(jBFilt);
        jTFilt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFiltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFiltFocusLost(evt);
            }
        });
        jTFilt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFiltKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFiltKeyTyped(evt);
            }
        });
        jPParts.add(jTFilt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 260, 20));

        jBFilt.setBackground(new java.awt.Color(255, 255, 255));
        jBFilt.setText("D");
        jBFilt.setToolTipText("Buscar documento(s)");
        jBFilt.setNextFocusableComponent(jBConcep);
        jBFilt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFiltMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFiltMouseExited(evt);
            }
        });
        jBFilt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFiltActionPerformed(evt);
            }
        });
        jBFilt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFiltKeyPressed(evt);
            }
        });
        jPParts.add(jBFilt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 50, 20));

        jLabel41.setText("Folio:");
        jPParts.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        jTNoSerF.setEditable(false);
        jTNoSerF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoSerF.setNextFocusableComponent(jTProvF);
        jTNoSerF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoSerFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoSerFFocusLost(evt);
            }
        });
        jTNoSerF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoSerFKeyPressed(evt);
            }
        });
        jPParts.add(jTNoSerF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 90, 20));

        jLabel42.setText("Serie:");
        jPParts.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, -1));

        jTProvF.setEditable(false);
        jTProvF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProvF.setNextFocusableComponent(jTSerF);
        jTProvF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvFFocusLost(evt);
            }
        });
        jTProvF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProvFKeyPressed(evt);
            }
        });
        jPParts.add(jTProvF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 90, 20));

        jLabel43.setText("Proveedor:");
        jPParts.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 100, -1));

        jTSerF.setEditable(false);
        jTSerF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSerF.setNextFocusableComponent(jTMetPagF);
        jTSerF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFFocusLost(evt);
            }
        });
        jTSerF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerFKeyPressed(evt);
            }
        });
        jPParts.add(jTSerF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 90, 20));

        jLabel46.setText("Serie Proveedor:");
        jPParts.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 100, -1));

        jLabel49.setText("Método Pago:");
        jPParts.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, -1));

        jLabel50.setText("Cuenta:");
        jPParts.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 100, -1));

        jTUsrF.setEditable(false);
        jTUsrF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsrF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrFFocusLost(evt);
            }
        });
        jTUsrF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrFKeyPressed(evt);
            }
        });
        jPParts.add(jTUsrF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, 170, 20));

        jTMetPagF.setEditable(false);
        jTMetPagF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMetPagF.setNextFocusableComponent(jTCtaF);
        jTMetPagF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMetPagFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMetPagFFocusLost(evt);
            }
        });
        jTMetPagF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMetPagFKeyPressed(evt);
            }
        });
        jPParts.add(jTMetPagF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 90, 20));

        jTCtaF.setEditable(false);
        jTCtaF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaF.setNextFocusableComponent(jTFechF);
        jTCtaF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaFFocusLost(evt);
            }
        });
        jTCtaF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaFKeyPressed(evt);
            }
        });
        jPParts.add(jTCtaF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 90, 20));

        jLabel51.setText("Fecha:");
        jPParts.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 80, -1));

        jLabel52.setText("Subtotal:");
        jPParts.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 80, -1));

        jLabel53.setText("Impuesto:");
        jPParts.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 80, -1));

        jLabel54.setText("Total:");
        jPParts.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 80, -1));

        jLabel55.setText("Estado:");
        jPParts.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 80, -1));

        jLabel56.setText("Motivo:");
        jPParts.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 80, -1));

        jLabel57.setText("F. Vencimiento:");
        jPParts.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 110, -1));

        jLabel59.setText("Sucursal:");
        jPParts.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 80, -1));

        jLabel60.setText("Caja:");
        jPParts.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 80, -1));

        jLabel61.setText("Observaciones:");
        jPParts.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 110, -1));

        jTFechF.setEditable(false);
        jTFechF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFechF.setNextFocusableComponent(jTSubTotF);
        jTFechF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFechFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFechFFocusLost(evt);
            }
        });
        jTFechF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFechFKeyPressed(evt);
            }
        });
        jPParts.add(jTFechF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 140, 20));

        jTSubTotF.setEditable(false);
        jTSubTotF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSubTotF.setNextFocusableComponent(jTImpueF);
        jTSubTotF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSubTotFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSubTotFFocusLost(evt);
            }
        });
        jTSubTotF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSubTotFKeyPressed(evt);
            }
        });
        jPParts.add(jTSubTotF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 140, 20));

        jTImpueF.setEditable(false);
        jTImpueF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTImpueF.setNextFocusableComponent(jTTotF);
        jTImpueF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpueFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpueFFocusLost(evt);
            }
        });
        jTImpueF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpueFKeyPressed(evt);
            }
        });
        jPParts.add(jTImpueF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 140, 20));

        jTTotF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTotF.setNextFocusableComponent(jTEstadF);
        jTTotF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotFFocusLost(evt);
            }
        });
        jTTotF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotFKeyPressed(evt);
            }
        });
        jPParts.add(jTTotF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 140, 20));

        jTEstadF.setEditable(false);
        jTEstadF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstadF.setNextFocusableComponent(jTMotivF);
        jTEstadF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstadFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstadFFocusLost(evt);
            }
        });
        jTEstadF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstadFKeyPressed(evt);
            }
        });
        jPParts.add(jTEstadF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 140, 20));

        jTMotivF.setEditable(false);
        jTMotivF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMotivF.setNextFocusableComponent(jTFVencF);
        jTMotivF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMotivFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMotivFFocusLost(evt);
            }
        });
        jTMotivF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMotivFKeyPressed(evt);
            }
        });
        jPParts.add(jTMotivF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 140, 20));

        jTFVencF.setEditable(false);
        jTFVencF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFVencF.setNextFocusableComponent(jTComp);
        jTFVencF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFVencFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFVencFFocusLost(evt);
            }
        });
        jTFVencF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFVencFKeyPressed(evt);
            }
        });
        jPParts.add(jTFVencF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 170, 20));

        jTObservF.setEditable(false);
        jTObservF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTObservF.setNextFocusableComponent(jTSucF);
        jTObservF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTObservFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTObservFFocusLost(evt);
            }
        });
        jTObservF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTObservFKeyPressed(evt);
            }
        });
        jPParts.add(jTObservF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 170, 20));

        jTSucF.setEditable(false);
        jTSucF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSucF.setNextFocusableComponent(jTCajF);
        jTSucF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSucFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSucFFocusLost(evt);
            }
        });
        jTSucF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSucFKeyPressed(evt);
            }
        });
        jPParts.add(jTSucF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 170, 20));

        jTCajF.setEditable(false);
        jTCajF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCajF.setNextFocusableComponent(jTUsrF);
        jTCajF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCajFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCajFFocusLost(evt);
            }
        });
        jTCajF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCajFKeyPressed(evt);
            }
        });
        jPParts.add(jTCajF, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, 170, 20));

        jBVePart.setBackground(new java.awt.Color(255, 255, 255));
        jBVePart.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVePart.setForeground(new java.awt.Color(0, 104, 0));
        jBVePart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/verpartvta.png"))); // NOI18N
        jBVePart.setToolTipText("Ver las partidas de este documento");
        jBVePart.setNextFocusableComponent(jComImp);
        jBVePart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVePartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVePartMouseExited(evt);
            }
        });
        jBVePart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVePartActionPerformed(evt);
            }
        });
        jBVePart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVePartKeyPressed(evt);
            }
        });
        jPParts.add(jBVePart, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 50, 30));

        jBConcep.setBackground(new java.awt.Color(255, 255, 255));
        jBConcep.setText("C");
        jBConcep.setToolTipText("Buscar concepto(s)");
        jBConcep.setNextFocusableComponent(jBVePart);
        jBConcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConcepMouseExited(evt);
            }
        });
        jBConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConcepActionPerformed(evt);
            }
        });
        jBConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConcepKeyPressed(evt);
            }
        });
        jPParts.add(jBConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 50, 20));

        jTComp.setEditable(false);
        jTComp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTComp.setNextFocusableComponent(jTSucF);
        jTComp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCompFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCompFocusLost(evt);
            }
        });
        jTComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCompKeyPressed(evt);
            }
        });
        jPParts.add(jTComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 170, 20));

        jLabel63.setText("Compra:");
        jPParts.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 110, -1));

        jComImp.setNextFocusableComponent(jTFVencF);
        jComImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComImpFocusLost(evt);
            }
        });
        jComImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComImpActionPerformed(evt);
            }
        });
        jComImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComImpKeyPressed(evt);
            }
        });
        jPParts.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 90, 20));

        jLabel64.setText("Impuesto:");
        jPParts.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 110, 10));

        jP1.add(jPParts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 790, 175));

        jPClien.setBackground(new java.awt.Color(255, 255, 255));
        jPClien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Proveedor", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPClien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPClien.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("*CP:");
        jPClien.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 70, -1));

        jLabel20.setText("*Proveedor:");
        jPClien.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, -1));

        jLabel24.setText("*Calle:");
        jPClien.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 70, -1));

        jLabel25.setText("Correo 3:");
        jPClien.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 70, -1));

        jLabel29.setText("Teléfono:");
        jPClien.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 70, -1));

        jLabel30.setText("*Colonia:");
        jPClien.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, -1));

        jLabel31.setText("*No. Exterior:");
        jPClien.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 90, -1));

        jLabel32.setText("No. Interior:");
        jPClien.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 90, -1));

        jLabel34.setText("*Ciudad:");
        jPClien.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 70, -1));

        jLabel35.setText("*Estado:");
        jPClien.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 70, -1));

        jLabel36.setText("Condiciones:");
        jPClien.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 230, -1));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo1FocusLost(evt);
            }
        });
        jTCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo1KeyPressed(evt);
            }
        });
        jPClien.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 160, 20));

        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTCol);
        jTCall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCallFocusLost(evt);
            }
        });
        jTCall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCallKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCallKeyTyped(evt);
            }
        });
        jPClien.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 160, 20));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTTel);
        jTCol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTColFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTColFocusLost(evt);
            }
        });
        jTCol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTColKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTColKeyTyped(evt);
            }
        });
        jPClien.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 160, 20));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTPai);
        jTTel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelFocusLost(evt);
            }
        });
        jTTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelKeyTyped(evt);
            }
        });
        jPClien.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 160, 20));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTCP);
        jTPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPaiFocusLost(evt);
            }
        });
        jTPai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPaiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPaiKeyTyped(evt);
            }
        });
        jPClien.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 160, 20));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTNoExt);
        jTCP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCPFocusLost(evt);
            }
        });
        jTCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCPKeyTyped(evt);
            }
        });
        jPClien.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 160, 20));

        jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoExt.setNextFocusableComponent(jTNoInt);
        jTNoExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoExtFocusLost(evt);
            }
        });
        jTNoExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoExtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoExtKeyTyped(evt);
            }
        });
        jPClien.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 160, 20));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTRFC);
        jTNoInt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoIntFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoIntFocusLost(evt);
            }
        });
        jTNoInt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoIntKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoIntKeyTyped(evt);
            }
        });
        jPClien.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 160, 20));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRFCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRFCFocusLost(evt);
            }
        });
        jTRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRFCKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRFCKeyTyped(evt);
            }
        });
        jPClien.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 160, 20));

        jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCiu.setNextFocusableComponent(jTEstad);
        jTCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCiuFocusLost(evt);
            }
        });
        jTCiu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCiuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCiuKeyTyped(evt);
            }
        });
        jPClien.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 160, 20));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTCo1);
        jTEstad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstadFocusLost(evt);
            }
        });
        jTEstad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstadKeyTyped(evt);
            }
        });
        jPClien.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 160, 20));

        jCGDats.setBackground(new java.awt.Color(255, 255, 255));
        jCGDats.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGDats.setText("Modificar datos del proveedor F11");
        jCGDats.setNextFocusableComponent(jDFech);
        jCGDats.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGDatsKeyPressed(evt);
            }
        });
        jPClien.add(jCGDats, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 180, -1));

        jLabel38.setText("Correo 1:");
        jPClien.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 70, -1));

        jLabel39.setText("Correo 2:");
        jPClien.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 70, -1));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo3FocusLost(evt);
            }
        });
        jTCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo3KeyPressed(evt);
            }
        });
        jPClien.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 160, 20));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo2FocusLost(evt);
            }
        });
        jTCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo2KeyPressed(evt);
            }
        });
        jPClien.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 160, 20));

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBProv);
        jTProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvFocusLost(evt);
            }
        });
        jTProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProvKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProvKeyTyped(evt);
            }
        });
        jPClien.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 130, 20));

        jBProv.setBackground(new java.awt.Color(255, 255, 255));
        jBProv.setText("...");
        jBProv.setToolTipText("Buscar Cliente(s)");
        jBProv.setNextFocusableComponent(jTCall);
        jBProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProvMouseExited(evt);
            }
        });
        jBProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProvActionPerformed(evt);
            }
        });
        jBProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProvKeyPressed(evt);
            }
        });
        jPClien.add(jBProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 30, 20));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jPClien.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 410, 20));

        jTCond.setEditable(false);
        jTCond.setBackground(new java.awt.Color(255, 255, 204));
        jTCond.setForeground(new java.awt.Color(0, 153, 0));
        jTCond.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCond.setFocusable(false);
        jTCond.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCondFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCondFocusLost(evt);
            }
        });
        jTCond.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCondKeyPressed(evt);
            }
        });
        jPClien.add(jTCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 320, 20));

        jLabel45.setText("País:");
        jPClien.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 70, -1));

        jLabel47.setText("*RFC:");
        jPClien.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 70, -1));

        jTSer.setEditable(false);
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jPClien.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 70, 20));

        jP1.add(jPClien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 790, 183));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 590, 110, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTTot.setForeground(new java.awt.Color(51, 51, 0));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setNextFocusableComponent(jBGuar);
        jTTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotFocusLost(evt);
            }
        });
        jTTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotKeyPressed(evt);
            }
        });
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 590, 160, 20));

        jTSaldDispo.setEditable(false);
        jTSaldDispo.setFocusable(false);
        jP1.add(jTSaldDispo, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 600, 10, -1));

        jTLimCred.setEditable(false);
        jTLimCred.setFocusable(false);
        jP1.add(jTLimCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 600, 10, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 230, 20));

        jTDiaCre.setEditable(false);
        jTDiaCre.setFocusable(false);
        jP1.add(jTDiaCre, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 600, 10, -1));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setName(""); // NOI18N
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 382, 130, 18));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nueva");
        jBNew.setToolTipText("Nueva Partida (Ctrl+N)");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 110, 20));

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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 110, 20));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Encabezado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Fecha:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, -1));

        jLabel22.setText("*Serie:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        jComSer.setNextFocusableComponent(jTMetPag);
        jComSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFocusLost(evt);
            }
        });
        jComSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerActionPerformed(evt);
            }
        });
        jComSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerKeyPressed(evt);
            }
        });
        jPanel1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 120, 20));

        jLabel44.setText("Método Pago:");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jLabel48.setText("Cuenta:");
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jTMetPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMetPag.setNextFocusableComponent(jTCta);
        jTMetPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMetPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMetPagFocusLost(evt);
            }
        });
        jTMetPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMetPagKeyPressed(evt);
            }
        });
        jPanel1.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 120, 20));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jCGuaPag);
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
        });
        jPanel1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 120, 20));

        jCGuaPag.setBackground(new java.awt.Color(255, 255, 255));
        jCGuaPag.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGuaPag.setText("Guardar Pago F7");
        jCGuaPag.setName(""); // NOI18N
        jCGuaPag.setNextFocusableComponent(jComMon);
        jCGuaPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaPagKeyPressed(evt);
            }
        });
        jPanel1.add(jCGuaPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 140, 20));

        jTAObserv.setColumns(20);
        jTAObserv.setRows(5);
        jTAObserv.setNextFocusableComponent(jBNew);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAObservFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAObservFocusLost(evt);
            }
        });
        jTAObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAObservKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTAObserv);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 230, 110));

        jLabel40.setText("Observaciones:");
        jPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 110, -1));

        jDFech.setNextFocusableComponent(jComSer);
        jDFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFechKeyPressed(evt);
            }
        });
        jPanel1.add(jDFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 120, -1));

        jLabel9.setText("Moneda:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 110, -1));

        jComMon.setNextFocusableComponent(jTAObserv);
        jComMon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComMonFocusLost(evt);
            }
        });
        jComMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMonActionPerformed(evt);
            }
        });
        jComMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMonKeyPressed(evt);
            }
        });
        jPanel1.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 120, 20));

        jP1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 280, 365));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setText("NOTA DE CRÉDITO PROVEEDOR");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 543, 530, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel37.setText("Impuesto:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 570, 110, -1));

        jTImpue.setEditable(false);
        jTImpue.setBackground(new java.awt.Color(204, 255, 204));
        jTImpue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTImpue.setForeground(new java.awt.Color(51, 51, 0));
        jTImpue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImpue.setText("$0.00");
        jTImpue.setNextFocusableComponent(jBGuar);
        jTImpue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpueFocusLost(evt);
            }
        });
        jTImpue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpueKeyPressed(evt);
            }
        });
        jP1.add(jTImpue, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 570, 160, 20));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel62.setText("Subtotal:");
        jP1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 550, 110, -1));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(51, 51, 0));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
        jTSubTot.setNextFocusableComponent(jBGuar);
        jTSubTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSubTotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSubTotFocusLost(evt);
            }
        });
        jTSubTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSubTotKeyPressed(evt);
            }
        });
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 550, 160, 20));

        jTTotCost.setEditable(false);
        jP1.add(jTTotCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 600, 10, -1));

        jTConcep.setEditable(false);
        jP1.add(jTConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 600, 10, -1));

        jTCodConcep.setEditable(false);
        jP1.add(jTCodConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 600, 10, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    
    /*Función para que coloca las condiciones del cliente en un control*/
    private void vCond(Connection con, javax.swing.JTextField jTCon, String sLimit, String sDiCred)
    {
        /*Si los días de crédito es cadena vacia entonces que sea 0*/
        if(sDiCred.compareTo("")==0)
            sDiCred = "0";

        /*Intenta convertir en número los días de crédito*/
        try
        {
            Integer.parseInt(sDiCred);
        }
        catch(NumberFormatException expnNumForm)
        {
            sDiCred = "0";
        }                        

        /*Si el límite de crédito es cadena vacia entonces que sea 0*/
        if(sLimit.compareTo("")==0)
            sLimit  = "0";                               

        /*Intenta convertir el límite de crédito*/
        try
        {
            Double.parseDouble(sLimit);
        }
        catch(NumberFormatException expnNumForm)
        {
            sLimit = "0";
        }  

        /*Dale formato de moneda al límite de crédito*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sLimit);                
        sLimit          = n.format(dCant);

        /*Coloca las condiciones del crédito*/
        jTCon.setText      ("Días: " + sDiCred + " Límite: " + sLimit);                                
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el saldo que tiene pendiente de pagar el cliente*/
        String sPendPag = "0";
        try
        {
            sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxc WHERE empre = '" + jTProv.getText().trim() + "' AND concep <> 'NOTP'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())           
                sPendPag      = rs.getString("pendpag");                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
        
        /*Obtiene el saldo que tiene disponible la cliente*/
        String sSaldDispo   = Double.toString(Double.parseDouble(sLimit.replace("$", "").replace(",", "")) - Double.parseDouble(sPendPag));

        /*Coloca en el campo el saldo disponible*/
        jTSaldDispo.setText (sSaldDispo);
        
        /*Coloca en el campo el límite de crédito*/
        jTLimCred.setText   (sLimit.replace("$", "").replace(",", ""));
        
        /*Dale formato de moneda al saldo disponible*/        
        dCant               = Double.parseDouble(sSaldDispo);                
        sSaldDispo          = n.format(dCant);

        /*Agrega en el campo el saldo disponible*/
        String sTemp    = jTCond.getText();
        jTCond.setText(sTemp + " Saldo disponible:" + sSaldDispo);
        
    }/*Fin de private void vCond(javax.swing.JTextField jTCond, String sLimCred, String sDiaCred)*/
                
                
                
    /*Cuando se presiona el botón de generar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                                                                              
        /*Si el código del proveedor esta vacio entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un proveedor.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
            /*Coloca el foco del teclado en el control y regresa*/
            jTProv.grabFocus();                        
            return;            
        }

        /*Si el total es menor o igual a 0 entonces*/
        if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", ""))<=0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El total del documento es incorrecto.", "Nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;            
        }
        
        /*Si no a seleccionado una moneda entonces*/
        if(jComMon.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una moneda.", "Nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
            /*Coloca el foco del teclado en el control y regresa*/
            jComMon.grabFocus();                        
            return;            
        }
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el proveedor existe*/        
        try
        {
            sQ = "SELECT prov, bloq FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return; 

                /*Coloca el borde rojo*/                               
                jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTProv.grabFocus();
                return;                                 
            }
            /*Else, si existe entonces*/
            else
            {                                
                /*Si esta bloqueado el proveedor entonces*/
                if(rs.getString("bloq").compareTo("1")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return; 

                    /*Coloca el borde rojo*/                               
                    jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " esta bloqueado.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Pon el foco del teclado en el campo de la cliente y regresa*/
                    jTProv.grabFocus();                                        
                    return;
                }   
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }            
        
        /*Si aún no hay partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return; 
            
            /*Coloca el borde rojo*/                               
            jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo del código del producto*/
            jTFilt.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen partidas en la nota de crédito.", "Generar nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            return;
        }
            
        /*Si la serie de la nota de crédito es cadena vacia entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return; 
            
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
    
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para la nota de crédito.", "Generar nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSer.grabFocus();
            return;
        }
                
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Generar nota de crédito", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                       
        }
        
        /*Obtiene el método de pago*/
        String sMetPag      = jTMetPag.getText();
        
        /*Si el método de pago es cadena vacia entonces el método de pago será no identificable*/
        if(sMetPag.compareTo("")==0)
            sMetPag         = "No identificable";
        
        /*Obtiene la cuenta de pago*/
        String sCta         = jTCta.getText();
        
        /*Si la cuenta de pago es cadena vacia entonces que la cunta sea 0000*/
        if(sCta.compareTo("")==0)
            sCta            = "0000";
        
        /*Obtiene la fecha de vencimiento de la nota de crédito*/
        String sFVenc = "now()";
        try
        {
            sQ = "SELECT CASE WHEN CONVERT(IFNULL(diacred,0), SIGNED INTEGER) = 0 THEN 0 ELSE  IFNULL(diacred,0) END AS dias, CASE WHEN CONVERT(IFNULL(diacred,0), SIGNED INTEGER ) = 0 THEN now() ELSE now() + INTERVAL IFNULL(diacred,0) DAY END AS vencimien FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sFVenc      = "'" + rs.getString("vencimien") + "'";                                                                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
                
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;        
        
        /*Si el checkbox para modificar el proveedor esta activado y no es cliente mostrador entonces*/
        if(jCGDats.isSelected())
        {                        
            /*Actualiza en la base de datos los datos del proveedor*/
            try 
            {                
                sQ = "UPDATE provs SET "
                        + "calle    = '" + jTCall.getText().replace     ("'", "''") + "', "
                        + "col      = '" + jTCol.getText().replace      ("'", "''") + "', "
                        + "tel      = '" + jTTel.getText().replace      ("'", "''") + "', "
                        + "pais     = '" + jTPai.getText().replace      ("'", "''") + "', "
                        + "cp       = '" + jTCP.getText().replace       ("'", "''") + "', "
                        + "noint    = '" + jTNoInt.getText().replace    ("'", "''") + "', "
                        + "noext    = '" + jTNoExt.getText().replace    ("'", "''") + "', "
                        + "rfc      = '" + jTRFC.getText().replace      ("'", "''") + "', "
                        + "ciu      = '" + jTCiu.getText().replace      ("'", "''") + "', "
                        + "estad    = '" + jTEstad.getText().replace    ("'", "''") + "', "
                        + "co1      = '" + jTCo1.getText().replace      ("'", "''") + "', "
                        + "co2      = '" + jTCo2.getText().replace      ("'", "''") + "', "
                        + "co3      = '" + jTCo3.getText().replace      ("'", "''") + "', "
                        + "sucu     = '" + Star.sSucu.replace           ("'", "''") + "', "
                        + "nocaj    = '" + Star.sNoCaj.replace          ("'", "''") + "' "
                        + "WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
             }
            
        }/*Fin de if(jCGuarDatsClient.isSelected() && jTEmp.getText().compareTo("SYS")!=0)*/               
        
        /*Contiene el consecutivo y la serie de la nota de crédito*/
        String sConsec  = "";
        String sSerComp  = "";
        
        /*Obtiene el consecutivo de la venta y la serie*/               
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = 'NOTP' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {               
                sConsec         = rs.getString("consec");
                sSerComp         = rs.getString("ser");                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }         
        
        /*Actualiza el consecutivo de la nota de crédito*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'NOTP' AND ser = '" + jComSer.getSelectedItem().toString().replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }        
                
        /*Si se tiene que guardar la forma de pago entonces*/
        if(jCGuaPag.isSelected())
        {
            /*Actualiza la forma de pago del proveedor*/
            try 
            {         
                sQ = "UPDATE provs SET "
                        + "metpag       = '" + sMetPag.replace("'", "''") + "', "
                        + "cta          = '" + sCta.replace("'", "''") + "', "
                        + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";                    
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
                
        /*Lee la fecha del documento*/
        Date fe             =  jDFech.getDate();        
        SimpleDateFormat sdf= new SimpleDateFormat("yyy-MM-dd");
        String sFDoc        = sdf.format(fe);
        
        //Inserta CXP en la base de datos        
        if(Star.iInsCXCP(con, "cxp", sConsec, sSerComp, jTProv.getText(), jTSer.getText(), jTSubTot.getText(), jTImpue.getText(), jTTot.getText(), "0", jTTot.getText(), sFVenc, "'" + sFDoc + "'", "NOTP", "", "0", "", "","")==-1)
            return;        
        
        /*Obtiene el tipo de cambio actual*/
        String sTipCam  = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + jComMon.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sTipCam      = rs.getString("val");                                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
                
        //Inserta en la base de datos la nueva nota de crédito
        if(Star.iInsComprs(con, sSerComp.trim() + sConsec.trim(), sSerComp.replace("'", "''"), jTProv.getText().replace("'", "''").trim(), jTSer.getText().replace("'", "''").trim(), sConsec.trim(), "'" + sFDoc + "'", jTSubTot.getText().replace("$", "").replace(",", ""), jTImpue.getText().replace("$", "").replace(",", ""), jTTot.getText().replace("$", "").replace(",", ""), "'PE'", "now()", "", jTNom.getText().trim().replace("'", "''"), sFVenc, "NOTP", "now()", sMetPag.replace("'", "''").trim(), sCta.replace("'", "''").trim(), jComMon.getSelectedItem().toString().trim(), sTipCam, "", "0", "0")==-1)
            return;
        
        /*Contiene las variables para obtener los datos de la compra*/
        String sNomEstac    = "";
        String sFAl         = "";       
        
        /*Obtiene algunos datos de la compra recién insertada*/        
        try
        {                  
            sQ = "SELECT nomprov, comprs.FALT, codcomp FROM comprs LEFT OUTER JOIN estacs ON comprs.ESTAC = estacs.ESTAC WHERE codcomp = '" + sSerComp.trim() + sConsec.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sFAl            = rs.getString("comprs.FALT");                                                   
                sNomEstac       = rs.getString("nomprov");                                 
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }

        /*Recorre la tabla de partidas de de la factura para revisar que todas las notas de crédito si sean del proveedor*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si esa nota de crédito no es de ese proveedor entonces*/           
            try
            {
                sQ = "SELECT prov FROM comprs WHERE codcomp = '" + jTab.getValueAt(x, 10) + "' AND prov = '" + jTProv.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si n hay datos entonces no es esa nota de crédito de ese proveedor*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return; 

                    /*Coloca el foco del teclado en la tabla de partidas*/
                    jTab.grabFocus();
                    
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La nota de crédito: " + jTab.getValueAt(x, 3) + jTab.getValueAt(x, 2) + " no es del proveedor: " + jTProv.getText().trim(), "Nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
            }            
            
        }/*Fin de for(int x = 0; x < jTab.getRowCount(); x++)*/
        
        /*Recorre la tabla de las notas de credito*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Inserta en la base de datos la partida de la compra*/
            try 
            {                
                sQ = "INSERT INTO partcomprs(  codcom,                                               prod,                                                                                                               cant,           unid,                                                           descrip,                                                    cost,descu,      impue,          mon,    impo,                                                                       falt,  eskit,   kitmae,   alma,       costpro,tipcam,         serprod,        comenser,   cantlotpend,    garan,      descad,     codimpue) " + 
                                 "VALUES('" +  sSerComp.trim() + sConsec.trim() + "','"  +           jTab.getValueAt(x, 3).toString().replace("'", "''") + jTab.getValueAt(x, 2).toString().replace("'", "''") + "',     1,'" +          jTab.getValueAt(x, 4).toString().replace("'", "''") + "','" +   jTab.getValueAt(x, 6).toString().replace("'", "''") + "',   0,   0,          0,              '', " + jTab.getValueAt(x, 7).toString().replace("$", "").replace(",", "") + ",     now(), 0,       0,        '',         0,      0,              '',             '',         0,              '',         0,          '')";                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
             }
                        
            /*Actualiza la compra de la partida para saber que ya esta asignada a una nota de crédito*/
            try 
            {                
                sQ = "UPDATE comprs SET "
                        + "notcred              = '" + sSerComp.replace("'", "''") + sConsec.replace("'", "''") + "' "
                        + "WHERE codcomp        = '" + jTab.getValueAt(x, 10) + "'";                                  
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
             }
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
             
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return; 

        /*Dale formato de moneda al total*/
        String sTot             = jTTot.getText().replace("$", "").replace(",", "");        
        double dCant            = Double.parseDouble                (sTot);                
        NumberFormat n          = NumberFormat.getCurrencyInstance  (Locale.getDefault());
        sTot                    = n.format(dCant);

        /*Agrega los datos en la tabla de compras del otro formulario*/
        DefaultTableModel te    = (DefaultTableModel)jTabVe.getModel();
        Object nu[]             = {Star.iContFiComp, "NOTP", sSerComp + sConsec, sSerComp, sConsec, jTProv.getText(), jTNom.getText(), sTot, jComMon.getSelectedItem().toString(), sFAl, sFDoc, sFAl, sFAl, "PE", "", 1, "", Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomEstac};
        te.addRow(nu);

        /*Aumenta en uno el contador de filas de las compras*/
        ++Star.iContFiComp;
                
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Nota de crédito con folio: " + sConsec + " generada con éxito.", "Éxito al generar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                
        /*Sal del formulario*/
        this.dispose();
        
        /*Llama al recolector de basura*/
        System.gc();
        
    }//GEN-LAST:event_jBGuarActionPerformed
                                                            
                        
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Pregunta al usuario si esta seguro de abandonar*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();        
            this.dispose();
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de generar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/                
        jBSal.doClick();       
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

        
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    private void jPPartsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPPartsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPPartsKeyPressed
       
        
    /*Cuando se gana el foco del teclado en el campo de tot*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length()); 
        
    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se presiona una tecla en el campo de tot*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed
            
        
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si no a seleccionado un proveedor válido entonces*/
        if(jTNom.getText().compareTo("")==0 && jTProv.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor válido.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTProv.grabFocus();                        
            return;
        }
        
        /*Si el código de la compra es cadena vacia entonces*/
        if(jTComp.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una compra primeramente.", "Nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTFilt.grabFocus();                        
            return;
        }
        
        /*Si el importe es cadena vacia entonces*/
        if(jTTotF.getText().replace("$", "").replace(",", "").compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTTot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Indica un importe primero.", "Importe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTTot.grabFocus();                        
            return;
        }
        
        /*Si el importe es 0 entonces*/
        if(Double.parseDouble(jTTotF.getText().replace("$", "").replace(",", ""))== 0)
        {
            /*Coloca el borde rojo*/                               
            jTTot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El importe es 0.", "Importe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTTot.grabFocus();                        
            return;
        }

        /*Recorre la tabla de las partidas para ver que no esten los registros repetidos*/
        for( int row = 0; row < jTab.getRowCount(); row++)
        {            
            /*Si la compra ya existe entonces*/
            if((jTComp.getText().trim()).compareTo(jTab.getValueAt(row, 10).toString().trim())==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya esta la compra: " + jTComp.getText().trim() + " en las partidas.", "Agregar partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }            
        }
        
        /*Agrega los datos en la tabla*/
        DefaultTableModel temp  = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, 1, jTFol.getText(), jTNoSerF.getText(), jTProv.getText(), jTNom.getText(), jTConcep.getText().trim(), jTSubTotF.getText().trim(), jTImpueF.getText().trim(), jTTotF.getText().trim(), jTComp.getText().trim()};        
        temp.addRow(nu);

        /*Aumenta el contador de filas en uno*/
        iContFi = iContFi + 1;                             
        
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecTots();                
        
        /*Función para limpiar todos los campos de la compra*/
        vLimpP();
            
        /*Coloca el foco del teclado en el campo del filtro*/
        jTFilt.grabFocus();
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow() == -1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una partida para borrar.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de partidas y regresa*/
            jTab.grabFocus();                        
            return;
        }   
        
        /*Preguntar al usuario si esta seguro de que querer borrar la partida*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) partida(s)?", "Borrar Partida", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
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
        
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecTots();
                
    }//GEN-LAST:event_jBDelActionPerformed

            
    /*Cuando se gana el foco del teclado en el campo del folio*/
    private void jTFolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFol.setSelectionStart(0);jTFol.setSelectionEnd(jTFol.getText().length());        
        
    }//GEN-LAST:event_jTFolFocusGained

    
    /*Cuando se presiona una tecla en el campo de folio*/
    private void jTFolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFolKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de calle*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo col*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo pai*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());        
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de ciu*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());        
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de estad*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de correo 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());        
        
    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se presiona un tecla en el campo de teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de calle*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de col*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo de pai*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se presiona una tecla en el campo de CP*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo de número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTNoIntKeyPressed

    
    /*Cuando se presiona una tecla en el campo de RFC*/
    private void jTRFCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRFCKeyPressed

    
    /*Cuando se presiona una tecla en el campo de ciu*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se presiona una tecla en el campo de estad*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    
            
    /*Cuando se presiona una tecla en el checkbox de guardar datos del cliente*/
    private void jCGDatsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGDatsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGDatsKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de correo 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());        
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el correo 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());        
        
    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se presiona una tecla en el campo de correo 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

                
    /*Cuando se gana el foco del teclado en el campo de cliente*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());

    }//GEN-LAST:event_jTProvFocusGained

    
    /*Resetea los campos del cliente*/
    private void vResCamProv()
    {
        /*Resetea los campos que involucran a la cliente*/
        jTNom.setText    ("");
        jTCall.setText      ("");
        jTCol.setText       ("");
        jTTel.setText       ("");
        jTPai.setText       ("");
        jTCP.setText        ("");
        jTNoExt.setText     ("");
        jTNoInt.setText     ("");
        jTRFC.setText       ("");
        jTCiu.setText       ("");
        jTEstad.setText     ("");
        jTCo1.setText       ("");
        jTCo2.setText       ("");
        jTCo3.setText       ("");
        jTMetPag.setText    ("");
        jTCta.setText       ("");        
        jTSer.setText       ("");
        jTSaldDispo.setText ("0");
        jTLimCred.setText   ("0");
        jTDiaCre.setText    ("0");
    }
                
                
    /*Obtiene todos los datos del proveedor*/
    private void vCargProv()
    {
        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si la cliente es cadena vacia entonces*/
        if(jTProv.getText().compareTo("")==0)
        {
            /*Limpia todos los campos del proveedor y regresa*/            
            vResCamProv();
            return;
        }            
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todos los datos del proveedor en base a su código y si no existe activa la bandera*/        
        try
        {                  
            sQ = "SELECT * FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca todos los valores en los controles*/
                jTNom.setText       (rs.getString("nom"));                                
                jTCall.setText      (rs.getString("calle"));
                jTCol.setText       (rs.getString("col"));
                jTTel.setText       (rs.getString("lada") + rs.getString("tel"));
                jTPai.setText       (rs.getString("pais"));
                jTCP.setText        (rs.getString("cp"));
                jTNoInt.setText     (rs.getString("noint"));
                jTNoExt.setText     (rs.getString("noext"));
                jTRFC.setText       (rs.getString("rfc"));
                jTCiu.setText       (rs.getString("ciu"));
                jTCo1.setText       (rs.getString("co1"));
                jTCo2.setText       (rs.getString("co2"));
                jTSer.setText       (rs.getString("ser"));
                jTCo3.setText       (rs.getString("co3"));
                jTEstad.setText     (rs.getString("estad"));                
                jTDiaCre.setText    (rs.getString("diacred"));
                jTMetPag.setText    (rs.getString("metpag"));
                jTCta.setText       (rs.getString("cta"));                
                                
                /*Coloca todos los controles al principio para que sean visibles*/                
                jTSer.setCaretPosition      (0);
                jTCall.setCaretPosition     (0);
                jTCol.setCaretPosition      (0);
                jTTel.setCaretPosition      (0);
                jTPai.setCaretPosition      (0);
                jTCP.setCaretPosition       (0);
                jTNoExt.setCaretPosition    (0);
                jTNoInt.setCaretPosition    (0);
                jTRFC.setCaretPosition      (0);
                jTCiu.setCaretPosition      (0);
                jTEstad.setCaretPosition    (0);
                jTCo1.setCaretPosition      (0);
                jTNom.setCaretPosition      (0);
                
                /*Función que coloca las condiciones*/
                vCond(con, jTCond, rs.getString("provs.LIMCRED"), rs.getString("provs.DIACRED"));                                
            }
            /*Else, la cliente no existe*/
            else
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return; 

                /*Resetea los campos de la cliente y regresa*/
                vResCamProv();
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
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargProv()*/
        
        
    /*Cuando se pierde el foco del teclado en el campo de código de cliente*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Procesa todo esto en una función*/
        vCargProv();               
                       
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del cliente*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar proveedor*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProv.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else                 
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se presiona el botón de buscar conincidencias*/
    private void jBProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProvActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNom, jTSer, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del código del proveedor*/
        jTProv.grabFocus();

        /*Procesa todo esto en una función*/
        vCargProv();               
        
    }//GEN-LAST:event_jBProvActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProvKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nom de la cliente*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());

    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo de nombre de cliente*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del filtro*/
    private void jTFiltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFiltFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFilt.setSelectionStart(0);                
        jTFilt.setSelectionEnd(jTFilt.getText().length());

    }//GEN-LAST:event_jTFiltFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del filtro*/
    private void jTFiltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFiltFocusLost

        /*Funciòn para procesar esta parte*/
        vCargC();
        
    }//GEN-LAST:event_jTFiltFocusLost

    
    /*Funciòn para procesar toda la informaciòn del cliente cuando se pierde el foco del teclado en el control*/
    private void vCargC()
    {
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTFilt.getText().compareTo("")!=0)
            jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Coloca el caret al principio del control*/
        jTFilt.setCaretPosition(0);                
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ;
        
        /*Obtiene todos los datos de la compra en base a su código*/        
        try
        {
            sQ = "SELECT * FROM comprs WHERE codcomp  = '" + jTFilt.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Dale formato de moneda a los totales*/                                
                NumberFormat n          = NumberFormat.getCurrencyInstance(Locale.getDefault());                                
                double dCant            = Double.parseDouble(rs.getString("tot"));                                
                String sTot             = n.format(dCant);
                dCant                   = Double.parseDouble(rs.getString("subtot"));                                
                String sSubTot          = n.format(dCant);
                dCant                   = Double.parseDouble(rs.getString("impue"));                                
                String sImpue           = n.format(dCant);
                
                /*Colocalos en los campos correspondientes*/
                jTFol.setText               (rs.getString("nodoc"));
                jTNoSerF.setText            (rs.getString("noser"));                
                jTProvF.setText             (rs.getString("prov"));                
                jTSerF.setText              (rs.getString("ser"));                
                jTMetPagF.setText           (rs.getString("metpag"));
                jTComp.setText              (rs.getString("codcomp"));
                jTCtaF.setText              (rs.getString("cta"));                                
                jTFechF.setText             (rs.getString("fdoc"));                
                jTSubTotF.setText           (sSubTot);
                jTImpueF.setText            (sImpue);
                jTTotF.setText              (sTot);                
                jTEstadF.setText            (rs.getString("estado"));
                jTMotivF.setText            (rs.getString("motiv"));
                jTFVencF.setText            (rs.getString("fvenc"));
                jTObservF.setText           (rs.getString("observ"));                
                jTSucF.setText              (rs.getString("sucu"));
                jTCajF.setText              (rs.getString("nocaj"));
                jTUsrF.setText              (rs.getString("estac"));
                
                /*Coloca el concepto en su lugar*/
                jTConcep.setText            ("Pago de factura: " + rs.getString("noser") + rs.getString("nodoc") + " de serie: " + rs.getString("noser"));
                
                /*Coloca todos los controles al principio de su control*/
                jTFol.setCaretPosition          (0);
                jTNoSerF.setCaretPosition       (0);
                jTProvF.setCaretPosition        (0);
                jTSerF.setCaretPosition         (0);
                jTMetPagF.setCaretPosition      (0);
                jTCtaF.setCaretPosition         (0);
                jTFechF.setCaretPosition        (0);
                jTSubTotF.setCaretPosition      (0);
                jTImpueF.setCaretPosition       (0);
                jTTotF.setCaretPosition         (0);
                jTEstadF.setCaretPosition       (0);
                jTMotivF.setCaretPosition       (0);
                jTFVencF.setCaretPosition       (0);
                jTObservF.setCaretPosition      (0);                
                jTSucF.setCaretPosition         (0);
                jTCajF.setCaretPosition         (0);
                jTUsrF.setCaretPosition         (0);
            }
            /*Else, la venta no existe entonces limpia los controles*/
            else                            
                vLimpP();
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
        Star.iCierrBas(con);
                
    }/*Fin de private void vCargC()*/            
    
    
    /*Cuando se presiona una tecla en el campo del filtro*/
    private void jTFiltKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFiltKeyPressed

        /*Si se presiona la tecla de abajo entonces presioma el botón de búscar compra*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBFilt.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTFiltKeyPressed

    
    /*Función para limpiar todos los campos de la venta*/
    private void vLimpP()
    {
        /*Resetea los campos*/
        jTFol.setText               ("");
        jTNoSerF.setText            ("");
        jTCodConcep.setText         ("");
        jTProvF.setText             ("");                
        jTSerF.setText              ("");
        jTComp.setText              ("");
        jTMetPagF.setText           ("");                                                                
        jTCtaF.setText              ("");
        jTFechF.setText             ("");                
        jTSubTotF.setText           ("");
        jTImpueF.setText            ("");
        jTTotF.setText              ("");        
        jTEstadF.setText            ("");
        jTMotivF.setText            ("");
        jTFVencF.setText            ("");
        jTObservF.setText           ("");        
        jTSucF.setText              ("");
        jTCajF.setText              ("");
        jTUsrF.setText              ("");        
    }
    
    
    /*Cuando se presiona el botón de buscar factura*/
    private void jBFiltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFiltActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        BuscComp b = new BuscComp(this, jTFilt.getText(), jTFilt, "fac", jTab, jTProv.getText(), 0);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del filtro*/
        jTFilt.grabFocus();
        
        /*Funciòn para procesar esta parte*/
        vCargC();

    }//GEN-LAST:event_jBFiltActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar factura*/
    private void jBFiltKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFiltKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBFiltKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en mayùsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))        
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Cuando se tipea una tecla en el campo de calle*/
    private void jTCallKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCallKeyTyped

    
    /*Cuando se tipea una tecla en el campo de col*/
    private void jTColKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTColKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se tipea una tecla en el campo de pai*/
    private void jTPaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                                      
        
    }//GEN-LAST:event_jTPaiKeyTyped

    
    /*Cuando se tipea una tecla en el campo de cp*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCPKeyTyped

    
    /*Cuando se tipea una tecla en el campo del número de exterior*/
    private void jTNoExtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTNoExtKeyTyped

    
    /*Cuando se tipea una tecla en el campo del número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped

    
    /*Cuando se tipea una tecla en el campo de RFC*/
    private void jTRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyTyped
                
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRFCKeyTyped

    
    /*Cuando se tipea una tecla en el campo de ciu*/
    private void jTCiuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCiuKeyTyped

    
    /*Cuando se tipea una tecla en el campo de estad*/
    private void jTEstadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstadKeyTyped
        
    
    /*Cuando se pierde el foco del teclado en el campo de calle*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el caret al principio del control*/
        jTCall.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCall.getText().compareTo("")!=0)
            jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de col*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el caret al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCol.getText().compareTo("")!=0)
            jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el caret al principio del control*/
        jTTel.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTel.getText().compareTo("")!=0)
            jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                       
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de pai*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el caret al principio del control*/
        jTPai.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPai.getText().compareTo("")!=0)
            jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTPai.getText().length()> 255)
            jTPai.setText(jTPai.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el caret al principio del control*/
        jTCP.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCP.getText().compareTo("")!=0)
            jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCP.getText().length()> 21)
            jTCP.setText(jTCP.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en e lcampo de número de exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el caret al principio del control*/
        jTNoExt.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNoExt.getText().compareTo("")!=0)
            jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el caret al principio del control*/
        jTNoInt.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRFC.getText().compareTo("")!=0)
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTRFC.getText().length()> 21)
            jTRFC.setText(jTRFC.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el caret al principio del control*/
        jTCiu.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCiu.getText().compareTo("")!=0)
            jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCiu.getText().length()> 255)
            jTCiu.setText(jTCiu.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de estad*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el caret al principio del control*/
        jTEstad.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEstad.getText().compareTo("")!=0)
            jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTEstad.getText().length()> 255)
            jTEstad.setText(jTEstad.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de co1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret al principio del control*/
        jTCo1.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret al principio del control*/
        jTCo2.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret al principio del control*/
        jTCo3.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));
       
    }//GEN-LAST:event_jTCo3FocusLost
    
            
    /*Cuando se tipea una tecla en el campo del código del filtro*/
    private void jTFiltKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFiltKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTFiltKeyTyped

           
    
    
    
    /*Cuando se presiona una tecla en el campo del método de pago*/
    private void jTMetPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMetPagKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar pagos*/
    private void jCGuaPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de método de pago*/
    private void jTMetPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPag.setSelectionStart(0);jTMetPag.setSelectionEnd(jTMetPag.getText().length());         
        
    }//GEN-LAST:event_jTMetPagFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());         
        
    }//GEN-LAST:event_jTCtaFocusGained

    
    /*Cuando se presiona una tecla en el combobox de series*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed
   

    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el ratón en la forma*/
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

    
    /*Cuando se presiona una tecla sobre el botón de ver tabla*/
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

    
    /*Cuando se pierde el foco del teclado en el control de la serie*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Función para validar si puede o no modificar el cliente mostrador*/
    private void vClien()
    {
        /*Si esta seleccionado entonces*/
        if(jCGDats.isSelected())
        {
            /*Si es cliente mostrador entonces*/
            if(jTProv.getText().compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y desmarca el control*/
                JOptionPane.showMessageDialog(null, "No se puede modificar el Proveedor Mostrador.", "Modificar Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                jCGDats.setSelected(false);                                                       
            }
        }            
    }
        
        
   
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProv.setBackground(Star.colBot);
                        
    }//GEN-LAST:event_jBProvMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBFiltMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFiltMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBFilt.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBFiltMouseEntered
           
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBTodMouseEntered

    
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
    private void jBFiltMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFiltMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBFilt.setBackground(colOri);
        
    }//GEN-LAST:event_jBFiltMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProv.setBackground(colOri);
        
    }//GEN-LAST:event_jBProvMouseExited
        
    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/    
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

        
    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclaod en el campo de la serie*/
    private void jTSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del cliente*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el caret al principio del control*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

        
    /*Cuando se pierde el foco del teclado en el campo de la existencia*/
    private void jTFolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolFocusLost
        
        /*Coloca el caret al principio del control*/
        jTFol.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFolFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMetPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFocusLost
       
    
    /*Cuando se presiona el botón de ver partidas de la venta*/
    private void jBVePartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVePartActionPerformed

        /*Si no a seleccionado una venta entonces*/
        if(jTFol.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una compra válida.", "Ver partidas de la compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
            jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el control y regresa*/
            jTFilt.grabFocus();
            return;
        }
                    
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si la compra existe*/        
        try
        {
            sQ = "SELECT codcomp FROM comprs WHERE codcomp = '" + jTComp.getText().trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return; 

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la compra : " + jTComp.getText().trim() + ".", "Ver partidas de la compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/                               
                jTFilt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Coloca el foco del teclado en el control y regresa*/
                jTFilt.grabFocus();
                return;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
        
        //Obtiene todas las partidas de esa compra
        try
        {
            sQ = "SELECT fent, cost, impo, cant, prod, descrip, devs, eskit, unid, descu, partcomprs.IMPUE, partcomprs.FALT, tall, colo, lot, pedimen, flotvenc, fent, serprod, comenser FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE codcomp = '" + jTComp.getText().trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }                        
        
        //Muestra la tabla de partidas en grande a partir del resultset
        Star.vMaxTab(rs);
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
                
    }//GEN-LAST:event_jBVePartActionPerformed

    
    /*Cuando se presiona una tecla en el campo de la serie de la venta*/
    private void jTNoSerFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoSerFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoSerFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del código del cliente de la venta*/
    private void jTProvFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvFKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la serie del cliente de la venta*/
    private void jTSerFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del método de pago de la venta*/
    private void jTMetPagFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMetPagFKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la cuenta de la venta*/
    private void jTCtaFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaFKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha de la venta*/
    private void jTFechFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFechFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFechFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del subtotal de la venta*/
    private void jTSubTotFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSubTotFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del impuesto de la venta*/
    private void jTImpueFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpueFKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpueFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del total de la venta*/
    private void jTTotFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del estado de la venta*/
    private void jTEstadFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del motivo de la venta*/
    private void jTMotivFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMotivFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMotivFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del vencimiento de la venta*/
    private void jTFVencFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFVencFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFVencFKeyPressed

    
    /*Cuando se presiona una tecla en el campo de las observaciones de la venta*/
    private void jTObservFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObservFKeyPressed
         
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTObservFKeyPressed

    
    
    /*Cuando se presiona una tecla en el campo de sucursal de la venta*/
    private void jTSucFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSucFKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la caja de la venta*/
    private void jTCajFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCajFKeyPressed

    
    /*Cuando se presiona una tecla en el campo del usuario de la venta*/
    private void jTUsrFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrFKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la serie de la venta*/
    private void jTNoSerFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTNoSerF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoSerFFocusLost

    
    /*Cuando se pierde el foco del teclado en el camo del cliente de la venta*/
    private void jTProvFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFFocusLost
       
        /*Coloca el caret al principio del control*/
        jTProvF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTProvFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la serie de la venta*/
    private void jTSerFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del método de pago de la venta*/
    private void jTMetPagFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMetPagF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMetPagFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de la venta*/
    private void jTCtaFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCtaF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de la venta*/
    private void jTFechFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFechFFocusLost
       
        /*Coloca el caret al principio del control*/
        jTFechF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFechFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal de la factura*/
    private void jTSubTotFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSubTotF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto de la venta*/
    private void jTImpueFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTImpueF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpueFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total de la venta*/
    private void jTTotFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTotF.getText().compareTo("")!=0)
            jTTotF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Coloca el caret al principio del control*/
        jTTotF.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTTotF.getText().replace("$", "").replace(",", "");

        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;

        /*Si los carácteres introducidos no se puede convertir a double colocar 0 y regresar*/
        try
        {
            double d = Double.parseDouble(sTex);
        }
        catch(NumberFormatException expnNumForm)
        {
            jTTotF.setText("$0.00");
            return;
        }
        
        /*Obtiene el valor del impuesto*/
        String sValImp  = jComImp.getToolTipText().trim();
        
        /*Si el valor del impuesto escadena vacia entonces que sea 0*/
        if(sValImp.compareTo("")==0)
            sValImp     = "0";
        
        /*Obtiene el subtotal*/
        String sSubTot  = Double.toString(Double.parseDouble(sTex) / ((Double.parseDouble(sValImp) / 100) + 1));
        
        /*Obtiene el impuesto correcto*/
        String sImpue   = Double.toString(Double.parseDouble(sTex) - Double.parseDouble(sSubTot));
        
        /*Formatealo a moneda a los totales*/
        java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        double dCant    = Double.parseDouble(sTex);               
        sTex                        = n.format(dCant);
        dCant           = Double.parseDouble(sSubTot);               
        sSubTot                     = n.format(dCant);
        dCant           = Double.parseDouble(sImpue);               
        sImpue                      = n.format(dCant);

        /*Coloca los totales en su lugar*/
        jTImpueF.setText    (sImpue);
        jTSubTotF.setText   (sSubTot);
        jTTotF.setText      (sTex);
        
    }//GEN-LAST:event_jTTotFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del estado de la venta*/
    private void jTEstadFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTEstadF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstadFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del motivo de la venta*/
    private void jTMotivFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotivFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMotivF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMotivFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de vencimiento de la venta*/
    private void jTFVencFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFVencFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTFVencF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFVencFFocusLost

    
    /*Cuando se pierde el foco del teclado en el camo de las observaciones de la venta*/
    private void jTObservFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTObservF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTObservFFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo de la sucursal de la venta*/
    private void jTSucFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSucF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSucFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la caja de la venta*/
    private void jTCajFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCajF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCajFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del usuario de la venta*/
    private void jTUsrFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFFocusLost
        
        /*Coloca el caret al principio del control*/
        jTUsrF.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUsrFFocusLost

    
    /*Cuando se gana el foco del teclado en el camo del nùmero de serie de la venta*/
    private void jTNoSerFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoSerF.setSelectionStart(0);jTNoSerF.setSelectionEnd(jTNoSerF.getText().length());        
        
    }//GEN-LAST:event_jTNoSerFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del cliente de la venta*/
    private void jTProvFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProvF.setSelectionStart(0);jTProvF.setSelectionEnd(jTProvF.getText().length());        
        
    }//GEN-LAST:event_jTProvFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del nùmero de serie del cliente de la venta*/
    private void jTSerFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSerF.setSelectionStart(0);jTSerF.setSelectionEnd(jTSerF.getText().length());        
        
    }//GEN-LAST:event_jTSerFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del método de pago de la venta*/
    private void jTMetPagFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPagF.setSelectionStart(0);jTMetPagF.setSelectionEnd(jTMetPagF.getText().length());        
        
    }//GEN-LAST:event_jTMetPagFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo de la cuenta de la venta*/
    private void jTCtaFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaF.setSelectionStart(0);jTCtaF.setSelectionEnd(jTCtaF.getText().length());        
        
    }//GEN-LAST:event_jTCtaFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo de la fecha de la venta*/
    private void jTFechFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFechFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFechF.setSelectionStart(0);jTFechF.setSelectionEnd(jTFechF.getText().length());        
        
    }//GEN-LAST:event_jTFechFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del subtotal de la venta*/
    private void jTSubTotFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTotF.setSelectionStart(0);jTSubTotF.setSelectionEnd(jTSubTotF.getText().length());        
        
    }//GEN-LAST:event_jTSubTotFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del impuesto de la venta*/
    private void jTImpueFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImpueF.setSelectionStart(0);jTImpueF.setSelectionEnd(jTImpueF.getText().length());        
        
    }//GEN-LAST:event_jTImpueFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del total de la venta de la venta*/
    private void jTTotFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTotF.setSelectionStart(0);jTTotF.setSelectionEnd(jTTotF.getText().length());        
        
    }//GEN-LAST:event_jTTotFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del estado de la venta*/
    private void jTEstadFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstadF.setSelectionStart(0);jTEstadF.setSelectionEnd(jTEstadF.getText().length());        
        
    }//GEN-LAST:event_jTEstadFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del motivo de la venta*/
    private void jTMotivFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotivFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMotivF.setSelectionStart(0);jTMotivF.setSelectionEnd(jTMotivF.getText().length());        
        
    }//GEN-LAST:event_jTMotivFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo de la fecha de vencimiento de la venta*/
    private void jTFVencFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFVencFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFVencF.setSelectionStart(0);jTFVencF.setSelectionEnd(jTFVencF.getText().length());        
        
    }//GEN-LAST:event_jTFVencFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo de las observaciones de la venta*/
    private void jTObservFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTObservF.setSelectionStart(0);jTObservF.setSelectionEnd(jTObservF.getText().length());        
        
    }//GEN-LAST:event_jTObservFFocusGained

    
    
    /*Cuando se gana el foco del teclado en el camo de la sucursal de la venta*/
    private void jTSucFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSucF.setSelectionStart(0);jTSucF.setSelectionEnd(jTSucF.getText().length());        
        
    }//GEN-LAST:event_jTSucFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo de la caja de la venta*/
    private void jTCajFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCajF.setSelectionStart(0);jTCajF.setSelectionEnd(jTCajF.getText().length());        
        
    }//GEN-LAST:event_jTCajFFocusGained

    
    /*Cuando se gana el foco del teclado en el camo del usuario de la venta*/
    private void jTUsrFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsrF.setSelectionStart(0);jTUsrF.setSelectionEnd(jTUsrF.getText().length());        
        
    }//GEN-LAST:event_jTUsrFFocusGained

    
    /*Cuando el mouse entra en el botón de ver partidas de la venta*/
    private void jBVePartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVePartMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVePart.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVePartMouseEntered

    
    /*Cuando se presiona una tecla en el botón de ver partidas de la venta*/
    private void jBVePartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVePartKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVePartKeyPressed

    
    /*Cuando el mouse sale del botón de ver partidas de la venta*/
    private void jBVePartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVePartMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVePart.setBackground(colOri);
        
    }//GEN-LAST:event_jBVePartMouseExited

    
    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el caret en la prosición 0*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    
    /*Cuando sucede una acción en el combo de las series*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'NOTP' AND ser = '" + jComSer.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
                
        //Cierra la base de datos
        Star.iCierrBas(con);                    
        
    }//GEN-LAST:event_jComSerActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo del impuesto*/
    private void jTImpueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImpue.setSelectionStart(0);jTImpue.setSelectionEnd(jTImpue.getText().length()); 
        
    }//GEN-LAST:event_jTImpueFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFocusLost
        
        /*Coloca el caret en la prosición 0*/
        jTImpue.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpueFocusLost

    
    /*Cuando se presiona una tecla en el campo del impuesto*/
    private void jTImpueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpueKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpueKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length()); 
        
    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se pierde el foco del teclado en el camo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost

        /*Coloca el caret en la prosición 0*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se presiona una tecla en el campo del subtotal*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando el mouse entra en el botón del concepto*/
    private void jBConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBConcepMouseEntered

    
    /*Cuando el mouse sale del botón de concepto*/
    private void jBConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(colOri);                
        
    }//GEN-LAST:event_jBConcepMouseExited

    
    /*Cuando se presiona el botón de búscar concepto*/
    private void jBConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConcepActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, "", 38, jTCodConcep, null, null, "", null);
        b.setVisible(true);
        
        /*Si el campo del código del concepto no es vacio entonces coloca el nuevo concepto*/
        if(jTCodConcep.getText().trim().compareTo("")!=0)
            jTConcep.setText( jTCodConcep.getText().trim() + ", Factura: " + jTNoSerF.getText().trim() + jTFol.getText().trim() + " de Serie: " + jTNoSerF.getText().trim());
                        
    }//GEN-LAST:event_jBConcepActionPerformed

    
    /*Cuando se presiona una tecla en el botón de concepto*/
    private void jBConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConcepKeyPressed

    
    /*Cuando se presiona una tecla en el campo de las condiciones*/
    private void jTCondKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCondKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCondKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de las condiciones*/
    private void jTCondFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCond.setSelectionStart(0);jTCond.setSelectionEnd(jTCond.getText().length());        
        
    }//GEN-LAST:event_jTCondFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de las condiciones*/
    private void jTCondFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusLost
 
        /*Coloca el caret al principio del control*/
        jTCond.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCond.getText().compareTo("")!=0)
            jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCondFocusLost

    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost

        /*Coloca el caret al principio del control*/
        jTAObserv.setCaretPosition(0);

    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se presiona una tecla en el campo de las observaciones de la compra*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de las observaciones de la compra*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la compra*/
    private void jTCompFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCompFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTComp.setSelectionStart(0);jTComp.setSelectionEnd(jTComp.getText().length());        
        
    }//GEN-LAST:event_jTCompFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la compra*/
    private void jTCompFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCompFocusLost
        
        /*Coloca el caret al principio del control*/
        jTComp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCompFocusLost

    
    /*Cuando se presiona una tecla en el campo del código de la compra*/
    private void jTCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCompKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCompKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha*/
    private void jDFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFechKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFechKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de la moneda*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando sucede una acción en el combo de monedas*/
    private void jComMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMon.getSelectedItem()==null)
            return;
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "mondescrip", "mons", "WHERE mon = '" + jComMon.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComMon.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComMonActionPerformed

    
    /*Cuando se presiona una tecla en el combo de monedas*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComMonKeyPressed

    
    //Cuando se pierde el foco del teclado en el combo del impuesto
    private void jComImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComImp.getSelectedItem().toString().compareTo("")!=0)
            jComImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpFocusLost

    
    /*Cuando sucede una acción en el combo de impuestos*/
    private void jComImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComImpActionPerformed

        /*Si noy datos entonces regresa*/
        if(jComImp.getSelectedItem()==null)
            return;
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "impueval", "impues", "WHERE codimpue = '" + jComImp.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComImp.setToolTipText(sDescrip);
                
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComImpActionPerformed

    
    /*Carga los impuestos en el control*/
    private void vCargImp()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todos los impuestos en el combo
        if(Star.iCargImpueCom(con, jComImp)==-1)
            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);                    
    }
    
    
    /*Cuando se presiona una tecla en el combo de impuestos*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComImpKeyPressed
    
        
    /*Función para recalcular los totales*/
    private void vRecTots()
    {
        //Declara variables locales
        String          sSubTot     = "0";
        String          sImpue      = "0";
        String          sTot        = "0";                        
        NumberFormat    n;        
        
        
        
        
        /*Si la tabla no tiene elementos entonces coloca los totales en $0.00*/
        if(jTab.getRowCount()== 0 )
        {
            jTSubTot.setText            ("$0.00");            
            jTImpue.setText             ("$0.00");
            jTTot.setText               ("$0.00");            
        }
        /*Else, recorre toda la tabla para hacer los cálculos*/
        else
        {
            /*Recalcula los importes de todas las partidas de la tabla y ve sumando los globales*/
            for( int row = 0; row < jTab.getRowCount(); row++)                         
            {
                sSubTot                     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getModel().getValueAt(row, 7).toString().replace("$", "").replace(",", "")));                           
                sImpue                      = Double.toString(Double.parseDouble(sImpue) + Double.parseDouble(jTab.getModel().getValueAt(row, 8).toString().replace("$", "").replace(",", "")));                           
                sTot                        = Double.toString(Double.parseDouble(sTot) + Double.parseDouble(jTab.getModel().getValueAt(row, 9).toString().replace("$", "").replace(",", "")));                                           
            }

            /*Formatea a moneda los totales*/
            n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            double dCant                    = Double.parseDouble(sSubTot);                        
            sSubTot                         = n.format(dCant);
            dCant                           = Double.parseDouble(sImpue);                        
            sImpue                          = n.format(dCant);
            dCant                           = Double.parseDouble(sTot);                        
            sTot                            = n.format(dCant);
            
            /*Coloca en el campo total global el valor*/
            jTSubTot.setText    (sSubTot);
            jTImpue.setText     (sImpue);
            jTTot.setText       (sTot);            
        }                                
        
    }/*Fin de private void vRecTots()*/                                                                   
                
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)                    
            jBNew.doClick();        
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona F7*/
        else if(evt.getKeyCode() == KeyEvent.VK_F7 )
        {
            /*Marca o desmarca el checkbox de guardar pagos*/
            if(jCGuaPag.isSelected())
                jCGuaPag.setSelected(false);
            else
                jCGuaPag.setSelected(true);            
        } 
        /*Si se presiona F11*/
        else if(evt.getKeyCode() == KeyEvent.VK_F11)
        {
            /*Marca o desmarca el checkbox de guardar datos del cliente*/
            if(jCGDats.isSelected())                            
                jCGDats.setSelected(false);            
            else            
            {
                /*Seleccionalo primeramente*/
                jCGDats.setSelected(true);                        
                
                /*Función para validar si puede o no modificar el cliente mostrador*/
                vClien();                        
            }
        } 
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBConcep;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBFilt;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProv;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVePart;
    private javax.swing.JCheckBox jCGDats;
    private javax.swing.JCheckBox jCGuaPag;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private com.toedter.calendar.JDateChooser jDFech;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPClien;
    private javax.swing.JPanel jPParts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCajF;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodConcep;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTComp;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTCtaF;
    private javax.swing.JTextField jTDiaCre;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTEstadF;
    private javax.swing.JTextField jTFVencF;
    private javax.swing.JTextField jTFechF;
    private javax.swing.JTextField jTFilt;
    private javax.swing.JTextField jTFol;
    private javax.swing.JTextField jTImpue;
    private javax.swing.JTextField jTImpueF;
    private javax.swing.JTextField jTLimCred;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTMetPagF;
    private javax.swing.JTextField jTMotivF;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNoSerF;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTObservF;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTProvF;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTSaldDispo;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerF;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTSubTotF;
    private javax.swing.JTextField jTSucF;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTTotCost;
    private javax.swing.JTextField jTTotF;
    private javax.swing.JTextField jTUsrF;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
