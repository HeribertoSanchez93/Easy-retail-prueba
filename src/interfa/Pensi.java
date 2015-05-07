//Paquete
package interfa;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import ptovta.Star;
import ptovta.Login;




/*Clase para pensiones con datapark*/
public class Pensi extends javax.swing.JFrame 
{
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
        
    /*Contiene los valores originales de la tabla*/
    private String          sTarOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean         bSel;

    /*Grupo para los radio buttons*/
    javax.swing.ButtonGroup bG = new javax.swing.ButtonGroup();
    
    
    /*Constructor sin argumentos*/
    public Pensi() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();                    
        
        /*Crea el grupo de los radio buttons*/        
        bG.add(jRLoc);
        bG.add(jRExt);        
        
        /*Establece el título de los tags*/
        jTabb.setTitleAt(0, "Datapark interfaz");        
                
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Pensiones, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
        
        /*Pon el foco del teclado en el campo del cliente*/
        jTCli.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de tarjetas*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(110);
        
        /*Activa en las tablas que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                        
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
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
                        /*Obtén los datos originales de la fila*/                        
                        sTarOri             = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sTarOri,       jTab.getSelectedRow(), 1);                                                                    
                    }                                        
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Carga todos los datos de la fila de la tabla de artículos en los campos*/
                vLoadCamp();               
            }
        });
        
        /*Listener para el combobox de monedas*/
        jComMon.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga las monedas en el control*/
                vCargMon();
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
        
        /*Listener para el combobox de series de facturas*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga nuevamente las series en el control*/
                vCargSer();                                               
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
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Carga las monedas en el control*/
        vCargMon();
                
        /*Carga las series en el control*/
        vCargSer();
        
        /*Carga las tarjetas en la tabla*/        
        vCargTar();
        
        /*Carga la configuración*/        
        vCargConf();
                         
        //Obtiene el día, mes y año actual
        Calendar caleCal = Calendar.getInstance();
        int iDay    = caleCal.get(Calendar.DAY_OF_MONTH); 
        int iMes    = caleCal.get(Calendar.MONTH); 
        int iAnio   = caleCal.get(Calendar.YEAR); 

        //Suma 1 al mes para que concuerde con los meses en SQL server
        ++iMes;
        
        //Inicia los controles de fecha a la fecha de hoy
        jSDia.setValue  (iDay);
        jSDiaA.setValue (iDay);
        jSMes.setValue  (iMes);
        jSMesA.setValue (iMes);
        jSAnio.setValue (iAnio);
        jSAnioA.setValue(iAnio);
                
    }/*Fin de public Pensi() */
        
    
    /*Carga las monedas en el control*/
    private void vCargMon()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todas las monedas en el combo
        if(Star.iCargMonCom(con, jComMon)==-1)
            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);                          
    }
    
        
    /*Carga las series en el combobox*/
    private void vCargSer()
    {
        /*Borra los items en el combobox de series*/
        jComSer.removeAllItems();

        /*Agrega el elemento vacio*/
        jComSer.addItem("");
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ; 
        
        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'FAC'";                                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos al combobox*/
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
        
    }/*Fin de private void vCargSer()*/
   
   
    /*Carga la configuración en los controles*/
    private void vCargConf()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene la configuración*/        
        try
        {
            sQ = "SELECT * FROM interdpark WHERE codemploc = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca los valores en los controles*/
                jTInst.setText      (rs.getString("inst"));
                jTUsr.setText       (rs.getString("usr"));
                jPCont.setText      (Star.sDecryp(rs.getString("contra")));
                jTBD.setText        (rs.getString("bd"));                
                jTProd.setText      (rs.getString("prod"));
                jTUsrMand.setText   (rs.getString("usrmand"));
                jComSer.setSelectedItem(rs.getString("serfac"));
                jComMon.setSelectedItem(rs.getString("mon"));                                
                
                //Si el puerto es 0 entonces que sea cadena vacia
                if(rs.getInt("port")==0)
                    jTPort.setText      ("");                
                else
                    jTPort.setText      (rs.getString("port"));                
                
                /*Determina si es automatizado o no*/
                if(rs.getString("automat").compareTo("1")==0)
                    jCAut.setSelected(true);
                else
                    jCAut.setSelected(false);
                
                /*Coloca el valor de los días de corte en el control*/
                jSPDia.setValue(new Integer(rs.getString("diacort")));
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
        
    }/*Fin de private void vCargConf()*/
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTabb = new javax.swing.JTabbedPane();
        jPTars = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBTod = new javax.swing.JButton();
        jPGral = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jCAut = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jSPDia = new javax.swing.JSpinner();
        jBUsr = new javax.swing.JButton();
        jTUsrMand = new javax.swing.JTextField();
        jComSer = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComMon = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jBProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPCon = new javax.swing.JPanel();
        jTInst = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTBD = new javax.swing.JTextField();
        jTUsr = new javax.swing.JTextField();
        jPCont = new javax.swing.JPasswordField();
        jBProb = new javax.swing.JButton();
        jCMosC = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTPort = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jBNew = new javax.swing.JButton();
        jTTar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTTarif = new javax.swing.JTextField();
        jCPre = new javax.swing.JCheckBox();
        jCFactu = new javax.swing.JCheckBox();
        jTTarDat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jRExt = new javax.swing.JRadioButton();
        jRLoc = new javax.swing.JRadioButton();
        jBGua = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTFUltFac = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTCli = new javax.swing.JTextField();
        jBCli = new javax.swing.JButton();
        jTNom = new javax.swing.JTextField();
        jPRep = new javax.swing.JPanel();
        jSDia = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSMes = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        jSAnio = new javax.swing.JSpinner();
        jSDiaA = new javax.swing.JSpinner();
        jSMesA = new javax.swing.JSpinner();
        jSAnioA = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jBGen = new javax.swing.JButton();
        jSMin = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSHor = new javax.swing.JSpinner();
        jSHorA = new javax.swing.JSpinner();
        jSMinA = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        jSSegA = new javax.swing.JSpinner();
        jSSeg = new javax.swing.JSpinner();
        jBSal = new javax.swing.JButton();
        jBGuaC = new javax.swing.JButton();

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

        jTabb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbKeyPressed(evt);
            }
        });

        jPTars.setBackground(new java.awt.Color(255, 255, 255));
        jPTars.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Tarjeta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jTCli);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jPTars.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, 470));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
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
        jPTars.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 2, 130, 18));

        jPGral.setBackground(new java.awt.Color(255, 255, 255));
        jPGral.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        jPGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPGralKeyPressed(evt);
            }
        });
        jPGral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Días de corte:");
        jPGral.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, -1));

        jCAut.setBackground(new java.awt.Color(255, 255, 255));
        jCAut.setText("Automatizar factura");
        jCAut.setNextFocusableComponent(jTUsrMand);
        jCAut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAutKeyPressed(evt);
            }
        });
        jPGral.add(jCAut, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 190, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Moneda:");
        jPGral.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 90, -1));

        jSPDia.setNextFocusableComponent(jCAut);
        jSPDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSPDiaKeyPressed(evt);
            }
        });
        jPGral.add(jSPDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 40, -1));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar usuario");
        jBUsr.setName(""); // NOI18N
        jBUsr.setNextFocusableComponent(jComSer);
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
        jPGral.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 30, 20));

        jTUsrMand.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsrMand.setNextFocusableComponent(jBUsr);
        jTUsrMand.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrMandFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrMandFocusLost(evt);
            }
        });
        jTUsrMand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrMandKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUsrMandKeyTyped(evt);
            }
        });
        jPGral.add(jTUsrMand, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 20));

        jComSer.setNextFocusableComponent(jComMon);
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
        jPGral.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 100, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Usuario de envio:");
        jPGral.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, -1));

        jComMon.setNextFocusableComponent(jTProd);
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
        jPGral.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 100, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Serie:");
        jPGral.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, -1));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTInst);
        jBProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdMouseExited(evt);
            }
        });
        jBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdActionPerformed(evt);
            }
        });
        jBProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdKeyPressed(evt);
            }
        });
        jPGral.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 30, 20));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBProd);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProdFocusLost(evt);
            }
        });
        jTProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProdKeyTyped(evt);
            }
        });
        jPGral.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 70, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Producto:");
        jPGral.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, 20));

        jPTars.add(jPGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, 230, 230));

        jPCon.setBackground(new java.awt.Color(255, 255, 255));
        jPCon.setBorder(javax.swing.BorderFactory.createTitledBorder("Conexión"));
        jPCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPConKeyPressed(evt);
            }
        });
        jPCon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTInst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTInst.setNextFocusableComponent(jTUsr);
        jTInst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTInstFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTInstFocusLost(evt);
            }
        });
        jTInst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTInstKeyPressed(evt);
            }
        });
        jPCon.add(jTInst, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 210, 20));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("*Usuario:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPCon.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 210, 20));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("*Contraseña:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPCon.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 210, 20));

        jTBD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBD.setNextFocusableComponent(jTPort);
        jTBD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBDFocusLost(evt);
            }
        });
        jTBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBDKeyPressed(evt);
            }
        });
        jPCon.add(jTBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 210, 20));

        jTUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jPCont);
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
        });
        jPCon.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 210, 20));

        jPCont.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPCont.setNextFocusableComponent(jCMosC);
        jPCont.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContFocusLost(evt);
            }
        });
        jPCont.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContKeyPressed(evt);
            }
        });
        jPCon.add(jPCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 210, 20));

        jBProb.setBackground(new java.awt.Color(255, 255, 255));
        jBProb.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProb.setForeground(new java.awt.Color(0, 102, 0));
        jBProb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prov.png"))); // NOI18N
        jBProb.setToolTipText("Probar conexión con SQL");
        jBProb.setName(""); // NOI18N
        jBProb.setNextFocusableComponent(jSPDia);
        jBProb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbMouseExited(evt);
            }
        });
        jBProb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbActionPerformed(evt);
            }
        });
        jBProb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbKeyPressed(evt);
            }
        });
        jPCon.add(jBProb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 50, 30));

        jCMosC.setBackground(new java.awt.Color(255, 255, 255));
        jCMosC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosC.setText("Mostrar Contraseña");
        jCMosC.setNextFocusableComponent(jTBD);
        jCMosC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMosCActionPerformed(evt);
            }
        });
        jCMosC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosCKeyPressed(evt);
            }
        });
        jPCon.add(jCMosC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 160, 20));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("*Puerto:");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPCon.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 110, 20));

        jTPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPort.setNextFocusableComponent(jBProb);
        jTPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPortFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPortFocusLost(evt);
            }
        });
        jTPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPortKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPortKeyTyped(evt);
            }
        });
        jPCon.add(jTPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 70, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("*Base de Datos:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPCon.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 220, 20));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("*Instancia DATAPARK:");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPCon.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 20));

        jPTars.add(jPCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, -1, 260));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/newcli.png"))); // NOI18N
        jBNew.setToolTipText("Agregar tarjeta (Ctrl+N)");
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
        jPanel1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 50, 30));

        jTTar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTar.setNextFocusableComponent(jRLoc);
        jTTar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarFocusLost(evt);
            }
        });
        jTTar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTarKeyTyped(evt);
            }
        });
        jPanel1.add(jTTar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Tarifa:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 90, -1));

        jTTarif.setText("$0.00");
        jTTarif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTarif.setNextFocusableComponent(jTTarDat);
        jTTarif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarifFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarifFocusLost(evt);
            }
        });
        jTTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarifKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTarifKeyTyped(evt);
            }
        });
        jPanel1.add(jTTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 100, 20));

        jCPre.setBackground(new java.awt.Color(255, 255, 255));
        jCPre.setText("Prepago");
        jCPre.setNextFocusableComponent(jCFactu);
        jCPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPreKeyPressed(evt);
            }
        });
        jPanel1.add(jCPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, -1));

        jCFactu.setBackground(new java.awt.Color(255, 255, 255));
        jCFactu.setText("Facturar");
        jCFactu.setNextFocusableComponent(jBNew);
        jCFactu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFactuKeyPressed(evt);
            }
        });
        jPanel1.add(jCFactu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jTTarDat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTarDat.setNextFocusableComponent(jTFUltFac);
        jTTarDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarDatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarDatFocusLost(evt);
            }
        });
        jTTarDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarDatKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTarDatKeyTyped(evt);
            }
        });
        jPanel1.add(jTTarDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 100, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tarjeta:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, -1));

        jRExt.setBackground(new java.awt.Color(255, 255, 255));
        jRExt.setSelected(true);
        jRExt.setText("Externo");
        jRExt.setNextFocusableComponent(jCPre);
        jRExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRExtKeyPressed(evt);
            }
        });
        jPanel1.add(jRExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        jRLoc.setBackground(new java.awt.Color(255, 255, 255));
        jRLoc.setText("Locatario");
        jRLoc.setNextFocusableComponent(jRExt);
        jRLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRLocKeyPressed(evt);
            }
        });
        jPanel1.add(jRLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 90, -1));

        jBGua.setBackground(new java.awt.Color(255, 255, 255));
        jBGua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGua.setForeground(new java.awt.Color(0, 102, 0));
        jBGua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGua.setToolTipText("Guardar cambios(Ctrl+G)");
        jBGua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGua.setNextFocusableComponent(jTTarif);
        jBGua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuaMouseExited(evt);
            }
        });
        jBGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuaActionPerformed(evt);
            }
        });
        jBGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuaKeyPressed(evt);
            }
        });
        jPanel1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 50, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBDel.setToolTipText("Borrar tarjeta(s) (Ctrl+Supr)");
        jBDel.setNextFocusableComponent(jBGua);
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
        jPanel1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 50, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Última factura:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 150, 20));

        jTFUltFac.setEditable(false);
        jTFUltFac.setToolTipText("Fecha de última factura realizada");
        jTFUltFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFUltFac.setNextFocusableComponent(jSDia);
        jTFUltFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFUltFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFUltFacFocusLost(evt);
            }
        });
        jTFUltFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFUltFacKeyPressed(evt);
            }
        });
        jPanel1.add(jTFUltFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 190, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Tarjeta Datapark:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 150, 20));

        jPTars.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 370, 190));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Cliente:");
        jPTars.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 170, -1));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBCli);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCliFocusLost(evt);
            }
        });
        jTCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCliKeyTyped(evt);
            }
        });
        jPTars.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 130, 20));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jTNom);
        jBCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCliMouseExited(evt);
            }
        });
        jBCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCliActionPerformed(evt);
            }
        });
        jBCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCliKeyPressed(evt);
            }
        });
        jPTars.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 30, 20));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTTar);
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
        jPTars.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 200, 20));

        jPRep.setBackground(new java.awt.Color(255, 255, 255));
        jPRep.setBorder(javax.swing.BorderFactory.createTitledBorder("Reporteador"));
        jPRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPRepKeyPressed(evt);
            }
        });
        jPRep.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSDia.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, Integer.valueOf(31), Integer.valueOf(1)));
        jSDia.setNextFocusableComponent(jSMes);
        jSDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSDiaKeyPressed(evt);
            }
        });
        jPRep.add(jSDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel5.setText("Día:");
        jPRep.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 30, -1));

        jLabel12.setText("Mes:");
        jPRep.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 30, -1));

        jSMes.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, Integer.valueOf(12), Integer.valueOf(1)));
        jSMes.setNextFocusableComponent(jSAnio);
        jSMes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSMesKeyPressed(evt);
            }
        });
        jPRep.add(jSMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 40, 40, -1));

        jLabel18.setText("A:");
        jPRep.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 30, -1));

        jSAnio.setModel(new javax.swing.SpinnerNumberModel(1999, 1999, 2050, 1));
        jSAnio.setNextFocusableComponent(jSHor);
        jSAnio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSAnioKeyPressed(evt);
            }
        });
        jPRep.add(jSAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jSDiaA.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, Integer.valueOf(31), Integer.valueOf(1)));
        jSDiaA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSDiaAKeyPressed(evt);
            }
        });
        jPRep.add(jSDiaA, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jSMesA.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, Integer.valueOf(12), Integer.valueOf(1)));
        jSMesA.setNextFocusableComponent(jSAnioA);
        jSMesA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSMesAKeyPressed(evt);
            }
        });
        jPRep.add(jSMesA, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 60, 40, -1));

        jSAnioA.setModel(new javax.swing.SpinnerNumberModel(1999, 1999, 2050, 1));
        jSAnioA.setName(""); // NOI18N
        jSAnioA.setNextFocusableComponent(jSHorA);
        jSAnioA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSAnioAKeyPressed(evt);
            }
        });
        jPRep.add(jSAnioA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, -1));

        jLabel22.setText("Seg:");
        jPRep.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 40, -1));

        jLabel23.setText("De:");
        jPRep.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, -1));

        jBGen.setBackground(new java.awt.Color(255, 255, 255));
        jBGen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGen.setForeground(new java.awt.Color(0, 102, 0));
        jBGen.setText("Generar");
        jBGen.setToolTipText("Generar reporte");
        jBGen.setNextFocusableComponent(jTCli);
        jBGen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGenMouseExited(evt);
            }
        });
        jBGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGenActionPerformed(evt);
            }
        });
        jBGen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGenKeyPressed(evt);
            }
        });
        jPRep.add(jBGen, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jSMin.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        jSMin.setNextFocusableComponent(jSSeg);
        jSMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSMinKeyPressed(evt);
            }
        });
        jPRep.add(jSMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 40, 40, -1));

        jLabel24.setText("Año:");
        jPRep.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 30, -1));

        jLabel25.setText("Hr:");
        jPRep.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 30, -1));

        jSHor.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        jSHor.setNextFocusableComponent(jSMin);
        jSHor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSHorKeyPressed(evt);
            }
        });
        jPRep.add(jSHor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        jSHorA.setModel(new javax.swing.SpinnerNumberModel(23, 0, 23, 1));
        jSHorA.setNextFocusableComponent(jSMinA);
        jSHorA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSHorAKeyPressed(evt);
            }
        });
        jPRep.add(jSHorA, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, -1, -1));

        jSMinA.setModel(new javax.swing.SpinnerNumberModel(59, 0, 59, 1));
        jSMinA.setNextFocusableComponent(jSSegA);
        jSMinA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSMinAKeyPressed(evt);
            }
        });
        jPRep.add(jSMinA, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 60, 40, -1));

        jLabel26.setText("Min:");
        jPRep.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 40, -1));

        jSSegA.setModel(new javax.swing.SpinnerNumberModel(59, 0, 59, 1));
        jSSegA.setNextFocusableComponent(jBGen);
        jSSegA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSSegAKeyPressed(evt);
            }
        });
        jPRep.add(jSSegA, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 60, -1, -1));

        jSSeg.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        jSSeg.setNextFocusableComponent(jSDiaA);
        jSSeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSSegKeyPressed(evt);
            }
        });
        jPRep.add(jSSeg, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 40, -1, -1));

        jPTars.add(jPRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 340, 110));

        jTabb.addTab("tab2", jPTars);

        jP1.add(jTabb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 850, 530));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jBGuaC);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 540, 80, 30));

        jBGuaC.setBackground(new java.awt.Color(255, 255, 255));
        jBGuaC.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuaC.setForeground(new java.awt.Color(0, 102, 0));
        jBGuaC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuaC.setToolTipText("Guardar cambios");
        jBGuaC.setNextFocusableComponent(jBSal);
        jBGuaC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuaCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuaCMouseExited(evt);
            }
        });
        jBGuaC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuaCActionPerformed(evt);
            }
        });
        jBGuaC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuaCKeyPressed(evt);
            }
        });
        jP1.add(jBGuaC, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 540, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
   
    /*Carga todos los datos de la fila de la tabla de artículos en los campos*/
    private void vLoadCamp()
    {
        /*Si no hay selección entonces regresa*/
        if(jTab.getSelectedRow()==-1)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene los datos de la tarjeta seleccionada*/        
        try
        {
            sQ = "SELECT * FROM tars WHERE cli = '" + jTCli.getText().trim() + "' AND tar = " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim();
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene la tarifa*/
                String sTarif   = rs.getString("tarif");
                
                /*Dale formato de moneda a la tarifa*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sTarif);                
                sTarif          = n.format(dCant);
                                
                /*Coloca los valores en los controles*/
                jTTar.setText       (rs.getString("tar"));
                jTTarif.setText     (sTarif);                
                jTFUltFac.setText   (rs.getString("ffactu"));                
                
                /*Marca el radio de locatario dependiendo el valor*/
                if(rs.getString("loc").compareTo("1")==0)
                    bG.setSelected(jRLoc.getModel(), true);
                else
                    bG.setSelected(jRExt.getModel(), true);
                
                /*Marca el check de prepago dependiendo el valor*/
                if(rs.getString("prepag").compareTo("1")==0)
                    jCPre.setSelected(true); 
                else
                    jCPre.setSelected(false); 
                
                /*Marca el check de facturar dependiendo el valor*/
                if(rs.getString("factur").compareTo("1")==0)
                    jCFactu.setSelected(true);                                
                else
                    jCFactu.setSelected(false);                                
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
        
    }/*Fin de private void vLoadCamp()*/
                
                
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

    
    /*Cuando se gana el foco del teclado en el campo de la instancia*/
    private void jTInstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTInst.setSelectionStart(0);jTInst.setSelectionEnd(jTInst.getText().length());
        
    }//GEN-LAST:event_jTInstFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la instancia*/
    private void jTInstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTInst.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTInst.getText().compareTo("")!=0)
            jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTInstFocusLost

    
    /*Cuado se presiona una tecla en el campo de la instancia*/
    private void jTInstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTInstKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTInstKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la base de datos*/
    private void jTBDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTBD.setSelectionStart(0);jTBD.setSelectionEnd(jTBD.getText().length());
        
    }//GEN-LAST:event_jTBDFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la base de datos*/
    private void jTBDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBD.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTBD.getText().compareTo("")!=0)
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTBDFocusLost

    
    /*Cuando se presiona una tecla en el campo de la base de datos*/
    private void jTBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBDKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se presiona una tecla en el campo del usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se gana el foco del teclado en campo de la contraseña*/
    private void jPContFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jPCont.setSelectionStart(0);jPCont.setSelectionEnd(jPCont.getText().length());
        
    }//GEN-LAST:event_jPContFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la contraseña*/
    private void jPContFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jPCont.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jPCont.getText().compareTo("")!=0)
            jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jPContFocusLost

    
    /*Cuando se presiona una tecla en campo de la contraseña*/
    private void jPContKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContKeyPressed

    
    /*Cuando el mouse entra en el botón de probar conexión*/
    private void jBProbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProb.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProbMouseEntered

    
    /*Cuando el mouse sale del botón de probar conexión*/
    private void jBProbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProb.setBackground(Star.colOri);

    }//GEN-LAST:event_jBProbMouseExited

    
    /*Cuando se presioba el botón de probar conexión*/
    private void jBProbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbActionPerformed

        //Muestra el loading
        Star.vMostLoading("");

        /*Lee el puerto*/
        String sPort    = jTPort.getText().trim();        
        
        /*Obtiene todos los campos de conexión*/
        final String sInst  = jTInst.getText().trim();
        final String sUsr   = jTUsr.getText().trim();
        final String sPass  = new String(jPCont.getPassword());
        final String sBD    = jTBD.getText().trim();
        final String sPortFi= sPort;

        /*Intenta conectar en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Abre la base de datos
                Connection con = conCon(sInst, sPortFi, sUsr, sBD, sPass);

                //Esconde la forma de loading
                Star.vOcultLoadin();

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                                            
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Conexión exitosa.", "Conexión", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            }
            
        }).start();
        
    }//GEN-LAST:event_jBProbActionPerformed

    
    /*Cuando se presiona una tecla en el botón de probar conexión*/
    private void jBProbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbKeyPressed

    
    /*Cuando sucede una acción en el check de mostrar contraseña*/
    private void jCMosCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMosCActionPerformed

        /*Si esta marcado entonces muestra la contraseña*/
        if(jCMosC.isSelected())
            jPCont.setEchoChar((char)0);
        /*Else, ocultala*/
        else
            jPCont.setEchoChar('*');

    }//GEN-LAST:event_jCMosCActionPerformed

    
    /*Cuando se presiona una tecla en el campo de mostrar contraseña*/
    private void jCMosCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMosCKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del puerto*/
    private void jTPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPort.setSelectionStart(0);jTPort.setSelectionEnd(jTPort.getText().length());

    }//GEN-LAST:event_jTPortFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del puerto*/
    private void jTPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPort.setCaretPosition(0);

    }//GEN-LAST:event_jTPortFocusLost

    
    /*Cuando se presiona una tecla en el campo del puerto*/
    private void jTPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPortKeyPressed

    
    /*Cuando se tipea una tecla en el campo del puerto*/
    private void jTPortKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTPortKeyTyped

    
    /*Cuando se presiona una tecla en el tabbed*/
    private void jTabbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabbKeyPressed

    
    /*Cuando el mouse entra en el botón de salir*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered

        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);

    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón de salir*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);

    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Pregunta al usuario si esta seguro de salir*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();
            this.dispose();
        }

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona uan tecla en la tabla de tickets*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());
        
    }//GEN-LAST:event_jTCliFocusGained

    
    /*Función para limpiar los controles*/
    private void vLimp()
    {
        /*Limpia todos los controles*/
        jTTar.setText       ("");
        jTTarif.setText     ("$0.00");
        jTTarDat.setText    ("");        
        jCFactu.setSelected (false);
        jCPre.setSelected   (false);
        
        /*Borra las partidas de la tabla*/        
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de fila*/
        iContFi = 1;
    }
    
    
    /*Cuando se pierde el foco del teclado en el campo del cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el cliente exista*/        
        try
        {
            sQ = "SELECT nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el nombre en su lugar*/
            if(rs.next())
                jTNom.setText(rs.getString("nom"));
            else
            {
                /*Borra la tabla de tarjetas*/        
                DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
                dm.setRowCount(0);

                /*Resetea el contador de fila*/
                iContFi = 1;
        
                /*Coloca el nombre en cadena vacia*/
                jTNom.setText("");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                

        /*Coloca el caret al principio del control del nombre*/
        jTNom.setCaretPosition(0);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona una tecla en el campo del cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar cliente*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBCli.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se tipea una tecla en el campo del cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en mayùsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Cuando el mouse entra en el botón del cliente*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse sale del botón del cliente*/
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(Star.colOri);

    }//GEN-LAST:event_jBCliMouseExited

    
    /*Cuando se presiona el botón de búscar cliente*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTCli.getText(), 5, jTCli, jTNom, null, "", null);
        b.setVisible(true);

        /*Coloca el nombre el cliente al principio*/
        jTNom.setCaretPosition(0);

        /*Coloca el foco del teclado en el campo del búscar cliente*/
        jBCli.grabFocus();
                
        /*Carga las tarjetas en la tabla*/        
        vCargTar();
                
    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Cuando se presiona una tecla en el botón del cliente*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nombre del cliente*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del nombre*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el caret al principio del control*/
        jTNom.setCaretPosition(0);

    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se presiona una tecla en el campo del nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando el mouse entra en el botón de seleccionar todo*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el botón sale del botón de selecconar todo*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);

    }//GEN-LAST:event_jBTodMouseExited

    
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

    
    /*Cuando el mouse entra en el botón de borrar*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse sale del botón de borrar*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);

    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no a seleccionado un cliente entonces*/
        if(jTCli.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente primeramente.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo y regresa*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        /*Si no a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una tarjeta.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer quitar la tarjeta*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) tarjeta(s)?", "Borrar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)                    
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Bandera para saber si hubo alguna modificación*/
        boolean bSi             = false;

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borra la tarjeta*/
            try 
            {                
                sQ = "DELETE FROM tars WHERE cli = '" + jTCli.getText().trim() + "' AND tar = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";                                                            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Coloca la bandera para saber que si hubo por lo menos una eliminación*/
            bSi = true;
            
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                    
        /*Carga las tarjetas en la tabla nuevamente*/        
        vCargTar();
        
        /*Si hubo eliminaciones entonces mensajea de éxito*/
        if(bSi)
            JOptionPane.showMessageDialog(null, "Tarjeta(s) modificada(s) con éxito.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando el mouse entra en el botón de nuevo*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered

        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);

    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse sale del botón de nuevo*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);

    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando se presiona el botón de nuevo*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si no hay un cliente seleccionado entonces*/
        if(jTCli.getText().trim().compareTo("")==0 && jTNom.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Seleccona un cliente primero.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo*/
            jTCli.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        /*Si no hay tarjeta seleccionada entonces*/
        if(jTTar.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa una tarjeta primero.", "Tarjeta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo*/
            jTTar.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTTar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        /*Si la tarifa es 0 entonces*/
        if(Double.parseDouble(jTTarif.getText().trim().replace(",", "").replace("$", ""))<=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una tarifa válida.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo*/
            jTTarif.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTTarif.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
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
        
        /*Comprueba si el ticket ya esta asignado a ese cliente*/        
        try
        {
            sQ = "SELECT tar FROM tars WHERE cli = '" + jTCli.getText().trim() + "' AND tar = " + jTTar.getText().trim();
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jTTar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "La tarjeta ya esta asignada para este cliente y no se puede asignar.", "Registro existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo del código del peso y regresa*/
                jTTar.grabFocus();               
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                
        
        /*Comprueba si la tarjeta ya esta asignada a algún otro cliente*/        
        try
        {
            sQ = "SELECT cli FROM tars WHERE tar = '" + jTTar.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                
                /*Coloca el borde rojo*/                               
                jTTar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "La tarjeta ya esta asignada al cliente: " + rs.getString("cli") + " y no se puede asignar de nuevo.", "Registro existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                                
                /*Pon el foco del teclado en el campo del código del peso y regresa*/
                jTTar.grabFocus();               
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres agregar la tarjeta?", "Agregar tarjeta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Determina si es localtario o no*/
        String sLoc = "0";
        if(jRLoc.isSelected())
            sLoc    = "1";
        
        /*Determina si es externo o no*/
        String sExt = "0";
        if(jRExt.isSelected())
            sExt    = "1";
        
        /*Determina si es de prepago o no*/
        String sPrePag  = "0";
        if(jCPre.isSelected())
            sPrePag     = "1";
        
        /*Determina si se tiene que facturar o no*/
        String sFactur  = "0";
        if(jCFactu.isSelected())
            sFactur     = "1";
        
        /*Inserta la nueva asignación en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO tars( tar,                                                                                  cli,                                                loc,         exter,        prepag,          factuya,                factur,          tarif,                                                                         estac,                                      sucu,                                     nocaj,                               pag) " + 
                        "VALUES(" + jTTar.getText().replace(" ", "").replace(",", "").trim().replace("'", "''") + ",'" +  jTCli.getText().replace("'", "''").trim() + "'," +  sLoc + "," + sExt + ", " + sPrePag + ",     0,              " +     sFactur + ", " + jTTarif.getText().trim().replace(",", "").replace("$", "").trim() + ", '" +    Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', 0)";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
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
        
        /*Carga las tarjetas en la tabla del cliente nuevamente*/        
        vCargTar();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Tarjeta agregada con éxito.", "Tarjetas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Función para cargar las tarjetas en la tabla*/
    private void vCargTar()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    
        
        /*Borra la tabla*/
        te.setRowCount(0);
        
        /*Resetea el contador de fila*/
        iContFi = 1;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene las tarjetas de la base de datos y cargalas en la tabla*/        
        try
        {
            sQ = "SELECT tar FROM tars WHERE cli = '" + jTCli.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("tar")};
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
        
    }/*Fin de private void vCargTar()*/
    
    
    /*Cuando se presiona una tecla en el botón de nuevo*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
          
    
    /*Cuando se presiona una tecla en el campo de la tarjeta*/
    private void jTTarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTarKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la tarjeta*/
    private void jTTarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTar.setSelectionStart(0);jTTar.setSelectionEnd(jTTar.getText().length());        
        
    }//GEN-LAST:event_jTTarFocusGained

    
    /*Cuando se pierde el foco del teclado en campo de la tarjeta*/
    private void jTTarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTar.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTar.getText().compareTo("")!=0)
            jTTar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTTarFocusLost

    
    /*Cuando se tipea una tecla en el campo de la tarjeta*/
    private void jTTarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTarKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la tarifa*/
    private void jTTarifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarifKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTarifKeyTyped

    
        
    /*Cuando se presiona una tecla en el check de prepago*/
    private void jCPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPreKeyPressed

    
    
    /*Cuando se gana el foco del teclado en el campo de la tarifa*/
    private void jTTarifFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarifFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTarif.setSelectionStart(0);jTTarif.setSelectionEnd(jTTarif.getText().length());        
        
    }//GEN-LAST:event_jTTarifFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la tarifa*/
    private void jTTarifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarifFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTarif.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTarif.getText().compareTo("")!=0)
            jTTarif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTTarifFocusLost

    
    /*Cuando se presiona una tecla en el campo de la tarifa*/
    private void jTTarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarifKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTarifKeyPressed

    
    /*Cuando se presiona una tecla en el panel*/
    private void jPConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPConKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPConKeyPressed

    
    /*Cuando se presiona una tecla en el panel de generales*/
    private void jPGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPGralKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPGralKeyPressed

    
    /*Cuando se presiona una tecla en el check de automatizar*/
    private void jCAutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAutKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCAutKeyPressed

    
    
    /*Cuando se gana el foco del teclado en el campo de la tarjeta de datapark*/
    private void jTTarDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarDatFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTarDat.setSelectionStart(0);jTTarDat.setSelectionEnd(jTTarDat.getText().length());        
        
    }//GEN-LAST:event_jTTarDatFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la tarjeta*/
    private void jTTarDatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarDatFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTarDat.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTarDatFocusLost

    
    /*Cuando se presiona una tecla en el campo de la tarjeta de datapark*/
    private void jTTarDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarDatKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTarDatKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la tarjeta de datapark*/
    private void jTTarDatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarDatKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTarDatKeyTyped

    
    /*Cuando se presiona una tecla en el radio de locatarios*/
    private void jRLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRLocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRLocKeyPressed

    
    /*Cuando se presiona una tecla en el radio de externos*/
    private void jRExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRExtKeyPressed

    
    /*Cuando el mouse entra en el botón de guardar cambios*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered

    
    /*Cuando el mouse sale del botón de guardar cambios*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed
        
        /*Si no a seleccionado un cliente entonces*/
        if(jTCli.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente primeramente.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo y regresa*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        /*Si no a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una tarjeta.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
    
        /*Si la tarifa es 0 entonces*/
        if(Double.parseDouble(jTTarif.getText().trim().replace(",", "").replace("$", ""))<=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una tarifa válida.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo*/
            jTTarif.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTTarif.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer quitar la tarjeta*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar los cambios?", "Guardar cambios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)                    
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Determina si es local o no*/
        String sLoc     = "0";
        if(jRLoc.isSelected())
            sLoc        = "1";
        
        /*Determina si es de prepago o no*/
        String sPre     = "0";
        if(jCPre.isSelected())
            sPre        = "1";
        
        /*Determina si se tiene que facturar o no*/
        String sFactu   = "0";
        if(jCFactu.isSelected())
            sFactu      = "1";

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Actualiza la información de esa tarjeta*/
        try 
        {                
            sQ = "UPDATE tars SET "                    
                    + "loc      = " + sLoc + ", "
                    + "prepag   = " + sPre + ", "
                    + "factur   = " + sFactu + ", "
                    + "tarif    = " + jTTarif.getText().replace("$", "").replace(",", "").trim() + " "
                    + "WHERE cli= '" + jTCli.getText().trim() + "' AND tar = " + jTTar.getText().trim();                                                                        
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cambios guardados con éxito para la tarjeta: " + jTTar.getText().trim(), "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBGuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaKeyPressed

    
    /*Cuando se presiona una tecla en el botón de facturar*/
    private void jCFactuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFactuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFactuKeyPressed

    
    /*Cuando se presiona una tecla en el control del día de corte*/
    private void jSPDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSPDiaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSPDiaKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar cambios todo*/
    private void jBGuaCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaCKeyPressed

    
    /*Cuando el mouse entra en el botón de guardar cambios*/
    private void jBGuaCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaCMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuaC.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaCMouseEntered

    
    /*Cuando el mouse sale del botón de guardar cambios todo*/
    private void jBGuaCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaCMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBGuaC.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuaCMouseExited

    
    //Método para validar que los datos de SQL se allan ingresado correctamente
    private int iCheckSQL()
    {
        /*Si no a ingresado la instancia de SQL entonces*/
        if(jTInst.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la instancia de SQL.", "Instancia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el control y regresa*/
            jTInst.grabFocus();
            
            /*Coloca el borde rojo y regresa error*/                               
            jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return -1;
        }
        
        /*Si no a ingresado el usuario de SQL entonces*/
        if(jTUsr.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa el usuario de SQL.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTUsr.grabFocus();
            
            /*Coloca el borde rojo y regresa error*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return -1;
        }
        
        /*Si no a ingresado la contraseña de SQL entonces*/
        if(jPCont.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la contraseña de SQL.", "Contraseña", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jPCont.grabFocus();
            
            /*Coloca el borde rojo y regresa error*/                               
            jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return -1;
        }
        
        //Regresa que todo va bien
        return 0;
        
    }//Fin de private int iCheckSQL()
    
    
    //Método para abrir la base de datos
    private Connection conCon(String sInst, String sPort, String sUsr, String sBD, String sPass)
    {
        /*Registra el driver*/
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF, expnClassNoF.getStackTrace());            
            return null;
        }
        
        //Si el puerto no es cadena vacia entonces agregale los puntos
        if(sPort.compareTo("")!=0 && sPort.compareTo("0")!=0)
            sPort   = ":" + sPort;
        
        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + sPort + ";user=" + sUsr + ";password=" + sPass + ";database=" + sBD);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());            
            return null;
        }
        
        //Devuelve el resultado
        return con;
        
    }//Fin de private Connection conCon(String sInst, String sPort, String sUsr, String sBD, String sPass)
    
    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuaCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaCActionPerformed

        //Si los datos de conexión a SQL no estan llenos entonces
        if(iCheckSQL()==-1)
            return;

        /*Si no a ingresado un usuario de envio entonces*/
        if(jTUsrMand.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa el usuario de envio.", "Usuario envio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTUsrMand.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTUsrMand.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }        
        
        /*Si no a ingresado una serie entonces*/
        if(jComSer.getSelectedItem().toString().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la serie de la factura.", "Serie factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSer.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }        
        
        /*Si no a ingresado una moneda entonces*/
        if(jComMon.getSelectedItem().toString().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la moneda de la factura.", "Moneda factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComMon.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }        
        
        /*Si no a ingresado un producto entonces*/
        if(jTProd.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un producto para facturar.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            
            /*Coloca el borde rojo y regresa*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }                        

        /*Lee el puerto*/
        String sPort    = jTPort.getText().trim();        
                
        //Abre la base de datos
        Connection con = conCon(jTInst.getText().trim(), sPort, jTUsr.getText().trim(), jTBD.getText().trim(), new String(jPCont.getPassword()).trim());
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;        

        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;                
        ResultSet   rs;
        String      sQ;
        
        //Comprueba si el producto existe        
        int iRes    = Star.iExistProd(con, jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText().trim() + " no existe.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo y regresa*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;            
        }
                        
        //Comprueba si el producto de envio existe
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsrMand.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario de envio: " + jTUsrMand.getText().trim() + " no existe.", "Usuario envio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo y regresa*/                               
                jTUsrMand.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                
        
        /*Preguntar al usuario si esta seguro de querer guardar los datos*/
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar los cambios?", "Guardar cambios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)                    
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Regresa*/
            return;                       
        }
        
        /*Determina si será automático o no*/
        String sAuto    = "0";
        if(jCAut.isSelected())
            sAuto       = "1";
        
        /*Lee el puerto*/
        sPort           = jTPort.getText().trim();        
        
        /*Si el puerto esta vacio que sea 0*/
        if(sPort.compareTo("")==0)
            sPort       = "0";
        
        /*Comprueba si existe la empresa ya en la tabla*/        
        boolean bSi = false;
        try
        {
            sQ = "SELECT codemploc FROM interdpark WHERE codemploc  = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                
        
        /*Crea la consulta correcta dependiendo de si existe o no la empresa local*/
        if(bSi)
            sQ = "UPDATE interdpark SET "
                    + "inst         = '" + jTInst.getText().trim().replace("\\", "\\\\") + "', "
                    + "usr          = '" + jTUsr.getText().trim() + "', "
                    + "usrmand      = '" + jTUsrMand.getText().trim() + "', "
                    + "contra       = '" + Star.sEncrip(new String(jPCont.getPassword())) + "', "
                    + "bd           = '" + jTBD.getText().trim() + "', "                                        
                    + "port         = " + sPort + ", "
                    + "diacort      = " + jSPDia.getValue() + ", "
                    + "automat      = " + sAuto + ","
                    + "diacort      = " + jSPDia.getValue() + ","
                    + "fmod         = now(), "
                    + "serfac       = '" + jComSer.getSelectedItem().toString().trim() + "', "
                    + "prod         = '" + jTProd.getText().trim() + "', "
                    + "mon          = '" + jComMon.getSelectedItem().toString().trim() + "', "                    
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sNoCaj + "', "
                    + "estac        = '" + Login.sUsrG + "'";                    
        else
            sQ = "INSERT INTO interdpark (  inst,                                                       usr,                                contra,                                                     bd,                                 port,               diacort,                    sucu,                   nocaj,                 estac,                 codemploc,                 usrmand,                              serfac,                                                mon,                                                   prod) "
                        + "VALUES('" +      jTInst.getText().trim().replace("\\", "\\\\") + "','" +     jTUsr.getText().trim() + "','" +    Star.sEncrip(new String(jPCont.getPassword())) + "','" +    jTBD.getText().trim() + "', '" +    sPort + "', " +     jSPDia.getValue() + ", '" + Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "', '" + Login.sCodEmpBD + "', '" + jTUsrMand.getText().trim() + "', '" + jComSer.getSelectedItem().toString().trim() + "', '" + jComMon.getSelectedItem().toString().trim() + "', '" + jTProd.getText().trim() + "')";                    
        
        /*Ejecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
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
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cambios guardados con éxito.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBGuaCActionPerformed

    
    /*Cuando el mouse entra en el botón de usuario*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUsrMouseEntered

    
    /*Cuando el mouse sale del botón de usuario*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(Star.colOri);

    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando se presiona el botón de búscar usuario*/
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTUsrMand.getText(), 4, jTUsrMand, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de usuario*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del usuario*/
    private void jTUsrMandFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrMandFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsrMand.setSelectionStart(0);jTUsrMand.setSelectionEnd(jTUsrMand.getText().length());

    }//GEN-LAST:event_jTUsrMandFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del usuario*/
    private void jTUsrMandFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrMandFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsrMand.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTUsrMand.getText().compareTo("")!=0)
            jTUsrMand.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTUsrMandFocusLost

    
    /*Cuando se presiona una tecla en el campo del usuario*/
    private void jTUsrMandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrMandKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            ptovta.Busc b = new ptovta.Busc(this, jTUsrMand.getText(), 4, jTUsrMand, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTUsrMandKeyPressed

    
    /*Cuando se tipea una tecla en el campo del usuario*/
    private void jTUsrMandKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrMandKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTUsrMandKeyTyped

    
    
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
        jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando sucede una acción en el combo de series de facturas*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed

        /*Si es nulo entonces regresa*/
        if(jComSer.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'FAC' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
                        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerActionPerformed

    
    /*Cuando se presiona una tecla en el combo de series*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerKeyPressed

    
    
    
    
    
    
    
    /*Cuando se pierde el foco del teclado en el combo de la moneda*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando sucede una acción en el combo de las monedas*/
    private void jComMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMon.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "mondescrip", "mons", " WHERE mon = '" + jComMon.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComMon.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComMonActionPerformed

    
    /*Cuando se presiona una tecla en el combo de las monedas*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar producto*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del botón de búscar producto*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(Star.colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona el botón de búscar producto*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una teclaen el botón de búscar producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);
        jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained

    
    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar producto*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProd.doClick();
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan w = new ptovta.BuscAvan(this, jTProd, null, null, null);
            w.setVisible(true);

            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost
        
        /*Coloca el caret al principio del control*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la fecha de última factura*/
    private void jTFUltFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFUltFacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFUltFac.setSelectionStart(0);jTFUltFac.setSelectionEnd(jTFUltFac.getText().length());        
        
    }//GEN-LAST:event_jTFUltFacFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de última factura*/
    private void jTFUltFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFUltFacFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFUltFac.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFUltFacFocusLost

    
    /*Cuando se presiona una tecla en el campo de fecha de última factura*/
    private void jTFUltFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFUltFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFUltFacKeyPressed

    
    //Cuando se presiona una tecla en el spiner de D día
    private void jSDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSDiaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSDiaKeyPressed

    
    //Cuando se presiona una tecla en el spiner de D mes
    private void jSMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSMesKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSMesKeyPressed

    
    //Cuando se presiona una tecla en el spin de anio
    private void jSAnioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSAnioKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSAnioKeyPressed

    
    //Cuando se presiona una tecla en el spin de A día
    private void jSDiaAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSDiaAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSDiaAKeyPressed

    
    //Cuando se presiona una tecla en el spin A mes
    private void jSMesAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSMesAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSMesAKeyPressed

    
    //Cuando se presiona una tecla en el spin A año
    private void jSAnioAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSAnioAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSAnioAKeyPressed

    
    //Cuando se presiona una tecla en el botón de generar reporte
    private void jBGenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGenKeyPressed

    
    //Cuando el mouse entra en el botón de generar reporte
    private void jBGenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGen.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGenMouseEntered

    
    //Cuando el mouse sale del botón de generar reporte
    private void jBGenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBGen.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGenMouseExited

    
    //Cuando se presiona una tecla en el botón de reporteador
    private void jPRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPRepKeyPressed

    
    //Cuando se presiona el botón de generar reporte
    private void jBGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGenActionPerformed
        
        //Si los datos de conexión a SQL no estan llenos entonces
        if(iCheckSQL()==-1)
            return;
        
        /*Lee el puerto*/
        String sPort    = jTPort.getText().trim();        
                        
        //Abre la base de datos
        Connection con = conCon(jTInst.getText().trim(), sPort, jTUsr.getText().trim(), jTBD.getText().trim(), new String(jPCont.getPassword()).trim());
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ; 
        
        //Contiene la cantidad de tickets y el total de los mismos
        String sCantTic = "0";
        String sTotTic  = "0";
        
        //Obtiene la cantidad de tickets y el total de los mismos de datapark
        try
        {
            sQ = "SELECT COUNT(mnTotal) AS canttic, SUM(mnTotal) AS tottic FROM trdpfee WHERE dtExitDate BETWEEN '" + jSAnio.getValue().toString() + "-" + jSMes.getValue().toString() + "-" + jSDia.getValue().toString() + " " + jSHor.getValue().toString() + ":" + jSMin.getValue().toString() + ":" + jSSeg.getValue().toString() + ".000' AND '" + jSAnioA.getValue().toString() + "-" + jSMesA.getValue().toString() + "-" + jSDiaA.getValue().toString() + " " + jSHorA.getValue().toString() + ":" + jSMinA.getValue().toString() + ":" + jSSegA.getValue().toString() + ".000' AND inttrtype = 1";                                               
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {                
                sCantTic    = rs.getString("canttic");
                sTotTic     = rs.getString("tottic");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        //Contiene la cantidad de tickets totales y el total de los mismos totales
        String sCantTicTot  = "0";
        String sTotTicTot   = "0";
        
        //Obtiene la cantidad de tickets y el total de los mismos de datapark
        try
        {
            sQ = "SELECT COUNT(mnTotal) AS canttic, SUM(mnTotal) AS tottic FROM trdpfee WHERE dtExitDate BETWEEN '" + jSAnio.getValue().toString() + "-" + jSMes.getValue().toString() + "-" + jSDia.getValue().toString() + " " + jSHor.getValue().toString() + ":" + jSMin.getValue().toString() + ":" + jSSeg.getValue().toString() + ".000' AND '" + jSAnioA.getValue().toString() + "-" + jSMesA.getValue().toString() + "-" + jSDiaA.getValue().toString() + " " + jSHorA.getValue().toString() + ":" + jSMinA.getValue().toString() + ":" + jSSegA.getValue().toString() + ".000'";                                                           
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {                                                
                sCantTicTot = rs.getString("canttic");
                sTotTicTot  = rs.getString("tottic");
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

        //Obtiene las cantidades y totales cancelados
        sTotTicTot                  = Double.toString(Double.parseDouble(sTotTicTot) - Double.parseDouble(sTotTic));
        sCantTicTot                 = Double.toString(Double.parseDouble(sCantTicTot) - Double.parseDouble(sCantTic));
        
        //Dale formato de moneda a los totales        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sTotTic);                
        sTotTic         = n.format(dCant);
        dCant           = Double.parseDouble(sTotTicTot);                
        sTotTicTot      = n.format(dCant);
        
        //Crea las variables final para el reporte
        final String sFDeFi         = jSAnio.getValue().toString() + "-" + jSMes.getValue().toString() + "-" + jSDia.getValue().toString() + " T:" + jSHor.getValue().toString() + ":" + jSMin.getValue().toString() + ":" + jSSeg.getValue().toString();
        final String sFAFi          = jSAnioA.getValue().toString() + "-" + jSMesA.getValue().toString() + "-" + jSDiaA.getValue().toString() + " T:" + jSHorA.getValue().toString() + ":" + jSMinA.getValue().toString() + ":" + jSSegA.getValue().toString();
        final String sTotTicFi      = sTotTic;
        final String sCantTicFi     = sCantTic;
        final String sTotTicTotFi   = sTotTicTot;
        final String sCantTicTotFi  = sCantTicTot;
        
        /*Crea el thread para cargar el reporte en un hilo aparte*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Abre la base de datos                             
                Connection  con2 = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con2==null)
                    return;

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();
                    pa.clear();
                    pa.put("TOTTIC",        sTotTicFi);
                    pa.put("CANTTIC",       sCantTicFi);
                    pa.put("TOTTICCA",      sTotTicTotFi);
                    pa.put("CANTICCA",      sCantTicTotFi);
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());

                    /*Compila el reporte y maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptDP.jrxml"));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con2);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Pensi");
                    v.setIconImage(newimg);
                    v.setVisible(true);

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con2);                    
                    return;
                }

                //Cierra la base de datos
                Star.iCierrBas(con2);
                
            }/*Fin de public void run()*/
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jBGenActionPerformed

    
    //Cuando se presiona una tecla en el control de la hora D
    private void jSHorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSHorKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSHorKeyPressed

    
    //Cuando se presiona una tecla en el control de la hora A
    private void jSHorAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSHorAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSHorAKeyPressed

    
    //Cuando se presiona una tecla en el control del minúto D
    private void jSMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSMinKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSMinKeyPressed

    
    //Cuando se presiona una tecla en el control del minúto A
    private void jSMinAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSMinAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSMinAKeyPressed

    
    //Cuando se presiona una tecla en el spin de segundos D
    private void jSSegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSSegKeyPressed
        
       //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSSegKeyPressed

    
    //Cuando se presiona una tecla en el spin de segundos
    private void jSSegAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSSegAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSSegAKeyPressed
          
            
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)                    
            jBNew.doClick();        
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGen;
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBGuaC;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProb;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCAut;
    private javax.swing.JCheckBox jCFactu;
    private javax.swing.JCheckBox jCMosC;
    private javax.swing.JCheckBox jCPre;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPCon;
    private javax.swing.JPasswordField jPCont;
    private javax.swing.JPanel jPGral;
    private javax.swing.JPanel jPRep;
    private javax.swing.JPanel jPTars;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRExt;
    private javax.swing.JRadioButton jRLoc;
    private javax.swing.JSpinner jSAnio;
    private javax.swing.JSpinner jSAnioA;
    private javax.swing.JSpinner jSDia;
    private javax.swing.JSpinner jSDiaA;
    private javax.swing.JSpinner jSHor;
    private javax.swing.JSpinner jSHorA;
    private javax.swing.JSpinner jSMes;
    private javax.swing.JSpinner jSMesA;
    private javax.swing.JSpinner jSMin;
    private javax.swing.JSpinner jSMinA;
    private javax.swing.JSpinner jSPDia;
    private javax.swing.JSpinner jSSeg;
    private javax.swing.JSpinner jSSegA;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBD;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTFUltFac;
    private javax.swing.JTextField jTInst;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPort;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTTar;
    private javax.swing.JTextField jTTarDat;
    private javax.swing.JTextField jTTarif;
    private javax.swing.JTextField jTUsr;
    private javax.swing.JTextField jTUsrMand;
    private javax.swing.JTable jTab;
    private javax.swing.JTabbedPane jTabb;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
