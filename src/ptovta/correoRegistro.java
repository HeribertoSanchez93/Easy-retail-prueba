package ptovta;

/* obligatorias para el envio de correo electrónico */

/* detectan conexion internet */
import java.awt.HeadlessException;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

/* conexion a base datos */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* envio del correo */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/*02 Junio 2015 CLase Creada por Felipe Ruiz Garcia */
/* esta clase es el envio de correo*/
public class correoRegistro {

    private String correoA;

    private String correoDesde;
    private String contraseñaDesde;
    private String MuestraDesde;

    private String puerto;
    private String direccionServidor;

    private String asunto;

    private String mensaje;
    // ejemplo 
    // puede incluir html 
    /*
     " variablecorreo = <h1>This is a test</h1>"
     + "<img src=\"http://www.rgagnon.com/images/jht.gif\">";
     */

    correoRegistro(int x) {

    }

    correoRegistro() {

        /*valores default de la cuenta Que Enviara la contraseña */
        correoDesde = "info@easyretail.com.mx";
        contraseñaDesde = "{41xZm~TZ;p{";
        MuestraDesde = "Easy Retail® - Sencillo y a tiempo";
        puerto = "465";
        direccionServidor = "gator3201.hostgator.com";
        asunto = "Contraseña de activación de Easy Retail - Sencillo y a tiempo";

        /*
         correoA = "felipe_57@live.com.mx";
         mensaje = "Fee";
         */
    }

    public String getCorreoA() {
        return correoA;
    }

    public void setCorreoA(String correoA) {
        this.correoA = correoA;
    }

    public String getCorreoDesde() {
        return correoDesde;
    }

    public void setCorreoDesde(String correoDesde) {
        this.correoDesde = correoDesde;
    }

    public String getContraseñaDesde() {
        return contraseñaDesde;
    }

    public void setContraseñaDesde(String contraseñaDesde) {
        this.contraseñaDesde = contraseñaDesde;
    }

    public String getMuestraDesde() {
        return MuestraDesde;
    }

    public void setMuestraDesde(String MuestraDesde) {
        this.MuestraDesde = MuestraDesde;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getDireccionServidor() {
        return direccionServidor;
    }

    public void setDireccionServidor(String direccionServidor) {
        this.direccionServidor = direccionServidor;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mesage) {
        this.mensaje = mesage;
    }

    /* Verifica que los campos Obligatorios no esten vacios */
    /* Si se encuentra algun campo obligatorio con un campo no valido retorna falso */
    /* De lo contrario / si todo esta en orden / retorna true */
    public boolean verifica() {

        if (correoDesde == null) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta correo \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }
        
        if (correoDesde.equals("")) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta correo \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }
        


        if (contraseñaDesde == null) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }
        if (contraseñaDesde.equals("")) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }

