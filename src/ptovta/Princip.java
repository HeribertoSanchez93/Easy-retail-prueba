/*CR01 Modificado para JPS Ingenieros 
13327 botones de manejo de proyecto y empleados
*/
//CRR01 modificación para entrar a ventana de cambio de contraseña280
/*Paquete*/
package ptovta;

/*Importaciones*/
import cats.TallColo;
import cats.MaxsMins;
import cats.Usrs;
import cats.UbiAd;
import cats.SerOrd;
import cats.SerCot;
import cats.SerProv;
import cats.SerFac;
import cats.SerNot;
import cats.SerNotVent;
import cats.SerTik;
import cats.SerComp;
import cats.TipsPags;
import cats.SerEmp;
import cats.SerRem;
import cats.Mons;
import cats.Prods;
import cats.Provs;
import cats.Imps;
import cats.Clients;
import cats.ClasCli;
import cats.CatMsjs;
import cats.Almas;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import report.*;




/*Clase que controla el ancho de la aplicación*/
public class Princip extends javax.swing.JFrame 
{
    /*Contiene la dirección de la forma*/
    private javax.swing.JFrame jFram;
    
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/
    private static Princip      obj                 = null;    
    public static String        sClavDefau          = "admin";   
    public static boolean       bIdle;
        
    /*Bandera para menajar los errores de las funciones*/
    private boolean             bErr                = false;
    
    //Permisos del boton de compras
    private java.util.ArrayList<Boolean> btnsCompras = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsProvee = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsPrevio = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsProductos = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsClientes = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsVentas = new java.util.ArrayList<>();
    
    //Permisos del boton de proveedores
    private java.util.ArrayList<Boolean> btnsCotiza = new java.util.ArrayList<>();
    
    /*Constructor sin argumentos*/
    public Princip() 
    {                  
        /*Inicializa los componentes gráficos*/
        initComponents();
        
        Star.lCargGral=null;
            
        /*Crea un imageicon con la imàgen del banner*/        
        ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource("/imgs/ban.png")).getImage());        
        
        /*Redimensiona la imàgen del banner*/
        java.awt.Image im = img.getImage(); 
        java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );  

        /*Crea el nuevo ícono del baner*/
        img                 = new ImageIcon(newimg);
        
        /*Asigna el nuevo ìcono a la forma principal*/
        jLImg.setIcon(img);
        
        javax.swing.JLabel lblVersion = new javax.swing.JLabel();
        lblVersion.setText("Versión 1.1");
        //lblVersion.setBounds(695, 428, 160, 26);    
        lblVersion.setBounds(695, 450, 160, 40);    
        lblVersion.setFont(new java.awt.Font("Tahoma", 2, 25)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(255, 255, 255));
        this.jLImg.add(lblVersion);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBPtoVta.getBackground();
                
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                            
                
        /*Inicialmente la bandera del tiempo muerto estará marcada como que no hay evento*/
        bIdle   = false;       
        
        /*Inicialmente la imágen de warnign no será visible*/
        jLImgAlert.setVisible(false);
        
        /*La ventana estara mazimizada*/
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
        /*Establece el titulo de la ventana con el nombre de la empresa, la fecha y hora*/                
        this.setTitle(Star.sNombreEmpresa + " Usuario: <" + Login.sUsrG + "> V10.0.0.1 " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        //Deshabilita los botones correspondientes
        deshabilitaBotones();
                
        /*Crea el runable para que respalde en caso de que así deba ser*/
        (new Thread()
        {
            @Override
            public void run() 
            {                                
                /*Función para procesar esta parte*/
                Star.vRespUsr();
            }
        }).start();
        
        //Inicia los threads de los sockets 
        Star.vIniSocks();
            
        /*Crea el thread para que envie automáticamente las facturas de la interfaz de datapark*/
        (new Thread()
        {
            @Override
            public void run() 
            {                                
                /*Función para procesar esta parte*/
                vMandAutoFacDTP();
            }
        }).start();
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Si el usuario actual esta configurado para máximos y mínimos entonces comienza el thread*/
        Star.vMinMax(con);               
                               
        //Registra el log de inicio de sesión del sistema al usuario
        if(Star.iRegUsr(con, "0")==-1)
            return;
        
        //Método para iniciar los 2 threads que hacen de socket udp y socket normal
        Star.vIniSocks();
        
        /*Crea el thread para que determine si hay mensajes pendientes por ver en los  corporativos y de usuarios*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Función para procesar esta parte*/
                vChats();                              
            }
        }).start();
        
        /*Crea el thread para mensajear en el mouse cada 5 minùtos si hay mensajes personales o coorporativos pedientes de ver*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Función para procesar esta parte*/
                vChatsM();                              
            }
        }).start();
        
        /*Crea el thread para que este revisando constantemente si hay conexión a la base de datos o no*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Función para procesar esta parte*/
                vCon();                              
            }
        }).start();
        
        /*Función para procesar toda la parte de envio de correos de cumpleaños automáticament*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCorrCumple();
            }
        }).start();
                   
        /*Crea el thread para que mande correos automáticamente a los clientes de agradecimiento*/
        (new Thread()
        {
          @Override
          public void run()
          {              
              /*Función para procesar esta parte*/
              vAgradCor();              
          }
        }).start();
                
        /*Crea el thread para validar los usuarios que ya no están logeados*/
        (new Thread()
        {
            @Override
            public void run()
            {              
                /*Función para procesar esta parte*/
                vDesLogT();              
            }
        }).start();
        
        /*Crea el thread para validar que siempre esten conectados solo los usuarios correctos*/
        (new Thread()
        {
            @Override
            public void run()
            {              
                /*Función para procesar esta parte*/
                vLic();              
            }
        }).start();
        //CRR01
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        String      sQ; 
        
        try {
            
            /* creamos el objeto de envio de correo */
            correoRegistro correoAUsuario = new correoRegistro();
        
            /*revisamos si el campo "ya_entro_booleano" de la  tabla registroemail es 0*/
            /* esto significa si no ha entrado por primera vez */
            /* para entrar por primera vez debe emplear la contraseña
            que se envio a correo elecrónico que el usuario proporciona */
           
            /* cuando entra por primera vez se le obliga a cambiar la contraseña
            y valor del campo "ya_entro_booleano" cambia a 1 */
            
            /*"ya_entro_booleano" de la  tabla registroemail es 0 */
                if(correoAUsuario.es0BaseDatos())
                {
                    ClavCamb_1 c = new ClavCamb_1(null,true);
                    c.getObj().setVisible(true);
                    if(ClavCamb_1.bSi==true)
                    {
                        correoAUsuario.cambia1BaseDatos();
                    }
                    if(ClavCamb_1.bSi==false)
                    {
                        Star.vExitAp();
                    }
                }
            
            } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        //CRR01
        /*Comprueba si esta habilitado el deslogeo por tiempo de inactividad*/        
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'sist' AND conf = 'dlogin'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si tiene habilitado entonces coloca la bandera*/
                if(Integer.parseInt(rs.getString("val"))> 0)                
                {
                    /*Crea el thread para que se deslogie en el tiempo que debe de hacerlo*/
                    (new Thread()
                    {                
                        @Override
                        public void run()
                        {
                            /*Función que procesa esta parte*/
                            vDeslo();                        
                        }
                    }).start();
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
                
    }/*Fin de public Princip() */
    
                
    /*Función para procesar esta parte*/
    private void vMandAutoFacDTP()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales        
        String sDia         = "";
        String sSerFac      = "";
        String sMon         = "";        
        String sProd        = "";        
        
        /*Contienen las variables de conexión a datapark*/
        String sInst        = "";
        String sUsr         = "";
        String sBD          = "";
        String sPort        = "";
        String sContra      = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene algunos datos de la interfaz de datapark*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT inst, usr, contra, bd, port, prod, mon, diacort, automat, serfac FROM interdpark WHERE codemploc = '" + Login.sCodEmpBD + "' AND usrmand = '" + Login.sUsrG + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene los resultados*/                
                sDia        = rs.getString("diacort");
                sSerFac     = rs.getString("serfac");
                sMon        = rs.getString("mon");                
                sProd       = rs.getString("prod");                
                
                /*Obtiene los datos de conexión a DATAPARK*/
                sInst       = rs.getString("inst");
                sUsr        = rs.getString("usr");
                sBD         = rs.getString("bd");
                sPort       = rs.getString("port");
                sContra     = Star.sDecryp(rs.getString("contra"));
                
                /*Si tiene que mandarlo entonces coloca la bandera*/
                if(rs.getString("automat").compareTo("1")==0)
                    bSi = true; 
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }        
                
        /*Si no tiene que mandar entonces*/
        if(!bSi)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);                  
            return;
        }
        
        /*Obtiene la fecha de hoy*/
        java.util.Calendar caHoy = java.util.Calendar.getInstance();
                
        /*Si no es el día en que se tiene que mandar entonces*/
        if(Integer.parseInt(sDia)>caHoy.get(java.util.Calendar.DAY_OF_MONTH))
        {
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)                  
            return;
        }
        
        /*Comprueba si los datos del certificado, los datos fiscales locales*/
        if(iValDatsLoc(con)==-1)
            return;            
        
        /*Si no esta definida la instancia de SQL entonces*/
        if(sInst.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la instancia de SQL Datapark.", "Instancia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si no esta definido el usuario de SQL Datapark entonces*/
        if(sUsr.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido el usuario de SQL Datapark.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si no esta definida la contraseña de SQL Datapark entonces*/
        if(sContra.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la contraseña de SQL Datapark.", "Contraseña", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si no esta definida la base de datos de SQL Datapark entonces*/
        if(sBD.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la base de datos de SQL Datapark.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        //Comprueba si el producto existe en la base de datos        
        int iRes        = Star.iExistProd(con, sProd);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si el producto no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El producto: " + sProd + " para envio automático de facturación no existe.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
            
        }//Fin de if(iRes==0)
                
        /*Comprueba si hay registros pendientes para facturar*/
        String sCont    = "";
        try
        {
            sQ = "SELECT COUNT(cli) AS cli FROM tars WHERE factur = 1 AND MONTH(NOW())>MONTH(ffactu)";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())       
                sCont   = rs.getString("cli");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }        
        
        /*Si hay registros pendientes para facturar entonces*/
        if(Integer.parseInt(sCont)>0)
        {
            /*Preguntar al usuario si quiere proceder para la facturación automática*/
            Object[] op = {"Si","No"};
            iRes        = JOptionPane.showOptionDialog(this, "¿Existen " + sCont + " tarjetas pendientes de facturar. ¿Quieres proceder a la facturación automática?", "Facturación automática", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            {
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                  
                return;                       
            }
        }
        
        /*Registra el driver*/
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF, expnClassNoF.getStackTrace(), con);
            return;                        
        }
        
        /*Si el puerto no es igual a 0 entonces*/                
        if(Integer.parseInt(sPort)!=0)
            sPort       = ":" + sPort;
        else
            sPort       = "";
                
        /*Abre la base de datos de DATAPARK solo para probar la conexión*/
        Connection  conP;
        try
        {
            conP = DriverManager.getConnection("jdbc:sqlserver://" + sInst + sPort + ";user=" + sUsr + ";password=" + sContra + ";database=" + sBD);
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

        /*Recorre todos los registros que se tienen que facturar*/
        try
        {
            sQ = "SELECT cli FROM tars WHERE factur = 1 AND MONTH(NOW())>MONTH(ffactu) GROUP BY cli";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())       
            {                
                /*Procesa todo esto en una función*/
                if(iFacCli(con, rs.getString("cli"), sSerFac, sMon, sProd)==-1)
                    return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }        
                                        
        /*Recorre todas las tarjetas que no han sido pagados*/
//        if(iSinDatPark(con, sInst, sPort, sUsr, sContra, sBD)==-1)
//            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);                 
                
    }/*Fin de private void vMandAutoFacDTP()*/
                
        
    /*Recorre todas las tarjetas que no han sido pagadas*/
    private int iSinDatPark(Connection con, String sInst, String sPort, String sUsr, String sPass, String sBD)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Recorre todos los tickets que no han sido pagados*/                                
        try
        {
            sQ = "SELECT tar FROM tars WHERE factur = 0";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces recorre los resultados*/
            while(rs.next())
            {                
                /*Desactiva la tarjeta por que no esta pagada*/
                if(iDesTar(rs.getString("tar"), sInst, sPort, sUsr, sPass, sBD)==-1)
                    return -1;
            }
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }        
                
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iSinDatPark(Connection con, String sInst, String sPort, String sUsr, String sPass, String sBD)*/
        
        
    /*Desactiva la tarjeta por que no esta pagada*/
    private int iDesTar(String sTar, String sInst, String sPort, String sUsr, String sPass, String sBD)
    {
        /*Abre la base de datos de datapark*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + sPort + ";user=" + sUsr + ";password=" + sPass + ";database=" + sBD);
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());
            return -1;                                                
        }

        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        /*Actualiza la tarjeta como desactivada*/
        try 
        {            
            sQ = "UPDATE "
                    + "WHERE tip    = 'FAC' AND ser = '" + sTar + "'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                        
        }        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return -1;        

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iDesTar(String sTar)*/
                
                
    /*Obtiene el consecutivo de la serie*/
    private String sGetConsFac(Connection con, String sSerFac)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene el consecutivo de la serie*/       
        String sConFac  = "";        
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = 'FAC' AND ser = '" + sSerFac + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sConFac         = rs.getString("consec");                                                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }         
        
        /*Actualiza el consecutivo de la serie de la venta*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'FAC' AND ser = '" + sSerFac + "'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }        
        
        /*Devuelve el resultado*/
        return sConFac;
        
    }/*Fin de private String sGetConsFac(Connection con, String sSerFac)*/
                
        
    /*Obtiene el valor del impuesto*/
    private String sGetValImp(Connection con, String sImpue)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Recorre las tarjetas de ese cliente*/                        
        String sVal = "";
        try
        {
            sQ = "SELECT impueval FROM impues WHERE codimpue = '" + sImpue + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sVal    = rs.getString("impueval");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }        
        
        /*Devuelve el resultado*/
        return sVal;
        
    }/*Fin de private String sGetValImp(Connection con, String sImpue)*/
        
        
    /*Obtiene el impuesto del producto*/
    private String sGetImpueProd(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Recorre las tarjetas de ese cliente*/                        
        String sImpue = "";
        try
        {
            sQ = "SELECT impue FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sImpue    = rs.getString("impue");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                        
        }        
        
        /*Devuelve el resultado*/
        return sImpue;
        
    }/*Fin de private String sGetImpueProd(Connection con, String sProd)*/
    
    
    /*Función para hacer la factura del cliente*/
    private int iFacCli(Connection con, String sCli, String sSerFac, String sMon, String sProd)
    {
        //Muestra el loading
        Star.vMostLoading("");
        
        /*Obtiene el consecutivo de la serie*/
        String sConFac      = sGetConsFac(con, sSerFac);
        
        /*Si hubo error entonces regresa error*/
        if(sConFac==null)
            return -1;
        
        //Declara variables de la base de datos
        Statement   st;        
        ResultSet   rs;
        String      sQ; 
                
        /*Comprueba si los datos del cliente estan completos para facturar*/
        int iResp   = iValDatsCli(con, sCli);
                
        /*Si hubo error entonces regresa, caso contrario regresa 0 para que solo continue*/
        if(iResp==-1)
            return -1;            
        else if(iResp==1)
            return 0;
        
        /*Contiene el total de la tarifa y la lista de las tarjetas*/
        String sSubTot  = "0";
        String sTars    = "";
        
        /*Obtiene el total que se va a facturar para ese cliente*/                                
        try
        {
            sQ = "SELECT tarif, tar FROM tars WHERE factur = 1 AND MONTH(NOW())>MONTH(ffactu) AND cli = '" + sCli + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Ve sumando la tarifa*/                
                sSubTot  = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(rs.getString("tarif")));
                
                /*Ve completando las tarjetas*/
                sTars   += rs.getString("tar") + ",";
                
                /*Actualiza la fecha de facturación de esa tarjeta*/
                if(iUpdatTar(con, rs.getString("tar"))==-1)
                    return -1;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                        
        }        
        
        /*Quita la última coma de las tarjetas*/
        sTars   = sTars.substring(0, sTars.length() - 1);
        
        /*Obtiene el impuesto del producto*/
        String sImpue  = sGetImpueProd(con, sProd);
        
        /*Si hubo error entonces regresa error*/
        if(sImpue==null)
            return -1;        
        
        /*Obtiene el valor del impuesto*/
        String sValImp  = sGetValImp(con, sImpue);
        
        /*Si hubo error entonces regresa error*/
        if(sValImp==null)
            return -1;        
        
        /*Obtiene el método de pago del cliente*/
        String sMetPag  = sGetMetPag(con, sCli);
        
        /*Si hubo error entonces regresa error*/
        if(sMetPag==null)
            return -1;        
        
        /*Obtiene la cuenta del cliente*/
        String sCta     = sGetCtaCli(con, sCli);
        
        /*Si hubo error entonces regresa error*/
        if(sCta==null)
            return -1;        
        
        /*Obtiene la serie del cliente*/
        String sSer     = sGetSerCli(con, sCli);
        
        /*Si hubo error entonces*/
        if(sSer==null)
            return -1;        
        
        /*Obtiene el tipo de cambio de la moneda*/
        String sTipCam  = sGetTipCam(con, sMon);
        
        /*Si hubo error entonces*/
        if(sTipCam==null)
            return -1;        
        
        /*Obtiene los totales en base al impuesto*/
        String sImpueVal= Double.toString(Double.parseDouble(sSubTot) * (Double.parseDouble(sValImp) / 100));
        String sTot     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpueVal));        
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return -1;        
                
        //Inserta en la base de datos la nueva venta
        if(Star.iInsVtas(con, sSerFac, sConFac.replace("'", "''").trim(), sCli, sSer, sSubTot, sImpueVal, sTot, "now()", "now()", "now()", "'CO'", "0", "", "FAC", "0", sMetPag.replace("'", "''"), sCta.replace("'", "''"), "", "0", "0", "0", "1", "0", Login.sUsrG, sMon, sTipCam, "C", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "0", "0", "0", "0","","")==-1)
            return -1;        
            
        /*Obtiene la última venta insertada*/
        String sVta = sGetVta(con);
        
        /*Si hubo error entonces regresa error*/
        if(sVta==null)
            return -1;        
        
        /*Obtiene la fecha de la venta en específico*/
        String sFDoc = sGetFDoc(con, sVta);
        
        /*Si hubo error entonces regresa error*/
        if(sFDoc==null)
            return -1;        
        
        /*Obtiene la descripción del producto*/
        String sDescrip = "";
        try
        {
            sQ = "SELECT descrip FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sDescrip = rs.getString("descrip");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }        
        
        /*Obtiene la unidad del producto*/
        String sUnid    = "";
        try
        {
            sQ = "SELECT unid FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sUnid   = rs.getString("unid");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }        
        
        /*Inserta en la base de datos la partida de la venta*/
        try 
        {                
            sQ = "INSERT INTO partvta(   vta,                  prod,        cant,       unid,                 descrip,                                          pre,        descu,     impue,          mon,         impo,           falt,  eskit,       kitmae,     tipdoc, list,   alma,   peps,    ueps, tall,    colo,   fentre, cantentre,  serprod,    comenser,   tipcam, garan,      cost,   costprom,   lot,    pedimen,    fcadu,          codimpue) " + 
                             "VALUES(" + sVta + ",'"  +        sProd + "',  1,'" +      sUnid + "','" +       sDescrip + ", Tarjeta(s): " + sTars + "'," +      sSubTot + ",0," +      sImpueVal + ",  ''," +       sSubTot + ",    now(), 0,           0,          'FAC',  1,      '',     0,       0,    '',      '',     NOW(),  1,          '',         '',         0,      '',         0,      0,          '',     '',         NOW(), '" +     sImpue + "')";                                                                    
            st = con.createStatement();
            st.executeUpdate(sQ);                                                
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }
        
        //Declara variables locales
        String sPai     = "";
        String sTel     = "";
        String sCall    = "";
        String sCol     = "";
        String sCP      = "";
        String sNoExt   = "";
        String sNoInt   = "";
        String sCiu     = "";
        String sEstad   = "";
        String sRFC     = "";
        String sNom     = "";
        String sCo1     = "";
        String sCtaPred = "";
        
        /*Obtiene algunos datos del cliente*/                                
        try
        {
            sQ = "SELECT ctapred, co1, nom, pai, tel, calle, col, cp, noext, noint, ciu, estad, rfc FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";             
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sPai    = rs.getString("pai");
                sTel    = rs.getString("tel");
                sCall   = rs.getString("calle");
                sCol    = rs.getString("col");
                sCP     = rs.getString("cp");
                sNoExt  = rs.getString("noext");
                sNoInt  = rs.getString("noint");
                sCiu    = rs.getString("ciu");
                sEstad  = rs.getString("estad");
                sRFC    = rs.getString("rfc");
                sNom    = rs.getString("nom");
                sCo1    = rs.getString("co1");
                sCtaPred= rs.getString("ctapred");
            }
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
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }            
        
        /*Función para hacer el timbrado y generar PDF y XML*/
        Star.vGenTim("fac", "", sConFac, sVta, "", sFDoc, sNom, sPai, sTel, sCall, sCol, sCP, sNoExt, sNoInt, sCiu, sEstad, sRFC, sCo1, Star.sObLet(sTot.replace("$", "").replace(",", ""), sMon, sSimb, true), sSubTot, sImpueVal, sTot, sSerFac, sMetPag, sCta, "Contado", getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), false, false, sCo1, "", "", true, false, false, true, -1, null, false, sMon, "0", "Pago en una sola exhibición", "ingreso", sTipCam, sCtaPred);                                                            
                
        /*Devuelve que todo fue bien*/
        return 0;
        
    }/*Fin de private int iFacCli(Connection con, String sCli, String sSerFac, String sMon, String sProd)*/
    
    
    /*Comprueba si los datos del certificado y los datos fiscales locales están bien definidos*/
    private int iValDatsLoc(Connection con)
    {
        /*Declara variables de bases de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene si es persona moral o física en la empresa local*/        
        String sPers            = "";
        try
        {
            sQ = "SELECT pers FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resultados*/
            if(rs.next())
                sPers           = rs.getString("pers");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }  
        
        /*Contiene los campos para saber si buscar por física o moral*/
        String sCer;
        String sKey;
        String sPass;
        
        /*Determina la consulta dependiendo de si es persona moral o física*/
        if(sPers.compareTo("F")==0)                    
        {
            sCer    = "rutcerf";
            sKey    = "rutkeyf";
            sPass   = "passcerf";
        }
        else
        {
            sCer    = "rutcer";
            sKey    = "rutkey";
            sPass   = "passcer";
        }
        
        /*Contiene algunos datos del certificado*/
        String sRutCert         = "";
        String sPassCer         = "";
        String sRutKey          = "";
        String sRegFisc         = "";
        String sLugExp          = "";
        String sRazSoc          = "";
        String sCall            = "";
        String sCol             = "";
        String sNoExt           = "";
        String sCP              = "";
        String sCiu             = "";
        String sEstad           = "";
        String sRFC             = "";
        
        /*Obtiene algunos datos de la empresa local*/        
        try
        {            
            sQ = "SELECT rfc, estad, ciu, cp, noext, col, calle, nom, lugexp, regfisc, " + sCer + ", " + sKey + ", " + sPass + " FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resultados*/
            if(rs.next())
            {
                sRutCert        = rs.getString(sCer);
                sPassCer        = Star.sDecryp(rs.getString(sPass));
                sRutKey         = rs.getString(sKey);
                sRegFisc        = rs.getString("regfisc");
                sLugExp         = rs.getString("lugexp");
                sRazSoc         = rs.getString("nom");
                sCall           = rs.getString("calle");
                sCol            = rs.getString("col");
                sNoExt          = rs.getString("noext");
                sCP             = rs.getString("cp");
                sCiu            = rs.getString("ciu");
                sEstad          = rs.getString("estad");
                sRFC            = rs.getString("rfc");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }  
        
        /*Si el certificado no esta definido entonces*/
        if(sRutCert.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al CSD no esta definida para facturación electrónica automática de tarjetas.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si la ruta al CSD no existe entonces*/
        if(!new File(sRutCert).exists())
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al CSD no existe  para facturación electrónica automática de tarjetas.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si la llave no esta definida entonces*/
        if(sRutKey.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al archivo .KEY no esta definida  para facturación electrónica automática de tarjetas.", "KEY", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si la ruta al key no existe entonces*/
        if(!new File(sRutKey).exists())
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ruta al .KEY no existe  para facturación electrónica automática de tarjetas.", "KEY", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si el password no esta definido entonces*/
        if(sPassCer.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La contraseña del CSD no esta definida  para facturación electrónica automática de tarjetas.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si el regimén fiscal no esta definido entonces*/
        if(sRegFisc.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El regimén fiscal no esta definido  para facturación electrónica automática de tarjetas.", "Regimén fiscal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si el lugar de expedición no esta definido entonces*/
        if(sLugExp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El lugar de expedición no esta definido  para facturación electrónica automática de tarjetas.", "Lugar expedición", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si el password no esta definido entonces*/
        if(sPassCer.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La contraseña del CSD no esta definida  para facturación electrónica automática de tarjetas.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si la razón social no esta definida entonces*/
        if(sRazSoc.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La razón social local no esta definida  para facturación electrónica automática de tarjetas.", "Razón social", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
               
        /*Si la calle no esta definida entonces*/
        if(sCall.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La calle local no esta definida para facturación electrónica automática de tarjetas.", "Calle", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
                
        /*Si la colonia no esta definida entonces*/
        if(sCol.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La colonia local no esta definida para facturación electrónica automática de tarjetas.", "Colonia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
                
        /*Si el número de exterior no esta definida entonces*/
        if(sNoExt.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El número de exterior local no esta definida para facturación electrónica automática de tarjetas.", "No. exterior", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
                        
        /*Si el CP no esta definida entonces*/
        if(sCP.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El CP local no esta definido para facturación electrónica automática de tarjetas.", "CP", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si la ciudad no esta definida entonces*/
        if(sCiu.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "La ciudad local no esta definido para facturación electrónica automática de tarjetas.", "Ciudad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
                
        /*Si el estado no esta definido entonces*/
        if(sEstad.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El estado local no esta definido para facturación electrónica automática de tarjetas.", "Estado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Si el RFC no esta definida entonces*/
        if(sRFC.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return -1;
            
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El RFC local no esta definido para facturación electrónica automática de tarjetas.", "RFC", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return -1;
        }
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iValDatsLoc(Connection con)*/
        
        
    /*Comprueba si los datos del cliente estan completos para facturar*/
    private int iValDatsCli(Connection con, String sCli)
    {
        /*Declara variables de bases de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                                
        /*Contiene algunos datos del certificado*/
        String sRazSoc          = "";
        String sCall            = "";
        String sCol             = "";
        String sNoExt           = "";
        String sCP              = "";
        String sCiu             = "";
        String sEstad           = "";
        String sRFC             = "";
        
        
        
        
        
        /*Obtiene algunos datos del cliente*/        
        try
        {            
            sQ = "SELECT rfc, estad, ciu, cp, noext, col, calle, nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resultados*/
            if(rs.next())
            {                                                
                sRazSoc         = rs.getString("nom");
                sCall           = rs.getString("calle");
                sCol            = rs.getString("col");
                sNoExt          = rs.getString("noext");
                sCP             = rs.getString("cp");
                sCiu            = rs.getString("ciu");
                sEstad          = rs.getString("estad");
                sRFC            = rs.getString("rfc");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                
        }  
        
        /*Si la razón social no esta definida entonces*/
        if(sRazSoc.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La razón social del cliente: " + sCli + " no esta definida.", "Razón social", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
               
        /*Si la calle no esta definida entonces*/
        if(sCall.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La calle del cliente: " + sCli + " no esta definida.", "Calle", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
                
        /*Si la colonia no esta definida entonces*/
        if(sCol.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La colonia del cliente: " + sCli + " no esta definido.", "Colonia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
                
        /*Si el número de exterior no esta definida entonces*/
        if(sNoExt.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El número de exterior del cliente: " + sCli + " no esta definido.", "No. exterior", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
                        
        /*Si el CP no esta definida entonces*/
        if(sCP.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El CP del cliente: " + sCli + " no esta definido.", "CP", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
        
        /*Si la ciudad no esta definida entonces*/
        if(sCiu.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La ciudad del cliente: " + sCli + " no esta definido.", "Ciudad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
                
        /*Si el estado no esta definido entonces*/
        if(sEstad.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El estado del cliente: " + sCli + " no esta definido.", "Estado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
        
        /*Si el RFC no esta definida entonces*/
        if(sRFC.compareTo("")==0)
        {
            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, "El RFC del cliente: " + sCli + " no esta definido.", "RFC", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return 1;
        }
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iValDatsCli(Connection con, String sCli)*/
    
    
    /*Obtiene el método de pago del cliente*/
    private String sGetMetPag(Connection con, String sCli)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene el método de pago del cliente*/
        String sMetPag  = "";
        try
        {
            sQ = "SELECT metpag FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sMetPag = rs.getString("metpag");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                    
        }        
        
        /*Devuelve el resultado*/
        return sMetPag;
        
    }/*Fin de private String sGetMetPag(Connection con, String sCli)*/
                
        
    /*Obtiene la cuenta de pago del cliente*/
    private String sGetCtaCli(Connection con, String sCli)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene la cuenta del cliente*/
        String sCta  = "";
        try
        {
            sQ = "SELECT cta FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCta    = rs.getString("cta");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                                
        }        
        
        /*Devuelve el resultado*/
        return sCta;
        
    }/*Fin de private String sGetCtaCli(Connection con, String sCli)*/
    
    
    /*Obtiene la serie del cliente*/
    private String sGetSerCli(Connection con, String sCli)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene la cuenta del cliente*/
        String sSer = "";
        try
        {
            sQ = "SELECT ser FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSer    = rs.getString("ser");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                                
        }        
        
        /*Devuelve el resultado*/
        return sSer;
        
    }/*Fin de private String sGetSerCli(Connection con, String sCli)*/
            
    
    /*Obtiene el tipo de cambio de la moneda*/
    private String sGetTipCam(Connection con, String sMon)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene el tipo de cambio de la moneda*/
        String sTipCam = "";
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
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                                
        }        
        
        /*Devuelve el resultado*/
        return sTipCam;
        
    }/*Fin de private String sGetTipCam(Connection con, String sMon)*/
    
    
    /*Obtiene la última venta insertada venta*/
    private String sGetVta(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene el tipo de cambio de la moneda*/
        String sVta = "";
        try
        {
            sQ = "SELECT vta FROM vtas ORDER BY vta DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sVta = rs.getString("vta");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                                
        }        
        
        /*Devuelve el resultado*/
        return sVta;
        
    }/*Fin de private String sGetVta(Connection con)*/
    
        
    /*Obtiene la fecha de la venta en específico*/
    private String sGetFDoc(Connection con, String sVta)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene la fecha de la venta*/
        String sFDoc = "";
        try
        {
            sQ = "SELECT femi FROM vtas WHERE vta = " + sVta;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sFDoc = rs.getString("femi");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return null;                                                
        }        
        
        /*Devuelve el resultado*/
        return sFDoc;
        
    }/*Fin de private String sGetFDoc(Connection con, String sVta)*/
    
    
    /*Función para actualizar las tarjetas como que ya están facturadas*/
    private int iUpdatTar(Connection con, String sTar)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        
        
        /*Actualiza esa tarjeta con la fecha de hoy de facturación*/
        try 
        {                
            sQ = "UPDATE tars SET "
                    + "ffactu           = NOW() "                    
                    + "WHERE tar        = '" + sTar + "'";                          
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;                                                                        
         }                    

        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iUpdatTar(Connection con, String sTar)*/
                        
                
    /*Función para procesar toda la parte de envio de correos de cumpleaños automáticamente*/
    private synchronized void vCorrCumple()
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
        
        /*Comprueba si este usuario es el que tiene que mandar correos de cumpleaños automáticamente*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE mandcump = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {   
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                  
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Comprueba si esta usuario tiene un correo configurado ya*/        
        try
        {
            sQ = "SELECT id_id FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                  
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }            
        
        /*Arrego que contiene los clientes*/
        String sCli[]   = null;
        
        /*Obtiene todos los clientes que no se le han enviado correos de cumpleaños el contador para crear el arreglo*/
        try
        {
            sQ = "SELECT COUNT(codemp) AS cont FROM emps WHERE MONTH(DATE(cumple)) <= MONTH(DATE(now())) AND DAY(DATE(cumple)) <= DAY(DATE(now())) AND (anio = '' OR anio <> YEAR(DATE(now())))";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces crea el arreglo de clientes*/
            if(rs.next())
                sCli    = new String[rs.getInt("cont")];
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Obtiene todos los clientes que no se le han enviado correos de cumpleaños*/
        int x = 0;
        try
        {
            sQ = "SELECT CONCAT_WS('', ser, codemp) AS codemp FROM emps WHERE MONTH(DATE(cumple)) <= MONTH(DATE(now())) AND DAY(DATE(cumple)) <= DAY(DATE(now())) AND (anio = '' OR anio <> YEAR(DATE(now())))";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces recorre todo y ve creando el arreglo de clientes*/
            while(rs.next())
            {                
                sCli[x]  = rs.getString("codemp");                                   
                ++x;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }
        
        /*Declara variables fianales para el thread*/
        final String sCliFi[] = sCli;
        
        /*Manda los correos de cumpleaños a todos los clientes que están pendientes en la tabla de cumpleaños*/        
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vMandCoCump(sCliFi);                                             
            }
        }).start();
                
    }/*Fin de private void vCorrCumple(Connection con)*/                            
                                                                            
    
    /*Función para deslogear a los que ya no están logeados en caso de que alla un error*/
    private void vDesLogT()
    {                           
        /*Bucle infinito*/
        while(true)
        {
            //Abre la base de datos nuevamente
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;                       
            String      sQ;         

            /*Borra todos los registros que ya tienen mas de 20 segundos sin logearse en el sistema*/            
//            try 
//            {                
//                sQ = "DELETE FROM logestac WHERE (CURTIME() - proxlog) > 30";                    
//                st = con.createStatement();
//                st.executeUpdate(sQ);
//             }
//             catch(SQLException expnSQL) 
//             { 
//                //Procesa el error y regresa
//                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
//                return;                                                               
//            }    
//            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Duerme el thread 1 y un poco mas minuto*/
            try
            {
                Thread.sleep(70000);
            }
            catch(InterruptedException expnInterru)
            {      
                /*Sal del thread*/
                break;
            }
            
        }/*Fin de while(true)*/                                        
        
    }/*Fin de private void vDesLogT()*/
              
    
    /*Función para estar controlando el tiempo de inactividad*/
    private void vDeslo()
    {
        /*Búcle infinito*/
        while(true)
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

            /*Comprueba si esta habilitado el deslogeo por tiempo de inactividad*/                        
            String sTim = "";
            try
            {
                sQ = "SELECT val FROM confgral WHERE clasif = 'sist' AND conf = 'dlogin'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Si tiene habilitado entonces obtiene el resultado*/
                    if(Integer.parseInt(rs.getString("val"))> 0)                
                        sTim    = rs.getString("val");
                    /*Else no tiene habilitado entonces*/
                    else
                    {
                        //Cierra la base de datos y regresa
                        Star.iCierrBas(con);                  
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

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Declara el contador de segundos inicialmente en 0*/
            long lSeg       = 0;

            /*Este bucle revisa cada segundo la bandera para saber si hubo algún evento*/
            while(!bIdle)
            {                            
                /*Duerme el tread un segundo*/
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException expnInterru)
                {  
                    /*Sal del thread*/
                    break;
                }                                                                                                

                /*Aumenta el contador de segundos en 1*/
                ++lSeg;

                /*Si el contador de segundos es igual a la cantidad de segundos que el usr configuro para inactividad entonces*/                            
                if((Integer.parseInt(sTim) * 60)==lSeg)
                {                  
                    /*Llama a la forma para deslogearse*/
                    DesLogin l = new DesLogin(jFram);
                    l.setVisible(true);

                    /*Sal del bucle*/
                    break;                                                                    
                }

            }/*Fin de while(true)*/   

            /*Pone la bandera en false otra vez*/
            bIdle           = false;

        }/*Fin de while(true)*/

    }/*Fin de private void vDeslo()*/            
    
    
    /*Función para verificar que si esten conctados los usuarios que deben de estarlo*/
    private void vLic()
    {                               	
        /*Bucle infinito*/
        while(true)
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

            /*Obtiene la cantidad de usuarios conectados y el número de licencias permitidas*/            
            try
            {
                sQ = "SELECT (SELECT usrs FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "') AS lic, IFNULL(COUNT(id_id),0 ) AS count FROM logestac";            
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                {
                    /*Si la cantidad de usuarios conectados es mayor o igual a los permitidos entonces*/
                    if(Integer.parseInt(rs.getString("count"))>Integer.parseInt(Star.sDecryp(rs.getString("lic"))))
                    {
                        /*Mensajea y sal del sistema*/
                        JOptionPane.showMessageDialog(null, "Se detectarón: " + rs.getString("count") + " conectados, la licencia es válida solo para: " + Star.sDecryp(rs.getString("lic")) + " usuarios." + System.getProperty( "line.separator" ) + "La aplicación se cerrara automáticamente ahora.", "Licencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        System.exit(1);
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
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Duerme el thread 3 minútos*/
            try
            {
                Thread.sleep(210000);
            }
            catch(InterruptedException expnInterru)
            {      
                /*Sal del thread*/
                break;
            }
            
        }/*Fin de while(true)*/                                        
        
    }/*Fin de private void vLic()*/
    
    
    /*Función para procesar esta parte*/
    private void vChats()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        /*Obtiene el color original del menú de chat*/
        Color cOri = jMChatC.getBackground();
        
        /*Bucle infinito*/
        while(true)
        {
            /*Duerme el thread*/
            try
            {
                Thread.sleep(10000);
            }
            catch(InterruptedException expnInterru)
            {
                //Cierra la base de datos y sal del búcle
                Star.iCierrBas(con);                  
                break;
            }

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Comprueba si existen mensajes pendientes por ver en la base de datos para chat corporativo*/        
            try
            {
                sQ = "SELECT id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin = ''";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces cambia el color del menù*/
                if(rs.next())                                             
                    jMChatC.setBackground(Color.ORANGE);                                                                        
                else                                    
                    jMChatC.setBackground(cOri);                                    
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                                                
            }
            
            /*Comprueba si existen mensajes pendientes por ver en la base de datos para chat usuarios*/        
            try
            {
                sQ = "SELECT id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin <> ''";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces cambia el color del menù*/
                if(rs.next())                          
                    jMChat.setBackground(Color.ORANGE);                                    
                else
                    jMChat.setBackground(cOri);                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                                                
            }
            
        }/*Fin de while(true)*/                    	        
        
    }/*Fin de private void vChats()*/
                
                
    /*Función para determinar cuando hay mensajes pendientes mostrarlos en el mouse cada 5 minùtos*/
    private void vChatsM()
    {                               
        /*Bucle infinito*/
        while(true)
        {
            /*Duerme el thread*/
            try
            {
                Thread.sleep(100000);
            }
            catch(InterruptedException expnInterru)
            {                
                break;
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

            /*Comprueba si existen mensajes pendientes por ver en la base de datos para chat corporativo*/        
            try
            {
                sQ = "SELECT id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin = ''";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())          
                {                                
                    /*Obtiene las coordenadas del mouse*/
                    java.awt.Point poi = java.awt.MouseInfo.getPointerInfo().getLocation();
                    
                    /*Instancia la forma para ver el mensaje*/                    
                    Star.avisChat = new AvisChatC("Mensaje coorporativo", 0);
                    
                    /*Aumentale un poco al X*/
                    poi.x = poi.x + 50;
                    
                    /*Coloca el mensaje a un lado del mouse y muestrala*/
                    Star.avisChat.setLocation(poi);
                    Star.avisChat.setVisible(true);

                    /*Crea el thread para que cierre la forma a los 10 segundos*/
                    (new Thread()
                    {
                        @Override
                        public void run()
                        {                                                
                            /*Duerme el thread segundos*/
                            try
                            {
                                Thread.sleep(3000);
                            }
                            catch(InterruptedException expnInterru)
                            {                                
                            }
                            
                            /*Cierra la forma*/
                            Star.avisChat.setVisible(false);
                        }
                        
                    }).start();

                    //Cierra la base de datos y continua
                    Star.iCierrBas(con);                  
                    continue;
                }                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                                                
            }
            
            /*Comprueba si existen mensajes pendientes por ver en la base de datos para chat usuarios*/        
            try
            {
                sQ = "SELECT id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin <> ''";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())          
                {
                    /*Obtiene las coordenadas del muse*/
                    java.awt.Point poi = java.awt.MouseInfo.getPointerInfo().getLocation();
                            
                    /*Instancia la forma para ver el mensaje*/                    
                    Star.avisChat = new AvisChatC("Mensaje personal", 1);
                    
                    /*Aumentale un poco al X*/
                    poi.x = poi.x + 50;
                    
                    /*Coloca la ventana a una lado del mouse y muestrala*/
                    Star.avisChat.setLocation(poi);
                    Star.avisChat.setVisible(true);

                    /*Crea el thread para que cierre la forma a los 10 segundos*/
                    (new Thread()
                    {
                        @Override
                        public void run()
                        {                                                    
                            /*Duerme el thread 10 segundos*/
                            try
                            {
                                Thread.sleep(3000);
                            }
                            catch(InterruptedException expnInterru)
                            {                                
                            }
                            
                            /*Cierra la forma*/
                            Star.avisChat.setVisible(false);
                        }
                        
                    }).start();
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
                        
        }/*Fin de while(true)*/                    	        
        
    }/*Fin de private void vChatsM()*/
    
    
    /*Función para procesar esta parte*/
    private void vCon()
    {
        /*Bucle infinito*/
        while(true)
        {
            /*Duerme el thread 15 segundos*/
            try
            {
                Thread.sleep(15000);
            }
            catch(InterruptedException expnInterru)
            {
                /*Sal del bucle*/
                break;
            }
            
            //Abre la base de datos nuevamente
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
            {
                /*Carga la imágen en el panel y que sea visible*/
                jLImgAlert.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/imgs/alert.png")).getImage()));                        
                jLImgAlert.setVisible(true);
                
                //Regresa
                return;
            }                

            /*Que no sea visible la imgágen de la alerta*/
            jLImgAlert.setVisible(false);            

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
            {
                /*Carga la imágen en el panel y que sea visible*/
                jLImgAlert.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/imgs/alert.png")).getImage()));                        
                jLImgAlert.setVisible(true);                    
            }                
            
        }/*Fin de while(true)*/                    
        
    }/*Fin de private void vCon()*/
    
    
    /*Función para mandar corres automáticamente a los clientes de agradecimiento*/
    private void vAgradCor()
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
        
        /*Comprueba si este usuario es el que tiene que mandar correos de agradecimientos automáticamente*/        
        String sDia;
        try
        {
            sQ = "SELECT manddia FROM estacs WHERE mandagra = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {   
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                  
                return;
            }
            /*Else si hay datos entonces obtiene los días*/
            else
                sDia    = rs.getString("manddia");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }
        
        /*Comprueba si esta usuario tiene un correo configurado ya*/        
        try
        {
            sQ = "SELECT id_id FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                  
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }            
        
        /*Arrego que contiene los clientes*/
        String sCli[]   = null;
        
        /*Obtiene todos los clientes que se les tiene que mandar correo de agradecimiento*/
        try
        {
            sQ = "SELECT COUNT(codemp) AS cont FROM emps WHERE DATE(agradfec) >= DATE_SUB(now(), INTERVAL " + sDia + " DAY)";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces crea el arreglo de clientes del tamaño específicado*/
            if(rs.next())
                sCli    = new String[rs.getInt("cont")];
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }
        
        /*Obtiene todos los clientes que no se le han enviado correos de agradecimiento*/
        int x = 0;
        try
        {
            sQ = "SELECT CONCAT_WS('', ser, codemp) AS codemp FROM emps WHERE DATE(agradfec) >= DATE_SUB(now(), INTERVAL " + sDia + " DAY)";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces recorre todo y ve creando el arreglo de clientes*/
            while(rs.next())
            {                
                sCli[x]  = rs.getString("codemp");                                   
                ++x;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }
        
        /*Declara variables fianales para el thread*/
        final String sCliFi[] = sCli;
        
        /*Manda los correos de cumpleaños a todos los clientes que están pendientes en la tabla de cumpleaños*/        
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vMandCoAgra(sCliFi);                                             
            }
            
        }).start();
        
    }/*Fin de private void vAgradCor()*/
                                                                                   
                    
    /*Metodo para que el formulario no se abra dos veces*/
    public static Princip getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Princip();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Login getObj()*/    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jLImgAlert = new javax.swing.JLabel();
        jP1 = new javax.swing.JPanel();
        jBPtoVta = new javax.swing.JButton();
        jBVtas = new javax.swing.JButton();
        jBComps = new javax.swing.JButton();
        jBEmps = new javax.swing.JButton();
        jBProvs = new javax.swing.JButton();
        jBProds = new javax.swing.JButton();
        jBCots = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBPrevComp = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jCheckBox1 = new javax.swing.JCheckBox();
        jMenBar1 = new javax.swing.JMenuBar();
        jMMSis = new javax.swing.JMenu();
        jMenLogin = new javax.swing.JMenu();
        jMenItLogear = new javax.swing.JMenuItem();
        jMenItDeslog = new javax.swing.JMenuItem();
        jMenItCamEmp = new javax.swing.JMenuItem();
        jMMUsr = new javax.swing.JMenu();
        jMenItEstacs = new javax.swing.JMenuItem();
        jMUsr = new javax.swing.JMenuItem();
        jMenItPermEstacs = new javax.swing.JMenuItem();
        jMen2 = new javax.swing.JMenu();
        jMenItCambClavSeg = new javax.swing.JMenuItem();
        jMMAplic = new javax.swing.JMenu();
        jMCarpAp = new javax.swing.JMenuItem();
        jMCatMsj = new javax.swing.JMenuItem();
        jMMsj = new javax.swing.JMenuItem();
        jMChatC = new javax.swing.JMenuItem();
        jMChat = new javax.swing.JMenuItem();
        jMNotic = new javax.swing.JMenuItem();
        jMForLin = new javax.swing.JMenuItem();
        jMenLogs = new javax.swing.JMenu();
        jMenItLogCorr = new javax.swing.JMenuItem();
        jMLogRes = new javax.swing.JMenuItem();
        jMLogSis = new javax.swing.JMenu();
        jMVLogSys = new javax.swing.JMenuItem();
        jMDelLog = new javax.swing.JMenuItem();
        jMMLogR = new javax.swing.JMenu();
        jMLogProd = new javax.swing.JMenuItem();
        jMLogK = new javax.swing.JMenuItem();
        jMLogLin = new javax.swing.JMenuItem();
        jMLogMarc = new javax.swing.JMenuItem();
        jMLogFab = new javax.swing.JMenuItem();
        jMLogColo = new javax.swing.JMenuItem();
        jMLogClas = new javax.swing.JMenuItem();
        jMLogPes = new javax.swing.JMenuItem();
        jMLogMed = new javax.swing.JMenuItem();
        jMLogUnid = new javax.swing.JMenuItem();
        jMLogMon = new javax.swing.JMenuItem();
        jMLogImpu = new javax.swing.JMenuItem();
        jMLogConcep = new javax.swing.JMenuItem();
        jMLogAlma = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMLogLug = new javax.swing.JMenuItem();
        jMLogUbiE = new javax.swing.JMenuItem();
        jMLogMod = new javax.swing.JMenuItem();
        jMLogTall = new javax.swing.JMenuItem();
        jMLogTip = new javax.swing.JMenuItem();
        jMLogCatAct = new javax.swing.JMenuItem();
        jMLogActFij = new javax.swing.JMenuItem();
        jMLogCatGral = new javax.swing.JMenuItem();
        jMLogClasCli = new javax.swing.JMenuItem();
        jMLogClasProv = new javax.swing.JMenuItem();
        jMLogConcepNot = new javax.swing.JMenuItem();
        jMLogGara = new javax.swing.JMenuItem();
        jMLogGir = new javax.swing.JMenuItem();
        jMLogZon = new javax.swing.JMenuItem();
        jMLogConcepPags = new javax.swing.JMenuItem();
        jMenRep = new javax.swing.JMenu();
        jMenIt8 = new javax.swing.JMenuItem();
        jMenIt9 = new javax.swing.JMenuItem();
        jMenBDs = new javax.swing.JMenu();
        jMenItModBD = new javax.swing.JMenuItem();
        jMenConfigFil = new javax.swing.JMenuItem();
        jMMRep = new javax.swing.JMenu();
        jMEstacs = new javax.swing.JMenuItem();
        jMResp = new javax.swing.JMenuItem();
        jMRepLogCo = new javax.swing.JMenuItem();
        jMEstadisCor = new javax.swing.JMenuItem();
        jMMUtil = new javax.swing.JMenu();
        jMEnviA = new javax.swing.JMenuItem();
        jMArchC = new javax.swing.JMenuItem();
        jMCuade = new javax.swing.JMenuItem();
        jMApFav = new javax.swing.JMenuItem();
        jMCalc = new javax.swing.JMenuItem();
        jMEasCon = new javax.swing.JMenuItem();
        jMRevo = new javax.swing.JMenuItem();
        jMActSis = new javax.swing.JMenuItem();
        jMenItResps = new javax.swing.JMenuItem();
        jMRese = new javax.swing.JMenuItem();
        jMenItSal = new javax.swing.JMenuItem();
        jMenInvents = new javax.swing.JMenu();
        jMenComps = new javax.swing.JMenu();
        jMenItManProvs = new javax.swing.JMenuItem();
        jMClasProv = new javax.swing.JMenuItem();
        jMRubr = new javax.swing.JMenuItem();
        jMClasJerP = new javax.swing.JMenuItem();
        jMenItComps = new javax.swing.JMenuItem();
        jMCXP = new javax.swing.JMenuItem();
        jMContra = new javax.swing.JMenuItem();
        jMImpProvs = new javax.swing.JMenuItem();
        jMExpoProvs = new javax.swing.JMenuItem();
        jMPrevioComp = new javax.swing.JMenuItem();
        jMMRepCom = new javax.swing.JMenu();
        jMRepProv = new javax.swing.JMenuItem();
        jMRepCom = new javax.swing.JMenuItem();
        jMenCXP = new javax.swing.JMenuItem();
        jMRepOr = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMInven = new javax.swing.JMenu();
        jMVProd = new javax.swing.JMenuItem();
        jMenItKits = new javax.swing.JMenuItem();
        jMLotPed = new javax.swing.JMenuItem();
        jMTallCol = new javax.swing.JMenuItem();
        jMenuMarcMod = new javax.swing.JMenuItem();
        jMTabCompa = new javax.swing.JMenuItem();
        jMenItConceps = new javax.swing.JMenuItem();
        jMMInvenImpor = new javax.swing.JMenu();
        jMInvenImpo = new javax.swing.JMenuItem();
        jMInvenImpoSer = new javax.swing.JMenuItem();
        jMImpExistAlm = new javax.swing.JMenuItem();
        jMImpAlma = new javax.swing.JMenuItem();
        jMMInvenExpor = new javax.swing.JMenu();
        jMInvenExpor = new javax.swing.JMenuItem();
        jMExpoSer = new javax.swing.JMenuItem();
        jMExpExisAlm = new javax.swing.JMenuItem();
        jMExpAlma = new javax.swing.JMenuItem();
        jMMClas = new javax.swing.JMenu();
        jMenIt1 = new javax.swing.JMenuItem();
        jMTip = new javax.swing.JMenuItem();
        jMenItMarcs = new javax.swing.JMenuItem();
        jMenItFabs = new javax.swing.JMenuItem();
        jMenItCols = new javax.swing.JMenuItem();
        jMClasProd = new javax.swing.JMenuItem();
        jMJeraProd = new javax.swing.JMenuItem();
        jMenItPesos = new javax.swing.JMenuItem();
        jMenItMeds = new javax.swing.JMenuItem();
        jMenItUnids = new javax.swing.JMenuItem();
        jMModel = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenMaxsMins = new javax.swing.JMenu();
        jMenItProdsBajMin = new javax.swing.JMenuItem();
        jMenItProdsArrMax = new javax.swing.JMenuItem();
        jMenItDefEstacs = new javax.swing.JMenuItem();
        jMenTraspas = new javax.swing.JMenu();
        jMenItTraspas = new javax.swing.JMenuItem();
        jMenItIngr = new javax.swing.JMenuItem();
        jMMUbic = new javax.swing.JMenu();
        jMenItAlmas = new javax.swing.JMenuItem();
        jMenIt5 = new javax.swing.JMenuItem();
        jMenItLugs = new javax.swing.JMenuItem();
        jMUbiAd = new javax.swing.JMenuItem();
        jMMRepProd = new javax.swing.JMenu();
        jMCosts = new javax.swing.JMenuItem();
        jMenProds = new javax.swing.JMenuItem();
        jMKard = new javax.swing.JMenuItem();
        jMenVtas = new javax.swing.JMenu();
        jMMClien = new javax.swing.JMenuItem();
        jMClasCli = new javax.swing.JMenuItem();
        jMClasJer = new javax.swing.JMenuItem();
        jMenItVtas = new javax.swing.JMenuItem();
        jMenCotsT = new javax.swing.JMenuItem();
        jMenItTipsPags = new javax.swing.JMenuItem();
        jMConcepPags = new javax.swing.JMenuItem();
        jMCXC = new javax.swing.JMenuItem();
        jMenCatGral = new javax.swing.JMenuItem();
        jMenItTouch = new javax.swing.JMenuItem();
        jMImpCliens = new javax.swing.JMenuItem();
        jMExpoCliens = new javax.swing.JMenuItem();
        jMAsoc = new javax.swing.JMenuItem();
        jMMRepVta = new javax.swing.JMenu();
        jMRepCli = new javax.swing.JMenuItem();
        jMenVtasR = new javax.swing.JMenuItem();
        jMenCXCR = new javax.swing.JMenuItem();
        jMRepCot = new javax.swing.JMenuItem();
        jMRepVend = new javax.swing.JMenuItem();
        jMFluj = new javax.swing.JMenuItem();
        jMRepBackO = new javax.swing.JMenuItem();
        jMConta = new javax.swing.JMenu();
        jMActFij = new javax.swing.JMenuItem();
        jMCatActFij = new javax.swing.JMenuItem();
        jMTipAct = new javax.swing.JMenuItem();
        jMRepConta = new javax.swing.JMenuItem();
        jMConcepNot = new javax.swing.JMenuItem();
        jMFlujAct = new javax.swing.JMenuItem();
        jMCatGara = new javax.swing.JMenuItem();
        jMZon = new javax.swing.JMenuItem();
        jMGir = new javax.swing.JMenuItem();
        jMenItMons = new javax.swing.JMenuItem();
        jMeItImps = new javax.swing.JMenuItem();
        jMSist = new javax.swing.JMenu();
        jMenIt4 = new javax.swing.JMenuItem();
        jMenItDatsGenEmp = new javax.swing.JMenuItem();
        jMenSer = new javax.swing.JMenu();
        jMeniSerFac = new javax.swing.JMenuItem();
        jMeniSerCom = new javax.swing.JMenuItem();
        jMeniSerCot = new javax.swing.JMenuItem();
        jMeniSerEmp = new javax.swing.JMenuItem();
        jMeniSerProv = new javax.swing.JMenuItem();
        jMeniTik = new javax.swing.JMenuItem();
        jMeniRem = new javax.swing.JMenuItem();
        jMSerO = new javax.swing.JMenuItem();
        NotVe = new javax.swing.JMenuItem();
        jMSerNot = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenItImpres = new javax.swing.JMenuItem();
        jMenCambIco = new javax.swing.JMenuItem();
        jMConfAd = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMMAyu = new javax.swing.JMenu();
        jMAcerc = new javax.swing.JMenuItem();

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
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
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLImg.setBackground(new java.awt.Color(255, 255, 255));
        jLImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jPanel1.add(jLImg, java.awt.BorderLayout.CENTER);

        jLImgAlert.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLImgAlert.setForeground(new java.awt.Color(204, 0, 0));
        jLImgAlert.setText("No hay Conexión a la Base de Datos");
        jPanel1.add(jLImgAlert, java.awt.BorderLayout.PAGE_END);

        jP1.setBackground(new java.awt.Color(204, 255, 204));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });

        jBPtoVta.setBackground(new java.awt.Color(255, 255, 255));
        jBPtoVta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPtoVta.setForeground(new java.awt.Color(0, 102, 0));
        jBPtoVta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pos.png"))); // NOI18N
        jBPtoVta.setText("POS");
        jBPtoVta.setToolTipText("Punto de venta (U)");
        jBPtoVta.setBorder(null);
        jBPtoVta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPtoVtaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPtoVtaMouseExited(evt);
            }
        });
        jBPtoVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPtoVtaActionPerformed(evt);
            }
        });
        jBPtoVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPtoVtaKeyPressed(evt);
            }
        });

        jBVtas.setBackground(new java.awt.Color(255, 255, 255));
        jBVtas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBVtas.setForeground(new java.awt.Color(0, 102, 0));
        jBVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/vtas2.png"))); // NOI18N
        jBVtas.setText("Ventas");
        jBVtas.setToolTipText("Módulo de ventas (V)");
        jBVtas.setBorder(null);
        jBVtas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVtasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVtasMouseExited(evt);
            }
        });
        jBVtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVtasActionPerformed(evt);
            }
        });
        jBVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVtasKeyPressed(evt);
            }
        });

        jBComps.setBackground(new java.awt.Color(255, 255, 255));
        jBComps.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBComps.setForeground(new java.awt.Color(0, 102, 0));
        jBComps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/comprs2.png"))); // NOI18N
        jBComps.setText("Compras");
        jBComps.setToolTipText("Módulo de compras y órdenes (O)");
        jBComps.setBorder(null);
        jBComps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCompsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCompsMouseExited(evt);
            }
        });
        jBComps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompsActionPerformed(evt);
            }
        });
        jBComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCompsKeyPressed(evt);
            }
        });

        jBEmps.setBackground(new java.awt.Color(255, 255, 255));
        jBEmps.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBEmps.setForeground(new java.awt.Color(0, 102, 0));
        jBEmps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/manemp.png"))); // NOI18N
        jBEmps.setText("Clientes");
        jBEmps.setToolTipText("Módulo de clientes (E)");
        jBEmps.setBorder(null);
        jBEmps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEmpsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEmpsMouseExited(evt);
            }
        });
        jBEmps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEmpsActionPerformed(evt);
            }
        });
        jBEmps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEmpsKeyPressed(evt);
            }
        });

        jBProvs.setBackground(new java.awt.Color(255, 255, 255));
        jBProvs.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBProvs.setForeground(new java.awt.Color(0, 102, 0));
        jBProvs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/provs.png"))); // NOI18N
        jBProvs.setText("Proveedores");
        jBProvs.setToolTipText("Módulo de proveedores (P)");
        jBProvs.setBorder(null);
        jBProvs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProvsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProvsMouseExited(evt);
            }
        });
        jBProvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProvsActionPerformed(evt);
            }
        });
        jBProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProvsKeyPressed(evt);
            }
        });

        jBProds.setBackground(new java.awt.Color(255, 255, 255));
        jBProds.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBProds.setForeground(new java.awt.Color(0, 102, 0));
        jBProds.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prods.png"))); // NOI18N
        jBProds.setText("Producto");
        jBProds.setToolTipText("Módulo de productos (R)");
        jBProds.setBorder(null);
        jBProds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdsMouseExited(evt);
            }
        });
        jBProds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdsActionPerformed(evt);
            }
        });
        jBProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdsKeyPressed(evt);
            }
        });

        jBCots.setBackground(new java.awt.Color(255, 255, 255));
        jBCots.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBCots.setForeground(new java.awt.Color(0, 102, 0));
        jBCots.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cots.png"))); // NOI18N
        jBCots.setText("Cotizaciones");
        jBCots.setToolTipText("Módulo de cotizaciones (C)");
        jBCots.setBorder(null);
        jBCots.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCotsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCotsMouseExited(evt);
            }
        });
        jBCots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCotsActionPerformed(evt);
            }
        });
        jBCots.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCotsKeyPressed(evt);
            }
        });

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

        jBPrevComp.setBackground(new java.awt.Color(255, 255, 255));
        jBPrevComp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPrevComp.setForeground(new java.awt.Color(0, 102, 0));
        jBPrevComp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Prevcom.png"))); // NOI18N
        jBPrevComp.setText("Previo de compra");
        jBPrevComp.setToolTipText("Módulo de previo de compra (B)");
        jBPrevComp.setBorder(null);
        jBPrevComp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPrevCompMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPrevCompMouseExited(evt);
            }
        });
        jBPrevComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrevCompActionPerformed(evt);
            }
        });
        jBPrevComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrevCompKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jP1Layout = new javax.swing.GroupLayout(jP1);
        jP1.setLayout(jP1Layout);
        jP1Layout.setHorizontalGroup(
            jP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPtoVta, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBVtas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBComps, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBEmps, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBProvs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBProds, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCots, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPrevComp)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLAyu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jP1Layout.setVerticalGroup(
            jP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBPtoVta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBVtas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBComps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBEmps, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jBProvs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBProds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBCots, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPrevComp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addComponent(jLAyu))
        );

        jPanel1.add(jP1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCheckBox1.setText("jCheckBox1");

        jMenBar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenBar1KeyPressed(evt);
            }
        });

        jMMSis.setMnemonic('s');
        jMMSis.setText("Sistema");
        jMMSis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMSisKeyPressed(evt);
            }
        });

        jMenLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logi.png"))); // NOI18N
        jMenLogin.setMnemonic('l');
        jMenLogin.setText("Sesión");
        jMenLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenLoginKeyPressed(evt);
            }
        });

        jMenItLogear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenItLogear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/login.png"))); // NOI18N
        jMenItLogear.setText("Cambiar sesión");
        jMenItLogear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItLogearActionPerformed(evt);
            }
        });
        jMenItLogear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItLogearKeyPressed(evt);
            }
        });
        jMenLogin.add(jMenItLogear);

        jMenItDeslog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenItDeslog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Logout.png"))); // NOI18N
        jMenItDeslog.setMnemonic('d');
        jMenItDeslog.setText("Suspender");
        jMenItDeslog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItDeslogActionPerformed(evt);
            }
        });
        jMenItDeslog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItDeslogKeyPressed(evt);
            }
        });
        jMenLogin.add(jMenItDeslog);

        jMenItCamEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cambemp.png"))); // NOI18N
        jMenItCamEmp.setMnemonic('c');
        jMenItCamEmp.setText("Cambiar de empresa");
        jMenItCamEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItCamEmpActionPerformed(evt);
            }
        });
        jMenItCamEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItCamEmpKeyPressed(evt);
            }
        });
        jMenLogin.add(jMenItCamEmp);

        jMMSis.add(jMenLogin);

        jMMUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/estacs.png"))); // NOI18N
        jMMUsr.setMnemonic('u');
        jMMUsr.setText("Usuarios");
        jMMUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMUsrKeyPressed(evt);
            }
        });

        jMenItEstacs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/defusr.png"))); // NOI18N
        jMenItEstacs.setMnemonic('e');
        jMenItEstacs.setText("Definir usuarios");
        jMenItEstacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItEstacsActionPerformed(evt);
            }
        });
        jMenItEstacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItEstacsKeyPressed(evt);
            }
        });
        jMMUsr.add(jMenItEstacs);

        jMUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/usrcon.png"))); // NOI18N
        jMUsr.setText("Usuarios conectados");
        jMUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMUsrActionPerformed(evt);
            }
        });
        jMUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMUsrKeyPressed(evt);
            }
        });
        jMMUsr.add(jMUsr);

        jMenItPermEstacs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/permusr.png"))); // NOI18N
        jMenItPermEstacs.setMnemonic('p');
        jMenItPermEstacs.setText("Permisos usuarios");
        jMenItPermEstacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItPermEstacsActionPerformed(evt);
            }
        });
        jMMUsr.add(jMenItPermEstacs);

        jMMSis.add(jMMUsr);

        jMen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/contra.png"))); // NOI18N
        jMen2.setMnemonic('l');
        jMen2.setText("Claves");
        jMen2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMen2KeyPressed(evt);
            }
        });

        jMenItCambClavSeg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cambclav.png"))); // NOI18N
        jMenItCambClavSeg.setMnemonic('c');
        jMenItCambClavSeg.setText("Cambiar clave de seguridad");
        jMenItCambClavSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItCambClavSegActionPerformed(evt);
            }
        });
        jMenItCambClavSeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItCambClavSegKeyPressed(evt);
            }
        });
        jMen2.add(jMenItCambClavSeg);

        jMMSis.add(jMen2);

        jMMAplic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/aplica.png"))); // NOI18N
        jMMAplic.setMnemonic('a');
        jMMAplic.setText("Aplicación");
        jMMAplic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMAplicKeyPressed(evt);
            }
        });

        jMCarpAp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirap.png"))); // NOI18N
        jMCarpAp.setMnemonic('c');
        jMCarpAp.setText("Directorio aplicación");
        jMCarpAp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCarpApActionPerformed(evt);
            }
        });
        jMCarpAp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCarpApKeyPressed(evt);
            }
        });
        jMMAplic.add(jMCarpAp);

        jMCatMsj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/catmens2.png"))); // NOI18N
        jMCatMsj.setText("Catálogo de mensajes");
        jMCatMsj.setToolTipText("a");
        jMCatMsj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCatMsjActionPerformed(evt);
            }
        });
        jMCatMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCatMsjKeyPressed(evt);
            }
        });
        jMMAplic.add(jMCatMsj);

        jMMsj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/msjs.png"))); // NOI18N
        jMMsj.setMnemonic('m');
        jMMsj.setText("Mensajes");
        jMMsj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMMsjActionPerformed(evt);
            }
        });
        jMMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMsjKeyPressed(evt);
            }
        });
        jMMAplic.add(jMMsj);

        jMChatC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/chatcorp.png"))); // NOI18N
        jMChatC.setMnemonic('h');
        jMChatC.setText("Easy message");
        jMChatC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMChatCActionPerformed(evt);
            }
        });
        jMChatC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMChatCKeyPressed(evt);
            }
        });
        jMMAplic.add(jMChatC);

        jMChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cht.png"))); // NOI18N
        jMChat.setMnemonic('t');
        jMChat.setText("Easy talk");
        jMChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMChatActionPerformed(evt);
            }
        });
        jMChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMChatKeyPressed(evt);
            }
        });
        jMMAplic.add(jMChat);

        jMNotic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/news.png"))); // NOI18N
        jMNotic.setText("Avisos y noticias");
        jMNotic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMNoticActionPerformed(evt);
            }
        });
        jMNotic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMNoticKeyPressed(evt);
            }
        });
        jMMAplic.add(jMNotic);

        jMForLin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/forlin.png"))); // NOI18N
        jMForLin.setText("Foro en línea");
        jMForLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMForLinActionPerformed(evt);
            }
        });
        jMForLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMForLinKeyPressed(evt);
            }
        });
        jMMAplic.add(jMForLin);

        jMMSis.add(jMMAplic);

        jMenLogs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log.png"))); // NOI18N
        jMenLogs.setMnemonic('g');
        jMenLogs.setText("Logs");
        jMenLogs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenLogsKeyPressed(evt);
            }
        });

        jMenItLogCorr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logcorr.png"))); // NOI18N
        jMenItLogCorr.setMnemonic('l');
        jMenItLogCorr.setText("Log correos");
        jMenItLogCorr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItLogCorrActionPerformed(evt);
            }
        });
        jMenItLogCorr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItLogCorrKeyPressed(evt);
            }
        });
        jMenLogs.add(jMenItLogCorr);

        jMLogRes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logresp.png"))); // NOI18N
        jMLogRes.setMnemonic('r');
        jMLogRes.setText("Log respaldos");
        jMLogRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogResActionPerformed(evt);
            }
        });
        jMLogRes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogResKeyPressed(evt);
            }
        });
        jMenLogs.add(jMLogRes);

        jMLogSis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logsist.png"))); // NOI18N
        jMLogSis.setMnemonic('g');
        jMLogSis.setText("Log sistema");
        jMLogSis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogSisKeyPressed(evt);
            }
        });

        jMVLogSys.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logsist.png"))); // NOI18N
        jMVLogSys.setMnemonic('v');
        jMVLogSys.setText("Ver log sistema");
        jMVLogSys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMVLogSysActionPerformed(evt);
            }
        });
        jMVLogSys.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMVLogSysKeyPressed(evt);
            }
        });
        jMLogSis.add(jMVLogSys);

        jMDelLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dellogsis.png"))); // NOI18N
        jMDelLog.setMnemonic('b');
        jMDelLog.setText("Borrar log");
        jMDelLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMDelLogActionPerformed(evt);
            }
        });
        jMDelLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMDelLogKeyPressed(evt);
            }
        });
        jMLogSis.add(jMDelLog);

        jMenLogs.add(jMLogSis);

        jMMLogR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/lograst.png"))); // NOI18N
        jMMLogR.setMnemonic('o');
        jMMLogR.setText("Logs rastreo");
        jMMLogR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMLogRKeyPressed(evt);
            }
        });

        jMLogProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogProd.setMnemonic('l');
        jMLogProd.setText("Log productos");
        jMLogProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogProdActionPerformed(evt);
            }
        });
        jMLogProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogProdKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogProd);

        jMLogK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogK.setMnemonic('o');
        jMLogK.setText("Log kits");
        jMLogK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogKActionPerformed(evt);
            }
        });
        jMLogK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogKKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogK);

        jMLogLin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogLin.setMnemonic('o');
        jMLogLin.setText("Log líneas");
        jMLogLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogLinActionPerformed(evt);
            }
        });
        jMLogLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogLinKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogLin);

        jMLogMarc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogMarc.setMnemonic('m');
        jMLogMarc.setText("Log marcas");
        jMLogMarc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogMarcActionPerformed(evt);
            }
        });
        jMLogMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogMarcKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogMarc);

        jMLogFab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogFab.setMnemonic('f');
        jMLogFab.setText("Log fabricantes");
        jMLogFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogFabActionPerformed(evt);
            }
        });
        jMLogFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogFabKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogFab);

        jMLogColo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogColo.setMnemonic('c');
        jMLogColo.setText("Log colores");
        jMLogColo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogColoActionPerformed(evt);
            }
        });
        jMLogColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogColoKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogColo);

        jMLogClas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogClas.setMnemonic('a');
        jMLogClas.setText("Log clasificación extra productos");
        jMLogClas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogClasActionPerformed(evt);
            }
        });
        jMLogClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogClasKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogClas);

        jMLogPes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogPes.setMnemonic('p');
        jMLogPes.setText("Log pesos");
        jMLogPes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogPesActionPerformed(evt);
            }
        });
        jMLogPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogPesKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogPes);

        jMLogMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogMed.setMnemonic('m');
        jMLogMed.setText("Log medidas");
        jMLogMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogMedActionPerformed(evt);
            }
        });
        jMLogMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogMedKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogMed);

        jMLogUnid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogUnid.setMnemonic('u');
        jMLogUnid.setText("Log unidades");
        jMLogUnid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogUnidActionPerformed(evt);
            }
        });
        jMLogUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogUnidKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogUnid);

        jMLogMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogMon.setMnemonic('m');
        jMLogMon.setText("Log monedas");
        jMLogMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogMonActionPerformed(evt);
            }
        });
        jMLogMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogMonKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogMon);

        jMLogImpu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogImpu.setMnemonic('i');
        jMLogImpu.setText("Log impuestos");
        jMLogImpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogImpuActionPerformed(evt);
            }
        });
        jMLogImpu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogImpuKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogImpu);

        jMLogConcep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogConcep.setMnemonic('o');
        jMLogConcep.setText("Log conceptos");
        jMLogConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogConcepActionPerformed(evt);
            }
        });
        jMLogConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogConcepKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogConcep);

        jMLogAlma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogAlma.setMnemonic('l');
        jMLogAlma.setText("Log almacenes");
        jMLogAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogAlmaActionPerformed(evt);
            }
        });
        jMLogAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogAlmaKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogAlma);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMenuItem1.setMnemonic('q');
        jMenuItem1.setText("Log anaqueles");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuItem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem1KeyPressed(evt);
            }
        });
        jMMLogR.add(jMenuItem1);

        jMLogLug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogLug.setMnemonic('g');
        jMLogLug.setText("Log lugares");
        jMLogLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogLugActionPerformed(evt);
            }
        });
        jMLogLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogLugKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogLug);

        jMLogUbiE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogUbiE.setMnemonic('c');
        jMLogUbiE.setText("Log ubicaciones extra");
        jMLogUbiE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogUbiEActionPerformed(evt);
            }
        });
        jMLogUbiE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogUbiEKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogUbiE);

        jMLogMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogMod.setMnemonic('o');
        jMLogMod.setText("Log modelos");
        jMLogMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogModActionPerformed(evt);
            }
        });
        jMLogMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogModKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogMod);

        jMLogTall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogTall.setMnemonic('l');
        jMLogTall.setText("Log tallas");
        jMLogTall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogTallActionPerformed(evt);
            }
        });
        jMLogTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogTallKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogTall);

        jMLogTip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogTip.setText("Log tipos");
        jMLogTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogTipActionPerformed(evt);
            }
        });
        jMLogTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogTipKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogTip);

        jMLogCatAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogCatAct.setText("Log catálogo activo fijo");
        jMLogCatAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogCatActActionPerformed(evt);
            }
        });
        jMLogCatAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogCatActKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogCatAct);

        jMLogActFij.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogActFij.setText("Log tipo activo fijo");
        jMLogActFij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogActFijActionPerformed(evt);
            }
        });
        jMLogActFij.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogActFijKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogActFij);

        jMLogCatGral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogCatGral.setText("Log catálogo general");
        jMLogCatGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogCatGralActionPerformed(evt);
            }
        });
        jMLogCatGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogCatGralKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogCatGral);

        jMLogClasCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogClasCli.setText("Log clasificación clientes");
        jMLogClasCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogClasCliActionPerformed(evt);
            }
        });
        jMLogClasCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogClasCliKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogClasCli);

        jMLogClasProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogClasProv.setText("Log clasificación proveedores");
        jMLogClasProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogClasProvActionPerformed(evt);
            }
        });
        jMLogClasProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogClasProvKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogClasProv);

        jMLogConcepNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogConcepNot.setText("Log conceptos nota de crédito");
        jMLogConcepNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogConcepNotActionPerformed(evt);
            }
        });
        jMLogConcepNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogConcepNotKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogConcepNot);

        jMLogGara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogGara.setText("Log garantías");
        jMLogGara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogGaraActionPerformed(evt);
            }
        });
        jMLogGara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogGaraKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogGara);

        jMLogGir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogGir.setText("Log giros");
        jMLogGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogGirActionPerformed(evt);
            }
        });
        jMLogGir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogGirKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogGir);

        jMLogZon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogZon.setText("Log zonas");
        jMLogZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogZonActionPerformed(evt);
            }
        });
        jMLogZon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogZonKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogZon);

        jMLogConcepPags.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/log2.png"))); // NOI18N
        jMLogConcepPags.setText("Log concepto pagos");
        jMLogConcepPags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLogConcepPagsActionPerformed(evt);
            }
        });
        jMLogConcepPags.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLogConcepPagsKeyPressed(evt);
            }
        });
        jMMLogR.add(jMLogConcepPags);

        jMenLogs.add(jMMLogR);

        jMMSis.add(jMenLogs);

        jMenRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repa.png"))); // NOI18N
        jMenRep.setMnemonic('p');
        jMenRep.setText("Reparar");
        jMenRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenRepKeyPressed(evt);
            }
        });

        jMenIt8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rep.png"))); // NOI18N
        jMenIt8.setMnemonic('r');
        jMenIt8.setText("Reparador de errores");
        jMenIt8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenIt8ActionPerformed(evt);
            }
        });
        jMenIt8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenIt8KeyPressed(evt);
            }
        });
        jMenRep.add(jMenIt8);

        jMenIt9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/restau.png"))); // NOI18N
        jMenIt9.setMnemonic('e');
        jMenIt9.setText("Restaurar sistema");
        jMenIt9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenIt9ActionPerformed(evt);
            }
        });
        jMenIt9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenIt9KeyPressed(evt);
            }
        });
        jMenRep.add(jMenIt9);

        jMMSis.add(jMenRep);

        jMenBDs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/basdats.png"))); // NOI18N
        jMenBDs.setMnemonic('b');
        jMenBDs.setText("Base de datos");
        jMenBDs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenBDsKeyPressed(evt);
            }
        });

        jMenItModBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/conect.png"))); // NOI18N
        jMenItModBD.setMnemonic('m');
        jMenItModBD.setText("Conexiones a bases de datos");
        jMenItModBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItModBDActionPerformed(evt);
            }
        });
        jMenItModBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItModBDKeyPressed(evt);
            }
        });
        jMenBDs.add(jMenItModBD);

        jMenConfigFil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/archconfg.png"))); // NOI18N
        jMenConfigFil.setMnemonic('a');
        jMenConfigFil.setText("Archivo de configuración");
        jMenConfigFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenConfigFilActionPerformed(evt);
            }
        });
        jMenConfigFil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenConfigFilKeyPressed(evt);
            }
        });
        jMenBDs.add(jMenConfigFil);

        jMMSis.add(jMenBDs);

        jMMRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repgen.png"))); // NOI18N
        jMMRep.setText("Reportes");
        jMMRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMRepKeyPressed(evt);
            }
        });

        jMEstacs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptusr.png"))); // NOI18N
        jMEstacs.setMnemonic('t');
        jMEstacs.setText("Usuarios");
        jMEstacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMEstacsActionPerformed(evt);
            }
        });
        jMEstacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMEstacsKeyPressed(evt);
            }
        });
        jMMRep.add(jMEstacs);

        jMResp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/represp.png"))); // NOI18N
        jMResp.setMnemonic('d');
        jMResp.setText("Respaldos");
        jMResp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRespActionPerformed(evt);
            }
        });
        jMResp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRespKeyPressed(evt);
            }
        });
        jMMRep.add(jMResp);

        jMRepLogCo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/replogcor.png"))); // NOI18N
        jMRepLogCo.setMnemonic('l');
        jMRepLogCo.setText("Log correos");
        jMRepLogCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepLogCoActionPerformed(evt);
            }
        });
        jMRepLogCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepLogCoKeyPressed(evt);
            }
        });
        jMMRep.add(jMRepLogCo);

        jMEstadisCor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/replogcor.png"))); // NOI18N
        jMEstadisCor.setText("Estadísticas de correo");
        jMEstadisCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMEstadisCorActionPerformed(evt);
            }
        });
        jMEstadisCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMEstadisCorKeyPressed(evt);
            }
        });
        jMMRep.add(jMEstadisCor);

        jMMSis.add(jMMRep);

        jMMUtil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/util.png"))); // NOI18N
        jMMUtil.setText("Utilerías");
        jMMUtil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMUtilKeyPressed(evt);
            }
        });

        jMEnviA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/env.png"))); // NOI18N
        jMEnviA.setMnemonic('e');
        jMEnviA.setText("Enviar/Descargar archivo(s)");
        jMEnviA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMEnviAActionPerformed(evt);
            }
        });
        jMEnviA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMEnviAKeyPressed(evt);
            }
        });
        jMMUtil.add(jMEnviA);

        jMArchC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/archcomp.png"))); // NOI18N
        jMArchC.setMnemonic('h');
        jMArchC.setText("Easy drive");
        jMArchC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMArchCActionPerformed(evt);
            }
        });
        jMArchC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMArchCKeyPressed(evt);
            }
        });
        jMMUtil.add(jMArchC);

        jMCuade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cuade.png"))); // NOI18N
        jMCuade.setMnemonic('u');
        jMCuade.setText("Cuaderno");
        jMCuade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCuadeActionPerformed(evt);
            }
        });
        jMCuade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCuadeKeyPressed(evt);
            }
        });
        jMMUtil.add(jMCuade);

        jMApFav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/apfav.png"))); // NOI18N
        jMApFav.setMnemonic('a');
        jMApFav.setText("Aplicación favorita");
        jMApFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMApFavActionPerformed(evt);
            }
        });
        jMApFav.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMApFavKeyPressed(evt);
            }
        });
        jMMUtil.add(jMApFav);

        jMCalc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/calc.png"))); // NOI18N
        jMCalc.setMnemonic('c');
        jMCalc.setText("Calculadora");
        jMCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCalcActionPerformed(evt);
            }
        });
        jMCalc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCalcKeyPressed(evt);
            }
        });
        jMMUtil.add(jMCalc);

        jMEasCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/easyconve.png"))); // NOI18N
        jMEasCon.setText("Easy convertidor");
        jMEasCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMEasConActionPerformed(evt);
            }
        });
        jMEasCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMEasConKeyPressed(evt);
            }
        });
        jMMUtil.add(jMEasCon);

        jMMSis.add(jMMUtil);

        jMRevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/revosis.png"))); // NOI18N
        jMRevo.setText("Revocación");
        jMRevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRevoActionPerformed(evt);
            }
        });
        jMRevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRevoKeyPressed(evt);
            }
        });
        jMMSis.add(jMRevo);

        jMActSis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actsis.png"))); // NOI18N
        jMActSis.setText("Activar sistema");
        jMActSis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMActSisActionPerformed(evt);
            }
        });
        jMActSis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMActSisKeyPressed(evt);
            }
        });
        jMMSis.add(jMActSis);

        jMenItResps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/respal.png"))); // NOI18N
        jMenItResps.setMnemonic('s');
        jMenItResps.setText("Respaldos");
        jMenItResps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItRespsActionPerformed(evt);
            }
        });
        jMenItResps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItRespsKeyPressed(evt);
            }
        });
        jMMSis.add(jMenItResps);

        jMRese.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/reset.png"))); // NOI18N
        jMRese.setMnemonic('t');
        jMRese.setText("Reiniciar");
        jMRese.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMReseActionPerformed(evt);
            }
        });
        jMRese.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMReseKeyPressed(evt);
            }
        });
        jMMSis.add(jMRese);

        jMenItSal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenItSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jMenItSal.setText("Salir");
        jMenItSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItSalActionPerformed(evt);
            }
        });
        jMenItSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItSalKeyPressed(evt);
            }
        });
        jMMSis.add(jMenItSal);

        jMenBar1.add(jMMSis);

        jMenInvents.setMnemonic('M');
        jMenInvents.setText("Módulos");
        jMenInvents.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenInventsKeyPressed(evt);
            }
        });

        jMenComps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/comprs2.png"))); // NOI18N
        jMenComps.setMnemonic('o');
        jMenComps.setText("Compras");
        jMenComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCompsKeyPressed(evt);
            }
        });

        jMenItManProvs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jMenItManProvs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/provs.png"))); // NOI18N
        jMenItManProvs.setMnemonic('a');
        jMenItManProvs.setText("Proveedores");
        jMenItManProvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItManProvsActionPerformed(evt);
            }
        });
        jMenItManProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItManProvsKeyPressed(evt);
            }
        });
        jMenComps.add(jMenItManProvs);

        jMClasProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasifs.png"))); // NOI18N
        jMClasProv.setMnemonic('l');
        jMClasProv.setText("Clasificaciones proveedores");
        jMClasProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMClasProvActionPerformed(evt);
            }
        });
        jMClasProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMClasProvKeyPressed(evt);
            }
        });
        jMenComps.add(jMClasProv);

        jMRubr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasrub.png"))); // NOI18N
        jMRubr.setText("Clasificaciones rubro");
        jMRubr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRubrActionPerformed(evt);
            }
        });
        jMRubr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRubrKeyPressed(evt);
            }
        });
        jMenComps.add(jMRubr);

        jMClasJerP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasjeraprov.png"))); // NOI18N
        jMClasJerP.setText("Clasificaciones jerárquicas");
        jMClasJerP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMClasJerPActionPerformed(evt);
            }
        });
        jMClasJerP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMClasJerPKeyPressed(evt);
            }
        });
        jMenComps.add(jMClasJerP);

        jMenItComps.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, 0));
        jMenItComps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ordcom.png"))); // NOI18N
        jMenItComps.setMnemonic('c');
        jMenItComps.setText("Órdenes/Compras");
        jMenItComps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItCompsActionPerformed(evt);
            }
        });
        jMenItComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItCompsKeyPressed(evt);
            }
        });
        jMenComps.add(jMenItComps);

        jMCXP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cxp.png"))); // NOI18N
        jMCXP.setMnemonic('X');
        jMCXP.setText("CXP");
        jMCXP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCXPActionPerformed(evt);
            }
        });
        jMCXP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCXPKeyPressed(evt);
            }
        });
        jMenComps.add(jMCXP);

        jMContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/contracu.png"))); // NOI18N
        jMContra.setMnemonic('c');
        jMContra.setText("Contra recibos");
        jMContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMContraActionPerformed(evt);
            }
        });
        jMContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMContraKeyPressed(evt);
            }
        });
        jMenComps.add(jMContra);

        jMImpProvs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jMImpProvs.setText("Importar catálogo de proveedores desde excel");
        jMImpProvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMImpProvsActionPerformed(evt);
            }
        });
        jMImpProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMImpProvsKeyPressed(evt);
            }
        });
        jMenComps.add(jMImpProvs);

        jMExpoProvs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMExpoProvs.setText("Exportar catálogo de proveedores a excel");
        jMExpoProvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExpoProvsActionPerformed(evt);
            }
        });
        jMExpoProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMExpoProvsKeyPressed(evt);
            }
        });
        jMenComps.add(jMExpoProvs);

        jMPrevioComp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, 0));
        jMPrevioComp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Prevcom.png"))); // NOI18N
        jMPrevioComp.setText("Previo de compra");
        jMPrevioComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMPrevioCompActionPerformed(evt);
            }
        });
        jMenComps.add(jMPrevioComp);

        jMMRepCom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repgen.png"))); // NOI18N
        jMMRepCom.setText("Reportes");
        jMMRepCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMRepComKeyPressed(evt);
            }
        });

        jMRepProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptprov.png"))); // NOI18N
        jMRepProv.setText("Proveedores");
        jMRepProv.setToolTipText("p");
        jMRepProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepProvActionPerformed(evt);
            }
        });
        jMRepProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepProvKeyPressed(evt);
            }
        });
        jMMRepCom.add(jMRepProv);

        jMRepCom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repcom.png"))); // NOI18N
        jMRepCom.setMnemonic('o');
        jMRepCom.setText("Compras");
        jMRepCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepComActionPerformed(evt);
            }
        });
        jMRepCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepComKeyPressed(evt);
            }
        });
        jMMRepCom.add(jMRepCom);

        jMenCXP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptcxp.png"))); // NOI18N
        jMenCXP.setMnemonic('c');
        jMenCXP.setText("CXP");
        jMenCXP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenCXPActionPerformed(evt);
            }
        });
        jMenCXP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCXPKeyPressed(evt);
            }
        });
        jMMRepCom.add(jMenCXP);

        jMRepOr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repord.png"))); // NOI18N
        jMRepOr.setMnemonic('m');
        jMRepOr.setText("Órdenes de compra");
        jMRepOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepOrActionPerformed(evt);
            }
        });
        jMRepOr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepOrKeyPressed(evt);
            }
        });
        jMMRepCom.add(jMRepOr);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Prevcom.png"))); // NOI18N
        jMenuItem4.setText("Previo de compra");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMMRepCom.add(jMenuItem4);

        jMenComps.add(jMMRepCom);

        jMenInvents.add(jMenComps);

        jMInven.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/inven.png"))); // NOI18N
        jMInven.setMnemonic('p');
        jMInven.setText("Inventarios");
        jMInven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMInvenKeyPressed(evt);
            }
        });

        jMVProd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        jMVProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prods.png"))); // NOI18N
        jMVProd.setMnemonic('P');
        jMVProd.setText("Productos");
        jMVProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMVProdActionPerformed(evt);
            }
        });
        jMVProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMVProdKeyPressed(evt);
            }
        });
        jMInven.add(jMVProd);

        jMenItKits.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/kits.png"))); // NOI18N
        jMenItKits.setMnemonic('i');
        jMenItKits.setText("Kits");
        jMenItKits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItKitsActionPerformed(evt);
            }
        });
        jMenItKits.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItKitsKeyPressed(evt);
            }
        });
        jMInven.add(jMenItKits);

        jMLotPed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/lotped.png"))); // NOI18N
        jMLotPed.setText("Lotes y pedimentos");
        jMLotPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMLotPedActionPerformed(evt);
            }
        });
        jMLotPed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMLotPedKeyPressed(evt);
            }
        });
        jMInven.add(jMLotPed);

        jMTallCol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tallcol.png"))); // NOI18N
        jMTallCol.setMnemonic('t');
        jMTallCol.setText("Tallas y colores");
        jMTallCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMTallColActionPerformed(evt);
            }
        });
        jMTallCol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMTallColKeyPressed(evt);
            }
        });
        jMInven.add(jMTallCol);

        jMenuMarcMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/modmarc.png"))); // NOI18N
        jMenuMarcMod.setText("Marcas y modelos");
        jMenuMarcMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMarcModActionPerformed(evt);
            }
        });
        jMenuMarcMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuMarcModKeyPressed(evt);
            }
        });
        jMInven.add(jMenuMarcMod);

        jMTabCompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabcompa.png"))); // NOI18N
        jMTabCompa.setMnemonic('v');
        jMTabCompa.setText("Ver tabla de compatibilidades");
        jMTabCompa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMTabCompaActionPerformed(evt);
            }
        });
        jMTabCompa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMTabCompaKeyPressed(evt);
            }
        });
        jMInven.add(jMTabCompa);

        jMenItConceps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/conceps.png"))); // NOI18N
        jMenItConceps.setMnemonic('t');
        jMenItConceps.setText("Conceptos");
        jMenItConceps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItConcepsActionPerformed(evt);
            }
        });
        jMenItConceps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItConcepsKeyPressed(evt);
            }
        });
        jMInven.add(jMenItConceps);

        jMMInvenImpor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/import.png"))); // NOI18N
        jMMInvenImpor.setText("Importaciones");
        jMMInvenImpor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMInvenImporKeyPressed(evt);
            }
        });

        jMInvenImpo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jMInvenImpo.setMnemonic('I');
        jMInvenImpo.setText("Importar catálogo de productos desde excel");
        jMInvenImpo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMInvenImpoActionPerformed(evt);
            }
        });
        jMMInvenImpor.add(jMInvenImpo);

        jMInvenImpoSer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impcatser.png"))); // NOI18N
        jMInvenImpoSer.setMnemonic('m');
        jMInvenImpoSer.setText("Importar catálogo de series y comentarios por producto");
        jMInvenImpoSer.setToolTipText("");
        jMInvenImpoSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMInvenImpoSerActionPerformed(evt);
            }
        });
        jMInvenImpoSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMInvenImpoSerKeyPressed(evt);
            }
        });
        jMMInvenImpor.add(jMInvenImpoSer);

        jMImpExistAlm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jMImpExistAlm.setMnemonic('p');
        jMImpExistAlm.setText("Importar existencias por almacén");
        jMImpExistAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMImpExistAlmActionPerformed(evt);
            }
        });
        jMImpExistAlm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMImpExistAlmKeyPressed(evt);
            }
        });
        jMMInvenImpor.add(jMImpExistAlm);

        jMImpAlma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jMImpAlma.setMnemonic('o');
        jMImpAlma.setText("Importar almacenes");
        jMImpAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMImpAlmaActionPerformed(evt);
            }
        });
        jMImpAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMImpAlmaKeyPressed(evt);
            }
        });
        jMMInvenImpor.add(jMImpAlma);

        jMInven.add(jMMInvenImpor);

        jMMInvenExpor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/export.png"))); // NOI18N
        jMMInvenExpor.setText("Exportaciones");
        jMMInvenExpor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMInvenExporKeyPressed(evt);
            }
        });

        jMInvenExpor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMInvenExpor.setText("Exportar catálogo de productos a excel");
        jMInvenExpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMInvenExporActionPerformed(evt);
            }
        });
        jMInvenExpor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMInvenExporKeyPressed(evt);
            }
        });
        jMMInvenExpor.add(jMInvenExpor);

        jMExpoSer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMExpoSer.setMnemonic('S');
        jMExpoSer.setText("Exportar series por producto a excel");
        jMExpoSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExpoSerActionPerformed(evt);
            }
        });
        jMExpoSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMExpoSerKeyPressed(evt);
            }
        });
        jMMInvenExpor.add(jMExpoSer);

        jMExpExisAlm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMExpExisAlm.setMnemonic('E');
        jMExpExisAlm.setText("Exportar existencias por almacén a excel");
        jMExpExisAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExpExisAlmActionPerformed(evt);
            }
        });
        jMExpExisAlm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMExpExisAlmKeyPressed(evt);
            }
        });
        jMMInvenExpor.add(jMExpExisAlm);

        jMExpAlma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMExpAlma.setMnemonic('X');
        jMExpAlma.setText("Exportar almacenes a excel");
        jMExpAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExpAlmaActionPerformed(evt);
            }
        });
        jMExpAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMExpAlmaKeyPressed(evt);
            }
        });
        jMMInvenExpor.add(jMExpAlma);

        jMInven.add(jMMInvenExpor);

        jMMClas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasifs.png"))); // NOI18N
        jMMClas.setText("Clasificaciones");
        jMMClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMClasKeyPressed(evt);
            }
        });

        jMenIt1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/claslin.png"))); // NOI18N
        jMenIt1.setMnemonic('l');
        jMenIt1.setText("Líneas");
        jMenIt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenIt1ActionPerformed(evt);
            }
        });
        jMenIt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenIt1KeyPressed(evt);
            }
        });
        jMMClas.add(jMenIt1);

        jMTip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tips.png"))); // NOI18N
        jMTip.setMnemonic('s');
        jMTip.setText("Tipos");
        jMTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMTipActionPerformed(evt);
            }
        });
        jMTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMTipKeyPressed(evt);
            }
        });
        jMMClas.add(jMTip);

        jMenItMarcs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasmarc.png"))); // NOI18N
        jMenItMarcs.setMnemonic('m');
        jMenItMarcs.setText("Marcas");
        jMenItMarcs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItMarcsActionPerformed(evt);
            }
        });
        jMenItMarcs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItMarcsKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItMarcs);

        jMenItFabs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasfab.png"))); // NOI18N
        jMenItFabs.setMnemonic('f');
        jMenItFabs.setText("Fabricantes");
        jMenItFabs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItFabsActionPerformed(evt);
            }
        });
        jMenItFabs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItFabsKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItFabs);

        jMenItCols.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clascol.png"))); // NOI18N
        jMenItCols.setMnemonic('c');
        jMenItCols.setText("Colores");
        jMenItCols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItColsActionPerformed(evt);
            }
        });
        jMenItCols.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItColsKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItCols);

        jMClasProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasext.png"))); // NOI18N
        jMClasProd.setMnemonic('c');
        jMClasProd.setText("Clasificación extra");
        jMClasProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMClasProdActionPerformed(evt);
            }
        });
        jMClasProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMClasProdKeyPressed(evt);
            }
        });
        jMMClas.add(jMClasProd);

        jMJeraProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasjeraprod.png"))); // NOI18N
        jMJeraProd.setText("Clasificaciones jerárquicas");
        jMJeraProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMJeraProdActionPerformed(evt);
            }
        });
        jMJeraProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMJeraProdKeyPressed(evt);
            }
        });
        jMMClas.add(jMJeraProd);

        jMenItPesos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/claspes.png"))); // NOI18N
        jMenItPesos.setMnemonic('p');
        jMenItPesos.setText("Pesos");
        jMenItPesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItPesosActionPerformed(evt);
            }
        });
        jMenItPesos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItPesosKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItPesos);

        jMenItMeds.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasmed.png"))); // NOI18N
        jMenItMeds.setMnemonic('e');
        jMenItMeds.setText("Medidas");
        jMenItMeds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItMedsActionPerformed(evt);
            }
        });
        jMenItMeds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItMedsKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItMeds);

        jMenItUnids.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasunid.png"))); // NOI18N
        jMenItUnids.setMnemonic('u');
        jMenItUnids.setText("Unidades");
        jMenItUnids.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItUnidsActionPerformed(evt);
            }
        });
        jMenItUnids.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItUnidsKeyPressed(evt);
            }
        });
        jMMClas.add(jMenItUnids);

        jMModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/models.png"))); // NOI18N
        jMModel.setMnemonic('s');
        jMModel.setText("Modelos");
        jMModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMModelActionPerformed(evt);
            }
        });
        jMModel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMModelKeyPressed(evt);
            }
        });
        jMMClas.add(jMModel);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tall.png"))); // NOI18N
        jMenuItem2.setMnemonic('t');
        jMenuItem2.setText("Tallas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuItem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMMClas.add(jMenuItem2);

        jMInven.add(jMMClas);

        jMenMaxsMins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/maxmin.png"))); // NOI18N
        jMenMaxsMins.setMnemonic('a');
        jMenMaxsMins.setText("Máximos y mínimos");
        jMenMaxsMins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenMaxsMinsKeyPressed(evt);
            }
        });

        jMenItProdsBajMin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prodabajmin.png"))); // NOI18N
        jMenItProdsBajMin.setMnemonic('p');
        jMenItProdsBajMin.setText("Productos debajo del mínimo");
        jMenItProdsBajMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItProdsBajMinActionPerformed(evt);
            }
        });
        jMenItProdsBajMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItProdsBajMinKeyPressed(evt);
            }
        });
        jMenMaxsMins.add(jMenItProdsBajMin);

        jMenItProdsArrMax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prodarrimax.png"))); // NOI18N
        jMenItProdsArrMax.setMnemonic('r');
        jMenItProdsArrMax.setText("Productos arriba del máximo");
        jMenItProdsArrMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItProdsArrMaxActionPerformed(evt);
            }
        });
        jMenItProdsArrMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItProdsArrMaxKeyPressed(evt);
            }
        });
        jMenMaxsMins.add(jMenItProdsArrMax);

        jMenItDefEstacs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/usrmax.png"))); // NOI18N
        jMenItDefEstacs.setMnemonic('d');
        jMenItDefEstacs.setText("Definir usuarios Max. Min.");
        jMenItDefEstacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItDefEstacsActionPerformed(evt);
            }
        });
        jMenItDefEstacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItDefEstacsKeyPressed(evt);
            }
        });
        jMenMaxsMins.add(jMenItDefEstacs);

        jMInven.add(jMenMaxsMins);

        jMenTraspas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/movalma.png"))); // NOI18N
        jMenTraspas.setMnemonic('r');
        jMenTraspas.setText("Movimientos almacenes");
        jMenTraspas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenTraspasKeyPressed(evt);
            }
        });

        jMenItTraspas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/traspas.png"))); // NOI18N
        jMenItTraspas.setMnemonic('t');
        jMenItTraspas.setText("Traspasos");
        jMenItTraspas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItTraspasActionPerformed(evt);
            }
        });
        jMenItTraspas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItTraspasKeyPressed(evt);
            }
        });
        jMenTraspas.add(jMenItTraspas);

        jMenItIngr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ingrsal.png"))); // NOI18N
        jMenItIngr.setMnemonic('i');
        jMenItIngr.setText("Entradas/salidas");
        jMenItIngr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItIngrActionPerformed(evt);
            }
        });
        jMenItIngr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItIngrKeyPressed(evt);
            }
        });
        jMenTraspas.add(jMenItIngr);

        jMInven.add(jMenTraspas);

        jMMUbic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ubics.png"))); // NOI18N
        jMMUbic.setMnemonic('i');
        jMMUbic.setText("Ubicaciones");
        jMMUbic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMUbicKeyPressed(evt);
            }
        });

        jMenItAlmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/almas.png"))); // NOI18N
        jMenItAlmas.setMnemonic('a');
        jMenItAlmas.setText("Almacenes");
        jMenItAlmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItAlmasActionPerformed(evt);
            }
        });
        jMenItAlmas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItAlmasKeyPressed(evt);
            }
        });
        jMMUbic.add(jMenItAlmas);

        jMenIt5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/anaqs.png"))); // NOI18N
        jMenIt5.setMnemonic('q');
        jMenIt5.setText("Anaqueles");
        jMenIt5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenIt5ActionPerformed(evt);
            }
        });
        jMenIt5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenIt5KeyPressed(evt);
            }
        });
        jMMUbic.add(jMenIt5);

        jMenItLugs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/lugs.png"))); // NOI18N
        jMenItLugs.setMnemonic('l');
        jMenItLugs.setText("Lugares");
        jMenItLugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItLugsActionPerformed(evt);
            }
        });
        jMenItLugs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItLugsKeyPressed(evt);
            }
        });
        jMMUbic.add(jMenItLugs);

        jMUbiAd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ubiad.png"))); // NOI18N
        jMUbiAd.setMnemonic('u');
        jMUbiAd.setText("Ubicaciones adicionales");
        jMUbiAd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMUbiAdActionPerformed(evt);
            }
        });
        jMUbiAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMUbiAdKeyPressed(evt);
            }
        });
        jMMUbic.add(jMUbiAd);

        jMInven.add(jMMUbic);

        jMMRepProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repgen.png"))); // NOI18N
        jMMRepProd.setText("Reportes");
        jMMRepProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMRepProdKeyPressed(evt);
            }
        });

        jMCosts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptcost.png"))); // NOI18N
        jMCosts.setMnemonic('t');
        jMCosts.setText("Costeos");
        jMCosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCostsActionPerformed(evt);
            }
        });
        jMCosts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCostsKeyPressed(evt);
            }
        });
        jMMRepProd.add(jMCosts);

        jMenProds.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repprods.png"))); // NOI18N
        jMenProds.setMnemonic('p');
        jMenProds.setText("Productos");
        jMenProds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenProdsActionPerformed(evt);
            }
        });
        jMenProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenProdsKeyPressed(evt);
            }
        });
        jMMRepProd.add(jMenProds);

        jMKard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repkar.png"))); // NOI18N
        jMKard.setMnemonic('k');
        jMKard.setText("Kardex");
        jMKard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMKardActionPerformed(evt);
            }
        });
        jMKard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMKardKeyPressed(evt);
            }
        });
        jMMRepProd.add(jMKard);

        jMInven.add(jMMRepProd);

        jMenInvents.add(jMInven);

        jMenVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/vtas2.png"))); // NOI18N
        jMenVtas.setMnemonic('v');
        jMenVtas.setText("Ventas");
        jMenVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenVtasKeyPressed(evt);
            }
        });

        jMMClien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        jMMClien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/manemp.png"))); // NOI18N
        jMMClien.setMnemonic('m');
        jMMClien.setText("Clientes");
        jMMClien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMMClienActionPerformed(evt);
            }
        });
        jMMClien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMClienKeyPressed(evt);
            }
        });
        jMenVtas.add(jMMClien);

        jMClasCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasifs.png"))); // NOI18N
        jMClasCli.setMnemonic('l');
        jMClasCli.setText("Clasificaciones clientes");
        jMClasCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMClasCliActionPerformed(evt);
            }
        });
        jMClasCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMClasCliKeyPressed(evt);
            }
        });
        jMenVtas.add(jMClasCli);

        jMClasJer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/clasjeracli.png"))); // NOI18N
        jMClasJer.setMnemonic('j');
        jMClasJer.setText("Clasificaciones jerárquicas");
        jMClasJer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMClasJerActionPerformed(evt);
            }
        });
        jMClasJer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMClasJerKeyPressed(evt);
            }
        });
        jMenVtas.add(jMClasJer);

        jMenItVtas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, 0));
        jMenItVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/vtas.png"))); // NOI18N
        jMenItVtas.setMnemonic('v');
        jMenItVtas.setText("Ventas");
        jMenItVtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItVtasActionPerformed(evt);
            }
        });
        jMenItVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItVtasKeyPressed(evt);
            }
        });
        jMenVtas.add(jMenItVtas);

        jMenCotsT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, 0));
        jMenCotsT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cots.png"))); // NOI18N
        jMenCotsT.setMnemonic('C');
        jMenCotsT.setText("Cotizaciones");
        jMenCotsT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenCotsTActionPerformed(evt);
            }
        });
        jMenCotsT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCotsTKeyPressed(evt);
            }
        });
        jMenVtas.add(jMenCotsT);

        jMenItTipsPags.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tipspags.png"))); // NOI18N
        jMenItTipsPags.setMnemonic('t');
        jMenItTipsPags.setText("Tipos de pagos");
        jMenItTipsPags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItTipsPagsActionPerformed(evt);
            }
        });
        jMenItTipsPags.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItTipsPagsKeyPressed(evt);
            }
        });
        jMenVtas.add(jMenItTipsPags);

        jMConcepPags.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tipspags.png"))); // NOI18N
        jMConcepPags.setMnemonic('n');
        jMConcepPags.setText("Conceptos de pagos");
        jMConcepPags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMConcepPagsActionPerformed(evt);
            }
        });
        jMConcepPags.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMConcepPagsKeyPressed(evt);
            }
        });
        jMenVtas.add(jMConcepPags);

        jMCXC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cxc.png"))); // NOI18N
        jMCXC.setMnemonic('c');
        jMCXC.setText("CXC");
        jMCXC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCXCActionPerformed(evt);
            }
        });
        jMCXC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCXCKeyPressed(evt);
            }
        });
        jMenVtas.add(jMCXC);

        jMenCatGral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/catgral.png"))); // NOI18N
        jMenCatGral.setMnemonic('a');
        jMenCatGral.setText("Catálogo general");
        jMenCatGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenCatGralActionPerformed(evt);
            }
        });
        jMenCatGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCatGralKeyPressed(evt);
            }
        });
        jMenVtas.add(jMenCatGral);

        jMenItTouch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, 0));
        jMenItTouch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pos.png"))); // NOI18N
        jMenItTouch.setMnemonic('t');
        jMenItTouch.setText("Punto de venta");
        jMenItTouch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItTouchActionPerformed(evt);
            }
        });
        jMenItTouch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItTouchKeyPressed(evt);
            }
        });
        jMenVtas.add(jMenItTouch);

        jMImpCliens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jMImpCliens.setMnemonic('i');
        jMImpCliens.setText("Importar catálogo de clientes desde excel");
        jMImpCliens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMImpCliensActionPerformed(evt);
            }
        });
        jMImpCliens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMImpCliensKeyPressed(evt);
            }
        });
        jMenVtas.add(jMImpCliens);

        jMExpoCliens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jMExpoCliens.setMnemonic('e');
        jMExpoCliens.setText("Exportar catálogo de clientes desde excel");
        jMExpoCliens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExpoCliensActionPerformed(evt);
            }
        });
        jMExpoCliens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMExpoCliensKeyPressed(evt);
            }
        });
        jMenVtas.add(jMExpoCliens);

        jMAsoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/asocprodcli.png"))); // NOI18N
        jMAsoc.setMnemonic('a');
        jMAsoc.setText("Reglas de negocio");
        jMAsoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMAsocActionPerformed(evt);
            }
        });
        jMAsoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMAsocKeyPressed(evt);
            }
        });
        jMenVtas.add(jMAsoc);

        jMMRepVta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repgen.png"))); // NOI18N
        jMMRepVta.setText("Reportes");
        jMMRepVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMRepVtaKeyPressed(evt);
            }
        });

        jMRepCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptcli.png"))); // NOI18N
        jMRepCli.setMnemonic('c');
        jMRepCli.setText("Clientes");
        jMRepCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepCliActionPerformed(evt);
            }
        });
        jMRepCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepCliKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMRepCli);

        jMenVtasR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repvta.png"))); // NOI18N
        jMenVtasR.setMnemonic('v');
        jMenVtasR.setText("Ventas");
        jMenVtasR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenVtasRActionPerformed(evt);
            }
        });
        jMenVtasR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenVtasRKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMenVtasR);

        jMenCXCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rptcxc.png"))); // NOI18N
        jMenCXCR.setMnemonic('x');
        jMenCXCR.setText("CXC");
        jMenCXCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenCXCRActionPerformed(evt);
            }
        });
        jMenCXCR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCXCRKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMenCXCR);

        jMRepCot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repcots.png"))); // NOI18N
        jMRepCot.setMnemonic('t');
        jMRepCot.setText("Cotizaciones");
        jMRepCot.setToolTipText("");
        jMRepCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepCotActionPerformed(evt);
            }
        });
        jMRepCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepCotKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMRepCot);

        jMRepVend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repvend.png"))); // NOI18N
        jMRepVend.setMnemonic('n');
        jMRepVend.setText("Vendedores");
        jMRepVend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepVendActionPerformed(evt);
            }
        });
        jMRepVend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepVendKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMRepVend);

        jMFluj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repfluj.png"))); // NOI18N
        jMFluj.setMnemonic('f');
        jMFluj.setText("Flujo");
        jMFluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMFlujActionPerformed(evt);
            }
        });
        jMFluj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMFlujKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMFluj);

        jMRepBackO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repback.png"))); // NOI18N
        jMRepBackO.setText("Backorder");
        jMRepBackO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepBackOActionPerformed(evt);
            }
        });
        jMRepBackO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepBackOKeyPressed(evt);
            }
        });
        jMMRepVta.add(jMRepBackO);

        jMenVtas.add(jMMRepVta);

        jMenInvents.add(jMenVtas);

        jMConta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/conta.png"))); // NOI18N
        jMConta.setMnemonic('c');
        jMConta.setText("Contabilidad");
        jMConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMContaKeyPressed(evt);
            }
        });

        jMActFij.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actfij.png"))); // NOI18N
        jMActFij.setMnemonic('a');
        jMActFij.setText("Activo fijo");
        jMActFij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMActFijActionPerformed(evt);
            }
        });
        jMActFij.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMActFijKeyPressed(evt);
            }
        });
        jMConta.add(jMActFij);

        jMCatActFij.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/catactfij.png"))); // NOI18N
        jMCatActFij.setMnemonic('c');
        jMCatActFij.setText("Catálogo activo fijo");
        jMCatActFij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCatActFijActionPerformed(evt);
            }
        });
        jMCatActFij.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCatActFijKeyPressed(evt);
            }
        });
        jMConta.add(jMCatActFij);

        jMTipAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/catactfij.png"))); // NOI18N
        jMTipAct.setMnemonic('t');
        jMTipAct.setText("Catálogo de tipos de activo fijo");
        jMTipAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMTipActActionPerformed(evt);
            }
        });
        jMTipAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMTipActKeyPressed(evt);
            }
        });
        jMConta.add(jMTipAct);

        jMRepConta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/repcots.png"))); // NOI18N
        jMRepConta.setMnemonic('r');
        jMRepConta.setText("Reportes");
        jMRepConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMRepContaActionPerformed(evt);
            }
        });
        jMRepConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMRepContaKeyPressed(evt);
            }
        });
        jMConta.add(jMRepConta);

        jMenInvents.add(jMConta);

        jMConcepNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/concepnot.png"))); // NOI18N
        jMConcepNot.setText("Conceptos notas de crédito");
        jMConcepNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMConcepNotActionPerformed(evt);
            }
        });
        jMConcepNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMConcepNotKeyPressed(evt);
            }
        });
        jMenInvents.add(jMConcepNot);

        jMFlujAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/flujact.png"))); // NOI18N
        jMFlujAct.setText("Flujo actividades");
        jMFlujAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMFlujActActionPerformed(evt);
            }
        });
        jMFlujAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMFlujActKeyPressed(evt);
            }
        });
        jMenInvents.add(jMFlujAct);

        jMCatGara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/catgara.png"))); // NOI18N
        jMCatGara.setText("Catálogo garantías");
        jMCatGara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCatGaraActionPerformed(evt);
            }
        });
        jMCatGara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMCatGaraKeyPressed(evt);
            }
        });
        jMenInvents.add(jMCatGara);

        jMZon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/zon.png"))); // NOI18N
        jMZon.setMnemonic('z');
        jMZon.setText("Zonas");
        jMZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMZonActionPerformed(evt);
            }
        });
        jMZon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMZonKeyPressed(evt);
            }
        });
        jMenInvents.add(jMZon);

        jMGir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/gir.png"))); // NOI18N
        jMGir.setMnemonic('g');
        jMGir.setText("Giros");
        jMGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMGirActionPerformed(evt);
            }
        });
        jMGir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMGirKeyPressed(evt);
            }
        });
        jMenInvents.add(jMGir);

        jMenItMons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mons.png"))); // NOI18N
        jMenItMons.setMnemonic('o');
        jMenItMons.setText("Monedas");
        jMenItMons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItMonsActionPerformed(evt);
            }
        });
        jMenItMons.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItMonsKeyPressed(evt);
            }
        });
        jMenInvents.add(jMenItMons);

        jMeItImps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impues.png"))); // NOI18N
        jMeItImps.setMnemonic('m');
        jMeItImps.setText("Impuestos");
        jMeItImps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeItImpsActionPerformed(evt);
            }
        });
        jMeItImps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeItImpsKeyPressed(evt);
            }
        });
        jMenInvents.add(jMeItImps);

        jMenBar1.add(jMenInvents);

        jMSist.setMnemonic('c');
        jMSist.setText("Configuraciones");
        jMSist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMSistKeyPressed(evt);
            }
        });

        jMenIt4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/corrs.png"))); // NOI18N
        jMenIt4.setMnemonic('o');
        jMenIt4.setText("Correos electrónicos");
        jMenIt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenIt4ActionPerformed(evt);
            }
        });
        jMenIt4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenIt4KeyPressed(evt);
            }
        });
        jMSist.add(jMenIt4);

        jMenItDatsGenEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/datsgralempre.png"))); // NOI18N
        jMenItDatsGenEmp.setMnemonic('t');
        jMenItDatsGenEmp.setText("Datos generales de la empresa");
        jMenItDatsGenEmp.setToolTipText("");
        jMenItDatsGenEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItDatsGenEmpActionPerformed(evt);
            }
        });
        jMenItDatsGenEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItDatsGenEmpKeyPressed(evt);
            }
        });
        jMSist.add(jMenItDatsGenEmp);

        jMenSer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/fol.png"))); // NOI18N
        jMenSer.setMnemonic('s');
        jMenSer.setText("Series y consecutivos");
        jMenSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenSerKeyPressed(evt);
            }
        });

        jMeniSerFac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/serfac.png"))); // NOI18N
        jMeniSerFac.setMnemonic('f');
        jMeniSerFac.setText("Facturas");
        jMeniSerFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniSerFacActionPerformed(evt);
            }
        });
        jMeniSerFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniSerFacKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniSerFac);

        jMeniSerCom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sercompr.png"))); // NOI18N
        jMeniSerCom.setMnemonic('c');
        jMeniSerCom.setText("Compras");
        jMeniSerCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniSerComActionPerformed(evt);
            }
        });
        jMeniSerCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniSerComKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniSerCom);

        jMeniSerCot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sercot.png"))); // NOI18N
        jMeniSerCot.setMnemonic('o');
        jMeniSerCot.setText("Cotizaciones");
        jMeniSerCot.setToolTipText("");
        jMeniSerCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniSerCotActionPerformed(evt);
            }
        });
        jMeniSerCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniSerCotKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniSerCot);

        jMeniSerEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/seremp.png"))); // NOI18N
        jMeniSerEmp.setMnemonic('e');
        jMeniSerEmp.setText("Clientes");
        jMeniSerEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniSerEmpActionPerformed(evt);
            }
        });
        jMeniSerEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniSerEmpKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniSerEmp);

        jMeniSerProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/serprov.png"))); // NOI18N
        jMeniSerProv.setMnemonic('p');
        jMeniSerProv.setText("Proveedores");
        jMeniSerProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniSerProvActionPerformed(evt);
            }
        });
        jMeniSerProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniSerProvKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniSerProv);

        jMeniTik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sertic.png"))); // NOI18N
        jMeniTik.setMnemonic('t');
        jMeniTik.setText("Tickets");
        jMeniTik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniTikActionPerformed(evt);
            }
        });
        jMeniTik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniTikKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniTik);

        jMeniRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/serrem.png"))); // NOI18N
        jMeniRem.setMnemonic('r');
        jMeniRem.setText("Remisiones");
        jMeniRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMeniRemActionPerformed(evt);
            }
        });
        jMeniRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMeniRemKeyPressed(evt);
            }
        });
        jMenSer.add(jMeniRem);

        jMSerO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/serord.png"))); // NOI18N
        jMSerO.setText("Ordenes de compra");
        jMSerO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMSerOActionPerformed(evt);
            }
        });
        jMSerO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMSerOKeyPressed(evt);
            }
        });
        jMenSer.add(jMSerO);

        NotVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sernotcred.png"))); // NOI18N
        NotVe.setText("Notas de crédito ventas");
        NotVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotVeActionPerformed(evt);
            }
        });
        jMenSer.add(NotVe);

        jMSerNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sernotcred.png"))); // NOI18N
        jMSerNot.setMnemonic('n');
        jMSerNot.setText("Notas de crédito compra");
        jMSerNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMSerNotActionPerformed(evt);
            }
        });
        jMSerNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMSerNotKeyPressed(evt);
            }
        });
        jMenSer.add(jMSerNot);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/CataPrevCom.png"))); // NOI18N
        jMenuItem3.setText("Previo de compra");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenSer.add(jMenuItem3);

        jMSist.add(jMenSer);

        jMenItImpres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impres.png"))); // NOI18N
        jMenItImpres.setMnemonic('m');
        jMenItImpres.setText("Impresoras");
        jMenItImpres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenItImpresActionPerformed(evt);
            }
        });
        jMenItImpres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenItImpresKeyPressed(evt);
            }
        });
        jMSist.add(jMenItImpres);

        jMenCambIco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cambico.png"))); // NOI18N
        jMenCambIco.setMnemonic('p');
        jMenCambIco.setText("Cambiar icono aplicación");
        jMenCambIco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenCambIcoActionPerformed(evt);
            }
        });
        jMenCambIco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenCambIcoKeyPressed(evt);
            }
        });
        jMSist.add(jMenCambIco);

        jMConfAd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/confgral.png"))); // NOI18N
        jMConfAd.setMnemonic('n');
        jMConfAd.setText("Configuraciones generales");
        jMConfAd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMConfAdActionPerformed(evt);
            }
        });
        jMConfAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMConfAdKeyPressed(evt);
            }
        });
        jMSist.add(jMConfAd);

        jMenBar1.add(jMSist);

        jMenu4.setText("Sincronizar");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenBar1.add(jMenu4);

        jMMAyu.setMnemonic('y');
        jMMAyu.setText("Ayuda");
        jMMAyu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMMAyuKeyPressed(evt);
            }
        });

        jMAcerc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/acerd.png"))); // NOI18N
        jMAcerc.setMnemonic('a');
        jMAcerc.setText("Acerca de...");
        jMAcerc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMAcercActionPerformed(evt);
            }
        });
        jMAcerc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMAcercKeyPressed(evt);
            }
        });
        jMMAyu.add(jMAcerc);

        jMenBar1.add(jMMAyu);

        setJMenuBar(jMenBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
                   
        
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
                        
        
    /*Cuando se presiona el menú de corrs electrónicos*/
    private void jMenIt4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenIt4ActionPerformed
        
        /*Muestra el gráfico de corrs electrónicos*/
        CorrElecs c = new CorrElecs();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMenIt4ActionPerformed

    
    /*Cuando se presiona una tecla en la barra de menús*/
    private void jMenBar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenBar1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenBar1KeyPressed

    
    /*Cuando se presiona una tecla en la barra de menús de configuraciones*/
    private void jMMSisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMSisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMSisKeyPressed

    
    /*Cuando se presiona una tecla en el menú corrs electrónicos*/
    private void jMenIt4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenIt4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenIt4KeyPressed

    
    
    private void jMenIt8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenIt8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenIt8ActionPerformed

    
    private void jMenIt8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenIt8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenIt8KeyPressed

    
    /*Cuando se presiona una tecla en el menú de reparar*/
    private void jMenRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenRepKeyPressed

    
    private void jMenIt9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenIt9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenIt9ActionPerformed

    
    private void jMenIt9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenIt9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenIt9KeyPressed

    
    /*Cuando se presiona el menú de cambiar clave de seguridad 1*/
    private void jMenItCambClavSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItCambClavSegActionPerformed
               
        /*Mostrar el gráfico de cambiar clave de seguridad 1*/
        ClavCamb c = new ClavCamb(null, true);
        c.getObj().setVisible(true);
        
    }//GEN-LAST:event_jMenItCambClavSegActionPerformed

    
    /*Método para que los clientes se abran solo una vez*/
    private void vAbrClients()
    {
        if(Star.gClients==null)
        {            
            Star.gClients = new Clients(btnsClientes);
            Star.gClients.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gClients.isVisible())            
                Star.gClients.toFront();
            else
                Star.gClients.setVisible(true);            
        }
    }
    
    
    /*Cuando se presiona el menú de manejo de emps*/
    private void jMMClienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMMClienActionPerformed
        
        /*Muestra el gráfico de manejo de clientes*/
        vAbrClients();
        
    }//GEN-LAST:event_jMMClienActionPerformed

    
    /*Cuando se presiona una tecla en el menú de manejo de emps*/
    private void jMMClienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMClienKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMClienKeyPressed

    
    /*Función para abrir una sola vez la forma de los proveedores*/
    private void vAbrProvs()
    {
        if(Star.gProvs==null)
        {            
            Star.gProvs = new Provs(btnsProvee);
            Star.gProvs.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gProvs.isVisible())            
                Star.gProvs.toFront();
            else
                Star.gProvs.setVisible(true);            
        }
    }
    
    /*Cuando se presiona el menú de manejo de proveedores*/
    private void jMenItManProvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItManProvsActionPerformed
        
        /*Mustra el gráfico de manejo de proveedores*/        
        vAbrProvs();
        
    }//GEN-LAST:event_jMenItManProvsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de manejo de proveedores*/
    private void jMenItManProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItManProvsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItManProvsKeyPressed


    /*Cuando se presiona el menú de permisos de usuarios*/
    private void jMenItPermEstacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItPermEstacsActionPerformed

        /*Muestra el módulo de permisos de usuarios*/
        PermsEstacs p = new PermsEstacs();
        p.setVisible(true);
        
    }//GEN-LAST:event_jMenItPermEstacsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de claves*/
    private void jMen2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMen2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMen2KeyPressed

    
    
    /*Cuando se presiona una tecla en el menú de usuarios/usuarios*/
    private void jMenItEstacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItEstacsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItEstacsKeyPressed

    
    /*Cuando se presiona el menú de usuarios*/
    private void jMenItEstacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItEstacsActionPerformed
        
        /*Muestra el fomrulario de usuarios*/
        Usrs e = new Usrs();
        e.setVisible(true);
        
    }//GEN-LAST:event_jMenItEstacsActionPerformed

    
    /*Cuando se presiona una tecla en el menú principal login*/
    private void jMenLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenLoginKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenLoginKeyPressed

    
    /*Cuando se presiona el menú item de logearse*/
    private void jMenItLogearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItLogearActionPerformed
        
        /*Muestra el gráfico de loggearse*/
        LoginOtra l = new LoginOtra(this, null);
        l.getObj(this, null).setVisible(true);
        
    }//GEN-LAST:event_jMenItLogearActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de logearse*/
    private void jMenItLogearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItLogearKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItLogearKeyPressed

    
    /*Cuando se presiona una tecla en el menú item de cambiar clave de segurida*/
    private void jMenItCambClavSegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItCambClavSegKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItCambClavSegKeyPressed

    
    /*Cuando se presiona una tecla en el menu de base de datos*/
    private void jMenBDsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenBDsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenBDsKeyPressed

    
    /*Cuando se presiona una tecla en el menú de salir*/
    private void jMenItSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItSalKeyPressed


    /*Función para cuando se tiene que salir de la aplicación*/
    private void vExiAp()
    {
        /*Pregunta al usr si esta seguro de salir de la aplicación*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Sal de la aplicación
        Star.vExitAp();
    }
    
    
    /*Cuando se presiona el menú de salir*/
    private void jMenItSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItSalActionPerformed
        
        /*Función para cuando se tiene que salir de la aplicación*/
        vExiAp();

    }//GEN-LAST:event_jMenItSalActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de modificar conexión a base de datos*/
    private void jMenItModBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItModBDKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItModBDKeyPressed

    
    /*Cuando se presiona el menú item de modificar conexión a base de datos*/
    private void jMenItModBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItModBDActionPerformed

        /*Llamar al formulario de configuración*/
        BDs b = new BDs();
        b.setVisible(true);

    }//GEN-LAST:event_jMenItModBDActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de respaldos*/
    private void jMenItRespsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItRespsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItRespsKeyPressed

    
    /*Cuando se presiona el menú item de respaldos*/
    private void jMenItRespsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItRespsActionPerformed
        
        /*Abre el formulario de respaldos*/
        Resps r = new Resps();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMenItRespsActionPerformed

            
    /*Cuando se presiona una tecla en el menú de compras*/
    private void jMenCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenCompsKeyPressed
    
    
    /*Cuando se presiona una tecla en el menú de JPS1*/
    private void jMenItCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItCompsKeyPressed


    /*Método para que se abra solamente una vez la forma de compras*/
    private void vAbrComprs()
    {
        /*Abre la forma de las compras una sola vez*/
        if(Star.gComprs==null)
        {            
            Star.gComprs = new Compr(btnsCompras);
            Star.gComprs.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gComprs.isVisible())            
                Star.gComprs.toFront();
            else
                Star.gComprs.setVisible(true);            
        }                    
    }
    
    
    /*Cuando se presiona el menú de compras*/
    private void jMenItCompsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItCompsActionPerformed
        
        /*Muestra el formulario de compras*/
        vAbrComprs();
        
    }//GEN-LAST:event_jMenItCompsActionPerformed

    
    
    /*Cuando se presiona una tecla en el menú de cotizaciones proyectos*/    
    private void jMenCotsTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCotsTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenCotsTKeyPressed


    /*Método para que se abra una sola vez la forma de cotizaciones*/
    
    private void vAbrCots()
    {
        /*Abre la forma de las cotizaciones una sola vez*/
        if(Star.gCots==null)
        {            
            Star.gCots = new Cots(btnsCotiza);
            Star.gCots.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gCots.isVisible())            
                Star.gCots.toFront();
            else
                Star.gCots.setVisible(true);            
        }                    
    }
    
    /*Método para que se abra una sola vez la forma de cotizaciones*/
    private void vPrevComps()
    {          
        /*Abre la forma de las cotizaciones una sola vez*/
        if(Star.gPrevComps==null)
        {            
            Star.gPrevComps = new PrevComp(btnsPrevio);
            Star.gPrevComps.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gPrevComps.isVisible())            
                Star.gPrevComps.toFront();
            else
                Star.gPrevComps.setVisible(true);            
        }                    
    }
    
    
    /*Cuando se presiona el menú de cotizaciones proyectos*/
    private void jMenCotsTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenCotsTActionPerformed

        /*Muestra el formulario para ver todos las cotizaciones de los proyectos*/
        vAbrCots();
        
    }//GEN-LAST:event_jMenCotsTActionPerformed

            
    /*Cuando se gana el foco del teclado en el menú de ventas*/
    private void jMenVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenVtasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenVtasKeyPressed

    
    /*Cuando se presiona una tecla en el menú de ventas*/
    private void jMenItVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItVtasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItVtasKeyPressed

    
    /*Cuando se presiona el menú de ventas*/
    private void jMenItVtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItVtasActionPerformed
        
        /*Muestra el formulario de ventas*/
        vAbrVtas();
        
    }//GEN-LAST:event_jMenItVtasActionPerformed
    
    
    /*Cuando se presiona una tecla en el menú de touch*/
    private void jMenItTouchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItTouchKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItTouchKeyPressed

                
    /*Función escalable para abrir el punto de venta*/
    private void vAbrPto()
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

        /*Obtiene el almacén del punto de venta*/        
        String sAlma    = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'almapto'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el almacén*/
            if(rs.next())
                sAlma   = rs.getString("extr");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                                        
        }         
        
        /*Checa si el código del almacén ya existe en la base de datos*/        
        int iRes    = Star.iExistAlma(con, sAlma.trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si no existe entonces coloca la bandera en false
        boolean bSi = true;
        if(iRes==0)
            bSi = false;
                        
        /*Si el almacén no exsite entonces*/
        if(!bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)                  
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El almacén para el punto de venta no existe y no se puede continuar.", "Almacén", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)                  
            return;
        
        if(Star.gPtoVtaTou==null)
        {            
            Star.gPtoVtaTou = new PtoVtaTou(this);
            Star.gPtoVtaTou.setVisible(true);
        }
        else
        {      
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gPtoVtaTou.isVisible())            
                Star.gPtoVtaTou.toFront();
            else
                Star.gPtoVtaTou.setVisible(true);            
        }
        /*Llama al formulario para punto de venta touch*/
//        PtoVtaTou p = new PtoVtaTou(this);        
//        p.setVisible(true);        
        
    }/*Fin de private void vAbrPto()*/
    
    
    /*Cuando se presiona el menú de punto de venta touch*/
    private void jMenItTouchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItTouchActionPerformed
        
        /*Abre el punto de venta*/
        vAbrPto();
        
    }//GEN-LAST:event_jMenItTouchActionPerformed

    
    /*Cuando se presiona una tecla en el menú de inventarios*/
    private void jMenInventsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenInventsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenInventsKeyPressed

    
    /*Cuando se presiona una tecla en el menú de conceptos*/
    private void jMenItConcepsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItConcepsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItConcepsKeyPressed

    
    /*Cuando se presiona el menú de conceptos*/
    private void jMenItConcepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItConcepsActionPerformed

        /*Muestra el formulario para conceptos*/
        cats.PlanGralCat l = new cats.PlanGralCat("conceps","ingres","concep","concepto","concep","concep");
        l.setVisible(true);

    }//GEN-LAST:event_jMenItConcepsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de impuestos*/
    private void jMeItImpsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeItImpsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMeItImpsKeyPressed

    
    /*Cuando se presiona el menú de impuestos*/
    private void jMeItImpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeItImpsActionPerformed

        /*Muestra el formulario de impuestos*/
        Imps i = new Imps();
        i.setVisible(true);

    }//GEN-LAST:event_jMeItImpsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de lugares*/
    private void jMenItLugsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItLugsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItLugsKeyPressed

    
    /*Cuando se presiona el menú de lugares*/
    private void jMenItLugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItLugsActionPerformed

        /*Muestra el formulario de lugares*/
        cats.PlanGralCat l = new cats.PlanGralCat("lugs","prods","lug","lugar","lugs","cod");
        l.setVisible(true);

    }//GEN-LAST:event_jMenItLugsActionPerformed

    
    /*Cuando se presiona uan tecla en el menú de anaqueles*/
    private void jMenIt5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenIt5KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenIt5KeyPressed

    
    /*Cuando se presiona el menú de anaqueles*/
    private void jMenIt5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenIt5ActionPerformed

        /*Muestra el formulario de los anaqueles*/
        cats.PlanGralCat l = new cats.PlanGralCat("Anaqs","prods","anaq","anaquel","anaq","cod");
        l.setVisible(true);

    }//GEN-LAST:event_jMenIt5ActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de medidas*/
    private void jMenItMedsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItMedsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItMedsKeyPressed

    
    /*Cuando se presiona el menú de medidas*/
    private void jMenItMedsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItMedsActionPerformed

        /*Muestra el formulario de medidas*/
        cats.PlanGralCat f = new cats.PlanGralCat("meds","Prods","codmed","medida","med","cod");
        f.setVisible(true);

    }//GEN-LAST:event_jMenItMedsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de monedas*/
    private void jMenItMonsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItMonsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItMonsKeyPressed

    
    /*Cuando se presiona el menú de monedas*/
    private void jMenItMonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItMonsActionPerformed

        /*Muestra el formulario de monedas*/
        Mons m = new Mons();
        m.setVisible(true);

    }//GEN-LAST:event_jMenItMonsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de almacenes*/
    private void jMenItAlmasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItAlmasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItAlmasKeyPressed

    
    /*Cuando se presiona el menú de almacenes*/
    private void jMenItAlmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItAlmasActionPerformed

        /*Muestra el formulario de almacenes*/
        Almas a = new Almas();
        a.setVisible(true);

    }//GEN-LAST:event_jMenItAlmasActionPerformed

    
    /*Cuando se presiona un botón en el menú de unidades*/
    private void jMenItUnidsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItUnidsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItUnidsKeyPressed

    
    /*Cuando se presiona el menú de unidades*/
    private void jMenItUnidsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItUnidsActionPerformed

        /*Muestra el formulario de unidades*/
        cats.PlanGralCat f = new cats.PlanGralCat("Unids","Prods","Unid","unidad","Unid","cod");
        f.setVisible(true);

    }//GEN-LAST:event_jMenItUnidsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de pesos*/
    private void jMenItPesosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItPesosKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItPesosKeyPressed

    
    /*Cuando se presiona el menú de pesos*/
    private void jMenItPesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItPesosActionPerformed

        /*Muestra el formulario de pesos*/
        cats.PlanGralCat p = new cats.PlanGralCat("Pes","Prods","Pes","peso","pes","cod");
        p.setVisible(true);

    }//GEN-LAST:event_jMenItPesosActionPerformed

    
    /*Cuando se presaiona una tecla en el menú  de colores*/
    private void jMenItColsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItColsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItColsKeyPressed

    
    /*Cuando se presiona el menú de colores*/
    private void jMenItColsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItColsActionPerformed

        /*Muestra la forma para los colores*/
        cats.PlanGralCat c = new cats.PlanGralCat("Colos","Prods","Colo","color","Colo","cod");
        c.setVisible(true);

    }//GEN-LAST:event_jMenItColsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de fabricantes*/
    private void jMenItFabsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItFabsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItFabsKeyPressed

    
    /*Cuando se presiona el menú de fanricantes*/
    private void jMenItFabsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItFabsActionPerformed

        /*Muestra el formulario de fabricantes*/
        cats.PlanGralCat f = new cats.PlanGralCat("Fabs","Prods","Fab","fabricante","FabS","cod");
        f.setVisible(true);

    }//GEN-LAST:event_jMenItFabsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de marcas*/
    private void jMenItMarcsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItMarcsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenItMarcsKeyPressed

    
    /*Cuando se presiona el menú de marcas*/
    private void jMenItMarcsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItMarcsActionPerformed

        /*Muestra el formulario de marcas*/
        cats.PlanGralCat m = new cats.PlanGralCat("Marcs","Prods","Marc","marca","Marc","cod");
        m.setVisible(true);

    }//GEN-LAST:event_jMenItMarcsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de líneas*/
    private void jMenIt1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenIt1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMenIt1KeyPressed

    
    /*Cuando se presiona el menú de líneas*/
    private void jMenIt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenIt1ActionPerformed

        /*Muestra la froma de las líneas*/
        cats.PlanGralCat l = new cats.PlanGralCat("lins","Prods","lin","linea","lins","cod");
        l.setVisible(true);

    }//GEN-LAST:event_jMenIt1ActionPerformed

    
    /*Cuando se presiona una tecla en el menú de productos*/
    private void jMVProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMVProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jMVProdKeyPressed

    
    /*Crea el método para abrir solo una vez la forma de productos*/    
    private void vAbrProds()
    {
        if(Prods.bMostVe)
        {
            /*Si es nulo entonces crea la referencia y regresa la referencia*/
            if(Star.gProds==null)
            {            
                Star.gProds = new Prods(btnsProductos);
                Star.gProds.setVisible(true);
            }
            else
            {            
                /*Si ya esta visible entonces traela al frente*/
                if(Star.gProds.isVisible())            
                    Star.gProds.toFront();
                else
                    Star.gProds.setVisible(true);            
            }
        }            
    }
    
    
    /*Cuando se presiona el menú de productos*/
    private void jMVProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMVProdActionPerformed

        /*Si se tiene que mostrar la ventana entonces*/
        vAbrProds();
        
    }//GEN-LAST:event_jMVProdActionPerformed

    
    /*Cuando se presiona una tecla en el menú de máximos y mínimos*/
    private void jMenMaxsMinsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenMaxsMinsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenMaxsMinsKeyPressed

    
    /*Cuando se presiona una tecla en el menú de productos por debajo del mínimo*/
    private void jMenItProdsBajMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItProdsBajMinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItProdsBajMinKeyPressed

    
    /*Cuando se presiona el menú de productos por debajo del mínimo*/
    private void jMenItProdsBajMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItProdsBajMinActionPerformed
        
        /*Muestra el formulario de los productos por debajo del mínimo*/
        BajsMin b  = new BajsMin();
        b.setVisible(true);
        
    }//GEN-LAST:event_jMenItProdsBajMinActionPerformed

    
    /*Cuando se presiona una tecla en el menú de definir usuarios*/
    private void jMenItDefEstacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItDefEstacsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItDefEstacsKeyPressed

    
    /*Cuando se presiona el menú de defirnir usuarios*/
    private void jMenItDefEstacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItDefEstacsActionPerformed
        
        /*Muestra el formulario para máximos y mínimos por usuario*/
        MaxsMins m = new MaxsMins();
        m.setVisible(true);
        
    }//GEN-LAST:event_jMenItDefEstacsActionPerformed

    
    
    /*Cuandose presiona una tecla en el menú de kits*/
    private void jMenItKitsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItKitsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItKitsKeyPressed

    
    /*Cuando se presiona el menú de kits*/
    private void jMenItKitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItKitsActionPerformed
        
        /*Muestra el formulario que contiene todos los kits*/
        Kits k = new Kits();
        k.setVisible(true);
        
    }//GEN-LAST:event_jMenItKitsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de productos arriba del máximo*/
    private void jMenItProdsArrMaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItProdsArrMaxKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItProdsArrMaxKeyPressed

    
    /*Cuando se presiona el menú de productos arriba del máximo*/
    private void jMenItProdsArrMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItProdsArrMaxActionPerformed
        
        /*Muestra el formulario para productos arriba del máximo*/
        AltsMaxs a = new AltsMaxs();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMenItProdsArrMaxActionPerformed

    
    /*Cuando se presiona el menú item de traspasos*/
    private void jMenItTraspasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItTraspasActionPerformed
        
        /*Muestra el formulario para los traspasos*/
        Traspas t = new Traspas();
        t.setVisible(true);
        
    }//GEN-LAST:event_jMenItTraspasActionPerformed

    
    /*Cuando se presiona una tecla en el menú de traspasos*/
    private void jMenTraspasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenTraspasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenTraspasKeyPressed

    
    /*Cuando se presiona una tecla en el menú item de traspasos*/
    private void jMenItTraspasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItTraspasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItTraspasKeyPressed

    
    /*Cuando se presiona el menú item de ingresar*/
    private void jMenItIngrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItIngrActionPerformed
        
        /*Muestra el formulario para ingresos al inventario*/
        IngrInvent i = new IngrInvent();
        i.setVisible(true);
        
    }//GEN-LAST:event_jMenItIngrActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de ingresar al inventario*/
    private void jMenItIngrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItIngrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItIngrKeyPressed
    
    
    /*Cuando se presiona una tecla en el menú de datos generales de la empresa*/
    private void jMenItDatsGenEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItDatsGenEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItDatsGenEmpKeyPressed

    
    /*Cuando se presiona el menú de datos generales de la empresa*/
    private void jMenItDatsGenEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItDatsGenEmpActionPerformed
        
        /*Muestra el formulario para específicar los datos generales de la empresa*/
        DatsGenEmp d = new DatsGenEmp(this);
        d.setVisible(true);
        
    }//GEN-LAST:event_jMenItDatsGenEmpActionPerformed

    
    /*Cuando se presiona una tecla tecla en el menú de logs*/
    private void jMenLogsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenLogsKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenLogsKeyPressed

    
    /*Cuando se presiona una tecla en el menú item de log de corrs*/
    private void jMenItLogCorrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItLogCorrKeyPressed
               
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItLogCorrKeyPressed

    
    /*Cuando se presiona el menú item de log de corrs*/
    private void jMenItLogCorrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItLogCorrActionPerformed
        
        /*Muestra el formulario de log de corrs*/
        LogCorrs l = new LogCorrs();
        l.setVisible(true);
        
    }//GEN-LAST:event_jMenItLogCorrActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de tipos de pagos*/
    private void jMenItTipsPagsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItTipsPagsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItTipsPagsKeyPressed

    
    /*Cuando se presiona el menú de tipos de pagos*/
    private void jMenItTipsPagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItTipsPagsActionPerformed
        
        /*Muestra el formulario para los tipos de pagos*/
        TipsPags p = new TipsPags();
        p.setVisible(true);
        
    }//GEN-LAST:event_jMenItTipsPagsActionPerformed
        
    
    /*Cuando se presiona una tecla en el menú item de impresoras*/
    private void jMenItImpresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItImpresKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);     
        
    }//GEN-LAST:event_jMenItImpresKeyPressed

    
    /*Cuando se presiona el menú item de impresoras*/
    private void jMenItImpresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItImpresActionPerformed
        
        /*Muestra la forma para ver las impresoras por estacions*/
        Impres i = new Impres();
        i.setVisible(true);
        
    }//GEN-LAST:event_jMenItImpresActionPerformed

    
    /*Cuando se presiona una tecla en el menú de resetear*/
    private void jMReseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMReseKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jMReseKeyPressed

    
    /*Cuando se presiona el menú de resetear*/
    private void jMReseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMReseActionPerformed
        
        /*Muestra la forma para resetear*/
        Reset re = new Reset();
        re.setVisible(true);
        
    }//GEN-LAST:event_jMReseActionPerformed

    
    /*Cuando se presiona una tecla en el menú de cambiar icono de la aplicación*/
    private void jMenCambIcoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCambIcoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);    
        
    }//GEN-LAST:event_jMenCambIcoKeyPressed

    
    /*Cuando se presiona el menú de cambiar el icono de la aplicación*/
    private void jMenCambIcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenCambIcoActionPerformed
        
        /*Preguntar al usr si esta seguro de que están bien los datos*/
        int iResp;
        Object[] op = {"Default","Personalizado", "Cancelar"};
        iResp = JOptionPane.showOptionDialog(this, "¿A que icono de aplicación quieres cambiar?", "Icono Aplicación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        
        /*Default*/
        if(iResp==0)
        {                   
            /*Preguntar al usr si esta seguro de querer reestablecer el icono por default*/
            Object[] op2 = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres utilizar el icono predeterminado?", "Icono Aplicación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op2, op2[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)                            
                return;                                   
            
            /*Borra el logo de la carpeta*/
            new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").delete();
            
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Icono cambiado a predeterminado con éxito, tienes que reiniciar el sistema para ver los cambios.", "Cambiar Icono Aplicación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
        }     
        /*Else if, personalizado*/
        else if(iResp==1)
        {
            /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen del icono*/
            final JFileChooser fc   = new JFileChooser();
            FileFilter filter1 = new ExtensionFileFilter("JPG ,JPEG ,BMP ,TIF ,TIFF Y PNG", new String[] { "JPG", "JPEG","BMP","TIF","TIFF","PNG","jpg","jpeg","bmp","tif","tiff","png" });
            fc.setFileFilter(filter1);
            fc.setDialogTitle               ("Buscar Imágen Icono"); 
            fc.setAcceptAllFileFilterUsed   (false);

            /*Declara variables*/
            String sRut, sCarp;

            /*Si el usr presiono aceptar entonces*/
            if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) 
            {
                /*Lee la ruta seleccionada*/
                sRut                = fc.getCurrentDirectory().getAbsolutePath();                        

                /*Concatena la carpeta final seleccionada*/
                sRut                += "\\" + fc.getSelectedFile().getName();  

                /*Completa la ruta a donde se copiara*/
                sCarp                =  new java.io.File("").getAbsolutePath() + "\\Logo.jpg";  
                
                System.out.println(sRut);
                /*Si el archivo de origén no existe entonces*/
                if( !new File(sRut).exists())            
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El archivo de origén\"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Regresa por que no puede continuar*/
                    return;

                }

                /*Preguntar al usr si esta seguro de querer cambiar el icono de la aplicación*/
                Object[] op1 = {"Si","No"};
                int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres cambiar el icono de la aplicación?", "Cambiar Icono Aplicación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op1, op1[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

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

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Icono guardado con éxito en: " + sCarp + ", tienes que reiniciar el sistema para ver los cambios.", "Cambiar Icono Aplicación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) */        
            
        }/*Fin de else*/                    
        
    }//GEN-LAST:event_jMenCambIcoActionPerformed

            
    /*Cuando se presiona una tecla en el menú de ver log sistema*/
    private void jMVLogSysKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMVLogSysKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMVLogSysKeyPressed

    
    /*Cuando se presiona el menú de log sistema*/
    private void jMVLogSysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMVLogSysActionPerformed
        
        /*Abre el archivo log del sistema*/
        if(new File("log.txt").exists())
        {
            /*Si el sistema soporta esta característica entonces*/
            if(Desktop.isDesktopSupported()) 
            {          
                try
                {
                    /*Abre el archivo*/
                    Desktop.getDesktop().edit(new File("log.txt"));
                }
                catch(IOException expnIO)                        
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                    
                }                
            } 
            else 
            {
                //Mensajea                
                JOptionPane.showMessageDialog(null, this.getClass().getName() + "No soportado en: if(Desktop.isDesktopSupported())", "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                
            }
        }
        
    }//GEN-LAST:event_jMVLogSysActionPerformed

    
    /*Cuando se presiona una tecla en el menú de configuraciones adicionales de inventarios*/
    private void jMConfAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMConfAdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMConfAdKeyPressed

    
    /*Cuando se presiona el menú de configuraciones generales*/
    private void jMConfAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMConfAdActionPerformed
        
        /*Muestra la forma para configuraciones generales*/
        ConfGral i = new ConfGral();
        i.setVisible(true);
        
    }//GEN-LAST:event_jMConfAdActionPerformed

    
    /*Cuando se presiona el menú de cxc*/
    private void jMCXCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCXCActionPerformed
        
        /*Muestra la forma para ver las cuentas por pagar*/
        Cxc c = new Cxc();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMCXCActionPerformed

    
    /*Cuando se presiona una tecla en el menú de cxc*/
    private void jMCXCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCXCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCXCKeyPressed

        
    /*Cuando se presiona el menú de cuentas por pagar*/
    private void jMCXPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCXPActionPerformed
        
        /*Muestra la forma de cuentas por pagar*/
        Cxp c = new Cxp();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMCXPActionPerformed

    
    /*Cuando se presiona una tecla en el menú de cuentas por pagar*/
    private void jMCXPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCXPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCXPKeyPressed

    
    /*Cuando se presiona una tecla en el menú de contrarecibos*/
    private void jMContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMContraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMContraKeyPressed

    
    /*Cuando se presiona el menú de contrarecibo*/
    private void jMContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMContraActionPerformed
        
        /*Muestra la forma para ver los contrarecibos*/
        Contras c = new Contras();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMContraActionPerformed

    
    /*Cuando se presiona una tecla en el menù de abrir archivo de configuración*/
    private void jMenConfigFilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenConfigFilKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenConfigFilKeyPressed

    
    /*Cuando se presiona el menù de abrir archivo de configuración*/
    private void jMenConfigFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenConfigFilActionPerformed
        
        /*Abre el archivo de configuración*/
        BDCon b = new BDCon(false);
        b.setVisible(true);
        
    }//GEN-LAST:event_jMenConfigFilActionPerformed

    
    /*Cuando se presiona una tecla en el menú de series*/
    private void jMenSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenSerKeyPressed

    
    /*Cuando se presioan el menú item de series de emps*/
    private void jMeniSerEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniSerEmpActionPerformed
        
        /*Muestra la forma para las series de las emps*/
        SerEmp c = new SerEmp();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniSerEmpActionPerformed

    
    /*Cuando se presiona una tecla en el menú de series facturas*/
    private void jMeniSerFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniSerFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniSerFacKeyPressed

    
    /*Cuando se presiona una tecla en el menú de series de compras*/
    private void jMeniSerComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniSerComKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniSerComKeyPressed

    
    /*Cuando se presiona una tecla en el menú de series de cotizaciones*/
    private void jMeniSerCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniSerCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniSerCotKeyPressed

    
    /*Cuando se presiona una tecla en el menú de series de emps*/
    private void jMeniSerEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniSerEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniSerEmpKeyPressed

    
    /*Cuando se presiona el menú item de series de proveedores*/
    private void jMeniSerProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniSerProvActionPerformed
        
        /*Muestra la forma para las series de los proveedores*/
        SerProv c = new SerProv();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniSerProvActionPerformed

    
    /*Cuando se presiona una tecla en el menú de series de proveedores*/
    private void jMeniSerProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniSerProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniSerProvKeyPressed

    
    /*Cuando se presiona el menú de series de facturas*/
    private void jMeniSerFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniSerFacActionPerformed
        
        /*Muestra el formulario para las series de las facturas*/
        SerFac f = new SerFac();
        f.setVisible(true);
        
    }//GEN-LAST:event_jMeniSerFacActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de series de tickets*/
    private void jMeniTikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniTikKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniTikKeyPressed

    
    /*Cuando se presiona una tecla en el menú item de remisiones*/
    private void jMeniRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMeniRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMeniRemKeyPressed

    
    /*Cuando se presioan el menú item de series de compras*/
    private void jMeniSerComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniSerComActionPerformed
        
        /*Muestra la forma para las series de las compras*/
        SerComp c = new SerComp();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniSerComActionPerformed

    
    /*Cuando se presiona el menú item de series de cotizaciones*/
    private void jMeniSerCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniSerCotActionPerformed
        
        /*Muestra la forma para las series de las cotizaciones*/
        SerCot c = new SerCot();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniSerCotActionPerformed

    
    /*Cuando se presiona el menú item de series de tickets*/
    private void jMeniTikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniTikActionPerformed
        
        /*Muestra la forma para las series de los tickets*/
        SerTik c = new SerTik();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniTikActionPerformed

    
    /*Cuando se presiona el menú item de series de remisiones*/
    private void jMeniRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMeniRemActionPerformed
        
        /*Muestra la forma para las series de las remisiones*/
        SerRem c = new SerRem();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMeniRemActionPerformed

    
    /*Cuando se presiona una tecla en el menú de catálogo general*/
    private void jMenCatGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCatGralKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenCatGralKeyPressed

    
    /*Cuando se presiona el menú item de catálogo general*/
    private void jMenCatGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenCatGralActionPerformed

        /*Muestra la forma del catálogo general*/
        cats.PlanGralCat l = new cats.PlanGralCat("grals","prods","anaq","concepto general","CatGral","cod");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMenCatGralActionPerformed

    
    /*Cuando se presiona una tecla en el menú item de deslogearse*/
    private void jMenItDeslogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItDeslogKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItDeslogKeyPressed

    
    /*Cuando se presiona el menù item de deslogearse*/
    private void jMenItDeslogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItDeslogActionPerformed
        
        /*Llama a la forma que deslogea*/
        DesLogin l = new DesLogin(jFram);
        l.setVisible(true);
        
    }//GEN-LAST:event_jMenItDeslogActionPerformed

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se arrastra el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona una tecla en el menù item de cambiar de empresa*/
    private void jMenItCamEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenItCamEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenItCamEmpKeyPressed

    
    /*Cuando se presiona el menù de camabiar de emps*/
    private void jMenItCamEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenItCamEmpActionPerformed
                
        /*Muestra la forma para logearse con otra empresa*/
        Login l = new Login(true);
        l.setVisible(true);
        
    }//GEN-LAST:event_jMenItCamEmpActionPerformed

    
    /*Cuando se presiona una tecla en el menù de reporte de CXC*/
    private void jMenCXCRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCXCRKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenCXCRKeyPressed

    
    /*Cuando se presiona el menù de CXC*/
    private void jMenCXCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenCXCRActionPerformed
        
        /*Muestra la forma para reportear CXC*/
        RepCXC r = new RepCXC();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMenCXCRActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reportes de CXP*/
    private void jMenCXPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenCXPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenCXPKeyPressed

    
    /*Cuando se presiona el menú de CXP reportes*/
    private void jMenCXPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenCXPActionPerformed
        
        /*Muestra la forma para ver reportes de CXP*/
        RepCXP r = new RepCXP();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMenCXPActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reportes*/
    private void jMenVtasRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenVtasRKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenVtasRKeyPressed

    
    /*Cuando se presiona el menú de ventas*/
    private void jMenVtasRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenVtasRActionPerformed
        
        /*Muestra la forma para reportear las ventas*/
        RepVtas t = new RepVtas();
        t.setVisible(true);
        
    }//GEN-LAST:event_jMenVtasRActionPerformed

            
    /*Cuando se presiona una tecla en el menú de productos*/
    private void jMenProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenProdsKeyPressed

    
    /*Cuando se presiona el menú de productos*/
    private void jMenProdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenProdsActionPerformed
        
        /*Muestra el reporteador de productos*/
        RepProds r = new RepProds();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMenProdsActionPerformed

    
    /*Cuando se presiona una tecla en el menú del log del sistema*/
    private void jMLogSisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogSisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogSisKeyPressed

    
    /*Cuando se presiona una tecla en el menú de borrar archivo log*/
    private void jMDelLogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMDelLogKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMDelLogKeyPressed

    
    /*Cuando se presiona el menú de borrar el log del sistema*/
    private void jMDelLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMDelLogActionPerformed
        
        /*Preguntar al usr si esta seguro de querer borrar el archivo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres Reiniciar el archivo del log del sistema?", "Log del Sistema", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
        
        /*Si el archivo de log existe entonces*/
        if(new File("log.txt").exists())
        {
            /*Borralo*/
            new File("log.txt").delete();
            
            /*Crealo de nuevo*/
            try
            {
                new File("log.txt").createNewFile();
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
                return;                                                                                   
            }            
        }
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Exito al Reiniciar el archivo de log del sistema.", "Archivo Log del Sistema", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jMDelLogActionPerformed

    
    /*Cuando se presinoa una tecla en el menú de productos*/
    private void jMInvenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMInvenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMInvenKeyPressed

    
    /*Cuando se presiona una tecla en el menú de clasificación de productos*/
    private void jMClasProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMClasProdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMClasProdKeyPressed

    
    /*Cuando se presiona el menú de clasificación de producto*/
    private void jMClasProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMClasProdActionPerformed
        
        /*Muestra la forma de clasificación de productos*/
        cats.ClasExtProd c = new cats.ClasExtProd();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMClasProdActionPerformed

    
    /*Cuando se presiona una tecla en el menù de ubicaciones adicionales*/
    private void jMUbiAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMUbiAdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMUbiAdKeyPressed

    
    /*Cuando se presiona el menù de ubicaciones adicionales*/
    private void jMUbiAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMUbiAdActionPerformed
        
        /*Muestra la forma para las ubicaciones adicionales*/
        UbiAd a = new UbiAd();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMUbiAdActionPerformed

    
    /*Cuando se presiona una tecla en el menú de sistema*/
    private void jMSistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMSistKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMSistKeyPressed

    
    /*Cuando se presiona una tecla en el menú mayor de usuarios*/
    private void jMMUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMUsrKeyPressed

    
    /*Cuando se presona una tecla en el menú para ver los usuarios conectados*/
    private void jMUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMUsrKeyPressed

    
    /*Cuando se presiona el menú de usuarios conectados*/
    private void jMUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMUsrActionPerformed

        /*Muestra la forma para mostrar los usuarios conectados*/
        UsrCon c = new UsrCon();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMUsrActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de usuarios*/
    private void jMEstacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMEstacsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMEstacsKeyPressed

    
    /*Cuando se presiona el menú de reporte de usuarios*/
    private void jMEstacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMEstacsActionPerformed
        
        /*Muestra la forma para ver el reporte de las usuarios*/
        RepEstac r = new RepEstac();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMEstacsActionPerformed

    
    /*Cuando se presiona una tecla en el menú maestro ubicaciones*/
    private void jMMUbicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMUbicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMUbicKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de productos*/
    private void jMLogProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogProdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogProdKeyPressed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones*/
    private void jMMClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMClasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMClasKeyPressed

    
    /*Cuando se presiona el menú de log de productos*/
    private void jMLogProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogProdActionPerformed
        
        /*Abre la forma para ver el log de productos*/
        LogGral l = new LogGral("Log productos", "logprods", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogProdActionPerformed

    
    /*Cuando se presiona una tecla en el menú master de logs de rastreo*/
    private void jMMLogRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMLogRKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMLogRKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de kits*/
    private void jMLogKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogKKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogKKeyPressed

    
    /*Cuando se presiona el menú de log de kits*/
    private void jMLogKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogKActionPerformed
        
        /*Muestra la forma para ver el log de kits*/
        LogKit k = new LogKit();
        k.setVisible(true);
        
    }//GEN-LAST:event_jMLogKActionPerformed

    
    /*Cuando se presiona una tecla en el menú de log líneas*/
    private void jMLogLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogLinKeyPressed

    
    /*Cuando se presiona el menú de log de líneas*/
    private void jMLogLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogLinActionPerformed
       
        /*Muestra la forma para ver los logs de líneas*/        
        LogGral l = new LogGral("Log líneas", "loglins", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogLinActionPerformed

    
    /*Cuando se presiona una tecla en el menú de log de marcas*/
    private void jMLogMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogMarcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogMarcKeyPressed

    
    /*Cuando se presiona el menú de log marcas*/
    private void jMLogMarcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogMarcActionPerformed
        
        /*Muestra la forma para ver el log de marcas*/
        LogGral l = new LogGral("Log marcas", "logmarc", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogMarcActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de fabricantes*/
    private void jMLogFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogFabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogFabKeyPressed

    
    /*Cuando se presiona el menù de log de fabricantes*/
    private void jMLogFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogFabActionPerformed
        
        /*Muestra la forma para ver los logs de los fabricantes*/
        LogGral l = new LogGral("Log fabricantes", "logfabs", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogFabActionPerformed

    
    /*Cuando se presiona una tecla en el menu de log colores*/
    private void jMLogColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogColoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogColoKeyPressed

    
    /*Cuando se presiona el menù de log de colores*/
    private void jMLogColoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogColoActionPerformed
                
        /*Muestra la forma para ver el log de los colores*/
        LogGral l = new LogGral("Log colores", "logcolo", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogColoActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de clasificación extra de productos*/
    private void jMLogClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogClasKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogClasKeyPressed

    
    /*Cuando se presiona el menù de clasificación extra de productos*/
    private void jMLogClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogClasActionPerformed
        
        /*Muestra la forma para ver los logs de la clasificación extra de productos*/
        LogGral l = new LogGral("Log clasificaciones extra", "logclasext", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogClasActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de pesos*/
    private void jMLogPesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogPesKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogPesKeyPressed

    
    /*Cuando se presioa el menù de log de pesos*/
    private void jMLogPesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogPesActionPerformed
        
        /*Muestra la forma para ver el log de los pesos*/
        LogGral l = new LogGral("Log pesos", "logpes", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogPesActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de medidas*/
    private void jMLogMedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogMedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogMedKeyPressed

    
    /*Cuando se presiona el menù de log de medidas*/
    private void jMLogMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogMedActionPerformed
        
        /*Muestra la forma para ver el log de medidas*/
        LogGral l = new LogGral("Log medidas", "logmed", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogMedActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de unidades*/
    private void jMLogUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogUnidKeyPressed

    
    /*Cuando se presiona el menù de log de unidades*/
    private void jMLogUnidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogUnidActionPerformed
        
        /*Muestra la forma para ver el log de unidades*/
        LogGral l = new LogGral("Log unidades", "logunid", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogUnidActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de monedas*/
    private void jMLogMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogMonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogMonKeyPressed

    
    /*Cuando se presiona el menú de log de monedas*/
    private void jMLogMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogMonActionPerformed
        
        /*Muestra la forma para ver el log de las monedas*/
        LogGral l = new LogGral("Log monedas", "logmons", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogMonActionPerformed

    
    /*Cuando se presiona el menù de log de impuestos*/
    private void jMLogImpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogImpuActionPerformed
        
        /*Muestra la forma para ver el log de los impuestos*/
        LogGral l = new LogGral("Log impuestos", "logimpue", "val");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogImpuActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de impuestos*/
    private void jMLogImpuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogImpuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogImpuKeyPressed

    
    /*Cuando se presiona una tecla en el menù de log de conceptos*/
    private void jMLogConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogConcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogConcepKeyPressed

    
    /*Cuando se presiona el menù de log de conceptos*/
    private void jMLogConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogConcepActionPerformed
        
        /*Muestra la forma para ver el log de los conceptos*/
        LogGral l = new LogGral("Log conceptos", "logconcep", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogConcepActionPerformed

    
    /*Cuando se presiona una tecla en el menù del log de almacenes*/
    private void jMLogAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogAlmaKeyPressed

    
    /*Cuando se presioan el menù del log de almacenes*/
    private void jMLogAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogAlmaActionPerformed
        
        /*Muestra la forma para ver el log de almacenes*/
        LogGral l = new LogGral("Log almacenes", "logalmas", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de anaqueles*/
    private void jMenuItem1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItem1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenuItem1KeyPressed

    
    /*Cuando se presiona el menù de log de anaqueles*/
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        /*Muestra la forma de log de anaqueles*/
        LogGral l = new LogGral("Log anaqueles", "loganaq", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de lugares*/
    private void jMLogLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogLugKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogLugKeyPressed

    
    /*Cuando se presiona el menù de log de lugares*/
    private void jMLogLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogLugActionPerformed
        
        /*Muestra la forma de log de lugares*/
        LogGral l = new LogGral("Log lugares", "loglugs", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogLugActionPerformed

    
    /*Cuando se presiona una tecla en el menù de log de ubicaciones extra*/
    private void jMLogUbiEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogUbiEKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogUbiEKeyPressed

    
    /*Cuando se presiona el menù de log de ubicaciones extra*/
    private void jMLogUbiEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogUbiEActionPerformed
        
        /*Muestra la forma de ubicacines adicionales*/
        LogGral l = new LogGral("Log ubicaciones adicionales", "logubiad", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogUbiEActionPerformed

    
    /*Cuando se presiona una tecla en el menú maestro de carpeta de la aplicación*/
    private void jMMAplicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMAplicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMAplicKeyPressed

    
    /*Cuando se presiona una tecla en el menú de carpeta de la aplicación*/  
    private void jMCarpApKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCarpApKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCarpApKeyPressed

    
    /*Cuando se presiona el menú de carpeta de la aplicación*/
    private void jMCarpApActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCarpApActionPerformed
       
        /*Abre el directorio de la aplicación*/
        try
        {
            Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
        }        
        
    }//GEN-LAST:event_jMCarpApActionPerformed

    
    /*Cuando se presiona el menú del reporte de las cotizaciones*/
    private void jMRepCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepCotKeyPressed

    
    /*Cuando se presiona el menú de reporte de cotizaciones*/
    private void jMRepCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepCotActionPerformed
        
        /*Muestra la forma para ver el reporteador de cotizaciones*/
        RepCots c = new RepCots();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMRepCotActionPerformed

    
    /*Cuando se presiona una tecla en el menú de catálogo de mensajes*/
    private void jMCatMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCatMsjKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCatMsjKeyPressed

    
    /*Cuando se presiona el menú de catálogo de mensajes*/
    private void jMCatMsjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCatMsjActionPerformed
        
        /*Muestra la forma para ver el catálogo de mensajes*/
        CatMsjs c = new CatMsjs();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMCatMsjActionPerformed

    
    /*Cuando el estado de la ventana cambia*/
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        
        /*Que este máximizado siempre*/
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
    }//GEN-LAST:event_formWindowStateChanged

    
    /*Cuando se presiona una tecla en el menú de mensajes*/
    private void jMMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMsjKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMsjKeyPressed

    
    /*Cuando se presiona el menú de mensajes*/
    private void jMMsjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMMsjActionPerformed
        
        /*Muestra la forma para ver los mensajes*/
        Msjs m = new Msjs();
        m.setVisible(true);
        
    }//GEN-LAST:event_jMMsjActionPerformed

    
    /*Cuando se presiona una tecla en el menú de chat corporativo*/
    private void jMChatCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMChatCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMChatCKeyPressed

    
    /*Cuando se presiona el menú de chat corporativo*/
    private void jMChatCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMChatCActionPerformed
        
        /*Muestra la forma de chat corporativo*/
        ChatC c = new ChatC(false);
        c.getObj(true).setVisible(true);
        
    }//GEN-LAST:event_jMChatCActionPerformed

    
    /*Cuando se presiona una tecla en el menú de chat*/
    private void jMChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMChatKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMChatKeyPressed

    
    /*Cuando se presiona el menú de chat*/
    private void jMChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMChatActionPerformed
               
        /*Muestra la forma de chat*/
        ChatUsr c = new ChatUsr();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMChatActionPerformed

        
    /*Cuando se presiona una tecla en el menù de calculadora*/
    private void jMCalcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCalcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCalcKeyPressed

    
    /*Cuando se presiona el menú de ver calculadora*/
    private void jMCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCalcActionPerformed
        
        /*Muestra la calculadora que este configurada*/
        Star.vShowCal();
        
    }//GEN-LAST:event_jMCalcActionPerformed

    
    /*Cuando se presiona una tecla en el menú de ver cuaderno herramienta*/
    private void jMCuadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCuadeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCuadeKeyPressed

    
    /*Cuando se presiona una tecla en el menú de abrir aplicación favorita*/
    private void jMApFavKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMApFavKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMApFavKeyPressed

    
    /*Cuando se presiona el menú de cuaderno*/
    private void jMCuadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCuadeActionPerformed
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene la ruta hacia el cuaderno*/
        String sRut = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE conf = 'cuade' AND clasif = 'sist'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sRut      = rs.getString("extr");                                   
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

        /*Si la ruta es cadena vacia entonces se va a abrir el notepad de windows*/        
        if(sRut.compareTo("")==0)
            sRut    = "notepad";      
        
        /*Abre el cuaderno*/
        try
        {
            Runtime.getRuntime().exec(sRut);
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                        
        }                        
        
    }//GEN-LAST:event_jMCuadeActionPerformed

    
    /*Cuando se presiona el menú de ver aplicación favorita*/
    private void jMApFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMApFavActionPerformed
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene la ruta hacia la aplicación favorita*/
        String sRut = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE conf = 'apfavo' AND clasif = 'sist'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sRut      = rs.getString("extr");                                   
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

        /*Si la ruta es cadena vacia entonces*/        
        if(sRut.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido ruta para la aplicación favorita. En las configuraciones de sistema lo puedes hacer.", "Aplicación Favorita", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
        /*Abre el cuaderno*/
        try
        {
            Runtime.getRuntime().exec(sRut);
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                        
        }                        
        
    }//GEN-LAST:event_jMApFavActionPerformed

    
    /*Cuando se presiona una tecla en el menú de enviar archivo*/
    private void jMEnviAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMEnviAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMEnviAKeyPressed

    
    /*Cuando se presiona el menù de enviar archivo*/
    private void jMEnviAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMEnviAActionPerformed

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
                
        /*Muestra la forma para escoger la o las usuarios a las que se les va a mandar un archivo*/
        EnviArchEstac a = new EnviArchEstac();
        a.getObj().setVisible(true);
        
    }//GEN-LAST:event_jMEnviAActionPerformed

    
    /*Cuando se presiona una tecla en el botón de punto de venta*/
    private void jBPtoVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPtoVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPtoVtaKeyPressed

    
    /*Cuando se presiona el botón de punto de venta*/
    private void jBPtoVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPtoVtaActionPerformed
        
        /*Abre el punto de venta*/
        vAbrPto();
        
    }//GEN-LAST:event_jBPtoVtaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ventas*/
    private void jBVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVtasKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVtasKeyPressed


    /*Método para que se abra una sola vez la forma de ventas*/
    private void vAbrVtas()
    {
        /*Abre la forma de las cotizaciones una sola vez*/
        if(Star.gVtas==null)
        {            
            Star.gVtas = new Vtas(btnsVentas);
            Star.gVtas.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gVtas.isVisible())            
                Star.gVtas.toFront();
            else
                Star.gVtas.setVisible(true);            
        }                    
    }
    
    
    /*Cuando se presiona el botón de ventas*/
    private void jBVtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVtasActionPerformed
        
        /*Muestra el formulario de ventas*/
        vAbrVtas();
        
    }//GEN-LAST:event_jBVtasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de compras*/
    private void jBCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCompsKeyPressed

    
    /*Cuando se presiona el botón de compras*/
    private void jBCompsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompsActionPerformed
        
        /*Muestra el formulario de compras*/
        vAbrComprs();
        
    }//GEN-LAST:event_jBCompsActionPerformed

    
    /*Cuando se presiona una tecla en el botón de empresas*/
    private void jBEmpsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEmpsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEmpsKeyPressed

    
    /*Cuando se presiona una tecla en el botón de proveedores*/
    private void jBProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProvsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProvsKeyPressed

    
    /*Cuando se presiona el botón de empresas*/
    private void jBEmpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEmpsActionPerformed
        
        /*Muestra el gráfico de manejo de clientes*/
        vAbrClients();
        
    }//GEN-LAST:event_jBEmpsActionPerformed

    
    /*Cuando se presiona el botón de proveedores*/
    private void jBProvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProvsActionPerformed
        
        /*Mustra el gráfico de manejo de proveedores*/        
        vAbrProvs();
        
    }//GEN-LAST:event_jBProvsActionPerformed

    
    /*Cuando se presiona una tecla en el botón de productos*/
    private void jBProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdsKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cotizaciones*/
    private void jBCotsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCotsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCotsKeyPressed

    
    /*Cuando se presiona el botón de productos*/
    private void jBProdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdsActionPerformed
        
        /*Si se tiene que mostrar la ventana entonces*/
        vAbrProds();
        
    }//GEN-LAST:event_jBProdsActionPerformed

    
    /*Cuando se presiona el botón de cotizaciones*/
    private void jBCotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCotsActionPerformed
        
        /*Muestra el formulario para ver todos las cotizaciones*/
        vAbrCots();
        
    }//GEN-LAST:event_jBCotsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de archivos compartidos*/
    private void jMArchCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMArchCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMArchCKeyPressed

    
    /*Cuando se presiona el menú de archivos compartidos*/
    private void jMArchCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMArchCActionPerformed

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
        
        /*Muestra la forma de archivos compartidos*/
        ArchComps a = new ArchComps();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMArchCActionPerformed

    
    /*Cuando se presiona una tecla en el menù de reporte de vendedores*/
    private void jMRepVendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepVendKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepVendKeyPressed

    
    /*Cuando se presiona el menù de reporte de vendedores*/
    private void jMRepVendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepVendActionPerformed
        
        /*Muestra la forma para ver el reporte de los vendedores*/
        RepVends r = new RepVends();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepVendActionPerformed

    
    /*Cuando se presiona una tecla en el menú padre de ayuda*/
    private void jMMAyuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMAyuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMAyuKeyPressed

    
    /*Cuando se presiona una tecla en el menú de acerca de*/
    private void jMAcercKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMAcercKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMAcercKeyPressed

    
    /*Cuando se presiona el menú de acerca de*/
    private void jMAcercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMAcercActionPerformed
        
        /*Muestra la forma de acerca de*/
        AcerD a = new AcerD();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMAcercActionPerformed

    
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
            
    
    /*Cuando se presiona el menù de reporte de compras*/
    private void jMRepComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepComActionPerformed
        
        /*Muestra la forma de reporte de compras*/
        RepComT r = new RepComT();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepComActionPerformed

    
    /*Cuando se presiona una tecla en el menù de reporte de compras*/
    private void jMRepComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepComKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepComKeyPressed

    
    /*Cuando se presiona una tecla en el menú de series de órdenes de compra*/
    private void jMSerOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMSerOKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMSerOKeyPressed

    
    /*Cuando se presiona el menú de series de órdenes de compra*/
    private void jMSerOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMSerOActionPerformed
        
        /*Muestra la forma para las series de las remisiones*/
        SerOrd c = new SerOrd();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMSerOActionPerformed

    
    /*Cuando se presiona una tecla en el menú del reporteador de órdenes de compra*/    
    private void jMRepOrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepOrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jMRepOrKeyPressed

    
    /*Cuando se presiona el menú de ver órdenes de compra*/
    private void jMRepOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepOrActionPerformed
        
        /*Muestra el reporteador de las órdenes de compra*/
        RepOrd r = new RepOrd();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepOrActionPerformed

    
    /*Cuando se va cerrando la aplicación*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Función para cuando se tiene que salir de la aplicación*/
        vExiAp();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona el menú de reporte de kardex*/
    private void jMKardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMKardActionPerformed
        
        /*Muestra la forma para el reporteador de kardex*/
        RepKard k = new RepKard();
        k.setVisible(true);
        
    }//GEN-LAST:event_jMKardActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de kardex*/
    private void jMKardKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMKardKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMKardKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBPtoVtaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPtoVtaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPtoVta.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPtoVtaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVtasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVtasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVtas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVtasMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCompsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBComps.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCompsMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBEmpsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEmpsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEmps.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEmpsMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProvsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProvs.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProvsMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProdsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProds.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdsMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCotsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCotsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCots.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCotsMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPtoVtaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPtoVtaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPtoVta.setBackground(colOri);
        
    }//GEN-LAST:event_jBPtoVtaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVtasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVtasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVtas.setBackground(colOri);
        
    }//GEN-LAST:event_jBVtasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCompsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBComps.setBackground(colOri);
        
    }//GEN-LAST:event_jBCompsMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBEmpsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEmpsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEmps.setBackground(colOri);
        
    }//GEN-LAST:event_jBEmpsMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProvsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProvs.setBackground(colOri);
        
    }//GEN-LAST:event_jBProvsMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProdsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProds.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdsMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCotsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCotsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCots.setBackground(colOri);
        
    }//GEN-LAST:event_jBCotsMouseExited

    
    /*Cuando se presiona una tecla en el menú de reporte de costeos*/
    private void jMCostsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCostsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCostsKeyPressed

    
    /*Cuando se presiona el menú de ver reporte de costeos*/
    private void jMCostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCostsActionPerformed
        
        /*Muestra la forma par ver los reportes de costeos*/
        RepCosts k = new RepCosts();
        k.setVisible(true);
        
    }//GEN-LAST:event_jMCostsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de log de respaldos*/
    private void jMLogResKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogResKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogResKeyPressed

    
    /*Cuando se presiona el menú de ver log de respaldos*/
    private void jMLogResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogResActionPerformed
        
        /*Muestra la forma para ver el log de respaldos*/
        LogResp l = new LogResp();
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogResActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de respaldos*/
    private void jMRespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRespKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRespKeyPressed

    
    /*Cuando se presiona el menú de reporte de respaldos*/
    private void jMRespActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRespActionPerformed
        
        /*Muestra la forma para reportear los respaldos*/
        RepResp r = new RepResp();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRespActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de flujo*/
    private void jMFlujKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMFlujKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMFlujKeyPressed

    
    /*Cuando se presiona el menú de reporte de flujo*/
    private void jMFlujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMFlujActionPerformed
        
        /*Muestra la forma para reportear el flujo*/
        report.RepFluj  r = new report.RepFluj();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMFlujActionPerformed

    
    /*Cuando se presiona una tecla en el panel 1*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el control de la imágen*/
    private void jLImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLImgKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLImgKeyPressed

    
    /*Cuando se presiona una tecla en el menú de series de notas de crédito*/
    private void jMSerNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMSerNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMSerNotKeyPressed

    
    /*Cuando se presiona el menú de series de notras de crédito*/
    private void jMSerNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMSerNotActionPerformed
        
        /*Muestra la forma para las series de las remisiones*/
        SerNot c = new SerNot();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMSerNotActionPerformed

    
    /*Cuando e presiona el menú de clasificaciones de clientes*/
    private void jMClasCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMClasCliActionPerformed
                
        /*Abre la forma para dar de alta las clasificaciones de los clientes*/
        ClasCli c = new ClasCli();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMClasCliActionPerformed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones de clientes*/
    private void jMClasCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMClasCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMClasCliKeyPressed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones de proveedores*/
    private void jMClasProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMClasProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMClasProvKeyPressed

    
    /*Cuando se presiona el menú de clasificaciones de proveedores*/
    private void jMClasProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMClasProvActionPerformed
        
        /*Muestra la forma para dar de alta las clasificaciones de los provs*/
        cats.PlanGralCat l = new cats.PlanGralCat("clasprov","ProVs","codclas","proveedor","clasprov","cod");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMClasProvActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de log de correos*/
    private void jMRepLogCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepLogCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepLogCoKeyPressed

    
    /*Cuando se presiona el menú de reporte de log de correos*/
    private void jMRepLogCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepLogCoActionPerformed
        
        /*Muestra la forma para ver el reporte de los logs de correos*/
        RepLogCor r = new RepLogCor();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepLogCoActionPerformed

    
    /*Cuando se presiona una tecla en el menú de Modelos*/
    private void jMModelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMModelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMModelKeyPressed

    
    /*Cuando se presiona el menú de Modelos*/
    private void jMModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMModelActionPerformed
        
        /*Muestra la forma para ver el catálogo de Modelos*/
        cats.PlanGralCat f = new cats.PlanGralCat("mODEL","Prods","mode","modelo","mod","cod");
        f.setVisible(true);
        
    }//GEN-LAST:event_jMModelActionPerformed

    
    /*Cuando se presiona una tecla en el menú de ver tabla de compatibilidades*/
    private void jMTabCompaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMTabCompaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMTabCompaKeyPressed

    
    /*Cuando se presiona el menú de ver tabla de compabilidades*/
    private void jMTabCompaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMTabCompaActionPerformed
        
        /*Muestra la forma para ver la tabla de compatibilidades*/
        ptovta.TabCompa t = new ptovta.TabCompa();
        t.setVisible(true);
        
    }//GEN-LAST:event_jMTabCompaActionPerformed

    
    /*Cuando se presiona una tecla en el menú de tallas*/
    private void jMenuItem2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItem2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenuItem2KeyPressed

    
    /*Cuando se presiona el menú de tallas*/
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        /*Muestra el formulario de marcas*/
        cats.PlanGralCat f = new cats.PlanGralCat("Tall","TallColo","tall","talla","tall","cod");
        f.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    
    /*Cuando se presiona el menú de log de Modelos*/
    private void jMLogModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogModActionPerformed
        
        /*Muestra la forma de log de Modelos*/
        LogGral l = new LogGral("Log modelos", "logmod", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogModActionPerformed

    
    /*Cuando se presiona una tecla en el menú de log Modelos*/
    private void jMLogModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogModKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de tallas*/
    private void jMLogTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogTallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogTallKeyPressed

    
    /*Cuando se presiona el menú de log de tallas*/
    private void jMLogTallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogTallActionPerformed
        
        /*Muestra la forma de log de Modelos*/
        LogGral l = new LogGral("Log tallas", "logtall", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogTallActionPerformed

    
    /*Cuando se presiona una tecla en el menú de tallas y colores*/
    private void jMTallColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMTallColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMTallColKeyPressed

    
    /*Cuando se presiona el menú de tallas y colores*/
    private void jMTallColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMTallColActionPerformed
        
        /*Muestra la forma para ver las tallas y colores*/
        TallColo t = new TallColo();
        t.setVisible(true);
        
    }//GEN-LAST:event_jMTallColActionPerformed

    
    /*Cuando se presiona una tecla en el menú de lotes y pedimentos*/
    private void jMLotPedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLotPedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLotPedKeyPressed

    
    /*Cuando se presiona el menu de ver lotes y pedimentos*/
    private void jMLotPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLotPedActionPerformed
        
        /*Muestra la forma de lotes y pedimentos*/
        cats.LotPedCat l = new cats.LotPedCat();
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLotPedActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de compras*/
    private void jMMRepComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMRepComKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMRepComKeyPressed

    
    /*Cuando se presiona una tecla en el menú de reporte padre de ventas*/
    private void jMMRepVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMRepVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMRepVtaKeyPressed

    
    /*Cuando se presiona una tecla en el menú padre de reportes de productos*/
    private void jMMRepProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMRepProdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMRepProdKeyPressed

    
    /*Cuando se presiona una tecla en el menú de reportes de sistema*/
    private void jMMRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMRepKeyPressed

    
    /*Cuando se presiona una tecla en el menú de reporte de backorder*/
    private void jMRepBackOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepBackOKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepBackOKeyPressed

    
    /*Cuando se presiona el botón de reporte de backorder*/
    private void jMRepBackOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepBackOActionPerformed
    
        /*Muestra la forma para reportear el backorder*/
        RepBack r = new RepBack();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepBackOActionPerformed

    
    /*Cuando se presiona una tecla en el menú de tipos*/
    private void jMTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMTipKeyPressed

    
    /*Cuando se presiona el menú de tipos*/
    private void jMTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMTipActionPerformed
        
        /*Muestra el catálogo de tipos*/
        cats.PlanGralCat t = new cats.PlanGralCat("Tips","Prods","tip","tipo","Tip","cod");
        t.setVisible(true);
        
    }//GEN-LAST:event_jMTipActionPerformed

    
    /*Cuando se presiona una tecla en el menú de log de tipos*/
    private void jMLogTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogTipKeyPressed

    
    /*Cuando se preciona el menú de log de tipos*/
    private void jMLogTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogTipActionPerformed
        
        /*Muestra la forma de log de tipos*/
        LogGral l = new LogGral("Log tipos", "logtip", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogTipActionPerformed

    
    /*Cuando se presiona una tecla en el menú padre de importaciones en inventarios*/
    private void jMMInvenImporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMInvenImporKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMInvenImporKeyPressed

    
    /*Cuando se presiona una tecla en el menú padre de exportaciones en inventarios*/
    private void jMMInvenExporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMInvenExporKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMInvenExporKeyPressed

    
    /*Cuando se presiona una tecla en el menú de exportar catálogo de productos a excel*/
    private void jMInvenExporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMInvenExporKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMInvenExporKeyPressed

    
    /*Cuando se presiona una tecla en el menú de importar inventarios de series desde excel*/
    private void jMInvenImpoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMInvenImpoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMInvenImpoSerKeyPressed
 
    /*Cuando se presiona el menú de exportar catálogo de productos a excel*/
    private void jMInvenExporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMInvenExporActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar productos");
                
        /*Si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        /*Muestra la forma para simular que esta cargando el reporte*/
        Star.lCargGral = new LoadinGral("Exportando de productos a excel...");
        Star.lCargGral.setVisible(true);

        /*Realiza todo esto en un thread*/
//        Thread th = new Thread()
//        {
//            @Override
//            public void run()
//            {
                /*Lee la ruta seleccionada*/
                String sRut = fc.getCurrentDirectory().getAbsolutePath();

                /*Obtiene el nombre del archivo y concatenalo a la ruta*/
                sRut += "\\" + fc.getSelectedFile().getName(); 

                /*Agregale la extensiÃ³n de excel*/
                sRut += ".xlsx";
                /*Crea un documentovacio*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Productos");

                /*Variable contadora*/        
                int         iConta                   = 1;
                
                /*Abre la base de datos*/
                Connection  con;
                try
                {
                    con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
                }
                catch(SQLException | HeadlessException e)
                {
                    /*Escone el loading*/
                    if(Star.lCargGral!=null)                        
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
                    return;
                }

                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> data = new TreeMap<>();
                data.put(Integer.toString(iConta), new Object[] {"COD PRODUCTO (1)", "COD PROD OPCIONAL 1 (2)", "COD PROD OPCIONAL 2 (3)", "COD MEDIDA (4)", "MEDIDA (5)", "DESCRIPCION LARGA (6)", "DESCRIPCION CORTA (7)", "COD LINEA Y DESCRIPCIÃ“N (8)", "COD MARCA Y DESCRIPCIÃ“N (9)", "COD FABRICANTE Y DESCRIPCIÃ“N (10)", "COD COLOR Y DESCRIPCIÃ“N(11)", "COD PESO Y DESCRIPCIÃ“N (12)", "PESO (13)", "PRECIO LISTA 1 (14)", "PRECIO LISTA 2 (15)", "PRECIO LISTA 3 (16)", "PRECIO LISTA 4 (17)", "PRECIO LISTA 5 (18)", "PRECIO LISTA 6 (19)", "PRECIO LISTA 7 (20)", "PRECIO LISTA 8 (21)", "PRECIO LISTA 9 (22)", "PRECIO LISTA 10 (23)", "COSTO (24)", "COSTO RECOGER (25)", "EXISTENCIA (26)", "COD UNIDAD Y DESCRIPCIÃ“N (27)", "COD ALMACEN Y DESCRIPCIÃ“N (28)", "COD ANAQUEL Y DESCRIPCIÃ“N (29)", "COD LUGAR Y DESCRIPCIÃ“N (30)", "ANOTACIONES(31)", "INFORMACION (32)", "MINIMO (33)", "MAXIMO (34)", "COMPUESTO (35)", "IMPUESTO Y VALOR(36)", "INVENTARIABLE (37)", "VENDER ABAJO DEL COSTO (38)", "ES PARA VENTA(39)", "ES SERVICIO(40)", "TIPO(41)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iConta;

                /*Declara variables de la base de datos*/
                Statement   st;
                ResultSet   rs;                
                String      sQ              = "";

                /*Trae todos los registros de los productos*/
                try
                {
                    sQ = "SELECT CASE WHEN prods.DESCRIP = '' THEN '.' ELSE prods.DESCRIP END descripprod, CASE WHEN descripcort = '' THEN '.' ELSE descripcort END descripcort, med, prod, CASE WHEN prodop1 = '' THEN '.' ELSE prodop1 END prodop1, CASE WHEN prodop2 = '' THEN '.' ELSE prodop2 END prodop2, CASE WHEN prods.CODMED = '' THEN '.' ELSE prods.CODMED END codmed, CASE WHEN meds.DESCRIP = '' THEN 'GENERICA' ELSE meds.DESCRIP END meddescrip, CASE WHEN prods.LIN = '' THEN 'SYS' ELSE prods.LIN END lin, CASE WHEN lins.DESCRIP = '' THEN 'GENERICA' ELSE lins.DESCRIP END lindescrip, CASE WHEN prods.MARC = '' THEN 'SYS' ELSE prods.MARC END marc, CASE WHEN marcs.DESCRIP = '' THEN 'GENERICA' ELSE marcs.DESCRIP END marcdescrip, CASE WHEN prods.FAB = '' THEN 'SYS' ELSE prods.FAB END fab, CASE WHEN fabs.DESCRIP = '' THEN 'GENERICA' ELSE fabs.DESCRIP END fabdescrip, CASE WHEN prods.COLO = '' THEN 'SYS' ELSE prods.COLO END colo, CASE WHEN colos.DESCRIP = '' THEN 'GENERICA' ELSE colos.DESCRIP END colodescrip, CASE WHEN prods.PES = '' THEN 'SYS' ELSE prods.PES END pes, CASE WHEN pes.DESCRIP = '' THEN 'GENERICA' ELSE pes.DESCRIP END pesdescrip, pesman, prelist1, prelist2, prelist3, prelist4, prelist5, prelist6, prelist7, prelist8, prelist9, prelist10, cost, costre, exist, CASE WHEN prods.UNID = '' THEN 'SYS' ELSE prods.UNID END unid, CASE WHEN unids.DESCRIP = '' THEN 'GENERICA' ELSE unids.DESCRIP END uniddescrip, CASE WHEN prods.ALMA = '' THEN 'SYS' ELSE prods.ALMA END alma, CASE WHEN almadescrip = '' THEN 'GENERICA' ELSE almadescrip END almadescrip, CASE WHEN prods.ANAQ = '' THEN 'SYS' ELSE prods.ANAQ END anaq, CASE WHEN anaqs.DESCRIP = '' THEN 'GENERICA' ELSE anaqs.DESCRIP END anaqdescrip, CASE WHEN prods.LUG = '' THEN 'SYS' ELSE prods.LUG END lug, CASE WHEN lugs.DESCRIP = '' THEN 'GENERICA' ELSE lugs.DESCRIP END lugdescrip, CASE WHEN descprov = '' THEN '.' ELSE descprov END descprov, CASE WHEN infor = '' THEN '.' ELSE infor END infor, min, max, compue, CASE WHEN prods.IMPUE = '' THEN 'IVA' ELSE prods.IMPUE END impue, IFNULL(impueval,0) impueval , invent, bajcost, esvta, servi, CASE WHEN prods.TIP = '' THEN 'SYS' ELSE prods.TIP END tip, CASE WHEN tips.DESCRIP = '' THEN 'GENERICA' ELSE tips.DESCRIP END tipdescrip FROM prods LEFT OUTER JOIN tips ON tips.COD = prods.TIP LEFT OUTER JOIN impues ON impues.CODIMPUE = prods.IMPUE LEFT OUTER JOIN lugs ON lugs.COD = prods.LUG LEFT OUTER JOIN anaqs ON anaqs.COD = prods.ANAQ LEFT OUTER JOIN almas ON almas.ALMA = prods.ALMA LEFT OUTER JOIN unids ON unids.COD = prods.UNID LEFT OUTER JOIN pes ON pes.COD = prods.PES LEFT OUTER JOIN colos ON colos.COD = prods.COLO LEFT OUTER JOIN fabs ON fabs.COD = prods.FAB LEFT OUTER JOIN marcs ON marcs.COD = prods.MARC LEFT OUTER JOIN lins ON prods.LIN = lins.COD LEFT OUTER JOIN meds ON meds.COD = prods.CODMED";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {                    
                        /*Agrega el registro en la fila de excel*/
                        data.put(Integer.toString(iConta), new Object[] {rs.getString("prod"), rs.getString("prodop1"), rs.getString("prodop2"), rs.getString("codmed") + "," + rs.getString("meddescrip"), Integer.parseInt(rs.getString("med")), rs.getString("descripprod"),rs.getString("descripcort"), rs.getString("lin") + "," + rs.getString("lindescrip"), rs.getString("marc") + "," + rs.getString("marcdescrip"), rs.getString("fab") + "," + rs.getString("fabdescrip"), rs.getString("colo") + "," + rs.getString("colodescrip"), rs.getString("pes") + "," + rs.getString("pesdescrip"), rs.getString("pesman"), rs.getString("prelist1"), rs.getString("prelist2"), rs.getString("prelist3"), rs.getString("prelist4"), rs.getString("prelist5"), rs.getString("prelist6"), rs.getString("prelist7"),rs.getString("prelist8"), rs.getString("prelist9"), rs.getString("prelist10"), rs.getString("cost"), rs.getString("costre"), rs.getString("exist"), rs.getString("unid") + "," + rs.getString("uniddescrip"), rs.getString("alma") + "," + rs.getString("almadescrip"), rs.getString("anaq") + "," + rs.getString("anaqdescrip"), rs.getString("lug") + "," + rs.getString("lugdescrip"), rs.getString("descprov"), rs.getString("infor"), Integer.parseInt(rs.getString("min")), Integer.parseInt(rs.getString("max")), Integer.parseInt(rs.getString("compue")), rs.getString("impue") + "," + rs.getString("impueval"), Integer.parseInt(rs.getString("invent")), Integer.parseInt(rs.getString("bajcost")), Integer.parseInt(rs.getString("esvta")), Integer.parseInt(rs.getString("servi")), rs.getString("tip") + "," + rs.getString("tipdescrip")});

                        /*Aumenta en uno el contador de filas del libro*/
                        ++iConta;
                    }
                }
                catch(SQLException e)
                {
                    /*Escone el loading*/
                    if(Star.lCargGral!=null)                        
                        Star.lCargGral.setVisible(false);

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
                    return;
                }

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                {
                    /*Escone el loading*/
                    if(Star.lCargGral!=null)                        
                        Star.lCargGral.setVisible(false);
                    
                    //Regresa
                    return;
                }                    

                /*Agrega el terminador de fichero*/
                data.put(Integer.toString(iConta), new Object[] {"FINARCHIVO"});

                /*Itera sobre la informaciÃ³n y escribela en la hoja*/
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset)
                {
                    Row row = sheet.createRow(rownum++);
                    Object [] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object ob : objArr)
                    {
                        Cell cell = row.createCell(cellnum++);
                        if(ob instanceof String)
                        cell.setCellValue((String)ob);
                        else if(ob instanceof Integer)
                        cell.setCellValue((Integer)ob);
                    }
                }

                /*Escribe los datos en el archivo*/
                try
                {
                    //Write the wkbok in file system
                    FileOutputStream out;
                    out = new FileOutputStream(new File(sRut));
                    wkbok.write(out);
                    out.close();
                }
                catch(IOException e)
                {
                    /*Escone el loading*/
                    if(Star.lCargGral!=null)                        
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "Error por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
                    return;
                }

                /*Escone el loading*/
                if(Star.lCargGral!=null)                        
                    Star.lCargGral.setVisible(false);

                /*Mensaje de Ã©xito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en \"" + sRut + "\" con Éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op     = {"Si","No"};
                int iRes        = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                } 
                catch(IOException e) 
                {
                    /*Agrega en el log y mensajea*/
                    Login.vLog(e.getMessage());            
                    JOptionPane.showMessageDialog(null, "No se puede abrir archivo por " + e.getMessage(), "Abrir Archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                }

//            }/*Fin de public void run()*/
//        };
//        th.start();                               
                
    }//GEN-LAST:event_jMInvenExporActionPerformed

    
    /*Cuando se presiona el menú de impotar catálogo de series desde excel*/
    private void jMInvenImpoSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMInvenImpoSerActionPerformed
        
        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario n presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
            
        /*Lee la ruta seleccionada*/
        final String sRut    = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Si no es un archivo de excel entonces*/
        if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no VÃ¡lido", JOptionPane.INFORMATION_MESSAGE, null); 
            return;
        }                

        /*Realiza todo en un thread*/
        Thread th = new Thread()
        {
            @Override
            public void run()
            {                
                /*Abre la base de datos*/                                        
                Connection      con;
                try 
                {
                    con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );       
                    con.setAutoCommit(false);
                } 
                catch(SQLException | HeadlessException e) 
                {   
                    /*Esconde el loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/    
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null); 
                    return;
                }

                /*Crea la instancia hacia el archivo a importar*/
                FileInputStream file;
                try
                {
                    file    =  new FileInputStream(new File(sRut));
                }
                catch(Exception e)
                {
                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/    
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null); 
                    return;
                }                    

                /*Instancia el objeto de excel*/
                XSSFWorkbook wkbok;
                try
                {
                    wkbok   = new XSSFWorkbook(file);
                }
                catch(Exception e)
                {
                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/    
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null); 
                    return;
                }                                        

                /*Obtiene la primera hoja del libro*/
                XSSFSheet sheet         = wkbok.getSheetAt(0);
                                                    
                /*Inicia la transacciÃ³n*/
                try           
                {
                    con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                }
                catch(SQLException ex)
                {
                    /*Esconde la forma de loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;
                    
                    /*Agrega en el log*/
                    Login.vLog(ex.getMessage());

                    /*Mensajea y regresa*/   
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);      
                    return;
                }

                /*Declara variables de la base de datos*/
                Statement       st;                                        
                String          sQ              = ""; 
                ResultSet       rs;                
                
                /*Borra todos los registros de las series*/
                try 
                {            
                    sQ = "DELETE FROM serieprod";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException | HeadlessException e) 
                 { 
                    /*Esconde el loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)                  
                        return;
                    
                    /*Agrega en el log y mensajea*/
                    Login.vLog(e.getMessage());                        
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
                }
            
                /*Contador de fila*/
                int iConta                  = 1;               
            
                /*Inicializa el contador de la celda por cada fila*/
                int iContCell               = 1;

                /*Recorre todas las hojas de excel*/
                Iterator<Row> rowIt     = sheet.iterator();
                while(rowIt.hasNext())
                {                    
                    /*Recorre todas columnas del archivo*/
                    Row row             = rowIt.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                    if(iConta == 1)
                    {
                        ++iConta;
                        continue;
                    }

                    /*Variable para leer las celdas*/
                    String sIn;                   

                    /*Inicializa la consulta*/
                    String sQInsert    = "INSERT INTO serieprod(prod, ser, comen, exist, alma, sucu, nocaj, estac) VALUES('";                    
                
                    /*Contiene el cÃ³digo del producto y la serie*/
                    String sProd = "", sSer  = "";
                    
                    /*Recorre todas las celdas de la fila*/
                    while(cellIterator.hasNext())
                    {
                        /*Obtiene el objeto de la celda*/
                        Cell cell       = cellIterator.next();                 

                        /*Determina el tipo de celda que es*/
                        switch(cell.getCellType())
                        {
                            /*En caso de que sea de tipo string entonces*/
                            case Cell.CELL_TYPE_STRING:
                                
                                /*Obtiene el valor de la celda*/
                                sIn         = cell.getStringCellValue();                                                            
                            
                                /*Si es la celda 1 entonces*/
                                if(iContCell==1)
                                {
                                    /*Si es el fin del archivo entonces*/
                                    if(sIn.compareTo("FINARCHIVO")==0)
                                    {
                                        /*Termina la transacciÃ³n*/
                                        try           
                                        {
                                            con.commit();
                                        }
                                        catch(SQLException ex)
                                        {                                            
                                            /*Esconde la forma de loading*/
                                            if(Star.lCargGral!=null)
                                                Star.lCargGral.setVisible(false);

                                            //Cierra la base de datos
                                            if(Star.iCierrBas(con)==-1)                  
                                                return;
                                            
                                            /*Agrega en el log*/
                                            Login.vLog(ex.getMessage());

                                            /*Mensajea y regresa*/   
                                            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);      
                                            return;
                                        }

                                        /*Esconde el loading*/
                                        if(Star.lCargGral!=null)
                                            Star.lCargGral.setVisible(false);
                                        
                                        //Cierra la base de datos
                                        if(Star.iCierrBas(con)==-1)                  
                                            return;
                                        
                                        /*Mensajea y regresa*/
                                        JOptionPane.showMessageDialog(null, "Exito en la importaciÃ³n de " + (iConta - 1) + " series.", "Series", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                        return;
                                    }
                                    /*Else no es el final del archivo entonces*/
                                    else
                                    {
                                        /*Guarda el cÃ³digo del producto y completa la consulta*/
                                        sProd       = cell.getStringCellValue();
                                        sQInsert   += cell.getStringCellValue() + "','";
                                    }
                                    
                                }/*Fin de if(iContCell==1)*/                                    
                                /*Else if: serie*/
                                else if(iContCell==2)
                                {
                                    /*Guarda el la serie y completa la consulta*/
                                    sSer       = cell.getStringCellValue();
                                    sQInsert   += cell.getStringCellValue() + "','";
                                }
                                /*Else if: AlmacÃ©n*/
                                else if(iContCell==5)
                                {   
                                    /*Checa si el cÃ³digo del almacÃ©n ya existe en la base de datos*/        
                                    int iRes    = Star.iExiste(con, cell.getStringCellValue().trim(), "almas", "alma");

                                    //Si hubo error entonces
                                    if(iRes==-1)
                                    {
                                        //Resetea la bandera
                                        bErr    = false;
                                        
                                        //Cierra la base de datos y regresa
                                        Star.iCierrBas(con);                  
                                        return;
                                    }

                                    /*Completa la consulta*/
                                    sQInsert   += cell.getStringCellValue() + "','";
                                }
                                /*Else completa la cadena de sql*/
                                else
                                    sQInsert   += cell.getStringCellValue() + "','";
                                
                                break;

                                /*En caso de que sea de tipo nÃºmero entonces*/
                                case Cell.CELL_TYPE_NUMERIC:
                                    
                                    /*Si va en la celda de la existencia entonces*/
                                    if(iContCell==4)
                                    {
                                        /*Quita la Ãºltima coma y completa la consulta*/
                                        sQInsert = sQInsert.substring(0, sQInsert.length() - 1);
                                        sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                    
                                    }
                                            
                                break;
                                
                        }/*Fin de switch(cell.getCellType())*/

                        /*Aumenta en uno el contador de las celdas*/
                        ++iContCell;
                        
                    }/*Fin de while(cellIterator.hasNext())*/

                    /*Aumenta en uno el contador de las filas*/
                    ++iConta;
                    
                    /*Resetea el contador de celdas*/
                    iContCell   = 1;
                            
                    /*Quita los Ãºltimos carÃ¡cteres invÃ¡lidos*/
                    sQInsert        = sQInsert.substring(0, sQInsert.length() - 2);

                    /*Agrega el terminador de la consulta*/
                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "')";
                    
                    	/*Comprueba si ya existe ese producto para esa serie*/
                        try
                        {
                            sQ = "SELECT prod FROM serieprod WHERE prod = '" + sProd + "' AND ser = '" + sSer + "'";	
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces si existe y continua*/
                            if(rs.next())
                                continue;
                        }
                        catch(SQLException e)
                        {
                            //Cierra la base de datos y regresa
                            if(Star.iCierrBas(con)==-1)                                
                                return;

                            /*Agrega en el log*/
                            Login.vLog(e.getMessage());

                            /*Mensajea y regresa*/
                            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null); 
                            return;
                        }

                    /*Inserta en la base de datos el registro*/
                    try
                    {
                        st = con.createStatement();                        
                        st.executeUpdate(sQInsert);
                    }
                    catch(SQLException | HeadlessException e)
                    {
                        /*Esconde el loading*/
                        if(Star.lCargGral!=null)
                            Star.lCargGral.setVisible(false);

                        //Cierra la base de datos y regresa
                        if(Star.iCierrBas(con)==-1)                                
                            return;
                        
                        /*Agrega en el log*/
                        Login.vLog(e.getMessage());

                        /*Llama al recolector de basura y mensajea*/
                        System.gc();                       
                        
                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "Error en fila: "  + iConta + " celda: " + iContCell + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
                        return;
                    }                                                                        
                    
                }/*Fin de while(rowIt.hasNext())*/                                    
                
            }/*Fin de public void run()*/
        };
        th.start();                                
                
        /*Muestra la forma para simular que estÃ¡n importando las series*/
        Star.lCargGral = new LoadinGral("Importando Series...");
        Star.lCargGral.setVisible(true);

    }//GEN-LAST:event_jMInvenImpoSerActionPerformed
                                        
                                    
    /*Cuando se presiona uan tecla en el menú de exportar series de productos a excel*/
    private void jMExpoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMExpoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMExpoSerKeyPressed

    
    /*Cuandose presiona el menú de exportar series de productos a excel*/
    private void jMExpoSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExpoSerActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar Series por Producto");
                
        /*Si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        //Muestra el loading
        Star.vMostLoading("");

        /*Realiza todo esto en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Lee la ruta seleccionada*/
                String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

                /*Crea un documentovacio*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Productos");

                /*Variable contadora*/        
                int         iConta                   = 1;
                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                
                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> data = new TreeMap<>();
                data.put(Integer.toString(iConta), new Object[] {"PRODUCTO (1)", "SERIE(2)", "COMENTARIO(3)", "EXISTENCIA(4)", "ALMACEN(5)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iConta;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae todos los registros de las seris por producto*/
                try
                {
                    sQ = "SELECT prod, ser, comen, exist, alma FROM serieprod";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {                    
                        /*Agrega el registro en la fila de excel*/
                        data.put(Integer.toString(iConta), new Object[] {rs.getString("prod"), rs.getString("ser"), rs.getString("comen"), rs.getString("exist"), rs.getString("alma")});

                        /*Aumenta en uno el contador de filas del libro*/
                        ++iConta;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                                                                       
                }

                //Cierra la base de datos y regresa
                if(Star.iCierrBas(con)==-1)                                
                    return;                

                /*Agrega el terminador de fichero*/
                data.put(Integer.toString(iConta), new Object[] {"FINARCHIVO"});

                /*Itera sobre la información y escribela en la hoja*/
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset)
                {
                    Row row = sheet.createRow(rownum++);
                    Object [] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object ob : objArr)
                    {
                        Cell cell = row.createCell(cellnum++);
                        if(ob instanceof String)
                            cell.setCellValue((String)ob);
                        else if(ob instanceof Integer)
                            cell.setCellValue((Integer)ob);
                    }
                }

                /*Escribe los datos en el archivo físico*/
                try
                {                    
                    FileOutputStream out;
                    out = new FileOutputStream(new File(sRut));
                    wkbok.write(out);
                    out.close();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);
                    return;                                                                   
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en \"" + sRut + "\" con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op     = {"Si","No"};
                int iRes        = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                } 
                catch(IOException expnIO) 
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                        
                }

            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jMExpoSerActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de estadísticas de correo*/
    private void jMEstadisCorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMEstadisCorKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMEstadisCorKeyPressed

    
    /*Cuando se presiona el menú de reporte de estadísitcas de correo*/
    private void jMEstadisCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMEstadisCorActionPerformed
        
        /*Muestra la forma para ver el reporteador*/
        RepEstadiCor r = new RepEstadiCor();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMEstadisCorActionPerformed

    
    /*Cuando se presiona uan tecla en el menú de exportar catálogo de clientes a excel*/
    private void jMExpoCliensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMExpoCliensKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMExpoCliensKeyPressed

    
    /*Cuando se presiona una tecla en el menú de importar catálogo de clientes desde ecel*/
    private void jMImpCliensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMImpCliensKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMImpCliensKeyPressed

    
    /*Cuando se presiona el menú de importar catálogo de clientes desde excel*/
    private void jMImpCliensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMImpCliensActionPerformed
     /*Configura el file chooser para escoger a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Muestra el file choorse y si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
            
        /*Lee la ruta seleccionada*/
        final String sRut    = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Si no es un archivo de excel entonces*/
        if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no VÃ¡lido", JOptionPane.INFORMATION_MESSAGE, null); 
            return;
        }                
                       
        /*Muestra la forma para simular que estÃ¡n importando los clientes*/
        Star.lCargGral = new LoadinGral("Importando Clientes desde Excel...");
        Star.lCargGral.setVisible(true);
        Connection      con;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?useServerPrepStmts=true&user=" + Star.sUsuario + "&password=" + Star.sContrasenia);       
        } 
        catch(SQLException | HeadlessException e) 
        {   
            /*Esconde el loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null); 
            return;
        }

        /*Crea la instancia hacia el archivo a importar*/
        FileInputStream file;
        try
        {
            file    =  new FileInputStream(new File(sRut));
        }
        catch(Exception e)
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null); 
            return;
        }                    

        /*Instancia el objeto de excel*/
        XSSFWorkbook wkbok;
        try
        {
            wkbok   = new XSSFWorkbook(file);
        }
        catch(Exception e)
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null); 
            return;
        }                                        

        /*Obtiene la primera hoja del libro*/
        XSSFSheet sheet         = wkbok.getSheetAt(0);


        /*Declara variables de la base de datos*/
        PreparedStatement pst;
        Statement        st;                                        
        String          sQ              = ""; 
//                ResultSet       rs;                

        /*Borra todos los clientes de la base de datos*/
//        try 
//        {            
//            //borra todos los registros, y reinica el contador autoincrementable (Truncate solo es aplicable a tablas sin llaves foreaneas)
//            sQ = "Truncate table emps";                    
//            st = con.createStatement();
//            st.executeUpdate(sQ);
//         }
//         catch(SQLException | HeadlessException e) 
//         {                                
//
//
//            /*Esconde el loading*/
//            if(Star.lCargGral!=null)
//                Star.lCargGral.setVisible(false);
//
//            //Cierra la base de datos y regresa
//            if(Star.iCierrBas(con)==-1)                                
//                return;
//
//            //Agrega en el log
//            Login.vLog(e.getMessage());                        
//
//            //Mensajea y regresa
//            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
//            return;
//        }

        /*Contador de fila*/
        int iConta = 0;               

        /*Inicializa el contador de la celda por cada fila*/
        int iContCell;

        //Contador de rows de errores
        int iRows = 0;
        
        /*Recorre todas las hojas de excel*/
        Iterator<Row> rowIt     = sheet.iterator();
        while(rowIt.hasNext())
        {                    
            cliente:{
                //Reinicia el contador
                iContCell = 1;
                /*Obtiene la fila*/
                XSSFRow row = (XSSFRow)rowIt.next();

                 /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                if(iConta < 1)
                {
                    ++iConta;
                    continue;
                }

                /*Variable para leer las celdas*/
                String sIn;                   

                /*Inicializa la consulta*/
                //String sQInsert     = "INSERT INTO emps(codemp, ser, codclas, lada, tel, exten, cel, telper1, telper2, nom, calle, col, cp, ciu, estad, pai, revis, pags, rfc, descu, co1, co2, co3, pagweb1, pagweb2, contac, puest, contact2, puest2, observ, noint, noext, diacred, limtcred, metpag, cta, pers, bloq, list, zon, giro, sucu, nocaj, estac) VALUES('";                    
                String sQInsert = "INSERT INTO emps(codemp, ser, pers, nom, list, calle, col, noext, cp, ciu, estad, pai, rfc, co1, codclas,  lada, tel, exten, cel, telper1, telper2, noint, descu, diacred, limtcred, curp, co2, co3, pagweb1, pagweb2, contac, puest, contact2, puest2, observ, metpag, cta, ctaconta, banc, clavbanc, revis, pags, cumple, giro, zon, deposit, ctapred, otramon, otramonc, bloq, bloqlimcred, estac, sucu, nocaj)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                //Contiene los valores que van en el query dentro de VALUES
                java.util.ArrayList<String> valores = new java.util.ArrayList<>();

                //Uso la variable para hacer entero el list
                //Double d;

                //Uso para cadena de rfc en el matches
                String rfc = "";
                
                //Uso para tomar el tipo de celda
                int tipo;
                
                /*Recorre todas las celdas de la fila*/
                for(int i=1; i <= 51; i++) 
                {
                    /*Obtiene el objeto de la celda*/      
                    XSSFCell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        sIn = cell.getNumericCellValue()+"";  
                    else
                        sIn = cell.getStringCellValue();
                    
                    tipo = cell.getCellType();

                    //Determina la celda en la que estamos
                    switch(iContCell)
                    {
                        case 1://codigo
                            sIn = Star.checaLongitud(30, sIn.trim());
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                             if( !sIn.matches("\\d+")) 
                                 valores.add("");
                             else{
                                 //d = cell.getNumericCellValue();
                                 valores.add(sIn+""); 
                             }
                            break;
                        case 2:{//serie
                            sIn = Star.checaLongitud(30, sIn);
                            if(! sIn.matches("[a-zA-Z\\d]+,.+")){
                                if(valores.get(0).isEmpty()){
                                    //escribo el registro en una nueva pagina de excel
                                    wkbok = agregaError(row, wkbok, iRows, iContCell, "El registro no tiene codigo y tampoco cuenta con una serie valida.");
                                    iRows++;
                                     break cliente;
                                }
                                else
                                    valores.add("");
                            }
                            else{
                                /*Si la serie no existe entonces creala*/
                                vCreSerCli(con, sIn);

                                /*Si hubo error entonces resetea la bandera de error y regresa*/
                                if(bErr)
                                {
                                    bErr    = false;
                                    return;
                                }
                                /*Tokniza la cadena para obtener la serie*/
                                java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");
                                valores.add(stk.nextToken());
                            }
                            }break;
                        case 3: case 48: case 49: case 50: case 51://Persona moral
                            //le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                             if (!sIn.matches("0|1")){
                                 //pongo los valores default que tienen en la tabla de BD, correspondientes
                                 if(iContCell == 48 || iContCell == 49)
                                    valores.add("1");
                                 else
                                     valores.add("0");
                             }
                             else{
                                 valores.add(sIn);
                             }
                            break;
                        case 4: case 6: case 7: case 10: case 11: case 12: case 29: case 30: case 31: case 32: case 33: case 34: case 35: case 36: case 39://razon social,calle, colonia, ciudad, estado, pais, pagweb1, pagweb2, contac1-2,puesto1-2, observaciones, metPago
                            if(sIn.isEmpty()){
                                if( iContCell <= 12){
                                    wkbok = agregaError(row, wkbok, iRows, iContCell, "");
                                    iRows++;
                                     break cliente;
                                }
                                else
                                    valores.add("");
                            }
                            else{
                                if(iContCell == 36 )
                                    valores.add(Star.checaLongitud(45, sIn));
                                else if(iContCell == 39 )
                                    valores.add(Star.checaLongitud(30, sIn));
                                else
                                    valores.add(Star.checaLongitud(255, sIn));
                            }
                            break;
                        case 5://lista de precios
                            //le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                            
                             if( !sIn.matches("[1-9]|10")){
                                 wkbok = agregaError(row, wkbok, iRows, iContCell,"Formato de lista de precios invalida.");
                                 iRows++;
                                 break cliente;
                             } 
                                
                             else{
                                 valores.add(sIn); 
                             }
                                
                            break;
                        case 8: case 22://no ext
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                             if( !sIn.matches("[\\da-zA-Z\\- ]+")){
                                 if(iContCell == 8){
                                     wkbok = agregaError(row, wkbok, iRows, iContCell," Formato de numero exterior invalido.");
                                    iRows++;
                                     break cliente;
                                 }
                                    
                                 else
                                     valores.add("");
                             }
                             else
                                valores.add(sIn); 
                            break;
                        case 9://CP
                            //le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn.trim());
                             if( !sIn.matches("\\d{5}")){
                                wkbok = agregaError(row, wkbok, iRows, iContCell,"Formato de codigo postal invalido");
                                iRows++;
                                break cliente;
                             } 
                                 
                             else{
                                 valores.add(sIn);
                             }
                                 
                            break;
                        case 13://RFC
                            sIn = Star.checaLongitud(255, sIn.trim());
                            
                            if( !sIn.trim().matches("[a-zA-Z\\&]{4}\\d{6}.{3}") && !sIn.trim().matches("[a-zA-Z\\&]{3}\\d{6}.{3}")){
                                wkbok = agregaError(row, wkbok, iRows, iContCell,"Formato de RFC Invalido.");
                                iRows++;
                                break cliente;
                            }
                            else{//Si no existe el rfc, lo agrega
                                if( Star.iExisteRFC(con, sIn, "emps") == 0)
                                    valores.add(sIn);
                                else//si ya existe o hay algun error se brinca al siguiente cliente
                                {
                                    wkbok = agregaError(row, wkbok, iRows, iContCell,"El RFC ya se encuentra registrado en otro cliente.");
                                    iRows++;
                                    break cliente;
                                }
                                    
                            }
                             break;
                        case 14: case 27: case 28://email
                            sIn = Star.checaLongitud(100, sIn);
                            if( !sIn.matches(".+@.+")){
                                if(iContCell == 14)//si es el correo obligatorio y no cumple se brinca al siguiente cliente
                                {
                                    wkbok = agregaError(row, wkbok, iRows, iContCell, "Formato de email invalido");
                                    iRows++;
                                    break cliente;
                                }
                                else
                                    valores.add("");
                            }
                            else
                                valores.add(sIn);
                            break;
                        case 15: {// clasificacion
                                sIn = Star.checaLongitud(30, sIn);
                                if(!sIn.matches("[a-zA-Z\\d\\-]+,.+"))
                                        valores.add("");
                                else{
                                    /*Si la clasificaciÃ³n no existe entonces creala*/
                                    vCreClas(con, sIn);

                                    /*Si hubo error entonces resetea la bandera de error y regresa*/
                                    if(bErr)
                                    {
                                        bErr    = false;
                                        return;
                                    }

                                    /*Tokniza la cadena para obtener el cÃ³digo de la clasificaciÃ³n*/
                                    java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");
                                    valores.add(stk.nextToken());   
                                }
                        }break;
                        case 16: case 17: case 18: case 19: case 20: case 21://lada, telefono, extension, cel, telper1, telper2
                            switch(iContCell){
                                case 16: sIn = Star.checaLongitud(10, sIn); break;   
                                case 18: sIn = Star.checaLongitud(20, sIn); break;
                                default: sIn = Star.checaLongitud(255, sIn);
                            }
                            
                            //le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                            if(!sIn.matches("\\d+\\-?\\d+"))
                                valores.add("");
                            else
                                valores.add(sIn);
                            break;
                        case 23: case 25: case 46://descuento, limite credito, dep garantia
                            //esta validacion es porque la expresion regular si acepta vacios
                            if(sIn.isEmpty())
                                valores.add("0");
                            else{
                                if(!sIn.matches("(\\d+)?(\\.\\d{1,2})?"))
                                    valores.add("0");//le pongo el valor default
                                else{
                                    valores.add(sIn);
                                }
                            }
                            
                            break;
                         case 24: case 37: case 38: case 40: case 41: case 42: case 47://dias credito, cuenta bancaria, cuenta contable
                             switch(iContCell){
                                 case 24: sIn = Star.checaLongitud(21, sIn); break;
                                 case 37: sIn = Star.checaLongitud(45, sIn); break;
                                 case 38: sIn = Star.checaLongitud(30, sIn); break;
                                 case 40: sIn = Star.checaLongitud(255, sIn); break;
                                 case 41: case 42: case 47: sIn = Star.checaLongitud(100, sIn); break;
                             }
                             //le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                             if(!sIn.matches("\\d+")){
                                 if(iContCell == 37)
                                     valores.add("0000");
                                 else
                                     valores.add("");
                             }
                            else
                                valores.add(sIn);
                            
                            break;
                        case 26:
                            sIn = Star.checaLongitud(50, sIn);
                            if(!sIn.matches("[a-zA-Z\\d]+"))
                                valores.add("");
                            else{
                                valores.add(sIn);
                            }
                            break;
                        case 43://cumpleaÃ±os
                            sIn = Star.checaLongitud(30, sIn);
                            if(!sIn.matches("(\\d|1\\d|2\\d|3[0-1])/((0[1-9])|(1[012]))/\\d{4}"))
                                valores.add("");
                            else{
                                valores.add(sIn);
                            }
                            break;
                        case 44:{ 
                            sIn = Star.checaLongitud(30, sIn);
                            if(!sIn.matches("[a-zA-Z\\d]+"))
                                valores.add("");
                            else{
                                /*Si el giro no existe entonces creala*/
                                vCreGir(con, sIn);

                                /*Si hubo error entonces resetea la bandera de error y regresa*/
                                if(bErr)
                                {
                                    bErr    = false;
                                    return;
                                }

                                /*Tokniza la cadena para obtener el cÃ³digo de la zona*/
                                java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");

                                /*Guarda el giro y completa la consulta*/ 
                                valores.add(stk.nextToken());
                            }
                        }break;
                        case 45:
                            sIn = Star.checaLongitud(30, sIn);
                            if(!sIn.matches("[a-zA-Z\\d]+"))
                                valores.add("");
                            else{
                            /*Si la zona no existe entonces creala*/
                                vCreZon(con, sIn);

                                /*Si hubo error entonces resetea la bandera de error y regresa*/
                                if(bErr)
                                {
                                    bErr    = false;
                                    return;
                                }

                                /*Tokniza la cadena para obtener el cÃ³digo de la zona*/
                                java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");

                                /*Guarda la zona y completa la consulta*/                                    
                                valores.add(stk.nextToken()); 
                            }
                            break;   
                        default: valores.add(sIn); 
                    }


                    /*Aumenta en uno el contador de las celdas*/
                    ++iContCell;

                }/*Fin de while(cellIterator.hasNext())*/

                /*Aumenta en uno el contador de las filas*/
                ++iConta;

                /*Resetea el contador de celdas*/
                iContCell       = 1;


                //Comprueba si ya existe este cliente, concateno serie+codigo
                int iRes    = Star.iExistCliProv(con,  valores.get(1) + valores.get(0), true);

                //Si hubo error entonces regresa
                if(iRes==-1)
                    return;

                //Si el cliente existe entonces continua
                if(iRes==1)
                    continue;

                /*Inserta en la base de datos el registro*/
                try
                {
                    //(codemp, ser, nom, list, calle, col, noext, cp, ciu, estad, pai, rfc, co1, codclas, estac, sucu, nocaj)
                    pst = con.prepareStatement(sQInsert );      

                    //recorreo todo el array y agrega los valores
                    for (int i = 0; i < valores.size(); i++) {
                        switch(i){
                            case 4: 
                                //casos especiales, donde el tipo de dato es integer
                                if(!valores.get(i).isEmpty())
                                    pst.setInt(i + 1, Integer.parseInt(valores.get(i))); break;
                            case 22: case 24: case 45:
                                //casos especiales, donde el tipo de dato es flotante
                                if(!valores.get(i).isEmpty())
                                    pst.setFloat(i + 1,Float.parseFloat(valores.get(i))); break;
                            case 47: case 48: case 49: case 50:
                                //casos especiales, donde el tipo de dato es boolean
                                if(!valores.get(i).isEmpty())
                                    pst.setBoolean(i + 1, (Integer.parseInt(valores.get(i)) == (1))); break;
                            default: pst.setString(i + 1,valores.get(i));
                        }
                    }    
                    pst.setString(52,Login.sUsrG); //agrego estac 
                    pst.setString(53,Star.sSucu);  //agrego sucu
                    pst.setString(54,Star.sNoCaj); //agrego nocaj
                    
                    //Cambio el boolean a la letra correspondiente de persona moral
                    if(valores.get(2).equals("1"))
                        pst.setString(3, "M");
                    else
                        pst.setString(3, "F");
                    
                    //si no tiene codigo, mando llamar el consecutivo
                    if(valores.get(0).isEmpty()){
                        String s;
                        
                        s = regresaConsecutivo(valores.get(1),"EMP", con);
                        //Mientras exista un cliente con ese codigo+serie, pide el siguiente consecutivo
                        while(Star.iExistCliProv(con,  valores.get(1) + s, true) == 1){
                            s = regresaConsecutivo(valores.get(1),"EMP", con);
                        }
                        
                        if(s.contentEquals("-1"))//si hubo error regresa
                            return;
                        else//sino, reemplaza el condigo por el consecutivo
                            pst.setString(1, s);
                    }

                    pst.executeUpdate();
                }
                catch(Exception e)
                {                        

                    /*Esconde el loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    //Cierra la base de datos y regresa
                    if(Star.iCierrBas(con)==-1)                                
                        return;

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Llama al recolector de basura y mensajea*/
                    System.gc();                       

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "Error en fila: "  + iConta + " celda: " + iContCell + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
                    return;
                }                                                                        
            }   
        }/*Fin de while(rowIt.hasNext())*/                                    

        if(iRows > 0){
            try{
                boolean delete = new File(sRut).delete();
                FileOutputStream fileOut = new FileOutputStream(sRut);
                wkbok.write(fileOut);
                fileOut.close();
            }catch(Exception e){
            /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "Error al escribir los errores encontrados en los registros por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
            }
        }
        if(Star.lCargGral != null)
            Star.lCargGral.setVisible(false);
        
    }//GEN-LAST:event_jMImpCliensActionPerformed

    
    /*Cuando se presiona el menú de exportar el catálogo de clientes a excel*/
    private void jMExpoCliensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExpoCliensActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar catálogo de clientes");
                
        /*Si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        //Muestra el loading
        Star.vMostLoading("");
        
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Lee la ruta seleccionada*/
                String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

                /*Crea un documento vacio*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Productos");
                
                /*Variable contadora*/        
                int         iConta                   = 1;
                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                
                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> data = new TreeMap<>();
                data.put(Integer.toString(iConta), new Object[] {"COD EMPRESA(1)", "SERIE(2)", "CLASIFICACIÓN,DESCRIPCIÓN(3)", "LADA(4)", "TELEFONO(5)", "EXTENSIÓN(6)", "CELULAR(7)", "TELÉFONO PERSONAL 1(8)", "TELÉFONO PERSONAL 2(9)", "NOMBRE(10)", "CALLE(11)", "COLONIA(12)", "CP(13)", "CIUDAD(14)", "ESTADO(15)", "PAIS(16)", "REVISIÓN(17)", "PAGOS(18)", "RFC(19)", "DESCUENTO(20)", "CORREO 1(21)", "CORREO 2(22)", "CORREO 3(23)", "PAG WEB 1(24)", "PAG WEB 2(25)", "CONTACTO(26)", "PUESTO(27)", "CONTACTO 2(28)", "PUESTO 2(29)", "OBSERVACIONES(30)", "NO INTERIOR(31)", "NO EXTERIOR(32)", "DIAS CREDITO(33)", "LIMITE CREDITO(34)", "MÉTODO DE PAGO(35)", "CUENTA(36)", "PERSONA(37)", "BLOQUEADO(38)", "LISTA(39)", "ZONA,DESCRIPCIÓN(40)", "GIRO,DESCRIPCIÓN(41)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iConta;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae todos los registros de los clientes*/
                try
                {
                    sQ = "SELECT IFNULL(emps.GIRO, '' ) giro, IFNULL(emps.ZON, '' ) zon, IFNULL(giro.descrip, '' ) girdescrip, IFNULL(descrip,'') zondescrip, codemp, IFNULL(emps.SER,'') ser, IFNULL(consecs.DESCRIP,'') descrip, IFNULL(emps.CODCLAS,'') codclas, IFNULL(descrip,'') descrip, CASE WHEN lada = '' THEN '.' ELSE lada END lada, CASE WHEN tel = '' THEN '.' ELSE tel END tel, CASE WHEN exten = '' THEN '.' ELSE exten END exten, CASE WHEN cel = '' THEN '.' ELSE cel END cel, CASE WHEN telper1 = '' THEN '.' ELSE telper1 END telper1, CASE WHEN telper2 = '' THEN '.' ELSE telper2 END telper2, CASE WHEN nom = '' THEN '.' ELSE nom END nom, CASE WHEN calle = '' THEN '.' ELSE calle END calle, CASE WHEN col = '' THEN '.' ELSE col END col, CASE WHEN cp = '' THEN '.' ELSE cp END cp, CASE WHEN ciu = '' THEN '.' ELSE ciu END ciu, CASE WHEN estad = '' THEN '.' ELSE estad END estad, CASE WHEN pai = '' THEN '.' ELSE pai END pai, CASE WHEN revis = '' THEN '' ELSE revis END revis, CASE WHEN pags = '' THEN '.' ELSE pags END pags,  CASE WHEN rfc = '' THEN '.' ELSE rfc END rfc, descu, CASE WHEN co1 = '' THEN '.' ELSE co1 END co1, CASE WHEN co2 = '' THEN '.' ELSE co2 END co2, CASE WHEN co3 = '' THEN '.' ELSE co3 END co3, CASE WHEN pagweb1 = '' THEN '.' ELSE pagweb1 END pagweb1, CASE WHEN pagweb2 = '' THEN '.' ELSE pagweb2 END pagweb2, CASE WHEN contac = '' THEN '.' ELSE contac END contac, CASE WHEN puest = '' THEN '.' ELSE puest END puest, CASE WHEN contact2 = '' THEN '.' ELSE contact2 END contact2, CASE WHEN puest2 = '' THEN '.' ELSE puest2 END puest2, CASE WHEN observ = '' THEN '.' ELSE observ END observ, CASE WHEN noint = '' THEN '.' ELSE noint END noint, CASE WHEN noext = '' THEN '.' ELSE noext END noext, CASE WHEN diacred = '' THEN '.' ELSE diacred END diacred, limtcred, CASE WHEN metpag = '' THEN '.' ELSE metpag END metpag, CASE WHEN cta = '' THEN '.' ELSE cta END cta, pers, bloq, list FROM emps LEFT OUTER JOIN giro ON giro.GIR = emps.GIRO LEFT OUTER JOIN zona ON zona.COD = emps.ZON LEFT OUTER JOIN clasemp ON clasemp.COD = emps.CODCLAS LEFT OUTER JOIN consecs ON CONCAT_WS('', tip, consecs.SER) = CONCAT_WS('', 'EMP', emps.SER) ORDER BY emps.ID_ID DESC";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {                    
                        /*Si la zona es cadena vacia entonces*/
                        String sZon = rs.getString("zon");
                        if(rs.getString("zon").compareTo("")==0)
                            sZon    = "SYS,GENERICA";
                        else
                            sZon    += "," + rs.getString("zondescrip");
                        
                        /*Si el giro es cadena vacia entonces*/
                        String sGir = rs.getString("giro");
                        if(rs.getString("giro").compareTo("")==0)
                            sGir    = "SYS,GENERICA";
                        else
                            sGir    += "," + rs.getString("girdescrip");
                        
                        /*Si la serie es cadena vacia entonces*/
                        String sSer  = rs.getString("ser");
                        if(sSer.compareTo("")==0)
                            sSer     = "SYS,GENERICA";
                        else
                            sSer    += "," + rs.getString("descrip");
                        
                        /*Si la clasificación es cadena vacia entonces*/
                        String sClas = rs.getString("codclas");
                        if(sClas.compareTo("")==0)
                            sClas   = "SYS,GENERICA";
                        else
                            sClas   += "," + rs.getString("descrip");
                        
                        /*Agrega el registro en la fila de excel*/
                        data.put(Integer.toString(iConta), new Object[] {rs.getString("codemp"), sSer, sClas, rs.getString("lada"), rs.getString("tel"), rs.getString("exten"), rs.getString("cel"), rs.getString("telper1"), rs.getString("telper2"), rs.getString("nom"), rs.getString("calle"), rs.getString("col"), rs.getString("cp"), rs.getString("ciu"), rs.getString("estad"), rs.getString("pai"), rs.getString("revis"), rs.getString("pags"), rs.getString("rfc"), rs.getString("descu"), rs.getString("co1"), rs.getString("co2"), rs.getString("co3"), rs.getString("pagweb1"), rs.getString("pagweb2"), rs.getString("contac"), rs.getString("puest"), rs.getString("contact2"), rs.getString("puest2"), rs.getString("observ"), rs.getString("noint"), rs.getString("noext"), rs.getString("diacred"), rs.getString("limtcred"), rs.getString("metpag"), rs.getString("cta"), rs.getString("pers"), rs.getString("bloq"), rs.getString("list"), sZon, sGir});
                        
                        /*Aumenta en uno el contador de filas del libro*/
                        ++iConta;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                                                                       
                }

                //Cierra la base de datos y regresa
                if(Star.iCierrBas(con)==-1)                                
                    return;                

                /*Obtiene todos los datos insertados en el objeto de excel*/
                Set<String> keyset = data.keySet();
                
                /*Recorre todas las filas*/
                int rownum = 0;
                for (String key : keyset)
                {
                    /*Crea la fila*/
                    Row row = sheet.createRow(rownum++);                    
                    
                    /*Obtiene todos los datos de la fila*/
                    Object [] objArr = data.get(key);
                    
                    /*Recorre todos los datos de la fila*/
                    int cellnum = 0;
                    for(Object ob : objArr)
                    {
                        /*Obtiene el valor a insertar en la celda*/
                        String sVal = ob.toString();
                        
                        /*Si esta en la celda 0 y la fila es mayor a 1*/
                        if(cellnum==0 && rownum>1)
                        {
                            /*Si es un número entonces conviertelo a su valor absoluto*/
                            try
                            {
                                Double.parseDouble(sVal);
                                sVal    = Integer.toString((int)Double.parseDouble(sVal));                                                                
                            }
                            catch(NumberFormatException expnNumForm)
                            {
                            }                                                        
                        }                        
                                                
                        /*Crea la celda y establecele el valor*/
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue(sVal);                        
                    }
                }
                
                /*Escribe el final del fichero*/
                Row row     = sheet.createRow(rownum);                    
                Cell cell   = row.createCell(0);
                cell.setCellValue("FINARCHIVO");                        
                    
                /*Escribe los datos en el archivo físico*/
                try(FileOutputStream out    = new FileOutputStream(new File(sRut));)
                {                                        
                    wkbok.write(out);                    
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
                    return;                                                                                       
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en: " + sRut + " con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op     = {"Si","No"};
                int iRes        = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;
                
                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                }
                catch(IOException expnIO) 
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                        
                }

            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jMExpoCliensActionPerformed

    
    /*Cuando se presiona una tecla en el menú de importar catálogo de proveedores desde excel*/
    private void jMImpProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMImpProvsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMImpProvsKeyPressed

    
    /*Cuando se presiona una tecla en el menú de exportar catálogo de proveedores a excel*/
    private void jMExpoProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMExpoProvsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMExpoProvsKeyPressed

    
    /*Cuando se presiona el menú de importar catálogo de proveedores de excel*/
    private void jMImpProvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMImpProvsActionPerformed
        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario no presiono aceptar entonces regresa*/
        if(fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
                
        /*Muestra la forma para simular que esta cargando el reporte*/
        Star.lCargGral = new LoadinGral("Importando catalogo de proveedores...");
        Star.lCargGral.setVisible(true);

        /*Lee la ruta seleccionada con el nombre del archivo*/
        final String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Realiza todo esto en un thread*/
//        Thread th = new Thread()
//        {
//            @Override
//            public void run()
//            {                
                /*Abre la base de datos*/        
                Connection      con;
                try 
                {
                    con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
                } 
                catch(SQLException | HeadlessException e) 
                {   
                    /*Esconde la forma de loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                    return;
                }

                /*Crea la instancia hacia el archivo a importar*/            
                FileInputStream f;
                try
                {
                    f   = new FileInputStream(new File(sRut));
                }
                catch(Exception e)
                {
                    /*Esconde la forma de loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                    return;
                }                                

                /*Crea el objeto de excel*/                                       
                XSSFWorkbook wkbok;
                try
                {
                    wkbok = new XSSFWorkbook(f);
                }
                catch(Exception e)
                {
                    /*Esconde la forma de loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                    return;
                }                                                

                /*Obtiene la primera hoja del archivo de excel*/
                XSSFSheet sheet = wkbok.getSheetAt(0);

                /*Declara variables de la base de datos*/
                PreparedStatement pst;
                Statement       st;              
                String sQ = "";

                /*Borra todos los registros de la base de datos de los proveedores*/
//                try 
//                {            
//                    sQ = "DELETE FROM provs WHERE CONCAT_WS('', ser, prov) <> 'PROVSYS'";                    
//                    st = con.createStatement();
//                    st.executeUpdate(sQ);
//                 }
//                 catch(SQLException | HeadlessException e) 
//                 { 
//                    //Cierra la base de datos y regresa
//                    if(Star.iCierrBas(con)==-1)                                
//                        return;
//                    
//                    /*Agrega en el log*/
//                    Login.vLog(e.getMessage());
//
//                    /*Mensajea y regresa*/
//                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
//                    return;
//                 }

                /*Contador de fila y de celda respectivamente*/        
                int             iCont           = 0;
                //Inicializa el contador de la celda por cada fila
                int             iContCell;                        
                //Variable de errores 
                int iRows = 0;

                /*Recorre todas las filas una por una*/
                Iterator<Row> rowIterator = sheet.iterator();
                while(rowIterator.hasNext())
                {
                    proveedor:{
                        /*Resetea el contador de celdas*/
                        iContCell       = 1;
                        
                        /*Objeto para recorrer todas las celdas*/
                        XSSFRow row = (XSSFRow)rowIterator.next();

                        /*Si el contador no es igual a uno entonces continua ya que no quiero leer la primera fila y que continue*/
                        if(iCont < 1)
                        {
                            ++iCont;
                            continue;
                        }

                        /*Inicializa la consulta*/
                        String sQInsert = "INSERT INTO provs (prov, ser, pers, nom, calle, col, noext, cp, ciu, estad, pais, rfc, co1, codclas,  lada, tel, exten, cel, telper1, telper2, noint, descu, diacred, limcred,  co2, co3, pagweb1, pagweb2, eje1,  eje2,  observ, metpag, cta, ctaconta, banc, clavbanc, rubr, revis, pags,  giro, zon, tentre, otramon, otramonc, bloq, bloqlimcred, estac, sucu, nocaj)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        //Contiene los valores que van en el query dentro de VALUES
                        java.util.ArrayList<String> valores = new java.util.ArrayList<>();

                        /*Variable para leer las celdas*/
                        String sIn; 

                        //Uso la variable para hacer entero el list
                        Double d;

                        //variables de serie y proveedor
                        String sSer = "";

                        //Uso para tomar el tipo de celda
                        int tipo;

                        //Uso para rfc
                        String rfc;
                        
                        /*Recorre todas las celdas de la fila*/
                        for(int i=1; i <= 46; i++) 
                        {
                            /*Obtiene la primera celda*/
                            XSSFCell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                                sIn = cell.getNumericCellValue()+"";  
                            else
                                sIn = cell.getStringCellValue();

                            tipo = cell.getCellType();

                            //Determina la celda en la que estamos
                            switch(iContCell)
                            {
                                case 1://codigo
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                     if( !sIn.matches("\\d+")) 
                                         valores.add("");
                                     else{
                                         d = cell.getNumericCellValue();
                                         valores.add(d.intValue()+""); 
                                     }
                                break;
                                case 2:{//serie
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(! sIn.matches("[a-zA-Z\\d]+,.+")){
                                        if(valores.get(0).isEmpty()){
                                            wkbok = agregaError(row, wkbok, iRows, iContCell, "El registro no tiene codigo y tampoco cuenta con una serie valida.");
                                            iRows++;
                                            break proveedor;
                                        }
                                            
                                        else
                                            valores.add("");
                                    }
                                    else{
                                    /*Si la serie no existe entonces creala*/
                                        vCreSer(con, sIn);

                                        /*Si hubo error entonces resetea la bandera de error y regresa*/
                                        if(bErr)
                                        {
                                            bErr    = false;
                                            return;
                                        }

                                        /*Tokniza la cadena para obtener la serie*/
                                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");
                                        sSer = stk.nextToken();
                                        valores.add(sSer);
                                    }
                                }break;
                                case 3: case 43: case 44: case 45: case 46://Persona moral, booleanos
                                    //le quita los decimales a los numeros que pudiran haber escrito
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);

                                     if (!sIn.matches("0|1")){
                                         //pongo los valores default 
                                         if(iContCell == 43 || iContCell == 44)
                                            valores.add("1");
                                         else
                                             valores.add("0");
                                     }
                                     else{
                                         valores.add(sIn);
                                     }
                                break;
                                case 4: case 5: case 6: case 9: case 10: case 11: case 27: case 28: case 29: case 30: case 31: case 32: case 35: case 42://razon, calle, colonia, ciudad, estado, pais, pagweb1, pagweb2, eje, eje2, observ, metpag, banco, tentre
                                    if(sIn.isEmpty()){
                                        if(iContCell <= 11){
                                            wkbok = agregaError(row, wkbok, iRows, iContCell, "No puede ser vacio, ya que es un campo obligatorio.");
                                            iRows++;
                                            break proveedor;
                                        }
                                        else
                                            valores.add("");
                                    }
                                    else{
                                        if(iContCell != 35)
                                            valores.add(Star.checaLongitud(255, sIn));
                                        else//banco
                                            valores.add(Star.checaLongitud(30, sIn));
                                    }

                                break;
                                case 7: case 21://no ext, no int
                                    sIn = Star.checaLongitud(100, sIn);
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                    if( !sIn.matches("[\\da-zA-Z\\-]+")){
                                        if(iContCell == 7){
                                            wkbok = agregaError(row, wkbok, iRows, iContCell, "Formato de numero exterior invalido.");
                                            iRows++;
                                            break proveedor;
                                        }
                                        else
                                            valores.add("");
                                    }
                                    else
                                       valores.add(sIn); 
                                break;
                                case 8://CP
                                    sIn = Star.checaLongitud(20, sIn.trim());
                                    //le quita los decimales a los numeros que pudiran haber escrito
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                     if( !sIn.matches("\\d{5}")){
                                         wkbok = agregaError(row, wkbok, iRows, iContCell, "Formato invalido.");
                                        iRows++;
                                        break proveedor;
                                     } 
                                     else{
                                         valores.add(sIn);
                                     }
                                break;
                                case 12://RFC
                                    if( !sIn.trim().matches("[a-zA-Z\\&]{4}\\d{6}.{3}") && !sIn.trim().matches("[a-zA-Z\\&]{3}\\d{6}.{3}")){
                                        wkbok = agregaError(row, wkbok, iRows, iContCell,"Formato de RFC Invalido.");
                                        iRows++;
                                        break proveedor;
                                    }
                                    else{//Si no existe el rfc, lo agrega
                                        if( Star.iExisteRFC(con, sIn, "provs") == 0)
                                            valores.add(sIn);
                                        else//si ya existe o hay algun error se brinca al siguiente cliente
                                        {
                                            wkbok = agregaError(row, wkbok, iRows, iContCell,"El RFC ya se encuentra registrado en otro proveedor.");
                                            iRows++;
                                            break proveedor;
                                        }

                                    }
                                 break;
                                case 13: case 25: case 26://email
                                    sIn = Star.checaLongitud(100, sIn);
                                    if( !sIn.matches(".+@.+")){
                                        if(iContCell == 13)//si es el correo obligatorio y no cumple se brinca al siguiente cliente
                                        {
                                            wkbok = agregaError(row, wkbok, iRows, iContCell,"Formato de email invalido.");
                                            iRows++;
                                            break proveedor;   
                                        }
                                        else
                                            valores.add("");
                                    }
                                    else
                                        valores.add(sIn);
                                break;
                                case 14: 
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(!sIn.matches("[a-zA-Z\\d\\-]+,.+"))
                                            valores.add("");
                                    else{// clasificacion
                                        /*Si la clasificaciÃ³n no existe entonces creala*/
                                        vCreClasProv(con, sIn);
                                        /*Si hubo error entonces resetea la bandera de error y regresa*/
                                        if(bErr)
                                        {
                                            bErr    = false;
                                            return;
                                        }
                                        /*Tokniza la cadena para obtener el cÃ³digo de la clasificaciÃ³n*/
                                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");
                                        valores.add(stk.nextToken());   
                                }break;   
                                case 15: case 16: case 17: case 18: case 19: case 20://lada, tel, exten, cel, telper1, telper2
                                    switch(iContCell){
                                        case 15: Star.checaLongitud(10, sIn); break;
                                        case 17: Star.checaLongitud(20, sIn); break;
                                        default:Star.checaLongitud(255, sIn); 
                                    }
                                    //le quita los decimales a los numeros que pudiran haber escrito
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                    if(!sIn.matches("\\d+\\-?\\d+"))
                                        valores.add("");
                                    else
                                        valores.add(sIn);
                                    break;
                                case 22:  case 24:// valores flotantes (descu, limcred)
                                    //esta validacion es porque la expresion regular si acepta vacios
                                    if(sIn.isEmpty())
                                        valores.add("0");
                                    else{
                                        if(!sIn.matches("(\\d+)?(\\.\\d{1,2})?"))
                                            valores.add("0");//le pongo el valor default
                                        else{
                                            valores.add(sIn);
                                        }
                                    }
                                break;
                                case 23: case 33: case 36: case 38: case 39://diacred, cta, clave, revision, pagos
                                    switch(iContCell){
                                        case 23: sIn = Star.checaLongitud(21, sIn); break;
                                        case 33: sIn = Star.checaLongitud(45, sIn); break;
                                        case 36: sIn = Star.checaLongitud(255, sIn); break;
                                        default: sIn = Star.checaLongitud(100, sIn); 

                                    }
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                     if(!sIn.matches("\\d+")){
                                         if(iContCell == 33)
                                             valores.add("0000");
                                         else
                                             valores.add("");
                                     }
                                    else
                                        valores.add(sIn);

                                break;
                                case 34://ctaconta
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(tipo == 0 && !sIn.isEmpty())
                                        sIn = quitaDoble(sIn);
                                     if(!sIn.matches("[\\d\\-]+"))
                                        valores.add("");
                                    else
                                        valores.add(sIn);
                                    break;
                                case 37:
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(!sIn.matches("[a-zA-Z\\d\\-]+,.+"))
                                            valores.add("");
                                    else{// rubro
                                    /*Si el rubro no existe entonces creala*/
                                        vCreRubr(con, sIn);

                                        /*Si hubo error entonces resetea la bandera de error y regresa*/
                                        if(bErr)
                                        {
                                            bErr    = false;
                                            return;
                                        }

                                        /*Tokniza la cadena para obtener el cÃ³digo de la zona*/
                                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");

                                        /*Guarda el giro y completa la consulta*/ 
                                        valores.add(stk.nextToken());
                                }break;
                                case 40:
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(!sIn.matches("[a-zA-Z\\d\\-]+,.+"))
                                            valores.add("");
                                    else{ 
                                    /*Si el giro no existe entonces creala*/
                                        vCreGir(con, sIn);

                                        /*Si hubo error entonces resetea la bandera de error y regresa*/
                                        if(bErr)
                                        {
                                            bErr    = false;
                                            return;
                                        }

                                        /*Tokniza la cadena para obtener el cÃ³digo de la zona*/
                                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");

                                        /*Guarda el giro y completa la consulta*/ 
                                        valores.add(stk.nextToken());
                                    }break;
                                case 41:
                                    sIn = Star.checaLongitud(30, sIn);
                                    if(!sIn.matches("[a-zA-Z\\d\\-]+,.+"))
                                            valores.add("");
                                    else{
                                    /*Si la zona no existe entonces creala*/
                                        vCreZon(con, sIn);

                                        /*Si hubo error entonces resetea la bandera de error y regresa*/
                                        if(bErr)
                                        {
                                            bErr    = false;
                                            return;
                                        }

                                        /*Tokniza la cadena para obtener el cÃ³digo de la zona*/
                                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");

                                        /*Guarda la zona y completa la consulta*/                                    
                                        valores.add(stk.nextToken()); 
                                }break;
                                default: valores.add(sIn); 
                            }

                            /*Aumenta el contador para no volver a entrar aquÃ­*/
                            ++iContCell;

                    }/*Fin de while (cellIterator.hasNext())*/
                    /*Aumenta en uno el contador de las filas*/
                    ++iCont;

                    

                    ResultSet rs;
                    //Comprueba si ya existe este proveedor                    
                    try
                    {
                        sQ = "SELECT prov FROM provs WHERE CONCAT_WS('', ser, prov) = '" + sSer + valores.get(0) + "'";	                        
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces si existe y continua*/
                        if(rs.next())
                            continue;
                    }
                    catch(SQLException e)
                    {
                        //Cierra la base de datos y regresa
                        if(Star.iCierrBas(con)==-1)                                
                            return;

                        /*Agrega en el log*/
                        Login.vLog(e.getMessage());

                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null); 
                        return;
                    }

                  /*Inserta en la base de datos el registro*/
                    try
                    {
                        //(codemp, ser, nom, list, calle, col, noext, cp, ciu, estad, pai, rfc, co1, codclas, estac, sucu, nocaj)
                        pst = con.prepareStatement(sQInsert );      

                        //recorreo todo el array y agrega los valores
                        for (int i = 0; i < valores.size(); i++) {
                            switch(i){
                                case 21: case 23:
                                    //casos especiales, donde el tipo de dato es float
                                    if(!valores.get(i).isEmpty())
                                        pst.setFloat(i + 1,Float.parseFloat(valores.get(i))); break;
                                case 42: case 43: case 44: case 45:
                                    //casos especiales, donde el tipo de dato es boolean
                                    if(!valores.get(i).isEmpty())
                                        pst.setBoolean(i + 1, (Integer.parseInt(valores.get(i)) == (1))); break;
                                default: pst.setString(i + 1,valores.get(i));
                            }
                        }    
                        pst.setString(47,Login.sUsrG);  //agrego estac 
                        pst.setString(48,Star.sSucu);  //agrego sucu
                        pst.setString(49,Star.sNoCaj); //agrego nocaj

                        //Cambio el boolean a la letra correspondiente de persona moral
                        if(valores.get(2).equals("1"))
                            pst.setString(3, "M");
                        else
                            pst.setString(3, "F");

                        //si no tiene codigo, mando llamar el consecutivo
                        if(valores.get(0).isEmpty()){
                            boolean existe = false;
                            String s;
                            //mienstras existea algun otro proveedor con el mismo codigo+serie
                            do{
                                s = regresaConsecutivo(valores.get(1),"PROV", con);
                                try
                                {
                                    sQ = "SELECT prov FROM provs WHERE CONCAT_WS('', ser, prov) = '" + sSer + s + "'";	                        
                                    st = con.createStatement();
                                    rs = st.executeQuery(sQ);
                                    /*Si hay datos entonces si existe y continua*/
                                    if(rs.next())
                                        existe = true;
                                }
                                catch(SQLException e)
                                {
                                    //Cierra la base de datos y regresa
                                    if(Star.iCierrBas(con)==-1)                                
                                        return;

                                    /*Agrega en el log*/
                                    Login.vLog(e.getMessage());

                                    s = "-1";
                                }
                            }while(existe);
                                    
                            if(s.contentEquals("-1"))//si hubo error regresa
                                return;
                            else//sino, reemplaza el condigo por el consecutivo
                                pst.setString(1, s);
                        }

                            pst.executeUpdate();
                        }
                    catch(SQLException | HeadlessException e)
                    {                        

                        /*Esconde el loading*/
                        if(Star.lCargGral!=null)
                            Star.lCargGral.setVisible(false);

                        //Cierra la base de datos y regresa
                        if(Star.iCierrBas(con)==-1)                                
                            return;

                        /*Agrega en el log*/
                        Login.vLog(e.getMessage());

                        /*Llama al recolector de basura y mensajea*/
                        System.gc();                       

                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "Error en fila: "  + iCont + " celda: " + iContCell + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
                        return;
                    }    
                }
            }/*Fin de while (rowIterator.hasNext())*/
             
            if(iRows > 0){
                try{
                    boolean delete = new File(sRut).delete();
                    FileOutputStream fileOut = new FileOutputStream(sRut);
                    wkbok.write(fileOut);
                    fileOut.close();
                }catch(Exception e){
                /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "Error al escribir los errores encontrados en los registros por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
                }
            }        
            /*Esconde la forma de loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);

                /*Mensaje de Ã©xito*/
                //JOptionPane.showMessageDialog(null, "Termino importaciÃ³n desde \"" + sRut + "\".", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

//            }/*Fin de public void run()*/
//        };
//        th.start();  
        
    }//GEN-LAST:event_jMImpProvsActionPerformed

    
    /*Cuando se presiona el botón de exportar proveedores a excel*/
    private void jMExpoProvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExpoProvsActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar proveedores");

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        /*Lee la ruta seleccionada*/
        final String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

        //Muestra el loading
        Star.vMostLoading("");

        /*Haz todo el proceso en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Crea el objeto de excel*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Proveedores");
                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(false, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                    
                //Declara variables locales        
                int         iCont           = 1;

                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> d = new TreeMap<>();
                    d.put(Integer.toString(iCont), new Object[] {"COD PROVEEDOR (1)", "NOMBRE(2)", "SERIE Y DESCRIPCIÓN(3)", "CLASIFICACION Y DESCRIPCIÓN(4)", "LADA(5)", "TELEFONO(6)", "EXTENSIÓN(7)", "CELULAR(8)", "CALLE(9)", "COLONIA(10)", "CP(11)", "NO INTERIOR(12)", "NO EXTERIOR(13)", "CIUDAD(14)", "ESTADO(15)", "PAIS(16)", "RFC(17)", "REVISIÓN(18)",  "PAGOS(19)", "DESCUENTO(20)", "DIAS CREDITO(21)", "LIMITE CREDITO(22)", "CORREO 1(23)", "CORREO 2(24)", "CORREO 3(25)", "PAG WEB 1(26)", "PAG WEB 2(27)", "EJECUTIVO 1(28)","TEL PERSONAL 1(29)","TEL PERSONAL 2(30)","EJECUTIVO 2(31)", "TEL PERSONAL 1(32)", "TEL PERSONAL 2(33)", "OBSERVACIONES(34)", "PERSONA(35)", "BLOQUEADO(36)", "ZONA,DESCRIPCIÓN(37)", "GIRO,DESCRIPCIÓN(38)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iCont;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae todos los registros de los proveedores*/
                try
                {
                    sQ = "SELECT IFNULL(provs.GIRO,'') giro, IFNULL(provs.ZON,'') zon, IFNULL(zona.DESCRIP,'') zondescrip, IFNULL(giro.descrip,'') girdescrip, prov, nom,  IFNULL(consecs.DESCRIP,'') descrip, IFNULL(provs.SER,'') ser, IFNULL(provs.CODCLAS,'') codclas, IFNULL(clasprov.DESCRIP,'') descrip, lada, tel, exten, cel, calle, col, cp, noint, noext, ciu, estad, pais, rfc, revis, pags, descu, diacred, limcred, co1, co2, co3, pagweb1, pagweb2, eje1, telper1, telper2, eje2, telper21, telper22, observ, pers, bloq FROM provs LEFT OUTER JOIN giro ON giro.GIR = provs.GIRO LEFT OUTER JOIN zona ON zona.COD = provs.ZON LEFT OUTER JOIN clasprov ON provs.CODCLAS = clasprov.COD LEFT OUTER JOIN consecs ON CONCAT_WS('', consecs.TIP, consecs.SER)  = CONCAT_WS('', consecs.TIP, provs.SER)";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {
                        /*Si la zona es cadena vacia entonces*/
                        String sZon = rs.getString("zon");
                        if(sZon.compareTo("")==0)
                            sZon    = "SYS,GENERICA";
                        else
                            sZon    += "," + rs.getString("zondescrip");
                        
                        /*Si el giro es cadena vacia entonces*/
                        String sGir = rs.getString("giro");
                        if(sGir.compareTo("")==0)
                            sGir    = "SYS,GENERICA";
                        else
                            sGir    += "," + rs.getString("girdescrip");
                        
                        /*Si la clasificación es cadena vacia entonces*/
                        String sClas= rs.getString("codclas");
                        if(sClas.compareTo("")==0)
                            sClas    = "SYS,GENERICA";
                        else
                            sClas    += "," + rs.getString("descrip");
                        
                        /*Si la serie es cadena vacia entonces*/
                        String sSer  = rs.getString("ser");
                        if(sSer.compareTo("")==0)
                            sSer     = "SYS,GENERICA";
                        else
                            sSer    += "," + rs.getString("descrip");
                        
                        /*Agrega el registro en la fila de excel*/
                        d.put(Integer.toString(iCont), new Object[] {rs.getString("prov"), rs.getString("nom"), sSer, sClas, rs.getString("lada"), rs.getString("tel"),rs.getString("exten"), rs.getString("cel"), rs.getString("calle"), rs.getString("col"), rs.getString("cp"), rs.getString("noint"), rs.getString("noext"), rs.getString("ciu"), rs.getString("estad"), rs.getString("pais"), rs.getString("rfc"), rs.getString("revis"), rs.getString("pags"),rs.getString("descu"), rs.getString("diacred"), rs.getString("limcred"), rs.getString("co1"), rs.getString("co2"), rs.getString("co3"), rs.getString("pagweb1"), rs.getString("pagweb2"), rs.getString("eje1"), rs.getString("telper1"), rs.getString("telper2"), rs.getString("eje2"), rs.getString("telper21"), rs.getString("telper22"), rs.getString("observ"),  rs.getString("pers"), rs.getString("bloq"), sZon,sGir });

                        /*Aumenta en uno el contador de filas del libro*/
                        ++iCont;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                
                }

                //Cierra la base de datos y regresa
                if(Star.iCierrBas(con)==-1)                                
                    return;                                    

                /*Obtiene todos los datos insertados en el objeto de excel*/
                Set<String> keyset = d.keySet();
                
                /*Recorre todas las filas*/
                int rownum = 0;
                for (String key : keyset)
                {
                    /*Crea la fila*/
                    Row row = sheet.createRow(rownum++);                    
                    
                    /*Obtiene todos los datos de la fila*/
                    Object [] objArr = d.get(key);
                    
                    /*Recorre todos los datos de la fila*/
                    int cellnum = 0;
                    for(Object ob : objArr)
                    {
                        /*Obtiene el valor a insertar en la celda*/
                        String sVal = ob.toString();
                        
                        /*Si el valor es cadena vacia entonces que sea un .*/
                        if(sVal.compareTo("")==0)
                            sVal    = ".";
                        
                        /*Si esta en la celda 0 y la fila es mayor a 1*/
                        if(cellnum==0 && rownum>1)
                        {
                            /*Si es un número entonces conviertelo a su valor absoluto*/
                            try
                            {
                                Double.parseDouble(sVal);
                                sVal    = Integer.toString((int)Double.parseDouble(sVal));                                                                
                            }
                            catch(NumberFormatException expnNumForm)
                            {
                            }                                                        
                        }                        
                                                
                        /*Crea la celda y establecele el valor*/
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue(sVal);                        
                    }
                }
                
                /*Escribe el final del fichero*/
                Row row     = sheet.createRow(rownum);                    
                Cell cell   = row.createCell(0);
                cell.setCellValue("FINARCHIVO");                        
                                
                /*Escribe los datos en el archivo*/
                try
                {
                    /*Escribe el archivo en el sistema de archivos local*/
                    FileOutputStream out;
                    out = new FileOutputStream(new File(sRut));
                    wkbok.write(out);
                    out.close();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                    
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en: " + sRut + " con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op = {"Si","No"};
                int iRes    = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                } 
                catch(IOException expnIO) 
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                                               
                }

            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jMExpoProvsActionPerformed

    
    /*Cuando se presiona una tecla en el menú de asociaciones de precios por producto y cliente*/
    private void jMAsocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMAsocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMAsocKeyPressed

    
    /*Cuando se presiona el menú de asociaciones de productos por cliente*/
    private void jMAsocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMAsocActionPerformed
        
        /*Muestra la forma de asociaciones de productos por cliente*/
        cats.AsocPrec a = new cats.AsocPrec();       
        a.setVisible(true);
        
    }//GEN-LAST:event_jMAsocActionPerformed

    
    /*Cuando se presiona una tecla en el menú del catálog de zonas*/
    private void jMZonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMZonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMZonKeyPressed

    
    /*Cuando se presiona una tecla en el menú de catálogo de giros*/
    private void jMGirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMGirKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMGirKeyPressed

    
    /*Cuando se presiona el menú de ver catálogo de zonas*/
    private void jMZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMZonActionPerformed
        
        /*Muestra la forma para ver el catálogo de las zonas*/
        cats.PlanGralCat l = new cats.PlanGralCat("zona","emps","zon","zona","zona","cod");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMZonActionPerformed

    
    /*Cuando se presiona el menú de ver catálogo de giros*/
    private void jMGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMGirActionPerformed
        
        /*Muestra la forma para ver el catálogo de las zonas*/
        cats.PlanGralCat l = new cats.PlanGralCat("giro","emps","giro","concepto de giro","giro","gir");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMGirActionPerformed

    
    /*Cuando se presiona una tecla en el menú padre de contabilidad*/
    private void jMContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMContaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMContaKeyPressed
    /*Cuando se presiona una tecla en el menú padre de interfaces*/
    private void jMenu4KeyPressed(java.awt.event.KeyEvent evt) {                                  
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }                                 

    
    /*Cuando se presiona el menú de interfaz de macro pro*/
    private void jMMacroPActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        /*Muestra la forma para hacer la interfaz entre easy reatail y macropro*/
        InterMac240ThinCli i = new InterMac240ThinCli();
        i.setVisible(true);
        
    }                                        


    
    
    
    /*Cuando se presiona una tecla en el menú de easy converter*/
    private void jMEasConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMEasConKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMEasConKeyPressed

    
    /*Cuando se presiona el menú de easy converter*/
    private void jMEasConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMEasConActionPerformed
        
        /*Muestra la forma para hacer las conversiones*/
        EasyConv e = new EasyConv();
        e.setVisible(true);
        
    }//GEN-LAST:event_jMEasConActionPerformed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones de rubros*/
    private void jMRubrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRubrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRubrKeyPressed

    
    /*Cuando se presiona el menú de clasificaciones de rubro*/
    private void jMRubrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRubrActionPerformed
        
        /*Muestra la forma de clasificaciones de rubro*/
        cats.Rubr r = new cats.Rubr();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRubrActionPerformed

    
    /*Cuando se presiona una tecla en el menú de catálogo de garantías*/
    private void jMCatGaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCatGaraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCatGaraKeyPressed

    
    /*Cuando se presiona el menú de catálogo de garantías*/
    private void jMCatGaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCatGaraActionPerformed
        
        /*Muestra la forma para ver el catálogo de garantías*/
        cats.PlanGralCat l = new cats.PlanGralCat("garan","prods","garan","garantia","Gara","gara");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMCatGaraActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de clientes*/
    private void jMRepCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepCliKeyPressed

    
    /*Cuando se presiona el menú de ver reporte de clientes*/
    private void jMRepCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepCliActionPerformed
        
        /*Muestra la forma para ver el reporte de clientes*/
        report.RepCliens r = new report.RepCliens();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepCliActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reportear proveedores*/
    private void jMRepProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepProvKeyPressed

    
    /*Cuando se presioan el menú de reportear proveedores*/
    private void jMRepProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepProvActionPerformed
        
        /*Muestra la forma para reportear a los proveedores*/
        report.RepProvs r = new report.RepProvs();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepProvActionPerformed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones jerárquicas de clientes*/
    private void jMClasJerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMClasJerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMClasJerKeyPressed

    
    /*Cuando se presiona el menú de clasificaciones jerárquicas de clientes*/
    private void jMClasJerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMClasJerActionPerformed
        
        /*Muestra la forma de las clasificaciones de los clientes*/
        cats.ClasJeraGral c = new cats.ClasJeraGral("clasjeracli","emps","clientes");
        c.setVisible(true);
        
    }//GEN-LAST:event_jMClasJerActionPerformed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones jerárquicas de proveedores*/
    private void jMClasJerPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMClasJerPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMClasJerPKeyPressed

    
    /*Cuando se presiona el botón de clasificaciones jerárquicas*/
    private void jMClasJerPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMClasJerPActionPerformed
        
        /*Muestra la forma para clasificaciones de proveedores*/
        cats.ClasJeraGral c = new cats.ClasJeraGral("clasjeraprov","provs","proveedores");
        c.setVisible(true);
        
    }//GEN-LAST:event_jMClasJerPActionPerformed

    
    /*Cuando se presiona una tecla en el menú de flujo de actividades*/
    private void jMFlujActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMFlujActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMFlujActKeyPressed

    
    /*Cuando se presiona el menú de flujo de actividades*/
    private void jMFlujActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMFlujActActionPerformed
        
        /*Muestra la forma de flujo de actividades*/
        FlujAct f = new FlujAct();        
        f.setVisible(true);
        
    }//GEN-LAST:event_jMFlujActActionPerformed

    
    /*Cuando se presiona una tecla en el menú de utilerías*/
    private void jMMUtilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMMUtilKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMMUtilKeyPressed

    
    /*Cuando se presiona una tecla en el menú de revocación*/
    private void jMRevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRevoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRevoKeyPressed

    
    /*Cuando se presiona el menú de revocación de sistema*/
    private void jMRevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRevoActionPerformed
        
        /*Preguntar al usuario si esta seguro de querer revocar el sistema*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres revocar el sistema?", "Revocación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Si el archivo de validación del sistema no existe entonces*/
        if(!new File(Star.sArchVal).exists())                       
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema no cuenta con un serial. Podría tener problemas para validarlo o en un futuro.", "Serial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;        
        }
        
        /*Desserializamos el archivo de validación del sistema*/
        SerVali serVal;
        try(FileInputStream fileIn   = new FileInputStream(Star.sArchVal); ObjectInputStream in     = new ObjectInputStream(fileIn))
        {                      
           serVal                   = (SerVali)in.readObject();                      
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
            return;                        
        } 
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF, expnClassNoF.getStackTrace());                                                       
            return;
        }
        
        /*Contiene la MAC del equipo*/
        String sMAC;

        /*Obtiene la MAC del equipo*/
        InetAddress ip;
        try 
        {
            /*Obtiene la ip local del equipo*/
            ip = InetAddress.getLocalHost();                

            /*Obtiene otros parámetros de conexión*/
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            /*Obtiene en bytes la MAC*/
            byte[] mac = network.getHardwareAddress();

            /*Dale formato a la MAC*/
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mac.length; i++)                 
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));                                        

            /*Inicia la MAC*/
            sMAC    = sb.toString();
        }
        catch(UnknownHostException expnUnknowHos) 
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnUnknowHos.getMessage(), Star.sErrUnknowHos, expnUnknowHos.getStackTrace());                                                       
            return;
        }
        catch(SocketException expnSock)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSock.getMessage(), Star.sErrSock, expnSock.getStackTrace());                                                       
            return;
        }

        /*Si la MAC del equipo no es la misma que la del archivo de validación entonces*/
        if(sMAC.replace("-", "").compareTo(Star.sDecryp(serVal.sMac).replace("-", ""))!=0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema esta incorrectamente validado y no puede continuar.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;            
        }
        
        /*Obtiene la llave del sistema*/
        String sKey     = Star.sDecryp(serVal.sKey);
                
        /*Si la llave esta vacia entonces*/
        if(sKey.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se tiene una llave valida y no se puede revocar el sistema.", "Revocación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Obtiene la serie*/
        String sSer     = Star.sDecryp(serVal.sSer);
        
        /*Si la seire esta vacia entonces*/
        if(sSer.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se tiene una serie valida y no se puede revocar el sistema.", "Revocación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Comprueba con el web service si los datos introducidos por el usuario son válidos*/
        String sResp;
        try
        {
            sResp    = Star.vRevoSis(Star.sEncyMy(sSer + Star.sCadVerif + Star.sNomProd), Star.sEncyMy(Star.sEncyMy(sKey)), Star.sEncyMy(sMAC));
        }
        catch(Exception expnWSPAC)
        {                
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, expnWSPAC.getStackTrace());                                                       
            return;
        }                                                    
    
        /*Desencripta el resultado*/
        sResp   = Star.sDencyMy(sResp);        
        JOptionPane.showMessageDialog(null, sResp, "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        /*Si hubo error entonces*/
        if(sResp.contains("<ERROR>"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Error del servidor: " + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Borra el archivo de validación*/
        new File(Star.sArchVal).delete();        
        
        /*Muestra el código de revocación que el servidor regreso*/                
        JOptionPane.showInputDialog("Guarda el código de revocación que se te pedirá posteriormente para poder hacer nuevamente la instalación del sistema:", sResp);
                
    }//GEN-LAST:event_jMRevoActionPerformed

    
    /*Cuando se presiona una tecla en el menú de activar sistema*/
    private void jMActSisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMActSisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMActSisKeyPressed

    
    /*Cuando se presiona el menú de activar sistema*/
    private void jMActSisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMActSisActionPerformed
        
        /*Si el archivo de validación del sistema no existe entonces*/
        if(!new File(Star.sArchVal).exists())                       
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema no cuenta con un serial. Podría tener problemas para validarlo o en un futuro.", "Serial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;        
        }
        
        /*Desserializamos el archivo de validación del sistema*/
        SerVali serVal;
        try(FileInputStream fileIn   = new FileInputStream(Star.sArchVal); ObjectInputStream in     = new ObjectInputStream(fileIn);)
        {           
           serVal                   = (SerVali)in.readObject();           
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
            return;
        } 
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF, expnClassNoF.getStackTrace());                                                       
            return;
        }
        
        /*Contiene la MAC del equipo*/
        String sMAC;

        /*Obtiene la MAC del equipo*/
        InetAddress ip;
        try 
        {
            /*Obtiene la ip local del equipo*/
            ip = InetAddress.getLocalHost();                

            /*Obtiene otros parámetros de conexión*/
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            /*Obtiene en bytes la MAC*/
            byte[] mac = network.getHardwareAddress();

            /*Dale formato a la MAC*/
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mac.length; i++)                 
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));                                        

            /*Inicia la MAC*/
            sMAC    = sb.toString();
        }
        catch(UnknownHostException expnUnknowHos) 
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnUnknowHos.getMessage(), Star.sErrUnknowHos, expnUnknowHos.getStackTrace());                                                       
            return;
        }
        catch(SocketException expnSock)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSock.getMessage(), Star.sErrSock, expnSock.getStackTrace());                                                       
            return;
        }

        /*Si la MAC del equipo no es la misma que la del archivo de validación entonces*/
        if(sMAC.replace("-", "").compareTo(Star.sDecryp(serVal.sMac).replace("-", ""))!=0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema esta incorrectamente validado y no puede continuar.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;            
        }
        
        //Si el sistema ya esta validado entonces
        if(Star.sDecryp(serVal.sKey).compareTo("")!=0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema ya esta activado.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;            
        }
        
        /*Obtiene la serie*/
        String sSer     = Star.sDecryp(serVal.sSer);
        
        /*Contiene el número de intentos válidos*/
        int iCont   = 0;
        
        /*Mientras no se pueda salir del bucle entonces*/
        boolean bSa = false;
        do
        {
            /*Si el número de intentos ya llego a su límite entonces*/
            if(iCont>=2)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El número de intentos válidos a expirado.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Pidele al usuario que ingrese su llave o código de revocación*/                
            String sKey    = JOptionPane.showInputDialog("Serial: " + sSer + ".Ingresa tu llave o código de revocación:");

            /*Si es nulo entonces regresa*/
            if(sKey==null)               
                return;                

            /*Si es cadena vacia entonces*/
            if(sKey.compareTo("")==0)
            {                    
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una llave o código de revocación válido.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon la bandera en falso y continua*/
                bSa    = false;
                continue;
            }
            
            /*Comprueba con el web service si los datos introducidos por el usuario son válidos para activación o revocación*/
            String sResp;
            try
            {                   
                sResp    = Star.vSerKeyU(Star.sEncyMy(sSer + Star.sCadVerif + Star.sNomProd), Star.sEncyMy(Star.sEncyMy(sKey)), Star.sEncyMy(sMAC.replace("-", "")));                                                                                                
            }
            catch(Exception expnWSPAC)
            {                
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, expnWSPAC.getStackTrace());                                                       
                return;
            }                                                    
            
            /*Desencripta la respuesta del servidor*/
            sResp   = Star.sDencyMy(sResp);
            
            /*Si la respuesta fue negativa entonces*/
            if(sResp.contains("<ERROR>"))
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Error del servidor." + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Aumenta en uno el contador de intentos y continua*/
                ++iCont;
                continue;
            }

            /*Si la respuesta fue negativo entonces*/
            if(sResp.compareTo("0")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Datos de validación incorrectos.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Aumenta en uno el contador de intentos y continua*/
                ++iCont;
                continue;
            }
                                    
            /*Obtiene los datos faltantes del archivo*/            
            String sFech        = Star.sDecryp(serVal.sFech);
            String sCont        = Star.sDecryp(serVal.sCont);               
            
            /*Borra el archivo de validación*/
            new File(Star.sArchVal).delete();

            /*Instancia la clase de validación para serializarla*/                       
            serVal.sSer         = Star.sEncrip(sSer); 
            serVal.sMac         = Star.sEncrip(sMAC); 
            serVal.sFech        = Star.sEncrip(sFech);
            serVal.sCont        = Star.sEncrip(sCont);
            serVal.sKey         = Star.sEncrip(sKey);

            /*Serializa el objeto de validación en un archivo*/
            try(FileOutputStream fi     = new FileOutputStream(Star.sArchVal); ObjectOutputStream out  = new ObjectOutputStream(fi);)
            {                
                out.writeObject(serVal);                
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                return;
            }   

            /*Poner la bandera en true*/
            bSa        = true;                    
        }
        while(!bSa);                                   

        /*Mensajea de éxito en la revocación*/
        JOptionPane.showMessageDialog(null, "Sistema activado correctamente.", "Activación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jMActSisActionPerformed

    
    /*Cuando se presiona una tecla en el menú de noticias y avisas*/
    private void jMNoticKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMNoticKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMNoticKeyPressed

    
    /*Cuando se presiona el mnú de avisos y noticias*/
    private void jMNoticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMNoticActionPerformed
        
        /*Muestra la forma para ver los avisos y noticias*/
        AvisNot a = new AvisNot();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMNoticActionPerformed

    
    /*Cuando se presiona una tecla en el menú de foro de ayuda en línea*/
    private void jMForLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMForLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMForLinKeyPressed

    
    /*Cuando se presiona el menú de foro en línea*/
    private void jMForLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMForLinActionPerformed
        
        /*Muestra la forma de foro en línea*/
        ForLin f = new ForLin();
        f.setVisible(true);
        
    }//GEN-LAST:event_jMForLinActionPerformed

    
    /*Cuando se presiona una tecla en el menú de clasificaciones jerárquicas de productos*/
    private void jMJeraProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMJeraProdKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMJeraProdKeyPressed

    
    /*Cuando se presiona el menú de clasificaciones jerarquicas de productos*/
    private void jMJeraProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMJeraProdActionPerformed
        
        /*Muestra la forma para clasificaciones de los productos*/
        cats.ClasJeraGral c = new cats.ClasJeraGral("clasjeraprod","prods","productos");
        c.setVisible(true);
        
    }//GEN-LAST:event_jMJeraProdActionPerformed

    
    /*Cuando se presiona una tecla en el menú de conceptos de notas de crédito*/
    private void jMConcepNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMConcepNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMConcepNotKeyPressed

    
    /*Cuando se presiona el menú de conceptos de notas de crédito*/
    private void jMConcepNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMConcepNotActionPerformed
        
        /*Muestra la forma para ver los conceptos de notas de crédito*/
        cats.PlanGralCat l = new cats.PlanGralCat("concepnot","comprs","concep","concepto de notas de crédito","conCnot","concep");
	l.setVisible(true);
        
    }//GEN-LAST:event_jMConcepNotActionPerformed

    
    /*Cuando se presiona una tecla en el menú de importar existencias por almacén desde excel*/
    private void jMImpExistAlmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMImpExistAlmKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMImpExistAlmKeyPressed

    
    /*Cuando se presiona una tecla en el menú de exportar existencias por almacén a excel*/
    private void jMExpExisAlmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMExpExisAlmKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMExpExisAlmKeyPressed

    
    /*Cuando se presiona el menú de importar existencias por almacén*/
    private void jMImpExistAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMImpExistAlmActionPerformed
        
        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario n presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
            
        /*Lee la ruta seleccionada*/
        final String sRut    = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Si no es un archivo de excel entonces*/
        if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no válido", JOptionPane.INFORMATION_MESSAGE, null); 
            return;
        }                

        /*Realiza todo en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {               
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(false, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                
                /*Crea la instancia hacia el archivo a importar*/
                FileInputStream file;
                try
                {
                    file    =  new FileInputStream(new File(sRut));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;
                }                    

                /*Instancia el objeto de excel*/
                XSSFWorkbook wkbok;
                try
                {
                    wkbok   = new XSSFWorkbook(file);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;
                }                                        

                /*Obtiene la primera hoja del libro*/
                XSSFSheet sheet         = wkbok.getSheetAt(0);
                               
                //Inicia la transacción
                if(Star.iIniTransCon(con)==-1)
                    return;                
                
                //Declara variables de la base de datos
                Statement       st;                                        
                String          sQ;                                 
                
                /*Borra todos los registros de las existencias por almacén*/
                try 
                {            
                    sQ = "DELETE FROM existalma";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                    
                }
            
                /*Contador de fila*/
                int iConta                  = 1;               
            
                /*Inicializa el contador de la celda por cada fila*/
                int iContCell               = 1;

                /*Recorre todas las hojas de excel*/
                Iterator<Row> rowIt     = sheet.iterator();
                while(rowIt.hasNext())
                {                    
                    /*Recorre todas columnas del archivo*/
                    Row row             = rowIt.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                    if(iConta == 1)
                    {
                        ++iConta;
                        continue;
                    }

                    /*Variable para leer las celdas*/
                    String sIn;                   

                    /*Inicializa la consulta*/
                    String sQInsert    = "INSERT INTO existalma(prod, alma, exist, sucu, nocaj, estac) VALUES('";                    
                
                    /*Contiene el código del producto y el almacén*/
                    String sProd        = "";
                    String sAlma        = "";
                    
                    /*Recorre todas las celdas de la fila*/
                    while(cellIterator.hasNext())
                    {
                        /*Obtiene el objeto de la celda*/
                        Cell cell       = cellIterator.next();                 

                        /*Determina el tipo de celda que es*/
                        switch(cell.getCellType())
                        {
                            /*En caso de que sea de tipo string entonces*/
                            case Cell.CELL_TYPE_STRING:
                                
                                /*Obtiene el valor de la celda*/
                                sIn         = cell.getStringCellValue();                                                            
                            
                                /*Si es la celda 1 entonces*/
                                if(iContCell==1)
                                {
                                    /*Si es el fin del archivo entonces*/
                                    if(sIn.compareTo("FINARCHIVO")==0)
                                    {
                                        //Esconde la forma de loading
                                        Star.vOcultLoadin();

                                        //Termina la transacción
                                        if(Star.iTermTransCon(con)==-1)
                                            return;
                                        
                                        //Cierra la base de datos y regresa
                                        if(Star.iCierrBas(con)==-1)                                
                                            return;
                                        
                                        /*Mensajea y regresa*/
                                        JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " productos para existencia.", "Existencias productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                        return;
                                    }
                                    /*Else no es el final del archivo entonces*/
                                    else
                                    {                                                
                                        /*Guarda el código del producto y completa la consulta*/
                                        sProd       = cell.getStringCellValue().replace("|", "");
                                        sQInsert   += sProd + "','";
                                        
                                        //Muestra el loading
                                        Star.vMostLoading("");
                                    }
                                    
                                }/*Fin de if(iContCell==1)*/                                    
                                /*Else if: Almacén*/
                                else if(iContCell==2)                                                                                                       
                                {
                                    /*Obtiene el almacén*/
                                    sAlma       = cell.getStringCellValue();
                                    sQInsert   += sAlma + "','";                               
                                }
                                /*Else if: Existencia*/
                                else if(iContCell==3)                                                                    
                                    sQInsert   += cell.getStringCellValue() + "','";
                                
                                break;

                                /*En caso de que sea de tipo número entonces*/
                                case Cell.CELL_TYPE_NUMERIC:
                                    
                                    /*Si va en la celda de la existencia entonces*/
                                    if(iContCell==3)
                                    {
                                        /*Quita la última coma y completa la consulta*/
                                        sQInsert = sQInsert.substring(0, sQInsert.length() - 1);
                                        sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                    
                                    }
                                            
                                break;
                                
                        }/*Fin de switch(cell.getCellType())*/

                        /*Aumenta en uno el contador de las celdas*/
                        ++iContCell;
                        
                    }/*Fin de while(cellIterator.hasNext())*/

                    /*Aumenta en uno el contador de las filas*/
                    ++iConta;
                    
                    /*Resetea el contador de celdas*/
                    iContCell   = 1;
                            
                    /*Quita los últimos carácteres inválidos*/
                    sQInsert        = sQInsert.substring(0, sQInsert.length() - 2);

                    /*Agrega el terminador de la consulta*/
                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "')";
                    
                    /*Checa si el código del almacén ya existe en la base de datos*/        
                    int iRes    = Star.iExistAlma(con, sAlma.trim());

                    //Si hubo error entonces regresa
                    if(iRes==-1)
                        return;

                    //Si no existe entonces
                    if(iRes==0)
                    {
                        //Cierra la base de datos y regresa
                        if(Star.iCierrBas(con)==-1)                                
                            return;

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "El almacén: " + sAlma + " no existe y no se puede continuar.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                        return;                                                                                
                    }
                    
                    /*Inserta en la base de datos el registro*/
                    try
                    {
                        st = con.createStatement();                        
                        st.executeUpdate(sQInsert);
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                        
                    }                                                                        
                
                    /*Actualiza la existencia general de ese producto*/
                    if(Star.iCalcGralExis(con, sProd)==-1)
                        return;
                                        
                }/*Fin de while(rowIt.hasNext())*/                                    
                
            }/*Fin de public void run()*/
            
        }).start();
                
        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jMImpExistAlmActionPerformed

    
    /*Cuando se presiona el menú de exportar existencias por almacén a excel*/
    private void jMExpExisAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExpExisAlmActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar existencias");
                
        /*Si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        //Muestra el loading
        Star.vMostLoading("");

        /*Realiza todo esto en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Lee la ruta seleccionada*/
                String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

                /*Crea un documentovacio*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Existencias");

                /*Variable contadora*/        
                int         iConta                   = 1;
                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                    
                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> data = new TreeMap<>();
                data.put(Integer.toString(iConta), new Object[] {"PRODUCTO(1)", "ALMACEN(2)", "EXISTENCIA(3)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iConta;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae todos los registros de las existencias por almacén*/
                try
                {
                    sQ = "SELECT prod, alma, exist FROM existalma";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {                    
                        /*Agrega el registro en la fila de excel*/
                        data.put(Integer.toString(iConta), new Object[] {rs.getString("prod"), rs.getString("alma"), rs.getString("exist")});

                        /*Aumenta en uno el contador de filas del libro*/
                        ++iConta;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                    
                }

                //Cierra la base de datos y regresa
                if(Star.iCierrBas(con)==-1)                                
                    return;                                    

                /*Agrega el terminador de fichero*/
                data.put(Integer.toString(iConta), new Object[] {"FINARCHIVO"});

                /*Itera sobre la información y escribela en la hoja*/
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset)
                {
                    Row row = sheet.createRow(rownum++);
                    Object [] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object ob : objArr)
                    {
                        Cell cell = row.createCell(cellnum++);
                        if(ob instanceof String)
                        cell.setCellValue((String)ob);
                        else if(ob instanceof Integer)
                        cell.setCellValue((Integer)ob);
                    }
                }

                /*Escribe los datos en el archivo físico*/
                try
                {                    
                    FileOutputStream out;
                    out = new FileOutputStream(new File(sRut));
                    wkbok.write(out);
                    out.close();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                    
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en: " + sRut + " con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op     = {"Si","No"};
                int iRes        = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                } 
                catch(IOException expnIO) 
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                           
                }

            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jMExpExisAlmActionPerformed

    
    /*Cuando se presiona una tecla en el menú de exportar almacenes a excel*/
    private void jMExpAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMExpAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMExpAlmaKeyPressed

    
    /*Cuando se presiona una tecla en el menú de importar almacenes*/
    private void jMImpAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMImpAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMImpAlmaKeyPressed

    
    /*Cuando se presiona el menú de importar almacenes desde excel*/
    private void jMImpAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMImpAlmaActionPerformed
        
        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario n presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
            
        /*Lee la ruta seleccionada*/
        final String sRut    = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName();

        /*Si no es un archivo de excel entonces*/
        if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no válido", JOptionPane.INFORMATION_MESSAGE, null); 
            return;
        }                

        /*Realiza todo en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(false, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                
                /*Crea la instancia hacia el archivo a importar*/
                FileInputStream file;
                try
                {
                    file    =  new FileInputStream(new File(sRut));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;
                }                    

                /*Instancia el objeto de excel*/
                XSSFWorkbook wkbok;
                try
                {
                    wkbok   = new XSSFWorkbook(file);
                }
                catch(Exception expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;
                }                                        

                /*Obtiene la primera hoja del libro*/
                XSSFSheet sheet         = wkbok.getSheetAt(0);
                               
                //Inicia la transacción
                if(Star.iIniTransCon(con)==-1)
                    return;                
                
                //Declara variables de la base de datos
                Statement       st;                                        
                String          sQ;                                 
                
                /*Borra todos los almacenes*/
                try 
                {            
                    sQ = "DELETE FROM almas";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 {                                         
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                    
                }
            
                /*Contador de fila*/
                int iConta                  = 1;               
            
                /*Inicializa el contador de la celda por cada fila*/
                int iContCell               = 1;

                /*Recorre todas las hojas de excel*/
                Iterator<Row> rowIt     = sheet.iterator();
                while(rowIt.hasNext())
                {                    
                    /*Recorre todas columnas del archivo*/
                    Row row             = rowIt.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                    if(iConta == 1)
                    {
                        ++iConta;
                        continue;
                    }

                    /*Variable para leer las celdas*/
                    String sIn;                   

                    /*Inicializa la consulta*/
                    String sQInsert    = "INSERT INTO almas(alma, almadescrip, dir1, dir2, dir3, sucu, nocaj, estac, respon) VALUES('";                    
                
                    /*Contiene el código del producto y el almacén*/                    
                    String sAlma;
                    
                    /*Recorre todas las celdas de la fila*/
                    while(cellIterator.hasNext())
                    {
                        /*Obtiene el objeto de la celda*/
                        Cell cell       = cellIterator.next();                 

                        /*Determina el tipo de celda que es*/
                        switch(cell.getCellType())
                        {
                            /*En caso de que sea de tipo string entonces*/
                            case Cell.CELL_TYPE_STRING:
                                
                                /*Obtiene el valor de la celda*/
                                sIn         = cell.getStringCellValue();                                                            
                            
                                /*Si es la celda 1 entonces*/
                                if(iContCell==1)
                                {
                                    /*Si es el fin del archivo entonces*/
                                    if(sIn.compareTo("FINARCHIVO")==0)
                                    {
                                        //Esconde la forma de loading
                                        Star.vOcultLoadin();

                                        //Termina la transacción
                                        if(Star.iTermTransCon(con)==-1)
                                            return;
                                        
                                        //Cierra la base de datos y regresa
                                        if(Star.iCierrBas(con)==-1)                                
                                            return;

                                        /*Mensajea y regresa*/
                                        JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " almaenes.", "Almacenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                        return;
                                    }
                                    /*Else no es el final del archivo entonces*/
                                    else
                                    {                                                
                                        /*Guarda el código del almácen y completa la consulta*/
                                        sAlma       = cell.getStringCellValue().replace("|", "");
                                        sQInsert    += sAlma + "','";
                                        
                                        //Muestra el loading
                                        Star.vMostLoading("");
                                    }
                                    
                                }/*Fin de if(iContCell==1)*/                                    
                                /*Else if: Descripción*/
                                else if(iContCell==2)                                                                                                       
                                    sQInsert   += cell.getStringCellValue() + "','";                                                               
                                /*Else if: Dirección 1*/
                                else if(iContCell==3)                                                                    
                                    sQInsert   += cell.getStringCellValue() + "','";
                                /*Else if: Dirección 2*/
                                else if(iContCell==4)                                                                    
                                    sQInsert   += cell.getStringCellValue() + "','";
                                /*Else if: Dirección 3*/
                                else if(iContCell==5)                                                                    
                                    sQInsert   += cell.getStringCellValue() + "','";
                                
                                break;
                                
                        }/*Fin de switch(cell.getCellType())*/

                        /*Aumenta en uno el contador de las celdas*/
                        ++iContCell;
                        
                    }/*Fin de while(cellIterator.hasNext())*/

                    /*Aumenta en uno el contador de las filas*/
                    ++iConta;
                    
                    /*Resetea el contador de celdas*/
                    iContCell   = 1;
                            
                    /*Quita los últimos carácteres inválidos*/
                    sQInsert        = sQInsert.substring(0, sQInsert.length() - 2);

                    /*Agrega el terminador de la consulta*/
                    sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "', '')";
                    
                    /*Inserta en la base de datos el registro*/
                    try
                    {
                        st = con.createStatement();                        
                        st.executeUpdate(sQInsert);
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                        
                    }                                                                        
                    
                }/*Fin de while(rowIt.hasNext())*/                                    
                
            }/*Fin de public void run()*/
            
        }).start();
                
        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jMImpAlmaActionPerformed

    
    /*Cuando se presiona el menú de exportar almacenes a excel*/
    private void jMExpAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExpAlmaActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar almacenes");
                
        /*Si el usuario no presiono aceptar entonces regresa*/        
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        //Muestra el loading
        Star.vMostLoading("");

        /*Realiza todo esto en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Lee la ruta seleccionada*/
                String sRut = fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName() + ".xlsx";

                /*Crea un documentovacio*/
                XSSFWorkbook wkbok = new XSSFWorkbook();

                /*Crea la hoja en blanco*/
                XSSFSheet sheet = wkbok.createSheet("Alacenes");

                /*Variable contadora*/        
                int         iConta                   = 1;
                
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                
                
                /*Pon los encabezados en el archivo de excel*/
                Map<String, Object[]> data = new TreeMap<>();
                data.put(Integer.toString(iConta), new Object[] {"ALMACEN(1)", "DESCRIPCION(2)", "DIRECCION1(3)", "DIRECCION2(4)", "DIRECCION3(5)"});

                /*Aumenta en uno el contador de filas del libro*/
                ++iConta;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae todos los almacenes*/
                try
                {
                    sQ = "SELECT alma, almadescrip, dir1, dir2, dir3 FROM almas";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {                    
                        /*Agrega el registro en la fila de excel*/
                        data.put(Integer.toString(iConta), new Object[] {rs.getString("alma"), rs.getString("almadescrip"), rs.getString("dir1"), rs.getString("dir2"), rs.getString("dir3")});

                        /*Aumenta en uno el contador de filas del libro*/
                        ++iConta;
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                    
                }

                //Cierra la base de datos y regresa
                if(Star.iCierrBas(con)==-1)                                
                    return;                                    

                /*Agrega el terminador de fichero*/
                data.put(Integer.toString(iConta), new Object[] {"FINARCHIVO"});

                /*Itera sobre la información y escribela en la hoja*/
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset)
                {
                    Row row = sheet.createRow(rownum++);
                    Object [] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object ob : objArr)
                    {
                        Cell cell = row.createCell(cellnum++);
                        if(ob instanceof String)
                        cell.setCellValue((String)ob);
                        else if(ob instanceof Integer)
                        cell.setCellValue((Integer)ob);
                    }
                }

                /*Escribe los datos en el archivo físico*/
                try
                {                    
                    FileOutputStream out;
                    out = new FileOutputStream(new File(sRut));
                    wkbok.write(out);
                    out.close();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                    
                }

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo exportado en: " + sRut + " con éxito.", "Exportar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Preguntar al usuario si quiere abrir el archivo*/
                Object[] op     = {"Si","No"};
                int iRes        = JOptionPane.showOptionDialog(null, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    return;                       

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sRut));
                } 
                catch(IOException expnIO) 
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                           
                }

            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jMExpAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el menú de activo fijo*/
    private void jMActFijKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMActFijKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMActFijKeyPressed

    
    /*Cuando se presiona el menú de activo fijo*/
    private void jMActFijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMActFijActionPerformed
        
        /*Muestra la forma para ver el activo fijo*/
        ActFij a = new ActFij();
        a.setVisible(true);
        
    }//GEN-LAST:event_jMActFijActionPerformed

    
    /*Cuando se presiona una tecla en le menú de catálogo de activo fijo*/
    private void jMCatActFijKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMCatActFijKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMCatActFijKeyPressed

    
    /*Cuando se presiona el menú de catálogo de activo fijo*/
    private void jMCatActFijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCatActFijActionPerformed
        
        /*Muestra la forma de activo fijo*/        
        cats.PlanGralCat l = new cats.PlanGralCat("actfijcat","actfij","tipact","activo fijo","ActTip","concep");
	l.setVisible(true);
        
    }//GEN-LAST:event_jMCatActFijActionPerformed

    
    /*Cuando se presiona una tecla en el menú de tipos de activos*/
    private void jMTipActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMTipActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMTipActKeyPressed

    
    /*Cuando se presiona el menú de tipos de activo fijo*/
    private void jMTipActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMTipActActionPerformed
        
        /*Muestra la forma para ver los tipos de activo figo*/
        cats.PlanGralCat l = new cats.PlanGralCat("tipactfij","actfij","tipact","tipo activo fijo","ActTip","tip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMTipActActionPerformed

    
    /*Cuando se presiona una tecla en el menú de reporte de contabilidad*/
    private void jMRepContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMRepContaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMRepContaKeyPressed

    
    /*Cuando se presiona el menú de reporte de contabilidad*/
    private void jMRepContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMRepContaActionPerformed
        
        /*Muestra la forma para reporte de contabilidad*/
        RepConta r = new RepConta();
        r.setVisible(true);
        
    }//GEN-LAST:event_jMRepContaActionPerformed

    
    
    
    
    
    /*Cuando se presiona una tecla en el menú de catálogo de activo fijo*/
    private void jMLogCatActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogCatActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogCatActKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de activo fijo*/
    private void jMLogActFijKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogActFijKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogActFijKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de catálogo general*/
    private void jMLogCatGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogCatGralKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogCatGralKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de clasificaciones general*/
    private void jMLogClasCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogClasCliKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogClasCliKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de clasificaciones de proveedores*/
    private void jMLogClasProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogClasProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogClasProvKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de conceptos*/
    private void jMLogConcepNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogConcepNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogConcepNotKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de garantías*/
    private void jMLogGaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogGaraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogGaraKeyPressed

    
    /*Cuando se presiona una tecla en el menú de log de giros*/
    private void jMLogGirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogGirKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogGirKeyPressed

    
    
    /*Cuando se presiona una tecla en el menú de log de zonas*/
    private void jMLogZonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogZonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogZonKeyPressed

    
    /*Cuando se presiona el menú de log de catálogo de activo fijo*/
    private void jMLogCatActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogCatActActionPerformed
        
        /*Abre la forma para ver el log de catálogo de activo fijo*/
        LogGral l = new LogGral("Log catálogo de activo fijo", "logactcat", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogCatActActionPerformed

    
    /*Cuando se presiona el menú de log de activo fijo*/
    private void jMLogActFijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogActFijActionPerformed
        
        /*Abre la forma para ver el log de tipo de activo fijo*/
        LogGral l = new LogGral("Log tipo activo fijo", "logacttip", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogActFijActionPerformed

    
    /*Cuando se presiona el menú de log de catálgo general*/
    private void jMLogCatGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogCatGralActionPerformed
        
        /*Abre la forma para ver el log de catálogo general*/
        LogGral l = new LogGral("Log catálogo general", "logcatgral", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogCatGralActionPerformed

    
    /*Cuando se presiona el menú de log de catálogo general*/
    private void jMLogClasCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogClasCliActionPerformed
        
        /*Abre la forma para ver el log de tipo de activo fijo*/
        LogGral l = new LogGral("Log clasificación clientes", "logclasemp", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogClasCliActionPerformed

    
    /*Cuando se presiona el menú de log de clasificación de proveedor*/
    private void jMLogClasProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogClasProvActionPerformed
        
        /*Abre la forma para ver el log de tipo de activo fijo*/
        LogGral l = new LogGral("Log clasificación proveedores", "logclasprov", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogClasProvActionPerformed

    
    /*Cuando se presiona el menú de log de conceptos de nota de crédito*/
    private void jMLogConcepNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogConcepNotActionPerformed
        
        /*Abre la forma para ver el log de tipo de activo fijo*/
        LogGral l = new LogGral("Log conceptos activo fijo", "logconcnot", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogConcepNotActionPerformed

    
    /*Cuando se presiona el menú de log de garántia*/
    private void jMLogGaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogGaraActionPerformed
        
        /*Abre la forma para ver el log de garantías*/
        LogGral l = new LogGral("Log garantías", "loggara", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogGaraActionPerformed

    
    /*Cuando se presiona el menú de log de giros*/
    private void jMLogGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogGirActionPerformed
        
        /*Abre la forma para ver el log de giros*/
        LogGral l = new LogGral("Log de giros", "loggiro", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogGirActionPerformed

    
    
    /*Cuando se presiona el menú de log de zonas*/
    private void jMLogZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogZonActionPerformed
        
        /*Abre la forma para ver el log de zonas*/
        LogGral l = new LogGral("Log de zonas", "logzona", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogZonActionPerformed

    
    /*Cuando la forma de activa*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Guadar la dirección de la forma*/
        jFram   = this;
        
    }//GEN-LAST:event_formWindowActivated

    
    //Cuando se presiona una tecla en el menú de conceptos de pagos
    private void jMConcepPagsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMConcepPagsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMConcepPagsKeyPressed

    
    //Cuando se presiona el menú de conceptos de pagos
    private void jMConcepPagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMConcepPagsActionPerformed
        
        //Muestra la forma para ver los conceptos de pagos
        cats.PlanGralCat l = new cats.PlanGralCat("conceppag","cxc","concep","concepto de pagos","concpag","concep");
	l.setVisible(true);
        
    }//GEN-LAST:event_jMConcepPagsActionPerformed

    
    //Cuando se presiona una tecla en el menú de log de conceptos de pagos
    private void jMLogConcepPagsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMLogConcepPagsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMLogConcepPagsKeyPressed

    
    //Cuando se presiona el menú de log de conceptos de pagos
    private void jMLogConcepPagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMLogConcepPagsActionPerformed
        
        //Abre la forma para ver el log de conceptos de pagos
        LogGral l = new LogGral("Log concepto de pagos", "logconcpag", "descrip");
        l.setVisible(true);
        
    }//GEN-LAST:event_jMLogConcepPagsActionPerformed

    //Cuando se preciona alguna tecla en el control
    private void jMenuMarcModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuMarcModKeyPressed
    
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jMenuMarcModKeyPressed

    
    //Se seleciona el catalogo de marcas y modelos
    private void jMenuMarcModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMarcModActionPerformed
        
        /*Muestra la forma para ver las tallas y colores*/
        cats.MarcMod t = new cats.MarcMod();
        t.setVisible(true);
        
    }//GEN-LAST:event_jMenuMarcModActionPerformed

        
    //Cuando entra el mouse del previo de compra
    private void jBPrevCompMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrevCompMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPrevComp.setBackground(Star.colBot);

    }//GEN-LAST:event_jBPrevCompMouseEntered

    
    //Cuando se sale el mouse del previo de compra 
    private void jBPrevCompMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrevCompMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPrevComp.setBackground(colOri);

    }//GEN-LAST:event_jBPrevCompMouseExited

    
    //Se llama al previo de compra
    private void jBPrevCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrevCompActionPerformed
        
        //Se llama al previo de compra
        vPrevComps();
        
    }//GEN-LAST:event_jBPrevCompActionPerformed

    
    //Cuando se preciona una tecla en el boton de previo de compra
    private void jBPrevCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrevCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPrevCompKeyPressed

    
    //Al abrir el menu de concecutivos de previo de compra
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        /*Muestra la forma para las series de los previos de compra*/
        cats.SerPrev c = new cats.SerPrev();
        c.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    
    //Cuando se preciona en el menu el previo de compra
    private void jMPrevioCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMPrevioCompActionPerformed
        
        //Se llama al previo de compra
        vPrevComps();
        
    }//GEN-LAST:event_jMPrevioCompActionPerformed

    private void NotVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotVeActionPerformed
        
        SerNotVent c = new SerNotVent();
        c.setVisible(true);
        
    }//GEN-LAST:event_NotVeActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        RepPrevCom c = new RepPrevCom();
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed
    /*1 07 2015 Felipe Ruiz Garcia*/
    /*Metodo hace que la forma Sincronizar se abra solo una vez */
    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
        
          /*Abre la forma de sincronizar una sola vez*/
        if(Star.ventanaSincronizar==null)
        {            
        Star.ventanaSincronizar = new correoTerminal();
        Star.ventanaSincronizar.setVisible(true);
        }
        else
        {    
            /*Si ya esta visible entonces traela al frente*/
            if(Star.ventanaSincronizar.isVisible())            
                Star.ventanaSincronizar.toFront();
            else
                Star.ventanaSincronizar.setVisible(true);            
        }   
    }//GEN-LAST:event_jMenu4MouseClicked

    private void jMInvenImpoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMInvenImpoActionPerformed
        /*Configura el file chooser para escogerl a ruta donde esta el archivo de excel*/
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario no presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;                                            
        
        /*Lee la ruta seleccionada*/
        sRut    = fc.getCurrentDirectory().getAbsolutePath();

        /*Concatena la carpeta final seleccionada*/
        sRut    += "\\" + fc.getSelectedFile().getName();   

        /*Si no es un archivo de excel entonces*/
        if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no válido", JOptionPane.INFORMATION_MESSAGE, null); 
            return;
        }                

        /*Llama a la forma que harÃ¡ la importaciÃ³n*/
        Loadin lo = new Loadin(sRut);
        lo.setVisible(true);
    }//GEN-LAST:event_jMInvenImpoActionPerformed
/**/    
        /*Cuando se presiona el botón de intérfaz de contapaqi*/
    private void jMContpaqActionPerformed(java.awt.event.ActionEvent evt) {                                          
     
        /*Muestra la forma para hacer la interfaz de contabilidad*/
        InterCI i = new InterCI();
        i.setVisible(true);
        
    }  
    
    /*Cuando se presiona una tecla en el menù de contpaq i*/
    private void jMContpaqKeyPressed(java.awt.event.KeyEvent evt) {                                     
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }   
    
    /*Si no existe la serie entonces creala*/
    private void vCreSer(Connection con, String sIn)
    {
        /*Separa la serie y el código de la serie*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sIn,",");
        String sCod     = stk.nextToken();
        String sDescrip = stk.nextToken();
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                                
        /*Comprueba si la serie existe ya*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'PROV' AND ser = '" + sCod + "'";	                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces regresa*/
            if(rs.next())
                return;                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }
        
        /*Inserta la serie en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO consecs (ser,             descrip,       tip,    consec, estac,                 sucu,                 nocaj) "
                        + "VALUES('" + sCod + "', '" +  sDescrip + "', 'PROV', 0, '" + Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                         
         }    
            
    }/*Fin de private void vCreSer(Connection con, String sIn)*/
    
    
    /*Si la clasificación no existe entonces creala*/
    private void vCreClas(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción de la clasificación del cliente*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
                                        
        //Comprueba si la clasificación para el cliente existe        
        int iRes    = Star.iExistClasCli(con, sCod);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si existe entonces regresa
        if(iRes==1)
            return;
        
        /*Inserta la nueva clasificación en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO clasemp(cod,              descrip,                 sucu,                   nocaj,                 estac) "
                       + "VALUES('" + sCod + "','" +    sDescrip + "', '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
         }    
            
    }/*Fin de private void vCreClas(Connection con, String sS1)*/
    
    /*Si la clasificación de proveedor no existe entonces creala*/
    private void vCreClasProv(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción de la clasificación del cliente*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        /*Declara variables de la base de datos*/
        Statement   st;        
        String      sQ              = ""; 
                                        
        //Comprueba si la clasificación para el cliente existe        
        int iRes    = Star.iExistClasProv(con, sCod);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si existe entonces regresa
        if(iRes==1)
            return;
        
        /*Inserta la nueva clasificación en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO clasprov(cod,              descrip,                 sucu,                   nocaj,                 estac) "
                       + "VALUES('" + sCod + "','" +    sDescrip + "', '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Esconde el loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);
            
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)                                
                return;
             
            /*Coloca la bandera de error*/
            bErr    = true;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());           
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);             
         }    
            
    }/*Fin de private void vCreClas(Connection con, String sS1)*/
    
    /*Si la zona no existe entonces creala*/
    private void vCreZon(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                                        
        /*Comprueba si la zona existe*/        
        try
        {
            sQ = "SELECT cod FROM zona WHERE cod = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces regresa por que ya existe*/
            if(rs.next())            
                return;            
        }
        catch(SQLException expnSQL)
        {                                    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }
        
        /*Inserta la nueva zona en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO zona(cod,             descrip,                 sucu,                   nocaj,                 estac) "
                   + "VALUES('" + sCod + "','" +    sDescrip + "', '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {                         
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
         }    
            
    }/*Fin de private void vCreZon(Connection con, String sS1)*/
    
    /*Si el giro no existe entonces creala*/
    private void vCreRubr(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ              = ""; 
                                        
        /*Comprueba si el giro existe*/        
        try
        {
            sQ = "SELECT cod FROM rubr WHERE cod = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces regresa por que ya existe*/
            if(rs.next())            
                return;            
        }
        catch(SQLException e)
        {                        
            /*Esconde el loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);                     
            
            /*Coloca la bandera de error*/
            bErr    = true;
            
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)                                
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null); 
            return;
        }
        
        /*Inserta el nuevo giro en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO rubr(cod,             descrip,              sucu,                   nocaj,                 estac) "
                   + "VALUES('" + sCod + "','" +    sDescrip + "', '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         {                         
            /*Esconde el loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);
           
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)                                
                return;
            
            /*Coloca la bandera de error*/
            bErr    = true;
            
            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());           
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);             
         }    
            
    }
    
    /*Si el giro no existe entonces creala*/
    private void vCreGir(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                                        
        /*Comprueba si el giro existe*/        
        try
        {
            sQ = "SELECT gir FROM giro WHERE gir = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces regresa por que ya existe*/
            if(rs.next())            
                return;            
        }
        catch(SQLException expnSQL)
        {                        
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }
        
        /*Inserta el nuevo giro en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO giro(gir,             descrip,              sucu,                   nocaj,                 estac) "
                   + "VALUES('" + sCod + "','" +    sDescrip + "', '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {                         
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                           
         }    
            
    }/*Fin de private void vCreGir(Connection con, String sS1)*/
    
    
    /*Si la serie no existe entonces creala*/
    private void vCreSerCli(Connection con, String sS1)
    {
        /*Obtiene el código y la descricipción de la serie del cliente*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sS1,",");
        String sCod         = stk.nextToken();
        String sDescrip     = stk.nextToken();
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                                        
        /*Comprueba si la serie para el cliente existe*/        
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'EMP' AND ser = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces regresa por que ya existe*/
            if(rs.next())
                return;            
        }
        catch(SQLException expnSQL)
        {                        
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                        
        }
        
        /*Inserta en los consecutivos la nueva serie*/
        try 
        {                
            sQ = "INSERT INTO consecs(ser,              descrip,       tip,     consec,         sucu,                   nocaj,                 estac) "
                       + "VALUES('" + sCod + "','" +    sDescrip + "', 'EMP',   0,    '" +      Star.sSucu + "', '" +   Star.sNoCaj + "', '" + Login.sUsrG + "') ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {                         
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                           
         }    
            
    }/*Fin de private void vCreSerCli(Connection con, String sS1)*/
                                                                                
        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {        
        /*Coloca la bandera para saber que hubo un evento del teclado*/
        bIdle   = true;                                            
    }            

    public static XSSFWorkbook agregaError(XSSFRow row, XSSFWorkbook wkbok, final int cont,final int celdaError,final String comentario) {
        //Si no existe la hoja de errores la creo
        if(wkbok.getSheet("Errores") == null){
            wkbok.createSheet("Errores");
            XSSFRow r = wkbok.getSheet("Errores").createRow(0);
            //Creo los encabezados 
            for (int i = 1; i < wkbok.getSheetAt(0).getRow(0).getLastCellNum(); i++) {
                //r.createCell(i,Cell.CELL_TYPE_STRING).setCellStyle(wkbok.getSheetAt(0).getRow(0).getCell(i).getCellStyle());
                r.createCell(i,Cell.CELL_TYPE_STRING).setCellValue(wkbok.getSheetAt(0).getRow(0).getCell(i).getStringCellValue());
            }

        }
        //Obtengo la hoja
        XSSFSheet sh = wkbok.getSheet("Errores");
        //Creo la row
        sh.createRow(cont+1);
        //Creo la nueva row
        XSSFRow nRow = sh.getRow(cont+1);
        //Nueva y vieja celda
        XSSFCell oldCell;
        
        try{
            for (int i = 1; i <= row.getLastCellNum(); i++) {
                oldCell = row.getCell(i);
                nRow.createCell(i);
                 // Set the cell data value
                if(oldCell == null)
                    nRow.getCell(i).setCellValue("");
                else
                    switch (oldCell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            nRow.getCell(i).setCellValue("");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            nRow.getCell(i).setCellValue(oldCell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            nRow.getCell(i).setCellValue(oldCell.getNumericCellValue());
                            break;
                        default: System.out.println(oldCell.getCellType());
                    }
                //Si es la celda que causo el error la pinto y agrego el comentario
                if(i == celdaError){
                    XSSFCellStyle style = wkbok.createCellStyle();
                    style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                    style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
                    nRow.getCell(i).setCellStyle(style);
                    
                    nRow.getCell(i).setCellComment( creaComentario(comentario, wkbok, i, nRow.getRowNum()));
                    //Ajusto el size de la celda al contenido
                    wkbok.getSheetAt(2).autoSizeColumn(i);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return wkbok;
    }

public static Comment creaComentario(final String comentario, XSSFWorkbook wb, final int i,final int rowNum) {

        CreationHelper factory = wb.getCreationHelper();

        Drawing drawing = wb.getSheetAt(2).createDrawingPatriarch();

        // When the comment box is visible, have it show in a 1x3 space
        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(wb.getSheetAt(2).getRow(rowNum).getCell(i).getColumnIndex());
        anchor.setCol2(wb.getSheetAt(2).getRow(rowNum).getCell(i).getColumnIndex()+1);
        anchor.setRow1(rowNum);
        anchor.setRow2(rowNum+3);

        // Create the comment and set the text+author
        Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(comentario);
        comment.setString(str);
        //comment.setAuthor("");

        return comment;
    }
    
    public void deshabilitaBotones() {
        //Abre la conexion
        Connection con = Star.conAbrBas(true, false);
        
        //Declara variables de la base de datos    
        java.sql.Statement   st;
        java.sql.ResultSet   rs;                        
        String      sQ;
        try {
            /*Obtiene todos los datos del usuario*/
            sQ  = "SELECT * FROM er_permisos WHERE FKIdUsuario = (SELECT id_id FROM estacs WHERE estac = '"+Login.sUsrG+"')";
            st = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                jMSist.setEnabled((Integer.parseInt(rs.getString("permisoConf"))==(1)));
                if(jMSist.isEnabled()){
                    jMenIt4.setEnabled((Integer.parseInt(rs.getString("permisoCorreos"))==(1)));
                    jMenItDatsGenEmp.setEnabled((Integer.parseInt(rs.getString("permisoDatosEmpresa"))==(1)));
                    jMenSer.setEnabled((Integer.parseInt(rs.getString("permisoSeries"))==(1)));
                    jMenItImpres.setEnabled((Integer.parseInt(rs.getString("permisoImpresoras"))==(1)));
                    jMenCambIco.setEnabled((Integer.parseInt(rs.getString("permisoCambiarIcono"))==(1)));
                    jMConfAd.setEnabled((Integer.parseInt(rs.getString("permisoConfiguracionesGenerales"))==(1)));
                }
                jMMUsr.setEnabled((Integer.parseInt(rs.getString("permisoUsuarios"))==(1)));
                if(jMMUsr.isEnabled()){
                    jMenItEstacs.setEnabled((Integer.parseInt(rs.getString("permisoUsuariosDefinir"))==(1)));
                    jMUsr.setEnabled((Integer.parseInt(rs.getString("permisoUsuariosConectados"))==(1)));
                    jMenItPermEstacs.setEnabled((Integer.parseInt(rs.getString("permisoUsuariosPermisos"))==(1)));
                }
                jMen2.setEnabled((Integer.parseInt(rs.getString("permisoClaves"))==(1)));
                jMenRep.setEnabled((Integer.parseInt(rs.getString("permisoReparar"))==(1)));
                if(jMenRep.isEnabled()){
                    jMenIt8.setEnabled((Integer.parseInt(rs.getString("permisoRepararErrores"))==(1)));
                    jMenIt9.setEnabled((Integer.parseInt(rs.getString("permisoRepararRestaurar"))==(1)));
                }
                jMenBDs.setEnabled((Integer.parseInt(rs.getString("permisoBaseDatos"))==(1)));
                if(jMenBDs.isEnabled()){
                    jMenItModBD.setEnabled((Integer.parseInt(rs.getString("permisoBaseDatosConexiones"))==(1)));
                    jMenConfigFil.setEnabled((Integer.parseInt(rs.getString("permisoBaseDatosArchivo"))==(1)));
                }
                jMMRep.setEnabled((Integer.parseInt(rs.getString("permisoReportes"))==(1)));
                if(jMMRep.isEnabled()){
                    jMEstacs.setEnabled((Integer.parseInt(rs.getString("permisoReportesUsuarios"))==(1)));
                    jMResp.setEnabled((Integer.parseInt(rs.getString("permisoReportesRespaldos"))==(1)));
                    jMRepLogCo.setEnabled((Integer.parseInt(rs.getString("permisoReportesLog"))==(1)));
                    jMEstadisCor.setEnabled((Integer.parseInt(rs.getString("permisoReportesEstadistica"))==(1)));
                }
                jMRevo.setEnabled((Integer.parseInt(rs.getString("permisoRevocacion"))==(1)));
                jMActSis.setEnabled((Integer.parseInt(rs.getString("permisoActivarSistema"))==(1)));
                jMConta.setEnabled((Integer.parseInt(rs.getString("permisoContabilidad"))==(1)));
                jMConcepNot.setEnabled((Integer.parseInt(rs.getString("permisoConceptosNC"))==(1)));
                jMCatGara.setEnabled((Integer.parseInt(rs.getString("permisoCatalogoGarantias"))==(1)));
                jMZon.setEnabled((Integer.parseInt(rs.getString("permisoZonas"))==(1)));
                jMGir.setEnabled((Integer.parseInt(rs.getString("permisoGiros"))==(1)));
                jMenItMons.setEnabled((Integer.parseInt(rs.getString("permisoMonedas"))==(1)));
                jMeItImps.setEnabled((Integer.parseInt(rs.getString("permisoImpuestos"))==(1)));
                jBComps.setEnabled((Integer.parseInt(rs.getString("permisoCompras"))==(1)));
                if(jBComps.isEnabled()){
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasCancelar"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasDevolucion"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasParcial"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasNuevo"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasNotCredito"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasVer"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasCargarArchivo"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasBorrarArchivo"))==(1)));
                    btnsCompras.add((Integer.parseInt(rs.getString("permisoComprasRecibirOrden"))==(1)));
                }
                jBProvs.setEnabled((Integer.parseInt(rs.getString("permisoProvee"))==(1)));
                if(jBProvs.isEnabled()){
                    btnsProvee.add((Integer.parseInt(rs.getString("permisoProveeNuevo"))==(1)));
                    btnsProvee.add((Integer.parseInt(rs.getString("permisoProveeModificar"))==(1)));
                    btnsProvee.add((Integer.parseInt(rs.getString("permisoProveeVer"))==(1)));
                    btnsProvee.add((Integer.parseInt(rs.getString("permisoProveeBorrar"))==(1)));
                }
                jBPrevComp.setEnabled((Integer.parseInt(rs.getString("permisoPrevio"))==(1)));
                if(jBPrevComp.isEnabled()){
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioNueva"))==(1)));
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioAbrir"))==(1)));
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioVer"))==(1)));
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioCancelar"))==(1))); 
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioSeries"))==(1)));
                    btnsPrevio.add((Integer.parseInt(rs.getString("permisoPrevioCompra"))==(1)));
                }
                jMInven.setEnabled((Integer.parseInt(rs.getString("permisoInventario"))==(1)));
                jBProds.setEnabled((Integer.parseInt(rs.getString("permisoProductos"))==(1)));
                if(jBProds.isEnabled()){
                    btnsProductos.add((Integer.parseInt(rs.getString("permisoProductosNuevo"))==(1)));
                    btnsProductos.add((Integer.parseInt(rs.getString("permisoProductosModificar"))==(1)));
                    btnsProductos.add((Integer.parseInt(rs.getString("permisProductosBorrar"))==(1)));
                }
                jBEmps.setEnabled((Integer.parseInt(rs.getString("permisoClientes"))==(1)));
                if(jBEmps.isEnabled()){
                    btnsClientes.add((Integer.parseInt(rs.getString("permisoClientesNuevo"))==(1)));
                    btnsClientes.add((Integer.parseInt(rs.getString("permisoClientesModificar"))==(1)));
                    btnsClientes.add((Integer.parseInt(rs.getString("permisoClientesVer"))==(1)));
                    btnsClientes.add((Integer.parseInt(rs.getString("permisoClientesBorrar"))==(1))); 
                    btnsClientes.add((Integer.parseInt(rs.getString("permisoClientesEnviar"))==(1)));
                }
                jBVtas.setEnabled((Integer.parseInt(rs.getString("permisoVentas"))==(1)));
                if(jBVtas.isEnabled()){
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasCancelar"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasDevolucion"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasParcial"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasNueva"))==(1))); 
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasNotCredito"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasVer"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasEnviar"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasTimbrar"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasEntregar"))==(1))); 
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasComprobar"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasAcuse"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasObtenerXml"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasFacturar"))==(1)));
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasCargarArchivo"))==(1))); 
                    btnsVentas.add((Integer.parseInt(rs.getString("permisoVentasBorrarArchivo"))==(1)));
                }
                jBCots.setEnabled((Integer.parseInt(rs.getString("permisoCotiza"))==(1)));
                if(jBCots.isEnabled()){
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaNueva"))==(1)));
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaAbrir"))==(1)));
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaVer"))==(1)));
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaCancelar"))==(1)));
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaReenviar"))==(1))); 
                    btnsCotiza.add((Integer.parseInt(rs.getString("permisoCotizaVenta"))==(1)));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermsEstacs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    class ExtensionFileFilter extends FileFilter {
  String description;

  String extensions[];

  public ExtensionFileFilter(String description, String extension) {
    this(description, new String[] { extension });
  }

  public ExtensionFileFilter(String description, String extensions[]) {
    if (description == null) {
      this.description = extensions[0];
    } else {
      this.description = description;
    }
    this.extensions = (String[]) extensions.clone();
    toLower(this.extensions);
  }

  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      for (int i = 0, n = extensions.length; i < n; i++) {
        String extension = extensions[i];
        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
          return true;
        }
      }
    }
    return false;
  }
}

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem NotVe;
    private javax.swing.JButton jBComps;
    private javax.swing.JButton jBCots;
    private javax.swing.JButton jBEmps;
    private javax.swing.JButton jBPrevComp;
    private javax.swing.JButton jBProds;
    private javax.swing.JButton jBProvs;
    private javax.swing.JButton jBPtoVta;
    private javax.swing.JButton jBVtas;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLImgAlert;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenuItem jMAcerc;
    private javax.swing.JMenuItem jMActFij;
    private javax.swing.JMenuItem jMActSis;
    private javax.swing.JMenuItem jMApFav;
    private javax.swing.JMenuItem jMArchC;
    private javax.swing.JMenuItem jMAsoc;
    private javax.swing.JMenuItem jMCXC;
    private javax.swing.JMenuItem jMCXP;
    private javax.swing.JMenuItem jMCalc;
    private javax.swing.JMenuItem jMCarpAp;
    private javax.swing.JMenuItem jMCatActFij;
    private javax.swing.JMenuItem jMCatGara;
    private javax.swing.JMenuItem jMCatMsj;
    private javax.swing.JMenuItem jMChat;
    private javax.swing.JMenuItem jMChatC;
    private javax.swing.JMenuItem jMClasCli;
    private javax.swing.JMenuItem jMClasJer;
    private javax.swing.JMenuItem jMClasJerP;
    private javax.swing.JMenuItem jMClasProd;
    private javax.swing.JMenuItem jMClasProv;
    private javax.swing.JMenuItem jMConcepNot;
    private javax.swing.JMenuItem jMConcepPags;
    private javax.swing.JMenuItem jMConfAd;
    private javax.swing.JMenu jMConta;
    private javax.swing.JMenuItem jMContra;
    private javax.swing.JMenuItem jMCosts;
    private javax.swing.JMenuItem jMCuade;
    private javax.swing.JMenuItem jMDelLog;
    private javax.swing.JMenuItem jMEasCon;
    private javax.swing.JMenuItem jMEnviA;
    private javax.swing.JMenuItem jMEstacs;
    private javax.swing.JMenuItem jMEstadisCor;
    private javax.swing.JMenuItem jMExpAlma;
    private javax.swing.JMenuItem jMExpExisAlm;
    private javax.swing.JMenuItem jMExpoCliens;
    private javax.swing.JMenuItem jMExpoProvs;
    private javax.swing.JMenuItem jMExpoSer;
    private javax.swing.JMenuItem jMFluj;
    private javax.swing.JMenuItem jMFlujAct;
    private javax.swing.JMenuItem jMForLin;
    private javax.swing.JMenuItem jMGir;
    private javax.swing.JMenuItem jMImpAlma;
    private javax.swing.JMenuItem jMImpCliens;
    private javax.swing.JMenuItem jMImpExistAlm;
    private javax.swing.JMenuItem jMImpProvs;
    private javax.swing.JMenu jMInven;
    private javax.swing.JMenuItem jMInvenExpor;
    private javax.swing.JMenuItem jMInvenImpo;
    private javax.swing.JMenuItem jMInvenImpoSer;
    private javax.swing.JMenuItem jMJeraProd;
    private javax.swing.JMenuItem jMKard;
    private javax.swing.JMenuItem jMLogActFij;
    private javax.swing.JMenuItem jMLogAlma;
    private javax.swing.JMenuItem jMLogCatAct;
    private javax.swing.JMenuItem jMLogCatGral;
    private javax.swing.JMenuItem jMLogClas;
    private javax.swing.JMenuItem jMLogClasCli;
    private javax.swing.JMenuItem jMLogClasProv;
    private javax.swing.JMenuItem jMLogColo;
    private javax.swing.JMenuItem jMLogConcep;
    private javax.swing.JMenuItem jMLogConcepNot;
    private javax.swing.JMenuItem jMLogConcepPags;
    private javax.swing.JMenuItem jMLogFab;
    private javax.swing.JMenuItem jMLogGara;
    private javax.swing.JMenuItem jMLogGir;
    private javax.swing.JMenuItem jMLogImpu;
    private javax.swing.JMenuItem jMLogK;
    private javax.swing.JMenuItem jMLogLin;
    private javax.swing.JMenuItem jMLogLug;
    private javax.swing.JMenuItem jMLogMarc;
    private javax.swing.JMenuItem jMLogMed;
    private javax.swing.JMenuItem jMLogMod;
    private javax.swing.JMenuItem jMLogMon;
    private javax.swing.JMenuItem jMLogPes;
    private javax.swing.JMenuItem jMLogProd;
    private javax.swing.JMenuItem jMLogRes;
    private javax.swing.JMenu jMLogSis;
    private javax.swing.JMenuItem jMLogTall;
    private javax.swing.JMenuItem jMLogTip;
    private javax.swing.JMenuItem jMLogUbiE;
    private javax.swing.JMenuItem jMLogUnid;
    private javax.swing.JMenuItem jMLogZon;
    private javax.swing.JMenuItem jMLotPed;
    private javax.swing.JMenu jMMAplic;
    private javax.swing.JMenu jMMAyu;
    private javax.swing.JMenu jMMClas;
    private javax.swing.JMenuItem jMMClien;
    private javax.swing.JMenu jMMInvenExpor;
    private javax.swing.JMenu jMMInvenImpor;
    private javax.swing.JMenu jMMLogR;
    private javax.swing.JMenu jMMRep;
    private javax.swing.JMenu jMMRepCom;
    private javax.swing.JMenu jMMRepProd;
    private javax.swing.JMenu jMMRepVta;
    private javax.swing.JMenu jMMSis;
    private javax.swing.JMenu jMMUbic;
    private javax.swing.JMenu jMMUsr;
    private javax.swing.JMenu jMMUtil;
    private javax.swing.JMenuItem jMModel;
    private javax.swing.JMenuItem jMMsj;
    private javax.swing.JMenuItem jMNotic;
    private javax.swing.JMenuItem jMPrevioComp;
    private javax.swing.JMenuItem jMRepBackO;
    private javax.swing.JMenuItem jMRepCli;
    private javax.swing.JMenuItem jMRepCom;
    private javax.swing.JMenuItem jMRepConta;
    private javax.swing.JMenuItem jMRepCot;
    private javax.swing.JMenuItem jMRepLogCo;
    private javax.swing.JMenuItem jMRepOr;
    private javax.swing.JMenuItem jMRepProv;
    private javax.swing.JMenuItem jMRepVend;
    private javax.swing.JMenuItem jMRese;
    private javax.swing.JMenuItem jMResp;
    private javax.swing.JMenuItem jMRevo;
    private javax.swing.JMenuItem jMRubr;
    private javax.swing.JMenuItem jMSerNot;
    private javax.swing.JMenuItem jMSerO;
    private javax.swing.JMenu jMSist;
    private javax.swing.JMenuItem jMTabCompa;
    private javax.swing.JMenuItem jMTallCol;
    private javax.swing.JMenuItem jMTip;
    private javax.swing.JMenuItem jMTipAct;
    private javax.swing.JMenuItem jMUbiAd;
    private javax.swing.JMenuItem jMUsr;
    private javax.swing.JMenuItem jMVLogSys;
    private javax.swing.JMenuItem jMVProd;
    private javax.swing.JMenuItem jMZon;
    private javax.swing.JMenuItem jMeItImps;
    private javax.swing.JMenu jMen2;
    private javax.swing.JMenu jMenBDs;
    private javax.swing.JMenuBar jMenBar1;
    private javax.swing.JMenuItem jMenCXCR;
    private javax.swing.JMenuItem jMenCXP;
    private javax.swing.JMenuItem jMenCambIco;
    private javax.swing.JMenuItem jMenCatGral;
    private javax.swing.JMenu jMenComps;
    private javax.swing.JMenuItem jMenConfigFil;
    private javax.swing.JMenuItem jMenCotsT;
    private javax.swing.JMenu jMenInvents;
    private javax.swing.JMenuItem jMenIt1;
    private javax.swing.JMenuItem jMenIt4;
    private javax.swing.JMenuItem jMenIt5;
    private javax.swing.JMenuItem jMenIt8;
    private javax.swing.JMenuItem jMenIt9;
    private javax.swing.JMenuItem jMenItAlmas;
    private javax.swing.JMenuItem jMenItCamEmp;
    private javax.swing.JMenuItem jMenItCambClavSeg;
    private javax.swing.JMenuItem jMenItCols;
    private javax.swing.JMenuItem jMenItComps;
    private javax.swing.JMenuItem jMenItConceps;
    private javax.swing.JMenuItem jMenItDatsGenEmp;
    private javax.swing.JMenuItem jMenItDefEstacs;
    private javax.swing.JMenuItem jMenItDeslog;
    private javax.swing.JMenuItem jMenItEstacs;
    private javax.swing.JMenuItem jMenItFabs;
    private javax.swing.JMenuItem jMenItImpres;
    private javax.swing.JMenuItem jMenItIngr;
    private javax.swing.JMenuItem jMenItKits;
    private javax.swing.JMenuItem jMenItLogCorr;
    private javax.swing.JMenuItem jMenItLogear;
    private javax.swing.JMenuItem jMenItLugs;
    private javax.swing.JMenuItem jMenItManProvs;
    private javax.swing.JMenuItem jMenItMarcs;
    private javax.swing.JMenuItem jMenItMeds;
    private javax.swing.JMenuItem jMenItModBD;
    private javax.swing.JMenuItem jMenItMons;
    private javax.swing.JMenuItem jMenItPermEstacs;
    private javax.swing.JMenuItem jMenItPesos;
    private javax.swing.JMenuItem jMenItProdsArrMax;
    private javax.swing.JMenuItem jMenItProdsBajMin;
    private javax.swing.JMenuItem jMenItResps;
    private javax.swing.JMenuItem jMenItSal;
    private javax.swing.JMenuItem jMenItTipsPags;
    private javax.swing.JMenuItem jMenItTouch;
    private javax.swing.JMenuItem jMenItTraspas;
    private javax.swing.JMenuItem jMenItUnids;
    private javax.swing.JMenuItem jMenItVtas;
    private javax.swing.JMenu jMenLogin;
    private javax.swing.JMenu jMenLogs;
    private javax.swing.JMenu jMenMaxsMins;
    private javax.swing.JMenuItem jMenProds;
    private javax.swing.JMenu jMenRep;
    private javax.swing.JMenu jMenSer;
    private javax.swing.JMenu jMenTraspas;
    private javax.swing.JMenu jMenVtas;
    private javax.swing.JMenuItem jMenVtasR;
    private javax.swing.JMenuItem jMeniRem;
    private javax.swing.JMenuItem jMeniSerCom;
    private javax.swing.JMenuItem jMeniSerCot;
    private javax.swing.JMenuItem jMeniSerEmp;
    private javax.swing.JMenuItem jMeniSerFac;
    private javax.swing.JMenuItem jMeniSerProv;
    private javax.swing.JMenuItem jMeniTik;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuMarcMod;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    private String quitaDoble(String sIn) {
        Double d = Double.parseDouble(sIn);
        
        return d.intValue()+"";
    }

    private String regresaConsecutivo(String sSer, String tipo, Connection con) {
        String sQ = "";
        Statement st;
        ResultSet rs;
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = '"+tipo+"' AND ser = '" + sSer + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next()){
                                
                /*Aumenta uno el consecutivo del cliente*/
                try 
                {            
                    sQ = "UPDATE consecs SET "
                            + "consec       = consec + 1, "
                            + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                            + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                            + "WHERE tip    = 'EMP' AND ser = '" + sSer.replace("'", "''") + "'";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException ex) 
                 { 
                     //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return "-1";

                    /*Agrega en el log*/
                    Login.vLog(ex.getMessage());

                    /*Mensajea y regresa error*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                    return "-1";
                 }
                //regresa el consecutivo
                return  rs.getString("consec");   
            }
                
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return "-1";
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa error*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return "-1";
        } 
        return "-1";
    }

    
    
        
}/*Fin de public class Princip extends javax.swing.JFrame */

