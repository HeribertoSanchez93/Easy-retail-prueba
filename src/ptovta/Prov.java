//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import javax.swing.ImageIcon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;




/*Clase para crear un nu proveedor*/
public class Prov extends javax.swing.JFrame 
{
    /*Contiene el RFC original*/
    private String  sRFCOri;
    
    /*Variables que contiene la dirección de entrega*/
    public String    sTelEn;
    public String    sLadaEn;
    public String    sExtEn;
    public String    sCelEn;
    public String    sTelPer1En;
    public String    sTelPer2En;
    public String    sCallEn;
    public String    sColEn;
    public String    sNoExtEn;
    public String    sIntEn;
    public String    sCPEn;
    public String    sCiuEn;
    public String    sEstadEn;
    public String    sPaiEn;
    public String    sCo1En;
    public String    sCo2En;
    public String    sCo3En;
    
    /*Declara variables públicas estáticas*/
    public String sContac3;
    public String sTelCon3;
    public String sTelPer3;
    public String sTelPer33;
    public String sContac4;
    public String sTelCon4;
    public String sTelPer4;
    public String sTelPer44;
    public String sContac5;
    public String sTelCon5;
    public String sTelPer5;
    public String sTelPer55;
    public String sContac6;
    public String sTelCon6;
    public String sTelPer6;
    public String sTelPer66;
    public String sContac7;
    public String sTelCon7;
    public String sTelPer7;
    public String sTelPer77;
    public String sContac8;
    public String sTelCon8;
    public String sTelPer8;
    public String sTelPer88;
    public String sContac9;
    public String sTelCon9;
    public String sTelPer9;
    public String sTelPer99;
    public String sContac10;
    public String sTelCon10;
    public String sTelPer10;
    public String sTelPer100;
    
    /*Contiene el proveedor*/
    private String sProvG;
    
    /*Contiene la referencia a si mismo*/
    private Prov newProv   = null;       
    
    /*Contiene la serie del proveedor*/
    private String sSerG;   
    
    /*Contiene la tabla de proveedores del otro formulario*/
    private javax.swing.JTable jTabProv;
    
    
    
    
    /*Consructor sin argumentos*/
    public Prov(String sProv, javax.swing.JTable jTabPro) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        if(sProv.compareTo("")==0)
        {
            jComSer.setVisible(true);
            jLabel35.setVisible(true);
            jTCodProv.setVisible(true);
            jLabel20.setVisible(true);
        }
        else
        {
            jComSer.setVisible(false);
            jLabel35.setVisible(false);
            jTCodProv.setVisible(false);
            jLabel20.setVisible(false);
        }
                
        /*Obtiene la tabla de proveedores del otro formulario*/
        jTabProv    = jTabPro;
        
        /*Obtiene el proveedor*/
        sProvG      = sProv;
        
        /*Coloca el código del proveedor en el control*/
        jTProv.setText(sProv);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Inicia estas variables inicialmente en cadena vacia*/                                       
        sTelCon3        = "";
        sTelCon4        = "";
        sTelCon5        = "";
        sTelCon6        = "";
        sTelCon7        = "";
        sTelCon8        = "";
        sTelCon9        = "";
        sTelCon10       = "";
        sTelPer3        = "";
        sTelPer4        = "";
        sTelPer5        = "";
        sTelPer6        = "";
        sTelPer7        = "";
        sTelPer8        = "";
        sTelPer9        = "";
        sTelPer10       = "";
        sTelPer33       = "";
        sTelPer44       = "";
        sTelPer55       = "";
        sTelPer66       = "";
        sTelPer77       = "";
        sTelPer88       = "";
        sTelPer99       = "";
        sTelPer100      = "";
        sContac3        = "";
        sContac4        = "";
        sContac5        = "";
        sContac6        = "";
        sContac7        = "";
        sContac8        = "";
        sContac9        = "";
        sContac10       = "";
        
        /*La dirección de envio inicialmente estara en cadena vacia*/                                
        sTelEn          = "";
        sLadaEn         = "";
        sExtEn          = "";
        sCelEn          = "";
        sTelPer1En      = "";
        sTelPer2En      = "";
        sCallEn         = "";
        sColEn          = "";
        sNoExtEn        = "";
        sIntEn          = "";
        sCPEn           = "";
        sCiuEn          = "";
        sEstadEn        = "";
        sPaiEn          = "";
        sCo1En          = "";
        sCo2En          = "";
        sCo3En          = "";
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Proveedor, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Pon el foco del teclado en el campo de edición del nom del cliente*/
        jTNomb.grabFocus();               
        
        /*Activa en jtextarea de observ que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Crea el grupo para los radio buttons*/
        ButtonGroup  g  = new ButtonGroup ();
        g.add(jRaMor);
        g.add(jRaFisi);
        
        //Se cargan las series del proveedor 
        Star.vCargSer(jComSer, "PROV");
        
        /*Listener para el combobox de series*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Se cargan las series del proveedor 
                Star.vCargSer(jComSer, "PROV");

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
                        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Carga de la base de datos al proveedor y ponlos en los controles de edición*/                
        try
        {
            sQ = "SELECT * FROM provs LEFT OUTER JOIN clasprov ON clasprov.COD = provs.CODCLAS WHERE CONCAT_WS('', ser, prov) = '" + sProvG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el límite de crédito*/                                                                                                                                                                                                               
                String sLimCred     = rs.getString("limcred");                
    
                /*Obtiene la serie del proveedor*/
                sSerG               = rs.getString("ser");
                
                /*Coloca la cuenta contable*/
                jTCtaConta.setText  (rs.getString("ctaconta"));
                
                /*Coloca la clasificación jerarquica*/
                jTJera.setText      (rs.getString("clasjera"));
                
                /*Coloca el método de pago*/
                jTMetPag.setText    (rs.getString("metpag"));
                
                /*Coloca la cuenta bancaria*/
                jTCta.setText       (rs.getString("cta"));
                
                /*Coloca el banco y la clave bancaría*/
                jTBanc.setText      (rs.getString("banc"));
                jTClavBanc.setText  (rs.getString("clavbanc"));
                
                /*Coloca el tiempo de entrega*/
                jTEntre.setText     (rs.getString("tentre"));
                
                /*Coloca el rubro*/
                jTRub.setText       (rs.getString("rubr"));
                
                /*Coloca el giro y la zona*/
                jTGir.setText       (rs.getString("giro"));
                jTZon.setText       (rs.getString("zon"));
                
                /*Si no es cadena vacia entonces convierte a double el límite de crédito y formatealo*/
                if(sLimCred.compareTo("")!=0)
                {                    
                    double dCant    = Double.parseDouble(sLimCred);                   
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    sLimCred        = n.format(dCant);                    
                }                                                                       
                
                /*Coloca todas las lecturas en los campos de edición*/                
                jTNomb.setText      (rs.getString("nom"));
                jTTel.setText       (rs.getString("tel"));
                jTCall.setText      (rs.getString("calle"));
                jTCol.setText       (rs.getString("col"));
                jTCP.setText        (rs.getString("CP"));
                jTCel.setText       (rs.getString("cel"));
                jTExten.setText     (rs.getString("exten"));
                jTNoInt.setText     (rs.getString("noint"));
                jTLada.setText      (rs.getString("lada"));
                jTNoExt.setText     (rs.getString("noext"));
                jTCiu.setText       (rs.getString("ciu"));
                jTEstad.setText     (rs.getString("estad"));
                jTPai.setText       (rs.getString("pais"));
                jTRFC.setText       (rs.getString("rfc"));
                jTDesc.setText      (rs.getString("descu"));
                jTDCred.setText     (rs.getString("diacred"));
                jTLimiCred.setText  (sLimCred);                                
                jTCo1.setText       (rs.getString("co1"));
                jLCo1.setText       (rs.getString("co1"));
                jTCo2.setText       (rs.getString("co2"));
                jLCo2.setText       (rs.getString("co2"));
                jTCo3.setText       (rs.getString("co3"));
                jLCo3.setText       (rs.getString("co3"));
                jTPag1.setText      (rs.getString("pagweb1"));
                jLPag1.setText      (rs.getString("pagweb1"));
                jTPag2.setText      (rs.getString("pagweb2"));   
                jLPag2.setText      (rs.getString("pagweb2"));
                jTEje.setText       (rs.getString("eje1"));
                jTTelPerl11.setText (rs.getString("telper1"));                                
                jTTelPersol22.setText(rs.getString("telper2"));                                
                jTEje2.setText      (rs.getString("eje2"));
                jTTelPer1.setText   (rs.getString("telper21"));
                jTTelPerso2.setText (rs.getString("telper22"));                                                
                jTAObserv.setText   (rs.getString("observ"));
                jTCodProv.setText   (sProvG);    
                jTClas.setText      (rs.getString("codclas"));    
                jTClasDescrip.setText(rs.getString("descrip"));    
                jTRev.setText       (rs.getString("revis"));    
                jTPag.setText       (rs.getString("pags"));                    
                
                /*Carga todos los Contactos*/
                sContac3        = rs.getString("contac3");
                sContac4        = rs.getString("contac4");
                sContac5        = rs.getString("contac5");
                sContac6        = rs.getString("contac6");
                sContac7        = rs.getString("contac7");
                sContac8        = rs.getString("contac8");
                sContac9        = rs.getString("contac9");
                sContac10       = rs.getString("contac10");

                /*Coloca todos los valores del domicilio*/
                sTelEn          = rs.getString("entel");
                sLadaEn         = rs.getString("enlada");
                sExtEn          = rs.getString("ennoext");
                sCelEn          = rs.getString("encel");
                sTelPer1En      = rs.getString("entelper1");
                sTelPer2En      = rs.getString("entelper2");
                sCallEn         = rs.getString("encalle");
                sColEn          = rs.getString("encol");
                sNoExtEn        = rs.getString("enexten");
                sIntEn          = rs.getString("ennoint");
                sCPEn           = rs.getString("encp");
                sCiuEn          = rs.getString("enciu");
                sEstadEn        = rs.getString("enestad");
                sPaiEn          = rs.getString("enpais");
                sCo1En          = rs.getString("enco1");
                sCo2En          = rs.getString("enco2");
                sCo3En          = rs.getString("enco3");
                
                /*Carga todos los teléfonos de los Contactos*/
                sTelCon3        = rs.getString("telcon3");
                sTelCon4        = rs.getString("telcon4");
                sTelCon5        = rs.getString("telcon5");
                sTelCon6        = rs.getString("telcon6");
                sTelCon7        = rs.getString("telcon7");
                sTelCon8        = rs.getString("telcon8");
                sTelCon9        = rs.getString("telcon9");
                sTelCon10       = rs.getString("telcon10");
                
                /*Carga todos los teléfono persles de los Contactos*/
                sTelPer3        = rs.getString("telper3");
                sTelPer4        = rs.getString("telper4");
                sTelPer5        = rs.getString("telper5");
                sTelPer6        = rs.getString("telper6");
                sTelPer7        = rs.getString("telper7");
                sTelPer8        = rs.getString("telper8");
                sTelPer9        = rs.getString("telper9");
                sTelPer10       = rs.getString("telper10");
                
                /*Carga todos los teléfonos persles segunda parte de los Contactos*/
                sTelPer33       = rs.getString("telper33");
                sTelPer44       = rs.getString("telper44");
                sTelPer55       = rs.getString("telper55");
                sTelPer66       = rs.getString("telper66");
                sTelPer77       = rs.getString("telper77");
                sTelPer88       = rs.getString("telper88");
                sTelPer99       = rs.getString("telper99");
                sTelPer100      = rs.getString("telper100");
                
                /*Marca el radio button para saber si es pers física o moral*/
                if(rs.getString("pers").compareTo("M")==0)
                    jRaMor.setSelected(true);
                else
                    jRaFisi.setSelected(true);
                        
                /*Marca o desmarca si se le puede o no comprar con otra moneda*/
                if(rs.getString("otramon").compareTo("1")==0)
                    jCOtraMon.setSelected(true);
                else
                    jCOtraMon.setSelected(false);
                
                /*Marca o desmarca si esta bloqueado o no*/
                if(rs.getString("bloq").compareTo("1")==0)
                    jCBloq.setSelected(true);
                else
                    jCBloq.setSelected(false);
                
                //Marca o desmarca si esta bloqueado el crédito
                if(rs.getString("bloqlimcred").compareTo("1")==0)
                    jCBloqCred.setSelected(true);
                else
                    jCBloqCred.setSelected(false);
                
                /*Marca o desmarca si se le puede o no ordenar con otra moneda*/
                if(rs.getString("otramonc").compareTo("1")==0)
                    jCOrdOtrMon.setSelected(true);
                else
                    jCOrdOtrMon.setSelected(false);
                
                //Se selecciona la serie que el proveedor tiene
                jComSer.setSelectedItem(sSerG);
            }/*Fin de while (rs.next())*/
            
