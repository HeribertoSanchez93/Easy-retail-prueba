//Paquete
package ptovta;

//Importaciones
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;




/*Clase para controlar el logearse nuevamente en el sistema*/
public class DesLogin extends javax.swing.JFrame
{        
    //Contador de intentos de ingreso al sistema
    private int                             iContEnt    = 0;
    
    /*Contiene el color original del botón*/
    private final java.awt.Color            colOri;
    
    /*Contiene la dirección del frame del otro formulario*/
    private final javax.swing.JFrame        jFrame;
    
    
    
    
    /*Consructor sin argumentos*/
    public DesLogin(javax.swing.JFrame jFra) 
    {        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Obtiene el frame del otro formulario*/
        this.jFrame = jFra;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);        
        
        //Que sea modal la ventana       
        this.setAlwaysOnTop(true);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Pon el foco del teclado en el control del usuario*/
        jTUsr.grabFocus();      

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
            
        //Registra el deslog del actual usuario
        Star.iRegUsr(null, "1");            
                        
    }/*Fin de public DesLogin() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBIng = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jPContra = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTUsr = new javax.swing.JTextField();
        jCMostC = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
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

        jBIng.setBackground(new java.awt.Color(255, 255, 255));
        jBIng.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBIng.setForeground(new java.awt.Color(0, 102, 0));
        jBIng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ent.png"))); // NOI18N
        jBIng.setText("Entrar");
        jBIng.setToolTipText("Entrar (ENTER)");
        jBIng.setNextFocusableComponent(jBSal);
        jBIng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBIngMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBIngMouseExited(evt);
            }
        });
        jBIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIngActionPerformed(evt);
            }
        });
        jBIng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBIngKeyPressed(evt);
            }
        });
        jP1.add(jBIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 110, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 110, -1));

        jPContra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPContra.setNextFocusableComponent(jCMostC);
        jPContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContraFocusLost(evt);
            }
        });
        jPContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContraKeyPressed(evt);
            }
        });
        jP1.add(jPContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 230, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contraseña:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 230, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, -1));

        jTUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTUsr.setText("SUP");
        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jPContra);
        jTUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrFocusLost(evt);
            }
        });
        jTUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUsrKeyTyped(evt);
            }
        });
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, 20));

        jCMostC.setBackground(new java.awt.Color(255, 255, 255));
        jCMostC.setText("Mostrar Contraseña F2");
        jCMostC.setNextFocusableComponent(jBIng);
        jCMostC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMostCActionPerformed(evt);
            }
        });
        jCMostC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMostCKeyPressed(evt);
            }
        });
        jP1.add(jCMostC, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 180, -1));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.setToolTipText("");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 110, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    /*Cuando se presiona el botón de ingresar*/
    private void jBIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIngActionPerformed
        
        //Declara variables locales               
        String      sClavOri            = "";        
        boolean     bSi                 = false;
                
        
        
        
        
        
        /*Si no a escrito un usuario entonces*/
        if(jTUsr.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }

        //Comprueba si la configuración para multilogeo esta activada
        int iMulti  = Star.iGetMultiLog(null);
        
        //Si hubo error entonces regresa
        if(iMulti==-1)
            return;
        
        //Si esta activado el multilogeo entonces
        if(iMulti==0)
        {
            
            //Obtiene si el usuario esta actualmente logeado
            iMulti  = Star.iUsrCon(null, jTUsr.getText().trim());
            
            //Si el usuario actual ya esta logeado entonces
            if(iMulti==1)
            {
                //Mensajea y regresa
                JOptionPane.showMessageDialog(null, "El sistema esta configurado para no aceptar multilogeos.", "Multilogeo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }            
        }
        
        /*Si no a escrito un password entonces*/
        if(new String(jPContra.getPassword()).compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "contraseña", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo y regresa*/
            jPContra.grabFocus();           
            return;
        }
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Lee la password que escribio el usuario*/
        String sClav            = new String(jPContra.getPassword());

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Consulta si el usuario que se ingreso existe en la base de datos, y si existe si la contraseña es correcta*/             
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                bSi   = true;                                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }                                      
        
        /*Si no existe el usuario entonces mensajear y retornar*/
        if(!bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                System.exit(0);                
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
            
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo del usuario y regresa*/
            jTUsr.grabFocus();                        
            return;           
        }
        
        /*Obtiene la password de el usuario*/        
        try
        {
            sQ = "SELECT pass FROM estacs WHERE estac = '" + jTUsr.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {                
                sClavOri      = rs.getString("pass");                                   
                sClavOri      = Star.sDecryp(sClavOri);                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }
                        
        /*Obtiene la fecha del sistema para registrar la hora en que se logeo*/
        DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dat        = new Date();
        Login.sFLog     = da.format(dat);
                
        /*Compara la pass introducida por el usuario con la establecida en la base de datos, si no es entonces*/
        if(sClavOri.compareTo(sClav)!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                System.exit(0);                
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos" , "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            //Pon el foco del teclado en el campo del usuario y regresa
            jTUsr.grabFocus();                       
            return;            
        }                        

        //Logea nuevamente el usuario en la base de datos
        if(Star.iRegLogUsr(null, "act", jTUsr.getText().trim(), "", "")==-1)
            return;                        
        
        /*Inicializa el nuevo usuario logeda*/
        Login.sUsrG = jTUsr.getText().trim();        
        
        //Registra el log de inicio de sesión del sistema al usuario
        if(Star.iRegUsr(con, "0")==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el nuevo título de la ventana*/
        this.jFrame.setTitle(Star.sNombreEmpresa + " Usuario: <" + Login.sUsrG + "> " + Login.sFLog);
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();
                        
    }//GEN-LAST:event_jBIngActionPerformed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Preguntar al usuario si esta seguro de querer salir de la aplicación*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la aplicación?", "Salir Aplicación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
        
        //Logea nuevamente el usuario en la base de datos
        if(Star.iRegLogUsr(null, "del", "", "", "")==-1)
            return;                        

        /*Cierra la aplicación*/
        System.exit(0);
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ingresar*/
    private void jBIngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBIngKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBIngKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cancelar*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed
    
    
    /*Cuando se presiona una tecla en el camo de edición de password*/
    private void jPContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContraKeyPressed

        //Declara variables locales
        String sPass;
        
        
        
        
        /*Lee el password introducido por el usuario*/
        sPass = new String(jPContra.getPassword());
        
        /*Si el campo excede la cantidad de cares permitidos recortalo*/
        if(sPass.length()> 255)
            jPContra.setText(sPass.substring(0, 255));
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContraKeyPressed

    
    
    /*Cuando se ganael foco del teclado en el campo de edición de password*/
    private void jPContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPContra.setSelectionStart(0);jPContra.setSelectionEnd(jPContra.getText().length());                        
        
    }//GEN-LAST:event_jPContraFocusGained

    
                
    /*Cuando se pierde el foco del teclado en el campo de contraseña*/
    private void jPContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusLost

        /*Coloca el cursor al principio del control*/
        jPContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPContra.getText().compareTo("")!=0)
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el password introducido por el usuario*/
        String sPass = new String(jPContra.getPassword());
        
        /*Si el campo excede la cantidad de cares permitidos recortalo*/
        if(sPass.length()> 255)        
            jPContra.setText(sPass.substring(0, 255));        
        
    }//GEN-LAST:event_jPContraFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);        
        jTUsr.setSelectionEnd(jTUsr.getText().length());
        
    }//GEN-LAST:event_jTUsrFocusGained

         
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed
   
    
    /*Cuando se tipea una tecla en el campo de usuario*/
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUsrKeyTyped

            
    /*Cuando sucede una acción en el checkbox de mostrar contraseña*/
    private void jCMostCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMostCActionPerformed

        /*Si esta marcado entonces muestra la contraseña*/
        if(jCMostC.isSelected())
            jPContra.setEchoChar((char)0);
        /*Else, ocultala*/
        else
            jPContra.setEchoChar('*');

    }//GEN-LAST:event_jCMostCActionPerformed

    
    /*Cuando se presiona un tecla en el campo de mostrar contraseña*/
    private void jCMostCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCMostCKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el control de El usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBIngMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBIngMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBIng.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBIngMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBIngMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBIngMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBIng.setBackground(colOri);
        
    }//GEN-LAST:event_jBIngMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Si se presiona la tecla de escape cerrar el formulario entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona ENTER entonces presiona el botón de ingresar*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            jBIng.doClick();
        /*Si se presiona F2 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Si esta seleccionado entonces*/
            if(jCMostC.isSelected())            
            {
                /*Deseleccionalo y muestra el asterisco*/
                jCMostC.setSelected(false);
                jPContra.setEchoChar('*');
            }
            else
            {                
                /*Else, desmarcalo y muestra los caracteres*/
                jCMostC.setSelected(true);            
                jPContra.setEchoChar((char)0);
            }            
        }
    
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBIng;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCMostC;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPContra;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
