//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.Cursor;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;




/*Clase para cobrar*/
public class Cobro extends javax.swing.JFrame 
{
    //Contiene la referencia a esta misma forma
    private Cobro           cobThis;
    
    /*Bandera de errores de la forma*/
    private boolean         bErr                = false;
    
    /*Declara variables de instancia*/    
    private String          sTotG               = "";
    private String          sSubTotG            = "";
    private String          sImpueG             = "";
    private String          sCodEmpGlo;
    private String          sSer;
    private String          sFDoc;
    private String          sNomEmp;    
    private JTable          jTabPart;
    private JTextField      jTTotGlob;
    private JLabel          jLImgGlo;
    private JTextField      jTProdGlob;
    private JTextField      jTEmp;
    private JTextField      jTNom;    
    private String          sNomLocGlob         = "";
    private String          sCallLocGlob        = "";
    private String          sTelLocGlob         = "";
    private String          sColLocGlob         = "";
    private String          sCPLocGlob          = "";
    private String          sCiuLocGlob         = "";
    private String          sMailLocGlob        = "";
    private String          sNoExtLocGlob       = "";
    private String          sNoIntLocGlob       = "";
    private String          sEstLocGlob         = "";
    private String          sPaiLocGlob         = "";
    private String          sRFCLocGlob         = "";
    private String          sCallGlob           = "";      
    private String          sColGlob            = "";
    private String          sTelGlob            = "";        
    private String          sCPGlob             = "";
    private String          sNoIntGlob          = "";
    private String          sNoExtGlob          = "";
    private String          sRFCGlob            = "";
    private String          sCo1Glob            = "";
    private String          sCiuGlob            = "";
    private String          sPaiGlob            = "";
    private String          sEstGlob            = "";
    private String          sCatGral            = "";
    private String          sRutTicGlob         = "";
    private String          sRutLogGlob;
    private String          sRutCedGlob;
    private String          sRutFacGlob;    
    private String          sRutRemGlob;    
    private String          sImprFacGlob        = "";
    private String          sImpAntGlob;
    private String          s52Glob             = "";    

    /*Bandera para saber si se puede remisionar o no*/
    private boolean         bRem                = true;
    
    /*Bandera para saber si se puede facturar o no*/
    private boolean         bFac                = true;
    
    /*Contiene la dirección el control de subtotal, impuesto y descuento del otro formulario*/
    private JTextField      jTImp;
    private JTextField      jTSub;
    private JTextField      jTDesc;
    private JTextField      jTTotCost;

    /*Contiene la dirección para resetear las cantidades de piezas*/
    private JTextField      jTCant;
    
    /*Contiene la moneda global*/
    private String          sMonG;
    
    /*Contiene la forma de pago*/
    private String          sFormPag;
    
    //Grupo de los radio buttons a elegir en el tipo de documento
    private ButtonGroup     bG      = new ButtonGroup();
    
    //Grupo de los radio buttons a elegir en si se paga o no el documento
    private ButtonGroup     bGPag   = new ButtonGroup();
    
