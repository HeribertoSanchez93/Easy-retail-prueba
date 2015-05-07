//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import static ptovta.Login.sUsrG;




/*Clase para cambiar las clavs de seguridad*/
public class ClavMast extends javax.swing.JFrame
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    //Declara variables de la base de datos    
    private Statement               st      = null;
    private ResultSet               rs      = null;
    private Connection              con;
    
    /*Declara variables estaticas publicas*/
    static boolean                  bSi;
    static boolean                  ban;
    
    /*Esta variable es como un switch para saber que tipo de password tiene que comparar el sistema*/
    private final int               iTipC;           
    
    
    
    /*Consructor sin argumentos*/
    public ClavMast(JFrame jFram, int iTip) 
    {
        /*Obtiene los parámetros del otro formulario*/
        iTipC   = iTip;
        
        /*Inicializa la variable en false siempre que se crea la forma*/
        bSi     = false;
        ban     = false;
        
        //Que sea modal el frame
        this.setAlwaysOnTop(true);
        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Si es por password de administrador entonces*/
        if(iTip==1)
        {            
            /*Pon el foco del teclado en el campo del usuario*/
            jTEsta.grabFocus();                      
        }            
        else
        {
            /*Que no sea visible el campo de El usuario ya que no es necesario*/
            jTEsta.setVisible(true);
            
            /*Pon el foco del teclado en el campo de password*/
            jPClav.grabFocus();                      
        }
        
    }/*Fin de public ClaveIngresa1*/

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPClav = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jTEsta = new javax.swing.JTextField();
        jLAyu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Aceptar");
        jBGuar.setNextFocusableComponent(jBSal);
        jBGuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarMouseExited(evt);
            }
        });
        jBGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarActionPerformed(evt);
            }
        });
        jBGuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuarKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTEsta);
        jBSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSalMouseExited(evt);
            }
        });
        jBSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalActionPerformed(evt);
            }
        });
        jBSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSalKeyPressed(evt);
            }
        });
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 120, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario administrador:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, -1));

        jPClav.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPClav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPClav.setNextFocusableComponent(jBGuar);
        jPClav.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPClavFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPClavFocusLost(evt);
            }
        });
        jPClav.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPClavKeyPressed(evt);
            }
        });
        jP1.add(jPClav, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 220, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ingresa clave:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 220, -1));

        jTEsta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEsta.setNextFocusableComponent(jPClav);
        jTEsta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstaFocusLost(evt);
            }
        });
        jTEsta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstaKeyTyped(evt);
            }
        });
        jP1.add(jTEsta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 220, 20));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 150, 210, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                
        /*Presiona el botón de salir*/
        jBSal.doClick();                
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el botón de cancelar*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
                
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la ventana*/
        this.dispose();            
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        //Abre la base de datos                             
        Connection  conInt = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(conInt==null)
        {
            ban=true;
            return;
        }
        //Declara variables locales
        String sIngCla;        
        String sClaSeg              = "";
        
        /*Si se tiene que válidar password de administrador entonces*/
        if(iTipC==1)
        {
            /*Lee el usuario que ingreso el usuario*/
            String sEsta    = jTEsta.getText();
            
            /*Si el campo de usuario es cadena vacia entonces*/
            if(sEsta.compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo de pass y regresa*/
                jTEsta.grabFocus();        
                return;
            }        
            
            /*Lee el password que ingreso el usuario*/
            sIngCla = new String(jPClav.getPassword()).trim();

            /*Si el campo de ingresar pass esta vacio entonces*/
            if(sIngCla.compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jPClav.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo de ingresar la contraseña esta vacio.", "Ingresa Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo de pass y regresa*/
                jPClav.grabFocus();        
                return;
            }        
                                                
            /*Comprueba si el usuario existe*/            
            String sQ;
            try
            {
                sQ = "SELECT admcaj, pass FROM estacs WHERE estac = '" + sEsta + "'";
                st = conInt.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces El usuario no existe*/
                if(!rs.next())
                {
                    ban=true;
                    //Cierra la base de datos
                    if(Star.iCierrBas(conInt)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTEsta.grabFocus();
                    return;                    
                }
                /*Else, si hay datos entonces*/
                else
                {
                    /*Si no es un administrador entonces*/
                    if(rs.getString("admcaj").compareTo("1")!=0)
                    {
                        ban=true;
                        //Cierra la base de datos
                        if(Star.iCierrBas(conInt)==-1)
                            return;

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                        /*Coloca el foco del teclado en el control y regresa*/
                        jTEsta.grabFocus();
                        return;                    
                    }                    
                    
                    /*Obtiene la password*/
                    String sClav    = rs.getString("pass");
                    
                    /*Desencripta la pass*/
                    sClav           = Star.sDecryp(sClav);
                    
                    /*Si la pass no es correcto entonces*/
                    if(sClav.compareTo(new String(jPClav.getPassword()).trim())!=0)
                    {   
                        ban=true;
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                        /*Coloca el foco del teclado en el control y regresa*/
                        jPClav.grabFocus();
                        return;                    
                    }
                    
                    /*Coloca la babdera*/
                    ban=true;
                    bSi = true;
                }                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
        
        }/*Fin de if(iTipC==1)*/
        else
        {
            /*Lee la password que ingreso el usuario*/
            sIngCla = new String(jPClav.getPassword()).trim();

            /*Si el campo de ingresar pass esta vacio entonces*/
            if(sIngCla.compareTo("")==0)
            {
                ban=true;
                //Cierra la base de datos
                if(Star.iCierrBas(conInt)==-1)
                    return;
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo de ingresar la contraseña esta vacio.", "Ingresa Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo de pass y regresa*/
                jPClav.grabFocus();        
                return;
            }        

            /*Consulta el password de seguridad*/
            String sQ;
            try
            {
                //sQ = "SELECT clav FROM clavs WHERE uso = 'clavseg'";
                sQ="SELECT pass FROM estacs WHERE estac = '" + sUsrG + "'";
                st = conInt.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene la pass de seguridad*/
                    sClaSeg = rs.getString("pass");  

                    /*Desencripta la password de seguridad*/
                    sClaSeg = Star.sDecryp(sClaSeg);                                                
                }
            }
            catch(SQLException expnSQL)
            {   
                ban=true;
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }                                 

            /*Si la password no coincide con el password de seguridad entonces*/
            if(sIngCla.compareTo(sClaSeg)!=0)
            {
                ban=true;
                //Cierra la base de datos
                if(Star.iCierrBas(conInt)==-1)
                    return;
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Clave de seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo de password y regresa*/
                jPClav.grabFocus();           
                return;
            }

            /*Coloca la bandera en true*/
            ban=true;
            bSi = true;
            
        }/*Fin de else*/                                                                            
        
        //Cierra la base de datos
        if(Star.iCierrBas(conInt)==-1)
            return;
        
        System.out.println(bSi);
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();

    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de password*/
    private void jPClavKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPClavKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPClavKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de password*/
    private void jPClavFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPClavFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPClav.setSelectionStart(0);jPClav.setSelectionEnd(jPClav.getText().length());                
        
    }//GEN-LAST:event_jPClavFocusGained
                      
    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el ratón en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se gana el foco del teclado en el campo de El usuario*/
    private void jTEstaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEsta.setSelectionStart(0);jTEsta.setSelectionEnd(jTEsta.getText().length());        
        
    }//GEN-LAST:event_jTEstaFocusGained

    
    /*Cuando se presiona una tecla en el campo de El usuario*/
    private void jTEstaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de El usuario*/
    private void jTEstaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstaKeyTyped

    
    /*Cuando el mouse entra en el campo del link de ayuda*/
    private void jLAyuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando el mouse sale del campo del link de ayuda*/
    private void jLAyuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseExited
                
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));	
        
    }//GEN-LAST:event_jLAyuMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de la estación*/
    private void jTEstaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusLost

        /*Coloca el cursor al principio del control*/
        jTEsta.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEsta.getText().compareTo("")!=0)
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTEstaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control de la contraseña*/
    private void jPClavFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPClavFocusLost

        /*Coloca el cursor al principio del control*/
        jPClav.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPClav.getText().compareTo("")!=0)
            jPClav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jPClavFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

            
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPClav;
    private javax.swing.JTextField jTEsta;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
