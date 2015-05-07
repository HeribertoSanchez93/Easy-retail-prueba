//Paquete
package ptovta;

//Importaciones
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Clase para chatear con una usuario*/
public class ChatP extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Thread para estar mostrando la información en el control de mensajes*/
    private Thread              th          = null;
           
    /*Thread para estar revisando si esta conectado el usuario con el que se esta chateando*/
    private  Thread             thCon       = null;
    
    /*Almacena El usuario con la que se va a chatear*/
    private String              sEstacGlo;
        
    
    
    
    /*Constructor sin argumentos*/
    public ChatP(boolean bEntra, String sEsta) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEnvi);
                        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        setLocationRelativeTo(null);
        
        /*Recibe los parámetros del otro formulario*/
        sEstacGlo   = sEsta;
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Chat con " + sEsta + ", Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Activa en el textarea que se usen normamente las teclas de tabulador*/
        jTAMsj.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAMsj.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                

        /*Pon el foco del teclado en el control del chat*/
        jTAChat.grabFocus();
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        String      sQ2; 

        /*Actualiza todos los mensajes del usuario como que no se han visto*/                
        try 
        {                
            sQ2= "UPDATE chat SET vist = '' WHERE estac = '" + Login.sUsrG + "' OR estacdestin = '" + Login.sUsrG + "'";
            st = con.createStatement();
            st.executeUpdate(sQ2);                    
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }                    

        /*Obtiene todos los mensajes para acutalizar el campo de vistos*/
        try
        {
            sQ = "SELECT IFNULL(vist, '' ) AS vist, id_id FROM chat WHERE estac = '" + Login.sUsrG + "' AND vist NOT LIKE('%!" + Login.sUsrG + "!%') AND estacdestin = '" + sEstacGlo.trim() + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                                
                /*Actualiza el registro para saber que esta usuario a visto ese mensaje*/                
                try 
                {                
                    sQ2= "UPDATE chat SET "
                            + "vist         = '" + rs.getString("vist").replace("!" + Login.sUsrG + "!", "") + "' "
                            + "WHERE id_id  = " + rs.getString("id_id") + " AND estacdestin = '" + Login.sUsrG.replace("'", "''") + "' AND estac = '" + sEstacGlo.replace("'", "''").trim() + "'";                                        
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                    
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                 }                    
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

        /*Carga los mensajes nuevos*/
        vCarg();
        
        /*Thread para estar monitoreando si el usuario esta conectado o no*/
        thCon   = new Thread()
        {
           @Override
           public void run()
           {
               /*Búcle inifinito*/
               while(true)
               {                  
                    //Abre la base de datos                             
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    //Declara variables de la base de datos
                    Statement   st;
                    ResultSet   rs;                   
                    String      sQ; 

                    /*Comprueba si el usuario esta conectado*/                                       
                    try
                    {
                        sQ = "SELECT port FROM logestac WHERE estac = '" + sEstacGlo + "'";	
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si no hay datos entonces no esta conectado y pon el título como no conectadp*/
                        if(!rs.next())                                                                                
                            setTitle("Chat con " + sEstacGlo + "<Offline>, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                        /*Else si esta conctado y coloca el título como conectado*/
                        else
                            setTitle("Chat con " + sEstacGlo + "<Online>, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
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

                    /*Duerme el thread 10 segundos*/
                    try
                    {
                        Thread.sleep(10000);
                    }
                    catch(InterruptedException expnInterru)
                    {
                        break;
                    }
                    
               }/*Fin de while(true)*/                    
                    
           }/*Fin de public void run()*/
        };
        thCon.start();
        
        /*Thread para cargar todos los mensajes no leidos*/
        th = new Thread()
        {
            @Override
            public void run()
            {                
                /*Bucle infinito*/
                while(true)
                {
                    /*Duerme el thread 1 segundo*/
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException expnInterru)
                    {      
                        break;
                    }     
                    
                    /*Carga los mensajes nuevos*/
                    vCarg();                                    
                }                                            
            }
        };
        th.start();
                        
    }/*Fin de public VisEmps() */
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTAMsj = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAChat = new javax.swing.JTextArea();
        jBEnvi = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBVide = new javax.swing.JButton();

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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTAMsj.setEditable(false);
        jTAMsj.setColumns(20);
        jTAMsj.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jTAMsj.setLineWrap(true);
        jTAMsj.setRows(5);
        jTAMsj.setNextFocusableComponent(jTAChat);
        jTAMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAMsjKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTAMsj);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 500));

        jTAChat.setColumns(20);
        jTAChat.setLineWrap(true);
        jTAChat.setRows(5);
        jTAChat.setNextFocusableComponent(jBEnvi);
        jTAChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAChatKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAChat);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 320, 60));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jBEnvi.setForeground(new java.awt.Color(0, 102, 0));
        jBEnvi.setText("Enviar");
        jBEnvi.setNextFocusableComponent(jBVide);
        jBEnvi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEnviMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEnviMouseExited(evt);
            }
        });
        jBEnvi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEnviActionPerformed(evt);
            }
        });
        jBEnvi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEnviKeyPressed(evt);
            }
        });
        getContentPane().add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, 90, 60));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTAChat);
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
        getContentPane().add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 500, -1, 60));

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
        getContentPane().add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(326, 560, 170, -1));

        jBVide.setBackground(new java.awt.Color(255, 255, 255));
        jBVide.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jBVide.setForeground(new java.awt.Color(0, 102, 0));
        jBVide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/vidlla.png"))); // NOI18N
        jBVide.setToolTipText("Realizar una Video Llamada con el Usuario");
        jBVide.setNextFocusableComponent(jBSal);
        jBVide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVideMouseExited(evt);
            }
        });
        jBVide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVideActionPerformed(evt);
            }
        });
        jBVide.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVideKeyPressed(evt);
            }
        });
        getContentPane().add(jBVide, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, 85, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        
    /*Carga los mensajes en la tabla*/
    private void vCarg()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene todos los mensajes de la base de datos*/
        try
        {
            sQ = "SELECT msj, estac, falt, id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND ((estacdestin = '" + Login.sUsrG + "' AND estac = '" + sEstacGlo.trim() + "') OR (estacdestin = '" + sEstacGlo.trim() + "' AND estac = '" + Login.sUsrG + "'))";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                
                /*Agrega en el control de los mensajes el mensaje concatenado con otros datos*/
                jTAMsj.setText(jTAMsj.getText() + rs.getString("falt") + ", " + Star.sSucu + " , " + rs.getString("estac") + ", " + Star.sNoCaj + ":" + System.getProperty( "line.separator" ) + rs.getString("msj").replace(System.getProperty( "line.separator" ), "") + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" ));                                                                                                                      
                
                /*Coloca el control al final del texto*/
                jTAMsj.setCaretPosition(jTAMsj.getText().length());                                
                                
                /*Actualiza el mensaje en la base de datos de que ya fue leido*/                
                vMarcMsj(rs.getString("id_id"), "!" + Login.sUsrG  + "!");                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
	
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCarg()*/
        

    /*Marca como que ese mensaje ya fue visto*/
    private void vMarcMsj(String sId, String sUsr)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Actualiza el mensaje que ya fue visto por esta usuario*/
        try 
        {                
            sQ = "UPDATE chat SET "
                    + "vist         = CONCAT_WS('', vist, '" + sUsr + "') "
                    + "WHERE id_id  =  " + sId;                    
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
        Star.iCierrBas(con);
        
    }/*Fin de private void vMarcMsj(String sId, String sUsr, Connection con)*/
                
                
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

    
    /*Cuando se presiona una tecla en el control de los mensajes*/
    private void jTAMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAMsjKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
    
    }//GEN-LAST:event_jTAMsjKeyPressed

    
    
    /*Cuando se presiona una tecla en el control del chat*/
    private void jTAChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAChatKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAChatKeyPressed

    
    /*Cuando se presiona una tecla en el botón de enviar*/
    private void jBEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEnviKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEnviKeyPressed

    
    /*Cuando se presiona el botón de enviar*/
    private void jBEnviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviActionPerformed
        
        /*Si esta vacio el mensaje entonces*/
        if(jTAChat.getText().compareTo("")==0 || jTAChat.getText().compareTo(System.getProperty( "line.separator" ))==0)
        {
            /*Coloca cadena vacia en el control del chat y coloca el cursor al principo del control*/
            jTAChat.setText("");
            jTAChat.setCaretPosition(0);
            
            /*Coloca el foco del teclado en el control de mensaje y regresa*/
            jTAChat.grabFocus();
            return;
        }            
        
        /*Obtiene la fecha y hora del sistema*/
        DateFormat dat      = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date da             = new Date();                                    
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;               
        String      sQ; 

        /*Ingresa en la base de datos el mensaje*/
        try 
        {                
            sQ = "INSERT INTO chat( estac,                                      msj,                                            sucu,                                     nocaj,                                falt,         vist,                                         estacdestin) " + 
                       "VALUES('" + Login.sUsrG.replace("'", "''") + "', '" +   jTAChat.getText().replace("'", "''") + "', '" + Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "',  now(),  '!" + Login.sUsrG.replace("'", "''") + "!', '" +    sEstacGlo.replace("'", "''").trim() + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            /*Agrega el mensaje en el control*/
            jTAMsj.append(dat.format(da) + ", " + Star.sSucu + ", " + Login.sUsrG + ", " + Star.sNoCaj + ": " + System.getProperty( "line.separator" ) + jTAChat.getText().replace(System.getProperty( "line.separator" ), "") + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" ));

            /*Pon el cursor del control de mensajes al final del control*/
            jTAMsj.setCaretPosition(jTAMsj.getText().length());
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

        /*Limpia el control de chat*/
        jTAChat.setText("");
        
    }//GEN-LAST:event_jBEnviActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/        
        dispose();                        
        
        /*Detiene los threads*/
        if(th!=null)
            th.interrupt();              
        if(thCon!=null)
            thCon.interrupt();              
        
    }//GEN-LAST:event_jBSalActionPerformed

        
    /*Cuando se esta cerrando la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
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
    private void jBEnviMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEnvi.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEnviMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBEnviMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEnvi.setBackground(colOri);
        
    }//GEN-LAST:event_jBEnviMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el botòn de video llamada*/
    private void jBVideKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVideKeyPressed
        
        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVideKeyPressed

    
    /*Cuando el mouse entra en el botòn de video*/
    private void jBVideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVideMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVide.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVideMouseEntered

    
    /*Cuando el mouse sale del botòn de video*/
    private void jBVideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVideMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVide.setBackground(colOri);
        
    }//GEN-LAST:event_jBVideMouseExited

    
    /*Cuando se presiona el botòn de video*/
    private void jBVideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVideActionPerformed

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el puerto del usuario
        String sPort    = Star.sGetPortUsr(con, sEstacGlo.trim());        
        
        //Si hubo error entonces regresa
        if(sPort==null)
            return;
        
        //Obtiene el host del usuario
        String sHost    = Star.sGetHostUsr(con, sEstacGlo.trim());
        
        //Si hubo error entonces regresa
        if(sHost==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si el usuario no esta conectado entonces*/
        if(sHost.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El usuario: " + sEstacGlo.trim() + " no esta conectado actualmente.", "Video Llamada", JOptionPane.INFORMATION_MESSAGE, null);                                                                                   
            return;
        }

        /*Si ya se tiene una ventana abierta de video llamada entonces*/
        if(Star.imgCa!=null)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Ya se tiene abierta una sesión de video llamada.", "Video Llamada", JOptionPane.INFORMATION_MESSAGE, null);                                                                                   
            return;
        }
        
        /*Muestra la forma para ver la càmara*/
        if(Star.imgCa==null)
        {
            Star.imgCa = new ImgCam(sEstacGlo);
            Star.imgCa.setVisible(true);
        }
        else
        {
            /*Si esta visible entonces solo vuelvela a hacer visible*/
            if(Star.imgCa.isVisible())            
                Star.imgCa.setVisible(true);            
        }
                            
    }//GEN-LAST:event_jBVideActionPerformed
   
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si fue enter entonces presiona el botón de enviar*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
            jBEnvi.doClick();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBEnvi;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBVide;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAChat;
    private javax.swing.JTextArea jTAMsj;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
