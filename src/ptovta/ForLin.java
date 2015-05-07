//Paquete
package ptovta;

//Importaciones
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Forma para usar el foro en línea*/
public class ForLin extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color      colOri;
    
    
    
    
    /*Constructor sin argumentos*/
    public ForLin() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Coloca el foco del teclado en el control del mensaje*/
        jTAMsj.grabFocus();
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBEnvi.getBackground();
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*El contenido del editor pane será HTML*/
        jED1.setContentType("text");                
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Foro en línea, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Carga todos los mensajes que tiene el usuario con su MAC*/
        vCarg();
        
    }/*Fin de public AvisNot() */
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jED1 = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAMsj = new javax.swing.JTextArea();
        jBEnvi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jED1.setEditable(false);
        jED1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jED1.setForeground(new java.awt.Color(0, 102, 153));
        jED1.setNextFocusableComponent(jTAMsj);
        jED1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jED1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jED1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 403));

        jTAMsj.setColumns(20);
        jTAMsj.setRows(5);
        jTAMsj.setNextFocusableComponent(jBEnvi);
        jTAMsj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAMsjFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAMsjFocusLost(evt);
            }
        });
        jTAMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAMsjKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTAMsj);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 406, 950, 90));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setText("Enviar");
        jBEnvi.setNextFocusableComponent(jED1);
        jBEnvi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEnviMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEnviMouseExited(evt);
            }
        });
        jBEnvi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEnviActionPerformed(evt);
            }
        });
        jBEnvi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEnviKeyPressed(evt);
            }
        });
        getContentPane().add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 496, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga los mensajes que tiene el usuario con su MAC*/
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
            Star.iErrProc(this.getClass().getName() + " " + expnUnknowHos.getMessage(), Star.sErrUnknowHos, expnUnknowHos.getStackTrace());            
            return;                                                    
        }
        catch(SocketException expnSock)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSock.getMessage(), Star.sErrSock, expnSock.getStackTrace());            
            return;                                                    
        }
        
        /*Obtiene del web service los mensajes de esta MAC ya desencriptados*/
        String sResp;
        try
        {
            sResp    = Star.sDecryp(msjmac(Star.sEncrip(sMAC)));
        }
        catch(Exception expnExcep)
        {                
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());                                                       
            return;                                                                            
        }                                                    

        /*Agrega en el control la respuesta del servidor*/
        jED1.setText(sResp);                                                                
                
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

    
    /*Cuando se gana el foco del teclado en el campo del mensaje*/
    private void jTAMsjFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAMsjFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAMsj.setSelectionStart(0);jTAMsj.setSelectionEnd(jTAMsj.getText().length());        
        
    }//GEN-LAST:event_jTAMsjFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del mensaje*/
    private void jTAMsjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAMsjFocusLost
        
        /*Coloca el caret al principio del control*/
        jTAMsj.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAMsj.getText().compareTo("")!=0)
            jTAMsj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAMsjFocusLost

    
    /*Cuando se presiona una tecla en el campo del mensaje*/
    private void jTAMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAMsjKeyPressed

        /*Si la tecla presiona fue enter entonces presiona el botón de enviar*/        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
            jBEnvi.doClick();
        /*Else llama a la función escalable*/
        else                            
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAMsjKeyPressed

    
    /*Cuando se presiona una tecla en el botón de enviar*/
    private void jBEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEnviKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEnviKeyPressed

    
    /*Cuando el mouse entra en el botón de enviar*/
    private void jBEnviMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEnvi.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBEnviMouseEntered

    
    /*Cuando el mouse sale del botón de enviar*/
    private void jBEnviMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBEnvi.setBackground(colOri);                
        
    }//GEN-LAST:event_jBEnviMouseExited

    
    /*Cuando se presiona el botón de enviar*/
    private void jBEnviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviActionPerformed
        
        /*Si no a escrito nada en el mensaje entonces regresa*/
        if(jTAMsj.getText().compareTo("")==0)
            return;
        
        /*Obtiene la fecha del sistema*/
        DateFormat datFor = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        /*Agrega en el control el texto que el usuario ingreso*/
        jED1.setText(jED1.getText() + "Fecha: " + datFor.format(date) + " Usuario: Local" + System.getProperty( "line.separator" ) + jTAMsj.getText().trim() + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" ));

        /*Obtiene el mensaje antes de borrarlo*/
        String sMsj = jTAMsj.getText().replace("'", "''").trim();
        
        /*Limpia el control del mensaje*/
        jTAMsj.setText("");
        
        /*Coloca el foco del teclado en el control del mensaje otra vez*/
        jTAMsj.grabFocus();
        
        /*Si el archivo de validación del sistema no existe entonces*/
        if(!new File(Star.sArchVal).exists())                       
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema no cuenta con un serial. Podría tener problemas para validarlo o en un futuro.", "Serial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;        
        }
        
        //Desserializamos el archivo de validación del sistema
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
        
        /*Obtiene la serie y la llave desencriptados*/
        String sSer = Star.sDecryp(serVal.sSer);
        String sKey = Star.sDecryp(serVal.sKey);
        
        /*Obtiene la respuesta del web service ya desencriptada*/
        String sResp;
        try
        {
            sResp    = Star.sDecryp(regmsj(Star.sEncrip(sMAC), Star.sEncrip(sMsj), Star.sEncrip(sSer), Star.sEncrip(sKey)));
        }
        catch(Exception expnExcep)
        {                
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());                                                       
            return;                                                    
        }                                                    
        
        /*Si la respuesta fue negativa entonces*/
        if(sResp.contains("<ERROR>"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Error del servidor." + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si la respuesta no fue 1 entonces mensajea*/
        if(sResp.compareTo("1")!=0)        
            JOptionPane.showMessageDialog(null, "Error del servidor." + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                   
        
    }//GEN-LAST:event_jBEnviActionPerformed

    
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
    private javax.swing.JButton jBEnvi;
    private javax.swing.JEditorPane jED1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAMsj;
    // End of variables declaration//GEN-END:variables

    
    /*Método para obtener los mensajes de esta MAC del servidor*/
    private static String msjmac(java.lang.String mac) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.msjmac(mac);
    }       

    
    /*Método para registrar el mensaje del cliente en la base de datos*/
    private static String regmsj(java.lang.String mac, java.lang.String msj, java.lang.String ser, java.lang.String key) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.regmsj(mac, msj, ser, key);
    }
    
    
}/*Fin de public class Clientes extends javax.swing.JFrame */