    //Contiene la dirección al otro formulario
    private PtoVtaTou       frmPto;
    
    

    
    /*Constructor sin argumentos*/
    public Cobro(String sTot, String sCodE, String sSe, String sFDo, String sNombEmp, JTable jTabPar, JTextField jTTotG, JLabel jLImgG, JTextField jTProdG, String sDes, String sSubTot, String sImp, JTextField jTEm, JTextField jTNomre, String sCatGra, JTextField jTIm, JTextField jTSu, JTextField jTCan, JTextField jTDes, JTextField jTTotCos, String sMon, String sFormPa, PtoVtaTou frmPt) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        //Obtiene la dirección del otro formulario
        frmPto  = frmPt;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCob);
        
        /*Obtiene la forma de pago del otro formulario*/
        sFormPag        = sFormPa;
        
        /*Obtiene la moneda del otro formulario*/
        sMonG   = sMon;
        
        /*Pon el foco del teclado en el campo de edición del primer pago*/
        jTEfeCant.grabFocus();                
        
        //Crea el grupo de los radio buttons de tipo de documento
        bG.add(jRTic);
        bG.add(jRRem);
        bG.add(jRFac);
        bG.add(jRNot);
        
        //Crea el grupo de los radio buttons de pagado o no pagado
        bGPag.add(jRPagad);
        bGPag.add(jRNoPag);
                
        /*Obtiene la dirección del control del total del costo*/
        jTTotCost   = jTTotCos;
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la vtana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la vtana con El usuario, la fecha y hora*/                
        this.setTitle("Cobrar, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en el JTextArea que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Recibe argumentos del otro formulario*/
        sTotG           = sTot;
        sCatGral        = sCatGra;
        sSubTotG        = sSubTot;
        sCodEmpGlo      = sCodE;
        sFDoc           = sFDo;
        sNomEmp         = sNombEmp;
        jTImp           = jTIm;
        jTSub           = jTSu;
        jTabPart        = jTabPar;
        jTTotGlob       = jTTotG;
        jTCant          = jTCan;
        jTDesc          = jTDes;
        jLImgGlo        = jLImgG;
        jTProdGlob      = jTProdG;    
        sImpueG         = sImp;        
        jTEmp           = jTEm;
        jTNom           = jTNomre;
        sSer            = sSe;        
                
        /*Coloca el total en su campo*/
        jTTot.setText           (sTotG);
        
        /*Coloca la sugerencia de pago en el campo de pago 1*/
        jTEfeCant.setText       (sTotG);
        
        /*Coloca en el campo del saldo 0 pesos inicialmente*/
        jTSald.setText          ("$0.00");
        
        /*Coloca en el campo del cambio 0 pesos inicialmente*/
        jTCamb.setText          ("$0.00");
                             
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene la impresora predeterminada actual*/
        PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
        sImpAntGlob                 = service.getName();

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el nombre de la impresora que tiene configurada El usuario actual y si es de cort o no*/
        try
        {
            sQ = "SELECT imptic, 52m, cort, impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {            
                sImprFacGlob        = rs.getString("impfac");                
                s52Glob             = rs.getString("52m");                
            }
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }   

        /*Comprueba si se pueden hacer remisiones en el punto de venta*/
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'remptovta'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no se puede remisionar entonces deshabilita el control*/
                jRRem.setEnabled    (false);
                jRRem.setFocusable  (false);
                
                /*Coloca la bandera global*/
                this.bRem    = false;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }   
        
        /*Comprueba si se pueden hacer facturas en el punto de venta*/
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'facptovta'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no se puede facturar entonces deshabilita el control*/
                jRFac.setEnabled    (false);
                jRFac.setFocusable  (false);
                
                /*Coloca la bandera global*/
                this.bFac    = false;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }   
        
        /*Obtiene todos los datos de la empresa local*/
        try
        {                  
            sQ = "SELECT corr, noext, noint, nom, calle, tel, col, cp, ciu, estad, pai, rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sNomLocGlob             = rs.getString("nom");                                    
                sCallLocGlob            = rs.getString("calle");                                    
                sTelLocGlob             = rs.getString("tel");                                    
                sColLocGlob             = rs.getString("col");                                    
                sCPLocGlob              = rs.getString("cp");                                    
                sCiuLocGlob             = rs.getString("ciu");                                    
                sMailLocGlob            = rs.getString("corr");                                    
                sNoExtLocGlob           = rs.getString("noext");
                sNoIntLocGlob           = rs.getString("noint");
                sEstLocGlob             = rs.getString("estad");                                    
                sPaiLocGlob             = rs.getString("pai");                                    
                sRFCLocGlob             = rs.getString("rfc");                                                   
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene todos los datos de la empresa en base a su código*/
        try
        {                  
            sQ = "SELECT * FROM emps WHERE codemp = '" + sCodEmpGlo.replace(sSer, "") + "' AND ser = '" + sSer + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {                
                sCallGlob               = rs.getString("calle");                                    
                sColGlob                = rs.getString("col");                                    
                sTelGlob                = rs.getString("lada") + rs.getString("tel");                                                    
                sPaiGlob                = rs.getString("pai");                                                    
                sCPGlob                 = rs.getString("cp");                                    
                sNoIntGlob              = rs.getString("noint");                                    
                sNoExtGlob              = rs.getString("noext");                                    
                sRFCGlob                = rs.getString("rfc");                                    
                sCiuGlob                = rs.getString("ciu");   
                sEstGlob                = rs.getString("estad");   
                sCo1Glob                = rs.getString("co1");                                                                   
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
            
        /*Obtiene la ruta completa hacia la carpeta de las remisiones*/
        sRutRemGlob             = sCarp + "\\Remisiones";  
        
        /*Si no existe la ruta hacia la carpeta de las remisiones entonces creala*/
        if(!new File(sRutRemGlob).exists())
            new File(sRutRemGlob).mkdirs();
                
        /*Si no existe la ruta hacia la la empresa entonces creala*/
        sRutRemGlob             += "\\" + Login.sCodEmpBD;
        if(!new File(sRutRemGlob).exists())
            new File(sRutRemGlob).mkdirs();
        
        /*Obtiene la ruta completa hacia la carpeta del logo de la empresa*/
        sRutLogGlob             = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";
        
        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLogGlob).exists())
            sRutLogGlob         = getClass().getResource(Star.sIconDef).toString();
        
        /*Obtiene la ruta completa hacia la carpeta de tickets*/
        sRutTicGlob             = sCarp + "\\Tickets";                        
        
        /*Si no existe la ruta hacia la carpeta de los tickets entonces creala*/
        if(!new File(sRutTicGlob).exists())
            new File(sRutTicGlob).mkdirs();
        
        /*Si no existe la ruta hacia la la empresa entonces creala*/
        sRutTicGlob             += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTicGlob).exists())
            new File(sRutTicGlob).mkdirs();
        
        /*Obtiene la ruta completa hacia la carpeta de facturas*/
        sRutFacGlob             = sCarp + "\\Facturas";  
        
        /*Si no existe la ruta hacia la carpeta de las facturas entonces creala*/
        if(!new File(sRutFacGlob).exists())
            new File(sRutFacGlob).mkdirs();
                                
        /*Si no existe la ruta hacia la empresa entonces creala*/
        sRutFacGlob             += "\\" + Login.sCodEmpBD;
        if(!new File(sRutFacGlob).exists())
            new File(sRutFacGlob).mkdirs();

        /*Obtiene la ruta completa hacia la carpeta de la cédula de la empresa*/
        sRutCedGlob             = sCarp + "\\imgs\\Cedula\\" + Login.sCodEmpBD + "\\Cedula.jpg";

        /*Si no existe un logo para la cédlula de la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutCedGlob).exists())
            sRutCedGlob         = getClass().getResource(Star.sIconDef).toString();
                
    }/*Fin de public Cobro() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBCob = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTTot = new javax.swing.JTextField();
        jTEfe = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTEfeDescrip = new javax.swing.JTextField();
        jTEfeCant = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTCamb = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTSald = new javax.swing.JTextField();
        jTDebDescrip = new javax.swing.JTextField();
        jTDeb = new javax.swing.JTextField();
        jTDebCant = new javax.swing.JTextField();
        jTTar = new javax.swing.JTextField();
        jTTarDescrip = new javax.swing.JTextField();
        jTTarCredCant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLAyu = new javax.swing.JLabel();
        jRTic = new javax.swing.JRadioButton();
        jRRem = new javax.swing.JRadioButton();
        jRFac = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jRNot = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jRNoPag = new javax.swing.JRadioButton();
        jRPagad = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
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
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
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

        jBCob.setBackground(new java.awt.Color(255, 255, 255));
        jBCob.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCob.setForeground(new java.awt.Color(0, 102, 0));
        jBCob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cobra.png"))); // NOI18N
        jBCob.setText("COBRAR");
        jBCob.setToolTipText("Cobrar Venta (F10)");
        jBCob.setNextFocusableComponent(jBSal);
        jBCob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCobMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCobMouseExited(evt);
            }
        });
        jBCob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCobActionPerformed(evt);
            }
        });
        jBCob.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCobKeyPressed(evt);
            }
        });
        jP1.add(jBCob, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 120, 50));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTEfeCant);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, 120, 50));

        jTTot.setEditable(false);
        jTTot.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTTot.setForeground(new java.awt.Color(0, 0, 255));
        jTTot.setNextFocusableComponent(jBCob);
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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 150, 30));

        jTEfe.setEditable(false);
        jTEfe.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTEfe.setText("EFE");
        jTEfe.setFocusable(false);
        jTEfe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEfeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEfeFocusLost(evt);
            }
        });
        jTEfe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEfeKeyPressed(evt);
            }
        });
        jP1.add(jTEfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 110, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("PAGO 1:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Observaciones:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 260, -1));

        jTEfeDescrip.setEditable(false);
        jTEfeDescrip.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTEfeDescrip.setText("EFECTIVO");
        jTEfeDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEfeDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEfeDescripFocusLost(evt);
            }
        });
        jTEfeDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEfeDescripKeyPressed(evt);
            }
        });
        jP1.add(jTEfeDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 180, 30));

        jTEfeCant.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTEfeCant.setForeground(new java.awt.Color(0, 0, 255));
        jTEfeCant.setText("$0.00");
        jTEfeCant.setNextFocusableComponent(jTDebCant);
        jTEfeCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEfeCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEfeCantFocusLost(evt);
            }
        });
        jTEfeCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEfeCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEfeCantKeyTyped(evt);
            }
        });
        jP1.add(jTEfeCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 150, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("*Saldo:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 150, -1));

        jTCamb.setEditable(false);
        jTCamb.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTCamb.setForeground(new java.awt.Color(0, 0, 255));
        jTCamb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCambFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCambFocusLost(evt);
            }
        });
        jTCamb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCambKeyPressed(evt);
            }
        });
        jP1.add(jTCamb, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 150, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Total:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 150, -1));

        jTSald.setEditable(false);
        jTSald.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTSald.setForeground(new java.awt.Color(0, 0, 255));
        jTSald.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSaldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSaldFocusLost(evt);
            }
        });
        jTSald.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSaldKeyPressed(evt);
            }
        });
        jP1.add(jTSald, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 150, 30));

        jTDebDescrip.setEditable(false);
        jTDebDescrip.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTDebDescrip.setText("DEBITO");
        jTDebDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDebDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDebDescripFocusLost(evt);
            }
        });
        jTDebDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDebDescripKeyPressed(evt);
            }
        });
        jP1.add(jTDebDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 180, 30));

        jTDeb.setEditable(false);
        jTDeb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTDeb.setText("DEB");
        jTDeb.setFocusable(false);
        jTDeb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDebFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDebFocusLost(evt);
            }
        });
        jTDeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDebKeyPressed(evt);
            }
        });
        jP1.add(jTDeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 110, 30));

        jTDebCant.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTDebCant.setForeground(new java.awt.Color(0, 0, 255));
        jTDebCant.setText("$0.00");
        jTDebCant.setNextFocusableComponent(jTTarCredCant);
        jTDebCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDebCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDebCantFocusLost(evt);
            }
        });
        jTDebCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDebCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDebCantKeyTyped(evt);
            }
        });
        jP1.add(jTDebCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 150, 30));

        jTTar.setEditable(false);
        jTTar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTTar.setText("TAR");
        jTTar.setFocusable(false);
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
        });
        jP1.add(jTTar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 110, 30));

        jTTarDescrip.setEditable(false);
        jTTarDescrip.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTTarDescrip.setText("TARJETA CREDITO");
        jTTarDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarDescripFocusLost(evt);
            }
        });
        jTTarDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarDescripKeyPressed(evt);
            }
        });
        jP1.add(jTTarDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 180, 30));

        jTTarCredCant.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTTarCredCant.setForeground(new java.awt.Color(0, 0, 255));
        jTTarCredCant.setText("$0.00");
        jTTarCredCant.setNextFocusableComponent(jTAObserv);
        jTTarCredCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarCredCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarCredCantFocusLost(evt);
            }
        });
        jTTarCredCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarCredCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTarCredCantKeyTyped(evt);
            }
        });
        jP1.add(jTTarCredCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 150, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Cambio:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 110, -1));

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setNextFocusableComponent(jRTic);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAObservFocusGained(evt);
            }
        });
        jTAObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAObservKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAObserv);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 220, 60));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 340, 210, 20));

        jRTic.setBackground(new java.awt.Color(255, 255, 255));
        jRTic.setSelected(true);
        jRTic.setText("Ticket");
        jRTic.setNextFocusableComponent(jRRem);
        jRTic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRTicKeyPressed(evt);
            }
        });
        jP1.add(jRTic, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 80, -1));

        jRRem.setBackground(new java.awt.Color(255, 255, 255));
        jRRem.setText("Remisión");
        jRRem.setNextFocusableComponent(jRFac);
        jRRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRRemActionPerformed(evt);
            }
        });
        jRRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRRemKeyPressed(evt);
            }
        });
        jP1.add(jRRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 90, -1));

        jRFac.setBackground(new java.awt.Color(255, 255, 255));
        jRFac.setText("Factura");
        jRFac.setNextFocusableComponent(jRNot);
        jRFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRFacKeyPressed(evt);
            }
        });
        jP1.add(jRFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 90, -1));

        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setText("Altl+ N");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 80, 20));

        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setText("Altl+ T");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 80, 20));

        jLabel33.setForeground(new java.awt.Color(0, 0, 255));
        jLabel33.setText("Altl+ R");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 80, 20));

        jRNot.setBackground(new java.awt.Color(255, 255, 255));
        jRNot.setText("Nota");
        jRNot.setToolTipText("Es un ticket cancelado (solo como comprobante)");
        jRNot.setNextFocusableComponent(jBCob);
        jRNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRNotKeyPressed(evt);
            }
        });
        jP1.add(jRNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 90, -1));

        jLabel34.setForeground(new java.awt.Color(0, 0, 255));
        jLabel34.setText("Altl+ F");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 80, 20));

        jRNoPag.setBackground(new java.awt.Color(255, 255, 255));
        jRNoPag.setText("No pagada");
        jRNoPag.setNextFocusableComponent(jBCob);
        jRNoPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRNoPagActionPerformed(evt);
            }
        });
        jRNoPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRNoPagKeyPressed(evt);
            }
        });
        jP1.add(jRNoPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jRPagad.setBackground(new java.awt.Color(255, 255, 255));
        jRPagad.setSelected(true);
        jRPagad.setText("Pagada");
        jRPagad.setNextFocusableComponent(jBCob);
        jRPagad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRPagadActionPerformed(evt);
            }
        });
        jRPagad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPagadKeyPressed(evt);
            }
        });
        jP1.add(jRPagad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    
    /*Cuando se presiona el botón de cobrar*/
    private void jBCobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCobActionPerformed

        /*Lee el saldo y quitale al saldo el formato de moneda*/
        String sSa      = jTSald.getText().replace("$", "").replace(",", "");        
                        
        /*Si el saldo es mayor a 0 entonces*/
        if(Double.parseDouble(sSa) > 0)
        {
            /*Dale formato de moneda al saldo*/            
            double dCant    = Double.parseDouble(sSa);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSa             = n.format(dCant);

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Hay saldo pendiente: " + sSa + ".", "Cobrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el pago de efectivo y regresa*/
            jTEfeCant.grabFocus();                        
            return;
        }
                
        /*Lee el cambio*/
        String sCam                 = jTCamb.getText().replace("$", "").replace(",", "");
        
        /*Quitar el formato de moneda al total*/
        sTotG                       = sTotG.replace("$", "").replace(",", "");                
        
        /*Quitar el formato de moneda al subtotal*/
        sSubTotG                    = sSubTotG.replace("$", "").replace(",", "");               
                     
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                
        
        /*Comprueba la configuración para vender sobre límite de crédito en el punto de venta*/
        boolean bSiLim = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE conf = 'slimcredpvta' AND clasif = 'vtas'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si no esta habilitado para que se pueda vender sobre límite de crédito de la empresa entonces coloca la bandera*/
                if(rs.getString("val").compareTo("1")==0)                                   
                    bSiLim = true;                        
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Declara variables locales
        String sMinFac  = "";
        boolean bSiMin  = false;
        
        /*Comprueba si esta habilitado usar el mínimo a facturar o no*/                
        try
        {
            sQ = "SELECT nume, val FROM confgral WHERE conf = 'minfac' AND clasif = 'vtas'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                
                /*Obtiene el mínimo a facturar*/
                sMinFac = rs.getString("nume");
                
                /*Si tiene habilitado usar el mínimo para facturar entonces coloca la bandera*/                                
                if(rs.getString("val").compareTo("1")==0)
                    bSiMin  = true;
            }                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }            
        
        /*Si va a ser factura entonces*/
        if(jRFac.isSelected())
        {
            //Obtiene si el usuario tiene correo asociado
            int iRes    = Star.iUsrConfCorr(con, Login.sUsrG);
                    
            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            
            //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
            int iMosMsj = Star.iMostMsjCorrUsr(con);
            
            //Si hubo error entonces regresa
            if(iMosMsj==-1)
                return;                        
                    
            //Si no tiene correo asociado entonces
            if(iRes==0 && iMosMsj==1)
            {
                //Que esta forma pase atras
                cobThis.toBack();
                
                //Mensajea y que la forma pase adelante
                JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + ". No se mandará correo electrónico con la factura", "Correo electronico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                        
                cobThis.toFront();
            }                
            
            /*Descativa siempre en el tope de la ventana para que se puedan ver los mensajes*/
            this.setAlwaysOnTop(false);
            
            /*Valida los datos necesarios para la factura*/
            if(Star.iValFac(con)==-1)
                return;
            
            /*Activa siempre en el tope de la ventana*/
            this.setAlwaysOnTop(true);
            
            /*Si tiene habilitado usar el mínimo para facturar entonces*/
            if(bSiMin)
            {            
                /*Si la cantidad a facturar es menor que el mínimo a facturar entonces*/
                if(Double.parseDouble(jTTot.getText().trim().replace("$", "").replace(",", ""))<Double.parseDouble(sMinFac))
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Dale formato de moneda a la cantidad mínima a facturar*/                
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                    double dCant    = Double.parseDouble(sMinFac);                
                    sMinFac           = n.format(dCant);

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La cantidad a facturar: " + jTTot.getText().trim() + " es menor a la cantidad mínima a facturar: " + sMinFac, "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                    return;
                }
            }

            /*Comprueba si se tiene que pedir clave para facturar*/
            try
            {
                sQ = "SELECT val, extr FROM confgral WHERE clasif = 'vtas' AND conf = 'clavsegfacp'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {
                    /*Si tiene que pedir clave entonces*/
                    if(rs.getString("val").compareTo("1")==0)                                   
                    {
                        /*Manda llamar la forma para pedir la clave de facturacion*/
                        ClavFac f = new ClavFac(this, Star.sDecryp(rs.getString("extr")));
                        f.setVisible(true);

                        /*Si la clave esta mal o no sigio entonces regresa*/
                        if(!ClavFac.bSi)
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

        }/*Fin de if(jCFac.isSelected())*/
        
        //Si va a ser ticket o nota entonces
        if(jRTic.isSelected() || jRNot.isSelected())
            vTic(con, sCam);                                                            
        /*Else if será factura entonces*/
        else if(jRFac.isSelected())                        
            vFac(con, bSiLim, sCam, "fac");                                  
        /*Else if será remisión entonces*/
        else if(jRRem.isSelected())                           
            vFac(con, bSiLim, sCam, "rem");                                  
        
        /*Si no hubo error entonces*/
        if(!bErr)
        {
            //Cierra la base de datos
            Star.iCierrBas(con);                                        
        }            
        
        /*Si hubo error entonces*/
        if(bErr)
        {
            /*Resetea la bandera de error y regresa*/
            bErr    = false;
            return;
        }
        
        /*Borra toda la tabla de partidas entonces borra las filas*/
        DefaultTableModel mod=(DefaultTableModel)jTabPart.getModel();
        for(int x = jTabPart.getRowCount() - 1; x >= 0 ; x--)
            mod.removeRow(x);

        /*Resetea los totales*/
        jTTotGlob.setText       ("$0.00");        
        jTImp.setText           ("$0.00");
        jTSub.setText           ("$0.00");
        jTDesc.setText          ("$0.00");
        
        //Limpia los datos del vehículo frmPto
        frmPto.sRecib            = "";
        frmPto.sMarc             = "";
        frmPto.sMod              = "";
        frmPto.sColo             = "";
        frmPto.sPlacs            = "";
        frmPto.sNom              = "";
        frmPto.sTarCirc          = "";
        frmPto.sNumLic           = "";
        frmPto.sTel              = "";
        frmPto.sDirPart          = "";
        frmPto.sDirOfi           = "";
        frmPto.sTelOfi           = "";
        
        /*Resetea las cantidades de piezas*/
        jTCant.setText          ("0 pzas");
        
        /*Que no sea visible la imágen*/
        jLImgGlo.setVisible     (false);

        /*Borra lo que tiene en el campo del código del prodcuto en el campo del otro formulario*/
        jTProdGlob.setText      (""); 
        
        /*Coloca al cliente mostrador nuevamente*/
        jTEmp.setText           (Star.sCliMostG);
        jTNom.setText           (Star.sNomCliMostG);        

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma y seguimos procesando mas abajo*/
        this.dispose();                
        
    }//GEN-LAST:event_jBCobActionPerformed
              

    /*Finción para procesar la factura*/
    private void vFac(Connection con, boolean bSiLim, String sCam, String sTip)
    {        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;          
        String      sQ; 
                        
        //Declara variables locales
        boolean bSi     = false;
        String sFVenc   = "";
        String sMetPag  = "";
        String sCta     = "";
        String sLimit   = "0";
        
        /*Comprueba si la empresa tiene crédito y obtén otros datos*/               
        try
        {
            sQ = "SELECT diacred AS dias, now() + INTERVAL diacred DAY AS vencimien, metpag, cta, limtcred FROM emps WHERE codemp = '" + sCodEmpGlo.replace(sSer, "") + "' AND ser = '" + sSer + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si los días son cadena vacia entonces que sea 0*/                    
                String sDia = rs.getString("dias");
                if(sDia.compareTo("")==0)
                    sDia    = "0";

                /*Coloca la bandera para saber que si tiene crédito*/
                if(Integer.parseInt(sDia) > 0)
                    bSi     = true;

                /*Obtiene la fecha de vencimiento*/
                sFVenc      = rs.getString("vencimien");

                /*Si el método de pago es cadena vacia entonces que sea no identificable*/
                if(rs.getString("metpag").compareTo("")==0)
                    sMetPag = "No identificado";
                else
                    sMetPag = rs.getString("metpag");

                /*Si la cuenta es cadena vacia entonces que sea 0000*/
                if(rs.getString("cta").compareTo("")==0)
                    sCta    = "0000";
                else
                    sCta    = rs.getString("cta");

                /*Obtiene el límite de crédito de la empresa*/
                sLimit  = rs.getString("limtcred");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        //Comprueba si el cliente no esta bloqueado
        int iRes    = Star.iGetBloqCredCliProv(null, "cli", sCodEmpGlo);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si tiene bloqueado el crédito entonces
        if(iRes==1)
            bSi = false;
        
        /*Inicialmente la factura no es de crédito*/
        String sCred    = "now()";
        
        /*Si tiene días de crédito, es factura y no va a ser de contado entonces*/
        if(bSi && sTip.compareTo("fac")==0)
        {
            /*Coloca la variable con la fecha de vencimiento*/
            sCred       = "'" + sFVenc + "'";

            /*Obtiene el saldo que tiene pendiente de pagar el cliente*/
            String sPendPag = "0";
            try
            {
                sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxc WHERE empre = '" + sCodEmpGlo.trim() + "'";            
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene el resultado*/
                    sPendPag      = rs.getString("pendpag"); 

                    /*Si es cadena vacia que sea 0*/
                    if(sPendPag.compareTo("")==0)
                        sPendPag  = "0";
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Obtiene el saldo que ya pago de todas las pendientes*/
            String sTotPag = "0";
            try
            {
                sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS tot FROM cxc WHERE empre = '" + sCodEmpGlo.trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene el resultado*/
                    sTotPag      = rs.getString("tot");                                                   

                    /*Si es cadena vacia que sea 0*/
                    if(sTotPag.compareTo("")==0)
                        sTotPag  = "0";
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Obtiene el total que queda pendiente ya con los pagos aplicados*/
            String sTot         = Double.toString(Double.parseDouble(sPendPag) - Double.parseDouble(sTotPag));                                

            /*Obtiene el saldo que tiene disponible la empresa*/
            String sSaldDispo   = Double.toString(Double.parseDouble(sLimit.replace("$", "").replace(",", "")) - Double.parseDouble(sTot));

            /*Si no esta permitido vender sobre el límite de crédito de la empresa entonces*/
            if(!bSiLim)
            {
                /*Si el total es mayor al saldo disponible entonces*/
                if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(sSaldDispo))
                {          
                    /*Si el saldo disponible no es negativo entonces*/
                    if(Double.parseDouble(sSaldDispo)>=0)
                    {
                        /*Dale formato de moneda al saldo disponible*/                                    
                        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        double dCant    = Double.parseDouble(sSaldDispo);                
                        sSaldDispo      = n.format(dCant);
                    }   
                    /*Else, solo agregale al símbolo de mon al principio*/
                    else
                        sSaldDispo      = "$" + sSaldDispo;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El total de la factura: " + jTTot.getText() + " es mayor que el saldo actual: " + sSaldDispo + " de la empresa. Se necesita permiso de admnistrador para completar la factura.", "Factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Pide clave de administrador*/            
                    ClavMast cl = new ClavMast(this, 1);
                    cl.setVisible(true);

                    /*Si la clave que ingreso el usuario fue incorrecta entonces*/
                    if(!ClavMast.bSi)
                    {
                        //Cierra la base de datos y regresa
                        Star.iCierrBas(con);                            
                        return;
                    }

                }/*Fin de if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")))*/                                            

            }/*Fin de if(!bSi)*/                                                                   

        }/*Fin de if(bSi)*/

        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;

        /*Contiene el tipo de documnto que será*/
        String sTipDoc  = "";
        
        /*Determina donde buscara la serie*/
        String sSerFR   = "";
        if(sTip.compareTo("fac")==0)
        {                        
            /*Obtiene la serie que debe tomar el punto de venta ya sea para las facturas*/                
            try
            {                  
                sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'serfac'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())            
                    sSerFR                 = rs.getString("extr");
                /*Else si no hay datos entonces*/
                else
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No existe serie para las facturas en el punto de venta. Se tiene que configurar la serie para el punto de venta.", "Serie de Tickets", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca la bandera de error y regresa*/
                    bErr                    = true;
                    return;
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
         
            /*El tipo de documento será factura*/
            sTipDoc     = "FAC";
            
        }/*Fin de if(sTip.compareTo("fac")==0)*/                    
        /*Else if es remisión entonces*/
        else if(sTip.compareTo("rem")==0)
        {
            /*Obtiene la serie que debe tomar el punto de venta ya sea para las remisiones*/                
            try
            {                  
                sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'serrem'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())            
                    sSerFR                 = rs.getString("extr");
                /*Else si no hay datos entonces*/
                else
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No existe serie para las remisiones en el punto de venta. Se tiene que configurar la serie para el punto de venta.", "Serie de Tickets", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca la bandera de error y regresa*/
                    bErr                    = true;
                    return;
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
            
            /*El tipo de documento será remisión*/
            sTipDoc     = "REM";
            
        }/*Fin de else if(sTip.compareTo("rem")==0)*/                    
        
        /*Obtiene el consecutivo de las facturas o remisiones*/            
        String sConsFac             = "";        
        try
        {                  
            sQ = "SELECT ser, consec FROM consecs WHERE ser = '" + sSerFR + "' AND tip = '" + sTipDoc + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                           
                sConsFac                 = rs.getString("consec");                                                                                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }        

        /*Actualiza el consecutivo de la factura o remisión en uno mas*/
        try 
        {                
            sQ = "UPDATE consecs SET "
                    + "consec           = consec + 1, "
                    + "sucu             ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE ser        = '" + sSerFR.replace("'", "''") + "' AND tip = '" + sTipDoc + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                        
        
        /*Si tiene que insertar en CXC y es factura entonces*/
        if(bSi && sTip.compareTo("fac")==0)
        {
            //Inserta el CXC del abono
            if(Star.iInsCXCP(con, "cxc", sConsFac, sSerFR, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, sTotG, "0", "'" + sFVenc + "'", "'" + sFDoc + "'", "ABON FAC", "", "", "", "","")==-1)
                return;
            if(jRPagad.isSelected())
            {
            //Inserta cxc en la base de datos            
            if(Star.iInsCXCP(con, "cxc", sConsFac, sSerFR, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, "0", sTotG, "'" + sFVenc + "'", "'" + sFDoc + "'", "FAC", "", "0", "", "","")==-1)
            return;
            }
        }
        //Else no será a crédito entonces
        else
        {
            //Inserta el CXC de la deuda            
//            if(Star.iInsCXCP(con, "cxc", sConsFac, sSerFR, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, sTotG,"0", "'" + sFVenc + "'", "'" + sFDoc + "'", sTipDoc, "", "", "", "","")==-1)
//                return;                                
//            
            //Inserta el CXC del abono
            if(Star.iInsCXCP(con, "cxc", sConsFac, sSerFR, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, sTotG, "0", "'" + sFVenc + "'", "'" + sFDoc + "'", sTipDoc, "", "", "", "","")==-1)
                return;
            if(jRPagad.isSelected())
            {
            if(Star.iInsCXCP(con, "cxc", sConsFac, sSerFR, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, "0", sTotG, "'" + sFVenc + "'", "'" + sFDoc + "'", "EFE", "", "", "", "","")==-1)
                return;
            }
        }

        /*Declara variables*/
        String sConsCort                 = "1";

        /*Obtiene el consecutivo del cort Z*/
        bSi  = false;
        try
        {                  
            sQ = "SELECT numcort AS numcort FROM cortszx WHERE cort = 'Z' AND regis = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe ya un cort Z entonces obtiene los resultados*/                
            if(rs.next())
            {        
                sConsCort           = rs.getString("numcort");     
                bSi                 = true; 
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Si no existe un corte entonces insertalo*/
        if(!bSi)
        {
            /*Obtiene el máximo del corte*/                
            try
            {                  
                sQ = "SELECT numcort + 1 as numcort FROM cortszx WHERE cort = 'Z'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces si existe ya un cort Z entonces obtiene el resultado*/                
                if(rs.next())
                    sConsCort            = rs.getString("numcort");                             
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Inserta el cort Z*/
            try 
            {                    
                sQ = "INSERT INTO cortszx (numcort,                                 cort, regis, totvtas, totingr, totingr, totcaj, impue, estac,                       sucu,                       nocaj) " + 
                                    "VALUES(" + sConsCort.replace("'", "''") + ",   'Z',   0,          0,           0,             0,            0,         0,     '" + Login.sUsrG + "','" +    Star.sSucu + "','" +  Star.sNoCaj + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }                               

        }/*Fin de if(!bExisteYaC)*/                                   

        /*Determina si ya esta facturado o no*/
        String sFactu   = "0";
        if(sTipDoc.compareTo("FAC")==0)
            sFactu      = "1";
        
        /*Obtiene el tipo de cambio de la moneda*/
        String sTipCam  = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + sMonG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el tipo de cambio*/
            if(rs.next())
                sTipCam = rs.getString("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                   

       //Inserta en la base de datos la nueva venta
       if(Star.iInsVtas(con, sSerFR, sConsFac, sCodEmpGlo, sSer, sSubTotG.replace("$", "").replace(",", ""), sImpueG.replace("$", "").replace(",", ""), sTotG.replace("$", "").replace(",", ""), "now()", "'" + sFDoc + "'", sCred, "'CO'", "0", "", sTipDoc, sConsCort, sMetPag, sCta, jTAObserv.getText().replace("'", "''"), "0", jTDesc.getText().replace("$", "").replace(",", ""), "1", sFactu, jTTotCost.getText(), Login.sUsrG, sMonG, sTipCam, sFormPag, frmPto.sRecib, frmPto.sMarc, frmPto.sMod, frmPto.sColo, frmPto.sPlacs, frmPto.sNom, frmPto.sTarCirc, frmPto.sNumLic, frmPto.sTel, frmPto.sDirPart, frmPto.sDirOfi, frmPto.sTelOfi, "N", "", "0", "0", "0", "0","")==-1)
            return;
       
       /*Obtiene algunos datos de la última venta insertada*/        
        String sVta     = "";        
        try
        {
            sQ = "SELECT vta, falt FROM vtas ORDER BY vta DESC LIMIT 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sVta        = rs.getString("vta");                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                    

        /*Obtiene algunos datos del cliente*/        
        String sCtaPred     = "";        
        try
        {
            sQ = "SELECT ctapred FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCodEmpGlo + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sCtaPred        = rs.getString("ctapred");                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                    
        
        /*Lee las cuentas de los controles*/
        String sEfe    = jTEfeCant.getText().replace("$", "").replace(",", "");
        String sDeb    = jTDebCant.getText().replace("$", "").replace(",", "");
        String sTar    = jTTarCredCant.getText().replace("$", "").replace(",", "");                                    

        /*Inserta el flujo en la base de datos del efectivo si es mayor a 0*/
        if(Double.parseDouble(sEfe)>0)
        {
            try 
            {
                sQ = "INSERT INTO fluj (    concep, ing_eg,             tipdoc,                                 impo,     mon,      modd,         vta,              norefer,                                    ncortz,                                     estac,                                          sucu,                                           nocaj) " + 
                                 "VALUES(  'EFE',    'I',       '" +    sTipDoc.replace("'", "''") + "'," +     sEfe + ", '',       'CXC', " +    sVta + "," +      sConsFac.replace("'", "''") + ", " +        sConsCort.replace("'", "''") + ", '" +      Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }

        }/*Fin de if(Double.parseDouble(sEfe)>0)*/

        /*Inserta el flujo en la base de datos de débito si es mayor a 0*/
        if(Double.parseDouble(sDeb)>0)
        {
            try 
            {                    
                sQ = "INSERT INTO fluj (    concep, ing_eg,                 tipdoc,                                 impo,     mon,      modd,       vta,            norefer,                                    ncortz,                                     estac,                                          sucu,                                               nocaj) " + 
                                  "VALUES(  'DEB',    'I',          '" +    sTipDoc.replace("'", "''") + "'," +     sDeb + ", '',       'CXC', " +  sVta + "," +    sConsFac.replace("'", "''") + ", " +        sConsCort.replace("'", "''") + ", '" +      Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Inserta el flujo en la base de datos de tarjeta de crédito si es mayor a 0*/
        if(Double.parseDouble(sTar)>0)
        {
            try 
            {                    
                sQ = "INSERT INTO fluj(concep,   ing_eg,            tipdoc,                                 impo,     mon,      modd,         vta,              norefer,                                    ncortz,                                     estac,                                          sucu,                                           nocaj) " + 
                             "VALUES(  'TAR',    'I',       '" +    sTipDoc.replace("'", "''") + "'," +     sTar + ", '',       'CXC', " +    sVta + "," +      sConsFac.replace("'", "''") + ", " +        sConsCort.replace("'", "''") + ", '" +      Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Si el cambio es mayor a 0 entonces*/
        if(Double.parseDouble(sCam) > 0)
        {
            /*Inserta el fluj en la base de datos*/
            try 
            {
                sQ = "INSERT INTO fluj (    concep,     ing_eg,         tipdoc,                                 impo,     mon,      modd,       vta,            norefer,                                ncortz,     estac,                                          sucu,                                           nocaj) " + 
                                  "VALUES(  'CAM',      'E',       '" + sTipDoc.replace("'", "''") + "'," +     sCam + ", '',       'CXP', " +  sVta + "," +    sConsFac.replace("'", "''") + ",        0, '" +     Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Si el cambio es mayor a 0 entonces*/
        if(Double.parseDouble(sCam) > 0)
        {
            /*Inserta el flujo en la base de datos*/
            try 
            {                    
                sQ = "INSERT INTO fluj (    concep,     ing_eg,         tipdoc,                                 impo,           mon,    modd,       vta,            norefer,                                ncortz,     estac,                                          sucu,                                           nocaj) " + 
                                 "VALUES(  'CAM',       'E',       '" + sTipDoc.replace("'", "''") + "'," +     sCam + ",       MN,     'CXP', " +  sVta + "," +    sConsFac.replace("'", "''") + ",        0, '" +     Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Recorre la tabla de partidas*/        
        for(int x = 0; x < jTabPart.getRowCount(); x++)
        {
            /*Obtiene el descuento*/
            String sDesc               = jTabPart.getValueAt(x, 4).toString().trim();

            /*Si el descuento es cadena vacia entonces colocar 0*/
            if(sDesc.compareTo("")==0)
                sDesc           = "0";

            //Declara variables locales
            String sImpue   = "";
            String sUni     = "";
            
            /*Obtiene el valor del impuesto y la unidad*/            
            try
            {
                sQ = "SELECT impues.IMPUEVAL, prods.UNID FROM prods LEFT JOIN impues ON impues.CODIMPUE = prods.IMPUE WHERE prods.PROD = '" + jTabPart.getValueAt(x, 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                   
                    sImpue          = rs.getString ("impueval");                    
                    sUni            = rs.getString ("unid");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }  

            /*Obtiene el costo dependiendo el método de costeo de la empresa*/
            String sCostMe  = Star.sGetCost(con, jTabPart.getValueAt(x, 1).toString().trim(), jTabPart.getValueAt(x, 0).toString().trim());
                        
            /*Si hubo error entonces regresa*/
            if(sCostMe==null)
                return;
            
            /*Si tiene talla o color entonces procesa esto en una función*/
            if(jTabPart.getValueAt(x, 10).toString().trim().compareTo("")!=0 || jTabPart.getValueAt(x, 11).toString().trim().compareTo("")!=0)                           
                Star.vTallCol(con, jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 10).toString().trim(), jTabPart.getValueAt(x, 11).toString().trim(), jTabPart.getValueAt(x, 1).toString().trim(), "-");            
            
            /*Si tiene pedimento la fila entonces*/
            if(jTabPart.getValueAt(x, 15).toString().trim().compareTo("")!=0)
            {
                /*Actualiza la cantidad en las partidas de la compra para el lote y factura*/
                try 
                {            
                    sQ = "UPDATE partcomprs SET "
                            + "cantlotpend      = cantlotpend - " + jTabPart.getValueAt(x, 0).toString().trim() 
                            + " WHERE id_id     = " + jTabPart.getValueAt(x, 15).toString().trim();                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }                                      
                
                /*Actualiza todas las partidas de lote y pedimento que estan ya igual en cantidades*/
                try 
                {            
                    sQ = "UPDATE partcomprs SET "
                            + "aplic                = 1 "
                            + "WHERE cantlotpend    = 0";                            
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                 }        
                
            }/*Fin de if(jTab.getValueAt(x, 14).toString().compareTo("")!=0)*/                        
                        
            /*Obtiene si tiene fecha de caducidad correctamente*/
            String sFCadu               = "now()";
            if(jTabPart.getValueAt(x, 14).toString().trim().compareTo("")!=0)                           
                sFCadu                  = "'" + jTabPart.getValueAt(x, 14).toString().trim() + "'";            
            
            /*Si tiene serie entonces procesa esta parte en la función*/
            if(jTabPart.getValueAt(x, 19).toString().trim().compareTo("")!=0)
                Star.vSerPro(con, jTabPart.getValueAt(x, 1).toString().trim(), jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 19).toString().trim(), jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 20).toString().trim(), "-");            
                        
            /*Si el producto es un kit entonces*/                 
            String sKit         = "0";
            if(jTabPart.getValueAt(x, 16).toString().trim().compareTo("Si")==0)           
                sKit            = "1";                        
                        
            /*Si el imuesto es cadena vacia o nulo que sea 0 entonces*/
            if(sImpue==null||sImpue.compareTo("")==0)
                sImpue          = "0";
            
            /*Inserta en la base de datos la partida de la venta*/
            try 
            {                    
                sQ = "INSERT INTO partvta(         vta,             prod,                                                                          cant,                                                      unid,                                           descrip,                                                                                                               pre,                                                                                      descu,              impue,     mon,     impo,                                                                              falt,       eskit,              kitmae,        tipdoc,   list,   alma,                                                                       ueps,    peps,   tall,                                                                        colo,                                                                        cantentre,                                              serprod,                                                comenser,                                               tipcam,           garan,                                                    cost,        costprom,     idlotped, lot,                                                    pedimen,                                               fcadu,           codimpue) " + 
                                     "VALUES(" +   sVta + ", '"  +  jTabPart.getValueAt(x, 1).toString().trim().replace("'", "''") + "'," +        jTabPart.getValueAt(x, 0).toString().trim() + ",'" +       sUni.replace("'", "''").trim() + "','" +        jTabPart.getValueAt(x, 3).toString().trim().replace("'", "''") + " " + frmPto.sRecib + " " + frmPto.sPlacs + "'," +    jTabPart.getValueAt(x, 5).toString().trim().replace("$", "").replace(",", "") + "," +     sDesc + "," +       sImpue + ",''," +   jTabPart.getValueAt(x, 6).toString().trim().replace("$", "").replace(",", "") + ", now()," +   sKit + ",           0,             'FAC',    1, '" + jTabPart.getValueAt(x, 9).toString().trim().replace("'", "''").trim() + "', 0,       0, '" + jTabPart.getValueAt(x, 10).toString().trim().replace("'", "''") + "', '" +   jTabPart.getValueAt(x, 11).toString().trim().replace("'", "''") + "', " +    jTabPart.getValueAt(x, 0).toString().trim() + ", '" +   jTabPart.getValueAt(x, 19).toString().trim() + "', '" + jTabPart.getValueAt(x, 20).toString().trim() + "', " +  sTipCam + ", '" + jTabPart.getValueAt(x, 21).toString().trim() + "', " +    sCostMe + ", 0,            0, '" +   jTabPart.getValueAt(x, 12).toString().trim() + "', '" + jTabPart.getValueAt(x, 13).toString().trim() + "', " + sFCadu + ", '" + jTabPart.getValueAt(x, 24).toString().trim() + "')";                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }
            
            /*Si el producto es un kit entonces*/                             
            if(sKit.compareTo("1")==0)
            {                
                /*Obtiene el último ID insertado para referenciar los kits*/
                String sId  = "";
                try
                {
                    sQ = "SELECT MAX(id_id) AS id FROM partvta WHERE vta = " + sVta + " ORDER BY id DESC LIMIT 1";	
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sId = rs.getString("id");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }

                /*Coloca que si es kit y agrega las partidas con precio 0*/
                sKit            = "1";
                if(Star.iInsFacComp(con, jTabPart.getValueAt(x, 1).toString().trim(), sVta.trim(), jTabPart.getValueAt(x, 9).toString().trim(), "FAC", sSerFR.replace("'", "''").trim() + sConsFac.replace("'", "''").trim(), sCodEmpGlo.replace("'", "''").trim(), "1", sSerFR.replace("'", "''").trim(), sTipCam, sId, jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 2).toString().trim(), jTabPart.getValueAt(x, 24).toString().trim())==-1)
                    return;                                
                
            }/*Fin de if(sKit.compareTo("1")==0)*/
            
            /*Comprueba si el producto es un servicio*/       
            boolean bServ   = false;
            try
            {
                sQ = "SELECT servi FROM prods WHERE prod = '" + jTabPart.getValueAt(x, 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())          
                {
                    /*Si es un servicio entonces coloa la bandera*/
                    if(rs.getString("servi").compareTo("1")==0)                                                    
                        bServ   = true;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }         
            
            /*Si no es un kit y si no es un servicio entonces*/
            if(sKit.compareTo("0")==0 && !bServ)
            {
                /*Obtiene la cantidad correcta dependiendo de su unidad*/
                String sCant    = Star.sCantUnid(jTabPart.getValueAt(x, 2).toString().replace("'", "''").trim(), jTabPart.getValueAt(x, 0).toString().trim());
                        
                /*Si tiene lote o pedimento la fila entonces procesa esto en una función*/
                if(jTabPart.getValueAt(x, 12).toString().compareTo("")!=0 || jTabPart.getValueAt(x, 13).toString().compareTo("")!=0)            
                {                    
                    if(Star.sLotPed(con, jTabPart.getValueAt(x, 1).toString().trim(), sCant, jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 12).toString().trim(), jTabPart.getValueAt(x, 13).toString().trim(), jTabPart.getValueAt(x, 14).toString().trim(), "-")==null)
                        return;
                }

                /*Realiza la afectación correspondiente al almacén*/
                if(Star.iAfecExisProd(con, jTabPart.getValueAt(x, 1).toString().replace("'", "''").trim(), jTabPart.getValueAt(x, 9).toString().replace("'", "''").trim(), sCant, "-")==-1)
                    return;
                
                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, jTabPart.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                    return;
                
                /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                if(!Star.vRegMoniInv(con, jTabPart.getValueAt(x, 1).toString().trim().replace("'", "''"), sCant, jTabPart.getValueAt(x, 3).toString().trim().replace("'", "''"), jTabPart.getValueAt(x, 9).toString().trim().replace("'", "''"), Login.sUsrG , sConsFac.replace("'", "''".trim()), sTipDoc.replace("'", "''"), sUni.replace("'", "''"), sSerFR.replace("'", "''".trim()), sCodEmpGlo.replace("'", "''"), "1"))                                
                    return;      
                
            }/*Fin de if(sKit.compareTo("0")==0)*/                
                
        }/*Fin de for(int x = 0; x < jTabPart.getRowCount(); x++)*/

        /*Contiene el total del costo*/
        String sTotCost    = "";
        
        /*Obtiene la sumatoria del total del costo*/
        try
        {
            sQ = "SELECT IFNULL(SUM(cost * cant),0) AS cost FROM partvta WHERE vta = " + sVta;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sTotCost    = rs.getString("cost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el encabezado de la venta con total del costo de la venta*/
        try 
        {                
            sQ = "UPDATE vtas SET "                    
                    + "totcost      = " + sTotCost + " "                    
                    + "WHERE vta    = " + sVta;                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Comprueba si el sistema esta en modo prueba*/
        boolean bSiT    = true;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'modp'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la bandera correcta*/
                if(rs.getString("val").compareTo("1")==0)                                   
                    bSiT  = false;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        /*Obtiene el símbolo de la moneda*/
        String sSimb    = "";
        try
        {
            sQ = "SELECT simb FROM mons WHERE mon = '" + sMonG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSimb   = rs.getString("simb");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }            

        /*Dale formato de moneda otra vez al total*/                
        double dCant    = Double.parseDouble(sTotG);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTotG           = n.format(dCant);

        /*Dale formato de moneda otra vez al subtotal*/	
        dCant           = Double.parseDouble(sSubTotG);                        
        sSubTotG       = n.format(dCant);

        /*Dale formato de moneda al impuesto*/	
        dCant           = Double.parseDouble(sImpueG.replace("$", "").replace(",", ""));                        
        sImpueG       = n.format(dCant);

        /*Deja la forma de pago correcta*/        
        if(sFormPag.compareTo("CR")==0)
            sFormPag    = "Crédito";
        else
            sFormPag    = "Contado";
        
        /*Deja la fecha correcta*/
        sFDoc   = sFDoc.replace(" ", "T").replace("/", "-");
            
        /*Declara variables final para el thread*/
        final String sVtaFin        = sVta;
        final String sConsFacFin    = sConsFac;
        final String sSerFRFin      = sSerFR;        
        final String sPaiFin        = sPaiGlob;
        final String sFDocFin       = sFDoc;
        final String sTelFin        = sTelGlob;        
        final String sCallFin       = sCallGlob;                
        final String sNomFin        = sNomEmp;
        final String sCPFin         = sCPGlob;
        final String sNoExtFi       = sNoExtGlob;
        final String sNoIntFi       = sNoIntGlob;
        final String sCiuFi         = sCiuGlob;
        final String sColFin        = sColGlob;
        final String sEstaFi        = sEstGlob;
        final String sRFCFi         = sRFCGlob;
        final String sCo1Fi         = sCo1Glob;        
        final String sImpFi         = sImpueG;
        final String sTotFi         = sTotG;        
        final String sTotLetFi      = Star.sObLet(sTotG.replace(",", "").replace("$", ""), sMonG, sSimb, true);        
        final String sSubTotFi      = sSubTotG;
        final String sMetPagFi      = sMetPag;
        final String sCtaPredFi     = sCtaPred;
        final String sTipCamFi      = sTipCam;
        final String sMonFi         = sMonG;
        final String sCtaFi         = sCta;
        final String sFormPagFi     = sFormPag;
        final String sTotDescFi     = jTDesc.getText().replace("$", "").replace(",", "");        
        final String sCatGralFin    = sCatGral;
                
        /*Si se tiene que timbrar entonces*/
        if(bSiT)
        {
            /*Si es una factura entonces genera el timbrado y el PDF*/
            if(sTip.compareTo("fac")==0)
            {
                /*Thread para quitar carga y todo se haga mas rápido*/
                (new Thread()
                {
                    @Override
                    public void run()
                    { 
                        System.out.println("llego");
                        /*Función para hacer el timbrado y generar PDF y XML*/
                        Star.vGenTim("fac", "", sConsFacFin, sVtaFin, sCatGralFin, sFDocFin, sNomFin, sPaiFin, sTelFin, sCallFin, sColFin, sCPFin, sNoExtFi, sNoIntFi, sCiuFi, sEstaFi, sRFCFi, sCo1Fi, sTotLetFi, sSubTotFi, sImpFi, sTotFi, sSerFRFin, sMetPagFi, sCtaFi, sFormPagFi, getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), false, true, sCo1Fi, null, null, true, false, false, true, -1, null, false, sMonFi, sTotDescFi, sFormPagFi, "ingreso", sTipCamFi, sCtaPredFi);                                            
                    }
                    
                }).start();

                /*Regresa aquí*/
                return;
            }            
        }                    
        
        /*Thread para manejar la remisión*/
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
                ResultSet   rs;                
                String      sQ;
                
                /*Comprueba si tiene que imprimir o no el logo en la remisión*/
                boolean bSi = false;
                try
                {
                    sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'logofac'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene la fecha de creación*/
                        if(rs.getString("val").compareTo("1")==0)                                   
                            bSi = true;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }                   
                
                /*Genera el reporte de la venta*/
                JasperPrint  pri;
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("VTA",                 sVtaFin);
                    para.put("CATGRAL",             sCatGral);
                    para.put("CONSFAC",             sConsFacFin);
                    para.put("SER",                 sSerFRFin);
                    para.put("FDOC",                sFDoc);
                    para.put("NOMEMP",              sNomEmp);
                    para.put("TEL",                 sTelGlob);
                    para.put("CALL",                sCallGlob);
                    para.put("COL",                 sColGlob);
                    para.put("CP",                  sCPGlob);
                    para.put("NOEXT",               sNoExtGlob);
                    para.put("NOINT",               sNoIntGlob);
                    para.put("CIU",                 sCiuGlob);
                    para.put("ESTAD",               sEstGlob);
                    para.put("RFC",                 sRFCGlob);
                    para.put("CORR",                sCo1Glob);
                    para.put("IMPLET",              sTotLetFi);
                    para.put("SUBTOT",              sSubTotG);
                    para.put("IMPUE",               sImpueG);
                    para.put("MON",                 sMonFi);
                    para.put("TOT",                 sTotG);
                    para.put("EMPLOC",              sNomLocGlob);
                    para.put("TELLOC",              sTelLocGlob);
                    para.put("COLLOC",              sColLocGlob);
                    para.put("CALLLOC",             sCallLocGlob);
                    para.put("CPLOC",               sCPLocGlob);
                    para.put("CIULOC",              sCiuLocGlob);
                    para.put("ESTADLOC",            sEstLocGlob);
                    para.put("PAISLOC",             sPaiLocGlob);
                    para.put("RFCLOC",              sRFCLocGlob);
                    para.put("LOG",                 sRutLogGlob);                                                    
                    para.put("LOGE",                Star.class.getResource(Star.sIconDef).toString());

                    /*Si va a ser remisión entonces*/
                    JasperReport jas = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptRem.jrxml"));
                    
                    /*Si tiene que ser el que no tiene logo entonces pasa el parámetro*/                            
                    if(bSi)
                        para.put("LOG",          sRutLogGlob);
                    
                    /*Llena el reporte*/
                    pri           = JasperFillManager.fillReport(jas, (Map)para, con);                    
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                
                    return;
                }

                /*Crea la ruta para la remisión*/
                sRutRemGlob     = sRutRemGlob + "\\" + sSerFRFin + "-" +sConsFacFin + ".pdf";
                
                /*Cambia la impresora predeterminada */
                Star.vCambImp(sImprFacGlob);
                
                /*Manda la impresión a la impresora configurada para la estación de facturas*/
                try
                {
                    JasperPrintManager.printReport(pri,false);                 
                }
                catch(JRException expnJASR)
                {    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                
                    return;
                }
                
                /*Cambia la impresora predeterminada que estaba anteriormente*/
                Star.vCambImp(sImpAntGlob);

                /*La ruta de la remisión*/
                String sRu  = sRutRemGlob;                                    
                
                /*Exportalo a PDF en el directorio*/
                try
                {
                    JasperExportManager.exportReportToPdfFile(pri, sRu);        
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                    
                }
                                
            }/*Fin de public void run()*/
            
        }).start();
        
    }/*Fin de private void vFac(Connection con)*/
            
    
    /*Función para procesar el ticket*/
    private void vTic(Connection con, String sCam)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;          
        String      sQ; 
     
        
        
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Obtiene la serie que debe tomar el punto de venta para los tickets*/        
        String sSerTic;
        try
        {                  
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'sertic'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sSerTic                   = rs.getString("extr");
            /*Else si no hay datos entonces*/
            else
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe serie para los tickets en el punto de venta. Se tiene que configurar la serie para el punto de venta.", "Serie de Tickets", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca la bandera de error y regresa*/
                bErr                    = true;
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene el consecutivo de los tickets*/
        String sConTic    = "";        
        try
        {                  
            sQ = "SELECT consec FROM consecs WHERE ser = '" + sSerTic + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sConTic                   = rs.getString("consec");                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Declara variables*/
        String sConsCort                 = "1";

        /*Obtiene el consecutivo del cort Z*/
        boolean bSi                     = false;
        try
        {                  
            sQ = "SELECT numcort AS numcort FROM cortszx WHERE cort = 'Z' AND regis = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe ya un cort Z*/                
            if(rs.next())
            {
                /*Obtiene el consecutivo del fluj y pon la bandera*/
                sConsCort               = rs.getString("numcort");     
                bSi                     = true; 
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Si no existe un corte entonces insertalo*/
        if(!bSi)
        {
            /*Obtiene el máximo del cort*/                
            try
            {                  
                sQ = "SELECT numcort + 1 AS numcort FROM cortszx WHERE cort = 'Z'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/                
                if(rs.next())
                    sConsCort            = rs.getString("numcort");                             
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Inserta el cort Z*/
            try 
            {                    
                sQ = "INSERT INTO cortszx (numcort,                             cort, regis,      totvtas,        totingr,        totingr,      totcaj,    impue,   estac,                                          sucu,                                           nocaj) " + 
                               "VALUES(" + sConsCort.replace("'", "''") + ",    'Z',   0,          0,             0,              0,            0,         0,     '" + Login.sUsrG.replace("'", "''") + "','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }                               

        }/*Fin de if(!bExisteYaC)*/

        /*Actualiza el consecutivo del ticket en uno mas*/
        try 
        {                
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE ser    = 'TIK'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }                               

        /*Contiene el método de pago y la cuenta*/
        String sMetPag  = "";
        String sCta     = "";
        
        /*Obtiene algunos datos de la empresa*/               
        try
        {
            sQ = "SELECT metpag, cta FROM emps WHERE codemp = '" + sCodEmpGlo.replace(sSer, "") + "' AND ser = '" + sSer + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {                                        
                /*Si el método de pago es cadena vacia entonces que sea no identificable*/
                if(rs.getString("metpag").compareTo("")==0)
                    sMetPag = "No Identificado";
                else
                    sMetPag = rs.getString("metpag");

                /*Si la cuenta es cadena vacia entonces que sea 0000*/
                if(rs.getString("cta").compareTo("")==0)
                    sCta    = "0000";
                else
                    sCta    = rs.getString("cta");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Obtiene el tipo de cambio de la moneda*/
        String sTipCam  = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + sMonG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el tipo de cambio*/
            if(rs.next())
                sTipCam = rs.getString("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                   
        
        //Determina el estado de la venta dependiendo de si es nota o ticket
        String sEstadVta    = "CO";
        if(jRNot.isSelected())
            sEstadVta       = "CA";
            
        //Inserta en la base de datos la nueva venta
       if(Star.iInsVtas(con, sSerTic, sConTic, sCodEmpGlo, sSer, sSubTotG.replace("$", "").replace(",", ""), sImpueG.replace("$", "").replace(",", ""), sTotG.replace("$", "").replace(",", ""), "now()", "'" + sFDoc + "'", "now()", "'" + sEstadVta + "'", "1", "", "TIK", sConsCort, sMetPag, sCta, jTAObserv.getText().replace("'", "''"), "0", jTDesc.getText().replace("$", "").replace(",", ""), "1", "0", jTTotCost.getText(), Login.sUsrG, sMonG, sTipCam, sFormPag, frmPto.sRecib, frmPto.sMarc, frmPto.sMod, frmPto.sColo, frmPto.sPlacs, frmPto.sNom, frmPto.sTarCirc, frmPto.sNumLic, frmPto.sTel, frmPto.sDirPart, frmPto.sDirOfi, frmPto.sTelOfi, "N", "", "0", "0", "0", "0","")==-1)
            return;
       
        //Inserta el CXC de la deuda        
        if(Star.iInsCXCP(con, "cxc", sConTic, sSerTic, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, sTotG, "0", "now()", "'" + sFDoc + "'", "TIK", "", "0", "", "","")==-1)
            return;

//		//Inserta el CXC del abono        
//        if(Star.iInsCXCP(con, "cxc", sConTic, sSerTic, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, sTotG, "0",  "now()", "'" + sFDoc + "'", "ABON TIK", "EFE", "", "", "","")==-1)
                 
                
        //Si va a estar pagado
        if(jRPagad.isSelected())
        {
            //Inserta el abono            
            if(Star.iInsCXCP(con, "cxc", sConTic, sSerTic, sCodEmpGlo, sSer, sSubTotG, sImpueG, sTotG, "0", sTotG, "now()", "'" + sFDoc + "'", "ABON TIK", "EFE", "0", "", "","")==-1)
            return;               
        }
                                
        /*Obtiene la ùltima venta insertada*/
        String sVta = "";
        try
        {
            sQ = "SELECT vta FROM vtas ORDER BY vta DESC LIMIT 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sVta         = rs.getString("vta");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                    

        /*Lee las cantes de los controles*/
        String sEfe    = jTEfeCant.getText().replace("$", "").replace(",", "");
        String sDeb    = jTDebCant.getText().replace("$", "").replace(",", "");
        String sTar    = jTTarCredCant.getText().replace("$", "").replace(",", "");

        /*Inserta el flujo en la base de datos del efectivo si es mayor a 0*/
        if(Double.parseDouble(sEfe)>0)
        {
            try 
            {                    
                sQ = "INSERT INTO fluj(    concep,   ing_eg,    tipdoc,    impo,     mon,       modd,       vta,            norefer,                                        ncortz,                                     estac,                                          sucu,                                               nocaj) " + 
                                 "VALUES(  'EFE',    'I',       'TIK'," +  sEfe + ", '',        'CXC', " +  sVta + "," +    sConTic.replace("'", "''") + ", " +           sConsCort.replace("'", "''") + ", '" +      Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Inserta el flujo en la base de datos de débito si es mayor a 0*/
        if(Double.parseDouble(sDeb)>0)
        {
            try 
            {                    
                sQ = "INSERT INTO fluj(    concep,   ing_eg,    tipdoc,      impo,     mon,     modd,        vta,           norefer,                                    ncortz,                                     estac,                                          sucu,                                           nocaj) " + 
                                 "VALUES(  'DEB',    'I',       'TIK'," +    sDeb + ", '',      'CXC', " +   sVta + "," +   sConTic.replace("'", "''") + ", " +       sConsCort.replace("'", "''") + ", '" +      Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Inserta el flujo en la base de datos de tarjeta de crédito si es mayor a 0*/
        if(Double.parseDouble(sTar)>0)
        {
            try 
            {                    
                sQ = "INSERT INTO fluj(    concep,  ing_eg,    tipdoc,      impo,     mon,      modd,           vta,           norefer,                                     ncortz,                                         estac,                                          sucu,                                           nocaj) " + 
                                "VALUES(  'TAR',    'I',       'TIK'," +    sTar + ", '',       'CXC', " +      sVta + "," +   sConTic.replace("'", "''") + ", " +        sConsCort.replace("'", "''") + ", '" +          Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Si el cambio es mayor a 0 entonces*/
        if(Double.parseDouble(sCam) > 0)
        {
            /*Inserta el flujo en la base de datos*/
            try 
            {                    
                sQ = "INSERT INTO fluj(concep,   ing_eg,    tipdoc,     impo,     mon ,     modd,        vta,            norefer,                           ncortz,  estac,                                         sucu,                                           nocaj) " + 
                             "VALUES(  'CAM',    'E',       'TIK'," +   sCam + ", '',       'CXP', " +   sVta + "," +    sConTic.replace("'", "''") + ",  0, '" +  Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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

        /*Recorre la tabla de partidas*/        
        for(int x = 0; x < jTabPart.getRowCount(); x++)
        {
            /*Obtiene el descuento*/
            String sDesc               = jTabPart.getValueAt(x, 4).toString().trim();

            /*Si el descuento es cadena vacia entonces colocar 0*/
            if(sDesc.compareTo("")==0)
                sDesc           = "0";

            //Declara variables locales
            String sImpue   = "";
            String sUni     = "";
            
            /*Obtiene el valor del impuesto y la unidad*/            
            try
            {
                sQ = "SELECT impues.IMPUEVAL AS Impuesto, prods.UNID FROM prods LEFT JOIN impues ON impues.CODIMPUE = prods.IMPUE WHERE prods.PROD = '" + jTabPart.getValueAt(x, 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sImpue          = rs.getString ("Impuesto");                    
                    sUni            = rs.getString ("unid");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }  

            /*Obtiene el costo dependiendo el método de costeo de la empresa*/
            String sCostMe  = Star.sGetCost(con, jTabPart.getValueAt(x, 1).toString().trim(), jTabPart.getValueAt(x, 0).toString().trim());
                        
            /*Si hubo error entonces regresa*/
            if(sCostMe==null)
                return;
            
            /*Si tiene talla o color entonces procesa esto en una función*/
            if(jTabPart.getValueAt(x, 10).toString().trim().compareTo("")!=0 || jTabPart.getValueAt(x, 11).toString().trim().compareTo("")!=0)                           
                Star.vTallCol(con, jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 10).toString().trim(), jTabPart.getValueAt(x, 11).toString().trim(), jTabPart.getValueAt(x, 1).toString().trim(), "-");            
            
            /*Si tiene pedimento la fila entonces*/
            if(jTabPart.getValueAt(x, 15).toString().trim().compareTo("")!=0)
            {
                /*Actualiza la cantidad en las partidas de la compra para el lote y factura*/
                try 
                {            
                    sQ = "UPDATE partcomprs SET "
                            + "cantlotpend      = cantlotpend - " + jTabPart.getValueAt(x, 0).toString().trim() 
                            + " WHERE id_id     = " + jTabPart.getValueAt(x, 15).toString().trim();                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                 }        
                
                /*Actualiza todas las partidas de lote y pedimento que estan ya igual en cantidades*/
                try 
                {            
                    sQ = "UPDATE partcomprs SET "
                            + "aplic                = 1 "
                            + "WHERE cantlotpend    = 0";                            
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                 }        
                
            }/*Fin de if(jTab.getValueAt(x, 14).toString().compareTo("")!=0)*/            
                        
            /*Obtiene si tiene fecha de caducidad correctamente*/
            String sFCadu               = "now()";
            if(jTabPart.getValueAt(x, 14).toString().trim().compareTo("")!=0)                           
                sFCadu                  = "'" + jTabPart.getValueAt(x, 14).toString().trim() + "'";            
            
            /*Si tiene serie entonces procesa esta parte en la función*/
            if(jTabPart.getValueAt(x, 19).toString().trim().compareTo("")!=0)
                Star.vSerPro(con, jTabPart.getValueAt(x, 1).toString().trim(), jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 19).toString().trim(), jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 20).toString().trim(), "-");                                    
            
            /*Obtiene el id de lote y pedimento*/
            String sIdLotPed            = jTabPart.getValueAt(x, 15).toString().trim();
            
            /*Si es cadena vacia que sea -1 entonces*/
            if(sIdLotPed.compareTo("")==0)
                sIdLotPed               = "-1";
            
            /*Si el producto es un kit entonces*/                 
            String sKit         = "0";
            if(jTabPart.getValueAt(x, 16).toString().trim().compareTo("Si")==0)           
                sKit            = "1";                        
            
            /*Si el impuesto es nulo entonces que sea cadena vacia*/
            if(sImpue==null)
                sImpue          = "0";
            
            /*Inserta en la base de datos la partida de la venta*/
            try 
            {                    
                sQ = "INSERT INTO partvta(         vta,              prod,                                                                         cant,                                                  unid,                                       descrip,                                                                                                          pre,                                                                                          descu,                    impue,            mon,     impo,                                                                               falt,       eskit,              kitmae, tipdoc,  list,   alma,                                                                       ueps,  peps,   tall,                                                                       colo,                                                                       cantentre,                                              serprod,                                                comenser,                                               tipcam,           garan,                                                    cost,        costprom,      idlotped,           lot,                                                      pedimen,                                               fcadu,           codimpue) " + 
                                     "VALUES(" +   sVta + ", '"  +   jTabPart.getValueAt(x, 1).toString().trim().replace("'", "''") + "'," +       jTabPart.getValueAt(x, 0).toString().trim() + ",'" +   sUni.replace("'", "''").trim() + "','" +    jTabPart.getValueAt(x, 3).toString().trim().replace("'", "''") + frmPto.sRecib + " " + frmPto.sPlacs + "'," +     jTabPart.getValueAt(x, 5).toString().trim().replace("$", "").replace(",", "") + "," +         sDesc.trim() + "," +      sImpue.trim() + ",''," +   jTabPart.getValueAt(x, 6).toString().trim().replace("$", "").replace(",", "") + ",  now()," +   sKit + ",           0,      'TIK',   1, '" + jTabPart.getValueAt(x, 9).toString().trim().replace("'", "''").trim() + "', 0,     0, '" + jTabPart.getValueAt(x, 10).toString().trim().replace("'", "''") + "', '" +  jTabPart.getValueAt(x, 11).toString().trim().replace("'", "''") + "', " +   jTabPart.getValueAt(x, 0).toString().trim() + ", '" +   jTabPart.getValueAt(x, 19).toString().trim() + "', '" + jTabPart.getValueAt(x, 20).toString().trim() + "', " +  sTipCam + ", '" + jTabPart.getValueAt(x, 21).toString().trim() + "', " +    sCostMe + ", 0,         " + sIdLotPed + ", '" + jTabPart.getValueAt(x, 12).toString().trim() + "', '" +   jTabPart.getValueAt(x, 13).toString().trim() + "', " + sFCadu + ", '" + jTabPart.getValueAt(x, 24).toString().trim() + "')";                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Si el producto es un kit entonces*/                             
            if(sKit.compareTo("1")==0)
            {                
                /*Obtiene el último ID insertado para referenciar los kits*/
                String sId  = "";
                try
                {
                    sQ = "SELECT MAX(id_id) AS id FROM partvta WHERE vta = " + sVta + " ORDER BY id DESC LIMIT 1";	
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sId = rs.getString("id");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                
                /*Coloca que si es kit y agrega las partidas con precio 0*/
                sKit            = "1";
                if(Star.iInsFacComp(con, jTabPart.getValueAt(x, 1).toString().trim(), sVta, jTabPart.getValueAt(x, 9).toString().trim(), "TIK", sSerTic + sConTic, sCodEmpGlo.replace("'", "''") , "1", sSerTic, sTipCam, sId, jTabPart.getValueAt(x, 0).toString().trim(), jTabPart.getValueAt(x, 2).toString().trim(), jTabPart.getValueAt(x, 24).toString().trim())==-1)
                    return;                                
            }
            
            /*Comprueba si el producto es un servicio*/       
            boolean bServ   = false;
            try
            {
                sQ = "SELECT servi FROM prods WHERE prod = '" + jTabPart.getValueAt(x, 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())          
                {
                    /*Si es un servicio entonces coloa la bandera*/
                    if(rs.getString("servi").compareTo("1")==0)                                                    
                        bServ   = true;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }         
            
            /*Si no es un kit y si no es un servicio entonces*/
            if(sKit.compareTo("0")==0 && !bServ)
            {
                /*Obtiene la cantidad correcta dependiendo de su unidad*/
                String sCant    = Star.sCantUnid(jTabPart.getValueAt(x, 2).toString().replace("'", "''").trim(), jTabPart.getValueAt(x, 0).toString().trim());
                
                /*Si tiene lote o pedimento la fila entonces procesa esto en una función*/
                if(jTabPart.getValueAt(x, 12).toString().compareTo("")!=0 || jTabPart.getValueAt(x, 13).toString().compareTo("")!=0)            
                {                    
                    if(Star.sLotPed(con, jTabPart.getValueAt(x, 1).toString().trim(), sCant, jTabPart.getValueAt(x, 9).toString().trim(), jTabPart.getValueAt(x, 12).toString().trim(), jTabPart.getValueAt(x, 13).toString().trim(), jTabPart.getValueAt(x, 14).toString().trim(), "-")==null)
                        return;
                }
                
                /*Realiza la afectación correspondiente al almacén*/
                if(Star.iAfecExisProd(con, jTabPart.getValueAt(x, 1).toString().trim().replace("'", "''").trim(), jTabPart.getValueAt(x, 9).toString().trim().replace("'", "''").trim(), sCant, "-")==-1)
                    return;
                
                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, jTabPart.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                    return;
                
                /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                if(!Star.vRegMoniInv(con, jTabPart.getValueAt(x, 1).toString().trim().replace("'", "''"), sCant, jTabPart.getValueAt(x, 3).toString().trim().replace("'", "''"), jTabPart.getValueAt(x, 9).toString().trim().replace("'", "''"), Login.sUsrG , sConTic.replace("'", "''"), "TIK", sUni.replace("'", "''"), sSerTic.replace("'", "''".trim()), sCodEmpGlo.replace("'", "''"), "1"))                                
                    return;      
                                
            }/*Fin de if(sKit.compareTo("0")==0)*/
                            
        }/*Fin de for(int x = 0; x < jTabPart.getRowCount(); x++)*/

        /*Contiene el total de costo*/
        String sTotCost     = "";
        
        /*Obtiene la sumatoria del total de costo*/
        try
        {
            sQ = "SELECT IFNULL(SUM(cost * cant),0) AS totcost FROM partvta WHERE vta = " + sVta;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sTotCost   = rs.getString("totcost");                           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el encabezado de la venta con el costo total*/
        try 
        {                
            sQ = "UPDATE vtas SET "
                    + "totcost      = " + sTotCost + " "                    
                    + "WHERE vta    = " + sVta;                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Dale formato de moneda a los totales
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sSubTotG);                
        sSubTotG        = n.format(dCant);
        dCant           = Double.parseDouble(sTotG);                
        sTotG           = n.format(dCant);

        //Mensaje en caso de que sea nota
        String sMjsNot  = "";
        if(jRNot.isSelected())
            sMjsNot     = "    <NOTA>";        
        
        /*Declara variables finales que se necesitan para el thread*/
        final String sConTicFin     = sConTic;
        final String sVtaFin        = sVta;
        final String sSerTicFin     = sSerTic;
        final String sPaiFi         = sPaiGlob; 
        final String sTelFi         = sTelGlob; 
        final String sCallFi        = sCallGlob; 
        final String sColFi         = sColGlob; 
        final String sCPFi          = sCPGlob; 
        final String sNoExtFi       = sNoExtGlob; 
        final String sNoIntFi       = sNoIntGlob;
        final String sCiuFi         = sCiuGlob; 
        final String sEstFi         = sEstGlob; 
        final String sRFCFi         = sRFCGlob; 
        final String sCoFi          = sMailLocGlob;
        final String sNomFi         = sNomEmp;
        final String sFDocFi        = sFDoc;
        final String sMonFi         = sMonG + sMjsNot;
        final String sCoLocFi       = sMailLocGlob;
        final String sTotFi         = sTotG;
        final String sSubTotFi      = sSubTotG;
        final String sImpueFi       = sImpueG;        
        final String sNomLocFi      = sNomLocGlob;
        final String sTelLocFi      = sTelLocGlob;
        final String sColLocFi      = sColLocGlob;
        final String sCallLocFi     = sCallLocGlob;
        final String sCPLocFi       = sCPLocGlob;
        final String sCiuLocFi      = sCiuLocGlob;
        final String sEstLocFi      = sEstLocGlob;
        final String sPaiLocFi      = sPaiLocGlob;
        final String sRFCLocFi      = sRFCLocGlob;
        final String sNoIntLocFi    = sNoIntLocGlob;
        final String sNoExtLocFi    = sNoExtLocGlob;       

        /*Thread para quitar carga y que pase mas rapidamente a la otra vta*/
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

                //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                String sCarp    = Star.sGetRutCarp(con);                    

                //Si hubo error entonces regresa
                if(sCarp==null)
                    return;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                    
                String      sQ; 
                
                /*Comprueba si tiene que imprimir o no el logo en el ticket*/
                boolean bSi = false;
                try
                {
                    sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'logotik'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene la fecha de creación*/
                        if(rs.getString("val").compareTo("1")==0)                                   
                            bSi = true;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }

                /*Obtiene el símbolo de la moneda*/
                String sSimb    = "";
                try
                {
                    sQ = "SELECT simb FROM mons WHERE mon = '" + sMonG + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sSimb   = rs.getString("simb");
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

                /*Obtiene la ruta completa hacia el logo*/
                String sRutLog      = sCarp + "\\Imagenes\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

                /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
                if(!new File(sRutLog).exists())
                    sRutLog             = getClass().getResource(Star.sIconDef).toString();

                /*Inicialmente no es de 52mm*/
                int i52;

                /*Contiene el reporte que sera*/
                java.io.InputStream in;

                /*Si es de 52mm entonces*/
                if(s52Glob.compareTo("1")==0)                        
                {
                    /*Coloca que será de 52mm*/
                    i52 = 1;

                    /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/                    
                    if(sNomEmp.trim().compareTo(Star.sNomCliMostG)!=0)                        
                        in    = getClass().getResourceAsStream("/jasreport/rptTickVta52.jrxml");                        
                    /*El reporte será este*/
                    else
                        in    = getClass().getResourceAsStream("/jasreport/rptTickVtaMost52.jrxml");
                }                    
                else
                {
                    /*Coloca la bandera para saber que no es de 52mm*/
                    i52 = 0;

                    /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/
                    if(sNomEmp.compareTo(Star.sNomCliMostG)!=0)
                        in    = getClass().getResourceAsStream("/jasreport/rptTickVta.jrxml");
                    /*Else será este reporte*/
                    else                                                    
                        in    = getClass().getResourceAsStream("/jasreport/rptTickVtaMost.jrxml");
                }                    

                /*Si la carpeta de los tickets no existen entonces crea la carpeta*/
                sCarp                    += "\\Tickets";
                if(!new File(sCarp).exists())
                    new File(sCarp).mkdir();

                /*Si la carpeta de la empresa no existen entonces crea la carpeta*/
                sCarp                    += "\\" + Login.sCodEmpBD;
                if(!new File(sCarp).exists())
                    new File(sCarp).mkdir();

                /*Completa la ruta del ticket*/
                sCarp                   += "\\" +  sSerTicFin + "-" + sConTicFin + ".pdf";
                
                /*Llama la función para crear el PDF*/
                Star.vPDF("", sMonFi, sConTicFin, "", sVtaFin, sSerTicFin, sFDocFi, sNomFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstFi, sRFCFi, sCoLocFi, Star.sObLet(sTotFi.replace(",", "").replace("$", ""), sMonFi, sSimb, true), sSubTotFi, sImpueFi, sTotFi, "", "", "", sNomLocFi, sTelLocFi, sColLocFi, sCallLocFi, sCPLocFi, sCiuLocFi, sEstLocFi, sPaiLocFi, sRFCLocFi, sRutLog, bSi, true, true, in, null, "", sCarp, 2, true, false, i52, false, "", "", "", "", "", sNoIntLocFi, sNoExtLocFi, "", "", "", "");

            }/*Fin de public void run()*/
            
        }).start();

    }/*Fin de private void vTic(Connection con)*/
            
            
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de cobrar*/
    private void jBCobKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCobKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCobKeyPressed

    
    
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
   
    
    /*Cuando se presiona una tecla en el campo de edición de tot*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición del tot*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());        
        
    }//GEN-LAST:event_jTTotFocusGained

                
    /*Cuando se pierde el foco del teclado en el campo del tot*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTTot.setCaretPosition(0);
                
    }//GEN-LAST:event_jTTotFocusLost
                                            
    
    /*Cuando se presiona una tecla en el campo de efectivo*/
    private void jTEfeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEfeKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTEfeKeyPressed
        
    
    /*Cuando se gana el foco del teclado en el campo de efectivo*/
    private void jTEfeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEfe.setSelectionStart(0);jTEfe.setSelectionEnd(jTEfe.getText().length());        

    }//GEN-LAST:event_jTEfeFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del efectivo*/
    private void jTEfeDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeDescripFocusGained
                
        /*Selecciona todo el texto cuando gana el foco*/
        jTEfeDescrip.setSelectionStart(0);jTEfeDescrip.setSelectionEnd(jTEfeDescrip.getText().length());        
        
    }//GEN-LAST:event_jTEfeDescripFocusGained

    
    /*Cuando se presiona una tecla en el campo de la descripción del efectivo*/
    private void jTEfeDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEfeDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEfeDescripKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del efectivo cant*/
    private void jTEfeCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEfeCant.setSelectionStart(0);jTEfeCant.setSelectionEnd(jTEfeCant.getText().length());                
        
    }//GEN-LAST:event_jTEfeCantFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del pago en efectivo cantidad*/
    private void jTEfeCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeCantFocusLost
        
        /*Lee todos los pagos*/
        String sPagEfe    = jTEfeCant.getText();
        String sPagDeb    = jTDebCant.getText();
        String sPagCred   = jTTarCredCant.getText();
        
        /*Lee el total*/
        String sTot        = jTTot.getText();
                        
        /*Si los pagos y el total tienen signos de dollar y comas quitarselos*/
        sPagEfe    = sPagEfe.replace  ("$", "").replace(",", "");                            
        sPagDeb    = sPagDeb.replace  ("$", "").replace(",", "");                            
        sPagCred   = sPagCred.replace ("$", "").replace(",", "");                            
        sTot       = sTot.replace     ("$", "").replace(",", "");                            

        /*Si es cadena vacia el pago en efectivo entonces*/
        String sSald;
        String sCamb;
        if(sPagEfe.compareTo("")==0)
        {
            /*Coloca 0 en el control del efectivo y en el cambio*/
            jTEfeCant.setText   ("$0.00");            
            jTCamb.setText      ("$0.00");            
            
            /*Dale formato de mon al total*/            
            double dCant    = Double.parseDouble(sTot);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sTot            = n.format(dCant);

            /*Coloca el total en el saldo*/
            jTSald.setText(sTot);
            
            /*Quita el formato de moneda al total*/
            sTot        = sTot.replace      ("$", "").replace(",", "");                    
            
            /*Suma el pago con débito, crédito y resta al total para saber el saldo pendiente*/
            sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred)));
            
            /*Si la diferencia es 0 entonces*/
            if(Double.parseDouble(sSald)==0)
            {
                /*El saldo va a ser igual a 0 y regresa*/
                jTSald.setText("$0.00");                                            
                return;
            }
            
            /*Si la diferencia es menor a 0 entonces*/            
            if(Double.parseDouble(sSald) <  0)
            {
                /*El saldo va a ser igual a 0*/
                jTSald.setText("$0.00");
                
                /*Obtiene el cambio*/
                sCamb = Double.toString((Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred)) - Double.parseDouble(sTot));
                
                /*Dale formato de moneda al cambio*/
                dCant       = Double.parseDouble(sCamb);                                
                sCamb       = n.format(dCant);
                
                /*Coloca el cambio en su lugar y regresa*/
                jTCamb.setText(sCamb);                                
                return;
            }                        
            
            /*Si la diferencia es mayor al total entonces*/
            if(Double.parseDouble(sSald) >  Double.parseDouble(sTot))
            {
                /*Dale formato de mon al saldo*/
                dCant       = Double.parseDouble(sSald);                                
                sSald       = n.format(dCant);
                                
                /*Coloca el saldo correcto*/
                jTSald.setText(sSald);
                
                /*El cambio sera 0 y regresa*/
                jTCamb.setText("$0.00");                                
                return;
            }    
            
            /*Regresa*/
            return;
            
        }/*Fin de if(sPagEfe.compareTo("")==0)*/
        
        /*Si el texto introducido no es númerico para la cant entonces*/
        try
        {
            Double.parseDouble(sPagEfe);
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Colocar 0 en el campo y el cambio*/
            jTEfeCant.setText   ("$0.00");        
            jTCamb.setText      ("$0.00");            
            
            /*Regresa*/
            return;
        }    
        
        /*Suma todos los pagos y luego restalos del tot para saber el saldo*/
        sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)));
        
        /*Si el salgo es igual al total entonces*/
        if(Double.parseDouble(sSald)==Double.parseDouble(sTot))
        {
            /*Dale formato de moneda al total*/            
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            double dCant    = Double.parseDouble(sTot.replace("$", "").replace(",", ""));                
            sTot            = n.format(dCant);
            
            /*El saldo va a ser el total*/
            jTSald.setText(sTot);
            
            /*Obtiene las cantidades de los campos de pagos*/
            String sEfe     = jTEfeCant.getText();
            String sDeb     = jTDebCant.getText();
            String sTar     = jTTarCredCant.getText();
            
            /*Dale formato de moneda a todos los campos de pagos*/
            dCant           = Double.parseDouble(sEfe.replace("$", "").replace(",", ""));                
            sEfe            = n.format(dCant);
            dCant           = Double.parseDouble(sDeb.replace("$", "").replace(",", ""));                
            sDeb            = n.format(dCant);
            dCant           = Double.parseDouble(sTar.replace("$", "").replace(",", ""));                
            sTar            = n.format(dCant);
            
            /*Coloca las formas de cobro con su cantidad ya con formato en su lugar*/
            jTEfeCant.setText       (sEfe);
            jTDebCant.setText       (sDeb);
            jTTarCredCant.setText   (sTar);
            
            /*El cambio es 0*/
            jTCamb.setText("$0.00");
            
            /*El cambio va a ser 0 y regresa*/
            jTCamb.setText("$0.00");
            return;
        }
        
        /*Si el saldo es igual a 0 entonces*/
        if(Double.parseDouble(sSald)==0)
        {
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Dale formato de mon al efectivo*/            
            double dCant    = Double.parseDouble(sPagEfe);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sPagEfe         = n.format(dCant);
            
            /*Coloca enm el efectivo la cant ya con formato*/
            jTEfeCant.setText(sPagEfe);
            
            /*El cambio será 0 y regresa*/
            jTCamb.setText("$0.00");                        
            return;
        }
        
        /*Si el saldo es mayor a 0 entonces*/
        if(Double.parseDouble(sSald) > 0)
        {
            /*Dale formato de mon al saldo*/            
            double dCant    = Double.parseDouble(sSald);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSald           = n.format(dCant);
            
            /*Coloca el saldo correcto*/
            jTSald.setText(sSald);
            
            /*Dale formato de mon al efectivo*/           
            dCant       = Double.parseDouble(sPagEfe);                            
            sPagEfe        = n.format(dCant);
            
            /*Coloca enm el efectivo la cant ya con formato y regresa*/
            jTEfeCant.setText(sPagEfe);            
            return;
        }
        
        /*Si el saldo es menor a 0 entonces*/
        if(Double.parseDouble(sSald)<0)
        {
            /*Obtiene el cambio*/
            sCamb = Double.toString((Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)) - Double.parseDouble(sTot));
            
            /*Dale formato de mon al cambio*/            
            double dCant    = Double.parseDouble(sCamb);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sCamb           = n.format(dCant);
            
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Coloca el cambio*/
            jTCamb.setText(sCamb);
            
            /*Dale formato de mon al efectivo*/            
            dCant       = Double.parseDouble(sPagEfe);                            
            sPagEfe        = n.format(dCant);
            
            /*Coloca enm el efectivo la cant ya con formato*/
            jTEfeCant.setText(sPagEfe);            
        }                                                
        
    }//GEN-LAST:event_jTEfeCantFocusLost

    
    /*Cuando se presiona una tecla en el campo del pago en efectivo cant*/
    private void jTEfeCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEfeCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEfeCantKeyPressed

      
    /*Cuando se gana el foco del teclado en el campo de cambio*/
    private void jTCambFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCambFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCamb.setSelectionStart(0);jTCamb.setSelectionEnd(jTCamb.getText().length());        
        
    }//GEN-LAST:event_jTCambFocusGained

    
    /*Cuando se presiona una tecla en el campo del cambio*/
    private void jTCambKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCambKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCambKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de saldo*/
    private void jTSaldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSaldFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSald.setSelectionStart(0);jTSald.setSelectionEnd(jTSald.getText().length());           
        
    }//GEN-LAST:event_jTSaldFocusGained
       
        
    /*Cuando se presiona una tecla en el campo del saldo*/
    private void jTSaldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSaldKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSaldKeyPressed

    
        
    /*Cuando se gana el foco del teclado en el campo del débito*/
    private void jTDebFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDeb.setSelectionStart(0);jTDeb.setSelectionEnd(jTDeb.getText().length());        
        
    }//GEN-LAST:event_jTDebFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del débito*/
    private void jTDebDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDebDescrip.setSelectionStart(0);jTDebDescrip.setSelectionEnd(jTDebDescrip.getText().length());        
        
    }//GEN-LAST:event_jTDebDescripFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la tarjeta de crédito*/
    private void jTTarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTar.setSelectionStart(0);jTTar.setSelectionEnd(jTTar.getText().length());        
        
    }//GEN-LAST:event_jTTarFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la tarjeta de crédito*/
    private void jTTarDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTarDescrip.setSelectionStart(0);jTTarDescrip.setSelectionEnd(jTTarDescrip.getText().length());        
        
    }//GEN-LAST:event_jTTarDescripFocusGained

    
    /*Cuando se presiona una tecla en el campo del débito*/
    private void jTDebKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDebKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jTDebKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la descripción del débito*/
    private void jTDebDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDebDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jTDebDescripKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la tarjeta de crédito*/
    private void jTTarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jTTarKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la descripción de la tarjeta de crédito*/
    private void jTTarDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jTTarDescripKeyPressed

    
    /*Cuando se tipea una tecla en el campo de débito cant*/
    private void jTDebCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDebCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();        
        
    }//GEN-LAST:event_jTDebCantKeyTyped

    
    /*Cuando se tipea una tecla en el campo de tarjeta de crédito cant*/
    private void jTTarCredCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarCredCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTTarCredCantKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de débito cant*/
    private void jTDebCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDebCant.setSelectionStart(0);jTDebCant.setSelectionEnd(jTDebCant.getText().length());                
        
    }//GEN-LAST:event_jTDebCantFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la tarjeta de crédito cant*/
    private void jTTarCredCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarCredCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTarCredCant.setSelectionStart(0);jTTarCredCant.setSelectionEnd(jTTarCredCant.getText().length());                
        
    }//GEN-LAST:event_jTTarCredCantFocusGained

    
    /*Cuando se presiona una tecla en el campo de débito cant*/
    private void jTDebCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDebCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDebCantKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la tarjeta de crédito cant*/
    private void jTTarCredCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarCredCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTTarCredCantKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de débito cant*/
    private void jTDebCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebCantFocusLost
        
        //Declara variables locales
        String sTot;
        String sCamb;
        String sPagEfe;
        String sPagDeb;
        String sPagCred;
        String sSald;
        
        
        
        
        
        /*Lee todos los pagos*/
        sPagEfe    = jTEfeCant.getText();
        sPagDeb    = jTDebCant.getText();
        sPagCred   = jTTarCredCant.getText();
        
        /*Lee el tot*/
        sTot        = jTTot.getText();
                        
        /*Si los pagos y el tot tienen signos de dollar y comas quitarselos*/
        sPagEfe    = sPagEfe.replace  ("$", "");                    
        sPagEfe    = sPagEfe.replace  (",", "");
        sPagDeb    = sPagDeb.replace  ("$", "");                    
        sPagDeb    = sPagDeb.replace  (",", "");
        sPagCred   = sPagCred.replace ("$", "");                    
        sPagCred   = sPagCred.replace (",", "");
        sTot       = sTot.replace      ("$", "");                    
        sTot       = sTot.replace      (",", "");

        /*Si es cadena vacia el pago en débto entonces*/
        if(sPagDeb.compareTo("")==0)
        {
            /*Coloca 0 en el control*/
            jTDebCant.setText("$0.00");
            
            /*Dale formato de mon al tot*/            
            double dCant    = Double.parseDouble(sTot);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sTot            = n.format(dCant);

            /*Coloca el tot en el saldo*/
            jTSald.setText(sTot);
            
            /*Quita el formato de mon al tot*/
            sTot        = sTot.replace      ("$", "");                    
            sTot        = sTot.replace      (",", "");
            
            /*Suma el pago con efectivo, crédito y resta al tot para saber el saldo pendiente*/
            sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagEfe) + Double.parseDouble(sPagCred)));
            
            /*Si la diferencia es 0 entonces*/
            if(Double.parseDouble(sSald)==0)
            {
                /*El saldo va a ser igual a 0*/
                jTSald.setText("$0.00");
                
                /*Regresa*/
                return;
            }
            
            /*Si la diferencia es menor a 0 entonces*/
            if(Double.parseDouble(sSald) <  0)
            {
                /*El saldo va a ser igual a 0*/
                jTSald.setText("$0.00");
                
                /*Obtiene el cambio*/
                sCamb = Double.toString((Double.parseDouble(sPagEfe) + Double.parseDouble(sPagCred)) - Double.parseDouble(sTot));
                
                /*Dale formato de mon al cambio*/
                dCant           = Double.parseDouble(sCamb);                                
                sCamb           = n.format(dCant);
                
                /*Coloca el cambio en su lugar y regresa*/
                jTSald.setText(sCamb);                                
                return;
            }                        
            
            /*Si la diferencia es mayor al tot entonces*/
            if(Double.parseDouble(sSald) >  Double.parseDouble(sTot))
            {
                /*Dale formato de mon al saldo*/
                dCant           = Double.parseDouble(sSald);                                
                sSald           = n.format(dCant);
                                
                /*Coloca el saldo pendiente*/
                jTSald.setText(sSald);
                
                /*El cambio sera 0 y regresa*/
                jTCamb.setText("$0.00");                
                return;
            }    
            
            /*Regresa*/
            return;
            
        }/*Fin de if(sPagoDev.compareTo("")==0)*/
        
        /*Si el texto introducido no es númerico para la cant entonces*/
        try
        {
            Double.parseDouble(sPagDeb);
        }
        catch(NumberFormatException expnNumForm)
        {            
            /*Colocar 0 en el campo y regresa*/
            jTDebCant.setText("$0.00");           
            return;
        }    
        
        /*Suma todos los pagos y luego restalos del tot para saber el saldo*/
        sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)));
            
        /*Si el saldo es igual a 0 entonces*/
        if(Double.parseDouble(sSald)==0)
        {
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Dale formato de mon al pago en débito*/            
            double dCant    = Double.parseDouble(sPagDeb);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sPagDeb         = n.format(dCant);
            
            /*Coloca en el debitola cant ya con formato*/
            jTDebCant.setText(sPagDeb);
            
            /*El cambio será 0 y regresa*/
            jTCamb.setText("$0.00");           
            return;
        }
        
        /*Si el saldo es mayor a 0 entonces*/
        if(Double.parseDouble(sSald)>0)
        {
            /*Dale formato de mon al saldo*/            
            double dCant    = Double.parseDouble(sSald);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSald           = n.format(dCant);
            
            /*Coloca el saldo correcto*/
            jTSald.setText(sSald);
            
            /*Dale formato de mon al pago en débito*/            
            dCant           = Double.parseDouble(sPagDeb);                           
            sPagDeb         = n.format(dCant);
            
            /*Coloca en el debitola cant ya con formato y regresa*/
            jTDebCant.setText(sPagDeb);            
            return;
        }
        
        /*Si el saldo es menor a 0 entonces*/
        if(Double.parseDouble(sSald)<0)
        {
            /*Obtiene el cambio*/
            sCamb           = Double.toString((Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)) - Double.parseDouble(sTot));
            
            /*Dale formato de mon al cambio*/            
            double dCant    = Double.parseDouble(sCamb);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sCamb           = n.format(dCant);
            
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Coloca el cambio*/
            jTCamb.setText(sCamb);
            
            /*Dale formato de mon al pago en débito*/            
            dCant           = Double.parseDouble(sPagDeb);                            
            sPagDeb         = n.format(dCant);
            
            /*Coloca en el debitola cant ya con formato*/
            jTDebCant.setText(sPagDeb);            
        }   
        
    }//GEN-LAST:event_jTDebCantFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tarjeta de credito cant*/
    private void jTTarCredCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarCredCantFocusLost
        
        //Declara variables locales
        String sTot;
        String sCamb;
        String sPagEfe;
        String sPagDeb;
        String sPagCred;
        String sSald;
        
        
        
        
        
        /*Lee todos los pagos*/
        sPagEfe    = jTEfeCant.getText();
        sPagDeb    = jTDebCant.getText();
        sPagCred   = jTTarCredCant.getText();
        
        /*Lee el tot*/
        sTot        = jTTot.getText();
                        
        /*Si los pagos y el tot tienen signos de dollar y comas quitarselos*/
        sPagEfe    = sPagEfe.replace  ("$", "");                    
        sPagEfe    = sPagEfe.replace  (",", "");
        sPagDeb    = sPagDeb.replace  ("$", "");                    
        sPagDeb    = sPagDeb.replace  (",", "");
        sPagCred   = sPagCred.replace ("$", "");                    
        sPagCred   = sPagCred.replace (",", "");
        sTot        = sTot.replace      ("$", "");                    
        sTot        = sTot.replace      (",", "");

        /*Si es cadena vacia el pago en tarjeta de crédito entonces*/
        if(sPagCred.compareTo("")==0)
        {
            /*Coloca 0 en el control*/
            jTTarCredCant.setText("$0.00");
            
            /*Dale formato de mon al tot*/            
            double dCant    = Double.parseDouble(sTot);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sTot            = n.format(dCant);

            /*Coloca el tot en el saldo*/
            jTSald.setText(sTot);
            
            /*Quita el formato de mon al tot*/
            sTot        = sTot.replace      ("$", "");                    
            sTot        = sTot.replace      (",", "");
            
            /*Suma el pago con débito, efectivo y resta al tot para saber el saldo pendiente*/
            sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagEfe) + Double.parseDouble(sPagDeb)));
            
            /*Si la diferencia es 0 entonces*/
            if(Double.parseDouble(sSald)==0)
            {
                /*El saldo va a ser igual a 0 y regresa*/
                jTSald.setText("$0.00");                                
                return;
            }
            
            /*Si la diferencia es menor a 0 entonces*/
            if(Double.parseDouble(sSald) <  0)
            {
                /*El saldo va a ser igual a 0*/
                jTSald.setText("$0.00");
                
                /*Obtiene el cambio*/
                sCamb = Double.toString((Double.parseDouble(sPagEfe) + Double.parseDouble(sPagDeb)) - Double.parseDouble(sTot));
                
                /*Dale formato de mon al cambio*/
                dCant       = Double.parseDouble(sCamb);                                
                sCamb         = n.format(dCant);
                
                /*Coloca el cambio en su lugar y regresa*/
                jTSald.setText(sCamb);                                
                return;
            }                        
            
            /*Si la diferencia es mayor al tot entonces*/
            if(Double.parseDouble(sSald) >  Double.parseDouble(sTot))
            {
                /*Dale formato de mon al saldo*/                
                dCant       = Double.parseDouble(sSald);                                
                sSald          = n.format(dCant);
                                
                /*Coloca el saldo pendiente*/
                jTSald.setText(sSald);
                
                /*El cambio sera 0 y regresa*/
                jTCamb.setText("$0.00");               
                return;
            }    
            
            /*Regresa*/
            return;
            
        }/*Fin de if(sPagCred.compareTo("")==0)*/
        
        /*Si el texto introducido no es númerico para la cant entonces*/
        try
        {
            Double.parseDouble(sPagCred);
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Colocar 0 en el campo y regresa*/
            jTTarCredCant.setText("$0.00");           
            return;
        }    
        
        /*Suma todos los pagos y luego restalos del tot para saber el saldo*/
        sSald = Double.toString(Double.parseDouble(sTot) - (Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)));
            
        /*Si el saldo es igual a 0 entonces*/
        if(Double.parseDouble(sSald)==0)
        {
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Dale formato de mon al pago en tarjeta de credito*/            
            double dCant    = Double.parseDouble(sPagCred);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sPagCred        = n.format(dCant);
            
            /*Coloca en el pago de tarjeta de crédito la cant ya con formato*/
            jTTarCredCant.setText(sPagCred);
            
            /*El cambio será 0 y regresa*/
            jTCamb.setText("$0.00");                        
            return;
        }
        
        /*Si el saldo es mayor a 0 entonces*/
        if(Double.parseDouble(sSald)>0)
        {
            /*Dale formato de mon al saldo*/            
            double dCant    = Double.parseDouble(sSald);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSald           = n.format(dCant);
            
            /*Coloca el saldo*/
            jTSald.setText(sSald);
            
            /*Dale formato de mon al pago en tarjeta de credito*/
            dCant       = Double.parseDouble(sPagCred);                            
            sPagCred       = n.format(dCant);
            
            /*Coloca en el pago de tarjeta de crédito la cant ya con formato y regresa*/
            jTTarCredCant.setText(sPagCred);                        
            return;
        }
        
        /*Si el saldo es menor a 0 entonces*/
        if(Double.parseDouble(sSald)<0)
        {
            /*Obtiene el cambio*/
            sCamb = Double.toString((Double.parseDouble(sPagDeb) + Double.parseDouble(sPagCred) + Double.parseDouble(sPagEfe)) - Double.parseDouble(sTot));
            
            /*Dale formato de mon al cambio*/            
            double dCant    = Double.parseDouble(sCamb);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sCamb           = n.format(dCant);
            
            /*El saldo va a ser 0*/
            jTSald.setText("$0.00");
            
            /*Coloca el cambio*/
            jTCamb.setText(sCamb);
            
            /*Dale formato de mon al pago en tarjeta de credito*/
            dCant       = Double.parseDouble(sPagCred);                            
            sPagCred       = n.format(dCant);
            
            /*Coloca en el pago de tarjeta de crédito la cant ya con formato*/
            jTTarCredCant.setText(sPagCred);            
        }  
        
    }//GEN-LAST:event_jTTarCredCantFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de observaciones*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se presiona una tecla en el campo de observaciones*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
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

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se tipea una tecla en el campo del efectivo cantidad*/
    private void jTEfeCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEfeCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTEfeCantKeyTyped

    
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCobMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCob.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCobMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCobMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCob.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCobMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el radio de tickets*/
    private void jRTicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRTicKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRTicKeyPressed

    
    /*Cuando se presiona una tecla en el radio de remisiones*/
    private void jRRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRRemKeyPressed

    
    /*Cuando se presiona una tecla en el radio de factura*/
    private void jRFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRFacKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del efectivo*/
    private void jTEfeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTEfe.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEfeFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del dèbito*/
    private void jTDebFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDeb.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDebFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tarjeta de crèdito*/
    private void jTTarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTTar.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTarFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripciòn del efectivo*/
    private void jTEfeDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEfeDescripFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTEfeDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEfeDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripciòn de dèbito*/
    private void jTDebDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDebDescripFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDebDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDebDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripciòn de la tarjeta de crédito*/
    private void jTTarDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarDescripFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTTarDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTarDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del saldo*/
    private void jTSaldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSaldFocusLost
       
        /*Coloca el cursor al principio del control*/
        jTSald.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSaldFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cambio*/
    private void jTCambFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCambFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCamb.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCambFocusLost

    
    //Cuando se presiona una tecla en el radio de nota
    private void jRNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRNotKeyPressed

    
    //Cuando la forma se activa
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        //Guarda la eferencia a esta forma
        cobThis = this;
                
    }//GEN-LAST:event_formWindowActivated

    
    //Cuando se presiona una tecla en el check de pagada
    private void jRPagadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPagadKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRPagadKeyPressed

    
    //Cuando se presiona una tecla en el radio de no pagado
    private void jRNoPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRNoPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRNoPagKeyPressed

    
    //Cuando sucede una acción en el radio de pagada
    private void jRPagadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRPagadActionPerformed
        
        //Manda el foco del botón de cobrar
        jBCob.grabFocus();
        
    }//GEN-LAST:event_jRPagadActionPerformed

    
    //Cuando sucede una acción en el botón de no pagada
    private void jRNoPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRNoPagActionPerformed
        
        //Manda el foco del botón de cobrar
        jBCob.grabFocus();
        
    }//GEN-LAST:event_jRNoPagActionPerformed

    private void jRRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRRemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRRemActionPerformed
        
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces marca el radio button de ticket*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            bG.setSelected(jRTic.getModel(), true);
        //Else if se presiona Alt + N entonces marca el radio button de nota
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_N)
            bG.setSelected(jRNot.getModel(), true);
        /*Else if se presiona Alt + F entonces marca el radio button de ticket*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F)
        {
            /*Si no se puede remisionar entonces*/
            if(!this.bFac)
            {
                /*Manda la forma atras*/
                this.setAlwaysOnTop(false);
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No esta activada la configuración para poder facurar.", "Facturas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Manda la forma adelante y regresa*/
                this.setAlwaysOnTop(true);                
                return;
            }
            
            /*Selecciona el radio de facturar*/           
            bG.setSelected(jRFac.getModel(), true);
        }
            
        /*Else if se presiona Alt + R entonces marca el radio button de ticket*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_R)
        {                
            /*Si no se puede remisionar entonces*/
            if(!this.bRem)
            {
                /*Manda la forma atras*/
                this.setAlwaysOnTop(false);

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No esta activada la configuración para poder remisionar.", "Remisiones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Manda la forma adelante y regresa*/
                this.setAlwaysOnTop(true);
                return;
            }
            
            /*Selecciona el radio de remisión*/
            bG.setSelected(jRRem.getModel(), true);
        }
        /*Si se presiona F10 entonces da clic en el botón de cobrar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F10)
            jBCob.doClick();
        /*Si se presiona F2 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Marca el checkbox de factura o desmarcalo*/
            if(!jRFac.isSelected())
            {
                /*Marcalo nuevamente*/
                bG.setSelected(jRFac.getModel(), true);
                             
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Declara variables*/
                String sRFC     = "";
                String sCall    = "";
                String sCol     = "";
                String sCP      = "";            

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene datos del cliente*/            
                try
                {                  
                    sQ = "SELECT rfc, calle, cp, col FROM emps WHERE codemp = '" + sCodEmpGlo.replace(sSer, "") + "' AND ser = '" + sSer + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos, entonces si tiene RFC*/
                    if(rs.next())
                    {
                        /*Obtiene los resultados de la consulta*/
                        sRFC    = rs.getString("rfc");
                        sCall   = rs.getString("calle");
                        sCP     = rs.getString("cp");
                        sCol    = rs.getString("col");
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

                /*Si el RFC es cadena vacia entonces*/
                if(sRFC.compareTo("")==0)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No tiene RFC el cliente \"" + sCodEmpGlo + "\".", "RFC", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Desmarca el checkbox de factura y regresa*/
                    bG.setSelected(jRTic.getModel(), true);
                    return;
                }

                /*Si el cliente es cliente mostrador entonces*/
                if(sCodEmpGlo.compareTo("SYS")==0)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No se puede facturar a cliente mostrador directamente.", "Facturación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Desmarca el checkbox de factura y regresa*/
                    bG.setSelected(jRTic.getModel(), true);
                    return;
                }

                /*Si no tiene calle entonces*/
                if(sCall.compareTo("")==0)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No tiene calle el cliente \"" + sCodEmpGlo + "\".", "Calle", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Desmarca el checkbox de factura y regresa*/
                    bG.setSelected(jRTic.getModel(), true);
                    return;
                }

                /*Si no tiene CP entonces*/
                if(sCP.compareTo("")==0)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No tiene CP el cliente: " + sCodEmpGlo + ".", "CP", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Desmarca el checkbox de factura y regresa*/
                    bG.setSelected(jRTic.getModel(), true);
                    return;
                }

                /*Si no tiene colonia entonces*/
                if(sCol.compareTo("")==0)
                {
                    /*Mensajea y desmarca el checkbox de factura*/
                    JOptionPane.showMessageDialog(null, "No tiene colonia el cliente: " + sCodEmpGlo + ".", "Colonia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
                    bG.setSelected(jRTic.getModel(), true);
                }               
                
            }/*Fin de if(jCFac.isSelected())*/
            else
                bG.setSelected(jRTic.getModel(), true);
                                        
        }/*Fin de else if(evt.getKeyCode() == KeyEvent.VK_F2*/        
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCob;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jP1;
    private javax.swing.JRadioButton jRFac;
    private javax.swing.JRadioButton jRNoPag;
    private javax.swing.JRadioButton jRNot;
    private javax.swing.JRadioButton jRPagad;
    private javax.swing.JRadioButton jRRem;
    private javax.swing.JRadioButton jRTic;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTCamb;
    private javax.swing.JTextField jTDeb;
    private javax.swing.JTextField jTDebCant;
    private javax.swing.JTextField jTDebDescrip;
    private javax.swing.JTextField jTEfe;
    private javax.swing.JTextField jTEfeCant;
    private javax.swing.JTextField jTEfeDescrip;
    private javax.swing.JTextField jTSald;
    private javax.swing.JTextField jTTar;
    private javax.swing.JTextField jTTarCredCant;
    private javax.swing.JTextField jTTarDescrip;
    private javax.swing.JTextField jTTot;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
