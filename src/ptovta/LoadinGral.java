//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;



/*Clase para mostrar el progreso del cargado general del que lo quiera utilizar*/
public class LoadinGral extends javax.swing.JFrame 
{
    /*Constructor sin argumentos*/
    public LoadinGral(String sTi)     
    {
        /*Inicializa los componentes gráficos*/
        initComponents();
        
        if(sTi!="")
            jTInf.setText(sTi);
        else
            jTInf.setText("cargando...");
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
                
    }/*Fin de public Loadin()*/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTInf = new javax.swing.JTextField();

        setAlwaysOnTop(true);
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

        jTInf.setEditable(false);
        jTInf.setBackground(new java.awt.Color(255, 255, 255));
        jTInf.setForeground(new java.awt.Color(51, 102, 0));
        jTInf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTInfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

            
    /*Cuando se mueve la rueda del mouse en la forma*/
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

    private void jTInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTInfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTInfActionPerformed
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField jTInf;
    // End of variables declaration//GEN-END:variables
}
