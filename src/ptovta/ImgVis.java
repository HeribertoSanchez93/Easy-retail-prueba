//Paquete
package ptovta;

//Importaciones
import java.awt.Dimension;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;




/*Clase que muestra un visro distinto de las imágenes*/
public class ImgVis extends javax.swing.JFrame 
{                                       
    /*Constructor sin argumentos*/
    public ImgVis(String sRut) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        /*Obtiene el alto y ancho del icono*/
        int iH = new ImageIcon(sRut).getIconHeight();
        int iW = new ImageIcon(sRut).getIconWidth();
        Dimension dmDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        
        /*Si el ancho es mayor al ancho de la pantalla entonces que sea el ancho de la pantalla*/
        if(iW>dmDim.getWidth())
            iW  = (int)dmDim.getWidth() - 50;
        
        /*Si el alto es mayor al alta de la pantalla entonces que sea el alto de la pantalla*/
        if(iH>dmDim.getHeight())
            iH  = (int)dmDim.getHeight() - 50;
                
        /*Establece el ancho de la ventana al tamaño del ícono*/
        this.setSize(iW, iH);
        
        /*Crea  el icono en el botón con un tamaño personalizado*/                        
        ImageIcon img       = new ImageIcon(sRut);

        /*Crea la imágen para redimensionar la imágen del icono*/
        java.awt.Image im = img.getImage(); 
        java.awt.Image newimg = im.getScaledInstance( iW, iH,  java.awt.Image.SCALE_SMOOTH );  

        /*Crea el nuevo ícono*/
        img                 = new ImageIcon(newimg);

        /*Agrega el icono a la imágen*/
        jLImg.setIcon(img);
                    
    }/*Fin de public ImgVis() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();

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

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new java.awt.BorderLayout());
        jP1.add(jLImg, java.awt.BorderLayout.CENTER);

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
    private javax.swing.JLabel jLImg;
    private javax.swing.JPanel jP1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
