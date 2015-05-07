//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Cursor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Clase para poder chatear con otros usuarios*/
public class ChatUsr extends javax.swing.JFrame 
{    
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Thread que estará cargando las usuarios para saber cuál tiene mensajes pendientes*/
    private final Thread        th;
        
    /*Contiene el arreglo de los usuarios que están pendientes de que esta usuario vea los mensajes*/
    private String              sEstacP[];
    
    
    
    
    
    /*Constructor sin argumentos*/
    public ChatUsr() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBChat);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Chat, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en la lista*/
        jLiEstac.grabFocus();       
        
        /*Listener para las filas de la lista, para saber que usuarios tienen pendientes chats*/
        jLiEstac.setCellRenderer(new javax.swing.ListCellRenderer() 
        {
            /*Objeto para renderizar el control*/
            javax.swing.DefaultListCellRenderer def = new javax.swing.DefaultListCellRenderer();            
            
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
            {                                   
                /*Con este objeto colocamos el color de fondo*/
                javax.swing.JLabel re = (javax.swing.JLabel) def.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);                
                                             
                /*Si se seleccionó la fila entonces*/
                if(isSelected)
                {
                    /*Coloca color de selección y regresa*/                    
                    re.setBackground(Color.LIGHT_GRAY);                    
                    return re; 
                }
                
                /*Si el elemento que se va a insertar coincide con uno de los que estamos pendientes de recibir mensajes entonces*/
                if(java.util.Arrays.asList(sEstacP).contains(value.toString()))
                {
                    /*Coloca amarillo y regresa*/                    
                    re.setBackground(Color.ORANGE);                    
                    return re; 
                }
                
                /*Coloca blanco y regresa*/
                re.setBackground(Color.WHITE);                                                
                return re;                                                 
            }
    	});                                               
        
        /*Carga las bases de datos nuevamente en el control*/
        vCarg();                    
                    
        /*Inicia el thread para que cargue las usuarios nuevamente en el control*/
        th = new Thread()
        {
            @Override
            public void run()
            {   
                while(true)
                {                    
                    /*Duerme el thread unos segundo*/
                    try
                    {
                        Thread.sleep(10000);
                    }
                    catch(InterruptedException expnInterru)
                    {
                        /*Sal del bucle*/
                        break;
                    }                                      
                    
                    /*Carga las bases de datos nuevamente en el control*/
                    vCarg();                    
                }                    
            }
        };  
        th.start();                
        
    }/*Fin de public Pesos() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBChat = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLiEstac = new javax.swing.JList();
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

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Usuarios:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 250, 20));

        jBChat.setBackground(new java.awt.Color(255, 255, 255));
        jBChat.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBChat.setForeground(new java.awt.Color(0, 102, 0));
        jBChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/chat.png"))); // NOI18N
        jBChat.setText("Chatear");
        jBChat.setToolTipText("Chatear con Usuario(s)");
        jBChat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBChat.setNextFocusableComponent(jBSal);
        jBChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBChatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBChatMouseExited(evt);
            }
        });
        jBChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChatActionPerformed(evt);
            }
        });
        jBChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBChatKeyPressed(evt);
            }
        });
        jP1.add(jBChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 110, 30));

        jLiEstac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLiEstacMouseClicked(evt);
            }
        });
        jLiEstac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLiEstacKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jLiEstac);

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 250, 340));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 360, 110, -1));

        getContentPane().add(jP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 379, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        
    /*Carga las usuarios existentes en la base de datos*/
    private synchronized void vCarg()
    {
        
        /*Obtiene los índices que estaban seleccionados anteriormente*/
        int iInd[]   = jLiEstac.getSelectedIndices();                
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene el contador de mensajes pendientes que no a visto este usuario*/
        int iCoun   = 0;
        try
        {
            sQ = "SELECT COUNT(id_id) count FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                        
                iCoun      = rs.getInt("count");                                                              
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                
        
        /*Crea el objeto con el nuevo tamaño*/
        sEstacP = new String[iCoun];
        
        /*Comprueba si existen mensajes pendientes para esta usuario*/
        try
        {
            sQ = "SELECT estac FROM chat WHERE vist NOT LIKE('%!"  + Login.sUsrG + "!" + "%') AND estacdestin = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            int x = 0;
            while(rs.next())
            {
                /*Ve creando el arreglo*/
                sEstacP[x]      = rs.getString("estac");                                   
                
                /*Ve aumentando el contador de las filas*/
                ++x;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                
        
        /*Borra todos los items en el control*/
        jLiEstac.removeAll();
        
        /*Crea el modelo para cargar cadenas en el*/       
        jLiEstac.setModel(new DefaultListModel());
        
        /*Trae todas las usuarios de la base de datos*/
        try
        {
            sQ = "SELECT con, (SELECT port FROM logestac WHERE logestac.ESTAC = con) AS cone FROM (SELECT estac AS con FROM estacs WHERE estac <> '" + Login.sUsrG + "')a";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene si esta conctado o no*/
                String sCone    = rs.getString("cone");
                
                /*Si es nulo entonces colocalo offline, caso contrario online*/
                if(sCone==null || sCone.compareTo("")==0)
                    sCone       = "<Offline>";
                else
                    sCone       = "<Online>";
                
                /*Agregalo a la lista*/
                DefaultListModel m = (DefaultListModel)jLiEstac.getModel();        
                m.addElement(sCone + " " + rs.getString("con"));                               
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

        /*Selecciona nuevamente los índices que estaban seleccionados*/        
        jLiEstac.setSelectedIndices(iInd);
        
    }/*Fin de private void vCarg()*/
    
        
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

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

        
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma y detén el thread*/        
        this.dispose();
        th.interrupt();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de chatear*/
    private void jBChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBChatKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBChatKeyPressed

    
    /*Cuando se presiona una tecla en la lista de usuarios*/
    private void jLiEstacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLiEstacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jLiEstacKeyPressed

    
    /*Cuando se presiona el botón de chat*/
    private void jBChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChatActionPerformed
        
        /*Si no se a seleccionado por lo menos una usuario entonces*/
        if(jLiEstac.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un usuario para chatear.", "Chat", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jLiEstac.grabFocus();
            return;
        }
      
        /*Recorre toda la selección del usuario*/        
        int iSel[]              = jLiEstac.getSelectedIndices();                        
        for(int x = iSel.length - 1; x >= 0; x--)
        {           
            /*Obtiene el Modelo*/
            DefaultListModel m  = (DefaultListModel)jLiEstac.getModel();
            
            /*Tokeniza para quitar la cadena de conectado*/
            java.util.StringTokenizer stk = new java.util.StringTokenizer(m.getElementAt(iSel[x]).toString(),">");
            
            /*Obtiene el nombre del usuario*/
            stk.nextToken();
            String sUsr        = stk.nextToken();
            
            /*Muestra la forma con el nombre de el usuario*/            
            ChatP p = new ChatP(false, sUsr);
            p.setVisible(true);
        }
                
    }//GEN-LAST:event_jBChatActionPerformed

    
    /*Cuando se hace click en la lista de usuarios*/
    private void jLiEstacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLiEstacMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de chatear*/
        if(evt.getClickCount() == 2) 
            jBChat.doClick();
        
    }//GEN-LAST:event_jLiEstacMouseClicked

    
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBChatMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBChat.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBChatMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBChatMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBChat.setBackground(colOri);
        
    }//GEN-LAST:event_jBChatMouseExited

    
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
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBChat;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jLiEstac;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
