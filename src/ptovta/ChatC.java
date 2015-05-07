//Paquete
package ptovta;

//Importaciones
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static ptovta.Princip.bIdle;




/*Clase para chatear corporativamente*/
public class ChatC extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Thread para estar mostrando la información en el control de mensajes*/
    private Thread              th;
           
    /*Para validar que la forma no se abra dos veces*/
    private static ChatC        obj = null;
    
    
    
    
    /*Constructor sin argumentos*/
    public ChatC(boolean bEntra) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEnvi);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Si esta verificando si ya esta abierta entonces regresa*/
        if(!bEntra)
            return;
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Chat Corporativo, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
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

        /*Obtiene todos los mensajes para acutalizar el campo de vistos*/
        try
        {
            sQ = "SELECT IFNULL(vist, '' ) AS vist, id_id FROM chat WHERE estacdestin = '' AND vist LIKE('%!" + Login.sUsrG + "!%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Actualiza el registro para saber que esta usuario no a visto ese mensaje*/                
                try 
                {                
                    sQ2= "UPDATE chat SET "
                            + "vist         = '" + rs.getString("vist").replace("!" + Login.sUsrG + "!", "") + "' "
                            + "WHERE id_id  = " + rs.getString("id_id");                                        
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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 560, 60));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jBEnvi.setForeground(new java.awt.Color(0, 102, 0));
        jBEnvi.setText("Enviar");
        jBEnvi.setNextFocusableComponent(jTAMsj);
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
        getContentPane().add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 500, 140, 60));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("file:/C:/Users/Hp/Desktop/CARPETA DE TRABAJO/Proyectos/Net Beans/Proyecto Punto de Venta/Electricos/src/imgs/sal.png")
                    );
                } catch(java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir");
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
        getContentPane().add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 500, 100, 60));

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
        getContentPane().add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(566, 560, 230, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Metodo para que el formulario no se abra dos veces*/
    public static ChatC getObj(boolean bEntra)
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new ChatC(bEntra);
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Login getObj()*/
    
    
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
            sQ = "SELECT msj, estac, falt, id_id FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin = ''";                        
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
            sQ = "INSERT INTO chat(estac,                                           msj,                                                                                                                                    sucu,                                           nocaj,                                          falt,           vist) " + 
                       "VALUES('" + Login.sUsrG.replace("'", "''") + "', '" +   jTAChat.getText().substring(0, jTAChat.getText().length() > 1000 ? 1000: jTAChat.getText().length()).replace("'", "''") + "', '" +      Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "',       now(),  '!" + Login.sUsrG.replace("'", "''") + "!')";                    
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
        
        /*Cierra la forma y detiene el thread*/        
        th.interrupt();
        obj = null;
        dispose();                        
        
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
    private javax.swing.JLabel jLAyu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAChat;
    private javax.swing.JTextArea jTAMsj;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