//        if (MuestraDesde.equals("")){
//            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta nombre a mostrar \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//
//            return false;
//        } 
        if (direccionServidor == null) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta el servidor de correo \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }
        if (direccionServidor.equals("")) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta el servidor de correo \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }

        
        if (puerto == null) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta puerto \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }
        if (puerto.equals("")) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta puerto \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }

        if (correoA == null) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta remitente \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }

        if (correoA.equals("")) {
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el correo : Falta remitente \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return false;
        }

        return true;
    }

    public static boolean hayInternet() {
        try {
            URL u = new URL("https://www.google.es/");
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.connect();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /* Primero debe establecer todos los parametros */
    /* Si el mail no se envie no puede ser enviado retorna false */
    /* De lo contrario / si el correo es enviado con exito / retorna true */
    boolean enviaCorreo() {

        if (!verifica()) {
            return false;
        }

        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", direccionServidor); //"gator3201.hostgator.com"
            props.setProperty("mail.smtp.starttls.enable", "true");
            //props.put("mail.smtp.EnableSSL.enable", "true");
            props.put("mail.smtp.socketFactory.port", puerto);
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.debug", "true");
            props.setProperty("mail.smtp.port", puerto); // "25"
            props.setProperty("mail.smtp.user", correoDesde); // "info@easyretail.com.mx"
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(correoDesde, MuestraDesde)); /// como se muestra el remitente //"\"Easy Retail® - Sencillo y a tiempo\"<info@easyretail.com.mx>"

            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correoA)); // PARA 
            //message.setSubject(asunto, "UTF-8"); /// "Contraseña de activacion de Easy Retail - Sencillo y a tiempo"
            message.setSubject(asunto);
            /*HTML*/
            message.setContent(mensaje, "text/html; charset=utf-8");

            // Lo enviamos. // "{41xZm~TZ;p{"
            Transport t = session.getTransport("smtp");
            t.connect(direccionServidor, correoDesde, contraseñaDesde); /// AQUI SE DETECTA SI HAY INTERNET O NO! // SE LANZA LA EXCEPCION

            //System.out.println("SI SE ENVIO ®"); // PARA DEBUG
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("No hay internet"); // PARA DEBUG
            return false;
        }

        return true;
    }

    // ESTA FUNCION RECIBE LA RUTA DE UN ARCHIVO EL CUAL SERA ENVIADO DE MANERA ADJUNTA EN EL EMAIL
    /* Primero debe establecer todos los parametros */
    /* Si el mail no se envie no puede ser enviado retorna false */
    /* De lo contrario / si el correo es enviado con exito / retorna true */
    boolean enviaCorreo(String ruta) {

        if (!verifica()) {
            return false;
        }

        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", direccionServidor); //"gator3201.hostgator.com"
            props.setProperty("mail.smtp.starttls.enable", "true");
            //props.put("mail.smtp.EnableSSL.enable", "true");
            props.put("mail.smtp.socketFactory.port", puerto);
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.debug", "true");
            props.setProperty("mail.smtp.port", puerto); // "25"
            props.setProperty("mail.smtp.user", correoDesde); // "info@easyretail.com.mx"
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getInstance(props);

            /// attached the document
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);
            texto.setHeader("Content-Type", "text/html");;

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(
                    new DataHandler(new FileDataSource(ruta)));
