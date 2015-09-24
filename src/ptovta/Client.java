//Paquete
package ptovta;

//Importaciones
import cats.Clients;
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;




/*Clase para crear un nuevo cliente*/
public class Client extends javax.swing.JFrame 
{        
    /*Declara variables de instancia*/    
    private final JTable    jTabEmp;

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
    public String   sContac3;
    public String   sTelCon3;
    public String   sTelPer3;
    public String   sTelPer33;
    public String   sContac4;
    public String   sTelCon4;
    public String   sTelPer4;
    public String   sTelPer44;
    public String   sContac5;
    public String   sTelCon5;
    public String   sTelPer5;
    public String   sTelPer55;
    public String   sContac6;
    public String   sTelCon6;
    public String   sTelPer6;
    public String   sTelPer66;
    public String   sContac7;
    public String   sTelCon7;
    public String   sTelPer7;
    public String   sTelPer77;
    public String   sContac8;
    public String   sTelCon8;
    public String   sTelPer8;
    public String   sTelPer88;
    public String   sContac9;
    public String   sTelCon9;
    public String   sTelPer9;
    public String   sTelPer99;
    public String   sContac10;
    public String   sTelCon10;
    public String   sTelPer10;
    public String   sTelPer100;

    //Declara variables locales
    private String  sCodEmpres;    
    private String  sSer;    
    private String  sRFCOri;
    
    /*Guarda la referencia a la misma forma*/
    Client            newCli;
    
    /*Contiene el código del cliente*/
    private String  sCliG;
    
    
    
    
    /*Consructor con argumento*/
    public Client(JTable jTabEmpsas, String sCli, final boolean permiso) 
    {        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        //(Des)habilita el boton guardar
        jBGuar.setEnabled(permiso);
        
        if(sCli.compareTo("")==0)
        {
            jComSer.setVisible(true);
            jLabel35.setVisible(true);
            jTCodEmp.setVisible(true);
            jLabel36.setVisible(true);
        }
        else
        {
            jComSer.setVisible(false);
            jLabel35.setVisible(false);
            jTCodEmp.setVisible(false);
            jLabel36.setVisible(false);
        }
        /*Guarda el código del cliente*/
        sCliG   = sCli;
        
        /*Esconde la tabla de los docmilios*/
        jScrTab.setVisible(false);
                
        /*Incializa la lista del formulario de empresas*/
        jTabEmp = jTabEmpsas;
                        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        /*Inicializa todo lo relacionado a los Contactos*/
        sContac3        = "";
        sTelCon3        = "";
        sTelPer3        = "";
        sTelPer33       = "";
        sTelPer33       = "";
        sContac4        = "";
        sTelCon4        = "";
        sTelPer4        = "";
        sTelPer44       = "";
        sContac5        = "";
        sTelCon5        = "";
        sTelPer5        = "";
        sTelPer55       = "";
        sContac6        = "";
        sTelCon6        = "";
        sTelPer6        = "";
        sTelPer66       = "";
        sContac7        = "";
        sTelCon7        = "";
        sTelPer7        = "";
        sTelPer77       = "";
        sContac8        = "";
        sTelCon8        = "";
        sTelPer8        = "";
        sTelPer88       = "";
        sContac9        = "";
        sTelCon9        = "";
        sTelPer9        = "";
        sTelPer99       = "";
        sContac10       = "";
        sTelCon10       = "";
        sTelPer10       = "";
        sTelPer100      = "";                
                
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Cliente, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en jtextarea de observ que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo de edición del nom del cliente*/
        jTRazSoc.grabFocus();
        
        /*Crea el grupo para los radio buttons*/
        ButtonGroup  g  = new ButtonGroup();
        g.add(jRaMor);
        g.add(jRaFis);        
              
        /*Listener para el combobox de series*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga las series del cliente*/
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
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;        
        
        /*Obtiene todas las direcciones del cliente existentes*/
        try
        {
            sQ = "SELECT * FROM domentcli WHERE codemp = '" + sCli + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                    
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {rs.getString("tel"), rs.getString("lada"), rs.getString("exten"), rs.getString("cel"), rs.getString("telper1"), rs.getString("telper2"), rs.getString("calle"), rs.getString("col"), rs.getString("noext"), rs.getString("noint"), rs.getString("cp"), rs.getString("ciu"), rs.getString("estad"), rs.getString("pai"), rs.getString("co1"), rs.getString("co2"), rs.getString("co3")};
                te.addRow(nu);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Declara variables de bloque*/        
        String sLimCred;        
        String sMetPag;
        String sCta;
        
        //Obtiene las formas de pagos y cargalos en el combo
        if(Star.iCargFormPagCom(con, jComFormPag)==-1)
            return;
        
        /*Carga de la base de datos del cliente en base a su código y serie los datos y ponlos en los controles de edición*/
        try
        {
            sQ = "SELECT * FROM emps LEFT OUTER JOIN clasemp ON clasemp.COD = emps.CODCLAS WHERE CONCAT_WS('', ser, codemp)= '" + sCli + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Lee todos los valores obtenidos*/                                                                                                                               
                sLimCred        = rs.getString("limtcred"); 
                sMetPag         = rs.getString("metpag"); 
                sCta            = rs.getString("cta"); 
            
                /*Guarda la serie del cliente*/
                sSer            = rs.getString("ser");
                
                /*Coloca el código del cliente*/
                jTCodCli.setText(rs.getString("ser") + rs.getString("codemp"));
                
                /*Coloca la cuenta de predial*/
                jTCtaPred.setText(rs.getString("ctapred"));
                
                /*Coloca el giro y la zona*/
                jTGir.setText   (rs.getString("giro"));
                jTZon.setText   (rs.getString("zon"));
                
                /*Coloca la cuenta contable*/
                jTCtaConta.setText(rs.getString("ctaconta"));
                
                /*Coloca la clasificación de jearaquía*/
                jTJera.setText  (rs.getString("clasjera"));
                
                /*Si no es cadena vacia entonces convierte a double el límite de crédito y formatealo*/
                if(sLimCred.compareTo("")!=0)
                {                    
                    double dCant    = Double.parseDouble(sLimCred);                
                    NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                    sLimCred        = n.format(dCant);                    
                }                                
                
//                /*Si el método de pago es cadena vacia entonces que sea no identificable*/
//                if(sMetPag.compareTo("")==0)
//                    sMetPag         = "No Identificado";
                
                /*Si la cuenta de pago es cadena vacia entonces que sea 0000*/
                if(sCta.compareTo("")==0)
                    sCta            = "0000";

                /*Coloca el banco y la clave bancaría en el campo correspondiente*/
                jTBanc.setText      (rs.getString("banc"));
                jTClavBanc.setText  (rs.getString("clavbanc"));
                
                /*Coloca todos los valores del domicilio de entrega*/
                sTelEn          = rs.getString("entel1");
                sLadaEn         = rs.getString("enlada");
                sExtEn          = rs.getString("ennoexten");
                sCelEn          = rs.getString("encel");
                sTelPer1En      = rs.getString("entel2");
                sTelPer2En      = rs.getString("entel3");
                sCallEn         = rs.getString("encall");
                sColEn          = rs.getString("encol");
                sNoExtEn        = rs.getString("ennoext");
                sIntEn          = rs.getString("ennoint");
                sCPEn           = rs.getString("encp");
                sCiuEn          = rs.getString("enciu");
                sEstadEn        = rs.getString("enestad");
                sPaiEn          = rs.getString("enpai");
                sCo1En          = rs.getString("enco1");
                sCo2En          = rs.getString("enco2");
                sCo3En          = rs.getString("enco3");
                
                /*Coloca todas las lecturas en los campos de edición*/                
                jTRazSoc.setText    (rs.getString("nom"));
                jTTel.setText       (rs.getString("tel"));
                jTTelPer1.setText   (rs.getString("telper1"));
                jTTelPer2.setText   (rs.getString("telper2"));
                jTCall.setText      (rs.getString("calle"));
                jTCel.setText       (rs.getString("cel"));
                jTExten.setText     (rs.getString("exten"));
                jTLada.setText      (rs.getString("lada"));
                jTCol.setText       (rs.getString("col"));
                jTCP.setText        (rs.getString("CP"));
                jTCiu.setText       (rs.getString("ciu"));
                jTEstad.setText     (rs.getString("estad"));
                jTPai.setText       (rs.getString("pai"));
                jTRFC.setText       (rs.getString("rfc"));
                jTCURP.setText      (rs.getString("curp"));
                jTDesc.setText      (rs.getString("descu"));
                jTCo1.setText       (rs.getString("co1"));
                jTVend.setText      (rs.getString("vend"));
                jLCo1.setText       (rs.getString("co1"));
                jTCo2.setText       (rs.getString("co2"));
                jLCo2.setText       (rs.getString("co2"));
                jTCo3.setText       (rs.getString("co3"));
                jLCo3.setText       (rs.getString("co3"));
                jTPag1.setText      (rs.getString("pagweb1"));
                jLPag1.setText      (rs.getString("pagweb1"));                        
                jTPag2.setText      (rs.getString("pagweb2"));
                jLPag2.setText      (rs.getString("pagweb2"));                        
                jTContac.setText    (rs.getString("contac"));
                jTPues.setText      (rs.getString("puest"));
                jTContac2.setText   (rs.getString("contact2"));
                jTPues2.setText     (rs.getString("puest2"));                
                jTAObserv.setText   (rs.getString("observ"));
                jTCodEmp.setText    (sCodEmpres);
                jTNoInt.setText     (rs.getString("noint"));
                jTNoExt.setText     (rs.getString("noext"));
                jTDCred.setText     (rs.getString("diacred"));
                jTLimCred.setText   (sLimCred);
                jComFormPag.setSelectedItem(sMetPag);
                jTCta.setText       (sCta);
                jComList.setSelectedItem(rs.getString("list"));
                jTClas.setText      (rs.getString("codclas"));
                jTClasDescrip.setText(rs.getString("descrip"));
                jTRev.setText       (rs.getString("revis"));
                jTPag.setText       (rs.getString("pags"));
                
                /*Si la fecha de cumpleaños no es cadena vacia entonces*/
                if(rs.getString("cumple").compareTo("")!=0)
                {
                    /*Coloca la fecha en el control*/
                    SimpleDateFormat fr= new SimpleDateFormat("yyyy-MM-dd");
                    Date fi;
                    try
                    {            
                        fi             = fr.parse(rs.getString("cumple"));
                        jDCumple.setDate(fi);       
                    }
                    catch(ParseException expnPARS)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace(), con);                                
                        return;
                    }        
                }
                
                /*Carga todos los Contactos*/
                sContac3        = rs.getString("contac3");
                sContac4        = rs.getString("contac4");
                sContac5        = rs.getString("contac5");
                sContac6        = rs.getString("contac6");
                sContac7        = rs.getString("contac7");
                sContac8        = rs.getString("contac8");
                sContac9        = rs.getString("contac9");
                sContac10       = rs.getString("contac10");
                
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
                
                /*Marca el checkbox si es cliente bloq*/
                if(rs.getString("bloq").compareTo("1")==0)
                    jCBloq.setSelected(true);                
                
                //Marca o desmarca si esta bloqueado el crédito
                if(rs.getString("bloqlimcred").compareTo("1")==0)
                    jCBloqCred.setSelected(true);
                else
                    jCBloqCred.setSelected(false);
                
                /*Marca el radio button si es pers física o moral*/
                if(rs.getString("pers").compareTo("M")==0)
                    jRaMor.setSelected(true);
                else
                    jRaFis.setSelected(true);
                
                /*Marca si se le puede o no vender con otra moneda*/
                if(rs.getString("otramon").compareTo("1")==0)
                    jCOtraMon.setSelected(true);
                else
                    jCOtraMon.setSelected(false);
                        
                /*Marca si se le puede o no cotizar con otra moneda*/
                if(rs.getString("otramonc").compareTo("1")==0)
                    jCCotOtrMon.setSelected(true);
                else
                    jCCotOtrMon.setSelected(false);
                
                /*Lee el total de depósito en garantía*/
                String sDepGar  = rs.getString("deposit");
                
                /*Dale formato de moneda al depósito en garantía*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sDepGar);                
                sDepGar         = n.format(dCant);
                
                /*Colocalo en el campo el depósito en garantía*/
                jTDepGar.setText(sDepGar);
                
            }/*Fin de while (rs.next())*/            
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
        
