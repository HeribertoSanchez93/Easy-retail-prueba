//Paquete
package ptovta;

//Imortaciones para XLT
import java.awt.AWTException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//Importaciones
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.security.cert.CertificateFactory;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.xmlbeans.impl.util.Base64;
import static ptovta.Login.sUsrG;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import wscance.CancelacionesCancelaMultipleFallaServicioFaultFaultMessage;
import wscance.CancelacionesCancelaMultipleFallaSesionFaultFaultMessage;
import wscance.CancelacionesRecuperarAcusesFallaServicioFaultFaultMessage;
import wscance.CancelacionesRecuperarAcusesFallaSesionFaultFaultMessage;
import wscance.CancelacionesRecuperarAcusesFallaValidacionFaultFaultMessage;
import wscance.RespuestaCancelaMultiple;
import wscance.RespuestaRecuperarAcuse;
import wsseg.RespuestaObtenerToken;
import wsseg.SeguridadObtenerTokenFallaServicioFaultFaultMessage;
import wsseg.SeguridadObtenerTokenFallaSesionFaultFaultMessage;
import wstimb.RespuestaCancelaTimbrado;
import wstimb.RespuestaEstatusTimbrado;
import wstimb.RespuestaObtenerQRTimbrado;
import wstimb.RespuestaObtenerTimbrado;
import wstimb.RespuestaTimbraXML;
import wstimb.TimbradoCancelaTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoCancelaTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoCancelaTimbradoFallaValidacionFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaValidacionFaultFaultMessage;
import wstimb.TimbradoObtenerQRTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoObtenerQRTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoObtenerQRTimbradoFallaValidacionFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaValidacionFaultFaultMessage;



/*Clase que contiene la clase principal*/
public class Star 
{
    //sirve para agregar el tipo de producto
    public static String sNomProd       = "|EasyRetail_Admin";
    
    //Contiene el código de cliente mostrador
    public static String sCliMostG      = "EMPMOS";
    
    //Contiene el nombre de cliente mostrador
    public static String sNomCliMostG   = "CLIENTE MOSTRADOR";
    
    //Contiene la serie del cliente mostrador
    public static String sSerMostG      = "EMP";
    
    //sirve para agregar la cadena de verificacion
    public static String sCadVerif      ="|JkZ#TLBdWj81*&N6p*?b0cjswr";
    
    /*Declara variables de instancia*/    
    public static Timer tim;
    
    /*Contiene la clave para encriptar la mayoría de las cosas*/
    public static byte[]            sClavEncrip         = new byte[]{'a','5','z','8','y','1','I','S','N','C','O','N','S','U','L','T'};
    
    //Variables de utilidad para el cell editor
    private static int              intiContCellEdG     = 1;
    private static String           strCellEdG;
    
    //Contiene el código del almacén de activo fijo
    public static String            strAlmaActFij       = "ACTFIJ";
    
    //TÍTULOS DE LES MENSAJES DE ERROR DE LAS EXCEPCIONES
    
    //Contiene el título de los mensajes de error de la base de datos
    public static String            sErrSQL             = "Error SQL";
        
    //Contiene el título de los mensajes de error de AWT
    public static String            sErrAWT             = "Error AWT";
    
    //Contiene el título de mensajes de firma general
    public static String            sErrGralSecu        = "Error GralSecu";
    
    //Contiene el título de mensajes de certificación
    public static String            sErrCertifi        = "Error Certifi";
    
    //Contiene el título de mensajes de firma
    public static String            sErrSignatu         = "Error Signatu";
    
    //Contiene el título de los mensajes de criptografía
    public static String            sErrNoSuchAlgo      = "Error NoSuchAlgo";
    public static String            sErrNoSuchPad       = "Error NoSuchPad";
    public static String            sErrIlegSizeBloq    = "Error IlegSizeBloq";
    public static String            sErrBadPad          = "Error BadPad";
    public static String            sErrInvalKey        = "Error InvalKey";
    
    //Contiene el título de los mensajes de error de transformación de formato
    public static String            sErrTransfor        = "Error Transfor";
    
    //Contiene el título de los mensajes de error del WS del PAC
    public static String            sErrWSPAC           = "Error WSPAC";
    
    //Contiene el título de los mensajes de error de línea no disponible
    public static String            sErrLinUnav         = "Error LinUnav";
    
    //Contiene el título de los mensajes de error de impresión
    public static String            sErrPrint           = "Error Print";
    
    //Contiene el título de los mensajes de error de archivo no encontrado
    public static String            sErrFilNotFoun      = "Error FileNotFoun";
    
    //Contiene el título de los mensajes de error de socket
    public static String            sErrSock            = "Error Sock";
    
    //Contiene el título de los mensajes de error de host desconocido
    public static String            sErrUnknowHos       = "Error UnknwHos";
    
    //Contiene el título de los mensajes de error de sintaxis URI
    public static String            sErrUriSynta        = "Error URISynta";
    
    //Contiene el contador de la tabla 1 de las compras
    public static int           iContFiPrevComp     = 1;
    
    //Contiene el título de los mensajes de error de seguridad
    public static String            sErrSecuri          = "Error Securi";
    
    //Contiene el título de los mensajes de error de no se encontro clase
    public static String            sErrClassNoF        = "Error ClassNoF";
    
    //Contiene el titulo de los mensajes de error de mensajes
    public static String            sErrMessag          = "Error Messag";
    
    //Contiene el tìtulo de los mensajes de error de excepciones generales
    public static String            sErrExcep           = "Error EXCEP";
    
    //Contiene el título de los mensajes de error de excepciones de interrupción
    public static String            sErrInterru         = "Error Interru";
    
    //Contiene el error para las excepciones compartidas
    public static String            sErrCompa           = "Error COMPA";
    
    //Contiene el título de los mensajes de error de formato de números
    public static String            sErrNumForm         = "Error NUMFORM";
    
    //Contiene el título de los mensjaes de error de apuntador nulo
    public static String            sErrNullPoint       = "Error NullPoint";
    
    //Contiene el título de los mensajes de error de parseo
    public static String            sErrPARS            = "Error PARSE";
    
    //Contiene el título de los mensajes de error de jasper reports
    public static String            sErrJASR            = "Error JASR";
    
    //Contiene el título de los mensajes de error de entrada y salida
    public static String            sErrIO              = "Error IO";
    
    //Contiene el título de los mensajes de error normales
    public static String            sErrNorm            = "Error";    
    
    
    /*Timer para reportar los mínimos*/
    public static Timer             timMin;

    /*Para que no se abra dos veces la forma de ventas*/
    public static NewVta            newVta;
    
    //Para que no se abra dos veces la forma de órden de compra
    public static RecibOrd          recibOrdG;    
    
    //Para que no se abra dos veces la forma de entregar backorder
    public static EntBack           entBackG;    
    
    /*Para que no se abra dos veces la forma de compra*/
    public static IngrCom           gCompr;
    
    /*Para que no se abra dos veces la forma de cotizacion*/
    public static CotNorm           gCot;
    
    /*Para que no se abra dos veces la forma de productos*/
    public static cats.Prods        gProds;

     /*Para que no se abra dos veces la forma de previo de compra*/
    public static PrevComp          gPrevComps;
    
    /*Para que no se abra dos veces la forma de nuevo cliente*/
    public static Client            gClient;
    
    /*Para que no se abra dos veces la forma de nuevo proveedor*/
    public static Prov              gProv;
    
    /*Para que no se abra dos veces la forma de compras*/
    public static Compr             gComprs;
    
    /*Para que no se abra dos veces la forma de cotizaciones*/
    public static Cots              gCots;
    
    /*Para que no se abra dos veces la forma de ventas*/
    public static Vtas              gVtas;
    
    /*Para que no se abra dos veces la forma de proveedores*/
    public static cats.Provs        gProvs;
    
    /*Para que no se abra dos veces la forma de clientes*/
    public static cats.Clients      gClients;
    
    /*Para que no se abra dos veces la forma de cotizacion ya*/
    public static IngrPrevCom       gPrevComp;
    
    //para que no se abra dos veces la forma de punto de venta 
    public static PtoVtaTou         gPtoVtaTou;
    
    //para administras las claves
    public static ClavMast          gClavMast;
            
    /*Timer para reportar los mínimos*/
    public static Timer             timMax;
    
    /*Define el formato de línea de entrada*/
    public static final AudioFormat format  = new AudioFormat(8000.0f, 16, 1, true, true);
    
    /*Para saber si estación de trabajo o no*/
    public static boolean       bEstacTrab      = false;
    
    /*Objeto para que sol ose abra una vez la video llamada*/
    public static ImgCam        imgCa           = null;
    
    /*Bandera para cuando el usuario cierra la video llamada en la parte del video*/
    public static boolean       bCierrVid       = false;
    
    /*Bandera para cuando el usuario cierra la video llamada en la parte del audio*/
    public static boolean       bCierrVidAud    = false;
    
    /*Para los mensajes de chat*/
    public static AvisChatC     avisChat;
    
    /*Contiene el contador de la tabla 1 de las compras*/
    public static int           iContFiComp     = 1;
    
    /*Contiene el contador de la tabla 1 de ventas*/    
    public static int           iContFiVent     = 1;    
    
    /*Para saber si el RFC es correcto en las diferentes formas*/
    public static boolean       bValRFC         = false;
    
    //Clase para loading de todo el sistema
    public static LoadinGral    lCargGral;
    public static LoadinGral    lCargGral2;
    public static LoadinGral    lCargFac;
    public static LoadinGral    lCargPDF;
    
    /*Variable que puede ser utilizada por todos para errores*/
    public static boolean       bErr;
    
    /*Archivos*/
    public static String        sArchConf       = "config.cfg";

    /*Socket TCP*/
    public static java.net.ServerSocket sockSrv;
    
    /*Socket UDP*/
    public static java.net.DatagramSocket socSrvUDP;
    
    /*Declara variables globales de bases de datos*/
    public  static String       sInstancia;
    public  static String       sUsuario;
    public  static String       sContrasenia;        
    public  static String       sBD;
    public  static String       sPort;
    public  static String       sPortLoc;
    public  static String       sNombreEmpresa;    
    public  static String       sEstacTrab;    
    public  static String       sSucu;
    public  static String       sNoCaj;
    
    /*Objeto que contiene la línea del microfono*/
    public static javax.sound.sampled.TargetDataLine lineMic;
            
    /*Contiene el color de los botones*/
    public static Color         colBot      = Color.lightGray;    
    
    /*Declara variables de conexión a la base de datos local*/
    public  static String       sInstLoc;
    public  static String       sUsrLoc;
    public  static String       sContraLoc;        

    /*Contiene el nombre del archivo de validación*/
    public static String        sArchVal    = "val";
    
    /*Contiene el nombre del icono de la aplicaciòn que se carga por default*/
    public static String        sIconDef    = "/imgs/logo.png";
    
    /*Contiene el token que la aplicación puede utilizar para token de seguridad*/
    public static String        sTokServ    = "-1";
    
    /*Contiene el mensaje de error de cuando se pide el token al wssegice*/
    public static String        sErr        = "";
    
    /*Bandera para saber si hubo error*/
    public static boolean        bErrTok    = false;
    
    //Contiene el RFC genérico para facturación
    public static String        sRFCGen     = "XAXX010101000";
    
    /*Contiene el id integrador*/
    public static String        sIDInte    = "c2b7e31b-4e82-4a4a-82cf-8632969beb57";

    /*Thread en el que estará el socket servidor esperando petición de los clientes*/
    public static Thread        thServ;
    
    /*Thread en el que estará el socket UDP servidor esperando petición de los clientes*/
    public static Thread        thServUDP;
    
    //Contiene el color original de los botones
    public static java.awt.Color  colOri    = Color.WHITE;
    
    /*Contiene la cámara para poder cerrarla*/
    private static com.github.sarxos.webcam.Webcam webcam;        
    
    /*Declara variables final*/
    private static final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private static final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private static final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};

    /*Variable que contiene la ruta de donde tomar el ícono de duda y advertencia*/
    public static final String  sRutIconDu  = "/imgs/dud.png";
    public static final String  sRutIconAd  = "/imgs/adver.png";
    public static final String  sRutIconEr  = "/imgs/err.png";    
    
    
    
        
    /*Clase principal*/
    public static void main(String[] args) 
    {
        //Try en modo protegido para todo el sistema
        try
        {
            /*Si el archivo de configuración no existe entonces*/        
            File f          = new File(sArchConf);
            if(!f.exists())
            {                
                /*Instancia la clase para crear el archivo de configuración*/
                BDCon bd    = new BDCon(true);

                /*Hazlo visible*/
                bd.setVisible(true);                        
            }
            else
            {
                /*Desserializamos el archivo*/
                BDConf bd   = null;
                try(FileInputStream fileIn = new FileInputStream(sArchConf); ObjectInputStream in = new ObjectInputStream(fileIn))
                {                   
                   bd               = (BDConf)in.readObject();                   
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                       
                    return;                                                                                                                                            
                } 

                /*Si el objeto es nulo entonces que regrese en este punto*/
                if(bd==null)
                    System.exit(1);

                /*Obtiene la instancia*/
                sInstancia          = bd.sInst;                            

                /*Obtiene el usuario*/
                sUsuario            = bd.sUsr;

                /*Obtiene la contraseña*/
                sContrasenia        = bd.sContra;                        

                /*Obtiene la base de datos*/
                sBD                 = bd.sBD;            

                /*Obtiene la sucursal*/
                sSucu               = bd.sSucur;            

                /*Obtiene el nùmero de caja*/
                sNoCaj              = bd.sNumCaj;            

                /*Obtiene el puerto*/
                sPort               = bd.sPort;                        
                
            }/*Fin de else*/                                

            /*Desencripta todos los campos de conexión*/
            sNoCaj              = Star.sDecryp(sNoCaj);                       
            sSucu               = Star.sDecryp(sSucu);
            sBD                 = Star.sDecryp(sBD);
            sContrasenia        = Star.sDecryp(sContrasenia);
            sUsuario            = Star.sDecryp(sUsuario);
            sInstancia          = Star.sDecryp(sInstancia);
            sPort               = Star.sDecryp(sPort);
            
            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
            {
                /*Mensajea el error*/
                JOptionPane.showMessageDialog(null, "Presione aceptar para abrir el archivo de configuración y revisar los parámetros de conexión ya que no se pudo establecer la comunicación con el servidor correctamente.", "Error de conexión", JOptionPane.INFORMATION_MESSAGE, null); 

                /*Instancia la clase para crear el archivo de configuración*/
                BDCon bd        = new BDCon(true);

                /*Hazlo visible*/
                bd.setVisible   (true);            

                /*Desencripta nuevamente todos los campos de conexión*/
                sNoCaj              = Star.sDecryp(sNoCaj);                       
                sSucu               = Star.sDecryp(sSucu);
                sBD                 = Star.sDecryp(sBD);
                sContrasenia        = Star.sDecryp(sContrasenia);
                sUsuario            = Star.sDecryp(sUsuario);
                sInstancia          = Star.sDecryp(sInstancia);
                sPort               = Star.sDecryp(sPort);

                //Abre la base de datos nuevamente
                con = Star.conAbrBas(true, false);

                //Si hubo error entonces sal del sistema
                if(con==null)
                    System.exit(1);

            }//Fin de if(con==null)

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Muestra la presentación del sistema*/
            Present p = new Present();
            p.setVisible(true);
        }
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF);                                                                                               
        }                    
                
    }/*Fin de public static void main(String[] args) */   
        
    
    /*Respalda la base de datos en el directorio actual*/
    public static void vRespBasAut()
    {
        //Declara variables locales        
        String sRut            = ".\\electricosautomatico.sql";        
        String sRutBin         = "";
        String sUnid;
        String sCadComple;
        String sConfig;
        File   file;                
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el si está activado el respaldo automático*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT configuracion FROM configuraciones WHERE tipo = 'respaldos' and nombre = 'respaldoautomatico'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene si es respaldo automático o no*/
                sConfig   = rs.getString("configuracion");
                
                /*Coloca la bandera*/
                if(sConfig.compareTo("1")==0)
                    bSi = true;
                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                            
        }
        
        /*Si no esta activado el respaldo automático entonces regresa*/
        if(!bSi)
            return;
        
        /*Comprueba si ya esta definida la ruta al directorio bin de mysql*/
        bSi = false;
        try
        {
            sQ = "SELECT * FROM rutas WHERE programa = 'mysql' and nombre = 'bin'";
            st = con.prepareStatement(sQ);
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si esta definida la ruta*/
            if(rs.next())
            {
                /*Lee la ruta al directorio bin*/
                sRutBin = rs.getString("ruta");
                
                /*Substring para quitar la unidad con todo y la diagonal invertida*/
                sUnid   = sRutBin.substring(0, 3);
                
                /*Substring la ruta del bin para ponerles comillas dobles a todos los nombres de archivos para que el bat corra correctamente*/
                sRutBin = sRutBin.substring(3, sRutBin.length());
                                
                /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta*/                                                                
                StringTokenizer st1 = new StringTokenizer(sRutBin,"\\");
                sRutBin = "\"";
                while(st1.hasMoreTokens())
                {
                    sRutBin += st1.nextToken() + "\"\\\"";
                }
                
                /*Quita la última diagonal invertida y dobles comillas*/
                sRutBin = sRutBin.substring(0, sRutBin.length() - 2);
                
                /*Junta la unidad con la ruta al bin*/
                sRutBin = sUnid + sRutBin;
                
                /*Pon la bandera en true para saber que si esta definida*/
                bSi = true;

            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                                    
        }  
        
        /*Si no está definida la ruta al directorio bin de mysql entonces*/
        if(!bSi)
        {
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Directorio bin de mysql no a sido definido para respaldo automático.", "No existe", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            
            /*Regresa por que no puede continuar*/
            return;
        }
                
        
        /*Crea la cadena completa que ejecutara la bases de datos para el respaldo*/
        sCadComple = sRutBin + "\\mysqldump --user=" + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " > " + sRut;

        /*Borra el archivo bat si es que existe*/
        file = new File("respaldoautomatico.bat");
        file.delete();

        /*Crealo*/
        try 
        {
            file.createNewFile();
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                       
            return;                                                                                                                                                                            
        }

        /*Escribe en el la cadena que ejecutara la base de datos por consola para realizar el respaldo*/
        FileWriter fw;
        try 
        {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw;
            bw = new BufferedWriter(fw);
            bw.write(sCadComple);
            bw.close();
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                       
            return;                                                                                                                                                                                                    
        }

        /*Corre el archivo bat*/
        try 
        {

            Runtime.getRuntime().exec("cmd /c start /b respaldoautomatico.bat");
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, con);                                                       
            return;                                                                                                                                                                            
        }
            
        //Cierra la base de datos
        Star.iCierrBas(con);
                        
    }/*Fin de public static void vRespBasAut()*/
               
    
    /*Crea la base de datos electricos si no existe*/
    public static void vCreElecs(String sBD, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Comprueba si la base de datos existe*/
        boolean bSi = false;
        try
        {
            sQ  = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                                    
        }            
        
        /*Si la base de datos no existe entonces*/
        if(!bSi)
        {             
            /*Crea la tabla de consecutivos*/
            try 
            {
                /*Crea la consulta*/
                sQ = "CREATE DATABASE IF NOT EXISTS " + sBD;                                    
                st = con.createStatement();
                st.execute(sQ);

             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y sal del sistema
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                System.exit(1);
             }                                
        }                           
        
    }/*Fin de private void vCreElecs()*/   
    
                           
    /*Inserta error en la tabla de errores de la base de datos*/
    public static void vInsertError(String sError, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        
        
        /*Inserta el error*/
        try 
        {            
            sQ = "INSERT INTO errores (error,                               estac,                                      falt,       sucu,       nocaj) " + 
                          "VALUES('" + sError.replace("'", "''") + "','" +  Login.sUsrG.replace("'", "''") + "',    now(),      'INICIAL',  'INICIAL')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);

         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                                                                                                                                                                          
         }
                
    }/*Fin de public static void vInsertError(String sError)*/            

    
    /*Método para generar una clave en base al día, mes, año, hora, minuto y segundo*/
    public static String sGenClavDia()
    {
        //Declara variables locales
        String sClav   = "";
        
        
        
        
        /*Obtiene la fecha del día de hoy*/
        Date dat = new Date();
        
        /*Genera el código en base a: dia, mes, año, hora, minuto y segundos*/        
        sClav          = sClav + Integer.toString(dat.getDate());
        sClav          = sClav + Integer.toString(dat.getMonth());
        sClav          = sClav + Integer.toString(dat.getYear());
        sClav          = sClav + Integer.toString(dat.getHours());
        sClav          = sClav + Integer.toString(dat.getMinutes());
        sClav          = sClav + Integer.toString(dat.getSeconds());
        
        /*Devuelve el resultado*/
        return sClav;        
    }

    
    /*Desencripta información*/    
    public static String sDecryp(String sData) 
    {
        //Declara variables locales
        String dencrypVal;
                
        /*Desencripta los datos*/
        try 
        {
            Key key = new SecretKeySpec(sClavEncrip, "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(sData);
            byte[] decValue = c.doFinal(decordedValue);
            dencrypVal = new String(decValue);
        }
        catch(NoSuchAlgorithmException expnNoSuchAlgo) 
        {
            dencrypVal = "Error al desencriptar por " + expnNoSuchAlgo.getMessage();            
        }
        catch(IOException expnIO)
        {
            dencrypVal = "Error al desencriptar por " + expnIO.getMessage();
        }
        catch(NoSuchPaddingException expnNoSuchPad)
        {
            dencrypVal = "Error al desencriptar por " + expnNoSuchPad.getMessage();
        }
        catch(IllegalBlockSizeException expnIlegSizeBloq)
        {
            dencrypVal = "Error al desencriptar por " + expnIlegSizeBloq.getMessage();
        }        
        catch(BadPaddingException expnBadPad)
        {
            dencrypVal = "Error al desencriptar por " + expnBadPad.getMessage();
        }
        catch(InvalidKeyException expnBadKey)
        {
            dencrypVal = "Error al desencriptar por " + expnBadKey.getMessage();
        }
        
        /*Devuelve el resultado*/
        return dencrypVal;        
    }
    

    /*Manda correo de cumpleaños automático y manual*/
    public static void vMandCoCump(String sEmps[])
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces 
        if(con==null)
            return;        
        
        /*Declara variables del correo*/
        String      sServSMTPSal;
        String      sSMTPPort;
        String      sUsr;
        String      sContra;
        String      sActSSL;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ;
        String      sQ2;
        
        /*Obtiene los datos del correo electrónico de esta estación*/
        try
        {
            sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal            = rs.getString("srvsmtpsal");
                sSMTPPort               = rs.getString("portsmtp");
                sUsr                    = rs.getString("usr");
                sContra                 = rs.getString("pass");
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";

                /*Desencripta la contraseña*/
                sContra                = Star.sDecryp(sContra);                                        
            }
            /*Else no hay datos entonces regresa*/
            else
            {
                /*Ingresa en la base de datos el registor de que se fallo por que no hay datos de configuración del correo*/
                try 
                {                    
                    sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                               estac,                                  falt,  corrde,  tipdoc,                 sucu,                                     nocaj) " + 
                                            "VALUES('',    '',         'FALLO','NO PARÁMETROS DE CONEXIÓN','" +     Login.sUsrG.replace("'", "''") + "',    now(), '',      'CUMPLEAÑOS','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                               
                }
                
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                            
        }            
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Si no esta definida la carpeta de la aplicación entonces
        if(sCarp.compareTo("")==0)
        {
            /*Ingresa en la base de datos el registor de que se fallo por que no esta definida la ruta con el servidor*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                                           estac,                                  falt,  corrde,  tipdoc,                 sucu,                                     nocaj) " + 
                                        "VALUES('',    '',         'FALLO','NO ESTA DEFINIDA CARPETA COMPARITDA','" +       Login.sUsrG.replace("'", "''") + "',    now(), '',      'CUMPLEAÑOS','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                            
            }                

            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
            
        }//Fin de if(sCarp.compareTo("")==0)
        
        //Declara variables locales
        String sMens    = "";
        String sAsun    = ""; 
        
        /*Obtiene el mensaje y el asunto que debe tener el correo de cumpleaños*/        
        try
        {            
           sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {                  
                sMens          = rs.getString("extr");                                                
                sAsun          = rs.getString("asun");                                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
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

        /*Bandera para saber si hay archivo para mandar adjunto o no*/
        boolean bSi             = false;

        /*Si es nulo entonces regresa*/
        if(new File(sCarp).list()==null)
            return;
        
        /*Si existe imágen en el directorio entonces*/
        if(new File(sCarp).list().length > 0)
        {
            /*Obtiene la lista del archivo y completa la ruta a la imágen*/
            String sArch [] = new File(sCarp).list();
            sCarp   = sCarp + "\\" + sArch[0];           
            
            /*Coloca la bandera para saber que si hay adjunto*/
            bSi                 = true;            
        }              
        
        /*Declara algunas variables como final*/
        final String sUsrFi     = sUsr;        
        final String sContraFi  = sContra;
        /*Recorre todo el arreglo de cliente*/
        for (String sEmp : sEmps) 
        {
            //Declara variables locales
            String sNom = "";
            String sCo1 = "";
            
            /*Obtiene el nombre del cliente de la base de datos y su correo*/            
            try 
            {
                sQ = "SELECT co1, nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sEmp + "'";	                                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {
                    sNom    = rs.getString("nom");
                    sCo1    = rs.getString("co1");            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                           
            }
            
            /*Manda el correo*/
            try 
            {
                //Define las propiedades de conexión
                Properties props = System.getProperties();
                props.setProperty("mail.smtp.host", sServSMTPSal);
                props.put("mail.smtp.starttls.enable", sActSSL);
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.port", sSMTPPort);
                props.put("mail.store.protocol", "pop3");
                props.put("mail.transport.protocol", "smtp");
                final String username = sUsrFi;
                final String password = sContraFi;
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                /*Si hay imágen entonces el mensajea va a ser con imágen*/
                Multipart multipart = null;
                if(bSi)
                {
                    /*Carga la imágen*/
                    multipart               = new MimeMultipart("related");
                    BodyPart htmlPart       = new MimeBodyPart();                                       
                    htmlPart.setContent("<html><body>" +
                            "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNom + "</body></html>", "text/html");                    
                    /*Continua creando la imágen*/
                    multipart.addBodyPart(htmlPart);
                    BodyPart imgPart        = new MimeBodyPart();
                    DataSource ds           = new FileDataSource(sCarp);
                    imgPart.setDataHandler(new DataHandler(ds));
                    imgPart.setHeader       ("Content-ID","the-img-1");
                    multipart.addBodyPart(imgPart);
                }
                
                /*Crea el contenido del mensaje*/
                MimeMessage  msj            = new MimeMessage(session);
                msj.setFrom(new InternetAddress(sUsrFi));
                msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo1));
                msj.setSubject              (sAsun);

                /*Si es con imágen entonces adjunta el mensaje multipart*/
                if(bSi)
                    msj.setContent(multipart);
                /*Else sin imágen entonces inserta el mensaje en el objeto*/
                else
                    msj.setContent("<html>" + sMens + " " + sNom + "<body></body></html>", "text/html; charset=utf-8");
                
                /*Manda el correo*/
                Transport.send(msj);
            }
            catch(MessagingException expnMessag) 
            {                        
                /*Ingresa en la base de datos el registor de que se fallo*/
                try
                {
                    sQ2 = "INSERT INTO logcorrs(    corr,                  nodoc,     estad,           motiv,                                                   estac,                                  falt,       corrde,                         tipdoc,                 sucu,                                     nocaj) " +
                            "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +     Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'CUMPLEAÑOS','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                                
                }
                
                /*Mandalo al correo alternativo y continua*/
                Star.vMandAlterCump(sCo1, sEmp, expnMessag.getMessage(), sNom, "man");                                                                
                continue;
                
            } /*Fin de catch(MessagingException expnMessag)*/
            
            /*Actualiza el cliente que ya se le mando correo este año*/
            try 
            {                
                sQ = "UPDATE emps SET anio = '" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + "' WHERE CONCAT_WS('', ser, codemp  )= '" + sEmp + "'";                                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                           
            }
                        
            /*Ingresa en la base de datos el registro que se mando con éxito*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,                            nodoc,      estad,      motiv,              estac,                                         falt,       corrde,                         tipdoc,                     sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '',                '" +   Login.sUsrG.replace("'", "''") + "',     now(), '" + sUsr.replace("'", "''") + "',   'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
                
                sQ2 = "INSERT INTO estadiscor(    corr,                            nodoc,      estad,      motiv,              estac,                                         falt,       corrde,                         tipdoc,                     sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '',                '" +   Login.sUsrG.replace("'", "''") + "',     now(), '" + sUsr.replace("'", "''") + "',   'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                           
            }                                                            
            
        } /*Fin de for(String sCli: sEmps)*/

        //Esconde la forma de loading
        Star.vOcultLoadin();

        //Cierra la base de datos
        Star.iCierrBas(con);            
                
    }/*Fin de public static void vMandCoCump(String sEmps[])*/
    
    
    /*Manda correo de agradecimiento*/
    public static void vMandCoAgra(String sEmps[])
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;        
        
        /*Declara variables del correo*/
        String      sServSMTPSal;
        String      sSMTPPort;
        String      sUsr;
        String      sContra;
        String      sActSSL;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ;
        String      sQ2;
        
        /*Obtiene los datos del correo electrónico de esta estación*/
        try
        {
            sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal            = rs.getString("srvsmtpsal");
                sSMTPPort               = rs.getString("portsmtp");
                sUsr                    = rs.getString("usr");
                sContra                 = rs.getString("pass");
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";

                /*Desencripta la contraseña*/
                sContra                = Star.sDecryp(sContra);                                        
            }
            /*Else no hay datos entonces regresa*/
            else
            {
                //Esconde la forma de loading
                Star.vOcultLoadin();
        
                /*Ingresa en la base de datos el registor de que se fallo por que no hay datos de configuración del correo*/
                try 
                {                    
                    sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                               estac,                                  falt,  corrde,  tipdoc,                     sucu,                                     nocaj) " + 
                                            "VALUES('',    '',         'FALLO','NO PARÁMETROS DE CONEXIÓN','" +     Login.sUsrG.replace("'", "''") + "',    now(), '',      'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                                                               
                }
                
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                       
        }            
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Si no esta definida la ruta del servidor entonces 
        if(sCarp.compareTo("")==0)
        {
            /*Ingresa en la base de datos el registor de que se fallo por que no esta definida la ruta con el servidor*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                                           estac,                                  falt,  corrde,  tipdoc,                     sucu,                                     nocaj) " + 
                                        "VALUES('',    '',         'FALLO','NO ESTA DEFINIDA CARPETA COMPARITDA','" +       Login.sUsrG.replace("'", "''") + "',    now(), '',      'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                       
            }                

            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
            
        }//Fin de if(sCarp.compareTo("")==0)
        
        //Declara variables locales
        String sMens    = "";
        String sAsun    = ""; 
                
        /*Obtiene el mensaje y el asunto que debe tener el correo de agradecmiento*/        
        try
        {            
            sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'agrad'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {                  
                sMens          = rs.getString("extr");                                                
                sAsun          = rs.getString("asun");                                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                       
        }             
        
        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();            

        /*Si la carpeta de agradecmiento no existe entonces creala*/
        sCarp                    += "\\Agradecimiento";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Bandera para saber si hay archivo para mandar adjunto o no*/
        boolean bSi             = false;
        
        /*Si es nulo entonces regresa*/
        if(new File(sCarp).list()==null)
            return;
        
        /*Si existe imágen en el directorio entonces*/
        if(new File(sCarp).list().length > 0)
        {
            /*Obtiene la lista del archivo y completa la ruta a la imágen*/
            String sArch [] = new File(sCarp).list();
            sCarp   = sCarp + "\\" + sArch[0];           
            
            /*Coloca la bandera para saber que si hay adjunto*/
            bSi                 = true;            
        }              
        
        /*Declara algunas variables como final*/
        final String sUsrFi     = sUsr;        
        final String sContraFi  = sContra;
        /*Recorre todo el arreglo de cliente*/
        for (String sEmp : sEmps) 
        {
            /*Obtiene el nombre del cliente de la base de datos y su correo*/
            String sNom = "";
            String sCo1 = "";
            try 
            {
                sQ = "SELECT co1, nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sEmp + "'";	                                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {
                    sNom    = rs.getString("nom");
                    sCo1    = rs.getString("co1");            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                                                       
            }
            
            /*Manda el correo*/
            try 
            {
                //Define las propiedades de conexión
                Properties props = System.getProperties();
                props.setProperty("mail.smtp.host", sServSMTPSal);
                props.put("mail.smtp.starttls.enable", sActSSL);
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.port", sSMTPPort);
                props.put("mail.store.protocol", "pop3");
                props.put("mail.transport.protocol", "smtp");
                final String username = sUsrFi;
                final String password = sContraFi;
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                /*Si hay imágen entonces el mensajea va a ser con imágen*/
                Multipart multipart = null;
                if(bSi)
                {
                    /*Carga la imágen*/
                    multipart               = new MimeMultipart("related");
                    BodyPart htmlPart       = new MimeBodyPart();                                       
                    htmlPart.setContent("<html><body>" +
                            "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNom + "</body></html>", "text/html");                    
                    /*Continua creando la imágen*/
                    multipart.addBodyPart(htmlPart);
                    BodyPart imgPart        = new MimeBodyPart();
                    DataSource ds           = new FileDataSource(sCarp);
                    imgPart.setDataHandler(new DataHandler(ds));
                    imgPart.setHeader       ("Content-ID","the-img-1");
                    multipart.addBodyPart(imgPart);
                }
                
                /*Crea el contenido del mensaje*/
                MimeMessage  msj            = new MimeMessage(session);
                msj.setFrom(new InternetAddress(sUsrFi));
                msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo1));
                msj.setSubject              (sAsun);

                /*Si es con imágen entonces adjunta el mensaje multipart*/
                if(bSi)
                    msj.setContent(multipart);
                /*Else sin imágen entonces inserta el mensaje en el objeto*/
                else
                    msj.setContent("<html>" + sMens + " " + sNom + "<body></body></html>", "text/html; charset=utf-8");
                
                /*Manda el correo*/
                Transport.send(msj);
            }
            catch(MessagingException expnMessag) 
            {                        
                /*Ingresa en la base de datos el registor de que se fallo*/
                try
                {
                    sQ2 = "INSERT INTO logcorrs(      corr,                            nodoc,      estad,          motiv,                                               estac,                                  falt,       corrde,                         tipdoc,                      sucu,                                     nocaj) " +
                                        "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'AGRADECIMIENTO','" +        Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                                                                                           
                }
                
                /*Mandalo al correo alternativo y continua*/
                Star.vMandAlterAgra(sCo1, sEmp, expnMessag.getMessage(), sNom);                                                                
                continue;
                
            } /*Fin de catch(MessagingException expnMessag)*/
            
            /*Ingresa en la base de datos el registro que se mando con éxito*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,                            nodoc,      estad,      motiv,    estac,                                  falt,       corrde,                         tipdoc,                     sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
                
                sQ2 = "INSERT INTO estadiscor(    corr,                            nodoc,      estad,      motiv,    estac,                                  falt,       corrde,                         tipdoc,                     sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'AGRADECIMIENTO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                                                       
            }
            
            /*Modifica la fecha de envio del correo de agradecimiento al cliente*/
            try 
            {                    
                sQ2 = "UPDATE emps SET "
                        + "agradfec                         = DATE(now()) "
                        + "WHERE CONCAT_WS('', ser, codemp) = '" + sEmp + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                                                       
            }
            
        } /*Fin de for(String sCli: sEmps)*/
        
        //Esconde la forma de loading
        Star.vOcultLoadin();
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
        
    }/*Fin de public static void vMandCoAgra(String sEmps[])*/
    
    
    /*Manda correo al alternativo*/
    public static void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc, final String sCodEmp, final int iTip, final String sErr, final String sNomEmp)            
    {
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               

                      
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        /*Determina el cuerpo y el asunto que debe tomar*/
        String sCuerp   = "cuerpfac";
        String sAsunt   = "asunfac";        
        
        /*Si es por cotización entonces*/
        if(iTip==2)
        {
            sCuerp      = "cuerpcot";
            sAsunt      = "asuncot";
        }
        
        /*Contiene el correo alternativo*/
        String sCoAlter = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Trae todos los datos del correo electrónico de la base de datos en base a la estación y otros datos*/        
        try
        {
            sQ = "SELECT cuerpfac, srvsmtpsal, portsmtp, actslenlog, usr, pass, asuncot, " + sAsunt + ", " + sCuerp + ", corralter FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("srvsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");
                sAsunt                      = rs.getString(sAsunt);
                sCuerp                      = rs.getString(sCuerp);
                sCoAlter                    = rs.getString("corralter");
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                                                   
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si es por cumpleaños o por agradecimiento entonces*/
        if(iTip==3 || iTip==4)
        {   
            /*Contiene el mensaje y el asunto*/
            String sMens    = "";
            String sAsun    = ""; 
            
            /*Crea la consulta para obtener el cuerpo y asunto del correo ya sea de agradecimiento o de cumpleaños*/            
            if(iTip==3)
                sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
            else if(iTip==4)
                sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'agrad'";                        
                        
            /*Corre la consulta par obtener el resultado*/
            try
            {
                sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sMens          = rs.getString("extr");                                                
                    sAsun          = rs.getString("asun");                                                
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                                                       
            }  

            /*Si la carpeta de la aplicación compartida en el servidor esta definida entonces*/        
            boolean bSi  = false;
            if(sCarp.compareTo("")!=0)
            {
                /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
                sCarp                       += "\\Imagenes";
                if(!new File(sCarp).exists())
                    new File(sCarp).mkdir();

                /*Determina la carpeta que será en caso de agradecimiento o de cumpleaños*/                
                if(iTip==3)                
                    sCarp                   += "\\Cumpleanos";
                else if(iTip==4)
                    sCarp                   += "\\Agradecimiento";
                
                /*Si la carpeta no existe entonces crea la carpeta*/                
                if(!new File(sCarp).exists())
                    new File(sCarp).mkdir();

                /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
                sCarp                       += "\\" + Login.sCodEmpBD;
                if(!new File(sCarp).exists())
                    new File(sCarp).mkdir();

                /*Si existe imágen en el directorio entonces*/
                if(new File(sCarp).list().length > 0)
                {
                    /*Obtiene la lista del archivo y completa la ruta a la imágen*/
                    String sArch [] = new File(sCarp).list();
                    sCarp   = sCarp + "\\" + sArch[0];

                    /*Coloca la bandera para saber que si hay imágen*/
                    bSi = true;
                }

            }/*Fin de if(sCarp.compareTo()==0)*/

            /*Declara algunas variables como final*/
            final String sUsrFi     = sUsr;
            final String sContraFi  = sCont;

            /*Manda el correo*/
            try
            {
                //Define las propiedades de conexión
                Properties props = System.getProperties();
                props.setProperty("mail.smtp.host", sServSMTPSal);
                props.put("mail.smtp.starttls.enable", sActSSL);
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.port", sSMTPPort);
                props.put("mail.store.protocol", "pop3");
                props.put("mail.transport.protocol", "smtp");
                final String username = sUsrFi;
                final String password = sContraFi;
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                /*Si hay imágen entonces el mensajea va a ser con imágen*/
                Multipart multipart = null;
                if(bSi)
                {
                    /*Carga la imágen*/
                    multipart               = new MimeMultipart("related");
                    BodyPart htmlPart       = new MimeBodyPart();                                       
                    htmlPart.setContent("<html><body>" +
                                        "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNomEmp + " *Fallo envío agradecimiento a " + sCo1 + " por " + sErr + "*</body></html>", "text/html");                    
                    /*Continua creando la imágen*/
                    multipart.addBodyPart(htmlPart);
                    BodyPart imgPart        = new MimeBodyPart();
                    DataSource ds           = new FileDataSource(sCarp);
                    imgPart.setDataHandler(new DataHandler(ds));
                    imgPart.setHeader("Content-ID","the-img-1");
                    multipart.addBodyPart(imgPart);
                }                                        

                /*Determina el mensajea que dirá el error*/
                String sMsj = "";
                if(iTip==3)
                    sMsj    = "Cumpleaños";
                else if(iTip==4)
                    sMsj    = "Agradecimiento";
                
                /*Crea el contenido del mensaje*/
                MimeMessage  msj            = new MimeMessage(session);
                msj.setFrom(new InternetAddress(sUsrFi));
                msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
                msj.setSubject(sAsun + " *Fallo envío " + sMsj + " a " + sCo1 + "*");

                /*Si es con imágen entonces*/
                if(bSi)
                {
                    /*Adjunta el mensaje multipart*/
                    msj.setContent(multipart);
                }
                /*Else sin imágen entonces*/
                else
                {
                    /*Inserta el mensaje en el objeto*/                        
                    msj.setContent("<html>" + sMens + " " + sNomEmp + " *Fallo envío a " + sCo1 + " por " + sErr + "*<body></body></html>", "text/html; charset=utf-8");
                }                                        
                
                /*Manda el correo*/
                Transport.send(msj);
            }
            catch(MessagingException expnMessag)
            {                   
                /*Determina cuál será el mensaje de error a poner en caso de que sea el de agradecimiento o el de cumpleaños*/
                String sMsj = "";
                if(iTip==3)
                    sMsj = "CUMPLEAÑOS ENVIO ALTERNATIVO";
                else if(iTip==4)
                    sMsj = "AGRADECIMIENTO ENVIO ALTERNATIVO";
                
                /*Ingresa en la base de datos el registor de que se fallo*/
                try 
                {                    
                    sQ = "INSERT INTO logcorrs(  corr,                         nodoc,       estad,          motiv,                                                  estac,                                  falt,           corrde,                                 tipdoc,                             sucu,                                     nocaj) " + 
                                      "VALUES('" + sCo1.replace("'", "''")+ "','',          'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" +     sUsr.replace("'", "''") + "', '" +      sMsj.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                                                                           
                }                               
                
                /*Actualiza el registro en la tabla de cumpleaños para que se vuelva intentar enviar en caso de que sea por cumpleaños*/                
                if(iTip==3)
                {
                    try 
                    {                
                        sQ = "UPDATE cumple SET anio = YEAR(CURDATE()) WHERE id_cumple = " + sNombPDF;                                        
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                                                                                                                                                                               
                     }                        
                }                                    
                
                /*Regresa*/
                return;
                
            }/*Fin de catch(MessagingException expnMessag)*/

            /*Determina cuál será el mensaje de error a poner en caso de que sea el de agradecimiento o el de cumpleaños*/
            String sMsj = "";
            if(iTip==3)
                sMsj = "CUMPLEAÑOS ENVIO ALTERNATIVO";
            else if(iTip==4)
                sMsj = "AGRADECIMIENTO ENVIO ALTERNATIVO";

            /*Ingresa en la base de datos el registro de que se mando con éxito*/
            try 
            {          
                sQ= "INSERT INTO logcorrs(corr,                         nodoc,       estad,     motiv,  estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                             "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sMsj.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                sQ= "INSERT INTO estadiscor(corr,                         nodoc,       estad,     motiv,  estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                             "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sMsj.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                       
            }                        

        }/*Fin de if(iTip==3)*/
        /*Else if es cotización o factura entonces*/
        else if(iTip==1 || iTip==2)
        {            
            /*Declara variables final para que el objeto las acepte*/
            final String sUser  = sUsr;
            final String sContra= sCont;
            
            //Muestra el loading
            Star.vMostLoading("");
        
            /*Manda el correo*/
            try
            {
                //Define las propiedades de conexión
                Properties props = System.getProperties();
                props.setProperty("mail.smtp.host", sServSMTPSal);
                props.put("mail.smtp.starttls.enable", sActSSL);
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.port", sSMTPPort);
                props.put("mail.store.protocol", "pop3");
                props.put("mail.transport.protocol", "smtp");
                final String username = sUser;
                final String password = sContra;
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                MimeMessage  msj    = new MimeMessage(session);
                msj.setFrom         (new InternetAddress(sUser));
                msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
                msj.setSubject      (sAsunt + "\"" + sNoDoc + "\" Fallo envío a " + sCo1 + " por " + sErr);
                String msg          = sCuerp + " *Fallo envío a " + sCo1 + "*";
                msj.setContent      (msg, "text/html; charset=utf-8");

                /*Genera el adjunto*/
                BodyPart msgBod     = new MimeBodyPart();
                msgBod.setText      (sCuerp + " *Fallo envío a " + sCo1 + " por " + sErr + "*");
                Multipart mult      = new MimeMultipart();
                mult.addBodyPart    (msgBod);

                /*Adjunta el PDF*/
                msgBod              = new MimeBodyPart();                                               
                DataSource src1     = new FileDataSource(sRutPDF);                        
                msgBod.setDataHandler(new DataHandler(src1));
                msgBod.setFileName  (sNombPDF);
                mult.addBodyPart    (msgBod);                                                

                /*Junta todo y manda el correo*/
                msj.setContent      (mult);               
                Transport.send(msj);
            }
            catch(MessagingException expnMessag)
            {
                /*Determina el mensajea que tendrá*/
                String sMsj;
                if(iTip==1)
                    sMsj    = "COTIZACIÓN ENVIO ALTERNATIVO";
                else
                    sMsj    = "FACTURA ENVIO ALTERNATIVO";
                
                /*Ingresa en la base de datos el registro de que se fallo*/
                try 
                {                
                    sQ = "INSERT INTO logcorrs(   corr,                                     nodoc,                          estad,       motiv,                                                 estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                     nocaj) " + 
                                     "VALUES('" + sCoAlter.replace("'", "''") + "','" +     sNoDoc.replace("'", "''") + "', 'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sMsj.replace("'", "''") + "','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                                
                }

                /*Regresa no puede continuar*/
                return;
                
            }/*Fin de catch(MessagingException expnMessag)*/
                        
            /*Determina el mensajea que tendrá*/
            String sMsj;
            if(iTip==1)
                sMsj    = "COTIZACIÓN ENVIO ALTERNATIVO";
            else
                sMsj    = "FACTURA ENVIO ALTERNATIVO";

            /*Ingresa en la base de datos el registor de que se mando con éxito*/
            try 
            {          
                sQ = "INSERT INTO logcorrs(   corr,                                 nodoc,                          estad,   motiv,     estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                                 "VALUES('" + sCoAlter.replace("'", "''") + "','" + sNoDoc.replace("'", "''") + "', 'ENVIADO','','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sMsj.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                sQ = "INSERT INTO estadiscor(   corr,                                 nodoc,                          estad,   motiv,     estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                                 "VALUES('" + sCoAlter.replace("'", "''") + "','" + sNoDoc.replace("'", "''") + "', 'ENVIADO','','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sMsj.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                            
            }

            //Esconde la forma de loading
            Star.vOcultLoadin();
                
        }/*Fin de if(iTip==1 || iTip==2)*/

    }/*Fin de public static void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc, final String sCodEmp, final int iTip, final String sErr, final String sNomEmp)            */                                            
    

    /*Manda correo al alternativo*/
    public static void vMandAlterCump(final String sCo1, final String sCodEmp, final String sErr, final String sNomEmp, final String sTip)            
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               
        String      sCoAlter                = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Trae todos los datos de conexión al correo electrónico*/        
        try
        {
            sQ = "SELECT corralter, srvsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("srvsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");                                
                sCoAlter                    = rs.getString("corralter");                                
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                                                                                                   
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Si no esta definida la ruta al servidor entonces
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        //Declara variables locales
        String sMens    = "";
        String sAsun    = "";
        
        /*Obtiene el cuerpo y asunto del correo de agradecimiento*/        
        try
        {
            sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sMens          = rs.getString("extr");                                                
                sAsun          = rs.getString("asun");                                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }  

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                       += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de cumpleaños no existe entonces crea la carpeta*/                
        sCarp                       += "\\Cumpleanos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                       += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si existe imágen en el directorio entonces*/
        boolean bSi                 = false;
        if(new File(sCarp).list().length > 0)
        {
            /*Obtiene la lista del archivo y completa la ruta a la imágen*/
            String sArch [] = new File(sCarp).list();
            sCarp   = sCarp + "\\" + sArch[0];

            /*Coloca la bandera para saber que si hay imágen*/
            bSi = true;
        }
    
        /*Declara variables final para el thread*/        
        final String sContraFi  = sCont;
        final String sUsrFi     = sUsr;
        /*Manda el correo*/
        try
        {
            //Define las propiedades de conexión
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", sServSMTPSal);
            props.put("mail.smtp.starttls.enable", sActSSL);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", sSMTPPort);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = sUsrFi;
            final String password = sContraFi;
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            /*Si hay imágen entonces el mensajea va a ser con imágen*/
            Multipart multipart = null;
            if(bSi)
            {
                /*Carga la imágen*/
                multipart               = new MimeMultipart("related");
                BodyPart htmlPart       = new MimeBodyPart();                                       
                htmlPart.setContent("<html><body>" +
                                    "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNomEmp + " *Fallo envío agradecimiento a " + sCo1 + " por " + sErr + "*</body></html>", "text/html");                    
                /*Continua creando la imágen*/
                multipart.addBodyPart(htmlPart);
                BodyPart imgPart        = new MimeBodyPart();
                DataSource ds           = new FileDataSource(sCarp);
                imgPart.setDataHandler(new DataHandler(ds));
                imgPart.setHeader("Content-ID","the-img-1");
                multipart.addBodyPart(imgPart);
            }                                        

            /*Crea el contenido del mensaje*/
            MimeMessage  msj            = new MimeMessage(session);
            msj.setFrom(new InternetAddress(sUsr));
            msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
            msj.setSubject(sAsun + " *Fallo envío Cumpleaños a " + sCo1 + "*");

            /*Si es con imágen entonces afjunta el mensajea  multipart*/
            if(bSi)
                msj.setContent(multipart);
            /*Else sin imágen entonces inserta el mensajea en el objeto*/
            else                                                        
                msj.setContent("<html>" + sMens + " " + sNomEmp + " *Fallo envío a " + sCo1 + " por " + sErr + "*<body></body></html>", "text/html; charset=utf-8");                                                     

            /*Manda el correo*/
            Transport.send(msj);
        }
        catch(MessagingException expnMessag)
        {                   
            /*Ingresa en la base de datos el registor de que se fallo*/
            try 
            {                    
                sQ = "INSERT INTO logcorrs(  corr,                         nodoc,       estad,          motiv,                                                  estac,                                  falt,           corrde,                                 tipdoc,                                      sucu,                                     nocaj) " + 
                                  "VALUES('" + sCo1.replace("'", "''")+ "','',          'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" +     sUsr.replace("'", "''") + "', '" +      "CUMPLEAÑOS ENVIO ALTERNATIVO" + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                
            }                               

            /*Regresa*/
            return;

        }/*Fin de catch(MessagingException expnMessag)*/

        /*Ingresa en la base de datos el registro de que se mando con éxito*/
        try 
        {          
            sQ= "INSERT INTO logcorrs(corr,                         nodoc,       estad,     motiv,  estac,                                  falt,       corrde,                       tipdoc,                               sucu,                                      nocaj) " + 
                         "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', 'CUMPLEAÑOS ENVIO ALTERNATIVO','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            sQ= "INSERT INTO estadiscor(corr,                         nodoc,       estad,     motiv,  estac,                                  falt,       corrde,                       tipdoc,                               sucu,                                      nocaj) " + 
                         "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', 'CUMPLEAÑOS ENVIO ALTERNATIVO','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
        }                               
    
    }/*Fin de public static void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc, final String sCodEmp, final int iTip, final String sErr, final String sNomEmp)            */                                            
    
    
    /*Manda correo al alternativo de agradecmiento*/
    public static void vMandAlterAgra(final String sCo1, final String sCodEmp, final String sErr, final String sNomEmp)            
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               
        String      sCoAlter                = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Trae todos los datos de conexión al correo electrónico*/        
        try
        {
            sQ = "SELECT corralter, srvsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("srvsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");                                
                sCoAlter                    = rs.getString("corralter");                                
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Si no esta definida la ruta al servidor entonces
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        //Declara variables locales
        String sMens    = "";
        String sAsun    = "";
        
        /*Obtiene el cuerpo y asunto del correo de agradecimiento*/        
        try
        {
            sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sMens          = rs.getString("extr");                                                
                sAsun          = rs.getString("asun");                                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }  

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                       += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de cumpleaños no existe entonces crea la carpeta*/                
        sCarp                       += "\\Agradecimiento";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                       += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si existe imágen en el directorio entonces*/
        boolean bSi                 = false;
        if(new File(sCarp).list().length > 0)
        {
            /*Obtiene la lista del archivo y completa la ruta a la imágen*/
            String sArch [] = new File(sCarp).list();
            sCarp   = sCarp + "\\" + sArch[0];

            /*Coloca la bandera para saber que si hay imágen*/
            bSi = true;
        }
    
        /*Declara variables final para el thread*/        
        final String sContraFi  = sCont;
        final String sUsrFi     = sUsr;
        /*Manda el correo*/
        try
        {
            //Define las propiedades de conexión
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", sServSMTPSal);
            props.put("mail.smtp.starttls.enable", sActSSL);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", sSMTPPort);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = sUsrFi;
            final String password = sContraFi;
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            /*Si hay imágen entonces el mensajea va a ser con imágen*/
            Multipart multipart = null;
            if(bSi)
            {
                /*Carga la imágen*/
                multipart               = new MimeMultipart("related");
                BodyPart htmlPart       = new MimeBodyPart();                                       
                htmlPart.setContent("<html><body>" +
                                    "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNomEmp + " *Fallo envío agradecimiento a " + sCo1 + " por " + sErr + "*</body></html>", "text/html");                    
                /*Continua creando la imágen*/
                multipart.addBodyPart(htmlPart);
                BodyPart imgPart        = new MimeBodyPart();
                DataSource ds           = new FileDataSource(sCarp);
                imgPart.setDataHandler(new DataHandler(ds));
                imgPart.setHeader("Content-ID","the-img-1");
                multipart.addBodyPart(imgPart);
            }                                        

            /*Crea el contenido del mensaje*/
            MimeMessage  msj            = new MimeMessage(session);
            msj.setFrom(new InternetAddress(sUsr));
            msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
            msj.setSubject(sAsun + " *Fallo envío Cumpleaños a " + sCo1 + "*");

            /*Si es con imágen entonces afjunta el mensajea  multipart*/
            if(bSi)
                msj.setContent(multipart);
            /*Else sin imágen entonces inserta el mensajea en el objeto*/
            else                                                        
                msj.setContent("<html>" + sMens + " " + sNomEmp + " *Fallo envío a " + sCo1 + " por " + sErr + "*<body></body></html>", "text/html; charset=utf-8");                                                     

            /*Manda el correo*/
            Transport.send(msj);
        }
        catch(MessagingException expnMessag)
        {                   
            /*Ingresa en la base de datos el registor de que se fallo*/
            try 
            {                    
                sQ = "INSERT INTO logcorrs(  corr,                         nodoc,       estad,          motiv,                                                  estac,                                  falt,           corrde,                                 tipdoc,                                             sucu,                                     nocaj) " + 
                                  "VALUES('" + sCo1.replace("'", "''")+ "','',          'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" +     sUsr.replace("'", "''") + "', '" +      "AGRADECIMIENTO ENVIO ALTERNATIVO" + "','" +        Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                
            }                               

            /*Regresa*/
            return;

        }/*Fin de catch(MessagingException expnMessag)*/

        /*Ingresa en la base de datos el registro de que se mando con éxito*/
        try 
        {          
            sQ= "INSERT INTO logcorrs(corr,                         nodoc,       estad,     motiv,  estac,                                  falt,       corrde,                       tipdoc,                                       sucu,                                      nocaj) " + 
                         "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', 'AGRADECIMIENTO ENVIO ALTERNATIVO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            sQ= "INSERT INTO estadiscor(corr,                         nodoc,       estad,     motiv,  estac,                                  falt,       corrde,                       tipdoc,                                       sucu,                                      nocaj) " + 
                         "VALUES('" + sCo1.replace("'", "''") + "', '',          'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', 'AGRADECIMIENTO ENVIO ALTERNATIVO','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                          
        }                               
    
    }/*Fin de public static void vMandAlterAgra(final String sCo1, final String sCodEmp, final String sErr, final String sNomEmp*/                                            
    
    
    /*Manda el correo con el PDF y XML de su factura a la empresa*/            
    public static void vMandPDFEmp(final String sNombPDF, final String sRutPDF, final String sNombXML, final String sRutXML, final String sCo1, final String sCo2, final String sCo3, final String sNoDoc, final String sTip)
    {        
        //Muestra el loading
        Star.vMostLoading("");
        
        /*Manda el PDF en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {                 
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                

                //Declara variables locales
                String      sServSMTPSal            = "";
                String      sSMTPPort               = "";
                String      sUsr                    = "";
                String      sCont                   = "";
                String      sActSSL                 = "";                               
                
                /*Determina que asunto va a usar*/                
                String sAsunT   = "";
                String sCuerpT  = "";
                if(sTip.compareTo("FAC")==0 || sTip.compareTo("REM")==0)
                {
                    sAsunT   = "asunfac";
                    sCuerpT  = "cuerpfac";
                }
                else if(sTip.compareTo("ORD")==0)
                {
                    sAsunT   = "asunord";
                    sCuerpT  = "cuerpord";
                }
                                                
                /*Contiene el asunto y el cuerpo*/
                String sAsun = "";
                String sCuer = "";                

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 
                
                /*Trae todos los datos del correo electrónico de la base de datos en base a la estación*/                
                try
                {
                    sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, " + sAsunT + ", " + sCuerpT + ", corralter FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene todos los datos de la consulta*/
                        sServSMTPSal                = rs.getString("srvsmtpsal");
                        sSMTPPort                   = rs.getString("portsmtp");
                        sUsr                        = rs.getString("usr");
                        sCont                       = rs.getString("pass");
                        sAsun                       = rs.getString(sAsunT);
                        sCuer                       = rs.getString(sCuerpT);                        
                        
                        /*Si activar ssl login esta activado entonces guarda true*/
                        if(rs.getString("actslenlog").compareTo("1")==0)
                            sActSSL = "true";
                        else
                            sActSSL = "false";                       
                        
                        /*Desencripta la contraseña*/
                        sCont                       = Star.sDecryp(sCont);                        
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                               
                }
                
                /*Determina el tipo de log*/
                String sTi  = "FAC";
                if(sTip.compareTo("ORD")==0)
                    sTi     = "ORDC";
                else if(sTip.compareTo("REM")==0)
                    sTi     = "REM";

                /*Crea el usuario y la contraseña como final para que el thread los válide*/
                final String sUser      = sUsr;
                final String sContra    = sCont;
                
                /*Si el primer correo no es null entonces*/
                if(sCo1!=null && sCo1.compareTo("")!=0)
                {                    
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUser));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo1));
                        msj.setSubject      (sAsun + "\"" + sNoDoc + "\"");
                        String msg           = sCuer;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msgBod     = new MimeBodyPart();
                        msgBod.setText      (sCuer);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msgBod);
                        
                        /*Si el PDF no existe entonces agrega en el log*/
                        if(!new File(sRutPDF).exists())
                            Login.vLog("La ruta del PDF: " + sRutPDF + " no existe al querer adjuntarse en el correo de timbrado de factura.");
                        
                        /*Adjunta el PDF*/
                        msgBod              = new MimeBodyPart();                                               
                        DataSource src1     = new FileDataSource(sRutPDF);                        
                        msgBod.setDataHandler(new DataHandler(src1));
                        msgBod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msgBod);                                                

                        /*Si el XML no existe entonces agrega en el log*/
                        if(!new File(sRutXML).exists())
                            Login.vLog("La ruta del XML: " + sRutXML + " no existe al querer adjuntarse en el correo de timbrado de factura.");
                        
                        /*Genera el adjunto si es factura*/
                        if(sTip.compareTo("FAC")==0)
                        {
                            /*Adjunta el XML*/
                            msgBod              = new MimeBodyPart();                                               
                            DataSource src2     = new FileDataSource(sRutXML);                        
                            msgBod.setDataHandler(new DataHandler(src2));                                                                        
                            msgBod.setFileName  (sNombXML);
                            mult.addBodyPart    (msgBod);                        
                        }                                                    
                        
                        /*Junta todo y manda correo*/
                        msj.setContent(mult);                                            
                        Transport.send(msj);                       
                                                
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                           
                            sQ = "INSERT INTO logcorrs (  corr,                                 nodoc,                              estad,    motiv,     estac,                                      falt,         corrde,                               tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" + sCo1.replace("'", "''") + "','" +     sNoDoc.replace("'", "''") + "',     'ENVIADO','','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" +   sUsr.replace("'", "''") + "', '" +    sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(  corr,                                 nodoc,                              estad,    motiv,     estac,                                      falt,         corrde,                               tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" + sCo1.replace("'", "''") + "','" +     sNoDoc.replace("'", "''") + "',     'ENVIADO','','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" +   sUsr.replace("'", "''") + "', '" +    sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                            
                        }                        
                    }
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (    corr,                               nodoc,                          estad,          motiv,                                                  estac,                                  falt,       corrde,                             tipdoc,                             sucu,                 nocaj) " + 
                                             "VALUES('" +   sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTi.replace("'", "''") + "','" +    Star.sSucu + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                            
                        }
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter2(sNombPDF, sRutPDF, sNombXML, sRutXML, sCo1, sNoDoc);                                                
                    }                        
                    
                }/*Fin de if(sCo1!=null)*/
                
                /*Si el segundo correo no es null entonces*/
                if(sCo2!=null  && sCo2.compareTo("")!=0)
                {
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUser));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo2));
                        msj.setSubject      (sAsun + "\"" + sNoDoc + "\"");
                        String msg           = sCuer;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msgBod     = new MimeBodyPart();
                        msgBod.setText      (sCuer);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msgBod);
                        
                        /*Adjunta el PDF*/
                        msgBod              = new MimeBodyPart();                                               
                        DataSource src1     = new FileDataSource(sRutPDF);                        
                        msgBod.setDataHandler(new DataHandler(src1));
                        msgBod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msgBod);                                                
                        
                        /*Genera el adjunto si es factura*/
                        if(sTip.compareTo("FAC")==0)
                        {
                            /*Adjunta el XML*/
                            msgBod              = new MimeBodyPart();                                               
                            DataSource src2     = new FileDataSource(sRutXML);                        
                            msgBod.setDataHandler(new DataHandler(src2));                                                                        
                            msgBod.setFileName  (sNombXML);
                            mult.addBodyPart    (msgBod);                        
                        }                                                    
                        
                        /*Junta todo y manda el correo*/
                        msj.setContent(mult);                                             
                        Transport.send(msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (    corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                                 tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" +   sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +      sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(    corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                                 tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" +   sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +      sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                                        
                        }                   
                        
                    }
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (    corr,                               nodoc,                          estad,          motiv,                                               estac,                                  falt,       corrde,                             tipdoc,                             sucu,                                     nocaj) " + 
                                             "VALUES('" +   sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTip.replace("'", "''") + "', '" +  Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                            
                        }
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter2(sNombPDF, sRutPDF, sNombXML, sRutXML, sCo2, sNoDoc);                                                
                    }                       
                    
                }/*Fin de if(sCo2!=null)*/
                
                /*Si el primer correo no es null entonces*/
                if(sCo3!=null  && sCo3.compareTo("")!=0)
                {
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUser));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo3));
                        msj.setSubject      (sAsun + "\"" + sNoDoc + "\"");
                        String msg          = sCuer;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Crea el cuerpo*/
                        BodyPart msgBod     = new MimeBodyPart();
                        msgBod.setText      (sCuer);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msgBod);
                        
                        /*Adjunta el PDF*/
                        msgBod              = new MimeBodyPart();                                               
                        DataSource src1     = new FileDataSource(sRutPDF);                        
                        msgBod.setDataHandler(new DataHandler(src1));
                        msgBod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msgBod);                                                
                        
                        /*Genera el adjunto si es factura*/
                        if(sTip.compareTo("FAC")==0)
                        {
                            /*Adjunta el XML*/
                            msgBod              = new MimeBodyPart();                                               
                            DataSource src2     = new FileDataSource(sRutXML);                        
                            msgBod.setDataHandler(new DataHandler(src2));                                                                        
                            msgBod.setFileName  (sNombXML);
                            mult.addBodyPart    (msgBod);                        
                        }                                                    
                        
                        /*Junta todo*/
                        msj.setContent(mult);
                        
                        /*Manda el correo*/
                        Transport.send(msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (    corr,                                   nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" +   sCo3.replace("'", "''") + "','" +       sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(    corr,                                   nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                             tipdoc,                             sucu,                                           nocaj) " + 
                                             "VALUES('" +   sCo3.replace("'", "''") + "','" +       sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTi.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                            
                        }
                        
                    }
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registro de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (    corr,                               nodoc,                          estad,          motiv,                                                  estac,                                  falt,        corrde,                            tipdoc,                                 sucu,                                     nocaj) " + 
                                             "VALUES('" +   sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" +  sUsr.replace("'", "''") + "', '" + sTi.replace("'", "''") + "','" +        Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                                        
                        }
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter2(sNombPDF, sRutPDF, sNombXML, sRutXML, sCo3, sNoDoc);                                                
                    }
                                            
                }/*Fin de if(sCo3!=null)*/
                
                //Esconde la forma de loading
                Star.vOcultLoadin();
                
                //Cierra la base de datos
                Star.iCierrBas(con);                    
               
            }/*Fin de public void run()*/            
        }).start();
        
    }/*Fin de private void vMandPDFEmp(String sRutPDF, String sCo1, String sCo2, String sCo3, String sIdCorreoSelec)*/
    
    
    /*Encriptar información*/    
    public static String sEncrip(String sData) 
    {
        //Declara variables locales
        String encrypVal;
        
        /*Encripta los datos*/
        try 
        {
            Key key         = new SecretKeySpec(sClavEncrip, "AES");
            Cipher c        = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal   = c.doFinal(sData.getBytes());
            encrypVal       = new BASE64Encoder().encode(encVal);           
        }
        catch(NoSuchPaddingException expnNoSuchPad)
        {
            encrypVal = "Error al encryptar por " + expnNoSuchPad.getMessage();
        }
        catch(InvalidKeyException expnInvalKey)
        {
            encrypVal = "Error al encryptar por " + expnInvalKey.getMessage();
        }
        catch(IllegalBlockSizeException expnIlegSizeBloq)
        {
            encrypVal = "Error al encryptar por " + expnIlegSizeBloq.getMessage();
        }
        catch(BadPaddingException expnBadPad)
        {
            encrypVal = "Error al encryptar por " + expnBadPad.getMessage();
        }
        catch(NoSuchAlgorithmException expnNoSuchAlgo)
        {
            encrypVal = "Error al encryptar por " + expnNoSuchAlgo.getMessage();
        }
        
        /*Devuelve el resultado*/
        return encrypVal;
        
    }/*Fin de String sEncrip(String sData)*/

    /*Manda la factura al correo alternativo*/
    public static void vMandAlter2(final String sNombPDF, final String sRutPDF, final String sNombXML, final String sRutXML, final String sCo1, final String sNoDoc)            
    {
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                    
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        //Variables de correo
        String sAsunFac = "";
        String sCuerFac = "";
        String sCoAlter = "";
        
        /*Trae todos los datos del correo electrónico de la base de datos en base a la estación*/        
        try
        {
            sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, asunfac, cuerpfac, corralter FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("srvsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");
                sAsunFac                    = rs.getString("asunfac");
                sCuerFac                    = rs.getString("cuerpfac");
                sCoAlter                    = rs.getString("corralter");

                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                    
        }

        /*Crea el usr y la contraseña como final para que el th valide si son válidos o no*/
        final String sUser      = sUsr;
        final String sContra    = sCont;

        //Muestra el loading
        Star.vMostLoading("");
        
        /*Manda el correo*/
        try
        {
            //Define las propiedades de conexión
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", sServSMTPSal);
            props.put("mail.smtp.starttls.enable", sActSSL);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", sSMTPPort);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = sUser;
            final String password = sContra;
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            MimeMessage  msj    = new MimeMessage(session);
            msj.setFrom         (new InternetAddress(sUser));
            msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
            msj.setSubject      (sAsunFac + "\"" + sNoDoc + "\" Fallo envío a " + sCo1);
            String msg          = sCuerFac + " *Fallo envío a " + sCo1 + "*";
            msj.setContent      (msg, "text/html; charset=utf-8");

            /*Genera el adjunto*/
            BodyPart msgBod     = new MimeBodyPart();
            msgBod.setText      (sCuerFac + " *Fallo envío a " + sCo1 + "*");
            Multipart mult      = new MimeMultipart();
            mult.addBodyPart    (msgBod);

            /*Adjunta el PDF*/
            msgBod              = new MimeBodyPart();                                               
            DataSource src1     = new FileDataSource(sRutPDF);                        
            msgBod.setDataHandler(new DataHandler(src1));
            msgBod.setFileName  (sNombPDF);
            mult.addBodyPart    (msgBod);                                                

            /*Adjunta el XML*/
            msgBod              = new MimeBodyPart();                                               
            DataSource src2     = new FileDataSource(sRutXML);                        
            msgBod.setDataHandler(new DataHandler(src2));                                                                        
            msgBod.setFileName  (sNombXML);
            mult.addBodyPart    (msgBod);                        

            /*Junta todo y manda el correo*/
            msj.setContent(mult);        
            Transport.send(msj);
        }
        catch(MessagingException expnMessag)
        {
            /*Ingresa en la base de datos el registor de que se fallo*/
            try 
            {                
                sQ = "INSERT INTO logcorrs (    corr,                               nodoc,                          estad,          motiv,                                                  estac,                                  falt,       corrde,                         tipdoc,                          sucu,                                   nocaj) " + 
                                 "VALUES('" +   sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'FACTURA ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {                  
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                        
            }

            /*Regresa no puede continuar*/
            return;
        }

        //Esconde la forma de loading
        Star.vOcultLoadin();
                
        /*Ingresa en la base de datos el registor de que se mando con éxito*/
        try 
        {            
            sQ = "INSERT INTO logcorrs(     corr,                                   nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                         tipdoc,                             sucu,                                           nocaj) " + 
                             "VALUES('" +   sCo1.replace("'", "''") + "','" +       sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'FACTURA ENVIO ALTERNATIVO', '" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            sQ = "INSERT INTO estadiscor(     corr,                                   nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                         tipdoc,                             sucu,                                           nocaj) " + 
                             "VALUES('" +   sCo1.replace("'", "''") + "','" +       sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'FACTURA ENVIO ALTERNATIVO', '" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
        }        
                
    }/*Fin de private void vMandAlter(final String sNombPDF, final String sRutPDF, final String sNombXML, final String sRutXML, final String sCo1, final String sNoDoc)            */            
        
    
    /*Función para recalcular los totales*/
    public static void vRecalcTot(JTable jTab1,  JTextField jTSubTotMat1, JTextField jTManObr1, JTextField jTSubTotGral1)
    {
        //Declara variables locales
        String          sSubTotMat;
        String          sManObr;            
        String          sSubTot;        
        NumberFormat    n;
        double          dCant;
        
        
        
        
        /*Inicialmente comienza en 0*/
        sSubTotMat                 = "0";
        
        /*Si la tabla no tiene elementos entonces*/
        if(jTab1.getRowCount()== 0 )
        {
            /*Colocar todos los campos en 0*/
            jTSubTotMat1.setText    ("$0.00");
            jTManObr1.setText       ("$0.00");
            jTSubTotGral1.setText   ("$0.00");            
        }
        /*Recorre toda la tabla para hacer los calculos*/
        else
        {
            /*Recalcula los importes de todas las partidas de la tabla*/
            for( int row = 0; row < jTab1.getRowCount(); row++)
            {                                    
                /*Declara variables de bloque*/
                String sImpo;

                /*Lee el importe de la fila*/
                sImpo                   = jTab1.getModel().getValueAt(row, 7).toString().replace("$", "").replace(",", "");

                /*Suma el importe de la fila al subtotal de materiales*/
                sSubTotMat              = Double.toString(Double.parseDouble(sSubTotMat) + Double.parseDouble(sImpo));
            }

            /*Formatea a moneda el sub total de materiales*/
            dCant                       = Double.parseDouble(sSubTotMat);
            n                           = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSubTotMat                  = n.format(dCant);

            /*Colocalo en el campo de sub total de materiales*/
            jTSubTotMat1.setText(sSubTotMat);
        }
                        
        /*Si el campo de sub total de matariales tiene el signo de dollar quitaselo*/
        sSubTotMat                      = sSubTotMat.replace("$", "").replace(",", "");

        /*Lee el campo de mano de obra para hacer los calculos*/
        sManObr                         = jTManObr1.getText().replace("$", "").replace(",", "");                                
        
        /*Obtener el subtotal general que es la suma de el subtotal de los materiales y la mano de obra*/
        sSubTot                         = Double.toString(Double.parseDouble(sSubTotMat) + Double.parseDouble(sManObr));        
        
        /*Formatear el subtotal a moneda*/
        dCant                           = Double.parseDouble(sSubTot);
        n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sSubTot                         = n.format(dCant);
        
        /*Colocalo en el campo*/
        jTSubTotGral1.setText(sSubTot);
        
    }/*Fin de private void vRecalcTot()*/

    
    /*Función para recalcular los totales*/
    public static void vRecalcTotN(JTable jTab,  JTextField jTSubTot, JTextField jTImpue, JTextField jTTot, JTextField jTTotDesc)
    {
        //Declara variables locales
        String          sSubTot     = "0";
        String          sImpue      = "0";                           
        String          sTotDesc    = "0";        
        
        
        
                
        /*Si la tabla no tiene elementos entonces*/
        if(jTab.getRowCount()== 0 )
        {
            /*Colocar todos los campos en 0*/
            jTSubTot.setText        ("$0.00");
            jTImpue.setText         ("$0.00");
            jTTot.setText           ("$0.00");            
            
            /*Regresa*/
            return;
        }
        
        /*Recalcula los importes de todas las partidas de la tabla*/
        for( int row = 0; row < jTab.getRowCount(); row++)
        {                                    
            /*Suma el importe de la fila al subtotal*/
            sSubTot              = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getValueAt(row, 9).toString().replace("$", "").replace(",", "")));

            /*Suma el impuesto de la fila al impuesto global*/
            sImpue              = Double.toString(Double.parseDouble(sImpue) + Double.parseDouble(jTab.getValueAt(row, 11).toString().replace("$", "").replace(",", "")));

            /*Suma el descuento de la fila al descuento global*/
            sTotDesc            = Double.toString(Double.parseDouble(sTotDesc) + Double.parseDouble(jTab.getValueAt(row, 12).toString().replace("$", "").replace(",", "")));                        
        }

        //Comprueba si debe de redondear o no el total
        String sResp    = Star.sGetConfRedon(null);
        
        //Si hubo error entonces regresa
        if(sResp==null)
            return;
        
        //Obtiene si se tiene que redondear y las posiciones a redondear
        java.util.StringTokenizer stk   = new java.util.StringTokenizer(sResp, "|");
        String sRedo            = stk.nextToken();
        String sNumRedo         = stk.nextToken();                
        
        //Si no se tiene que redondear entonces deja la cantidad en 2
        if(sRedo.compareTo("0")==0)
            sNumRedo            = "2";
        
        /*Obtiene el total que es el subtotal + el impuesto*/
        String sTot             = Double.toString(Star.dRound(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue), Integer.parseInt(sNumRedo)));        
                
        /*Formatea a moneda el sub total*/
        double dCant            = Double.parseDouble(sSubTot);
        NumberFormat n          = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sSubTot                 = n.format(dCant);

        /*Colocalo en su campo*/
        jTSubTot.setText        (sSubTot);        
                        
        /*Formatear el impuesto a moneda*/
        dCant                   = Double.parseDouble(sImpue);
        n                       = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImpue                  = n.format(dCant);
        
        /*Colocalo en su control*/
        jTImpue.setText         (sImpue);
        
        /*Formatear el total a moneda*/
        dCant                   = Double.parseDouble(sTot);
        n                       = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot                    = n.format(dCant);
        
        /*Colocalo en su control*/
        jTTot.setText           (sTot);
        
        /*Formatear el descuento total a moneda*/
        dCant                   = Double.parseDouble(sTotDesc);
        n                       = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTotDesc                = n.format(dCant);
        
        /*Colocalo en su control*/
        jTTotDesc.setText       (sTotDesc);                
        
    }/*Fin de private void vRecalcTotN()*/
    
    
    /*Función para mostrar la calculadora*/
    public static void vShowCal()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene la ruta hacia la calculadora*/
        String sRut = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE conf = 'calc' AND clasif = 'sist'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sRut      = rs.getString("extr");                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la ruta es cadena vacia entonces se va a abrir la calculadora de windows*/        
        if(sRut.compareTo("")==0)
            sRut    = "calc";      
        
        /*Abre la calculadora de windows*/
        try
        {
            Runtime.getRuntime().exec(sRut);
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                               
        }                        
        
    }/*Fin de public static void vShowCal()*/   

    
    /*Función para hacer el timbrado y generar PDF y XML*/
    public static synchronized void vGenTim(String sTip, String sDirEn, String sConFac, String sVta, String sCatGral, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sSerFac, String sMetPag, String sCta, String sConds, String sRutLoc, java.io.InputStream isRepNam, boolean bVeFac, boolean bImpFac, String sC1, String sC2, String sC3, boolean bCo1, boolean bCo2, boolean bCo3, boolean bMandCo, int iFil, JTable jTab, boolean bBusc, String sMon, String sTotDescu, String sFormPag, String sTipDocS, String sTipCam, String sCtaPred)
    {                
        /*Determina el mensaje que se va a mandar*/        
        String sMsj = "Factura";
        if(sTip.compareTo("notc")==0)
            sMsj    = "Nota de crédito";            
        if(sTip.compareTo("notp")==0)
            sMsj    = "Nota de crédito";
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
        {
            //Si la fila no es -1 entonces actualiza en la tabla de que no se timbro
            if(iFil!=-1)            
                jTab.setValueAt("No", iFil, 22);
            //Coloca la banera del error
            Star.bErr    = true;
            
            //Regresa
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\Imagenes\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = sRutLoc;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si tiene que imprimir o no el logo en la factura*/
        boolean bSiLog          = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'logofac'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene si se tiene que mandar o no*/
                if(rs.getString("val").compareTo("1")==0)                                   
                    bSiLog = true;
            }
        }
        catch(SQLException expnSQL)
        {
            /*Actualiza en la tabla de que no se timbró*/            
            jTab.setValueAt("No", iFil, 22);
            
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }

        //Declara variables locales
        String sNomLoc      = "";
        String sCallLoc     = "";
        String sTelLoc      = "";
        String sColLoc      = "";
        String sCPLoc       = "";
        String sCiuLoc      = "";
        String sEstLoc      = "";
        String sPaiLoc      = "";
        String sRFCLoc      = "";
        String sNoExtLoc    = "";        
        String sNoIntLoc    = "";
        
        /*Obtiene todos los datos de la empresa local*/       
        try
        {                  
            sQ = "SELECT noint, noext, IFNULL(nom, '') AS nom, IFNULL(calle,'') AS calle, IFNULL(tel,'') AS tel, IFNULL(col,'') AS col, IFNULL(cp,'') AS cp, IFNULL(ciu,'') AS ciu, IFNULL(estad,'') AS estad, IFNULL(pai,'') AS pai, IFNULL(rfc, '' ) AS rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sNomLoc             = rs.getString("nom");                                    
                sCallLoc            = rs.getString("calle");                                    
                sTelLoc             = rs.getString("tel");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstLoc             = rs.getString("estad");                                    
                sPaiLoc             = rs.getString("pai");                                    
                sRFCLoc             = rs.getString("rfc");                                   
                sNoExtLoc           = rs.getString("noext");                                   
                sNoIntLoc           = rs.getString("noint");                                   
            }                        
        }
        catch(SQLException expnSQL)
        {
            /*Actualiza en la tabla de que no se timbró*/            
            jTab.setValueAt("No", iFil, 22);
            
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
        
        /*Obtiene el símbolo de la moneda*/
        String sSimb    = "";
        try
        {
            sQ = "SELECT simb FROM mons WHERE mon = '" + sMon + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSimb   = rs.getString("simb");
        }
        catch(SQLException expnSQL)
        {
            /*Actualiza en la tabla de que no se timbró*/                
            jTab.setValueAt("No", iFil, 22);

            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }            

        /*Si tiene que traer todos los datos entonces*/
        if(bBusc)
        {    
            /*Obtiene el total con letra*/
            sTotLet                 = Star.sObLet(sTot, sMon, sSimb, true);                    
            
            /*Obtiene los datos de la empresa en base a la venta*/
            try
            {
                sQ = "SELECT noint, CASE WHEN DATE(femi) <> DATE(fvenc) THEN 'Crédito' ELSE 'Contado' END conds, IFNULL(vtas.METPAG,'') AS metpag, IFNULL(vtas.CTA, '') AS cta, calle, col, IFNULL(pai,'') AS pai, tel, noext, rfc, ciu, IFNULL(emps.ESTAD,'') AS estad, co1, cp, subtot, impue FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + sVta;
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sCall       = rs.getString("calle");                                   
                    sCol        = rs.getString("col");                                   
                    sTel        = rs.getString("tel");                                                       
                    sNoExt      = rs.getString("noext");                                   
                    sRFC        = rs.getString("rfc");                                   
                    sCiu        = rs.getString("ciu");                                   
                    sPai        = rs.getString("pai");                                   
                    sEsta       = rs.getString("estad");
                    sCo1        = rs.getString("co1");                                   
                    sCP         = rs.getString("cp");                                                       
                    sSubTot     = rs.getString("subtot");                                   
                    sImp        = rs.getString("impue");                                   
                    sCta        = rs.getString("cta");
                    sNoInt      = rs.getString("noint");
                    sMetPag     = rs.getString("metpag");                                                                            
                    sConds      = rs.getString("conds")==null ? "": rs.getString("conds");                                  
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;                            
            }            
            
        }/*Fin de if(bBusc)*/            
                      
        /*Determina la ruta donde mandar el PDF*/
        String sCa              = "\\Facturas";
        if(sTip.compareTo("notc")==0)
            sCa                 = "\\Notas credito";
        if(sTip.compareTo("notp")==0)
            sCa                 = "\\Notas credito";
                    
        /*Si el directorio no existe entonces crea la carpeta*/                    
        String sRutPDF          = sCarp + sCa;                          
        if(!new File(sRutPDF).exists())
            new File(sRutPDF).mkdir();

        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sRutPDF                 += "\\" + Login.sCodEmpBD;
        if(!new File(sRutPDF).exists())
            new File(sRutPDF).mkdir();

        /*Crea temporalmente la ruta al XML*/
        String sRutXML          =  sRutPDF + "\\CFDI-" + sRFCLoc + "-" +sSerFac + "-" +sConFac + ".xml";

        /*Completa la ruta*/
        sRutPDF                 += "\\CFDI-" + sRFCLoc + "-" +sSerFac + "-" +sConFac + ".pdf";                                        
        
        /*Contiene algunos datos del certificado*/
        String sRutCert         = "";
        String sPassCer         = "";
        String sRutKey          = "";
        String sRegFisc         = "";
        String sLugExp          = "";
        
        /*Obtiene la ruta al certificado*/        
        try
        {
            sQ = "SELECT lugexp, regfisc, rutcer, rutkey, passcer FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resultados*/
            if(rs.next())
            {
                sRutCert        = rs.getString("rutcer");
                sPassCer        = Star.sDecryp(rs.getString("passcer"));
                sRutKey         = rs.getString("rutkey");
                sRegFisc        = rs.getString("regfisc");
                sLugExp         = rs.getString("lugexp");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }  
        
        /*Obtiene el número de certificado*/
        String sNumCert     = Star.sGetCerNo(sRutCert);
        
        /*Si hubo error entonces regresa*/
        if(sNumCert==null)
            return;
        
        /*Obtiene el certificado*/
        String sCert        = Star.sGetCer(sRutCert);
        
        /*Si hubo error entonces regresa*/
        if(sCert==null)
            return;
        
        /*Crea el encabezado del xml*/
        String sXml = sCreEncaXML(sCta, sCiuLoc, sMon, sTipCam, sMetPag, sTot.replace(",", "").replace("$", ""), sTotDescu.replace(",", "").replace("$", ""), sSubTot.replace(",", "").replace("$", ""), sNumCert, sFormPag, sTipDocS, sFDoc, sCert, "",sNomLoc, sRFCLoc, sCPLoc, sPaiLoc, sEstLoc, sCiuLoc, sColLoc, sNoExtLoc, sCallLoc, sRegFisc, sNomEmp, sRFC, sPai, sEsta, sCiu, sCol, sCall, sCiu, sLugExp);
        /*Crea las partidas de la venta*/
        sXml        += sCrePartXML(con, sVta);        
        
        //Obtiene el final de la cadena
        String sFinCad  = sCreFinXML(sImp, Star.sGetValImp(sImp), sImp, sVta);
        
        //Si hubo error entonces regresa
        if(sFinCad==null)
            return;
        
        /*Agrega el final del comprobante*/
        sXml        += sFinCad;                
        Login.vLog(sXml);
        /*Genera la cadena original*/
        String sCadOri      = Star.sGenCad(sXml.replace("&", "&amp;"));
        
        /*Si hubo error entonces*/
        if(sCadOri==null)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        /*Genera el sello dígital*/
        PrivateKey prKey    = Star.pkGetKey(new File(sRutKey), sPassCer);                
        String sSell        = genSelDig(prKey, sCadOri);        
        
        /*Si hubo error entonces*/
        if(prKey==null)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        /*Genera nuevamente el XML pero ya con el sello*/
        sXml        = sCreEncaXML(sCta, sCiuLoc, sMon, sTipCam, sMetPag, sTot.replace(",", "").replace("$", ""), sTotDescu.replace(",", "").replace("$", ""), sSubTot.replace(",", "").replace("$", ""), sNumCert, sFormPag, sTipDocS, sFDoc, sCert, " sello = \"" + sSell + "\"", sNomLoc, sRFCLoc, sCPLoc, sPaiLoc, sEstLoc, sCiuLoc, sColLoc, sNoExtLoc, sCallLoc, sRegFisc, sNomEmp, sRFC, sPai, sEsta, sCiu, sCol, sCall, sCiu, sLugExp);
        sXml        += sCrePartXML(con, sVta);
        
        //Obtiene el final
        sFinCad     = sCreFinXML(sImp, Star.sGetValImp(sImp), sImp, sVta);                        
        
        //Si hubo error entonces regresa
        if(sFinCad==null)
            return;
        
        //Concatena la cadena
        sXml        += sFinCad;
        /*Obtiene el token de sguridad*/
        String sNewTok  = sCreTokEsta(sRFCLoc);
        /*Si hubo error entonces*/
        if(sNewTok==null)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
    
        /*Tokeniza para obtener el token y el id de transacción*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sNewTok, "|");
        sNewTok         = stk.nextToken();
        String sTransId = stk.nextToken();
        /*Crea el object factory para consultar el estatus de una cuenta*/
        wstimb.ObjectFactory facCli = new wstimb.ObjectFactory();
        /*Crea el comprobante XML*/
        wstimb.ComprobanteXML xmlComp= new wstimb.ComprobanteXML();
        xmlComp.setDatosXML(facCli.createComprobanteXMLDatosXML(sXml.replace("&", "&amp;")));
        
        /*Crea el objeto para solicitar timbrado en el web service*/
        wstimb.SolicitudTimbraXML wbPara    = new wstimb.SolicitudTimbraXML();         
        wbPara.setTransaccionID             (Long.parseLong(sTransId));
        wbPara.setToken                     (facCli.createSolicitudTimbraXMLToken(sNewTok));
        wbPara.setRFC                       (facCli.createSolicitudTimbraXMLRFC(sRFCLoc));
        wbPara.setComprobanteXML            (facCli.createComprobanteXML(xmlComp));
        
        /*Bandera para saber si se timbro o no*/
        boolean bSiTim                      = true;
        
        /*Contiene el folio fiscal*/
        String sFolFisc                     = "";
        
        /*Contiene el transaction ID*/
        String sTID                         = "";
        
        /*Contiene el sello dígital del sat*/
        String sSellSAT                     = "";
        
        /*Contiene la cadena original que devuelve el SAT*/
        String sCadOriSAT;
        
        /*Contiene la versión*/
        String sVer                         = "";        
        
        /*Contine el no certificado del SAT*/
        String sNoCertSAT                   = "";
        
        /*Contiene la fecha de timbrado*/
        String sFTimb                       = "";
        
        /*Manda timbrar con el WS*/                        
        wstimb.RespuestaTimbraXML wsResp;
        try
        {   
            System.out.println(wbPara);
            wstimb.Timbrado_Service servicioX   = new wstimb.Timbrado_Service();
            wstimb.Timbrado puertoX             = servicioX.getPuertoTimbrado();
            wsResp                              = puertoX.timbraXML(wbPara);
           
            /*Obtiene el transid del timbrado*/
            sTID                                = wsResp.getTransaccionID().toString();       
            /*Obtiene el XML del PAC*/
            sXml                                = wsResp.getComprobanteXML().getValue().getDatosXML().getValue();
             
            /*Tokeniza el XML en busca el UUID*/
            java.util.StringTokenizer stkXml    = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sFolFisc    = stkXml.nextToken();
                
                /*Si la cadena comienza con UUID entonces ya lo encontramos folio fiscal*/
                if(sFolFisc.startsWith("UUID="))
                    break;
            }
                           
            /*Tokeniza el XML en busca del sello dígital del SAT*/
            stkXml    = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sSellSAT    = stkXml.nextToken();
                
                /*Si la cadena comienza con UUID entonces ya lo encontramos*/
                if(sSellSAT.startsWith("selloSAT="))
                    break;
            }
            
            /*Tokeniza el XML en busca de la fecha del XML*/
            stkXml    = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sFTimb    = stkXml.nextToken();
                
                /*Si la cadena comienza con FechaTimbrado entonces ya lo encontramos*/
                if(sFTimb.startsWith("FechaTimbrado="))
                    break;
            }
            
            /*Tokeniza el XML en busca de la versión*/
            stkXml      = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sVer    = stkXml.nextToken();
                
                /*Si la cadena comienza con version entonces ya lo encontramos*/
                if(sVer.startsWith("version="))
                    break;
            }
            
            /*Tokeniza el XML en busca de la versión*/
            stkXml      = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sVer    = stkXml.nextToken();
                
                /*Si la cadena comienza con version entonces ya lo encontramos*/
                if(sVer.startsWith("version="))
                    break;
            }
            
            /*Tokeniza el XML en busca del número de certificado del sat*/
            stkXml      = new java.util.StringTokenizer(sXml, " ");
            while(stkXml.hasMoreTokens())
            {
                /*Obtiene el token*/
                sNoCertSAT       = stkXml.nextToken();
                
                /*Si la cadena comienza con noCertificadoSAT entonces ya lo encontramos*/
                if(sNoCertSAT.startsWith("noCertificadoSAT="))
                    break;
            }
        }
        catch(wstimb.TimbradoTimbraXMLFallaValidacionFaultFaultMessage | wstimb.TimbradoTimbraXMLFallaSesionFaultFaultMessage | wstimb.TimbradoTimbraXMLFallaServicioFaultFaultMessage expnWSPAC)
        {   
            System.out.println("6");
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, null);                                                                   
            return;                        
        }                               
        
        /*Reemplaza los caracteres innecesarios de la versión*/
        sVer        = sVer.replace("=", "").replace("version", "").replace("\"", "");
        
        /*Reemplaza los caracteres innecesarios del UUID*/
        sFolFisc    = sFolFisc.replace("=", "").replace("UUID", "").replace("\"", "");
        
        /*Reemplaza los caracteres innecesarios de la fecha de timbrado*/
        sFTimb      = sFTimb.replace("=", "").replace("FechaTimbrado", "").replace("\"", "");
        
        /*Reemplaza los caracteres innecesarios del sello del SAT*/
        sSellSAT    = sSellSAT.replace("=", "").replace("selloSAT", "").replace("\"", "");
                        
        /*Reemplaza los caracteres innecesarios del sello digital del cfdi*/
        sNoCertSAT  = sNoCertSAT.replace("=", "").replace("noCertificadoSAT", "").replace("\"", "");
        
        /*Genera la cadena original del SAT*/
        sCadOriSAT  = "||" + sVer + "|" + sFolFisc + "|" + sFTimb + "|" + sSellSAT + "|" + sNoCertSAT + "||";        
                
        /*Genera reporte y el xml*/
        Star.vPDF(sDirEn, sMon, sConFac, sCatGral, sVta, sSerFac, sFDoc, sNomEmp, sPai, sTel, sCall, sCol, sCP, sNoExt, sNoInt, sCiu + ", " + sEsta, "", sRFC, sCo1, sTotLet, sSubTot, sImp, sTot, sMetPag, sCta, sConds, sNomLoc, sTelLoc, sColLoc, sCallLoc, sCPLoc, sCiuLoc, sEstLoc, sPaiLoc, sRFCLoc, sRutLog, bSiLog, bVeFac, bImpFac, isRepNam, jTab, sRutXML, sRutPDF, 1, true, true, 0,false, sXml, sFolFisc, sSell, sSellSAT, sCadOriSAT, sNoIntLoc, sNoExtLoc, sNoCertSAT, sLugExp, sRegFisc, sCtaPred);
                
        /*Si se timbro con éxito entonces*/
        if(bSiTim)
        {               
            /*Actualiza algunos datos de la venta*/
            try 
            {                
                sQ = "UPDATE vtas SET "
                        + "timbr        = 1, "                    
                        + "transid      = '" + sTID + "', "
                        + "sell         = '" + sSell + "', "
                        + "certsat      = '" + sNoCertSAT + "', "
                        + "lugexp       = '" + sLugExp + "', "
                        + "regfisc      = '" + sRegFisc + "', "
                        + "sellsat      = '" + sSellSAT + "', "
                        + "cadori       = '" + sCadOriSAT + "', "
                        + "folfisc      = '" + sFolFisc + "' "                    
                        + "WHERE vta    = " + sVta;                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {                  
                 System.out.println("7");
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;
            }    
            
            /*Actualiza en la tabla de que si se timbró y el folio fiscal*/           
            if(iFil!=-1 && jTab!=null)
            {
                jTab.setValueAt("Si", iFil, 22);
                jTab.setValueAt(sFolFisc, iFil, 26);
            }

        }/*Fin de if(bSiTim)*/                    
        
        //Esconde la forma de loading
        Star.vOcultLoadin();
        
        //Obtiene si el usuario tiene correo asociado
        boolean bSi     = false;
        int iRes        = Star.iUsrConfCorr(con, Login.sUsrG);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si tiene correo asociado entonces coloca la bandera
        if(iRes==1)
            bSi    = true;

        /*Obtiene la serie de esa venta*/
        String sNoSer   = "";
        try
        {
            sQ = "SELECT noser FROM vtas WHERE vta = " + sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene el resultado*/
            if(rs.next())
                sNoSer  = rs.getString("noser");
        }
        catch(SQLException expnSQL)
        {
            System.out.println("8");
            /*Coloca la banera del error*/
            Star.bErr    = true;

            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }  
        
        /*Si tiene correo asociado entonces*/
        if(bSi)
        {
            /*Determina a que correos se le va a mandar al cliente*/
            if(!bCo1)
                sC1            = null;
            if(!bCo2)
                sC2            = null;
            if(!bCo3)
                sC3            = null;

            /*Si por lo menos a un correo se le va a mandar el pdf entonces y si tiene hablitado esa parte entonces manda el PDF*/
            if((bCo1 || bCo2 || bCo3) && bMandCo)
                Star.vMandPDFEmp(sNoSer + sConFac + ".pdf", sRutPDF, sNoSer + sConFac + ".xml", sRutXML, sC1, sC2, sC3, sNoSer + sConFac, "FAC");

        }/*Fin de if(bSi)*/      
        /*Else mensajea*/
        else
            JOptionPane.showMessageDialog(null, "No se envio la " + sMsj + " con Folio: " + sConFac + " por correo electrónico ya que no se tiene uno configurado para el usuario: " + Login.sUsrG + ".", "Facturación", JOptionPane.INFORMATION_MESSAGE, null);               
        
        //Cierra la base de datos 
        if(Star.iCierrBas(con)==-1)                  
            Star.bErr    = true;        
        
    }/*Fin de public static synchronized void vGenTim(String sTip, String sDirEn, String sConFac, String sVta, String sCatGral, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sSerFac, String sMetPag, String sCta, String sConds, String sRutLoc, java.io.InputStream isRepNam, boolean bVeFac, boolean bImpFac, String sC1, String sC2, String sC3, boolean bCo1, boolean bCo2, boolean bCo3, boolean bMandCo, int iFil, JTable jTab, boolean bBusc, String sMon, String sTotDescu, String sFormPag, String sTipDocS, String sTimCam, String sCtaPred)*/
        
    
    /*Función para generar el PDF de la remisión y enviarlo por correo*/
    public static synchronized void vGenRem(String sDirEn, String sMon, String sConFac, String sVta, String sCatGral, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sSerFac, String sMetPag, String sCta, String sConds, String sRutLoc, java.io.InputStream isRepNam, boolean bVeFac, boolean bImpFac, String sC1, String sC2, String sC3, boolean bCo1, boolean bCo2, boolean bCo3, boolean bMandCo)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = sRutLoc;
               
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
    
        /*Comprueba si tiene que imprimir o no el logo en la remisión*/
        boolean bSiLog          = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'logorem'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene si se tiene que mandar o no*/
                if(rs.getString("val").compareTo("1")==0)                                   
                    bSiLog = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
        
        //Declara variables locales
        String sNomLoc      = "";
        String sCallLoc     = "";
        String sTelLoc      = "";
        String sColLoc      = "";
        String sCPLoc       = "";
        String sCiuLoc      = "";
        String sEstLoc      = "";
        String sPaiLoc      = "";
        String sRFCLoc      = "";
        String sNoIntLoc    = "";
        String sNoExtLoc    = "";
        
        /*Obtiene todos los datos de la empresa local*/       
        try
        {                  
            sQ = "SELECT IFNULL(noext, '') AS noext, IFNULL(noint, '') AS noint, IFNULL(nom, '') AS nom, IFNULL(calle,'') AS calle, IFNULL(tel,'') AS tel, IFNULL(col,'') AS col, IFNULL(cp,'') AS cp, IFNULL(ciu,'') AS ciu, IFNULL(estad,'') AS estad, IFNULL(pai,'') AS pai, IFNULL(rfc, '' ) AS rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sNomLoc             = rs.getString("nom");                                    
                sCallLoc            = rs.getString("calle");                                    
                sTelLoc             = rs.getString("tel");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstLoc             = rs.getString("estad");                                    
                sPaiLoc             = rs.getString("pai");                                    
                sRFCLoc             = rs.getString("rfc");                                   
                sNoIntLoc           = rs.getString("noint");                                   
                sNoExtLoc           = rs.getString("noext");                  
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
                
        /*Si el directorio no existe entonces crea la carpeta*/                    
        String sRutPDF          = sCarp + "\\Remisiones";                          
        if(!new File(sRutPDF).exists())
            new File(sRutPDF).mkdir();

        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sRutPDF                 += "\\" + Login.sCodEmpBD;
        if(!new File(sRutPDF).exists())
            new File(sRutPDF).mkdir();

        /*Completa la ruta para el PDF de la remisión*/
        sRutPDF                 += "\\" + sSerFac + "-" +sConFac + ".pdf";                                        
        
        /*Genera reporte*/
        Star.vPDF(sDirEn, sMon, sConFac, sCatGral, sVta, sSerFac, sFDoc, sNomEmp, sPai, sTel, sCall, sCol, sCP, sNoExt, sNoInt, sCiu, sEsta, sRFC, sCo1, sTotLet, sSubTot, sImp, sTot, sMetPag, sCta, sConds, sNomLoc, sTelLoc, sColLoc, sCallLoc, sCPLoc, sCiuLoc, sEstLoc, sPaiLoc, sRFCLoc, sRutLog, bSiLog, bVeFac, bImpFac, isRepNam, null, null, sRutPDF, 1, true, false, 0,false, "", "", "", "", "", sNoIntLoc, sNoExtLoc, "", "", "", "");
        
        //Esconde la forma de loading
        Star.vOcultLoadin();

        //Obtiene si el usuario tiene correo asociado
        int iRes    = Star.iUsrCon(con, Login.sUsrG);
        System.out.println("1");
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si tiene correo asociado entonces coloca la bandera
        boolean bSi    = false;
        if(iRes==1)
            bSi    = true;
                    
        /*Si tiene correo asociado entonces*/
        if(bSi)
        {
            System.out.println("2");
            /*Determina a que correos se le va a mandar al cliente*/
            if(!bCo1)
                sC1            = null;
            if(!bCo2)
                sC2            = null;
            if(!bCo3)
                sC3            = null;
            System.out.println("3");
            /*Si por lo menos a un correo se le va a mandar el pdf entonces y si tiene hablitado esa parte entonces manda el PDF*/
            if((bCo1 || bCo2 || bCo3) && bMandCo)
            {
            System.out.println("4");
                Star.vMandPDFEmp(sSerFac + sConFac + ".pdf", sRutPDF, null, null, sC1, sC2, sC3, sSerFac + sConFac, "REM");
            System.out.println("5");
            }
        }
            
        /*Else mensajea*/
        else
            JOptionPane.showMessageDialog(null, "No se envio la remisión con Folio: " + sConFac + " por correo electrónico ya que no se tiene uno configurado para el usuario: " + Login.sUsrG + ".", "Remisión", JOptionPane.INFORMATION_MESSAGE, null);               
        System.out.println("6");
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            Star.bErr    = true;
        
    }/*Fin de public static synchronized void vGenRem(String sDirEn, String sConFac, String sVta, String sCatGral, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sSerFac, String sMetPag, String sCta, String sConds, String sRutLoc, java.io.InputStream isRepNam, boolean bVeFac, boolean bImpFac, String sC1, String sC2, String sC3, boolean bCo1, boolean bCo2, boolean bCo3, boolean bMandCo)*/
    
    
    /*Genera reporte en PDF*/                                    
    public static synchronized void vPDF(String sDirEn, String sMon, String sFol, String sCatGral, String sVta, String sSer, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sMetPag, String sCta, String sConds, String sNomLoc, String sTelLoc, String sColLoc, String sCallLoc, String sCPLoc, String sCiuLoc, String sEstLoc, String sPaiLoc, String sRFCLoc, String sRutLog, boolean bSi, boolean bVeFac, boolean bImp, java.io.InputStream isRepNam, JTable jTab, String sRutXML, String sRutPDF, int iTip, boolean bExporP, boolean bExporX, int i52, boolean bMsj, String sXml, String sFolFisc, String sSell, String sSellSAT, String sCadOri, String sNoIntLoc, String sNoExtLoc, String sNoCertSAT, String sLugExp, String sRegFisc, String sCtaPred)
    {                                                  
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;                    
        
        //Si se tiene que mostrar el loading entonces muestrala
        if(bMsj)
            Star.vMostLoading("");                            
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Si es una factura, remisión o nota de crédito entonces*/
        if(iTip==1)
        {                           
            /*Búcle para completar la cadena del total con 0*/
            for(int x = sTot.length(); x < 21; x++)
                sTot        += "0";
            
           /*Búcle para completar la cadena del folio fiscal con 0*/
            for(int x = sFolFisc.length(); x < 40; x++)
                sFolFisc    += "0";                        
            
            /*Crea los parámetros que se pasarán*/
            Map <String,String> para = new HashMap<>();             
            para.clear();
            para.put("CONSFAC",         sFol);
            para.put("CATGRAL",         sCatGral);
            para.put("VTA",             sVta);
            para.put("SER",             sSer);
            para.put("FDOC",            sFDoc);
            para.put("NOMEMP",          sNomEmp);
            para.put("TEL",             sTel);
            para.put("CALL",            sCall);
            para.put("COL",             sCol);
            para.put("CP",              sCP);
            para.put("NOEXT",           sNoExt);            
            para.put("NOINT",           sNoInt);
            para.put("PAI",             sPai);
            para.put("CIU",             sCiu);
            para.put("MON",             sMon);
            para.put("ESTAD",           sEsta);
            para.put("RFC",             sRFC);
            para.put("CORR",            sCo1);
            para.put("IMPLET",          sTotLet);
            para.put("SUBTOT",          sSubTot);
            para.put("IMPUE",           sImp);
            para.put("TOT",             sTot);
            para.put("METPAG",          sMetPag);
            para.put("CTA",             sCta);
            para.put("CONDI",           sConds);
            para.put("EMPLOC",          sNomLoc);
            para.put("TELLOC",          sTelLoc);
            para.put("COLLOC",          sColLoc);
            para.put("CALLLOC",         sCallLoc);
            para.put("CPLOC",           sCPLoc);
            para.put("CIULOC",          sCiuLoc);           
            para.put("ESTADLOC",        sEstLoc);
            para.put("PAILOC",          sPaiLoc);                        
            para.put("RFCLOC",          sRFCLoc);                    
            para.put("DOMENT",          sDirEn);
            para.put("SELL",            sSell);
            para.put("NOINTLOC",        sNoIntLoc);
            para.put("NOEXTLOC",        sNoExtLoc);
            para.put("SELLSAT",         sSellSAT);
            para.put("CADORI",          sCadOri);
            para.put("FOLFISC",         sFolFisc);
            para.put("LUGEXP",          sLugExp);
            para.put("REGFIS",          sRegFisc);
            para.put("CTAPRED",         sCtaPred);
            para.put("CERTSAT",         sNoCertSAT);
            
            /*Búcle para completar la cadena del RFC emisor con 0*/
            for(int x = sRFCLoc.length(); x < 17; x++)
                sRFCLoc     += "0";
                        
            /*Búcle para completar la cadena del RFC del que se le esta facturando con 0*/
            for(int x = sRFC.length(); x < 17; x++)
                sRFC        += "0";
                     
            //Crea el QR
            para.put("QR",              "?re=" + sRFCLoc + "?re=" + sRFC + "&tt=”" + sTot + "&id=" + sFolFisc);
            para.put("LOGE",            Star.class.getResource(Star.sIconDef).toString());
            
            /*Si se va a mostrar el logo entonces coloca el parámetro*/                    
            if(bSi)                                  
                para.put("LOG",         sRutLog);                                
            
            //Declara variables locales
            JasperReport ja;
            JasperPrint  pr;
            
            /*Compila el reporte*/                                    
            try
            {                                
                ja     = JasperCompileManager.compileReport(isRepNam);                            
            }
            catch(JRException expnJASR)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                return;                
            }                           
            
            /*Llena el reporte*/                                        
            try
            {                                
                pr     = JasperFillManager.fillReport(ja, (Map)para, con);            
            }
            catch(JRException expnJASR)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                return;                            
            }                
            
            /*Si se tiene que ver el PDF entonces*/
            if(bVeFac)
            {
                /*Muestralo maximizado*/
                JasperViewer v  = new JasperViewer(pr, false);
                v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                v.setVisible(true);
            }

            /*Si se tiene que imprimir el PDF entonces imprimirla*/
            if(bImp)
            {
                /*Declara variables*/
                String  sImp1             = "";
                String  sImpAnt;

                /*Obtiene el nombre de la impresora que tiene configurada la estación actual*/
                try
                {
                    sQ = "SELECT impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sImp1         = rs.getString("impfac");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                    
                }  

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;                

                /*Obtiene la impresora predeterminada actual*/
                PrintService ser        = PrintServiceLookup.lookupDefaultPrintService(); 
                sImpAnt                 = ser.getName();

                /*Cambia la impresora predeterminada */
                Star.vCambImp(sImp1);

                /*Imprime el reporte*/
                try
                {
                    JasperPrintManager.printReport(pr,false);
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                    return;                                
                }                                

                /*Cambia la impresora predeterminada que estaba anteriormente*/
                Star.vCambImp(sImpAnt);

            }/*Fin de if(jCheckBoxImprimir.isSelected())*/

            /*Exportalo a PDF en el directorio completo con el nombre del código de la factura*/
            if(bExporP)
            {                                                
                try
                {
                    JasperExportManager.exportReportToPdfFile(pr, sRutPDF);
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                    return;                    
                }                                                                
            }   
            
            /*Expota el XML*/
            if(bExporX && sXml.compareTo("")!=0)
            {                
                /*Crea el archivo XML*/
                File flFil = new File(sRutXML);                   
                try
                {
                    flFil.createNewFile();                    
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return;
                }                    
                
                //Escribe el XML en el fichero
                try(FileWriter fw = new FileWriter(flFil.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw))
                {                                                             
                    bw.write(sXml);                    
		} 
                catch(IOException expnIO) 
                {			
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return;
		}                
            }                            
            
        }/*Fin de if(iTip==1)*/            
        /*Else if es un ticket*/
        else if(iTip==2)
        {                        
            /*Crea los parámetros que se pasarán*/
            Map <String,String> par = new HashMap<>();             
            par.clear();
            par.put("CODTIK",           sFol);
            par.put("VTA",              sVta);                        
            par.put("SUBTOT",           sSubTot);
            par.put("IMPUE",            sImp);
            par.put("FDOC",             sFDoc);
            par.put("NOMEMP",           sNomEmp);
            par.put("TEL",              sTelLoc);
            par.put("CALL",             sCallLoc);
            par.put("COL",              sColLoc);
            par.put("CP",               sCPLoc);
            par.put("NOEXT",            sNoExt);            
            par.put("NOINT",            sNoInt);
            par.put("MON",              sMon);
            par.put("CIU",              sCiu);
            par.put("ESTAD",            sEsta);
            par.put("RFC",              sRFC);            
            par.put("IMPLET",           sTotLet);                       
            par.put("TOT",              sTot);
            par.put("EMPLOC",           sNomLoc);
            par.put("TELLOC",           sTelLoc);
            par.put("COLLOC",           sColLoc);
            par.put("CALLLOC",          sCallLoc);
            par.put("CPLOC",            sCPLoc);
            par.put("CIULOC",           sCiuLoc);
            par.put("ESTADLOC",         sEstLoc);
            par.put("MAILLOC",          sCo1);
            par.put("NOEXTLOC",         sNoExtLoc);            
            par.put("PAILOC",           sPaiLoc);
            par.put("RFCLOC",           sRFCLoc);                                                        
            par.put("LOG",              sRutLog);                                                        
            par.put("LOGE",             Star.class.getResource(Star.sIconDef).toString());
                        
            /*Compila el reporte*/
            JasperReport    jasp;
            try
            {
                /*Llama al tic de 52 o al normal*/                   
                if(i52==1)
                {
                    /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/                    
                    if(sNomEmp.trim().compareTo(Star.sNomCliMostG)!=0)
                    {
                        /*Si se tiene que mostrar el logo entonces pasa el parámetro*/                                
                        if(bSi && new File(sRutLog).exists())
                            par.put("LOG", sRutLog);

                        /*Compila el reporte*/
                        jasp    = JasperCompileManager.compileReport(isRepNam);               
                    }
                    else
                    {
                        /*Si se tiene que mostrar el logo entonces pasa el parámetro*/                                
                        if(bSi && new File(sRutLog).exists())
                            par.put("LOG", sRutLog);

                        /*Compila el reporte*/
                        jasp    = JasperCompileManager.compileReport(isRepNam);               
                    }
                }                    
                else
                {
                    /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/
                    if(sNomEmp.compareTo(Star.sNomCliMostG)!=0)
                    {
                        /*Si tiene que mostrar el logo entonces pasa el parámetros*/                                
                        if(bSi && new File(sRutLog).exists())
                            par.put("LOG", sRutLog);                                                                        


                        /*Compila el reporte*/                                
                        jasp    = JasperCompileManager.compileReport(isRepNam);
                    }
                    else
                    {
                        /*Si tiene que mostrar el logo entonces pasa el parámetro*/                                
                        if(bSi && new File(sRutLog).exists())
                            par.put("LOG", sRutLog);

                        /*Compila el reporte*/
                        jasp    = JasperCompileManager.compileReport(isRepNam);
                    }
                }                                    
            }
            catch(JRException expnJASR)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                return;               
            }
                     
            /*Llena el reporte*/
            JasperPrint pri;
            try
            {
                pri             = JasperFillManager.fillReport(jasp, (Map)par, con);            
            }
            catch(JRException expnJASR)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                return;                                               
            }                        
            
            //Declara variables locales
            String sImpTic  = "";            
            String sCort    = "";
            
            /*Obtiene el nombre de la impresora que tiene configurada la estación actual y si es de cort o no*/            
            if(bImp)
            {                
                try
                {
                    sQ = "SELECT imptic, 52m, cort, impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene los resultados*/
                    if(rs.next())
                    {            
                        sImpTic             = rs.getString("imptic");                                        
                        sCort               = rs.getString("cort");
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                   
                }                                           
                
                /*Obtiene la impresora predeterminada actual*/
                PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
                String sImpAnt              = service.getName();
                
                /*Cambia la impresora predeterminada */
                Star.vCambImp(sImpTic);           

                /*Si no es de corte entonces*/
                if(sCort.compareTo("0")==0)
                {
                    /*Manda una impresión*/
                    try
                    {
                        JasperPrintManager.printReport(pri,true);                    
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                        return;                                       
                    } 

                    /*Preguntar al usr si quiere imprimir el siguiente ticket*/
                    Object[] op = {"Si","No"};
                    if((JOptionPane.showOptionDialog(null, "¿Quieres mandar otra impresión?", "Impresión ticket", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null, op, op[0])) == JOptionPane.YES_OPTION)
                    {                   
                        /*Manda otra impresión*/
                        try
                        {
                            JasperPrintManager.printReport(pri,true);                    
                        }
                        catch(JRException expnJASR)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR);                                                                   
                            return;                                           
                        } 
                    }  

                }/*Fin de if(sCortGlob.compareTo("0")==0)*/
                /*Else, no es de corte entonces*/
                else
                {
                    /*Manda dos impresiones a la impresora configurada para la estación*/
                    try
                    {
                        JasperPrintManager.printReport(pri,false);
                        JasperPrintManager.printReport(pri,false);
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR);                                                                   
                        return;                                       
                    }  
                }                                        

                /*Cambia la impresora predeterminada que estaba anteriormente*/
                vCambImp(sImpAnt);
                
            }/*Fin de if(bImp)*/                                            
            
            /*Exportalo a pdf en el directorio completo con el nombre del código del tiket*/
            try
            {
                JasperExportManager.exportReportToPdfFile(pri, sRutPDF);                                      
            }
            catch(JRException expnJASR)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR);                                                                   
                return;                                               
            }   

        }/*Fin de else if(iTip==2)*/
        
        /*Esconde la forma de loading*/
        if(bMsj)
            Star.vOcultLoadin();
        
    }/*Fin de public static synchronized void vPDF(String sDirEn, String sMon, String sFol, String sCatGral, String sVta, String sSer, String sFDoc, String sNomEmp, String sPai, String sTel, String sCall, String sCol, String sCP, String sNoExt, String sNoInt, String sCiu, String sEsta, String sRFC, String sCo1, String sTotLet, String sSubTot, String sImp, String sTot, String sMetPag, String sCta, String sConds, String sNomLoc, String sTelLoc, String sColLoc, String sCallLoc, String sCPLoc, String sCiuLoc, String sEstLoc, String sPaiLoc, String sRFCLoc, String sRutLog, boolean bSi, boolean bVeFac, boolean bImp, java.io.InputStream isRepNam, JTable jTab, String sRutXML, String sRutPDF, int iTip, boolean bExporP, boolean bExporX, int i52, boolean bMsj, String sXml, String sFolFisc, String sSell, String sSellSAT, String sCadOri, String sNoIntLoc, String sNoExtLoc, String sNoCertSAT, String sLugExp, String sRegFisc)*/
        
        
    /*Cambia la impresora predeterminada */
    public static synchronized void vCambImp(String sImp)
    {
        //Declara variables locales
        String sCmd = "RUNDLL32 PRINTUI.DLL,PrintUIEntry /y /n \"" + sImp + "\"";
        
        
             
        /*Cambia la impresora predeterminada*/
        try
        {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(sCmd);
            pr.waitFor();
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                               
        }   
        catch(InterruptedException expnInterru)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnInterru.getMessage(), Star.sErrInterru);                                                                               
        }
    }

    
    /*Método para convertir números a su representación con letra*/
    public static synchronized String sObLet(String numero, String sMon, String sSimb, boolean mayusculas) 
    {
        String literal;
        String parte_decimal;    
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if(numero.indexOf(",")==-1){
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if(Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");            
            //de da formato al numero decimal
            parte_decimal = Num[1] + "/100 " + sMon + " " + sSimb;
            //se convierte el numero a literal
            if(Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if(Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMill(Num[0]);
            } else if(Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMil(Num[0]);
            } else if(Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCent(Num[0]);
            } else if(Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDec(Num[0]);
            } else {//sino unids -> 9
                literal = getUnid(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if(mayusculas) {
                return (literal + parte_decimal).toUpperCase();
            } else {
                return (literal + parte_decimal);
            }
        } 
        else 
        {//error, no se puede convertir
            return literal = null;
        }
        
    }/*Fin de public static synchronized String sObLet(String numero, boolean mayusculas) */
    
    
    /*Obtiene los millones*/
    private static String getMill(String numero) 
    { 
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n;
        if(millon.length()>1){
            n = getCent(millon) + "millones ";
        }else{
            n = getUnid(millon) + "millon ";
        }
        return n + getMil(miles);        
    }
    
    
    /*Obtiene las decenas*/
    private static String getDec(String num) 
    {                        
        int n = Integer.parseInt(num);
        if(n < 10) 
        {
            return getUnid(num);
        } 
        else if(n > 19) 
        {
            String u = getUnid(num);
            if(u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } 
        else 
        {
            return DECENAS[n - 10];
        }
        
    }/*Fin de private String getDec(String num) */
    
        
    /*Obtiene las centenas*/
    private static String getCent(String num) 
    {
        if( Integer.parseInt(num)>99 )
        {
            if(Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                 return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDec(num.substring(1));
            } 
        }
        else
        {
            //se quita el 0 antes de convertir a decenas
            return getDec(Integer.parseInt(num)+"");            
        }                
    }

    
    /*Obtiene las unids*/
    private static String getUnid(String numero) 
    {
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }
    
    
    /*Obtiene los miles*/
    private static String getMil(String numero) 
    {
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n="";
        //se comprueba que miles tenga valor entero
        if(Integer.parseInt(m) > 0) {
            n = getCent(m);           
            return n + "mil " + getCent(c);
        } else {
            return "" + getCent(c);
        }
    }

    
    /*Función para hacer corte X*/
    public static synchronized void vCortX(String sTi, String sCarp)
    {
        //Muestra el loading
        Star.vMostLoading("");
        
        /*Declara variables final para el thread*/
        final String sTip   = sTi;
        final String sCarpG = sCarp;
        
        /*Todo el proceso se hará en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(false, false);

                //Si hubo error entonces
                if(con==null)
                    return;               

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;
                ResultSet   rs2;                
                String      sQ;
                String      sQ2;

                /*Obtiene si se tiene que guardar o no el cort X en PDF automáticamente*/
                boolean bSiG    = false;
                try
                {
                    sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'cort" + sTip + "a'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces*/
                    if(rs.next())
                    {                        
                        /*Coloca la bandera dependiendo el valor*/
                        if(rs.getString("val").compareTo("1")==0)
                            bSiG    = true;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                   
                }
                
                //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                String sCarp    = Star.sGetRutCarp(con);                    

                //Si hubo error entonces regresa
                if(sCarp==null)
                    return;
        
                /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces coloca la bandera*/
                boolean bCarp   = true;
                if(sCarp.compareTo("")==0)
                {
                    System.out.println("nop");
                    bCarp       = false;
                }

                /*Obtiene el número de corte ya sea X o Z*/
                String sCort = "1";
                try
                {
                    sQ = "SELECT IFNULL(MAX(numcort),0) + 1 as numcort FROM cortszx WHERE cort = '" + sTip + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sCort        = rs.getString("numcort");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                           
                }

                /*Recorre todas las ventas confirmadas*/
                boolean bS = false;
                try
                {
                    sQ = "SELECT mon FROM vtas WHERE ptovta = 1 AND factu = 0 AND cierr = 0 AND cort = 'N' AND estad = 'CO' AND (tipdoc = 'FAC' OR tipdoc = 'TIK' OR tipdoc = 'DEV' OR tipdoc = 'REM')";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {                        
                        /*Pon la bandera para saber que si hay registros*/
                        bS                  = true;
                        
                        /*Obtiene la fecha y hora del sistema*/
                        String      sFHoy;
                        DateFormat da       = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date dat            = new Date();
                        sFHoy               = da.format(dat);

                        //Declara variables locales
                        String sVtaImp     = "0.00";
                        String sVtaDesc    = "0.00";
                        String sVtaImpue   = "0.00";
                        String sVtaTot     = "0.00";
                        String sDevImp     = "0.00";                        
                        String sPagEfe     = "0.00";
                        String sTotCaj     = "0.00";  
                        String sPagTarCred = "0.00";
                        String sPagDeb     = "0.00";
                        String sCantFac    = "0";
                        String sCantTic    = "0";
                        String sCantRem    = "0";
                        String sTotFac     = "0.00";
                        String sTotTic     = "0.00";        
                        String sTotRem     = "0.00";
                        String sTotDep     = "0.00";
                        String sTotVtas    = "0.00"; 
                        
                        /*Obtiene los totales del corte*/                                                
                        try
                        {
                            sQ2= "SELECT (SELECT COUNT(vta) AS Ventas FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.CIERR = 0 AND vtas.CORT <> 'S' AND vtas.PTOVTA = 1 AND vtas.TIPDOC IN('FAC', 'REM', 'TIK')) AS CantVtas, (SELECT IFNULL(SUM(impo),0) AS Total FROM fluj WHERE ing_eg = 'I' AND corta = 0) AS TotDia, (SELECT IFNULL(SUM(tot),0) AS resp FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.PTOVTA = 1 AND vtas.TIPDOC = 'TIK' AND vtas.CIERR = 0 AND vtas.CORT <> 'S') AS TotTic, (SELECT IFNULL(SUM(tot),0) AS resp FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.PTOVTA = 1 AND vtas.TIPDOC = 'REM' AND vtas.CIERR = 0 AND vtas.CORT <> 'S') AS TotRem, (SELECT IFNULL(SUM(tot),0 ) AS Facs FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC = 'FAC' AND vtas.CIERR = 0 AND vtas.PTOVTA = 1 AND vtas.CORT <> 'S') AS TotFac, (SELECT COUNT(vta) AS tics FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC = 'TIK' AND vtas.CIERR = 0 AND vtas.PTOVTA = 1 AND vtas.CORT <> 'S') AS CantTic, (SELECT COUNT(vta) AS tics FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC = 'REM' AND vtas.CIERR = 0 AND vtas.PTOVTA = 1 AND vtas.CORT <> 'S') AS CantRem, (SELECT COUNT(vta) AS Facs FROM vtas WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC = 'FAC' AND vtas.PTOVTA = 1 AND vtas.CIERR = 0 AND vtas.CORT <> 'S') AS CantFac, (SELECT IFNULL(SUM(impo),0) AS Imp FROM fluj WHERE corta = 0 AND concep = 'TAR') AS ImpoTar, (SELECT CASE WHEN SUM(impo) IS NULL THEN 0 ELSE SUM(impo) END as Importe FROM fluj WHERE corta = 0 AND concep = 'DEB') AS ImpoDeb, (SELECT IFNULL(SUM(impo),0) AS ImpoCaj FROM fluj WHERE corta = 0 AND concep = 'CAJ') AS ImpoCaj, (SELECT SUM(impo) as Impo FROM fluj WHERE corta = 0 AND concep = 'EFE') AS ImpoEfe, (CASE WHEN SUM(((partvta.PRE * (1-(partvta.DESCU/100))) * partvta.CANT) * (partvta.IMPUE/100) ) IS NULL THEN 0 ELSE SUM(((partvta.PRE * (1-(partvta.DESCU/100))) * partvta.CANT) * (partvta.IMPUE/100) ) END) AS ImpueDev, (SELECT CASE WHEN SUM((partvta.PRE * (1-(partvta.DESCU/100))) * partvta.CANT) IS  NULL THEN 0 ELSE SUM((partvta.PRE * (1-(partvta.DESCU/100))) * partvta.CANT) END AS Impo FROM partvta LEFT JOIN vtas ON vtas.VTA = partvta.VTA WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC = 'DEV' AND vtas.CIERR = 0 AND vtas.PTOVTA = 1 AND vtas.CORT <> 'S') AS ImpoDev, IFNULL(SUM(partvta.PRE * partvta.CANT),0) AS Impo, IFNULL(SUM((partvta.PRE * (partvta.DESCU/100))*partvta.CANT),0) AS Descu, IFNULL(SUM(((partvta.PRE * (1-(partvta.DESCU/100)))* partvta.CANT)*(partvta.IMPUE/100)),0) AS Impue FROM partvta LEFT JOIN vtas ON vtas.VTA = partvta.VTA WHERE vtas.ESTAD = 'CO' AND vtas.TIPDOC <> 'DEV' AND vtas.PTOVTA = 1 AND vtas.CIERR = 0 AND vtas.CORT <> 'S'";                        
                            st = con.createStatement();
                            rs2= st.executeQuery(sQ2);
                            /*Si hay datos*/
                            if(rs2.next())
                            {
                                /*Obtiene los resultados*/
                                sVtaImp     = rs2.getString("Impo");
                                sVtaDesc    = rs2.getString("Descu");
                                sVtaImpue   = rs2.getString("Impue");                                                
                                sVtaTot     = Double.toString(Double.parseDouble(sVtaImp) - Double.parseDouble(sVtaImpue) - Double.parseDouble(sVtaDesc));                                                
                                
                                /*Obtiene las devoluciones*/
                                sDevImp     = rs2.getString("ImpoDev");                                
                                
                                /*Obtiene el total de ventas en efectivo*/
                                sPagEfe     = rs2.getString("ImpoEfe");
                                
                                /*Obtiene el total en caja*/
                                sTotCaj     = rs2.getString("ImpoCaj"); 
                                
                                /*Obtiene el total de pagos en débito*/
                                sPagDeb     = rs2.getString("ImpoDeb");                                                                                
                                
                                /*Obtiene el total de pagos con tarjeta de crédito*/
                                sPagTarCred = rs2.getString("ImpoTar");                                                                                
                                
                                /*Obtiene la cantidad total de facturas*/
                                sCantFac    = rs2.getString("CantFac");                                                                
                                
                                /*Obtiene la cantidad total de tickets*/
                                sCantTic    = rs2.getString("CantTic"); 
                                
                                /*Obtiene la cantidad total de remisiones*/
                                sCantRem    = rs2.getString("CantRem"); 
                                
                                /*Obtiene el total de facturas*/
                                sTotFac     = rs2.getString("TotFac");                                                                
                                
                                /*Obtiene el total de los tickets*/
                                sTotTic     = rs2.getString("TotTic");                                                                
                                
                                /*Obtiene el total de las remisiones*/
                                sTotRem     = rs2.getString("TotRem");                                                                
                                                                                               
                                /*Obtiene el total de depositar del día*/
                                sTotDep     = rs2.getString("TotDia");   
                                
                                /*Obtiene la cantidad de ventas del día*/
                                sTotVtas    = rs2.getString("CantVtas");                                                                
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                               
                        }                                                                               

                        /*Cálcula la venta neta*/
                        String sVtaNet          = Double.toString(Double.parseDouble(sVtaImp) - Double.parseDouble(sVtaDesc) - Double.parseDouble(sDevImp) - Double.parseDouble(sVtaImpue));
    
                        //Redondea la venta neta
                        sVtaNet                 = Double.toString(Math.round(Double.parseDouble(sVtaNet)));
                        
                        /*Cálcula el impuesto global*/
                        String sVtaImpueRea     = Double.toString(Double.parseDouble(sVtaImpue));
                        
                        /*Cálcula la venta neta con impuesto*/
                        String sVtaNetImp       = Double.toString(Double.parseDouble(sVtaNet) + Double.parseDouble(sVtaImpueRea));

                        /*Dale formato de moneda a la venta total bruta*/                        
                        double dCant            = Double.parseDouble(sVtaTot);
                        NumberFormat n          = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        sVtaTot                 = n.format(dCant);

                        /*Dale formato de moneda a los descuentos*/
                        dCant                   = Double.parseDouble(sVtaDesc);
                        sVtaDesc                = n.format(dCant);

                        /*Dale formato de moneda al total de caja*/
                        dCant                   = Double.parseDouble(sTotCaj);
                        sTotCaj                 = n.format(dCant);

                        /*Dale formato de moneda a los pagos en efectivo*/
                        dCant                   = Double.parseDouble(sPagEfe);
                        sPagEfe                 = n.format(dCant);

                        /*Dale formato de moneda al pago de tarjeta de crédito*/
                        dCant                   = Double.parseDouble(sPagTarCred);
                        sPagTarCred             = n.format(dCant);

                        /*Dale formato de moneda a la venta neta global*/
                        dCant                   = Double.parseDouble(sVtaNet);
                        sVtaNet                 = n.format(dCant);

                        /*Dale formato de moneda al pago de tarjeta de débito*/
                        dCant                   = Double.parseDouble(sPagDeb);
                        sPagDeb                 = n.format(dCant);

                        /*Dale formato de moneda al total de las facturas*/
                        dCant                   = Double.parseDouble(sTotFac);
                        sTotFac                 = n.format(dCant);

                        /*Dale formato de moneda al total de los tickets*/
                        dCant                   = Double.parseDouble(sTotTic);
                        sTotTic                 = n.format(dCant);
                        
                        /*Dale formato de moneda al total de las remisiones*/
                        dCant                   = Double.parseDouble(sTotRem);
                        sTotRem                 = n.format(dCant);
                        
                        /*Dale formato de moneda al total a depositar*/
                        dCant                   = Double.parseDouble(sTotDep);
                        sTotDep                 = n.format(dCant);

                        /*Dale formato de moneda a las devoluciones*/
                        dCant                   = Double.parseDouble(sDevImp);
                        sDevImp                 = n.format(dCant);

                        /*Dale formato de moneda al impuesto*/
                        dCant                   = Double.parseDouble(sVtaImpueRea);
                        sVtaImpueRea            = n.format(dCant);

                        /*Dale formato de moneda a la venta neta con impuesto*/
                        dCant                   = Double.parseDouble(sVtaNetImp);
                        sVtaNetImp              = n.format(dCant);

                        /*Muestra el formulario*/
                        try
                        {
                            /*Crea los parámetros que se pasarán*/
                            Map <String,String> par = new HashMap<>();
                            par.clear();
                            par.put("CORT",          sTip);
                            par.put("TOTCAJ",        sTotCaj);
                            par.put("MN",            rs.getString("mon"));
                            par.put("NOMEMPLOC",     Star.sNombreEmpresa);
                            par.put("NOCORT",        sCort);
                            par.put("F_HOY",         sFHoy);
                            par.put("VTABRUT",       sVtaTot);
                            par.put("DESCS",         sVtaDesc);
                            par.put("DEV",           sDevImp);
                            par.put("VTANET",        sVtaNet);
                            par.put("IMPUE",         sVtaImpueRea);
                            par.put("VTANETCONIMP",  sVtaNetImp);
                            par.put("TOTVTAS",       sTotVtas);
                            par.put("TOTFAC",        sTotFac);
                            par.put("TOTREM",        sTotRem);
                            par.put("TOTTICK",       sTotTic);
                            par.put("CANTFAC",       sCantFac);
                            par.put("CANTTICK",      sCantTic);
                            par.put("CANTREM",       sCantRem);
                            par.put("TOTEFE",        sPagEfe);
                            par.put("TOTDEB",        sPagDeb);
                            par.put("TOTTARCRED",    sPagTarCred);
                            par.put("TOTDEP",        sTotDep);
                            par.put("LOGE",          Star.class.getResource(Star.sIconDef).toString());

                            /*Compila el reporte*/
                            JasperReport jas    = JasperCompileManager.compileReport(Star.class.getResourceAsStream("/jasreport/rptCortXZ.jrxml"));

                            /*Llena el reporte*/
                            JasperPrint pr      = JasperFillManager.fillReport(jas, (Map)par, con);

                            /*Mandalo a la vista maximizado*/
                            JasperViewer v      = new JasperViewer(pr, false);
                            v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                                   /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte de "+sTip);
                    v.setIconImage(newimg);

                            v.setVisible(true);

                            if(bCarp && bSiG)
                            {
                                /*Si no existe la carpeta de los cortes X o Z entonces creala*/
                                String sRut = sCarp + sCarpG;
                                if(!new File(sRut).exists())
                                    new File(sRut).mkdir();
                                
                                /*Si no existe la carpeta de la empresa entonces creala*/
                                sRut        += "\\" + Login.sCodEmpBD;
                                if(!new File(sRut).exists())
                                    new File(sRut).mkdir();
                                
                                /*Completa el nomre del corte con su consecutivo*/
                                sRut        += "\\" + sCort + ".pdf";
                                
                                System.out.println(sRut);
                                /*Exportalo a la ruta específicada*/
                                JasperExportManager.exportReportToPdfFile(pr, sRut);
                            }
                            /*Declara variables*/
                            String  sImpAnt;
                            String  sImp       = "";

                            /*Obtiene el nombre de la impresora que tiene configuraada la estación actual*/
                            try
                            {
                                sQ = "SELECT impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";
                                st = con.createStatement();
                                rs = st.executeQuery(sQ);
                                /*Si hay datos entonces obtiene el resultado*/
                                if(rs.next())
                                    sImp         = rs.getString("impfac");
                            }
                            catch(SQLException expnSQL)
                            {
                                //Procesa el error y regresa
                                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                return;                                                                   
                            }

                            /*Obtiene la impresora predeterminada actual*/
                            PrintService serv   = PrintServiceLookup.lookupDefaultPrintService();
                            sImpAnt             = serv.getName();

                            /*Cambia la impresora predeterminada */
                            Star.vCambImp(sImp);

                            /*Mandalo a la impresora*/
                            JasperPrintManager.printReport(pr,false);

                            /*Cambia la impresora predeterminada que estaba anteriormente*/
                            Star.vCambImp(sImpAnt);

                            /*Declara variables*/
                            String sConsCort    = "1";

                            /*Obtiene el consecutivo del corte X o Z*/
                            boolean bSi  = false;
                            try
                            {
                                sQ = "SELECT numcort AS numcort FROM cortszx WHERE cort = '" + sTip + "' AND regis = 0";
                                st = con.createStatement();
                                rs = st.executeQuery(sQ);
                                /*Si hay datos, entonces si existe ya un cort Z*/
                                if(rs.next())
                                {
                                    /*Obtiene el consecutivo del flujo y pon la bandera*/
                                    sConsCort           = rs.getString("numcort");
                                    bSi                 = true;
                                }
                            }
                            catch(SQLException expnSQL)
                            {
                                //Procesa el error y regresa
                                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                return;                                                                   
                            }
                            
                            /*Si es corte Z entonces*/
                            if(sTip.compareTo("Z")==0)                                    
                            {
                                /*Genera la factura de cierre*/
                                vGeneFacCie(con);

                                /*Pon como ya registrados a todos los flujos y el ingreso a caja*/
                                try
                                {
                                    sQ = "UPDATE fluj SET corta  = 1, sucu = '" + Star.sSucu + "', nocaj = '" + Star.sNoCaj + "'";
                                    st = con.createStatement();
                                    st.executeUpdate(sQ);
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                    return;                                                                       
                                }

                            }/*Fin de if(sTip.compareTo("Z")==0)                                    */

                            /*Si no existe un corte entonces insertalo*/
                            if(!bSi)
                            {
                                /*Obtiene el máximo del corte*/
                                try
                                {
                                    sQ = "SELECT numcort + 1 as numcort FROM cortszx WHERE cort = '" + sTip + "'";
                                    st = con.createStatement();
                                    rs = st.executeQuery(sQ);
                                    /*Si hay datos, entonces si existe ya un cort Z o X entonces obtiene el corte*/
                                    if(rs.next())
                                        sConsCort            = rs.getString("numcort");
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                    return;                                                                       
                                }
                                
                                /*Inserta el cort Z o X*/
                                try
                                {
                                    sQ = "INSERT INTO cortszx (numcort,                                 cort,                           regis,      totvtas,     totingr,       totegre,       totcaj,     impue,      estac,                                       sucu,                                           nocaj ) " +
                                                   "VALUES(" + sConsCort.replace("'", "''") + ", '" +   sTip.replace("'", "''") + "',   0,          0,           0,             0,             0,          0,     '" + Login.sUsrG.replace("'", "''") + "','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";
                                    st = con.createStatement();
                                    st.executeUpdate(sQ);
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                    return;                                                                       
                                }                                                                
                                
                            }/*Fin de if(!bSi)*/

                            /*Si el total de ventas globales tiene el signo de dollar y coma quitaselo*/
                            sTotVtas            = sTotVtas.replace("$", "").replace(",", "");                            

                            /*Si el descuento de ventas globales tiene el signo de dollar y coma quitaselo*/
                            sVtaDesc            = sVtaDesc.replace("$", "").replace(",", "");                            

                            /*Si el importe de devolución de ventas globales tiene el signo de dollar y coma quitaselo*/
                            sDevImp             = sDevImp.replace("$", "").replace(",", "");                            

                            /*Si la venta neta global tiene el signo de dollar y coma quitaselo*/
                            sVtaNet             = sVtaNet.replace("$", "").replace(",", "");                            

                            /*Si la venta neta con impuesto global tiene el signo de dollar y coma quitaselo*/
                            sVtaNetImp          = sVtaNetImp.replace("$", "").replace(",", "");                            

                            /*Si el total de ventas tiene el signo de dollar y coma quitaselo*/
                            sTotFac             = sTotFac.replace("$", "").replace(",", "");                            

                            /*Si el total de tickets tiene el signo de dollar y coma quitaselo*/
                            sTotTic             = sTotTic.replace("$", "").replace(",", "");                            

                            /*Si el el pago en efectivo tiene el signo de dollar y coma quitaselo*/
                            sPagEfe             = sPagEfe.replace("$", "").replace(",", "");                            

                            /*Si el el pago en débito tiene el signo de dollar y coma quitaselo*/
                            sPagDeb             = sPagDeb.replace("$", "").replace(",", "");                            

                            /*Si el el pago en con tarjeta de crédito tiene el signo de dollar y coma quitaselo*/
                            sPagTarCred         = sPagTarCred.replace("$", "").replace(",", "");                            

                            /*Si el total a depositar tiene el signo de dollar y coma quitaselo*/
                            sTotDep             = sTotDep.replace("$", "").replace(",", "");                                                       
                            
                            /*Inserta el registro de que se consulto el corte X en caso de que sea tal corte*/
                            if(sTip.compareTo("X")==0)
                            {
                                try
                                {
                                    sQ = "INSERT INTO cortszx(numcort,                                  cort,                           regis,      totvtas,                  totingr,       totegre,        totcaj,     impue,          estac,                                             descu,               devs,               vtanet,            vtanetimp,              totfacs,            tottics,           cantfac,             canttics,           totefe,          totdeb,            tottarcred,           totdep,           sucu,                                               nocaj ) " +
                                                 "VALUES(" + sConsCort.replace("'", "''") + ",     '" + sTip.replace("'", "''") + "',   0,      " + sTotVtas + ",             0,             0,              0,          0,     '" +     Login.sUsrG.replace("'", "''") + "', " +       sVtaDesc + ", " +    sDevImp + ", " +    sVtaNet + ", " +   sVtaNetImp + ", " +     sTotFac + ", " +    sTotTic + ", " +   sCantFac + ", " +    sCantTic + ", " +   sPagEfe + ", " + sPagDeb + ", " +   sPagTarCred + ", " +  sTotDep + ",'" +  Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";
                                    st = con.createStatement();
                                    st.executeUpdate(sQ);
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                    return;                                                                       
                                }
                                
                            }/*Fin de if(sTip.compareTo("X")==0)*/                                                            
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                                                   
                        }
                        catch(JRException expnJASR)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR, con);                                                                   
                            return;                                   
                        }

                    }/*Fin de while (rs.next())*/
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                           
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();
                
                /*Si no hay registros entonces*/
                if(!bS)
                {                 
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "No existen registros para el corte " + sTip + ".", "Corte " + sTip, JOptionPane.INFORMATION_MESSAGE, null);
                    return;
                }
                /*Else si hay registros entonces*/
                else 
                {
                    /*Si es corte Z entoces*/
                    if(sTip.compareTo("Z")==0)
                    {
                        /*Pon las ventas para saber que ya se hizo corte*/
                        try
                        {
                            sQ = "UPDATE vtas SET cort = 'S', sucu = '" + Star.sSucu + "', nocaj = '" + Star.sNoCaj + "'";
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                               
                        }

                        /*Obtiene si se tiene que insertar automáticamente dinero en el cajón o no*/                
                        boolean bSiInsCaj   = false;
                        String sCaj = "";
                        try
                        {                  
                            sQ = "SELECT val, extr FROM confgral WHERE clasif = 'vtas' AND conf = 'insautcaj'";
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos, entonces*/                
                            if(rs.next())
                            {
                                /*Coloca la bandera correcta*/
                                if(rs.getString("val").compareTo("1")==0)
                                    bSiInsCaj = true;

                                /*Obtiene la cantidad a insertar en la caja automáticamente*/
                                sCaj   = rs.getString("extr");
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                               
                        }                   

                        /*Si se tiene que insertar automáticamente el dinero en el cajón entonces*/
                        if(bSiInsCaj)
                        {
                            /*Ingresa en el flujo lo de la caja inicial*/
                            try 
                            {                    
                                sQ = "INSERT INTO fluj(concep,    tipdoc,   norefer,    ing_eg,   impo,     mon,    modd,    vta,        ncortz,        corta,      estac,                                          sucu,                                           nocaj) " + 
                                               "VALUES('CAJ',    'NA',     'NA',       'I',   " + sCaj + ", 'MN',   'CXC',  0,           1,             0, '" +     Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                                st = con.createStatement();
                                st.executeUpdate(sQ);
                             }
                             catch(SQLException expnSQL) 
                             { 
                                //Procesa el error y regresa
                                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                return;                                                                                       
                             }

                        }/*Fin de if(bSiInsCaj)*/
                        /*Else el usr va a insertar el dinero*/
                        else
                        {
                            /*Mientras no se pueda salir del bucle entonces*/                    
                            do
                            {
                                /*Pidele al usr la cant para ingresar en caja*/
                                sCaj        = JOptionPane.showInputDialog("No existe ingreso de caja anterior, ingresa la cant inicial: ");

                                /*Si es nulo entonces sal del bucle*/
                                if(sCaj==null)
                                    break;

                                /*Si es cadena vacia entonces*/
                                if(sCaj.compareTo("")==0)
                                {
                                    /*Mensajea y continua*/
                                    JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "Ingreso Caja", JOptionPane.INFORMATION_MESSAGE, null);
                                    continue;
                                }

                                /*Si no se puede convertir a double entonces*/
                                try
                                {
                                    Double.parseDouble(sCaj);
                                }
                                catch(NumberFormatException expnNumForm)
                                {
                                    /*Mensajea y continua*/
                                    JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "Ingreso caja", JOptionPane.INFORMATION_MESSAGE, null);
                                    continue;
                                }

                                /*Si el valor es menor a 0 entonces*/
                                if(Double.parseDouble(sCaj) < 0)
                                {
                                    /*Mensajea y continua*/
                                    JOptionPane.showMessageDialog(null, "Ingresa una cant válida mayor a 0.", "Ingreso Caja", JOptionPane.INFORMATION_MESSAGE, null);                           
                                    continue;
                                }

                                /*Ingresa en el flujo lo de la caja inicial*/
                                try
                                {
                                    sQ = "INSERT INTO fluj(concep,   tipdoc,    norefer,    ing_eg,    impo,      mon,   modd,   vta,   ncortz,          corta,       estac,                                            sucu,                                           nocaj) " +
                                                   "VALUES('CAJ',    'NA',      'NA',       'I',   " + sCaj + ", 'MN',   'CXC',  0,     1,                0, '" +     Login.sUsrG.replace("'", "''") + "','" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";
                                    st = con.createStatement();
                                    st.executeUpdate(sQ);
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                                    return;                                                                       
                                }

                                /*Sal del bucle*/
                                break;
                            }
                            while(true);

                        }/*Fin de else*/                       

                    }/*Fin de if(sTip.compareTo("Z")==0)*/
                    
                }/*Fin de else*/

                //Cierra la base de datos
                Star.iCierrBas(con);                              
                                
            }/*Fin de public void run()*/
        }).start();
        
    }/*Fin de public static void vCortX()*/
    
    
    /*Función para generar y mandar por correo una órden de compra*/
    public static void vMandOr(final boolean bImp, final boolean bMos, final String sCo1, final String sCo2, final String sCo3, final String sCod, final String sNom, final String sProv, final String sSubTot, final String sTot, final String sImpue, final String sNomLoc, final String sTelLoc, final String sColLoc, final String sCallLoc, final String sCPLoc, final String sCiuLoc, final String sEstaLoc, final String sPaiLoc, final boolean bNoMan, final String sRutLog, final String sMon)
    {
        /*Thread para quitar carga y todo se haga mas rápido*/
        (new Thread()
        {
            @Override
            public void run()
            {               
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces
                if(con==null)
                    return;
                    
                /*Genera el reporte de la órden*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("ORD",               sCod);                                        
                    para.put("NOMPROV",           sNom);
                    para.put("CODPROV",           sProv);                    
                    para.put("EMPLOC",            sNomLoc);
                    para.put("TELLOC",            sTelLoc);
                    para.put("COLLOC",            sColLoc);
                    para.put("CALLLOC",           sCallLoc);
                    para.put("CPLOC",             sCPLoc);
                    para.put("CIULOC",            sCiuLoc);
                    para.put("MON",               sMon);
                    para.put("ESTLOC",            sEstaLoc);
                    para.put("PAILOC",            sPaiLoc);                    
                    para.put("LOG",               sRutLog);                    
                    para.put("LOGE",              Star.class.getResource(Star.sIconDef).toString());
                    para.put("SUBTOT",            sSubTot);
                    para.put("IMPUE",             sImpue);
                    para.put("TOT",               sTot);
                    /*Establece la ruta del reporte xml*/                        
                    JasperReport ja     = JasperCompileManager.compileReport(Star.class.getResourceAsStream("/jasreport/rptOrd.jrxml"));            
                    JasperPrint  pr     = JasperFillManager.fillReport(ja, (Map)para, con);            

                    /*Objetos para mostrarlo*/
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    

                    /*Si se tiene que mostrar el archivo entonces*/
                    if(bMos)
                    {
                               /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte de Orden de compra");
                    v.setIconImage(newimg);

                        v.setVisible(true);                    
                    }
                    /*Si se tiene que imprimir entonces hazlo*/
                    if(bImp)
                        JasperPrintManager.printReport(pr,true);

                    //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                    String sCarp    = Star.sGetRutCarp(con);                    

                    //Si hubo error entonces regresa
                    if(sCarp==null)
                        return;

                    /*Si el directorio de ordenes no existe entonces crea la ruta*/
                    sCarp = sCarp + "\\Ordenes";
                    if(!new File(sCarp).exists())
                        new File(sCarp).mkdir();
                        
                    /*Si el directorio de la empresa ordenes no existe entonces crea la ruta*/                    
                    sCarp += "\\" + Login.sCodEmpBD; 
                    if(!new File(sCarp).exists())
                        new File(sCarp).mkdir();                                        

                    /*Completa la ruta completa*/
                    String sRutPDF     = sCarp + "\\" + sCod + ".pdf";
                    System.out.println(sRutPDF);
                    /*Exportalo a pdf en el directorio completo con el nom del código de la órden*/
                    JasperExportManager.exportReportToPdfFile(pr, sRutPDF);
                    
                    /*Manda el PDF a los correos si lo tiene que mandar*/                                        
                    if((sCo1.compareTo("")!=0 || sCo2.compareTo("")!=0 || sCo3.compareTo("")!=0 ) && !bNoMan)
                        vMandPDFEmp(sCod + ".pdf", sRutPDF, "", "", sCo1, sCo2, sCo3, sCod, "ORD");                                                
                }
                catch(JRException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                           
                }

                //Cierra la base de datos
                Star.iCierrBas(con);                              
                                
            }/*Fin de public void run()*/
        }).start();
        
    }/*Fin de public static void vMandOr(final boolean bImp, final boolean bMos, final String sCo1, final String sCo2, final String sCo3, final String sCod, final String sFAlt, final String sNom, final String sProv,  final String sFEnt, final String sSubTot, final String sTot, final String sImpue, final String sEje, final String sNomLoc, final String sTelLoc, final String sColLoc, final String sCallLoc, final String sCPLoc, final String sCiuLoc, final String sEstaLoc, final String sPaiLoc)*/

    
    /*Válida si el rfc introducido por el usuario es válido o no*/
    public static void vValRFC(String sRFC, boolean bMora, JTextField jTRFC, boolean bGen)
    {
        /*Conviertelo a mayúsculas*/
        sRFC        = sRFC.toUpperCase().trim();
        
        /*Si tiene que validar RFC genérico entonces*/
        if(bGen)
        {
            /*Si es un RFC genérico entonces*/
            if(sRFC.compareTo(Star.sRFCGen)==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El RFC es genérico.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                /*Pon la bandera en false y regresa*/
//                bValRFC  = false;
//                return;                    
            }                        
        }                       
                                
        /*Si es moral entonces*/
        if(bMora)
        {
            /*Si la longitud es igual a 12 entonces*/
            if(sRFC.length() == 12)            
            {
                
                /*Si los primeros 3 carácteres no son letras entonces*/                
                if(NumberUtils.isDigits(sRFC.substring(0, 1)) || NumberUtils.isDigits(sRFC.substring(1, 2)) || NumberUtils.isDigits(sRFC.substring(2, 3)))
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los primeros 3 caracteres del RFC deben ser letras.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;                    
                }
                
                /*Si es mayor a 12 el mes entonces*/                
                if(Integer.parseInt(sRFC.substring(5, 7)) > 12)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los dígitos 6 y 7 del mes del RFC no deben ser mayor a 12.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;
                }

                /*Si es mayor a 31 el día entonces*/
                if(Integer.parseInt(sRFC.substring(7, 9)) > 31)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los dígitos 8 y 9 del día no deben ser mayores a 31.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;
                }

                /*Si el pnúltimo dígito es O entonces*/
                if(sRFC.substring(sRFC.length() - 2, sRFC.length()-1).compareTo("O")==0)
                {
                    /*Si el último dígito no es 0 entonces*/
                    if(!sRFC.endsWith("0"))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Si el penúltimo dígito es O entonces el último dígito del RFC debe ser 0.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                        /*Pon la bandera en false y regresa*/
                        bValRFC  = false;
                        return;
                    }
                                        
                }
                
                /*Si el pnúltimo dígito es S entonces*/
                if(sRFC.substring(sRFC.length() - 2, sRFC.length()-1).compareTo("S")==0)
                {
                    /*Si el último dígito no es 5 entonces*/
                    if(!sRFC.endsWith("5"))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Si el penúltimo dígito es S entonces el último dígito del RFC debe ser 5.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                        /*Pon la bandera en false y regresa*/
                        bValRFC  = false;
                        return;
                    }
                                            
                }
                
                /*Devuelve el resultado*/
                bValRFC   = true;        
                
            }/*Fin de if(sRFC.length() == 13)*/
            else
            {
                /*Coloca el borde rojo*/                               
                jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La longitud debe ser de 12 dítigos para persona moral.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 
                bValRFC      = false;
            }
         
        }/*Fin de if(bMora)*/
        /*Else, es física*/
        else
        {            
            /*Si la longitud es igual a 13 entonces*/
            if(sRFC.length() == 13)            
            {                
                /*Si los primeros 4 carácteres no son letras entonces*/                
                if(NumberUtils.isDigits(sRFC.substring(0, 1)) || NumberUtils.isDigits(sRFC.substring(1, 2)) || NumberUtils.isDigits(sRFC.substring(2, 3)) || NumberUtils.isDigits(sRFC.substring(3, 4)))
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los primeros 4 caracteres del RFC deben ser letras.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;                    
                }
                
                /*Si los 6 dígitos del 5 al 10 no son númericos entonces*/                                
                if(!NumberUtils.isDigits(sRFC.substring(5, 6)) || !NumberUtils.isDigits(sRFC.substring(6, 7)) || !NumberUtils.isDigits(sRFC.substring(7, 8)) || !NumberUtils.isDigits(sRFC.substring(8, 9)) || !NumberUtils.isDigits(sRFC.substring(9, 10)))
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los dígitos del 5 al 10 deben ser numéricos.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 
                    
                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;
                }                                    
                
                /*Si es mayor a 12 el mes entonces*/                
                if(Integer.parseInt(sRFC.substring(6, 8)) > 12)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los dígitos 7 y 8 del mes del RFC no deben ser mayor a 12.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;
                }

                /*Si es mayor a 31 el día entonces*/
                if(Integer.parseInt(sRFC.substring(8, 10)) > 31)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Los dígitos 9 y 10 del día no deben ser mayores a 31.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                    /*Pon la bandera en false y regresa*/
                    bValRFC  = false;
                    return;
                }

                /*Si el pnúltimo dígito es O entonces*/
                if(sRFC.substring(sRFC.length() - 2, sRFC.length()-1).compareTo("O")==0)
                {
                    /*Si el último dígito no es 0 entonces*/
                    if(!sRFC.endsWith("0"))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Si el penúltimo dígito es O entonces el último dígito del RFC debe ser 0.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                        /*Pon la bandera en false y regresa*/
                        bValRFC  = false;
                        return;
                    }
                                        
                }
                
                /*Si el pnúltimo dígito es S entonces*/
                if(sRFC.substring(sRFC.length() - 2, sRFC.length()-1).compareTo("S")==0)
                {
                    /*Si el último dígito no es 5 entonces*/
                    if(!sRFC.endsWith("5"))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Si el penúltimo dígito es S entonces el último dígito del RFC debe ser 5.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 

                        /*Pon la bandera en false y regresa*/
                        bValRFC  = false;
                        return;
                    }
                                            
                }
                
                /*Devuelve el resultado*/
                bValRFC   = true;        
                
            }/*Fin de if(sRFC.length() == 13)*/
            else
            {
                /*Coloca el borde rojo*/                               
                jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La longitud debe ser de 13 dítigos para persona física.", "RFC", JOptionPane.INFORMATION_MESSAGE, null); 
                bValRFC      = false;
            }
                
        }/*Fin de else*/
                
    }/*Fin de public satatic boolean bValidaRFC(String sRFC, boolean bMora)*/
    

    /*Obtiene la imágen si es que tiene*/
    public static synchronized void vGetImg(String sProd, javax.swing.JLabel jLImg)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                               
        }
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);                
            return;                        
        }
        
        //Si la carpeta de las imágenes no existe entonces crea la carpeta
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();                    
        
        //Si la carpeta de los productos no existe entonces crea la carpeta
        sCarp                    += "\\Productos";               
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();                                    
        
        //Si la carpeta de la empresa no existe entonces crea la carpeta
        sCarp                    += "\\" + Login.sCodEmpBD;               
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();                
        
        //Si la carpeta del producto en específico no existe entonces crea la carpeta
        sCarp                    += "\\" + sProd;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();                
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;
        
        /*Si la imágen existe en la ruta entonces*/                        
        if(new File(sCarp).exists())
        {
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArch [];
                sArch = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg.setIcon(new ImageIcon(sCarp + "\\" + sArch[0]));

                /*Que el label sea visible*/
                jLImg.setVisible(true);

            }
            /*Else, no existe imágen entonces que esconda la imágen*/
            else
                jLImg.setVisible(false);
        }                    
        
    }/*Fin de private void vGetImg(String sProd)*/

    
    /*Registra el producto que se esta devolviendo o agregando al invtario en la tabla de monitor de invtarios*/
    public static boolean vRegMoniInv(Connection con, String sCodProd, String sCant, String sDescrip, String sCodAlma, String sEstac, String sCod, String sConcep, String sUnid, String sNoSer, String sEmp, String sEntSal)
    {
        //Declara variables de la base de datos
         Statement      st;                 
         String         sQ; 
         

         /*Registra en la base de altos la alta de los productos por la devolución de la compra*/
         try 
         {             
             sQ = "INSERT INTO moninven(prod,                                   cant,           descrip,                                alma,                                   estac,                              nodoc,                              concep,                                 sucu,                                     nocaj,                                            unid,                               noser,                                      emp,                                entsal) " + 
                           "VALUES('" + sCodProd.replace("'", "''") + "'," +    sCant + ",'" +  sDescrip.replace("'", "''") + "','" +   sCodAlma.replace("'", "''") + "','" +   sEstac.replace("'", "''") + "','" + sCod.replace("'", "''") + "','" +   sConcep.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" +         sUnid.replace("'", "''") + "', '" + sNoSer.replace("'", "''") + "', '" +        sEmp.replace("'", "''") + "', " +   sEntSal + ")";                                 
             st = con.createStatement();
             st.executeUpdate(sQ);
          }
          catch(SQLException expnSQL) 
          { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return false;                        
          }
         
         /*Devuelve que todo fue bien*/
         return true;
         
    }/*Fin de public static boolean vRegMoniInv(Connection con, String sCodProd, String sCant, String sDescrip, String sCodAlma, String sEstac, String sCodComp, String sConcep, String sUnid, String sNoSer, String sEmp, String sEntSal)*/    

    
    /*Actualiza el PEPS igual a la cantidad y continua*/
    public static int iActuaPEPS(Connection con, String sId, String sCan, boolean bSi)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        
        /*Determina la consulta que debe de quedar*/
        if(bSi)
            sQ  = "UPDATE costs SET cant = cant - " + sCan + " WHERE id_id = " + sId;
        else
            sQ = "DELETE FROM costs WHERE id_id = " + sId;
        
        /*Ejecuta la consulta*/
        try 
        {                                              
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }    
       
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iActuaPEPS(Connection con, String sId, String sCan)*/

    
    /*Actualiza el UEPS igual a la cantidad y continua*/
    public static int iActuaUEPS(Connection con, String sId, String sCan, boolean bSi)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        /*Crea la consulta correcta*/
        if(bSi)
           sQ = "UPDATE costs SET cant = cant - " + sCan + " WHERE id_id = " + sId;                                
        else 
            sQ  = "DELETE FROM costs WHERE id_id = " + sId;            
        
        /*Enecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }    
                
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iActuaUEPS(Connection con, String sId, String sCan)*/
    
    
    /*Genera factura de cierrre*/
    public static void vGeneFacCie(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
               
        
        
        
        /*Obtiene la serie que debe tomar para hacer la factura*/                
        String sSerFR               = "";
        try
        {                  
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'serfac'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sSerFR                 = rs.getString("extr");            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }

        //Declara variables locales        
        String      sTotCost        = "";
        String      sTotDesc        = "";
        String      sSubTot         = "";        
        String      sImpue          = "";
        String      sTot            = "";
        String      sTotProm        = "";
        String      sTotUEPS        = "";
        String      sTotPEPS        = "";
         
        /*Recorre todas las partidas de las remisiones o tickets que no han sido facturados para obtener los totales*/
        try
        {
            sQ = "SELECT IFNULL(SUM(totdescu),0) AS totdescu, IFNULL(SUM(totcostprom),0) AS totcostprom, IFNULL(SUM(totpeps),0) AS totpeps, IFNULL(SUM(totueps),0) AS totueps, IFNULL(SUM(totcost),0) AS totcost, IFNULL(SUM(subtot),0) AS totsubtot, IFNULL(SUM(tot),0) AS tottot, IFNULL(SUM(impue),0) AS totimpue FROM vtas WHERE (tipdoc = 'TIK' OR tipdoc = 'REM') AND estad = 'CO' AND ptovta = 1 AND factu = 0";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sTotCost    = rs.getString("totcost");
                sSubTot     = rs.getString("totsubtot");
                sImpue      = rs.getString("totimpue");
                sTot        = rs.getString("tottot");
                sTotProm    = rs.getString("totcostprom");
                sTotUEPS    = rs.getString("totueps");
                sTotDesc    = rs.getString("totdescu");
                sTotPEPS    = rs.getString("totpeps");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }  
        
        /*Obtiene el consecutivo de la factura*/       
        String      sConsFac        = "";                
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE ser = '" + sSerFR + "' AND tip = 'FAC'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sConsFac      = rs.getString("consec");                      
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }                  
        
        /*Actualiza el consecutivo de las facturas*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sSucu + "' "
                    + "WHERE ser    = '" + sSerFR + "' AND tip = 'FAC'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }                        
        
        //Inserta en la base de datos la nueva factura
        if(Star.iInsVtas(con, sSerFR.replace("'", "''"), sConsFac.replace("'", "''"), Star.sCliMostG, Star.sSerMostG, sSubTot, sImpue, sTot, "now()", "now()", "now()", "'CO'", "0", "", "FAC", "0", "EFE", "0000", "CORT Z", "0", sTotDesc, "1", "1", sTotCost, Login.sUsrG, "PESOS", "1", "C", "", "", "", "", "", "", "", "", "", "", "", "", "0", "", "1", sTotUEPS, sTotPEPS, sTotProm,"")==-1)
            return;
                
        /*Declara variables*/
        String  sFAlt       = "";        
        String  sVta        = "";        
        String  sCo1        = "";
        String  sMon        = "";
        
        /*Obtiene algunos datos de la factura*/
        try
        {                  
            sQ = "SELECT vtas.MON, vtas.FALT, vta, co1 FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER ,emps.CODEMP) = vtas.CODEMP ORDER BY vta DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {            
                sFAlt       = rs.getString("vtas.FALT");                                                                    
                sVta        = rs.getString("vta");                                
                sCo1        = rs.getString("co1");
                sMon        = rs.getString("mon");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }        

        /*Obtiene todas las ventas que no han sido cortadas ni facturadas y que son tickets o remisiones*/        
        try
        {                  
            sQ = "SELECT vta FROM vtas WHERE cort <> 'S' AND tipdoc <> 'FAC' AND factu = 0 AND estad = 'CO'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Inserta las partidas de la venta en la nueva*/
                Star.vInsPrtvta(rs.getString("vta"), con, sVta);                                                                                                            
                
                /*Actualiza la venta que ya fue facturada*/
                Star.vActFac(rs.getString("vta"), con);                                                                                                            
                
                /*Actualiza la venta que ya fue cortada*/
                Star.vActCort(rs.getString("vta"), con);                                                                                                            
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                         
        }
        
        /*Contiene la moneda nacional y su tipo de cambio*/        
        String sTipCam  = "";
        
        /*Obtiene el tipo de cambio de la moneda*/        
        try
        {                  
            sQ = "SELECT val FROM mons WHERE mon = '" + sMon + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sTipCam = rs.getString("val");            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
        
        /*Actualiza el el flujo el norefer para referenciarla*/
        try 
        {            
            sQ = "UPDATE fluj SET "
                    + "norefer      = " + sVta + ", "
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sSucu + "' "
                    + "WHERE corta  = 0";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }

        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)        
            return;        
        
        /*Dale formato de moneda al total para quitar todos los decimales que por eso falla la función de convertir a letra*/        	
        double dCant                    = Double.parseDouble(sTot);                
        NumberFormat n                  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot                            = n.format(dCant).replace("$", "").replace(",", "");
        
        /*Obtiene el símbolo de la moneda*/
        String sSimb    = "";
        try
        {
            sQ = "SELECT simb FROM mons WHERE mon = '" + sMon + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSimb   = rs.getString("simb");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }            
        
        /*Obtiene el total con letra*/        
        String sTotLet                  = Star.sObLet(sTot, sMon, sSimb, true);      
        
        /*Declara variables locales de la empresa local*/        
        String      sCallLoc            = "";
        String      sTelLoc             = "";
        String      sPaiLoc             = "";
        String      sColLoc             = "";
        String      sCPLoc              = "";
        String      sCiuLoc             = "";
        String      sEstaLoc            = "";                
        String      sNoExtLoc           = "";
        String      sNoIntLoc           = "";        
        
        /*Obtiene todos los datos de la empresa local*/
        try
        {                  
            sQ = "SELECT pai, noint, noext, calle, tel, col, cp, ciu, estad FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                                
                sCallLoc            = rs.getString("calle");                                    
                sTelLoc             = rs.getString("tel");                                    
                sPaiLoc             = rs.getString("pai");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstaLoc            = rs.getString("estad");                                                                    
                sNoExtLoc           = rs.getString("noext");   
                sNoIntLoc           = rs.getString("noint");                                                                  
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }
       
        /*Dale formato de moneda a los totales*/
        dCant           = Double.parseDouble(sSubTot);        
        sSubTot         = n.format(dCant);
        dCant           = Double.parseDouble(sImpue);        
        sImpue          = n.format(dCant);
        dCant           = Double.parseDouble(sTot);        
        sTot            = n.format(dCant);        
        
        /*Declara variables finales para el thread*/
        final String sConsFacFi = sConsFac;
        final String sVtaFi     = sVta;        
        final String sFAltFi    = sFAlt;        
        final String sTelFi     = sTelLoc;
        final String sCallFi    = sCallLoc;
        final String sColFi     = sColLoc;
        final String sCPFi      = sCPLoc;
        final String sNoExtFi   = sNoExtLoc;
        final String sPaiFi     = sPaiLoc;
        final String sNoIntFi   = sNoIntLoc;
        final String sTipCamFi  = sTipCam;
        final String sCiuFi     = sCiuLoc;
        final String sEstaFi    = sEstaLoc;
        final String sRFCFi     = Star.sRFCGen;
        final String sCo1Fi     = sCo1;
        final String sTotLetFi  = sTotLet;
        final String sSubTotFi  = sSubTot;
        final String sImpFi     = sImpue;
        final String sMonFi     = sMon;
        final String sTotFi     = sTot;        
        final String sSerFi     = sSerFR;        
        
        /*Thread para quitar carga y todo se haga mas rápido*/
        (new Thread()
        {
            @Override
            public void run()
            {   
                /*Función para hacer el timbrado y generar PDF y XML*/
                Star.vGenTim("fac", "", sConsFacFi, sVtaFi, "", sFAltFi, "PUBLICO GENERAL", sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstaFi, sRFCFi, sCo1Fi, sTotLetFi, sSubTotFi, sImpFi, sTotFi, sSerFi, "Efectivo", "0000", "", getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), false, false, "", "", "", false, false, false, false, -1, null, false, sMonFi, "0", "Pago en efectivo", "ingreso", sTipCamFi, "");                                            
            }
        }).start();
        
    }/*Fin de private void vGeneFacCie()*/
    
    
    /*Inserta las partidas de cada venta en la venta de cierre*/
    public static void vInsPrtvta(String sVta, Connection con, String sVtaOri)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        String      sQ; 
        Statement   st2;        
        String      sQ2; 
        
        
        
        /*Obtiene todas las partidas de esta venta*/
        try
        {
            sQ = "SELECT * FROM partvta WHERE vta = " + sVta;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Inserta en la base de datos las partida de la factura*/
                try 
                {                    
                    sQ2= "INSERT INTO partvta(    vta,                prod,                                             cant,                                 unid,                                                 descrip,                                                pre,                            descu,                              impue,                              mon,                                                impo,                          falt,        eskit,                         kitmae,                                              tipdoc,                                                 list,                           alma,                           peps,                           ueps,                          tipcam,                              serprod,                                       comenser,                           garan,                           lot,                            pedimen,                          fcadu,                           codimpue) " + 
                                     "VALUES('" + sVtaOri + "','"  +  rs.getString("prod").replace("'", "''") + "','" + rs.getString("cant") + "','" +        rs.getString("unid").replace("'", "''") + "','" +     rs.getString("descrip").replace("'", "''") + "','" +    rs.getString("pre") + "','" +   rs.getString("descu") + "','" +     rs.getString("impue") + "','" +     rs.getString("mon").replace("'", "''") + "','" +    rs.getString("impo") + "',     now(), " +   rs.getString("eskit") + "," +  rs.getString("kitmae").replace("'", "''") + ", '" +  rs.getString("tipdoc").replace("'", "''") + "', " +     rs.getString("list") + ", '" +  rs.getString("alma") + "', " +  rs.getString("peps") + ", " +   rs.getString("ueps") + ", '" + rs.getString("tipcam") + "', '" +    rs.getString("serprod") + "',             '" + rs.getString("comenser") + "', '" + rs.getString("garan") + "', '" + rs.getString("lot") + "', '" + rs.getString("pedimen") + "', '" + rs.getString("fcadu") + "', '" + rs.getString("codimpue") + "')";                                    
                    st2= con.createStatement();
                    st2.executeUpdate(sQ2);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                       
                 }                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                      
        }                
        
    }/*Fin de private void vInsPrtvta(String sVta, Connection con)*/            

    
    /*Actualiza la venta de que ya se facturo*/
    public static void vActFac(String sVta, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        /*Inserta en la base de datos las partida de la factura*/
        try 
        {                    
            sQ = "UPDATE vtas SET factu = 1 WHERE vta = " + sVta;                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
         }                
        
    }/*Fin de private void vActFac(String sVta, Connection con)*/            
    
    
    /*Actualiza la venta de que ya se corto*/
    public static void vActCort(String sVta, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        /*Inserta en la base de datos las partida de la factura*/
        try 
        {                    
            sQ = "UPDATE vtas "
                    + "SET cort     = 'S' "
                    + "WHERE vta    = " + sVta;                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                               
         }                
        
    }/*Fin de private void vActCort(String sVta, Connection con)*/            
    
    
    /*Procesa la parte de tallas y colores con respecto a las existencias*/
    public static void vTallCol(Connection con, String sCant, String sAlma, String sTall, String sColo, String sProd, String sOper)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Comprueba si ya existe este producto con la talla, color y almacén en la tabla de tallas y colores*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT prod FROM tallcolo WHERE prod = '" + sProd + "' AND alma = '" + sAlma + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }

        /*Crea la consulta correcta dependiendo si existe o no ya ese registro en la base de datos*/
        if(bSi)
            sQ  = "UPDATE tallcolo SET "
                    + "exist        = exist " + sOper + " " + sCant + " "
                    + "WHERE prod   = '" + sProd + "' AND alma = '" + sAlma + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";
        else
            sQ  = "INSERT INTO tallcolo (prod,           tall,            alma,            colo,           exist,                       estac,                 sucu,                         nocaj) "
                         + "VALUES('" + sProd + "', '" + sTall + "', '" + sAlma + "', '" + sColo + "', " + sOper + " " +sCant + ", '" + Login.sUsrG + "', '" + Star.sSucu + "', '" +   Star.sNoCaj + "')";
        
        /*Ejecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
         }    
        
    }/*Fin de private void vTallCol(Connection con, String sCant, String sAlma, String sTall, String sColo, String sProd)*/

    
    /*Procesa la parte de lote y pedimento con respecto a las existencias*/
    public static String sLotPed(Connection con, String sProd, String sCant, String sAlma, String sLot, String sPed, String sFCadu, String sOper)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        ResultSet   rs;
        
        
    
        
        /*Comprueba si ya existe ese lote o pedimento*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT lot FROM lotped WHERE LOWER(lot) = '" + sLot.toLowerCase() + "' AND LOWER(pedimen) = '" + sPed.toLowerCase() + "' AND alma = '" + sAlma + "' AND DATE(fcadu) = DATE('" + sFCadu + "')";	            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }

        /*Crea la consulta correcta dependiendo si existe o no ya ese registro en la base de datos*/
        if(bSi)
            sQ  = "UPDATE lotped SET "
                    + "exist        = exist " + sOper + " " + sCant + " "
                    + "WHERE lot = '" + sLot + "' AND pedimen = '" + sPed + "' AND alma = '" + sAlma + "' AND DATE(fcadu) = DATE('" + sFCadu + "')";
        else
            sQ  = "INSERT INTO lotped(prod,            alma,            lot,            pedimen,            fcadu,              exist,          estac,                  sucu,                   nocaj) "
                       + "VALUES('" + sProd + "', '" + sAlma + "', '" + sLot + "', '" + sPed + "', '" +     sFCadu + "', " +    sCant + ", '" + Login.sUsrG + "', '" +  Star.sSucu + "', '" +   Star.sNoCaj + "')";
         
        /*Ejecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
         }    
        
        /*Regresa que todo fue bien*/
        return "";
        
    }/*Fin de public static String sLotPed(Connection con, String sProd, String sCant, String sAlma, String sLot, String sPed, String sFCadu, String sOper)*/
    
    
    /*Si el usuario actual esta configurado para máximos y mínimos entonces comienza el thread*/
    public static void vMinMax(Connection con)
    {
        /*Si no es nulo el mínimos entonces detenlos*/
        if(Star.timMin!=null)
            Star.timMin.cancel();
                
        /*Si no es nulo el máximos entonces detenlos*/
        if(Star.timMax!=null)
            Star.timMax.cancel();
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                         
                    
        /*Recorre los usuarios que están configurados para mácimos y mínimos*/
        try
        {                  
            sQ = "SELECT estac, hrs FROM maxminconf WHERE estac = '" + sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si esta configurada para timer de máximos y mínimos*/
            if(rs.next())
            {                
                /*Programa el timer para que cada ciertas horas revise si hay producstos por debajo del mínimo*/
                Star.timMin = new Timer();
                Star.timMin.scheduleAtFixedRate(new TimerTask() 
                {
                   @Override
                   public void run() 
                   {                  
                        //Abre la base de datos nuevamente
                        Connection con = Star.conAbrBas(true, false);

                        //Si hubo error entonces
                        if(con==null)
                            return;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String sQ; 
                        
                        /*Comprueba si algún producto esta debajo de su mínimo*/
                        try
                        {                  
                            sQ = "SELECT COUNT(*) conteo FROM prods WHERE exist  < min AND solmaxmin = 0";                            
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces si los hay*/
                            if(rs.next())
                            {
                                /*Si el conteo es 0 entonces que no muestre nada*/
                                if(Integer.parseInt(rs.getString("conteo")) == 0)
                                {
                                    //Cierra la base de datos y regresa
                                    Star.iCierrBas(con);                
                                    return;
                                }
                                
                                /*Mensajea informando al usuario de la cantidad de productos que requieren su atención*/                                
                                Object[] op = {"Si","No"};
                                int iRes    = JOptionPane.showOptionDialog(null, "Hay " + rs.getString("conteo") + " productos que estan debajo del mínimo. ¿Quieres verlos?", "Existencia", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconDu)), op, op[0]);
                                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                                {
                                    //Cierra la base de datos y regresa
                                    Star.iCierrBas(con);                 
                                    return;                       
                                }

                                /*Muestra el formulario para ver los productos que están debajo del mínimo*/
                                BajsMin m = new BajsMin();
                                m.setVisible(true);                                
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                            
                        }
                        
                        //Cierra la base de datos
                        Star.iCierrBas(con);               
                        
                   }/*Fin de public void run()*/
                 }, Integer.parseInt(rs.getString("hrs")) * 60000, Integer.parseInt(rs.getString("hrs")) * 60000);                                                   

                /*Programa el timer para que cada ciertas horas revise si hay producstos por debajo del mínimo*/
                Star.timMin = new Timer();
                Star.timMin.scheduleAtFixedRate(new TimerTask() 
                {
                   @Override
                   public void run() 
                   {                  
                        //Abre la base de datos nuevamente
                        Connection con = Star.conAbrBas(true, false);

                        //Si hubo error entonces
                        if(con==null)
                            return;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String sQ; 
                        
                        /*Comprueba si algún producto esta arriba de su máximo*/
                        try
                        {                  
                            sQ = "SELECT COUNT(*) conteo FROM prods WHERE exist  > max AND solmaxmin = 0";                            
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces si los hay*/
                            if(rs.next())
                            {
                                /*Si el conteo es 0 entonces que no muestre nada*/
                                if(Integer.parseInt(rs.getString("conteo")) == 0)
                                {
                                    //Cierra la base de datos y regresa
                                    Star.iCierrBas(con);                  
                                    return;
                                }
                                
                                /*Mensajea informando al usuario de la cantidad de productos que requieren su atención*/                                
                                Object[] op = {"Si","No"};
                                int iRes    = JOptionPane.showOptionDialog(null, "Hay " + rs.getString("conteo") + " productos que están arriba del máximo. ¿Quieres verlos?", "Existencia", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconDu)), op, op[0]);
                                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                                {
                                    //Cierra la base de datos y regresa
                                    Star.iCierrBas(con);                 
                                    return;                       
                                }

                                /*Muestra el formulario para productos arriba del máximo*/
                                AltsMaxs a = new AltsMaxs();
                                a.setVisible(true);
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                            
                        }
                                 
                        //Cierra la base de datos
                        Star.iCierrBas(con);              
                        
                   }/*Fin de public void run()*/
                 }, Integer.parseInt(rs.getString("hrs")) * 60000, Integer.parseInt(rs.getString("hrs")) * 60000);                                                   
                
            }/*Fin de if(rs.next())*/
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                               
        }
        
    }/*Fin de public static void vMinMax(Connection con)*/


    /*Función para procesar esta parte*/
    public static void vRespUsr()
    {
        /*Búcle inifinito*/
        while(true)
        {
            //Abre la base de datos nuevamente
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;            
            String      sQ; 
            
            /*Comprueba si este usuario es de respaldo o no*/
            String sHrs;
            try
            {
                sQ = "SELECT hrs FROM resp WHERE estac = '" + Login.sUsrG + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sHrs   = rs.getString("hrs");                                    
                else
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);                  
                    break;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;                
            }
            
            //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
            String sCarp    = Star.sGetRutCarp(con);                    

            //Si hubo error entonces regresa
            if(sCarp==null)
                return;

            //Declara variables locales
            String sRut1    = "";
            String sRut2    = "";
            String sRut3    = "";
            String sRutBin  = "";            
            
            /*Obtiene las rutas donde esta el bin de mysql, la ruta de donde se respaldaran todos los archvios PDF y las 3 rutas de respaldo del usuario actual*/            
            try
            {
                sQ = "SELECT res1path, res2path, res3path, rutmysq FROM estacs WHERE estac = '" + Login.sUsrG + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los datos*/
                if(rs.next())
                {                
                    sRut1   = rs.getString("res1path");
                    sRut2   = rs.getString("res2path");
                    sRut3   = rs.getString("res3path");
                    sRutBin = rs.getString("rutmysq");            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;                
            }

            /*Si el exe de mysql no existe en la ruta entonces esta mal definida la ruta*/
            if(!new File(sRutBin + "\\mysql.exe").exists())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea y sal del búcle inifinito*/
                JOptionPane.showMessageDialog(null, "Respaldo automático: No existe el servidor Mysql.exe en la ruta específicada. " + System.getProperty( "line.separator" ) + "Específica la ruta correcta para poder hacer respaldo automático.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null);                         
                break;
            }

            /*Si no existe la ruta 1 de respaldo entonces*/
            if(!new File(sRut1).exists())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea y sal del búcle inifinito*/
                JOptionPane.showMessageDialog(null, "Respaldo automático: No existe la ruta \"" + sRut1 + "\"" + System.getProperty( "line.separator" ) + "Crea la ruta primero para poder hacer respaldo automático.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null);                         
                break;
            }
            
            /*Si no existe la ruta 1 de respaldo entonces*/
            if(!new File(sRut2).exists())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea y sal del búcle inifinito*/
                JOptionPane.showMessageDialog(null, "Respaldo automático: No existe la ruta \"" + sRut1 + "\"" + System.getProperty( "line.separator" ) + "Crea la ruta primero para poder hacer respaldo automático.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null);                         
                break;
            }
            
            /*Si no existe la ruta 1 de respaldo entonces*/
            if(!new File(sRut3).exists())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea y sal del búcle inifinito*/
                JOptionPane.showMessageDialog(null, "Respaldo automático: No existe la ruta \"" + sRut1 + "\"" + System.getProperty( "line.separator" ) + "Crea la ruta primero para poder hacer respaldo automático.", "Directorio BIN", JOptionPane.INFORMATION_MESSAGE, null);                         
                break;
            }
            
            /*Guarda las rutas originales*/
            String sRut1O   = sRut1;
            String sRut2O   = sRut2;
            String sRut3O   = sRut3;
                        
            /*Substring para obtener la unidad con todo y la diagonal invertida en todas las rutas*/
            String sUnid1       = sRut1.substring       (0, 3);
            String sUnid2       = sRut2.substring       (0, 3);
            String sUnid3       = sRut3.substring       (0, 3);
            String sUnidBin     = sRutBin.substring     (0, 3);
            
            /*Substring la ruta para ponerles comillas dobles a todos los nombres de archivos para que el bat corra correctamente a todas las rutas*/
            sRut1               = sRut1.substring       (3, sRut1.length());
            sRut2               = sRut2.substring       (3, sRut2.length());
            sRut3               = sRut3.substring       (3, sRut3.length());
            sRutBin             = sRutBin.substring     (3, sRutBin.length());
            
            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta de respaldo 1*/                                                                
            StringTokenizer st1 = new StringTokenizer(sRut1,"\\");
            sRut1 = "\"";
            while(st1.hasMoreTokens())
                sRut1 += st1.nextToken() + "\"\\\"";

            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta de respaldo 2*/                                                                
            st1 = new StringTokenizer(sRut2,"\\");
            sRut2 = "\"";
            while(st1.hasMoreTokens())
                sRut2 += st1.nextToken() + "\"\\\"";
            
            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta de respaldo 3*/                                                                
            st1 = new StringTokenizer(sRut3,"\\");
            sRut3 = "\"";
            while(st1.hasMoreTokens())
                sRut3 += st1.nextToken() + "\"\\\"";
            
            /*Tokeniza la cadena por la diagonal invertida para poder poner las comillas dobles en cada carpeta de la ruta bin*/                                                                
            st1 = new StringTokenizer(sRutBin,"\\");
            sRutBin = "\"";
            while(st1.hasMoreTokens())
                sRutBin += st1.nextToken() + "\"\\\"";
            
            /*Quita la última diagonal invertida y dobles comillas en todas las rutas*/
            sRut1    = sRut1.substring      (0, sRut1.length() - 2);
            sRut2    = sRut2.substring      (0, sRut2.length() - 2);
            sRut3    = sRut3.substring      (0, sRut3.length() - 2);
            sRutBin  = sRutBin.substring    (0, sRutBin.length() - 2);
            
            /*Junta la unidad con todas las rutas nuevamente*/
            sRut1    = sUnid1 + sRut1;
            sRut2    = sUnid2 + sRut2;
            sRut3    = sUnid3 + sRut3;
            sRutBin  = sUnidBin + sRutBin;
            
            /*Obtiene la fecha y hora del sistema*/
            java.text.DateFormat dateFormat   = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
            java.util.Date da                 = new java.util.Date();            
                    
            /*Concatena el nombre del archivo a las 3 rutas*/
            sRut1    += "\\1-" + dateFormat.format(da) + ".sql";     
            sRut2    += "\\2-" + dateFormat.format(da) + ".sql";     
            sRut3    += "\\3-" + dateFormat.format(da) + ".sql";     
            
            /*Crea la cadena completa que ejecutara la bases de datos de las 3 rutas*/
            String sCadComp1 = sRutBin + "\\mysqldump --user=" + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " > " + sRut1;            
            String sCadComp2 = sRutBin + "\\mysqldump --user=" + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " > " + sRut2;
            String sCadComp3 = sRutBin + "\\mysqldump --user=" + Star.sUsuario + " --password=" + Star.sContrasenia + " " + Star.sBD + " > " + sRut3;

            /*Borra los 3 archivos bat si es que existen*/
            if(new File("resp1.bat").exists())            
                new File("resp1.bat").delete();
            if(new File("resp2.bat").exists())            
                new File("resp2.bat").delete();
            if(new File("resp3.bat").exists())            
                new File("resp3.bat").delete();
            
            /*Crea las 3 rutas nuevamente*/
            File fil = new File("resp1.bat");
            try 
            {
                fil.createNewFile();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            File fil2 = new File("resp2.bat");
            try 
            {
                fil2.createNewFile();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            File fil3 = new File("resp3.bat");
            try 
            {
                fil3.createNewFile();
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            /*Escribe en el la cadena que ejecutara la base de datos por consola para realizar el respaldo en los 3 archivos*/            
            try(FileWriter fw = new FileWriter(fil.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw))
            {                                
                bw.write(sCadComp1);                
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            try(FileWriter fw = new FileWriter(fil2.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw))
            {                                
                bw.write(sCadComp2);            
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            try(FileWriter fw = new FileWriter(fil3.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw))
            {                                
                bw.write(sCadComp3);            
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }
            
            /*Corre el archivo bat 1*/
            try 
            {                             
                /*Crea el objeto para hacer el respaldo*/
                Process p = Runtime.getRuntime().exec("cmd /c start /b resp1.bat");

                /*Intenta esperar el proceso de respaldo por consola*/
                int iE  = -1;
                try
                {
                    iE = p.waitFor();
                }
                catch(InterruptedException expnInterru)
                {                    
                    /*Inserta en log que algo ha ido mal*/
                    try 
                    {                
                        sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                           pathamysq,                                                      estac,                                      sucu,                                     nocaj,                                       msj,                                                     `return`) " + 
                                            "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +           sRut1.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +    expnInterru.getMessage().replace("'", "''") + "', " +    iE + ")";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                        return;                       
                    }    

                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnInterru.getMessage(), Star.sErrInterru);                                                                   
                    return;
                }    

                /*Inserta en log que todo fue bien*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                       pathamysq,                                                      estac,                                          sucu,                                               nocaj,                                              `return`) " + 
                                        "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +       sRut1.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', " +      iE + ")";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                    
                 }    
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;                                
            }
            
            /*Copia todo en la ruta 1*/
            try
            {                
                /*Intenta copiar todo*/
                org.apache.commons.io.FileUtils.copyDirectory(new File(sCarp), new File(sRut1O + "\\Easy Retail® Admin"));

                /*Inserta en log que todo se copio con éxito*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                          estac,                                         sucu,                                            nocaj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut1O.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +  Star.sSucu.replace("'", "''") + "', '" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;

                    /*Agrega en el log*/
                    Login.vLog(expnSQL.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, Star.class.getClass().getName() + " Error en " + sQ + " por " + expnSQL.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
                    return;
                 }    
            }
            catch(IOException ex)
            {
                /*Inserta en log de Error por: el cuál no se pudo copiar*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                          estac,                                          sucu,                                           nocaj,                                              msj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut1O.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     ex.getMessage().replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                           
                }    

                /*Agrega en el log*/
                Login.vLog(ex.getMessage());               

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, Star.class.getClass().getName() + " Error en FileUtils.copyDirectory() por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));                
                return;
            }

            /*Corre el archivo bat 2*/
            try 
            {                             
                /*Crea el objeto para hacer el respaldo*/
                Process p = Runtime.getRuntime().exec("cmd /c start /b resp2.bat");

                /*Intenta esperar el proceso de respaldo por consola*/
                int iE  = -1;
                try
                {
                    iE = p.waitFor();
                }
                catch(InterruptedException expnInterru)
                {
                    /*Inserta en log que algo ha ido mal*/
                    try 
                    {                
                        sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                           pathamysq,                                                  estac,                                      sucu,                                     nocaj,                                        msj,                                                    `return`) " + 
                                            "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +           sRut2.replace("\\", "\\\\").replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnInterru.getMessage().replace("'", "''") + "', " +   iE + ")";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                         //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                        return;                                                
                    }    

                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnInterru.getMessage(), Star.sErrInterru, con);                                                                   
                    return;                        
                }    

                /*Inserta en log que todo fue bien*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                       pathamysq,                                                      estac,                                          sucu,                                               nocaj,                                              `return`) " + 
                                        "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +       sRut2.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', " +      iE + ")";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                            
                 }    
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, con);                                                                   
                return;                                        
            }

            /*Copia todo en la ruta 2*/
            try
            {
                /*Intenta copiar todo*/
                org.apache.commons.io.FileUtils.copyDirectory(new File(sCarp), new File(sRut2O + "\\Easy Retail® Admin"));

                /*Inserta en log que todo se copio con éxito*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                          estac,                                         sucu,                                            nocaj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut2O.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +  Star.sSucu.replace("'", "''") + "', '" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                            
                 }    
            }
            catch(IOException expnIO)
            {
                /*Inserta en log de Error por: el cuál no se pudo copiar*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                          estac,                                        sucu,                                     nocaj,                                        msj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut2O.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "',    '" +  Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnIO.getMessage().replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                            
                }                    

                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, con);                                                                   
                return;                                            
            }
            
            /*Corre el archivo bat 3*/
            try 
            {                             
                /*Crea el objeto para hacer el respaldo*/
                Process p = Runtime.getRuntime().exec("cmd /c start /b resp3.bat");

                /*Intenta esperar el proceso de respaldo por consola*/
                int iE  = -1;
                try
                {
                    iE = p.waitFor();
                }
                catch(InterruptedException expnInterru)
                {
                    /*Inserta en log que algo ha ido mal*/
                    try 
                    {                
                        sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                           pathamysq,                                                  estac,                                      sucu,                                     nocaj,                                        msj,                                                    `return`) " + 
                                            "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +           sRut3.replace("\\", "\\\\").replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', '" +     expnInterru.getMessage().replace("'", "''") + "', " +   iE + ")";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                        return;                                                                    
                    }    

                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnInterru.getMessage(), Star.sErrInterru, con);                                                                   
                    return;                                            
                }    

                /*Inserta en log que todo fue bien*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathdemysq,                                                       pathamysq,                                                      estac,                                          sucu,                                               nocaj,                                              `return`) " + 
                                        "VALUES(1, '" +   sRutBin.replace("\\", "\\\\").replace("'", "''") + "', '" +       sRut3.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', " +      iE + ")";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                    
                 }    
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, con);                                                                   
                return;                                            
            }
            
            /*Copia todo en la ruta 3*/
            try
            {
                /*Intenta copiar todo*/
                org.apache.commons.io.FileUtils.copyDirectory(new File(sCarp), new File(sRut3O + "\\Easy Retail® Admin"));

                /*Inserta en log que todo se copio con éxito*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                              estac,                                          sucu,                                           nocaj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut3O.replace("\\", "\\\\").replace("'", "''") + "', '" +          Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                    
                 }    
            }
            catch(IOException expnIO)
            {
                /*Inserta en log de Error por: el cuál no se pudo copiar*/
                try 
                {                
                    sQ = "INSERT INTO resplog  (tip,      pathde,                                                      patha,                                                          estac,                                      sucu,                                         nocaj,                                        msj) " + 
                                        "VALUES(1, '" +   sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +    sRut3O.replace("\\", "\\\\").replace("'", "''") + "', '" +      Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "', '" +     expnIO.getMessage().replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                    
                }                    

                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, con);                                                                   
                return;                                                                                    
            }
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;

            /*Borra los archivos bat*/            
            new File("resp1.bat").delete();     
            new File("resp2.bat").delete();     
            new File("resp3.bat").delete();     
                    
            /*Duerme el thread los segundos específicados*/
            try
            {
                Thread.sleep(Integer.parseInt(sHrs) * 60000);                
            }
            catch(NumberFormatException | InterruptedException expnNumForm)
            {
                /*Sal del búcle infinito*/
                break;
            }
            
        }/*Fin de while(true)*/                    
        
    }/*Fin de public static void vRespUsr()*/

    
    /*Manda los corrs de cumpleaños a todas las emps que están pendientes en la tabla de cumpleaños*/
    public static void vMandCump()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;
        
        /*Declara variables del corr*/
        String      sServSMTPSal        = "";
        String      sSMTPPort           = "";
        String      sUsr                = "";
        String      sContra             = "";
        String      sActSSL             = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        ResultSet   rs2;        
        String      sQ; 
        String      sQ2; 
        
        /*Obtiene los datos del corr electrónico de esta usuario*/
        try
        {
            sQ = "SELECT servsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal            = rs.getString("servsmtpsal");
                sSMTPPort               = rs.getString("portsmtp");
                sUsr                    = rs.getString("usr");
                sContra                 = rs.getString("pass");
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";

                /*Desencripta la contraseña*/
                sContra                = Star.sDecryp(sContra);                                        
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                
        }            
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Obtiene el mensaje y el asunto que debe tener el corr de cumpleaños*/
        String sMens    = "";
        String sAsun    = ""; 
        try
        {
            sQ = "SELECT extr, asun FROM confgral WHERE clasif = 'sist' AND conf = 'cumple'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene los resultados*/                
                sMens          = rs.getString("extr");                                                
                sAsun          = rs.getString("asun");                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }  
        
        /*Si la carpeta de la aplicación compartida en el servidor esta definida entonces*/        
        boolean bSi  = false;
        if(sCarp.compareTo("")!=0)
        {
            /*Si la carpeta de las imágenes no existe entonces crea el directorio*/
            sCarp                    += "\\Imagenes";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si la carpeta de cumpleaños no existe entonces crea el directorio*/
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
                /*Si contiene archivos entonces*/
                if(new File(sCarp).list().length > 0)
                {
                    /*Obtiene la lista del archivo y completa la ruta a la imágen*/
                    String sArch [] = new File(sCarp).list();
                    sCarp           = sCarp + "\\" + sArch[0];

                    /*Coloca la bandera para saber que si hay imágen*/
                    bSi = true;
                }
            }                

        }/*Fin de if(sCarp.compareTo()==0)*/
        
        /*Declara algunas variables como final*/
        final String sUsrFi     = sUsr;        
        final String sContraFi  = sContra;
        
        /*Obtiene todos los registros de la tabla de cumpleaños que no han sido enviados para este año*/        
        try
        {
            sQ = "SELECT * FROM cumple WHERE anio = YEAR(CURDATE())";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                
                /*Obtiene el nom de la empresa en base a su código*/
                String sNomEmp  = "";
                try
                {
                    sQ2= "SELECT nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + rs.getString("codemp") + "'";
                    st = con.createStatement();
                    rs2= st.executeQuery(sQ2);
                    /*Si hay datos entonces obtiene el nombre de la empresa*/
                    if(rs2.next())
                        sNomEmp      = rs2.getString("nom");                                   
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                                                    
                }

                /*Manda el correo*/
                try
                {
                    //Define las propiedades de conexión
                    Properties props = System.getProperties();
                    props.setProperty("mail.smtp.host", sServSMTPSal);
                    props.put("mail.smtp.starttls.enable", sActSSL);
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.debug", "true");
                    props.put("mail.smtp.port", sSMTPPort);
                    props.put("mail.store.protocol", "pop3");
                    props.put("mail.transport.protocol", "smtp");
                    final String username = sUsrFi;
                    final String password = sContraFi;
                    Session session = Session.getDefaultInstance(props,
                            new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });

                    /*Si hay imágen entonces el mensajea va a ser con imágen*/
                    Multipart multipart = null;
                    if(bSi)
                    {
                        /*Carga la imágen*/
                        multipart               = new MimeMultipart("related");
                        BodyPart htmlPart       = new MimeBodyPart();                                       
                        htmlPart.setContent("<html><body>" +
                                            "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNomEmp + "</body></html>", "text/html");                    
                        /*Continua creando la imágen*/
                        multipart.addBodyPart(htmlPart);
                        BodyPart imgPart        = new MimeBodyPart();
                        DataSource ds           = new FileDataSource(sCarp);
                        imgPart.setDataHandler(new DataHandler(ds));
                        imgPart.setHeader("Content-ID","the-img-1");
                        multipart.addBodyPart(imgPart);
                    }                                        

                    /*Crea el contenido del mensaje*/
                    MimeMessage  msj            = new MimeMessage(session);
                    msj.setFrom(new InternetAddress(sUsrFi));
                    msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rs.getString("a")));
                    msj.setSubject(sAsun);
                    
                    /*Si es con imágen entonces*/
                    if(bSi)
                    {
                        /*Adjunta el mensaje multipart*/
                        msj.setContent(multipart);
                    }
                    /*Else sin imágen entonces*/
                    else
                    {
                        /*Inserta el mensaje en el objeto*/                        
                        msj.setContent("<html>" + sMens + " " + sNomEmp + "<body></body></html>", "text/html; charset=utf-8");
                    }                                        
                                        
                    /*Manda el correo*/
                    Transport.send(msj);
                }
                catch(MessagingException expnMessag)
                {                        
                    /*Ingresa en la base de datos el registor de que se fallo*/
                    try 
                    {                    
                        sQ2 = "INSERT INTO logcorrs(corr,                                      nodoc,       estad,       motiv,                                                 estac,                                  falt,       corrde,                         tipdoc,              sucu,                                    nocaj) " + 
                                        "VALUES('" + rs.getString("a").replace("'", "''") + "','',          'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'CUMPLEAÑOS','" +    Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ2);                           
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y continua
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                        continue;
                    }

                    /*Actualiza el registro en la tabla para que se mande el próximo año*/
                    try 
                    {                    
                        sQ2 = "UPDATE cumple SET "
                                + "anio             = YEAR(CURDATE()) + 1 "
                                + "WHERE id_cumple  = " + rs.getString("id_cumple");                    
                        st = con.createStatement();
                        st.executeUpdate(sQ2);                           
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                        return;                                                                                                            
                    }
                    
                    /*Mandalo al correo alternativo y continua*/
                    Star.vMandAlter(rs.getString("id_cumple"), null, rs.getString("a"), null, rs.getString("codemp"), 3, expnMessag.getMessage(), sNomEmp);                                                           
                    continue;
                    
                }/*Fin de catch(MessagingException expnMessag)*/
                
                /*Actualiza el registro en la tabla para que se mande el próximo año*/
                try 
                {                    
                    sQ2 = "UPDATE cumple SET "
                            + "anio = YEAR(CURDATE()) + 1 "
                            + "WHERE id_cumple = " + rs.getString("id_cumple");                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                                        
                }
                
                /*Ingresa en la base de datos el registro de que se mando con éxito*/
                try 
                {          
                    sQ2= "INSERT INTO logcorrs (corr,                                       nodoc,          estad,   motiv,  estac,                                   falt,        corrde,                          tipdoc,          sucu,                                          nocaj) " + 
                                   "VALUES('" + rs.getString("a").replace("'", "''") + "',  '',            'ENVIADO','','" + Login.sUsrG.replace("'", "''") + "', now(), '" +  sUsr.replace("'", "''") + "',    'CUMPLEAÑOS','" + Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);
                    
                    sQ2= "INSERT INTO estadiscor(corr,                                       nodoc,          estad,   motiv,  estac,                                   falt,        corrde,                          tipdoc,          sucu,                                          nocaj) " + 
                                   "VALUES('" + rs.getString("a").replace("'", "''") + "',  '',            'ENVIADO','','" + Login.sUsrG.replace("'", "''") + "', now(), '" +  sUsr.replace("'", "''") + "',    'CUMPLEAÑOS','" + Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                                                                                                        
                }                        

            }/*Fin de while(rs.next())*/
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                      
        }
        
    }/*Fin de public static void vMandCump()*/

    
    /*Inserta en partvta el producto*/
    public static int iInsertFa(Connection con, String sVta, String sUnid, String sProd,  String sCant, String sDescrip, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sTipDoc, String sId, String sCodImpue)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        


        
        /*Obtiene el costo dependiendo el método de costeo de la empresa*/
        String sCostMe  = Star.sGetCost(con, sProd, sCant);

        /*Si hubo error entonces regresa error*/
        if(sCostMe==null)
            return -1;

        /*Inserta en la base de datos la partida de la factura*/
        try 
        {            
            sQ = "INSERT INTO partvta(    vta,                   prod,                                 cant,            unid,                   descrip,                         pre,          descu,     impue,       mon,    impo,  falt,        eskit,       tipdoc,         list,     alma,                              tall,           colo,                  kitmae,      tipcam,         serprod,        comenser,  costprom,   peps,   ueps,   cost,            idkit,   idueps,   idpeps,      codimpue) " + 
                              "VALUES(" + sVta + ",'"  +         sProd.replace("'", "''") + "','" +    sCant + "','" +  sUnid + "','" +         sDescrip.replace("'", "''") + "',0,            0,         0,           '',     0,     now(),       0,      '" + sTipDoc + "',   1, '" +   sAlma.replace("'", "''") + "',     '',             '',                    1,       " + sTipCam + ",    '',             '',        0,          0,      0, " +  sCostMe + ", " + sId + ", '',       '',     '" + sCodImpue + "')";                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
    
        /*Obtiene la cantidad correcta dependiendo de su unidad*/
        String sCantO              = Star.sCantUnid(sUnid, sCant);
                            
        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma, sCantO, "-")==-1)
            return -1;

        /*Si hubo error entonces regresa error*/
        if(Star.bErr)        
            return -1;        

        /*Actualiza la existencia general del producto*/
        if(Star.iCalcGralExis(con, sProd)==-1)
            return -1;

        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, sProd.replace("'", "''"), sCantO, sDescrip.replace("'", "''"), sAlma.replace("'", "''"), Login.sUsrG , sDoc.replace("'", "''"), "COMP ORD", sConcep, sNoSer.replace("'", "''"), sEmp.replace("'", "''"), sES))                                
            return -1;                                                                                                                                                                                                             
            
        /*Regresa que todo va bien*/
        return 0;
        
    }/*Fin de public static int InsertFa(Connection con, String sVta, String sUnid, String sProd,  String sCant, String sDescrip, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sTipDoc)*/

    
    /*Inserta en partcomprs el producto*/
    public static int iInsertCom(Connection con, String sCom, String sUnid, String sProd,  String sCant, String sDescrip, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sTipDoc, String sId)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        


        /*Obtiene el costo promedio del producto*/
        String sCostP           = sCostProm(con, sProd);
        
        /*Si hubo algún error entonces regresa error*/
        if(sCostP==null)
            return -1;
        
        /*Inserta en la base de datos la partida de la compra*/
        try
        {
            sQ = "INSERT INTO partcomprs(codcom,        prod,               cant,               unid,           descrip,      cost,     descu,  descad, codimpue,   mon,    impo,   falt,      recib,               costpro,                                      lot,     pedimen,    flotvenc,   cantlotpend,    serprod,    comenser,       garan,          tipcam,      eskit,     kitmae,         idkit,         alma) " +
                            "VALUES('" + sCom + "','"  +sProd + "','" +     sCant + "','" +     sUnid + "','" + sDescrip + "',0,        0,      0,      '',         '',     0,      now(), " + sCant + ",    " +    sCostP.replace("$", "").replace(",", "") + ", '',      '',         now(),      0,              '',         '',             '',         " + sTipCam + ", 0,         1,          " + sId + ", '" + sAlma + "')";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
            
        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma, sCant, "+")==-1)
            return -1;

        /*Actualiza la existencia general del producto*/
        if(Star.iCalcGralExis(con, sProd)==-1)
            return -1;

        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, sProd.replace("'", "''"), sCant, sDescrip.replace("'", "''"), sAlma.replace("'", "''"), Login.sUsrG , sCom.replace("'", "''"), sConcep, sUnid.replace("'", "''"), sNoSer.replace("'", "''"), sEmp.replace("'", "''"), sES))                                
            return -1;                                                                                                                                                                                                             
        
        /*Regresa que todo va bien*/
        return 0;
        
    }/*Fin de public static int InsertCom(Connection con, String sVta, String sUnid, String sProd,  String sCant, String sDescrip, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sTipDoc)*/
    
    
    /*Inserta en partvta los componentes de todo el kit*/                
    public static int iInsFacComp(Connection con, String sProd, String sVta, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sId, String sCant, String sUnid, String sCodImpue)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ; 



        
        /*Obtiene todos los componentes y sus datos del kit en base a su código y almacén*/        
	try
        {                  
            sQ = "SELECT prods.UNID, prods.PROD, kits.CANT, prods.DESCRIP FROM kits LEFT JOIN prods ON kits.PROD = prods.PROD WHERE kits.CODKIT = '" + sProd + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*La cantidad a manejar originalmente será la que ingreso el usuario*/
                String sCantO        = Double.toString(Double.parseDouble(rs.getString("cant")) * Double.parseDouble(sCant));
                                    
                /*Inserta las partidas del kit*/
                if(iInsertFa(con, sVta, rs.getString("prods.UNID"), rs.getString("prods.PROD"), sCantO, rs.getString("prods.DESCRIP"), sAlma, sConcep, sDoc, sEmp, sES, sNoSer, sTipCam, sConcep, sId, sCodImpue)==-1)
                    return -1;                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
           
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin public static int iInsFacComp(Connection con, String sProd, String sVta, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sId, String sCant, String sUnid, String sCodImpue)*/
    
    
    /*Inserta en partcomprs los componentes de todo el kit*/                
    public static int iInsCompKit(Connection con, String sProd, String sVta, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sId, String sCant, String sUnid)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ          ; 



        
        /*Obtiene todos los componentes y sus datos del kit en base a su código y almacén*/        
	try
        {                  
            sQ = "SELECT prods.UNID, prods.PROD, kits.CANT, prods.DESCRIP FROM kits LEFT JOIN prods ON kits.PROD = prods.PROD WHERE kits.CODKIT = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {                
                /*La cantidad a manejar originalmente será la que ingreso el usuario*/
                String sCantO        = Double.toString(Double.parseDouble(rs.getString("cant")) * Double.parseDouble(sCant));

                /*Si la cantidad que se quiere manejar es gramo, kilo o tonelada entonces*/
                if(sUnid.compareTo("KILO")==0 || sUnid.compareTo("GRAMO")==0 || sUnid.compareTo("TONELADA")==0)
                    sCantO          = Star.sCantUnid(sUnid, sCantO);
                
                /*Inserta las partidas del kit*/
                if(iInsertCom(con, sVta, rs.getString("prods.UNID"), rs.getString("prods.PROD"), sCantO, rs.getString("prods.DESCRIP"), sAlma, sConcep, sDoc, sEmp, sES, sNoSer, sTipCam, sConcep, sId)==-1)
                    return -1;                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
           
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iInsCompKit(Connection con, String sProd, String sVta, String sAlma, String sConcep, String sDoc, String sEmp, String sES, String sNoSer, String sTipCam, String sId)*/
    

    /*Función para procesar cada componente de un kit cuando se esta insertando*/    
    private static int iProcCom(Connection con, String sProd, String sCant, String sDescrip, String sAlma, String sUnid, String sConcep, String sCost)
    {                        
        /*Registra el producto que se esta aumentando al inventario en la tabla de monitor de inventarios y devuelve error*/
        if(!Star.vRegMoniInv(con, sProd, sCant, sDescrip, sAlma, Login.sUsrG , "", sConcep, sUnid, "", "", "0"))                                
            return -1;      

        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma, sCant, "+")==-1)
            return -1;
                
        /*Actualiza la existencia general del producto*/
        if(Star.iCalcGralExis(con, sProd)==-1)
            return -1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ; 
        
        /*Obtiene el último ID de moniven*/
        String sId  = "";
        try
        {
            sQ = "SELECT id_id FROM moninven ORDER BY id_id DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sId      = rs.getString("id_id");                                               
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Inserta la compra en la base de datos que sirve para ingresar el costeo*/
        try
        {
            sQ = "INSERT INTO comprs (  codcomp,                noser, prov,       ser,         nodoc,              estac,                                  fdoc,       subtot,     impue,      tot,    estado, falt,       motiv,  nomprov,    fvenc,          sucu,                                     nocaj,                                 tip,       fent) " +
                             "VALUES(   'II" + sId + "',        '',    ''      ,   '',     '" + sId + "',     '" +  Login.sUsrG.replace("'", "''") + "',    now(),      0,          0,          0,      'CO',   now(),      '',     '',         now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',   'INV',     now())";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Obtiene algunos datos del producto para el costo promedio*/
        String sExistG              = "";
        String sCostU               = "";
        try
        {
            sQ = "SELECT (SELECT IFNULL(cost,0) FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND (partcomprs.CANT - partcomprs.DEVS) > 0 AND prod = '" + sProd + "' ORDER BY partcomprs.ID_ID DESC LIMIT 1) AS ultcost, SUM(exist) AS exist FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene el último costo*/
                sCostU  = rs.getString("ultcost");

                /*Si el último costo es cadena vacia entonces*/
                if(sCostU==null || sCostU.compareTo("")==0)
                    sCostU  = "0";

                /*Obtiene la existencia general*/
                sExistG = rs.getString("exist");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Si el último costo es 0 entonces dejarlo como el costo actual*/
        String sCostP;
        if(Double.parseDouble(sCostU)==0)
            sCostP      = sCost;
        else
            sCostP      = Double.toString(((Double.parseDouble(sExistG) * Double.parseDouble(sCostU)) + (Double.parseDouble(sCant) * Double.parseDouble(sCost))) / (Double.parseDouble(sExistG) + Double.parseDouble(sCant)));

        /*Inserta las partidas de las compras*/
        try
        {
            sQ = "INSERT INTO partcomprs(codcom,                  prod,             cant,           unid,           descrip,            cost,       descu,    descad,     codimpue,   mon,    impo,    falt,          recib,            alma,            costpro,                                      lot,       pedimen,    flotvenc,   cantlotpend,    serprod,    comenser,   garan,  tipcam,     eskit) " +
                            "VALUES(     'II" + sId + "','"  +    sProd + "'," +    sCant + ",'" +  sUnid + "','" + sDescrip + "','" +  sCost + "', 0,        0,          '',         '',     0,       now(),     " + sCant + ", '" +   sAlma + "', " +  sCostP.replace("$", "").replace(",", "") + ", '',        '',         now(),      0,              '',         '',         '',     0,          0)";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private static int iProcCom(Connection con, String sProd, String sCant, String sDescrip, String sAlma, String sUnid, String sConcep, String sCost)*/
    
    
    /*Función para procesar cada componente de un kit para el traspaso entre almacenes*/    
    private static int iTrasCom(Connection con, String sProd, String sCant, String sDescrip, String sAlma, String sUnid, String sConcep, String sAlma2)
    {                        
        /*Registra el producto que se esta sacando al inventario del almacén en la tabla de monitor de inventarios y devuelve error*/
        if(!Star.vRegMoniInv(con, sProd, sCant, sDescrip, sAlma, Login.sUsrG , "", sConcep, sUnid, "", "", "1"))                                
            return -1;      

        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma, sCant, "-")==-1)
            return -1;
                
        /*Registra el producto que se esta ingresando al inventario del almacén en la tabla de monitor de inventarios y devuelve error*/
        if(!Star.vRegMoniInv(con, sProd, sCant, sDescrip, sAlma2, Login.sUsrG , "", sConcep, sUnid, "", "", "0"))                                
            return -1;      

        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma2, sCant, "+")==-1)
            return -1;
        
        /*Actualiza la existencia general del producto*/
        if(Star.iCalcGralExis(con, sProd)==-1)
            return -1;

        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        /*Registra el traspaso entre los almacenes*/
        try 
        {            
            sQ = "INSERT INTO traspas(      prod,              alma,                    concep,                 cantsal,            almaa,              cantent,           estac,                                   falt,           sucu,                                     nocaj) " + 
                            "VALUES('" +    sProd + "','" +    sAlma.trim() + "','" +   sConcep + "', " +       sCant + ",'" +      sAlma2 + "', " +    sCant + ", '" +    Login.sUsrG.replace("'", "''") + "',     now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
         }

        /*Regresa que todo fue bien*/
        return 0;        
    }
    
    
    /*Función para procesar cada componente de un kit cuando se esta sacando por salida manual*/    
    private static int iProcComS(Connection con, String sProd, String sCant, String sDescrip, String sAlma, String sUnid, String sConcep, String sCost)
    {                        
        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, sProd, sCant, sDescrip, sAlma, Login.sUsrG , "", sConcep, sUnid, "", "", "1"))                                
            return -1;      

        /*Realiza la afectación correspondiente al almacén*/
        if(Star.iAfecExisProd(con, sProd, sAlma, sCant, "-")==-1)
            return -1;
                
        /*Actualiza la existencia general del producto*/
        if(Star.iCalcGralExis(con, sProd)==-1)
            return -1;

        /*Obtiene el costo dependiendo el método de costeo de la empresa*/
        String sCostMe  = Star.sGetCost(con, sProd, sCant);

        /*Si hubo error entonces regresa*/
        if(sCostMe==null)
            return -1;

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private static int iProcComS(Connection con, String sProd, String sCant, String sDescrip, String sAlma, String sUnid, String sConcep, String sCost)*/
    
    
    /*Inserta o retira de moninven, actualiza las existencias de los productos para los componentes de todo el kit y costeos*/                
    public static int iInsCompKitInv(Connection con, String sProd, String sAlma, String sConcep, String sCost, String sTip, String sAlma2, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ; 



        
        /*Obtiene todos los componentes y sus datos del kit en base a su código*/        
	try
        {                  
            sQ = "SELECT prods.UNID, prods.PROD, kits.CANT, prods.DESCRIP FROM kits LEFT JOIN prods ON kits.PROD = prods.PROD WHERE kits.CODKIT = '" + sProd.trim() + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Si la cantidad que se quiere manejar es gramo, kilo o tonelada entonces*/
                if(rs.getString("unid").compareTo("KILO")==0 || rs.getString("unid").compareTo("GRAMO")==0 || rs.getString("unid").compareTo("TONELADA")==0)
                    sCant       = Star.sCantUnid(rs.getString("unid"), Double.toString(Double.parseDouble(sCant) * Double.parseDouble(rs.getString("cant"))));
                
                /*Si el tipo es de entrada entonces procesa todos los componentes aquí*/
                if(sTip.compareTo("0")==0)
                {
                    if(Star.iProcCom(con, rs.getString("prod"), sCant, rs.getString("descrip"), sAlma, rs.getString("unid"), sConcep, sCost)==-1)
                        return -1;
                }   
                /*Else es de salida entonces procesa aca*/
                else if(sTip.compareTo("1")==0)
                {
                    if(Star.iProcComS(con, rs.getString("prod"), sCant, rs.getString("descrip"), sAlma, rs.getString("unid"), sConcep, sCost)==-1)
                        return -1;
                }
                /*Else es de traspaso entonces*/
                else if(sTip.compareTo("traspas")==0)
                {                    
                    if(Star.iTrasCom(con, rs.getString("prod"), sCant, rs.getString("descrip"), sAlma, rs.getString("unid"), sConcep, sAlma2)==-1)
                        return -1;
                }
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
           
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iInsCompKitInv(Connection con, String sProd, String sAlma, String sConcep)*/
    
    
    /*Agrega todos los datos de la base de datos a la tabla de compras*/
    public static synchronized void vCargComp(javax.swing.JTable jTab)
    {
        /*Resetea el contador de filas*/
        Star.iContFiComp = 1;
        
        /*Borra todos los item en la tabla de comprs*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();
        tm.setRowCount(0);
                      
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs ;        
        String      sQ;
        
        /*Comprueba si se tiene habilitado que solo se puedan mostrar las compras del usuario o todas*/
        String sCond    = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'compsxusr'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si esta activado entonces coloca la consulta*/
                if(rs.getString("val").compareTo("1")==0)                                  
                    sCond   = " WHERE comprs.ESTAC= '" + Login.sUsrG + "'";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }                            
        
        /*Trae todas las compras de la base de datos y cargalas en la tabla*/
        try
        {
            sQ  = "SELECT comprs.MON, comprs.NOTCRED, comprs.NOTCREDPAG, comprs.NOMPROV, comprs.CODORD, comprs.RECIB, comprs.FENT, comprs.TIP, estacs.NOM, comprs.SUCU, comprs.NOSER, comprs.PROV, comprs.NOCAJ, comprs.CODCOMP, comprs.NODOC, comprs.TOT, comprs.FALT, comprs.FDOC,  comprs.ESTADO, comprs.ESTAC, comprs.FMOD, comprs.MOTIV FROM comprs LEFT OUTER JOIN estacs ON estacs.ESTAC = comprs.ESTAC  " + sCond + " WHERE tip <> 'INV' ORDER BY comprs.ID_ID DESC";        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                 
                /*Obtiene el total*/
                String sTot            = rs.getString("comprs.tot");
                
                /*Dale formato de moneda al tot*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTot            = n.format(dCant);
                
                /*Agregalo a la tabla*/
                Object nu[]= {Star.iContFiComp, rs.getString("comprs.TIP"), rs.getString("comprs.CODCOMP"), rs.getString("comprs.NOSER"), rs.getString("comprs.NODOC"), rs.getString("comprs.PROV"), rs.getString("nomprov"), sTot, rs.getString("comprs.MON"), rs.getString("comprs.FALT"), rs.getString("comprs.FDOC"), rs.getString("comprs.FENT"), rs.getString("comprs.FMOD"), rs.getString("comprs.ESTADO"), rs.getString("comprs.MOTIV"), rs.getString("comprs.RECIB"), rs.getString("comprs.CODORD"), rs.getString("NOTCRED"), rs.getString("NOTCREDPAG").replace("|", " "), rs.getString("comprs.SUCU"), rs.getString("comprs.NOCAJ"), rs.getString("comprs.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las comprs*/
                ++Star.iContFiComp;                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }            
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, con);                                                                   
            return;                                                                                    
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);               
        
    }/*Fin de public static synchronized void vCargComp(javax.swing.JTable jTab)*/

    
    /*Agrega todos los datos de la base de datos a la tabla de vtas*/
    public static synchronized void vCargVtas(String sCond, javax.swing.JLabel jLNo, javax.swing.JTable jTab, javax.swing.JLabel jLTotVtas)
    {
        /*Resetea el texto rojo de arriba que indica el tipo de documento que es*/
        jLNo.setText("");                        
        
        /*Borra todos los item en la tabla de facturas*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        te.setRowCount(0);
        
        /*Resetea el contador de filas*/
        Star.iContFiVent = 1;
                      
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs ;        
        String      sQ;
        
        /*Comprueba si se tiene habilitado que solo se puedan mostrar las ventas del usuario o todas*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vtasxusr'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la bandera en caso de que este activado*/
                if(rs.getString("val").compareTo("1")==0)                                  
                    bSi = true;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }
        
        //Contador del total de las ventas
        double  dCont    = 0;
        
        /*Si se tiene habilitado que solo se muestren las ventas del usuario entonces la consulta será otra*/
        if(bSi)
            sQ = "SELECT autrecibde, autmarc, autmod, autcolo, autplacs, autnom, auttarcirc, autnumlic, auttel, autdirpart, autdirofi, auttelofi, vtas.FOLFISC, vtas.CODEMP, vtas.NOTCREDPAG, vtas.VTADEVP, vtas.CODCOT, vtas.TOTCOST, CASE WHEN vtas.TIMBR = 1 THEN 'Si' ELSE 'No' END timbr, vtas.TOTDESCU, estacs.NOM, vtas.FVENC, vtas.NOTCRED, vtas.TIPDOC, vtas.NOSER, vtas.SUCU, vtas.NOCAJ, vtas.NOREFER, vtas.TIC, vtas.NOREFER, vtas.VTA, vtas.TOT, vtas.FALT, vtas.FEMI,  vtas.ESTAD, vtas.ESTAC, vtas.FMOD, vtas.MOTIV, vtas.OBSERV FROM vtas LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC WHERE vtas.ESTAC = '" + Login.sUsrG + "' " + sCond + " ORDER BY vtas.VTA DESC";
        else
            sQ = "SELECT autrecibde, autmarc, autmod, autcolo, autplacs, autnom, auttarcirc, autnumlic, auttel, autdirpart, autdirofi, auttelofi, vtas.FOLFISC, vtas.CODEMP, vtas.NOTCREDPAG, vtas.VTADEVP, vtas.CODCOT, vtas.TOTCOST, CASE WHEN vtas.TIMBR = 1 THEN 'Si' ELSE 'No' END timbr, estacs.NOM, vtas.TOTDESCU, vtas.FVENC, vtas.NOTCRED, vtas.TIPDOC, vtas.NOSER, vtas.SUCU, vtas.NOCAJ, vtas.NOREFER, vtas.TIC, vtas.NOREFER, vtas.VTA, vtas.TOT, vtas.FALT, vtas.FEMI,  vtas.ESTAD, vtas.ESTAC, vtas.FMOD, vtas.MOTIV, vtas.OBSERV FROM vtas LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC WHERE 1=1 " + sCond + " ORDER BY vtas.VTA DESC";
                
        /*Trae todas las vtas de la base de datos y cargalas en la tabla*/
        try
        {            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                 
                /*Obtiene los totales*/
                String sTot     = rs.getString("vtas.TOT");
                String sTotDesc = rs.getString("totdescu");
                String sTotCost = rs.getString("totcost");                                
                
                //Ve sumando el total de las ventas
                dCont           = dCont + rs.getDouble("vtas.TOT");
                        
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sTot);                                
                sTot            = n.format(dCant);
                dCant           = Double.parseDouble(sTotDesc);                                
                sTotDesc        = n.format(dCant);
                dCant           = Double.parseDouble(sTotCost);                                
                sTotCost        = n.format(dCant);                
                
                /*Agregalos a la tabla*/
                Object nu[]     = {Star.iContFiVent, rs.getString("vtas.VTA"), rs.getString("vtas.NOREFER"), rs.getString("vtas.NOSER"),rs.getString("codemp"), sTot, sTotDesc, sTotCost, rs.getString("vtas.FALT"), rs.getString("vtas.FEMI"), rs.getString("vtas.FMOD"), rs.getString("vtas.FVENC"), rs.getString("vtas.NOTCRED"), rs.getString("vtas.NOTCREDPAG").replace("|", " "), rs.getString("vtas.ESTAD"), rs.getString("vtas.ESTAC"), rs.getString("vtas.MOTIV"), rs.getString("vtas.TIPDOC"), rs.getString("observ"), rs.getString("vtas.SUCU"), rs.getString("vtas.NOCAJ"), rs.getString("estacs.NOM"), rs.getString("timbr"), rs.getString("codcot"), rs.getString("vtadevp"), "", rs.getString("folfisc")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las compras*/
                ++Star.iContFiVent;                            
            }                        
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }            
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, con);                                                                   
            return;                                                                                    
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);                  

        //Dale formato de moneda al total de ventas
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String sTotVtas = n.format(dCont);
        
        //Coloca el total de ventas y el contador igual
        if(jLTotVtas!=null)
            jLTotVtas.setText("# Ventas y total: " + Star.iContFiVent + " - " + sTotVtas);
        
    }/*Fin de public static synchronized void vCargVtas(String sCond, javax.swing.JLabel jLNo, javax.swing.JTable jTab, javax.swing.JLabel jLTotVtas)*/

    
    /*Recalcula los importes de toda la tabla*/
    public static void vRecalcImports(JTable jTable, JTextField jTextFieldTotal, JTextField jTextFieldSubTotal, JTextField jTextFieldIVA)
    {
        /*Declara varaibles locales*/
        String sTot;
        String sIVA;
        String sSubTot    = "0";
        String sImp;
        
        
        
        /*Recorre toda la tabla de carátula*/
        for(int x = 0; x < jTable.getRowCount(); x++)
        {
            /*Obtiene el impo de la fila*/
            sImp        = jTable.getModel().getValueAt(x, 7).toString();
            
            /*Si el impo tiene el signo de dollar quitaselo*/
            sImp        = sImp.replace("$", "").replace(",", "");                       
            
            /*Suma al subtot el impo y déjalo como cadena*/
            sSubTot     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImp));            
        }
        
        /*Obtén el iva de el subtot*/
        sIVA            = Double.toString((Double.parseDouble(sSubTot) * 1.16) - Double.parseDouble(sSubTot));
        
        /*Genera el tot*/
        sTot            = Double.toString(Double.parseDouble(sIVA) + Double.parseDouble(sSubTot));
        
        /*Dale formato de mon al subtot*/    
        double dCant    = Double.parseDouble(sSubTot);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sSubTot         = n.format(dCant);
        
        /*Dale formato de mon al IVA*/
        dCant           = Double.parseDouble(sIVA);                
        n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sIVA            = n.format(dCant);
        
        /*Dale formato de mon al tot*/
        dCant           = Double.parseDouble(sTot);                
        n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot            = n.format(dCant);
        
        /*Coloca los tres importes en sus controles correspondientes*/
        jTextFieldSubTotal.setText  (sSubTot);
        jTextFieldIVA.setText       (sIVA);
        jTextFieldTotal.setText     (sTot);
        
    }/*Fin de void vRecalcImports()*/

    
    /*Función para recalcular los totes*/
    public static void vRecalcTot(JTable jTablParts,  JTextField jTexSubTot, JTextField jTexImpue, JTextField jTexTot, JTextField jTexDesc)
    {
        /*Inicialmente comienzan los totales en 0*/
        String sSubTot                 = "0";
        String sImpue                  = "0";        
        String sTot                    = "0";
        String sTotDesc                = "0";
        
        
        
        /*Si la tabla no tiene elementos entonces*/
        if(jTablParts.getRowCount()== 0 )
        {
            /*Colocar en los campos de totes $0.00*/
            jTexSubTot.setText  ("$0.00");
            jTexImpue.setText   ("$0.00");
            jTexTot.setText     ("$0.00");
            jTexDesc.setText    ("$0.00");
        }
        /*Recorre toda la tabla para hacer los cálculos*/
        else
        {
            /*Recalcula los importes de todas las partidas de la tabla*/
            for( int row = 0; row < jTablParts.getRowCount(); row++)
            {                                    
                /*Lee el importe de la fila y quitale el formato de moneda*/
                String sImpo            = jTablParts.getValueAt(row, 10).toString().replace("$", "").replace(",", "");
                
                /*Lee el impuesto del importe*/
                String sImpueInt        = jTablParts.getValueAt(row, 11).toString().replace("$", "").replace(",", "");
                
                /*Lee el importe del descuento*/
                String sImpoDesc        = jTablParts.getValueAt(row, 12).toString().replace("$", "").replace(",", "");

                /*Suma el importe de la fila al subtotal*/
                sSubTot                 = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpo));
                
                /*Suma el importe del descuento al global*/
                sTotDesc                = Double.toString(Double.parseDouble(sTotDesc) + Double.parseDouble(sImpoDesc));
                
                /*Suma el importe del impuesto al global*/
                sImpue                  = Double.toString(Double.parseDouble(sImpue) + Double.parseDouble(sImpueInt));
            }

            /*Crea el total en base al subtotal + el impuesto*/
            sTot                        = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue));
                            
        }/*Fin de else*/
        
        /*Dale formato de moneda a los totales*/
        double dCant                = Double.parseDouble(sSubTot);
        NumberFormat n              = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sSubTot                     = n.format(dCant);
        dCant                       = Double.parseDouble(sImpue);            
        sImpue                      = n.format(dCant);
        dCant                       = Double.parseDouble(sTot);            
        sTot                        = n.format(dCant);
        dCant                       = Double.parseDouble(sTotDesc);            
        sTotDesc                    = n.format(dCant);
        
        /*Coloca los totes en sus controles correspondientes*/
        jTexSubTot.setText          (sSubTot);
        jTexImpue.setText           (sImpue);
        jTexDesc.setText             (sTotDesc);
        jTexTot.setText             (sTot);        
        
    }/*Fin de public static void vRecalcTot(JTable jTablParts,  JTextField jTexSubTot, JTextField jTexImpue, JTextField jTexTot, JTextField jTexDesc)*/

    
    /*Función para determinar si existe un cliente*/
    public static void vExisCli(String sCli, javax.swing.JTextField jTDescrip)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                
        
        /*Comprueba si existe el cliente*/
        try
        {
            sQ = "SELECT nom FROM emps WHERE CONCAT_WS('',ser,codemp) = '" +  sCli.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                /*Si el campo de descripción no es nulo entonces coloca el nombre del cliente*/
                if(jTDescrip!=null)
                    jTDescrip.setText(rs.getString("nom"));
            }
            /*Else no existe entonces*/
            else
            {
                /*Si el campo de descripción no es nulo entonces coloca cadena vacia*/
                if(jTDescrip!=null)
                    jTDescrip.setText("");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }            

        //Cierra la base de datos
        Star.iCierrBas(con);                
        
    }/*Fin de public static void vExisCli(String sCli, javax.swing.JTextField jTDescrip)*/
    
    
    /*Función para determinar si existe un producto*/
    public static void vExisProd(String sProd, javax.swing.JTextField jTDescrip)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                
        
        /*Comprueba si existe el producto*/
        try
        {
            sQ = "SELECT descrip FROM prods WHERE prod = '" +  sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                /*Si el campo de descripción no es nulo entonces coloca la descripción del producto*/
                if(jTDescrip!=null)
                    jTDescrip.setText(rs.getString("descrip"));
            }
            /*Else no existe entonces*/
            else
            {
                /*Si el campo de descripción no es nulo entonces coloca cadena vacia*/
                if(jTDescrip!=null)
                    jTDescrip.setText("");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }            

        //Cierra la base de datos
        Star.iCierrBas(con);                
        
    }/*Fin de public static void vExisProd(String sProd, javax.swing.JTextField jTDescrip)*/
            
    
    /*Función para convertir una cadena de bytes en hash*/
    public static String toSHA1(byte[] convertme) 
    {
        /*Intenta hacer el hash*/
        java.security.MessageDigest md = null;
        try 
        {
            md = java.security.MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException expnNoSuchAlgo) 
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnNoSuchAlgo.getMessage(), Star.sErrNoSuchAlgo);                                                                   
            return null;                                                                                    
        } 
        
        /*Regresa el resultado en cadena ya convertido en hexadécimal*/
        return sByTOHex(md.digest(convertme));
    }
    

    /*Procesa la parte de las series por producto*/
    public static void vSerPro(Connection con, String sProd, String sCant, String sSer, String sAlma, String sComen, String sOper)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Comprueba si ya existe este producto con esta serie en la base de datos*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT prod FROM serieprod WHERE prod = '" + sProd + "' AND ser = '" + sSer + "' AND alma = '" + sAlma + "'";	            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }

        /*Crea la consulta correcta dependiendo si existe o no ya ese registro en la base de datos*/
        if(bSi)
            sQ  = "UPDATE serieprod SET "
                    + "exist        = exist  " + sOper + " " + sCant + " "
                    + "WHERE prod   = '" + sProd + "' AND alma = '" + sAlma + "' AND ser = '" + sSer + "'";
        else
            sQ  = "INSERT INTO serieprod (prod,            ser,            alma,           exist,          estac,                 sucu,                   nocaj,                 comen) "
                           + "VALUES('" + sProd + "', '" + sSer + "', '" + sAlma + "', " + sOper + " " + sCant + ", '" + Login.sUsrG + "', '" + Star.sSucu + "', '" +   Star.sNoCaj + "', '" + sComen + "')";
        
        /*Ejecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                               
         }    
        
    }/*Fin de public stativ void vSerPro(Connection con, String sProd, String sCant, String sSer, String sAlma, String sComen, String sOper)*/
    
    
    /*Devuelve un arreglo de bytes en hexadecimal*/
    public static String sByTOHex(byte[] b) 
    {
        /*Crea el arreglo de bytes en una cadena de hexadécimal*/
        String result = "";
        for (int i=0; i < b.length; i++)         
          result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        
        /*Devuelve el resultado*/
        return result;
    }
    
    /*Función que genera automáticamente el net beans para el web service y obtener el token de seguridad*/
    private static RespuestaObtenerToken obtenerToken(wsseg.SolicitudObtenerToken parameters) throws SeguridadObtenerTokenFallaSesionFaultFaultMessage, SeguridadObtenerTokenFallaServicioFaultFaultMessage 
    {
        wsseg.Seguridad_Service service = new wsseg.Seguridad_Service();
        wsseg.Seguridad port = service.getPuertoSeguridad();
        return port.obtenerToken(parameters);
    }

    
    /*Funcion sincronizable para leer o actualizar la bandera de la video llamada en la parte de video*/
    public static synchronized boolean bBanVid(boolean bVal, boolean bActua)
    {
        /*Si se tiene que actualizar entonces*/
        if(bActua)
            Star.bCierrVid = bVal;
        
        /*Devuelve el valor de la bandera*/
        return Star.bCierrVid;
    }
    
    
    /*Funcion sincronizable para leer o actualizar la bandera de la video llamada en la parte del audio*/
    public static synchronized boolean bBanVidAud(boolean bVal, boolean bActua)
    {
        /*Si se tiene que actualizar entonces*/
        if(bActua)
            Star.bCierrVidAud = bVal;
        
        /*Devuelve el valor de la bandera*/
        return Star.bCierrVidAud;
    }

    
    /*Función para cargar todas las series de las facturas en el control*/
    public static void vCargSer(javax.swing.JComboBox jComSer, String sTip)
    {
        /*Borra los items en el combobox de series*/
        jComSer.removeAllItems();

        /*Agrega el elemento vacio*/
        jComSer.addItem("");
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
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
                jComSer.addItem(rs.getString("ser"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                            
        }  

        //Cierra la base de datos
        Star.iCierrBas(con);                  
        
    }/*Fin de public static void vCargSer(javax.swing.JComboBox jComSer, String sTip)*/

    
    /*Método para solicitar un código de revocación*/
    public static String vRevoSis(java.lang.String sSerU, java.lang.String sKeyU, java.lang.String sMac) 
    {
        erws.ERWSServicio service = new erws.ERWSServicio();
        erws.ERWSPort port = service.getERWSPort();
        return port.vRevoSis(sSerU, sKeyU, sMac);
    }
    
    
    /*Método para solicitar un código de revocación*/
    public static String revsis(java.lang.String ser, java.lang.String key, java.lang.String mac) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.revsis(ser, key, mac);
    }

    
    /*Método para comprobar si la siere del usuario, su MAC y la llave son válidos en el servidor para poder continuar*/
    public static String serkey(java.lang.String ser, java.lang.String key, java.lang.String mac) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.serkey(ser, key, mac);
    }
        
    
    /*Función para calcular la existencia general de un producto y actualizarla en la base de datos*/
    public static int iCalcGralExis(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene la existencia general de ese producto*/
        String sExisG   = "";
        try
        {
            sQ = "SELECT IFNULL(SUM(exist),0) AS exis FROM existalma WHERE prod = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sExisG = rs.getString("exis");                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Actualiza la existencia general de ese producto*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "exist        = " + sExisG + " "
                    + "WHERE prod   = '" + sProd + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {                         
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static void vCalcGralExis(Connection con, String sProd)*/

    
    /*Función para hacer la afectación en las existencias por almacén*/
    public static int iAfecExisProd(Connection con, String sProd, String sAlma, String sExis, String sOper)
    {
        //Declara variables de la base de datos        
        String      sQ;
        ResultSet   rs;
        Statement   st;
        
        
        
        
        /*Comprueba si ya existe este producto en existencias almacén*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT prod FROM existalma WHERE prod = '" + sProd.trim() + "' AND alma = '" + sAlma.trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Crea la consulta correcta en base a si existe ya en existencias almacén o no*/
        if(bSi)
            sQ = "UPDATE existalma SET "
                    + "exist        = exist " + sOper + " " + sExis.trim() + ", "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE prod   = '" + sProd.trim() + "' AND alma = '" + sAlma.trim() + "'";                
        else
            sQ = "INSERT INTO existalma(    prod,                   alma,                  exist,                               sucu,                  nocaj,                 estac) "
                            + "VALUES('" +  sProd.trim() + "', '" + sAlma.trim() + "', " + sOper + " " + sExis.trim() + ", '" + Star.sSucu + "', '" +  Star.sNoCaj + "', '" + Login.sUsrG + "')";                        
        
        /*Realiza la consulta*/
        try 
        {                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {              
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                    
        }

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static void vAfecExis(Connection con, String sProd, String sAlma, String sExis, String sOper)*/

    
    /*Función para obtener el PEPS de un producto*/
    public static String sGetPEPS(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
                return null;                    
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtien el PEPS del producto*/
        String sPeps    = "0";
        try
        {
            sQ = "SELECT cost FROM costs ORDER BY costs.ID_ID DESC LIMIT 1";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sPeps   = rs.getString("cost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }

        /*Si el PEPS es igual a 0 entonces*/
        if(Double.parseDouble(sPeps)==0)
        {
            /*Obtiene la la última compra de empezando a arriba hacia abajo*/
            try
            {
                sQ = "SELECT IFNULL(cost,0) * comprs.TIPCAM AS cost FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND partcomprs.PROD = '" + sProd + "' ORDER BY partcomprs.ID_ID DESC LIMIT 1";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sPeps   = rs.getString("cost");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return null;                                                                                                    
            }

        }/*Fin de if(Double.parseDouble(sPeps)==0)*/
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;                                 
        }
        
        /*Regresa el resultado*/
        return sPeps;
        
    }/*Fin de public static String sGetPEPS(Connection con, String sProd)*/           
    
    
    /*Función para obtener el UEPS de un producto*/
    public static String sGetUEPS(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
                return null;           
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el UEPS del producto*/
        String sUeps    = "0";
        try
        {
            sQ = "SELECT cost FROM costs ORDER BY costs.ID_ID ASC LIMIT 1";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                                    
                sUeps   = rs.getString("cost");                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }

        /*Si el UEPS es igual a 0 entonces*/
        if(Double.parseDouble(sUeps)==0)
        {
            /*Obtiene el último UEPS empezando de arriba hacia abajo*/
            try
            {
                sQ = "SELECT IFNULL(cost,0) * comprs.TIPCAM AS cost FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND partcomprs.PROD = '" + sProd + "' ORDER BY partcomprs.ID_ID ASC LIMIT 1";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sUeps   = rs.getString("cost");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return null;                                                                                                    
            }

        }/*Fin de if(Double.parseDouble(sUeps)==0)*/
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;                                 
        }
        
        /*Regresa el resultado*/
        return sUeps;
        
    }/*Fin de public static String sGetUEPS(Connection con, String sProd)*/
               
    
    /*Obtiene el costo del producto en base al código del producto*/
    public static String sGetCost(Connection con, String sProd, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        /*Obtiene el método de costeo de la empresa*/
        String sMetCost    = "";
        try
        {
            sQ = "SELECT metcost FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                                    
                sMetCost    = rs.getString("metcost");                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }

        /*Obtiene el costo en base al método de costeo de la empresa*/
        String sCost    = "";
        if(sMetCost.compareTo("peps")==0)
        {
            /*Obtiene el costo PEPS*/
            sCost   = Star.sGetPEPS(con, sProd);
            
            /*Dale seguimiento al método de costeo de la empresa*/                        
            if(Star.iSegPEPS(con, sCant, sProd)==-1)
                return null;            
        }
        else if(sMetCost.compareTo("ueps")==0)
        {            
            /*Obtiene el costo PEPS*/
            sCost   = Star.sGetUEPS(con, sProd);
            
            /*Dale seguimiento al método de costeo de la empresa*/                        
            if(Star.iSegUEPS(con, sCant, sProd)==-1)
                return null;            
        }
        else if(sMetCost.compareTo("ultcost")==0)
            sCost   = Star.sUltCost(con, sProd);
        else if(sMetCost.compareTo("prom")==0)
            sCost   = Star.sCostProm(con, sProd);

        /*Si hubo error entonces regresa nulo*/
        if(sCost==null)
            return null;
        
        /*Regresa el resultado*/
        return sCost;
        
    }/*Fin de public static String sGetCost(Connection con)*/
    
    
    /*Función para darle seguimiento PEPS*/
    public static int iSegPEPS(Connection con, String sCant, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Dale seguimiento PEPS para ese producto*/
        try
        {
            sQ = "SELECT cant, id_id FROM costs WHERE prod = '" + sProd + "' ORDER BY costs.ID_ID DESC";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {               
                /*Si la cantidad que se quiere ingresar es igual a la disponible entonces*/
                if(Double.parseDouble(rs.getString("cant"))==Double.parseDouble(sCant))
                {
                    /*Borra ese registro de los costeos*/
                    if(Star.iActuaPEPS(con, rs.getString("id_id"), "0", false)==-1)
                        return -1;
                    
                    /*Sal del búcle*/
                    break;
                }
                /*Else if la cantidad a insertar es menor que la disponible*/
                else if(Double.parseDouble(sCant)<Double.parseDouble(rs.getString("cant")))
                {                                                
                    /*Actualiza ese costeo para restarle la cantidad*/
                    if(Star.iActuaPEPS(con, rs.getString("id_id"), sCant, true)==-1)                                                                                                
                        return -1;
                    
                    /*Sal del búcle*/
                    break;
                }
                /*Else if la cantidad a insertar es mayor que la disponible*/
                else if(Double.parseDouble(sCant)>Double.parseDouble(rs.getString("cant")))
                {                                                
                    /*Borra ese costeo*/
                    if(Star.iActuaPEPS(con, rs.getString("id_id"), "0", false)==-1)
                        return -1;

                    /*Resta la diferencia a la cantidad*/
                    sCant    = Double.toString(Double.parseDouble(sCant) - Double.parseDouble(rs.getString("cant")));                                                    
                }                                                            
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }                
                
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iSegPEPS(Connection con, String sCant, String sProd)*/


    /*Función para darle seguimiento UEPS*/
    public static int iSegUEPS(Connection con, String sCan, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
            
        
        
        /*Dale seguimiento a las compras con método UEPS*/            
        try
        {
            sQ = "SELECT cant, id_id FROM costs WHERE prod = '" + sProd + "' ORDER BY costs.ID_ID DESC";		                                
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Si la cantidad que se quiere ingresar es igual a la disponible entonces*/
                if(Double.parseDouble(sCan)==Double.parseDouble(rs.getString("cant")))
                {
                    /*Acutualiza la diferencia solamente en esa partida de compra y continua*/
                    if(Star.iActuaUEPS(con, rs.getString("id_id"), sCan, false)==-1)
                        return -1;
                    
                    /*Sal del búcle*/
                    break;
                }
                /*Else if la cantidad a insertar es menor que la disponible*/
                else if(Double.parseDouble(sCan)<Double.parseDouble(rs.getString("cant")))
                {                                                
                    /*Actualiza el UEPS igual a la cantidad y sal del búcle*/
                    if(Star.iActuaUEPS(con, rs.getString("id_id"), sCan, true)==-1)
                        return -1;
                    
                    /*Sal del búcle*/
                    break;
                }
                /*Else if la cantidad a insertar es mayor que la disponible*/
                else if(Double.parseDouble(sCan)>Double.parseDouble(rs.getString("cant")))
                {                                                
                    /*Actualiza el UEPS igual a la cantidad disponible*/
                    if(Star.iActuaUEPS(con, rs.getString("id_id"), sCan, false)==-1)
                        return -1;

                    /*Resta la diferencia a la cantidad*/
                    sCan    = Double.toString(Double.parseDouble(sCan) - Double.parseDouble(rs.getString("cant")));                                                    
                }                                                            
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iSegUEPS(Connection con, String sCant, String sProd)*/
                            
    
    /*Función para obtener el costo promedio de un productp*/
    public static String sCostProm(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
                return null;           
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;         
        
        /*Obtiene el costo promedio*/
        String sCostP              = "0";            
        try
        {
            sQ = "SELECT IFNULL(costpro,0) * comprs.TIPCAM AS costp FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND (partcomprs.CANT - partcomprs.DEVS) > 0 AND prod = '" + sProd + "' ORDER BY partcomprs.ID_ID DESC LIMIT 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCostP          = rs.getString("costp");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;                                 
        }
        
        /*Regresa el resultado*/
        return sCostP;
        
    }/*Fin de public static String sCostProm(Connection con, String sProd)*/
    
    
    /*Función para obtener el último costo del producto*/
    public static String sUltCost(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces
            if(con==null)
                return null;            
        }
                
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;         
        
        /*Obtiene el último costo del producto*/
        String sUltCost     = "0";            
        try
        {
            sQ = "SELECT IFNULL(cost,0) AS cost FROM costs WHERE prod = '" + sProd + "' ORDER BY costs.ID_ID DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sUltCost          = rs.getString("cost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;                                                         
        }
        
        /*Regresa el resultado*/
        return sUltCost;
        
    }/*Fin de public static String sUltCost(Connection con, String sProd)*/

    
    /*Función para obtener el id del último costo del producto*/
    public static String sIDUltCost(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene el id del último costo del producto*/
        String sId     = "0";            
        try
        {
            sQ = "SELECT IFNULL(partcomprs.ID_ID,0) AS id FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND (partcomprs.CANT - partcomprs.DEVS) > 0 AND prod = '" + sProd + "' ORDER BY partcomprs.ID_ID DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sId          = rs.getString("id");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                                                                            
        }
        
        /*Regresa el resultado*/
        return sId;
        
    }/*Fin de public static String sIDUltCost(Connection con, String sProd)*/
    
    
    /*Función para devolver los componentes de un kit al inventario correctamente*/
    public static int iDevKit(Connection con, String sId, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ; 


        
        /*Recorre todas las partidas que son los componentes de ese kit*/        
	try
        {                  
            sQ = "SELECT CONCAT_WS('', noser, norefer) AS fol, prod, codemp, id_id, noser, cant, alma, descrip, unid, partvta.VTA, vtas.TIPDOC FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA WHERE idkit = " + sId;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene la cantidad correcta dependiendo de su unidad*/
                String sCantO   = Star.sCantUnid(rs.getString("unid"), rs.getString("cant"));
                        
                /*Realiza la afectación correspondiente al almacén para la entrada*/
                if(Star.iAfecExisProd(con, rs.getString("prod").replace("'", "''").trim(), rs.getString("alma").replace("'", "''").trim(), sCantO, "+")==-1)
                    return -1;

                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, rs.getString("prod").replace("'", "''").trim())==-1)
                    return -1;

                /*Actualiza la partida en la base de datos para saber los que se devolvierón*/                
                if(Star.iActuaDev(con, rs.getString("id_id"), sCant)==-1)
                    return -1;
            
                /*Registra el producto que se esta aumentando al inventario en la tabla de monitor de inventarios y devuelve error*/
                if(!Star.vRegMoniInv(con, rs.getString("prod"), sCantO, rs.getString("descrip"), rs.getString("alma"), Login.sUsrG , rs.getString("fol"), "DEVP " + rs.getString("tipdoc"), rs.getString("unid"), rs.getString("noser"), rs.getString("codemp"), "0"))                                
                    return -1;                                                                                       
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }
           
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iDevKit(String sKit)*/

    
    /*Actualiza la devolución en la partida de la compra epecífica*/
    public static int iActuaDevCom(Connection con, String sId, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        
        //Si manda vacio que sea 0
        if(sCant.compareTo("")==0)
            sCant           = "0";
        
        /*Actualiza la partida en la base de datos para saber los que se devolvierón*/
        try 
        {                
            sQ = "UPDATE partcomprs SET "
                    + "devs         = devs + " + sCant + " "                    
                    + "WHERE id_id  = " + sId;                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                                                                            
        }

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iActuaDevCom(String sId)*/
    
    
    /*Función para devolver los componentes de un kit al inventario correctamente de una devolución de compra*/
    public static int iDevKitCom(Connection con, String sId, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;    
        String      sQ; 



        
        /*Actualiza la partida en la base de datos para marcar ese kit que ya se devolvió y en las compras se marque posteriormente como compra devuelta en su totalidad*/                
        if(Star.iActuaDevCom(con, sId, sCant)==-1)
            return -1;

        /*Recorre todas las partidas que son los componentes de ese kit*/        
	try
        {                  
            sQ = "SELECT prod, prov, partcomprs.ID_ID, noser, cant, alma, descrip, unid, partcomprs.CODCOM, comprs.TIP FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP  = partcomprs.CODCOM WHERE idkit = " + sId;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Realiza la afectación correspondiente al almacén para la salida*/
                if(Star.iAfecExisProd(con, rs.getString("prod").replace("'", "''").trim(), rs.getString("alma").replace("'", "''").trim(), rs.getString("cant").replace("'", "''").trim(), "-")==-1)
                    return -1;

                /*Si hubo error entonces*/
                if(Star.bErr)
                {
                    /*Resetea la bandera de error y regresa error*/
                    Star.bErr   = false;
                    return -1;
                }

                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, rs.getString("prod").replace("'", "''").trim())==-1)
                    return -1;

                /*Actualiza la partida en la base de datos para saber los que se devolvierón*/                
                if(Star.iActuaDevCom(con, rs.getString("id_id"), rs.getString("cant"))==-1)
                    return -1;
            
                /*Registra el producto que se esta restando al inventario en la tabla de monitor de inventarios y devuelve error*/
                if(!Star.vRegMoniInv(con, rs.getString("prod"), rs.getString("cant"), rs.getString("descrip"), rs.getString("alma"), Login.sUsrG , rs.getString("codcom"), "DEVP " + rs.getString("tip"), rs.getString("unid"), rs.getString("noser"), rs.getString("prov"), "1"))                                
                    return -1;                                                                                       
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;
        }
           
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iDevKitCom(Connection con, String sId, String sCant)*/
    
   
    /*Actualiza la devolución en la partida de la venta epecífica*/
    public static int iActuaDev(Connection con, String sId, String sCant)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 

        
        /*Actualiza la partida en la base de datos para saber los que se devolvierón*/
        try 
        {                
            sQ = "UPDATE partvta SET "
                    + "devs         = devs + " + sCant + ", "
                    + "entrenow     = " + sCant + " "
                    + "WHERE id_id  = " + sId;                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iActuaDev(String sId)*/
        

    /*Función para devolver los costeos a las compras en base al id específicado*/
    public static int iDevCost(Connection con, String sId)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        
        
        
        
        /*Contiene los peps y ueps que se deben devolver*/
        String sUeps    = "";
        String sPeps    = "";
        
        /*Obtiene el dato que contiene ese id de partida de venta*/
        try
        {
            sQ = "SELECT idpeps, idueps FROM partvta WHERE id_id = " + sId;                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sUeps       = rs.getString("idueps");
                sPeps       = rs.getString("idpeps");                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        /*Separa por tubería los PEPS*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sPeps, "|");
        
        /*Recorre todos los id que se tienen que regresar a las compras*/
        while(stk.hasMoreTokens())
        {
            /*Obtiene el id de la partida de compra y la cantidad a devolverle*/
            java.util.StringTokenizer stkCom = new java.util.StringTokenizer(stk.nextToken(), "<");
            String sIdCom   = stkCom.nextToken();
            String sCantCom = stkCom.nextToken();
            
            /*Actualiza para esa partida de compra los nuevos disponibles devueltos*/
            try 
            {                
                sQ = "UPDATE partcomprs SET peps = peps - " + sCantCom + " WHERE id_id = " + sIdCom;                                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return -1;                            
            }
            
        }/*Fin de while(stk.hasMoreTokens())*/                
            
        /*Separa por tubería los UEPS*/
        stk = new java.util.StringTokenizer(sUeps, "|");
        
        /*Recorre todos los id que se tienen que regresar a las compras*/
        while(stk.hasMoreTokens())
        {
            /*Obtiene el id de la partida de compra y la cantidad a devolverle*/
            java.util.StringTokenizer stkCom = new java.util.StringTokenizer(stk.nextToken(), "<");
            String sIdCom   = stkCom.nextToken();
            String sCantCom = stkCom.nextToken();
            /*Actualiza para esa partida de compra los nuevos disponibles devueltos*/
            try 
            {                
                sQ = "UPDATE partcomprs SET ueps = ueps - " + sCantCom + " WHERE id_id = " + sIdCom;                                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return -1;               
            }
            
        }/*Fin de while(stk.hasMoreTokens())*/                
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iDevCost(String sId)*/    

    
    /*Comprueba si el parámetro es alguna de las unidade básicas*/
    public static boolean bUnidBas(String sUnid)
    {
        /*Comprueba si la unidad pasada es alguna de las unidades básicas*/
        return sUnid.toUpperCase().trim().compareTo("KILO")==0 || sUnid.toUpperCase().trim().compareTo("METRO CUADRADO")==0 || sUnid.toUpperCase().trim().compareTo("CABEZA")==0 || sUnid.toUpperCase().trim().compareTo("KILOWATT")==0 || sUnid.toUpperCase().trim().compareTo("KILOWATT/HORA")==0 || sUnid.toUpperCase().trim().compareTo("GRAMO NETO")==0 || sUnid.toUpperCase().trim().compareTo("DOCENAS")==0 || sUnid.toUpperCase().trim().compareTo("GRAMO")==0 || sUnid.toUpperCase().trim().compareTo("METRO CÚBICO")==0 || sUnid.toUpperCase().trim().compareTo("LITRO")==0 || sUnid.toUpperCase().trim().compareTo("MILLAR")==0 || sUnid.toUpperCase().trim().compareTo("TONELADA")==0 || sUnid.toUpperCase().trim().compareTo("DECENAS")==0 || sUnid.toUpperCase().trim().compareTo("CAJA")==0 || sUnid.toUpperCase().trim().compareTo("METRO LINEAL")==0 || sUnid.toUpperCase().trim().compareTo("PIEZA")==0 || sUnid.toUpperCase().trim().compareTo("PAR")==0 || sUnid.toUpperCase().trim().compareTo("JUEGO")==0 || sUnid.toUpperCase().trim().compareTo("BARRIL")==0 || sUnid.toUpperCase().trim().compareTo("CIENTOS")==0 || sUnid.toUpperCase().trim().compareTo("BOTELLA")==0;        
    }
    

    /*Comprueba si la unidad pasada es unidad base del segundo parámetro*/
    public static boolean bEsUnidBas(String sUnid, String sBas)
    {
        /*Valida para KILO y sus derivados*/
        if((sUnid.toUpperCase().compareTo("KILO")==0 && sBas.toUpperCase().compareTo("GRAMO")==0) || (sUnid.toUpperCase().compareTo("KILO")==0 && sBas.toUpperCase().compareTo("TONELADA")==0))
            return true;        
        /*Valida para TONELADA y sus derivados*/
        else if((sUnid.toUpperCase().compareTo("TONELADA")==0 && sBas.toUpperCase().compareTo("GRAMO")==0) || (sUnid.toUpperCase().compareTo("TONELADA")==0 && sBas.toUpperCase().compareTo("KILO")==0))
            return true;        
        /*Valida para GRAMO y sus derivados*/
        else if((sUnid.toUpperCase().compareTo("GRAMO")==0 && sBas.toUpperCase().compareTo("KILO")==0) || (sUnid.toUpperCase().compareTo("GRAMO")==0 && sBas.toUpperCase().compareTo("TONELADA")==0))
            return true;        
        
        /*Devuelve que no se encontró en ninguna base*/
        return false;        
    }

    
    /*Función para obtener la cantidad correcta en base a la unidad deseada*/
    public static String sCantUnid(String sUnid, String sCant)
    {
        /*Si la unidad a insertar es TONELADA entonces devuelve 1*/
        if(sUnid.toUpperCase().trim().compareTo("TONELADA")==0)
            return Double.toString(Double.parseDouble(sCant) * Double.parseDouble("1000000"));
        /*Else si la unidad a insertar es KILO entonces devuelve 1*/
        else if(sUnid.toUpperCase().trim().compareTo("KILO")==0)
            return Double.toString(Double.parseDouble(sCant) * Double.parseDouble("1000"));
        /*Else si la unidad a insertar es GRAMO entonces devuelve 1*/
        else 
            return Double.toString(Double.parseDouble(sCant) * Double.parseDouble("1"));                        
    }

    
    /*Función para saber si una unidad es de un producto en específico*/
    public static String sEsUnidProd(Connection con, String sProd, String sUnid)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Comprueba si la unidad tomada es la que tiene el producto asignada*/                
        try
        {
            sQ = "SELECT unid FROM prods WHERE prod = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene la unidad del producto*/
                String sUnidProd   =  rs.getString("unid").trim();
                
                /*Devuelve el valor correspondiente dependiendo de si la unidad es igual a la del producto*/
                if(sUnidProd.compareTo(sUnid)==0)
                    return "1";
                else
                    return "0";                
            }            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }            
        
        /*Regresa que no es de ese producto*/
        return "0";
        
    }/*Fin de public String sEsUnidProd(Connection con, String sProd, String sUnid)*/
    
    
    /*Función para devolver la unidad de un producto*/
    public static String sGetUnidProd(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene la unidad del producto específicado*/                
        String sUnid        = "";
        try
        {
            sQ = "SELECT unid FROM prods WHERE prod = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sUnid       = rs.getString("unid");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }            
        
        /*Regresa la unidad*/
        return sUnid;
        
    }/*Fin de public String sGetUnidProd(Connection con, String sProd)*/

    
    /*Método para encriptar información con la base de datos*/
    public static String sEncyMy(String sTex)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return null;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Encripta la cadena pasada a la función con MYSQL*/
        String sResul   = "";
        try
        {
            sQ = "SELECT HEX(AES_ENCRYPT('" + sTex + "','" + new String(sClavEncrip) + "')) AS result";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sResul  = rs.getString("result");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return null;                                 

        /*Devuelve el resultado*/
        return sResul;
        
    }/*Fin de public static String sEncyMy(String sTex)*/    
    
    
    /*Método para desencriptar información con la base de datos*/
    public static String sDencyMy(String sTex)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return null;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Desencripta la cadena pasada a la función con MYSQL*/
        String sResul   = "";
        try
        {
            sQ = "SELECT AES_DECRYPT(UNHEX('" + sTex + "'),'" + new String(sClavEncrip) + "') AS result";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sResul  = rs.getString("result");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return null;                                 

        /*Devuelve el resultado*/
        return sResul;
        
    }/*Fin de public static String sDencyMy(String sTex)*/    

    
    /*Método para obtener el precio de venta correcto de un producto*/
    public static String sPreCostVta(String sProd, String sCli, String sList, String sClasif, String sTip)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces
        if(con==null)
            return null;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 

        //Obtiene el método de costeo del producto        
        String sMetCost = Star.sGetMetCostProd(con, sProd);
        
        //Si hubo error entonces regresa error
        if(sMetCost==null)
            return null;
                        
        //Obtiene el costo del producto dependiendo el método de costeo obtenido
        String sCost    = "";
        if(sMetCost.compareTo("ueps")==0)
            sCost       = Star.sGetUEPS(con, sProd);            
        else if(sMetCost.compareTo("peps")==0)
            sCost       = Star.sGetPEPS(con, sProd);            
        else if(sMetCost.compareTo("prom")==0)
            sCost       = Star.sCostProm(con, sProd);            
        else if(sMetCost.compareTo("ultcost")==0)
            sCost       = Star.sUltCost(con, sProd);            
        
        //Obtiene la clasificación original del cliente        
        String sClas    = "";
        try
        {
            sQ = "SELECT codclas FROM emps WHERE CONCAT_WS('', ser, codemp ) = '" + sCli + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sClas       = rs.getString("codclas");                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }  
        
        //Obtiene la clasificación jerárquica del producto
        String sClasJera    = "";
        try
        {
            sQ = "SELECT clasjera FROM prods WHERE prod = '" + sProd + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sClasJera   = rs.getString("clasjera");                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }  
        
        /*Contiene los datos de la regla de negocio*/
        String sListReg = "";
        String sPreReg  = "";
        String sUtilReg = "";
        String sUsar    = "";
        String cProd    = "";
        String cClien   = "";
        String cClas    = "";
        String cClasjera= "";
        String gListReg[]= new String[9999];
        String gPreReg[]= new String[9999];
        String gUtilReg[]= new String[9999];
        String gUsar[]= new String[9999];
        String gProd[]= new String[9999];
        String gClien[]= new String[9999];
        String gClas[]= new String[9999];
        String gClasjera[]= new String[9999];
        int  ban[]= new int [9999];
        int  ban2[]= new int [9999];
        int z=0,a,b=0,c=0,x=0;
        boolean bandera=false;
        try
        {
            sQ = "SELECT prod, clien ,clas ,clasjera ,list, prec, util, usar FROM asocdesc ";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resutlados
            while(rs.next())
            {
                //Obtiene los resultados
                cProd           = rs.getString("prod");
                cClien          = rs.getString("clien");
                cClas           = rs.getString("clas");
                cClasjera       = rs.getString("clasjera");
                sListReg        = rs.getString("list");
                sPreReg         = rs.getString("prec");
                sUtilReg        = rs.getString("util");
                sUsar           = rs.getString("usar");
                if(cProd.equalsIgnoreCase(sProd)==true||cClien.equalsIgnoreCase(sCli)==true||cClas.equalsIgnoreCase(sClas)==true||cClasjera.equalsIgnoreCase(sClasJera)==true)
                {
                    gListReg[x]=sListReg;
                    gPreReg[x]=sPreReg;
                    gUtilReg[x]=sUtilReg;
                    gUsar[x]=sUsar;
                    gProd[x]=cProd;
                    gClien[x]=cClien;
                    gClas[x]=cClas;
                    gClasjera[x]=cClasjera;
                    x++;
                }
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }
        if(x>0)
        {
        do{
            a=0;
            x--;
            
            
                if(gProd[x].equalsIgnoreCase("")==true)
                {
                    gProd[x]=sProd;
                    a++;
                }
                if(gClien[x].equalsIgnoreCase("")==true)
                {
                    gClien[x]=sCli;
                    a++;
                }
                if(gClas[x].equalsIgnoreCase("")==true)
                {
                    gClas[x]=sClas;
                    a++;
                }
                if(gClasjera[x].equalsIgnoreCase("")==true)
                {
                    gClasjera[x]=sClasJera;
                    a++;
                }
                if(gProd[x].equalsIgnoreCase(sProd)==true&&gClien[x].equalsIgnoreCase(sCli)==true&&gClas[x].equalsIgnoreCase(sClas)==true&&gClasjera[x].equalsIgnoreCase(sClasJera)==true)        
                { 
                    ban[b]=x;
                    ban2[b]=a;
                    b++;
                    
                }
        }while( x>0);
        
        if(z==0)
        {
            if(b==0)
            {
                
            }else
            {
                if(b==1)
                {
                    b--;
                    c=b;
                }
                else
                do{
                    if(b==0)
                    {
                        if(ban2[b]> ban2[c])
                        c=b;
                        if(ban2[b]==ban2[c])
                        {
       
                            int seleccion = JOptionPane.showOptionDialog( null,"regla 1: Prod="+gProd[b]+"Cliente="+gClien[b]+"Clas"+gClas[b]+"jerarquia"+gClasjera[b]+"\nregla 2: Prod="+gProd[c]+"Cliente="+gClien[c]+"Clas"+gClas[c]+"jerarquia"+gClasjera[c],"Regla de venta",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "opcion 1", "opcion 2" },"opcion 1");
      
                            if(seleccion==0)
                                c=b;
                            else
                            {
                                c=c;
                            }
                        }
                    }
                    else
                    {
                    b--;
                    c=b;
                    b--;
                    if(ban2[b]> ban2[c])
                        c=b;
                    if(ban2[b]==ban2[c])
                    {
       
                        int seleccion = JOptionPane.showOptionDialog( null,"regla 1: Prod="+gProd[b]+"Cliente="+gClien[b]+"Clas"+gClas[b]+"jerarquia"+gClasjera[b]+"\nregla 2: Prod="+gProd[c]+"Cliente="+gClien[c]+"Clas"+gClas[c]+"jerarquia"+gClasjera[c],"Regla de venta",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "opcion 1", "opcion 2" },"opcion 1");
      
                        if(seleccion==0)
                            c=b;
                        else
                        {
                            c=c;
                        }
                    }
                    }
                    }while( b>0);
            z= ban[c];
            bandera=true;
            }
        }
        }
        /*Comprueba si esta habilitado usar el precio de lista del cliente*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = '" + sClasif + "' AND conf = 'alistprecli" + sTip + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la banera si esta habilitado*/
                if(rs.getString("val").compareTo("1")==0)
                    bSi = true;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                       
        }  
        
        /*Si esta habilitado que el cliente utilice la lista de precios entonces*/        
        if(bSi)
        {
            /*Obtiene la lista de precios del cliente*/
            try
            {
                sQ = "SELECT list FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sList         = rs.getString("list");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return null;                
            }                         
            
        }/*Fin de if(bSi && jTNomEmp.getText().compareTo("")!=0)*/                 
        
        /*Si la regla de negocio dice que lista de precios entonces la lista será esta*/
        if(sUsar.compareTo("list")==0)
            sList           = gListReg[z];
        
        /*Contiene el precio de la lista específica y la utilidad de venta*/
        String sPreMen      = "";
        String sUtilVta     = "";
        /*Obtiene el precio y la utildiad de venta del producto*/        
        try
        {
            sQ = "SELECT prelist" + sList + ", utilvta" + sList + " FROM prods WHERE prod = '" + sProd + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sPreMen    = rs.getString("prelist" + sList);
                sUtilVta   = rs.getString("utilvta" + sList);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }  
                             
        /*Si la regla dice que será por utilidad entonces la utilidad de la venta será esta*/
        if(sUsar.compareTo("util")==0&&bandera==true)
            sUtilVta    = gUtilReg[z];
        
        /*Si la utilidad de venta es mayor a 0 entonces obtiene el precio de venta correcto*/
        if(Double.parseDouble(sUtilVta)>0)
            sPreMen     = Double.toString((Double.parseDouble(sCost) * 100) / (100 - Double.parseDouble(sUtilVta)));                    
                    
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return null;                                 

        /*Si la regla dice que es por precio entonces el precio será ese*/
        if(sUsar.compareTo("prec")==0&&bandera==true)
            sPreMen     = gPreReg[z];
        
        /*Devuelve el resultado*/
        return sPreMen + "|" + sList;
        
    }/*Fin de public static String sPreCostVta(String sProd, String sUtil)*/

    
    /*Función para obtener el token de seguridad del WS*/
    public static String sCreTokEsta(String sRFC)
    {
        /*Declara el objeto factory para el servicio de seguridad*/
        wsseg.ObjectFactory fac = new wsseg.ObjectFactory();
        
        /*Declara objeto para mandar solicitud de token con todas su propiedades*/
        wsseg.SolicitudObtenerToken sol   = fac.createSolicitudObtenerToken();
        sol.setRFC(fac.createSolicitudObtenerTokenRFC(sRFC));
        sol.setTransaccionID(System.currentTimeMillis());
        
        /*Obtiene el token de resultado*/                
        wsseg.RespuestaObtenerToken resp;
        try
        {                                            
            resp = obtenerToken(sol);            
        }
        catch(SeguridadObtenerTokenFallaSesionFaultFaultMessage | SeguridadObtenerTokenFallaServicioFaultFaultMessage expnWSPAC)
        {    
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC);                                                                   
            return null;                        
        }               
        
        /*Crea el token con el id integrador y el símbolo de tuberia*/            
        String sNewTok = toSHA1((sIDInte + "|" + resp.getToken().getValue()).getBytes());                        
        
        /*Devuelve el resultado*/
        return sNewTok + "|" + sol.getTransaccionID();
        
    }/*Fin de public static String sCreTokEsta()*/   

    
    /*Para timbrar una factura*/
    public static RespuestaTimbraXML timbraXML(wstimb.SolicitudTimbraXML parameters) throws wstimb.TimbradoTimbraXMLFallaSesionFaultFaultMessage, wstimb.TimbradoTimbraXMLFallaValidacionFaultFaultMessage, wstimb.TimbradoTimbraXMLFallaServicioFaultFaultMessage 
    {
        wstimb.Timbrado_Service service = new wstimb.Timbrado_Service();
        wstimb.Timbrado port = service.getPuertoTimbradoSeguro();
        return port.timbraXML(parameters);
    }    
    
    
    /*Función para generar una cadena original*/
    public static String sGenCad(String sXml)
    {
        /*Haz la transformación*/
        try
        {
            StreamSource srcXSL         = new StreamSource("cadenaoriginal_3_2.xslt");            
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer     = tFactory.newTransformer(srcXSL); 
            ByteArrayOutputStream otp   = new ByteArrayOutputStream(); 
            transformer.transform(new StreamSource(new StringReader(sXml)), new StreamResult(otp));
            
            /*Devuelve el resultado*/
            return otp.toString();
        }   
        catch(TransformerException expnTransfor)
        {
            //Procesa el error y regresa errir
            Star.iErrProc(Star.class.getName() + " " + expnTransfor.getMessage(), Star.sErrTransfor);                                                                               
            return null;
        }                                           
    }
    
    
    /*Función para generar el sello digital*/
    public static String genSelDig(final PrivateKey key, final String cadOrigi)
    {
        /*Genera el sello dígital*/
        try
        {
            Signature sign  = Signature.getInstance("SHA1withRSA");
            sign.initSign   (key, new SecureRandom());
            sign.update     (cadOrigi.getBytes());
            byte[] signature= sign.sign();
            return new String(Base64.encode(signature));
        }
        catch(NoSuchAlgorithmException expnNoSuchAlgo)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnNoSuchAlgo.getMessage(), Star.sErrNoSuchAlgo);                                                                   
            return null;
        }
        catch(InvalidKeyException expnInvalKey)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnInvalKey.getMessage(), Star.sErrInvalKey);                                                                   
            return null;
        }
        catch(SignatureException expnSignatu)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSignatu.getMessage(), Star.sErrSignatu);                                                                   
            return null;
        }
        
    }//Fin de public static String genSelDig(final PrivateKey key, final String cadOrigi)
    
    
    /*Función para obtener la llave privada*/
    public static PrivateKey pkGetKey(final File keyfile, final String password)
    {                
        /*Obtiene la llave privada*/
        try
        {
            FileInputStream in = new FileInputStream(keyfile);
            org.apache.commons.ssl.PKCS8Key pkcs8 = new org.apache.commons.ssl.PKCS8Key(in, password.toCharArray());
            byte [] decrypted = pkcs8.getDecryptedBytes();
            java.security.spec.PKCS8EncodedKeySpec spec = new java.security.spec.PKCS8EncodedKeySpec(decrypted);
            PrivateKey pk = null;            
            if(pkcs8.isDSA())
                pk = KeyFactory.getInstance("DSA").generatePrivate(spec);
            else if(pkcs8.isRSA())
                pk = KeyFactory.getInstance("RSA").generatePrivate(spec);
            
            pk = pkcs8.getPrivateKey();
            return pk;        
        }
        catch(GeneralSecurityException expnGralSecu)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnGralSecu.getMessage(), Star.sErrGralSecu);                                                                   
            return null;
        }           
        catch(IOException expnIO)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return null;
        }
        
    }/*Fin de public static PrivateKey pkGetKey(final File keyfile, final String password)*/
    
    
    /*Función para leer un CSD*/   public static java.util.Date sCrypto(String sFile)
    {
        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        try 
        {
            // use FileInputStream to read the file
            fis = new FileInputStream(sFile);

            // read the bytes
            byte value[] = new byte[fis.available()];
            fis.read(value);
            bais = new ByteArrayInputStream(value);

            // get X509 certificate factory
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            // certificate factory can now create the certificate 
            X509Certificate xRes = (X509Certificate)certFactory.generateCertificate(bais);
            return xRes.getNotAfter();         
        }
        catch(CertificateException expnCertifi)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnCertifi.getMessage(), Star.sErrCertifi);                                                                   
            return null;
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return null;
        }
        finally 
        {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bais);
        }
        
    }/*Fin de public static java.util.Date sCrypto(String sFile)*/
    
    
    /*Función para obtener el certificado en si ya en base64*/
    public static String sGetCer(String sFile)
    {
        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        try 
        {
            // use FileInputStream to read the file
            fis = new FileInputStream(sFile);

            // read the bytes
            byte value[] = new byte[fis.available()];
            fis.read(value);
            bais = new ByteArrayInputStream(value);

            // get X509 certificate factory
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            // certificate factory can now create the certificate 
            X509Certificate xRes = (X509Certificate)certFactory.generateCertificate(bais);
            return new String(Base64.encode(xRes.getEncoded()));         
        }
        catch(CertificateException expnCertifi)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnCertifi.getMessage(), Star.sErrCertifi);                                                                   
            return null;
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return null;
        }
        finally 
        {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bais);
        }
        
    }/*Fin de public static String sGetSer(String sFile)*/
    
    
    /*Función para obtener el número de certificado*/
    public static String sGetCerNo(String sFile)
    {
        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        try 
        {
            // use FileInputStream to read the file
            fis = new FileInputStream(sFile);

            // read the bytes
            byte value[] = new byte[fis.available()];
            fis.read(value);
            bais = new ByteArrayInputStream(value);

            // get X509 certificate factory
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            // certificate factory can now create the certificate 
            X509Certificate xRes = (X509Certificate)certFactory.generateCertificate(bais);
            
            /*Convierte el certificado a gran entero*/
            java.math.BigInteger biSer  = xRes.getSerialNumber();
            
            /*Conviertte el gran entero en arreglo de bytes*/
            byte bArr[] = biSer.toByteArray();
            
            /*Mete los bytes en un buffer de string*/
            StringBuffer sBuff = new StringBuffer();
            for(int x = 0; x < bArr.length; x++)
                sBuff.append((char)bArr[x]);
            
            /*Devuelve el resultado*/
            return sBuff.toString();         
        }
        catch(CertificateException expnCertifi)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnCertifi.getMessage(), Star.sErrCertifi);                                                                   
            return null;
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return null;
        }
        finally 
        {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bais);
        }
        
    }/*Fin de public static java.util.Date sCrypto(String sFile)*/
    
    
    /*Método para generar el PDF de las cotizaciones*/
    public static void vGenPDFCot(String sCli, String sCodCot, String sMon, String sSubTot, String sImpue, String sTot, String sNomLoc, String sTelLoc, String sColLoc, String sCallLoc, String sCPLoc, String sCiuLoc, String sEstLoc, String sPaiLoc, String sRFCLoc, String sObserv, String sDescrip, String sFCot, String sImpLet, boolean bMostA, boolean bImp, String sCo1, String sCo2, String sCo3, boolean bMandCo, String sRut)
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\Imagenes\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = Star.class.getResource(Star.sIconDef).toString();
                
        /*Declara variables*/
        String  sNomEmp     = "";
        String  sTelEmp     = "";
        String  sCallEmp    = "";
        String  sColEmp     = "";
        String  sCPEmpre    = "";
        String  sCiuEmp     = "";
        String  sEstaEmp    = "";
        String  sPaiEmp     = "";
        String  sRFCEmp     = "";
        String  sNoIntEmp   = "";
        String  sNoExtEmp   = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene todos los datos del cliente a la que se le esta cotizando*/
        try
        {                  
            sQ = "SELECT * FROM emps WHERE codemp = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los datos*/
            if(rs.next())
            {                        
                sNomEmp             = rs.getString("nom");                                    
                sTelEmp             = rs.getString("tel");                                    
                sCallEmp            = rs.getString("calle");                                    
                sColEmp             = rs.getString("col");                                    
                sCPEmpre            = rs.getString("cp");                                    
                sCiuEmp             = rs.getString("ciu");                                    
                sEstaEmp            = rs.getString("estad");                                    
                sPaiEmp             = rs.getString("pai");                                    
                sRFCEmp             = rs.getString("rfc");   
                sNoIntEmp           = rs.getString("noint");   
                sNoExtEmp           = rs.getString("noext");                                                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }                

        //Abre la base de datos nuevamente
        Connection con2 = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con2==null)
            return;        
            
        /*Muestra el reporteador*/
        try
        {
            /*Crea los parámetros que se pasarán*/
            Map <String,String> par = new HashMap<>();             
            par.clear();
            par.put("COD_COT",           sCodCot);
            par.put("CP_EMP",            sCPEmpre);                    
            par.put("NOMB_EMP",          sNomEmp);
            par.put("MON",               sMon);
            par.put("TEL_EMP",           sTelEmp);
            par.put("CALLE_EMP",         sCallEmp);
            par.put("COL_EMP",           sColEmp);            
            par.put("CIUDAD_EMP",        sCiuEmp);
            par.put("ESTADO_EMP",        sEstaEmp);
            par.put("PAIS_EMP",          sPaiEmp);
            par.put("RFC_EMP",           sRFCEmp);
            par.put("NO_EXT_EMP",        sNoExtEmp);
            par.put("NO_INT_EMP",        sNoIntEmp);
            par.put("SUBTOT",            sSubTot);
            par.put("IMPUE",             sImpue);
            par.put("TOT",               sTot);
            par.put("EMP_LOCAL",         sNomLoc);
            par.put("TEL_LOCAL",         sTelLoc);
            par.put("COL_LOCAL",         sColLoc);
            par.put("CALLE_LOCAL",       sCallLoc);                    
            par.put("CP_LOCAL",          sCPLoc);
            par.put("CIUDAD_LOCAL",      sCiuLoc);
            par.put("ESTADO_LOCAL",      sEstLoc);
            par.put("PAIS_LOCAL",        sPaiLoc);
            par.put("RFC_LOCAL",         sRFCLoc);
            par.put("OBSERVACIONES",     sObserv);
            par.put("DESCRIPCION",       sDescrip);
            par.put("FECHACOT",          sFCot);
            par.put("COD_EMP",           sCli);            
            par.put("LOGO",              sRutLog);            
            par.put("IMPLET",            sImpLet);            
            par.put("LOGE",              Star.class.getResource(Star.sIconDef).toString());

            /*Establece la ruta del reporte xml*/            
            JasperReport ja   = JasperCompileManager.compileReport(Star.class.getResourceAsStream("/jasreport/rptNewCot.jrxml"));
            JasperPrint pri   = JasperFillManager.fillReport(ja, (Map)par, con2);

            /*Si se tiene que mostrar el archivo entonces*/
            if(bMostA)
            {
                /*Muestralo maximizado*/
                JasperViewer v  = new JasperViewer      (pri, false);
                v.setExtendedState                      (JasperViewer.MAXIMIZED_BOTH);                    
                v.setVisible                            (true);
            }

            /*Si se tiene que imprimir entonces imprimelo*/
            if(bImp)
                JasperPrintManager.printReport(pri,true);                        

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;                                             

            /*Declara variables*/
            String sRutPDF     = sCarp + "\\Cotizaciones";

            /*Si el directorio de cotizaciones no existe entonces creala*/                               
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            /*Si el directorio de la cliente no existe entonces creala*/                               
            sRutPDF             = sRutPDF + "\\" + Login.sCodEmpBD;
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            /*Si la ruta pasada como parámetro no es cadena vacia entonces la ruta será esa*/
            if(sRut.compareTo("")!=0)
                sRutPDF         = sRut;
            else
                sRutPDF         += "\\" + sCodCot + ".pdf";
            
            /*Exportalo a pdf en el directorio completo con el nom del código de la cotización*/
            JasperExportManager.exportReportToPdfFile(pri, sRutPDF);

            /*Si se va a mandar el correo entonces que así sea*/
            if((sCo1!=null || sCo2!=null || sCo3!=null) && bMandCo)
                vManPDFEmp(sCodCot + ".pdf", sRutPDF, sCo1, sCo2, sCo3, sCodCot, "COTIZACIÓN");            
        }
        catch(JRException expnJASR)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR);
            return;
        }

        //Cierra la base de datos
        Star.iCierrBas(con);                  
        
    }/*Fin de public static void vGenPDFCot(String sCli, String sCodCot, String sMon, String sSubTot, String sImpue, String sTot, String sNomLoc, String sTelLoc, String sColLoc, String sCallLoc, String sCPLoc, String sCiuLoc, String sEstLoc, String sPaiLoc, String sRFCLoc, String sObserv, String sDescrip, String sFCot, String sImpLet, boolean bMostA, boolean bImp, String sCo1, String sCo2, String sCo3, boolean bMandCo, String sRut)*/
    
    /*Método para generar el PDF de previo de compras*/
    public static void vGenPDFPreC(String sCli, String sCodCot, String sMon, String sSubTot, String sImpue, String sTot, String sNomLoc, String sTelLoc, String sColLoc, String sCallLoc, String sCPLoc, String sCiuLoc, String sEstLoc, String sPaiLoc, String sRFCLoc, String sObserv, String sDescrip, String sFCot, String sImpLet, boolean bMostA, boolean bImp, String sCo1, String sCo2, String sCo3, boolean bMandCo, String sRut)
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\Imagenes\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = Star.class.getResource(Star.sIconDef).toString();
                
        /*Declara variables*/
        String  sNomEmp     = "";
        String  sTelEmp     = "";
        String  sCallEmp    = "";
        String  sColEmp     = "";
        String  sCPEmpre    = "";
        String  sCiuEmp     = "";
        String  sEstaEmp    = "";
        String  sPaiEmp     = "";
        String  sRFCEmp     = "";
        String  sNoIntEmp   = "";
        String  sNoExtEmp   = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene todos los datos del cliente a la que se le esta cotizando*/
        try
        {                  
            sQ = "SELECT * FROM emps WHERE codemp = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los datos*/
            if(rs.next())
            {                        
                sNomEmp             = rs.getString("nom");                                    
                sTelEmp             = rs.getString("tel");                                    
                sCallEmp            = rs.getString("calle");                                    
                sColEmp             = rs.getString("col");                                    
                sCPEmpre            = rs.getString("cp");                                    
                sCiuEmp             = rs.getString("ciu");                                    
                sEstaEmp            = rs.getString("estad");                                    
                sPaiEmp             = rs.getString("pai");                                    
                sRFCEmp             = rs.getString("rfc");   
                sNoIntEmp           = rs.getString("noint");   
                sNoExtEmp           = rs.getString("noext");                                                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }                

        //Abre la base de datos nuevamente
        Connection con2 = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con2==null)
            return;        
            
        /*Muestra el reporteador*/
        try
        {
            /*Crea los parámetros que se pasarán*/
            Map <String,String> par = new HashMap<>();             
            par.clear();
            par.put("COMP",       sCodCot);
            par.put("FDOC",       sFCot);
            par.put("NOM",        sNomLoc);
            par.put("PROV",       sCli);
            par.put("NODOC",      "");                    
            par.put("MON",        sMon);
            par.put("SUBTOT",     sSubTot);
            par.put("IMPUE",      sImpue);
            par.put("TOT",        sTot);
            par.put("LOGE",       sRutLog);


            /*Establece la ruta del reporte xml*/            
            JasperReport ja   = JasperCompileManager.compileReport(Star.class.getResourceAsStream("/jasreport/rptVPrevCom.jrxml"));
            JasperPrint pri   = JasperFillManager.fillReport(ja, (Map)par, con2);

            /*Si se tiene que mostrar el archivo entonces*/
            if(bMostA)
            {
                /*Muestralo maximizado*/
                JasperViewer v  = new JasperViewer      (pri, false);
                v.setExtendedState                      (JasperViewer.MAXIMIZED_BOTH);                    
                v.setVisible                            (true);
            }

            /*Si se tiene que imprimir entonces imprimelo*/
            if(bImp)
                JasperPrintManager.printReport(pri,true);                        

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;                                             

            /*Declara variables*/
            String sRutPDF     = sCarp + "\\Previos de compra";

            /*Si el directorio de cotizaciones no existe entonces creala*/                               
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            /*Si el directorio de la cliente no existe entonces creala*/                               
            sRutPDF             = sRutPDF + "\\" + Login.sCodEmpBD;
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            /*Si la ruta pasada como parámetro no es cadena vacia entonces la ruta será esa*/
            if(sRut.compareTo("")!=0)
                sRutPDF         = sRut;
            else
                sRutPDF         += "\\" + sCodCot + ".pdf";
            
            /*Exportalo a pdf en el directorio completo con el nom del código de la cotización*/
            JasperExportManager.exportReportToPdfFile(pri, sRutPDF);

            /*Si se va a mandar el correo entonces que así sea*/
            if((sCo1!=null || sCo2!=null || sCo3!=null) && bMandCo)
                vManPDFEmp(sCodCot + ".pdf", sRutPDF, sCo1, sCo2, sCo3, sCodCot, "Previo de compra");            
        }
        catch(JRException expnJASR)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnJASR.getMessage(), Star.sErrJASR);
            return;
        }

        //Cierra la base de datos
        Star.iCierrBas(con);                  
        
    }
    
    /*Manda la cotización al corr alternativo*/
    private static void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc, final String sErr)            
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
            
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               
        String      sAsunCot                = "";
        String      sCuerCot                = "";
        String      sCoAlter                = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Trae todos los datos del correo electrónico de la base de datos en base a el usuario*/        
        try
        {
            sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, asuncot, cuerpcot, corralter FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("srvsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");
                sAsunCot                    = rs.getString("asuncot");
                sCuerCot                    = rs.getString("cuerpcot");
                sCoAlter                    = rs.getString("corralter");

                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
        }

        /*Crea el usr y la contraseña como final para que el th valide si son válidos o no*/
        final String sUser      = sUsr;
        final String sContra    = sCont;
    
        //Muestra el loading
        Star.vMostLoading("");
        
        /*Manda el correo*/
        try
        {
            //Define las propiedades de conexión
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", sServSMTPSal);
            props.put("mail.smtp.starttls.enable", sActSSL);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", sSMTPPort);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = sUser;
            final String password = sContra;
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            MimeMessage  msj    = new MimeMessage(session);
            msj.setFrom         (new InternetAddress(sUser));
            msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
            msj.setSubject      (sAsunCot + "\"" + sNoDoc + "\" Fallo envío a " + sCo1 + " por " + sErr);
            String msg          = sCuerCot + " *Fallo envío a " + sCo1 + " por *" + sErr + "*";
            msj.setContent      (msg, "text/html; charset=utf-8");

            /*Genera el adjunto*/
            BodyPart msgBod     = new MimeBodyPart();
            msgBod.setText      (sCuerCot + " *Fallo envío a " + sCo1 + " por *" + sErr + "*");
            Multipart mult      = new MimeMultipart();
            mult.addBodyPart    (msgBod);

            /*Adjunta el PDF*/
            msgBod              = new MimeBodyPart();                                               
            DataSource src1     = new FileDataSource(sRutPDF);                        
            msgBod.setDataHandler(new DataHandler(src1));
            msgBod.setFileName  (sNombPDF);
            mult.addBodyPart    (msgBod);                                                

            /*Junta todo y manda el corr*/
            msj.setContent      (mult);            
            Transport.send(msj);
        }
        catch(MessagingException expnMessag)
        {
            /*Ingresa en la base de datos el registro de que se fallo*/
            try 
            {                
                sQ = "INSERT INTO logcorrs(corr,                                    nodoc,                          estad,          motiv,                                                  estac,                                    falt,       corrde,                         tipdoc,                               sucu,                                     nocaj) " + 
                             "VALUES('" + sCoAlter.replace("'", "''") + "','" +     sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'","''") + "','" +     Login.sUsrG.replace("'", "''") + "',      now(), '" + sUsr.replace("'", "''") + "',   'COTIZACIÓN ENVIO ALTERNATIVO','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;                
            }

            //Cierra la base de datos y regresa
            Star.iCierrBas(con);                  
            return;
        }

        /*Ingresa en la base de datos el registor de que se mando con éxito*/
        try 
        {            
            sQ = "INSERT INTO logcorrs(corr,                                        nodoc,                          estad,      motiv,  estac,                                  falt,       corrde,                         tipdoc,                             sucu,                                     nocaj) " + 
                          "VALUES('" + sCoAlter.replace("'", "''") + "','" +        sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'COTIZACIÓN ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            sQ = "INSERT INTO estadiscor(corr,                                        nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                         tipdoc,                             sucu,                                           nocaj) " + 
                          "VALUES('" + sCoAlter.replace("'", "''") + "','" +        sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'COTIZACIÓN ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
        }        
        
    }/*Fin de private void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc)*/
    
    
    /*Manda el corr con el pdf al cliente*/            
    private static void vManPDFEmp(final String sNombPDF, final String sRutPDF, final String sCo1, final String sCo2, final String sCo3, final String sNoDoc, final String sTipoDoc)
    {
        //Muestra el loading
        Star.vMostLoading("");
        
        /*Manda el PDF a la cliente en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {                 
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                

                //Declara variables locales
                String      sSrvSMTPSal   = "";
                String      sSMTPPort     = "";
                String      sUsr          = "";
                String      sContra       = "";                 
                String      sActSSL       = "";                                
                
                /*Contiene el asunto y cuerpo de la cotización*/
                String sAsunCot = "";
                String sCuerCot = "";                

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 
                
                /*Trae todos los datos del corr electrónico de la base de datos en base a El usuario*/                
                try
                {
                    sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, asuncot, cuerpcot FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene todos los datos de la consulta*/
                        sSrvSMTPSal                 = rs.getString("srvsmtpsal");
                        sSMTPPort                   = rs.getString("portsmtp");
                        sUsr                        = rs.getString("usr");
                        sContra                     = rs.getString("pass");
                        sAsunCot                    = rs.getString("asuncot");
                        sCuerCot                    = rs.getString("cuerpcot");
                        
                        /*Si activar ssl login esta activado entonces guarda true*/
                        if(rs.getString("actslenlog").compareTo("1")==0)
                            sActSSL             = "true";
                        else
                            sActSSL             = "false";                       
                        
                        /*Desencripta la contraseña*/
                        sContra                 = Star.sDecryp(sContra);                                                
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return;                    
                }                               
                if(sTipoDoc=="Previo de compra")
                {sAsunCot="Previo de Compra";
                sCuerCot="Previo de Compra";
                }
                /*Si el primer correo no es null entonces*/
                if(sCo1!=null)
                {
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUsr));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo1));
                        msj.setSubject      (sAsunCot + "\"" + sNoDoc + "\"");
                        String msg           = sCuerCot;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod     = new MimeBodyPart();
                        msjbod.setText      (sCuerCot);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msjbod);
                        msjbod              = new MimeBodyPart();
                        DataSource sou      = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler(new DataHandler(sou));
                        msjbod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msjbod);
                        msj.setContent      (mult);
                        
                        /*Manda el correo*/
                        Transport.send(msj);                        
                        
                        /*Ingresa en la base de datos el registro de que se mando con éxito*/
                        try 
                        {                    
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                              estad,      motiv,  estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "',     'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                              estad,      motiv,  estac,                                      falt,       corrde,                             tipdoc,                                 sucu,                                           nocaj) " + 
                                           "VALUES('" + sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "',     'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                            
                         }
                    }
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                           
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,          motiv,                                                  estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                       nocaj) " + 
                                           "VALUES('" + sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                            
                         }
                        
                        /*Obtiene el mensaje de error para el thread*/
                        final String sMsjEr  = expnMessag.getMessage().replace("'", "");
                        
                        /*Mandalo al correo alternativo*/
                        Thread th = new Thread()
                        {
                           @Override
                           public void run()
                           {
                               vMandAlter(sNombPDF, sRutPDF, sCo1, sNoDoc, sMsjEr);
                           }
                        };
                        th.start();                                                            
                                                
                    }/*Fin de catch(MessagingException expnMessag)*/                        
                    
                }/*Fin de if(sCo1!=null)*/
                
                /*Si el segundo correo no es null entonces*/
                if(sCo2!=null)
                {
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUsr));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo2));
                        msj.setSubject      (sAsunCot + "\"" + sNoDoc + "\"");
                        String msg           = sCuerCot;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod     = new MimeBodyPart();
                        msjbod.setText      (sCuerCot);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msjbod);
                        msjbod              = new MimeBodyPart();
                        DataSource sou      = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler(new DataHandler(sou));
                        msjbod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msjbod);
                        msj.setContent      (mult);
                        
                        /*Manda el correo*/
                        Transport.send(msj);                        
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,  estac,                                  falt,       corrde,                             tipdoc,                                     sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                          estad,      motiv, estac,                                  falt,       corrde,                             tipdoc,                                     sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         {                              
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                                                   
                         }                   
                    }
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,          motiv,                                                  estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                                                    
                         }
                        
                        /*Obtiene el mensaje de error para el thread*/
                        final String sMsjEr  = expnMessag.getMessage().replace("'", "");
                        
                        /*Mandalo al correo alternativo*/
                        Thread th = new Thread()
                        {
                           @Override
                           public void run()
                           {
                               vMandAlter(sNombPDF, sRutPDF, sCo2, sNoDoc, sMsjEr);
                           }
                        };
                        th.start();                                                            
                                                
                    }/*Fin de catch(MessagingException expnMessag)*/                        
                    
                }/*Fin de if(sCo2!=null)*/
                
                /*Si el primer correo no es null entonces*/
                if(sCo3!=null)
                {
                    /*Manda un correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUsr));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo3));
                        msj.setSubject      (sAsunCot + "\"" + sNoDoc + "\"");
                        String msg          = sCuerCot;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod     = new MimeBodyPart();
                        msjbod.setText      (sCuerCot);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart(msjbod);
                        msjbod              = new MimeBodyPart();
                        DataSource sou      = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler(new DataHandler(sou));
                        msjbod.setFileName  (sNombPDF);
                        mult.addBodyPart    (msjbod);
                        msj.setContent      (mult);
                        
                        /*Manda el correo*/
                        Transport.send(msj);                        
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,  estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                          estad,      motiv, estac,                                  falt,       corrde,                             tipdoc,                                 sucu,                                      nocaj) " + 
                                           "VALUES('" + sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                            
                         }
                    }
                    catch(MessagingException expnMessag)
                    {                    
                        /*Ingresa en la base de datos el registro de que fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                          estad,       motiv,                                                 estac,                                  falt,       corrde,                             tipdoc,                                sucu,                 nocaj) " + 
                                          "VALUES('" + sCo3.replace("'", "''") + "','" +    sNoDoc.replace("'", "''") + "', 'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "', '" +  sTipoDoc.replace("'", "''") + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                            return;                                                                                    
                         }
                        
                        /*Obtiene el mensaje de error para el thread*/
                        final String sMsjEr  = expnMessag.getMessage().replace("'", "");
                        
                        /*Mandalo al correo alternativo*/
                        Thread th = new Thread()
                        {
                           @Override
                           public void run()
                           {
                               vMandAlter(sNombPDF, sRutPDF, sCo3, sNoDoc, sMsjEr);
                           }
                        };
                        th.start();                                                            
                        
                        //Cierra la base de datos y regresa
                        Star.iCierrBas(con);                  
                        return;
                        
                    }/*Fin de catch(MessagingException expnMessag)*/                       
                    
                }/*Fin de if(sCo3!=null)*/

                //Esconde la forma de loading
                Star.vOcultLoadin();
                    
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Borra el PDF*/
                new File(sNombPDF).delete();
                
            }/*Fin de public void run()*/
            
        }).start();
        
    }/*Fin de private void vManPDFEmp(String sRutPDF, String sCo1, String sCo2, String sCo3, String sIdCorreoSelec)*/
    
    
    /*Método para obtener la existencia general del producto*/
    public static String sGetExisGral(Connection con, String sProd)
    {
        /*Declara variables de bases de datos*/
        ResultSet   rs;
        Statement   st;
        String      sQ;
        
        
        
        
        /*Obtiene la existencia general del producto*/
        try
        {
            sQ = "SELECT IFNULL(SUM(exist),0) AS exist FROM existalma WHERE prod = '" + sProd.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces devuelve el resultado*/
            if(rs.next())
                return rs.getString("exist");                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                        
        }
        
        /*Devuelve 0*/
        return "0";
        
    }/*Fin de public static String sGetExisGral(String sProd)*/
    
    
    /*Método para abrir la forma de ventas una sola vez*/
    public static void AbrVta(JTable jTabVent, String sVta, String sCodCo)
    {
        /*Si es nulo entonces crea la referencia y regresa la referencia*/
        if(Star.newVta==null)
        {            
            Star.newVta = new NewVta(jTabVent, sVta, sCodCo);
            Star.newVta.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.newVta.isVisible())            
                Star.newVta.toFront();
            else
                Star.newVta.setVisible(true);            
        }
    }
        
    /*Método para insertar costeo*/
    public static int iInsCost(Connection con, String sProd, String sCant, String sCost)
    {
        //Declara variables de la base de datos
        Statement   st;
        String      sQ; 
        
        
        
        
        /*Inserta el costeo*/
        try
        {
            sQ = "INSERT INTO costs(prod,                   cant,                     cost,             estac,                 sucu,                 nocaj) " +
                            "VALUES('" + sProd  + "','"  +  sCant + "','" +           sCost + "','" +   Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }
                
        /*Devuelve que todo fue bien*/
        return 0;
        
    }/*Fin de public static int iInsCost(String sProd, String sCant, String sCost)*/
    
    
    /*Método para cargar los clientes en la tabla*/
    public static void vCargCli(javax.swing.JTable jTab, String sBusc)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    
        
        /*Vacai la tabla*/
        te.setRowCount(0);
        
        /*Inica el contador*/
        int iCont   = 1;

        //Declara variables de la base de datos    
        Statement       st;
        ResultSet       rs;        
        String          sQ;                        
        
        /*Trae todos los clientes de la base de datos y cargalos en la lista*/
        try
        {
            sQ = "SELECT codemp, ser, nom FROM emps WHERE CONCAT_WS('', ser, codemp) LIKE('%" + sBusc.replace(" ", "%") + "%') OR nom LIKE('%" + sBusc.replace(" ", "%") + "%') OR ser LIKE('%" + sBusc.replace(" ", "%") + "%') OR tel LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR calle LIKE('%" + sBusc.replace(" ", "%") + "%') OR col LIKE('%" + sBusc.replace(" ", "%") + "%') OR cp LIKE('%" + sBusc.replace(" ", "%") + "%') OR ciu LIKE('%" + sBusc.replace(" ", "%") + "%') OR estad LIKE('%" + sBusc.replace(" ", "%") + "%') OR rfc LIKE('%" + sBusc.replace(" ", "%") + "%') OR descu LIKE('%" + sBusc.replace(" ", "%") + "%') OR co1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR co2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR co3 LIKE('%" + sBusc.replace(" ", "%") + "%') OR pagweb1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR pagweb2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR contac LIKE('%" + sBusc.replace(" ", "%") + "%') OR puest LIKE('%" + sBusc.replace(" ", "%") + "%') OR contact2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR puest2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR observ LIKE('%" + sBusc.replace(" ", "%") + "%') OR noint LIKE('%" + sBusc.replace(" ", "%") + "%') OR noext LIKE('%" + sBusc.replace(" ", "%") + "%') OR diacred LIKE('%" + sBusc.replace(" ", "%") + "%') OR limtcred LIKE('%" + sBusc.replace(" ", "%") + "%') OR falt LIKE('%" + sBusc.replace(" ", "%") + "%') OR fmod LIKE('%" + sBusc.replace(" ", "%") + "%') ORDER BY ser";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[] = {iCont, rs.getString("ser") + rs.getString("codemp"), rs.getString("ser"), rs.getString("nom")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas en uno*/
                ++iCont;        
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                    
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);                
        
    }/*Fin de public static void vCargCli(javax.swing.JTable jTab)*/
    
    
    /*Método para cargar los proveedores en la tabla*/
    public static void vCargProvS(javax.swing.JTable jTab, String sBusc)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    
        
        /*Vacai la tabla*/
        te.setRowCount(0);
        
        /*Inica el contador*/
        int iCont   = 1;

        //Declara variables de la base de datos    
        Statement       st;
        ResultSet       rs;        
        String          sQ;                        
        
        /*Trae todas las emps de la base de dats y cargalas en la lista*/
        try
        {
            sQ = "SELECT prov, ser, nom FROM provs WHERE CONCAT_WS('', ser, prov ) LIKE('%" + sBusc.replace(" ", "%") + "%') OR nom LIKE('%" + sBusc.replace(" ", "%") + "%') OR ser LIKE('%" + sBusc.replace(" ", "%") + "%') OR tel LIKE('%" + sBusc.replace(" ", "%") + "%') OR calle LIKE('%" + sBusc.replace(" ", "%") + "%') OR col LIKE('%" + sBusc.replace(" ", "%") + "%') OR cp LIKE('%" + sBusc.replace(" ", "%") + "%') OR noint LIKE('%" + sBusc.replace(" ", "%") + "%') OR noext LIKE('%" + sBusc.replace(" ", "%") + "%') OR ciu LIKE('%" + sBusc.replace(" ", "%") + "%') OR pais LIKE('%" + sBusc.replace(" ", "%") + "%') OR estad LIKE('%" + sBusc.replace(" ", "%") + "%') OR rfc LIKE('%" + sBusc.replace(" ", "%") + "%') OR descu LIKE('%" + sBusc.replace(" ", "%") + "%') OR diacred LIKE('%" + sBusc.replace(" ", "%") + "%') OR limcred LIKE('%" + sBusc.replace(" ", "%") + "%') OR co1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR co2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR co3 LIKE('%" + sBusc.replace(" ", "%") + "%') OR pagweb1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR pagweb2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR eje1 LIKE('%" + sBusc.replace(" ", "%") + "%') OR eje2 LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper21 LIKE('%" + sBusc.replace(" ", "%") + "%') OR telper22 LIKE('%" + sBusc.replace(" ", "%") + "%') OR observ LIKE('%" + sBusc.replace(" ", "%") + "%') OR falt LIKE('%" + sBusc.replace(" ", "%") + "%') OR fmod LIKE('%" + sBusc.replace(" ", "%") + "%') ORDER BY ser";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[] = {iCont, rs.getString("ser") + rs.getString("prov"), rs.getString("ser"), rs.getString("nom")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas en uno*/
                ++iCont;        
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                    
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);                 
        
    }/*Fin de public static void vCargProvS(javax.swing.JTable jTab, String sBusc)*/
    
    
    /*Método para agregar el final del comprobante*/
    public static String sCreFinXML(String sImpo, String sTas, String sImpue, String sVta)
    {               
        //Contiene la cadena inicial
        String sCad = "<cfdi:Impuestos totalImpuestosTrasladados=\"" + sImpo.replace("$", "").replace(",", "") + "\">" + System.getProperty( "line.separator" ) +
                    System.getProperty( "line.separator" ) +                            
                    "<cfdi:Traslados>" + System.getProperty( "line.separator" );
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return null;
        
        /*Declara variables de base de datos*/
        String sQ;
        ResultSet   rs;
        Statement   st;
        
        //Obtiene la suma de los impuestos de las partidas y el código del impuesto
        try
        {
            sQ = "SELECT SUM(impo * (impue/100)) AS totimpue, codimpue, impue FROM partvta WHERE vta = " + sVta + " GROUP BY codimpue, impue";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces recorre los resultados
            while(rs.next())
            {                
                sCad            +=  System.getProperty( "line.separator" ) + 
                                    "<cfdi:Traslado importe=\"" + Star.dRound(rs.getDouble("totimpue"),2) + "\" tasa=\"" + rs.getString("impue") + "\" impuesto=\"" + rs.getString("codimpue") + "\"/>";                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return null;

        //Devuelve el resultado
        return      sCad += System.getProperty( "line.separator" ) +
                    "</cfdi:Traslados>" + System.getProperty( "line.separator" ) +
                    System.getProperty( "line.separator" ) +
                    "</cfdi:Impuestos>" + System.getProperty( "line.separator" ) +
                    System.getProperty( "line.separator" ) +
                    "<cfdi:Complemento> </cfdi:Complemento>" + System.getProperty( "line.separator" ) + 
                    System.getProperty( "line.separator" ) +
                    "</cfdi:Comprobante>" + System.getProperty( "line.separator" );
        
    }//Fin de public static String sCreFinXML(String sImpo, String sTas, String sImpue, String sVta)
    
    
    /*Función para crear las partidas de una venta*/
    public static String sCrePartXML(Connection con, String sVta)
    {
        /*Inicia los conceptos*/
        String sXml = "<cfdi:Conceptos>" + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" );                                                                                
                
        /*Declara variables de base de datos*/
        String sQ;
        ResultSet   rs;
        Statement   st;
                
        /*Obtiene las partidas de esa venta*/
        try
        {
            sQ = "SELECT cant, impo, pre, descrip, unid FROM partvta WHERE vta = " + sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces completa el XML*/
            while(rs.next())
            {                
                sXml        +=  "<cfdi:Concepto importe=\"" + rs.getString("impo") + "\" valorUnitario=\"" + rs.getString("pre") + "\" descripcion=\"" + rs.getString("descrip").trim().replace("\"", "'") + "\" unidad=\"" + rs.getString("unid") + "\" cantidad=\"" + rs.getString("cant") + "\"/>" +  System.getProperty( "line.separator" ) + System.getProperty( "line.separator" );                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  

        /*Termina la cadena XML*/
        sXml    += "</cfdi:Conceptos>" + System.getProperty( "line.separator" );
        
        /*Devuelve el resultado*/
        return sXml;
        
    }/*Fin de public static String sCrePartXML(Connection con, String sVta)*/
    
    
    /*Función para devolver el XML encabezado*/    
    public static String sCreEncaXML(String sCta, String sCiuLo, String sMon, String sTipCam, String sMetPag, String sTot, String sTotDescu, String sSubTot, String sNumCert, String sFormPag, String sTipDocS, String sFDoc, String sCert, String sSell, String sNomLoc, String sRFCLoc, String sCPLoc, String sPaiLoc, String sEstLoc, String sCiuLoc, String sColLoc, String sNoExtLoc, String sCallLoc, String sRegFisc, String sNomEmp, String sRFC, String sPai, String sEsta, String sCiu, String sCol, String sCall, String sCiud, String sLugExp)
    {                
        /*Crea el XML para mandarlo timbrar*/
        String sXml         ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.getProperty( "line.separator" ) +
                            "<cfdi:Comprobante xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + System.getProperty( "line.separator" ) + 
                            "xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd http://www.sat.gob.mx/nomina http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina11.xsd\" " + System.getProperty( "line.separator" ) +
                            "version=\"3.2\" fecha=\"" + sFDoc + "\" " + System.getProperty( "line.separator" ) +
                             sSell + " " + System.getProperty( "line.separator" ) +  
                            "formaDePago=\"" + sFormPag + "\" noCertificado=\"" + sNumCert + "\" " + System.getProperty( "line.separator" ) +
                            "certificado=\"" + sCert + "\" " + System.getProperty( "line.separator" ) +
                            "subTotal=\"" + sSubTot + "\" descuento=\"" + sTotDescu + "\" total=\"" + sTot + "\" " + System.getProperty( "line.separator" ) +
                            "tipoDeComprobante=\"" + sTipDocS + "\" metodoDePago=\"" + sMetPag + "\" LugarExpedicion=\"" + sLugExp + "\" NumCtaPago=\"" + sCta + "\" Moneda=\"" + sMon + "\" TipoCambio=\"" + sTipCam + "\"> " + System.getProperty( "line.separator" ) +                                                                                                                                                              
                            "<cfdi:Emisor nombre=\"" + sNomLoc + "\" rfc=\"" + sRFCLoc + "\">" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +
                            "<cfdi:DomicilioFiscal codigoPostal=\"" + sCPLoc + "\" pais=\"" + sPaiLoc + "\" estado=\"" + sEstLoc + "\" municipio=\"" + sCiuLo + "\" colonia=\"" + sColLoc + "\" noExterior=\"" + sNoExtLoc + "\" calle=\"" + sCallLoc + "\"/>" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +
                            "<cfdi:RegimenFiscal Regimen=\"" + sRegFisc + "\"/>" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +
                            "</cfdi:Emisor>" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +                            
                            "<cfdi:Receptor nombre=\"" + sNomEmp + "\" rfc=\"" + sRFC + "\">" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +
                            "<cfdi:Domicilio pais=\"" + sPai + "\" estado=\"" + sEsta + "\" municipio=\"" + sCiud + "\" colonia=\"" + sCol + "\" calle=\"" + sCall + "\" localidad=\"" + sCiud + "\"/>" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" ) +
                            "</cfdi:Receptor>" + System.getProperty( "line.separator" ) +
                            System.getProperty( "line.separator" );                                                        
        
        /*Devuelve el resultado*/
        return sXml;
    }
        
    
    /*Método para validar antes de hacer una factura*/
    public static int iValFac(Connection con)
    {
        //Abre la base de datos nuevamente
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return -1;
        
        /*Declara las variales de la fiel*/        
        String sRutKeyF = "";
        String sRutCerF = "";
        String sPasCerF = "";
        
        /*Declara las variales del CSD*/        
        String sRutKey  = "";
        String sRutCer  = "";
        String sPasCer  = "";
        
        /*Variable para saber si es pesona física o moral*/
        String sFM      = "";
        
        /*Datos fiscales de la empresa*/
        String sRaz     = "";
        String sCall    = "";
        String sCol     = "";        
        String sNoExt   = "";
        String sCP      = "";
        String sCiu     = "";
        String sEstad   = "";
        String sPai     = "";
        String sRegFis  = "";
        String sLugExp  = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene algunos datos de la empresa local*/        
        try
        {
            sQ = "SELECT * FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sRutKeyF    = rs.getString("rutkeyf");
                sRutCerF    = rs.getString("rutcerf");
                sPasCerF    = Star.sDecryp(rs.getString("passcerf"));
                sRutKey     = rs.getString("rutkey");
                sRutCer     = rs.getString("rutcer");
                sPasCer     = Star.sDecryp(rs.getString("passcer"));
                sFM         = rs.getString("pers");
                sRaz        = rs.getString("nom");
                sCall       = rs.getString("calle");
                sCol        = rs.getString("col");                
                sNoExt      = rs.getString("noext");
                sCP         = rs.getString("cp");
                sCiu        = rs.getString("ciu");
                sEstad      = rs.getString("estad");
                sPai        = rs.getString("pai");
                sLugExp     = rs.getString("lugexp");
                sRegFis     = rs.getString("regfisc");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }
                
        /*Si la razón social no esta definida entonces*/
        if(sRaz.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La razón social de la empresa no esta definida.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el lugar de expedición no esta definido entonces*/
        if(sLugExp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El lugar de expedición local de la empresa no esta definida.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el regimén fiscal no esta definido entonces*/
        if(sRegFis.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El régimen fiscal de la empresa no esta definido.\nIr a datos general de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si la calle no esta definido entonces*/
        if(sCall.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La calle de la empresa no esta definida.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si la colonia no esta definida entonces*/
        if(sCol.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La colonia de la empresa no esta definida.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el número de exterior no esta definido entonces*/
        if(sNoExt.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El número exterior de la empresa no esta definido.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el CP no esta definido entonces*/
        if(sCP.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
                        
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El CP de la empresa no esta definido.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si la ciudad no esta definida entonces*/
        if(sCiu.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ciudad de la empresa no esta definida.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el estado no esta definido entonces*/
        if(sEstad.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El estado de la empresa no esta definido.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si el pais no esta definido entonces*/
        if(sPai.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El país de la empresa no esta definido.\nIr a datos generales de la empresa.", "Datos fiscales de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }
        
        /*Si es persona física entonces*/
        if(sFM.compareTo("F")==0)
        {
            /*Valida la ruta al certificado fiel*/
            if(sRutCerF.compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return -1;

                /*Mensajea y regresa error*/
                JOptionPane.showMessageDialog(null, "La ruta al CSD fiel de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
                return -1;
            }
            
            /*Valida la ruta al key fiel*/
            if(sRutKeyF.compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return -1;

                /*Mensajea y regresa error*/
                JOptionPane.showMessageDialog(null, "La ruta al key fiel de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
                return -1;
            }
            
            /*Valida la contraseña fiel*/
            if(sPasCerF.compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return -1;

                /*Mensajea y regresa error*/
                JOptionPane.showMessageDialog(null, "La contraseña del CSD fiel de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
                return -1;
            }
            
        }/*Fin de if(sFM.compareTo("F")==0)*/
        
        /*Valida la ruta al certificado*/
        if(sRutCer.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al CSD de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }

        /*Valida la ruta al key fiel*/
        if(sRutKey.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al key de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }

        /*Valida la contraseña*/
        if(sPasCer.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La contraseña del CSD de la empresa no esta definida.\nIr a datos generales de la empresa.", "CSD de la empresa", JOptionPane.INFORMATION_MESSAGE, null);
            return -1;
        }

        /*Devuelve que todo fue bien*/
        return 0;
        //chalo mensajes de la empreza
    }/*Fin de public static int iValFac(Connection con)*/

    
    //Obtiene el valor del impuesto
    public static String sGetValImp(String sImp)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return null;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el valor del impuesto*/
        String sVal = "0";
        try
        {
            sQ = "SELECT impueval FROM impues WHERE codimpue = '" + sImp.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                               
                sVal    = rs.getString("impueval");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return null;

        //Regresa el resultado
        return sVal;
        
    }//Fin de public static String sGetValImp(String sImp)
    
    
    //Método para validar si un producto necesita seria a fuerzas
    public static int iProdSolSer(Connection con, String sProd)
    {        
        //Incialmente no solicita serie
        int iResul  = 0;
     
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        //Coprueba si el producto solicita forzosamente numero de serie        
        try        
        {
            sQ = "SELECT solser FROM prods WHERE prod = '" + sProd.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                iResul = rs.getInt("solser");                                                                  
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }
        
        //Regresa el resultado
        return iResul;
                
    }//public static int iProdSolSer(Connection con, String sProd)
    
    
    /*Método para obtener QR de un timbrado*/
    public static RespuestaObtenerQRTimbrado obtenerQRTimbrado(wstimb.SolicitudObtenerQRTimbrado parameters) throws TimbradoObtenerQRTimbradoFallaValidacionFaultFaultMessage, TimbradoObtenerQRTimbradoFallaServicioFaultFaultMessage, TimbradoObtenerQRTimbradoFallaSesionFaultFaultMessage 
    {
        wstimb.Timbrado_Service service = new wstimb.Timbrado_Service();
        wstimb.Timbrado port = service.getPuertoTimbradoSeguro();
        return port.obtenerQRTimbrado(parameters);
    }

    /*Método para cancelar un timbre*/
    public static RespuestaCancelaTimbrado cancelaTimbrado(wstimb.SolicitudCancelaTimbrado parameters) throws TimbradoCancelaTimbradoFallaSesionFaultFaultMessage, TimbradoCancelaTimbradoFallaServicioFaultFaultMessage, TimbradoCancelaTimbradoFallaValidacionFaultFaultMessage 
    {
        wstimb.Timbrado_Service service = new wstimb.Timbrado_Service();
        wstimb.Timbrado port = service.getPuertoTimbradoSeguro();
        return port.cancelaTimbrado(parameters);
    }

    
    /*Método para obtener el estatus del timbrado*/
    public static RespuestaEstatusTimbrado estatusTimbrado(wstimb.SolicitudEstatusTimbrado parameters) throws TimbradoEstatusTimbradoFallaServicioFaultFaultMessage, TimbradoEstatusTimbradoFallaSesionFaultFaultMessage, TimbradoEstatusTimbradoFallaValidacionFaultFaultMessage 
    {
        wstimb.Timbrado_Service service = new wstimb.Timbrado_Service();
        wstimb.Timbrado port = service.getPuertoTimbradoSeguro();
        return port.estatusTimbrado(parameters);
    }

    
    /*Método para cancelación multiple de comprobantes*/
    public static RespuestaCancelaMultiple cancelaMultiple(wscance.SolicitudCancelaMultiple parameters) throws CancelacionesCancelaMultipleFallaServicioFaultFaultMessage, CancelacionesCancelaMultipleFallaSesionFaultFaultMessage 
    {
        wscance.Cancelaciones_Service service = new wscance.Cancelaciones_Service();
        wscance.Cancelaciones port = service.getPuertoCancelacionSeguro();
        return port.cancelaMultiple(parameters);
    }

    
    /*Método para obtener los acuses de cancelación del SAT*/
    public static RespuestaRecuperarAcuse recuperarAcuses(wscance.SolicitudAcuse parameters) throws CancelacionesRecuperarAcusesFallaSesionFaultFaultMessage, CancelacionesRecuperarAcusesFallaServicioFaultFaultMessage, CancelacionesRecuperarAcusesFallaValidacionFaultFaultMessage 
    {
        wscance.Cancelaciones_Service service = new wscance.Cancelaciones_Service();
        wscance.Cancelaciones port = service.getPuertoCancelacionSeguro();
        return port.recuperarAcuses(parameters);
    }

    
    /*Método para obtener el XML en caso de perdida*/
    public static RespuestaObtenerTimbrado obtenerTimbrado(wstimb.SolicitudObtenerTimbrado parameters) throws TimbradoObtenerTimbradoFallaValidacionFaultFaultMessage, TimbradoObtenerTimbradoFallaSesionFaultFaultMessage, TimbradoObtenerTimbradoFallaServicioFaultFaultMessage 
    {
        wstimb.Timbrado_Service service = new wstimb.Timbrado_Service();
        wstimb.Timbrado port = service.getPuertoTimbradoSeguro();
        return port.obtenerTimbrado(parameters);
    }

    
    /*Método para comprobar si la siere del usuario, su MAC y la llave son válidos en el servidor para poder continuar*/
    public static String vSerKeyU(java.lang.String sSerU, java.lang.String sKeyU, java.lang.String sMac) 
    {
        erws.ERWSServicio service = new erws.ERWSServicio();
        erws.ERWSPort port = service.getERWSPort();
        return port.vSerKeyU(sSerU, sKeyU, sMac);
    }
                            
    
    //Método para cancelar con el WS una factura
    public static int iCanFac(String sFolFisc, String sRFCLoc)
    {
        /*Obtiene el token de sguridad*/
        String sNewTok  = Star.sCreTokEsta(sRFCLoc);

        /*Si hubo error regresa error*/
        if(sNewTok==null)
            return -1;

        /*Tokeniza para obtener el token y el id de transacción*/
        java.util.StringTokenizer stk2 = new java.util.StringTokenizer(sNewTok, "|");
        sNewTok         = stk2.nextToken();
        String sTransId = stk2.nextToken();

        /*Crea el object factory para cancelar el o los comprobante*/
        wscance.ObjectFactory facCli = new wscance.ObjectFactory();

        /*Crea la lista de los UUDI a cancelar*/
        wscance.ListaCancelar lstUid = facCli.createListaCancelar();
        lstUid.getGuid().add(sFolFisc);                  

        /*Crea la solicitud para cancelación multiple*/
        wscance.SolicitudCancelaMultiple solicitud = new wscance.SolicitudCancelaMultiple();
        solicitud.setRFC(facCli.createSolicitudCancelaMultipleRFC(sRFCLoc));
        solicitud.setToken(facCli.createSolicitudCancelaMultipleToken(sNewTok));
        solicitud.setTransaccionID(Long.parseLong(sTransId));
        solicitud.setListaCancelar(facCli.createSolicitudCancelaMultipleListaCancelar(lstUid));

        /*Cancela los comprobantes*/
        RespuestaCancelaMultiple wsRes;
        try
        {
            wscance.Cancelaciones_Service servicio  = new wscance.Cancelaciones_Service();
            wscance.Cancelaciones puerto            = servicio.getPuertoCancelacion();
            wsRes                                   = puerto.cancelaMultiple(solicitud);  
            wsRes                                   = Star.cancelaMultiple(solicitud);                
        }
        catch(CancelacionesCancelaMultipleFallaServicioFaultFaultMessage | CancelacionesCancelaMultipleFallaSesionFaultFaultMessage expnWSPAC)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC);                                                                   
            return -1;                                                    
        }                        
                
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de prublic static iCanFac(String sFolFisc)
    
    
    //Método para obtener el RFC local
    public static String sGetRFCLoc(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        /*Obtiene el RFC local*/            
        String sRFCLoc  = "";
        try
        {                  
            sQ = "SELECT rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                                                 
                sRFCLoc   = rs.getString("rfc");                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }
        
        //Regresa el resultado
        return sRFCLoc;
        
    }//Fin de public static String sGetRFCLoc(Connection con)
    
    
    //Método para obtener la ruta de la empresa local
    public static String sGetRutCarp(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Trae la carpeta compartida del servidor
        String sCarp    = "";                    
        try
        {
            sQ = "SELECT rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        } 
        
        //Regresa el resultado
        return sCarp;
        
    }//Fin de public static String sGetRutCarp(Connection con)
    
                                   //anaqs,  almas,    lugs,  ubiad,                marcs, meds, model,     lins, fabs,         clasprod,               unids,   pes
    //MÃ©todo para saber si existe un anaquel, almacen, lugar, ubicacion adicional, marca, medida, modelos, linea, fabricante, clasificacion de producto, unidad, peso
    public static int iExiste(Connection con, String sCod, String tabla, String colCod)
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ      = ""; 

        //Comprueba si el almacÃ©n especÃ­fico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT "+colCod+" FROM "+ tabla +" WHERE "+colCod+" = '" + sCod.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, Star.class.getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getResource(Star.sRutIconEr))); 
            return -1;
        }  

        //Regresa el resutlado
        return iRes;
        
    }// Fin de un metodo que reemplazo 1000 lineas por menos de 40, exito (yn)
    
    //MÃ©todo para comprobar si un cliente existe
    public static int iExistCliProv(Connection con, String sCliProv, boolean esCliente)
    {
        //Si la conexiÃ³n es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexiÃ³n
            bSi = true;
            
            /*Abre la base de datos*/                   
            try 
            {
                con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
            } 
            catch(SQLException ex) 
            {    
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                //Mensajea y regresa error
                JOptionPane.showMessageDialog(null, Star.class + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
                return -1;
            }
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ      = ""; 
        
        //Comprueba si existe el cliente
        int iExis           = 0;
        try
        {
            if (esCliente)
                sQ = "SELECT codemp FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCliProv.trim() + "'";                        
            else
                sQ = "SELECT prov FROM provs WHERE CONCAT_WS('', ser, prov) = '" + sCliProv.trim() + "'";           
            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, Star.class.getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getResource(Star.sRutIconEr))); 
            return -1;
        } 
        
        //Si la conexiÃ³n es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;                       
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistCliProv
    
    
    //Método para comprobar si el tipo de pago existe
    public static int iExistTipPag(Connection con, String sTip)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si existe el tipo de pago
        int iExis           = 0;
        try
        {
            sQ = "SELECT cod FROM pags WHERE cod = '" + sTip.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        } 
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistTipPag(Connection con, String sTip)
    
    
    //Método para comprobar si el concepto de pago existe
    public static int iExistConPag(Connection con, String sConcep)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si existe el tipo de concepto
        int iExis           = 0;
        try
        {
            sQ = "SELECT concep FROM conceppag WHERE concep = '" + sConcep.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        } 
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistConPag(Connection con, String sConcep)
    
    
    //Método para comprobar si un cliente existe
    public static int iExistCli(Connection con, String sCli)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;            
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe el cliente
        int iExis           = 0;
        try
        {
            sQ = "SELECT codemp FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;                       
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistCli(Connection con, String sCli)
    
    
    //Método para obtener la serie de un cliente
    public static String strGetSerCli(Connection con, String sCli)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return null;            
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la serie del ciente
        String strSerCli    = "";
        try
        {
            sQ = "SELECT ser FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                strSerCli   = rs.getString("ser");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;                       
        }
        
        //Regresa el resultado
        return strSerCli;
        
    }//Fin de public static String strGetSerCli(Connection con, String sCli)
    
    
    //Método para comprobar si un proveedor existe
    public static int iExisProv(Connection con, String sProv)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Guarda la bandera para saber que era nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                                    
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe el proveedor
        int iExis           = 0;
        try
        {
            sQ = "SELECT prov FROM provs WHERE CONCAT_WS('', ser, prov) = '" + sProv.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        } 

        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
                        
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExisProv(Connection con, String sProv)
    
    
    //Método para obtener las monedas y cargarlas en un combo
    public static int iCargMonCom(Connection con, javax.swing.JComboBox jCom)
    {
        /*Borra los items en el combobox de monedas*/
        jCom.removeAllItems();

        /*Agrega la moneda vacia*/
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todas las monedas actualizadas y cargalas en el combo
        try
        {
            sQ = "SELECT mon FROM mons";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("mon"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargMonCom(Connection con, javax.swing.JComboBox jCom)            
           
    
    //Método para obtener las unidades y cargarlas en un combo
    public static int iCargUnidCom(Connection con, javax.swing.JComboBox jCom)
    {
        //Borra todos los items
        jCom.removeAllItems();

        //Agrega el item vacio
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todas las unidades actualizadas y cargalas en el combo
        try
        {
            sQ = "SELECT cod FROM unids";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("cod"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargUnidCom(Connection con, javax.swing.JComboBox jCom)            
    
    
    //Método para obtener los impuestos y cargarlos en un combo
    public static int iCargImpueCom(Connection con, javax.swing.JComboBox jCom)
    {
        //Borra todos los items
        jCom.removeAllItems();

        //Agrega el item vacio
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todos los impuestos actualizados y cargalos en el combo
        try
        {
            sQ = "SELECT codimpue FROM impues";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("codimpue"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargImpueCom(Connection con, javax.swing.JComboBox jCom)            
    
    
    //Método para obtener los colores y cargarlos en un combo
    public static int iCargColoCom(Connection con, javax.swing.JComboBox jCom)
    {
        //Borra todos los items
        jCom.removeAllItems();

        //Agrega el elemento vacio
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todas las monedas actualizadas y cargalas en el combo
        try
        {
            sQ = "SELECT cod FROM colos";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("cod"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargColoCom(Connection con, javax.swing.JComboBox jCom)            
    
    
    //Método para saber si un color existe
    public static int iExisColo(Connection con, String sColo)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        //Comprueba si el color existe
        int iRes            =    0;
        try
        {
            sQ = "SELECT cod FROM colos WHERE cod = '" + sColo.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }  

        //Regresa el resultado
        return iRes;
        
    }//Fin de public static int iExisColo(Connection con, String sColo)            
    
    
    //Método para obtener los almacenes y cargarlas en un combo
    public static int iCargAlmaCom(Connection con, javax.swing.JComboBox jCom)
    {
        //Borra todos los items del combo
        jCom.removeAllItems();

        //Agrega el elemento vacio
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todos los almacenes actualizados y cargalos en el combo
        try
        {
            sQ = "SELECT alma FROM almas WHERE alma <> '" + Star.strAlmaActFij + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("alma"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                     
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargAlmaCom(Connection con, javax.swing.JComboBox jCom)            
    
    
    //Método para obtener el almacén de ventas
    public static String sGetAlmaVta(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el almacén de ventas
        String sAlma        = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'almavtaf'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sAlma   = rs.getString("extr");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  

        //Regresa el resultado
        return sAlma;
        
    }//Fin de public static String sGetAlmaVta(Connection con)            
    
    
    //Método para obtener la moneda nacional
    public static String sGetMonNac(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el almacén de ventas
        String sMon        = "";
        try
        {
            sQ = "SELECT mon FROM mons WHERE mn = 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sMon   = rs.getString("mon");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  

        //Regresa el resultado
        return sMon;
        
    }//Fin de public static String sGetMonNac(Connection con)            
    
    
    //Método para registrar el log de entrada o salida de los usuarios
    public static int iRegUsr(Connection con, String sEntSal)
    {        
        //Si la conexión es nula entonces
        boolean     bSi     = false;
        if(con==null)
        {
            //Coloca la bandera para saber que se abrió la base de datos desde aqui
            bSi             = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                                    
        }
        
        //Declara variables de bases de datos
        Statement   st;                
        String      sQ;                 

        //Regista la salida o entrada del usuario
        try 
        {                
            sQ = "INSERT INTO logini(  estac,                                       sucu,                                     nocaj,                                    falt,           entsal) " + 
                          "VALUES('" + Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now(),      " + sEntSal + ")";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }

        //Si se abrió la base de datos localmente entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;                        
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iRegUsr(Connection con, String sEntSal)
    
    
    //Método para redondear cantidades
    public static double dRound(double value, int places) 
    {        
        BigDecimal bd   = new BigDecimal(value);
        bd              = bd.setScale(places, RoundingMode.HALF_UP);        
        return bd.doubleValue();
    }
    
    
    //Borra el registro del usuario en la tabla de logestac
    public static int iDesLog()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return -1;            

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si existe el usuario en la tabla de los logeados*/
        String sID;
        try
        {
            sQ = "SELECT id_id FROM logestac WHERE estac = '" + Login.sUsrG + "' AND sucu = '" + Star.sSucu + "' AND NOCAJ = '" + Star.sNoCaj + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe y regresa*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return -1;

                //Regresa que no hay datos pero todo va bien
                return 0;
            }
            /*Else si hay datos entonces obtiene el resultado*/
            else
                sID = rs.getString("id_id");         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
        }
	
        /*Borra el usuario de los logeados*/
        try 
        {                
            sQ = "DELETE FROM logestac WHERE id_id = " + sID;                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                                                    
         }                    
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return -1;

        //Regresa que todo gue bien
        return 0;
        
    }/*Fin de public satatic int iDesLog()*/
        
    
    //Método para obtener el host de los usuarios
    public static String sGetHostUsr(Connection con, String sUsr)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el host del usuario
        String sHost        = "";
        try
        {
            sQ = "SELECT host FROM logestac WHERE estac = '" + sUsr.trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sHost   = rs.getString("host");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  

        //Regresa el resultado
        return sHost;
        
    }//Fin de public static String sGetHostUsr(Connection con, String sUsr)
    
    
    //Método para obtener el puerto de los usuarios
    public static String sGetPortUsr(Connection con, String sUsr)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el host del usuario
        String sResp        = "";
        try
        {
            sQ = "SELECT port FROM logestac WHERE estac = '" + sUsr.trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sResp   = rs.getString("port");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                                                    
        }  

        //Regresa el resultado
        return sResp;
        
    }//Fin de public static String sGetPortUsr(Connection con, String sUsr)
    
    
    //Método para borrar, insertar o actualizar al usuario en la tabla de logestac
    public static int iRegLogUsr(Connection con, String sEntSalDel, String sUsr, String sPort, String sPortUDP)
    {
        //Si la conexión es nula entonces
        boolean     bSi     = false;
        if(con==null)
        {
            //Coloca la bandera para saber que la conexión se abrió localmente
            bSi             = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                       
        }

        //Declara variables de la base de datos
        Statement   st;                
        String      sQ;                                             

        //Si es para borrar al usuario entonces
        if(sEntSalDel.compareTo("del")==0)
        {                        
            //Borra el usuario actual de la tabla de logestac
            try 
            {            
                sQ = "DELETE FROM logestac WHERE estac = '" + Login.sUsrG + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {                 
                //Procesa el error y regresa error
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return -1;                                                        
            }            
        }
        else if(sEntSalDel.compareTo("act")==0)
        {
            //Actualiza el registro con el nuevo usuario
            try 
            {                
                sQ = "UPDATE logestac SET "
                        + "estac            = '" + sUsr + "' "                        
                        + "WHERE estac      = '" + Login.sUsrG + "' AND sucu = '" + Star.sSucu.replace("'", "''") + "' AND nocaj = '" + Star.sNoCaj.replace("'", "''") + "'";                          
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa error
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return -1;                                           
            }            
        }
        //Else si es un nuevo ingreso entonces
        else if(sEntSalDel.compareTo("new")==0)
        {
            //Declara variables locales
            ResultSet   rs;
            
            /*Comprueba si ya existe el usuario logeado en la base de datos*/
            boolean bSiExis = false;
            try
            {
                sQ = "SELECT estac FROM logestac WHERE estac = '" + Login.sUsrG + "' AND sucu = '" + Star.sSucu + "' AND nocaj = '" + Star.sNoCaj + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces si existe, coloca la bandera*/
                if(rs.next())
                    bSiExis = true;
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa error
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return -1;                                                            
            }        

            /*Obtiene el nombre host del equipo local*/
            String sHost;
            try
            {
                sHost   = java.net.InetAddress.getLocalHost().getHostName();
            }
            catch(UnknownHostException expnUnknowHos)
            {
                //Procesa el error y regresa error
                Star.iErrProc(Star.class.getName() + " " + expnUnknowHos.getMessage(), Star.sErrUnknowHos, con);                                                                   
                return -1;                            
            }                        

            /*Si existe entonces*/
            if(bSiExis)
            {
                //Actualiza el registro con los nuevos datos para el socket
                try 
                {                
                    sQ = "UPDATE logestac SET "
                            + "proxlog          = DATE_ADD(CURTIME(), INTERVAL 30 SECOND), "
                            + "host             = '" + sHost.replace("'", "''") + "', "
                            + "port             = '" + sPort + "', "                        
                            + "portudp          = '" + sPortUDP + "' "                        
                            + "WHERE estac      = '" + Login.sUsrG.replace("'", "''") + "' AND sucu = '" + Star.sSucu.replace("'", "''") + "' AND nocaj = '" + Star.sNoCaj.replace("'", "''") + "'";                          
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return -1;                                               
                 }                    
            }
            /*Else no existe entonces*/
            else
            {               
                //Inserta al usuario en la tabla para el socket
                try 
                {                
                    sQ = "INSERT INTO logestac(estac,                                       sucu,                                     nocaj,                                    proxlog,                                      host,                                 port,            portudp) " + 
                                  "VALUES('" + Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      DATE_ADD(CURTIME(), INTERVAL 30 SECOND), '" + sHost.replace("'", "''") + "', '" +   sPort + "', '" + sPortUDP + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                    return -1;                                                
                 }                    
            }

        }//Fin de else if(sEntSalDel.compareTo("new")==0)

        //Si la conexión se abrió localmente entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iRegLogUsr(Connection con, String sEntSalDel, String sUsr, String sPort, String sPortUDP)
    
    
    //Método para iniciar los 2 sockets el UDP y el normal
    public static void vIniSocks()
    {
        /*Contiene el puerto que el sistema genero automáticamente*/
        final int iPort;
        
        /*Crea el servidor socket con un puerto dinámico para el socket*/                
        try
        {
            /*Crea el socket*/
            Star.sockSrv        = new java.net.ServerSocket(0);            
            
            /*Obtiene el puerto que el sistema asigno automáticamente*/
            iPort               = Star.sockSrv.getLocalPort();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;                            
        }                 
        
        /*Contiene el puerto que el sistema genero automáticamente para el socket UDP*/
        final int iPortUDP;
        
        /*Crea servidor socket UDP con un puerto dinámico*/                
        try
        {
            /*Crea el socket*/
            Star.socSrvUDP  = new java.net.DatagramSocket(0);            
            
            /*Obtiene el puerto que el sistema asigno automáticamente*/
            iPortUDP        = Star.socSrvUDP.getLocalPort();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;                            
        }                                

        //Inserta el usuario en la tabla de logestac
        Star.iRegLogUsr(null, "new", "", Integer.toString(iPort), Integer.toString(iPortUDP));
                
        /*Crea el thread para que este esperando por el socket TCP instrucciones*/
        Star.thServ  = new Thread()
        {
            @Override
            public void run()
            {
                /*Función para procesar esta parte*/
                vSock();                              
            }
        };
        Star.thServ.start();        
        
        /*Crea el thread para que este esperando por el socket UDP instrucciones*/
        Star.thServUDP  = new Thread()
        {
            @Override
            public void run()
            {
                /*Función para procesar esta parte*/
                vSockUDP();                              
            }
        };
        Star.thServUDP.start();        
        
    }//Fin de public static void vIniSocks()
    
    /*Función que se llama desde unthread para que el servidor TCP este esperando conexiones de los clientes*/
    private static void vSock()
    {                        
        /*Bucle infinito para que este respondiendo al cliente*/
        while(true)
        {
            /*Acepta conexiones*/            
            final java.net.Socket socCli;
            try
            {        
                socCli = Star.sockSrv.accept();                        
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;                            
            }                
            
            /*Thread pra procesar la solicitud del cliente*/
            (new Thread()
            {
                @Override
                public void run()
                {
                    vProsCli(socCli);
                }
            }).start();
                                    
        }/*Fin de while(true)*/                                               
        
    }/*Fin de private void vSock*/
                
    
    /*Función que se llama desde un thread para que el servidor UDP este esperando conexiones de los clientes*/
    private static void vSockUDP()
    {                        
        /*Objeto para recibir información*/
        byte[] bReDat   = new byte[1024];                     
            
        /*Bucle infinito para que este respondiendo al cliente*/
        while(true)
        {
            /*Crea el datagrama para recibir el datagrama del cliente*/
            java.net.DatagramPacket rPac = new java.net.DatagramPacket(bReDat, bReDat.length);                   
            
            /*Acepta conexiones*/                        
            try
            {        
                /*Espera algún mensaje del cliente*/
                Star.socSrvUDP.receive(rPac);                 
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;                                           
            }                
            
            /*Declara variable final para el thread*/
            final java.net.DatagramPacket rPacFi    = rPac;
            
            /*Thread pra procesar la solicitud del cliente*/
            (new Thread()
            {
                @Override
                public void run()
                {
                    vProsUDP(rPacFi);                    
                }
            }).start();
                                    
        }/*Fin de while(true)*/                                               
        
    }/*Fin de private void vSockUDP*/
        
    
    /*Función para decirdir lo que el socket cliente desea*/
    private static synchronized void vProsCli(final java.net.Socket socCli)
    {
        /*Crea el objeto para poder recibir mensajes del cliente*/
        java.io.DataInputStream in;
        try
        {
            in = new java.io.DataInputStream(socCli.getInputStream());
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;                            
        }

        /*Obtiene el mensaje del cliente*/            
        String sMsj;
        try
        {
            sMsj    = in.readUTF();                
        }
        catch(IOException expnIO)
        {
            /*Regresa*/
            return;
        }                
        
        /*Si el mensaje es salir entonces*/
        if(sMsj.compareTo("exit")==0)
        {
            /*Cierra la conexión del socket*/
            try
            {
                socCli.close();
            }
            catch(IOException expnIO)
            {                
                return; 
            }                

            /*Función para hacer el cerrado de la aplicación*/
            vSalS();           
        }
        /*Else if el cliente quiere enviar video entonces*/
        else if(sMsj.compareTo("camenvi")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Envia el video de la cámara*/
                    vSockCamRecib(socCli);
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                        
                    }                
                }
            }).start();
        }
        //Else es mensaje de petición para saber si esta conectado este usuario o no
        else if(sMsj.compareTo("usrcon")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    //Responde que si esta conectado el usuario
                    vSockUsrCon(socCli);
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                        
                    }                
                }
            }).start();
        }
        /*Else if el cliente quiere recibir video solamente entonces*/
        else if(sMsj.compareTo("camrecibsol")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Recibe el video del cliente*/
                    vSockCamEnvi(socCli, false, null);
                                        
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                                   
                    }                                                        
                }
            }).start();
        }
        /*Else if el cliente quiere recibir video entonces*/
        else if(sMsj.startsWith("camrecib"))
        {
            /*Declara el mensaje como final para tratarlo en el thread*/
            final String sMsjFi = sMsj;
            
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Obtiene el nombre del usuario al que se le tiene que obtener el video*/                    
                    java.util.StringTokenizer stk = new java.util.StringTokenizer(sMsjFi, "|");
                    stk.nextToken();
                    String sUsr = stk.nextToken();
                            
                    /*Envia el video al cliente*/
                    vSockCamEnvi(socCli, true, sUsr);
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                                            
                    }                                    
                }
            }).start();
        }        
        /*Else if el mensaje es de enviar audio entonces en un thread haz el envio de audio*/
        else if(sMsj.compareTo("audrecib")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Envia el audio por el socket*/
                    vSockAudEnvi(socCli);
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                                       
                    }                
                }
            }).start();
        }
        /*Else if el mensaje es de recibir audio entonces realiza esto en un thread*/
        else if(sMsj.compareTo("audenvi")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Recibe el audio del cliente*/
                    vSockAudRecib(socCli);
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                                        
                    }                
                }
            }).start();
        }
        /*Else if el mensaje es de enviar capturas de pantalla entonces realizalo en un thread*/
        else if(sMsj.compareTo("panta")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {                    
                    /*Envia capturas de pantalla para el remoto*/
                    vSockVid(socCli);                          
                    
                    /*Cierra la conexión del socket*/
                    try
                    {
                        socCli.close();
                    }
                    catch(IOException expnIO)
                    {                                        
                    }                
                }
            }).start();
        }
                            
    }/*private synchronized void vProsCli(java.net.Socket socCli)*/
        
    
    /*Función para decirdir lo que el socket UDP cliente desea*/
    private static synchronized void vProsUDP(final java.net.DatagramPacket dgPac)
    {
        /*Obtiene el mensaje del cliente*/            
        String sMsj = new String(dgPac.getData()).trim();
        
        /*Si el mensaje es de enviar audio entonces en un thread haz el envio de audio*/
        if(sMsj.compareTo("audrecib")==0)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Envia el audio por el socket UDP*/
                    vSockAudEnviUDP(dgPac);                      
                }
            }).start();
        }
                            
    }/*private synchronized void vProsCli(java.net.Socket socU)*/
        
    
    /*Función para enviar y recibir video por el socket hacia el cliente*/
    private static synchronized void vSockVid(java.net.Socket socCli)
    {
        /*Objeto para obtener la imágen de la pantalla*/
        java.awt.image.BufferedImage cap;
        
        /*Búcle inifinito para que este mandando el video por el socket*/
        while(true)
        {
            /*Obtiene imágen de la pantalla*/           
            try
            {
                java.awt.Robot rob = new java.awt.Robot();
                java.awt.Rectangle rec = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                cap = rob.createScreenCapture(rec);
            }   
            catch(AWTException expnAWT)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnAWT.getMessage(), Star.sErrAWT);                                                                   
                return;
            }                    
            
            /*Si no hay imágen que continue*/
            if(cap==null)
                continue;
            
            /*Objeto para contener los bytes de la imágen*/
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            
            /*Mete la imágen buffered dentro del objeto de bytes*/
            try
            {
                 javax.imageio.ImageIO.write(cap,"PNG",baos);                 
            }
            catch(IOException expnIO)
            {                
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                                                
            
            /*Flushea el buffer*/
            try
            {
                baos.flush();
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                

            /*Crea el arreglo de bytes*/
            byte[] imageInByte = baos.toByteArray();
            
            /*Cierra el buffer de bytes*/
            try
            {
                baos.close();
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                
            
            /*Envia por el socket la imágen en bytes*/
            try
            {
                socCli.getOutputStream().write(imageInByte);
            }
            catch(IOException expnIO)
            {
                /*Falla regresa*/
                return;
            }                            
            
        }/*Fin de while(true)*/                    

    }/*Fin de private synchronized void vSockVid(java.net.Socket socCli)*/
                

    /*Función para recibir video del cliente de la cámara*/
    private static void vSockCamRecib(java.net.Socket socCli)
    {                
        /*Si es nulo la forma de la cámara entonces*/
        if(Star.imgCa==null)
        {                                
            /*Instanciala y muestrala*/
            Star.imgCa = new ImgCam("");
            Star.imgCa.setVisible(true);
        }

        /*Objeto imágen*/
        java.awt.Image img;
        
       /*Bucle infinito para que este respondiendo al cliente*/
       while(true)
        {    
            /*Lee la imégen del servidor*/                    
            try
            {                
                /*Obtiene la imágen del servidor y colocala en el control*/
                img                     = javax.imageio.ImageIO.read(javax.imageio.ImageIO.createImageInputStream(socCli.getInputStream()));                                                
                if(img!=null)
                {                                      
                    /*Obtiene en un objeto de imageicon*/                                                    
                    ImageIcon img1      = new ImageIcon(img);

                    /*Crea la imágen para redimensionar la imágen del icono*/
                    java.awt.Image im       = img1.getImage(); 
                    java.awt.Image newimg   = im.getScaledInstance( 400,400,  java.awt.Image.SCALE_SMOOTH );  

                    /*Crea el nuevo ícono y asignalo al label*/                      
                    img1                    = new ImageIcon(newimg);                        
                    Star.imgCa.jLImg.setIcon(img1);                                                         
                }
            }
            catch(IOException expnIO)
            {                        
                //Procesa el error
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                                   
            }                                                                   
            
        }/*Fin de while(true)*/                                               
        
    }/*Fin de private void vSockCamRecib*/
    
    
    /*Función para enviar video al cliente de la cámara*/
    private static void vSockCamEnvi(java.net.Socket socCli, boolean bAbreF, String sUsr)
    {                
        /*Si es nulo la forma de la cámara entonces y se tiene que abrir*/
        if(bAbreF && Star.imgCa==null)
        {                                
            /*Instanciala y muestrala*/
            Star.imgCa = new ImgCam(sUsr);            
            Star.imgCa.setVisible(true);
        }

        /*Obtiene la cámara por default del sistema*/
        webcam = com.github.sarxos.webcam.Webcam.getDefault();

        /*Abre la càmara*/                    
        if(!webcam.getLock().isLocked())
        {
            if(!webcam.isOpen())
                webcam.open();                                    
        }                                            

        /*Inicialmente no se a cerrado la ventana de videollamada en la parte de video*/
        Star.bBanVid(false, true);
                
       /*Bucle infinito para que este respondiendo al cliente*/
       while(true)
        {                
            /*Si el socket ya esta cerrado entonces*/
            if(socCli.isClosed())
            {
                /*Cierra la cámara*/
                if(webcam!=null)
                    webcam.close();
                
                /*Si la forma no es nula entonces*/
                if(Star.imgCa!=null)
                {
                    /*Que no sea visible y lebera el recurso*/
                    Star.imgCa.setVisible(false);
                    Star.imgCa.dispose();
                    Star.imgCa = null;
                }
                
                /*Regresa*/
                return;
            }
            
            /*Si el usuario ya cerro la ventana de video llamada entonces*/
            if(Star.bBanVid(true, false))
            {                
                /*Resetea la bandera*/
                Star.bBanVid(false, true);
                
                /*Cierra la cámara*/
                if(webcam!=null)
                    webcam.close();
                            
                /*Si la forma no es nula entonces*/
                if(Star.imgCa!=null)
                {
                    /*Que no sea visible y lebera el recurso*/
                    Star.imgCa.setVisible(false);
                    Star.imgCa.dispose();
                    Star.imgCa = null;
                }
                                
                /*Regresa*/
                return;
            }
            
            /*Obtiene la imàgen en un buffer*/
            BufferedImage img       = webcam.getImage();

            /*Si no hay imágen que continue*/
            if(img==null)
                continue;
            
            /*Objeto para contener los bytes de la imágen*/
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            
            /*Mete la imágen buffered dentro del objeto de bytes*/
            try
            {
                 javax.imageio.ImageIO.write(img,"PNG",baos);                 
            }
            catch(IOException expnIO)
            {                
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                                                
            
            /*Flushea el buffer*/
            try
            {
                baos.flush();
            }
            catch(IOException expnIO)
            {                
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                                                

            /*Crea el arreglo de bytes*/
            byte[] imageInByte = baos.toByteArray();
            
            /*Cierra el buffer de bytes*/
            try
            {
                baos.close();
            }
            catch(IOException expnIO)
            {                
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return;
            }                                                
                        
            /*Envia por el socket la imágen en bytes*/
            try
            {
                socCli.getOutputStream().write(imageInByte);
            }
            catch(IOException expnIO)
            {
                /*Cierra la cámara*/
                if(webcam!=null)
                    webcam.close();
                
                /*Si la forma no es nula entonces*/
                if(Star.imgCa!=null)
                {
                    /*Que no sea visible y lebera el recurso*/
                    Star.imgCa.setVisible(false);
                    Star.imgCa.dispose();
                    Star.imgCa = null;
                }
                                    
               /*Regresa*/
               return;
            }                            
            
        }/*Fin de while(true)*/                                               
        
    }/*Fin de private void vSockCamEnvi*/
            
    
    /*Función para enviar sonido del micro por el socket*/
    private static void vSockAudEnvi(java.net.Socket socCli)
    {
        /*Si el microfono no esta soportado entonces regresa*/                
        final DataLine.Info info = new DataLine.Info(TargetDataLine.class, Star.format); 
        if(!AudioSystem.isLineSupported(info)) 
            return;                   
        
        /*Si el microfono no esta abierto entonces*/
        if(Star.lineMic!=null)
        {            
            if(!Star.lineMic.isOpen())
            {                
                /*Obtiene y abre la lìnea de entrada*/
                try 
                {            
                    Star.lineMic = (TargetDataLine) AudioSystem.getLine(info);            
                    Star.lineMic.open(Star.format);                    
                } 
                catch(LineUnavailableException expnLinUnav) 
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav);                                                                   
                    return;
                }
            }
        }
        /*Else es nulo entonces*/
        else
        {
            /*Obtiene y abre la lìnea de entrada*/
            try 
            {            
                Star.lineMic = (TargetDataLine) AudioSystem.getLine(info);            
                Star.lineMic.open(Star.format);                    
            } 
            catch(LineUnavailableException expnLinUnav) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav);                                                                   
                return;
            }
        }

        /*Crea el objeto para poder enviar mensajes al cliente*/
        java.io.DataOutputStream out;
        try
        {
            out = new java.io.DataOutputStream(socCli.getOutputStream());
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;
        }
        
        /*Crea el objeto para enviar el sonido del microfono por el socket*/
        java.io.ByteArrayOutputStream outB  = new java.io.ByteArrayOutputStream();
        int numBytesRead;
        byte[] data;

        /*Crea el arreglo de bytes con el tamaño necesario de bytes a enviar*/
        data = new byte[Star.lineMic.getBufferSize() / 5];
        
        /*Comienza a capturar del microfono*/
        Star.lineMic.start();                         
        
        /*Bucle infinito para que este respondiendo al cliente*/
        while(true)
         {  
            /*Obtiene la cantidad de bytes que se van a transmitir a la bocina*/
            numBytesRead =  Star.lineMic.read(data, 0, data.length);
            
            /*Guarda en el arreglo de bytes lo que se van a la bocina*/
            outB.write(data, 0, numBytesRead);                        

            /*Envia el sonido por el socket*/
            try
            {                
                 out.write(data);                 
            }
            catch(IOException expnIO)
            {                
                /*Regresa*/                
                return;
            }                                    
            
         }/*Fin de while(true)*/                                                                   
                            
    }/*Fin de private void vSockAudEnvi*/
    
    
    /*Función para enviar sonido del micro por el socket UDP*/
    private static void vSockAudEnviUDP(java.net.DatagramPacket dgPac)
    {
        /*Si el microfono no esta soportado entonces regresa*/                
        final DataLine.Info info = new DataLine.Info(TargetDataLine.class, Star.format); 
        if(!AudioSystem.isLineSupported(info)) 
            return;                   
        
        /*Si el microfono no esta abierto entonces*/
        if(Star.lineMic!=null)
        {            
            if(!Star.lineMic.isOpen())
            {                
                /*Obtiene y abre la lìnea de entrada*/
                try 
                {            
                    Star.lineMic = (TargetDataLine) AudioSystem.getLine(info);            
                    Star.lineMic.open(Star.format);                    
                } 
                catch(LineUnavailableException expnLinUnav) 
                {
                    //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav);                                                                   
                    return;
                }
            }
        }
        /*Else es nulo entonces*/
        else
        {
            /*Obtiene y abre la lìnea de entrada*/
            try 
            {            
                Star.lineMic = (TargetDataLine) AudioSystem.getLine(info);            
                Star.lineMic.open(Star.format);                    
            } 
            catch(LineUnavailableException expnLinUnav) 
            {
                //Procesa el error y regresa
                Star.iErrProc(Star.class.getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav);                                                                   
                return;
            }
        }

        /*Crea la direcciín y puerto a donde se mandara el sonido al cliente*/
        java.net.InetAddress iaAdd  = dgPac.getAddress();                   
        int port                    = dgPac.getPort();
                
        /*Crea el objeto para enviar el sonido del microfono por el socket*/
        java.io.ByteArrayOutputStream outB  = new java.io.ByteArrayOutputStream();
        int numBytesRead;
        byte[] data;

        /*Crea el arreglo de bytes con el tamaño necesario de bytes a enviar*/
        data = new byte[Star.lineMic.getBufferSize() / 5];
        
        /*Comienza a capturar del microfono*/
        Star.lineMic.start();                         
        
        /*Inicialmente no se a cerrado la ventana para terminar la comunicación en la parte del sonido*/
        Star.bBanVidAud(false, true);
        
        /*Bucle infinito para que este respondiendo al cliente*/
        while(true)
         {                      
             /*Si el socket esta cerrado entonces*/
             if(Star.socSrvUDP.isClosed())
             {
                 /*Cierra el microfono y regresa*/
                 Star.lineMic.close();
                 return;
             }
             
             /*Si el usuario ya cerro la ventana de video llamada entonces*/
            if(Star.bBanVidAud(true, false))
            {                
                /*Resetea la bandera*/
                Star.bBanVidAud(false, true);
                
                /*Cierra el microfono y regresa*/
                 Star.lineMic.close();
                 return;
            }
                        
            /*Obtiene la cantidad de bytes que se van a transmitir a la bocina*/
            numBytesRead =  Star.lineMic.read(data, 0, data.length);
            
            /*Guarda en el arreglo de bytes lo que se van a la bocina*/
            outB.write(data, 0, numBytesRead);                        
            
            /*Crea el paquete datagrama a enviar*/
            java.net.DatagramPacket dgPacEn =   new java.net.DatagramPacket(data, data.length, iaAdd, port);
            
            /*Envia el sonido por el socket*/
            try
            {                
                 Star.socSrvUDP.send(dgPacEn);
            }
            catch(IOException expnIO)
            {                
                /*Regresa*/                
                return;
            }                                    
            
         }/*Fin de while(true)*/                                                                   
                            
    }/*Fin de private void vSockAudEnviUDP*/
    
    
    /*Función para recibir el sonido del micro del cliente*/
    private static void vSockAudRecib(java.net.Socket socCli)
    {
        /*Crea el objeto para poder recibir mensajes del cliente*/
        java.io.DataInputStream in;
        try
        {
            in = new java.io.DataInputStream(socCli.getInputStream());
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;
        }
        
        /*Bucle infinito para que este leyendo el sonido del micro del cliente*/
        while(true)
         {                                                                                                                                     
             /*Lee el sonido del cliente y reproducelo*/                    
             try
             {
                 /*Crea el audioinputstream*/
                 javax.sound.sampled.AudioInputStream aud = new javax.sound.sampled.AudioInputStream(in, format, in.read());
                 try
                 {
                     javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                     clip.open(aud);
                     clip.start();
                 }
                 catch(LineUnavailableException expnLinUnav)
                 {
                     //Procesa el error y regresa
                    Star.iErrProc(Star.class.getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav);                                                                   
                    return;
                 }                    
             }
             catch(IOException expnIO)
             {                        
                 /*Agrega enel log y quita la imágen*/
                 Login.vLog(expnIO.getMessage());                                                                               
             }                                                       

         }/*Fin de while(true)*/                                                                   
                            
    }/*Fin de private void vSockAudRecib*/

    
    //Responde que si esta conectado el usuario
    private static void vSockUsrCon(java.net.Socket socCli)
    {
        /*Declara objetos para poder mandarle mensaje al servidor*/
        java.io.OutputStream        outS;
        java.io.DataOutputStream    out;
        try
        {
            outS   = socCli.getOutputStream();
            out= new java.io.DataOutputStream(outS);
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return;
        }

        /*Manda mensaje al servidor*/
        try
        {
            out.writeUTF("1");
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                              
        }
        
    }//Fin de private void vSockUsrCon(java.net.Socket socCli)
    
    
    /*Función para deslogearse del sistema y salir del mismo*/
    private static synchronized void vSalS()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;            
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        /*Registra que se esta deslogeando el usuario actual*/
        try 
        {                
            sQ = "DELETE FROM logestac WHERE estac = '" + Login.sUsrG + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                        
         }    
	        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;
        
        /*Sal de la aplicación*/
        System.exit(0);
        
    }/*Fin de private void vSalS()*/
        
    
    //Método para salir del sistema desde la forma principal
    public static void vExitAp()
    {
        /*Termina el thread del socket TCP servidor*/
        if(Star.thServ!=null)
            Star.thServ.interrupt();
        
        /*Termina el thread del socket UDP servidor*/
        if(Star.thServUDP!=null)
            Star.thServUDP.interrupt();

        //Registra el deslog del actual usuario
        if(Star.iRegUsr(null, "1")==-1)
            return;
        
        //Quita el registro del usuario en la tabla de logestac
        if(Star.iDesLog()==-1)
            return;
        
        //Sal del sistema
        System.exit(0);                
    }
    
    
    //Método para saber si un usuario esta conectado
    public static int iUsrCon(Connection con, String sUsr)
    {                        
        //Si se tiene que abrir la conexión entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que la conexión se abrió localmente
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                      
        }                    
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        //Comprueba si el usuario esta en conectado
        int iCon = 0;
        try
        {
            sQ = "SELECT logestac.SUCU, logestac.NOCAJ, estacs.NOM FROM logestac LEFT OUTER JOIN estacs ON estacs.ESTAC = logestac.ESTAC WHERE logestac.ESTAC = '" + sUsr.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces
            if(rs.next())
            {
                //Socket para saber si el usuario esta conectado o no
                java.net.Socket     socUsrCon;
    
                //Obtiene el puerto del usuario
                String sPortLoc     = Star.sGetPortUsr(con, sUsr.trim());        

                //Si hubo error entonces regresa error
                if(sPortLoc==null)
                    return -1;

                //Obtiene el host del usuario
                String sHost    = Star.sGetHostUsr(con, sUsr.trim());

                //Si hubo error entonces regresa error
                if(sHost==null)
                    return -1;

                /*Conecta con el servidor*/                    
                try
                {
                    socUsrCon   = new java.net.Socket(sHost, Integer.parseInt(sPortLoc));
                }
                catch(IOException expnIO)
                {
                    //Devuelve que no esta conectado
                    return 0;
                }                    
                /*Declara objetos para poder mandarle mensaje al servidor*/
                java.io.OutputStream        outS;
                java.io.DataOutputStream    out;
                try
                {
                    outS   = socUsrCon.getOutputStream();
                    out= new java.io.DataOutputStream(outS);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return -1;
                }

                /*Manda mensaje al servidor*/
                try
                {
                    out.writeUTF("usrcon");
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return -1;
                }
                
                //Declara objetos para poder recibir mensajes del servidor
                java.io.InputStream         inS;
                java.io.DataInputStream     in;
                try
                {
                    inS = socUsrCon.getInputStream();
                    in  = new java.io.DataInputStream(inS);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return -1;
                }
                //Recibe mensajes del servidor
                String sMsj="1";
//                try
//                {
//                    System.out.println("1");
//                    sMsj    = in.readUTF();
//                    System.out.println("2");
//                }
//                catch(Exception e){
//                    System.out.println("error");
//                return -1;
//                 }
//                
//                System.out.println("6");
                /*Cerra el socket*/
                try
                {
                    socUsrCon.close();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa error
                    Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                    return -1;
                }

                //Si el mensaje es 1 entonces coloca la bandera para saber que si esta conectado
                if(sMsj.compareTo("1")==0)
                    iCon    = 1;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }            

        //Si se tiene que cerrar la conexión entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }   
        
        //Regresa que resultado
        return iCon;
                            
    }//Fin de public static int iUsrCon(String sUsr)
    
    
    //Método para saber si el sistema esta configurado para multilogeo
    public static int iGetMultiLog(Connection con)
    {
        //Si se tiene que abrir la conexión entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que la conexión se abrió localmente
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }                    
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la configuración de multilogeo del sistema
        int iRes    = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'sist' AND conf = 'usrmulti'";
            st = con.prepareStatement(sQ);
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iRes    = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  
        
        //Si se tiene que cerrar la conexión entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }   
        
        //Devuelve el resultado
        return iRes;
        
    }//Fin de public static int iGetMultiLog(Connection con)            
    
    
    //Método para obtener la existencia de un producto
    public static double dExisProd(Connection con, String sAlma, String sProd)    
    {
        //Si se tiene que abrir la conexión entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que la conexión se abrió localmente
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }                    
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la configuración de multilogeo del sistema
        double dRes         = 0;
        try
        {
            sQ = "SELECT exist FROM existalma WHERE prod = '" + sProd.trim() + "' AND alma = '" + sAlma.trim() + "'";
            st = con.prepareStatement(sQ);
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                dRes    = rs.getInt("exist");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  
        
        //Si se tiene que cerrar la conexión entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }   
        
        //Devuelve el resultado
        return dRes;
        
    }//Fin de public static double dExisProd(Connection con, String sAlma, String sProd)    
    
    
    //Método para obtener la configuración de si se puede o no hacer movimientos de entradas y salidas sin existencias
    public static int iGetConfGral(String sConf)
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return -1;            
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la configuración para saber si se pueden hacer entradas o salidas sin existencias
        int iRes                    = 0;
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'inv' AND conf = '" + sConf + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())            
                iRes                = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return -1;

        //Devuelve el resultado
        return iRes;
        
    }//Fin de public static int iGetConfGral(String sConf)
       
    
    //Método para obtener las tallas y cargarlas en un combo
    public static int iCargTallCom(Connection con, javax.swing.JComboBox jCom)
    {
        //Borra todos los items
        jCom.removeAllItems();

        //Agrega el item vacio
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene todas las tallas actualizadas y cargalas en el combo
        try
        {
            sQ = "SELECT cod FROM tall";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("cod"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargTallCom(Connection con, javax.swing.JComboBox jCom)            
    
    
    //Método para convertir una cantidad a la cantidad correcta visual
    public static String sCantVisuaGKT(String sUnid, String sCant)
    {
        //Si la unidad no es gramo, kilogramo o tonelada entonces regresa aqui
        if(sUnid.compareTo("KILO")!=0 && sUnid.compareTo("GRAMO")!=0 && sUnid.compareTo("TONELADA")!=0)
            return sCant;
        
        //Obtiene la equivalencia visual correcta        
        if(sUnid.compareTo("KILO")==0)
            sCant   = Double.toString(Double.parseDouble(sCant)/1000);
        else if(sUnid.compareTo("TONELADA")==0)
            sCant   = Double.toString(Double.parseDouble(sCant)/1000000);
        
        //Devuelve el resultado
        return sCant;        
    }
    
    
    //Método para saber si un usuario tiene correo definido o no
    public static int iUsrConfCorr(Connection con, String sUsr)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe correo electrónico para el usuario
        int iRes    = 0;
        try
        {
            sQ = "SELECT estac FROM corrselec WHERE estac = '" + sUsr.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces si tiene correo y coloca la bandera
            if(rs.next())
                iRes    = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iRes;
        
    }//Fin de public static int iUsrConfCorr(Connection con, String sUsr)
   
    
    //Método para cargar las formas de pago en un combobox
    public static int iCargFormPagCom(Connection con, javax.swing.JComboBox jCom)
    {
        /*Borra los items en el combobox de monedas*/
        jCom.removeAllItems();

        /*Agrega la moneda vacia*/
        jCom.addItem("");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
    
        //Obtiene las formas de pago y cargalas en el combo
        try
        {
            sQ = "SELECT cod FROM pags";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jCom.addItem(rs.getString("cod"));                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iCargFormPagCom(Connection con, javax.swing.JComboBox jCom)
    
    
    //Registra el log de tipos de cambios
    public static int iRegTipsCambLog(Connection con, String sMon, String sTipCam)
    {
        //Declara variables de la base de datos
        Statement   st;              
        String      sQ; 
        
        
        
        
        
        //Inserta en el log el cambio del tipo de cambio
        try 
        {                
            sQ = "INSERT INTO tipscamb (    mon,                  tipcam,                  sucu,                nocaj,                  estac) "
                            + "VALUES('" +  sMon.trim() + "', " + sTipCam.trim() + ", '" + Star.sSucu + "','" + Star.sNoCaj + "','" +   Login.sUsrG + "')";                                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }

        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iRegTipsCambLog(Connection con, String sMon, String sTipCam)
    
    
    //Método para actualizar el tipo de cambio de la moneda específica
    public static int iActTipCamMon(Connection con, String sMon, String sVal)
    {
        //Declara variables de la base de datos
        Statement   st;              
        String      sQ; 
        
        
        
        
        
        //Actualiza el tipo de cambio de la moneda específica
        try 
        {                
            sQ = "UPDATE mons SET "
                    + "val          = " + sVal + ", "
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sNoCaj + "', "
                    + "estac        = '" + Login.sUsrG + "' "
                    + "WHERE    mon = '" + sMon.trim() + "'";                                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }

        //Regresa que todo fue bien
        return 0;
                
    }//Fin de public static int iActTipCamMon(Connection con, String sMon, String sVal)
    
    
    //Método para saber si debe o no de mostrar el mensaje de correo electrónico configurado por usuario
    public static int iMostMsjCorrUsr(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si se debe de mostrar el mensaje de correos electrónicos configurados
        int iRes            = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'sist' AND conf = 'mostmsjusr'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iRes        = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iMostMsjCorrUsr(Connection con)
    
    
    //Método para obtener la configuración de si se puede cambiar o no el tipo de cambio desde las monedas o en otros lados
    public static int iCambTipCam(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene la configuración de si se puede o no cambiar el tipo de cambio de la moneda en todos lados
        int iRes            = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'sist' AND conf = 'tipcamtod'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iRes        = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iCambTipCam(Connection con)
    
    
    //Método para saber si un almacén existe
    public static int iExistAlma(Connection con, String sAlma)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el almacén específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT alma FROM almas WHERE alma = '" + sAlma.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistAlma(Connection con, String sAlma)
    
    
    //Método para saber si un anaquel existe
    public static int iExistAnaq(Connection con, String sAnaq)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el almacén específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM anaqs WHERE cod = '" + sAnaq.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistAnaq(Connection con, String sAnaq)
    
    
    //Método para saber si un lugar existe
    public static int iExistLug(Connection con, String sLug)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el lugar específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM lugs WHERE cod = '" + sLug.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistLug(Connection con, String sLug)
    
    
    //Método para saber si una ubicación adicional existe
    public static int iExistUbiAd(Connection con, String sUbi)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la ubicación adicional específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM ubiad WHERE cod = '" + sUbi.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistUbiAd(Connection con, String sUbi)
    
    
    //Método para saber si una marca existe
    public static int iExistMarc(Connection con, String sMarc)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la marca específica ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM marcs WHERE cod = '" + sMarc.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistMarc(Connection con, String sMarc)
    
    
    //Método para saber si una medida existe
    public static int iExistMed(Connection con, String sMed)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la marca específica ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM meds WHERE cod = '" + sMed.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistMed(Connection con, String sMed)
    
    
    //Método para saber si una línea existe
    public static int iExistLin(Connection con, String sLin)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la linea específica ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM lins WHERE cod = '" + sLin.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistLin(Connection con, String sLin)
    
    
    //Método para saber si un fabricante existe
    public static int iExistFab(Connection con, String sFab)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el fabricante específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM fabs WHERE cod = '" + sFab.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistFab(Connection con, String sFab)
    
    
    //Método para saber si un impuesto existe
    public static int iExistImpue(Connection con, String sImpue)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el impuesto específico ya existe en la base de datos
        int iRes            = 0;
        try
        {                     
            sQ = "SELECT codimpue FROM impues WHERE codimpue = '" + sImpue.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistImpue(Connection con, String sImpue)
    
    public static int iExistClasProv(Connection con, String sClas)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            /*Abre la base de datos*/                   
            try 
            {
                con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
            } 
            catch(SQLException ex) 
            {    
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                //Mensajea y regresa error
                JOptionPane.showMessageDialog(null, Star.class + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
                return -1;
            }
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ      = ""; 
        
        //Comprueba si existe la clasificación
        int iExis           = 0;
        try
        {
            sQ = "SELECT cod FROM clasprov WHERE cod = '" + sClas.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, Star.class.getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getResource(Star.sRutIconEr))); 
            return -1;
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistClasCli(Connection con, String sClas)
    
    
    //Método para saber si la clasificación del producto existe
    public static int iExistClasProd(Connection con, String sClas)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la clasificación específica ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM clasprod WHERE cod = '" + sClas.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistClasProd(Connection con, String sClas)
    
    
    //Método para saber si la unidad existe
    public static int iExistUnid(Connection con, String sUnid)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si la unidad específica ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM unids WHERE cod = '" + sUnid.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistUnid(Connection con, String sUnid)
    
    
    //Método para saber si el peso existe
    public static int iExistPes(Connection con, String sPes)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el peso específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT cod FROM pes WHERE cod = '" + sPes.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistPes(Connection con, String sPes)
    
    
    //Método para saber si el concepto existe
    public static int iExistConcep(Connection con, String sConcep)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Comprueba si el concepto específico ya existe en la base de datos
        int iRes            = 0;
        try
        {
            sQ = "SELECT concep FROM conceps WHERE concep = '" + sConcep.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera para saber que si existe
            if(rs.next())
                iRes        = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iExistConcep(Connection con, String sConcep)
    
    
    //Método para insertar en CXC o CXP
    public static int iInsCXCP(Connection con, String sTbl, String sNoRefer, String sNoSer, String sEmpre, String sSer, String sSubTot, String sImpue, String sTot, String sCarg, String sAbon, String sFVenc, String sFDoc, String sConcep, String sFormPag, String sFol, String sComen, String sConcepPag, String sFolBanc)
    {
        /*Declara variables de la base de datos*/
        Statement   st;        
        String      sQ              = ""; 
        String sCampo="empre";
        //Si es un cxc o cxp
        if(sTbl.trim().compareTo("cxp")==0)
            sCampo="prov";
       
        /*Inserta cxc el abono en la base de datos*/
        try 
        {                
             sQ = "INSERT INTO " + sTbl + "(  norefer,                                            noser,                                          " + sCampo + ",                ser,                                       subtot,                                                          impue,                                                           tot,                                                    carg,                                                   abon,                                                  fvenc,                 fdoc,               falt,  fmod,       estac,                                       sucu,                                    nocaj,                                     concep,            formpag,            comen,            conceppag,           folbanc) " + 
                                "VALUES('" + sNoRefer.replace("'", "''").trim() + "', '" +       sNoSer.replace("'", "''").trim() + "', '" +         sEmpre.trim() + "', '" +   sSer.trim().replace("'", "''") + "'," +    sSubTot.trim().replace("$", "").replace(",", "") + ", " +        sImpue.trim().replace("$", "").replace(",", "") + ", " +         sTot.trim().replace("$", "").replace(",", "") + "," +   sCarg.trim().replace("$", "").replace(",", "") + ", " + sAbon.replace("$", "").replace(",", "").trim() + "," + sFVenc.trim() + ", " +  sFDoc.trim() + ",  now(), now(), '" + Login.sUsrG.replace("'", "''") +"',  '" +    Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "','" +   sConcep + "', '" + sFormPag + "', '" + sComen + "', '" + sConcepPag + "','" + sFolBanc + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException ex) 
         { 
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;

            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, Star.class.getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getResource(Star.sRutIconEr))); 
            return -1;
         }

        //Regresa que todo fue bien
        return 0;
        
    }        
    
    //Método para saber si un proveedor o cliente tiene bloqueado el crédito o no
    public static int iGetBloqCredCliProv(Connection con, String sTip, String sCliProv)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                       
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Crea la consulta en base si es cliente o proveedor
        if(sTip.compareTo("cli")==0)
            sQ = "SELECT bloqlimcred FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCliProv.trim() + "'";                        
        else
            sQ = "SELECT bloqlimcred FROM provs WHERE CONCAT_WS('', ser, prov) = '" + sCliProv.trim() + "'";                        
        
        //Corre la consulta
        int iRes            = 0;
        try
        {            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iRes        = rs.getInt("bloqlimcred");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resutlado
        return iRes;
        
    }//Fin de public static int iGetBloqCredCliProv(Connection con, String sTip, String sCliProv)
    
    
    //Método para obtener el método de costeo de un producto
    public static String sGetMetCostProd(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return null;                        
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene el método de costeo del producto
        String sMetCost = "";
        try
        {            
            sQ = "SELECT metcost FROM prods WHERE prod  = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sMetCost        = rs.getString("metcost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }  
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;           
        }
        
        //Devuelve el resultado
        return sMetCost;
        
    }//Fin de public static String sGetMetCostProd(Connection con, String sProd)
    
    
    //Método para devolver si hay existencias por serie de un producto
    public static double dGetExistProdSer(Connection con, String sProd)
    {        
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la existencia por serie de ese producto
        double dExist       = 0;
        try
        {            
            sQ = "SELECT exist FROM serieprod WHERE prod  = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                dExist      = rs.getDouble("exist");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Devuelve el resultado
        return dExist;       
        
    }//Fin de public static double dGetExistProdSer(Connection con, String sProd)
    
    
    //Método para obtener la configuración de mostrar mensajes de existencias negativas    
    public static int iGetConfExistNeg(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el almacén de ventas
        int iRes            = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'msjexistnegfac'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iRes   = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resultado
        return iRes;
        
    }//Fin de public static int iGetConfExistNeg(Connection con)            
    
    
    //Método para obtener la cantidad de componentes que tiene un producto
    public static double dGetCompsProd(Connection con, String sKit)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene el almacén de ventas
        double dRes            = 0;
        try
        {
            sQ = "SELECT IFNUll(SUM(cant),0) AS cant FROM kits WHERE codkit = '" + sKit.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                dRes   = rs.getDouble("cant");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;
        }  

        //Regresa el resultado
        return dRes;
        
    }//Fin de public static int iGetCompsProd(Connection con, String sKit)            
    
    
    //Método para obtener la existencia general de un producto
    public static double dExistGralProd(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene la existencia general del producto
        double dExist       = 0;
        try
        {
            sQ = "SELECT exist FROM prods WHERE prod = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                dExist      = rs.getDouble("exist");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }  

        //Regresa el resultado
        return dExist;
        
    }//Fin de public static double dExistGralProd(Connection con, String sProd)            
    
    
    //Método para obtener la descripción de un campo de una tabla por una condicionante
    public static String sGetDescripCamp(Connection con, String sCampDescrip, String sTab, String sCond)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Obtiene la descripción del campo por la tabla
        String sDescrip     = "";
        try
        {
            sQ = "SELECT " + sCampDescrip + " FROM " + sTab + " " + sCond;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sDescrip        = rs.getString(sCampDescrip);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }  

        //Regresa el resultado
        return sDescrip;
        
    }//Fin de public static String sGetDescripCamp(Connection con, String sCampDescrip, String sTab, String sCampFilt)
    
    
    //Método para insertar en ventas
    public static int iInsVtas(Connection con, String sNoSer, String sNoRefer, String sCli, String sSer, String sSubTot, String sImpue, String sTot, String sFAlt, String sFEmi, String sFVenc, String sEstad, String sTic, String sMotiv, String sTipDoc, String sNoCort, String sMetPag, String sCta, String sObserv, String sTimb, String sTotDescu, String sPtoVta, String sFactu, String sTotCost, String sVend, String sMon, String sTipCam, String sFormPag, String sAutRecibDe, String sAutMarc, String sAutMod, String sAutColo, String sAutPlacs, String sAutNom, String sAutTarCirc, String sAutNumLic, String sAutTel, String sAutDirPart, String sAutDirOfi, String sAutTelOfi, String sCort, String sCodCot, String sCierr, String sTotUeps, String sTotPeps, String sTotCostProm,String Bo)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }
        
        //Declara variables de bases de datos
        Statement   st;        
        String      sQ; 
        
        //Inserta la venta en la base de datos
        try 
        {   
            sQ  = "INSERT INTO vtas(   norefer,                       codemp,               ser,                     noser,                         estac,                         femi,                     subtot,                     impue,                    tot,                         estad,                 tic,                falt,                   motiv,                  tipdoc,                           nocort,                   metpag,                     cta,                    observ,                     sucu,                  nocaj,                   timbr,              fvenc,                  totdescu,                   ptovta,             factu,          totcost,                  vend,                     mon,                    tipcam,                     formpag,                    autrecibde,                     autmarc,                    autmod,                     autcolo,                    autplacs,                       autnom,                     auttarcirc,                     autnumlic,                      auttel,                     autdirpart,                     autdirofi,                  auttelofi,                   cort,           cierr,          totueps,          totpeps,          totcostprom,   extr1 ) " + 
                         "VALUES('" +  sNoRefer.trim() + "','" +      sCli.trim() + "','" + sSer.trim() + "','" +    sNoSer.trim() + "','" +        Login.sUsrG.trim() + "'," +   sFEmi.trim() + ", " +     sSubTot.trim() + ", " +     sImpue.trim() + ", " +    sTot.trim() + ",      " +    sEstad + ",     " +    sTic + ",     " +   sFAlt + ",      '" +    sMotiv + "',     '" +   sTipDoc.trim() + "',  " +         sNoCort.trim() + ", '" +  sMetPag.trim() + "', '" +   sCta.trim() + "', '" +  sObserv.trim() + "','" +    Star.sSucu + "','" +   Star.sNoCaj + "',   " +  sTimb + ",      " + sFVenc.trim() + ", " +  sTotDescu.trim() + ", " +   sPtoVta + ", " +    sFactu + ", " + sTotCost.trim() + ", '" + Login.sUsrG + "', '" +    sMon.trim() + "', " +   sTipCam.trim() + ", '" +    sFormPag.trim() + "', '" +  sAutRecibDe.trim() + "', '" +   sAutMarc.trim() + "', '" +  sAutMod.trim() + "', '" +   sAutColo.trim() + "', '" +  sAutPlacs.trim() + "', '" +     sAutNom.trim() + "', '" +   sAutTarCirc.trim() + "', '" +   sAutNumLic.trim() + "', '" +    sAutTel.trim() + "','" +    sAutDirPart.trim() + "','" +    sAutDirOfi.trim() + "','" + sAutTelOfi.trim() + "', '" + sCort + "', " + sCierr + ", " + sTotUeps + ", " + sTotPeps + ", " + sTotCostProm +",'"+Bo+ "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iInsVtas(Connection con, String sNoSer, String sNoRefer, String sCli, String sSer, String sSubTot, String sImpue, String sTot, String sFAlt, String sFEmi, String sFVenc, String sEstad, String sTic, String sMotiv, String sTipDoc, String sNoCort, String sMetPag, String sCta, String sObserv, String sTimb, String sTotDescu, String sPtoVta, String sFactu, String sTotCost, String sVend, String sMon, String sTipCam, String sFormPag, String sAutRecibDe, String sAutMarc, String sAutMod, String sAutColo, String sAutPlacs, String sAutNom, String sAutTarCirc, String sAutNumLic, String sAutTel, String sAutDirPart, String sAutDirOfi, String sAutTelOfi, String sCort, String sCodCot, String sCierr, String sTotUeps, String sTotPeps, String sTotCostProm)
    
    
    //Método para insertar en compras
    public static int iInsComprs(Connection con, String sCodComp, String sNoSer, String sProv, String sSer, String sNoDoc, String sFDoc, String sSubTot, String sImpue, String sTot, String sEstad, String sFAlt, String sMotiv, String sNomProv, String sFVenc, String sTip, String sFEnt, String sMetPag, String sCta, String sMon, String sTipCam, String sCodOrd, String sArchPDF, String sArchXML)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                       
        }

        //Declara variables de bases de datos
        Statement   st;        
        String      sQ; 
        //Inserta la compra en la base de datos
        try 
        {                
            sQ = "INSERT INTO comprs (    codcomp,              noser,                 prov,                ser,              nodoc,                estac,                                     fdoc,                     subtot,             impue,              tot,            estado,            falt,             motiv,              nomprov,              fvenc,               sucu,                                     nocaj,                                        tip,            fent,              metpag,            cta,            mon,             tipcam,           codord,           archpdf,          archxml) " +
                             "VALUES('" + sCodComp + "','" +    sNoSer + "','" +       sProv + "','" +      sSer + "','" +    sNoDoc + "','" +      Login.sUsrG.replace("'", "''") + "'," +    sFDoc   + ", " +          sSubTot + ", " +    sImpue + ", " +     sTot + ",   " + sEstad  + ", " +   sFAlt + ", '" +   sMotiv + "','" +    sNomProv + "',  " +   sFVenc + ", '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',   '" +   sTip + "', " +  sFEnt + ", '" +    sMetPag + "', '" + sCta + "', '" + sMon + "', " +   sTipCam + ", '" + sCodOrd + "', " + sArchPDF + ", " + sArchXML + ")";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iInsComprs(Connection con, String sCodComp, String sNoSer, String sProv, String sSer, String sNoDoc, String sFDoc, String sSubTot, String sImpue, String sTot, String sEstad, String sFAlt, String sMotiv, String sNomProv, String sFVenc, String sTip, String sFEnt, String sMetPag, String sCta, String sMon, String sTipCam, String sCodOrd, String sArchPDF, String sArchXML)
    
    
    //Método para insertar en cotizaciones
    public static int iInsCots(Connection con, String sCodCot, String sProy, String sNoSer, String sCodEmp, String sSer, String sObserv, String sDescrip, String sSubTotGral, String sSubTotMat, String sManObr, String sSubTot, String sImpue, String sTot, String sEstad, String sSubTotGral2, String sSubTotMat2, String sFVenc, String sTotDescu, String sTotCost, String sMon, String sTipCam, String sFEntre, String sFDoc)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de bases de datos
        Statement   st;        
        String      sQ; 
        
        //Inserta la compra en la base de datos
        try 
        {                
            sQ = "INSERT INTO cots( codcot,                  proy,             noser,                 codemp,              ser,                  observ,            descrip,           subtotgral,          subtotmat,          manobr,             estac,                                   sucu,                                     nocaj,                                       subtot,             impue,               tot,           estad,          subtotgral2,          subtotmat2,          fvenc,          totdescu,                                           totcost,            mon,           tipcam,          fentre,           fdoc) " + 
                     "VALUES('" +   sCodCot + "',  '" +      sProy + "','" +   sNoSer + "','" +       sCodEmp + "','" +    sSer + "','" +        sObserv + "', '" + sDescrip + "', " + sSubTotGral + ", " + sSubTotMat + "," +  sManObr + ",'" +    Login.sUsrG.replace("'", "''") + "','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', " +     sSubTot + ", " +    sImpue + ", " +      sTot + ", '" + sEstad +"', " + sSubTotGral2 + ", " + sSubTotMat2 + ", " + sFVenc + ", " + sTotDescu.replace("$", "").replace(",", "") + "," + sTotCost + ", '" +  sMon + "', " + sTipCam + ", " + sFEntre + ", " +  sFDoc + ")";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de 
    
    
    //Método para obtener la configuración de si debe de redondear ventas y cotizaciones o no
    public static String sGetConfRedon(Connection con)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return null;                       
        }

        //Declara variables de bases de datos
        Statement   st;        
        String      sQ; 
        ResultSet   rs;
        
        //Obtiene la configuracion para saber si se tiene que redondear o no
        String sResp        = "0|0";
        try
        {            
            sQ = "SELECT val, nume FROM confgral WHERE clasif = 'sist' AND conf = 'redon'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
                sResp   = rs.getString("val") + "|" + rs.getString("nume");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }             
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;          
        }
        
        //Devuelve el resultado
        return sResp;
        
    }//Fin de public static int iGetConf(Connection con)
            
    
    //Método para comprobar si la clasificación de un cliente existe
    public static int iExistClasCli(Connection con, String sClas)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe la clasificación
        int iExis           = 0;
        try
        {
            sQ = "SELECT cod FROM clasemp WHERE cod = '" + sClas.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistClasCli(Connection con, String sClas)
    
    
    //Método para comprobar si un producto existe
    public static int iExistProd(Connection con, String sProd)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe el producto
        int iExis           = 0;
        try
        {
            sQ = "SELECT prod FROM prods WHERE prod = '" + sProd.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistProd(Connection con, String sProd)
    
    
    //Método para comprobar si un producto es un kit
    public static int iExistKit(Connection con, String sKit)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe el producto
        int iExis           = 0;
        try
        {
            sQ = "SELECT prod FROM prods WHERE prod = '" + sKit.trim() + "' AND compue = 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces coloca la bandera de que si existe
            if(rs.next())
                iExis       = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iExis;
        
    }//Fin de public static int iExistKit(Connection con, String sKit)
    
    
    //Método para comprobar si un producto es un kit
    public static String sDescripClasCli(Connection con, String sClas)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return null;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si existe el producto
        String sDescrip     = "";
        try
        {
            sQ = "SELECT descrip FROM clasemp WHERE cod = '" + sClas.trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sDescrip    = rs.getString("descrip");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return null;           
        }
        
        //Regresa el resultado
        return sDescrip;
        
    }//Fin de public static String sDescripClasCli(Connection con, String sKit)
           
    
    //Método para saber si se tiene que guardar el PDF de cancelación o no
    public static int iGetGuaPDFCan(Connection con)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si se tiene que guardar el PDF de cancelación o no
        int iResp           = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'guapdfcanf'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iResp       = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;            
        }
        
        //Regresa el resultado
        return iResp;
        
    }//Fin de public static int iGetGuaPDFCan(Connection con)
    
    
    //Método para saber si se tiene que mostrar el PDF de cancelación o no
    public static int iGetVePDFCan(Connection con)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            //Abre la base de datos nuevamente
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return -1;                        
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si se tiene que guardar el PDF de cancelación o no
        int iResp           = 0;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vercanvtaf'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iResp       = rs.getInt("val");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        } 
        
        //Si la conexión es nula entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa el resultado
        return iResp;
        
    }//Fin de public static int iGetVePDFCan(Connection con)
            
    
    //Método para mostrar la forma de loading
    public static synchronized void vMostLoading(String mens)
    {
        //Si es nula la forma de loadin entonces
        if(lCargGral == null)
        {
            //Instancia el loading y hazla visible
            lCargGral = new LoadinGral(mens);
            lCargGral.setVisible(true);            
        }
        //Else no es nula entonces hazla visibe
        else
            lCargGral.setVisible(true);                    
        
        //Traela al frente
        lCargGral.toFront();
    }
    
    
    //Método para ocultar la forma de loading
    public static synchronized void vOcultLoadin()
    {
        //Si es nula la forma de loadin entonces
        if(lCargGral != null)
        {
            //Libera los recursos
            lCargGral.setVisible(false);
            lCargGral = null;
            System.gc();            
        }                
    }
    
    
    //Método para manejar errores
    public static int iErrProc(Object...objPara)
    {
        //Contiene el nombre del archivo de log
        String sFil         = "log.txt";
        
        
        
        
        
        //Obtiene el error
        String sErrLoc      = "";
        if(objPara.length >= 1)
            sErrLoc         = objPara[0].toString();
        
        //Obtiene el título del error
        String sErrTit      = "";
        if(objPara.length >= 2)
            sErrTit         = objPara[1].toString();                
        
        //Obtiene la pila del error
        StackTraceElement[] stackStack = null;
        if(objPara.length >= 3)
            stackStack      = (StackTraceElement[])objPara[2];
        
        //Obtiene la conexión a la base de datos
        Connection conCon   = null;
        if(objPara.length >= 4)
            conCon          = (Connection)objPara[3];
    
        //Si la forma de loading no es nula entonces
        if(lCargGral != null)
        {
            //Si esta visible entonces esconderla y darle nulo
            if(lCargGral.isVisible())
            {
                lCargGral.setVisible(false);
                lCargGral = null;
                System.gc();
            }
        }
        
        //Si tiene que cerrar la base de datos entonces
        if(conCon != null)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(null)==-1)
                return -1;
        }
                
        //Si el archivo log no existe entonces crealo
        if(!new File(sFil).exists())
        {
            try
            {
                new File(sFil).createNewFile();                                        
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa error
                Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
                return -1;
            }                
        }
        
        //Obtiene la fecha actual
        DateFormat dateForm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateDat        = new Date();                        
        
       //Abre el archivo log para escribir en el
       try(BufferedWriter bufreadBuf = new BufferedWriter(new FileWriter(sFil)))
        {                
            //Escribe la fecha con nueva línea
            bufreadBuf.write(dateForm.format(dateDat));
            bufreadBuf.newLine();
            
            //Escribe el error con nueva línea
            bufreadBuf.write(sErrLoc);
            bufreadBuf.newLine();
            
            //Escribe que se va a imprimir el stack trace con nueva línea
            bufreadBuf.write("StackTrace:");
            bufreadBuf.newLine();
            
            //Si hay stack trace para imprimir entonces
            if(stackStack != null)
            {
                //Recorre todo el stacktrace
                for(StackTraceElement stackElemen: stackStack)
                {
                    //Imprimelo al archivo separado por nuevas líneas
                    bufreadBuf.write(stackElemen.toString());
                    bufreadBuf.newLine();
                }
            }                
            
            //Separador entre errores
            bufreadBuf.newLine();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnIO.getMessage(), Star.sErrIO);                                                                   
            return -1;
        }            

        //Mensajea
        JOptionPane.showMessageDialog(null, sErrLoc, sErrTit, JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));             
        
        //Devuelve que todo fue bien
        return 0;
        
    }//Fin de public static int iErrProc(String sErr, String sErrTit)
    
    
    //Método para abrir la base de datos
    public static synchronized Connection conAbrBas(boolean bAutoCom, boolean bTrans, String...sPara)
    {                
        //Crea la cadena de conexión dependiendo de si se pasarón mas parámetros a la función
        String sCon = "jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia;  
        if(sPara.length > 0)
            sCon    = "jdbc:mysql://" + sPara[0] + ":" + sPara[1] + "/" + sPara[2] + "?user=" + sPara[3] + "&password=" + sPara[4] ;
            
        //Abre la base de datos
        Connection  con;  
        try  
        {
            con = DriverManager.getConnection(sCon);               
            con.setAutoCommit(bAutoCom);
            
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL);
            return null;
        }
            
        //Si tiene que abrir en transacción entonces inicia transacción
        if(bTrans)
        {
            //Inicia la transacción
            try           
            {
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            }
            catch(SQLException expnSQL)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return null;                                
            }                        
        }
        
        //Devuelve la conexión abierta
        return con;                    
    }
    
    
    //Método para cerrar la base de datos
    public static int iCierrBas(Connection con)
    {
        //Si la conexión es nula entonces regresa aqui ya no es necesario continuar
        if(con == null)
            return 0;
        
        //Cierra la base de datos
        try
        {
            con.close();            
        }
        catch(SQLException expnSQL)
        {
            return -1;
        }
        
        //Devuelve que todo fue bien
        return 0;        
    }
    
    
    //Método para iniciar una transacción en la base de datos
    public static int iIniTransCon(Connection con)
    {
        //Inicia la transacción
        try           
        {
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }    
        
        //Devuelve que todo fue bien
        return 0;        
    }
    
    
    //Método para terminar una transacción en la base de datos
    public static int iTermTransCon(Connection con)
    {
        //Termina la transacción
        try           
        {
            con.commit();
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                               
            return -1;                        
        }    
        
        //Devuelve que todo fue bien
        return 0;        
    }
    
    
    //Método para verificar que una serie no se repita
    public static int iSerRep(Connection con, String sSerie)
    { 
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        
        
        
        /*Trae los datos del producto seleccionado*/        
        int iResul  = 0;
        try
        {                        
            sQ = "SELECT ser FROM serieprod WHERE ser='" + sSerie  + "'"; 
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
                iResul = 1;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Regresa el resultado
        return iResul;
                
    }//public static int iProdSolSer(Connection con, String sProd)

            
    //Se revisa que no se halla agregado en la misma compra el codigo del prodcuto
    public static int vSerRepTab(String sSerie,JTable jT, int iColum, int iNumFil)
    { 
        //Se recorren todas las filas
        for(int iFila=0;iFila<iNumFil-1;iFila++)
        {
            //Si en alguna fila se encuentra la misma serie entonces devuelve uno
            if(jT.getValueAt(iFila, iColum).toString().trim().compareTo(sSerie)==0)
                return 1;
        }
        
        //Devuelve que todo va bien
        return 0;                  
    }
    
    
    //Método para verificar que una serie no se repita
    public static int iConfSer(Connection con)
    { 
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        
        
        
        /*Trae los datos del producto seleccionado*/        
        int iResul  = 0;
        try
        {     
            sQ = "SELECT val FROM confgral WHERE clasif = 'inv' AND conf = 'igualser'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                iResul = Integer.parseInt(rs.getString("val"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return -1;                        
        }
        
        //Regresa el resultado
        return iResul;
                
    }//public static int iProdSolSer(Connection con, String sProd)

        
    //Valida si una serie esta bien
    public static String sValSer(String sSerie)
    {
        //Arreglo que contendra la serie
        char[] cArreySer    = sSerie.toCharArray();
        
        
                
        //Hasta que que termina el largo de la serie
        for(int iCont = 0;iCont < sSerie.length();iCont++)
        {
            //Comprueba que el carácter este dentro del limite de las series
            if(((cArreySer[iCont] < 'A') || (cArreySer[iCont] > 'Z')) && ((cArreySer[iCont] < '0') || (cArreySer[iCont] > '9')) && cArreySer[iCont] != 'Ñ')
                return null;                        
        }
        
        //Devuelve el resultado
        return sSerie;        
    }
    
    
    //Se obtiene la cantidad de elementos por almacen dependiendo de su serie
    public static String iCantSer(String sSer, Connection con, String sProd, String sAlma)
    { 
        //Contiene la existencia de esa serie actual
        String sExistSer   = "no existe";
                                        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        
        
        
        //Obtiene la existencia de la serie
        try
        {
            sQ  = "SELECT exist FROM serieprod WHERE ser = '" + sSer.trim() + "' AND prod = '" + sProd.trim() + "' AND alma = '" + sAlma + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sExistSer          = rs.getString("exist");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return null;                        
        }
        
        //Devuelve el resultado
        return sExistSer;
        
    }//Fin de public static String iCantSer(String sSer, Connection con, String sProd, String sAlma)

    
    //Método para crear las tablas maximizadas
    public static void vMaxTab(final Object objPara)
    {
        //Crea la ventana dinámica
        final JFrame jFrame                   = new JFrame();
        
        //Obtiene los graficos
        GraphicsEnvironment grapGrap    = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        //Que este maximizada la ventana
        jFrame.setMaximizedBounds(grapGrap.getMaximumWindowBounds());
        jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        //Establece el título de la forma
        jFrame.setTitle("Visor");
        
        //Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            jFrame.setIconImage(new ImageIcon(Star.class.getResource(Star.sIconDef)).getImage());
        
        //Agrega el listener general
        Star.vSetKeyPress(jFrame, jFrame);
        
        //Instancia la nueva tabla
        JTable jTab2 = new JTable();
        
        //Si el parametro es tabla entonces crea una copia de la tabla
        if(objPara instanceof JTable)
            jTab2.setModel(((JTable)objPara).getModel());                           
        //Else se paso parametro de resultset entonces 
        else
        {
            //Agrega las filas de ese resultset a la tabla            
            if(Star.intInsRowTabResul((ResultSet)objPara, jTab2, new String[]{"Fecha entrega", "Costo", "Importe", "Cantidad", "Producto", "Descripción", "Devueltos", "Es kit", "Unidad", "Descuento", "Impuesto", "Fecha alta", "Talla", "Color", "Lote", "Pedimento", "Lote vencimiento", "Fecha entrega", "Serie producto", "Comentario serie"}, null, "prod", "cant", "devs", "descu", "impue")==-1)
                return;
        }

        //Dale tamaño a las columnas dinàmico para el tamaño de su celda
        for(int iX = 1; iX < jTab2.getColumnCount() - 1; iX++)
            jTab2.getColumnModel().getColumn(iX).setPreferredWidth(150);
        
        //Para que tenga scroll la tabla
        jTab2.setAutoResizeMode(0);
        
        //Borra las líneas horisontales y verticales de la nueva tabla
        jTab2.setShowHorizontalLines    (false);
        jTab2.setShowVerticalLines      (false);
        
        //Que las celdas vuelvan a su valor original
        Star.vTabModUndo(jTab2);
                
        //Agrega la tabla a la forma        
        jFrame.add(new JScrollPane(jTab2));
        
        //Coloca el foco del teclado en la tabla
        jTab2.grabFocus();
        
        //Muestrala
        jFrame.setVisible(true);                        
        jFrame.requestFocusInWindow();
    }

    
    //Método para que una tabla se modifiquen sus celdas y posteriormente se vuelvan al mismo estado
    public static void vTabModUndo(final JTable jTab)
    {
        //Crea el listener para cuando se modifique alguna celda se regrese a su valor original       
        PropertyChangeListener properPro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {                
                //Obtén la propiedad que a sucedio en el control
                String strProp = event.getPropertyName();                                
                
                //Si el evento fue por entrar en una celda del tabla entonces
                if("tableCellEditor".equals(strProp)) 
                {                    
                    //Valida en poner el valor anterior
                    Star.vValCellEdit(jTab);
                }                
            }
        };
        
        //Agrega el listener a la tabla
        jTab.addPropertyChangeListener(properPro);
    }
        
    
    //Método de utilidad para el cell editor para poner el valor anterior en la tabla
    private synchronized static void vValCellEdit(final JTable jTab)
    {
        //Si el contador esta en 1 entonces
        if(Star.intiContCellEdG == 1)
        {
            //Guarda el valor original y aumenta el contador del cell editor
            Star.strCellEdG = jTab.getValueAt(jTab.getSelectedRow(), jTab.getSelectedColumn()).toString();        
            ++Star.intiContCellEdG;
        }   
        //Else va de salida entonces
        else
        {
            //Resetea el contador del cell editor
            Star.intiContCellEdG = 1;
            
            //Coloca el valor previo en la celda
            jTab.setValueAt(Star.strCellEdG, jTab.getSelectedRow(), jTab.getSelectedColumn());
        }
    }
    
    
    //Método para insertar las filas en una tabla existente a partir de un resultset
    public static int intInsRowTabResul(final ResultSet rs, final JTable jTab, final String[] strstrHeaders, final int[] intintTam, final String...strColumNoFormMon)
    {
        //Objeto para saber la cantidad de columnas que trae el resultset
        ResultSetMetaData ressetMetDat;
        try
        {
            ressetMetDat = rs.getMetaData();
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return -1;                                                
        }                

        //Contiene el tamaño de columnas por fila del resulset
        int intColuCount;

        //Obtiene la cantidad de columnas que tiene por fila el resultset            
        try
        {
            intColuCount = ressetMetDat.getColumnCount();                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return -1;                                                
        }                
        
        //Crea el arreglo de objetos que iran al modelo + la columna inicial del contador
        Object[] objObjResul            = new Object[intColuCount + 1];                        
        
        //Obtiene el modelo por default
        DefaultTableModel deftabmodMod  = (DefaultTableModel)jTab.getModel();
        
        //Si el usuario quiere agregar sus propias columnas entonces
        if(strstrHeaders != null)
        {
            //Agrega la columna del contador primeramente
            deftabmodMod.addColumn("No.");
            
            //Establece el tamaño de la columna del contador
            jTab.getColumnModel().getColumn(0).setPreferredWidth(30);
                    
            //Agrega las columnas que el usuario pidio
            for(String strColu: strstrHeaders)
                deftabmodMod.addColumn(strColu);        
        }            

        //Si el usuario quiere colocar sus propios tamaños de celda entonces
        if(intintTam != null)
        {
            //Contador de columnas, comienza de 1 para que el número de fila no se afecte
            int iContCol    = 1;
            
            //Obtiene la diferencia entre las columnas que el usuario paso y las reales
            int intDif      = intintTam.length > jTab.getColumnCount() ? jTab.getRowCount() : intintTam.length;
                        
            //Recorre la lista que paso el usuario para los tamaños de las celdas
            for(int intX = 0; intX < intDif; intX++)
            {
                //Modifica el tamaño de la celda y aument en uno el contador de la columna
                jTab.getColumnModel().getColumn(iContCol).setPreferredWidth(intintTam[intX]);      
                ++iContCol;
            }
        }            
        
        //Contador de filas del resultset
        int iFilCount   = 1;
        
        //Recorre todo el resultset
        try
        {                          
            while(rs.next())
            {                                 
                //Recorre todas las columnas del resultset 
                for(int iX = 1; iX <= intColuCount; iX++)  
                {
                    //Obtiene el dato
                    Object objElemen    = rs.getObject(iX);
                    
                    //Bandera para que ya no evalue el objeto 
                    boolean bEval       = true;

                    //Si el objeto es nulo entonces
                    if(objElemen == null)
                    {
                        //El objeto será cadena vacia
                        objElemen   = "";
                        
                        //Coloca la bandera para que ya no evalue el objeto
                        bEval           = false;
                    }
                    
                    //Si aun puede evaluar el objeto entonces
                    if(bEval)
                    {
                        //Si es tipo booleano entonces
                        if(objElemen instanceof Boolean)
                        {
                            //Coloca el valor correcto para el valor
                            if(Boolean.valueOf(objElemen.toString()))
                                objElemen       = "Si";
                            else
                                objElemen       = "No";

                            //Coloca la bandera para que ya no evalue el objeto
                            bEval           = false;
                        }
                    }                                            
                                        
                    //Si aun se puede elvaluar el objeto entonces
                    if(bEval)
                    {
                        //Parsea para saber si es tipo de dato double
                        try
                        {
                            //Intenta parsear
                            Double.parseDouble(objElemen.toString());

                            //Si hay parametros para filtrar el formato entonces
                            if(strColumNoFormMon.length > 0)
                            {
                                //Si la columna actual no esta en el array que paso el cliente para filtro de moneda entonces dale formato de moneda a la cantidad
                                if(!Arrays.asList(strColumNoFormMon).contains(ressetMetDat.getColumnName(iX)))                                               
                                    objElemen   = Star.strGetFormMon(objElemen.toString());                    
                            }                                
                        }
                        catch(NumberFormatException expnNumForm)
                        {                                                
                        }
                    }                                            
                    
                    //Agrega el elemento al arreglo para crear la fila
                    objObjResul[iX] =  objElemen;                                                                                
                }

                //Agrega el contador de fila y aumenta en uno
                objObjResul[0] = iFilCount;
                ++iFilCount;
                
                //Agrega la fila al modelo de la tabla                    
                deftabmodMod.addRow(objObjResul);
            }                                                                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return -1;                                                
        }            
        
        //Regresa que todo fue bien
        return 0;
    }
    
    
    //Método para dar formato de moneda a una cantidad
    public static String strGetFormMon(String strMon)
    {
        //Dale formato de moneda a la cantidad
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(strMon.replace("$", "").replace(",", ""));                
        strMon           = n.format(dCant);
        
        //Devuelve el formto de moneda
        return strMon;
    }

    
    //Método para establecer el ícono de la aplicación
    public static void vSetIconFram(final Object PobjPara)
    {        
        //Si el cliente tiene un logo personalizado entonces la imágen será esa
        Image imgIcon;
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
            imgIcon = Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg");
        //Else el usuario no tiene icono predefinido entonces usar el del sistema
        else
            imgIcon = new ImageIcon(Star.class.getResource(Star.sIconDef)).getImage();                
        
        //Coloca el icono de la forma dependiendo de si es forma o dialogo
        if(PobjPara instanceof JFrame)        
            ((JFrame)PobjPara).setIconImage(imgIcon);
        else
            ((JDialog)PobjPara).setIconImage(imgIcon);
    }


    //Método para crear un statement
    public static Statement stGetState(final Connection con)
    {
        //Obtiene el statemente
        Statement   st;
        try
        {
            st = con.createStatement();                      
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }  
        
        //Devuelve el statement
        return st;
    }
    
    
    //Método para correr una consulta y devolver un result set
    public static ResultSet rsGetResul(final Connection con, final String strQRY, final boolean bUpdate)
    {
        //Obtiene el statement
        Statement   st = Star.stGetState(con);
        
        //Si hubo error entonces regresa error
        if(st == null)
            return null;
        
        //Ejecuta la consulta
        ResultSet   rs  = null;   
        try
        {            
            //Ejecuta dependiendo si tiene que ser update o lectura
            if(bUpdate)
                st.executeUpdate(strQRY.trim());            
            else
                rs = st.executeQuery(strQRY.trim());            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa error
            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }  
        
        //Devuelve el resultset
        return rs;
    }                        

    
    //Método que agrega listener para las teclas a un objeto
    public static void vSetKeyPress(final Object...objPara)
    {                
        //Crea el key listener
        KeyListener keylKey = new java.awt.event.KeyAdapter() 
        {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) 
            {
                //Cierra la forma
                if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
                    ((JFrame)objPara[1]).setVisible(false);
            }
        };                
        
        //Determina al objeto que se agregara el listener
        if(objPara[0] instanceof JFrame)
            ((JFrame)objPara[0]).addKeyListener(keylKey);
        else if(objPara[0] instanceof JButton)
            ((JButton)objPara[0]).addKeyListener(keylKey);
    }
    
   //Checa alguna configuracion enviandole que tipo y cual nombre y te devuelve su val si no existe te manda "no existe"
    public static String sCheckUnConf(Connection con, String sClas, String sConf)
    {
        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ      = "";
        
        try
        {
            sQ  = "SELECT val FROM confgral WHERE clasif = '" + sClas + "' AND conf = '" + sConf + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                return rs.getString("val");
            }
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return null;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            return null;
        }
        
        JOptionPane.showMessageDialog(null, Star.class + "No existe la configuracion " + sConf + "En la clase " + sClas  , "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr)));    
        return "no existe";
    }//Fin sstatic String sCheckUnConf(Connection con, String sClas, String sConf)tatic String sCheckUnConf(Connection con, String sClas, String sConf)
     
    //Te busca que exista cierta cosa en algun campo si te regresa "no existe" es por que no se encuentra si no te regresa lo mismo que buscas
    public static String sTraUnCamp(Connection con, String sCamp, String sTab, String sBusc)
    {   
        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ      = "";
        
        try
        {
            sQ  = "SELECT " + sCamp + " FROM " + sTab + " WHERE " + sCamp + "= '" + sBusc + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                return rs.getString(sCamp);
            }
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return null;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            return null;
        }
           
        return "no existe";
    }//Fin static String sTraUnCamp(Connection con, String sCamp, String sTab, String sBusc)

    //Funcion para cargar la existencia por almacen
    static String sExistAlma(String sAlma,String sProd)
    {
        /*Abre la base de datos*/        
        Connection  con;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException | HeadlessException e) 
        {    
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null,  Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Star.class.getClass().getResource(Star.sRutIconEr))); 
            return null;
        }
        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;                
        String      sQ              = ""; 
        String      sResult;
        /*Obtiene la descripción del almacén en base a su código*/
        try
        {
            sQ ="SELECT exist FROM existalma WHERE alma = '" + sAlma + "' AND prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                sResult = rs.getString("exist");
            else
                sResult = "0";
                
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return null;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null,  Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Star.class.getClass().getResource(Star.sRutIconEr))); 
            return null;
        }
        if(Star.iCierrBas(con)==-1)
            return null;
        
        return sResult;
                
    }//Fin static String sExistAlma()
        public static int iInPrevComprs(Connection con, String sCodPrevComp, String sNoSer, String sNoDoc, String sProv, String sSer, String sTipCam, String sMon, String sObserv, String sSubTot, String sImpue, String sTot, String sEstad, String sFDoc, String sFEnt, String sFVenc, String sFAlt, String sMotiv, String sCodComp, String sNoSerComp)
   //public static int iInPrevComprs(Connection con, String sCodComp, String sNoSer, String sProv, String sSer, String sNoDoc, String sFDoc, String sSubTot, String sImpue, String sTot, String sEstad, String sFAlt, String sMotiv, String sNomProv, String sFVenc, String sTip, String sFEnt, String sMetPag, String sCta, String sMon, String sTipCam, String sCodOrd, String sArchPDF, String sArchXML)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            /*Abre la base de datos*/                   
            try 
            {
                con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
            } 
            catch(SQLException ex) 
            {    
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                //Mensajea y regresa error
                JOptionPane.showMessageDialog(null, Star.class + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
                return -1;
            }
        }

        //Declara variables de bases de datos
        Statement   st;        
        String      sQ      = ""; 
        
        //Inserta la compra en la base de datos
        try 
        {         
            //Se ignoran  export,notcredpag, ruta,tip
            //Codiprevio,
            sQ = "INSERT INTO prevcomprs(       codprevcomp,               noser,               nodoc,               prov,               ser,               tipcam,                mon,                observ,              subtot,              impue,              tot,              estado,              estac,                                      sucu,                                       nocaj,                                      fdoc,              fent,              fvenc,              falt,              motiv,               codcomp,               nosercomp     )"
                               +" VALUES('" +   sCodPrevComp   + "','" +   sNoSer   + "','" +   sNoDoc   + "','" +   sProv   + "','" +   sSer   + "','" +   sTipCam   + "', '" +   sMon    + "','" +   sObserv   + "', " +  sSubTot   + "," +    sImpue   + ", " +   sTot   + ", '" +   sEstad   + "','" +    Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''")   + "','" +   Star.sNoCaj.replace("'", "''")   + "'," +   sFDoc   + ", " +   sFEnt   + ", " +   sFVenc   + ", " +   sFAlt   + ",'" +   sMotiv   + "','" +   sCodComp   + "','" +   sNoSerComp   + "')";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            //Mensajea y regresa error
            JOptionPane.showMessageDialog(null, Star.class.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            return -1;
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iInsComprs(Connection con, String sCodComp, String sNoSer, String sProv, String sSer, String sNoDoc, String sFDoc, String sSubTot, String sImpue, String sTot, String sEstad, String sFAlt, String sMotiv, String sNomProv, String sFVenc, String sTip, String sFEnt, String sMetPag, String sCta, String sMon, String sTipCam, String sCodOrd, String sArchPDF, String sArchXML)

         public static int iUpdPrevComprs(Connection con, String sCodPrevComp, String sNoSer, String sNoDoc, String sProv, String sSer, String sTipCam, String sMon, String sObserv, String sSubTot, String sImpue, String sTot, String sEstad, String sFDoc, String sFEnt, String sFVenc, String sMotiv, String sCodComp, String sNoSerComp, String id_id)
    {
        //Si la conexión es nula entonces
        boolean bSi = false;
        if(con==null)
        {
            //Coloca la bandera para saber que es nula la conexión
            bSi = true;
            
            /*Abre la base de datos*/                   
            try 
            {
                con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
            } 
            catch(SQLException ex) 
            {    
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                //Mensajea y regresa error
                JOptionPane.showMessageDialog(null, Star.class + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
                return -1;
            }
        }

        //Declara variables de bases de datos
        Statement   st;        
        String      sQ      = ""; 
        
        //Inserta la compra en la base de datos
        try 
        {         
            //Se ignoran  export,notcredpag, ruta,tip
            //Codiprevio,
            /*Crea la consulta*/
            sQ = "UPDATE prevcomprs SET "
                    + "codprevcomp         = '" + sCodPrevComp.replace("'", "''") + "', "                      
                    + "noser               = '" + sNoSer .replace("'", "''") + "', " 
                    + "nodoc               = '" + sNoDoc.replace("'", "''") + "', " 
                    + "prov                = '" + sProv.replace("'", "''") + "',"
                    + "ser                 = '" + sSer.replace("'", "''") + "',"
                    + "tipcam              = '" + sTipCam.replace("'", "''") + "',"
                    + "mon                 = '" + sMon.replace("'", "''") + "',"
                    + "observ              = '" + sObserv.replace("'", "''") + "',"
                    + "subtot              = " + sSubTot .replace("'", "''") + ","
                    + "impue               = " + sImpue.replace("'", "''") + ","
                    + "tot                 = " + sTot.replace("'", "''") + ","       
                    + "estado              = '" + sEstad.replace("'", "''") + "',"
                    + "estac               = '" + Login.sUsrG.replace("'", "''") + "',"
                    + "sucu                = '" + Star.sSucu.replace("'", "''") + "',"
                    + "nocaj               = '" + Star.sNoCaj.replace("'", "''") + "',"
                    + "fdoc                = " + sFDoc + ","
                    + "fent                = " + sFEnt + ","                           
                    + "fvenc               = " + sFVenc + ","
                    + "motiv               = '" + sMotiv + "',"
                    + "codcomp             = '" + sCodComp + "',"
                    + "nosercomp           = '" + sNoSerComp + "' WHERE id_id = " + id_id + "";
            /*Ejecuta la consulta*/
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            //Mensajea y regresa error
            JOptionPane.showMessageDialog(null, Star.class.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            return -1;
        }
        
        //Si se abrió la base de datos entonces
        if(bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;           
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de public static int iInsComprs(Connection con, String sCodComp, String sNoSer, String sProv, String sSer, String sNoDoc, String sFDoc, String sSubTot, String sImpue, String sTot, String sEstad, String sFAlt, String sMotiv, String sNomProv, String sFVenc, String sTip, String sFEnt, String sMetPag, String sCta, String sMon, String sTipCam, String sCodOrd, String sArchPDF, String sArchXML)
    
//Se revisa que no se halla agregado en la misma compra el codigo del prodcuto
    public static int vSerRepTabSalto(String sSerie,JTable jT, int iColum, int iSaltFila)
    { 
        //Se recorren todas las filas
        for(int iFila=0;iFila<jT.getRowCount();iFila++)
        {
            if(iSaltFila == iFila)
                continue;
            
            //Si en alguna fila se encuentra la misma serie entonces devuelve uno
            if(jT.getValueAt(iFila, iColum).toString().trim().compareTo(sSerie)==0)
                return 1;
        }
        
        //Devuelve que todo va bien
        return 0;                  
    }
    //Se obtiene la cantidad de elementos por almacen dependiendo de su serie
    public static String sComenSer(String sSer,Connection con,String sProd, String sAlma)
    { 
        /*Obtiene la existencia de esa serie actual*/
        String sExistSer   = "no existe";
        
        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ      = "";
        
        try
        {
            sQ  = "SELECT comen FROM serieprod WHERE ser = '" + sSer.trim() + "' AND prod = '" + sProd.trim() + "' AND alma = '" + sAlma + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sExistSer          = rs.getString("comen");
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return null;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, Star.class + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(Star.class.getClass().getResource(Star.sRutIconEr))); 
            return null;
        }
           
        return sExistSer;
    }//Fin public static String iCantSer(String sSer,Connection con,String sProd, String sAlma)
    
    public static int iExisteRFC(Connection con, String rfc, String tabla){
        Statement   st;
        ResultSet   rs;        
        String      sQ      = ""; 
        
        try
            {
                sQ  = "SELECT nom FROM "+tabla+" WHERE rfc = '" + rfc + "'";                
                st  = con.createStatement();
                rs  = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {        
                    return 1;
                }
            }
            catch(SQLException | HeadlessException e)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return -1;

                /*Agrega en el log*/
                Login.vLog(e.getMessage());
                
                return -1;
            } 
        return 0;
    }
    
    //Checa que la longitud sea la correcta, sino trunca la cadena
    static String checaLongitud(int longi, String cad) {
        if (cad.length() > longi)
            cad = cad.substring(0, longi);
        
        return cad;
    }
}/*Fin de public class Star*/
    

