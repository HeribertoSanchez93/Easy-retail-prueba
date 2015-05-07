//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.LineUnavailableException;




/*Clase para ver la càmara del usuario*/
public class ImgCam extends javax.swing.JFrame 
{                
    /*Thread para estar recibiendo imágenes de la cámara del servidor*/
    private Thread                  thCamRecib   = null;
    
    /*Thread que recibiendo audio del micro del server*/
    private Thread                  thRMic;

    /*Socket para estar reciviendo la cámara del servidor*/
    private java.net.Socket         cli;
    
    /*Socket para estar reciviendo el sonido del servidor*/
    private java.net.DatagramSocket socRAud;        

    
    
    
    
    /*Constructor sin argumentos*/
    public ImgCam(final String sUsr) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String sPort        = "";
        String sHost        = "";        
        String sPortUDP     = "";
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene los parámetros de conexión con los host*/        
        try
        {
            sQ = "SELECT `host` AS `host`, port, portudp FROM logestac WHERE estac = '" + sUsr.trim() + "'";                                                
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                   
                sHost      = rs.getString("host");                                                                   
                sPort      = rs.getString("port");                                                                   
                sPortUDP   = rs.getString("portudp");     
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

        /*Declara variables final para el thread*/
        final String sHostFi    = sHost;        
        final String sPortFi    = sPort;
        final String sPortUDPFi = sPortUDP;
                   
        /*Establece el tamaño de la ventana*/
        this.setSize(500,400);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Video llamada, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Declara thread para estar recibiendo por el socket la imágen de la cámara*/
        thCamRecib   = new Thread()
        {
            @Override
            public void run()
            {                           
                /*Conecta con el servidor*/                
                try
                {
                    cli = new java.net.Socket(sHostFi, Integer.parseInt(sPortFi));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }                    
                
                /*Declara objetos para poder mandarle mensaje al servidor y recibirlos*/
                java.io.OutputStream        outS;
                java.io.DataOutputStream    out;
                try
                {
                    outS    = cli.getOutputStream();
                    out     = new java.io.DataOutputStream(outS);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                           
                }
                
                /*Envia la petición al socket server*/
                try
                {
                    out.writeUTF("camrecib|" + Login.sUsrG.trim());
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }
                
                /*Objeto imágen*/
                BufferedImage img;                
                                        
                /*Búcle infinito*/
                while(true)
                {                           
                    /*Si el socket esta cerrado entonces regresa y cierra la forma*/
                    if(cli.isClosed())                                                                    
                    {                        
                        dispose();
                        return;
                    }
                    
                    /*Lee la imégen del servidor*/                    
                    try
                    {
                        /*Obtiene la imágen del servidor y colocala en el control*/
                        java.io.BufferedInputStream buffIn = new java.io.BufferedInputStream(cli.getInputStream());
                        img = javax.imageio.ImageIO.read(javax.imageio.ImageIO.createImageInputStream(buffIn));                                                
                        final BufferedImage imgFi   = img;
                        if(img!=null)
                        {                                                                        
                            /*Objeto ícono*/
                            ImageIcon img1;

                            /*Objetos de imagenes*/
                            java.awt.Image im, newimg;

                            /*Obtiene en un objeto de imageicon*/                                                    
                            img1        = new ImageIcon(imgFi);

                            /*Crea la imágen para redimensionar la imágen del icono*/
                            im          = img1.getImage(); 
                            newimg      = im.getScaledInstance(jP1.getWidth(), jP1.getHeight(),  java.awt.Image.SCALE_SMOOTH);  
                            
                            /*Crea el nuevo ícono y asignalo al label*/                      
                            img1        = new ImageIcon(newimg);                        
                            jLImg.setIcon(img1);                                                                                                                                                                         
                        }
                    }
                    catch(IOException expnIO)
                    {                                
                        /*Cierra la forma y regresa*/                                                
                        dispose();
                        return;
                    }
                                                         
                }/*Fin de while(true)*/                                                                                   
        
            }/*Fin de public void run()*/
        };                
        thCamRecib.start();        

        /*Thread para recibir audio del micro del host y reproducirlo por las bocinas*/
        thRMic   = new Thread()
        {                
            @Override
            public void run()
            {    
                /*Inicia el objeto de conexión UDP*/                
                try
                {
                    socRAud = new java.net.DatagramSocket();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }                    

                /*Crea el host para mandar el paquete*/                
                java.net.InetAddress iaAdd;
                try
                {
                    iaAdd = java.net.InetAddress.getByName(sHostFi);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }                    
                
                /*Crea el mensaje a enviar*/
                byte[] bDat = "audrecib".getBytes();                
                
                /*Crea el objeto para enviar el mensaje*/
                java.net.DatagramPacket dgPacE = new java.net.DatagramPacket(bDat, bDat.length, iaAdd, Integer.parseInt(sPortUDPFi));
                
                /*Envia el paquete por el socket*/
                try
                {
                    socRAud.send(dgPacE);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }
                                
                /*Objeto para recibir paquetes del servidor*/
                byte bReci[] = new byte[1600];                
                java.net.DatagramPacket dgPacR = new java.net.DatagramPacket(bReci, bReci.length);        
                
                /*Búcle inifinito para estar reciviendo audio del servidor*/
                while(true)
                {       
                    /*Si el socket esta cerrado entonces regresa y cierra la forma*/
                    if(socRAud.isClosed())
                    {
                        dispose();
                        return;                    
                    }
                    
                    /*Obtiene el paquete del socket*/                    
                    try
                    {
                        socRAud.receive(dgPacR);                        
                    }
                    catch(IOException expnIO)
                    {
                        /*Regresa y cierra la forma*/
                        dispose();
                        return;
                    }
                    
                    /*Crea el audioinputstream*/
                    javax.sound.sampled.AudioInputStream aud = new javax.sound.sampled.AudioInputStream(new java.io.ByteArrayInputStream(bReci), Star.format, bReci.length);
                    try
                    {
                        javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                        clip.open(aud);
                        clip.start();        
                        while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
                        {
                        }
                    }
                    catch(LineUnavailableException expnLinUnav)
                    {
                        //Procesa el error
                        Star.iErrProc(this.getClass().getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav, expnLinUnav.getStackTrace());                        
                    }   
                   catch(IOException expnIO)
                   {
                       //Procesa el error
                       Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                               
                   }
                                                        
                }/*Fin de while(true)*/                                    
            }
        };
        thRMic.start();
        
    }/*Fin de public ImgRemo() */

                        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrin = new javax.swing.JPanel();
        jP1 = new javax.swing.JPanel();
        jLImg2 = new javax.swing.JLabel();
        jLImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        jPPrin.setBackground(new java.awt.Color(204, 204, 255));
        jPPrin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPPrinKeyPressed(evt);
            }
        });
        jPPrin.setLayout(new java.awt.BorderLayout());

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new java.awt.BorderLayout());

        jLImg2.setBackground(new java.awt.Color(255, 255, 255));
        jLImg2.setFocusable(false);
        jP1.add(jLImg2, java.awt.BorderLayout.EAST);

        jLImg.setBackground(new java.awt.Color(255, 255, 255));
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jP1.add(jLImg, java.awt.BorderLayout.WEST);

        jPPrin.add(jP1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPPrin, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
        
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
    
    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Llama a la función para cerrar la ventana*/        
        vCier();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el scroll de la imágen*/
    private void jLImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLImgKeyPressed
        
        /*Llama a la función escalable*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLImgKeyPressed

    
    /*Cuando se presiona una tecla en el panel 1*/
    private void jPPrinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPPrinKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPPrinKeyPressed

    
    /*Cuando se presiona una tecla en el panel 1*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed
                    
              
    /*Función para cerrar la ventana y liberar recursos*/        
    private void vCier()
    {   
        /*Bandera para que el socket deje de mandar cámara*/
        Star.bBanVid    (true, true);
        
        /*Bandera para que el socket deje de mandar sonido*/
        Star.bBanVidAud (true, true);
        
        /*Cierra el socket de estar recibiendo la cámara del servidor*/
        if(cli!=null)
        {            
            try
            {
                cli.close();
            }
            catch(IOException expnIO)
            {                 
            }                        
        }
        
        /*Cierra el socket del audio del servidor*/
        if(socRAud!=null)
        {            
            socRAud.close();                        
        }
                                                        
        /*Cierra la ventana*/
        dispose();
                
        /*Llama al recolector de basura*/
        System.gc();
        
    }/*Fin de private void vCier()*/        
        
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces llama a la función para cerrar la ventana*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)             
            vCier();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLImg;
    public javax.swing.JLabel jLImg2;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPPrin;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class ImpRemo extends javax.swing.JFrame */
