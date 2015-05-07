//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;




/*Clase que muestra un texto en grande*/
public class DescripGran extends javax.swing.JFrame 
{       
    /*Contiene la dirección del jtextfield del otro formulario y del textarea*/
    javax.swing.JTextField jText;
    javax.swing.JTextArea  jTAText;
    
    
    
    /*Constructor sin argumentos*/
    public DescripGran(javax.swing.JTextField jTTex, javax.swing.JTextArea jTATex) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Pon el foco del teclado en el campo de la infromación*/
        jTAInfo.grabFocus();
        
        /*Obtiene la dirección del otro formulario del control*/
        jText   = jTTex;
        jTAText = jTATex;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Agrega la información en el control*/
        if(jText!=null)        
            jTAInfo.setText(jText.getText());
        else if(jTAText!=null)
            jTAInfo.setText(jTAText.getText());
        
        /*Coloca la posición del caret al principio del control*/
        jTAInfo.setCaretPosition(0);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);        
    }

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTAInfo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
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

        jTAInfo.setColumns(20);
        jTAInfo.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        jTAInfo.setLineWrap(true);
        jTAInfo.setRows(5);
        jTAInfo.setBorder(null);
        jTAInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAInfoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAInfo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 470));

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

    
    /*Cuando se presiona una tecla en el campo de la información*/
    private void jTAInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAInfoKeyPressed
        
        /*Llama a la forma escalable para procesar la pulsación*/
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTAInfoKeyPressed
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces cierra la forma*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            /*Guarda la información en el control del otro formulario*/
            if(jText!=null)
            {
                jText.setText(jTAInfo.getText());
                jText.setCaretPosition(0);   
            }                
            else if(jTAText!=null)
            {
                jTAText.setText(jTAInfo.getText());
                jTAText.setCaretPosition(0);   
            }                
            
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();
            dispose();        
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAInfo;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
