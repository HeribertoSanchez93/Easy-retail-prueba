//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;




/*Clase que muestra la presentación del sistema*/
public class Present extends javax.swing.JFrame 
{       
    /*Constructor sin argumentos*/
    public Present() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        /*Inicia el thread para que cierra la forma en 4 segundos*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Duerme el thread 4 segundos*/
                try
                {
                    Thread.sleep(4000);
                }
                catch(InterruptedException expnInterru)
                {                    
                }
                
                /*Llama al recolector de basura*/
                System.gc();
        
                /*Cierra la forma*/
                dispose();
                
                /*Muestra el login*/
                Login l = new Login(false);
                l.setVisible(true);
            }
            
        }).start();
                        
    }/*Fin de public Presen() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jlImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setEnabled(false);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
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
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jP1.setLayout(new java.awt.BorderLayout());

        jlImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ban.png"))); // NOI18N
        jP1.add(jlImg, java.awt.BorderLayout.CENTER);

        getContentPane().add(jP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 510));

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

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces cierra la forma*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();
            dispose();        
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jP1;
    private javax.swing.JLabel jlImg;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