            /*Guarda el RFC original del proveedorr*/
            sRFCOri = jTRFC.getText();       
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }    
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de public Prov() */
        
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTNomb = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jTEstad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTRFC = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTTel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jTCo1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTPag1 = new javax.swing.JTextField();
        jTEje = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTCo2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTPag2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTTelPerl11 = new javax.swing.JTextField();
        jTTelPer1 = new javax.swing.JTextField();
        jTEje2 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTTelPersol22 = new javax.swing.JTextField();
        jTTelPerso2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTNoInt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTDCred = new javax.swing.JTextField();
        jTLimiCred = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jLPag2 = new javax.swing.JLabel();
        jLPag1 = new javax.swing.JLabel();
        jLCo1 = new javax.swing.JLabel();
        jLCo2 = new javax.swing.JLabel();
        jLCo3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRaFisi = new javax.swing.JRadioButton();
        jRaMor = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        jTClas = new javax.swing.JTextField();
        jBClas = new javax.swing.JButton();
        jTClasDescrip = new javax.swing.JTextField();
        jBContac = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTPag = new javax.swing.JTextField();
        jTRev = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jTCodProv = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jCOtraMon = new javax.swing.JCheckBox();
        jCOrdOtrMon = new javax.swing.JCheckBox();
        jCBloq = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTLada = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTExten = new javax.swing.JTextField();
        jTCel = new javax.swing.JTextField();
        jBDom = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTGir = new javax.swing.JTextField();
        jTZon = new javax.swing.JTextField();
        jBZon = new javax.swing.JButton();
        jBGir = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jTCtaConta = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTRub = new javax.swing.JTextField();
        jBRub = new javax.swing.JButton();
        jTRubDescrip = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTMetPag = new javax.swing.JTextField();
        jTCta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTEntre = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTBanc = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTClavBanc = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jTProv = new javax.swing.JTextField();
        jCBloqCred = new javax.swing.JCheckBox();

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

        jTNomb.setToolTipText("");
        jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomb.setNextFocusableComponent(jTLada);
        jTNomb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNombFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNombFocusLost(evt);
            }
        });
        jTNomb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNombKeyPressed(evt);
            }
        });
        jP1.add(jTNomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 230, 20));

        jLabel2.setText("*Razón social:");
        jLabel2.setToolTipText("Nombre de la Empresa o Nombnre del Proveedor");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jLabel3.setText("*Calle:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTCall);
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
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 230, 20));

        jLabel4.setText("*Colonia:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTNoExt);
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
        });
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 230, 20));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTPai);
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
        });
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 230, 20));

        jLabel5.setText("*Estado:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel6.setText("*País:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTRFC);
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
        });
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 230, 20));

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
        });
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 230, 20));

        jLabel7.setText("*Ciudad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jLabel8.setText("Observaciones:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, -1, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTCiu);
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
        });
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 230, 20));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 110, 30));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, 120, 30));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jTDesc);
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
        jP1.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 230, 20));

        jLabel11.setText("*RFC:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel12.setText("Límite de crédito:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 120, -1));

        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jTDCred);
        jTDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescFocusLost(evt);
            }
        });
        jTDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescKeyTyped(evt);
            }
        });
        jP1.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 230, 20));

        jLabel13.setText("Teléfono:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTExten);
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
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 140, 20));

        jScrollPane1.setNextFocusableComponent(jTClas);

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAObserv.setNextFocusableComponent(jTClas);
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
        jScrollPane1.setViewportView(jTAObserv);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 230, 40));

        jLabel14.setText("Correo1:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.setNextFocusableComponent(jTCo2);
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
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 230, 20));

        jLabel15.setText("Página web1:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, -1, -1));

        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag1.setNextFocusableComponent(jTPag2);
        jTPag1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag1FocusLost(evt);
            }
        });
        jTPag1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag1KeyPressed(evt);
            }
        });
        jP1.add(jTPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 230, 20));

        jTEje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEje.setNextFocusableComponent(jTTelPerl11);
        jTEje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEjeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEjeFocusLost(evt);
            }
        });
        jTEje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEjeKeyPressed(evt);
            }
        });
        jP1.add(jTEje, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 230, 20));

        jLabel17.setText("Correo2:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.setNextFocusableComponent(jTCo3);
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
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 230, 20));

        jLabel18.setText("Correo3:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jTPag1);
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
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 230, 20));

        jLabel19.setText("Página web2:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, -1));

        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag2.setNextFocusableComponent(jTEje);
        jTPag2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag2FocusLost(evt);
            }
        });
        jTPag2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag2KeyPressed(evt);
            }
        });
        jP1.add(jTPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 230, 20));

        jLabel21.setText("Contacto 1:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 120, -1));

        jLabel22.setText("Teléfono personal 1:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 130, -1));

        jLabel23.setText("Teléfono personal 2:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, 130, -1));

        jLabel24.setText("Contacto 2:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 120, -1));

        jTTelPerl11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPerl11.setNextFocusableComponent(jTTelPersol22);
        jTTelPerl11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPerl11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPerl11FocusLost(evt);
            }
        });
        jTTelPerl11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPerl11KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPerl11KeyTyped(evt);
            }
        });
        jP1.add(jTTelPerl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 230, 20));

        jTTelPer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer1.setNextFocusableComponent(jTTelPerso2);
        jTTelPer1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer1FocusLost(evt);
            }
        });
        jTTelPer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPer1KeyTyped(evt);
            }
        });
        jP1.add(jTTelPer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, 230, 20));

        jTEje2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEje2.setNextFocusableComponent(jTTelPer1);
        jTEje2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEje2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEje2FocusLost(evt);
            }
        });
        jTEje2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEje2KeyPressed(evt);
            }
        });
        jP1.add(jTEje2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, 230, 20));

        jLabel25.setText("Teléfono personal 1:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 130, -1));

        jLabel26.setText("Teléfono personal 2:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 130, -1));

        jTTelPersol22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPersol22.setNextFocusableComponent(jTEje2);
        jTTelPersol22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPersol22FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPersol22FocusLost(evt);
            }
        });
        jTTelPersol22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPersol22KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPersol22KeyTyped(evt);
            }
        });
        jP1.add(jTTelPersol22, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 290, 230, 20));

        jTTelPerso2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPerso2.setNextFocusableComponent(jTAObserv);
        jTTelPerso2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPerso2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPerso2FocusLost(evt);
            }
        });
        jTTelPerso2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPerso2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPerso2KeyTyped(evt);
            }
        });
        jP1.add(jTTelPerso2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 230, 20));

        jLabel27.setText("*CP:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel28.setText("No. interior:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 90, -1));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCol);
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
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 230, 20));

        jLabel29.setText("Descuento %:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 110, -1));

        jLabel30.setText("Días de crédito:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 120, -1));

        jTDCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDCred.setNextFocusableComponent(jTLimiCred);
        jTDCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDCredFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDCredFocusLost(evt);
            }
        });
        jTDCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDCredKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDCredKeyTyped(evt);
            }
        });
        jP1.add(jTDCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 230, 20));

        jTLimiCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLimiCred.setNextFocusableComponent(jTMetPag);
        jTLimiCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLimiCredFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLimiCredFocusLost(evt);
            }
        });
        jTLimiCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLimiCredKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLimiCredKeyTyped(evt);
            }
        });
        jP1.add(jTLimiCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 230, 20));

        jLabel9.setText("*No. exterior:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 90, -1));

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
        });
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 230, 20));

        jLPag2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLPag2.setForeground(new java.awt.Color(0, 0, 255));
        jLPag2.setText("-");
        jLPag2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLPag2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLPag2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLPag2MouseExited(evt);
            }
        });
        jP1.add(jLPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 230, -1));

        jLPag1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLPag1.setForeground(new java.awt.Color(0, 0, 255));
        jLPag1.setText("-");
        jLPag1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLPag1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLPag1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLPag1MouseExited(evt);
            }
        });
        jP1.add(jLPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 230, -1));

        jLCo1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo1.setForeground(new java.awt.Color(51, 51, 255));
        jLCo1.setText("-");
        jLCo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo1MouseExited(evt);
            }
        });
        jP1.add(jLCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 230, -1));

        jLCo2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo2.setForeground(new java.awt.Color(51, 51, 255));
        jLCo2.setText("-");
        jLCo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo2MouseExited(evt);
            }
        });
        jP1.add(jLCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 230, -1));

        jLCo3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo3.setForeground(new java.awt.Color(51, 51, 255));
        jLCo3.setText("-");
        jLCo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo3MouseExited(evt);
            }
        });
        jP1.add(jLCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 230, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRaFisi.setBackground(new java.awt.Color(255, 255, 255));
        jRaFisi.setText("Persona física");
        jRaFisi.setNextFocusableComponent(jTNomb);
        jRaFisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaFisiKeyPressed(evt);
            }
        });
        jPanel2.add(jRaFisi, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 120, -1));

        jRaMor.setBackground(new java.awt.Color(255, 255, 255));
        jRaMor.setSelected(true);
        jRaMor.setText("Persona moral");
        jRaMor.setNextFocusableComponent(jRaFisi);
        jRaMor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaMorKeyPressed(evt);
            }
        });
        jPanel2.add(jRaMor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, -1));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 240, 30));

        jLabel32.setText("Clasificación:");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 100, -1));

        jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClas.setNextFocusableComponent(jBClas);
        jTClas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasFocusLost(evt);
            }
        });
        jTClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTClasKeyTyped(evt);
            }
        });
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 100, 20));

        jBClas.setBackground(new java.awt.Color(255, 255, 255));
        jBClas.setText("...");
        jBClas.setToolTipText("Buscar Calsificación(es)");
        jBClas.setNextFocusableComponent(jTRub);
        jBClas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBClasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBClasMouseExited(evt);
            }
        });
        jBClas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBClasActionPerformed(evt);
            }
        });
        jBClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBClasKeyPressed(evt);
            }
        });
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 30, 20));

        jTClasDescrip.setEditable(false);
        jTClasDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClasDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasDescripFocusLost(evt);
            }
        });
        jP1.add(jTClasDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, 100, 20));

        jBContac.setBackground(new java.awt.Color(255, 255, 255));
        jBContac.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBContac.setForeground(new java.awt.Color(0, 102, 0));
        jBContac.setText("Contactos F2");
        jBContac.setNextFocusableComponent(jBDom);
        jBContac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBContacMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBContacMouseExited(evt);
            }
        });
        jBContac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBContacActionPerformed(evt);
            }
        });
        jBContac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBContacKeyPressed(evt);
            }
        });
        jP1.add(jBContac, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 330, 120, -1));

        jLabel33.setText("Revisión:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 100, -1));

        jLabel16.setText("Pagos:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, 100, -1));

        jTPag.setToolTipText("Días en los que se Hacen Pagos de Facturas");
        jTPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag.setNextFocusableComponent(jComSer);
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
        });
        jP1.add(jTPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, 100, 20));

        jTRev.setToolTipText("Días en los que se llevan Documentos  a Contra Recibo");
        jTRev.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRev.setNextFocusableComponent(jTPag);
        jTRev.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRevFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRevFocusLost(evt);
            }
        });
        jTRev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRevKeyPressed(evt);
            }
        });
        jP1.add(jTRev, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 100, 20));

        jLabel35.setText("*Serie:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 170, 100, -1));

        jComSer.setNextFocusableComponent(jTCodProv);
        jComSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFocusLost(evt);
            }
        });
        jComSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerKeyPressed(evt);
            }
        });
        jP1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 170, 100, 20));

        jTCodProv.setBackground(new java.awt.Color(204, 255, 204));
        jTCodProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv.setNextFocusableComponent(jTGir);
        jTCodProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodProvFocusLost(evt);
            }
        });
        jTCodProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodProvKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodProvKeyTyped(evt);
            }
        });
        jP1.add(jTCodProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 100, 20));

        jLabel20.setText("Cod.proveedor:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 190, 100, -1));

        jCOtraMon.setBackground(new java.awt.Color(255, 255, 255));
        jCOtraMon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCOtraMon.setSelected(true);
        jCOtraMon.setText("Se le puede comprar en otra moneda");
        jCOtraMon.setNextFocusableComponent(jCOrdOtrMon);
        jCOtraMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOtraMonKeyPressed(evt);
            }
        });
        jP1.add(jCOtraMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 390, 230, -1));

        jCOrdOtrMon.setBackground(new java.awt.Color(255, 255, 255));
        jCOrdOtrMon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCOrdOtrMon.setSelected(true);
        jCOrdOtrMon.setText("Se le puede ordenar en otra moneda");
        jCOrdOtrMon.setNextFocusableComponent(jRaMor);
        jCOrdOtrMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOrdOtrMonKeyPressed(evt);
            }
        });
        jP1.add(jCOrdOtrMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 230, -1));

        jCBloq.setBackground(new java.awt.Color(255, 255, 255));
        jCBloq.setText("Bloqueado");
        jCBloq.setNextFocusableComponent(jCBloqCred);
        jCBloq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBloqKeyPressed(evt);
            }
        });
        jP1.add(jCBloq, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 120, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 450, 140, 20));

        jLabel31.setText("Lada:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 40, 20));

        jTLada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLada.setNextFocusableComponent(jTTel);
        jTLada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLadaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLadaFocusLost(evt);
            }
        });
        jTLada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLadaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLadaKeyTyped(evt);
            }
        });
        jP1.add(jTLada, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 50, 20));

        jLabel34.setText("Extensión:");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jLabel41.setText("Celular:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, -1));

        jTExten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTExten.setNextFocusableComponent(jTCel);
        jTExten.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTExtenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTExtenFocusLost(evt);
            }
        });
        jTExten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExtenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTExtenKeyTyped(evt);
            }
        });
        jP1.add(jTExten, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 230, 20));

        jTCel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel.setNextFocusableComponent(jTCP);
        jTCel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCelFocusLost(evt);
            }
        });
        jTCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCelKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCelKeyTyped(evt);
            }
        });
        jP1.add(jTCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 230, 20));

        jBDom.setBackground(new java.awt.Color(255, 255, 255));
        jBDom.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDom.setForeground(new java.awt.Color(0, 102, 0));
        jBDom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/doment.png"))); // NOI18N
        jBDom.setText("Entrega");
        jBDom.setToolTipText("Domicilio de Entrega del Proveedor");
        jBDom.setNextFocusableComponent(jCOtraMon);
        jBDom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDomMouseExited(evt);
            }
        });
        jBDom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDomActionPerformed(evt);
            }
        });
        jBDom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDomKeyPressed(evt);
            }
        });
        jP1.add(jBDom, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 350, 120, 30));

        jLabel43.setText("Giro:");
        jP1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 210, 100, -1));

        jLabel40.setText("Zona:");
        jP1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 100, -1));

        jTGir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTGir.setNextFocusableComponent(jBGir);
        jTGir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTGirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTGirFocusLost(evt);
            }
        });
        jTGir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTGirKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTGirKeyTyped(evt);
            }
        });
        jP1.add(jTGir, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 210, 100, 20));

        jTZon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTZon.setNextFocusableComponent(jBZon);
        jTZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTZonFocusLost(evt);
            }
        });
        jTZon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTZonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTZonKeyTyped(evt);
            }
        });
        jP1.add(jTZon, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 230, 100, 20));

        jBZon.setBackground(new java.awt.Color(255, 255, 255));
        jBZon.setText("...");
        jBZon.setToolTipText("Buscar Clasificación(es)");
        jBZon.setNextFocusableComponent(jTEntre);
        jBZon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBZonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBZonMouseExited(evt);
            }
        });
        jBZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBZonActionPerformed(evt);
            }
        });
        jBZon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBZonKeyPressed(evt);
            }
        });
        jP1.add(jBZon, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 230, 30, 20));

        jBGir.setBackground(new java.awt.Color(255, 255, 255));
        jBGir.setText("...");
        jBGir.setToolTipText("Buscar Clasificación(es)");
        jBGir.setNextFocusableComponent(jTZon);
        jBGir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGirMouseExited(evt);
            }
        });
        jBGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGirActionPerformed(evt);
            }
        });
        jBGir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGirKeyPressed(evt);
            }
        });
        jP1.add(jBGir, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 210, 30, 20));

        jLabel44.setText("Cuenta contable:");
        jP1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 130, -1));

        jTCtaConta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaConta.setNextFocusableComponent(jTBanc);
        jTCtaConta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaContaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaContaFocusLost(evt);
            }
        });
        jTCtaConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaContaKeyPressed(evt);
            }
        });
        jP1.add(jTCtaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 230, 20));

        jLabel36.setText("Rubro:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, 100, -1));

        jTRub.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRub.setNextFocusableComponent(jBRub);
        jTRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRubFocusLost(evt);
            }
        });
        jTRub.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRubKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRubKeyTyped(evt);
            }
        });
        jP1.add(jTRub, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 100, 20));

        jBRub.setBackground(new java.awt.Color(255, 255, 255));
        jBRub.setText("...");
        jBRub.setToolTipText("Buscar Calsificación(es)");
        jBRub.setNextFocusableComponent(jTRev);
        jBRub.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRubMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRubMouseExited(evt);
            }
        });
        jBRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRubActionPerformed(evt);
            }
        });
        jBRub.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRubKeyPressed(evt);
            }
        });
        jP1.add(jBRub, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, 30, 20));

        jTRubDescrip.setEditable(false);
        jTRubDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRubDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRubDescripFocusLost(evt);
            }
        });
        jP1.add(jTRubDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 110, 100, 20));

        jLabel37.setText("Método pago:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 130, -1));

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
        jP1.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 230, 20));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jTCtaConta);
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
        jP1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 230, 20));

        jLabel10.setText("Cuenta bancaria:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 130, -1));

        jLabel38.setText("Tiempo entrega:");
        jP1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 250, 100, -1));

        jTEntre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEntre.setNextFocusableComponent(jTJera);
        jTEntre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEntreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEntreFocusLost(evt);
            }
        });
        jTEntre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEntreKeyPressed(evt);
            }
        });
        jP1.add(jTEntre, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 250, 100, 20));

        jLabel46.setText("Banco:");
        jP1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 130, -1));

        jTBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBanc.setNextFocusableComponent(jTClavBanc);
        jTBanc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBancFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBancFocusLost(evt);
            }
        });
        jTBanc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBancKeyPressed(evt);
            }
        });
        jP1.add(jTBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 230, 20));

        jLabel47.setText("Clabe interbancaria:");
        jP1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 130, -1));

        jTClavBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClavBanc.setNextFocusableComponent(jCBloq);
        jTClavBanc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClavBancFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClavBancFocusLost(evt);
            }
        });
        jTClavBanc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClavBancKeyPressed(evt);
            }
        });
        jP1.add(jTClavBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 230, 20));

        jLabel39.setText("Jerárquia:");
        jLabel39.setToolTipText("Depósito en Garantía");
        jP1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 270, 100, -1));

        jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTJera.setNextFocusableComponent(jBJera);
        jTJera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTJeraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTJeraFocusLost(evt);
            }
        });
        jTJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTJeraKeyPressed(evt);
            }
        });
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 270, 100, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar Jerárquia(s)");
        jBJera.setNextFocusableComponent(jBContac);
        jBJera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJeraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJeraMouseExited(evt);
            }
        });
        jBJera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJeraActionPerformed(evt);
            }
        });
        jBJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBJeraKeyPressed(evt);
            }
        });
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 270, 30, 20));

        jLabel42.setText("Cod.proveedor:");
        jP1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 120, -1));

        jTProv.setEditable(false);
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jTCo1);
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
        });
        jP1.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 130, 20));

        jCBloqCred.setBackground(new java.awt.Color(255, 255, 255));
        jCBloqCred.setText("Bloquear crédito");
        jCBloqCred.setNextFocusableComponent(jBGuar);
        jCBloqCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBloqCredKeyPressed(evt);
            }
        });
        jP1.add(jCBloqCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 130, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    /*Método para guardar los cambios de un proveedor*/
    private int iActuProv(Connection con)
    {
        /*Declara variables de bases de datos*/
        ResultSet   rs;
        Statement   st;
        String      sQ;
        
        if(jTNomb.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la razon social es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTNomb.grabFocus();                   
                    return -1;
        }
        //calle existe
        if(jTCall.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la calle es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCall.grabFocus();                   
                    return -1;
        }
        //falta colonia
        if(jTCol.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la colonia es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCol.grabFocus();                   
                    return -1;
        }
        //falta numero de exterior
        if(jTNoExt.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El numero de exterior es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTNoExt.grabFocus();                   
                    return -1;
        }
        //falta numero de cp
        if(jTCP.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El codigo postal es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCP.grabFocus();                   
                    return -1;
        }
        //falta cuidad
        if(jTCiu.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la cuidad es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCiu.grabFocus();                   
                    return -1;
        }
        //falta stado
        if(jTEstad.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El estado es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTEstad.grabFocus();                   
                    return -1;
        }
        //falta pais
        if(jTPai.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El país es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTPai.grabFocus();                   
                    return -1;
        }                  
        
        
        
        /*Si el rfc no es cadena vacia entonces*/
        if(jTRFC.getText().compareTo("")!=0)
        {            
            /*Si es persona moral entonces*/
            Star.bValRFC   = false;       
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario es válido o no*/
                Star.vValRFC(jTRFC.getText(), true, jTRFC, true);                        
                
                /*Si no es válido el RFC entonces*/
                if(!Star.bValRFC)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;
                    
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                    /*Pon el foco del teclado en el campo y regresa error*/
                    jTRFC.grabFocus();                                        
                    return -1;
                }
            }
            /*Else, es física*/
            else
            {
                /*Válida si el rfc introducido por el usuario es válido o no*/
                Star.bValRFC   = false;     
                Star.vValRFC(jTRFC.getText(), false, jTRFC, true);
                        
                /*Si no es válido el RFC entonces*/
                if(!Star.bValRFC)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;

                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Pon el foco del teclado en el campo y regresa error*/
                    jTRFC.grabFocus();                                     
                    return -1;
                }
            }       
        
            /*Comprueba si ya existe ese nombre con el RFC*/
            try
            {
                sQ = "SELECT nom FROM provs WHERE rfc = '" + jTRFC.getText().trim() + "' AND rfc <> '" + sRFCOri + "'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {  
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;
                    
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El proveedor: " + jTNomb.getText() + " ya existe con el RFC: " + jTRFC.getText() + ". Cambia el nombre o el RFC.", "Modificar Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Pon el foco del teclado en el campo de RFC y regresa error*/
                    jTRFC.grabFocus();                   
                    return -1;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                
            }
            
        }/*Fin de if(sRFC.compareTo("")!=0)*/                          
        else
        {   
            JOptionPane.showMessageDialog(null, "El RFC es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTRFC.grabFocus();                   
                    return -1;
        }
        /*Preguntar al usuario si esta seguro de que estan bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar modificación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
                    
            /*Regresa que todo va bien*/
            return 0;           
        }
        
        /*Lee si es pers física o moral*/
        String sPers    = "F";
        if(jRaMor.isSelected())
            sPers       = "M";
        
        /*Le el campo de descu*/
        String sDesc    = jTDesc.getText();
        
        /*Si el descu es cadena vacia ponerlo en 0 para que la base de datosla reciba*/
        if(sDesc.compareTo("")==0)
            sDesc       = "0";

        /*Lee el campo de días de crédito*/
        String sDiaCred = jTDCred.getText();
        
        /*Si el campo es cadena vacia entonces que sea 0*/
        if(sDiaCred.compareTo("")==0)
            sDiaCred    = "0";
        
        /*Lee el campo de límite de crédito*/
        String sLimCred = jTLimiCred.getText();
        
        /*Si el límite de crédito es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sLimCred.compareTo("")==0)
            sLimCred    = "0";
        
        /*Si tiene el signo de dollar el límite de crédito quitáselo*/
        sLimCred        = sLimCred.replace("$", "");
        
        /*Si tiene coma el límite de crédito quitáselo*/
        sLimCred        = sLimCred.replace(",", "");
        
        /*Lee la clasificación del proveedor*/
        String sClas    = jTClas.getText();
        
        /*Si la clasificación no es cadena vacia entonces*/
        if(sClas.compareTo("")!=0)
        {
            /*Si no es una clasificación válida entonces*/
            if(jTClasDescrip.getText().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return -1;

                /*Coloca el borde rojo*/                               
                jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación válida.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el control y regresa error*/
                jTClas.grabFocus();                                
                return -1;
            }
        }

        /*Determina si se le va a poder vender con otra moneda distinta a la nacional*/
        String  sOtrMon;
        if(jCOtraMon.isSelected())
            sOtrMon = "1";
        else
            sOtrMon = "0";
        
        /*Determina si se le va a poder órdenar con otra moneda distinta a la nacional*/
        String  sOtrMonO;
        if(jCOrdOtrMon.isSelected())
            sOtrMonO = "1";
        else
            sOtrMonO = "0";
        
        /*Determina si se le va a estar bloqueado o no*/
        String  sBloq;
        if(jCBloq.isSelected())
            sBloq = "1";
        else
            sBloq = "0";
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return -1;        

        /*Obtiene la clasificación jerarquica*/
        String sJera    = jTJera.getText().trim().replace("'", "''");
        
        /*Si no comienza con clasificaciones entonces agregarselo*/
        if(!sJera.startsWith("Clasificaciones|"))
            sJera       = "Clasificaciones|" + sJera;
        
        //Determina si tiene o no bloqueado el crédito
        String sBloqCred    = "0";
        if(jCBloqCred.isSelected())
            sBloqCred       = "1";
        
        /*Modifica los valores del proveedor en la base de datos*/
        try 
        {            
            sQ = "UPDATE provs SET " + 
                    "nom                = '" + jTNomb.getText().replace("'", "''").trim() + "', " + 
                    "tel                = '" + jTTel.getText().replace("'", "''").trim() + "', " + 
                    "tentre             = '" + jTEntre.getText().replace("'", "''").trim() + "', " + 
                    "entel              = '" + sTelEn.replace("'", "''").trim() + "', " + 
                    "pers               = '" + sPers + "', " + 
                    "bloqlimcred        = " + sBloqCred + ", " + 
                    "exten              = '" + jTExten.getText().replace("'", "''").trim() + "', " + 
                    "rubr               = '" + jTRub.getText().replace("'", "''").trim() + "', " + 
                    "metpag             = '" + jTMetPag.getText().replace("'", "''").trim() + "', " + 
                    "cta                = '" + jTCta.getText().replace("'", "''").trim() + "', " + 
                    "ctaconta           = '" + jTCtaConta.getText().trim() + "', " + 
                    "enexten            = '" + sExtEn.replace("'", "''").trim() + "', " + 
                    "clasjera           = '" + sJera + "', " + 
                    "cel                = '" + jTCel.getText().replace("'", "''").trim() + "', " + 
                    "banc               = '" + jTBanc.getText().replace("'", "''").trim() + "', " + 
                    "clavbanc           = '" + jTClavBanc.getText().replace("'", "''").trim() + "', " + 
                    "encel              = '" + sCelEn.replace("'", "''").trim() + "', " + 
                    "giro               = '" + jTGir.getText().replace("'", "''").trim() + "', " + 
                    "zon                = '" + jTZon.getText().replace("'", "''").trim() + "', " + 
                    "calle              = '" + jTCall.getText().replace("'", "''").trim() + "', " + 
                    "encalle            = '" + sCallEn.replace("'", "''").trim() + "', " + 
                    "lada               = '" + jTLada.getText().replace("'", "''").trim() + "', " + 
                    "enlada             = '" + sLadaEn.replace("'", "''").trim() + "', " + 
                    "col                = '" + jTCol.getText().replace("'", "''").trim() + "', " + 
                    "encol              = '" + sColEn.replace("'", "''").trim() + "', " + 
                    "bloq               =  " + sBloq + ", " + 
                    "otramon            =  " + sOtrMon + ", " + 
                    "otramonc           =  " + sOtrMonO + ", " + 
                    "CP                 = '" + jTCP.getText().replace("'", "''").trim() + "', " + 
                    "encp               = '" + sCPEn.replace("'", "''").trim() + "', " + 
                    "ennoint            = '" + sIntEn.replace("'", "''").trim() + "', " + 
                    "noext              = '" + jTNoExt.getText().replace("'", "''").trim() + "', " + 
                    "ennoext            = '" + sNoExtEn.replace("'", "''").trim() + "', " + 
                    "ciu                = '" + jTCiu.getText().replace("'", "''").trim() + "', " + 
                    "enciu              = '" + sCiuEn.replace("'", "''").trim() + "', " + 
                    "estad              = '" + jTEstad.getText().replace("'", "''").trim() + "', " + 
                    "enestad            = '" + sEstadEn.replace("'", "''").trim() + "', " + 
                    "pais               = '" + jTPai.getText().replace("'", "''").trim() + "', " + 
                    "enpais             = '" + sPaiEn.replace("'", "''").trim() + "', " + 
                    "RFC                = '" + jTRFC.getText().replace("'", "''").trim() + "', " + 
                    "descu              = '" + sDesc + "', " + 
                    "diacred            = '" + sDiaCred + "', " + 
                    "limcred            = '" + sLimCred + "', " + 
                    "co1                = '" + jTCo1.getText().replace("'", "''").trim() + "', " + 
                    "co2                = '" + jTCo2.getText().replace("'", "''").trim() + "', " + 
                    "co3                = '" + jTCo3.getText().replace("'", "''").trim() + "', " + 
                    "enco1              = '" + sCo1En.replace("'", "''").trim() + "', " + 
                    "enco2              = '" + sCo2En.replace("'", "''").trim() + "', " + 
                    "enco3              = '" + sCo3En.replace("'", "''").trim() + "', " + 
                    "pagweb1            = '" + jTPag1.getText().replace("'", "''").trim() + "',  " + 
                    "pagweb2            = '" + jTPag2.getText().replace("'", "''").trim() + "', " + 
                    "telper1            = '" + jTTelPerl11.getText().replace("'", "''").trim() + "', " + 
                    "entelper1          = '" + sTelPer1En.replace("'", "''").trim() + "', " + 
                    "telper2            = '" + jTTelPersol22.getText().replace("'", "''").trim() + "', " + 
                    "entelper2          = '" + sTelPer2En.replace("'", "''").trim() + "', " + 
                    "eje1               = '" + jTEje.getText().replace("'", "''").trim() + "', " + 
                    "eje2               = '" + jTEje2.getText().replace("'", "''").trim() + "', " + 
                    "telper21           = '" + jTTelPer1.getText().replace("'", "''").trim() + "', " + 
                    "telper22           = '" + jTTelPerso2.getText().replace("'", "''").trim() + "', " + 
                    "observ             = '" + jTAObserv.getText().replace("'", "''").trim() + "', " + 
                    "fmod               = now(), " + 
                    "estac              = '" + Login.sUsrG.replace("'", "''").trim() + "', " + 
                    "codclas            = '" + sClas.replace("'", "''").trim() + "',  " + 
                    "contac3            = '" + sContac3.replace("'", "''").trim() + "', " + 
                    "contac4            = '" + sContac4.replace("'", "''").trim() + "', " + 
                    "contac5            = '" + sContac5.replace("'", "''").trim() + "', " + 
                    "contac6            = '" + sContac6.replace("'", "''").trim() + "', " + 
                    "contac7            = '" + sContac7.replace("'", "''").trim() + "', " + 
                    "contac8            = '" + sContac8.replace("'", "''").trim() + "', " + 
                    "contac9            = '" + sContac9.replace("'", "''").trim() + "', " + 
                    "contac10           = '" + sContac10.replace("'", "''").trim() + "', " + 
                    "telcon3            = '" + sTelCon3.replace("'", "''").trim() + "', " + 
                    "telcon4            = '" + sTelCon4.replace("'", "''").trim() + "', " + 
                    "telcon5            = '" + sTelCon5.replace("'", "''").trim() + "', " + 
                    "telcon6            = '" + sTelCon6.replace("'", "''").trim() + "', " + 
                    "telcon7            = '" + sTelCon7.replace("'", "''").trim() + "', " + 
                    "telcon8            = '" + sTelCon8.replace("'", "''").trim() + "', " + 
                    "telcon9            = '" + sTelCon9.replace("'", "''").trim() + "', " + 
                    "telcon10           = '" + sTelCon10.replace("'", "''").trim() + "', " + 
                    "telper3            = '" + sTelPer3.replace("'", "''").trim() + "', " + 
                    "telper4            = '" + sTelPer4.replace("'", "''").trim() + "', " + 
                    "telper5            = '" + sTelPer5.replace("'", "''").trim() + "', " + 
                    "telper6            = '" + sTelPer6.replace("'", "''").trim() + "', " + 
                    "telper7            = '" + sTelPer7.replace("'", "''").trim() + "', " + 
                    "telper8            = '" + sTelPer8.replace("'", "''").trim() + "', " + 
                    "telper9            = '" + sTelPer9.replace("'", "''").trim() + "', " + 
                    "telper10           = '" + sTelPer10.replace("'", "''").trim() + "', " + 
                    "telper33           = '" + sTelPer33.replace("'", "''").trim() + "', " + 
                    "telper44           = '" + sTelPer44.replace("'", "''").trim() + "', " + 
                    "telper55           = '" + sTelPer55.replace("'", "''").trim() + "', " + 
                    "telper66           = '" + sTelPer66.replace("'", "''").trim() + "', " + 
                    "telper77           = '" + sTelPer77.replace("'", "''").trim() + "', " + 
                    "telper88           = '" + sTelPer88.replace("'", "''").trim() + "', " + 
                    "telper99           = '" + sTelPer99.replace("'", "''").trim() + "', " + 
                    "telper100          = '" + sTelPer100.replace("'", "''").trim() + "', " + 
                    "sucu               = '" + Star.sSucu.replace("'", "''").trim() + "', " +
                    "noCaj              = '" + Star.sNoCaj.replace("'", "''").trim() + "', " +
                    "revis              = '" + jTRev.getText().replace("'", "''").trim() + "', " + 
                    "pags               = '" + jTPag.getText().replace("'", "''").trim() + "' WHERE CONCAT_WS('', ser, prov) = '" + sProvG + "'";       
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;                                                                                                                             
         }
        
        /*Comprueba si la clasificación jerarquica ya existe en la base de datos*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT rut FROM clasjeraprov WHERE rut = '" + jTJera.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;                                                                                                                             
        }
        
        /*Si la jerarquía no existe entonces*/
        if(!bSi)
        {
            /*Inserta la nueva clasificación de jerarquía*/
            try 
            {                
                sQ = "INSERT INTO clasjeraprov( clas,   rut,                                                                           estac,                                         sucu,                                    nocaj) " + 
                                        "VALUES('',     'Clasificaciones|" + jTJera.getText().replace("'", "''").trim() + "', '" +     Login.sUsrG.replace("'", "''") +"',  '" +      Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return -1;                                                                                                                       
            }            
        }
        
        /*Inserta en log de proveedores*/
        try 
        {            
            sQ = "INSERT INTO logprovs(     prov,                   nom,                                                ser,                             accio,       estac,                                         sucu,                                           nocaj,                                          falt) " + 
                              "VALUES('" +  sProvG + "','" +        jTNomb.getText().replace("'", "''") + "',  '" +     sSerG.replace("'", "''") + "',   'M', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;                                                                                                                                                   
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return -1;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Proveedor: " + jTNomb.getText() + " modificado con éxito.", "Éxito al modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        /*Cierra el formaulario*/
        this.dispose();
        Star.gProv  = null;
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Regresa que todo fue bien*/
        return 0;
                
    }/*Fin de private int iActuProv(Connection con)*/
    
    
    /*Método para generar un nuevo proveedor*/
    private int iNewProv(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;          
        String      sQ; 
        //correcion para que no se pueda poner 2 veses el mismo codigo
        System.out.println("yep");
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        { 
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            JOptionPane.showMessageDialog(null, "La serie del proveedor es obligatoria.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jComSer.grabFocus();
            return-1;
        }           
        if(jComSer.getSelectedItem().toString().compareTo("")!=0&&jTCodProv.getText().compareTo("")!=0)
        {
           
            try
            {
                sQ = "SELECT * FROM provs WHERE ser = '" + jComSer.getSelectedItem().toString()+ "' and prov = '"+jTCodProv.getText()+"'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(rs.next())
                {
                    /*Coloca el borde rojo*/                               
                    jTCodProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El codigo del proveedor: " + jComSer.getSelectedItem().toString()+jTCodProv.getText() + " ya existe no se puede tener 2 codigos iguales.", "Codigo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTCodProv.grabFocus();
                    return-1;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return-1;
            }
        }
        
        if(jTNomb.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la razon social es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTNomb.grabFocus();                   
                    return -1;
        }
        //calle existe
        if(jTCall.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la calle es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCall.grabFocus();                   
                    return -1;
        }
        //falta colonia
        if(jTCol.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la colonia es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCol.grabFocus();                   
                    return -1;
        }
        //falta numero de exterior
        if(jTNoExt.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El numero de exterior es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTNoExt.grabFocus();                   
                    return -1;
        }
        //falta numero de cp
        if(jTCP.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El codigo postal es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCP.grabFocus();                   
                    return -1;
        }
        //falta cuidad
        if(jTCiu.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la cuidad es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCiu.grabFocus();                   
                    return -1;
        }
        //falta stado
        if(jTEstad.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El estado es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTEstad.grabFocus();                   
                    return -1;
        }
        //falta pais
        if(jTPai.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "El país es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTPai.grabFocus();                   
                    return -1;
        }                  
        
        /*Si el rfc no es cadena vacia entonces*/
        if(jTRFC.getText().compareTo("") != 0)
        {
            /*Si es pers moral entonces*/
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario es válido o no*/
                Star.bValRFC   = false;
                Star.vValRFC(jTRFC.getText(), true, jTRFC, true);

                /*Si no es válido entonces*/
                if(!Star.bValRFC)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;

                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Coloca el foco del teclado en el campo del rfc y regresa error*/
                    jTRFC.grabFocus();                    
                    return -1;
                }                
            }    
            /*Else, es física*/
            else
            {
                /*Válida si el rfc introducido por el usuario es válido o no*/
                Star.bValRFC   = false;
                Star.vValRFC(jTRFC.getText(), false, jTRFC, true);

                /*Si no es válido entonces*/
                if(!Star.bValRFC)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;

                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Coloca el foco del teclado en el campo del rfc y regresa error*/
                    jTRFC.grabFocus();                
                    return -1;
                }
            }
            
            /*Comprueba que el RFC no exista ya en la base de datos para ese nombre, si es cadena vacia no hacer nada*/
            try
            {
                sQ = "SELECT nom FROM provs WHERE rfc = '" + jTRFC.getText() + "' AND nom = '" + jTNomb.getText() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;

                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El proveedor: " + jTNomb.getText() + " ya existe con el RFC: " + jTRFC.getText() + ". Cambia el nombre o el RFC.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Pon el foco del teclado en el campo de RFC y regresa error*/
                    jTRFC.grabFocus();               
                    return -1;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return -1;                                                                                                                                       
            }           
            
        }/*Fin de if(sRFC.compareTo("") != 0)*/ 
        else
        {   
            JOptionPane.showMessageDialog(null, "El RFC es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTRFC.grabFocus();                   
                    return -1;
        }
        /*Si no se selecciono ser entonces la serie va a ser la vacia, caso contrario obtiene la ser*/
        String sSer;
        if(jComSer.getSelectedIndex()== -1)
            sSer    = "";
        else
            sSer    = jComSer.getSelectedItem().toString();
            
        /*Si no selecciono una ser y tampoco un código de proveedor entonces*/
        if(jTCodProv.getText().compareTo("")==0 && sSer.compareTo("")==0)        
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
            
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Escoge una serie o un código de proveedor.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el combobox de series y regresa error*/
            jComSer.grabFocus();
            return -1;
        }
        
        /*Preguntar al usuario si esta seguro de que estan bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar nuevo proveedor", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
            
            /*Regresa que toda va bien*/
            return 0;
        }
        
        /*Lee si es pers física o moral*/
        String sPers    = "F";
        if(jRaMor.isSelected())
            sPers       = "M";
        
        /*Lee el campo de descuento*/        
        String sDesc    = jTDesc.getText();
        
        /*Si el descu es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sDesc.compareTo("")==0)
            sDesc       = "0";
        
        /*Lee el campo de días de crédito*/
        String sDiaCred = jTDCred.getText();
        
        /*Si es cadena vacia que sea 0*/
        if(sDiaCred.compareTo("")==0)
            sDiaCred    = "0";
        
        /*Lee el campo de límite de crédito*/
        String sLimCred = jTLimiCred.getText().replace("$", "").replace(",", "");
        
        /*Si el límite de crédito es cadena vacia ponerlo en 0 para que la base de datosla reciba*/
        if(sLimCred.compareTo("")==0)
            sLimCred    = "0";
        
        /*Si la clasificación no es cadena vacia entonces*/
        if(jTClas.getText().compareTo("")!=0)
        {
            /*Si no es una clasificación válida entonces*/
            if(jTClasDescrip.getText().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return -1;

                /*Coloca el borde rojo*/                               
                jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
    
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación válida.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el control y regresa error*/
                jTClas.grabFocus();                                
                return -1;
            }
        }
        
        /*Determina si se le va a poder comprar con otra moneda distinta a la nacional*/
        String  sOtrMon;
        if(jCOtraMon.isSelected())
            sOtrMon = "1";
        else
            sOtrMon = "0";
        
        /*Determina si se le va a poder órdenar con otra moneda distinta a la nacional*/
        String  sOtrMonO;
        if(jCOtraMon.isSelected())
            sOtrMonO = "1";
        else
            sOtrMonO = "0";
        
        /*Determina si se le va a estar bloqueado o no*/
        String  sBloq;
        if(jCBloq.isSelected())
            sBloq = "1";
        else
            sBloq = "0";
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return -1;

        /*Contiene el consecutivo y la serie del proveedor*/
        String sConsecProv      = "";
        String sSerProv         = "";
        
        /*Obtiene el consecutivo del proveedor y la ser*/                            
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = 'PROV' AND ser = '" + sSer + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {               
                sConsecProv          = rs.getString("consec");                   
                sSerProv             = rs.getString("ser");                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                                
        }            
        
        /*Genera el código del proveedor ya sea el consecutivo o el perslizado*/        
        String sCodProv     = jTCodProv.getText();
        if(jTCodProv.getText().compareTo("")==0)        
            sCodProv        = sConsecProv;         
                
        /*Aumenta uno el consecutivo del proveedor*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'PROV' AND ser = '" + sSerProv.replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                        
        }                                    
        
        //Determina si tiene o no bloqueado el crédito
        String sBloqCred    = "0";
        if(jCBloqCred.isSelected())
            sBloqCred       = "1";
        
        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO provs(nom,                                            tel,                                                calle,                                          col,                                            cp,                                             noint,                                              noext,                                              ciu,                                                estad,                                          pais,                                       rfc,                                            descu,              diacred,            limcred,            co1,                                            co2,                                            co3,                                            pagweb1,                                        pagweb2,                                            telper1,                                            telper2,                                                eje1,                                           eje2,                                           telper21,                                               telper22,                                               observ,                                             prov,                               falt,       estac,                                      pers,                               codclas,                                            contac3,                                telcon3,                                telper3,                                telper33,                               contac4,                                telcon4,                                telper4,                                telper44,                               contac5,                                telcon5,                                telper5,                                telper55,                               contac6,                                telcon6,                                telper6,                                telper66,                               contac7,                                telcon7,                                telper7,                                telper77,                               contac8,                                telcon8,                                telper8,                                telper88,                               contac9,                                telcon9,                                telper9,                                telper99,                               contac10,                               telcon10,                               telper10,                               telper100,                                  revis,                                          pags,                                           sucu,                                     nocaj,                                        ser,                                    otramon,         otramonc,         bloq,            lada,                                           exten,                                              cel,                                            entel,                                  enlada,                                 enexten,                                encel,                                  entelper1,                                  entelper2,                                  encalle,                                encol,                                  ennoext,                                ennoint,                                encp,                                   enciu,                                  enestad,                                enpais,                                 enco1,                                  enco2,                                  enco3,                               giro,                                                  zon,                                                 ctaconta,                              rubr,                                                metpag,                                                 cta,                                                 tentre,                                                banc,                                                 clavbanc,                                                 clasjera,                                              bloqlimcred) " + 
                       "VALUES('" + jTNomb.getText().replace("'", "''") + "','" +   jTTel.getText().replace("'", "''") + "','" +        jTCP.getText().replace("'", "''") + "','" +     jTCol.getText().replace("'", "''") + "','" +    jTCol.getText().replace("'", "''") + "','" +    jTNoInt.getText().replace("'", "''") + "','" +      jTNoExt.getText().replace("'", "''") + "','" +      jTCiu.getText().replace("'", "''") + "','" +        jTEstad.getText().replace("'", "''") + "','" +  jTPai.getText().replace("'", "''") + "','" +jTRFC.getText().replace("'", "''") + "','" +    sDesc + "','" +     sDiaCred + "','" +  sLimCred + "','" +  jTCo1.getText().replace("'", "''") + "','" +    jTCo2.getText().replace("'", "''") + "','" +    jTCo3.getText().replace("'", "''") + "','" +    jTPag1.getText().replace("'", "''") + "','" +   jTPag2.getText().replace("'", "''") + "','" +       jTTelPer1.getText().replace("'", "''") + "','" +    jTTelPerl11.getText().replace("'", "''") + "','" +      jTEje.getText().replace("'", "''") + "','" +    jTEje2.getText().replace("'", "''") + "','" +   jTTelPerso2.getText().replace("'", "''") + "','" +      jTTelPersol22.getText().replace("'", "''") + "','" +    jTAObserv.getText().replace("'", "''") +  "','" +   sCodProv.replace("'", "''") + "',   now(), '" + Login.sUsrG.replace("'", "''") + "', '" +   sPers.replace("'", "''") + "', '" + jTClas.getText().replace("'", "''") + "', '" +      sContac3.replace("'", "''") + "', '" +  sTelCon3.replace("'", "''") + "', '" +  sTelPer3.replace("'", "''") + "', '" +  sTelPer33.replace("'", "''") + "', '" + sContac4.replace("'", "''") + "', '" +  sTelCon4.replace("'", "''") + "', '" +  sTelPer4.replace("'", "''") + "', '" +  sTelPer44.replace("'", "''") + "', '" + sContac5.replace("'", "''") + "', '" +  sTelCon5.replace("'", "''") + "', '" +  sTelPer5.replace("'", "''") + "', '" +  sTelPer55.replace("'", "''") + "', '" + sContac6.replace("'", "''") + "', '" +  sTelCon6.replace("'", "''") + "', '" +  sTelPer6.replace("'", "''") + "', '" +  sTelPer66.replace("'", "''") + "', '" + sContac7.replace("'", "''") + "', '" +  sTelCon7.replace("'", "''") + "', '" +  sTelPer7.replace("'", "''") + "', '" +  sTelPer77.replace("'", "''") + "', '" + sContac8.replace("'", "''") + "', '" +  sTelCon8.replace("'", "''") + "', '" +  sTelPer8.replace("'", "''") + "', '" +  sTelPer88.replace("'", "''") + "', '" + sContac9.replace("'", "''") + "', '" +  sTelCon9.replace("'", "''") + "', '" +  sTelPer9.replace("'", "''") + "', '" +  sTelPer99.replace("'", "''") + "', '" + sContac10.replace("'", "''") + "', '" + sTelCon10.replace("'", "''") + "','" +  sTelPer10.replace("'", "''") + "', '" + sTelPer100.replace("'", "''") + "', '" +    jTRev.getText().replace("'", "''") + "', '" +   jTPag.getText().replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "','" +      sSerProv.replace("'", "''") + "', " +   sOtrMon + ", " + sOtrMonO + ", " + sBloq + ", '" +  jTLada.getText().replace("'", "''") + "', '" +  jTExten.getText().replace("'", "''") + "', '" +     jTCel.getText().replace("'", "''") + "', '" +   sTelEn.replace("'", "''") + "', '" +   sLadaEn.replace("'", "''") + "', '" +    sExtEn.replace("'", "''") + "', '" +    sCelEn.replace("'", "''") + "', '" +    sTelPer1En.replace("'", "''") + "', '" +   sTelPer2En.replace("'", "''") + "', '" +     sCallEn.replace("'", "''") + "', '" +   sColEn.replace("'", "''") + "', '" +    sNoExtEn.replace("'", "''") + "', '" +  sIntEn.replace("'", "''") + "', '" +    sCPEn.replace("'", "''") + "', '" +     sCiuEn.replace("'", "''") + "', '" +    sEstadEn.replace("'", "''") + "', '" +  sPaiEn.replace("'", "''") + "', '" +    sCo1En.replace("'", "''") + "', '" +    sCo2En.replace("'", "''") + "', '" +    sCo3En.replace("'", "''") + "', '" + jTGir.getText().replace("'", "''").trim() + "', '" +   jTZon.getText().replace("'", "''").trim() + "', '" + jTCtaConta.getText().trim() + "', '" + jTRub.getText().replace("'", "''").trim() + "', '" + jTMetPag.getText().replace("'", "''").trim() + "', '" + jTCta.getText().replace("'", "''").trim() + "', '" + jTEntre.getText().replace("'", "''").trim() + "', '" + jTBanc.getText().trim().replace("'", "''") + "', '" + jTClavBanc.getText().trim().replace("'", "''") + "', '" + jTJera.getText().trim().replace("'", "''") + "', " +   sBloqCred + ")";                                                          
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                        
        }
        
        /*Comprueba si la clasificación jerarquica ya existe en la base de datos*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT rut FROM clasjeraprov WHERE rut = '" + jTJera.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;            
        }
        
        /*Si la jerarquía no existe entonces*/
        if(!bSi)
        {
            /*Inserta la nueva clasificación de jerarquía*/
            try 
            {                
                sQ = "INSERT INTO clasjeraprov( clas,   rut,                                                                            estac,                                         sucu,                                    nocaj) " + 
                                        "VALUES('',     'Clasificaciones|" + jTJera.getText().replace("'", "''").trim() + "', '" +      Login.sUsrG.replace("'", "''") +"',  '" +      Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return -1;                
            }            
        }
        
        /*Inserta en log de proveedores*/
        try 
        {            
            sQ = "INSERT INTO logprovs(     prov,                                                                   nom,                                                ser,                                accio,       estac,                                         sucu,                                           nocaj,                                      falt) " + 
                              "VALUES('" +  sSerProv.replace("'", "''") + sCodProv.replace("'", "''") + "','" +     jTNomb.getText().replace("'", "''") + "',  '" +     sSerProv.replace("'", "''") + "',   'A', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                       
        }

        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return -1;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Proveedor: " + jTNomb.getText() + " guardado con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Sal del formulario*/
        this.dispose();
        Star.gProv  = null;
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iNewProv(Connection con)*/
    
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Si el campo de nombre esta vacio no puede seguir*/
        if(jTNomb.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "campo de Razón social.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción de nom y regresa error*/
            jTNomb.grabFocus();
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si el giro existe en caso de que alla escrito uno*/
        if(jTGir.getText().compareTo("")!=0)
        {
            try
            {
                sQ = "SELECT gir FROM giro WHERE gir = '" + jTGir.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    /*Coloca el borde rojo*/                               
                    jTGir.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El Giro: " + jTGir.getText() + " no existe.", "Giros", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTGir.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                
            }
            
        }/*Fin de if(jTGir.getText().compareTo("")!=0)*/                      
        
        /*Comprueba si el rubro existe en caso de que alla escrito uno*/
        if(jTRub.getText().compareTo("")!=0)
        {
            try
            {
                sQ = "SELECT cod FROM rubr WHERE cod = '" + jTRub.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    /*Coloca el borde rojo*/                               
                    jTRub.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El rubro: " + jTRub.getText() + " no existe.", "Rubros", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTRub.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                
            }
            
        }/*Fin de if(jTRub.getText().compareTo("")!=0)*/                      
        
        /*Comprueba si la zona existe en caso de que alla escrito uno*/
        if(jTZon.getText().compareTo("")!=0)
        {
            try
            {
                sQ = "SELECT cod FROM zona WHERE cod = '" + jTZon.getText().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    /*Coloca el borde rojo*/                               
                    jTZon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "La zona: " + jTZon.getText() + " no existe.", "Zonas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTZon.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                
            }
            
        }/*Fin de if(jTZon.getText().compareTo("")!=0)*/                      
        
        /*Determina si va a guardar o actualizar*/
        if(jTProv.getText().trim().compareTo("")==0)
        {
            if(iNewProv(con)==-1)
                return;
        }                
        else
        {
            if(iActuProv(con)==-1)
                return;
        }
        
        /*Cierra la base de datos*/
        try           
        {
            con.close();
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;            
        }

        /*Obtiene todos los proveedores de la base de datos y cargalos en la tabla*/
        Star.vCargProvS(jTabProv, "");
        
    }//GEN-LAST:event_jBGuarActionPerformed
            
    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usuario si esta seguro de abandonar la alta del cliente*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la alta del proveedor?", "Salir Alta Proveedor", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura*/
            System.gc();
            
            /*Cierra la forma*/
            dispose();
            Star.gProv  = null;
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de nom del cliente*/
    private void jTNombKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNombKeyPressed

    
    /*Cuando se presiona un botón en el campo de edición calle*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de col*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de cp*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de ciu*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de estad*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de pais*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de RFC*/
    private void jTRFCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRFCKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
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

    
    /*Cuando se presiona una tecla en el campo de edición de descu*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de co1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de página web1*/
    private void jTPag1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPag1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de ejecutivo*/
    private void jTEjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEjeKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTEjeKeyPressed

    
    /*Cuando se presiona una tecla en el campo de co2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de co3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el campo de pagina web2*/
    private void jTPag2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPag2KeyPressed

    
    /*Cuando se presiona una tecla en nu provs*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed

        /*Si se presiona la tecla de tabulador entonces brincar al próximo control que puede tomar
         el foco del teclado*/
        if(evt.getKeyCode() == KeyEvent.VK_TAB) 
        {
            /*Mueve el foco del teclado al próximo control focusable y regresa*/
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.focusNextComponent();
            return;            
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de nom*/
    private void jTNombFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomb.setSelectionStart(0);jTNomb.setSelectionEnd(jTNomb.getText().length());        
        
    }//GEN-LAST:event_jTNombFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de calle*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de col*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de CP*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());        
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de estad*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de pais*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());        
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de descu*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());                
        
    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de correo 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());        
        
    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de correo 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());        
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de correo 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());        
        
    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web 1*/
    private void jTPag1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusGained
            
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag1.setSelectionStart(0);jTPag1.setSelectionEnd(jTPag1.getText().length());        
        
    }//GEN-LAST:event_jTPag1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web 2*/
    private void jTPag2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag2.setSelectionStart(0);jTPag2.setSelectionEnd(jTPag2.getText().length());        
        
    }//GEN-LAST:event_jTPag2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de página ejecutivo*/
    private void jTEjeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEjeFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEje.setSelectionStart(0);jTEje.setSelectionEnd(jTEje.getText().length());                
        
    }//GEN-LAST:event_jTEjeFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de área de observ*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
               
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de tel persl 1*/
    private void jTTelPerl11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPerl11FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPerl11.setSelectionStart(0);jTTelPerl11.setSelectionEnd(jTTelPerl11.getText().length());                
        
    }//GEN-LAST:event_jTTelPerl11FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tel persl 2*/
    private void jTTelPersol22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPersol22FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPersol22.setSelectionStart(0);jTTelPersol22.setSelectionEnd(jTTelPersol22.getText().length());                
        
    }//GEN-LAST:event_jTTelPersol22FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de ejecutivo 2*/
    private void jTEje2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEje2.setSelectionStart(0);jTEje2.setSelectionEnd(jTEje2.getText().length());                
        
    }//GEN-LAST:event_jTEje2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tel persl 1*/
    private void jTTelPer1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer1.setSelectionStart(0);jTTelPer1.setSelectionEnd(jTTelPer1.getText().length());        
        
    }//GEN-LAST:event_jTTelPer1FocusGained

    
    /*Cuando se gana el foco del teclado en tel persl 2*/
    private void jTTelPerso2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPerso2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPerso2.setSelectionStart(0);jTTelPerso2.setSelectionEnd(jTTelPerso2.getText().length());                
        
    }//GEN-LAST:event_jTTelPerso2FocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de tel persl 1*/
    private void jTTelPerl11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPerl11KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPerl11KeyPressed

    
    /*Cuando se presiona una tecla en el campo de tel persl 2*/
    private void jTTelPersol22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPersol22KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPersol22KeyPressed

    
    /*Cuando se presiona una tecla en el campo de ejecutivo 2*/
    private void jTEje2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEje2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEje2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de tel persl 1*/
    private void jTTelPer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de tel persl 2*/
    private void jTTelPerso2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPerso2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPerso2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoIntKeyPressed

    
    
    /*Cuando se presiona una tecla en el campo de días de crédito*/
    private void jTDCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDCredKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDCredKeyPressed

    
    /*Cuando se presiona una tecla en el campo de límite de crédito*/
    private void jTLimiCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLimiCredKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLimiCredKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
                
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de dis de crédito*/
    private void jTDCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDCredFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDCred.setSelectionStart(0);jTDCred.setSelectionEnd(jTDCred.getText().length());                
        
    }//GEN-LAST:event_jTDCredFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de límite de crédito*/
    private void jTLimiCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLimiCredFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTLimiCred.setSelectionStart(0);jTLimiCred.setSelectionEnd(jTLimiCred.getText().length());                
        
    }//GEN-LAST:event_jTLimiCredFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de nom*/
    private void jTNombFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNomb.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNomb.getText().compareTo("")!=0)
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNomb.getText().length()> 255)
            jTNomb.setText(jTNomb.getText().substring(0, 255));
              
    }//GEN-LAST:event_jTNombFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de límite de crédito*/
    private void jTLimiCredFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLimiCredFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLimiCred.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTLimiCred.getText().replace(",", "").replace("$", "");
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sTex.length()> 30)        
            jTLimiCred.setText(jTLimiCred.getText().substring(0, 30));        
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException expnNumForm)  
        {              
            jTLimiCred.setText("");
            return;
        }  
                        
        /*Conviertelo a double*/
        double dCant    = Double.parseDouble(sTex);
        
        /*Formatealo*/
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTex            = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTLimiCred.setText(sTex);                               
        
    }//GEN-LAST:event_jTLimiCredFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel.getText().length()> 255)
            jTTel.setText(jTTel.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclaodo en el campo de calle*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCP.getText().length()> 255)
            jTCP.setText(jTCP.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de col*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de CP*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoInt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de número exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoExt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCiu.getText().length()> 255)
            jTCiu.setText(jTCiu.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de estad*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEstad.getText().length()> 255)
            jTEstad.setText(jTEstad.getText().substring(0, 255));
                
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de pais*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPai.getText().length()> 255)
            jTPai.setText(jTPai.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuandose pierde el foco del teclado en el campo de RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRFC.getText().compareTo("")!=0)
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Coloca el caret en la posiciòn 0*/
        jTRFC.setCaretPosition(0);
        
        /*Si es cadena vacia entonces solo regresar*/
        if(jTRFC.getText().compareTo("")==0)
            return;
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTRFC.getText().length()> 21)        
            jTRFC.setText(jTRFC.getText().substring(0, 21));        
        
        /*Convierte los caes escritos en mayúsculas*/
        String sLec = jTRFC.getText().toUpperCase();
        
        /*Colocalos en el campo*/
        jTRFC.setText(sLec);   
        
        /*Si es pers moral entonces validalo, caso contrario también*/
        if(jRaMor.isSelected())
            Star.vValRFC(sLec, true, jTRFC, true);
        else
            Star.vValRFC(sLec, false, jTRFC, true);                        
        
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de descu*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDesc.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTDesc.getText().length()> 30)
            jTDesc.setText(jTDesc.getText().substring(0, 30));
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTDesc.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTDesc.setText("");
        }                                        
        
    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de días de crédito*/
    private void jTDCredFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDCredFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDCred.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTDCred.getText().length()> 21)
            jTDCred.setText(jTDCred.getText().substring(0, 21));
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTDCred.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTDCred.setText("");
        }                 
              
    }//GEN-LAST:event_jTDCredFocusLost

    
    /*Cuando se pierde el foco del teclado en el caampo de co1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo1.getText().toLowerCase().contains("@") || !jTCo1.getText().toLowerCase().contains(".")) && jTCo1.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));
                
        /*Coloca en el label de correo 1 el correo que el usuario escribió*/
        jLCo1.setText(jTCo1.getText());               
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo1.getText().compareTo("")==0)
            jLCo1.setText("-");
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de co2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo2.getText().toLowerCase().contains("@") || !jTCo2.getText().toLowerCase().contains(".")) && jTCo2.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));
                
        /*Coloca en el label de correo 2 el correo que el usuario escribió*/
        jLCo2.setText(jTCo2.getText());               
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo2.getText().compareTo("")==0)
            jLCo2.setText("-");
        
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de co3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo3.getText().toLowerCase().contains("@") || !jTCo3.getText().toLowerCase().contains(".")) && jTCo3.getText().toLowerCase().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        jTCo3.setText(jTCo3.getText().substring(0, 100));
                
        /*Coloca en el label de correo 3 el correo que el usuario escribió*/
        jLCo3.setText(jTCo3.getText());
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo3.getText().compareTo("")==0)
            jLCo3.setText("-");
                
    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de p+agina web 1*/
    private void jTPag1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida que la página sea válido*/
        if((!jTPag1.getText().toLowerCase().contains("www") || !jTPag1.getText().toLowerCase().toLowerCase().contains(".")) && jTPag1.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPag1.getText().length()> 255)
            jTPag1.setText(jTPag1.getText().substring(0, 255));
                
        /*Coloca en el label de página web 1 la página que el usuario escribió*/
        jLPag1.setText(jTPag1.getText());              
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTPag1.getText().compareTo("")==0)
            jLPag1.setText("-");
        
    }//GEN-LAST:event_jTPag1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de página web 2*/
    private void jTPag2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTPag2.getText().toLowerCase().contains("www") || !jTPag2.getText().toLowerCase().contains(".")) && jTPag2.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPag2.getText().length()> 255)
            jTPag2.setText(jTPag2.getText().substring(0, 255));
                
        /*Coloca en el label de página web 2 la página que el usuario escribió*/
        jLPag2.setText(jTPag2.getText());
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTPag2.getText().compareTo("")==0)
            jLPag2.setText("-");
        
    }//GEN-LAST:event_jTPag2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de observ*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAObserv.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTAObserv.getText().length()> 255)
            jTAObserv.setText(jTAObserv.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAObservFocusLost
    
    
    /*Cuando se pierde el foco del teclado en el campo de ejecutivo 1*/
    private void jTEjeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEjeFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEje.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEje.getText().length()> 255)
            jTEje.setText(jTEje.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEjeFocusLost


    /*Cuando se pierde el foco del teclado en el campo del ejecutivo 2*/
    private void jTEje2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEje2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEje2.getText().length()> 255)
            jTEje2.setText(jTEje2.getText().substring(0, 255));
                
    }//GEN-LAST:event_jTEje2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tel persl 11*/
    private void jTTelPerl11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPerl11FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTelPerl11.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPerl11.getText().length()> 255)
            jTTelPerl11.setText(jTTelPerl11.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelPerl11FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tel persl 22*/
    private void jTTelPersol22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPersol22FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTelPersol22.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPersol22.getText().length()> 255)
            jTTelPersol22.setText(jTTelPersol22.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelPersol22FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tel persl 1*/
    private void jTTelPer1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTelPer1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPer1.getText().length()> 255)
            jTTelPer1.setText(jTTelPer1.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelPer1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tel persl 2*/
    private void jTTelPerso2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPerso2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTelPerso2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPerso2.getText().length()> 255)
            jTTelPerso2.setText(jTTelPerso2.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTTelPerso2FocusLost

    
    /*Cuando se presiona una tecla typed en el campo de descu*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
                
    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se presiona una tecla typed en el campo de días de crédito*/
    private void jTDCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDCredKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDCredKeyTyped

    
    /*Cuando se presiona una tecla typed en el campo de límite de crédito*/
    private void jTLimiCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLimiCredKeyTyped
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTLimiCred.getText().length()> 30)
            jTLimiCred.setText(jTLimiCred.getText().substring(0, 30));
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();        
        
    }//GEN-LAST:event_jTLimiCredKeyTyped

    
    /*Cando se presiona una tecla typed en el campo de RFC*/
    private void jTRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRFCKeyTyped

   
    /*Cuando se da clic en el label de página web 1*/
    private void jLPag1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag1MouseClicked
        
        //Declara variables locales
        String sPag;
        String url = "http://";
        
        
        /*Obtiene la pagina que el usuario escribio*/
        sPag = jLPag1.getText();
                
        /*Completa la URL*/
        url     = url + sPag;
        
        /*Abre la página en el navegador por default*/
        try 
        {
            if(Desktop.isDesktopSupported())                                      
                Desktop.getDesktop().browse(new URI(url));
            else 
            {            
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("iexplorer " + url);
            }
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());            
        }                 
        catch(URISyntaxException expnUriSynta)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());            
        }
        
    }//GEN-LAST:event_jLPag1MouseClicked

    
    /*Cuando se da clic en el label de página web 2*/
    private void jLPag2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag2MouseClicked
        
        //Declara variables locales
        String sPag;
        String url = "http://";
        
        
        /*Obtiene la pagina que el usuario escribio*/
        sPag = jLPag2.getText();
                
        /*Completa la URL*/
        url     = url + sPag;
        
        /*Abre la página en el navegador por default*/
        try 
        {
            if(Desktop.isDesktopSupported())                                      
                Desktop.getDesktop().browse(new URI(url));
            else 
            {            
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("iexplorer " + url);
            }
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());            
        }   
        catch(URISyntaxException expnUriSynta)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());            
        }
        
    }//GEN-LAST:event_jLPag2MouseClicked

    
    /*Cuando se entra con el mouse en el label de página web 1*/
    private void jLPag1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag1MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLPag1MouseEntered

    
    /*Cuando se entra con el mouse en el label de página web 2*/
    private void jLPag2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag2MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLPag2MouseEntered

    
    /*Cuando se esta saliendo con el mouse del label de página web 1*/
    private void jLPag1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag1MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLPag1MouseExited

    
    /*Cuando se esta saliendo con el mouse del label de página web 2*/
    private void jLPag2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag2MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLPag2MouseExited

    
    /*Cuando se da un clic con el mouse en el label de correo 1*/
    private void jLCo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseClicked
        
        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        Desktop de;
        if(Desktop.isDesktopSupported() && (de = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) 
        {
          URI mailto;
          
            try 
            {
                mailto = new URI("mailto:" + jLCo1.getText() + "?subject=");
            } 
            catch(URISyntaxException expnUriSynta) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());
                return;
            }
            
            try 
            {
                de.mail(mailto);
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());            
            }
        }         
        
    }//GEN-LAST:event_jLCo1MouseClicked

    
    /*Cuando se entra con el mouse en el label de correo 1*/
    private void jLCo1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseEntered
 
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo1MouseEntered

    
    /*Cuando se sale con el mouse del label de correo 1*/
    private void jLCo1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo1MouseExited

    
    /*Cuando se da un clic con el mouse en el label de correo 2*/
    private void jLCo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseClicked
        
        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        Desktop de;
        if(Desktop.isDesktopSupported() && (de = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) 
        {
          URI mailto;
          
            try 
            {
                mailto = new URI("mailto:" + jLCo2.getText() + "?subject=");
            } 
            catch(URISyntaxException expnUriSynta) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());
                return;
            }
            
            try 
            {
                de.mail(mailto);
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                
            }
        }
        
    }//GEN-LAST:event_jLCo2MouseClicked

    
    /*Cuando se entra con el mouse en el label de correo 2*/
    private void jLCo2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo2MouseEntered

    
    /*Cuando se sale con el mouse del label de correo 2*/
    private void jLCo2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo2MouseExited

    
    /*Cuando se da un clic con el mouse en el label de correo 3*/
    private void jLCo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseClicked
        
        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        Desktop de;
        if(Desktop.isDesktopSupported() && (de = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) 
        {
          URI mailto;
          
            try 
            {
                mailto = new URI("mailto:" + jLCo3.getText() + "?subject=");
            } 
            catch(URISyntaxException expnUriSynta) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());
                return;
            }
            
            try 
            {
                de.mail(mailto);
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                
            }
        }
        
    }//GEN-LAST:event_jLCo3MouseClicked

    
    /*Cuando se entra con el mouse en el label de correo 3*/
    private void jLCo3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo3MouseEntered

    
    /*Cuando se sale con el mouse del label de correo 3*/
    private void jLCo3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo3MouseExited

          
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se tipea una tecla en el campo de calle*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTCPKeyTyped
            
    
    /*Cuando se tipea una tecla en el campo de número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped

                    
    /*Cuando se tipea una tecla en el campo de teléfono persl 1*/
    private void jTTelPerl11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPerl11KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
    }//GEN-LAST:event_jTTelPerl11KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono persl 2*/
    private void jTTelPersol22KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPersol22KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
    }//GEN-LAST:event_jTTelPersol22KeyTyped
    
    
    /*Cuando se tipea una tecla en el campo de teléfono persl 1*/
    private void jTTelPer1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyTyped
        
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();                        
        
    }//GEN-LAST:event_jTTelPer1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono persl 2*/
    private void jTTelPerso2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPerso2KeyTyped
                
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();                           
        
    }//GEN-LAST:event_jTTelPerso2KeyTyped
    

    /*Cuando se presiona una tecla en el radio button de pers física*/
    private void jRaFisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaFisiKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRaFisiKeyPressed

    
    /*Cuandos se presiona una tecla en el radio button de pers moral*/
    private void jRaMorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaMorKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRaMorKeyPressed

    
    /*Cuando se presiona una tecla en el panel que contiene los radio buttons*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanel2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de clasificación*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());        
        
    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la descripción de la clasificación*/
        try
        {
            sQ = "SELECT descrip FROM clasprov WHERE cod = '" + jTClas.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoalo en el control*/
            if(rs.next())
                jTClasDescrip.setText(rs.getString("descrip"));
            /*Else, el codigo de la descripción no existe entonces coloca cadena vacia*/
            else
                jTClasDescrip.setText("");
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

        /*Cierra la base de datos*/
        try
        {
            con.close();
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                    
        }
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clasificación*/
    private void jTClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar clasificación*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBClas.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClasKeyPressed

    
    /*Cuando se tipea una tecla en el campo de clasificación*/
    private void jTClasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTClasKeyTyped

    
    /*Cuando se presiona el botón de búscar clasificación*/
    private void jBClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClasActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTClas.getText(), 13, jTClas, jTClasDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTClas.grabFocus();
        
    }//GEN-LAST:event_jBClasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar clasificación*/
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se presiona el botón de Contactos*/
    private void jBContacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBContacActionPerformed

        /*Muestra el formulario de Contactos*/
        ContacsProv c = new ContacsProv(newProv);
        c.setVisible(true);

    }//GEN-LAST:event_jBContacActionPerformed

    
    /*Cuando se presiona una tecla en el botón de Contactos*/
    private void jBContacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBContacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBContacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de pago*/
    private void jTPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPag.setSelectionStart(0);jTPag.setSelectionEnd(jTPag.getText().length());        

    }//GEN-LAST:event_jTPagFocusGained

    
    /*Cuando se presiona una tecla en el campo de pago*/
    private void jTPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la revisión*/
    private void jTRevFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRevFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTRev.setSelectionStart(0);jTRev.setSelectionEnd(jTRev.getText().length());        

    }//GEN-LAST:event_jTRevFocusGained

    
    /*Cuando se presiona una tecla en el campo de revisión*/
    private void jTRevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRevKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTRevKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de series*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del proveedor*/
    private void jTCodProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv.setSelectionStart(0);jTCodProv.setSelectionEnd(jTCodProv.getText().length());

    }//GEN-LAST:event_jTCodProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del código del proveedor*/
    private void jTCodProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProvKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código del proveedor*/
    private void jTCodProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProvKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodProvKeyTyped

    
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

    
    /*Cuando se presiona una tecla en el checkbox de se le puede comprar en otra moneda*/
    private void jCOtraMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOtraMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCOtraMonKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de comprar en otra moneda*/
    private void jCOrdOtrMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOrdOtrMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCOrdOtrMonKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de bloqueado*/
    private void jCBloqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBloqKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBloqKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el combo de la serie*/          
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComSer.getSelectedItem()==null || jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la lada*/
    private void jTLadaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLada.setSelectionStart(0);jTLada.setSelectionEnd(jTLada.getText().length());

    }//GEN-LAST:event_jTLadaFocusGained

    
    /*Cuando se presiona una tecla en el campo de la lada*/
    private void jTLadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLadaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLadaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la lada*/
    private void jTLadaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLadaKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTLadaKeyTyped

    
    /*Cuando se presiona una tecla en el campo del número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBContacMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBContacMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBContac.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBContacMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBClasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBClasMouseEntered

    
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBClasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBClasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBContacMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBContacMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBContac.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBContacMouseExited

    
    /*Cuando se gana el foco del teclado en el campo de la extensión*/
    private void jTExtenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTExten.setSelectionStart(0);
        jTExten.setSelectionEnd(jTExten.getText().length());

    }//GEN-LAST:event_jTExtenFocusGained

    
    /*Cuando se presiona una tecla en el campo de la extensión*/
    private void jTExtenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTExtenKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la extensión*/
    private void jTExtenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTExtenKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del cálular*/
    private void jTCelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCel.setSelectionStart(0);
        jTCel.setSelectionEnd(jTCel.getText().length());

    }//GEN-LAST:event_jTCelFocusGained

    
    /*Cuando se presiona una tecla en el campo del cálular*/
    private void jTCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCelKeyPressed

    private void jBDomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDom.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDomMouseEntered

    private void jBDomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDom.setBackground(Star.colOri);

    }//GEN-LAST:event_jBDomMouseExited

    
    /*Cuando se presiona el botón de ver domicilio*/
    private void jBDomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDomActionPerformed

        /*Muestra la forma de domiclio de entrega*/
        DomEntProv d = new DomEntProv(newProv);
        d.setVisible(true);
        
    }//GEN-LAST:event_jBDomActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver domicilio*/
    private void jBDomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDomKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTLadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLada.setCaretPosition(0);
        
    }//GEN-LAST:event_jTLadaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExtenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExten.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExtenFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTClasDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTClasDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRevFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRevFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRev.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRevFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodProvFocusLost

    
    /*Cuando la forma se a activado completamente*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
 
        /*Establece la referencia a si mismo*/
        newProv         = this;
                
    }//GEN-LAST:event_formWindowActivated

    
    /*Cuando se gana el foco del teclado en el campo del giro*/
    private void jTGirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGirFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTGir.setSelectionStart(0);jTGir.setSelectionEnd(jTGir.getText().length());
        
    }//GEN-LAST:event_jTGirFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de giro*/
    private void jTGirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGirFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTGir.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTGir.getText().compareTo("")!=0)
            jTGir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTGirFocusLost

    
    /*Cuando se presiona una tecla en el campo de giro*/
    private void jTGirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGirKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar giro*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBGir.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTGirKeyPressed

    
    /*Cuando se tipea una tecla en el campo del giro*/
    private void jTGirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGirKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTGirKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la zona*/
    private void jTZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTZonFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTZon.setSelectionStart(0);jTZon.setSelectionEnd(jTZon.getText().length());
        
    }//GEN-LAST:event_jTZonFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la zona*/
    private void jTZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTZonFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTZon.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTZon.getText().compareTo("")!=0)
            jTZon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTZonFocusLost

    
    /*Cuando se presiona una tecla en el campo de la zona*/
    private void jTZonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTZonKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar zona*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBZon.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTZonKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la zona*/
    private void jTZonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTZonKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTZonKeyTyped

    
    /*Cuando el mouse entra en el botón de zona*/
    private void jBZonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBZonMouseEntered

        /*Cambia el color del fondo del botón*/
        jBZon.setBackground(Star.colBot);

    }//GEN-LAST:event_jBZonMouseEntered

    
    /*Cuando el mouse sale del botón de zona*/
    private void jBZonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBZonMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBZon.setBackground(Star.colOri);

    }//GEN-LAST:event_jBZonMouseExited

    
    /*Cuando se presiona el botón de zona*/
    private void jBZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBZonActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTZon.getText(), 32, jTZon, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTZon.grabFocus();
        
    }//GEN-LAST:event_jBZonActionPerformed

    
    /*Cuando se presiona una tecla en el botón de zona*/
    private void jBZonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBZonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBZonKeyPressed

    
    /*Cuando el mouse entra en el botón de giro*/
    private void jBGirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGirMouseEntered

        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);

    }//GEN-LAST:event_jBGirMouseEntered

    
    /*Cuando el mouse sale del botón de giro*/
    private void jBGirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGirMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(Star.colOri);

    }//GEN-LAST:event_jBGirMouseExited

    
    /*Cuando se presiona el botón de giro*/
    private void jBGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGirActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTGir.getText(), 33, jTGir, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTGir.grabFocus();
        
    }//GEN-LAST:event_jBGirActionPerformed

    
    /*Cuando se presiona una tecla en el botón de giro*/
    private void jBGirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGirKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGirKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta contable*/
    private void jTCtaContaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaContaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaConta.setSelectionStart(0);jTCtaConta.setSelectionEnd(jTCtaConta.getText().length());

    }//GEN-LAST:event_jTCtaContaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta contable*/
    private void jTCtaContaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaContaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCtaConta.setCaretPosition(0);

    }//GEN-LAST:event_jTCtaContaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta contable*/
    private void jTCtaContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaContaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCtaContaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del rubro del proveedor*/
    private void jTRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRubFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRub.setSelectionStart(0);jTRub.setSelectionEnd(jTRub.getText().length());        
        
    }//GEN-LAST:event_jTRubFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del rubro*/
    private void jTRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRubFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRub.setCaretPosition(0);
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la descripción del rubro*/
        try
        {
            sQ = "SELECT descrip FROM rubr WHERE cod = '" + jTRub.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoalo en el control*/
            if(rs.next())
                jTRubDescrip.setText(rs.getString("descrip"));
            /*Else, el codigo de la descripción no existe entonces coloca cadena vacia*/
            else
                jTRubDescrip.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Cierra la base de datos*/
        try
        {
            con.close();
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                    
        }
        
    }//GEN-LAST:event_jTRubFocusLost

    
    /*Cuando se presiona una tecla en el campo del rubro*/
    private void jTRubKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRubKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar rubro*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBRub.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRubKeyPressed

    
    /*Cuando se tipea una tecla en el campo del rubro*/
    private void jTRubKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRubKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRubKeyTyped

    
    /*Cuando el mouse entra en el botón de rubro*/
    private void jBRubMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRubMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRub.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRubMouseEntered

    
    /*Cuando el mouse sale del botón de rubro*/
    private void jBRubMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRubMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRub.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRubMouseExited

    
    /*Cuando se presiona el botón de rubro*/
    private void jBRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRubActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTRub.getText(), 36, jTRub, jTRubDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código*/
        jTRub.grabFocus();
        
    }//GEN-LAST:event_jBRubActionPerformed

    
    /*Cuando se presiona una tecla en el botón del rubro*/
    private void jBRubKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRubKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRubKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del rubro*/
    private void jTRubDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRubDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRubDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRubDescripFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPag.setSelectionStart(0);jTMetPag.setSelectionEnd(jTMetPag.getText().length());

    }//GEN-LAST:event_jTMetPagFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de método de pago*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMetPag.setCaretPosition(0);

    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se presiona una tecla en el campo del método de pago*/
    private void jTMetPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMetPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());

    }//GEN-LAST:event_jTCtaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCta.setCaretPosition(0);

    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la cuenta*/
    private void jTCtaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b'))
            evt.consume();

    }//GEN-LAST:event_jTCtaKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la fecha de entrega*/
    private void jTEntreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEntreFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEntre.setSelectionStart(0);jTEntre.setSelectionEnd(jTEntre.getText().length());                
        
    }//GEN-LAST:event_jTEntreFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de entrega*/
    private void jTEntreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEntreFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEntre.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEntre.getText().length()> 255)
            jTEntre.setText(jTEntre.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEntreFocusLost

    
    /*Cuando se presiona una tecla en el campo de fecha de entrega*/
    private void jTEntreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEntreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEntreKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del banco*/
    private void jTBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBancFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTBanc.setSelectionStart(0);jTBanc.setSelectionEnd(jTBanc.getText().length());

    }//GEN-LAST:event_jTBancFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del banco*/
    private void jTBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBancFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBanc.setCaretPosition(0);

    }//GEN-LAST:event_jTBancFocusLost

    
    /*Cuando se presiona una tecla en el campo del banco*/
    private void jTBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBancKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTBancKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del banco*/
    private void jTClavBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavBancFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClavBanc.setSelectionStart(0);jTClavBanc.setSelectionEnd(jTClavBanc.getText().length());

    }//GEN-LAST:event_jTClavBancFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la clave bancaría*/
    private void jTClavBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavBancFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClavBanc.setCaretPosition(0);

    }//GEN-LAST:event_jTClavBancFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clave bancaría*/
    private void jTClavBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClavBancKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTClavBancKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación de la jerarquía*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());

    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco del teclado en e lcampo de la clasificación de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el control de la clasificación de la jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTJeraKeyPressed

    
    /*Cuando el mouse entra en el botón de la jerarquía*/
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);

    }//GEN-LAST:event_jBJeraMouseEntered

    
    /*Cuando el mouse sale del botón de la jerarquía*/
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colOri);

    }//GEN-LAST:event_jBJeraMouseExited

    
    /*Cuaando se presiona el botón de jerarquía*/
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis v = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "prov");
        v.setVisible(true);

    }//GEN-LAST:event_jBJeraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de clasificación de jerarquías*/
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBJeraKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodProv.setCaretPosition(0);

    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());        
        
    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    //Cuando se presiona una tecla en el check de bloquear crédito
    private void jCBloqCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBloqCredKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBloqCredKeyPressed

    private void jTCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyTyped
        // TODO add your handling code here:
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
    }//GEN-LAST:event_jTCelKeyTyped
   
    
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
        /*Si se presiona F2 presiona el botón de Contactos*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBContac.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBContac;
    private javax.swing.JButton jBDom;
    private javax.swing.JButton jBGir;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBRub;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBZon;
    private javax.swing.JCheckBox jCBloq;
    private javax.swing.JCheckBox jCBloqCred;
    private javax.swing.JCheckBox jCOrdOtrMon;
    private javax.swing.JCheckBox jCOtraMon;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLCo1;
    private javax.swing.JLabel jLCo2;
    private javax.swing.JLabel jLCo3;
    private javax.swing.JLabel jLPag1;
    private javax.swing.JLabel jLPag2;
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
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRaFisi;
    private javax.swing.JRadioButton jRaMor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTBanc;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTClasDescrip;
    private javax.swing.JTextField jTClavBanc;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodProv;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTCtaConta;
    private javax.swing.JTextField jTDCred;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTEje;
    private javax.swing.JTextField jTEje2;
    private javax.swing.JTextField jTEntre;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTExten;
    private javax.swing.JTextField jTGir;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTLada;
    private javax.swing.JTextField jTLimiCred;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNomb;
    private javax.swing.JTextField jTPag;
    private javax.swing.JTextField jTPag1;
    private javax.swing.JTextField jTPag2;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTRev;
    private javax.swing.JTextField jTRub;
    private javax.swing.JTextField jTRubDescrip;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelPer1;
    private javax.swing.JTextField jTTelPerl11;
    private javax.swing.JTextField jTTelPerso2;
    private javax.swing.JTextField jTTelPersol22;
    private javax.swing.JTextField jTZon;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
