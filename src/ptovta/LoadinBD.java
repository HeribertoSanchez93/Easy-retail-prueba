/*Paquetes*/
package ptovta;

/*Importaciones*/
import static ptovta.BDCon.vDelBD;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;





/*Clase para mostrar el progreso de la creación de la estrucutura de la base de datos*/
public class LoadinBD extends javax.swing.JDialog {

    /*Constructor sin argumentos*/
    public LoadinBD(String sTex, String sTit, String sBD, String sInst, String sUsr, String sContra, String sPort) 
    {
        /*Inicializa los componentes gráficos*/
        initComponents();

        //Que sea modal
        this.setModal(true);
        
        /*Establece el valor del campo para saber en que procedimiento va*/
        jTInf.setText(sTex);

        /*Establece el título de la ventana*/
        this.setTitle(sTit);

        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())         
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));        
        else       
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

        /*Declara las variables final para el thread*/
        final String sBDFi      = sBD;
        final String sInstFi    = sInst;
        final String sContraFi  = sContra;
        final String sUsrFi     = sUsr;
        final String sPortFi    = sPort;

        /*Thread para crear toda la estructura de la base de datos específica*/
        Thread th = new Thread() 
        {
            @Override
            public void run() 
            {
                /*Crea toda la estructura de la base de datos*/
                vCreEstru(sBDFi, sInstFi, sContraFi, sUsrFi, sPortFi);
            }
        };
        th.start();

    }/*Fin de public Loadin(String sRu, JTable jTabPro, INT iContF)*/


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTInf = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jTInf.setEditable(false);
        jTInf.setBackground(new java.awt.Color(255, 255, 255));
        jTInf.setForeground(new java.awt.Color(51, 102, 0));
        jTInf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInf.setText("Iniciando Creación de Estructura de Base de Datos...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*Crea toda la estrucutura de la base de datos*/
    private void vCreEstru(String sBD, String sInst, String sContra, String sUsr, String sPort) 
    {
        /*Crea toda la estructura de la base de datos*/
        vCreaTTabsBs(sBD, sInst, sContra, sUsr, Star.sNombreEmpresa, Star.sGenClavDia(), sPort);

        /*Llama al recolector de basura*/
        System.gc();

        /*Cierra la forma*/
        this.dispose();
    }

    
    /*Función escalable para crear toda la estructura de la base de datos*/
    private void vCreaTTabsBs(String sBD, String sInst, String sContra, String sUsr, String sNombreEmp, String sCodEmpresa, String sPort) 
    {
        /*Abre la base de datos*/
        Connection con = null;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + sInst + ":" + sPort + "/" + sBD + "?user=" + sUsr + "&password=" + sContra);
            con.setAutoCommit(false);
        } 
        catch(SQLException | HeadlessException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Función para borrar la base de datos servidora*/
            vDelBD(Star.sBD);

            /*Mensajea y sal de la aplicación*/
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            System.exit(1);
        }

        /*Inicia la transacción*/
        try           
        {
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch(SQLException ex)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Esconde la forma de loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);
            
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);      
            return;
        }
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de consecutivos...");

        /*Crea la tabla de consecutivos si no existe*/
        vCreConsecs(sBD, con, "consecs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log almacenes...");

        /*Crea la tabla de log almacenes si no existe*/
        vCreLogAlmas(sBD, con, "logalmas");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log zona...");

        /*Crea la tabla de log zoonas si no existe*/
        vCreLogZon(sBD, con, "logzona");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log activo fijo...");

        /*Crea la tabla de log activo fijo si no existe*/
        vCreLogActFij(sBD, con, "logActCat");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log activo fijo tipo...");

        /*Crea la tabla de log activo fijo si no existe*/
        vCreLogActFijTip(sBD, con, "logActTip");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de catálogo general...");

        /*Crea la tabla de log catálogo general si no existe*/
        vCreLogCatGral(sBD, con, "logCatGral");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de clasificación de clientes...");

        /*Crea la tabla de log de clasificación de clientes si no existe*/
        vCreLogClasEmp(sBD, con, "logclasemp");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de clasificación de proveedores...");

        /*Crea la tabla de log de clasificación de proveedores si no existe*/
        vCreLogClasProv(sBD, con, "logclasprov");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de conceptos nota de crédito...");

        /*Crea la tabla de log de de conceptos nota de crédito si no existe*/
        vCreLogConcNot(sBD, con, "logconCnot");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de conceptos de pago...");

        /*Crea la tabla de log de de conceptos de pago si no existe*/
        vCreLogConcPag(sBD, con, "logconcpag");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de garantía...");

        /*Crea la tabla de log de garantias si no existe*/
        vCreLogGara(sBD, con, "logGara");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log giro...");

        /*Crea la tabla de log de giros si no existe*/
        vCreLogGir(sBD, con, "logGiro");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log anaqueles...");

        /*Crea la tabla de log anaqueles si no existe*/
        vCreLogAnaq(sBD, con, "loganaq");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log conceptos...");

        /*Crea la tabla de log conceptos si no existe*/
        vCreLogConcep(sBD, con, "logconcep");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log impuestos...");

        /*Crea la tabla de log impuestos si no existe*/
        vCreLogImpue(sBD, con, "logimpue");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log monedas...");

        /*Crea la tabla de log monedas si no existe*/
        vCreLogMons(sBD, con, "logmons");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log unidades...");

        /*Crea la tabla de log unidades si no existe*/
        vCreLogUnid(sBD, con, "logunid");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log medidas...");

        /*Crea la tabla de log medidas si no existe*/
        vCreLogMed(sBD, con, "logmed");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de pog pesos...");

        /*Crea la tabla de log pesos si no existe*/
        vCreLogPes(sBD, con, "logpes");
                
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log clasficación extra...");

        /*Crea la tabla de log clasificación extra si no existe*/
        vCreLogClasExt(sBD, con, "logclasext");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log colores...");

        /*Crea la tabla de log colores si no existe*/
        vCreLogColo(sBD, con, "logcolo");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log fabricantes...");

        /*Crea la tabla de log fabricantes si no existe*/
        vCreLogFabs(sBD, con, "logfabs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log marcas...");

        /*Crea la tabla de log marcas si no existe*/
        vCreLogMarcs(sBD, con, "logmarc");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log tallas...");

        /*Crea la tabla de log tallas si no existe*/
        vCreLogTall(sBD, con, "logtall");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log modelos...");

        /*Crea la tabla de log Modelos si no existe*/
        vCreLogMod(sBD, con, "logmod");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log líneas...");

        /*Crea la tabla de log almacenes si no existe*/
        vCreLogLins(sBD, con, "loglins");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log lugares...");

        /*Crea la tabla de log lugares si no existe*/
        vCreLogLugs(sBD, con, "loglugs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log kits...");

        /*Crea la tabla de log kits si no existe*/
        vCreLogKit(sBD, con, "logkit");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log productos...");

        /*Crea la tabla de log prods si no existe*/
        vCreLogProds(sBD, con, "logprods");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log empresas...");

        /*Crea la tabla de log empresas si no existe*/
        vCreLogEmps(sBD, con, "logemps");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log proveedores...");

        /*Crea la tabla de log proveedores si no existe*/
        vCreLogProvs(sBD, con, "logprovs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de cumpleaños...");

        /*Crea la tabla de cumpleaños si no existe*/
        vCreCumple(sBD, con, "cumple");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de cuentas por cobrar...");

        /*Crea la tabla de cxc  si no existe*/
        vCreCXC(sBD, con, "cxc");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log usuarios...");

        /*Crea la tabla del log usuarios si no existe*/
        vCreLogEstac(sBD, con, "logestac");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de login de usuarios...");

        /*Crea la tabla del log usuarios si no existe*/
        vCreLogIni(sBD, con, "logini");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de cuentas por pagar...");

        /*Crea la tabla de cxp si no existe*/
        vCreCXP(sBD, con, "cxp");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de contra recibos...");

        /*Crea la tabla de contrarecibos si no existe*/
        vCreContra(sBD, con, "contras");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de empresas...");

        /*Crea la tabla de empresas si no existe*/
        vCreCli(sBD, con, "emps");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de respaldos...");

        /*Crea la tabla de respaldos si no existe*/
        vCreResp(sBD, con, "resp");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de mensajes...");

        /*Crea la tabla de mensajes si no existe*/
        vCreMsjs(sBD, con, "msjs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de órdenes de compra...");

        /*Crea la tabla de ordenes de compra si no existe*/
        vCreOrds(sBD, con, "ords");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de órdenes de compra...");

        /*Crea la tabla de partidas de las ordenes de compra si no existe*/
        vCrePartOrds(sBD, con, "partords");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de viáticos de proyectos...");

        /*Crea la tabla de viatspro si no existe*/
        vCreViatsPro(sBD, con, "viatspro");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de cortes...");

        /*Crea la tabla de corteszx si no existe*/
        vCreCortszx(sBD, con, "cortszx");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de flujo...");

        /*Crea la tabla de flujo si no existe*/
        vCreFluj(sBD, con, "fluj");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de viáticos...");

        /*Crea la tabla de viaticos si no existe*/
        vCreViats(sBD, con, "viats");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de pagos...");

        /*Crea la tabla de pagos si no existe*/
        vCrePags(sBD, con, "pags");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de correos...");

        /*Crea la tabla de logcorreos si no existe*/
        vCreLogCorr(sBD, con, "logcorrs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de estadísticas de correo...");

        /*Crea la tabla de estadísticas de correo si no existe*/
        vCreEstadisCor(sBD, con, "estadiscor");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de proveedores...");

        /*Crea la tabla de proveedores si no existe*/
        vCreProvs(sBD, con, "provs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de personas...");

        /*Crea la tabla de personas si no existe*/
        vCrePers(sBD, con, "pers");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de usuarios...");

        /*Crea la tabla de usuarios si no existe*/
        vCreUrs(sBD, con, "estacs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de correos electrónicos...");

        /*Crea la tabla de correoselectronicos si no existe*/
        vCreCorrElec(sBD, con, "corrselec");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de bases de datos...");

        /*Crea la tabla de basesdedatos si no existe*/
        vCreBasDat(sBD, sNombreEmp, sCodEmpresa, con, "basdats");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log respaldos...");

        /*Crea la tabla de log de respaldos si no existe*/
        vCreRespLog(sBD, con, "resplog");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de líneas...");

        /*Crea la tabla de lineas si no existe*/
        vCreLins(sBD, con, "lins");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificación de empresas...");

        /*Crea la tabla de clasificaciones de empresas si no existe*/
        vCreClasClien(sBD, con, "clasemp");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificación de proveedores...");

        /*Crea la tabla de clasificaciones de proveedores si no existe*/
        vCreClasProv(sBD, con, "clasprov");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de kits...");

        /*Crea la tabla de kits si no existe*/
        vCreKits(sBD, con, "kits");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de impuestos...");

        /*Crea la tabla de impuestos si no existe*/
        vCreImpue(sBD, con, "impues");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tallas...");

        /*Crea la tabla de tallas si no existe*/
        vCreTall(sBD, con, "tall");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tallas y colores...");

        /*Crea la tabla de tallas y colores si no existe*/
        vCreTallColo(sBD, con, "tallcolo");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de marca y modelo...");

        /*Crea la tabla de asociaciones de marca y modelo si no existe*/
        vCreMarcMode(sBD,  con, "terMarcamodelo");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de producto, marca y modelo...");

        /*Crea la tabla productos por asociación de marca y modelo si no existe*/
        vCreProdMarcMode(sBD, con, "terProdCompa");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de anaqueles...");

        /*Crea la tabla de anaqueles si no existe*/
        vCreAnaqs(sBD, con, "anaqs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de lugares...");

        /*Crea la tabla de lugares si no existe*/
        vCreLugs(sBD, con, "lugs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de activos fijos...");

        /*Crea la tabla de activos fijos si no existe*/
        vCreActFij(sBD, con, "actfij");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de interfaz DATAPARK...");

        /*Crea la tabla de interfaz datapark fijos si no existe*/
        vCreInterDP(sBD, con, "interdpark");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de marcas...");

        /*Crea la tabla de marcas si no existe*/
        vCreMarcs(sBD, con, "marcs");
                        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tipscamb...");

        /*Crea la tabla de tipos de cambio si no existe*/
        vCreTipsCamb(sBD, con, "tipscamb");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tarjetas...");

        /*Crea la tabla de tarjetas si no existe*/
        vCreTars(sBD, con, "tars");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de lotes y pedimentos...");

        /*Crea la tabla de lotes y pedimentos si no existe*/
        vCreLotPed(sBD, con, "lotped");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de costeos...");

        /*Crea la tabla de costeos si no existe*/
        vCreCost(sBD, con, "costs");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tipos de activo fijo...");

        /*Crea la tabla de tipo de activo fijo si no existe*/
        vCreTipAct(sBD, con, "tipactfij");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de estados activo fijo...");

        /*Crea la tabla de estados de activo fijo si no existe*/
        vCreActFijCat(sBD, con, "actfijcat");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de existencias de producto X almacen...");

        /*Crea la tabla de existencias por producto por almacén si no existe*/
        vCreExisAlma(sBD, con, "existalma");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de conceptos notas...");

        /*Crea la tabla de notas de crédito*/
        vCreConcepNot(sBD, con, "concepnot");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de conceptos de pagos...");

        /*Crea la tabla de conceptos de pagos*/
        vCreConcepPag(sBD, con, "conceppag");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de flujo de actividades...");

        /*Crea la tabla de flujo de actividades si no existe*/
        vCreFlujAct(sBD, con, "flujact");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de actividades...");

        /*Crea la tabla de partidas de actividades si no existe*/
        vCrePartFlujAct(sBD, con, "partflujact");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificaciones jerárquicas clientes...");

        /*Crea la tabla de clasificaciones jerárquicas si no existe*/
        vCreClasJeraCli(sBD, con, "clasjeracli");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificaciones jerárquicas proveedores...");

        /*Crea la tabla de clasificaciones jerárquicas si no existe*/
        vCreClasJeraProv(sBD, con, "clasjeraprov");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificaciones jerárquicas productos...");

        /*Crea la tabla de clasificaciones jerárquicas de productos si no existe*/
        vCreClasJeraProd(sBD, con, "clasjeraprod");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de garantías...");

        /*Crea la tabla de garantías si no existe*/
        vCreGaran(sBD, con, "garan");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de rubros...");

        /*Crea la tabla de rubros si no existe*/
        vCreRubr(sBD, con, "rubr");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de interfacez BD...");

        /*Crea la tabla de interfaces bd si no existe*/
        vCreInterBD(sBD, con, "interbd");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de zonas...");

        /*Crea la tabla de zonas si no existe*/
        vCreZon(sBD, con, "zona");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de giros...");

        /*Crea la tabla de giros si no existe*/
        vCreGiro(sBD, con, "giro");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociación descuentos...");

        /*Crea la tabla de asociaciones de descuentos si no existe*/
        vCreAsocDesc(sBD, con, "asocdesc");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log tipos...");

        /*Crea la tabla de log tipos si no existe*/
        vCreLogTip(sBD, con, "logtip");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de tipos...");

        /*Crea la tabla de tipos si no existe*/
        vCreTips(sBD, con, "tips");
                        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociaciones marcas...");

        /*Crea la tabla de asociaciones marcas si no existe*/
        vCreMarcProd(sBD, con, "marcprod");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociaciones números de parte...");

        /*Crea la tabla de asociaciones marcas si no existe*/
        vCreProdPart(sBD, con, "prodpart");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociaciones domicilios de entrega clientes...");

        /*Crea la tabla de asociaciones marcas si no existe*/
        vCreDomEntCli(sBD, con, "domentcli");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de compatibilidades...");

        /*Crea la tabla de compatibilidades si no existe*/
        vCreCompa(sBD, con, "compa");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de modelos...");

        /*Crea la tabla de marcas si no existe*/
        vCreModel(sBD, con, "model");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociaciones modelos...");

        /*Crea la tabla de asociaciones Modelos si no existe*/
        vCreModelProd(sBD, con, "modelprod");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de asociaciones series...");

        /*Crea la tabla de asociaciones de series si no existe*/
        vCreSerProd(sBD, con, "serieprod");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de generales...");

        /*Crea la tabla de generales si no existe*/
        vCreGrals(sBD, con, "grals");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de fabricantes...");

        /*Crea la tabla de fabricantes si no existe*/
        vCreFabs(sBD, con, "fabs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de colores...");

        /*Crea la tabla de colores si no existe*/
        vCreColos(sBD, con, "colos");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de pesos...");

        /*Crea la tabla de pesos si no existe*/
        vCrePes(sBD, con, "pes");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de log de ubicacion adicional...");

        /*Crea la tabla de log de ubicación adicional en los prods si no existe*/
        vCreLogUbiAd(sBD, con, "logubiad");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de ubicacion adicional...");

        /*Crea la tabla de ubicación adicional en los prods si no existe*/
        vCreUbiAd(sBD, con, "ubiad");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de clasificaciones extra productos...");

        /*Crea la tabla de clasificaciones extra en prodcutos si no existe*/
        vCreClasProd(sBD, con, "clasprod");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de unidades...");

        /*Crea la tabla de unidades si no existe*/
        vCreUnids(sBD, con, "unids");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de almacenes...");

        /*Crea la tabla de almacenes si no existe*/
        vCreAlmas(sBD, con, "almas");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de máximos y mínimos para configuración...");

        /*Crea la tabla de maximosminimosconf si no existe*/
        vCreMaxMinConf(sBD, con, "maxminconf");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de conceptos...");

        /*Crea la tabla de conceptos si no existe*/
        vCreConcep(sBD, con, "conceps");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de monedas...");

        /*Crea la tabla de monedas si no existe*/
        vCreMons(sBD, con, "mons");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de productos...");

        /*Crea la tabla de productos si no existe*/
        vCreProds(sBD, con, "prods");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de medidas...");

        /*Crea la tabla de medidas si no existe*/
        vCreMeds(sBD, con, "meds");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de rutas...");

        /*Crea la tabla de rutas si no existe*/
        vCreRuts(sBD, con, "ruts");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de configuración general...");

        /*Crea la tabla de configuraciónes  generales si no existe*/
        vCreConfGral(sBD, con, "confgral");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de peticiones...");

        /*Crea la tabla de peticiones si no existe*/
        vCrePetis(sBD, con, "petis");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de chat corporativo...");

        /*Crea la tabla de chat corporativo si no existe*/
        vCreChat(sBD, con, "chat");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de cotizaciones...");

        /*Crea la tabla de cotizaciones si no existe*/
        vCreCots(sBD, con, "cots");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de cotizaciones...");

        /*Crea la tabla de partcotizaciones si no existe*/
        vCrePartCots(sBD, con, "partcot");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de proyectos...");

        /*Crea la tabla de proyectos si no existe*/
        vCreProys(sBD, con, "proys");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de compras...");

        /*Crea la tabla de compras si no existe*/
        vCreComp(sBD, con, "comprs");
        
        
        //Cambio alan
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de previo compras...");

        /*Crea la tabla de previo de compras si no existe*/
        vCrePrevComp(sBD, con, "prevcomprs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de ventas...");

        /*Crea la tabla de ventas si no existe*/
        vCreVta(sBD, con, "vtas");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de compras...");

        /*Crea la tabla de partcompras si no existe*/
        vCrePartComps(sBD, con, "partcomprs");
        
        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas del previo de compras...");

        /*Crea la tabla de partcompras si no existe*/
        vCrePartComps(sBD, con, "partprevcomprs");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de ventas...");

        /*Crea la tabla de partvta si no existe*/
        vCrePartVta(sBD, con, "partvta");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de monitor de inventarios...");

        /*Crea la tabla de monitordeinventarios si no existe*/
        vCreMonInv(sBD, con, "moninven");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de transpasos...");

        /*Crea la tabla de traspasos si no existe*/
        vCreTrasp(sBD, con, "traspas");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de ingresos...");

        /*Crea la tabla de ingresos si no existe*/
        vCreIng(sBD, con, "ingres");

        /*Muestra en el campo lo que se esta creando en este momento*/
        jTInf.setText("Creando tabla de partidas de cotizaciones de JPS2...");

        /*Termina la transacción*/
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
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        //Cierra la base de datos
        Star.iCierrBas(con);        

    }/*Fin de private void vCreaTTabsBs()*/


    /*Crea la tabla de consecutivos si no existe*/
    private void vCreConsecs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ      = "";

        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de consecutivos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de consecutivos*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`ser`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`tip`         VARCHAR(10) NOT NULL,\n"
                        + "`export`      BIT DEFAULT 0,\n"
                        + "`consec`      INT(11) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de los previos de compra*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,     ser,         Descrip,             consec,       estac,      sucu,      nocaj) "
                                    + "VALUES(  'PREV',   'PREV',      'PREVIOS DE COMPRA', 0,           'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta los consecutivos de las notas de crédito inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        Descrip,                    consec,      estac,      sucu,      nocaj) "
                                    + "VALUES(  'NOTC',  'NOTC',     'NOTAS DE CRÉDITO CLIENTES', 0,           'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta los consecutivos de las notas de crédito de los proveedores inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        Descrip,                         consec,      estac,      sucu,      nocaj) "
                                    + "VALUES(  'NOTP',  'NOTP',     'NOTAS DE CRÉDITO PROVEEDORES',  0,           'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta los consecutivos de las facturas inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        Descrip,       consec,      estac,      sucu,      nocaj) "
                        + "VALUES(  'FAC', 'FAC',     'FACTURAS',     0,           'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de los tickets inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        descrip,       consec,     estac,       sucu,       nocaj) "
                        + "VALUES(      'TIK', 'TIK',     'TICKETS',      0,          'INICIAL',   'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de las cotizaciones inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        descrip,       consec,     estac,       sucu,       nocaj) "
                        + "VALUES(      'COT', 'COT',     'COTIZACIONES',0,          'INICIAL',   'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de las empresas inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,   ser,        descrip,         consec,    estac,    sucu,      nocaj) "
                        + "VALUES(  'EMP', 'EMP',     'EMPRESAS',    0,           'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de los proveedores inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,    ser,       descrip,        consec,         estac,      sucu,      nocaj) "
                                    + "VALUES(  'PROV', 'PROV',     'PROVEEDORES',  0,             'INICIAL',  'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de las remisiones inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,    ser,       descrip,      consec,      estac,      sucu,      nocaj) "
                        + "VALUES(  'REM',  'REM',     'REMISIONES',  0,          'INICIAL',  'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de las compras inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,    ser,       descrip,    consec,      estac,      sucu,      nocaj) "
                        + "VALUES(  'COMP', 'COMP',    'COMPRAS',  0,           'INICIAL',  'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los consecutivos de las ordenes inicialmente*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( tip,    ser,       descrip,            consec,      estac,      sucu,      nocaj) "
                        + "VALUES(  'ORDC', 'ORDC',    'ORDENES COMPRA',   0,           'INICIAL',  'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreaConsecs()*/


    /*Crea la tabla de cumpleaños si no existe*/
    private void vCreCumple(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de cumpleaños no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`de`          VARCHAR(150) NOT NULL,\n"
                        + "`a`           VARCHAR(150) NOT NULL,\n"
                        + "`fcumple`     VARCHAR(30) NOT NULL,\n"
                        + "`codemp`      VARCHAR(30) NOT NULL,\n"
                        + "`ser`         VARCHAR(30) NOT NULL,\n"
                        + "`estad`       bit NOT NULL,\n"
                        + "`usuario`     VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`anio`        VARCHAR(10) NULL DEFAULT '',\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`fhoy`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codemp` (`codemp`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el registro que será el maestro para todos saber si ya están insertados en ese día o no*/
            try 
            {
                sQ = "INSERT INTO cumple (de,      a,      fcumple,       codemp,  ser,    estad,    usuario,  sucu,      nocaj,     fhoy,      falt) "
                                + "VALUES('MAEST', 'MAEST','',           'MAEST', 'MAEST', 0,        'INICIAL', 'INICIAL', 'INICIAL', CURDATE(), now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException e) 
            {
                /*Agrega en el log*/
                Login.vLog(e.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");               
                
                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreaCumple()*/


    /*Crea la tabla de log almacenes si no existe*/
    private void vCreLogAlmas(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogAlmas()*/


    /*Crea la tabla de log zona si no existe*/
    private void vCreLogZon(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogZon()*/
    
    
    /*Crea la tabla de log activo fijo si no existe*/
    private void vCreLogActFij(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogActFij()*/
    
    
    /*Crea la tabla de log activo fijo si no existe*/
    private void vCreLogActFijTip(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogActFijTip()*/
    
    
    /*Crea la tabla de log de conceptos notas de crédito si no existe*/
    private void vCreLogConcNot(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogConcNot()*/
    
    
    /*Crea la tabla de log de conceptos de pago si no existe*/
    private void vCreLogConcPag(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogConcPag()*/
    
    
    /*Crea la tabla de log de garantía si no existe*/
    private void vCreLogGara(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogGara()*/
    
    
    /*Crea la tabla de log de giros si no existe*/
    private void vCreLogGir(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogGir()*/
    
    
    /*Crea la tabla de log clasificación proveedores si no existe*/
    private void vCreLogClasProv(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");
                
                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogClasProv()*/
    
    
    /*Crea la tabla de log clasemp si no existe*/
    private void vCreLogClasEmp(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogClasEmp()*/
    
    
    /*Crea la tabla de log catálogo general si no existe*/
    private void vCreLogCatGral(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogCatGral()*/
    
    
    /*Crea la tabla de log de ubicacion adicional si no existe*/
    private void vCreLogUbiAd(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos coloca la bandera*/
            if(rs.next()) 
                bSi = true;    
            
        }
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `cod` (`cod`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogUbiAd()*/


    /*Crea la tabla de log anaqueles si no existe*/
    private void vCreLogAnaq(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogAnaq()*/


    /*Crea la tabla de log conceptos si no existe*/
    private void vCreLogConcep(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogConcep()*/


    /*Crea la tabla de log impues si no existe*/
    private void vCreLogImpue(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandaera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`val`         FLOAT NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogImpue()*/


    /*Crea la tabla de log de monedas si no existe*/
    private void vCreLogMons(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11)         NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30)     NOT NULL,\n"
                        + "`descrip`     VARCHAR(255)    NOT NULL,\n"
                        + "`accio`       VARCHAR(30)     NOT NULL,\n"
                        + "`estac`       VARCHAR(30)     NOT NULL,\n"
                        + "`sucu`        VARCHAR(30)     NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30)     NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP       NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY   (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogMons()*/


    /*Crea la tabla de log de unidades si no existe*/
    private void vCreLogUnid(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la taba de consecutivos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())                 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogUnid()*/


    /*Crea la tabla de log medidas si no existe*/
    private void vCreLogMed(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de log de medidas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogMed()*/


    /*Crea la tabla de log pesos no existe*/
    private void vCreLogPes(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogPes()*/


    /*Crea la tabla de log tipos no existe*/
    private void vCreLogTip(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogTip()*/
    
    
    /*Crea la tabla de log de clasificación extra si no existe*/
    private void vCreLogClasExt(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de log de clasificaciones existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next())                             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogClasExt()*/


    /*Crea la tabla de log colores si no existe*/
    private void vCreLogColo(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de log de colores existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogColo()*/


    /*Crea la tabla de log fabricantes si no existe*/
    private void vCreLogFabs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de log de fabricantes existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos y coloca la bandera*/
            if(rs.next())                
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogFabs()*/


    /*Crea la tabla de log marcas si no existe*/
    private void vCreLogMarcs(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de log de marcas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogMarcs()*/


    /*Crea la tabla de log tallas si no existe*/
    private void vCreLogTall(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogTall()*/
    
    
    /*Crea la tabla de log Modelos si no existe*/
    private void vCreLogMod(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogMod()*/
    
    
    /*Crea la tabla de log líneas si no existe*/
    private void vCreLogLins(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de log de líneas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())                
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogLins()*/


    /*Crea la tabla de log lugares si no existe*/
    private void vCreLogLugs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())                             
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`    VARCHAR(255) NOT NULL,\n"
                        + "`accio`      VARCHAR(30) NOT NULL,\n"
                        + "`estac`      VARCHAR(30) NOT NULL,\n"
                        + "`sucu`       VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`      VARCHAR(30) NOT NULL,\n"
                        + "`extr1`      VARCHAR(255) NULL,\n"
                        + "`extr2`      VARCHAR(255) NULL,\n"
                        + "`extr3`      VARCHAR(255) NULL,\n"
                        + "`falt`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogLugs()*/


    /*Crea la tabla de log kits si no existe*/
    private void vCreLogKit(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`kit`         VARCHAR(30) NOT NULL,\n"
                        + "`prod`        VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogKit()*/


    /*Crea la tabla de log prods si no existe*/
    private void vCreLogProds(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`       INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`         VARCHAR(30) NOT NULL,\n"
                        + "`descrip`     VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogProds()*/


    /*Crea la tabla de log empresas si no existe*/
    private void vCreLogEmps(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera en true*/
            if(rs.next())                
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`emp`         VARCHAR(30) NOT NULL,\n"
                        + "`ser`         VARCHAR(30) NOT NULL,\n"
                        + "`nom`         VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogEmps()*/


    /*Crea la tabla de log proveedores si no existe*/
    private void vCreLogProvs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement       st;
        ResultSet       rs;
        String          sQ = "";

        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera en true*/
            if(rs.next())                
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS `" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`      INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prov`        VARCHAR(30) NOT NULL,\n"
                        + "`ser`         VARCHAR(30) NOT NULL,\n"
                        + "`nom`         VARCHAR(255) NOT NULL,\n"
                        + "`accio`       VARCHAR(30) NOT NULL,\n"
                        + "`estac`       VARCHAR(30) NOT NULL,\n"
                        + "`sucu`        VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`       VARCHAR(30) NOT NULL,\n"
                        + "`extr1`       VARCHAR(255) NULL,\n"
                        + "`extr2`       VARCHAR(255) NULL,\n"
                        + "`extr3`       VARCHAR(255) NULL,\n"
                        + "`falt`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`accio`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogProvs()*/


    /*Crea la tabla de configuraciones si no existe*/
    private void vCreConfGral(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ  = "";

        
        
        
        
        
        /*Comprueba si la taba de configuraciones existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de configuraciones no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de configuraciones*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "  `id_id`              INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "  `clasif`             VARCHAR(45) NOT NULL,\n"
                        + "  `conf`               VARCHAR(45) NOT NULL,\n"
                        + "  `val`                INT(11) NOT NULL,\n"
                        + "  `nume`               FLOAT DEFAULT 0,\n"
                        + "  `falt`               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "  `fmod`               TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "  `estac`              VARCHAR(30) NOT NULL,\n"
                        + "  `dia`                INT NULL DEFAULT 0,\n"
                        + "  `envia`              TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "  `extr`               VARCHAR(1000) NULL DEFAULT '',\n"
                        + "  `asun`               VARCHAR(255) NULL DEFAULT '',\n"
                        + "  `anio`               INT NULL DEFAULT 0,\n"
                        + "  `sucu`               VARCHAR(30) NOT NULL,\n"
                        + "  `nocaj`              VARCHAR(30) NOT NULL,\n"
                        + "  `export`             BIT DEFAULT 0,\n"
                        + "  `extr1`              VARCHAR(255) NULL,\n"
                        + "  `extr2`              VARCHAR(255) NULL,\n"
                        + "  `extr3`              VARCHAR(255) NULL,\n"
                        + "  PRIMARY KEY (`id_id`),\n"
                        + "  UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `clasif` (`clasif`), KEY `conf` (`conf`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta las configuraciones iniciales de configuración*/
            try 
            {
                /*Configuraciones de previos de compra*/
                /*Inserta configuración por usuario*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevporusuario',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración moneda nacional*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevmonac',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración modificar decripcion*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevmodesc',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración modificar precio*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevmodprec',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración solamente dar previo a una serie una vez*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevunavezser',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración es obligatorio el uso de series*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'prev',     'prevobligarser',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Configuraciones de respaldos*/

                /*Inserta configuración inicial para saber El usuario inicial de respaldos*/
                sQ = "INSERT INTO confgral (extr,   clasif,  conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('RESP', 'resp',  'respaut',      1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Configuraciones de inventarios*/
                
                //Inserta configuración inicial para que se puedan sacar o no con existencias del iventario
                sQ = "INSERT INTO confgral (    dia,   clasif, conf,                val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'inv',  'esexitmov',         1,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                //Inserta configuración inicial para que se pueda o no hacer transpasos entre almacenes con existencias
                sQ = "INSERT INTO confgral (    dia,   clasif, conf,                  val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'inv',  'traspasexis',         1,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                //Inserta la configuracion inicial para que se pueda o no insertar series repetidas tanto en productos diferentes como iguales
                sQ = "INSERT INTO confgral (    dia,   clasif, conf,               val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'inv',  'igualser',         0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Configuraciones de sistema*/
                
                //Inserta configuración inicial para el redondeo de las cantidades
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,          val,   extr,    falt, fmod,   estac,     sucu,      nocaj,     nume) "
                                    + "VALUES(  0,     'sist',  'redon',       0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL', 0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                //Inserta configuración inicial para que se pueda o no cambiar el tipo de cambio desde todos lados o solo desde las monedas
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,              val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'tipcamtod',       0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                //Inserta configuración inicial para que muestre o no el mensaje cuando un usuario tiene o no correo configurado
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,               val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'mostmsjusr',       1,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                //Inserta configuración inicial para que los usuarios puedan multilogerae
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,               val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'usrmulti',         1,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para la ruta a la herramienta de calculadora*/
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'calc',         0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para la ruta a la herramienta de cuaderno*/
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'cuader',       0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para la ruta a la aplicación favorita*/
                sQ = "INSERT INTO confgral (    dia,   clasif,  conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  0,     'sist',  'apfavo',       0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que no se haga envío de correos de agradecimientos a las empresas*/
                sQ = "INSERT INTO confgral (    dia,    clasif,  conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  30,     'sist',  'agrad',        0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que no se haga envío de correos de cumpleaños a las empresas*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'sist',     'cumple',        0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que no se deslogie por inactividad*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'sist',     'dlogin',        0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Configuraciones remisiones*/
                //CAMBIO ALAN
                /*Inserta configuración inicial para que solo se pueda vender una sola vez esa serie*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'venunaser',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que no se pueda vender una serie que esta cotizada*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'vencotunaser',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que solo se pueda vender una sola vez esa serie en punto de venta*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'venunaserpv',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que no se pueda vender una serie que esta cotizada en punto de venta*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'vencotunaserpv',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que se muestre el logo de la empresa en las remisiones*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'logorem',       1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se pueda vender sobre límite de Crédito a las empresas en remisiones*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'slimcredrem',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan ver o no las remisiones en el punto de venta*/
                sQ = "INSERT INTO confgral (    clasif,     conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'vremptovta',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no imprimir remisiones desde el punto de venta*/
                sQ = "INSERT INTO confgral (    clasif,     conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'imprempto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no cancelar remisiones desde el punto de venta*/
                sQ = "INSERT INTO confgral (    clasif,     conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                                    + "VALUES(  'vtas',     'canrempto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre el PDF de cancelación de remisiones o no*/
                sQ = "INSERT INTO confgral (    clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj) "
                                    + "VALUES(  'rems',  'vercanrem', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se guarde el PDF de cancelación de remisiones o no*/
                sQ = "INSERT INTO confgral (    clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj) "
                                    + "VALUES(  'rems',  'guapdfcan', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Configuraciones de ventas*/

                /*Inserta configuración para saber si se puede o no modificar la descripción en la cotización*/
                sQ = "INSERT INTO confgral (clasif,  conf,           val,   falt, fmod,   estac,       sucu,       nocaj,     extr) "
                                 + "VALUES('vtas',   'moddescrip',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL', 'SYS')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial de la moneda de la factura fija*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'monfacfij',    0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial de la serie de factura fija*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'serfacfij',    0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial de lo permitido de facturar tanto en ventas como en punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj,     extr,  nume) "
                                 + "VALUES('vtas',  'minfac',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL', 'SYS', 100)";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial del almacén predeterminado de ventas*/
                sQ = "INSERT INTO confgral (clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj,     extr) "
                                 + "VALUES('vtas',  'almavtaf',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL', 'SYS')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se pueda o no hacer devoluciones desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'devvtaspto',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se pueda o no hacer devoluciones parciales desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,           val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'devpvtaspto',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se pueda o no modificar la lista de precios en las facturas*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'modlistfac',   1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se muestre o no la garantía en la descripción de las facturas*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'garandescfac', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se muestre o no la garantía en la descripción en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'garandescpto', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar la descripción del producto en la factura*/
                sQ = "INSERT INTO confgral (clasif,  conf,         val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'descrip', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar el precio del producto en la factura*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('vtas',  'modprec', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para saber el almacén con el que se trabajara en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,     falt, fmod,   estac,     sucu,      nocaj) "
                                 + "VALUES('vtas',  'almapto',      0,     'SYS',    now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para la serie de las facturas en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,          val,   extr,     falt, fmod,   estac,     sucu,      nocaj) "
                                 + "VALUES('vtas',  'serfac',      0,     'FAC',    now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para la serie de los tickets en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,     falt, fmod,   estac,     sucu,      nocaj) "
                                 + "VALUES('vtas',  'sertic',       0,     'TIK',    now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para la serie de las remisiones en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,     falt, fmod,   estac,     sucu,      nocaj) "
                                 + "VALUES('vtas',  'serrem',       0,     'REM',    now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que solo se muestre las ventas del día de hoy en el bùscador del punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,          val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'hoyvtap',     0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para saber si se tiene que hacer corte X automáticamente al cambio de cada turno*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'autcortx',     0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para saber si el sistema esta en modo de pruebas o no*/
                sQ = "INSERT INTO confgral (clasif, conf,       val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'modp',     0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que pida clave de seguridad para facturar*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'clavsegfac',   0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que pida clave de seguridad para facturar en punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'clavsegfacp',   0,     '',      now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se inserte automáticamente dinero en el cajón o no*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   extr,    falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'insautcaj',    0,     '0',     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se pueda o no chatear desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'chatptoc',     0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no ver los mensajes en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vmsjpto',      0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se guarden los PDF en disco o no en las facturas*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'guapdfcanf',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se muestre en pantalla el ticket cancelado o no en las facturas*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vercanvtaf',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se guarden los PDF en disco o no en punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'guapdfcan',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se muestre en pantalla el ticket cancelado o no en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vercanvta',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que pida o no clave de administrador al cancelar ventas en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'admcanvtas',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no configurar la descripción desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'descrippto',   0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no dar de alta empresas desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,         val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'empspto',     1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no cancelar facturas desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'canfacpto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no cancelar tickets desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'canticpto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no imprimir facturas desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'impfacpto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan o no imprimir tickets desde el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'impticpto',    1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan ver o no las facturas en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vfacptovta',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se puedan ver o no los tickets en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,           val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vticptovta',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se guarden los cortes X automáticamente*/
                sQ = "INSERT INTO confgral (clasif, conf,      val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'cortxa',  0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se guarden los cortes Z automáticamente*/
                sQ = "INSERT INTO confgral (    clasif, conf,      val,   falt, fmod,   estac,     sucu,      nocaj) "
                                      + "VALUES('vtas',  'cortza',  1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que solo se muestren las ventas de cada usuario*/
                sQ = "INSERT INTO confgral (clasif, conf,        val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'vtasxusr',  0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se pueda vender sobre límite de Crédito a las empresas en punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'slimcredpvta',  1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se pueda vender sobre límite de Crédito a las empresas en las facturas*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'slimcredfac',   1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se muestre el logo de la empresa en la factura*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'logofac',       1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta configuración inicial para que se muestre el logo de la empresa en los tickets*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'logotik',       1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no aplicar la lista de precios de la empresa en las facturas*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                        + "VALUES('vtas',  'alistpreclifac', 1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no aplicar la lista de precios de la empresa en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'alistpreclipvta', 1,    now(),now(),  'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre o no el punto de venta al iniciar el sistema*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'initpvta',       0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre o no el mensaje de existencia negativa en las facturas*/
                sQ = "INSERT INTO confgral (clasif, conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'msjexistnegfac', 0,     now(),now(), 'INICIAL',    'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre o no el mensaje de existencia negativa en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'msjexistnegpvta',0,     now(),now(),  'INICIAL',   'INICIAL',  'NOCAJ')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no vender con existencia en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'vendsinexistpvta', 1,   now(),now(), 'INICIAL',    'INICIAL',   'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no vender con existencia en facturas*/
                sQ = "INSERT INTO confgral (clasif,  conf,             val,   falt, fmod,   estac,      sucu,       nocaj) "
                        + "VALUES('vtas',  'vendsinexistfac', 1,     now(), now(), 'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre el catálogo general en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,             val,   falt, fmod,   estac,      sucu,       nocaj) "
                        + "VALUES('vtas',  'catgralpvta',     0,     now(), now(), 'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre el catálogo general en las facturas*/
                sQ = "INSERT INTO confgral (clasif,  conf,             val,   falt, fmod,   estac,      sucu,       nocaj) "
                                 + "VALUES('vtas',  'catgralfac',      0,     now(), now(), 'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que a las empresas se les pueda o no vender con otra moneda que no sea la nacional*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,      sucu,       nocaj) "
                        + "VALUES('vtas',  'otramon',      1,     now(), now(), 'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Configuraciones de cotizaciones*/
                //CAMBIO ALAN
                /*Inserta configuración inicial para que solo se pueda cotizar una sola vez esa serie*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                   + "VALUES(  'cots',     'cotunaser',       0,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que solo se pueda cotizar con serie*/
                sQ = "INSERT INTO confgral (    clasif,     conf,            val,   falt, fmod,   estac,     sucu,      nocaj) "
                                   + "VALUES(  'cots',     'cotconaser',       1,     now(), now(), 'INICIAL', 'INICIAL', 'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración para saber si se puede o no modificar la descripción en la cotización*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj,     extr) "
                                 + "VALUES('cots',  'moddescrip',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL', 'SYS')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial del almacén para cotizaciones*/
                sQ = "INSERT INTO confgral (clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj,     extr) "
                                 + "VALUES('cots',  'almavtac',   0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL', 'SYS')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta configuración inicial para que se pueda o no modificar la lista de precios en las cotizaciones*/
                sQ = "INSERT INTO confgral (clasif,  conf,          val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('cots',  'modlistcot',   1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se agregue la garantía en la descripción de la cotización o no*/
                sQ = "INSERT INTO confgral (clasif,  conf,         val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('cots',  'garadesccot', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar la descripción del producto en la cotización*/
                sQ = "INSERT INTO confgral (clasif,  conf,         val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('cots',  'descrip', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar el precio del producto en la cotización*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('cots',  'modprec', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se les pueda o no cotizar en otra moneda distINTa a la nacional*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                                  + "VALUES('cots',  'otramon',  1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que solo se puedan ver las cotizaciones de cada usuario*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('cots',  'cotsxusr', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no aplicar la lista de precios de la empresa en las cotizaciones*/
                sQ = "INSERT INTO confgral (clasif,  conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('cots',  'alistpreclicot', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre o no el mensaje de existencia negativa en la cotización*/
                sQ = "INSERT INTO confgral (clasif,  conf,            val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('cots',  'msjexistnegcot', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se pueda o no vender con existencia en cotizaciones*/
                sQ = "INSERT INTO confgral (clasif,  conf,             val,   falt, fmod,   estac,      sucu,       nocaj) "
                        + "VALUES('cots',  'vendsinexistcot', 1,     now(), now(), 'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestre o no la barra lateral en el punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,    val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'barlat', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se muestren o no imágenes en las líneas del punto de venta*/
                sQ = "INSERT INTO confgral (clasif,  conf,    val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('vtas',  'imglin', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Configuración de compras*/
                
                /*Inserta condiguración inicial para inicie en órden de compra las compras*/
                sQ = "INSERT INTO confgral (clasif,  conf,     val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('comps',  'iniord', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar la descripción del producto en la compra*/
                sQ = "INSERT INTO confgral (clasif,  conf,         val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('comps',  'descrip', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que pueda o no modificar el precio del producto en la compra*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                                 + "VALUES('comps',  'modprec', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta condiguración inicial para que se muestre el PDF de cancelación de compra o no*/
                sQ = "INSERT INTO confgral (clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('comps',  'vercancom', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se guarde el PDF de cancelación de compra o no*/
                sQ = "INSERT INTO confgral (clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('comps',  'guapdfcan', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que se puedan solo ver las compras de cada usuario*/
                sQ = "INSERT INTO confgral (clasif,  conf,        val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('comps',  'compsxusr', 0,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta condiguración inicial para que todos o ninguno de los proveedores pueda comprar en una moneda distINTa a la nacional*/
                sQ = "INSERT INTO confgral (clasif,  conf,      val,   falt, fmod,   estac,       sucu,       nocaj) "
                        + "VALUES('comps',  'otramon', 1,     now(), now(), 'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreConfGral()*/


    /*Crea la tabla de cotizaciones si no existe*/
    private void vCreCots(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de cotizaciones existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de cotizaciones no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de cotizaciones*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codcot`               VARCHAR(30) NOT NULL,\n"
                        + "`proy`                 VARCHAR(30) NOT NULL,\n"
                        + "`motiv`                VARCHAR(255) DEFAULT '',\n"
                        + "`noser`                VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"                        
                        + "`totdescu`             FLOAT NOT NULL,\n"
                        + "`totcost`              FLOAT NOT NULL,\n"
                        + "`vta`                  INT(11) DEFAULT 0,\n"
                        + "`norefer`              VARCHAR(30) DEFAULT '',\n"
                        + "`tipcam`               FLOAT NOT NULL,\n"
                        + "`mon`                  VARCHAR(30) NOT NULL,\n"
                        + "`noservta`             VARCHAR(30) DEFAULT '',\n"
                        + "`codemp`               VARCHAR(30) NOT NULL,\n"
                        + "`observ`               VARCHAR(255) NOT NULL,\n"
                        + "`estac`                VARCHAR(255) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"                        
                        + "`subtotgral`           FLOAT NOT NULL,\n"
                        + "`subtotgral2`          FLOAT NOT NULL,\n"
                        + "`subtot`               FLOAT NOT NULL,\n"
                        + "`manobr`               FLOAT NOT NULL,\n"
                        + "`impue`                FLOAT NOT NULL,\n"
                        + "`subtotmat`            FLOAT NOT NULL,\n"
                        + "`subtotmat2`           FLOAT NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"
                        + "`descrip`              VARCHAR(255) NOT NULL,\n"
                        + "`estad`                VARCHAR(10) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`fdoc`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fentre`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fvenc`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `codcot_UNIQUE` (`codcot`), KEY `codcot` (`codcot`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCots()*/


    /*Crea la tabla de partCotizaciones si no existe*/
    private void vCrePartCots(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de partCotizaciones existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de partCotizaciones no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de partCotizaciones*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codcot`                   VARCHAR(30) NOT NULL,\n"
                        + "`prod`                     VARCHAR(30) NOT NULL,\n"
                        + "`tipcam`                   FLOAT NOT NULL,\n"
                        + "`cost`                     FLOAT DEFAULT 0,\n"                        
                        + "`unid`                     VARCHAR(30) NOT NULL,\n"
                        + "`mon`                      VARCHAR(30) NOT NULL,\n"
                        + "`cant`                     INT(11) NOT NULL,\n"                        
                        + "`descrip`                  VARCHAR(255) NOT NULL,\n"
                        + "`eskit`                    BIT DEFAULT 0,\n"
                        + "`serprod`                  VARCHAR(30) NOT NULL,\n"
                        + "`comenser`                 VARCHAR(255) NOT NULL,\n"
                        + "`tall`                     VARCHAR(30) DEFAULT '',\n"
                        + "`fentre`                   VARCHAR(30) DEFAULT '',\n"
                        + "`codimpue`                 VARCHAR(30) NOT NULL,\n"
                        + "`colo`                     VARCHAR(30) DEFAULT '',\n"
                        + "`garan`                    VARCHAR(255) DEFAULT '',\n"
                        + "`list`                     INT(11) DEFAULT 1,\n"
                        + "`lot`                      VARCHAR(255) DEFAULT '',\n"
                        + "`pedimen`                  VARCHAR(255) DEFAULT '',\n"
                        + "`fcadu`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`pre`                      FLOAT NOT NULL,\n"
                        + "`pre2`                     FLOAT NOT NULL,\n"
                        + "`desc1`                    FLOAT NOT NULL,\n"
                        + "`desc2`                    FLOAT NOT NULL,\n"
                        + "`desc3`                    FLOAT NOT NULL,\n"
                        + "`desc4`                    FLOAT NOT NULL,\n"
                        + "`desc5`                    FLOAT NOT NULL,\n"
                        + "`impo`                     FLOAT NOT NULL,\n"
                        + "`impo2`                    FLOAT NOT NULL,\n"
                        + "`impueval`                 FLOAT NOT NULL,\n"
                        + "`impueimpo`                FLOAT NOT NULL,\n"
                        + "`impueimpo2`               FLOAT NOT NULL,\n"
                        + "`impodesc`                 FLOAT DEFAULT 0,\n"
                        + "`alma`                     VARCHAR(30) NOT NULL,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"
                        + "`falt`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codcot` (`codcot`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePartCots()*/


    /*Crea la tabla de proyectos si no existe*/
    private void vCreProys(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de proyectos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de proyectos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de proyectos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`proy`                 VARCHAR(30) NOT NULL,\n"
                        + "`codemp`               VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"
                        + "`obra`                 VARCHAR(255) NOT NULL,\n"
                        + "`tipobr`               VARCHAR(100) NOT NULL,\n"
                        + "`descrip`              VARCHAR(255) NOT NULL,\n"
                        + "`plant`                VARCHAR(255) NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"
                        + "`subtot`               FLOAT NOT NULL,\n"
                        + "`iva`                  FLOAT NOT NULL,\n"
                        + "`otr`                  VARCHAR(255) NOT NULL,\n"
                        + "`ubigraf`              VARCHAR(350) NOT NULL,\n"
                        + "`ubic`                 VARCHAR(255) NOT NULL,\n"
                        + "`estad`                VARCHAR(10) NOT NULL,\n"
                        + "`estatu`               INT(11) DEFAULT '0',\n"
                        + "`iniobr`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`termobr`              TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`tiement`              VARCHAR(255) NOT NULL,\n"
                        + "`condpag`              VARCHAR(100) NOT NULL,\n"
                        + "`nompers`              VARCHAR(500) NOT NULL,\n"
                        + "`estac`                VARCHAR(255) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`proyvac`              INT(11) DEFAULT '0',\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `proy_UNIQUE` (`proy`), KEY `proy` (`proy`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreProys()*/


    /*Crea la tabla de compras si no existe*/
    private void vCreComp(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de compras existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos y coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de compras no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de compras*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`            INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codcomp`          VARCHAR(30) NOT NULL,\n"
                        + "`noser`            VARCHAR(30) NOT NULL,\n"
                        + "`prov`             VARCHAR(30) NOT NULL,\n"                                                
                        + "`ser`              VARCHAR(30) NOT NULL,\n"
                        + "`notcred`          VARCHAR(30) DEFAULT '',\n"
                        + "`tipcam`           FLOAT NOT NULL,\n"
                        + "`mon`              VARCHAR(30) NOT NULL,\n"
                        + "`notcredpag`       VARCHAR(250)DEFAULT '',\n"
                        + "`concep`           VARCHAR(30) DEFAULT '',\n"
                        + "`ruta`             VARCHAR(500)DEFAULT '',\n"
                        + "`observ`           VARCHAR(255)DEFAULT '',\n"
                        + "`nomprov`          VARCHAR(255)NOT NULL,\n"
                        + "`metpag`           VARCHAR(42) DEFAULT '',\n"
                        + "`cta`              VARCHAR(42) DEFAULT '',\n"
                        + "`nodoc`            VARCHAR(50) NOT NULL,\n"
                        + "`fdoc`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fent`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`subtot`           FLOAT NOT NULL,\n"
                        + "`impue`            FLOAT NOT NULL,\n"
                        + "`tip`              VARCHAR(30) DEFAULT 'COMP',\n"
                        + "`tot`              FLOAT NOT NULL,\n"
                        + "`archpdf`          BIT DEFAULT 0,\n"
                        + "`archxml`          BIT DEFAULT 0,\n"
                        + "`recib`            FLOAT DEFAULT 0,\n"
                        + "`contra`           INT(11) DEFAULT 0,\n"
                        + "`estado`           VARCHAR(10) NOT NULL,\n"
                        + "`motiv`            VARCHAR(255) NOT NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`codord`           VARCHAR(30) NULL DEFAULT '',\n"
                        + "`falt`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fvenc`            TIMESTAMP NOT NULL,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codcomp` (`codcomp`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreComp()*/
    
    /*Crea la tabla de compras si no existe*/
    private void vCrePrevComp(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de compras existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos y coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de previos de compra no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de compras*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`            INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codprevcomp`      VARCHAR(30) NOT NULL,\n"
                        + "`motiv`            VARCHAR(255) NOT NULL,\n"
                        + "`noser`            VARCHAR(30) NOT NULL,\n"
                        + "`nodoc`            VARCHAR(30) DEFAULT '',\n"
                        + "`prov`             VARCHAR(30) NOT NULL,\n"
                        + "`ser`              VARCHAR(30) NOT NULL,\n"
                        + "`codcomp`          VARCHAR(30) DEFAULT '',\n"
                        + "`nosercomp`        VARCHAR(30) NOT NULL,\n"
                        + "`notcredpag`       VARCHAR(250) DEFAULT '',\n"
                        + "`tipcam`           FLOAT NOT NULL,\n"
                        + "`mon`              VARCHAR(30) NOT NULL,\n"
                        + "`ruta`             VARCHAR(500)DEFAULT '',\n"
                        + "`observ`           VARCHAR(255)DEFAULT '',\n"
                        + "`subtot`           FLOAT NOT NULL,\n"
                        + "`impue`            FLOAT NOT NULL,\n"
                        + "`tip`              VARCHAR(30) DEFAULT 'PREV',\n"
                        + "`tot`              FLOAT NOT NULL,\n"
                        + "`estado`           VARCHAR(10) NOT NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`fdoc`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fent`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`falt`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fvenc`            TIMESTAMP NOT NULL,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codprevcomp` (`codprevcomp`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePrevComp()*/


    /*Crea la tabla de ventas si no existe*/
    private void vCreVta(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ  = "";

        
        
        
        /*Comprueba si la taba de ventas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de ventas no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de facturas*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`vta`                  INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`norefer`              VARCHAR(30) NOT NULL,\n"
                        + "`noser`                VARCHAR(30) NOT NULL,\n"
                        + "`mon`                  VARCHAR(30) NOT NULL,\n"
                        + "`vend`                 VARCHAR(30) NOT NULL,\n"                        
                        + "`codcot`               VARCHAR(30) DEFAULT '',\n"
                        + "`folfisc`              VARCHAR(100) DEFAULT '',\n"
                        + "`transid`              VARCHAR(100) DEFAULT '',\n"
                        + "`sell`                 VARCHAR(1000) DEFAULT '',\n"
                        + "`sellsat`              VARCHAR(1000) DEFAULT '',\n"
                        + "`certsat`              VARCHAR(1000) DEFAULT '',\n"
                        + "`lugexp`               VARCHAR(100) DEFAULT '',\n"
                        + "`regfisc`              VARCHAR(255) DEFAULT '',\n"
                        + "`cadori`               VARCHAR(2000) DEFAULT '',\n"
                        + "`cort`                 VARCHAR(5) DEFAULT 'N',\n"
                        + "`formpag`              VARCHAR(10) NOT NULL,\n"
                        + "`nocort`               INT(11) DEFAULT 0,\n"
                        + "`cierr`                INT(11) DEFAULT 0,\n"
                        + "`vtadevp`              INT(11) DEFAULT 0,\n"
                        + "`ptovta`               BIT DEFAULT 0,\n"
                        + "`catgral`              VARCHAR(500) DEFAULT '',\n"
                        + "`factu`                BIT DEFAULT 0,\n"
                        + "`tipcam`               FLOAT NOT NULL,\n"                        
                        + "`tipdoc`               VARCHAR(30) NOT NULL,\n"
                        + "`notcred`              VARCHAR(30) DEFAULT '',\n"
                        + "`notcredpag`           VARCHAR(250) DEFAULT '',\n"
                        + "`impnotcred`           FLOAT DEFAULT 0,\n"                        
                        + "`totdescu`             FLOAT DEFAULT 0,\n"                        
                        + "`totcost`              FLOAT NOT NULL,\n"
                        + "`totcostprom`          FLOAT DEFAULT 0,\n"
                        + "`totueps`              FLOAT DEFAULT 0,\n"
                        + "`totpeps`              FLOAT DEFAULT 0,\n"
                        + "`codemp`               VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"
                        + "`metpag`               VARCHAR(42) NOT NULL,\n"
                        + "`cta`                  VARCHAR(42) NOT NULL,\n"
                        + "`femi`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fent`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`subtot`               FLOAT NOT NULL,\n"
                        + "`impue`                FLOAT NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"                        
                        + "`tic`                  INT(11) NOT NULL,\n"
                        + "`estad`                VARCHAR(10) NOT NULL,\n"
                        + "`motiv`                VARCHAR(255) NOT NULL,\n"
                        + "`observ`               VARCHAR(1000) NOT NULL,\n"
                        + "`timbr`                bit NOT NULL,\n"                        
                        + "`autrecibde`           VARCHAR(255) DEFAULT '',\n"
                        + "`autmarc`              VARCHAR(255) DEFAULT '',\n"
                        + "`autmod`               VARCHAR(255) DEFAULT '',\n"
                        + "`autcolo`              VARCHAR(100) DEFAULT '',\n"
                        + "`autplacs`             VARCHAR(255) DEFAULT '',\n"
                        + "`autnom`               VARCHAR(255) DEFAULT '',\n"
                        + "`auttarcirc`           VARCHAR(255) DEFAULT '',\n"
                        + "`autnumlic`            VARCHAR(255) DEFAULT '',\n"
                        + "`auttel`               VARCHAR(255) DEFAULT '',\n"
                        + "`autdirpart`           VARCHAR(255) DEFAULT '',\n"
                        + "`autdirofi`            VARCHAR(255) DEFAULT '',\n"
                        + "`auttelofi`            VARCHAR(255) DEFAULT '',\n"
                        + "`autimpo`              FLOAT DEFAULT 0,\n"
                        + "`autmotiv`             VARCHAR(255) DEFAULT '',\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`fvenc`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`vta`),\n"
                        + "UNIQUE KEY `venta_UNIQUE` (`vta`), KEY `norefer` (`norefer`), KEY `noser` (`noser`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreVta()*/


    /*Crea la tabla de partidas de compra si no existe*/
    private void vCrePartComps(String sBD, Connection con, String sTabl) {
                
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
                
        /*Comprueba si la taba de partcompras existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de partcompras no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codcom`                   VARCHAR(30) NOT NULL,\n"
                        + "`alma`                     VARCHAR(30) NOT NULL,\n"
                        + "`prod`                     VARCHAR(255) NOT NULL,\n"
                        + "`cant`                     INT(11) NOT NULL,\n"
                        + "`cantlotpend`              FLOAT NOT NULL,\n"
                        + "`impue`                    FLOAT DEFAULT 0,\n"
                        + "`devs`                     INT(11) DEFAULT '0',\n"                        
                        + "`unid`                     VARCHAR(30) NOT NULL,\n"
                        + "`descrip`                  VARCHAR(255) NOT NULL,\n"                        
                        + "`cost`                     FLOAT NOT NULL,\n"
                        + "`costpro`                  FLOAT NOT NULL,\n"                        
                        + "`serprod`                  VARCHAR(30) NOT NULL,\n"
                        + "`tall`                     VARCHAR(30) DEFAULT '',\n"
                        + "`colo`                     VARCHAR(30) DEFAULT '',\n"
                        + "`garan`                    VARCHAR(255) NOT NULL,\n"
                        + "`comenser`                 VARCHAR(255) NOT NULL,\n"
                        + "`peps`                     FLOAT NULL DEFAULT 0,\n"                        
                        + "`ueps`                     FLOAT NULL DEFAULT 0,\n"
                        + "`descu`                    FLOAT NOT NULL,\n"
                        + "`descad`                   FLOAT NOT NULL,\n"
                        + "`recib`                    FLOAT NULL DEFAULT 0,\n"
                        + "`codimpue`                 VARCHAR(30) NOT NULL,\n"
                        + "`mon`                      VARCHAR(30) NOT NULL,\n"
                        + "`lot`                      VARCHAR(30) DEFAULT '',\n"
                        + "`pedimen`                  VARCHAR(30) DEFAULT '',\n"
                        + "`aplic`                    BIT DEFAULT 0,\n"
                        + "`idkit`                    INT(11) DEFAULT -1,\n"
                        + "`kitmae`                   BIT DEFAULT 0,\n"
                        + "`eskit`                    BIT DEFAULT 0,\n"
                        + "`impo`                     FLOAT NOT NULL,\n"
                        + "`tipcam`                   FLOAT NOT NULL,\n"
                        + "`flotvenc`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"
                        + "`falt`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codcom` (`codcom`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePartComps()*/


    /*Crea la tabla de partvta si no existe*/
    private void vCrePartVta(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de partvta existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de partvta no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de partvta*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`                     VARCHAR(255) NOT NULL,\n"
                        + "`vta`                      VARCHAR(30) NOT NULL,\n"
                        + "`tipdoc`                   VARCHAR(30) NOT NULL,\n"
                        + "`cant`                     FLOAT NOT NULL,\n"
                        + "`tipcam`                   FLOAT NOT NULL,\n"
                        + "`devs`                     FLOAT DEFAULT 0,\n"                                                
                        + "`garan`                    VARCHAR(255) DEFAULT '',\n"
                        + "`eskit`                    BIT DEFAULT 0,\n"
                        + "`kitmae`                   INT(11) DEFAULT 0,\n"
                        + "`idkit`                    INT(11) DEFAULT -1,\n"
                        + "`idlotped`                 INT(11) DEFAULT -1,\n"                        
                        + "`list`                     INT(11) DEFAULT 1,\n"
                        + "`unid`                     VARCHAR(30) NOT NULL,\n"
                        + "`codimpue`                 VARCHAR(30) NOT NULL,\n"
                        + "`alma`                     VARCHAR(30) NOT NULL,\n"
                        + "`serprod`                  VARCHAR(30) NOT NULL,\n"
                        + "`comenser`                 VARCHAR(255) NOT NULL,\n"
                        + "`descrip`                  VARCHAR(255) NOT NULL,\n"
                        + "`pre`                      FLOAT NOT NULL,\n"
                        + "`descu`                    FLOAT NOT NULL,\n"
                        + "`costprom`                 FLOAT DEFAULT 0,\n"
                        + "`cost`                     FLOAT DEFAULT 0,\n"
                        + "`idultcost`                INT(11) DEFAULT 0,\n"                                                
                        + "`peps`                     FLOAT NOT NULL,\n"
                        + "`idpeps`                   VARCHAR(2000) DEFAULT '',\n"
                        + "`ueps`                     FLOAT NOT NULL,\n"
                        + "`idueps`                   VARCHAR(2000) DEFAULT '',\n"
                        + "`mon`                      VARCHAR(30) NOT NULL,\n"
                        + "`lot`                      VARCHAR(255) DEFAULT '',\n"
                        + "`pedimen`                  VARCHAR(255) DEFAULT '',\n"
                        + "`fcadu`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`impo`                     FLOAT NOT NULL,\n"
                        + "`impue`                    INT(11) NOT NULL,\n"
                        + "`tall`                     VARCHAR(30) DEFAULT '',\n"
                        + "`colo`                     VARCHAR(30) DEFAULT '',\n"
                        + "`cantentre`                FLOAT DEFAULT 0,\n"
                        + "`entrenow`                 FLOAT DEFAULT 0,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"                                                
                        + "`fentre`                   TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `vta` (`vta`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePartVta()*/


    /*Crea la tabla de monitordeinventarios si no existe*/
    private void vCreMonInv(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de monitordeinventarios existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())            
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de monitordeinventarios no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de monitordeinventarios*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                            INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`                             VARCHAR(255) NOT NULL,\n"
                        + "`cant`                             FLOAT NOT NULL,\n"
                        + "`descrip`                          VARCHAR(255) NOT NULL,\n"
                        + "`alma`                             VARCHAR(30) NOT NULL,\n"
                        + "`unid`                             VARCHAR(30) NOT NULL,\n"
                        + "`nodoc`                            VARCHAR(30) NOT NULL,\n"
                        + "`noser`                            VARCHAR(30) NOT NULL,\n"
                        + "`concep`                           VARCHAR(50) NOT NULL,\n"
                        + "`estac`                            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                            VARCHAR(30) NOT NULL,\n"
                        + "`emp`                              VARCHAR(30) NOT NULL,\n"
                        + "`entsal`                           BIT NOT NULL,\n"
                        + "`export`                           BIT DEFAULT 0,\n"
                        + "`extr1`                            VARCHAR(255) NULL,\n"
                        + "`extr2`                            VARCHAR(255) NULL,\n"
                        + "`extr3`                            VARCHAR(255) NULL,\n"
                        + "`falt`                             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `alma` (`alma`)\n"
                        + ") ";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMonInv()*/


    /*Crea la tabla de traspasos si no existe*/
    private void vCreTrasp(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de traspasos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de traspasos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de traspasos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`                 VARCHAR(255) NOT NULL,\n"
                        + "`alma`                 VARCHAR(255) NOT NULL,\n"
                        + "`concep`               VARCHAR(50) NOT NULL,\n"
                        + "`unid`                 VARCHAR(30) NOT NULL,\n"
                        + "`cantsal`              FLOAT NOT NULL,\n"
                        + "`almaa`                VARCHAR(30) NOT NULL,\n"
                        + "`cantent`              FLOAT NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `alma` (`alma`)\n"
                        + ") ";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTrasp()*/


    /*Crea la tabla de ingresos si no existe*/
    private void vCreIng(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de ingresos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de ingresos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de ingresos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`                 VARCHAR(255) NOT NULL,\n"
                        + "`alma`                 VARCHAR(255) NOT NULL,\n"
                        + "`unid`                 VARCHAR(255) NOT NULL,\n"
                        + "`concep`               VARCHAR(50) NOT NULL,\n"
                        + "`cant`                 FLOAT NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`entsal`               BIT NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `alma` (`alma`)\n"
                        + ") ";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreIng()*/


    /*Crea la tabla de fabricantes si no existe*/
    private void vCreFabs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de lineas existe*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de fabricantes no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `fab_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos fabricantes*/
            try 
            {
                /*Inserta mfabricante genérico*/
                sQ = "INSERT INTO " + sTabl + " ( cod,          descrip,                estac,      sucu,       nocaj ) "
                                        + "VALUES('SYS',        'FABRICANTE GENÉRICO',  'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de fabricantes*/
            try 
            {
                sQ = "INSERT INTO logfabs(cod,  descrip,               accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS','FABRICANTE GENÉRICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreFabs()*/


    /*Crea la tabla de pesos si no existe*/
    private void vCrePes(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de pesos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de pesos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algnos pesos*/
            try 
            {
                /*Inserta algunos pesos*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,     estac,     sucu,       nocaj) "
                                        + "VALUES(  'KR',       'KILOGRAMO',     'INICIAL', 'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + " ( cod,      descrip, estac,      sucu,       nocaj) "
                                        + "VALUES('GR',     'GRAMO',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "(        cod,   descrip,    estac,        sucu,       nocaj) "
                                            + "VALUES(  'LB',     'LIBRAS',      'INICIAL',    'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,            estac,      sucu,       nocaj) "
                                        + "VALUES(  'SYS',      'PESO GENÉRICO',    'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de pesos*/
            try 
            {
                /*Kilogramo*/
                sQ = "INSERT INTO logpes(cod, descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('KR','KILOGRAMO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Gramo*/
                sQ = "INSERT INTO logpes(cod, descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('GR','GRAMO',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Libra*/
                sQ = "INSERT INTO logpes(cod, descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('LB','LIBRAS',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Libra*/
                sQ = "INSERT INTO logpes(   cod,    descrip,             accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES(  'SYS',  'PESO GENÉRICO',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePes()*/


    /*Crea la tabla de ubicación adicional en los prods si no existe*/
    private void vCreUbiAd(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de pesos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunas ubicaciones*/
            try 
            {
                /*Inserta la ubicacion genárica*/
                sQ = "INSERT INTO " + sTabl + "(  cod,      descrip,                    estac,      sucu,       nocaj) "
                                        + "VALUES('SYS',    'UBICACIÓN GENáRICA',       'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de ubicación adicional*/
            try 
            {
                sQ = "INSERT INTO logubiad(cod,    descrip,                accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS',   'UBICACIÓN GENÉRICA',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreUbiAd()*/


    /*Crea la tabla de clasificaciones extras en prods si no existe*/
    private void vCreClasProd(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de clasificaciones extra en prods existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;        
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunas clasificaciones*/
            try 
            {
                /*Inserta algunas clasificaciones*/
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,                estac,      sucu,       nocaj) "
                        + "VALUES('SYS',         'CLASIFICACIÓN GENÉRICA',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,            estac,      sucu,       nocaj) "
                        + "VALUES('EXEC',        'EXCELENTE CALIDAD',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,        estac,      sucu,       nocaj) "
                        + "VALUES('MEDC',         'MEDIA CALIDAD',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,      estac,       sucu,       nocaj) "
                        + "VALUES('BAJC',        'BAJA CALIDAD',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de clasificaciones extra*/
            try {
                /*Clasificación genérica*/
                sQ = "INSERT INTO logclasext(   cod,  descrip,                     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'SYS','CLASIFICACIÓN GENÉRICA',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Clasificación para excelente cálidad*/
                sQ = "INSERT INTO logclasext(   cod,   descrip,                    accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'EXEC','EXCELENTE CALIDAD',        'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Clasificación para media cálidad*/
                sQ = "INSERT INTO logclasext(   cod,   descrip,                    accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'MEDC','MEDIA CALIDAD',            'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Clasificación para baja cálidad*/
                sQ = "INSERT INTO logclasext(   cod,   descrip,                    accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'BAJC','BAJA CALIDAD',             'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplciación
                Star.iCierrBas(con);                
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasProd()*/


    /*Crea la tabla de monedas si no existe*/
    private void vCreMons(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de monedas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {                
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de monedas no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`mon`                 VARCHAR(30) NOT NULL,\n"
                        + "`val`                 FLOAT DEFAULT 0,\n"
                        + "`mondescrip`          VARCHAR(255) NOT NULL,\n"
                        + "`simb`                VARCHAR(5) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`mn`                  bit NULL DEFAULT 0,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `mon_UNIQUE` (`mon`), KEY `mon` (`mon`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta monedas iniciales*/
            try 
            {
                /*Inserta moneda nacional*/
                sQ = "INSERT INTO " + sTabl + "(    mon,        mondescrip,         estac,      sucu,         nocaj,         mn,    val,simb) "
                                        + "VALUES(  'PESOS',    'MONEDA NACIONAL',  'INICIAL',  'INICIAL',    'INICIAL',      1,    1,  '$')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta moneda dollar*/
                sQ = "INSERT INTO " + sTabl + "(    mon,        mondescrip,     estac,      val, sucu,     nocaj,       simb) "
                                        + "VALUES(  'USD',      'DOLAR',       'INICIAL',   14, 'INICIAL','INICIAL',    '$')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de monedas*/
            try 
            {
                /*Moneda nacional*/
                sQ = "INSERT INTO logmons(cod,      descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('MN',     'MONEDA NACIONAL',  'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Moneda dollar*/
                sQ = "INSERT INTO logmons(cod,      descrip,      accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('USD',    'DOLAR',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMons()*/


    /*Crea la tabla de prods si no existe*/
    private void vCreProds(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de prods existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la moneda*/
            if(rs.next()) 
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de productos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`                 VARCHAR(255) NOT NULL,\n"
                        + "`prodop1`              VARCHAR(30) NOT NULL,\n"
                        + "`prodop2`              VARCHAR(30) NOT NULL,\n"
                        + "`provop1`              VARCHAR(30) DEFAULT '',\n"
                        + "`provop2`              VARCHAR(30) DEFAULT '',\n"
                        + "`codmed`               VARCHAR(30) DEFAULT '',\n"
                        + "`garan`                VARCHAR(30) DEFAULT '',\n"
                        + "`clasjera`             VARCHAR(2048) DEFAULT '',\n"
                        + "`descrip`              VARCHAR(2100) NOT NULL,\n"
                        + "`descripcort`          VARCHAR(255) DEFAULT '',\n"
                        + "`lin`                  VARCHAR(30) DEFAULT '',\n"
                        + "`metcost`              VARCHAR(10) DEFAULT 'ultcost',\n"
                        + "`tip`                  VARCHAR(30) DEFAULT '',\n"
                        + "`solmaxmin`            INT(11) DEFAULT 1,\n"
                        + "`marc`                 VARCHAR(30) DEFAULT '',\n"
                        + "`codubi`               VARCHAR(30) DEFAULT '',\n"
                        + "`mode`                 VARCHAR(30) DEFAULT '',\n"
                        + "`fab`                  VARCHAR(30) DEFAULT '',\n"
                        + "`colo`                 VARCHAR(30) DEFAULT '',\n"
                        + "`solser`               BIT DEFAULT 0,\n"
                        + "`codext`               VARCHAR(30) DEFAULT '',\n"
                        + "`noser`                VARCHAR(30) DEFAULT '',\n"
                        + "`comenser`             VARCHAR(600) DEFAULT '',\n"
                        + "`rutimg`               VARCHAR(500) DEFAULT '',\n"
                        + "`impue`                VARCHAR(30) DEFAULT '',\n"
                        + "`pes`                  VARCHAR(30) DEFAULT '',\n"
                        + "`pesman`               FLOAT NOT NULL,\n"
                        + "`prelist1`             FLOAT NOT NULL,\n"
                        + "`prelist2`             FLOAT DEFAULT 0,\n"
                        + "`prelist3`             FLOAT DEFAULT 0,\n"
                        + "`prelist4`             FLOAT DEFAULT 0,\n"
                        + "`prelist5`             FLOAT DEFAULT 0,\n"
                        + "`prelist6`             FLOAT DEFAULT 0,\n"
                        + "`prelist7`             FLOAT DEFAULT 0,\n"
                        + "`prelist8`             FLOAT DEFAULT 0,\n"
                        + "`prelist9`             FLOAT DEFAULT 0,\n"
                        + "`prelist10`            FLOAT DEFAULT 0,\n"
                        + "`utilvta1`             FLOAT DEFAULT 0,\n"
                        + "`utilvta2`             FLOAT DEFAULT 0,\n"
                        + "`utilvta3`             FLOAT DEFAULT 0,\n"
                        + "`utilvta4`             FLOAT DEFAULT 0,\n"
                        + "`utilvta5`             FLOAT DEFAULT 0,\n"
                        + "`utilvta6`             FLOAT DEFAULT 0,\n"
                        + "`utilvta7`             FLOAT DEFAULT 0,\n"
                        + "`utilvta8`             FLOAT DEFAULT 0,\n"
                        + "`utilvta9`             FLOAT DEFAULT 0,\n"
                        + "`utilvta10`            FLOAT DEFAULT 0,\n"
                        + "`med`                  FLOAT DEFAULT 0,\n"
                        + "`cost`                 FLOAT NOT NULL,\n"
                        + "`costre`               FLOAT NOT NULL,\n"
                        + "`exist`                FLOAT NOT NULL,\n"
                        + "`unid`                 VARCHAR(30) DEFAULT '',\n"
                        + "`anaq`                 VARCHAR(30) DEFAULT '',\n"
                        + "`lug`                  VARCHAR(30) DEFAULT '',\n"
                        + "`alma`                 VARCHAR(30) DEFAULT '',\n"
                        + "`descprov`             VARCHAR(100) NOT NULL,\n"
                        + "`infor`                VARCHAR(255) NOT NULL,\n"
                        + "`min`                  INT(11) NOT NULL,\n"
                        + "`max`                  INT(11) NOT NULL,\n"
                        + "`bajcost`              INT(11) NOT NULL,\n"
                        + "`esvta`                INT(11) DEFAULT 0,\n"
                        + "`compue`               INT(11) DEFAULT 0,\n"
                        + "`invent`               INT(11) NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`estaccrea`            VARCHAR(30) NULL DEFAULT '',\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`lote`                 VARCHAR(30) NULL DEFAULT '',\n"
                        + "`pedimen`              VARCHAR(30) NULL DEFAULT '',\n"
                        + "`servi`                BIT NULL DEFAULT 0,\n"
                        + "`lotped`               BIT DEFAULT 0,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`fcadu`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreProds()*/


    /*Crea la tabla de unidades si no existe*/
    private void vCreUnids(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de unidades existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de unidades no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta valores iniciales*/
            try 
            {
                /*Inserta unidad KILO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,        estac,      sucu,       nocaj ) "
                                        + "VALUES(  'KILO',     'KILO',         'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de METRO CUADRADO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,            estac,       sucu,       nocaj ) "
                                        + "VALUES(  'METRO CUADRADO',   'METRO CUADRADO',   'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);   
                
                /*Inserta unidad de CABEZA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,        estac,       sucu,       nocaj ) "
                                        + "VALUES(  'CABEZA',   'CABEZA',       'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de KILOWATT*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,        estac,       sucu,       nocaj ) "
                                        + "VALUES(  'KILOWATT', 'KILOWATT',     'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de KILOWATT/HORA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,        estac,       sucu,       nocaj ) "
                                        + "VALUES(  'KILOWATT/HORA',    'KILOWATT/HORA',    'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de GRAMO NETO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,       estac,       sucu,       nocaj ) "
                                        + "VALUES(  'GRAMO NETO',       'GRAMO NETO',  'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de DOCENAS*/
                sQ = "INSERT INTO " + sTabl + "(    cod,            descrip,      estac,       sucu,       nocaj ) "
                                        + "VALUES(  'DOCENAS',      'DOCENAS',    'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de GRAMO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,    estac,       sucu,       nocaj ) "
                                        + "VALUES(  'GRAMO',    'GRAMO',        'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               
                
                /*Inserta unidad de METRO CÚBICO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,     estac,       sucu,       nocaj ) "
                                        + "VALUES(  'METRO CÚBICO',     'METRO CÚBICO',  'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de LITRO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,    estac,       sucu,       nocaj ) "
                                        + "VALUES(  'LITRO',    'LITRO',        'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de MILLAR*/
                sQ = "INSERT INTO " + sTabl + "(    cod,            descrip,        estac,       sucu,       nocaj ) "
                                        + "VALUES(  'MILLAR',       'MILLAR',           'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de TONELADA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,            estac,          sucu,       nocaj ) "
                                        + "VALUES(  'TONELADA',         'TONELADA',             'TONELADA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de DECENAS*/
                sQ = "INSERT INTO " + sTabl + "(     cod,               descrip,           estac,          sucu,       nocaj ) "
                                        + "VALUES(  'DECENAS',          'DECENAS',             'TONELADA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de CAJA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,            descrip,        estac,      sucu,       nocaj ) "
                                        + "VALUES(  'CAJA',         'CAJA',             'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de METRO LINEAL*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,            estac,      sucu,       nocaj ) "
                                        + "VALUES(  'METRO LINEAL',     'METRO LINEAL',         'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                                
                /*Inserta unidad de PIEZA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,            descrip,        estac,       sucu,       nocaj ) "
                                        + "VALUES(  'PIEZA',        'PIEZA',        'INICIAL',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);               

                /*Inserta unidad de PAR*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,     estac,      sucu,       nocaj ) "
                                        + "VALUES(  'PAR',      'PAR',           'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta unidad de JUEGO*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,        estac,      sucu,       nocaj ) "
                                        + "VALUES(  'JUEGO',    'JUEGO',            'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
                
                /*Inserta unidad de BARRIL*/
                sQ = "INSERT INTO " + sTabl + "(    cod,            descrip,        estac,      sucu,       nocaj ) "
                                        + "VALUES(  'BARRIL',       'BARRIL',           'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
                
                /*Inserta unidad de CIENTOS*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,            estac,      sucu,       nocaj ) "
                                        + "VALUES(  'CIENTOS',          'CIENTOS',              'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
                
                /*Inserta unidad de BOTELLA*/
                sQ = "INSERT INTO " + sTabl + "(    cod,                descrip,            estac,      sucu,       nocaj ) "
                                        + "VALUES(  'BOTELLA',          'BOTELLA',              'CAJA',     'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de unidades*/
            try 
            {
                /*Unidad KILO*/
                sQ = "INSERT INTO logunid(cod,      descrip,   accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('KILO',   'KILO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Unidad METRO CUADRADO*/
                sQ = "INSERT INTO logunid(cod,              descrip,                accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('METRO CUADRADO', 'METRO CUADRADO',       'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Unidad CABEZA*/
                sQ = "INSERT INTO logunid(cod,      descrip,    accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('CABEZA', 'CABEZA',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad KILOWATT*/
                sQ = "INSERT INTO logunid(cod,          descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('KILOWATT',   'KILOWATT',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad KILOWATT/HORA*/
                sQ = "INSERT INTO logunid(cod,                  descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('KILOWATT/HORA',      'KILOWATT/HORA',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad GRAMO NETO*/
                sQ = "INSERT INTO logunid(cod,                  descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('GRAMO NETO',         'GRAMO NETO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad DOCENAS*/
                sQ = "INSERT INTO logunid(cod,              descrip,      accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('DOCENAS',        'DOCENAS',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad GRAMO*/
                sQ = "INSERT INTO logunid(cod,          descrip,    accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('GRAMO',      'GRAMO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad METRO CÚBICO*/
                sQ = "INSERT INTO logunid(cod,              descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('METRO CÚBICO',   'METRO CÚBICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad LITRO*/
                sQ = "INSERT INTO logunid(cod,       descrip,  accio,       estac,    sucu,      nocaj,    falt) "
                                + "VALUES('LITRO',   'LITRO',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad MILLAR*/
                sQ = "INSERT INTO logunid(cod,         descrip,      accio,          estac,    sucu,      nocaj,    falt) "
                                + "VALUES('MILLAR',   'MILLAR',     'AGREGAR',       'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad TONELADA*/
                sQ = "INSERT INTO logunid(cod,          descrip,        accio,              estac,    sucu,      nocaj,    falt) "
                                + "VALUES('TONELADA',   'TONELADA',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad DECENAS*/
                sQ = "INSERT INTO logunid(cod,         descrip,       accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('DECENAS',   'DECENAS',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad CAJA*/
                sQ = "INSERT INTO logunid(cod,      descrip,    accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('CAJA',   'CAJA',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad METRO LINEAL*/
                sQ = "INSERT INTO logunid(cod,              descrip,            accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('METRO LINEAL',   'METRO LINEAL',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad PIEZA*/
                sQ = "INSERT INTO logunid(cod,       descrip,     accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('PIEZA',   'PIEZA',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad PAR*/
                sQ = "INSERT INTO logunid(cod,     descrip,   accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('PAR',   'PAR',     'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad JUEGO*/
                sQ = "INSERT INTO logunid(cod,      descrip,    accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('JUEGO',  'JUEGO',    'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad BARRIL*/
                sQ = "INSERT INTO logunid(cod,          descrip,        accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('BARRIL',     'BARRIL',       'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                                
                /*Unidad CIENTOS*/
                sQ = "INSERT INTO logunid(cod,              descrip,         accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('CIENTOS',        'CIENTOS',       'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Unidad BOTELLA*/
                sQ = "INSERT INTO logunid(cod,              descrip,         accio,             estac,    sucu,      nocaj,    falt) "
                                + "VALUES('BOTELLA',        'BOTELLA',       'AGREGAR',         'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreUnids()*/


    /*Crea la tabla de coloressi no existe*/
    private void vCreColos(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de colores existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de colores no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos colores*/
            try 
            {
                /*Inserta colores varios*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,    estac,   sucu,       nocaj) "
                                        + "VALUES(  'BLAN',     'BLANCO',       'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,    estac,   sucu,       nocaj) "
                                          + "VALUES('NEG',      'NEGRO',        'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,    estac,      sucu,       nocaj) "
                                        + "VALUES(  'ROJO',     'ROJO',     'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + "(    cod,      descrip,      estac,      sucu,       nocaj ) "
                                        + "VALUES(  'VERDE',  'VERDE',      'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + " ( cod,      descrip,    estac,      sucu,       nocaj ) "
                                        + "VALUES('AZUL',   'AZUL',     'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                sQ = "INSERT INTO " + sTabl + " (   cod,        descrip,        estac,      sucu,       nocaj ) "
                                    + "VALUES(      'AMARILLO', 'AMARILLO',     'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de colores*/
            try 
            {
                /*Para el color rojo*/
                sQ = "INSERT INTO logcolo(cod,      descrip,   accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('ROJO',   'ROJO',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Para el color azul*/
                sQ = "INSERT INTO logcolo(cod,      descrip,    accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('AZUL',   'AZUL',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Para el color amarillo*/
                sQ = "INSERT INTO logcolo(cod,          descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('AMARILLO',   'AMARILLO',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Para el color verde*/
                sQ = "INSERT INTO logcolo(cod,      descrip,   accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('VERDE',  'VERDE',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreColos()*/


    /*Crea la tabla de lineas si no existe*/
    private void vCreLins(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de lineas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de lineas no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`              INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                VARCHAR(30) NOT NULL,\n"
                        + "`descrip`            VARCHAR(255) NOT NULL,\n"
                        + "`img`                VARCHAR(255) DEFAULT '',\n"                        
                        + "`estac`              VARCHAR(30) NOT NULL,\n"
                        + "`sucu`               VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`              VARCHAR(30) NOT NULL,\n"
                        + "`export`             BIT DEFAULT 0,\n"
                        + "`extr1`              VARCHAR(255) NULL,\n"
                        + "`extr2`              VARCHAR(255) NULL,\n"
                        + "`extr3`              VARCHAR(255) NULL,\n"
                        + "`falt`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `lin_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta líneas*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(  cod,          descrip,            estac,      img,    sucu,       nocaj) "
                                        + "VALUES('SYS',        'LÍNEA GENERICA',   'INICIAL',  '-1',   'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de líneas*/
            try 
            {
                sQ = "INSERT INTO loglins(cod,  descrip,          accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS','LÍNEA GENÉRICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLins()*/


    /*Crea la tabla de clasficación de clientes si no existe*/
    private void vCreClasClien(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla si no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                 VARCHAR(30) NOT NULL,\n"
                        + "`descrip`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta clasificaciones*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,            estac,      sucu,       nocaj) "
                                        + "VALUES(  'SYS',      'CLASE GENERICA',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log*/
            try 
            {
                sQ = "INSERT INTO logclasemp(cod,  descrip,          accio,         estac,    sucu,      nocaj,    falt) "
                                   + "VALUES('SYS','CLASE GENERICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasClien()*/


    /*Crea la tabla de clasficación de proveedores si no existe*/
    private void vCreClasProv(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`          INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`            VARCHAR(30) NOT NULL,\n"
                        + "`descrip`        VARCHAR(255) NOT NULL,\n"
                        + "`estac`          VARCHAR(30) NOT NULL,\n"
                        + "`sucu`           VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`          VARCHAR(30) NOT NULL,\n"
                        + "`export`         BIT DEFAULT 0,\n"                        
                        + "`extr1`          VARCHAR(255) NULL,\n"
                        + "`extr2`          VARCHAR(255) NULL,\n"
                        + "`extr3`          VARCHAR(255) NULL,\n"
                        + "`falt`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta clasificaciones*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,            estac,      sucu,       nocaj) "
                                       + "VALUES('SYS',     'CLASE GENERICA',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log*/
            try 
            {
                sQ = "INSERT INTO logclasprov(   cod,    descrip,          accio,         estac,    sucu,      nocaj,    falt) "
                                     + "VALUES(  'SYS',  'CLASE GENERICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasProv()*/


    /*Crea la tabla de kist si no existe*/
    private void vCreKits(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de kits no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codkit`          VARCHAR(30) NOT NULL,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`alma`            VARCHAR(30) NOT NULL,\n"                        
                        + "`cant`            FLOAT NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codkit` (`codkit`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreKits()*/


    /*Crea la tabla de maximosminimosconf si no existe*/
    private void vCreMaxMinConf(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de maximosminimosconf existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de maximosminimosconf no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`hrs`                 INT(11) NOT NULL,\n"
                        + "`estacglo`            VARCHAR(255) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `estac_UNIQUE` (`estac`), KEY `estac` (`estac`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMaxMinConf()*/


    /*Crea la tabla de impues si no existe*/
    private void vCreImpue(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de impues existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = 'impues' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de impues no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                   INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codimpue`                VARCHAR(30) NOT NULL,\n"
                        + "`impueval`                FLOAT NOT NULL,\n"
                        + "`estac`                   VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                    VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                   VARCHAR(30) NOT NULL,\n"
                        + "`export`                  BIT DEFAULT 0,\n"
                        + "`extr1`                   VARCHAR(255) NULL,\n"
                        + "`extr2`                   VARCHAR(255) NULL,\n"
                        + "`extr3`                   VARCHAR(255) NULL,\n"
                        + "`falt`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),    UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `codimpue_UNIQUE` (`codimpue`), KEY `codimpue` (`codimpue`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta valores iniciales en tabla*/
            try 
            {
                /*Inserta impuesto IVA*/
                sQ = "INSERT INTO impues (codimpue,     impueval,       estac,      sucu,       nocaj) "
                                + "VALUES('IVA',        '16',           'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta impuesto IEPS*/
                sQ = "INSERT INTO impues (codimpue,     impueval,       estac,      sucu,     nocaj) "
                                + "VALUES('IEPS',       '8',            'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de impuestos*/
            try 
            {
                /*Impuesto IVA*/
                sQ = "INSERT INTO logimpue(     cod,     val,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'IVA',   16,     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Impuesto IEPS*/
                sQ = "INSERT INTO logimpue(cod,      val,      accio,         estac,    sucu,      nocaj,    falt) "
                                 + "VALUES('IEPS',   8,      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreImpue()*/


    /*Crea la tabla de anaqueles si no existe*/
    private void vCreAnaqs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de anaqueles no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                   INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                     VARCHAR(30) NOT NULL,\n"
                        + "`descrip`                 VARCHAR(255) NOT NULL,\n"
                        + "`estac`                   VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                    VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                   VARCHAR(30) NOT NULL,\n"
                        + "`export`                  BIT DEFAULT 0,\n"
                        + "`extr1`                   VARCHAR(255) NULL,\n"
                        + "`extr2`                   VARCHAR(255) NULL,\n"
                        + "`extr3`                   VARCHAR(255) NULL,\n"
                        + "`falt`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),    UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `anaq_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta anaqueles*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(  cod,      descrip,            estac,      sucu,     nocaj) "
                                        + "VALUES('SYS',    'ANAQUEL GENÉRICO', 'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de anqueles*/
            try 
            {
                sQ = "INSERT INTO loganaq(cod,  descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS','ANAQUEL GENÉRICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreAnaqs()*/


    /*Crea la tabla de lugares si no existe*/
    private void vCreLugs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de lugares existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de lugares no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`              INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                VARCHAR(30) NOT NULL,\n"
                        + "`descrip`            VARCHAR(255) NOT NULL,\n"
                        + "`estac`              VARCHAR(30) NOT NULL,\n"
                        + "`sucu`               VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`              VARCHAR(30) NOT NULL,\n"
                        + "`export`             BIT DEFAULT 0,\n"
                        + "`extr1`              VARCHAR(255) NULL,\n"
                        + "`extr2`              VARCHAR(255) NULL,\n"
                        + "`extr3`              VARCHAR(255) NULL,\n"
                        + "`falt`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `lug_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta lugares*/
            try 
            {
                /*Inserta lugar genérico*/
                sQ = "INSERT INTO " + sTabl + "(    cod,        descrip,            estac,      sucu,     nocaj ) "
                                        + "VALUES(  'SYS',      'LUGAR GENÉRICO',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de lugares*/
            try 
            {
                sQ = "INSERT INTO loglugs(cod,  descrip,          accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS','LUGAR GENÉRICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLugs()*/


    /*Crea la tabla de medidas si no existe*/
    private void vCreMeds(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la taba de medidas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos y coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de medidas no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`              INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`                VARCHAR(30) NOT NULL,\n"
                        + "`descrip`            VARCHAR(255) NOT NULL,\n"
                        + "`estac`              VARCHAR(30) NOT NULL,\n"
                        + "`sucu`               VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`              VARCHAR(30) NOT NULL,\n"
                        + "`export`             BIT DEFAULT 0,\n"
                        + "`extr1`              VARCHAR(255) NULL,\n"
                        + "`extr2`              VARCHAR(255) NULL,\n"
                        + "`extr3`              VARCHAR(255) NULL,\n"
                        + "`falt`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`               TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta medidas*/
            try 
            {
                /*Inserta algnas medidas*/
                sQ = "INSERT INTO " + sTabl + "(  cod,        descrip,              estac,      sucu,       nocaj ) "
                                        + "VALUES('SYS',      'MEDIDA GENÉRICA',    'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de fabricantes*/
            try 
            {
                sQ = "INSERT INTO logmed(   cod,  descrip,             accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES(  'SYS','MEDIDA GENÉRICA',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMeds()*/


    /*Crea la tabla de rutas si no existe*/
    private void vCreRuts(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de rutas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de rutas no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`program`          VARCHAR(255) NOT NULL,\n"
                        + "`nom`              VARCHAR(255) NOT NULL,\n"
                        + "`rut`              VARCHAR(255) NOT NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`falt`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `nom_UNIQUE` (`nom`), KEY `estac` (`estac`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreRuts()*/


    /*Crea la tabla de chat corporativo si no existe*/
    private void vCreChat(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la taba de rutas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`            INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`msj`              VARCHAR(1000) NOT NULL,\n"
                        + "`vist`             VARCHAR(2000) NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`estacdestin`      VARCHAR(30) NULL DEFAULT '',\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`falt`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estac` (`estac`)"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreChat()*/
    
    
    /*Crea la tabla de marcas si no existe*/
    private void vCreMarcs(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunas marcas*/
            try 
            {
                /*Inserta marca genérica*/
                sQ = "INSERT INTO " + sTabl + "( cod,           descrip,            estac,      sucu,     nocaj ) "
                                       + "VALUES('SYS',         'MARCA GENÉRICA',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de marcas*/
            try 
            {
                sQ = "INSERT INTO logmarc(cod,  descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('SYS','MARCA GENÉRICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMarcs()*/

    
    /*Crea la tabla de tipos de cambio si no existe*/
    private void vCreTipsCamb(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11)        NOT NULL AUTO_INCREMENT,\n"
                        + "`mon`             VARCHAR(30)    NOT NULL,\n"
                        + "`tipcam`          FLOAT          NOT NULL,\n"
                        + "`estac`           VARCHAR(30)    NOT NULL,\n"
                        + "`sucu`            VARCHAR(30)    NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30)    NOT NULL,\n"
                        + "`export`          BIT            DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255)   NULL,\n"
                        + "`extr2`           VARCHAR(255)   NULL,\n"
                        + "`extr3`           VARCHAR(255)   NULL,\n"
                        + "`falt`            TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `mon` (`mon`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTipsCamb()*/
    
    
    /*Crea la tabla de tarjetas si no existe*/
    private void vCreTars(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cli`             VARCHAR(30) NOT NULL,\n"
                        + "`tar`             INT(11) NOT NULL,\n"
                        + "`vta`             INT(11) DEFAULT 0,\n"
                        + "`pag`             BIT NOT NULL,\n"
                        + "`loc`             BIT NOT NULL,\n"
                        + "`exter`           BIT NOT NULL,\n"
                        + "`prepag`          BIT NOT NULL,\n"
                        + "`factuya`         BIT NOT NULL,\n"
                        + "`ffactu`          TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`factur`          BIT NOT NULL,\n"
                        + "`tarif`           FLOAT NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `cli` (`cli`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTars()*/
    
    
    /*Crea la tabla de lotes y pedimentos si no existe*/
    private void vCreLotPed(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de lotes y pedimento existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`alma`            VARCHAR(30) NOT NULL,\n"
                        + "`lot`             VARCHAR(255) NOT NULL,\n"
                        + "`pedimen`         VARCHAR(255) NOT NULL,\n"
                        + "`fcadu`           TIMESTAMP NOT NULL,\n"
                        + "`exist`           FLOAT NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `alma` (`alma`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLotMarc()*/
    
    
    /*Crea la tabla de costeos si no existe*/
    private void vCreCost(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`cant`            FLOAT NOT NULL,\n"
                        + "`cost`            FLOAT NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`prod`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCost()*/
    
    
    /*Crea la tabla de tipos de activo fijo si no existe*/
    private void vCreTipAct(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ  = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`tip`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `tip_UNIQUE` (`tip`), KEY `tip` (`tip`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTipAct()*/
    
    
    /*Crea la tabla de catálogo de activo fijos si no existe*/
    private void vCreActFijCat(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`concep`          VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `marc_UNIQUE` (`concep`), KEY `concep` (`concep`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el concepto de vendido*/
            try 
            {             
                sQ = "INSERT INTO " + sTabl + "(    concep,         descrip,        estac,      sucu,     nocaj ) "
                                        + "VALUES(  'VEND',         'VENDIDO',      'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el concepto de deteriorado*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(    concep,          descrip,           estac,      sucu,     nocaj ) "
                                        + "VALUES(  'DETER',         'DETERIORADO',     'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreActFijCat()*/
    
    
    /*Crea la tabla de interfaz datapark si no existe*/
    private void vCreInterDP(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`inst`            VARCHAR(255) DEFAULT '',\n"
                        + "`usr`             VARCHAR(255) DEFAULT '',\n"
                        + "`usrmand`         VARCHAR(30) DEFAULT '',\n"
                        + "`serfac`          VARCHAR(30) DEFAULT '',\n"
                        + "`mon`             VARCHAR(30) DEFAULT '',\n"                        
                        + "`contra`          VARCHAR(255) DEFAULT '',\n"
                        + "`bd`              VARCHAR(255) DEFAULT '',\n"                        
                        + "`codemploc`       VARCHAR(30) DEFAULT '',\n"
                        + "`prod`            VARCHAR(30) DEFAULT '',\n"                        
                        + "`port`            INT(11) DEFAULT 0,\n"
                        + "`diacort`         INT(11) DEFAULT 0,\n"
                        + "`automat`         BIT DEFAULT 0,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`fultcort`        TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
                       
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreInterDP()*/
    
    
    /*Crea la tabla de activos fijo si no existe*/
    private void vCreActFij(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"                        
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`alma`            VARCHAR(30) NOT NULL,\n"
                        + "`lug`             VARCHAR(30) DEFAULT '',\n"
                        + "`ctadepre`        VARCHAR(45) NOT NULL,\n"
                        + "`ctadedu`         VARCHAR(45) NOT NULL,\n"
                        + "`ctagast`         VARCHAR(45) NOT NULL,\n"
                        + "`serprod`         VARCHAR(255) DEFAULT '',\n"
                        + "`descrip`         VARCHAR(1000) NOT NULL,\n"
                        + "`porcendep`       FLOAT NOT NULL,\n"
                        + "`porcendedu`      FLOAT NOT NULL,\n"
                        + "`cost`            FLOAT NOT NULL,\n"
                        + "`sal`             BIT DEFAULT 0,\n"
                        + "`exportconta`     BIT DEFAULT 0,\n"
                        + "`totmesbaj`       INT(11) DEFAULT 0,\n"
                        + "`totacumes`       FLOAT DEFAULT 0,\n"
                        + "`totvalactbaj`    FLOAT DEFAULT 0,\n"
                        + "`totestadbaj`     VARCHAR(30) DEFAULT '',\n"
                        + "`costsal`         FLOAT DEFAULT 0,\n"
                        + "`fadquisreal`     TIMESTAMP NOT NULL,\n"
                        + "`fadquisusr`      TIMESTAMP NOT NULL,\n"
                        + "`finidep`         TIMESTAMP NOT NULL,\n"
                        + "`ffindep`         TIMESTAMP NOT NULL,\n"
                        + "`fbajdep`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`estadusr`        VARCHAR(30) NOT NULL,\n"
                        + "`tipact`          VARCHAR(30) NOT NULL,\n"
                        + "`comen`           VARCHAR(1000) DEFAULT '',\n"
                        + "`baj`             BIT DEFAULT 0,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`fbaj`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreActFij()*/
    
    
    /*Crea la tabla de existencias por almacén si no existe*/
    private void vCreExisAlma(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`alma`            VARCHAR(30) NOT NULL,\n"
                        + "`prod`            VARCHAR(255) NOT NULL,\n"
                        + "`exist`           FLOAT NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`prod`), KEY `prod` (`prod`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreExisAlma()*/
    
    
    /*Crea la tabla de conceptos de notas de crédito si no existe*/
    private void vCreConcepNot(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de conceptos de la nota de crédito existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`concep`          VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `concep_UNIQUE` (`concep`), KEY `concep` (`concep`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el concepto genérico*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,                estac,      sucu,     nocaj ) "
                                        + "VALUES(  'SYS',      'CONCEPTO GENÉRICO',    'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta en log*/
            try 
            {
                sQ = "INSERT INTO logconcnot(   cod,    descrip,                accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'SYS',  'CONCEPTO GENÉRICO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreConcepNot()*/
    
    
    /*Crea la tabla de conceptos de pagos si no existe*/
    private void vCreConcepPag(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
                System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`concep`          VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`),UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `concep_UNIQUE` (`concep`), KEY `concep` (`concep`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el concepto de abono*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(    concep,      descrip,         estac,      sucu,     nocaj ) "
                                        + "VALUES(  'ABON',      'PAGO ABONO',    'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta en log*/
            try 
            {
                sQ = "INSERT INTO logconcpag(   cod,     descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'ABON',  'PAGO ABONO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreConcePag()*/
    
    
    /*Crea la tabla de flujo de actividades si no existe*/
    private void vCreFlujAct(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de marcas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`usrenvi`         VARCHAR(30) NOT NULL,\n"
                        + "`usrrecib`        VARCHAR(30) NOT NULL,\n"
                        + "`tit`             VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `usrenvi` (`usrenvi`), KEY `usrrecib` (`usrrecib`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreFlujAct()*/
    
    
    /*Crea la tabla de partidas de flujo de actividades si no existe*/
    private void vCrePartFlujAct(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`idfluj`          INT(11) NOT NULL,\n"
                        + "`segui`           VARCHAR(2148) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `idfluj` (`idfluj`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePartFlujAct()*/
    
    
    /*Crea la tabla de clasificaciones jerárquicas de clientes si no existe*/
    private void vCreClasJeraCli(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de marcas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`clas`            VARCHAR(30) NOT NULL,\n"
                        + "`rut`             VARCHAR(2048) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `clas` (`clas`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasJeraCli()*/
    
    
    /*Crea la tabla de clasificaciones jerárquicas de proveedores si no existe*/
    private void vCreClasJeraProv(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`clas`            VARCHAR(30) NOT NULL,\n"
                        + "`rut`             VARCHAR(2048) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `accio` (`clas`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasJeraProv()*/
    
    
    /*Crea la tabla de clasificaciones jerárquicas de productos si no existe*/
    private void vCreClasJeraProd(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`clas`            VARCHAR(30) NOT NULL,\n"
                        + "`rut`             VARCHAR(2048) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `clas` (`clas`)\n"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreClasJeraProd()*/
    
    
    /*Crea la tabla de garantías si no existe*/
    private void vCreGaran(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de marcas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`gara`            VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `gara_UNIQUE` (`gara`), KEY `gara` (`gara`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreGara()*/
    
    
    /*Crea la tabla de rubros si no existe*/
    private void vCreRubr(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreRubr()*/
    
    
    /*Crea la tabla de interfaces BD  si no existe*/
    private void vCreInterBD(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`inst`            VARCHAR(255) NOT NULL,\n"
                        + "`insteas`         VARCHAR(255) NOT NULL,\n"
                        + "`usr`             VARCHAR(255) NOT NULL,\n"
                        + "`usreas`          VARCHAR(255) NOT NULL,\n"
                        + "`contra`          VARCHAR(255) NOT NULL,\n"
                        + "`contraeas`       VARCHAR(255) NOT NULL,\n"
                        + "`bd`              VARCHAR(255) NOT NULL,\n"
                        + "`bdeas`           VARCHAR(255) NOT NULL,\n"
                        + "`port`            INT(11) NOT NULL,\n"
                        + "`porteas`         VARCHAR(255) NOT NULL,\n"
                        + "`nom`             VARCHAR(255) NOT NULL,\n"
                        + "`usr2`            VARCHAR(255) NOT NULL,\n"
                        + "`contra2`         VARCHAR(255) NOT NULL,\n"
                        + "`tip`             VARCHAR(20) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`retivaflet`      FLOAT DEFAULT 0,\n"
                        + "`retiva`          FLOAT DEFAULT 0,\n"
                        + "`retisr`          FLOAT DEFAULT 0,\n"
                        + "`ctaivaacred`     VARCHAR(30) DEFAULT '',\n"
                        + "`ivapendac`       VARCHAR(30) DEFAULT '',\n"
                        + "`ctaremvta1`      VARCHAR(30) DEFAULT '',\n"                        
                        + "`ctaremvta2`      VARCHAR(30) DEFAULT '',\n"
                        + "`ctaremrta1`      VARCHAR(30) DEFAULT '',\n"                        
                        + "`ctaremrta2`      VARCHAR(30) DEFAULT '',\n"
                        + "`ctaremint1`      VARCHAR(30) DEFAULT '',\n"                        
                        + "`ctaremint2`      VARCHAR(30) DEFAULT '',\n"
                        + "`cta1flet`        VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta2flet`        VARCHAR(30) DEFAULT '',\n"
                        + "`cta3flet`        VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta1arr`         VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta2arr`         VARCHAR(30) DEFAULT '',\n"
                        + "`cta3arr`         VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta1hon`         VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta2hon`         VARCHAR(30) DEFAULT '',\n"
                        + "`cta3hon`         VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta1merc`        VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta2merc`        VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta1gast`        VARCHAR(30) DEFAULT '',\n"                        
                        + "`cta2gast`        VARCHAR(30) DEFAULT '',\n"
                        + "`cta1cv`          VARCHAR(30) DEFAULT '',\n"
                        + "`cta1vta`         VARCHAR(30) DEFAULT '',\n"
                        + "`cta2vta`         VARCHAR(30) DEFAULT '',\n"
                        + "`cta3vta`         VARCHAR(30) DEFAULT '',\n"
                        + "`cta4vta`         VARCHAR(30) DEFAULT '',\n"
                        + "`ctadevsobvta`    VARCHAR(30) DEFAULT '',\n"                        
                        + "`ctabanc`         VARCHAR(30) DEFAULT '',\n"
                        + "`ctagaran`        VARCHAR(30) DEFAULT '',\n"
                        + "`ctaivapendpag`   VARCHAR(30) DEFAULT '',\n"
                        + "`ctaivaxpag`      VARCHAR(30) DEFAULT '',\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`)"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }           

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreInterBD()*/
    
    
    /*Crea la tabla de zonas si no existe*/
    private void vCreZon(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunas zonas*/
            try 
            {
                /*Inserta zona centro*/
                sQ = "INSERT INTO " + sTabl + "( cod,            descrip,           estac,      sucu,     nocaj ) "
                                      + "VALUES('CENTR',         'ZONA CENTRO',     'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta la zona oriente*/
                sQ = "INSERT INTO " + sTabl + "( cod,            descrip,       estac,      sucu,     nocaj ) "
                                      + "VALUES('ORIEN',         'ZONA ORIENTE',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta la zona poniente*/
                sQ = "INSERT INTO " + sTabl + "( cod,             descrip,        estac,      sucu,     nocaj ) "
                                      + "VALUES('PONIEN',         'ZONA PONIENTE',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta en logs*/
            try 
            {
                /*Inserta en log de zona centro*/
                sQ = "INSERT INTO logzona(cod,      descrip,       accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('CENTR',   'ZONA CENTRO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta en log de zona oriente*/
                sQ = "INSERT INTO logzona(cod,      descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('ORIEN',   'ZONA ORIENTE', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta en log de zona poniente*/
                sQ = "INSERT INTO logzona(cod,       descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('PONIEN',   'ZONA PONIENTE', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreZon()*/
    
    
    /*Crea la tabla de giros si no existe*/
    private void vCreGiro(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`gir`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `gir_UNIQUE` (`gir`), KEY `gir` (`gir`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos giros*/
            try 
            {
                /*Inserta el giro comercial*/
                sQ = "INSERT INTO " + sTabl + "( gir,            descrip,           estac,      sucu,     nocaj ) "
                                      + "VALUES('COMER',         'GIRO COMERCIAL',  'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta el giro agropecuario*/
                sQ = "INSERT INTO " + sTabl + "( gir,            descrip,               estac,      sucu,     nocaj ) "
                                      + "VALUES('AGRO',         'GIRO AGROPECUARIO',    'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                                
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta en log*/
            try 
            {
                /*Inserta log de giro comercial*/
                sQ = "INSERT INTO loggiro(cod,      descrip,          accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('COMER',  'GIRO COMERCIAL', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta log de giro agropecuario*/
                sQ = "INSERT INTO loggiro(cod,   descrip,             accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES('AGRO','GIRO AGROPECUARIO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreGiro()*/
    
    
    /*Crea la tabla de asociacioens de descuento si no existe*/
    private void vCreAsocDesc(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de marcas existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`clien`           VARCHAR(30) NOT NULL,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`clas`            VARCHAR(30) NOT NULL,\n"
                        + "`clasjera`        VARCHAR(2048) NOT NULL,\n"
                        + "`util`            FLOAT DEFAULT 0,\n"
                        + "`list`            INT(11) DEFAULT 1,\n"
                        + "`prec`            FLOAT DEFAULT 0,\n"
                        + "`usar`            VARCHAR(10) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `clien` (`clien`), KEY `prod` (`prod`), KEY `clas` (`clas`)" 
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreAsocDesc()*/
    
    
    /*Crea la tabla de tipos si no existe*/
    private void vCreTips(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos tipos*/
            try 
            {
                /*Inserta impresora*/
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,     estac,      sucu,     nocaj ) "
                                       + "VALUES('IMPRE',   'IMPRESORA',    'INICIAL',    'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta consumible*/
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,      estac,      sucu,     nocaj ) "
                                       + "VALUES('CONSU',   'CONSUMIBLE', 'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta metales*/
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,    estac,      sucu,     nocaj ) "
                                       + "VALUES('METAL',   'METALES',  'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta cable*/
                sQ = "INSERT INTO " + sTabl + "( cod,       descrip,        estac,      sucu,     nocaj ) "
                                      + "VALUES('CABLE',    'CABLES',       'INICIAL',    'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de tipos*/
            try 
            {
                /*Impresora*/
                sQ = "INSERT INTO logtip(cod,       descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('IMPRE',   'IMPRESORA',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Consumible*/
                sQ = "INSERT INTO logtip(cod,       descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('CONSU',   'CONSUMIBLE',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Metal*/
                sQ = "INSERT INTO logtip(cod,       descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('METAL',   'METALES',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Cables*/
                sQ = "INSERT INTO logtip(cod,           descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                              + "VALUES('CABLE',        'CABLES',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTips()*/
    
    
    /*Crea la tabla de tallas si no existe*/
    private void vCreTall(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `marc_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta la talla grande*/
            try 
            {
                /*Inserta talla grande*/
                sQ = "INSERT INTO " + sTabl + "( cod,           descrip,      estac,      sucu,     nocaj ) "
                                       + "VALUES('GA',          'TALLA GRANDE',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de tallas*/
            try 
            {
                sQ = "INSERT INTO logtall(cod,  descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('GA',  'TALLA GRANDE', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta talla mediana*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(  cod,              descrip,       estac,      sucu,     nocaj ) "
                                        + "VALUES('MED',            'TALLA MEDIANA',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de tallas*/
            try 
            {
                sQ = "INSERT INTO logtall(cod, descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('MED','TALLA MEDIANA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
            /*Inserta talla chica*/
            try 
            {
                /*Inserta talla grande*/
                sQ = "INSERT INTO " + sTabl + "(  cod,          descrip,        estac,      sucu,     nocaj ) "
                                        + "VALUES('CH',         'TALLA CHICA',  'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de tallas*/
            try 
            {
                sQ = "INSERT INTO logtall(cod,  descrip,       accio,         estac,    sucu,      nocaj,    falt) "
                               + "VALUES('CH',  'TALLA CHICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTall()*/
    
    
    /*Crea la tabla de asociaciones marcas si no existe*/
    private void vCreMarcProd(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`marc`            VARCHAR(30) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `marc` (`marc`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMarcProd()*/
    
    
    /*Crea la tabla de asociaciones de números de parte si no existe*/
    private void vCreProdPart(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`part`            VARCHAR(30) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `part` (`part`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreProdPart()*/
    
    
    /*Crea la tabla de asociaciones de domicilios de entrega de clientes si no existe*/
    private void vCreDomEntCli(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de consecutivos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11)         NOT NULL AUTO_INCREMENT,\n"
                        + "`codemp`               VARCHAR(30)     NOT NULL,\n"
                        + "`lada`                 VARCHAR(10)     NULL DEFAULT '',\n"
                        + "`exten`                VARCHAR(10)     NULL DEFAULT '',\n"
                        + "`cel`                  VARCHAR(255)    DEFAULT '', "
                        + "`tel`                  VARCHAR(255)    DEFAULT '',\n"                        
                        + "`telper1`              VARCHAR(255)    DEFAULT '', "
                        + "`telper2`              VARCHAR(255)    DEFAULT '', "
                        + "`calle`                VARCHAR(255)    DEFAULT '',\n"                        
                        + "`col`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`noint`                VARCHAR(255)    DEFAULT '', "                        
                        + "`noext`                VARCHAR(21)     DEFAULT '',\n"
                        + "`CP`                   VARCHAR(20)     DEFAULT '',\n"                        
                        + "`ciu`                  VARCHAR(255)    DEFAULT '',\n"                        
                        + "`estad`                VARCHAR(255)    DEFAULT '',\n"                        
                        + "`pai`                  VARCHAR(255)    DEFAULT '',\n"                        
                        + "`co1`                  VARCHAR(100)    DEFAULT '',\n"
                        + "`co2`                  VARCHAR(100)    DEFAULT '',\n"
                        + "`co3`                  VARCHAR(100)    DEFAULT '',\n"                        
                        + "`estac`                VARCHAR(30)     NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30)     NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30)     NOT NULL,\n"
                        + "`export`               BIT             DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255)    NULL,\n"
                        + "`extr2`                VARCHAR(255)    NULL,\n"
                        + "`extr3`                VARCHAR(255)    NULL,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codemp` (`codemp`)"                        
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreDomEntCli()*/
    
    
    /*Crea la tabla de marcas y modelos si no existe*/
    private void vCreProdMarcMode(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
                       
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla */
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`marc`            VARCHAR(30) NOT NULL,\n"
                        + "`model`           VARCHAR(30) NOT NULL,\n"
                        + "`rut`             VARCHAR(2048) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),KEY `prod` (`prod`),KEY `marc` (`marc`),KEY `model` (`model`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void private void vCreMarcMode(String sBD, Connection con, String sTabl)*/

    
    /*Crea la tabla de marcas y modelos si no existe*/
    private void vCreMarcMode(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ  = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`marc`            VARCHAR(30) NOT NULL,\n"
                        + "`model`           VARCHAR(30) NOT NULL,\n" 
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `marc` (`marc`),KEY `model` (`model`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void private void vCreMarcMode(String sBD, Connection con, String sTabl)*/
    
    
    /*Crea la tabla de tallas y colores si no existe*/
    private void vCreTallColo(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`tall`            VARCHAR(30) NOT NULL,\n"                        
                        + "`alma`            VARCHAR(30) NOT NULL,\n"                        
                        + "`colo`            VARCHAR(30) NOT NULL,\n"                        
                        + "`exist`           FLOAT DEFAULT 0,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreTallColo()*/
    
    
    /*Crea la tabla de compatibilidades de producto si no existe*/
    private void vCreCompa(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`compa`           VARCHAR(30) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `compa` (`compa`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCompa()*/
    
        
    /*Crea la tabla de asociaciones Modelos si no existe*/
    private void vCreModelProd(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`mode`            VARCHAR(30) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `mode` (`mode`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreModelProd()*/
    
        
    /*Crea la tabla de asociaciones de series si no existe*/
    private void vCreSerProd(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prod`            VARCHAR(30) NOT NULL,\n"
                        + "`ser`             VARCHAR(255) NOT NULL,\n"                        
                        + "`alma`            VARCHAR(30) NOT NULL,\n"
                        + "`exist`           FLOAT NOT NULL,\n"
                        + "`comen`           VARCHAR(500) NOT NULL,\n"                        
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prod` (`prod`), KEY `ser` (`ser`)"                       
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreSerProd()*/
    
    
    /*Crea la tabla de Modelos si no existe*/
    private void vCreModel(String sBD, Connection con, String sTabl) 
    {        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el Modelo genérico*/
            try 
            {
                /*Inserta marca genérica*/
                sQ = "INSERT INTO " + sTabl + "( cod,          descrip,             estac,      sucu,     nocaj ) "
                                      + "VALUES('SYS',         'Modelo GENÉRICO',   'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log*/
            try 
            {
                sQ = "INSERT INTO logmod(cod, descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                              + "VALUES('SYS','Modelo GENÉRICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreModel()*/
    
    
    /*Crea la tabla de generales si no existe*/
    private void vCreGrals(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`         VARCHAR(255) NOT NULL,\n"
                        + "`estac`           VARCHAR(30) NOT NULL,\n"
                        + "`sucu`            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`           VARCHAR(30) NOT NULL,\n"
                        + "`export`          BIT DEFAULT 0,\n"
                        + "`extr1`           VARCHAR(255) NULL,\n"
                        + "`extr2`           VARCHAR(255) NULL,\n"
                        + "`extr3`           VARCHAR(255) NULL,\n"
                        + "`falt`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `cod_UNIQUE` (`cod`), KEY `cod` (`cod`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el general genérico*/
            try 
            {                
                sQ = "INSERT INTO " + sTabl + "(    cod,    descrip,            estac,      sucu,     nocaj ) "
                                        + "VALUES(  'SYS',  'GENERAL GENÉRICA', 'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de generales*/
            try 
            {
                sQ = "INSERT INTO logcatgral(cod,  descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                   + "VALUES('SYS','GENERAL GENÉRICA', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreGrals()*/


    /*Crea la tabla de correoselectronicos si no existe*/
    private void vCreCorrElec(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de correoselectronicos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de correoselectronicos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de consecutivos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`srvsmtpsal`                      VARCHAR(50) NOT NULL,\n"
                        + "`asunfac`                         VARCHAR(255) NOT NULL,\n"
                        + "`asuncot`                         VARCHAR(255) NOT NULL,\n"
                        + "`asuncontra`                      VARCHAR(255) NOT NULL,\n"
                        + "`asunord`                         VARCHAR(255) NOT NULL,\n"
                        + "`asunrec1`                        VARCHAR(255) NOT NULL,\n"
                        + "`asunrec2`                        VARCHAR(255) NOT NULL,\n"
                        + "`asunrec3`                        VARCHAR(255) NOT NULL,\n"
                        + "`cuerpfac`                        VARCHAR(255) NOT NULL,\n"
                        + "`cuerpcot`                        VARCHAR(255) NOT NULL,\n"
                        + "`cuerpcontra`                     VARCHAR(255) NOT NULL,\n"
                        + "`cuerpord`                        VARCHAR(255) NOT NULL,\n"
                        + "`cuerprec1`                       VARCHAR(255) NOT NULL,\n"
                        + "`cuerprec2`                       VARCHAR(255) NOT NULL,\n"
                        + "`cuerprec3`                       VARCHAR(255) NOT NULL,\n"
                        + "`portsmtp`                        INT(11) NOT NULL,\n"
                        + "`actslenlog`                      bit(1) NOT NULL,\n"
                        + "`usr`                             VARCHAR(61) NOT NULL,\n"
                        + "`pass`                            VARCHAR(255) NOT NULL,\n"
                        + "`corralter`                       VARCHAR(50) NOT NULL,\n"
                        + "`estac`                           VARCHAR(30) NOT NULL,\n"
                        + "`estacglo`                        VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                            VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                           VARCHAR(30) NOT NULL,\n"
                        + "`extr1`                           VARCHAR(255) NULL,\n"
                        + "`extr2`                           VARCHAR(255) NULL,\n"
                        + "`extr3`                           VARCHAR(255) NULL,\n"
                        + "`export`                          BIT DEFAULT 0,\n"
                        + "`falt`                            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fmod`                            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estac` (`estac`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCorrElec()*/


    /*Crea la tabla de basesdatos si no existe*/
    private void vCreBasDat(String sBD, String sNombreEmp, String sCodEmpre, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de basesdatos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de basesdatos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11)         NOT NULL AUTO_INCREMENT,\n"
                        + "`codemp`               VARCHAR(30)     NOT NULL,\n"
                        + "`serv`                 VARCHAR(255)    NOT NULL,\n"
                        + "`usr`                  VARCHAR(255)    NOT NULL,\n"
                        + "`pass`                 VARCHAR(255)    NOT NULL,\n"
                        + "`bd`                   VARCHAR(255)    NOT NULL,\n"
                        + "`usrs`                 VARCHAR(255)    NOT NULL,\n"
                        + "`rutap`                VARCHAR(500)    DEFAULT '',\n"
                        + "`nom`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`ladaen`               VARCHAR(10)     NULL DEFAULT '',\n"
                        + "`metcost`              VARCHAR(10)     NULL DEFAULT 'ultcost',\n"
                        + "`celen`                VARCHAR(255)    DEFAULT '', "
                        + "`tel`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`telen`                VARCHAR(255)    DEFAULT '',\n"
                        + "`telper1en`            VARCHAR(255)    DEFAULT '', "
                        + "`telper2en`            VARCHAR(255)    DEFAULT '', "
                        + "`calle`                VARCHAR(255)    DEFAULT '',\n"
                        + "`calleen`              VARCHAR(255)    DEFAULT '', "
                        + "`col`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`nointen`              VARCHAR(255)    DEFAULT '', "
                        + "`colen`                VARCHAR(255)    DEFAULT '',\n"
                        + "`noextenen`            VARCHAR(21)     DEFAULT '',\n"
                        + "`CP`                   VARCHAR(20)     DEFAULT '',\n"
                        + "`CPen`                 VARCHAR(20)     DEFAULT '',\n"
                        + "`ciu`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`ciuen`                VARCHAR(255)    DEFAULT '',\n"
                        + "`estad`                VARCHAR(255)    DEFAULT '',\n"
                        + "`estaden`              VARCHAR(255)    DEFAULT '',\n"
                        + "`pai`                  VARCHAR(255)    DEFAULT '',\n"
                        + "`paien`                VARCHAR(255)    DEFAULT '',\n"
                        + "`co1en`                VARCHAR(100)    DEFAULT '',\n"
                        + "`co2en`                VARCHAR(100)    DEFAULT '',\n"
                        + "`co3en`                VARCHAR(100)    DEFAULT '',\n"
                        + "`RFC`                  VARCHAR(21)     DEFAULT '',\n"
                        + "`corr`                 VARCHAR(100)    DEFAULT '',\n"
                        + "`pagweb`               VARCHAR(255)    DEFAULT '',\n"
                        + "`noint`                VARCHAR(21)     DEFAULT '',\n"
                        + "`pers`                 VARCHAR(2)      DEFAULT '',\n"
                        + "`noext`                VARCHAR(21)     DEFAULT '',\n"
                        + "`noexten`              VARCHAR(21)     DEFAULT '',\n"
                        + "`estac`                VARCHAR(30)     NOT NULL,\n"
                        + "`lugexp`               VARCHAR(255)    DEFAULT '',\n"
                        + "`regfisc`              VARCHAR(255)    DEFAULT '',\n"
                        + "`rutcer`               VARCHAR(2045)   DEFAULT '',\n"
                        + "`rutkey`               VARCHAR(2045)   DEFAULT '',\n"
                        + "`passcer`              VARCHAR(1000)   DEFAULT '',\n"
                        + "`rutcerf`              VARCHAR(2045)   DEFAULT '',\n"
                        + "`rutkeyf`              VARCHAR(2045)   DEFAULT '',\n"
                        + "`passcerf`             VARCHAR(1000)   DEFAULT '',\n"
                        + "`sucu`                 VARCHAR(30)     NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30)     NOT NULL,\n"
                        + "`export`               BIT             DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255)    NULL,\n"
                        + "`extr2`                VARCHAR(255)    NULL,\n"
                        + "`extr3`                VARCHAR(255)    NULL,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `codemp_UNIQUE` (`codemp`), KEY `codemp` (`codemp`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Obtiene el nombre del equipo local*/
            String sNam = "";
            try 
            {
                sNam = java.net.InetAddress.getLocalHost().getHostName();
            } 
            catch(UnknownHostException e) 
            {
                /*Agrega en el log*/
                Login.vLog(e.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Si la ruta del directorio inicial no existe que lo cree*/
            String sRutA = "\\\\\\\\" + sNam + System.getProperty("user.dir").substring(2).replace("\\", "\\\\") + "\\\\Easy Retail®";
            if(!new File(sRutA).exists())             
                new File(sRutA).mkdir();            

            /*Inserta en la base de datos los primeros datos de conexión a la base de datos encriptados*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(   codemp,             nom,                  serv,                                    usr,                                   pass,                                      bd,                               estac,        falt,             sucu,       nocaj,         rutap,              rfc,                       usrs,                 metcost)"
                                    + "VALUES('" + sCodEmpre + "','" + sNombreEmp + "', '" + Star.sEncrip(Star.sInstancia) + "', '" + Star.sEncrip(Star.sUsuario) + "', '" + Star.sEncrip(Star.sContrasenia) + "', '" + Star.sEncrip(Star.sBD) + "',      'INICIAL',    now(),            'INICIAL', 'INICIAL', '" + sRutA + "',    '" + Star.sRFCGen + "', '" +    Star.sEncrip("2") + "', 'prom')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreBasDat()*/

    
    /*Crea la tabla de usuarios si no existe*/
    private void vCreUrs(String sBD, Connection con, String sTabl) 
    {       
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        
        /*Comprueba si la tabla de usuarios existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de usuarios no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de usuarios*/
            try 
            {
                sQ = "CREATE TABLE IF NOT EXISTS "
                        + "`" + sBD + "`.`" + sTabl + "` ("
                        + "`id_id`                INT(11)         NOT NULL AUTO_INCREMENT, "
                        + "`estac`                VARCHAR(30)     NOT NULL, "
                        + "`52m`                  BIT             NOT NULL, "
                        + "`ptovta`               BIT             DEFAULT 0, "
                        + "`descu`                FLOAT           DEFAULT 0, "
                        + "`comi`                 FLOAT           DEFAULT 0, "
                        + "`habdesc`              INT(11)         DEFAULT 0, "
                        + "`imptic`               VARCHAR(255)    DEFAULT '', "
                        + "`impfac`               VARCHAR(255)    DEFAULT '', "
                        + "`cort`                 VARCHAR(3)      NOT NULL, "
                        + "`pass`                 VARCHAR(255)    NOT NULL, "
                        + "`estacglo`             VARCHAR(30)     NOT NULL, "
                        + "`calle`                VARCHAR(255)    DEFAULT '', "
                        + "`nom`                  VARCHAR(255)    DEFAULT '', "
                        + "`col`                  VARCHAR(255)    DEFAULT '', "
                        + "`cp`                   VARCHAR(20)     DEFAULT '', "
                        + "`tel`                  VARCHAR(255)    DEFAULT '', "
                        + "`cel`                  VARCHAR(255)    DEFAULT '', "
                        + "`pai`                  VARCHAR(255)    DEFAULT '', "
                        + "`ciu`                  VARCHAR(255)    DEFAULT '', "
                        + "`esta`                 VARCHAR(255)    DEFAULT '', "
                        + "`noint`                VARCHAR(255)    DEFAULT '', "
                        + "`res1path`             VARCHAR(2048)   DEFAULT '', "
                        + "`res2path`             VARCHAR(2048)   DEFAULT '', "
                        + "`res3path`             VARCHAR(2048)   DEFAULT '', "
                        + "`rutmysq`              VARCHAR(2048)   DEFAULT '', "
                        + "`noext`                VARCHAR(255)    DEFAULT '', "
                        + "`sucu`                 VARCHAR(30)     NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30)     NOT NULL,\n"
                        + "`export`               BIT             DEFAULT 0,\n"
                        + "`admcaj`               bit             DEFAULT 0,\n"
                        + "`vend`                 bit             DEFAULT 0,\n"
                        + "`mandagra`             bit             DEFAULT 0,\n"
                        + "`manddia`              INT             DEFAULT 0,\n"
                        + "`mandcump`             bit             DEFAULT 0,\n"                        
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "`fmod`                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + " PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),"
                        + " UNIQUE KEY `estac_UNIQUE` (`estac`), KEY `estac` (`estac`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Encripta la clave de supervisor*/
            String sCla = Star.sEncrip("SUP");

            /*Encripta la clave de ventas*/
            String sClaVta = Star.sEncrip("VENTAS");

            /*Encripta la clave de compras*/
            String sClaCom = Star.sEncrip("COMPRAS");

            /*Encripta la clave de caja*/
            String sClaCaj = Star.sEncrip("CAJA");

            /*Encripta la clave de CXC*/
            String sClaCXC = Star.sEncrip("CXC");

            /*Encripta la clave de CXP*/
            String sClaCXP = Star.sEncrip("CXP");

            /*Obtiene la impresora por default*/
            PrintService se = PrintServiceLookup.lookupDefaultPrintService();

            /*Inserta el usuario SUP en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(estac,      nom,            52m, descu,  habdesc,  imptic,               impfac,              cort,  pass,      estacglo,       sucu,      nocaj,     falt,     fmod, admcaj,    vend) "
                                      + "VALUES('SUP',      'SUPERVISOR',   0,   100,   1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sCla + "', 'INICIAL',      'INICIAL', 'INICIAL', now(),    now(),1,          1)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario de respaldos en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,          52m, descu, habdesc,  imptic,                impfac,          cort,     pass,       estacglo,   sucu,      nocaj,     falt,     fmod, admcaj) "
                                        + "VALUES(  'RESPALDO',     0,   0,     0,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sCla + "',  'INICIAL',  'INICIAL', 'INICIAL', now(),    now(),0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario VENTAS en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,      52m, descu, habdesc,  imptic,               impfac,             cort,   pass,           estacglo,       sucu,      nocaj,     falt,      fmod, admcaj,  vend) "
                                        + "VALUES(  'VENTAS',   0,   0,     1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sClaVta + "', 'INICIAL',        'INICIAL', 'INICIAL', now(),     now(),0,       1)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario COMPRAS en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,       52m, descu, habdesc,  imptic,               impfac,             cort,   pass,           estacglo,       sucu,      nocaj,     falt,     fmod, admcaj) "
                                        + "VALUES(  'COMPRAS',   0,   0,     1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sClaCom + "',   'INICIAL',      'INICIAL', 'INICIAL', now(),    now(),0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario CAJA en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,      52m, descu, habdesc,  imptic,               impfac,             cort,   pass,           estacglo,       sucu,      nocaj,     falt,     fmod, admcaj) "
                                        + "VALUES(  'CAJA',      0,   0,     1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sClaCaj + "', 'INICIAL',        'INICIAL', 'INICIAL', now(),    now(),0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario CXC en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,      52m, descu, habdesc,  imptic,                impfac,             cort,  pass,           estacglo,       sucu,      nocaj,     falt,     fmod, admcaj) "
                                        + "VALUES(  'CXC',       0,   0,     1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sClaCXC + "',   'INICIAL',      'INICIAL', 'INICIAL', now(),    now(),0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario CXP en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    estac,      52m, descu, habdesc,  imptic,               impfac,             cort,   pass,           estacglo,       sucu,      nocaj,     falt,         fmod, admcaj) "
                                        + "VALUES(  'CXP',       0,   0,     1,   '" + se.getName() + "','" + se.getName() + "',  1,'" + sClaCXP + "',   'INICIAL',      'INICIAL', 'INICIAL', now(),        now(),0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreUrs()*/


    /*Crea la tabla de empresas si no existe*/
    private void vCreCli(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de clientes no existe*/
        if(!iSi) 
        {
            /*Crea la tabla de empresas*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"                        
                        + "`codemp`               VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"
                        + "`lada`                 VARCHAR(10) NULL DEFAULT '',\n"
                        + "`vend`                 VARCHAR(30) NULL DEFAULT '',\n"
                        + "`codclas`              VARCHAR(30) NOT NULL,\n"
                        + "`banc`                 VARCHAR(30) DEFAULT '',\n"
                        + "`clavbanc`             VARCHAR(255) DEFAULT '',\n"                        
                        + "`zon`                  VARCHAR(30) DEFAULT '',\n"
                        + "`giro`                 VARCHAR(30) DEFAULT '',\n"
                        + "`nom`                  VARCHAR(255) NOT NULL,\n"
                        + "`clasjera`             VARCHAR(2048) DEFAULT '',\n"
                        + "`tel`                  VARCHAR(255) DEFAULT '',\n"
                        + "`ctapred`              VARCHAR(100) DEFAULT '',\n"
                        + "`cel`                  VARCHAR(255) DEFAULT '',\n"
                        + "`exten`                VARCHAR(20) DEFAULT '',\n"
                        + "`telper1`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper2`              VARCHAR(255) DEFAULT '',\n"
                        + "`calle`                VARCHAR(255) DEFAULT '',\n"
                        + "`col`                  VARCHAR(255) DEFAULT '',\n"
                        + "`curp`                 VARCHAR(50) DEFAULT '',\n"
                        + "`CP`                   VARCHAR(20) DEFAULT '',\n"
                        + "`ciu`                  VARCHAR(255) DEFAULT '',\n"
                        + "`estad`                VARCHAR(255) DEFAULT '',\n"
                        + "`pai`                  VARCHAR(255) DEFAULT '',\n"
                        + "`revis`                VARCHAR(100) DEFAULT '',\n"
                        + "`pags`                 VARCHAR(100) DEFAULT '',\n"
                        + "`RFC`                  VARCHAR(21) DEFAULT '',\n"
                        + "`descu`                FLOAT DEFAULT '0',\n"
                        + "`deposit`              FLOAT DEFAULT '0',\n"
                        + "`co1`                  VARCHAR(100) DEFAULT '',\n"
                        + "`co2`                  VARCHAR(100) DEFAULT '',\n"
                        + "`otramon`              bit DEFAULT 1,\n"
                        + "`otramonc`             bit DEFAULT 1,\n"
                        + "`co3`                  VARCHAR(100) DEFAULT '',\n"
                        + "`pagweb1`              VARCHAR(255) DEFAULT '',\n"
                        + "`pagweb2`              VARCHAR(255) DEFAULT '',\n"
                        + "`contac`               VARCHAR(255) DEFAULT '',\n"
                        + "`puest`                VARCHAR(255) DEFAULT '',\n"
                        + "`contact2`             VARCHAR(255) DEFAULT '',\n"
                        + "`puest2`               VARCHAR(255) DEFAULT '',\n"
                        + "`observ`               VARCHAR(255) DEFAULT '',\n"
                        + "`noint`                VARCHAR(100) DEFAULT '',\n"
                        + "`noext`                VARCHAR(100) DEFAULT '',\n"
                        + "`diacred`              VARCHAR(21) DEFAULT '',\n"
                        + "`metpag`               VARCHAR(45) DEFAULT '',\n"
                        + "`cta`                  VARCHAR(45) DEFAULT '0000',\n"
                        + "`encall`               VARCHAR(255) DEFAULT '',\n"
                        + "`enciu`                VARCHAR(255) DEFAULT '',\n"
                        + "`ennoext`              VARCHAR(21) DEFAULT '',\n"
                        + "`ennoexten`            VARCHAR(21) DEFAULT '',\n"
                        + "`enlada`               VARCHAR(10) NULL DEFAULT '',\n"
                        + "`ennoint`              VARCHAR(21) DEFAULT '',\n"
                        + "`encol`                VARCHAR(255) DEFAULT '',\n"
                        + "`encp`                 VARCHAR(20) DEFAULT '',\n"
                        + "`encel`                VARCHAR(255) DEFAULT '',\n"
                        + "`entel1`               VARCHAR(255) DEFAULT '',\n"
                        + "`entel2`               VARCHAR(255) DEFAULT '',\n"
                        + "`entel3`               VARCHAR(255) DEFAULT '',\n"
                        + "`enemail`              VARCHAR(100) DEFAULT '',\n"
                        + "`enco1`                VARCHAR(100) DEFAULT '',\n"
                        + "`enco2`                VARCHAR(100) DEFAULT '',\n"
                        + "`enco3`                VARCHAR(100) DEFAULT '',\n"
                        + "`enpagweb`             VARCHAR(255) DEFAULT '',\n"
                        + "`enpai`                VARCHAR(255) DEFAULT '',\n"
                        + "`enestad`              VARCHAR(255) DEFAULT '',\n"
                        + "`diapag`               int DEFAULT 0,\n"
                        + "`pers`                 VARCHAR(2) DEFAULT '',\n"
                        + "`limtcred`             FLOAT DEFAULT '0',\n"
                        + "`bloq`                 bit DEFAULT   0,\n"
                        + "`bloqlimcred`          bit DEFAULT   0,\n"
                        + "`ctaconta`             VARCHAR(30) DEFAULT '',\n"
                        + "`list`                 INT(11) NOT NULL ,\n"                        
                        + "`contac3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper33`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper44`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper55`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper66`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper77`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper88`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper99`             VARCHAR(255) DEFAULT '',\n"
                        + "`Contac10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telcon10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telper10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telper100`            VARCHAR(255) DEFAULT '',\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`cumple`               VARCHAR(30) NULL DEFAULT '',\n"
                        + "`agradfec`             TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`anio`                 VARCHAR(10) NULL DEFAULT '',\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codemp` (`codemp`), KEY `ser` (`ser`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta cadena vacia para que incialmente en algunas opciones no alla selección inicial de empresa*/
            try 
            {
                /*Inserta la empresa cliente mostrador*/
                sQ = "INSERT INTO " + sTabl + " ( codemp,       nom,                  estac,       list, codclas,    sucu,       nocaj,      ser,        rfc) "
                                        + "VALUES('MOS',        'PUBLICO GENERAL',    'INICIAL',   1,   'SYS',      'INICIAL',  'INICIAL',  'EMP',  '" + Star.sRFCGen + "')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de empresas*/
            try 
            {
                sQ = "INSERT INTO logemps(      emp,        nom,                  ser,  accio,           estac,     sucu,     nocaj,     falt) "
                                + "VALUES(      'EMPMOS',   'CLIENTE MOSTRADOR',  'EMP', 'AGREGAR',       'INICIAL',  'INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCli()*/


    /*Crea la tabla de respaldos si no existe*/
    private void vCreResp(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;           
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla  no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`hrs`                  INT(11) NOT NULL,\n"
                        + "`estacres`             VARCHAR(30) NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estacres` (`estacres`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el usuario de respaldo*/
            try 
            {            
                sQ = "INSERT INTO " + sTabl + " ( estacres,     hrs,   estac,     sucu,         nocaj) "
                        + "VALUES('RESPALDO',   2,     'INICIAL', 'INICIAL',    'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreResp()*/


    /*Crea la tabla de log de respaldos si no existe*/
    private void vCreRespLog(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe entonces*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`tip`                  BIT NOT NULL,\n"
                        + "`return`               INT(11) DEFAULT -1,\n"
                        + "`pathdemysq`           VARCHAR(2048) DEFAULT '',\n"
                        + "`pathamysq`            VARCHAR(2048) DEFAULT '',\n"
                        + "`pathde`               VARCHAR(2048) DEFAULT '',\n"
                        + "`patha`                VARCHAR(2048) DEFAULT '',\n"
                        + "`msj`                  VARCHAR(500) DEFAULT '',\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `tip` (`tip`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreRespLog()*/


    /*Crea la tabla de mensajes si no existe*/
    private void vCreMsjs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`msj`                  VARCHAR(1000) NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreMsjs()*/


    /*Crea la tabla de cxc si no existe*/
    private void vCreCXC(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`norefer`              VARCHAR(50) NOT NULL,\n"
                        + "`noser`                VARCHAR(20) NOT NULL,\n"
                        + "`empre`                VARCHAR(30) NOT NULL,\n"
                        + "`formpag`              VARCHAR(30) DEFAULT '',\n"
                        + "`conceppag`            VARCHAR(30) DEFAULT '',\n"
                        + "`ser`                  VARCHAR(20) NOT NULL,\n"
                        + "`subtot`               FLOAT NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"                      
                        + "`impue`                FLOAT NOT NULL,\n"
                        + "`carg`                 FLOAT NOT NULL,\n"
                        + "`abon`                 FLOAT NOT NULL,\n"
                        + "`comen`                VARCHAR(500) DEFAULT '',\n"
                        + "`concep`               VARCHAR(30) DEFAULT '',\n"
                        + "`folbanc`              VARCHAR(50) DEFAULT '',\n"
                        + "`fvenc`                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fdoc`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`fol`                  INT(11) DEFAULT 0,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"                        
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `noser` (`noser`), KEY `norefer` (`norefer`), KEY `empre` (`empre`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCXC()*/


    /*Crea la tabla de log de usuarios si no existe*/
    private void vCreLogEstac(String sBD, Connection con, String sTabl) 
    {                
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())             
                iSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`proxlog`              TIME NOT NULL ,\n"
                        + "`port`                 VARCHAR(30) NOT NULL, "
                        + "`portudp`              VARCHAR(30) NOT NULL, "
                        + "`host`                 VARCHAR(30) NOT NULL, "
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estac` (`estac`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogEstac()*/


    /*Crea la tabla de log de log de inicio de los usuarios*/
    private void vCreLogIni(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;       
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`             INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`entsal`               bit NOT NULL,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NOT NULL ,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estac` (`estac`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogIni()*/


    /*Crea la tabla de cxp si no existe*/
    private void vCreCXP(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`norefer`              VARCHAR(50) NOT NULL,\n"
                        + "`noser`                VARCHAR(30) NOT NULL,\n"
                        + "`prov`                 VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"
                        + "`formpag`              VARCHAR(30) DEFAULT '',\n"
                        + "`conceppag`            VARCHAR(30) DEFAULT '',\n"
                        + "`comen`                VARCHAR(500) DEFAULT '',\n"
                        + "`subtot`               FLOAT NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"
                        + "`impue`                FLOAT NOT NULL,\n"                        
                        + "`carg`                 FLOAT NOT NULL,\n"
                        + "`abon`                 FLOAT NOT NULL,\n"
                        + "`concep`               VARCHAR(30) DEFAULT '',\n"
                        + "`folbanc`              VARCHAR(50) DEFAULT '',\n"
                        + "`fvenc`                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`fdoc`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`fol`                  INT(11) DEFAULT 0,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"                        
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `norefer` (`norefer`), KEY `noser` (`noser`), KEY `prov` (`prov`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCXP()*/

        
    /*Crea la tabla de contrarecibos si no existe*/
    private void vCreContra(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean iSi = false;
        try{
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                iSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!iSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`id_idp`               INT(11) NOT NULL,\n"
                        + "`prov`                 VARCHAR(30) NOT NULL,\n"
                        + "`respon`               VARCHAR(100) NOT NULL,\n"
                        + "`comp`                 VARCHAR(30) NOT NULL,\n"
                        + "`tot`                  FLOAT NOT NULL,\n"
                        + "`fech`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "PRIMARY KEY (`id_id`),\n"
                        + "UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `prov` (`prov`), KEY `comp` (`comp`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreContra()*/


    /*Crea la tabla de partidas de ordenes de compra si no existe*/
    private void vCrePartOrds(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de partords no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de ords*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codord`           VARCHAR(30) NOT NULL,\n"
                        + "`codcot`           VARCHAR(30) NOT NULL,\n"
                        + "`prod`             VARCHAR(30) NOT NULL,\n"
                        + "`cant`             INT(11) NOT NULL,\n"
                        + "`alma`             VARCHAR(30) NOT NULL,\n"
                        + "`unid`             VARCHAR(30) NOT NULL,\n"
                        + "`descrip`          VARCHAR(255) NOT NULL,\n"
                        + "`mon`              VARCHAR(30) NOT NULL,\n"
                        + "`descu`            INT(11) NOT NULL,\n"
                        + "`descad`           INT(11) NOT NULL,\n"
                        + "`impue`            VARCHAR(30) NOT NULL,\n"
                        + "`impueval`         INT(11) NOT NULL,\n"
                        + "`ultcost`          FLOAT NOT NULL,\n"
                        + "`impo`             FLOAT NOT NULL,\n"
                        + "`impoimpue`        FLOAT NOT NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`fent`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codord` (`codord`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePartOrds()*/


    /*Crea la tabla de ordenes de compra si no existe*/
    private void vCreOrds(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la tabla de ords existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de ords no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de ords*/
            try {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`codord`           INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prov`             VARCHAR(30) NOT NULL,\n"
                        + "`proy`             VARCHAR(30) NOT NULL,\n"
                        + "`subtot`           FLOAT NOT NULL,\n"
                        + "`impue`            FLOAT NOT NULL,\n"
                        + "`total`            FLOAT NOT NULL,\n"
                        + "`eje`              VARCHAR(255) NOT NULL,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`fent`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`codord`), KEY `prov` (`prov`), KEY `proy` (`proy`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ.trim() + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreOrds()*/


    /*Crea la tabla de viáticos para proyecto si no existe*/
    private void vCreViatsPro(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de viatspro existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de viatspro no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de viatspro*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`            INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`codpro`           VARCHAR(30) NOT NULL,\n"
                        + "`codviat`          VARCHAR(30) NOT NULL,\n"
                        + "`pag`              VARCHAR(30) DEFAULT '',\n"
                        + "`nomper`           VARCHAR(255) DEFAULT '',\n"
                        + "`imp`              double DEFAULT 0,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`f_de`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`f_a`              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`falt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `codpro` (`codpro`), KEY `codviat` (`codviat`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreViatsPro()*/


    /*Crea la tabla de corteszx si no existe*/
    private void vCreCortszx(String sBD, Connection con, String sTbl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de corteszx existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTbl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de corteszx no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTbl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`numcort`              INT(11) NOT NULL,\n"
                        + "`cort`                 VARCHAR(30) NOT NULL,\n"
                        + "`regis`                INT(11) DEFAULT 0,\n"
                        + "`totvtas`              INT(11) NOT NULL,\n"
                        + "`totingr`              FLOAT NOT NULL,\n"
                        + "`totegre`              FLOAT NOT NULL,\n"
                        + "`totcaj`               FLOAT NOT NULL,\n"
                        + "`vtabruta`             FLOAT DEFAULT 0,\n"
                        + "`descu`                FLOAT DEFAULT 0,\n"
                        + "`devs`                 FLOAT DEFAULT 0,\n"
                        + "`vtanet`               FLOAT DEFAULT 0,\n"
                        + "`impue`                FLOAT DEFAULT 0,\n"
                        + "`vtanetimp`            FLOAT DEFAULT 0,\n"
                        + "`totfacs`              FLOAT DEFAULT 0,\n"
                        + "`tottics`              FLOAT DEFAULT 0,\n"
                        + "`cantfac`              INT(11) DEFAULT 0,\n"
                        + "`canttics`             INT(11) DEFAULT 0,\n"
                        + "`totefe`               FLOAT DEFAULT 0,\n"
                        + "`totdeb`               FLOAT DEFAULT 0,\n"
                        + "`tottarcred`           FLOAT DEFAULT 0,\n"
                        + "`totdep`               FLOAT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreCortszx()*/


    /*Crea la tabla de flujo  si no existe*/
    private void vCreFluj(String sBD, Connection con, String sTbl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de flujo existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTbl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos y coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de flujo no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de flujo*/
            try 
            {
                sQ = "CREATE TABLE `" + sTbl + "` (\n"
                        + "`fluj`             INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`concep`           VARCHAR(100) NOT NULL,\n"
                        + "`tipdoc`           VARCHAR(50) NOT NULL,\n"
                        + "`norefer`          VARCHAR(50) NOT NULL,\n"
                        + "`ing_eg`           VARCHAR(5) NOT NULL,\n"
                        + "`impo`             FLOAT NOT NULL,\n"
                        + "`mon`              VARCHAR(100) NOT NULL,\n"
                        + "`modd`             VARCHAR(10) NOT NULL,\n"
                        + "`vta`              INT(11) NOT NULL,\n"
                        + "`ncortz`           INT(11) NOT NULL,\n"
                        + "`corta`            INT(11) DEFAULT 0,\n"
                        + "`estac`            VARCHAR(30) NOT NULL,\n"
                        + "`sucu`             VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`            VARCHAR(30) NOT NULL,\n"
                        + "`export`           BIT DEFAULT 0,\n"
                        + "`extr1`            VARCHAR(255) NULL,\n"
                        + "`extr2`            VARCHAR(255) NULL,\n"
                        + "`extr3`            VARCHAR(255) NULL,\n"
                        + "`falt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`fluj`), UNIQUE KEY `fluj_UNIQUE` (`fluj`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreFluj()*/


    /*Crea la tabla de viaticos si no existe*/
    private void vCreViats(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de viaticos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de viaticos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`              INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`viat`                 VARCHAR(255) NOT NULL,\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `viat` (`viat`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreViats()*/


    /*Crea la tabla de pags si no existe*/
    private void vCrePags(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        /*Comprueba si la tabla de pags existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de pags no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`          INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`cod`            VARCHAR(30) NOT NULL,\n"
                        + "`descrip`        VARCHAR(255) NOT NULL,\n"
                        + "`estac`          VARCHAR(30) NOT NULL,\n"
                        + "`sucu`           VARCHAR(30) NOT NULL,\n"
                        + "`export`         BIT DEFAULT 0,\n"
                        + "`nocaj`          VARCHAR(30) NOT NULL,\n"
                        + "`extr1`          VARCHAR(255) NULL,\n"
                        + "`extr2`          VARCHAR(255) NULL,\n"
                        + "`extr3`          VARCHAR(255) NULL,\n"
                        + "`falt`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `cod` (`cod`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos tipos de pags*/
            try 
            {
                /*Efectivo*/
                sQ = "INSERT INTO pags(cod,     descrip,        estac,      sucu,      nocaj,       falt,       fmod) "
                            + "VALUES('EFE',    'EFECTIVO',     'INICIAL', 'INICIAL', 'INICIAL',    now(),      now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Tarjeta de Crédito*/
                sQ = "INSERT INTO pags(cod,     descrip,                estac,      sucu,      nocaj,     falt,     fmod) "
                             + "VALUES('TAR',   'TARJETA DE CREDITO',   'INICIAL',  'INICIAL', 'INICIAL', now(),    now())";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Tarjeta de débito*/
                sQ = "INSERT INTO pags(cod,     descrip,                estac,      sucu,      nocaj,     falt,     fmod) "
                             + "VALUES('DEB',   'TARJETA DE DÉBITO',    'INICIAL',  'INICIAL', 'INICIAL', now(),     now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException | HeadlessException e) 
            {
                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Agrega en el log
                Login.vLog(e.getMessage());
                
                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
                
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePags()*/


    /*Crea la tabla de logcorreos si no existe*/
    private void vCreLogCorr(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        
        
        
        /*Comprueba si la tabla de logcorreos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de logcorreos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de logcorreos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`corrde`                   VARCHAR(61) NOT NULL,\n"
                        + "`corr`                     VARCHAR(61) NOT NULL,\n"
                        + "`nodoc`                    VARCHAR(255) NOT NULL,\n"
                        + "`tipdoc`                   VARCHAR(100) NOT NULL,\n"
                        + "`estad`                    VARCHAR(100) NOT NULL,\n"
                        + "`motiv`                    VARCHAR(500) NOT NULL,\n"
                        + "`estac`                    VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                     VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                    VARCHAR(30) NOT NULL,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`falt`                     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreLogCorr()*/


    /*Crea la tabla de estadísticas de correo si no existe*/
    private void vCreEstadisCor(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de logcorreos no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de logcorreos*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`corrde`                   VARCHAR(61) NOT NULL,\n"
                        + "`corr`                     VARCHAR(61) NOT NULL,\n"
                        + "`nodoc`                    VARCHAR(255) NOT NULL,\n"
                        + "`tipdoc`                   VARCHAR(100) NOT NULL,\n"
                        + "`estad`                    VARCHAR(100) NOT NULL,\n"
                        + "`motiv`                    VARCHAR(500) NOT NULL,\n"
                        + "`estac`                    VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                     VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                    VARCHAR(30) NOT NULL,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`falt`                     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`))";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreEstadisCor()*/
    
        
    /*Crea la tabla de pers si no existe*/
    private void vCrePers(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de pers no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de pers*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                    INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`nom`                      VARCHAR(255) NOT NULL,\n"
                        + "`act`                      bit DEFAULT 1,\n"
                        + "`rutimg`                   VARCHAR(500) DEFAULT '',\n"
                        + "`calle`                    VARCHAR(255) DEFAULT '',\n"
                        + "`col`                      VARCHAR(255) DEFAULT '',\n"
                        + "`noext`                    VARCHAR(21) DEFAULT '',\n"
                        + "`noint`                    VARCHAR(21) DEFAULT '',\n"
                        + "`CP`                       VARCHAR(6) DEFAULT '',\n"
                        + "`tel1`                     VARCHAR(255) DEFAULT '',\n"
                        + "`tel2`                     VARCHAR(255) DEFAULT '',\n"
                        + "`cel1`                     VARCHAR(255) DEFAULT '',\n"
                        + "`cel2`                     VARCHAR(255) DEFAULT '',\n"
                        + "`fingemp`                  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fsalemp`                  TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`teleme1`                  VARCHAR(255) DEFAULT '',\n"
                        + "`teleme2`                  VARCHAR(255) DEFAULT '',\n"
                        + "`contac1`                  VARCHAR(4255) DEFAULT NULL,\n"
                        + "`contac2`                  VARCHAR(255) DEFAULT '',\n"
                        + "`sueldactua`               FLOAT DEFAULT '0',\n"
                        + "`textra`                   FLOAT DEFAULT '0',\n"
                        + "`sueldfora`                FLOAT DEFAULT '0',\n"
                        + "`estac`                    VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                     VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                    VARCHAR(30) NOT NULL,\n"
                        + "`export`                   BIT DEFAULT 0,\n"
                        + "`extr1`                    VARCHAR(255) NULL,\n"
                        + "`extr2`                    VARCHAR(255) NULL,\n"
                        + "`extr3`                    VARCHAR(255) NULL,\n"
                        + "`falt`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `nom` (`nom`),\n"
                        + "UNIQUE KEY `nom_UNIQUE` (`nom`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta persona genérica*/
            try 
            {
                sQ = "INSERT INTO pers    (nom,                 sucu,       nocaj,      estac) "
                        + "VALUES('PERSONA GENERICA',   'INICIAL',  'INICIAL',  'INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException | HeadlessException e) 
            {
                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Agrega en el log y mensajea
                Login.vLog(e.getMessage());
                
                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePers()*/


    /*Crea la tabla de proveedores si no existe*/
    private void vCreProvs(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de proveedores existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de proveedores no existe*/
        if(!bSi) 
        {
            /*Crea la tabla de proveedores*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`prov`                 VARCHAR(30) NOT NULL,\n"
                        + "`ser`                  VARCHAR(30) NOT NULL,\n"
                        + "`codclas`              VARCHAR(30) NOT NULL,\n"
                        + "`zon`                  VARCHAR(30) DEFAULT '',\n"
                        + "`giro`                 VARCHAR(30) DEFAULT '',\n"
                        + "`tentre`               VARCHAR(255) DEFAULT '',\n"
                        + "`rubr`                 VARCHAR(30) DEFAULT '',\n"
                        + "`banc`                 VARCHAR(30) DEFAULT '',\n"
                        + "`clavbanc`             VARCHAR(255) DEFAULT '',\n"
                        + "`lada`                 VARCHAR(10) NULL DEFAULT '',\n"
                        + "`metpag`               VARCHAR(45) DEFAULT '',\n"
                        + "`cta`                  VARCHAR(45) DEFAULT '0000',\n"
                        + "`enlada`               VARCHAR(10) NULL DEFAULT '',\n"
                        + "`nom`                  VARCHAR(255) NOT NULL,\n"
                        + "`tel`                  VARCHAR(255) DEFAULT '',\n"
                        + "`entel`                VARCHAR(255) DEFAULT '',\n"
                        + "`cel`                  VARCHAR(255) DEFAULT '',\n"
                        + "`encel`                VARCHAR(255) DEFAULT '',\n"
                        + "`clasjera`             VARCHAR(2048) DEFAULT '',\n"
                        + "`exten`                VARCHAR(20) DEFAULT '',\n"
                        + "`enexten`              VARCHAR(20) DEFAULT '',\n"
                        + "`calle`                VARCHAR(255) DEFAULT '',\n"
                        + "`encalle`              VARCHAR(255) DEFAULT '',\n"
                        + "`col`                  VARCHAR(255) DEFAULT '',\n"
                        + "`encol`                VARCHAR(255) DEFAULT '',\n"
                        + "`cp`                   VARCHAR(20) DEFAULT '',\n"
                        + "`encp`                 VARCHAR(20) DEFAULT '',\n"
                        + "`ennoint`              VARCHAR(100) DEFAULT '',\n"
                        + "`noint`                VARCHAR(100) DEFAULT '',\n"
                        + "`noext`                VARCHAR(100) DEFAULT '',\n"
                        + "`ennoext`              VARCHAR(100) DEFAULT '',\n"
                        + "`ciu`                  VARCHAR(255) DEFAULT '',\n"
                        + "`enciu`                VARCHAR(255) DEFAULT '',\n"
                        + "`estad`                VARCHAR(255) DEFAULT '',\n"
                        + "`enestad`              VARCHAR(255) DEFAULT '',\n"
                        + "`otramon`              bit DEFAULT 1,\n"
                        + "`otramonc`             bit DEFAULT 1,\n"
                        + "`bloq`                 bit DEFAULT 1,\n"
                        + "`ctaconta`             VARCHAR(30) DEFAULT '',\n"
                        + "`revis`                VARCHAR(100) DEFAULT '',\n"
                        + "`pags`                 VARCHAR(100) DEFAULT '',\n"
                        + "`pais`                 VARCHAR(255) DEFAULT '',\n"
                        + "`enpais`               VARCHAR(255) DEFAULT '',\n"
                        + "`rfc`                  VARCHAR(21) DEFAULT '',\n"
                        + "`descu`                FLOAT DEFAULT '0',\n"
                        + "`diacred`              VARCHAR(21) DEFAULT '0',\n"
                        + "`limcred`              FLOAT DEFAULT '0',\n"
                        + "`bloqlimcred`          bit DEFAULT   0,\n"
                        + "`co1`                  VARCHAR(100) DEFAULT '',\n"
                        + "`enco1`                VARCHAR(100) DEFAULT '',\n"
                        + "`co2`                  VARCHAR(100) DEFAULT '',\n"
                        + "`enco2`                VARCHAR(100) DEFAULT '',\n"
                        + "`co3`                  VARCHAR(100) DEFAULT '',\n"
                        + "`enco3`                VARCHAR(100) DEFAULT '',\n"
                        + "`pagweb1`              VARCHAR(255) DEFAULT '',\n"
                        + "`pagweb2`              VARCHAR(255) DEFAULT '',\n"
                        + "`eje1`                 VARCHAR(255) DEFAULT '',\n"
                        + "`telper1`              VARCHAR(255) DEFAULT '',\n"
                        + "`entelper1`            VARCHAR(255) DEFAULT '',\n"
                        + "`telper2`              VARCHAR(255) DEFAULT '',\n"
                        + "`entelper2`            VARCHAR(255) DEFAULT '',\n"
                        + "`eje2`                 VARCHAR(255) DEFAULT '',\n"
                        + "`telper21`             VARCHAR(255) DEFAULT '',\n"
                        + "`telper22`             VARCHAR(255) DEFAULT '',\n"
                        + "`observ`               VARCHAR(255) DEFAULT '',\n"
                        + "`pers`                 VARCHAR(2) DEFAULT '',\n"
                        + "`contac3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper3`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper33`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper4`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper44`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper5`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper55`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper6`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper66`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper7`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper77`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper8`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper88`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telcon9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper9`              VARCHAR(255) DEFAULT '',\n"
                        + "`telper99`             VARCHAR(255) DEFAULT '',\n"
                        + "`contac10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telcon10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telper10`             VARCHAR(255) DEFAULT '',\n"
                        + "`telper100`            VARCHAR(255) DEFAULT '',\n"
                        + "`estac`                VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                 VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                VARCHAR(30) NOT NULL,\n"
                        + "`export`               BIT DEFAULT 0,\n"
                        + "`extr1`                VARCHAR(255) NULL,\n"
                        + "`extr2`                VARCHAR(255) NULL,\n"
                        + "`extr3`                VARCHAR(255) NULL,\n"
                        + "`falt`                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `prov_UNIQUE` (`prov`), KEY `prov` (`prov`), KEY `ser` (`ser`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el proveedor genérico en la base de datos*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + " (prov,     nom,                 estac,      codclas,    sucu,       nocaj,     ser,        bloq) "
                                     + "VALUES( 'SYS',    'PROVEEDOR GENÉRICO','INICIAL',   'SYS',      'INICIAL',  'INICIAL', 'PROV', 0)";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de proveedores*/
            try 
            {
                sQ = "INSERT INTO logprovs(   prov,           nom,                   ser,   accio,       estac,     sucu,    nocaj,     falt) "
                                    + "VALUES('SYS',          'PROVEEDOR GENÉRICO',  'PROV', 'AGREGAR',   'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreProvs()*/


    /*Crea la tabla de almacenes si no existe*/
    private void vCreAlmas(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de almacenes existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        }
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            /*Crea la tabla*/
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`               INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`alma`                VARCHAR(30) NOT NULL,\n"
                        + "`respon`              VARCHAR(30) NOT NULL,\n"
                        + "`almadescrip`         VARCHAR(255) NOT NULL,\n"
                        + "`dir1`                VARCHAR(255) DEFAULT '',\n"
                        + "`dir2`                VARCHAR(255) DEFAULT '',\n"
                        + "`dir3`                VARCHAR(255) DEFAULT '',\n"
                        + "`actfij`              BIT DEFAULT 0,\n"
                        + "`estac`               VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`               VARCHAR(30) NOT NULL,\n"
                        + "`export`              BIT DEFAULT 0,\n"
                        + "`extr1`               VARCHAR(255) NULL,\n"
                        + "`extr2`               VARCHAR(255) NULL,\n"
                        + "`extr3`               VARCHAR(255) NULL,\n"
                        + "`falt`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `alma_UNIQUE` (`alma`), KEY `alma` (`alma`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el almacén genérico*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "( alma,       almadescrip,        estac,      sucu,       nocaj,        respon) "
                        + "VALUES('SYS',         'ALMACÉN GENÉRICO', 'INICIAL',  'INICIAL',  'INICIAL',    '')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de almacenes*/
            try 
            {
                sQ = "INSERT INTO logalmas(     cod,  descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'SYS','ALMACÉN GENÉRICO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta el almacén de activo fijo*/
            try 
            {
                sQ = "INSERT INTO " + sTabl + "(    alma,           almadescrip,   estac,      sucu,       nocaj,        respon) "
                                        + "VALUES(  'ACTFIJ',       'ACTIVO FIJO', 'INICIAL',  'INICIAL',  'INICIAL',    '')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta en log de almacenes*/
            try 
            {
                sQ = "INSERT INTO logalmas( cod,        descrip,       accio,         estac,    sucu,      nocaj,    falt) "
                                + "VALUES(  'ACTFIJ',   'ACTIVO FIJO', 'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }
            
        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreAlmas()*/


    /*Crea la tabla de conceptos si no existe*/
    private void vCreConcep(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        
        /*Comprueba si la tabla de conceptos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla de conceptos no existe*/
        if(!bSi) 
        {
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                   INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`concep`                  VARCHAR(30) NOT NULL,\n"
                        + "`descrip`                 VARCHAR(255) NOT NULL,\n"
                        + "`estac`                   VARCHAR(30) NOT NULL,\n"
                        + "`sucu`                    VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                   VARCHAR(30) NOT NULL,\n"
                        + "`export`                  BIT DEFAULT 0,\n"
                        + "`extr1`                   VARCHAR(255) NULL,\n"
                        + "`extr2`                   VARCHAR(255) NULL,\n"
                        + "`extr3`                   VARCHAR(255) NULL,\n"
                        + "`falt`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`),\n"
                        + "UNIQUE KEY `concep_UNIQUE` (`concep`)\n"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta algunos conceptos*/
            try 
            {
                /*Inserta el concepto ajuste por inventario*/
                sQ = "INSERT INTO " + sTabl + "(    concep,         descrip,                estac,      sucu,     nocaj ) "
                                        + "VALUES(  'AIN',          'AJUSTE POR INVENTARIO','INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de merma*/
                sQ = "INSERT INTO " + sTabl + "(    concep,         descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'MER',          'MERMA',     'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de robo*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'ROB',       'ROBO',           'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de mal estado*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'MAL',      'MAL ESTADO',     'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de compra*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip, estac,      sucu,     nocaj ) "
                                        + "VALUES(  'COM',      'COMPRA',      'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de caduco*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip, estac,      sucu,     nocaj ) "
                                        + "VALUES(  'CAD',      'CADUCO',      'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de roto*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip, estac,      sucu,     nocaj ) "
                                        + "VALUES(  'ROT',      'ROTO',        'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de defectuoso*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip, estac,      sucu,     nocaj ) "
                                        + "VALUES(  'DEF',      'DEFECTUOSO',  'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de mojado*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip, estac,      sucu,     nocaj ) "
                                        + "VALUES(  'MOJ',      'MOJADO',      'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de sin caja*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'SINC',     'SIN CAJA',        'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de promoción*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'PROMO',    'PROMOCIÓN',       'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de cortesía*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'CORTE',    'CORTESÍA',        'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de oferta*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'OFER',    'OFERTA',          'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de no funciona*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'NOFUN',    'NO FUNCIONA',      'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de salida genérica*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'SALG',     'SALIDA GENERICA', 'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de entrada genérica*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,         estac,      sucu,     nocaj ) "
                                        + "VALUES(  'ENTG',     'ENTRADA GENERICA',    'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de merma*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,             estac,      sucu,     nocaj ) "
                                        + "VALUES(  'MERMA',    'MERMA A GRANEL',          'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de prorateo*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'PROR',    'PRORATEO',        'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);

                /*Inserta el concepto de donaciones*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,     estac,      sucu,     nocaj ) "
                                        + "VALUES(  'DONA',     'DONACIONES',       'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Inserta el concepto de traspaso*/
                sQ = "INSERT INTO " + sTabl + "(    concep,     descrip,    estac,      sucu,     nocaj ) "
                                        + "VALUES(  'TRAS',     'TRASPASO',       'INICIAL',  'INICIAL','INICIAL')";
                st = con.createStatement();
                st.executeUpdate(sQ);                
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

            /*Inserta los logs*/
            try 
            {                
                /*Ajuste por inventario*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,                    accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'AI',       'AJUSTE POR INVENTARIO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Merma*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,    accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'MER',     'MERMA',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Robo*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,   accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'ROB',      'ROBO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Mal estado*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'MAL',      'MAL ESTADO','AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Compra*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'COM',      'COMPRA',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Caduco*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'CAD',      'CADUCO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Roto*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,   accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'ROT',      'ROTO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Defectuoso*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'DEF',      'DEFECTUOSO','AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Mojado*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,     accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'MOJ',      'MOJADO',    'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Sin caja*/
                sQ = "INSERT INTO logconcep(    cod,        descrip,        accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'SINC',     'SIN CAJA',     'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Promoción*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'PROMO',        'PROMOCION',        'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Cortesia*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'CORTE',        'CORTESÍA',        'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Oferta*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'ORFER',        'OFERTA',          'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*No funciona*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,            accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'NOFUN',        'NO FUNCIONA',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Salida genérica*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,             accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'SALG',         'SALIDA GENÉRICA',   'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Entrada genérica*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,                 accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'ENTG',         'ENTRADA GENÉRICA',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Merma*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,              accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'MERMA',        'MERMA AGRANEL',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Prorateo*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,         accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'PROR',         'PRORATEO',      'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Donaciones*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,             accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'DONA',         'DONACIONES',        'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Traspaso*/
                sQ = "INSERT INTO logconcep(    cod,            descrip,           accio,         estac,    sucu,      nocaj,    falt) "
                                    + "VALUES(  'TRAS',         'TRASPASO',        'AGREGAR',     'INICIAL','INICIAL','INICIAL', now())";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCreConcep()*/


    /*Crea la tabla de peticiones de envio de archivos si no existe*/
    private void vCrePetis(String sBD, Connection con, String sTabl) 
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ = "";

        
        
        
        /*Comprueba si la tabla de conceptos existe*/
        boolean bSi = false;
        try 
        {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + sBD + "' AND table_name = '" + sTabl + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next()) 
                bSi = true;            
        } 
        catch(SQLException e) 
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Borra la base de datos local y servidora*/
            BDCon.vDelBD(Star.sBD);
            BDCon.vDelBDLoc(Star.sBD + "_tmp");

            //Mensajea
            JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Cierra la base de datos y sal de la aplicación
            Star.iCierrBas(con);            
            System.exit(1);
        }

        /*Si la tabla no existe*/
        if(!bSi) 
        {
            try 
            {
                sQ = "CREATE TABLE `" + sTabl + "` (\n"
                        + "`id_id`                   INT(11) NOT NULL AUTO_INCREMENT,\n"
                        + "`nomarch`                 VARCHAR(1000) NOT NULL,\n"
                        + "`path`                    VARCHAR(1000) NOT NULL,\n"
                        + "`estac`                   VARCHAR(30) NOT NULL,\n"
                        + "`estacdestin`             VARCHAR(30) NOT NULL,\n"
                        + "`val`                     BIT NOT NULL,\n"
                        + "`estad`                   BIT NOT NULL,\n"
                        + "`sucu`                    VARCHAR(30) NOT NULL,\n"
                        + "`nocaj`                   VARCHAR(30) NOT NULL,\n"
                        + "`export`                  BIT DEFAULT 0,\n"
                        + "`extr1`                   VARCHAR(255) NULL,\n"
                        + "`extr2`                   VARCHAR(255) NULL,\n"
                        + "`extr3`                   VARCHAR(255) NULL,\n"
                        + "`falt`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "`fmod`                    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n"
                        + "PRIMARY KEY (`id_id`), UNIQUE KEY `id_id_UNIQUE` (`id_id`), KEY `estac` (`estac`)"
                        + ")";
                st = con.createStatement();
                st.executeUpdate(sQ);
            } 
            catch(SQLException ex) 
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Borra la base de datos local y servidora*/
                BDCon.vDelBD(Star.sBD);
                BDCon.vDelBDLoc(Star.sBD + "_tmp");

                //Mensajea
                JOptionPane.showMessageDialog(null, "No se creará toda la estructura de las tablas ni esquemas en la base de datos debido al siguiente error:\n" + "private static void vCreMons() Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Cierra la base de datos y sal de la aplicación
                Star.iCierrBas(con);            
                System.exit(1);
            }

        }/*Fin de if(!bSi)*/

    }/*Fin de private void vCrePetis()*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTInf;
    // End of variables declaration//GEN-END:variables
}
