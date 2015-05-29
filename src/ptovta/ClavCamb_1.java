//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static ptovta.ClavMast.bSi;
import static ptovta.Login.sUsrG;




/*Clase para cambiar las passs de seguridad*/
public class ClavCamb_1 extends javax.swing.JDialog 
{
    /*Declara variables de instancia*/
    private static ClavCamb_1             obj = null;
            
    /*Contiene el color original del botón*/
    private final java.awt.Color        colOri;
    
    static boolean                  bSi;
    
    /*Consructor sin argumentos*/
    public ClavCamb_1(JFrame padre, boolean modal) 
    {
        super(padre,modal);
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        bSi     = false;
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        
        this.setTitle("Cambiar clave de seguridad");
        this.setLocation(540, 70);
        
        //Que sea modal el frame
        this.setAlwaysOnTop(true);
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo de password*/
        jPClavAct.grabFocus();               
    }

    
    /*Metodo para que el formulario no se abra dos veces*/
    public static ClavCamb_1 getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new ClavCamb_1(null,true);
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static AltaRapida getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPClavAct = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jPNew = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jPRep = new javax.swing.JPasswordField();
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jPClavAct);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 110, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ingresa Clave");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, -1));

        jPClavAct.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPClavAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPClavAct.setNextFocusableComponent(jPNew);
        jPClavAct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPClavActFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPClavActFocusLost(evt);
            }
        });
        jPClavAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPClavActKeyPressed(evt);
            }
        });
        jP1.add(jPClavAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 220, 20));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ingresa Nueva Clave:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 220, -1));

        jPNew.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPNew.setNextFocusableComponent(jPRep);
        jPNew.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPNewFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPNewFocusLost(evt);
            }
        });
        jPNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPNewKeyPressed(evt);
            }
        });
        jP1.add(jPNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 220, 20));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Repite Nueva Clave:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 220, -1));

        jPRep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPRep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPRep.setNextFocusableComponent(jBGuar);
        jPRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPRepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPRepFocusLost(evt);
            }
        });
        jPRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPRepKeyPressed(evt);
            }
        });
        jP1.add(jPRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 220, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 180, 210, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        /*Pregunta al usuario si esta seguro de abandonar el cambio de pass*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "No se registró la nueva contraseña, el programa se cerrara. \n¿Seguro que quieres salir de la modificación de la contraseña?", "Salir Modificación Clave", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {        
            /*Llama al recolector de basura*/
            System.gc();
        
            /*Cierra la ventana*/
            this.dispose();
            
            /*Resetea el objeto para que próximamente se abra un nuevo formulario*/
            obj = null;
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        //Declara variables locales
        String sIngCla;
        String sClaNew;
        String sClaNewRep;        
        String sClaSeg              = "";
        
        
        
        /*Lee ingresa password*/
        sIngCla = new String(jPClavAct.getPassword()).trim();
        
        /*Si el campo de ingresar pass esta vacio no puede seguir*/
        if(sIngCla.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPClavAct.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de ingresar pass esta vacio.", "Ingresa Clave de Seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de password y regresa*/
            jPClavAct.grabFocus();           
            return;
        }        
        
        /*Lee password nuevo*/
        sClaNew = new String(jPNew.getPassword()).trim();
        
        /*Si el campo de ingresar nuevo password esta vacio entonces*/
        if(sClaNew.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPNew.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de ingresar nuevo contraseña esta vacio.", "Ingresa Nueva Clave de Seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de nuevo password y regresa*/
            jPNew.grabFocus();           
            return;
        }
        
        /*Lee repite password nuevo*/
        sClaNewRep = new String(jPRep.getPassword()).trim();
        
        /*Si el campo de repite pass esta vacio no puede seguir*/
        if(sClaNewRep.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPRep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de repite contraseña esta vacio.", "Repite Clave de Seguridad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de repite password y regresa*/
            jPRep.grabFocus();           
            return;

        }
        
        /*Si password nuevo y repite password no coinciden entonces*/
        if(sClaNewRep.compareTo(sClaNew)!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La nueva contraseña y repite contraseña no coinciden.", "No coinciden las contraseña", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de nueva pass y regresa*/
            jPNew.grabFocus();           
            return;            
        }
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables de bases de datos*/    
        Statement   st;
        ResultSet   rs;
        String      sQ;        
    
        /*Consulta el password de seguridad*/
        try
        {
            //sQ = "SELECT clav FROM clavs WHERE uso = 'clavseg'";
            sQ="SELECT pass FROM estacs WHERE estac = '" +  Login.sUsrG.replace("'", "''")+ "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el password de seguridad*/
                //sClaSeg = rs.getString("clav");
                sClaSeg = rs.getString("pass");
                
                /*Desencripta el password de seguridad*/
                sClaSeg = Star.sDecryp(sClaSeg);                                                
            }                        
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                 
        System.out.println("clave introducida"+sIngCla);
        System.out.println("clave del sistema"+sClaSeg);
        /*Si el password no coincide con el password de seguridad entonces*/
        if(sIngCla.compareTo(sClaSeg)!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "la contraseña no coincide con la contraseña de seguridad original.", "No coinciden las contraseñas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de password y regresa*/
            jPClavAct.grabFocus();           
            return;
        }
        
        /*Pregunta al usuario si esta seguro de cambiar el password*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres cambiar la contraseña de seguridad?", "Cambiar contraseña", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        System.out.println("clave cambiada"+sClaNew);
        /*Encripta el password de seguridad nueva*/
        sClaNew = Star.sEncrip(sClaNew);                                
        
        /*Actualiza en la base de datos la nueva pass*/
        try 
        {            
            sQ = "UPDATE estacs SET "
                    + "pass         = '" + sClaNew.replace("'", "''") + "' "
                    + "WHERE estac    = '"+ Login.sUsrG.replace("'", "''") + "' ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {              
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }     
         try 
        {            
            sQ = "UPDATE estacs SET "
                    + "pass         = '" + sClaNew.replace("'", "''") + "' "
                    + "WHERE estac    = 'RESPALDO' ";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
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
                        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Contraseña cambiado con éxito.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));        
        
        bSi     = true;
        
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
    private void jPClavActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPClavActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPClavActKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de password*/
    private void jPClavActFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPClavActFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPClavAct.setSelectionStart(0);jPClavAct.setSelectionEnd(jPClavAct.getText().length());                
        
    }//GEN-LAST:event_jPClavActFocusGained

    
    /*Cuando se ganael foco del teclado en el campo de nueva pass*/
    private void jPNewFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPNewFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPNew.setSelectionStart(0);jPNew.setSelectionEnd(jPNew.getText().length());                
        
    }//GEN-LAST:event_jPNewFocusGained

    
    /*Cuando se presiona una tecla en el campo de password de nueva pass*/
    private void jPNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPNewKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de password repite*/
    private void jPRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPRepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPRep.setSelectionStart(0);jPRep.setSelectionEnd(jPRep.getText().length());                
        
    }//GEN-LAST:event_jPRepFocusGained

    
    /*Cuando se presiona una tecla en el campo de password repite*/
    private void jPRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPRepKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de pass*/
    private void jPClavActFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPClavActFocusLost

        /*Coloca el cursor al principio del control*/
        jPClavAct.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPClavAct.getText().compareTo("")!=0)
            jPClavAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jPClavAct.getText().length()> 255)
            jPClavAct.setText(jPClavAct.getText().substring(0, 255));
        
        /*Coloca el cursor al principio del campo*/
        jPClavAct.setCaretPosition(0);
        
    }//GEN-LAST:event_jPClavActFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de password nuevo*/
    private void jPNewFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPNewFocusLost

        /*Coloca el cursor al principio del control*/
        jPNew.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPNew.getText().compareTo("")!=0)
            jPNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jPNew.getText().length()> 255)
            jPNew.setText(jPNew.getText().substring(0, 255));
        
        /*Coloca el cursor al principio del campo*/
        jPNew.setCaretPosition(0);
        
    }//GEN-LAST:event_jPNewFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de repite password*/
    private void jPRepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPRepFocusLost

        /*Coloca el cursor al principio del control*/
        jPRep.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPRep.getText().compareTo("")!=0)
            jPRep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jPRep.getText().length()> 255)
            jPRep.setText(jPRep.getText().substring(0, 255));
        
        /*Coloca el cursor al principio del campo*/
        jPRep.setCaretPosition(0);
        
    }//GEN-LAST:event_jPRepFocusLost

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrasta el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el mouse en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPClavAct;
    private javax.swing.JPasswordField jPNew;
    private javax.swing.JPasswordField jPRep;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
