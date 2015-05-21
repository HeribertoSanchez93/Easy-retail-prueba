//Paquete
package ptovta;

//Importaciones
import java.awt.Desktop;
import java.text.NumberFormat;
import java.awt.HeadlessException;
import java.util.Locale;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import static ptovta.Princip.bIdle;




/*Clase para controlar las configuraciones adicionales del inventario*/
public class ConfGral extends javax.swing.JFrame 
{
    /*Contiene los errores de la aplicación*/
    private boolean                 bErr    = false;
    
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Declara variables de clase*/
    static boolean                  bSi;
    
    
    
    /*Constructor sin argumentos*/
    public ConfGral() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicializa en false la bandera*/
        bSi = false;
        
        /*Establece el título de los tags*/
        jTBP.setTitleAt(0, "Inventarios");
        jTBP.setTitleAt(1, "Facturas");                
        jTBP.setTitleAt(2, "Cotizaciones");                
        jTBP.setTitleAt(3, "Sistema");                
        jTBP.setTitleAt(4, "Compras");                
        jTBP.setTitleAt(5, "Punto de venta");                
        jTBP.setTitleAt(6, "Remisiones");                
        jTBP.setTitleAt(7, "Tickets");                
        jTBP.setTitleAt(8, "Chat"); 
        jTBP.setTitleAt(9, "Previos de compra"); 
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Configuraciones Generales, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Coloca el foco del teclado en el primer control*/
        jBTBajCost.grabFocus();
    
        /*Carga todas las series de las facturas, tickets y remisiones en el control*/
        vCargSerF("FAC", jComSerF);                               
        vCargSerF("TIK", jComSerT);                               
        vCargSerF("REM", jComSerR);                               
        vCargSerF("FAC", jComSerFac);
        
        /*Carga las monedas en las facturas fija*/
        vCargMon(jComMonFac);
        
        /*Listener para el combobox de monedas*/
        jComMonFac.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga las monedas en el combobox*/
                vCargMon(jComMonFac);                
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
        jComSerF.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga todas las series  en el control*/
                vCargSerF("FAC", jComSerF);                               
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
        
        /*Listener para el combobox de series de facturas fijo*/
        jComSerFac.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga nuevamente las series en el control*/                
                vCargSerF("FAC", jComSerFac);                               
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
        
        /*Listener para el combobox de series de tickets*/
        jComSerT.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga todas las series en el control*/
                vCargSerF("TIK", jComSerT);                               
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
        
        /*Listener para el combobox de series de remisiones*/
        jComSerR.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga todas las series en el control*/
                vCargSerF("REM", jComSerR);                               
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
        
        /*Carga las configuraciones de ventas*/
        vCargVtas();
        
        //Carga la configuración de inventarios
        vCargInv();
        
        /*Carga las configuraciones de cotizaciones*/
        vCargCots();
        
        /*Carga las configuraciones de sistema*/
        vCargSist();
        
        /*Carga las configuraciones de compras*/
        vCargComps();
        
