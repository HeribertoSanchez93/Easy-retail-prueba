//Paquete
package ptovta;

//Importaciones
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Clase para mostrar el progreso del cargado general del que lo quiera utilizar*/
public class SelSer extends javax.swing.JDialog 
{
    //Se dan de alta los punteros
    javax.swing.JTextField sSerG;
    javax.swing.JTextField sComenG;
    
    //Solo funciona para la forma de descripcion
    String sProdG;
    String sAlmaG;
    
        
    /*Contiene el color original del botón*/
    private java.awt.Color  colOri;

    /*Constructor sin argumentos*/
    public SelSer(javax.swing.JTextField sSer, javax.swing.JTextField sComen,String sTitulo,String sProd,String sAlma, int iEdit)     
    {
        /*Inicializa los componentes gráficos*/
        initComponents();

        /*Esconde el control del comentario de la serie*/
        jTComenSer.setVisible(false);

        //Se obtiene el color original
        colOri  = jBCan.getBackground();
        
        /*Pon el foco del teclado en el campo de la infromación*/
        jTSer.grabFocus();
        
        /*Obtiene la dirección del otro formulario del control*/
        sSerG   = sSer;
        sComenG = sComen;
        
        //Se toma el producto
        sProdG  = sProd;
        sAlmaG  = sAlma;
        
        //Si se puede editar o no
        if(iEdit==1)
            jTAComen.setEditable(false);
        
        /*Activa en el JTextArea que se usen normamente las teclas de tabulador*/
        jTAComen.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAComen.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        //Se le pone el producto que va
        jLabel3.setText("Producto: " + sTitulo);
                
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
                
    }/*Fin de public Loadin(String sRu, JTable jTabPro, int iContF)*/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPan = new javax.swing.JPanel();
        jBOK = new javax.swing.JButton();
        jBCan = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAComen = new javax.swing.JTextArea();
        jTComenSer = new javax.swing.JTextField();

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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPan.setBackground(new java.awt.Color(255, 255, 255));
        jPan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanKeyPressed(evt);
            }
        });

        jBOK.setBackground(new java.awt.Color(255, 255, 255));
        jBOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/carg.png"))); // NOI18N
        jBOK.setText("OK");
        jBOK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBOKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBOKMouseExited(evt);
            }
        });
        jBOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOKActionPerformed(evt);
            }
        });
        jBOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBOKKeyPressed(evt);
            }
        });

        jBCan.setBackground(new java.awt.Color(255, 255, 255));
        jBCan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBCan.setText("Cancelar");
        jBCan.setNextFocusableComponent(jTSer);
        jBCan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCanMouseExited(evt);
            }
        });
        jBCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCanActionPerformed(evt);
            }
        });
        jBCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCanKeyPressed(evt);
            }
        });

        jTSer.setToolTipText("Precione tecla hasi abajo para abrir la busqueda de la serie");
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.setNextFocusableComponent(jTAComen);
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jTSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTSerActionPerformed(evt);
            }
        });
        jTSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSerKeyTyped(evt);
            }
        });

        jLabel1.setText("Serie:");

        jLabel2.setText("Comentario:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Producto n-n");

        jTAComen.setColumns(20);
        jTAComen.setRows(5);
        jTAComen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAComen.setNextFocusableComponent(jBOK);
        jTAComen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAComenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAComenFocusLost(evt);
            }
        });
        jTAComen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAComenKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAComen);

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);

        javax.swing.GroupLayout jPanLayout = new javax.swing.GroupLayout(jPan);
        jPan.setLayout(jPanLayout);
        jPanLayout.setHorizontalGroup(
            jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanLayout.createSequentialGroup()
                                .addComponent(jBOK, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(jBCan, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTSer, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanLayout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTComenSer, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanLayout.setVerticalGroup(
            jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanLayout.createSequentialGroup()
                .addGroup(jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTComenSer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jTSer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBOK, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBCan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    
    //Cuando el campo de serie gana el foco
    private void jTSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSer.setSelectionStart(0);jTSer.setSelectionEnd(jTSer.getText().length());

    }//GEN-LAST:event_jTSerFocusGained

    
    //Cuando el campo de serie pierde el foco
    private void jTSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTSer.setCaretPosition(0);
                
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Se regresa a la normalidad el área de texto
        jTAComen.setText("");
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Obtiene el comentario de la serie
        try
        {
            sQ  = "SELECT comen FROM serieprod WHERE ser = '" + jTSer.getText().trim() + "' AND prod = '" + sProdG + "' AND alma = '" + sAlmaG + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                jTAComen.setText(rs.getString("comen"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                          
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);                                   

    }//GEN-LAST:event_jTSerFocusLost

    
    //Cuando se preciona alguna tecla en el campo de serie
    private void jTSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {            
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(null, sProdG, 34, jTSer, jTComenSer, null, sAlmaG, null);
            b.setModal     (true);
            b.setVisible   (true);
            
            //Se le pasa el comentario al texto
            jTAComen.setText(jTComenSer.getText());
        } 
        else
            //Llama a la función escalable
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTSerKeyPressed

    
    //cuando se peciona el boton de OK
    private void jBOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOKActionPerformed
        
        //Se toman los valores
        sSerG.setText( jTSer.getText().trim());
        sComenG.setText( jTAComen.getText().trim());
        
        //Se cierra
        this.dispose();
        
    }//GEN-LAST:event_jBOKActionPerformed

    
    //cuando se peciona el boton de cancelar
    private void jBCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCanActionPerformed
        
        //Dale valores que signifiquen nulo
        sSerG.setText("-1");
        sComenG.setText("-1");
        
        //Se cierra
        this.dispose();
                
    }//GEN-LAST:event_jBCanActionPerformed

    
    //Cuando se cierra la ventana
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/                
        jBCan.doClick(); 
        
    }//GEN-LAST:event_formWindowClosing

    //Al escribir en el campo de la serie
    private void jTSerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerKeyTyped
        
        //Primero cambialo a mayusculas
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar())); 
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < 'A') || (evt.getKeyChar() > 'Z')) && ((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && evt.getKeyChar() != 'Ñ')         
            evt.consume();
        
    }//GEN-LAST:event_jTSerKeyTyped

    
    //Al precionar alguna tecla en el campo de comentario
    private void jTAComenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAComenKeyPressed
        
        /*Llama a la forma escalable para procesar la pulsación*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAComenKeyPressed

    
    //Cuando se gana el foco el campo de comentario
    private void jTAComenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComenFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAComen.setSelectionStart(0);jTAComen.setSelectionEnd(jTAComen.getText().length());

    }//GEN-LAST:event_jTAComenFocusGained

    
    //Cuando se pierde el foco el campo de comentario
    private void jTAComenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComenFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAComen.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAComenFocusLost

    
    //Cuando se preciona una tecla en el boton OK
    private void jBOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBOKKeyPressed
       
        /*Llama a la forma escalable para procesar la pulsación*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBOKKeyPressed

    
    //Cuando se preciona una tecla en el boton cancelar
    private void jBCanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCanKeyPressed
        
        /*Llama a la forma escalable para procesar la pulsación*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCanKeyPressed

    
    //Cuando el cursor entra al boton ok
    private void jBOKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBOKMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBOK.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBOKMouseEntered

    
    //Cuando el cursor entra al boton cancelar
    private void jBCanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCan.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCanMouseEntered

    
    //Cuando el cursor sale al boton ok
    private void jBOKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBOKMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBOK.setBackground(Star.colOri);
    }//GEN-LAST:event_jBOKMouseExited

    
    //Cuando el cursor sale al boton cancelar
    private void jBCanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCan.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCanMouseExited

    
    //Cuando se preciona una tecla en el panel
    private void jPanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanKeyPressed
        
        /*Llama a la forma escalable para procesar la pulsación*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanKeyPressed

    private void jTSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTSerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTSerActionPerformed
  
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario y presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBCan.doClick();            
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBOK.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBCan.doClick();
        //Si se preciona enter
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER )
            jBOK.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCan;
    private javax.swing.JButton jBOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAComen;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTSer;
    // End of variables declaration//GEN-END:variables
}