        /*Guarda el RFC original del cliente*/
        sRFCOri = jTRFC.getText();        
                        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
            
        /*Carga las series del cliente*/
        vCargSer();        
        
    }/*Fin de public NewClien() */    
    
    
    /*Método para cargar la serie de los clientes*/
    private void vCargSer()
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

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
        
        /*Obtiene todas las series de los clientes*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'EMP'";                        
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
        
    }/*Fin de private void vCargSer()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTRazSoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jTEstad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTRFC = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTTel = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTCo1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTPag1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jTPag2 = new javax.swing.JTextField();
        jTCo2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTContac = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTTelPer1 = new javax.swing.JTextField();
        jTTelPer2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jTNoInt = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTDCred = new javax.swing.JTextField();
        jTLimCred = new javax.swing.JTextField();
        jCBloq = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        jTPues = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTContac2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTPues2 = new javax.swing.JTextField();
        jLPag2 = new javax.swing.JLabel();
        jLPag1 = new javax.swing.JLabel();
        jLCo3 = new javax.swing.JLabel();
        jLCo1 = new javax.swing.JLabel();
        jLCo2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRaFis = new javax.swing.JRadioButton();
        jRaMor = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTCta = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTClas = new javax.swing.JTextField();
        jBClas = new javax.swing.JButton();
        jTClasDescrip = new javax.swing.JTextField();
        jBContac = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTPag = new javax.swing.JTextField();
        jTRev = new javax.swing.JTextField();
        jComSer = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jTCodEmp = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jDCumple = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        jCOtraMon = new javax.swing.JCheckBox();
        jCCotOtrMon = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();
        jTCURP = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTVend = new javax.swing.JTextField();
        jBVend = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jComList = new javax.swing.JComboBox();
        jTLada = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTExten = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTCel = new javax.swing.JTextField();
        jBDom = new javax.swing.JButton();
        jScrTab = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTGir = new javax.swing.JTextField();
        jBGir = new javax.swing.JButton();
        jTZon = new javax.swing.JTextField();
        jBZon = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jTCtaConta = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTDepGar = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTBanc = new javax.swing.JTextField();
        jTClavBanc = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jTCodCli = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTCtaPred = new javax.swing.JTextField();
        jCBloqCred = new javax.swing.JCheckBox();
        jComFormPag = new javax.swing.JComboBox();

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

        jTRazSoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRazSoc.setNextFocusableComponent(jTLada);
        jTRazSoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRazSocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRazSocFocusLost(evt);
            }
        });
        jTRazSoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRazSocKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRazSocKeyTyped(evt);
            }
        });
        jP1.add(jTRazSoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 230, 20));

        jLabel2.setText("*Razón social:");
        jLabel2.setToolTipText("Nombre de la Empresa o Nombnre del Cliente");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jLabel3.setText("Calle:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));
        jLabel3.getAccessibleContext().setAccessibleName("Calle:");

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
        jTCall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCallActionPerformed(evt);
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
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 230, 20));

        jLabel4.setText("Colonia:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTNoExt);
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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 230, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstadKeyTyped(evt);
            }
        });
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 230, 20));

        jLabel5.setText("Estado:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel6.setText("País:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPaiKeyTyped(evt);
            }
        });
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 230, 20));

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
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 230, 20));

        jLabel7.setText("Ciudad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel8.setText("Cuenta bancaria:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, 130, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTCiu);
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
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 230, 20));

        jLabel9.setText("No. interior:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 100, -1));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTCodCli);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 120, 30));

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
        jP1.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 230, 20));

        jLabel11.setText("*RFC:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        jLabel12.setText("CURP:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 110, -1));

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
        jP1.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 230, 20));

        jLabel13.setText("Teléfono personal 2:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

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
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 130, 20));

        jLabel14.setText("Cuenta predial:");
        jLabel14.setToolTipText("Depósito en Garantía");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 310, 100, -1));

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
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 230, 20));

        jLabel15.setText("Página web1:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, -1));

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
        jP1.add(jTPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 230, 20));

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setBorder(null);
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

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, 230, 40));

        jLabel16.setText("Puesto2:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, 100, -1));

        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag2.setNextFocusableComponent(jTContac);
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
        jP1.add(jTPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 230, 20));

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
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 230, 20));

        jLabel17.setText("Correo2:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        jLabel18.setText("Correo3:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

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
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 230, 20));

        jLabel19.setText("Página web2:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

        jTContac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTContac.setNextFocusableComponent(jTPues);
        jTContac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTContacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTContacFocusLost(evt);
            }
        });
        jTContac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTContacKeyPressed(evt);
            }
        });
        jP1.add(jTContac, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, 230, 20));

        jLabel20.setText("Extensión:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jLabel21.setText("Teléfono personal 1:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jTTelPer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer1.setNextFocusableComponent(jTTelPer2);
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
        jP1.add(jTTelPer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 230, 20));

        jTTelPer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer2.setNextFocusableComponent(jTCall);
        jTTelPer2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer2FocusLost(evt);
            }
        });
        jTTelPer2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPer2KeyTyped(evt);
            }
        });
        jP1.add(jTTelPer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 230, 20));

        jLabel10.setText("CP:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel22.setText("No. exterior:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 100, -1));

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
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 230, 20));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCP);
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
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 230, 20));

        jLabel23.setText("Descuento %:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 100, -1));

        jLabel24.setText("Días de crédito:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 100, -1));

        jTDCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDCred.setNextFocusableComponent(jTLimCred);
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
        jP1.add(jTDCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 230, 20));

        jTLimCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLimCred.setNextFocusableComponent(jTCURP);
        jTLimCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLimCredFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLimCredFocusLost(evt);
            }
        });
        jTLimCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLimCredKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLimCredKeyTyped(evt);
            }
        });
        jP1.add(jTLimCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 230, 20));

        jCBloq.setBackground(new java.awt.Color(255, 255, 255));
        jCBloq.setText("Bloqueado");
        jCBloq.setNextFocusableComponent(jCBloqCred);
        jCBloq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBloqKeyPressed(evt);
            }
        });
        jP1.add(jCBloq, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 100, -1));

        jLabel25.setText("Contacto2:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, 100, -1));

        jTPues.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPues.setNextFocusableComponent(jTContac2);
        jTPues.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPuesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPuesFocusLost(evt);
            }
        });
        jTPues.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPuesKeyPressed(evt);
            }
        });
        jP1.add(jTPues, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 230, 20));

        jLabel26.setText("Contacto:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 100, -1));

        jTContac2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTContac2.setNextFocusableComponent(jTPues2);
        jTContac2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTContac2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTContac2FocusLost(evt);
            }
        });
        jTContac2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTContac2KeyPressed(evt);
            }
        });
        jP1.add(jTContac2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 230, 20));

        jLabel27.setText("Puesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 100, -1));

        jTPues2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPues2.setNextFocusableComponent(jTAObserv);
        jTPues2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPues2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPues2FocusLost(evt);
            }
        });
        jTPues2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPues2KeyPressed(evt);
            }
        });
        jP1.add(jTPues2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, 230, 20));

        jLPag2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLPag2.setForeground(new java.awt.Color(51, 51, 255));
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
        jP1.add(jLPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 230, 230, -1));

        jLPag1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLPag1.setForeground(new java.awt.Color(51, 51, 255));
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
        jP1.add(jLPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 230, -1));

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
        jP1.add(jLCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 230, -1));

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
        jP1.add(jLCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 230, -1));

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
        jP1.add(jLCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 230, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRaFis.setBackground(new java.awt.Color(255, 255, 255));
        jRaFis.setText("Persona física");
        jRaFis.setNextFocusableComponent(jTRazSoc);
        jRaFis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaFisKeyPressed(evt);
            }
        });
        jPanel2.add(jRaFis, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, 110, -1));

        jRaMor.setBackground(new java.awt.Color(255, 255, 255));
        jRaMor.setSelected(true);
        jRaMor.setText("Persona moral");
        jRaMor.setNextFocusableComponent(jRaFis);
        jRaMor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaMorKeyPressed(evt);
            }
        });
        jPanel2.add(jRaMor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 120, -1));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 240, 30));

        jLabel28.setText("Observaciones:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, -1, -1));

        jLabel29.setText("Método pago:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, 130, -1));

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
        jP1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 390, 230, 20));

        jLabel30.setText("Correo1:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 54, -1, 10));

        jLabel31.setText("Lista Precio:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, -1, 10));

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
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 70, 100, 20));

        jBClas.setBackground(new java.awt.Color(255, 255, 255));
        jBClas.setText("...");
        jBClas.setToolTipText("Buscar Clasificación(es)");
        jBClas.setNextFocusableComponent(jTClasDescrip);
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
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 30, 20));

        jTClasDescrip.setEditable(false);
        jTClasDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClasDescrip.setNextFocusableComponent(jTRev);
        jTClasDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClasDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasDescripFocusLost(evt);
            }
        });
        jP1.add(jTClasDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 90, 100, 20));

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
        jP1.add(jBContac, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 340, 130, -1));

        jLabel32.setText("Clasificación:");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 100, -1));

        jLabel33.setText("Revisión:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 100, -1));

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
        jP1.add(jTPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, 100, 20));

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
        jP1.add(jTRev, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, 100, 20));

        jComSer.setNextFocusableComponent(jTCodEmp);
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
        jP1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 150, 100, 20));

        jLabel34.setText("Pagos:");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 130, 100, -1));

        jTCodEmp.setBackground(new java.awt.Color(204, 255, 204));
        jTCodEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodEmp.setNextFocusableComponent(jDCumple);
        jTCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusLost(evt);
            }
        });
        jTCodEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodEmpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodEmpKeyTyped(evt);
            }
        });
        jP1.add(jTCodEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 170, 100, 20));

        jLabel35.setText("*Serie:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 100, -1));

        jDCumple.setNextFocusableComponent(jTVend);
        jDCumple.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDCumpleKeyPressed(evt);
            }
        });
        jP1.add(jDCumple, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 190, 100, -1));

        jLabel36.setText("Cod.cliente:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, 100, -1));

        jCOtraMon.setBackground(new java.awt.Color(255, 255, 255));
        jCOtraMon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCOtraMon.setSelected(true);
        jCOtraMon.setText("Se le puede vender en otra moneda");
        jCOtraMon.setNextFocusableComponent(jCCotOtrMon);
        jCOtraMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOtraMonKeyPressed(evt);
            }
        });
        jP1.add(jCOtraMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, 230, -1));

        jCCotOtrMon.setBackground(new java.awt.Color(255, 255, 255));
        jCCotOtrMon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCotOtrMon.setSelected(true);
        jCCotOtrMon.setText("Se le puede cotizar en otra moneda");
        jCCotOtrMon.setNextFocusableComponent(jRaMor);
        jCCotOtrMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCotOtrMonKeyPressed(evt);
            }
        });
        jP1.add(jCCotOtrMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 420, 230, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(846, 450, 150, 20));

        jTCURP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCURP.setNextFocusableComponent(jCBloq);
        jTCURP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCURPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCURPFocusLost(evt);
            }
        });
        jTCURP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCURPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCURPKeyTyped(evt);
            }
        });
        jP1.add(jTCURP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 230, 20));

        jLabel37.setText("Límite de crédito:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 100, -1));

        jTVend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTVend.setNextFocusableComponent(jBVend);
        jTVend.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTVendFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTVendFocusLost(evt);
            }
        });
        jTVend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTVendKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTVendKeyTyped(evt);
            }
        });
        jP1.add(jTVend, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 210, 100, 20));

        jBVend.setBackground(new java.awt.Color(255, 255, 255));
        jBVend.setText("...");
        jBVend.setToolTipText("Buscar Vendedor(es)");
        jBVend.setNextFocusableComponent(jTGir);
        jBVend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVendMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVendMouseExited(evt);
            }
        });
        jBVend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVendActionPerformed(evt);
            }
        });
        jBVend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVendKeyPressed(evt);
            }
        });
        jP1.add(jBVend, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 210, 30, 20));

        jLabel38.setText("Cumpleaños:");
        jP1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 100, -1));

        jComList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComList.setNextFocusableComponent(jTClas);
        jComList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComListKeyPressed(evt);
            }
        });
        jP1.add(jComList, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 50, 100, 20));

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

        jLabel39.setText("Lada:");
        jP1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 40, 20));

        jLabel40.setText("Teléfono:");
        jP1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

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
        jP1.add(jTExten, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 230, 20));

        jLabel41.setText("Celular:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, -1));

        jTCel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel.setNextFocusableComponent(jTTelPer1);
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
        jP1.add(jTCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 230, 20));

        jBDom.setBackground(new java.awt.Color(255, 255, 255));
        jBDom.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDom.setForeground(new java.awt.Color(0, 102, 0));
        jBDom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/doment.png"))); // NOI18N
        jBDom.setText("Entrega");
        jBDom.setToolTipText("Domicilio de Entrega del Cliente");
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
        jP1.add(jBDom, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 360, 130, 30));

        jScrTab.setEnabled(false);
        jScrTab.setFocusable(false);

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "tel", "lada", "exten", "cel", "telper1", "telper2", "calle", "col", "noext", "noint", "cp", "ciu", "estad", "pais", "co1", "co2", "co3"
            }
        ));
        jTab.setEnabled(false);
        jTab.setFocusable(false);
        jTab.setShowHorizontalLines(false);
        jTab.setShowVerticalLines(false);
        jScrTab.setViewportView(jTab);

        jP1.add(jScrTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 40, 20));

        jLabel42.setText("Vendedor:");
        jP1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 210, 100, -1));

        jLabel43.setText("Giro:");
        jP1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 230, 100, -1));

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
        jP1.add(jTGir, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 230, 100, 20));

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
        jP1.add(jBGir, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 230, 30, 20));

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
        jP1.add(jTZon, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 250, 100, 20));

        jBZon.setBackground(new java.awt.Color(255, 255, 255));
        jBZon.setText("...");
        jBZon.setToolTipText("Buscar Clasificación(es)");
        jBZon.setNextFocusableComponent(jTDepGar);
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
        jP1.add(jBZon, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, 30, 20));

        jLabel44.setText("Cuenta contable:");
        jP1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, 130, -1));

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
        jP1.add(jTCtaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 230, 20));

        jLabel45.setText("Zona:");
        jP1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 100, -1));

        jTDepGar.setText("$0.00");
        jTDepGar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDepGar.setNextFocusableComponent(jTJera);
        jTDepGar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDepGarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDepGarFocusLost(evt);
            }
        });
        jTDepGar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDepGarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDepGarKeyTyped(evt);
            }
        });
        jP1.add(jTDepGar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 270, 100, 20));

        jLabel46.setText("Banco:");
        jP1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 130, -1));

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
        jP1.add(jTBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 230, 20));

        jTClavBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClavBanc.setNextFocusableComponent(jComList);
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
        jP1.add(jTClavBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, 230, 20));

        jLabel47.setText("Clabe interbancaria:");
        jP1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 450, 130, -1));

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
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 290, 100, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar Jerárquia(s)");
        jBJera.setNextFocusableComponent(jTCtaPred);
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
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 290, 30, 20));

        jLabel48.setText("Dep. garantía:");
        jLabel48.setToolTipText("Depósito en Garantía");
        jP1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 270, 100, -1));

        jLabel49.setText("Cod.cliente:");
        jP1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 120, -1));

        jTCodCli.setEditable(false);
        jTCodCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodCli.setNextFocusableComponent(jTCo1);
        jTCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodCliFocusLost(evt);
            }
        });
        jTCodCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodCliKeyPressed(evt);
            }
        });
        jP1.add(jTCodCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 150, 20));

        jLabel50.setText("Jerárquia:");
        jLabel50.setToolTipText("Depósito en Garantía");
        jP1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 290, 100, -1));

        jTCtaPred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaPred.setNextFocusableComponent(jBContac);
        jTCtaPred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaPredFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaPredFocusLost(evt);
            }
        });
        jTCtaPred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaPredKeyPressed(evt);
            }
        });
        jP1.add(jTCtaPred, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 310, 100, 20));

        jCBloqCred.setBackground(new java.awt.Color(255, 255, 255));
        jCBloqCred.setText("Bloquear crédito");
        jCBloqCred.setNextFocusableComponent(jBGuar);
        jCBloqCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBloqCredKeyPressed(evt);
            }
        });
        jP1.add(jCBloqCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 130, 20));

        jComFormPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComFormPagFocusLost(evt);
            }
        });
        jComFormPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComFormPagActionPerformed(evt);
            }
        });
        jComFormPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComFormPagKeyPressed(evt);
            }
        });
        jP1.add(jComFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 230, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Función para procesar un cliente nuevo*/
    
    private int iNewCli(Connection con)
    {
        String text;
        int z;
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;          
        String      sQ;
        //correcion para que no se pueda poner 2 veses el mismo codigo
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        { 
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            JOptionPane.showMessageDialog(null, "La serie del cliente es obligatoria.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jComSer.grabFocus();
            return-1;
        }           
        if(jComSer.getSelectedItem().toString().compareTo("")!=0&&jTCodEmp.getText().compareTo("")!=0)
        {
           
            try
            {
                sQ = "SELECT * FROM emps WHERE ser = '" + jComSer.getSelectedItem().toString()+ "' and codemp = '"+jTCodEmp.getText()+"'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(rs.next())
                {
                    /*Coloca el borde rojo*/                               
                    jTCodEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El codigo del cliente: " + jComSer.getSelectedItem().toString()+jTCodEmp.getText() + " ya existe no se puede tener 2 codigos iguales.", "Codigo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTCodEmp.grabFocus();
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
        
        
//        /*Comprueba si el nombre del cliente existe en la base de datos con ese RFC*/                
//        try
//        {
//            sQ  = "SELECT nom FROM emps WHERE nom = '" + jTRazSoc.getText().trim() + "' AND rfc = '" + jTRFC.getText().trim() + "' AND '" + jTRFC.getText().trim() + "' <> ''";
//            st = con.createStatement();
//            rs = st.executeQuery(sQ);
//            /*Si hay datos, entonces*/
//            if(rs.next())
//            {         
//                //Cierra la base de datos
//                if(Star.iCierrBas(con)==-1)
//                    return -1;
//
//                /*Coloca el borde rojo*/                               
//                jTRazSoc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
//            
//                /*Mensajea y regresa error*/
//                JOptionPane.showMessageDialog(null, " El nombre del cliente: " + jTRazSoc.getText() + " ya existe para el RFC: " + jTRFC.getText() + ". Cambia de nombre de cliente o de RFC.", "Cliente existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                
//                return -1;                
//            } 
//        }
//        catch(SQLException expnSQL)
//        {
//            //Procesa el error y regresa
//            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
//            return -1;
//        }
        
        //razon social existe
        if(jTRazSoc.getText().compareTo("") != 0)
        {
            text=jTRazSoc.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "la razon social del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTRazSoc.grabFocus();                   
                    return -1;
            }
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la razon social es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTRazSoc.grabFocus();                   
                    return -1;
        }
//        //calle existe
//        if(jTCall.getText().compareTo("") != 0)
//        {
//            text=jTCall.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "la calle del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTCall.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "la calle es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCall.grabFocus();                   
//                    return -1;
//        }
//        
//        //falta colonia
//        if(jTCol.getText().compareTo("") == 0)
//        {   
//            JOptionPane.showMessageDialog(null, "la colonia es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCol.grabFocus();                   
//                    return -1;
//        }
//        //falta numero de exterior
//        if(jTNoExt.getText().compareTo("") != 0)
//        {
//            text=jTNoExt.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "El numero del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTNoExt.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El numero de exterior es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTNoExt.grabFocus();                   
//                    return -1;
//        }
//        //falta numero de cp
//        if(jTCP.getText().compareTo("") != 0)
//        {
//            text=jTCP.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "El codigo postal del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTCP.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El codigo postal es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCP.grabFocus();                   
//                    return -1;
//        }
//        //falta cuidad
//        if(jTCiu.getText().compareTo("") != 0)
//        {
//            text=jTCiu.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "La cuidad del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTCiu.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "la cuidad es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCiu.grabFocus();                   
//                    return -1;
//        }
//        //falta stado
//        if(jTEstad.getText().compareTo("") != 0)
//        {
//            text=jTEstad.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "El estado del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTEstad.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El estado es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTEstad.grabFocus();                   
//                    return -1;
//        }
//        //falta pais
//        if(jTPai.getText().compareTo("") != 0)
//        {
//            text=jTPai.getText();
//            z=text.length();
//            z--;
//            for(;z>=0;z--)
//            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
//            {
//                JOptionPane.showMessageDialog(null, "El país del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                jTPai.grabFocus();                   
//                    return -1;
//            }
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El país es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTPai.grabFocus();                   
//                    return -1;
//        }
        /*Si el RFC no es cadena vacia entonces*/
        if(jTRFC.getText().compareTo("") != 0)
        {
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario sea válido o no*/
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
                /*Válida si el RFC introducido por el usuario sea válido o no*/
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
                            
//            /*Comprueba que el RFC no exista en la base de datos*/
//            try
//            {
//                sQ  = "SELECT nom FROM emps WHERE rfc = '" + jTRFC.getText().trim() + "'";                
//                st  = con.createStatement();
//                rs  = st.executeQuery(sQ);
//                /*Si hay datos*/
//                if(rs.next())
//                {
//                    /*Coloca el borde rojo*/                               
//                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
//            
//                    /*Mensajea*/
//                    JOptionPane.showMessageDialog(null, "El RFC ya existe en la base de datos para: " + rs.getString("nom") + "", "RFC Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
//
//                    //Cierra la base de datos
//                    if(Star.iCierrBas(con)==-1)
//                        return -1;
//
//                    /*Pon el foco del teclado en el campo de RFC y regresa error*/
//                    jTRFC.grabFocus();                   
//                    return -1;
//                }
//            }
//            catch(SQLException expnSQL)
//            {
//                //Procesa el error y regresa
//                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
//                return -1;
//            }           
            
        }/*Fin de if(sRFC.compareTo("") != 0)*/                           
        //agregados para evitar avanzar sin llenar esos campos. correcion carlos
        else
        {   
            JOptionPane.showMessageDialog(null, "El RFC es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTRFC.grabFocus();                   
                    return -1;
        }
//        //falta calle
//        if(jTCall.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "la calle es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCall.grabFocus();                   
//                    return -1;
//        }
//        //falta colonia
//        if(jTCol.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "la colonia es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCol.grabFocus();                   
//                    return -1;
//        }
//        //falta numero de exterior
//        if(jTNoExt.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El numero de exterior es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTNoExt.grabFocus();                   
//                    return -1;
//        }
//        //falta cuidad
//        if(jTCiu.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "la cuidad es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTCiu.grabFocus();                   
//                    return -1;
//        }
//        //falta stado
//        if(jTEstad.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El estado es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTEstad.grabFocus();                   
//                    return -1;
//        }
//        //falta pais
//        if(jTPai.getText().compareTo("") != 0)
//        {
//        }
//        else
//        {   
//            JOptionPane.showMessageDialog(null, "El país es necesario para poder continuar" + "" + "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            jTPai.grabFocus();                   
//                    return -1;
//        }
                /*Si no se selecciono ser entonces la ser va a ser la vacia, caso contrario obtiene la ser*/
        String sSerInt;
        if(jComSer.getSelectedIndex()== -1)
            sSerInt = "";
        else
            sSerInt = jComSer.getSelectedItem().toString();
            
        /*Si no selecciono una serie y tampoco un código de cliente entonces*/
        if(jTCodEmp.getText().compareTo("")==0 && sSerInt.compareTo("")==0)        
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
            
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Escoge una serie o un código de cliente.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el combobox de sers y regresa error*/
            jComSer.grabFocus();
            return -1;
        }
                
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Cliente", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
            
            /*Regresa que todo fue bien*/
            return 0;                       
        }
        
        /*Lee si es persona física o moral*/
        String sPers    = "F";
        if(jRaMor.isSelected())
            sPers       = "M";
        
        /*Le el campo de desc*/
        String sDesc    = jTDesc.getText();
        
        /*Si el descuento es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sDesc.compareTo("")==0)
            sDesc       = "0";

        /*Le el campo de días de crédito*/
        String sDiaCred = jTDCred.getText();
        
        /*Si el campo es cadena vacia entonces que sea 0*/
        if(sDiaCred.compareTo("")==0)
            sDiaCred    = "0";
        
        /*Le el campo de límite de crédito*/
        String sLimCred = jTLimCred.getText();
        
        /*Si el límite de crédito es cadena vacia ponerlo en 0 para que la base de datosla reciba*/
        if(sLimCred.compareTo("")==0)
            sLimCred    = "0";
        
        /*Si tiene el signo de dollar el límite de crédito quitaselo*/
        sLimCred        = sLimCred.replace("$", "");
        
        /*Si tiene coma el límite de crédito quitaselo*/
        sLimCred        = sLimCred.replace(",", "");
        
        /*Lee el checkbox de bloq*/
        String sBloq    = "0";
        if(jCBloq.isSelected())
            sBloq       = "1";
        
        /*Obtiene el código de la empesa que ingreso el usuario*/
        String sEmpTmp  = jTCodEmp.getText();                        
        
        /*Obtiene el método de pago*/
        String sMetPag  = jComFormPag.getSelectedItem().toString();
        
        /*Si el método de pago es cadena vacia entonces el método de pago será no identificable*/
        if(sMetPag.compareTo("")==0)
            sMetPag     = "No Identificado";
        
        /*Obtiene la cuenta de pago*/
        String sCta     = jTCta.getText();
        
        /*Si la cuenta de pago es cadena vacia entonces la cuenta de pago será 0000*/
        if(sCta.compareTo("")==0)
            sCta        = "0000";
        
        /*Lee la lista de precios*/
        String sList    = jComList.getSelectedItem().toString();
        
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
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación válida.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el control y regresa error*/
                jTClas.grabFocus();                       
                return -1;
            }
        }

        /*Obtiene la fecha de cumpleaños*/
        String sFCumple;
        Date fe                     =  jDCumple.getDate();
        SimpleDateFormat sdf        = new SimpleDateFormat("yyy-MM-dd");
                                
        /*Intenta asignar la fecha a la variable*/
        try
        {
            sFCumple                = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoin)
        {
            /*La fecha será cadena vacia ya que con esto será una fecha no válida*/
            sFCumple                = "";
        }                        
        
        /*Determina si se le va a poder vender con otra moneda distinta a la nacional*/
        String  sOtrMon;
        if(jCOtraMon.isSelected())
            sOtrMon = "1";
        else
            sOtrMon = "0";        
        
        /*Determina si se le va a poder cotizar con otra moneda distinta a la nacional*/
        String  sOtrMonC;
        if(jCOtraMon.isSelected())
            sOtrMonC = "1";
        else
            sOtrMonC = "0";
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return -1;

        //Si la serie es nula entonces obtiene la serie del control
        if(sSer == null)
            sSer    = jComSer.getSelectedItem().toString();
        
        /*Obtiene el consecutivo del cliente*/                    
        String sConsec  = "";
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = 'EMP' AND ser = '" + sSer + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sConsec         = rs.getString("consec");                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;
        }            
        
        /*Genera el código de la cliente ya sea el consecutivo o el personalizado*/        
        String sEmp;
        if(sEmpTmp.compareTo("")==0)        
            sEmp        = sConsec;         
        else        
            sEmp        = sEmpTmp;         
        
        /*Aumenta uno el consecutivo del cliente*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'EMP' AND ser = '" + sSerInt.replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;
         }                                                        
        
        /*Si la tabla de domicilios tiene datos entonces*/
        if(jTab.getRowCount()>0)
        {
            /*Recorre toda la tabla de domicilios*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Inserta en la base de datos el domicilio*/
                try 
                {            
                    sQ = "INSERT INTO domentcli (codemp,                                                        tel,                                                               lada,                                                             exten,                                                            cel,                                                              telper1,                                                          telper2,                                                          calle,                                                            col,                                                              noext,                                                            noint,                                                              cp,                                                                ciu,                                                                estad,                                                            pai,                                                               co1,                                                               co2,                                                               co3,                                                               estac,                 sucu,                 nocaj) "
                                  + "VALUES('" + sSer.replace("'", "''") + sEmp.replace("'", "''") + "','" +    jTab.getValueAt(x, 0).toString().replace("'", "''") + "', '" +  jTab.getValueAt(x, 1).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 2).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 3).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 5).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 6).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 7).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 8).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 9).toString().replace("'", "''") + "', '" +   jTab.getValueAt(x, 10).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 11).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 12).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 13).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 15).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 16).toString().replace("'", "''") + "', '" + Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                                
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
        }
        
        //Determina si tiene o no bloqueado el crédito
        String sBloqCred    = "0";
        if(jCBloqCred.isSelected())
            sBloqCred       = "1";
        
        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO emps(nom,                                             tel,                                            telper1,                                            telper2,                                               calle,                                          col,                                                CP,                                             ciu,                                            estad,                                          pai,                                            rfc,                                            descu,          co1,                                            co2,                                            co3,                                            pagweb1,                                            pagweb2,                                        contac,                                             observ,                                             codemp,                             noext,                                              noint,                                              diacred,            limtcred,            bloq,      falt,       puest,                                              contact2,                                           puest2,                                         estac,                                      pers,                               metpag,                                 cta,                                list,             codclas,                                          contac3,                                telcon3,                                telper3,                                telper33,                               contac4,                                telcon4,                                    telper4,                                telper44,                               contac5,                                telcon5,                                telper5,                                telper55,                               contac6,                                telcon6,                                telper6,                                telper66,                               contac7,                                telcon7,                                telper7,                                telper77,                               contac8,                                telcon8,                                telper8,                                telper88,                               contac9,                                telcon9,                                telper9,                                telper99,                               contac10,                               telcon10,                               telper10,                               telper100,                                  revis,                                          pags,                                           sucu,                                     nocaj,                                    ser,                                     cumple,                                 otramon,       otramonc,          curp,                                             vend,                                           lada,                                           exten,                                              cel,                                          giro,                                                zon,                                                 ctaconta,                             deposit,                                                              banc,                                                   clavbanc,                                                 clasjera,                                              bloqlimcred)  " + 
                    "VALUES('" + jTRazSoc.getText().replace("'", "''") + "','" +    jTTel.getText().replace("'", "''") + "','" +    jTTelPer1.getText().replace("'", "''") + "','" +    jTTelPer2.getText().replace("'", "''") + "','" +       jTCall.getText().replace("'", "''") + "','" +   jTCol.getText().replace("'", "''") + "','" +        jTCP.getText().replace("'", "''") + "','" +     jTCiu.getText().replace("'", "''") + "','" +    jTEstad.getText().replace("'", "''") + "','" +  jTPai.getText().replace("'", "''") + "','" +    jTRFC.getText().replace("'", "''") + "','" +    sDesc + "','" + jTCo1.getText().replace("'", "''") + "','" +    jTCo2.getText().replace("'", "''") + "','" +    jTCo3.getText().replace("'", "''") + "','" +    jTPag1.getText().replace("'", "''") + "','" +       jTPag2.getText().replace("'", "''") + "','" +   jTContac.getText().replace("'", "''") +"','" +      jTAObserv.getText().replace("'", "''") + "','" +    sEmp.replace("'", "''") + "','" +   jTNoExt.getText().replace("'", "''") + "','" +      jTNoInt.getText().replace("'", "''") + "','" +      sDiaCred + "','" +  sLimCred + "',  " +  sBloq + ", now(), '" + jTPues.getText().replace("'", "''") + "','" +       jTContac2.getText().replace("'", "''") + "','" +    jTPues2.getText().replace("'", "''") + "','" +  Login.sUsrG.replace("'", "''") + "', '" +   sPers.replace("'", "''") + "', '" + sMetPag.replace("'", "''") + "', '" +   sCta.replace("'", "''") + "', " +   sList + ",   '" + jTClas.getText().replace("'", "''") + "', '" +    sContac3.replace("'", "''") + "', '" +  sTelCon3.replace("'", "''") + "', '" +  sTelPer3.replace("'", "''") + "', '" +  sTelPer33.replace("'", "''") + "', '" + sContac4.replace("'", "''") + "', '" +  sTelCon4.replace("'", "''") + "', '" +      sTelPer4.replace("'", "''") + "', '" +  sTelPer44.replace("'", "''") + "', '" + sContac5.replace("'", "''") + "', '" +  sTelCon5.replace("'", "''") + "', '" +  sTelPer5.replace("'", "''") + "', '" +  sTelPer55.replace("'", "''") + "', '" + sContac6.replace("'", "''") + "', '" +  sTelCon6.replace("'", "''") + "', '" + sTelPer6.replace("'", "''") + "', '" +   sTelPer66.replace("'", "''") + "', '" + sContac7.replace("'", "''") + "', '" +  sTelCon7.replace("'", "''") + "', '" +  sTelPer7.replace("'", "''") + "', '" +  sTelPer77.replace("'", "''") + "', '" + sContac8.replace("'", "''") + "', '" +  sTelCon8.replace("'", "''") + "', '" +  sTelPer8.replace("'", "''") + "', '" +  sTelPer88.replace("'", "''") + "', '" + sContac9.replace("'", "''") + "', '" +  sTelCon9.replace("'", "''") + "', '" +  sTelPer9.replace("'", "''") + "', '" +  sTelPer99.replace("'", "''") + "', '" + sContac10.replace("'", "''") + "', '" + sTelCon10.replace("'", "''") + "','" +  sTelPer10.replace("'", "''") + "', '" + sTelPer100.replace("'", "''") + "', '" +    jTRev.getText().replace("'", "''") + "', '" +   jTPag.getText().replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" + sSerInt.replace("'", "''") + "', '" +    sFCumple.replace("'", "''") + "', " +   sOtrMon + ", " + sOtrMonC + ", '" + jTCURP.getText().replace("'", "''") + "','" +   jTVend.getText().replace("'", "''") + "', '" +  jTLada.getText().replace("'", "''") + "', '" +  jTExten.getText().replace("'", "''") + "', '" +     jTCel.getText().replace("'", "''") + "', '" + jTGir.getText().replace("'", "''").trim() + "', '" + jTZon.getText().replace("'", "''").trim() + "', '" + jTCtaConta.getText().trim() + "', " + jTDepGar.getText().replace("$", "").replace(",", "").trim() + ", '" + jTBanc.getText().trim().replace("'", "''") + "', '" +   jTClavBanc.getText().trim().replace("'", "''") + "', '" + jTJera.getText().trim().replace("'", "''") + "', " +   sBloqCred + ")";                                
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
            sQ = "SELECT rut FROM clasjeracli WHERE rut = '" + jTJera.getText().trim() + "'";
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
                sQ = "INSERT INTO clasjeracli(  clas,   rut,                                                                            estac,                                         sucu,                                    nocaj) " + 
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
        
        /*Inserta en log de clientes*/
        try 
        {            
            sQ = "INSERT INTO logemps(  emp,                                                                    nom,                                                    ser,                                    accio,       estac,                                         sucu,                                           nocaj,                                          falt) " + 
                          "VALUES('" +  sSerInt.replace("'", "''") + sEmp.replace("'", "''") + "','" +          jTRazSoc.getText().replace("'", "''") + "',  '" +       sSerInt.replace("'", "''") + "',        'A', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
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
        
        /*Agrega el nombre de la cliente en la tabla de cliente del otro formulario solo si así debe ser*/
        if(jTabEmp!=null)
        {
            DefaultTableModel te    = (DefaultTableModel)jTabEmp.getModel();
            Object nu[]             = {Clients.iContFi, sSer + sEmp, sSer, jTRazSoc.getText()};
            te.addRow(nu);
        }            

        /*Aumenta en uno el contador de filas en uno*/
        ++Clients.iContFi;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cliente: " + jTRazSoc.getText() + " guardado con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Sal del formulario*/
        this.dispose();        
        Star.gClient    = null;
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iNewCli(Connection con)*/
    
    
    /*Función para actualizar un cliente ya existente*/
    private int iActuCli(Connection con)
            
    {    
        String text;
        int z;
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                        
        String      sQ;
        //razon social existe
        
        if(jTRazSoc.getText().compareTo("") != 0)
        {
            text=jTRazSoc.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "la razon social del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTRazSoc.grabFocus();                   
                    return -1;
            }
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "la razon social es necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTRazSoc.grabFocus();                   
                    return -1;
        }
        //calle existe
        if(jTCall.getText().compareTo("") != 0)
        {
            text=jTCall.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "la calle del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTCall.grabFocus();                   
                    return -1;
            }
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
            text=jTNoExt.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "El numero del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTNoExt.grabFocus();                   
                    return -1;
            }
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
            text=jTCP.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "El codigo postal del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTCP.grabFocus();                   
                    return -1;
            }
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
            text=jTCiu.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "La cuidad del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTCiu.grabFocus();                   
                    return -1;
            }
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
            text=jTEstad.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "El estado del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTEstad.grabFocus();                   
                    return -1;
            }
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
            text=jTPai.getText();
            z=text.length();
            z--;
            for(;z>=0;z--)
            if((text.charAt(z) == '|')  || (text.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "El país del cliente no puede tener los caracteres Pipe (|) o Negación lógica (¬). Favor de eliminarlos para poder continuar." + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTPai.grabFocus();                   
                    return -1;
            }
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
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario es válido o no*/
                Star.bValRFC    = false;
                Star.vValRFC(jTRFC.getText(), true, jTRFC, true);
                        
                /*Si no es válido entonces*/
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
                Star.bValRFC    = false;
                Star.vValRFC(jTRFC.getText(), false, jTRFC, true);
                
                /*Si no es válido entonces*/
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
                        
            /*Comprueba que el RFC no exista ya en la base de datos mientras no sea el mismo*/
            try
            {
                sQ = "SELECT nom FROM emps WHERE rfc = '" + jTRFC.getText() + "' AND rfc != '" + sRFCOri + "'";            
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {                    
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El RFC ya existe en la base de datos para: " + rs.getString("nom"), "RFC existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;

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

            /*Mensajea y regresa error*/
            return -1;                        
        }
        
        /*Le el campo de descuento*/
        String sDesc        = jTDesc.getText();
        
        /*Lee si es pers física o moral*/
        String sPers;
        if(jRaMor.isSelected())
            sPers           = "M";
        else
            sPers           = "F";
        
        /*Si el desc es cadena vacia ponerlo en 0 para que la base de datosla reciba*/
        if(sDesc.compareTo("")==0)
            sDesc           = "0";

        /*Le el campo de días de crédito*/
        String sDiaCred     = jTDCred.getText();
        
        /*Si el campo es cadena vacia entonces que sea 0*/        
        if(sDiaCred.compareTo("")==0)
            sDiaCred        = "0";
        
        /*Le el campo de límite de crédito*/
        String sLimCred     = jTLimCred.getText();
        
        /*Si el límite de crédito es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sLimCred.compareTo("")==0)
            sLimCred        = "0";
        
        /*Si tiene el signo de dollar el límite de crédito quitaselo*/
        sLimCred            = sLimCred.replace("$", "").replace(",", "");
        
        /*Lee el checkbox de bloqueado*/
        String sBloq;
        if(jCBloq.isSelected())
            sBloq           = "1";
        else
            sBloq           = "0";
        
        /*Obtiene el método de pago*/
        String sMetPag      = jComFormPag.getSelectedItem().toString();
        
        /*Si el método de pago es cadena vacia entonces el método de pago será no identificable*/
        if(sMetPag.compareTo("")==0)
            sMetPag         = "No Identificado";
        
        /*Obtiene la cuenta de pago*/
        String sCta         = jTCta.getText();
        
        /*Si la cuenta de pago es cadena vacia entonces la cuenta de pago será 0000*/
        if(sCta.compareTo("")==0)
            sCta = "0000";
        
        /*Obtiene la lista de precios*/
        String sList        = jComList.getSelectedItem().toString();
        
        /*Obtiene el código de la clasificación*/
        String sClas        = jTClas.getText();
        
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
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación válida.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el control y regresa error*/
                jTClas.grabFocus();                                
                return -1;
            }
        }
        
        /*Obtiene la fecha de cumpleaños*/
        String sFCumple;
        Date fe                     =  jDCumple.getDate();
        SimpleDateFormat sdf        = new SimpleDateFormat("yyy-MM-dd");
        
        /*Intenta asignar la fecha a la variable*/
        try
        {
            sFCumple                = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoint)
        {
            /*La fecha será cadena vacia ya que con esto será una fecha no válida*/
            sFCumple                = "";
        }                        

        /*Determina si se le va a poder vender con otra moneda distinta a la nacional*/
        String  sOtrMon;
        if(jCOtraMon.isSelected())
            sOtrMon = "1";
        else
            sOtrMon = "0";
        
        /*Determina si se le va a poder cotizar con otra moneda distinta a la nacional*/
        String  sOtrMonC;
        if(jCCotOtrMon.isSelected())
            sOtrMonC = "1";
        else
            sOtrMonC = "0";
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return -1;
        
        /*Borra toda la tabla de asociaciones de domicilios*/
        try 
        {            
            sQ = "DELETE FROM domentcli WHERE codemp = '" + sCliG + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return -1;
        }                                                        

        /*Si la tabla de domicilios tiene datos entonces*/
        if(jTab.getRowCount()>0)
        {
            /*Recorre toda la tabla de domicilios*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Inserta en la base de datos el domicilio*/
                try 
                {            
                    sQ = "INSERT INTO domentcli (codemp,            tel,                                                            lada,                                                          exten,                                                         cel,                                                              telper1,                                                    telper2,                                                       calle,                                                         col,                                                           noext,                                                         noint,                                                           cp,                                                             ciu,                                                            estad,                                                          pai,                                                            co1,                                                            co2,                                                            co3,                                                            estac,                 sucu,                 nocaj) "
                                  + "VALUES('" + sCliG + "','" +    jTab.getValueAt(x, 0).toString().replace("'", "''") + "', '" +  jTab.getValueAt(x, 1).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 2).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 3).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 5).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 6).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 7).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 8).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 9).toString().replace("'", "''") + "', '" +   jTab.getValueAt(x, 10).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 11).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 12).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 13).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 15).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 16).toString().replace("'", "''") + "', '" + Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                                
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
        }
        
        /*Obtiene la clasificación jerarquica*/
        String sJera    = jTJera.getText().trim().replace("'", "''");
        
        /*Si no comienza con clasificaciones entonces agregarselo*/
        if(!sJera.startsWith("Clasificaciones|"))
            sJera       = "Clasificaciones|" + sJera;

        //Determina si tiene o no bloqueado el crédito
        String sBloqCred    = "0";
        if(jCBloqCred.isSelected())
            sBloqCred       = "1";
        
        /*Modifica los valores de la empresa en la base de datos*/
        try 
        {
            /*Crea la consulta*/
            sQ = "UPDATE emps SET " + 
                    "nom                = '" + jTRazSoc.getText().replace("'", "''") + "', " + 
                    "tel                = '" + jTTel.getText().replace("'", "''") + "', " + 
                    "banc               = '" + jTBanc.getText().replace("'", "''") + "', " + 
                    "ctapred            = '" + jTCtaPred.getText().replace("'", "''") + "', " + 
                    "clavbanc           = '" + jTClavBanc.getText().replace("'", "''") + "', " + 
                    "entel2             = '" + sTelPer1En.replace("'", "''") + "', " + 
                    "bloqlimcred        = " + sBloqCred + ", " + 
                    "ctaconta           = '" + jTCtaConta.getText().trim() + "', " + 
                    "deposit            = " + jTDepGar.getText().replace("$", "").replace(",", "").trim() + ", " + 
                    "cel                = '" + jTCel.getText().replace("'", "''") + "', " + 
                    "encel              = '" + sCelEn.replace("'", "''") + "', " + 
                    "exten              = '" + jTExten.getText().replace("'", "''") + "', " + 
                    "clasjera           = '" + sJera + "', " + 
                    "ennoexten          = '" + sExtEn.replace("'", "''") + "', " + 
                    "telper1            = '" + jTTelPer1.getText().replace("'", "''") + "', " + 
                    "entel3             = '" + sTelPer2En.replace("'", "''") + "', " + 
                    "lada               = '" + jTLada.getText().replace("'", "''") + "', " + 
                    "enlada             = '" + sLadaEn.replace("'", "''") + "', " + 
                    "telper2            = '" + jTTelPer2.getText().replace("'", "''") + "', " + 
                    "vend               = '" + jTVend.getText().replace("'", "''") + "', " + 
                    "calle              = '" + jTCall.getText().replace("'", "''") + "', " + 
                    "encall             = '" + sCallEn.replace("'", "''") + "', " + 
                    "otramon            =  " + sOtrMon + ", " + 
                    "otramonc           =  " + sOtrMonC + ", " + 
                    "col                = '" + jTCol.getText().replace("'", "''") + "', " + 
                    "encol              = '" + sColEn.replace("'", "''") + "', " + 
                    "CP                 = '" + jTCP.getText().replace("'", "''") + "', " + 
                    "encp               = '" + sCPEn.replace("'", "''") + "', " + 
                    "ciu                = '" + jTCiu.getText().replace("'", "''") + "', " + 
                    "enciu              = '" + sCiuEn.replace("'", "''") + "', " + 
                    "estad              = '" + jTEstad.getText().replace("'", "''") + "', " + 
                    "enestad            = '" + sEstadEn.replace("'", "''") + "', " + 
                    "pai                = '" + jTPai.getText().replace("'", "''") + "', " + 
                    "enpai              = '" + sPaiEn.replace("'", "''") + "', " + 
                    "RFC                = '" + jTRFC.getText().replace("'", "''") + "', " + 
                    "descu              = '" + sDesc + "', " + 
                    "co1                = '" + jTCo1.getText().replace("'", "''") + "', " + 
                    "co2                = '" + jTCo2.getText().replace("'", "''") + "', " + 
                    "co3                = '" + jTCo3.getText().replace("'", "''") + "', " + 
                    "enco1              = '" + sCo1En.replace("'", "''") + "', " + 
                    "enco2              = '" + sCo2En.replace("'", "''") + "', " + 
                    "enco3              = '" + sCo3En.replace("'", "''") + "', " + 
                    "pagweb1            = '" + jTPag1.getText().replace("'", "''") + "', " + 
                    "pagweb2            = '" + jTPag2.getText().replace("'", "''") + "', " + 
                    "contac             = '" + jTContac.getText().replace("'", "''") + "', " + 
                    "observ             = '" + jTAObserv.getText().replace("'", "''") + "', " + 
                    "curp               = '" + jTCURP.getText().replace("'", "''") + "', " + 
                    "noext              = '" + jTNoExt.getText().replace("'", "''") + "', " + 
                    "ennoext            = '" + sNoExtEn.replace("'", "''") + "', " + 
                    "cumple             = '" + sFCumple.replace("'", "''") + "', " + 
                    "noint              = '" + jTNoInt.getText().replace("'", "''") + "', " + 
                    "giro               = '" + jTGir.getText().replace("'", "''").trim() + "', " + 
                    "zon                = '" + jTZon.getText().replace("'", "''").trim() + "', " + 
                    "ennoint            = '" + sIntEn.replace("'", "''") + "', " + 
                    "diacred            = '" + sDiaCred + "', " + 
                    "puest              = '" + jTPues.getText().replace("'", "''") + "', " + 
                    "contact2           = '" + jTContac2.getText().replace("'", "''") + "', " + 
                    "puest2             = '" + jTPues2.getText().replace("'", "''") + "', " + 
                    "limtcred           = '" + sLimCred + "', " + 
                    "bloq               = " + sBloq + ", " + 
                    "fmod               = now(), " + 
                    "estac              = '" + Login.sUsrG.replace("'", "''") + "', " + 
                    "pers               = '" + sPers.replace("'", "''") + "', " + 
                    "metpag             = '" + sMetPag.replace("'", "''") + "', " + 
                    "cta                = '" + sCta.replace("'", "''") + "', " + 
                    "list               = " + sList + ", " + 
                    "codclas            = '" + sClas.replace("'", "''") + "', " + 
                    "contac3            = '" + sContac3.replace("'", "''") + "', " + 
                    "contac4            = '" + sContac4.replace("'", "''") + "', " + 
                    "contac5            = '" + sContac5.replace("'", "''") + "', " + 
                    "contac6            = '" + sContac6.replace("'", "''") + "', " + 
                    "contac7            = '" + sContac7.replace("'", "''") + "', " + 
                    "contac8            = '" + sContac8.replace("'", "''") + "', " + 
                    "contac9            = '" + sContac9.replace("'", "''") + "', " + 
                    "contac10           = '" + sContac10.replace("'", "''") + "', " + 
                    "telcon3            = '" + sTelCon3.replace("'", "''") + "', " + 
                    "telcon4            = '" + sTelCon4.replace("'", "''") + "', " + 
                    "telcon5            = '" + sTelCon5.replace("'", "''") + "', " + 
                    "telcon6            = '" + sTelCon6.replace("'", "''") + "', " + 
                    "telcon7            = '" + sTelCon7.replace("'", "''") + "', " + 
                    "telcon8            = '" + sTelCon8.replace("'", "''") + "', " + 
                    "telcon9            = '" + sTelCon9.replace("'", "''") + "', " + 
                    "telcon10           = '" + sTelCon10.replace("'", "''") + "', " +
                    "telper3            = '" + sTelPer3.replace("'", "''") + "', " +
                    "telper4            = '" + sTelPer4.replace("'", "''") + "', " +
                    "telper5            = '" + sTelPer5.replace("'", "''") + "', " +
                    "telper6            = '" + sTelPer6.replace("'", "''") + "', " +
                    "telper7            = '" + sTelPer7.replace("'", "''") + "', " +
                    "telper8            = '" + sTelPer8.replace("'", "''") + "', " +
                    "telper9            = '" + sTelPer9.replace("'", "''") + "', " +
                    "telper10            = '" + sTelPer10.replace("'", "''") + "', " +
                    "telper33           = '" + sTelPer33.replace("'", "''") + "', " + 
                    "telper44           = '" + sTelPer44.replace("'", "''") + "', " + 
                    "telper55           = '" + sTelPer55.replace("'", "''") + "', " + 
                    "telper66           = '" + sTelPer66.replace("'", "''") + "', " + 
                    "telper77           = '" + sTelPer77.replace("'", "''") + "', " + 
                    "telper88           = '" + sTelPer88.replace("'", "''") + "', " + 
                    "telper99           = '" + sTelPer99.replace("'", "''") + "', " + 
                    "sucu               = '" + Star.sSucu.replace("'", "''")+ "', " +
                    "nocaj              = '" + Star.sNoCaj.replace("'", "''") + "', " +
                    "telper100          = '" + sTelPer100.replace("'", "''") + "', " + 
                    "revis              = '" + jTRev.getText().replace("'", "''") + "', " + 
                    "pags               = '" + jTPag.getText().replace("'", "''") + "' WHERE CONCAT_WS('', ser, codemp) = '" + sCliG + "'";            
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
            sQ = "SELECT rut FROM clasjeracli WHERE rut = '" + jTJera.getText().trim() + "'";
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
                sQ = "INSERT INTO clasjeracli(  clas,   rut,                                                                        estac,                                         sucu,                                    nocaj) " + 
                                        "VALUES('',     'Clasificaciones|" + jTJera.getText().replace("'", "''").trim() + "', '" +  Login.sUsrG.replace("'", "''") +"',  '" +      Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                 //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return -1;
            }
            
        }/*Fin de if(!bSi)*/                    
        
        /*Inserta en log de clientes*/
        try 
        {            
            sQ = "INSERT INTO logemps(  emp,                 nom,                                ser,                            accio,       estac,                                         sucu,                                           nocaj,                                      falt) " + 
                          "VALUES('" +  sCliG + "','" +      jTRazSoc.getText() + "',  '" +      sSer.replace("'", "''") + "',   'A', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
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
        JOptionPane.showMessageDialog(null, "Cliente: " + jTRazSoc.getText() + " modificado con éxito.", "Éxito al modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Cierra el formlario*/
        this.dispose();
        Star.gClient    = null;
                
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iActuCli(Connection con)*/
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        /*Declara variabels de bases de datos*/
        String      sQ;
        Statement   st;
        ResultSet   rs;
                
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
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                
                    /*Coloca el borde rojo*/                               
                    jTZon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "La Zona: " + jTZon.getText() + " no existe.", "Zonas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
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
        
        /*Comprueba si el vendedor existe en caso de que alla escrito uno*/
        if(jTVend.getText().compareTo("")!=0)
        {
            try
            {
                sQ = "SELECT estac FROM estacs WHERE estac = '" + jTVend.getText().trim() + "' AND vend = 1";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no existe*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El vendedor: " + jTVend.getText() + " no existe.", "Vendedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTVend.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
        }/*Fin de if(jTVend.getText().compareTo("")!=0)*/                      
        
        /*Comprueba si la razón social ya existe*/
        try
        {
            sQ = "SELECT nom FROM emps WHERE nom = '" + jTRazSoc.getText().trim() + "' AND CONCAT_WS('', ser, codemp) <> '" + jTCodCli.getText().trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces mensajea solamente*/
            if(rs.next())                            
                JOptionPane.showMessageDialog(null, "Como recordatorio esta razón social ya esta dada de alta pero si es posible continuar.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Si el código del cliente no es vacio entonces*/
        if(jTCodCli.getText().trim().compareTo("")!=0)
        {
            /*Procesa al cliente existente*/
            if(iActuCli(con)==-1)
                return;
        }
        else
        {
            /*Procesa al cliente nuevo*/
            if(iNewCli(con)==-1)
                return;
        }
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga los clientes de la base de datos y cargalos en la tabla del otro formulario*/
        Star.vCargCli(jTabEmp, "");
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usuario si esta seguro de abandonar la alta del cliente*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la alta del cliente?", "Salir Alta Cliente", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Llama al recolector de basura*/
        System.gc();        
        
        /*Cierra la forma*/
        this.dispose();
        Star.gClient    = null;
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de razón social*/
    private void jTRazSocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRazSocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRazSocKeyPressed

    
    /*Cuando se presiona un botón en el campo de edición call*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de col*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de cp*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el campo de edición de pai*/
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

    
    /*Cuando se presiona una tecla en el campo de edición de desc*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición del teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de página web1*/
    private void jTPag1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de página web2*/
    private void jTPag2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el text area de observ*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de razón social*/
    private void jTRazSocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRazSocFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRazSoc.setSelectionStart(0);jTRazSoc.setSelectionEnd(jTRazSoc.getText().length());                
        
    }//GEN-LAST:event_jTRazSocFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);        
        jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de call*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de col*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
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

    
    /*Cuando se gana el foco del teclado en el campo de edición de pai*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());        
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de desc*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());        
        
    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de co1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());        
        
    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de co2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());        
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de co3*/
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

    
    /*Cuando se gana el foco del teclado en el campo de area observ*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de contac*/
    private void jTContacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTContac.setSelectionStart(0);jTContac.setSelectionEnd(jTContac.getText().length());                
        
    }//GEN-LAST:event_jTContacFocusGained

    
    /*Cuando se presiona una tecla en el campo de contac*/
    private void jTContacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTContacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTContacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de tel personal 1*/
    private void jTTelPer1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer1.setSelectionStart(0);jTTelPer1.setSelectionEnd(jTTelPer1.getText().length());        
        
    }//GEN-LAST:event_jTTelPer1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tel personal 2*/
    private void jTTelPer2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer2.setSelectionStart(0);jTTelPer2.setSelectionEnd(jTTelPer2.getText().length());        
        
    }//GEN-LAST:event_jTTelPer2FocusGained

    
    /*Cuando se presiona una tecla en el campo de tel personal 1*/
    private void jTTelPer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer1KeyPressed

    
    /*Cuando se presiona una tecla en el tel personal 2*/
    private void jTTelPer2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer2KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTRFC.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRFC.getText().compareTo("")!=0)
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usurio*/
        String sLec = jTRFC.getText();
        
        /*Si es cadena vacia entonces solo regresar*/
        if(sLec.compareTo("")==0)
            return;
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sLec.length()> 21)
            jTRFC.setText(jTRFC.getText().substring(0, 21));
        
        /*Convierte los carácteres escritos en mayúsculas*/
        sLec = sLec.toUpperCase();
        
        /*Colocalos en el campo*/
        jTRFC.setText(sLec); 
        
        /*Si es persona moral entonces valida el RFC*/
        if(jRaMor.isSelected())
            Star.vValRFC(sLec, true, jTRFC, true);                                    
        /*Else, es física entonces validalo igual*/
        else
            Star.vValRFC(sLec, false, jTRFC, true);                        
               
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de razón social*/
    private void jTRazSocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRazSocFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTRazSoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRazSoc.getText().compareTo("")!=0)
            jTRazSoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
      
    }//GEN-LAST:event_jTRazSocFocusLost
        
    
    /*Cuando se pierde el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel.getText().length()> 255)
            jTTel.setText(jTTel.getText().substring(0, 255));
                       
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de texto de teléfono personal 1*/
    private void jTTelPer1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPer1.getText().length()> 255)
            jTTelPer1.setText(jTTelPer1.getText().substring(0, 255));
             
    }//GEN-LAST:event_jTTelPer1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de texto de teléfono personal 2*/
    private void jTTelPer2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTelPer2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPer2.getText().length()> 255)
            jTTelPer2.setText(jTTelPer2.getText().substring(0, 255));
                
    }//GEN-LAST:event_jTTelPer2FocusLost

    
    /*Cuando se gana el foco del teclado en el campo de edición de call*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de col*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo es cadena vacia entonces regresa aquí*/
        if(jTCP.getText().compareTo("")==0)
            return;
        
