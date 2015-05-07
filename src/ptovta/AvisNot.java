//Paquete
package ptovta;

//Importaciones
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.text.html.HTMLDocument;
import static ptovta.Princip.bIdle;




/*Clase para ver los avisos y noticias del coorporativo de easy retail*/
public class AvisNot extends javax.swing.JFrame 
{
    /*Constructor sin argumentos*/
    public AvisNot() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*La ventana se mostrará maximizada*/
        setExtendedState(getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);                        
        
        /*El contenido del editor pane será HTML*/
        jED1.setContentType("text/html");                
        
        /*Cambia el tamaño de la letra del editor pane*/
        Font font = new Font("Segoe UI", Font.PLAIN, 15);        
        String bodyRule = "body { font-family: " + font.getFamily() + "; font-size: " + font.getSize() + "pt; }";
        ((HTMLDocument)jED1.getDocument()).getStyleSheet().addRule(bodyRule);
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Avisis y noticias, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Carga todas las noticias y mensajes en el control*/
        vCarg();
        
    }/*Fin de public AvisNot() */
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jED1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
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
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jED1.setEditable(false);
        jED1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jED1.setForeground(new java.awt.Color(0, 102, 153));
        jED1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jED1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jED1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga todos los avisos y noticias en el control*/
    private void vCarg()
    {
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
            Star.iErrProc(BDCon.class.getName() + " " + expnUnknowHos.getMessage(), Star.sErrCompa, expnUnknowHos.getStackTrace());                                
            return;
        }
        catch(SocketException expnSock) 
        {
            //Procesa el error y regresa
            Star.iErrProc(BDCon.class.getName() + " " + expnSock.getMessage(), Star.sErrSock, expnSock.getStackTrace());                                
            return;
        }
        
        /*Obtiene del web service los avisos y noticias ya desencriptados*/
        String sResp;
        try
        {
            sResp    = Star.sDecryp(avis(Star.sEncrip(sMAC)));
        }
        catch(Exception expnExcep)
        {                
            //Procesa el error y regresa
            Star.iErrProc(AvisNot.class.getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());            
            return;
        }                                                    

        /*Comienza el documento html*/
        String sHTML    = "<html>";
        
        /*Conviertelo en arreglo de caracteres*/
        char caSt[] = sResp.toCharArray();

        /*Contiene la nueva cadena del mensaje*/
        sResp  = "";

        /*Recorre todo el array para colcar en cierta posición un carácter html de nueva línea*/
        int iChar   = 0;
        for(char c: caSt)
        {                 
            /*Contiene el carácter a concatenar en el mensaje*/
            String sHa    = Character.toString(c);

            /*Si el contador esta en 10 entonces*/
            if(iChar==100)
            {
                /*Inserta nueva línea*/                        
                sHa = "<br>";

                /*Inicializa nuevamente el contador de carácteres*/
                iChar   = 0;
            }
            /*Aumenta el contador de carácteres*/
            else                                            
                ++iChar;

            /*Completa la cadena del mensjaea*/
            sResp    += sHa;
        }                                

        /*Completa el html*/
        sHTML   += "<img src=\"" + getClass().getClassLoader().getResource("imgs/logo.png").toString() + "\"/> <font size='80' color='blue'><strong>AVISOS Y MENSAJES EASY RETAIL</strong></font><br><br>" + sResp + "</html>";                
        
        /*Agrega en el control de los mensajes el mensaje concatenado con otros datos*/
        jED1.setText(sHTML);                                                                
                
    }/*Fin de private void vCarg()*/
        
        
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
   
                    
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
    
    
    /*Cuando el estado de la ventana cambia*/
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        
        /*Que este máximizado siempre*/
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
    }//GEN-LAST:event_formWindowStateChanged

    
    /*Cuando se presiona una tecla en el editor pane 1*/
    private void jED1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jED1KeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jED1KeyPressed

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            /*Llama al recolector de basura y sal de la forma*/
            System.gc();        
            dispose();        
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jED1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
    /*Método para pedirle al web service que nos de las noticias y avisos*/
    private static String avis(java.lang.String mac) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.avis(mac);
    }   
    
}/*Fin de public class Clientes extends javax.swing.JFrame */