//                adjunto.setFileName(adjunto.getFileName());
            adjunto.setFileName(new File(ruta).getName());

            MimeMultipart multiparte = new MimeMultipart();
            multiparte.addBodyPart(texto);
            multiparte.addBodyPart(adjunto);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(correoDesde, MuestraDesde)); /// como se muestra el remitente //"\"Easy Retail® - Sencillo y a tiempo\"<info@easyretail.com.mx>"

            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correoA)); // PARA 
            //message.setSubject(asunto, "UTF-8"); /// "Contraseña de activacion de Easy Retail - Sencillo y a tiempo"
            message.setSubject(asunto);
            /*HTML*/
            //message.setContent(mensaje, "text/html; charset=utf-8");
            message.setContent(multiparte);

            // Lo enviamos. // "{41xZm~TZ;p{"
            Transport t = session.getTransport("smtp");
            t.connect(direccionServidor, correoDesde, contraseñaDesde); /// AQUI SE DETECTA SI HAY INTERNET O NO! // SE LANZA LA EXCEPCION

            //System.out.println("SI SE ENVIO ®"); // PARA DEBUG
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("No hay internet"); // PARA DEBUG
            return false;
        }

        return true;
    }

    public String creaMensajeCliente(String contraseña) {

        String mensajeHtml = "<!DOCTYPE html>\n"
                + "	<html>\n"
                + "	<head>\n"
                + "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "		<title>Gracias :)</title>\n"
                + "\n"
                + "<style type=\"text/css\">\n"
                + "	body {\n"
                + "		background-color: #33ccff;\n"
                + "		color: white;\n"
                + "		padding: 20px;\n"
                + "		font-family: Arial, Verdana, sans-serif;\n"
                + "	}\n"
                + "\n"
                + "	.Main{\n"
                + "		background-color: #ffffff;\n"
                + "		color: black;\n"
                + "		padding: 20px;\n"
                + "	}\n"
                + "	.Main.Datos{\n"
                + "		font-size: 20px;\n"
                + "		color: black;\n"
                + "\n"
                + "	}\n"
                + "\n"
                + "	.Main.Datos.Neg{\n"
                + "\n"
                + "		font-weight: bold;\n"
                + "		font-size: 23px;\n"
                + "		color: black;\n"
                + "	}\n"
                + "\n"
                + "\n"
                + "	h1 {\n"
                + "		background-color: #ffffff;\n"
                + "		background-color: hsla(0,100%,100%,0.5);\n"
                + "		color: #64645A;\n"
                + "		padding: inherit;}\n"
                + "\n"
                + "		p {\n"
                + "			padding: 0px;\n"
                + "			margin: 0px;}\n"
                + "\n"
                + "			.eight {\n"
                + "				background-color: rgb(140,202,242);}\n"
                + "				.nine {\n"
                + "					background-color: rgb(114,193,240);}\n"
                + "					.ten {\n"
                + "						background-color: rgb(84,182,237);}\n"
                + "						.eleven {\n"
                + "							background-color: rgb(48,170,233);}\n"
                + "							.twelve {\n"
                + "								background-color: rgb(0,160,230);}\n"
                + "								.thirteen {\n"
                + "									background-color: rgb(0,149,226);}\n"
                + "									.fourteen {\n"
                + "										background-color: rgb(0,136,221);}\n"
                + "\n"
                + "\n"
                + "									</style>\n"
                + "								</head>\n"
                + "								<body style=\"background-color: #33ccff;color: white;padding: 20px;font-family: Arial, Verdana, sans-serif;\">\n"
                + "									<div class=\"Main\" style=\"background-color: #ffffff;color: black;padding: 20px;\">\n"
                + "										<div class=\"header\">\n"
                + "											<tr>\n"
                + "												<th>\n"
                + "													<img src=\"http://easyretail.com.mx/wp-content/uploads/2015/06/IdentificadorEeasy-Retail-admin-marca-registrada.png\" id=\"image_bussines\">\n"
                + "												</th>\n"
                + "												<th>\n"
                + "													<h1 id=\"title_bussines\">\n"
                + "														Easy Retail® Admin\n"
                + "														</h1>\n"
                + "														Software administrativo sencillo y a tiempo. \n"
                + "													</h1>\n"
                + "												</th>\n"
                + "											</tr>\n"
                + "										</div>\n"
                + "									</div>\n"
                + "									<br>\n"
                + "\n"
                + "									<div class=\"Main Datos\" style=\"background-color: #ffffff;color: black;padding: 20px;font-size: 20px;\">\n"
                + "										<h4><b> Hola :) </b></h4>\n"
                + "										<h4></h4>\n" + "<br><p style=\"padding: 0px;margin: 0px;\">Su contraseña de activación de sistema Easy Retail® es : <b>" + contraseña + "</b></p>"
                + "\n"
                + "										\n"
                + "										<div class=\"Neg\">\n"
                + "											<p style=\"padding: 0px;margin: 0px;\">Estamos a su servicio en el <b>01-800-890-0365</b> para todo México y en el <b>01-33-3617-2968</b> en Jalisco.\n"
                + "												<br style=\"padding: 0px;margin: 0px;\">\n"
                + "												Atentamente el equipo Easy Retail® - Sencillo y a tiempo.\n"
                + "											</p>	\n"
                + "										</div>\n"
                + "									</div>\n"
                + "\n"
                + "									<br>\n"
                + "								</body>\n"
                + "								</html>";

        return mensajeHtml;
    }

    public String creaMensajeEmpresa(String email, String contra) {

        String mensajeHtml = "<!DOCTYPE html>\n"
                + "	<html>\n"
                + "	<head>\n"
                + "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "		<title>Gracias :)</title>\n"
                + "\n"
                + "<style type=\"text/css\">\n"
                + "	body {\n"
                + "		background-color: #33ccff;\n"
                + "		color: white;\n"
                + "		padding: 20px;\n"
                + "		font-family: Arial, Verdana, sans-serif;\n"
                + "	}\n"
                + "\n"
                + "	.Main{\n"
                + "		background-color: #ffffff;\n"
                + "		color: black;\n"
                + "		padding: 20px;\n"
                + "	}\n"
                + "	.Main.Datos{\n"
                + "		font-size: 20px;\n"
                + "		color: black;\n"
                + "\n"
                + "	}\n"
                + "\n"
                + "	.Main.Datos.Neg{\n"
                + "\n"
                + "		font-weight: bold;\n"
                + "		font-size: 23px;\n"
                + "		color: black;\n"
                + "	}\n"
                + "\n"
                + "\n"
                + "	h1 {\n"
                + "		background-color: #ffffff;\n"
                + "		background-color: hsla(0,100%,100%,0.5);\n"
                + "		color: #64645A;\n"
                + "		padding: inherit;}\n"
                + "\n"
                + "		p {\n"
                + "			padding: 0px;\n"
                + "			margin: 0px;}\n"
                + "\n"
                + "			.eight {\n"
                + "				background-color: rgb(140,202,242);}\n"
                + "				.nine {\n"
                + "					background-color: rgb(114,193,240);}\n"
                + "					.ten {\n"
                + "						background-color: rgb(84,182,237);}\n"
                + "						.eleven {\n"
                + "							background-color: rgb(48,170,233);}\n"
                + "							.twelve {\n"
                + "								background-color: rgb(0,160,230);}\n"
                + "								.thirteen {\n"
                + "									background-color: rgb(0,149,226);}\n"
                + "									.fourteen {\n"
                + "										background-color: rgb(0,136,221);}\n"
                + "\n"
                + "\n"
                + "									</style>\n"
                + "								</head>\n"
                + "								<body style=\"background-color: #33ccff;color: white;padding: 20px;font-family: Arial, Verdana, sans-serif;\">\n"
                + "									<div class=\"Main\" style=\"background-color: #ffffff;color: black;padding: 20px;\">\n"
                + "										<div class=\"header\">\n"
                + "											<tr>\n"
                + "												<th>\n"
                + "													<img src=\"http://easyretail.com.mx/wp-content/uploads/2015/06/IdentificadorEeasy-Retail-admin-marca-registrada.png\" id=\"image_bussines\">\n"
                + "												</th>\n"
                + "												<th>\n"
                + "													<h1 id=\"title_bussines\">\n"
                + "														Easy Retail® Admin\n"
                + "														</h1>\n"
                + "														Software administrativo sencillo y a tiempo. \n"
                + "													</h1>\n"
                + "												</th>\n"
                + "											</tr>\n"
                + "										</div>\n"
                + "									</div>\n"
                + "									<br>\n"
                + "\n"
                + "									<div class=\"Main Datos\" style=\"background-color: #ffffff;color: black;padding: 20px;font-size: 20px;\">\n"
                + "										<h4><b> Hola :) </b></h4>\n"
                + "										<h4></h4>\n" + "<br><p style=\"padding: 0px;margin: 0px;\"> El usuario <b>" + email + "</b> ha recibido su contraseña de activación de sistema Easy Retail® Admin</p>"
                + "\n" + "<br><p style=\"padding: 0px;margin: 0px;\"> La contraseña de activación de sistema Easy Retail® Admin del usuario es <b>" + contra + "</b></p><br>"
                + "										\n"
                + "									</div>\n"
                + "\n"
                + "									<br>\n"
                + "								</body>\n"
                + "								</html>";

        return mensajeHtml;
    }

    /* Revisa si el campo de la base de datos  ya_entro_booleano es 0 */
    /* si es 0 retorna 0, de lo contrario retorna false */
    public boolean es0BaseDatos() throws SQLException {

        /* nombre de la tabla de registro */
        String nametabla = "registroemail";

        /*Abre la base de datos*/
        Connection con = Star.conAbrBas(true, false);

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                Star.iCierrBas(con);
                return false;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            Star.iCierrBas(con);
            return false;
        }

        /*preparo la base de datos */
        try {

            sQ = "select ya_entro_booleano from " + nametabla + " where id_id = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);

            if (rs.next()) {
                int x = rs.getInt("ya_entro_booleano");

                /* cierra conexion base de datos */
                Star.iCierrBas(con);
                return x == 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL al consultar la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexion base de datos */
            Star.iCierrBas(con);
        }
        return false;
    }

    /* esta funcion retorna el email proporcionado por el usuario 
     al cual  se le enviara la contraseña para el ingreso al EasyRetail */
    public String getCorreoBaseDatos() {

        //Abre la base de datos                             
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces 
        if (con == null) {
            return "";
        }

        /* nombre de la tabla de registro */
        String nametabla = "registroemail";

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /* Variable para el retorno del correo */
        String correo = "";

        try {

            sQ = "select correo from " + nametabla + " where id_id = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);

            /*si la consulta nos arroja un resultado*/
            if (rs.next()) {
                /* guardamos el resultado de la consulta*/
                correo = rs.getString("correo");

                /* desencritamos el resultado de la consulta*/
                correo = Star.sDecryp(correo);

                // System.out.println(correo); // PARA DEBUG
                /* cierra conexion base de datos */
                Star.iCierrBas(con);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL al consultar la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /* cierra conexion base de datos */
            Star.iCierrBas(con);
        }

        return correo;
    }

    /* esta funcion retorna el email proporcionado por el usuario 
     al cual  se le enviara la contraseña para el ingreso al EasyRetail */
    public String getContraBaseDatos() {

        //Abre la base de datos                             
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces 
        if (con == null) {
            return "";
        }

        /* nombre de la tabla de registro */
        String nametabla = "registroemail";

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /* Variable para el retorno de la contraseña */
        String contra = "";

        try {

            sQ = "select contra from " + nametabla + " where id_id = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);

            /*si la consulta nos arroja un resultado*/
            if (rs.next()) {
                /* guardamos el resultado de la consulta*/
                contra = rs.getString("contra");

                /* desencritamos el resultado de la consulta*/
                contra = Star.sDecryp(contra);

                // System.out.println(contra); // PARA DEBUG
                /* cierra conexion base de datos */
                Star.iCierrBas(con);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL al consultar la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /* cierra conexion base de datos */
            Star.iCierrBas(con);
        }

        return contra;
    }

    public boolean cambia1BaseDatos() throws SQLException {

        /* nombre de la tabla de registro */
        String nametabla = "registroemail";

        /*Abre la base de datos*/
        Connection con = Star.conAbrBas(true, false);

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                Star.iCierrBas(con);
                return false;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            Star.iCierrBas(con);
            return false;
        }

        /*preparo la base de datos */
        try {

            sQ = "update " + nametabla + " set ya_entro_booleano = 1 where id_id = 1";
            st = con.createStatement();
            int result = st.executeUpdate(sQ);

            /* cierra conexion base de datos */
            Star.iCierrBas(con);
            return result == 1;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL al consultar la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexion base de datos */
            Star.iCierrBas(con);
        }
        return false;
    }

    /* esta funcion cambia el correo de registro en la base de datos */
    /* recibe el correo y este en automatico lo guarda encriptado */
    public boolean cambiaCorreoBaseDatos(String mail) throws SQLException {

        /* nombre de la tabla de registro */
        String nametabla = "registroemail";

        /*Abre la base de datos*/
        Connection con = Star.conAbrBas(true, false);

        /*Declara variables de la base de datos*/
        Statement st;
        ResultSet rs;
        String sQ = "";

        /*Bandera si la base de dato existe*/
        boolean iSi = false;

        /*Comprueba si la tabla existe*/
        try {
            sQ = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "' AND table_name = '" + nametabla + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if (rs.next()) {
                iSi = true;
            } else {

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                Star.iCierrBas(con);
                return false;
            }
        } catch (SQLException e) {
            //Mensajea
            JOptionPane.showMessageDialog(null, "No existe la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            Star.iCierrBas(con);
            return false;
        }

        /*preparo la base de datos */
        try {

            // System.out.println(mail // PARA DEBUG
            sQ = "update " + nametabla + " set correo = \"" + Star.sEncrip(mail) + "\" where id_id = 1";
            st = con.createStatement();
            int result = st.executeUpdate(sQ);

            /* cierra conexion base de datos */
            Star.iCierrBas(con);
            return result == 1;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL al consultar la tabla " + nametabla + " para el envio de la contraseña \n", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            /* cierra conexion base de datos */
            Star.iCierrBas(con);
        }
        return false;
    }

}