//        /*Geolocalización en un thread*/
//        (new Thread()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    /*Manda la petición a la página de internet*/
//                    java.net.URL url = new java.net.URL("http://ziptasticapi.com" + jTCP.getText().trim());
//                    java.net.URLConnection yc = url.openConnection();
//                    
//                    /*Objeto para leer los datos obtenidos*/
//                    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(yc.getInputStream()));
//                    
//                    /*Lee los datos obtenidos*/
//                    String sInput    = "";
//                    String sInp;
//                    while((sInp = in.readLine()) != null) 
//                        sInput   += sInp;
//                    
//                    /*Cierra el stream*/
//                    in.close();
//                    
//                    /*Quita lo que no se necesita de la cadena*/
//                    sInput  = sInput.replace("{", "").replace("}", "").replace("\"", "");
//                    
//                    /*Objetos que contienen el pais, estado y ciudad*/
//                    String sPai     = "";
//                    String sCiu     = "";
//                    String sEstad   = "";
//                    
//                    /*Tokeniza para obtener el pais, estado y ciudad*/                    
//                    java.util.StringTokenizer stk = new java.util.StringTokenizer(sInput,",");
//                    
//                    /*Tokeniza para separar las dos cadenas por : del pais*/
//                    java.util.StringTokenizer stk2 = new java.util.StringTokenizer(stk.nextToken(), ":");
//
//                    /*Si tiene 2 elementos entonces obtiene el pais que esta en la segunda posición*/
//                    if(stk2.countTokens()==2)
//                    {
//                        stk2.nextToken();
//                        sPai    = stk2.nextToken();
//                    }                    
//                    
//                    /*Si no hay mas tokens entonces regresa*/
//                    if(!stk.hasMoreElements())
//                        return;
//                    
//                    /*Tokeniza para separar las dos cadenas por : del estado*/
//                    stk2 = new java.util.StringTokenizer(stk.nextToken(), ":");
//
//                    /*Si tiene 2 elementos entonces obtiene el estado que esta en la segunda posición*/
//                    if(stk2.countTokens()==2)
//                    {
//                        stk2.nextToken();
//                        sEstad  = stk2.nextToken();
//                    }                    
//                    
//                    /*Si no hay mas tokens entonces regresa*/
//                    if(!stk.hasMoreElements())
//                        return;
//                    
//                    /*Tokeniza para separar las dos cadenas por : la ciudad*/
//                    stk2 = new java.util.StringTokenizer(stk.nextToken(), ":");
//
//                    /*Si tiene 2 elementos entonces obtiene la ciudad que esta en la segunda posición*/
//                    if(stk2.countTokens()==2)
//                    {
//                        stk2.nextToken();
//                        sCiu    = stk2.nextToken();
//                    }                    
//                    
//                    /*Coloca los valores en su lugar*/                    
//                    jTCiu.setText   (sCiu);
//                    jTPai.setText   (sPai);
//                    jTEstad.setText (sEstad);
//                }
//                catch(Exception expnExcep)
//                {
//                    //Procesa el error
//                    Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());                    
//                }                                    
//            }
//            
//        }).start();                    
//            
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCiu.getText().length()> 255)
            jTCiu.setText(jTCiu.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de estad*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEstad.getText().length()> 255)
            jTEstad.setText(jTEstad.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el campode edición de pai*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPai.getText().length()> 255)
            jTPai.setText(jTPai.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de desc*/
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
        catch(NumberFormatException expNumberForm)  
        {  
            jTDesc.setText("");
        }  
                
    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de co1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo1.getText().toLowerCase().contains("@") || !jTCo1.getText().toLowerCase().contains(".")) && jTCo1.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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

    
    /*Cuando se pierde el foco del teclado en el campo de edición de correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo2.getText().toLowerCase().contains("@") || !jTCo2.getText().toLowerCase().contains(".")) && jTCo2.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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

    
    /*Cuando se pierde el foco del teclado en el campo de edición de correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo3.getText().toLowerCase().contains("@") || !jTCo3.getText().toLowerCase().contains(".")) && jTCo3.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));
                
        /*Coloca en el label de correo 3 el correo que el usuario escribió*/
        jLCo3.setText(jTCo3.getText());               
        
        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo3.getText().compareTo("")==0)
            jLCo3.setText("-");        
        
    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de página web*/
    private void jTPag1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTPag1.getText().contains("www") || !jTPag1.getText().toLowerCase().contains(".")) && jTPag1.getText().toLowerCase().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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

    
    /*Cuando se pierde el foco del teclado en el campo de edición de página web 2*/
    private void jTPag2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTPag2.getText().contains("www") || !jTPag1.getText().toLowerCase().contains(".")) && jTPag2.getText().toLowerCase().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
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

    
    /*Cuando se pierde el foco del teclado en el campo de edición de contac*/
    private void jTContacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContacFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTContac.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTContac.getText().length()> 255)
            jTContac.setText(jTContac.getText().substring(0, 255));
        
        /*Coloca en el label de página web 2 la página que el usuario escribió*/
        jLPag2.setText(jTPag2.getText());              
        
    }//GEN-LAST:event_jTContacFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de área de observ*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTAObserv.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTAObserv.getText().length()> 255)
            jTAObserv.setText(jTAObserv.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se presiona una tecla en el campo de edición de descuent keytyped*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se presiona una tecla en el campo de texto de número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo de texto de número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoIntKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de días de crédito*/
    private void jTDCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDCredKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDCredKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de límite de crédito*/
    private void jTLimCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLimCredKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLimCredKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de días de crédito*/
    private void jTDCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDCredFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDCred.setSelectionStart(0);jTDCred.setSelectionEnd(jTDCred.getText().length());        
        
    }//GEN-LAST:event_jTDCredFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de límite de crédito*/
    private void jTLimCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLimCredFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTLimCred.setSelectionStart(0);jTLimCred.setSelectionEnd(jTLimCred.getText().length());                
        
    }//GEN-LAST:event_jTLimCredFocusGained

    
    /*Cuando se typea un ca en el campo de edición de límite de crédito*/
    private void jTLimCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLimCredKeyTyped
                
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTLimCred.getText().length()> 20)
            jTLimCred.setText(jTLimCred.getText().substring(0, 20));
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTLimCredKeyTyped

    
    /*Cuando se presiona una tecla en el checbox de bloq*/
    private void jCBloqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBloqKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBloqKeyPressed

    
    /*Cuando se typea una tecla en el campo de texto de días de crédito*/
    private void jTDCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDCredKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDCredKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de límite de crédito*/
    private void jTLimCredFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLimCredFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLimCred.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTLimCred.getText().replace(",", "").replace("$", "");
        
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
            jTLimCred.setText("");
            return;
        }                  
                                        
        /*Formatealo a moneda*/
        double dCant    = Double.parseDouble(sTex);
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sTex = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTLimCred.setText(sTex);                               
        
    }//GEN-LAST:event_jTLimCredFocusLost

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de número exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoExt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de número exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoInt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
               
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se presiona una tecla typed en el campo de RFC*/
    private void jTRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRFCKeyTyped

                                                   
    /*Cuando se gana el foco del teclado en el campo de puest*/
    private void jTPuesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPuesFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPues.setSelectionStart(0);jTPues.setSelectionEnd(jTPues.getText().length());                
        
    }//GEN-LAST:event_jTPuesFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de Contacto 2*/
    private void jTContac2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContac2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTContac2.setSelectionStart(0);jTContac2.setSelectionEnd(jTContac2.getText().length());                
        
    }//GEN-LAST:event_jTContac2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de puest 2*/
    private void jTPues2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPues2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPues2.setSelectionStart(0);jTPues2.setSelectionEnd(jTPues2.getText().length());                
        
    }//GEN-LAST:event_jTPues2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de puest*/
    private void jTPuesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPuesFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPues.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPues.getText().length()> 255)        
            jTPues.setText(jTPues.getText().substring(0, 255));        
        
    }//GEN-LAST:event_jTPuesFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de Contacto 2*/
    private void jTContac2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContac2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTContac2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTContac2.getText().length()> 255)
        {
            jTContac2.setText(jTContac2.getText().substring(0, 255));
        }                                
        
    }//GEN-LAST:event_jTContac2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de puest 2*/
    private void jTPues2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPues2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPues2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPues2.getText().length()> 255)        
            jTPues2.setText(jTPues2.getText().substring(0, 255));        
        
    }//GEN-LAST:event_jTPues2FocusLost

    
    /*Cuando se presiona una tecla en el campo de puest*/
    private void jTPuesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPuesKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPuesKeyPressed

    
    /*Cuando se presiona una tecla en el campo de Contacto 2*/
    private void jTContac2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTContac2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTContac2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de puest 2*/
    private void jTPues2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPues2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPues2KeyPressed

    
    
    
    
    /*Cuando se da un clic en el label de página web 1*/
    private void jLPag1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag1MouseClicked
        
        //Declara variables locales
        String sPag;
        String url  = "http://";
        
        
        /*Obtiene la pagina que el usuario escribio*/
        sPag        = jLPag1.getText();
                
        /*Completa la URL*/
        url         = url + sPag;
        
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

    
    /*Cuando se da un clic en el label de página web 2*/
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

    
    /*Cuando se sale con el mouse del label de página web 2*/
    private void jLPag2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag2MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLPag2MouseExited

    
    /*Cuando se sale con el mouse del label de página web 1*/
    private void jLPag1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLPag1MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLPag1MouseExited

    
    /*Cuando el mouse hace clic en el label de correo 1*/
    private void jLCo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseClicked
        
        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        Desktop desktop;
        if(Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) 
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
                desktop.mail(mailto);
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }
        }         
        
    }//GEN-LAST:event_jLCo1MouseClicked

    
    /*Cuando el mouse entra en el label de correo 1*/
    private void jLCo1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo1MouseEntered

    
    /*Cuando el mouse entra en el label de correo 2*/
    private void jLCo2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo2MouseEntered

    
    /*Cuando se hace clic con el mouse en el label de correo 3*/
    private void jLCo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseClicked
        
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
        
    }//GEN-LAST:event_jLCo3MouseClicked

    
    /*Cuando el mouse entra en el label de correo 3*/
    private void jLCo3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseEntered
    
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLCo3MouseEntered

    
    /*Cuando el mouse sale del label de correo 1*/
    private void jLCo1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseExited
                
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo1MouseExited

    
    /*Cuando el mouse sale del label de correo 2*/
    private void jLCo2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo2MouseExited

    
    /*Cuando el mouse sale del label de correo 3*/
    private void jLCo3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jLCo3MouseExited

    
    /*Cuando se hace clic con el mouse en el label de correo 2*/
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

               
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono persola 1*/
    private void jTTelPer1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyTyped
        
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
    }//GEN-LAST:event_jTTelPer1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono personal 2*/
    private void jTTelPer2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer2KeyTyped
        
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
    }//GEN-LAST:event_jTTelPer2KeyTyped
               
    
    /*Cuando se tipea una tecla en el campo de CP*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        Star.noCaracterXML(evt);
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
           

    /*Cuando se presiona una tecla en el control de pers física*/
    private void jRaFisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaFisKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaFisKeyPressed

    
    /*Cuando se presiona una tecla en el radio button de moral*/
    private void jRaMorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaMorKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaMorKeyPressed

    
    /*Cuando se presiona una tecla en el panel de los radio buttons*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanel2KeyPressed

    
    
    /*Cuando se gana el foco del teclado en el campo de cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());        
        
    }//GEN-LAST:event_jTCtaFocusGained

    
    
    /*Cuando se presiona una tecla en el campo de cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de cuenta*/
    private void jTCtaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCtaKeyTyped

   
    /*Cuando se gana el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());
        
    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene la descripción de la clasificación        
        String sDescrip = Star.sDescripClasCli(con, jTClas.getText().trim());
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en su lugar
        jTClasDescrip.setText(sDescrip);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el foco del teclado del control de la clasificación al principio del control*/
        jTClasDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se presiona una tecla en el campo de clasificación*/
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
        Busc b = new Busc(this, jTClas.getText(), 12, jTClas, jTClasDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTClas.grabFocus();                
        
    }//GEN-LAST:event_jBClasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar clasificación*/
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se presiona una tecla en el botón de Contactos*/
    private void jBContacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBContacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBContacKeyPressed

    
    /*Cuando se presiona el botón de Contactos*/
    private void jBContacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBContacActionPerformed
        
        /*Muestra el formulario de Contactos*/
        ContacsCli c = new ContacsCli(newCli);
        c.setVisible(true);
        
    }//GEN-LAST:event_jBContacActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de la revisión*/
    private void jTRevFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRevFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRev.setSelectionStart(0);jTRev.setSelectionEnd(jTRev.getText().length());
        
    }//GEN-LAST:event_jTRevFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del pago*/
    private void jTPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag.setSelectionStart(0);jTPag.setSelectionEnd(jTPag.getText().length());
        
    }//GEN-LAST:event_jTPagFocusGained

    
    /*Cuando se presiona una tecla en el campo de revisión*/
    private void jTRevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRevKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jTRevKeyPressed

    
    /*Cuando se presiona una tecla en el campo de pagos*/
    private void jTPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jTPagKeyPressed
    
    
    /*Cuando se presiona una tecla en el combobox de las sers*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando se presiona una tecla en el campo del código de la cliente*/
    private void jTCodEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodEmpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código de la cliente*/
    private void jTCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodEmp.setSelectionStart(0);jTCodEmp.setSelectionEnd(jTCodEmp.getText().length());
        
    }//GEN-LAST:event_jTCodEmpFocusGained

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTCodEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodEmpKeyTyped

    
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

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona una tecla en el campo de la fecha de cumpleaños*/
    private void jDCumpleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDCumpleKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDCumpleKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de se puede vender en otra moneda*/
    private void jCOtraMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOtraMonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCOtraMonKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cotizar en otra moneda*/
    private void jCCotOtrMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCotOtrMonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCotOtrMonKeyPressed

    
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
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del CURP*/
    private void jTCURPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCURPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCURP.setSelectionStart(0);jTCURP.setSelectionEnd(jTCURP.getText().length());                
        
    }//GEN-LAST:event_jTCURPFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CURP*/
    private void jTCURPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCURPFocusLost
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCURP.getText().length()> 21)
            jTCURP.setText(jTDCred.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTCURPFocusLost

    
    /*Cuando se presiona una tecla en el campo del CURP*/
    private void jTCURPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCURPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCURPKeyPressed

    
    /*Cuando se tipea una tecla en el campo del CURP*/
    private void jTCURPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCURPKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCURPKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del vendedor*/
    private void jTVendFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVendFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTVend.setSelectionStart(0);jTVend.setSelectionEnd(jTVend.getText().length());        

    }//GEN-LAST:event_jTVendFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del vendedor*/
    private void jTVendFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVendFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTVend.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTVend.getText().compareTo("")!=0)
            jTVend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTVendFocusLost

    
    /*Cuando se presiona una tecla en el campo del vendedor*/
    private void jTVendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTVendKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar vendedor*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBVend.doClick();
        /*Else, llama a la función para procesarlo normalmente else llama a la función escalable*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTVendKeyPressed

    
    /*Cuando se tipea una tecla en el campo del vendedor*/
    private void jTVendKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTVendKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTVendKeyTyped

    
    /*Cuando se presiona el botón de vendedor*/
    private void jBVendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVendActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTVend.getText(), 28, jTVend, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBVendActionPerformed

    
    /*Cuando se presiona una tecla en el botón de vendedor*/
    private void jBVendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVendKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVendKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la clase*/
    private void jTClasDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusLost

        /*Coloca el foco del teclado al principio del control*/
        jTClasDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasDescripFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación*/
    private void jTClasDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusGained
        
       /*Selecciona todo el texto cuando gana el foco*/
       jTClasDescrip.setSelectionStart(0);        
       jTClasDescrip.setSelectionEnd(jTClasDescrip.getText().length());        
        
    }//GEN-LAST:event_jTClasDescripFocusGained

    
    /*Cuando se presiona una tecla en el combobox de lista de precios*/
    private void jComListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComListKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComListKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la lada*/
    private void jTLadaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLada.setSelectionStart(0);jTLada.setSelectionEnd(jTLada.getText().length());
        
    }//GEN-LAST:event_jTLadaFocusGained

    
    /*Cuando se presioan una tecla en el campo de la lada*/
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
    private void jBVendMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVendMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVend.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBVendMouseEntered

    
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
    private void jBVendMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVendMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVend.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVendMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBContacMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBContacMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBContac.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBContacMouseExited

    
    /*Cuando se tipea una tecla en el campo de la extensión*/
    private void jTExtenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTExtenKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la extensión*/
    private void jTExtenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTExten.setSelectionStart(0);        
        jTExten.setSelectionEnd(jTExten.getText().length());        
        
    }//GEN-LAST:event_jTExtenFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cálular*/
    private void jTCelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCel.setSelectionStart(0);        
        jTCel.setSelectionEnd(jTCel.getText().length());        
        
    }//GEN-LAST:event_jTCelFocusGained

    
    /*Cuando se presiona una tecla en el campo de la extensión*/
    private void jTExtenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExtenKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cálular*/
    private void jTCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyPressed
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCelKeyPressed

    
    /*Cuando se presiona el botón de domcilio de entrega*/
    private void jBDomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDomActionPerformed

        /*Muestra la forma de domiclio de entrega*/
        DomEnt d = new DomEnt(jTab);
        d.setVisible(true);

    }//GEN-LAST:event_jBDomActionPerformed

    
    /*Cuando se presiona una tecla en el botón de domicilio de entrega*/
    private void jBDomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDomKeyPressed

    
    /*Cuando el mouse entra en el botón de domicilio*/
    private void jBDomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDom.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBDomMouseEntered

    
    /*Cuando el mouse sale del botón de domicilio*/
    private void jBDomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDom.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDomMouseExited

    
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
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFocusLost

    
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
    private void jTCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTCodEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodEmpFocusLost

    
    /*Cuando la forma esta activada entonces*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Que la tabla de domicilios este oculta*/
        jTab.setVisible(false);
        
        /*Obtiene la referencia de esta misma forma*/
        newCli  = this;
                
    }//GEN-LAST:event_formWindowActivated

    
    /*Cuando se gana el foco del teclado en el campo del giro*/
    private void jTGirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGirFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());
        
    }//GEN-LAST:event_jTGirFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del giro*/
    private void jTGirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGirFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTGir.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTGir.getText().compareTo("")!=0)
            jTGir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTGirFocusLost

    
    /*Cuando se presiona una tecla en el campo del giro*/
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

    
    /*Cuando el mouse entra en el botón de búscar giro*/
    private void jBGirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGirMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGir.setBackground(Star.colBot);

    }//GEN-LAST:event_jBGirMouseEntered

    
    /*Cuando el mouse sale del botón de búscar giro*/
    private void jBGirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGirMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGir.setBackground(Star.colOri);

    }//GEN-LAST:event_jBGirMouseExited

    
    /*Cuando se presiona el botón de búscar giro*/
    private void jBGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGirActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTGir.getText(), 33, jTGir, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTGir.grabFocus();
        
    }//GEN-LAST:event_jBGirActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar giro*/
    private void jBGirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGirKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGirKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la zona*/
    private void jTZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTZonFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());
        
    }//GEN-LAST:event_jTZonFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la zona*/
    private void jTZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTZonFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTZon.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTZon.getText().compareTo("")!=0)
            jTZon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTZonFocusLost

    
    /*Cuando se presiona uan tecla en el campo de zona*/
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

    
    /*Cuando el mouse entra en el botón de la zona*/
    private void jBZonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBZonMouseEntered

        /*Cambia el color del fondo del botón*/
        jBZon.setBackground(Star.colBot);

    }//GEN-LAST:event_jBZonMouseEntered

    
    /*Cuando el mouse sale del botón de la zona*/
    private void jBZonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBZonMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBZon.setBackground(Star.colOri);

    }//GEN-LAST:event_jBZonMouseExited

    
    /*Cuando se presiona el botón de búscar zona*/
    private void jBZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBZonActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTZon.getText(), 32, jTZon, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTZon.grabFocus();
        
    }//GEN-LAST:event_jBZonActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar zona*/
    private void jBZonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBZonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBZonKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta contable*/
    private void jTCtaContaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaContaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaConta.setSelectionStart(0);jTCtaConta.setSelectionEnd(jTCtaConta.getText().length());        
        
    }//GEN-LAST:event_jTCtaContaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la contabilidad*/
    private void jTCtaContaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaContaFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTCtaConta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaContaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta contable*/
    private void jTCtaContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaContaKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaContaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del depósito en garantía*/
    private void jTDepGarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepGarFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDepGar.setSelectionStart(0);jTDepGar.setSelectionEnd(jTDepGar.getText().length());
        
    }//GEN-LAST:event_jTDepGarFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de depósito en garantía*/
    private void jTDepGarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepGarFocusLost
 
        /*Coloca el borde negro si tiene datos*/
        if(jTDepGar.getText().compareTo("")!=0)
            jTDepGar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Coloca el caret en la posiciòn 0*/
        jTDepGar.setCaretPosition(0);

        /*Si es cadena vacia el campo que sea 0 y regresa*/
        if(jTDepGar.getText().compareTo("")==0)
        {
            jTDepGar.setText("$0.00");
            return;
        }
        
        /*Formatealo a moneda*/
        double dCant    = Double.parseDouble(jTDepGar.getText().replace("$", "").replace(",", ""));
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        jTDepGar.setText(n.format(dCant));
                
    }//GEN-LAST:event_jTDepGarFocusLost

    
    /*Cuando se presiona una tecla en el campo de depósito en garantía*/
    private void jTDepGarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDepGarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDepGarKeyPressed

    
    /*Cuando se tipea una tecla en el campo de depósito en garantía*/
    private void jTDepGarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDepGarKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDepGarKeyTyped

    
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

    
    /*Cuando se gana el foco del teclado en el campo de la clave bancaría*/
    private void jTClavBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavBancFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClavBanc.setSelectionStart(0);jTClavBanc.setSelectionEnd(jTClavBanc.getText().length());        
        
    }//GEN-LAST:event_jTClavBancFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la clave bancaría*/
    private void jTClavBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClavBancFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTClavBanc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClavBancFocusLost

    
    /*Cuando se presiona una tecla en el campo del banco*/
    private void jTClavBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClavBancKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClavBancKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la jerárquia*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());
        
    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco el teclado en el campo de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clasificación de jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTJeraKeyPressed

    
    /*Cuando el mouse entra en el botón de jerárquia*/
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBJeraMouseEntered

    
    /*Cuando el mouse sale del botón de jerárquia*/
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBJeraMouseExited

    
    /*Cuando se presiona el botón de búscar jerarquía*/
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed
        
        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis v = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "cli");
        v.setVisible(true);
        
    }//GEN-LAST:event_jBJeraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de jerárquia*/
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBJeraKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del cliente*/
    private void jTCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodEmp.setSelectionStart(0);jTCodEmp.setSelectionEnd(jTCodEmp.getText().length());

    }//GEN-LAST:event_jTCodCliFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del cliente*/
    private void jTCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodCliFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodEmp.setCaretPosition(0);

    }//GEN-LAST:event_jTCodCliFocusLost

    
    /*Cuando se presiona una tecla en el campod el cliente*/
    private void jTCodCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de predial*/
    private void jTCtaPredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaPredFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaPred.setSelectionStart(0);jTCtaPred.setSelectionEnd(jTCtaPred.getText().length());
        
    }//GEN-LAST:event_jTCtaPredFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de predial*/
    private void jTCtaPredFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaPredFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCtaPred.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaPredFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de predial*/
    private void jTCtaPredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaPredKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaPredKeyPressed

    
    //Cuando se presiona una tecla en el check de bloquear crédito
    private void jCBloqCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBloqCredKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCBloqCredKeyPressed

    private void jTCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyTyped
    
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();    
    // TODO add your handling code here:
    }//GEN-LAST:event_jTCelKeyTyped

    private void jTCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCallActionPerformed

    private void jTRazSocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRazSocKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTRazSocKeyTyped

    private void jTCallKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTCallKeyTyped

    private void jTColKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTColKeyTyped

    private void jTNoExtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTNoExtKeyTyped

    private void jTCiuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTCiuKeyTyped

    private void jTEstadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTEstadKeyTyped

    private void jTPaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyTyped
        // TODO add your handling code here:
        Star.noCaracterXML(evt);
    }//GEN-LAST:event_jTPaiKeyTyped

    private void jComFormPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComFormPagFocusLost

    }//GEN-LAST:event_jComFormPagFocusLost

    private void jComFormPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComFormPagActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComFormPag.getSelectedItem()==null)
        return;

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
        return;

        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "pags", "WHERE cod = '" + jComFormPag.getSelectedItem().toString().trim() + "'");

        //Si hubo error entonces regresa
        if(sDescrip==null)
        return;

        //Coloca la descripción en el control
        jComFormPag.setToolTipText(sDescrip);

        //Cierra la base de datos
        Star.iCierrBas(con);

    }//GEN-LAST:event_jComFormPagActionPerformed

    private void jComFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComFormPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComFormPagKeyPressed
    
    
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
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBVend;
    private javax.swing.JButton jBZon;
    private javax.swing.JCheckBox jCBloq;
    private javax.swing.JCheckBox jCBloqCred;
    private javax.swing.JCheckBox jCCotOtrMon;
    private javax.swing.JCheckBox jCOtraMon;
    private javax.swing.JComboBox jComFormPag;
    private javax.swing.JComboBox jComList;
    private javax.swing.JComboBox jComSer;
    private com.toedter.calendar.JDateChooser jDCumple;
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
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRaFis;
    private javax.swing.JRadioButton jRaMor;
    private javax.swing.JScrollPane jScrTab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTBanc;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCURP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTClasDescrip;
    private javax.swing.JTextField jTClavBanc;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodCli;
    private javax.swing.JTextField jTCodEmp;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTContac;
    private javax.swing.JTextField jTContac2;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTCtaConta;
    private javax.swing.JTextField jTCtaPred;
    private javax.swing.JTextField jTDCred;
    private javax.swing.JTextField jTDepGar;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTExten;
    private javax.swing.JTextField jTGir;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTLada;
    private javax.swing.JTextField jTLimCred;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTPag;
    private javax.swing.JTextField jTPag1;
    private javax.swing.JTextField jTPag2;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTPues;
    private javax.swing.JTextField jTPues2;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTRazSoc;
    private javax.swing.JTextField jTRev;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelPer1;
    private javax.swing.JTextField jTTelPer2;
    private javax.swing.JTextField jTVend;
    private javax.swing.JTextField jTZon;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NewClien extends javax.swing.JFrame */