        //Carga las configuraciones del previo de compras
        vCargPrevComp();
                
    }/*Fin de public ConfGral() */       
    
    
    /*Método para cargar las monedas en la configuración*/
    private void vCargMon(javax.swing.JComboBox jCom)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todas las monedas en el combo
        if(Star.iCargMonCom(con, jCom)==-1)
            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);                
    }
    
    
    /*Función para cargar todas las series de las facturas en el control*/
    private void vCargSerF(String sTip, javax.swing.JComboBox jCom)
    {
        /*Borra los items en el combobox de series*/
        jCom.removeAllItems();

        /*Agrega el elemento vacio*/
        jCom.addItem("");
        
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
            sQ = "SELECT ser FROM consecs WHERE tip = '" + sTip + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("ser"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private vois vCargSerF()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTBP = new javax.swing.JTabbedPane(3);
        jPInven = new javax.swing.JPanel();
        jBTBajCost = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jBTNoBajCost = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBMaxMin = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jBNoMaxMin = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jCESExistMov = new javax.swing.JCheckBox();
        jCTraspasExis = new javax.swing.JCheckBox();
        jCSerRep = new javax.swing.JCheckBox();
        jPFac = new javax.swing.JPanel();
        jCPListCFac = new javax.swing.JCheckBox();
        jCMMEFac = new javax.swing.JCheckBox();
        jCVendSinExistFac = new javax.swing.JCheckBox();
        jCCatGralFac = new javax.swing.JCheckBox();
        jCMosLogFac = new javax.swing.JCheckBox();
        jCLimCredFac = new javax.swing.JCheckBox();
        jCVtasXUsr = new javax.swing.JCheckBox();
        jCVendMN = new javax.swing.JCheckBox();
        jCVFacPto = new javax.swing.JCheckBox();
        jCImpFacPto = new javax.swing.JCheckBox();
        jCCanFacPto = new javax.swing.JCheckBox();
        jCMosTicCanF = new javax.swing.JCheckBox();
        jCGuaPDFCanF = new javax.swing.JCheckBox();
        jCClavFac = new javax.swing.JCheckBox();
        jTClavFac = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jCModPrecFac = new javax.swing.JCheckBox();
        jCModDescripFac = new javax.swing.JCheckBox();
        jCGaraF = new javax.swing.JCheckBox();
        jCModListF = new javax.swing.JCheckBox();
        jLabel27 = new javax.swing.JLabel();
        jTAlmaVta = new javax.swing.JTextField();
        jBAlmaVta = new javax.swing.JButton();
        jCMinFac = new javax.swing.JCheckBox();
        jTMinFac = new javax.swing.JTextField();
        jComSerFac = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jComMonFac = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        jPCot = new javax.swing.JPanel();
        jCPLCot = new javax.swing.JCheckBox();
        jCMMECot = new javax.swing.JCheckBox();
        jCVSinExistCot = new javax.swing.JCheckBox();
        jCCotsXUsr = new javax.swing.JCheckBox();
        jCCotMN = new javax.swing.JCheckBox();
        jCModDescripCot = new javax.swing.JCheckBox();
        jCModPrecCot = new javax.swing.JCheckBox();
        jCGaraC = new javax.swing.JCheckBox();
        jCModListC = new javax.swing.JCheckBox();
        jLabel28 = new javax.swing.JLabel();
        jTAlmaCot = new javax.swing.JTextField();
        jBAlmaCot = new javax.swing.JButton();
        jPSis = new javax.swing.JPanel();
        jCDesInac = new javax.swing.JCheckBox();
        jTMin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jBBuscCum = new javax.swing.JButton();
        jBCargCum = new javax.swing.JButton();
        jBDelCum = new javax.swing.JButton();
        jTCumple = new javax.swing.JTextField();
        jTAsun = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jBVerAgra = new javax.swing.JButton();
        jBCargAgra = new javax.swing.JButton();
        jBDelAgra = new javax.swing.JButton();
        jTAsunAgra = new javax.swing.JTextField();
        jTCuerAgra = new javax.swing.JTextField();
        jTDias = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTRutCalc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jBCalc = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTRutCuade = new javax.swing.JTextField();
        jBCuade = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTRutAp = new javax.swing.JTextField();
        jBApFav = new javax.swing.JButton();
        jTUsrCump = new javax.swing.JTextField();
        jBUsrCump = new javax.swing.JButton();
        jTUsrAgra = new javax.swing.JTextField();
        jBUsrAgra = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jBVerCXC1 = new javax.swing.JButton();
        jBCXC1 = new javax.swing.JButton();
        jBDelCXC1 = new javax.swing.JButton();
        jBVerCXC2 = new javax.swing.JButton();
        jBCXC2 = new javax.swing.JButton();
        jBDelCXC2 = new javax.swing.JButton();
        jBVerCXC3 = new javax.swing.JButton();
        jBCXC3 = new javax.swing.JButton();
        jBDelCXC3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jCUsrMulti = new javax.swing.JCheckBox();
        jCMosMsjConfCorr = new javax.swing.JCheckBox();
        jCDefTipCamSis = new javax.swing.JCheckBox();
        jCRedon = new javax.swing.JCheckBox();
        jTRedon = new javax.swing.JTextField();
        jPComp = new javax.swing.JPanel();
        jCCompXUsr = new javax.swing.JCheckBox();
        jCCompMN = new javax.swing.JCheckBox();
        jCModDescripComp = new javax.swing.JCheckBox();
        jCModPrecComp = new javax.swing.JCheckBox();
        jCIniOrd = new javax.swing.JCheckBox();
        jPPtoVta = new javax.swing.JPanel();
        jCCatGralPvta = new javax.swing.JCheckBox();
        jCPLEPvta = new javax.swing.JCheckBox();
        jCMMEPvta = new javax.swing.JCheckBox();
        jCVSinExistPvta = new javax.swing.JCheckBox();
        jCBarrLatP = new javax.swing.JCheckBox();
        jCImgLin = new javax.swing.JCheckBox();
        jCInitPvta = new javax.swing.JCheckBox();
        jCLimCredPVta = new javax.swing.JCheckBox();
        jCEmpsPtoVta = new javax.swing.JCheckBox();
        jCDescrip = new javax.swing.JCheckBox();
        jCAdmCan = new javax.swing.JCheckBox();
        jCMsjPto = new javax.swing.JCheckBox();
        jCChatPtoC = new javax.swing.JCheckBox();
        jTDinCaj = new javax.swing.JTextField();
        jCInsAutCaj = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jTClavFacP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jCClavFacP = new javax.swing.JCheckBox();
        jCCortXAut = new javax.swing.JCheckBox();
        jCDiaVtaP = new javax.swing.JCheckBox();
        jCCortXA = new javax.swing.JCheckBox();
        jCCortZA = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jComSerF = new javax.swing.JComboBox();
        jComSerT = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jComSerR = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jTAlma = new javax.swing.JTextField();
        jBAlma = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jCGaraP = new javax.swing.JCheckBox();
        jCDevPVtaPto = new javax.swing.JCheckBox();
        jCDevVtaPto = new javax.swing.JCheckBox();
        jCFacPto = new javax.swing.JCheckBox();
        jCRemPto = new javax.swing.JCheckBox();
        jPRem = new javax.swing.JPanel();
        jCMosLogRem = new javax.swing.JCheckBox();
        jCLimCredRem = new javax.swing.JCheckBox();
        jCVRemPto = new javax.swing.JCheckBox();
        jCImpRemPto = new javax.swing.JCheckBox();
        jCCanRemPto = new javax.swing.JCheckBox();
        jPTic = new javax.swing.JPanel();
        jCMosLogTik = new javax.swing.JCheckBox();
        jCVTicPto = new javax.swing.JCheckBox();
        jCImpTicPto = new javax.swing.JCheckBox();
        jCCanTicPto = new javax.swing.JCheckBox();
        jCMosTicCanPto = new javax.swing.JCheckBox();
        jCGuaPDFCanPto = new javax.swing.JCheckBox();
        jPChat = new javax.swing.JPanel();
        jBDelHisCor = new javax.swing.JButton();
        jBDelHisEstac = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jBPrevUsuario = new javax.swing.JCheckBox();
        jBPrevModDesc = new javax.swing.JCheckBox();
        jBPrevMonNacional = new javax.swing.JCheckBox();
        jBPrevModPrec = new javax.swing.JCheckBox();
        jBPrevCotunaSerie = new javax.swing.JCheckBox();
        jBPrevSerObli = new javax.swing.JCheckBox();
        jBGuar = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 570, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Configuraciones Generales");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, -1));

        jTBP.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTBP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBPKeyPressed(evt);
            }
        });

        jPInven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPInvenKeyPressed(evt);
            }
        });
        jPInven.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBTBajCost.setBackground(new java.awt.Color(255, 255, 255));
        jBTBajCost.setText("+");
        jBTBajCost.setNextFocusableComponent(jBTNoBajCost);
        jBTBajCost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTBajCostMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTBajCostMouseExited(evt);
            }
        });
        jBTBajCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTBajCostActionPerformed(evt);
            }
        });
        jBTBajCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTBajCostKeyPressed(evt);
            }
        });
        jPInven.add(jBTBajCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Todos los productos SI se pueden vender a bajo del costo");
        jPInven.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 430, 20));

        jBTNoBajCost.setBackground(new java.awt.Color(255, 255, 255));
        jBTNoBajCost.setText("-");
        jBTNoBajCost.setNextFocusableComponent(jBMaxMin);
        jBTNoBajCost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTNoBajCostMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTNoBajCostMouseExited(evt);
            }
        });
        jBTNoBajCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTNoBajCostActionPerformed(evt);
            }
        });
        jBTNoBajCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTNoBajCostKeyPressed(evt);
            }
        });
        jPInven.add(jBTNoBajCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("Todos los productos NO se pueden vender a bajo del costo");
        jPInven.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 33, 460, 20));

        jBMaxMin.setBackground(new java.awt.Color(255, 255, 255));
        jBMaxMin.setText("+");
        jBMaxMin.setNextFocusableComponent(jBNoMaxMin);
        jBMaxMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMaxMinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMaxMinMouseExited(evt);
            }
        });
        jBMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMaxMinActionPerformed(evt);
            }
        });
        jBMaxMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMaxMinKeyPressed(evt);
            }
        });
        jPInven.add(jBMaxMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 40, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel21.setText("Todos los productos SI solicitan máximo y mínimo");
        jPInven.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 460, 20));

        jBNoMaxMin.setBackground(new java.awt.Color(255, 255, 255));
        jBNoMaxMin.setText("-");
        jBNoMaxMin.setNextFocusableComponent(jCESExistMov);
        jBNoMaxMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNoMaxMinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNoMaxMinMouseExited(evt);
            }
        });
        jBNoMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNoMaxMinActionPerformed(evt);
            }
        });
        jBNoMaxMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNoMaxMinKeyPressed(evt);
            }
        });
        jPInven.add(jBNoMaxMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 40, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel26.setText("Todos los productos NO solicitan máximo y mínimo");
        jPInven.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 460, 20));

        jCESExistMov.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCESExistMov.setText("Salidas por concepto con existencias solamente");
        jCESExistMov.setNextFocusableComponent(jCTraspasExis);
        jCESExistMov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCESExistMovKeyPressed(evt);
            }
        });
        jPInven.add(jCESExistMov, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 430, -1));

        jCTraspasExis.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCTraspasExis.setText("Traspasos con existencias solamente");
        jCTraspasExis.setName(""); // NOI18N
        jCTraspasExis.setNextFocusableComponent(jCSerRep);
        jCTraspasExis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCTraspasExisKeyPressed(evt);
            }
        });
        jPInven.add(jCTraspasExis, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 350, -1));

        jCSerRep.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCSerRep.setText("Permitir almacenar productos con series iguales");
        jCSerRep.setName(""); // NOI18N
        jCSerRep.setNextFocusableComponent(jBTBajCost);
        jCSerRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCSerRepKeyPressed(evt);
            }
        });
        jPInven.add(jCSerRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 350, -1));

        jTBP.addTab("tab1", jPInven);

        jPFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPFacKeyPressed(evt);
            }
        });
        jPFac.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCPListCFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCPListCFac.setText("Aplicar P. lista de clientes en factura");
        jCPListCFac.setNextFocusableComponent(jCMMEFac);
        jCPListCFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPListCFacKeyPressed(evt);
            }
        });
        jPFac.add(jCPListCFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, -1));

        jCMMEFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMMEFac.setText("Mostrar mensaje si no hay existencia en facturas");
        jCMMEFac.setNextFocusableComponent(jCVendSinExistFac);
        jCMMEFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMMEFacKeyPressed(evt);
            }
        });
        jPFac.add(jCMMEFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 300, -1));

        jCVendSinExistFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVendSinExistFac.setText("Vender sin existencia en facturas");
        jCVendSinExistFac.setNextFocusableComponent(jCCatGralFac);
        jCVendSinExistFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVendSinExistFacKeyPressed(evt);
            }
        });
        jPFac.add(jCVendSinExistFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 290, -1));

        jCCatGralFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCatGralFac.setText("Catálogo general en facturas");
        jCCatGralFac.setName(""); // NOI18N
        jCCatGralFac.setNextFocusableComponent(jCMosLogFac);
        jCCatGralFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCatGralFacKeyPressed(evt);
            }
        });
        jPFac.add(jCCatGralFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 250, -1));

        jCMosLogFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosLogFac.setText("Mostrar logo en facturas y remisiones");
        jCMosLogFac.setNextFocusableComponent(jCLimCredFac);
        jCMosLogFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosLogFacKeyPressed(evt);
            }
        });
        jPFac.add(jCMosLogFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 290, -1));

        jCLimCredFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCLimCredFac.setText("Vender sobre límite de crédito en facturas");
        jCLimCredFac.setNextFocusableComponent(jCVtasXUsr);
        jCLimCredFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCLimCredFacActionPerformed(evt);
            }
        });
        jCLimCredFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCLimCredFacKeyPressed(evt);
            }
        });
        jPFac.add(jCLimCredFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 310, -1));

        jCVtasXUsr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVtasXUsr.setText("Mostrar solo ventas de usuario");
        jCVtasXUsr.setNextFocusableComponent(jCVendMN);
        jCVtasXUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVtasXUsrKeyPressed(evt);
            }
        });
        jPFac.add(jCVtasXUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, -1));

        jCVendMN.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVendMN.setText("Vender solo en moneda nacional");
        jCVendMN.setNextFocusableComponent(jCCortXA);
        jCVendMN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVendMNKeyPressed(evt);
            }
        });
        jPFac.add(jCVendMN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 240, -1));

        jCVFacPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVFacPto.setText("Mostrar facturas en buscador del punto de venta");
        jCVFacPto.setNextFocusableComponent(jCImpFacPto);
        jCVFacPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVFacPtoKeyPressed(evt);
            }
        });
        jPFac.add(jCVFacPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 320, -1));

        jCImpFacPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCImpFacPto.setText("Se pueden imprimir facturas en punto de venta");
        jCImpFacPto.setNextFocusableComponent(jCCanFacPto);
        jCImpFacPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpFacPtoKeyPressed(evt);
            }
        });
        jPFac.add(jCImpFacPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 270, -1));

        jCCanFacPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCanFacPto.setText("Se pueden cancelar/devolver facturas en el punto de venta");
        jCCanFacPto.setNextFocusableComponent(jCMosTicCanF);
        jCCanFacPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCanFacPtoKeyPressed(evt);
            }
        });
        jPFac.add(jCCanFacPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 300, -1));

        jCMosTicCanF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosTicCanF.setText("Mostrar ticket de cancelación o devolución en facturas");
        jCMosTicCanF.setNextFocusableComponent(jCGuaPDFCanF);
        jCMosTicCanF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosTicCanFKeyPressed(evt);
            }
        });
        jPFac.add(jCMosTicCanF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 320, -1));

        jCGuaPDFCanF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGuaPDFCanF.setText("Guardar PDF de cancelación o devolución en facturas");
        jCGuaPDFCanF.setNextFocusableComponent(jCClavFac);
        jCGuaPDFCanF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaPDFCanFKeyPressed(evt);
            }
        });
        jPFac.add(jCGuaPDFCanF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 300, -1));

        jCClavFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCClavFac.setText("Pedir clave de seguridad cada que se factura");
        jCClavFac.setNextFocusableComponent(jTClavFac);
        jCClavFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCClavFacKeyPressed(evt);
            }
        });
        jPFac.add(jCClavFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 290, -1));

        jTClavFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClavFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClavFacFocusGained(evt);
            }
        });
        jTClavFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClavFacKeyPressed(evt);
            }
        });
        jPFac.add(jTClavFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 160, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("Clave:");
        jPFac.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 60, -1));

        jCModPrecFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModPrecFac.setText("Modificar precio del producto");
        jCModPrecFac.setNextFocusableComponent(jCGaraF);
        jCModPrecFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModPrecFacKeyPressed(evt);
            }
        });
        jPFac.add(jCModPrecFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 240, -1));

        jCModDescripFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModDescripFac.setText("Modificar descripción del producto");
        jCModDescripFac.setNextFocusableComponent(jCModPrecFac);
        jCModDescripFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModDescripFacKeyPressed(evt);
            }
        });
        jPFac.add(jCModDescripFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 240, -1));

        jCGaraF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGaraF.setText("Agregar garantía de producto en descripción (aplica igual remisiones)");
        jCGaraF.setNextFocusableComponent(jCModListF);
        jCGaraF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGaraFKeyPressed(evt);
            }
        });
        jPFac.add(jCGaraF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 370, -1));

        jCModListF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModListF.setText("Modificar lista de precio");
        jCModListF.setNextFocusableComponent(jTAlmaVta);
        jCModListF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModListFKeyPressed(evt);
            }
        });
        jPFac.add(jCModListF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 240, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel27.setText("Moneda de facturas fija:");
        jPFac.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 130, 20));

        jTAlmaVta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlmaVta.setNextFocusableComponent(jBAlmaVta);
        jTAlmaVta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAlmaVtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAlmaVtaFocusLost(evt);
            }
        });
        jTAlmaVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAlmaVtaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAlmaVtaKeyTyped(evt);
            }
        });
        jPFac.add(jTAlmaVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 100, 20));

        jBAlmaVta.setBackground(new java.awt.Color(255, 255, 255));
        jBAlmaVta.setText("jButton1");
        jBAlmaVta.setToolTipText("Buscar Almacén(es)");
        jBAlmaVta.setNextFocusableComponent(jCMinFac);
        jBAlmaVta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAlmaVtaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAlmaVtaMouseExited(evt);
            }
        });
        jBAlmaVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlmaVtaActionPerformed(evt);
            }
        });
        jBAlmaVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAlmaVtaKeyPressed(evt);
            }
        });
        jPFac.add(jBAlmaVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 30, 20));

        jCMinFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMinFac.setText("Mínimo a facturar:");
        jCMinFac.setNextFocusableComponent(jTMinFac);
        jCMinFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMinFacKeyPressed(evt);
            }
        });
        jPFac.add(jCMinFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 140, -1));

        jTMinFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMinFac.setNextFocusableComponent(jComSerFac);
        jTMinFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMinFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMinFacFocusLost(evt);
            }
        });
        jTMinFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMinFacKeyPressed(evt);
            }
        });
        jPFac.add(jTMinFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 100, 20));

        jComSerFac.setNextFocusableComponent(jComMonFac);
        jComSerFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFacFocusLost(evt);
            }
        });
        jComSerFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerFacActionPerformed(evt);
            }
        });
        jComSerFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerFacKeyPressed(evt);
            }
        });
        jPFac.add(jComSerFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 100, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel29.setText("Almacén de venta:");
        jPFac.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 100, 20));

        jComMonFac.setNextFocusableComponent(jCPListCFac);
        jComMonFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComMonFacFocusLost(evt);
            }
        });
        jComMonFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMonFacActionPerformed(evt);
            }
        });
        jComMonFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMonFacKeyPressed(evt);
            }
        });
        jPFac.add(jComMonFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 90, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel30.setText("Serie de facturas fija:");
        jPFac.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 130, 20));

        jTBP.addTab("tab2", jPFac);

        jPCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPCotKeyPressed(evt);
            }
        });
        jPCot.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCPLCot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCPLCot.setText("Aplicar precio de lista de clientes en cotizaciones");
        jCPLCot.setNextFocusableComponent(jCMMECot);
        jCPLCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPLCotKeyPressed(evt);
            }
        });
        jPCot.add(jCPLCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, -1));

        jCMMECot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMMECot.setText("Mostrar mensaje si no hay existencia en cotización");
        jCMMECot.setNextFocusableComponent(jCVSinExistCot);
        jCMMECot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMMECotKeyPressed(evt);
            }
        });
        jPCot.add(jCMMECot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 310, -1));

        jCVSinExistCot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVSinExistCot.setText("Vender sin existencias en cotización");
        jCVSinExistCot.setNextFocusableComponent(jCCotsXUsr);
        jCVSinExistCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVSinExistCotKeyPressed(evt);
            }
        });
        jPCot.add(jCVSinExistCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 310, -1));

        jCCotsXUsr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCotsXUsr.setText("Mostrar solo cotizaciones de usuario");
        jCCotsXUsr.setNextFocusableComponent(jCCotMN);
        jCCotsXUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCotsXUsrKeyPressed(evt);
            }
        });
        jPCot.add(jCCotsXUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 250, -1));

        jCCotMN.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCotMN.setText("Cotizar solo en moneda nacional");
        jCCotMN.setNextFocusableComponent(jCModDescripCot);
        jCCotMN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCotMNKeyPressed(evt);
            }
        });
        jPCot.add(jCCotMN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 270, -1));

        jCModDescripCot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModDescripCot.setText("Modificar descripción del producto");
        jCModDescripCot.setNextFocusableComponent(jCModPrecCot);
        jCModDescripCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModDescripCotKeyPressed(evt);
            }
        });
        jPCot.add(jCModDescripCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 240, -1));

        jCModPrecCot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModPrecCot.setText("Modificar precio del producto");
        jCModPrecCot.setNextFocusableComponent(jCGaraC);
        jCModPrecCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModPrecCotKeyPressed(evt);
            }
        });
        jPCot.add(jCModPrecCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 240, -1));

        jCGaraC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGaraC.setText("Agregar garantía de producto en descripción");
        jCGaraC.setNextFocusableComponent(jCModListC);
        jCGaraC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGaraCKeyPressed(evt);
            }
        });
        jPCot.add(jCGaraC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 270, -1));

        jCModListC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModListC.setText("Modificar lista de precio");
        jCModListC.setName(""); // NOI18N
        jCModListC.setNextFocusableComponent(jTAlmaCot);
        jCModListC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModListCKeyPressed(evt);
            }
        });
        jPCot.add(jCModListC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 240, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel28.setText("Almacén cotización:");
        jPCot.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 100, -1));

        jTAlmaCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlmaCot.setNextFocusableComponent(jBAlmaCot);
        jTAlmaCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAlmaCotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAlmaCotFocusLost(evt);
            }
        });
        jTAlmaCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAlmaCotKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAlmaCotKeyTyped(evt);
            }
        });
        jPCot.add(jTAlmaCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 100, 20));

        jBAlmaCot.setBackground(new java.awt.Color(255, 255, 255));
        jBAlmaCot.setText("jButton1");
        jBAlmaCot.setToolTipText("Buscar Almacén(es)");
        jBAlmaCot.setNextFocusableComponent(jCPLCot);
        jBAlmaCot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAlmaCotMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAlmaCotMouseExited(evt);
            }
        });
        jBAlmaCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlmaCotActionPerformed(evt);
            }
        });
        jBAlmaCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAlmaCotKeyPressed(evt);
            }
        });
        jPCot.add(jBAlmaCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 30, 20));

        jTBP.addTab("tab3", jPCot);

        jPSis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPSisKeyPressed(evt);
            }
        });
        jPSis.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCDesInac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDesInac.setText("Suspender en inactividad");
        jCDesInac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCDesInacActionPerformed(evt);
            }
        });
        jCDesInac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDesInacKeyPressed(evt);
            }
        });
        jPSis.add(jCDesInac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, -1));

        jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMin.setNextFocusableComponent(jBCargCum);
        jTMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMinFocusLost(evt);
            }
        });
        jTMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMinKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMinKeyTyped(evt);
            }
        });
        jPSis.add(jTMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 32, 40, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("Asunto");
        jPSis.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 50, -1));

        jBBuscCum.setBackground(new java.awt.Color(255, 255, 255));
        jBBuscCum.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBBuscCum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBBuscCum.setToolTipText("Ver Imágen en Tamaño Completo");
        jBBuscCum.setNextFocusableComponent(jTUsrCump);
        jBBuscCum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscCumActionPerformed(evt);
            }
        });
        jBBuscCum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscCumKeyPressed(evt);
            }
        });
        jPSis.add(jBBuscCum, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 91, 20, 16));

        jBCargCum.setBackground(new java.awt.Color(255, 255, 255));
        jBCargCum.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBCargCum.setText("Cargar");
        jBCargCum.setNextFocusableComponent(jBDelCum);
        jBCargCum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargCumActionPerformed(evt);
            }
        });
        jBCargCum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargCumKeyPressed(evt);
            }
        });
        jPSis.add(jBCargCum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, 70, 16));

        jBDelCum.setBackground(new java.awt.Color(255, 255, 255));
        jBDelCum.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelCum.setText("Borrar");
        jBDelCum.setNextFocusableComponent(jBBuscCum);
        jBDelCum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelCumActionPerformed(evt);
            }
        });
        jBDelCum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelCumKeyPressed(evt);
            }
        });
        jPSis.add(jBDelCum, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 91, 70, 16));

        jTCumple.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTCumple.setForeground(new java.awt.Color(0, 102, 0));
        jTCumple.setToolTipText("Automàticamente se agrega el nombre de la empresa al final del texto escrito");
        jTCumple.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCumple.setNextFocusableComponent(jBCargAgra);
        jTCumple.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCumpleFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCumpleFocusLost(evt);
            }
        });
        jTCumple.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCumpleKeyPressed(evt);
            }
        });
        jPSis.add(jTCumple, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 160, 20));

        jTAsun.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTAsun.setForeground(new java.awt.Color(0, 102, 0));
        jTAsun.setToolTipText("Asunto del Correo de Cumpleaños");
        jTAsun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsun.setNextFocusableComponent(jTCumple);
        jTAsun.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunFocusLost(evt);
            }
        });
        jTAsun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunKeyPressed(evt);
            }
        });
        jPSis.add(jTAsun, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 160, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("Minutos");
        jPSis.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 70, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("Cuerpo");
        jPSis.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("Ruta aplicación favorita:");
        jPSis.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 230, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("Días:");
        jPSis.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 30, 10));

        jBVerAgra.setBackground(new java.awt.Color(255, 255, 255));
        jBVerAgra.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBVerAgra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVerAgra.setToolTipText("Ver Imágen en Tamaño Completo");
        jBVerAgra.setNextFocusableComponent(jTUsrAgra);
        jBVerAgra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerAgraActionPerformed(evt);
            }
        });
        jBVerAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerAgraKeyPressed(evt);
            }
        });
        jPSis.add(jBVerAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 191, 20, 16));

        jBCargAgra.setBackground(new java.awt.Color(255, 255, 255));
        jBCargAgra.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBCargAgra.setText("Cargar");
        jBCargAgra.setNextFocusableComponent(jBDelAgra);
        jBCargAgra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargAgraActionPerformed(evt);
            }
        });
        jBCargAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargAgraKeyPressed(evt);
            }
        });
        jPSis.add(jBCargAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 191, 70, 16));

        jBDelAgra.setBackground(new java.awt.Color(255, 255, 255));
        jBDelAgra.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelAgra.setText("Borrar");
        jBDelAgra.setNextFocusableComponent(jBVerAgra);
        jBDelAgra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelAgraActionPerformed(evt);
            }
        });
        jBDelAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelAgraKeyPressed(evt);
            }
        });
        jPSis.add(jBDelAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 191, 70, 16));

        jTAsunAgra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunAgra.setNextFocusableComponent(jTDias);
        jTAsunAgra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunAgraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunAgraFocusLost(evt);
            }
        });
        jTAsunAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunAgraKeyPressed(evt);
            }
        });
        jPSis.add(jTAsunAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 160, 20));

        jTCuerAgra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerAgra.setNextFocusableComponent(jTRutCalc);
        jTCuerAgra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerAgraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerAgraFocusLost(evt);
            }
        });
        jTCuerAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerAgraKeyPressed(evt);
            }
        });
        jPSis.add(jTCuerAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 160, 20));

        jTDias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTDias.setText("0");
        jTDias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDias.setNextFocusableComponent(jTCuerAgra);
        jTDias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDiasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDiasFocusLost(evt);
            }
        });
        jTDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDiasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDiasKeyTyped(evt);
            }
        });
        jPSis.add(jTDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 40, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Asunto");
        jPSis.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 50, 10));

        jTRutCalc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRutCalc.setNextFocusableComponent(jBCalc);
        jTRutCalc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRutCalcFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRutCalcFocusLost(evt);
            }
        });
        jTRutCalc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRutCalcKeyPressed(evt);
            }
        });
        jPSis.add(jTRutCalc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 275, 250, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setText("Imágen para recordatorio CXC 3:");
        jPSis.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 280, 10));

        jBCalc.setBackground(new java.awt.Color(255, 255, 255));
        jBCalc.setText("jButton1");
        jBCalc.setToolTipText("Buscar Ruta a Calculadora");
        jBCalc.setNextFocusableComponent(jTRutCuade);
        jBCalc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCalcMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCalcMouseExited(evt);
            }
        });
        jBCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCalcActionPerformed(evt);
            }
        });
        jBCalc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCalcKeyPressed(evt);
            }
        });
        jPSis.add(jBCalc, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 275, 30, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("Ruta calculadora:");
        jPSis.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 230, -1));

        jTRutCuade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRutCuade.setNextFocusableComponent(jBCuade);
        jTRutCuade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRutCuadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRutCuadeFocusLost(evt);
            }
        });
        jTRutCuade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRutCuadeKeyPressed(evt);
            }
        });
        jPSis.add(jTRutCuade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 315, 250, 20));

        jBCuade.setBackground(new java.awt.Color(255, 255, 255));
        jBCuade.setText("jButton1");
        jBCuade.setToolTipText("Buscar Ruta a Cuaderno");
        jBCuade.setNextFocusableComponent(jTRutAp);
        jBCuade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCuadeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCuadeMouseExited(evt);
            }
        });
        jBCuade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCuadeActionPerformed(evt);
            }
        });
        jBCuade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCuadeKeyPressed(evt);
            }
        });
        jPSis.add(jBCuade, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 315, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("Ruta cuaderno:");
        jPSis.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 230, -1));

        jTRutAp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRutAp.setNextFocusableComponent(jBApFav);
        jTRutAp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRutApFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRutApFocusLost(evt);
            }
        });
        jTRutAp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRutApKeyPressed(evt);
            }
        });
        jPSis.add(jTRutAp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 355, 250, 20));

        jBApFav.setBackground(new java.awt.Color(255, 255, 255));
        jBApFav.setText("jButton1");
        jBApFav.setName(""); // NOI18N
        jBApFav.setNextFocusableComponent(jBDelCXC1);
        jBApFav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBApFavMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBApFavMouseExited(evt);
            }
        });
        jBApFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBApFavActionPerformed(evt);
            }
        });
        jBApFav.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBApFavKeyPressed(evt);
            }
        });
        jPSis.add(jBApFav, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 355, 30, 20));

        jTUsrCump.setToolTipText("Usuario del que se mandarán los correos automáticamente de Cumpleaños");
        jTUsrCump.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsrCump.setNextFocusableComponent(jBUsrCump);
        jTUsrCump.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrCumpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrCumpFocusLost(evt);
            }
        });
        jTUsrCump.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrCumpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUsrCumpKeyTyped(evt);
            }
        });
        jPSis.add(jTUsrCump, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 90, 20));

        jBUsrCump.setBackground(new java.awt.Color(255, 255, 255));
        jBUsrCump.setText("...");
        jBUsrCump.setToolTipText("Buscar Usuario(s)");
        jBUsrCump.setNextFocusableComponent(jTAsun);
        jBUsrCump.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUsrCumpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUsrCumpMouseExited(evt);
            }
        });
        jBUsrCump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsrCumpActionPerformed(evt);
            }
        });
        jBUsrCump.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUsrCumpKeyPressed(evt);
            }
        });
        jPSis.add(jBUsrCump, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 30, 20));

        jTUsrAgra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsrAgra.setNextFocusableComponent(jBUsrAgra);
        jTUsrAgra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrAgraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrAgraFocusLost(evt);
            }
        });
        jTUsrAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrAgraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUsrAgraKeyTyped(evt);
            }
        });
        jPSis.add(jTUsrAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 90, 20));

        jBUsrAgra.setBackground(new java.awt.Color(255, 255, 255));
        jBUsrAgra.setText("...");
        jBUsrAgra.setToolTipText("Buscar Usuario(s)");
        jBUsrAgra.setNextFocusableComponent(jTAsunAgra);
        jBUsrAgra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUsrAgraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUsrAgraMouseExited(evt);
            }
        });
        jBUsrAgra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsrAgraActionPerformed(evt);
            }
        });
        jBUsrAgra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUsrAgraKeyPressed(evt);
            }
        });
        jPSis.add(jBUsrAgra, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 30, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel16.setText("Cuerpo");
        jPSis.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 40, 10));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel17.setText("Configuación de correo de cumpleaños:");
        jPSis.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel18.setText("Configuación de correo de agradecimiento:");
        jPSis.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 280, 20));

        jBVerCXC1.setBackground(new java.awt.Color(255, 255, 255));
        jBVerCXC1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBVerCXC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVerCXC1.setToolTipText("Ver Imágen en Tamaño Completo");
        jBVerCXC1.setNextFocusableComponent(jBDelCXC2);
        jBVerCXC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerCXC1ActionPerformed(evt);
            }
        });
        jBVerCXC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerCXC1KeyPressed(evt);
            }
        });
        jPSis.add(jBVerCXC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 20, 16));

        jBCXC1.setBackground(new java.awt.Color(255, 255, 255));
        jBCXC1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBCXC1.setText("Cargar");
        jBCXC1.setNextFocusableComponent(jBDelCXC1);
        jBCXC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCXC1ActionPerformed(evt);
            }
        });
        jBCXC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCXC1KeyPressed(evt);
            }
        });
        jPSis.add(jBCXC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 70, 16));

        jBDelCXC1.setBackground(new java.awt.Color(255, 255, 255));
        jBDelCXC1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelCXC1.setText("Borrar");
        jBDelCXC1.setNextFocusableComponent(jBVerCXC1);
        jBDelCXC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelCXC1ActionPerformed(evt);
            }
        });
        jBDelCXC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelCXC1KeyPressed(evt);
            }
        });
        jPSis.add(jBDelCXC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 70, 16));

        jBVerCXC2.setBackground(new java.awt.Color(255, 255, 255));
        jBVerCXC2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBVerCXC2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVerCXC2.setToolTipText("Ver Imágen en Tamaño Completo");
        jBVerCXC2.setNextFocusableComponent(jBDelCXC3);
        jBVerCXC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerCXC2ActionPerformed(evt);
            }
        });
        jBVerCXC2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerCXC2KeyPressed(evt);
            }
        });
        jPSis.add(jBVerCXC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 20, 16));

        jBCXC2.setBackground(new java.awt.Color(255, 255, 255));
        jBCXC2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBCXC2.setText("Cargar");
        jBCXC2.setNextFocusableComponent(jBDelCXC2);
        jBCXC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCXC2ActionPerformed(evt);
            }
        });
        jBCXC2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCXC2KeyPressed(evt);
            }
        });
        jPSis.add(jBCXC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 70, 16));

        jBDelCXC2.setBackground(new java.awt.Color(255, 255, 255));
        jBDelCXC2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelCXC2.setText("Borrar");
        jBDelCXC2.setNextFocusableComponent(jBVerCXC2);
        jBDelCXC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelCXC2ActionPerformed(evt);
            }
        });
        jBDelCXC2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelCXC2KeyPressed(evt);
            }
        });
        jPSis.add(jBDelCXC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 70, 16));

        jBVerCXC3.setBackground(new java.awt.Color(255, 255, 255));
        jBVerCXC3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBVerCXC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVerCXC3.setToolTipText("Ver Imágen en Tamaño Completo");
        jBVerCXC3.setNextFocusableComponent(jCUsrMulti);
        jBVerCXC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerCXC3ActionPerformed(evt);
            }
        });
        jBVerCXC3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerCXC3KeyPressed(evt);
            }
        });
        jPSis.add(jBVerCXC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 20, 16));

        jBCXC3.setBackground(new java.awt.Color(255, 255, 255));
        jBCXC3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBCXC3.setText("Cargar");
        jBCXC3.setNextFocusableComponent(jBDelCXC3);
        jBCXC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCXC3ActionPerformed(evt);
            }
        });
        jBCXC3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCXC3KeyPressed(evt);
            }
        });
        jPSis.add(jBCXC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 70, 16));

        jBDelCXC3.setBackground(new java.awt.Color(255, 255, 255));
        jBDelCXC3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelCXC3.setText("Borrar");
        jBDelCXC3.setNextFocusableComponent(jBVerCXC3);
        jBDelCXC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelCXC3ActionPerformed(evt);
            }
        });
        jBDelCXC3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelCXC3KeyPressed(evt);
            }
        });
        jPSis.add(jBDelCXC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, 70, 16));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel19.setText("Imágen para recordatorio CXC 1:");
        jPSis.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 280, 10));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel20.setText("Imágen para recordatorio CXC 2:");
        jPSis.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 280, 10));

        jCUsrMulti.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCUsrMulti.setText("Usuarios pueden iniciar múltiples sesiones");
        jCUsrMulti.setNextFocusableComponent(jCMosMsjConfCorr);
        jCUsrMulti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCUsrMultiKeyPressed(evt);
            }
        });
        jPSis.add(jCUsrMulti, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 300, -1));

        jCMosMsjConfCorr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosMsjConfCorr.setText("Mostrar mensaje de configuraciones de correos electrónicos");
        jCMosMsjConfCorr.setName(""); // NOI18N
        jCMosMsjConfCorr.setNextFocusableComponent(jCDefTipCamSis);
        jCMosMsjConfCorr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosMsjConfCorrKeyPressed(evt);
            }
        });
        jPSis.add(jCMosMsjConfCorr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 310, -1));

        jCDefTipCamSis.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDefTipCamSis.setText("Definir tipo de cambio en todo el sistema");
        jCDefTipCamSis.setNextFocusableComponent(jCRedon);
        jCDefTipCamSis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDefTipCamSisKeyPressed(evt);
            }
        });
        jPSis.add(jCDefTipCamSis, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 240, -1));

        jCRedon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCRedon.setText("Redondear totales en posiciones:");
        jCRedon.setToolTipText("Redondear los totales en ventas y cotizaciones");
        jCRedon.setNextFocusableComponent(jTRedon);
        jCRedon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCRedonKeyPressed(evt);
            }
        });
        jPSis.add(jCRedon, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 250, 20));

        jTRedon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRedon.setNextFocusableComponent(jCDesInac);
        jTRedon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRedonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRedonFocusLost(evt);
            }
        });
        jTRedon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRedonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRedonKeyTyped(evt);
            }
        });
        jPSis.add(jTRedon, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 50, 20));

        jTBP.addTab("tab4", jPSis);

        jPComp.setNextFocusableComponent(jCCompXUsr);
        jPComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPCompKeyPressed(evt);
            }
        });
        jPComp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCCompXUsr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCompXUsr.setText("Mostrar solo compras de usuario");
        jCCompXUsr.setNextFocusableComponent(jCCompMN);
        jCCompXUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompXUsrKeyPressed(evt);
            }
        });
        jPComp.add(jCCompXUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, -1));

        jCCompMN.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCompMN.setText("Solo comprar en moneda nacional");
        jCCompMN.setNextFocusableComponent(jCModDescripComp);
        jCCompMN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompMNKeyPressed(evt);
            }
        });
        jPComp.add(jCCompMN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, -1));

        jCModDescripComp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModDescripComp.setText("Modificar descripción del producto");
        jCModDescripComp.setNextFocusableComponent(jCModPrecComp);
        jCModDescripComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModDescripCompKeyPressed(evt);
            }
        });
        jPComp.add(jCModDescripComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 240, -1));

        jCModPrecComp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCModPrecComp.setText("Modificar precio del producto");
        jCModPrecComp.setName(""); // NOI18N
        jCModPrecComp.setNextFocusableComponent(jCIniOrd);
        jCModPrecComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModPrecCompKeyPressed(evt);
            }
        });
        jPComp.add(jCModPrecComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 240, -1));

        jCIniOrd.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCIniOrd.setText("Iniciar con orden de compra");
        jCIniOrd.setNextFocusableComponent(jCCompXUsr);
        jCIniOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCIniOrdActionPerformed(evt);
            }
        });
        jCIniOrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCIniOrdKeyPressed(evt);
            }
        });
        jPComp.add(jCIniOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 230, -1));

        jTBP.addTab("tab5", jPComp);

        jPPtoVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPPtoVtaKeyPressed(evt);
            }
        });
        jPPtoVta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCCatGralPvta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCatGralPvta.setText("Catálogo general en punto de venta");
        jCCatGralPvta.setNextFocusableComponent(jCLimCredPVta);
        jCCatGralPvta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCCatGralPvtaActionPerformed(evt);
            }
        });
        jCCatGralPvta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCatGralPvtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCCatGralPvta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 250, -1));

        jCPLEPvta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCPLEPvta.setText("Aplicar P.lista de clientes en punto de venta");
        jCPLEPvta.setNextFocusableComponent(jCMMEPvta);
        jCPLEPvta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPLEPvtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCPLEPvta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, -1));

        jCMMEPvta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMMEPvta.setText("Mostrar mensaje si no hay existencia en punto de venta");
        jCMMEPvta.setNextFocusableComponent(jCVSinExistPvta);
        jCMMEPvta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMMEPvtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCMMEPvta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 310, -1));

        jCVSinExistPvta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVSinExistPvta.setText("Vender sin existencia en punto de venta");
        jCVSinExistPvta.setNextFocusableComponent(jCBarrLatP);
        jCVSinExistPvta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVSinExistPvtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCVSinExistPvta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 290, -1));

        jCBarrLatP.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCBarrLatP.setText("Mostrar barra lateral en punto de venta");
        jCBarrLatP.setNextFocusableComponent(jCImgLin);
        jCBarrLatP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBarrLatPKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCBarrLatP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, -1));

        jCImgLin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCImgLin.setText("Mostrar imágenes en líneas del punto de venta");
        jCImgLin.setNextFocusableComponent(jCInitPvta);
        jCImgLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImgLinKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCImgLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 290, -1));

        jCInitPvta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCInitPvta.setText("Iniciar el punto de venta al entrar al sistema");
        jCInitPvta.setNextFocusableComponent(jCCatGralPvta);
        jCInitPvta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCInitPvtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCInitPvta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 290, -1));

        jCLimCredPVta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCLimCredPVta.setText("Vender sobre límite de crédito en punto de venta");
        jCLimCredPVta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCLimCredPVta.setName(""); // NOI18N
        jCLimCredPVta.setNextFocusableComponent(jCEmpsPtoVta);
        jCLimCredPVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCLimCredPVtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCLimCredPVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 310, -1));

        jCEmpsPtoVta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCEmpsPtoVta.setText("Dar de alta nuevos clientes en punto de venta");
        jCEmpsPtoVta.setNextFocusableComponent(jCDescrip);
        jCEmpsPtoVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEmpsPtoVtaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCEmpsPtoVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 280, -1));

        jCDescrip.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDescrip.setText("Modificar descripción de productos en punto de venta");
        jCDescrip.setNextFocusableComponent(jCAdmCan);
        jCDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDescripKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 310, -1));

        jCAdmCan.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCAdmCan.setText("Pedir clave de administrador al cancelar/devolver ventas en punto de venta");
        jCAdmCan.setNextFocusableComponent(jCMsjPto);
        jCAdmCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAdmCanKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCAdmCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 390, -1));

        jCMsjPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMsjPto.setText("Ver mensajes en el punto de venta");
        jCMsjPto.setNextFocusableComponent(jCChatPtoC);
        jCMsjPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMsjPtoKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCMsjPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 320, -1));

        jCChatPtoC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCChatPtoC.setText("Chatear corporativamente en punto de venta");
        jCChatPtoC.setNextFocusableComponent(jCInsAutCaj);
        jCChatPtoC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCChatPtoCKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCChatPtoC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 320, -1));

        jTDinCaj.setText("$0.00");
        jTDinCaj.setNextFocusableComponent(jCClavFacP);
        jTDinCaj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDinCajFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDinCajFocusLost(evt);
            }
        });
        jTDinCaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDinCajKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDinCajKeyTyped(evt);
            }
        });
        jPPtoVta.add(jTDinCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 291, 70, -1));

        jCInsAutCaj.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCInsAutCaj.setText("Insertar automáticamente dinero en el cajón");
        jCInsAutCaj.setNextFocusableComponent(jTDinCaj);
        jCInsAutCaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCInsAutCajKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCInsAutCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 280, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("$:");
        jPPtoVta.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 292, 20, -1));

        jTClavFacP.setNextFocusableComponent(jCCortXAut);
        jTClavFacP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClavFacPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClavFacPFocusLost(evt);
            }
        });
        jTClavFacP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClavFacPKeyPressed(evt);
            }
        });
        jPPtoVta.add(jTClavFacP, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 160, -1));

        jLabel15.setText("Clave:");
        jPPtoVta.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 60, 20));

        jCClavFacP.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCClavFacP.setText("Pedir clave de seguridad cada que se factura");
        jCClavFacP.setName(""); // NOI18N
        jCClavFacP.setNextFocusableComponent(jTClavFacP);
        jCClavFacP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCClavFacPKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCClavFacP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 290, 20));

        jCCortXAut.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCortXAut.setText("Hacer corte X automáticamente al inicio de usuario");
        jCCortXAut.setNextFocusableComponent(jCDiaVtaP);
        jCCortXAut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCortXAutKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCCortXAut, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 340, -1));

        jCDiaVtaP.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDiaVtaP.setText("Solo se muestran las ventas del día en punto de venta");
        jCDiaVtaP.setNextFocusableComponent(jCCortXA);
        jCDiaVtaP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDiaVtaPKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCDiaVtaP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 330, -1));

        jCCortXA.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCortXA.setText("Guardar automáticamente corte X en PDF");
        jCCortXA.setNextFocusableComponent(jCCortZA);
        jCCortXA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCortXAKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCCortXA, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 220, -1));

        jCCortZA.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCortZA.setText("Guardar automáticamente corte Z en PDF");
        jCCortZA.setNextFocusableComponent(jComSerF);
        jCCortZA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCortZAKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCCortZA, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 220, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel22.setText("Serie remisiones:");
        jPPtoVta.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 472, 90, -1));

        jComSerF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComSerF.setNextFocusableComponent(jComSerT);
        jComSerF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFFocusLost(evt);
            }
        });
        jComSerF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerFActionPerformed(evt);
            }
        });
        jComSerF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerFKeyPressed(evt);
            }
        });
        jPPtoVta.add(jComSerF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 432, 120, 20));

        jComSerT.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComSerT.setNextFocusableComponent(jComSerT);
        jComSerT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerTFocusLost(evt);
            }
        });
        jComSerT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerTActionPerformed(evt);
            }
        });
        jComSerT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerTKeyPressed(evt);
            }
        });
        jPPtoVta.add(jComSerT, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 453, 120, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel23.setText("Almacén de venta:");
        jPPtoVta.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 90, -1));

        jComSerR.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComSerR.setNextFocusableComponent(jTAlma);
        jComSerR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerRFocusLost(evt);
            }
        });
        jComSerR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerRActionPerformed(evt);
            }
        });
        jComSerR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerRKeyPressed(evt);
            }
        });
        jPPtoVta.add(jComSerR, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 474, 120, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel24.setText("Serie tickets:");
        jPPtoVta.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 452, 90, -1));

        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlma.setNextFocusableComponent(jBAlma);
        jTAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAlmaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAlmaFocusLost(evt);
            }
        });
        jTAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAlmaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAlmaKeyTyped(evt);
            }
        });
        jPPtoVta.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 100, 20));

        jBAlma.setBackground(new java.awt.Color(255, 255, 255));
        jBAlma.setText("jButton1");
        jBAlma.setToolTipText("Buscar Almacén(es)");
        jBAlma.setNextFocusableComponent(jCGaraP);
        jBAlma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAlmaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAlmaMouseExited(evt);
            }
        });
        jBAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlmaActionPerformed(evt);
            }
        });
        jBAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAlmaKeyPressed(evt);
            }
        });
        jPPtoVta.add(jBAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 30, 20));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel25.setText("Serie facturas:");
        jPPtoVta.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 432, 90, -1));

        jCGaraP.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGaraP.setText("Agregar garantía de producto en descripción");
        jCGaraP.setNextFocusableComponent(jCDevVtaPto);
        jCGaraP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGaraPKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCGaraP, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 270, -1));

        jCDevPVtaPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDevPVtaPto.setText("Devoluciones parciales de ventas en punto de venta");
        jCDevPVtaPto.setNextFocusableComponent(jCRemPto);
        jCDevPVtaPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDevPVtaPtoKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCDevPVtaPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 270, -1));

        jCDevVtaPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDevVtaPto.setText("Devoluciones de ventas en punto de venta");
        jCDevVtaPto.setNextFocusableComponent(jCDevPVtaPto);
        jCDevVtaPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDevVtaPtoKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCDevVtaPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 270, -1));

        jCFacPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCFacPto.setText("Se puede facturar en el punto de venta");
        jCFacPto.setNextFocusableComponent(jCPLEPvta);
        jCFacPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFacPtoKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCFacPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 270, -1));

        jCRemPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCRemPto.setText("Se puede remisionar en el punto de venta");
        jCRemPto.setNextFocusableComponent(jCFacPto);
        jCRemPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCRemPtoKeyPressed(evt);
            }
        });
        jPPtoVta.add(jCRemPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 270, -1));

        jTBP.addTab("tab6", jPPtoVta);

        jPRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPRemKeyPressed(evt);
            }
        });
        jPRem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCMosLogRem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosLogRem.setText("Mostrar logo en remisiones");
        jCMosLogRem.setNextFocusableComponent(jCLimCredRem);
        jCMosLogRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosLogRemKeyPressed(evt);
            }
        });
        jPRem.add(jCMosLogRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, -1));

        jCLimCredRem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCLimCredRem.setText("Vender sobre límite de crédito en remisiones");
        jCLimCredRem.setNextFocusableComponent(jCVRemPto);
        jCLimCredRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCLimCredRemKeyPressed(evt);
            }
        });
        jPRem.add(jCLimCredRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 310, -1));

        jCVRemPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVRemPto.setText("Mostrar remisiones en buscador del punto de venta");
        jCVRemPto.setNextFocusableComponent(jCImpRemPto);
        jCVRemPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVRemPtoKeyPressed(evt);
            }
        });
        jPRem.add(jCVRemPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 310, -1));

        jCImpRemPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCImpRemPto.setText("Se pueden imprimir remisiones en punto de venta");
        jCImpRemPto.setNextFocusableComponent(jCCanRemPto);
        jCImpRemPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpRemPtoKeyPressed(evt);
            }
        });
        jPRem.add(jCImpRemPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, -1));

        jCCanRemPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCanRemPto.setText("Se pueden cancelar/devolver remisiones en el punto de venta");
        jCCanRemPto.setNextFocusableComponent(jCMosLogRem);
        jCCanRemPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCanRemPtoKeyPressed(evt);
            }
        });
        jPRem.add(jCCanRemPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, -1));

        jTBP.addTab("tab7", jPRem);

        jPTic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPTicKeyPressed(evt);
            }
        });
        jPTic.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCMosLogTik.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosLogTik.setText("Mostrar logo en ticktes");
        jCMosLogTik.setNextFocusableComponent(jCVTicPto);
        jCMosLogTik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosLogTikKeyPressed(evt);
            }
        });
        jPTic.add(jCMosLogTik, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, -1));

        jCVTicPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVTicPto.setText("Mostrar tickets en buscador del punto de venta");
        jCVTicPto.setNextFocusableComponent(jCImpTicPto);
        jCVTicPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVTicPtoKeyPressed(evt);
            }
        });
        jPTic.add(jCVTicPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 280, -1));

        jCImpTicPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCImpTicPto.setText("Se pueden imprimir tickets en punto de venta");
        jCImpTicPto.setNextFocusableComponent(jCCanTicPto);
        jCImpTicPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpTicPtoKeyPressed(evt);
            }
        });
        jPTic.add(jCImpTicPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, -1));

        jCCanTicPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCanTicPto.setText("Se pueden cancelar/devolver tickets en el punto de venta");
        jCCanTicPto.setName(""); // NOI18N
        jCCanTicPto.setNextFocusableComponent(jCMosTicCanPto);
        jCCanTicPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCanTicPtoKeyPressed(evt);
            }
        });
        jPTic.add(jCCanTicPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 300, -1));

        jCMosTicCanPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosTicCanPto.setText("Mostrar ticket de cancelación/devolución en punto de venta");
        jCMosTicCanPto.setNextFocusableComponent(jCGuaPDFCanPto);
        jCMosTicCanPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosTicCanPtoKeyPressed(evt);
            }
        });
        jPTic.add(jCMosTicCanPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, -1));

        jCGuaPDFCanPto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGuaPDFCanPto.setText("Guardar PDF de cancelación/devolución en punto de venta");
        jCGuaPDFCanPto.setNextFocusableComponent(jCMosLogTik);
        jCGuaPDFCanPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaPDFCanPtoKeyPressed(evt);
            }
        });
        jPTic.add(jCGuaPDFCanPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 300, -1));

        jTBP.addTab("tab8", jPTic);

        jPChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPChatKeyPressed(evt);
            }
        });
        jPChat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBDelHisCor.setBackground(new java.awt.Color(255, 255, 255));
        jBDelHisCor.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelHisCor.setText("Borrar historial chat corporativo");
        jBDelHisCor.setNextFocusableComponent(jBDelHisEstac);
        jBDelHisCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelHisCorActionPerformed(evt);
            }
        });
        jBDelHisCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelHisCorKeyPressed(evt);
            }
        });
        jPChat.add(jBDelHisCor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 190, -1));

        jBDelHisEstac.setBackground(new java.awt.Color(255, 255, 255));
        jBDelHisEstac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDelHisEstac.setText("Borrar historial de usuarios");
        jBDelHisEstac.setNextFocusableComponent(jBDelHisCor);
        jBDelHisEstac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelHisEstacActionPerformed(evt);
            }
        });
        jBDelHisEstac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelHisEstacKeyPressed(evt);
            }
        });
        jPChat.add(jBDelHisEstac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 190, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPChat.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jPChat.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTBP.addTab("tab9", jPChat);

        jBPrevUsuario.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevUsuario.setText("Mostrar solo previos de compra de usuario");
        jBPrevUsuario.setToolTipText("Mostrar solo previos de compra de usuario");
        jBPrevUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevUsuarioKeyPressed(evt);
            }
        });

        jBPrevModDesc.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevModDesc.setText("Modificar descripción del producto");
        jBPrevModDesc.setToolTipText("Modificar descripción del producto");
        jBPrevModDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevModDescKeyPressed(evt);
            }
        });

        jBPrevMonNacional.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevMonNacional.setText("Solo previos de compra en moneda nacional");
        jBPrevMonNacional.setToolTipText("Solo comprar en moneda nacional");
        jBPrevMonNacional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevMonNacionalKeyPressed(evt);
            }
        });

        jBPrevModPrec.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevModPrec.setText("Modificar precio del producto");
        jBPrevModPrec.setToolTipText("Modificar precio del producto");
        jBPrevModPrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrevModPrecActionPerformed(evt);
            }
        });
        jBPrevModPrec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevModPrecKeyPressed(evt);
            }
        });

        jBPrevCotunaSerie.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevCotunaSerie.setText("Utilizar una sola vez una serie en previos de compra");
        jBPrevCotunaSerie.setToolTipText("Cotizar una sola vez una serie");
        jBPrevCotunaSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevCotunaSerieKeyPressed(evt);
            }
        });

        jBPrevSerObli.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBPrevSerObli.setText("Es obligatorio el uso de series en previos de compra");
        jBPrevSerObli.setToolTipText("Es obligatorio el uso de series en cotizaciones");
        jBPrevSerObli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevSerObliKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBPrevUsuario)
                    .addComponent(jBPrevMonNacional)
                    .addComponent(jBPrevModDesc)
                    .addComponent(jBPrevModPrec)
                    .addComponent(jBPrevCotunaSerie)
                    .addComponent(jBPrevSerObli))
                .addContainerGap(598, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPrevUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevMonNacional)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevModDesc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevModPrec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevCotunaSerie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevSerObli)
                .addContainerGap(392, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 861, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTBP.addTab("tab10", jPanel2);

        jP1.add(jTBP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 910, 530));
        jTBP.getAccessibleContext().setAccessibleName("\"Inventarios\"");

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setNextFocusableComponent(jCPListCFac);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 570, 120, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 590, 210, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga las configuraciones de ventas*/
    private void vCargVtas()
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

        /*Obtiene las configuraciones de ventas*/
        try
        {
            sQ = "SELECT * FROM confgral WHERE clasif = 'vtas'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Aplicar precio de lista de las empresas en las cotizaciones*/
                if(rs.getString("conf").compareTo("alistpreclifac")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCPListCFac.setSelected(true);
                    else
                        jCPListCFac.setSelected(false);
                }
                /*Else if configuración para el almacén de ventas del punto de venta, entonces colocalo en el control*/
                else if(rs.getString("conf").compareTo("almapto")==0)                                    
                    jTAlma.setText(rs.getString("extr"));                
                /*Else if configuración de serie de facturas*/
                else if(rs.getString("conf").compareTo("serfacfij")==0)                                    
                {
                    /*Si es diferente de cadena vacia el campo extra entonces selecciona esa serie en el combo*/
                    if(rs.getString("extr").compareTo("")!=0)
                        jComSerFac.setSelectedItem(rs.getString("extr"));
                }
                /*Else if configuración de monedas para factura fija*/
                else if(rs.getString("conf").compareTo("monfacfij")==0)                                    
                {
                    /*Si es diferente de cadena vacia el campo extra entonces selecciona esa moneda en el combo*/
                    if(rs.getString("extr").compareTo("")!=0)
                        jComMonFac.setSelectedItem(rs.getString("extr"));
                }
                /*Else if configuración el mínimo a facturar*/
                else if(rs.getString("conf").compareTo("minfac")==0)                                    
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMinFac.setSelected(true);
                    else
                        jCMinFac.setSelected(false);
                    
                    /*Dale formato de moneda al mínimo a facturar*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                    double dCant    = Double.parseDouble(rs.getString("nume"));                                    

                    /*Coloca el valor en el campo*/
                    jTMinFac.setText(n.format(dCant));                
                }
                /*Else if configuración para saber si se puede hacer remisiones en el punto de venta o no*/
                else if(rs.getString("conf").compareTo("remptovta")==0)                                    
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCRemPto.setSelected(true);
                    else
                        jCRemPto.setSelected(false);                                        
                }
                /*Else if configuración para saber si se puede hacer facturas en el punto de venta o no*/
                else if(rs.getString("conf").compareTo("facptovta")==0)                                    
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCFacPto.setSelected(true);
                    else
                        jCFacPto.setSelected(false);                                        
                }
                /*Else if configuración para el almacén de ventas, entonces colocalo en el control*/
                else if(rs.getString("conf").compareTo("almavtaf")==0)                                    
                    jTAlmaVta.setText(rs.getString("extr"));                
                /*Else if coloca la serie de las facturas para el punto de venta entonces seleccionalo en el combobox*/
                else if(rs.getString("conf").compareTo("serfac")==0)                                    
                    jComSerF.setSelectedItem(rs.getString("extr"));                
                /*Else if coloca la serie de los tickts para el punto de venta entonces seleccionadlo en el combobox*/
                else if(rs.getString("conf").compareTo("sertic")==0)
                    jComSerT.setSelectedItem(rs.getString("extr"));                
                /*Else if coloca la serie de las remisiones para el punto de venta entonces seleccionalo en el combobox*/
                else if(rs.getString("conf").compareTo("serrem")==0)
                    jComSerR.setSelectedItem(rs.getString("extr"));                
                /*Else if se puede o no modificar la lista de precio en facturas*/
                else if(rs.getString("conf").compareTo("modlistfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModListF.setSelected(true);
                    else
                        jCModListF.setSelected(false);
                }                
                /*Else if se puede o no hacer devoluciones completas en el punto de venta*/
                else if(rs.getString("conf").compareTo("devvtaspto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCDevVtaPto.setSelected(true);
                    else
                        jCDevVtaPto.setSelected(false);
                }                
                /*Else if se puede o no hacer devoluciones parciales en el punto de venta*/
                else if(rs.getString("conf").compareTo("devpvtaspto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCDevPVtaPto.setSelected(true);
                    else
                        jCDevPVtaPto.setSelected(false);
                }                
                /*Else if se tiene que agregar la serie en la descripción de las facturas*/
                else if(rs.getString("conf").compareTo("garandescfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCGaraF.setSelected(true);
                    else
                        jCGaraF.setSelected(false);
                }                
                /*Else if se tiene que agregar la garantía en la descripción en el punto de venta*/
                else if(rs.getString("conf").compareTo("garandescpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCGaraP.setSelected(true);
                    else
                        jCGaraP.setSelected(false);
                }                
                /*Else if se tienen que poder modificar la descripción en las facturas o no*/
                else if(rs.getString("conf").compareTo("moddescrip")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModDescripFac.setSelected(true);
                    else
                        jCModDescripFac.setSelected(false);
                }                
                /*Else if se tienen que poder modificar el precio en las facturas o no*/
                else if(rs.getString("conf").compareTo("modprec")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModPrecFac.setSelected(true);
                    else
                        jCModPrecFac.setSelected(false);
                }                
                /*Else if se tienen que mostrar las ventas solo del día*/
                else if(rs.getString("conf").compareTo("hoyvtap")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCDiaVtaP.setSelected(true);
                    else
                        jCDiaVtaP.setSelected(false);
                }                
                /*Else if corte X automático al iniciar sesión de los usuarios*/
                else if(rs.getString("conf").compareTo("autcortx")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCortXAut.setSelected(true);
                    else
                        jCCortXAut.setSelected(false);
                }                
//                /*Else if el sistema esta a modo prueba entonces*/
//                else if(rs.getString("conf").compareTo("modp")==0)
//                {
//                    /*Marca o desmarca el checkbox*/
//                    if(rs.getString("val").compareTo("1")==0)
//                        jCFPrue.setSelected(true);
//                    else
//                        jCFPrue.setSelected(false);
//                }                
                /*Else if se tiene o no que insertar automáticamente dinero en el cajón*/
                else if(rs.getString("conf").compareTo("insautcaj")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCInsAutCaj.setSelected(true);
                    else
                        jCInsAutCaj.setSelected(false);
                    
                    /*Dale formato de moneda a la cantidad a insertar*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(rs.getString("extr"));                
                    String sCan     = n.format(dCant);
                    
                    /*Coloca la cantidad a insertar*/
                    jTDinCaj.setText(sCan);                    
                }
                /*Else if se puede chatear corporativamente en el punto de venta*/
                else if(rs.getString("conf").compareTo("chatptoc")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCChatPtoC.setSelected(true);
                    else
                        jCChatPtoC.setSelected(false);
                }
                /*Else if se tiene que pedir clave de seguridad en cada factura o no*/
                else if(rs.getString("conf").compareTo("clavsegfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCClavFac.setSelected(true);
                    else
                        jCClavFac.setSelected(false);
                    
                    /*Coloca la clave en su lugar*/
                    jTClavFac.setText(Star.sDecryp(rs.getString("extr")));
                }
                /*Else if se tiene que pedir clave de seguridad en cada factura o no en el punto de venta*/
                else if(rs.getString("conf").compareTo("clavsegfacp")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCClavFacP.setSelected(true);
                    else
                        jCClavFacP.setSelected(false);
                    
                    /*Coloca la clave en su lugar*/
                    jTClavFacP.setText(Star.sDecryp(rs.getString("extr")));
                }
                /*Else if se pueden ver mensajes o no en el punto de venta*/
                else if(rs.getString("conf").compareTo("vmsjpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMsjPto.setSelected(true);
                    else
                        jCMsjPto.setSelected(false);
                }
                /*Else if se tiene que guardar el PDF de cancelación en el punto de venta*/
                else if(rs.getString("conf").compareTo("guapdfcan")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCGuaPDFCanPto.setSelected(true);
                    else
                        jCGuaPDFCanPto.setSelected(false);
                }
                /*Else if se tiene que guardar el PDF de cancelación en las facturas*/
                else if(rs.getString("conf").compareTo("guapdfcanf")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCGuaPDFCanF.setSelected(true);
                    else
                        jCGuaPDFCanF.setSelected(false);
                }                
                /*Else if se tiene que mostrar el PDF de cancelación en el punto de venta*/
                else if(rs.getString("conf").compareTo("vercanvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMosTicCanPto.setSelected(true);
                    else
                        jCMosTicCanPto.setSelected(false);
                }
                /*Else if se tiene que mostrar el PDF de cancelación en las facturas*/
                else if(rs.getString("conf").compareTo("vercanvtaf")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMosTicCanF.setSelected(true);
                    else
                        jCMosTicCanF.setSelected(false);
                }
                /*Else if se pueden cancelar facturas o no en el punto de venta*/
                else if(rs.getString("conf").compareTo("canfacpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCanFacPto.setSelected(true);
                    else
                        jCCanFacPto.setSelected(false);
                }
                /*Else if se tiene que pedir clave o no al cancelar ventas en el punto de venta*/
                else if(rs.getString("conf").compareTo("admcanvtas")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCAdmCan.setSelected(true);
                    else
                        jCAdmCan.setSelected(false);
                }
                /*Else if se puede o no modificar la descripción de los productos en el punto de venta*/
                else if(rs.getString("conf").compareTo("descrippto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCDescrip.setSelected(true);
                    else
                        jCDescrip.setSelected(false);
                }
                /*Else if se pueden dar de alta empresas en el punto de venta o no*/
                else if(rs.getString("conf").compareTo("empspto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCEmpsPtoVta.setSelected(true);
                    else
                        jCEmpsPtoVta.setSelected(false);
                }
                /*Else if se pueden cancelar remisiones o no en el punto de venta*/
                else if(rs.getString("conf").compareTo("canrempto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCanRemPto.setSelected(true);
                    else
                        jCCanRemPto.setSelected(false);
                }
                /*Else if se pueden cancelar tickets o no en el punto de venta*/
                else if(rs.getString("conf").compareTo("canticpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCanTicPto.setSelected(true);
                    else
                        jCCanTicPto.setSelected(false);
                }
                /*Else if se debe de imprimir o no tickets en el punto de venta*/
                else if(rs.getString("conf").compareTo("impticpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCImpTicPto.setSelected(true);
                    else
                        jCImpTicPto.setSelected(false);
                }
                /*Else if se debe de imprimir o no remisiones en el punto de venta*/
                else if(rs.getString("conf").compareTo("imprempto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCImpRemPto.setSelected(true);
                    else
                        jCImpRemPto.setSelected(false);
                }
                /*Else if se debe de imprimir o no facturas en el punto de venta*/
                else if(rs.getString("conf").compareTo("impfacpto")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCImpFacPto.setSelected(true);
                    else
                        jCImpFacPto.setSelected(false);
                }
                /*Else if se debe guardar automáticamente los cortes X o no*/
                else if(rs.getString("conf").compareTo("cortxa")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCortXA.setSelected(true);
                    else
                        jCCortXA.setSelected(false);
                }
                /*Else if se deben de mostrar los tickets en el bùscador del punto de venta*/
                else if(rs.getString("conf").compareTo("vticptovta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVTicPto.setSelected(true);
                    else
                        jCVTicPto.setSelected(false);
                }                
                /*Else if se deben de mostrar las remisiones en el bùscador del punto de venta*/
                else if(rs.getString("conf").compareTo("vremptovta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVRemPto.setSelected(true);
                    else
                        jCVRemPto.setSelected(false);
                }        
                /*Else if se deben de mostrar las facturas en el bùscador del punto de venta*/
                else if(rs.getString("conf").compareTo("vfacptovta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVFacPto.setSelected(true);
                    else
                        jCVFacPto.setSelected(false);
                }                
                /*Else if se debe guardar automáticamente los cortes Z o no*/
                else if(rs.getString("conf").compareTo("cortza")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCortZA.setSelected(true);
                    else
                        jCCortZA.setSelected(false);
                }
                /*Else if solo se debe vender con moneda nacional o no*/
                else if(rs.getString("conf").compareTo("otramon")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("0")==0)
                        jCVendMN.setSelected(true);
                    else
                        jCVendMN.setSelected(false);
                }
                /*Else if, mostrar logo en las facturas*/
                else if(rs.getString("conf").compareTo("logofac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMosLogFac.setSelected(true);
                    else
                        jCMosLogFac.setSelected(false);
                }
                /*Else if, mostrar solo mostrar las ventas por usuario*/
                else if(rs.getString("conf").compareTo("vtasxusr")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVtasXUsr.setSelected(true);
                    else
                        jCVtasXUsr.setSelected(false);
                }                
                /*Else if, vender sobre límite de Crédito en punto de venta*/
                else if(rs.getString("conf").compareTo("slimcredpvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCLimCredPVta.setSelected(true);
                    else
                        jCLimCredPVta.setSelected(false);
                }                
                /*Else if, vender sobre límite de Crédito en facturas*/
                else if(rs.getString("conf").compareTo("slimcredfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCLimCredFac.setSelected(true);
                    else
                        jCLimCredFac.setSelected(false);
                }                                
                /*Else if, vender sobre límite de Crédito en remisiones*/
                else if(rs.getString("conf").compareTo("slimcredrem")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCLimCredRem.setSelected(true);
                    else
                        jCLimCredRem.setSelected(false);
                }                
                /*Else if, mostrar logo en los tickets*/
                else if(rs.getString("conf").compareTo("logotik")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMosLogTik.setSelected(true);
                    else
                        jCMosLogTik.setSelected(false);
                }
                /*Else if, mostrar logo en las remisiones*/
                else if(rs.getString("conf").compareTo("logorem")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMosLogRem.setSelected(true);
                    else
                        jCMosLogRem.setSelected(false);
                }
                /*Else if, aplicar precio de lista de la empresa en las cotizaciones*/
                else if(rs.getString("conf").compareTo("alistpreclicot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCPLCot.setSelected(true);
                    else
                        jCPLCot.setSelected(false);
                }
                /*Else if, aplicar precio de lista de la empresa en el punto de venta*/
                else if(rs.getString("conf").compareTo("alistpreclipvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCPLEPvta.setSelected(true);
                    else
                        jCPLEPvta.setSelected(false);
                }
                /*Else if, mostrar o no al iniciar el sistema el punto de venta*/
                else if(rs.getString("conf").compareTo("initpvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCInitPvta.setSelected(true);
                    else
                        jCInitPvta.setSelected(false);
                }
                                
                /*Else if, mostrar mensaje de existencias negativas en las facturas*/
                else if(rs.getString("conf").compareTo("msjexistnegfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMMEFac.setSelected(true);
                    else
                        jCMMEFac.setSelected(false);
                }
                /*Else if, mostrar mensaje de existencias negativas en el punto de venta*/
                else if(rs.getString("conf").compareTo("msjexistnegpvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMMEPvta.setSelected(true);
                    else
                        jCMMEPvta.setSelected(false);
                }
                /*Else if, mostrar mensaje de existencias negativas en las cotizaciones*/
                else if(rs.getString("conf").compareTo("msjexistnegcot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMMECot.setSelected(true);
                    else
                        jCMMECot.setSelected(false);
                }
                /*Else if, vender sin existencia en las facturas*/
                else if(rs.getString("conf").compareTo("vendsinexistfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVendSinExistFac.setSelected(true);
                    else
                        jCVendSinExistFac.setSelected(false);
                }
                /*Else if, vender sin existencia en el punto de venta*/
                else if(rs.getString("conf").compareTo("vendsinexistpvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVSinExistPvta.setSelected(true);
                    else
                        jCVSinExistPvta.setSelected(false);
                }
                /*Else if, mostrar la barra lateral en el punto de venta*/
                else if(rs.getString("conf").compareTo("barlat")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCBarrLatP.setSelected(true);
                    else
                        jCBarrLatP.setSelected(false);
                }
                /*Else if, catálogo general en el punto de venta*/
                else if(rs.getString("conf").compareTo("catgralpvta")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCatGralPvta.setSelected(true);
                    else
                        jCCatGralPvta.setSelected(false);
                }
                /*Else if, catálogo general en el punto de venta*/
                else if(rs.getString("conf").compareTo("catgralfac")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCatGralFac.setSelected(true);
                    else
                        jCCatGralFac.setSelected(false);
                }
                /*Else if, mostrar imágenes en las líneas del punto de venta*/
                else if(rs.getString("conf").compareTo("imglin")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCImgLin.setSelected(true);
                    else
                        jCImgLin.setSelected(false);
                }
                                                
            }/*Fin de while(rs.next())*/                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }/*Fin de private void vCargVtas()*/
    
    
    /*Carga las configuraciones del sistema*/
    private void vCargSist()
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
        
        /*Obtiene el usuario que manda automáticamente los correos cumpleaños*/
        try
        {
            sQ = "SELECT estac FROM estacs WHERE mandcump = 1 LIMIT 1";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colocalo en el control*/
            if(rs.next())
                jTUsrCump.setText(rs.getString("estac"));     

        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene el usuario que manda automáticamente los correos agradecimiento*/
        try
        {
            sQ = "SELECT estac, manddia FROM estacs WHERE mandagra = 1 LIMIT 1";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colocalos en sus controles*/
            if(rs.next())
            {
                jTDias.setText      (rs.getString("manddia"));                            
                jTUsrAgra.setText   (rs.getString("estac"));
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        try
        {
            sQ  = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' and conf ='cumple'";                        
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                jTCumple.setText    (rs.getString("extr"));                            
                jTAsun.setText      (rs.getString("asun"));
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        try
        {
            sQ  = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' and conf ='agrad'";                        
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                jTCuerAgra.setText    (rs.getString("extr"));                            
                jTAsunAgra.setText    (rs.getString("asun"));
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Obtiene las configuraciones del sistema*/
        try
        {
            sQ  = "SELECT * FROM confgral WHERE clasif = 'sist'";                        
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Deslogearse en inactividad del sistema*/
                if(rs.getString("conf").compareTo("dlogin")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("0")!=0)
                    {
                        /*Marca el checkbox de inactividad*/
                        jCDesInac.setSelected(true);
                        
                        /*Coloca el tiempo de inactividad en el control*/
                        jTMin.setText(rs.getString("val"));
                    }
                    else
                    {
                        /*Deshablita el control de los minutos*/
                        jTMin.setEnabled(false);
                        
                        /*Desmarca el checkbox de inactividad*/
                        jCDesInac.setSelected(false);
                        
                        /*Coloca 5 en el control*/
                        jTMin.setText("5");
                    }
                }
                //Else if es la configuración redondear las cantidades
                else if(rs.getString("conf").compareTo("redon")==0)
                {
                    //Marca o desmarca el check
                    if(rs.getInt("val")==1)
                        jCRedon.setSelected(true);
                    else
                        jCRedon.setSelected(false);
                    
                    //Coloca el valor de redondeo en el control
                    jTRedon.setText(rs.getString("nume"));
                }
                //Else if es la configuración de poder cambiar tipo de cambio en el sistema
                else if(rs.getString("conf").compareTo("tipcamtod")==0)
                {
                    //Marca o desmarca el check
                    if(rs.getInt("val")==1)
                        jCDefTipCamSis.setSelected(true);
                    else
                        jCDefTipCamSis.setSelected(false);
                }
                //Else if es la configuración de mostrar mensajes de correos electrónicos
                else if(rs.getString("conf").compareTo("mostmsjusr")==0)
                {
                    //Marca o desmarca el check
                    if(rs.getInt("val")==1)
                        jCMosMsjConfCorr.setSelected(true);
                    else
                        jCMosMsjConfCorr.setSelected(false);
                }
                /*Else if es la ruta de la calculadora entonces*/
                else if(rs.getString("conf").compareTo("calc")==0)
                {
                    /*Coloca la ruta en el control*/
                    jTRutCalc.setText(rs.getString("extr"));
                }                
                /*Else if es la ruta del bloc de notas entonces*/
                else if(rs.getString("conf").compareTo("cuader")==0)
                {
                    /*Coloca la ruta en el control*/
                    jTRutCuade.setText(rs.getString("extr"));
                }
                /*Else if es la ruta de la aplicación favorita entonces*/
                else if(rs.getString("conf").compareTo("apfavo")==0)
                {
                    /*Coloca la ruta en el control*/
                    jTRutAp.setText(rs.getString("extr"));
                }                
                //Else if es la configuración de multilogeo entonces
                else if(rs.getString("conf").compareTo("usrmulti")==0)
                {
                    //Coloca la bandera correcta dependiendo la configuración
                    if(rs.getInt("val")==1)
                        jCUsrMulti.setSelected(true);
                    else
                        jCUsrMulti.setSelected(false);
                }
                
            }/*Fin de while(rs.next())*/                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargSist()*/
    
    //Carga las configuraciones de previo de compra
    private void vCargPrevComp()
    {
        
        /*Abre la base de datos*/        
        Connection  con;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );             
        } 
        catch(SQLException | HeadlessException e) 
        {    
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
     
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;          
        String      sQ              = ""; 
        
        /*Obtiene las configuraciones del sistema*/
        try
        {
            sQ  = "SELECT * FROM confgral WHERE clasif = 'prev'";                        
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                //Configuración de movimientos de inventarios entradas y salidas con existencias
                if(rs.getString("conf").compareTo("prevporusuario")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevUsuario.setSelected(true);
                    else
                        jBPrevUsuario.setSelected(false);                    
                }    
                //Configuración de traspasos entre almacenes solo con existencias
                else if(rs.getString("conf").compareTo("prevmonac")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevMonNacional.setSelected(true);
                    else
                        jBPrevMonNacional.setSelected(false);                    
                }
                //Configuración de almacenamiento de series 
                else if(rs.getString("conf").compareTo("prevmodesc")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevModDesc.setSelected(true);
                    else
                        jBPrevModDesc.setSelected(false);                    
                }
                
                //Configuración de almacenamiento de series 
                else if(rs.getString("conf").compareTo("prevmodprec")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevModPrec.setSelected(true);
                    else
                        jBPrevModPrec.setSelected(false);                    
                } 
                //Configuración de almacenamiento de series 
                else if(rs.getString("conf").compareTo("prevunavezser")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevCotunaSerie.setSelected(true);
                    else
                        jBPrevCotunaSerie.setSelected(false);                    
                } 
                //Configuración de almacenamiento de series 
                else if(rs.getString("conf").compareTo("prevobligarser")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jBPrevSerObli.setSelected(true);
                    else
                        jBPrevSerObli.setSelected(false);                    
                } 
            }                        
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//Final private void vCargPrevComp()
    
    //Carga las configuraciones de inventarios
    private void vCargInv()
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
        
        /*Obtiene las configuraciones del sistema*/
        try
        {
            sQ  = "SELECT * FROM confgral WHERE clasif = 'inv'";                        
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                //Configuración de movimientos de inventarios entradas y salidas con existencias
                if(rs.getString("conf").compareTo("esexitmov")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jCESExistMov.setSelected(true);
                    else
                        jCESExistMov.setSelected(false);                    
                }    
                //Configuración de traspasos entre almacenes solo con existencias
                else if(rs.getString("conf").compareTo("traspasexis")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jCTraspasExis.setSelected(true);
                    else
                        jCTraspasExis.setSelected(false);                    
                }  
                 
                //cambio alan
                //Configuración de almacenamiento de series 
                else if(rs.getString("conf").compareTo("igualser")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getInt("val")==1)
                        jCSerRep.setSelected(true);
                    else
                        jCSerRep.setSelected(false);                    
                } 
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
        
    }/*Fin de private void vCargInv()*/
    
    
    /*Carga las configuraciones de compras*/
    private void vCargComps()
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

        /*Obtiene las configuraciones del sistema*/
        try
        {
            sQ = "SELECT * FROM confgral WHERE clasif = 'comps'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Deslogearse en inactividad del sistema*/
                if(rs.getString("conf").compareTo("compsxusr")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCompXUsr.setSelected(true);                    
                    else
                        jCCompXUsr.setSelected(false);                    
                }   
                /*Else if se tienen que poder modificar la descripción en las compras o no*/
                else if(rs.getString("conf").compareTo("moddescrip")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModDescripComp.setSelected(true);
                    else
                        jCModDescripComp.setSelected(false);
                }                
                /*Else if se tiene que ponder la órden de compra en vez de la compra*/
                else if(rs.getString("conf").compareTo("iniord")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCIniOrd.setSelected(true);
                    else
                        jCIniOrd.setSelected(false);
                }                
                /*Else if se tienen que poder modificar el precio en las compras o no*/
                else if(rs.getString("conf").compareTo("modprec")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModPrecComp.setSelected(true);
                    else
                        jCModPrecComp.setSelected(false);
                }                
                /*Else if solo se debe comprar en moneda nacional o no*/
                else if(rs.getString("conf").compareTo("otramon")==0)
                {                    
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("0")==0)
                        jCCompMN.setSelected(true);                    
                    else
                        jCCompMN.setSelected(false);                    
                }
                
            }/*Fin de while(rs.next())*/                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);              
        
    }/*Fin de private void vCargComps()*/
    
    
    /*Carga las configuraciones de ventas*/
    private void vCargCots()
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

        /*Obtiene las configuraciones de ventas*/
        try
        {
            sQ = "SELECT * FROM confgral WHERE clasif = 'cots'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Vender sin existencia en cotizaciones*/
                if(rs.getString("conf").compareTo("vendsinexistcot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCVSinExistCot.setSelected(true);
                    else
                        jCVSinExistCot.setSelected(false);
                }               
                /*Else if se puede o no modificar la lista de precios en las cotizaciones*/
                else if(rs.getString("conf").compareTo("modlistcot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModListC.setSelected(true);
                    else
                        jCModListC.setSelected(false);
                }         
                /*Else if se tienen que poder modificar la descripción en las cotizaciones o no*/
                else if(rs.getString("conf").compareTo("moddescrip")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModDescripCot.setSelected(true);
                    else
                        jCModDescripCot.setSelected(false);
                }         
                /*Else if configuración para el almacén de cotización del punto de venta, entonces colocalo en el control*/
                else if(rs.getString("conf").compareTo("almavtac")==0)                                    
                    jTAlmaCot.setText(rs.getString("extr"));                
                /*Else if se tiene que agregar la serie en la descripción de las cotizaciones*/
                else if(rs.getString("conf").compareTo("garadesccot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCGaraC.setSelected(true);
                    else
                        jCGaraC.setSelected(false);
                }                
                /*Else if se tienen que poder modificar el precio en las cotizaciones o no*/
                else if(rs.getString("conf").compareTo("modprec")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCModPrecCot.setSelected(true);
                    else
                        jCModPrecCot.setSelected(false);
                }                
                /*Else if se tiene que cotizar solo con moneda nacional o no*/
                else if(rs.getString("conf").compareTo("otramon")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("0")==0)
                        jCCotMN.setSelected(true);
                    else
                        jCCotMN.setSelected(false);
                }
                /*Else if, aplicar precio de lista de la empresa en las cotizaciones*/
                else if(rs.getString("conf").compareTo("alistpreclicot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCPLCot.setSelected(true);
                    else
                        jCPLCot.setSelected(false);
                }
                /*Else if mostrar solo las cotizaciones de los usuarios*/
                else if(rs.getString("conf").compareTo("cotsxusr")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCCotsXUsr.setSelected(true);
                    else
                        jCCotsXUsr.setSelected(false);
                }
                /*Else if, mostrar mensaje de existencias negativas en las cotizaciones*/
                else if(rs.getString("conf").compareTo("msjexistnegcot")==0)
                {
                    /*Marca o desmarca el checkbox*/
                    if(rs.getString("val").compareTo("1")==0)
                        jCMMECot.setSelected(true);
                    else
                        jCMMECot.setSelected(false);
                }                
                                                
            }/*Fin de while(rs.next())*/                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargCots()*/
        
        
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
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();                  
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el boton de todos los productos se pueden vender abajo del costo*/
    private void jBTBajCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTBajCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTBajCostKeyPressed

    
    /*Cuando se presiona una tecla en el botón de todos los productos no se pueden vender abajo del costo*/
    private void jBTNoBajCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTNoBajCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTNoBajCostKeyPressed

    
    /*Cuando se presiona el botón de si todos los productos se pueden vender abajo del costo*/
    private void jBTBajCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTBajCostActionPerformed
        
        /*Muestra la forma de clave de seguridad*/
        ClavMast cl = new ClavMast(this, 0);
        cl.setVisible(true);
        
        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)                    
            return;        
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres que todos los productos \"SI\" se puedan vender abajo del costo?", "Productos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Actualiza el estado de todos los productos para que se vendan abajo del costo*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "bajcost      = 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "'";                    
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
        JOptionPane.showMessageDialog(null, "Exito en la modificación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBTBajCostActionPerformed

    
    /*Cuando se presiona el botón de que todos los prods no se puedan vender abajo del costo*/
    private void jBTNoBajCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTNoBajCostActionPerformed
        
        /*Muestra la forma de clave de seguridad*/
        ClavMast cl = new ClavMast(this, 0);
        cl.setVisible(true);
        
        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)
            return;
        
        /*Preguntar al usuario si esta seguro*/
        Object[] op = {"Si","No"};
        int iRes    =   JOptionPane.showOptionDialog(this, "¿Seguro que quieres que todos los productos \"NO\" se puedan vender abajo del costo?", "Productos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Actualiza el estado de todos los productos para que se no vendan abajo del costo*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "bajcost      = 0, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "'";                    
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
        JOptionPane.showMessageDialog(null, "Exito en la modificación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBTNoBajCostActionPerformed

    
    /*Cuando se presiona una tecla en el tabb panel*/
    private void jTBPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTBPKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de precio de lista*/
    private void jCPListCFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPListCFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCPListCFacKeyPressed

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona una tecla en el panel de ventas*/
    private void jPFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPFacKeyPressed

    
    /*Cuando se presiona una tecla en el panel inventarios*/
    private void jPInvenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPInvenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPInvenKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Si no a seleccionado una serie para las facturas entonces*/
        if(jComSerF.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para las facturas.", "Serie facturas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSerF.grabFocus();
            return;
        }
        
        /*Si no a seleccionado una serie para los tickets entonces*/
        if(jComSerT.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para los tickets.", "Serie tickets", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSerT.grabFocus();
            return;
        }
        
        /*Si no a seleccionado una serie para las remisiones entonces*/
        if(jComSerR.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para las remisiones.", "Serie remisiones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSerR.grabFocus();
            return;
        }
        
        /*Si no a seleccionado un almacén para el punto de venta entonces*/
        if(jTAlma.getText().compareTo("")==0)
        {                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un almacén de venta para el punto de venta primeramente.", "Almacén de venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTAlma.grabFocus();
            return;
        }
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Checa si el código del almacén ya existe en la base de datos*/        
        int iRes        = Star.iExiste(con, jTAlma.getText().trim(), "almas", "alma");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El almacén: " + jTAlma.getText().trim() + " de venta para el punto de venta no existe.", "Almacén venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Colcoa el foco del teclado en el conrol y regresa*/
            jTAlma.grabFocus();
            return;
            
        }//Fin de if(iRes==0)
        
    	/*Si el almacén de venta no es cadena vacia entonces*/
        if(jTAlmaVta.getText().trim().compareTo("")!=0)
        {
            /*Checa si el código del almacén ya existe en la base de datos*/        
            iRes        = Star.iExiste(con, jTAlmaVta.getText().trim(), "almas", "alma");

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;

            //Si no existe entonces
            if(iRes==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El almacén: " + jTAlmaVta.getText() + " de facturas no existe.", "Almacén venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el borde rojo*/                               
                jTAlmaVta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Colcoa el foco del teclado en el conrol y regresa*/
                jTAlmaVta.grabFocus();
                return;
                
            }//Fin de if(iRes==0)
                        
        }/*Fin de if(jTAlmaVta.getText().trim().contains("")!=0)*/                    
        
        /*Si el almacén de cotización no es cadena vacia entonces*/
        if(jTAlmaCot.getText().trim().compareTo("")!=0)
        {
            /*Checa si el código del almacén ya existe en la base de datos*/        
            iRes        = Star.iExiste(con, jTAlmaCot.getText().trim(), "almas", "alma");

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            
            //Si no existe entonces
            if(iRes==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El almacén: " + jTAlmaCot.getText() + " de cotización no existe.", "Almacén cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el borde rojo*/                               
                jTAlmaCot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Colcoa el foco del teclado en el conrol y regresa*/
                jTAlmaCot.grabFocus();
                return;
                
            }//Fin de if(iRes==0)
                        
        }/*Fin de if(jTAlmaCot.getText().trim().contains("")!=0)*/                    

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        /*Si el usuario que manda automáticamente correos de cumpleaños no es cadena vacia entonces*/        
        if(jTUsrCump.getText().trim().compareTo("")!=0)
        {
            /*Comprueba si el usuario que manda correos automáticamente de cumpleaños existe*/            
            try
            {
                sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsrCump.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El usuario que manda correos de cumpleaños: " + jTUsrCump.getText() + " no existe.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca el borde rojo*/                               
                    jTUsrCump.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Colcoa el foco del teclado en el conrol y regresa*/
                    jTUsrCump.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
        }/*Fin de if(jTUsrCump.getText().compareTo("")!=0)*/            
        
        /*Si el usuario que manda automáticamente correos de agradecimiento no es cadena vacia entonces*/        
        if(jTUsrAgra.getText().trim().compareTo("")!=0)
        {
            /*Comprueba si el usuario que manda correos automáticamente de agradecimiento existe*/
            try
            {
                sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsrAgra.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El usuario que manda correos de agradecimiento: " + jTUsrCump.getText().trim() + " no existe.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca el borde rojo*/                               
                    jTUsrAgra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Colcoa el foco del teclado en el conrol y regresa*/
                    jTUsrAgra.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
        }/*Fin de if(jTUsrAgra.getText().compareTo("")!=0)*/                    
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Preguntar al usuario si esta seguro de guardar la configuración*/
        Object[] op = {"Si","No"};
        iRes        =   JOptionPane.showOptionDialog(this, "¿Seguro de guardar la configuración?", "Guardar configuración", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Guarda configuración de ventas*/
        vGuaConfVtas(con);
       
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera y regresa*/
            bErr    = false;            
            return;
        }
       
        /*Guarda configuración de cotizaciones*/
        vGuaConfCots(con);
       
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera de error y regresa*/
            bErr    = false;            
            return;
        }
       
        /*Guarda configuración del sistema*/
        vGuaConfSist(con);
       
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera de error y regresa*/
            bErr    = false;            
            return;
        }    
       
        /*Guarda configuración de compras*/
        vGuaConfComp(con);
              
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera de error y regresa*/
            bErr    = false;            
            return;
        }
       
        //Guarda configuración de inventarios
        vGuaConfInv(con);
       
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera y regresa*/
            bErr    = false;            
            return;
        }
        
		//Guarda configuracion de previos
        vGuaConfPrev(con);
                
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera y regresa*/
            bErr    = false;            
            return;
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea*/
       JOptionPane.showMessageDialog(null, "Configuración guardada con éxito.", "Configuracion", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
       
       /*Llama al recolector de basura*/
       System.gc();
        
       /*Cierra la forma*/
       dispose();
       
    }//GEN-LAST:event_jBGuarActionPerformed
            
    
    /*Cuando se presiona una tecla en el checkbox de aplicar precio de lista en punto de venta de las empresas*/
    private void jCPLEPvtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPLEPvtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCPLEPvtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar mensaje de existencia en factura*/
    private void jCMMEFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMMEFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMMEFacKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar mensaje de existencia en punto de venta*/
    private void jCMMEPvtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMMEPvtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMMEPvtaKeyPressed
    
    
    /*Cuando se presiona una tecla en el checkbox de vender sin existencia en el punto de venta*/
    private void jCVSinExistPvtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVSinExistPvtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVSinExistPvtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vender sin existencia en facturas*/
    private void jCVendSinExistFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVendSinExistFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVendSinExistFacKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de aplicar precio de lista de empresa en cotizaciones*/    
    private void jCPLCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPLCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCPLCotKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar mensaje de cuando no hay existencias en las cotizaciones*/    
    private void jCMMECotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMMECotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMMECotKeyPressed
    
    
    /*Cuando se presiona una tecla en el checkbox de vender sin existencias en cotización*/
    private void jCVSinExistCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVSinExistCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVSinExistCotKeyPressed

    
    /*Cuando se presiona una tecla en el campo de mostrar la barra latera en el punto de venta*/
    private void jCBarrLatPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBarrLatPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCBarrLatPKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar imágenes en las líneas del punto de venta*/
    private void jCImgLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImgLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCImgLinKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de iniciar el punto de venta al entrar al sistema*/
    private void jCInitPvtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCInitPvtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCInitPvtaKeyPressed
    
    
    /*Cuando se presiona una tecla en el checkbox de catálogo general de punto de venta*/
    private void jCCatGralPvtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCatGralPvtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCatGralPvtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de catálogo general en las facturas*/
    private void jCCatGralFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCatGralFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCatGralFacKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar logo en las facturas*/
    private void jCMosLogFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosLogFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMosLogFacKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar el logo en los tickets*/
    private void jCMosLogTikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosLogTikKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMosLogTikKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar logo en las remisiones*/
    private void jCMosLogRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosLogRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMosLogRemKeyPressed

    
    /*Cuando se presiona una tecla en el panel de inventarios*/
    private void jPSisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPSisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPSisKeyPressed

    
    /*Cuando se presiona una tecla en el panel de cotizaciones*/
    private void jPCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPCotKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de deslogear por inactividad*/
    private void jCDesInacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDesInacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCDesInacKeyPressed
    
    
    /*Cuando se presiona una tecla en el campo de los minutos*/
    private void jTMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTMinKeyPressed

    
    /*Cuando se pierde el foco del teclado en el conrol del tiempo de inactividad para deslogin*/
    private void jTMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMin.setCaretPosition(0);
        
        /*Si el tiempo es cadena vacia O 0 entonces que sea 5 y regresa*/
        if(jTMin.getText().compareTo("")==0 || jTMin.getText().compareTo("0")==0)
            jTMin.setText("5");                                    
                        
    }//GEN-LAST:event_jTMinFocusLost

    
    /*Cuando se tipea una tecla en el campo de inactividad y deslogear*/
    private void jTMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMinKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTMinKeyTyped

    
    /*Cuando sucede una acción en el checkbo de inactividad y deslogeo*/
    private void jCDesInacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCDesInacActionPerformed
        
        /*Habilita o deshabilita el campo de los minutos*/
        if(jCDesInac.isSelected())
            jTMin.setEnabled(true);
        else
            jTMin.setEnabled(false);
        
    }//GEN-LAST:event_jCDesInacActionPerformed

    
    /*Cuando la rueda del ratón se mueve*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona una tecla en el checkbox de límite de Crédito en el punto de venta*/
    private void jCLimCredPVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCLimCredPVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCLimCredPVtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vender sobre límite de Crédito en facturas*/
    private void jCLimCredFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCLimCredFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCLimCredFacKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vender sobre límite de Crédito en remisione*/
    private void jCLimCredRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCLimCredRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCLimCredRemKeyPressed

    
    
    /*Cuando se presiona una tecla en el botón de cargar imágen de cumpleaños*/
    private void jBCargCumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargCumKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBCargCumKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar imágen de cumpleaños*/
    private void jBDelCumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelCumKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBDelCumKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ver en grande imágen de cumpleaños*/
    private void jBBuscCumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscCumKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBBuscCumKeyPressed

    
    /*Cuando se presioan el botón de cargar imágen de cumpleaños*/
    private void jBCargCumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargCumActionPerformed
        
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de cumpleaños no existe entonces creala*/
        sCarp                    += "\\Cumpleanos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen de cumpleaños en \"" + sCarp + "\".", "Cumpleaños", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));          
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)
            return;
                
        /*Lee la ruta seleccionada*/
        sRut                = fc.getCurrentDirectory().getAbsolutePath();

        /*Concatena la carpeta final seleccionada*/
        sRut                += "\\" + fc.getSelectedFile().getName();    

        /*Completa la ruta final con el nombre del archivo a donde se copiara*/
        sCarp               +=  "\\" + fc.getSelectedFile().getName();  

        /*Si el archivo de origén no existe entonces*/
        if( !new File(sRut).exists())
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));              
            return;
        }

        /*Copia el archivo orgien al destino*/
        try
        {
            org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;
        }           

        /*Mensjea de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen de cumpleaños guardada con éxito en \"" + sCarp + "\".", "Cumpleaños", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBCargCumActionPerformed

    
    /*Cuando se presiona el botón de borrar imágen de cumpleaños entonces*/
    private void jBDelCumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelCumActionPerformed
        
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de cumpleaños no existe entonces crea la carpeta*/
        sCarp                    += "\\Cumpleanos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de cumpleaños para borrar en \"" + sCarp + "\".", "Cumpleañoss", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                return;
            }
        }                    

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;
        }                       
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen de cumpleaños borrada con éxito.", "Cumpleaños", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));       
        
    }//GEN-LAST:event_jBDelCumActionPerformed

    
    /*Cuando se presiona el botón de bùscar imágen de cumpleaños entonces*/
    private void jBBuscCumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscCumActionPerformed
                
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de cumpleaños no existe entonces creala*/
        sCarp                    += "\\Cumpleanos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de cumpleaños en \"" + sCarp + "\".", "Cumpleañoss", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                    
                }
            }
        }                    
        
    }//GEN-LAST:event_jBBuscCumActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de minutos*/
    private void jTMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMin.setSelectionStart(0);jTMin.setSelectionEnd(jTMin.getText().length());        
        
    }//GEN-LAST:event_jTMinFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de cumpleaños*/
    private void jTCumpleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCumpleFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCumple.setSelectionStart(0);jTCumple.setSelectionEnd(jTCumple.getText().length());        
        
    }//GEN-LAST:event_jTCumpleFocusGained

    
    /*Cuando se presiona una tecla en el campo del cumpleaños*/
    private void jTCumpleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCumpleKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTCumpleKeyPressed

       
    /*Cuando se presiona una tecla en el campo de asunto del correo de cumpleaños*/
    private void jTAsunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunKeyPressed
      
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTAsunKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del asunto del cumpleaños*/
    private void jTAsunFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsun.setSelectionStart(0);jTAsun.setSelectionEnd(jTAsun.getText().length());        
        
    }//GEN-LAST:event_jTAsunFocusGained

    
    
    /*Cuandos se presiona una tecla en el botón de cargar imágen de agradecimientos*/
    private void jBCargAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargAgraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBCargAgraKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar imágen de agradecimientos*/
    private void jBDelAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelAgraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBDelAgraKeyPressed

    
    /*Cuando se presiona una tecla ene l botón de ver imágen de agradecimientos en grande*/
    private void jBVerAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerAgraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBVerAgraKeyPressed

    
    /*Cuando se presiona una tecla en el campo de asunto del agradecimiento*/
    private void jTAsunAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunAgraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTAsunAgraKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo agradecimiento*/
    private void jTCuerAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerAgraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTCuerAgraKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del asunto del agradecimiento*/
    private void jTAsunAgraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunAgraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsun.setSelectionStart(0);jTAsun.setSelectionEnd(jTAsun.getText().length());
        
    }//GEN-LAST:event_jTAsunAgraFocusGained

    
    /*Cuandos se gana el foco del teclado en el campo del cuerpo del agradecimiento*/
    private void jTCuerAgraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerAgraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerAgra.setSelectionStart(0);jTCuerAgra.setSelectionEnd(jTCuerAgra.getText().length());        
        
    }//GEN-LAST:event_jTCuerAgraFocusGained

    
   /*Cuando se presiona el botón de cargar imágen de agradecimientos*/
    private void jBCargAgraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargAgraActionPerformed
        
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }

        /*Si la carpeta de las imágenes no existe entonces creala la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de agradecimientos no existe entonces crea la carpeta*/
        sCarp                    += "\\Agradecimiento";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen de agradecimiento en \"" + sCarp + "\".", "Agradecimiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));          
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));              
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;               
            }           
            
            /*Mensjea de éxito*/
            JOptionPane.showMessageDialog(null, "Imágen de agradecimientos guardada con éxito en \"" + sCarp + "\".", "Agradecimientos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCargAgraActionPerformed

    
    /*Cuando se presiona el botón de borrar imágen de agradecimientos*/
    private void jBDelAgraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelAgraActionPerformed
        
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de agradecimientos no existe entonces crea la carpeta*/
        sCarp                    += "\\Agradecimiento";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de agradecimiento para borrar en: " + sCarp + ".", "Agradecimiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                return;
            }
        }                    

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }                       
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen de agradecimientos borrada con éxito.", "Agradecimiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));       
        
    }//GEN-LAST:event_jBDelAgraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen de agradecimiento*/
    private void jBVerAgraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerAgraActionPerformed
        
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de agradecimiento no existe entonces creala*/
        sCarp                    += "\\Agradecimiento";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de agradecimiento en \"" + sCarp + "\".", "Agradecimiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                    
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVerAgraActionPerformed

    
    /*Cuando se presiona una tecla en el campo de días*/
    private void jTDiasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTDiasKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de los días que debe de mandar correo de agradecimiento*/
    private void jTDiasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiasFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDias.setSelectionStart(0);jTDias.setSelectionEnd(jTDias.getText().length());        
        
    }//GEN-LAST:event_jTDiasFocusGained

        
    /*Cuando se tipea una tecla en el campo de días*/
    private void jTDiasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiasKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDiasKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de los días entonces*/
    private void jTDiasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDias.setCaretPosition(0);
        
        /*Si es cadena vacia entonces que sea 0*/
        if(jTDias.getText().compareTo("")==0)
            jTDias.setText("0");
        
        /*Deja el valor absoluto*/
        int iVal    = Integer.parseInt(jTDias.getText());
        iVal        = Math.abs(iVal);
        
        /*Colocalo en el campo*/
        jTDias.setText(Integer.toString(iVal));
        
    }//GEN-LAST:event_jTDiasFocusLost

    
    /*Cuando se presiona una tecla en el panel de compras*/
    private void jPCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPCompKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar solo las ventas por usuario*/
    private void jCVtasXUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVtasXUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVtasXUsrKeyPressed
    
    
    /*Cuando se presiona na tecla en el checkbox de mostrar solo cotizaciones de usuario*/
    private void jCCotsXUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCotsXUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCotsXUsrKeyPressed

       
    /*Cuando se presiona una tecla en el checkbox de mostrar solo compras de los usuarios*/
    private void jCCompXUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompXUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCompXUsrKeyPressed
   
    
    /*Cuando se presiona una tecla en el checkbox de solo comprar en moneda nacional*/
    private void jCCompMNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompMNKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCompMNKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vender solo en moneda nacional*/
    private void jCVendMNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVendMNKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVendMNKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cotizar solo en moneda nacional*/
    private void jCCotMNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCotMNKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCotMNKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corte X guardado automático*/
    private void jCCortXAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCortXAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCortXAKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corte Z guardado automático*/
    private void jCCortZAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCortZAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCortZAKeyPressed

    
    /*Cuando se presiona una tecla en el panel del punto de venta*/
    private void jPPtoVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPPtoVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPPtoVtaKeyPressed

    /*Cuando se presiona uan tecla en el panel de las remisiones*/
    private void jPRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPRemKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPRemKeyPressed

    
    /*Cuando se presiona una tecla en el panel de los tickets*/
    private void jPTicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPTicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPTicKeyPressed

    
    /*Cuando se presiona una tecla en el menù de ver facturas en el punto de venta*/
    private void jCVFacPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVFacPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVFacPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ver remisiones en el bùscador del punto de venta*/
    private void jCVRemPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVRemPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVRemPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ver tickets en el bùscador del punto de venta*/
    private void jCVTicPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVTicPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCVTicPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de se puede o no imprimir facturas en el punto de venta*/
    private void jCImpFacPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpFacPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCImpFacPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de imprimir remisiones en el punto de venta o no*/
    private void jCImpRemPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpRemPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCImpRemPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de imprimir tickets en el punto de venta*/
    private void jCImpTicPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpTicPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jCImpTicPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de canelar facturas en el punto de venta o no*/
    private void jCCanFacPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCanFacPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCanFacPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cancelar remisiones en el punto de venta*/
    private void jCCanRemPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCanRemPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCCanRemPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cancelar tickets desde el punto de venta o no*/
    private void jCCanTicPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCanTicPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jCCanTicPtoKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la ruta de la calculadora*/
    private void jTRutCalcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutCalcFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRutCalc.setSelectionStart(0);jTRutCalc.setSelectionEnd(jTRutCalc.getText().length());        
        
    }//GEN-LAST:event_jTRutCalcFocusGained

    
    /*Cuando se presiona una tecla en el campo de la ruta a la calculadora*/
    private void jTRutCalcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutCalcKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jTRutCalcKeyPressed

    
    /*Cuando se presiona una tecla en el botón de búscar calculadora*/
    private void jBCalcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCalcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jBCalcKeyPressed

    
    /*Cuando se presiona el botón de búscar calculadora*/
    private void jBCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCalcActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTRutCalc.setText(sRut);
        }
        
    }//GEN-LAST:event_jBCalcActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de dar de alta empresas en el punto de venta*/
    private void jCEmpsPtoVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEmpsPtoVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCEmpsPtoVtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de modificar descripción en el punto de venta o no*/
    private void jCDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCDescripKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de pedir clave de administrador en cancelaciones de ventas en el punto de venta*/
    private void jCAdmCanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAdmCanKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCAdmCanKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar ticket de cancelación en el punto de venta*/
    private void jCMosTicCanPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosTicCanPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jCMosTicCanPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar ticket de cancelación en las facturas*/
    private void jCMosTicCanFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosTicCanFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMosTicCanFKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar PDF de cancelación en facturas*/
    private void jCGuaPDFCanFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaPDFCanFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCGuaPDFCanFKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ver mensajes desde el punto de venta*/
    private void jCMsjPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMsjPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMsjPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de chatear corporativamente en el punto de venta*/
    private void jCChatPtoCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCChatPtoCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCChatPtoCKeyPressed

    
    /*Cuando se presiona una tecla en el panel de chat*/
    private void jPChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPChatKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jPChatKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar historial de chat corporativo*/
    private void jBDelHisCorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelHisCorKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBDelHisCorKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar historial de usuarios*/
    private void jBDelHisEstacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelHisEstacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBDelHisEstacKeyPressed

    
    /*Cuando se presiona el botón de borrar historial de chat de corporativo*/
    private void jBDelHisCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelHisCorActionPerformed
        
        /*Preguntar al usuario si esta seguro de continuar*/
        Object[] op = {"Si","No"};
        int iRes    =   JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el historial corporativo?", "Borrar Historial Corporativo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Borra el historial de mensajes corporativos*/
        try 
        {                
            sQ = "DELETE FROM chat WHERE estacdestin = ''";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
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

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Historial Borrado con Exito para Chat Corporativo.", "Borrar Historial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelHisCorActionPerformed

    
    /*Cuando se presiona el botón de borrar historial de usuarios*/
    private void jBDelHisEstacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelHisEstacActionPerformed
        
        /*Preguntar al usuario si esta seguro de continuar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el historial de usuarios?", "Borrar Historial Usuarios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Borra el historial de mensajes corporativos*/
        try 
        {                
            sQ = "DELETE FROM chat WHERE estacdestin <> ''";                    
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
        JOptionPane.showMessageDialog(null, "Historial Borrado con Exito para Chat de Usuarios.", "Borrar Historial Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelHisEstacActionPerformed

    
    /*Cuando se presiona una tecla en el control de la ruta del cuaderno*/
    private void jTRutCuadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutCuadeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jTRutCuadeKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la ruta del cuaderno*/
    private void jTRutCuadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutCuadeFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRutCuade.setSelectionStart(0);jTRutCuade.setSelectionEnd(jTRutCuade.getText().length());        
        
    }//GEN-LAST:event_jTRutCuadeFocusGained

    
    /*Cuando se presiona una tecla en el botón de búscar ruta al cuaderno*/
    private void jBCuadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCuadeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jBCuadeKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la ruta a la aplicación favorita*/
    private void jTRutApKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutApKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jTRutApKeyPressed

    
    /*Cuando se presiona una tecla en el botón de búscar ruta a la aplicación favorita*/
    private void jBApFavKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBApFavKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);          
        
    }//GEN-LAST:event_jBApFavKeyPressed

    
    /*Cuando se gana el foco del teclado en el control de la ruta de la aplicación favorita*/
    private void jTRutApFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutApFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRutCuade.setSelectionStart(0);jTRutCuade.setSelectionEnd(jTRutCuade.getText().length());
        
    }//GEN-LAST:event_jTRutApFocusGained

    
    /*Cuando se presiona el botón de búscar ruta al cuaderno*/
    private void jBCuadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCuadeActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta el cuaderno*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Cuaderno");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTRutCuade.setText(sRut);

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCuadeActionPerformed

    
    /*Cuando se presiona el botón de búscar ruta a la aplicación favorita*/
    private void jBApFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBApFavActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar aplicación");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTRutAp.setText(sRut);

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBApFavActionPerformed

    
    /*Cuando se presiona un tecla en el checkbox de insertar dinero automáticamente en el cajón de dinero*/
    private void jCInsAutCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCInsAutCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCInsAutCajKeyPressed

    
    /*Cuando se presiona una tecla en el campo del dinero a insertar en el cajón*/
    private void jTDinCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDinCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDinCajKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control del dinero a insertar en cajón*/
    private void jTDinCajFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDinCajFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDinCaj.setCaretPosition(0);
        
        /*Si el contro les cadena vacia entonces que sea 0 y regresa*/
        if(jTDinCaj.getText().compareTo("")==0)
        {
            jTDinCaj.setText("$0.00");
            return;
        }
        
        /*Dale formato de moneda a la cantidad y colocalo en su lugar*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(jTDinCaj.getText());                
        jTDinCaj.setText(n.format(dCant));          
        
    }//GEN-LAST:event_jTDinCajFocusLost

    
    /*Cuando se tipea una tecla en el campo de ingresar dinero al cajón*/
    private void jTDinCajKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDinCajKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTDinCajKeyTyped

    
    /*Cuando se gana el foco del teclado en el control del dinero a insertar en el cajón*/
    private void jTDinCajFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDinCajFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDinCaj.setSelectionStart(0);jTDinCaj.setSelectionEnd(jTDinCaj.getText().length());        
        
    }//GEN-LAST:event_jTDinCajFocusGained

    
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

    
    /*Cuando se presiona una tecla en el checkbox de pedir clave de seguridad al facturar*/
    private void jCClavFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCClavFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jCClavFacKeyPressed

    
    /*Cuando se presiona una tecla en el campo de clave de seguridad de facturas*/
    private void jTClavFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClavFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jTClavFacKeyPressed

    
    /*Cuando se gana el foco del teclado en el control de la clave de seguridad*/
    private void jTClavFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavFacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClavFac.setSelectionStart(0);jTClavFac.setSelectionEnd(jTClavFac.getText().length());        
        
    }//GEN-LAST:event_jTClavFacFocusGained

    
    /*Cuando se presiona un tecla en el checkbox de pedir clave de seguridad para facturar en punto de venta*/
    private void jCClavFacPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCClavFacPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jCClavFacPKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la contraseña de factura en el punto de venta*/
    private void jTClavFacPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavFacPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClavFacP.setSelectionStart(0);jTClavFacP.setSelectionEnd(jTClavFacP.getText().length());       
        
    }//GEN-LAST:event_jTClavFacPFocusGained

    
    /*Cuando se presiona una tecla en el campo de clave de seguridad de facturas en punto de venta*/
    private void jTClavFacPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClavFacPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jTClavFacPKeyPressed

    
    
    /*Cuando se presiona una tecla en el checkbox de corte automático X tras inicio de sesión*/
    private void jCCortXAutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCortXAutKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCortXAutKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ver las ventas solod el día*/
    private void jCDiaVtaPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDiaVtaPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDiaVtaPKeyPressed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
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
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el campo del asunto*/
    private void jTAsunFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAsun.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAsunFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cumpleaños*/
    private void jTCumpleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCumpleFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCumple.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCumpleFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTAsunAgraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunAgraFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAsunAgra.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAsunAgraFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCuerAgraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerAgraFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCuerAgra.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCuerAgraFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRutCalcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutCalcFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRutCalc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRutCalcFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRutCuadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutCuadeFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRutCuade.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRutCuadeFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRutApFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutApFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRutAp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRutApFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la clave de la factura*/
    private void jTClavFacPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavFacPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTClavFac.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClavFacPFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de las series de las facturas*/
    private void jComSerFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSerF.getSelectedItem().toString().compareTo("")!=0)
            jComSerF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFFocusLost

    
    /*Cuando sucede una acción en el combo de las series de las facturas*/
    private void jComSerFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerFActionPerformed

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'FAC' AND ser = '" + jComSerF.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSerF.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerFActionPerformed

    
    /*Cuando se presiona una tecla en el combo de las series de las facturas*/
    private void jComSerFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerFKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerFKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de la serie de los tickets*/
    private void jComSerTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerTFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSerF.getSelectedItem().toString().compareTo("")!=0)
            jComSerF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerTFocusLost

    
    /*Cuando sucede una acción en el combo de las series de los tickets*/
    private void jComSerTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerTActionPerformed

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'TIK' AND ser = '" + jComSerT.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSerT.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerTActionPerformed

    
    /*Cuando se preisona una tecla en el combo de las series de los tickets*/
    private void jComSerTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerTKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de las series de las remisiones*/
    private void jComSerRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerRFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSerR.getSelectedItem().toString().compareTo("")!=0)
            jComSerR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerRFocusLost

    
    /*Cuando sucede una acción en el combo de las series de las remisiones*/
    private void jComSerRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerRActionPerformed

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'REM' AND ser = '" + jComSerR.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSerR.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerRActionPerformed

    
    /*Cuando se presiona una tecla en el combo de las series de las remisiones*/
    private void jComSerRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerRKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerRKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTAlma.setSelectionStart(0);jTAlma.setSelectionEnd(jTAlma.getText().length());

    }//GEN-LAST:event_jTAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusLost

        /*Coloca el caret al principio del control*/
        jTAlma.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        if(jTAlma.getText().compareTo("")!=0)
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTAlmaFocusLost

    
    /*Cuando se presiona una tecla en el campo del almacén*/
    private void jTAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de almacén*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBAlma.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTAlmaKeyPressed

    
    /*Cuando se tipea una tecla en el campo del almacén*/
    private void jTAlmaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTAlmaKeyTyped

    
    /*Cuando el mouse entra el botón del almacén*/
    private void jBAlmaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaMouseEntered

        /*Cambia el color del fondo del botón*/
        jBAlma.setBackground(Star.colBot);

    }//GEN-LAST:event_jBAlmaMouseEntered

    
    /*Cuando el mouse sale del botón del almacén*/
    private void jBAlmaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBAlma.setBackground(colOri);

    }//GEN-LAST:event_jBAlmaMouseExited

    
    /*Cuando se presiona el botón del almacén*/
    private void jBAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlmaActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAlma.getText(), 42, jTAlma, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el botón del almacén*/
    private void jBAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del usuario*/
    private void jTUsrCumpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrCumpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsrCump.setSelectionStart(0);jTUsrCump.setSelectionEnd(jTUsrCump.getText().length());

    }//GEN-LAST:event_jTUsrCumpFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del usuario*/
    private void jTUsrCumpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrCumpFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsrCump.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTUsrCump.getText().compareTo("")!=0)
            jTUsrCump.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTUsrCumpFocusLost

    
    /*Cuando se presiona una tecla en el campo del usuario*/
    private void jTUsrCumpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrCumpKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUsrCump.getText(), 4, jTUsrCump, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);    

    }//GEN-LAST:event_jTUsrCumpKeyPressed

    
    /*Cuando se tipea una tecla en el campo del usuario*/
    private void jTUsrCumpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrCumpKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTUsrCumpKeyTyped

    
    /*Cuando el mouse entra en el campo del usuario*/
    private void jBUsrCumpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrCumpMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUsrCump.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUsrCumpMouseEntered

    
    /*Cuando el mouse sale del campo del usuario*/
    private void jBUsrCumpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrCumpMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUsrCump.setBackground(colOri);

    }//GEN-LAST:event_jBUsrCumpMouseExited

    
    /*Cuando se presiona el botón de búscar usuario*/
    private void jBUsrCumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrCumpActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsrCump.getText(), 4, jTUsrCump, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUsrCumpActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar usuario*/
    private void jBUsrCumpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrCumpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUsrCumpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del usuario de agradecimiento*/
    private void jTUsrAgraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrAgraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsrAgra.setSelectionStart(0);jTUsrAgra.setSelectionEnd(jTUsrAgra.getText().length());

    }//GEN-LAST:event_jTUsrAgraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del usuario de agradecimiento*/
    private void jTUsrAgraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrAgraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsrAgra.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTUsrAgra.getText().compareTo("")!=0)
            jTUsrAgra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTUsrAgraFocusLost

    
    /*Cuando se presiona uan tecla en el campo del usuario de agradecimiento*/
    private void jTUsrAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrAgraKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUsrAgra.getText(), 4, jTUsrAgra, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTUsrAgraKeyPressed

    
    /*Cuando se tipea una tecla en el campo del usuario de agradecimiento*/
    private void jTUsrAgraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrAgraKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTUsrAgraKeyTyped

    
    /*Cunado el mouse entra en el campo del usuario de agradecimiento*/
    private void jBUsrAgraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrAgraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUsrAgra.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUsrAgraMouseEntered

    
    /*Cuando el mouse sall del botón de usuario de agradecimiento*/
    private void jBUsrAgraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrAgraMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUsrAgra.setBackground(colOri);

    }//GEN-LAST:event_jBUsrAgraMouseExited

    
    /*Cunando se presiona una tecla en el campo del usuario de agradecimiento*/
    private void jBUsrAgraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrAgraActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsrAgra.getText(), 4, jTUsrAgra, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUsrAgraActionPerformed

    
    /*Cuando se presiona una tecla en el campo del agradecimiento*/
    private void jBUsrAgraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrAgraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUsrAgraKeyPressed

    
    /*Cuando se presiona el botón de ver imágen en grande de CX1*/
    private void jBVerCXC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerCXC1ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de CXC no existe entonces creala*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de REC1 no existe entonces creala*/
        sCarp                    += "\\REC1";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordatorio 1 CXC en \"" + sCarp + "\".", "Recordatorio CXC 1", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                    
                }
            }
        }
        
    }//GEN-LAST:event_jBVerCXC1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen de recordatorio CXC 1*/
    private void jBVerCXC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerCXC1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerCXC1KeyPressed

    
    /*Cuando se presiona el botón de búscar ruta de recordatorio 1 de CXC*/
    private void jBCXC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCXC1ActionPerformed

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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de REC1 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC1";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen de Recordatorio de CXC 2 en \"" + sCarp + "\".", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén: " + sRut + " no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;
            }

            /*Mensjea de éxito*/
            JOptionPane.showMessageDialog(null, "Imágen de Recordatorio 2 CXC guardada con éxito en: " + sCarp + ".", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCXC1ActionPerformed

    
    /*Cuando se preisona una tecla en el botón de búscar imágen de recordatorio 2 de CXC*/
    private void jBCXC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCXC1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCXC1KeyPressed

    
    /*Cuando se presiona el botónd de borrar imágen de recordatorio 1 CXC*/
    private void jBDelCXC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelCXC1ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de REC1 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC1";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordatorio CXC 1 para borrar en \"" + sCarp + "\".", "Recordatorio CXC 1", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen de Recordatorio CXC 1 borrada con éxito.", "Recordatorio CXC 1", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelCXC1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar imágen  de recordatorio de CXC 1*/
    private void jBDelCXC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelCXC1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelCXC1KeyPressed

    
    /*Cuando se presiona el botón de ver imágen en grande de recordatrio de CXC2*/
    private void jBVerCXC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerCXC2ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de CXC no existe entonces creala*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de REC2 no existe entonces creala*/
        sCarp                    += "\\REC2";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordatorio de CXC 2 en \"" + sCarp + "\".", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                        
                }
            }
        }
        
    }//GEN-LAST:event_jBVerCXC2ActionPerformed

    
    /*Cuando se preisona una tecla en el botón de ver imágen en grande de recordatorio de CXC 2*/
    private void jBVerCXC2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerCXC2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerCXC2KeyPressed

    
    /*Cuando se presiona el botón de búscar imágen de recordatorio de CXC2*/
    private void jBCXC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCXC2ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorio de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorio de REC2 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC2";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen de Recordatoio de CXC2 en \"" + sCarp + "\".", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                return;               
            }

            /*Mensjea de éxito*/
            JOptionPane.showMessageDialog(null, "Imágen de Recordatorio de CXC 2 guardada con éxito en: " + sCarp + ".", "Recordatorio de CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCXC2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar imágen de recordatorio de CXC 2*/
    private void jBCXC2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCXC2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCXC2KeyPressed

    
    /*Cuando se presiona el botón de borrar imágen de recordatorio de CXC 2*/
    private void jBDelCXC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelCXC2ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recoradtorio de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recoradtorio de REC2 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC2";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordatorio de CXC 2 para borrar en \"" + sCarp + "\".", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen de Recordatorio de CXC 2 borrada con éxito.", "Recordatorio CXC 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelCXC2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar imágen de recordatorio de CXC 2*/
    private void jBDelCXC2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelCXC2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelCXC2KeyPressed

    
    /*Cuando se presiona una tecla en el botón de ver imágen completa de recordatorio de CXC 3*/
    private void jBVerCXC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerCXC3ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorios de CXC no existe entonces creala*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorios de REC3 no existe entonces creala*/
        sCarp                    += "\\REC3";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordamiento CXC 3 en \"" + sCarp + "\".", "Recordamiento CXC 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                }
            }
        }
        
    }//GEN-LAST:event_jBVerCXC3ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen grande de recordatorio de CXC 3*/
    private void jBVerCXC3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerCXC3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerCXC3KeyPressed

    
    /*Cuando se presiona el botón de búscar imágen de recordatorio CXC 3*/
    private void jBCXC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCXC3ActionPerformed

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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces creala la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorio de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de recordatorio de REC3 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC3";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen de Recordatorio de CXC 3 en \"" + sCarp + "\".", "Recordatorio CXC 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                return;
            }

            /*Mensjea de éxito*/
            JOptionPane.showMessageDialog(null, "Imágen de Recordatorio CXC 3guardada con éxito en: " + sCarp + ".", "Recordatorio CXC 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCXC3ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar imágen de recordatorio de CXC 3*/
    private void jBCXC3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCXC3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCXC3KeyPressed

    
    /*Cuando se presiona el botón de borrar imágen de recordatorio de CXC 3*/
    private void jBDelCXC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelCXC3ActionPerformed

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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de CXC no existe entonces crea la carpeta*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de REC3 no existe entonces crea la carpeta*/
        sCarp                    += "\\REC3";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen de Recordatorio de CXC 3 para borrar en \"" + sCarp + "\".", "Recordatorio CXC 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen Recordatorio CXC 3 borrada con éxito.", "Recordatorio CXC 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelCXC3ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar imágen de recordatorio de CXC 3*/
    private void jBDelCXC3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelCXC3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelCXC3KeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar la descripción del producto en las facturas*/
    private void jCModDescripFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModDescripFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModDescripFacKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar precio de producto en la factura*/
    private void jCModPrecFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModPrecFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModPrecFacKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar descripicón de producto en las cotizaciones*/
    private void jCModDescripCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModDescripCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModDescripCotKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar la descripción del precio en las cotizaciones*/
    private void jCModPrecCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModPrecCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModPrecCotKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar descripción de producto en las compras*/
    private void jCModDescripCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModDescripCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModDescripCompKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar precio de producto en compras*/
    private void jCModPrecCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModPrecCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModPrecCompKeyPressed

    
    /*Cuando se presiona una tecla en el check de garantías en las facturas*/
    private void jCGaraFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGaraFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCGaraFKeyPressed

    
    /*Cuando se presiona una tecla en el check de la garantía en la cotización*/
    private void jCGaraCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGaraCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCGaraCKeyPressed

    
    
    /*Cuando se presiona una tecla en el check de guardar pdf de cancelación*/
    private void jCGuaPDFCanPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaPDFCanPtoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaPDFCanPtoKeyPressed

    
    /*Cuando se presiona una tecla en el chck de garantía en punto de venta*/
    private void jCGaraPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGaraPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGaraPKeyPressed

    
    /*Cuando se presiona el botón de todos los productos solicitan máximos y mínimos*/
    private void jBMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMaxMinActionPerformed
        
        /*Muestra la forma de clave de seguridad*/
        ClavMast cl = new ClavMast(this, 0);
        cl.setVisible(true);
        
        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)                    
            return;        
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres que todos los productos \"SI\" solicitan máximos y mínimos?", "Productos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        /*Actualiza el estado para que todos los productos soliciten máximo y mínimo*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "solmaxmin    = 0, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "'";                    
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
        JOptionPane.showMessageDialog(null, "Exito en la modificación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBMaxMinActionPerformed

    
    /*Cuando se presiona una tecla en el botón de todos solicitan máximos y mínimos*/
    private void jBMaxMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMaxMinKeyPressed
               
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMaxMinKeyPressed

    
    /*Cuando se presiona el botón de todos los productos no solicitan máximos y mínimos*/
    private void jBNoMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNoMaxMinActionPerformed
        
        /*Muestra la forma de clave de seguridad*/
        ClavMast cl = new ClavMast(this, 0);
        cl.setVisible(true);
        
        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)                    
            return;        
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres que todos los productos \"NO\" soliciten máximos y mínimos?", "Productos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Actualiza el estado para que todos los productos soliciten máximo y mínimo*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "solmaxmin    = 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "'";                    
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
        JOptionPane.showMessageDialog(null, "Exito en la modificación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBNoMaxMinActionPerformed

    
    /*Cuando se presiona una tecla en el botón de todos no solicitan máximos y mínimos*/
    private void jBNoMaxMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNoMaxMinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNoMaxMinKeyPressed

    
    /*Cuando el mouse entra en el botón de solicitar máximos y mínimos*/
    private void jBMaxMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMaxMinMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMaxMin.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMaxMinMouseEntered

    
    /*Cuando el mouse entra en el botón de no solicitar máximos y mínimos*/
    private void jBNoMaxMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNoMaxMinMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNoMaxMin.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNoMaxMinMouseEntered

    
    /*Cuando el mouse entra en el botón de todos los productos se pueden vender abajo del costo*/
    private void jBTBajCostMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTBajCostMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTBajCost.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTBajCostMouseEntered

    
    /*Cuando el mouse entra en el botón de todos los productos si se pueden vender abajo del costo*/
    private void jBTNoBajCostMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTNoBajCostMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTNoBajCost.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTNoBajCostMouseEntered

    
    /*Cuando el mouse sale del botón de todos los productos se pueden vender abajo del costo*/
    private void jBTBajCostMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTBajCostMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTBajCost.setBackground(colOri);
        
    }//GEN-LAST:event_jBTBajCostMouseExited

    
    /*Cuando el mouse sale del botón de todos los productos no se pueden vender abajo del costo*/
    private void jBTNoBajCostMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTNoBajCostMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTNoBajCost.setBackground(colOri);
        
    }//GEN-LAST:event_jBTNoBajCostMouseExited

    
    /*Cuando el mouse salel del botón de todos los productos si reportean máximos y mínimos*/
    private void jBMaxMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMaxMinMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMaxMin.setBackground(colOri);
        
    }//GEN-LAST:event_jBMaxMinMouseExited

    
    /*Cuando el mouse sale del botón de todos los productos no reportean máximos y mínimos*/
    private void jBNoMaxMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNoMaxMinMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNoMaxMin.setBackground(colOri);
        
    }//GEN-LAST:event_jBNoMaxMinMouseExited

    
    /*Cuando se presiona una tecla en el check de modificar lista de precios en facturas*/
    private void jCModListFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModListFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModListFKeyPressed

    
    /*Cuando se presiona una tecla en el check de modificar lista de precios en cotizaciones*/
    private void jCModListCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModListCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCModListCKeyPressed

    
    /*Cuando se presiona una tecla en el check de devolución parcial de la venta*/
    private void jCDevPVtaPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDevPVtaPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDevPVtaPtoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de devolución en punto de venta*/
    private void jCDevVtaPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDevVtaPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDevVtaPtoKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del almacén de venta*/
    private void jTAlmaVtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaVtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAlmaVta.setSelectionStart(0);jTAlmaVta.setSelectionEnd(jTAlmaVta.getText().length());        
        
    }//GEN-LAST:event_jTAlmaVtaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jTAlmaVtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaVtaFocusLost

        /*Coloca el caret al principio del control*/
        jTAlmaVta.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        if(jTAlmaVta.getText().compareTo("")!=0)
            jTAlmaVta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAlmaVtaFocusLost

    
    /*Cuando se presiona una tecla en el campo del almacén de venta*/
    private void jTAlmaVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaVtaKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presiona el botón del almacén de venta*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBAlmaVta.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAlmaVtaKeyPressed

    
    /*Cuando se tipea una tecla en el campo del almacén*/
    private void jTAlmaVtaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaVtaKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTAlmaVtaKeyTyped

    
    /*Cuando el mouse entra en el botón de almacén de venta*/
    private void jBAlmaVtaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaVtaMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBAlmaVta.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAlmaVtaMouseEntered

    
    /*Cuando el mouse sale del botón de almacén de venta*/
    private void jBAlmaVtaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaVtaMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBAlmaVta.setBackground(colOri);
        
    }//GEN-LAST:event_jBAlmaVtaMouseExited

    
    /*Cuando se pesiona el botón de almacén de venta*/
    private void jBAlmaVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlmaVtaActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAlmaVta.getText(), 42, jTAlmaVta, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBAlmaVtaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de almacén*/
    private void jBAlmaVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAlmaVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAlmaVtaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del almacén de cotización*/
    private void jTAlmaCotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaCotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAlmaCot.setSelectionStart(0);jTAlmaCot.setSelectionEnd(jTAlmaCot.getText().length());        
        
    }//GEN-LAST:event_jTAlmaCotFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del almacén de cotización*/
    private void jTAlmaCotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaCotFocusLost
        
        /*Coloca el caret al principio del control*/
        jTAlmaCot.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        if(jTAlmaCot.getText().compareTo("")!=0)
            jTAlmaCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAlmaCotFocusLost

    
    /*Cuando se presiona una tecla en el campo del almacén de cotización*/
    private void jTAlmaCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaCotKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presiona el botón de almacén de cotización*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBAlmaCot.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAlmaCotKeyPressed

    
    /*Cuando se tipea una tecla en el campo del almacén*/
    private void jTAlmaCotKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaCotKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTAlmaCotKeyTyped

    
    /*Cuando el mouse entra en el botón de almacén de cotización*/
    private void jBAlmaCotMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaCotMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAlmaCot.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAlmaCotMouseEntered

    
    /*Cuando el mouse sale del botón de almacén*/
    private void jBAlmaCotMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaCotMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBAlmaCot.setBackground(colOri);
        
    }//GEN-LAST:event_jBAlmaCotMouseExited

    
    /*Cuando se presiona el botón de búscar almacén de cotización*/
    private void jBAlmaCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlmaCotActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAlmaCot.getText(), 42, jTAlmaCot, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBAlmaCotActionPerformed

    
    /*Cuando se presiona una tecla en el botón de almacén de cotización*/
    private void jBAlmaCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAlmaCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAlmaCotKeyPressed

    
    /*Cuando se presiona una tecla en el chck de mínimo a facturar*/
    private void jCMinFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMinFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMinFacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del mínimo a facturar*/
    private void jTMinFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMinFac.setSelectionStart(0);jTMinFac.setSelectionEnd(jTMinFac.getText().length());        
        
    }//GEN-LAST:event_jTMinFacFocusGained

    
    /*Cuando se presiona una tecla en el campo del mínimo a facturar*/
    private void jTMinFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMinFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jTMinFacKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del mínimoa facturar*/
    private void jTMinFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFacFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMinFac.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        if(jTMinFac.getText().compareTo("")!=0)
            jTMinFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo esta en blanco entonces*/
        if(jTMinFac.getText().trim().compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTMinFac.setText("$0.00");
            return;
        }
        
        /*Dale formato de moneda al minímo a facturad*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(jTMinFac.getText().trim().replace("$", "").replace(",", ""));                
        jTMinFac.setText(n.format(dCant));
                
    }//GEN-LAST:event_jTMinFacFocusLost

    
    /*Cuando el mouse entra en el botón de búscar ruta a calculadora*/
    private void jBCalcMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCalcMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBCalc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCalcMouseEntered

    
    /*Cuando el mouse sale del botón de búscar ruta de calculadora*/
    private void jBCalcMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCalcMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBCalc.setBackground(colOri);
        
    }//GEN-LAST:event_jBCalcMouseExited

    
    /*Cuando el mouse entra en el botón de búscar ruta a cuaderno*/
    private void jBCuadeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCuadeMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCuade.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCuadeMouseEntered

    
    /*Cuando el mouse sale del botón de búscar ruta a cuaderno*/
    private void jBCuadeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCuadeMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBCuade.setBackground(colOri);
        
    }//GEN-LAST:event_jBCuadeMouseExited

    
    /*Cuando el mouse entra en el botón de búscar aplicación favorita*/
    private void jBApFavMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBApFavMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBApFav.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBApFavMouseEntered

    
    /*Cuando el mouse sale del botón de aplicación favorita*/
    private void jBApFavMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBApFavMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBApFav.setBackground(colOri);
        
    }//GEN-LAST:event_jBApFavMouseExited

    
    /*Cuando se pierde el foco del teclado en el combo de las series de las facturas*/
    private void jComSerFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFacFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSerFac.getSelectedItem().toString().compareTo("")!=0)
            jComSerFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFacFocusLost

    
    /*Cuando sucede una acción en el combo de las series de las facturas*/
    private void jComSerFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerFacActionPerformed

        /*Si no hay selección entonces regresa*/
        if(jComSerFac.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'FAC' AND ser = '" + jComSerFac.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSerFac.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);      
             
    }//GEN-LAST:event_jComSerFacActionPerformed

    
    /*Cuando se presiona una tecla en el combo de las series de las facturas*/
    private void jComSerFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerFacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerFacKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de la moneda de factura*/
    private void jComMonFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFacFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComMonFac.getSelectedItem().toString().compareTo("")!=0)
            jComMonFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComMonFacFocusLost

    
    /*Cuando sucede una acción en el combo de moneda de factura*/
    private void jComMonFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonFacActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMonFac.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "mondescrip", "mons", "WHERE mon = '" + jComMonFac.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComMonFac.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);            
        
    }//GEN-LAST:event_jComMonFacActionPerformed

    
    /*Cuando se presiona una tecla en el combo de monedas*/
    private void jComMonFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonFacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonFacKeyPressed

    
    /*Cuando se presiona una tecla en el check de remisión*/
    private void jCRemPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCRemPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCRemPtoKeyPressed

    
    /*Cuando se presiona una tecla en el check de facturar en el punto de venta*/
    private void jCFacPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFacPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFacPtoKeyPressed

    
    /*Cuando se presiona una tecla en el check de iniciar órden de compra*/
    private void jCIniOrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCIniOrdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCIniOrdKeyPressed

    
    //Cuando se presiona una tecla en el check de los usuarios pueden multilogease
    private void jCUsrMultiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCUsrMultiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCUsrMultiKeyPressed

    
    //Cuando se presiona una tecla en el check de movimientos de entradas y salidas solo con existencias
    private void jCESExistMovKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCESExistMovKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCESExistMovKeyPressed

    
    //Cuando se presiona una tecla en el check de traspasos solo con existencias
    private void jCTraspasExisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCTraspasExisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCTraspasExisKeyPressed

    
    //Cuando se presiona una tecla en el check de mostrar mensajes de configuraciones de correos electrónicos
    private void jCMosMsjConfCorrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosMsjConfCorrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCMosMsjConfCorrKeyPressed

    
    //Cuando se presiona una tecla en el check de definir tipo de cambio en el sistema
    private void jCDefTipCamSisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDefTipCamSisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCDefTipCamSisKeyPressed

    
    //Cuando se presiona una tecla en el check de redondear
    private void jCRedonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCRedonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jCRedonKeyPressed

    
    //Cuando se presiona una tecla en el campo de redondeo
    private void jTRedonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRedonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jTRedonKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de redondear
    private void jTRedonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRedonFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRedon.setSelectionStart(0);jTRedon.setSelectionEnd(jTRedon.getText().length());
        
    }//GEN-LAST:event_jTRedonFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de redondear
    private void jTRedonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRedonFocusLost
    
        //Si el campo es cadena vacia entonces que sea 2 y regresa
        if(jTRedon.getText().compareTo("")==0)
        {
            jTRedon.setText("0");
            return;
        }
        
        /*Coloca el caret en la posiciòn 0*/
        jTUsrCump.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRedonFocusLost

    
    //Se comprueba que en el redondeo solo use numeros
    private void jTRedonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRedonKeyTyped
   
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTRedonKeyTyped

    private void jCSerRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCSerRepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCSerRepKeyPressed

    private void jCCatGralPvtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCatGralPvtaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCCatGralPvtaActionPerformed

    private void jCLimCredFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCLimCredFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCLimCredFacActionPerformed

    private void jBPrevUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevUsuarioKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevUsuarioKeyPressed

    private void jBPrevModDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevModDescKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevModDescKeyPressed

    private void jBPrevMonNacionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevMonNacionalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevMonNacionalKeyPressed

    private void jBPrevModPrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrevModPrecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBPrevModPrecActionPerformed

    private void jBPrevModPrecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevModPrecKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevModPrecKeyPressed

    private void jBPrevCotunaSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevCotunaSerieKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevCotunaSerieKeyPressed

    private void jBPrevSerObliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevSerObliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrevSerObliKeyPressed

    private void jCIniOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCIniOrdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCIniOrdActionPerformed

            
   /*Guarda configuración de ventas*/
   void vGuaConfVtas(Connection con)
   {       
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        
        
        /*Actualiza la serie de las facturas*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jComSerF.getSelectedItem().toString().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'serfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza la serie de las facturas fija*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jComSerFac.getSelectedItem().toString().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'serfacfij'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza la moneda de las facturas fija*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jComMonFac.getSelectedItem().toString().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'monfacfij'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el almacén del punto de venta*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTAlma.getText().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'almapto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                
        /*Actualiza el almacén de ventas*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTAlmaVta.getText().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'almavtaf'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el mínimo a facturar*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "nume         = " + jTMinFac.getText().replace("$", "").replace(",", "").trim() + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'minfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza la serie de las remisiones*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jComSerR.getSelectedItem().toString().replace("'", "''") + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'serrem'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza la serie de los tickets*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jComSerT.getSelectedItem().toString().replace("'", "''") + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'sertic'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                
        /*Variable para todas las condiciones posibles*/
        String sVal = "1";
        
        /*Determina si solo se debe vender en moneda nacional o no*/        
        if(jCVendMN.isSelected())
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'otramon'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar la descripción del producto en las facturas o no*/        
        if(jCModDescripFac.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'moddescrip'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se puede o no hacer remisiones en el punto de venta*/        
        if(jCRemPto.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'remptovta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se puede o no hacer facturas en el punto de venta*/        
        if(jCFacPto.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'facptovta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar la descripción del producto en las facturas o no*/        
        if(jCMinFac.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Determina si va a manejar el mínimo a facturar*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'minfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder hacer devoluciones completas en el punto de venta o no*/        
        if(jCDevVtaPto.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'devvtaspto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder hacer devoluciones parciales en el punto de venta o no*/        
        if(jCDevPVtaPto.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'devpvtaspto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe poder midificar la lista de precio en las facturas o no*/        
        if(jCModListF.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'modlistfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe agregar la garantía en la descripción de las facturas*/        
        if(jCGaraF.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'garandescfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
            catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe agregar la garantía en la descripción en el punto de venta*/        
        if(jCGaraP.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'garandescpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar el precio del producto en las facturas o no*/        
        if(jCModPrecFac.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'modprec'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si solo se deben de mostrar solo las ventas del día en el bùscador del punto de venta*/        
        if(jCDiaVtaP.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'hoyvtap'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe hacer corte X automáticamente al inicio de sesión en punto de venta*/        
        if(jCCortXAut.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'autcortx'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
//        /*Determina si esta en modo de prueba o no*/        
//        if(jCFPrue.isSelected())            
//            sVal = "1";
//        else
//            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'modp'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);                        
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si solo se debe insertar dinero en el cajón automáticamente o no*/        
        if(jCInsAutCaj.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "', "
                    + "extr         = '" + jTDinCaj.getText().replace("$", "").replace(",", "") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'insautcaj'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe pedir clave de seguridad cada vez que se facture o no*/        
        if(jCClavFac.isSelected())
            sVal = "1";
        else
            sVal = "0";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "', "
                    + "extr         = '" + Star.sEncrip(jTClavFac.getText().replace("'", "''"))  + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'clavsegfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe pedir clave de seguridad cada vez que se facture o no en el punto de venta*/        
        if(jCClavFacP.isSelected())
            sVal = "1";
        else
            sVal = "0";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "', "
                    + "extr         = '" + Star.sEncrip(jTClavFacP.getText().replace("'", "''"))  + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'clavsegfacp'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si solo se debe vender en moneda nacional o no*/        
        if(jCMsjPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vmsjpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se puede chatear corporativamente desde el punto de venta*/        
        if(jCChatPtoC.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'chatptoc'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe de mostrar el PDF de ticket cancelado en las facturas*/        
        if(jCMosTicCanF.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vercanvtaf'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe de mostrar el PDF de ticket cancelado en el punto de venta*/        
        if(jCMosTicCanPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vercanvta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe de guardar el PDF de ticket cancelado en las facturas*/        
        if(jCGuaPDFCanF.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'guapdfcanf'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe guardar el PDF del ticket de cancelación en el punto de venta*/        
        if(jCGuaPDFCanPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'guapdfcan'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe pedir clave de administrador al cancelar las ventas en el punto de venta*/        
        if(jCAdmCan.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'admcanvtas'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se puede o no modificar la descripción de los productos en el punto de venta*/        
        if(jCDescrip.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'descrippto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben dar de alta empresas o no en el punto de venta*/        
        if(jCEmpsPtoVta.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'empspto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben o no de imprimir tickets en el punto de venta*/        
        if(jCImpTicPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'impticpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Determina si se pueden o no cancelar tickets desde el punto de venta*/        
        if(jCCanTicPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'canticpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se pueden o no cancelar remisiones desde el punto de venta*/        
        if(jCCanRemPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'canrempto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se pueden o no cancelar facturas desde el punto de venta*/        
        if(jCCanFacPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'canfacpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben o no de imprimir facturas en el punto de venta*/        
        if(jCImpFacPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''")+ "' "
                    + "WHERE clasif = 'vtas' AND conf = 'impfacpto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben o no de imprimir remisiones en el punto de venta*/        
        if(jCImpRemPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'imprempto'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben de mostrar las facturas en el bùscador del punto de venta o no*/        
        if(jCVFacPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vfacptovta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben de mostrar los tickets en el bùscador del punto de venta o no*/        
        if(jCVTicPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vticptovta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deben de mostrar las remisiones en el bùscador del punto de venta o no*/        
        if(jCVRemPto.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vremptovta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe guardar automáticamente los cortes X o no*/        
        if(jCCortXA.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'cortxa'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se debe guardar automáticamente los cortes Z o no*/        
        if(jCCortZA.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'cortza'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si solo se deben de mostrar las ventas por usuario*/        
        if(jCVtasXUsr.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vtasxusr'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se puede vender sobre límite de Crédito en punto de venta*/        
        if(jCLimCredPVta.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'slimcredpvta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se puede vender sobre límite de Crédito en facturas*/        
        if(jCLimCredFac.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'slimcredfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }

        /*Determina si se puede vender sobre límite de Crédito en remisiones*/        
        if(jCLimCredRem.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'slimcredrem'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
             
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se podra o no utilizar lista de precio de las empresas en las facturas*/        
        if(jCPListCFac.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'alistpreclifac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrará el logo en las facturas o no*/        
        if(jCMosLogFac.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'logofac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrará el logo en los tickts o no*/        
        if(jCMosLogTik.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'logotik'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrará el logo en las remisiones o no*/        
        if(jCMosLogRem.isSelected())
            sVal = "1";
        else
            sVal = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'logorem'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se podra o no utilizar lista de precio de las empresas en las cotizaciones*/        
        if(jCPLCot.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val              = " + sVal + ", "
                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif     = 'vtas' AND conf = 'alistpreclicot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
	
        /*Determina si se debe o no mostrar el catálogo general en el punto de venta*/        
        if(jCCatGralPvta.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'catgralpvta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se debe o no mostrar el catálogo general en las facturas*/        
        if(jCCatGralFac.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'catgralfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se podra o no utilizar lista de precio de las empresas en el punto de venta*/        
        if(jCPLEPvta.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'alistpreclipvta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrara o no el mensaje de existencia negativa en las facturas*/        
        if(jCMMEFac.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'msjexistnegfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrara o no el mensaje de existencia negativa en el punto de venta*/        
        if(jCMMEPvta.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val              = " + sVal + ", "
                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif     = 'vtas' AND conf = 'msjexistnegpvta'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrara o no el mensaje de existencia negativa en las cotizaciones*/        
        if(jCMMECot.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val              = " + sVal + ", "
                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif     = 'vtas' AND conf = 'msjexistnegpcot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se vendera con o sin existencias en la factura*/        
        if(jCVendSinExistFac.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vendsinexistfac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se vendera con o sin existencias en el punto de venta*/        
        if(jCVSinExistPvta.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'vendsinexistpvta'";                  
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrara la barra lateral del punto de venta o no*/        
        if(jCBarrLatP.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal.replace("'", "''") + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'barlat'";                  
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se mostrarán imágenes en las líneas del punto de venta*/        
        if(jCImgLin.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'imglin'";                  
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
                
        /*Determina si se tiene que iniciar o no el punto de venta al entrar al sistema*/        
        if(jCInitPvta.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'vtas' AND conf = 'initpvta'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
         }                       
                
   }/*Fin de void vGuaConfVtas()*/
   
    private void vGuaConfPrev(Connection con)
   {
       /*Declara variables de la base de datos*/
        Statement   st;                        
        String      sQ              = "";         

        /*Variable para todas las condiciones posibles*/
        String      sVal;

        
        
        //Solo mostrar las del usuario
        sVal     = "0";
        if(jBPrevUsuario.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevporusuario'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
	                                   
        
        //Solo usar moneda nacional
        sVal     = "0";
        if(jBPrevMonNacional.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevmonac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
        
        //Editar la descripcion
        sVal     = "0";
        if(jBPrevModDesc.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevmodesc'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
        
        //Editar el precio
        sVal     = "0";
        if(jBPrevModPrec.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevmodprec'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
        
        //Editar el precio
        sVal     = "0";
        if(jBPrevCotunaSerie.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevunavezser'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
        
        //Editar el precio
        sVal     = "0";
        if(jBPrevSerObli.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'prev' AND conf = 'prevobligarser'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
         }
        
        
   }/*Fin de void vGuaConfInv(Connection con)*/

   
   /*Guarda configuración de cotizaciones*/
   void vGuaConfCots(Connection con)
   {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        /*Variable para todas las condiciones posibles*/
        String      sVal;

        
        
        
        /*Actualiza el almacén de cotizaciones*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTAlmaCot.getText().replace("'", "''").trim() + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'almavtac'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                
        /*Determina si debe cotizar solo en moneda nacional o no*/        
        if(jCCotMN.isSelected())
            sVal     = "0";
        else
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'otramon'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe modificar la lista de precio en las cotizaciones o no*/        
        if(jCModListC.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'modlistcot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe agregar la garantía en la descripción de las cotizaciones*/        
        if(jCGaraC.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'garadesccot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de mostrar solo las cotizaciones por usuario o no*/        
        if(jCCotsXUsr.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val              = " + sVal + ", "
                    + "sucu             ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif     = 'cots' AND conf = 'cotsxusr'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar la descripción del producto en las cotizaciones o no*/        
        if(jCModDescripCot.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'moddescrip'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar el precio del producto en las cotizaciones o no*/        
        if(jCModPrecCot.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'modprec'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se podra o no utilizar lista de precio de las empresas en las cotizaciones*/        
        if(jCPLCot.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'alistpreclicot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
	
        /*Determina si se mostrara o no el mensaje de existencia negativa en las cotizaciones*/        
        if(jCMMECot.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'msjexistnegpcot'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        
        /*Determina si se vendera con o sin existencias en las cotizaciones*/        
        if(jCVSinExistCot.isSelected())
            sVal    = "1";
        else
            sVal    = "0";
        
        /*Actualiza la bandera*/        
        try 
        {            
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'cots' AND conf = 'vendsinexistcot'";                  
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
         }
                
   }/*Fin de void vGuaConfCots(Connection con)*/
       
       
   /*Guarda configuración del sistema*/
   void vGuaConfSist(Connection con)
   {
        //Declara variables de la base de datos
        Statement   st;                        
        String      sQ;         




        /*Actualiza la ruta a la calculadora*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTRutCalc.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'calc'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza todos los usuarios a que ninguno pueda mandar correos de cumleaños ni de agradecmimientos*/        
        try 
        {                        
            sQ = "UPDATE estacs SET "
                    + "mandcump = 0";                                        
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el usuario que enviara corres de cumpleaños*/        
        try 
        {                        
            sQ = "UPDATE estacs SET "
                    + "mandcump         = 1, "
                    + "manddia          = " + jTDias.getText() + " "
                    + "WHERE estac      = '" + jTUsrCump.getText() + "'";                                        
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el usuario que enviara corres de agradecimiento*/        
        try 
        {                        
            sQ = "UPDATE estacs SET "
                    + "mandagra     = 1 "
                    + "WHERE estac  = '"  + jTUsrAgra.getText() + "'";                                        
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza la ruta al cuaderno*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTRutCuade.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'cuader'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }
        /*Actualiza la ruta al cuaderno*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" + jTRutCuade.getText().replace("\\", "\\\\").replace("'", "''") + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'cuader'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }
        /*Actualiza cumple*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" +jTCumple.getText().replace("'", "''") + "', "
                    + "asun         = '" +jTAsun.getText().replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'cumple'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Actualiza agradecimiento*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "extr         = '" +jTCuerAgra.getText().replace("'", "''") + "', "
                    + "asun         = '" +jTAsunAgra.getText().replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'agrad'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Variable para todas las condiciones posibles*/
        String sVal;
        
        //Determina si debe o no de mostrar la configuración de mostrar mensaje de configuración de correos electrónicos
        sVal     = "0";
        if(jCMosMsjConfCorr.isSelected())                    
            sVal     = "1";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'mostmsjusr'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Determina si debe o no poder modificar el tipo de cambio en el sistema o solo en la forma de monedas
        sVal     = "0";
        if(jCDefTipCamSis.isSelected())                    
            sVal     = "1";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'tipcamtod'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Determina si debe o no de redondear en ventas y cotizaciones
        sVal     = "0";
        if(jCRedon.isSelected())                    
            sVal     = "1";
         
        //Si el texto del redondeo es vacio entonces
        String sRed = jTRedon.getText().trim();
        if(sRed.compareTo("")==0)
            sRed = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "nume         = " + sRed.trim() + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'redon'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si se deshabilitara o no el sistema en el periódo de tiempo*/        
        if(jCDesInac.isSelected())
            sVal     = jTMin.getText();
        else
            sVal     = "0";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'dlogin'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
	                    
        //Determina si los usuarios pueden multilogerase
        sVal        = "0";
        if(jCUsrMulti.isSelected())                   
            sVal    = "1";
                
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'sist' AND conf = 'usrmulti'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
         }
        
   }/*Fin de void vGuaConfSist()*/
   
   
   /*Guarda configuración de compras*/
   void vGuaConfComp(Connection con)
   {
       //Declara variables de la base de datos
        Statement   st;                        
        String      sQ;         

        /*Variable para todas las condiciones posibles*/
        String      sVal;

        
        
        /*Determina si solo se deben mostrar las compras a los usuarios o no*/        
        if(jCCompXUsr.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'comps' AND conf = 'compsxusr'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
	                
        /*Determina si debe de poder modificar la descripción del producto en las compras o no*/        
        if(jCModDescripComp.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'comps' AND conf = 'moddescrip'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de iniciar la orden de compra en vez de la compra*/        
        if(jCIniOrd.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'comps' AND conf = 'iniord'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si debe de poder modificar el precio del producto en las compras o no*/        
        if(jCModPrecComp.isSelected())
            sVal     = "1";
        else
            sVal     = "0";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'comps' AND conf = 'modprec'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Determina si solo se debe comprar en moneda nacional o no*/        
        if(jCCompMN.isSelected())
            sVal     = "0";
        else
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'comps' AND conf = 'otramon'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }
                        
   }/*Fin de void vGuaConfComp(Connection con)*/
   
   
   //Guarda configuración de inventarios
   void vGuaConfInv(Connection con)
   {
       //Declara variables de la base de datos
        Statement   st;                        
        String      sQ;         

        /*Variable para todas las condiciones posibles*/
        String      sVal;

        
        
        //Determina si se deben hacer movimientos de entradas y salidas solo con existencia
        sVal     = "0";
        if(jCESExistMov.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'inv' AND conf = 'esexitmov'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
	                                                
        //Determina si se deben hacer traspasos entre almacenes solo con existencia
        sVal     = "0";
        if(jCTraspasExis.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'inv' AND conf = 'traspasexis'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
                
        //Determina si se pueden almacenar series iguales
        sVal     = "0";
        if(jCSerRep.isSelected())                    
            sVal     = "1";
        
        /*Actualiza la bandera*/        
        try 
        {                        
            sQ = "UPDATE confgral SET "
                    + "val          = " + sVal + ", "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE clasif = 'inv' AND conf = 'igualser'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
         }
                
   }/*Fin de void vGuaConfInv(Connection con)*/
   
   
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario y presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();            
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlma;
    private javax.swing.JButton jBAlmaCot;
    private javax.swing.JButton jBAlmaVta;
    private javax.swing.JButton jBApFav;
    private javax.swing.JButton jBBuscCum;
    private javax.swing.JButton jBCXC1;
    private javax.swing.JButton jBCXC2;
    private javax.swing.JButton jBCXC3;
    private javax.swing.JButton jBCalc;
    private javax.swing.JButton jBCargAgra;
    private javax.swing.JButton jBCargCum;
    private javax.swing.JButton jBCuade;
    private javax.swing.JButton jBDelAgra;
    private javax.swing.JButton jBDelCXC1;
    private javax.swing.JButton jBDelCXC2;
    private javax.swing.JButton jBDelCXC3;
    private javax.swing.JButton jBDelCum;
    private javax.swing.JButton jBDelHisCor;
    private javax.swing.JButton jBDelHisEstac;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBMaxMin;
    private javax.swing.JButton jBNoMaxMin;
    private javax.swing.JCheckBox jBPrevCotunaSerie;
    private javax.swing.JCheckBox jBPrevModDesc;
    private javax.swing.JCheckBox jBPrevModPrec;
    private javax.swing.JCheckBox jBPrevMonNacional;
    private javax.swing.JCheckBox jBPrevSerObli;
    private javax.swing.JCheckBox jBPrevUsuario;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTBajCost;
    private javax.swing.JButton jBTNoBajCost;
    private javax.swing.JButton jBUsrAgra;
    private javax.swing.JButton jBUsrCump;
    private javax.swing.JButton jBVerAgra;
    private javax.swing.JButton jBVerCXC1;
    private javax.swing.JButton jBVerCXC2;
    private javax.swing.JButton jBVerCXC3;
    private javax.swing.JCheckBox jCAdmCan;
    private javax.swing.JCheckBox jCBarrLatP;
    private javax.swing.JCheckBox jCCanFacPto;
    private javax.swing.JCheckBox jCCanRemPto;
    private javax.swing.JCheckBox jCCanTicPto;
    private javax.swing.JCheckBox jCCatGralFac;
    private javax.swing.JCheckBox jCCatGralPvta;
    private javax.swing.JCheckBox jCChatPtoC;
    private javax.swing.JCheckBox jCClavFac;
    private javax.swing.JCheckBox jCClavFacP;
    private javax.swing.JCheckBox jCCompMN;
    private javax.swing.JCheckBox jCCompXUsr;
    private javax.swing.JCheckBox jCCortXA;
    private javax.swing.JCheckBox jCCortXAut;
    private javax.swing.JCheckBox jCCortZA;
    private javax.swing.JCheckBox jCCotMN;
    private javax.swing.JCheckBox jCCotsXUsr;
    private javax.swing.JCheckBox jCDefTipCamSis;
    private javax.swing.JCheckBox jCDesInac;
    private javax.swing.JCheckBox jCDescrip;
    private javax.swing.JCheckBox jCDevPVtaPto;
    private javax.swing.JCheckBox jCDevVtaPto;
    private javax.swing.JCheckBox jCDiaVtaP;
    private javax.swing.JCheckBox jCESExistMov;
    private javax.swing.JCheckBox jCEmpsPtoVta;
    private javax.swing.JCheckBox jCFacPto;
    private javax.swing.JCheckBox jCGaraC;
    private javax.swing.JCheckBox jCGaraF;
    private javax.swing.JCheckBox jCGaraP;
    private javax.swing.JCheckBox jCGuaPDFCanF;
    private javax.swing.JCheckBox jCGuaPDFCanPto;
    private javax.swing.JCheckBox jCImgLin;
    private javax.swing.JCheckBox jCImpFacPto;
    private javax.swing.JCheckBox jCImpRemPto;
    private javax.swing.JCheckBox jCImpTicPto;
    private javax.swing.JCheckBox jCIniOrd;
    private javax.swing.JCheckBox jCInitPvta;
    private javax.swing.JCheckBox jCInsAutCaj;
    private javax.swing.JCheckBox jCLimCredFac;
    private javax.swing.JCheckBox jCLimCredPVta;
    private javax.swing.JCheckBox jCLimCredRem;
    private javax.swing.JCheckBox jCMMECot;
    private javax.swing.JCheckBox jCMMEFac;
    private javax.swing.JCheckBox jCMMEPvta;
    private javax.swing.JCheckBox jCMinFac;
    private javax.swing.JCheckBox jCModDescripComp;
    private javax.swing.JCheckBox jCModDescripCot;
    private javax.swing.JCheckBox jCModDescripFac;
    private javax.swing.JCheckBox jCModListC;
    private javax.swing.JCheckBox jCModListF;
    private javax.swing.JCheckBox jCModPrecComp;
    private javax.swing.JCheckBox jCModPrecCot;
    private javax.swing.JCheckBox jCModPrecFac;
    private javax.swing.JCheckBox jCMosLogFac;
    private javax.swing.JCheckBox jCMosLogRem;
    private javax.swing.JCheckBox jCMosLogTik;
    private javax.swing.JCheckBox jCMosMsjConfCorr;
    private javax.swing.JCheckBox jCMosTicCanF;
    private javax.swing.JCheckBox jCMosTicCanPto;
    private javax.swing.JCheckBox jCMsjPto;
    private javax.swing.JCheckBox jCPLCot;
    private javax.swing.JCheckBox jCPLEPvta;
    private javax.swing.JCheckBox jCPListCFac;
    private javax.swing.JCheckBox jCRedon;
    private javax.swing.JCheckBox jCRemPto;
    private javax.swing.JCheckBox jCSerRep;
    private javax.swing.JCheckBox jCTraspasExis;
    private javax.swing.JCheckBox jCUsrMulti;
    private javax.swing.JCheckBox jCVFacPto;
    private javax.swing.JCheckBox jCVRemPto;
    private javax.swing.JCheckBox jCVSinExistCot;
    private javax.swing.JCheckBox jCVSinExistPvta;
    private javax.swing.JCheckBox jCVTicPto;
    private javax.swing.JCheckBox jCVendMN;
    private javax.swing.JCheckBox jCVendSinExistFac;
    private javax.swing.JCheckBox jCVtasXUsr;
    private javax.swing.JComboBox jComMonFac;
    private javax.swing.JComboBox jComSerF;
    private javax.swing.JComboBox jComSerFac;
    private javax.swing.JComboBox jComSerR;
    private javax.swing.JComboBox jComSerT;
    private javax.swing.JLabel jLAyu;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPChat;
    private javax.swing.JPanel jPComp;
    private javax.swing.JPanel jPCot;
    private javax.swing.JPanel jPFac;
    private javax.swing.JPanel jPInven;
    private javax.swing.JPanel jPPtoVta;
    private javax.swing.JPanel jPRem;
    private javax.swing.JPanel jPSis;
    private javax.swing.JPanel jPTic;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTAlmaCot;
    private javax.swing.JTextField jTAlmaVta;
    private javax.swing.JTextField jTAsun;
    private javax.swing.JTextField jTAsunAgra;
    private javax.swing.JTabbedPane jTBP;
    private javax.swing.JTextField jTClavFac;
    private javax.swing.JTextField jTClavFacP;
    private javax.swing.JTextField jTCuerAgra;
    private javax.swing.JTextField jTCumple;
    private javax.swing.JTextField jTDias;
    private javax.swing.JTextField jTDinCaj;
    private javax.swing.JTextField jTMin;
    private javax.swing.JTextField jTMinFac;
    private javax.swing.JTextField jTRedon;
    private javax.swing.JTextField jTRutAp;
    private javax.swing.JTextField jTRutCalc;
    private javax.swing.JTextField jTRutCuade;
    private javax.swing.JTextField jTUsrAgra;
    private javax.swing.JTextField jTUsrCump;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
