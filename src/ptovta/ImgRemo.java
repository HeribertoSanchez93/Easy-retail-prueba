//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.ImageIcon;




/*Clase para ver el remoto de un usuario*/
public class ImgRemo extends javax.swing.JFrame 
{
    /*Variable para que la forma no se abra dos veces*/
    private static ImgRemo      obj = null;          

    /*Thread para estar haciendo peticiones al servidor de que refresque su ventana*/
    private final Thread        thCli;
    
    /*Socket para conectar con el servidor*/    
    private java.net.Socket     cli;
    
    
    /*Constructor sin argumentos*/
    public ImgRemo(String sHost, String sPort, String sUsr) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el tamaño de la ventana*/
        this.setSize(800,600);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Remoto a usuario: <" + sUsr + ">");        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                
        /*Declara variables final para el thread*/
        final String sHostFi    = sHost;
        final String sPortFi    = sPort;
        
        /*Thread para procesar el cliente y este haciendo peticiones al servidor de que actualiza su ventana*/
        thCli   = new Thread()
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
                
                /*Declara objetos para poder mandarle mensaje al servidor*/
                java.io.OutputStream        outS;
                java.io.DataOutputStream    out;
                try
                {
                    outS   = cli.getOutputStream();
                    out= new java.io.DataOutputStream(outS);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                        
                }
                
                /*Manda mensaje al servidor*/
                try
                {
                    out.writeUTF("panta");
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                            
                }
                
                /*Objeto para contener el buffer de la imágen*/
                java.awt.image.BufferedImage img;
                
                /*Búcle infinito*/
                while(true)
                {                                         
                    /*Lee la imégen del servidor*/                    
                    try
                    {
                        /*Crea el flujo de bytes*/
                        java.io.BufferedInputStream buff = new java.io.BufferedInputStream(cli.getInputStream());
                        
                        /*Obtiene la imágen del flujo de bytes*/
                        javax.imageio.stream.ImageInputStream imgS  = javax.imageio.ImageIO.createImageInputStream(buff);
                        
                        /*Si no hay imágen que continue*/
                        if(imgS==null)
                            continue;                                                                                                                                                    
                        
                        /*Crea  el icono en el botón con un tamaño personalizado*/                        
                        img = javax.imageio.ImageIO.read(imgS);
                        
                        /*Si la imágen es nula entonces continua*/
                        if(img==null)
                            continue;
                        
                        ImageIcon img1       = new ImageIcon(img);

                        /*Crea la imágen para redimensionar la imágen del icono*/
                        java.awt.Image im = img1.getImage(); 
                        java.awt.Image newimg = im.getScaledInstance( jSImg.getWidth(), jSImg.getHeight(),  java.awt.Image.SCALE_SMOOTH );  

                        /*Crea el nuevo ícono y asignalo al label*/
                        img1                 = new ImageIcon(newimg);                        
                        jLImg.setIcon(img1);                                                                                                         
                    }
                    catch(IOException expnIO)
                    {                        
                        /*Regresa*/
                        return;
                    }
                    
                }/*Fin de while(true)*/                    
                
            }/*Fin de public void run()*/
        };
        thCli.start();                        
        
    }/*Fin de public ImgRemo(String sHost, String sPort, String sUsr) */

    
    /*Metodo para que el formulario no se abra dos veces*/
    public static ImgRemo getObj(String sHost, String sPort, String sUsr)
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new ImgRemo(sHost, sPort, sUsr);
        
        /*Devuelve el resultado*/
        return obj;        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jSImg = new javax.swing.JScrollPane();
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

        jSImg.setBackground(new java.awt.Color(255, 255, 255));

        jLImg.setBackground(new java.awt.Color(255, 255, 255));
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jSImg.setViewportView(jLImg);

        jP1.add(jSImg, java.awt.BorderLayout.CENTER);

        getContentPane().add(jP1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

          
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed
              
    
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
    
    
    /*Función para cerrar la ventana y liberar recursos*/        
    private void vCier()
    {                
        /*Termina el thread del cliente*/
        if(thCli!=null)
            thCli.interrupt();
        
        /*Cierra el socket*/
        if(cli!=null)
        {
            try
            {
                cli.close();
            }
            catch(Exception expnIO)
            {                
            }
        }
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la ventana*/
        dispose();
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
    private javax.swing.JLabel jLImg;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jSImg;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class ImpRemo extends javax.swing.JFrame */
