//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;




/*Clase que muestra que hay mensajes pendientes en chat coorporativo*/
public class AvisChatC extends javax.swing.JFrame 
{       
    /*Parasaber que forma abrir cuando el usuario de un click*/
    private int iVal;
    
    /*Constructor sin argumentos*/
    public AvisChatC(String sMsj, int iVa) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Inicializa la variable global para saber que forma abrir cuand el usuario de el click*/
        iVal    = iVa;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Coloca el mensaje en el control*/
        jTAMsj.setText(sMsj);                
    }

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTAMsj = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAutoRequestFocus(false);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
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

        jTAMsj.setEditable(false);
        jTAMsj.setBackground(new java.awt.Color(255, 255, 255));
        jTAMsj.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jTAMsj.setForeground(new java.awt.Color(0, 153, 255));
        jTAMsj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTAMsj.setText("Mensaje");
        jTAMsj.setBorder(null);
        jTAMsj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTAMsjMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTAMsjMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTAMsjMouseExited(evt);
            }
        });
        jTAMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAMsjKeyPressed(evt);
            }
        });
        getContentPane().add(jTAMsj, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 20));

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

    
    /*Cuando se da un clic en el campo del mensaje*/
    private void jTAMsjMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTAMsjMouseClicked
        
        /*Muestra la forma de chat corporativo o la personal*/
        if(iVal==0)
        {
            ChatC c = new ChatC(false);
            c.getObj(true).setVisible(true);
        }            
        else
        {
            ChatUsr c = new ChatUsr();
            c.setVisible(true);
        }
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jTAMsjMouseClicked

    
    /*Cuando el mouse entra en el campo del mensaje*/
    private void jTAMsjMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTAMsjMouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jTAMsjMouseEntered

    
    /*Cuando el mouse sale del campo del mensaje*/
    private void jTAMsjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTAMsjMouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_jTAMsjMouseExited

    
    /*Cuando se presiona una tecla en el campo del mensaje*/
    private void jTAMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAMsjKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
    
    }//GEN-LAST:event_jTAMsjKeyPressed

        
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
    private javax.swing.JTextField jTAMsj;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
