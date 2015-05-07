//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Clase de logearce nuevamente en el sistema*/
public class LoginOtra extends javax.swing.JFrame 
{
    //Contador de intentos de ingreso al sistema
    private int                 iContEnt    = 0;
    
    /*Contiene el color original del botón*/
    private final java.awt.Color colOri;
    
    /*Declara variables de instancia*/
    private static LoginOtra obj = null;
    
    //Declara variables de la base de datos    
    private Statement       st  = null;
    private ResultSet       rs    = null;
    private Connection      con;
    private final JFrame    jPrin;
    private final JFrame    jPrin2;
        
    
    
    /*Consructor sin argumentos*/
    public LoginOtra(JFrame jPrincip, JFrame jPrincip2) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicializa el jFrame del formulario principal*/
        jPrin = jPrincip;
        jPrin2= jPrincip2;
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo de esación*/
        jTUsr.grabFocus();                
    }

    
    /*Metodo para que el formulario no se abra dos veces*/
    public static LoginOtra getObj(JFrame jPrin, JFrame jPrin2)
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new LoginOtra(jPrin, jPrin2);
        
        /*Devuelve el resultado*/
        return obj;        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBIng = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jPContra = new javax.swing.JPasswordField();
        jTUsr = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCMostC = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
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

        jBIng.setBackground(new java.awt.Color(255, 255, 255));
        jBIng.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBIng.setForeground(new java.awt.Color(0, 102, 0));
        jBIng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ent.png"))); // NOI18N
        jBIng.setText("Entrar");
        jBIng.setToolTipText("Entrar al Sistema (ENTER)");
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
        jP1.add(jBIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTUsr);
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

        jTUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contraseña:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 230, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Usuario:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, -1));

        jCMostC.setBackground(new java.awt.Color(255, 255, 255));
        jCMostC.setText("Mostrar Contraseña");
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
        jP1.add(jCMostC, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 140, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 220, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de ingresar*/
    private void jBIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIngActionPerformed
        
        /*Si no a ingresado un usuario entonces*/
        if(jTUsr.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa una usuario.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/            
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
        
        /*Lee la password que escribio el usuario*/
        String sClav       = new String(jPContra.getPassword());
        
        /*Si no a ingresado una clave entonces*/
        if(sClav.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa una contraseña.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/            
            jPContra.grabFocus();
            return;
        }            
        
        //Abre la base de datos
        con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declare variables locales
        boolean bSi = false;
        String sClavOri = "";
        
        /*Consulta si el usuario que se ingreso existe en la base de datos, y si existe si la contraseña es correcta*/        
        String sQ;
        try
        {
            sQ = "SELECT estac, pass FROM estacs WHERE estac = '" + jTUsr.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Pon la bandera para indicar que si existe el usuario*/
                bSi             = true;
                        
                /*Desencripta la password*/
                sClavOri        = Star.sDecryp(rs.getString("pass"));                                                
            }                        
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

        /*Si no existe el usuario entonces*/
        if(!bSi)
        {
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {                
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
                Star.vExitAp();                
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/Contraseña incorrectos.", "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                     
            
            //Pon el foco del teclado en el campo del usuario y regresa
            jTUsr.grabFocus();
            return;            
        }
                        
        /*Compara el password introducido por el usuario con la establecida en la base de datos, si no es entonces*/
        if(sClavOri.compareTo(sClav)!=0)
        {
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                Star.vExitAp();
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/Contraseña incorrectos." , "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                     
            
            //Pon el foco del teclado en el campo del usuario y regresa
            jTUsr.grabFocus();
            return;            
        }
        
        //Registra el deslog del actual usuario
        if(Star.iRegUsr(null, "1")==-1)
            return;
        
        //Logea nuevamente el usuario en la base de datos
        if(Star.iRegLogUsr(null, "new", jTUsr.getText().trim(), "", "")==-1)
            return;                        
        
        /*Cambia El usuario global*/
        Login.sUsrG         = jTUsr.getText();
        
        //Registra el login del actual usuario
        if(Star.iRegUsr(null, "0")==-1)
            return;
        
        /*Cambia la fecha en que se logio globalmente*/
        DateFormat d        = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date           = new Date();
        Login.sFLog         = d.format(date);
               
        /*Deten el timer que era para el usuario para los máximos y los mínimos*/
        if(Star.timMin != null)
            Star.timMin.cancel();
        
        /*Deten el timer que era para respaldo del usuario anterior*/
        if(Star.timMax != null)
            Star.timMax.cancel();
        
        //Abre la base de datos
        con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Si el usuario actual esta configurado para máximos y mínimos entonces comienza el thread*/
        Star.vMinMax(con);               
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Crea el runable para que respalde en caso de que así deba ser*/
        (new Thread()
        {
            @Override
            public void run() 
            {                                
                /*Función para procesar esta parte*/
                Star.vRespUsr();
            }
            
        }).start();
        
        /*Muestra mensaje de éxito en loggearce*/
        JOptionPane.showMessageDialog(null, "Logeado correctamente como: " + jTUsr.getText(), "Exito en login", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        /*Establece el titulo de la ventana principal y las que se tengan que cambiar*/                
        jPrin.setTitle(Star.sNombreEmpresa + " Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                
        if(jPrin2!=null)
            jPrin2.setTitle(Star.sNombreEmpresa + " Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
            
        /*Llama al recolector de basura*/
        System.gc();
                
        /*Cierra el formulario*/
        this.dispose();        
        obj = null;
        
    }//GEN-LAST:event_jBIngActionPerformed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();
        obj = null;
        
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
        
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se presiona una tecla en el camo de edición de password*/
    private void jPContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContraKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContraKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());        
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se ganael foco del teclado en el campo de edición de password*/
    private void jPContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPContra.setSelectionStart(0);jPContra.setSelectionEnd(jPContra.getText().length());        
        
    }//GEN-LAST:event_jPContraFocusGained
        
    
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

    
    /*Cuando se presiona una tecla en el checkbox de mostrar contraseña*/
    private void jCMostCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMostCKeyPressed
            
    
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

    
    /*Cuando se pierde el foco del teclado en el campo de El usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la contraseña*/
    private void jPContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jPContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPContra.getText().compareTo("")!=0)
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jPContraFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
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
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBIng;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCMostC;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPContra;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
