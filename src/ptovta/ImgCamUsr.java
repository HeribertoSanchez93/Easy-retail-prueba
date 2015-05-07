//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;




/*Clase para ver la càmara del usuario*/
public class ImgCamUsr extends javax.swing.JFrame 
{                
    /*Thread para estar recibiendo imágenes de la cámara del servidor*/
    private Thread              thCamRecib   = null;
    
    /*Socket para controlar la recepción del video de la cámara*/
    private java.net.Socket     cli;
    
    
    
    /*Constructor sin argumentos*/
    public ImgCamUsr(final String sPort, final String sHost) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Declara variables final para el thread*/
        final String sHostFi    = sHost;        
        final String sPortFi    = sPort;
                   
        /*Establece el tamaño de la ventana*/
        this.setSize(500,400);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Video cámara, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
                    out.writeUTF("camrecibsol");
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
                        /*Regresa*/
                        return;
                    }
                                                         
                }/*Fin de while(true)*/                                    

            }/*Fin de public void run()*/
        };                
        thCamRecib.start();        
                        
    }/*Fin de public ImgCamUsr() */

                        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
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

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new java.awt.BorderLayout());

        jLImg.setBackground(new java.awt.Color(255, 255, 255));
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jP1.add(jLImg, java.awt.BorderLayout.WEST);

        getContentPane().add(jP1, java.awt.BorderLayout.CENTER);

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
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

                                   
    /*Función para cerrar la ventana y liberar recursos*/        
    private void vCier()
    {        
        /*Cierra el socket*/
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
        
        /*Termina el thread*/
        if(thCamRecib!=null)
            thCamRecib.interrupt();                                
        
        /*Cierra la ventana*/
        dispose();
        
        /*Llama al recolector de basura*/
        System.gc();        
    }        
        
    
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
    private javax.swing.JPanel jP1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class ImpRemo extends javax.swing.JFrame */
