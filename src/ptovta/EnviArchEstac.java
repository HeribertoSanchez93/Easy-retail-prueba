//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.apache.commons.io.FileUtils;
import static ptovta.Princip.bIdle;




/*Clase para enviar archivos a los usuarios*/
public class EnviArchEstac extends javax.swing.JFrame 
{
    /*Thread para mostar los que están pendientes de enviar*/
    private final Thread        thEnvi;
    
    /*Contiene el color original del botón*/
    private final java.awt.Color colOri;
    
    /*Thread para mostar los que están pendientes de recibir*/
    private final Thread        thRecib;

    /*Bandera para saber cuando hay un error*/    
    private final boolean       bErr;

    /*Para que solo se abra una vez esta forma y no cargar threads*/
    private static EnviArchEstac obj = null;
    
    
    
        
    /*Constructor sin argumentos*/
    public EnviArchEstac() 
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
        this.setLocationRelativeTo(null);
        
        /*Inicialente no hay error*/
        bErr    = false;
        
        /*Se pueden seleccionar varios usuarios en la lista de pendientes por recibir*/
        jLiRecib.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Envio de archivos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        /*Pon el foco del teclado en la lista*/
        jLiEstac.grabFocus();

        /*Se pueden seleccionar varias usuarios en la lista*/
        jLiEstac.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                
        /*Carga todas las usuarios de la base de datos*/
        vCarg();
        
        /*Función para mostrar los pendientes de enviar*/
        vTEnvi();
                
        /*Función para mostrar los pendientes por recibir esto*/
        vTRecib();
                
        /*Inicia el thread para que monitorie todos los que están pendientes de aceptación*/
        thEnvi = new Thread()
        {
            @Override
            public void run()
            {
                /*Búcle infinito*/
                while(true)
                {
                    /*Duerme el thread 15 segundos*/
                    try
                    {
                        Thread.sleep(15000);
                    }
                    catch(InterruptedException expnInterru)
                    {
                        /*Sal del bucle*/
                        break;
                    }

                    /*Función para procesar esto*/
                    vTEnvi();
                }                                    
            }
        };
        thEnvi.start();
        
        /*Inicia el thread para que monitorie todos los que están pendientes de recibir*/
        thRecib = new Thread()
        {
            @Override
            public void run()
            {
                /*Búcle infinito*/
                while(true)
                {
                    /*Duerme el thread 15 segundos*/
                    try
                    {
                        Thread.sleep(15000);
                    }
                    catch(InterruptedException expnInterru)
                    {
                        /*Sal del bucle*/
                        break;
                    }

                    /*Función para procesar esto*/
                    vTRecib();
                }                                                                        
            }
        };
        thRecib.start();
        
    }/*Fin de public EnviArchEstac() */
        
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static EnviArchEstac getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new EnviArchEstac();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static EnviArchEstac getObj()*/
    
    
    /*Función para procesar esto*/
    private synchronized void vTEnvi()
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los items de la lista de pendientes por enviar*/          
        jLiEnvi.removeAll();            

        /*Objeto para cargar cadenas en el control de los pendientes por enviar*/
        DefaultListModel m = new DefaultListModel();                            

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 

        /*Obtiene las peticiones que están pendientes de enviar*/
        try
        {
            sQ = "SELECT estacdestin, id_id, path FROM petis WHERE estac = '" + Login.sUsrG + "' AND estad = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agrega los resultados al Modelo*/
            while(rs.next())
            {
                /*Si la ruta no existe entonces borra ese registro y continua*/
                if(!new File(rs.getString("path")).exists())
                {                    
                    vDelPet(rs.getString("id_id"), false);                        
                    continue;
                }
                
                /*Agrega el registro al control*/
                m.addElement("ID:" + rs.getString("id_id") + ": " + rs.getString("estacdestin") + " " + rs.getString("path"));               
            }                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                           
        }

        /*Agrega el Modelo al control*/
        jLiEnvi.setModel(m);                    
                
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vTEnvi()*/
                                
                
    /*Función para procesar esto*/
    private synchronized void vTRecib()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los items de la lista de pendientes por recibir*/            
        jLiRecib.removeAll();

        /*Objeto para cargar cadenas en el control de los recibidos*/
        DefaultListModel m = new DefaultListModel();                         

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene las peticiones que están pendientes de recibir*/
        try
        {
            sQ = "SELECT id_id, path, estac FROM petis WHERE estacdestin = '" + Login.sUsrG + "' AND estad = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos a la lista*/
            while(rs.next())
            {
                /*Si no existe la ruta entonces borra la petición y continua*/
                if(!new File(rs.getString("path")).exists())
                {                    
                    vDelPet(rs.getString("id_id"), false);                        
                    continue;
                }
                
                /*Agrega el registro en el control*/
                m.addElement("ID:" + rs.getString("id_id") + ": " + rs.getString("estac") + " " + rs.getString("path"));               
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                            
        }

        /*Agrega los registros al control*/
        jLiRecib.setModel(m);                    
        
    }/*Fin de private void vTRecib()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBEnvi = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLiEstac = new javax.swing.JList();
        jBBusc = new javax.swing.JButton();
        jTRut = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLiEnvi = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLiRecib = new javax.swing.JList();
        jBDen = new javax.swing.JButton();
        jBAcep = new javax.swing.JButton();
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
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jLiEnvi);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 120, 30));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEnvi.setForeground(new java.awt.Color(0, 102, 0));
        jBEnvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/envi.png"))); // NOI18N
        jBEnvi.setToolTipText("Enviar Petición de Archivo (Ctrl+S)");
        jBEnvi.setNextFocusableComponent(jBSal);
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
        jP1.add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 130, 30));

        jLiEstac.setNextFocusableComponent(jTRut);
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

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 250, 410));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setText("jButton1");
        jBBusc.setToolTipText("Buscar Ruta");
        jBBusc.setNextFocusableComponent(jBEnvi);
        jBBusc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscMouseExited(evt);
            }
        });
        jBBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscActionPerformed(evt);
            }
        });
        jBBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscKeyPressed(evt);
            }
        });
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, 30, 20));

        jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRut.setNextFocusableComponent(jBBusc);
        jTRut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRutFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRutFocusLost(evt);
            }
        });
        jTRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRutKeyPressed(evt);
            }
        });
        jP1.add(jTRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 220, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuarios:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 250, 20));

        jLiEnvi.setNextFocusableComponent(jLiRecib);
        jLiEnvi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLiEnviKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jLiEnvi);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 490, 210));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Por Descargar:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 490, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Por Enviar:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 490, 20));

        jScrollPane1.setNextFocusableComponent(jBAcep);

        jLiRecib.setNextFocusableComponent(jBAcep);
        jLiRecib.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLiRecibMouseClicked(evt);
            }
        });
        jLiRecib.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLiRecibKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jLiRecib);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 490, 200));

        jBDen.setBackground(new java.awt.Color(255, 255, 255));
        jBDen.setText("Denegar");
        jBDen.setNextFocusableComponent(jLiEstac);
        jBDen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDenMouseExited(evt);
            }
        });
        jBDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDenActionPerformed(evt);
            }
        });
        jBDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDenKeyPressed(evt);
            }
        });
        jP1.add(jBDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 460, 80, -1));

        jBAcep.setBackground(new java.awt.Color(255, 255, 255));
        jBAcep.setText("Aceptar");
        jBAcep.setNextFocusableComponent(jBDen);
        jBAcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAcepMouseExited(evt);
            }
        });
        jBAcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAcepActionPerformed(evt);
            }
        });
        jBAcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAcepKeyPressed(evt);
            }
        });
        jP1.add(jBAcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(586, 460, 80, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(586, 510, 170, 20));

        getContentPane().add(jP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 760, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga las usuarios existentes en la base de datos*/
    private void vCarg()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/       
        jLiEstac.setModel(new DefaultListModel());

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas las usuarios de la base de datos*/
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac <> '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la lista*/
                DefaultListModel m = (DefaultListModel)jLiEstac.getModel();        
                m.addElement(rs.getString("estac"));               
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                           
        }                        
        
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

        /*Detiene los threads de monitoreo*/
        thEnvi.interrupt();
        thRecib.interrupt();
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/        
        this.dispose();
        obj = null;
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de chatear*/
    private void jBEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEnviKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEnviKeyPressed

    
    /*Cuando se presiona una tecla en la lista de usuarios*/
    private void jLiEstacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLiEstacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jLiEstacKeyPressed

    
    /*Cuando se presiona el botón de enviar*/
    private void jBEnviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviActionPerformed
        
        /*Si no se a seleccionado por lo menos una usuario entonces*/
        if(jLiEstac.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una usuario para enviar petición(es).", "Enviar Petición(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jLiEstac.grabFocus();
            return;
        }
        
        /*Si no a ingresado una ruta entonces*/
        if(jTRut.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una ruta de archivo/carpeta a enviar.", "Enviar Petición(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRut.grabFocus();
            return;
        }            
        
        /*Si el archivo/carpeta no existe entonces*/
        if(!new File(jTRut.getText()).exists())
        {
            /*Coloca el borde rojo*/                               
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo/carpeta " + jTRut.getText() + " no existe. Ingresa un archivo/carpeta existente.", "Enviar Archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRut.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer enviar el archivo al(los) usuario(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres enviar petición(es)?", "Enviar Archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
        
        /*Thread para simular el envio de archivos o carpetas*/
        (new Thread()
        {
            @Override
            public void run()
            {                 
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(false, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Muestra el loading
                Star.vMostLoading("");
                
                /*Recorre toda la selección del usuario*/        
                int iSel[]              = jLiEstac.getSelectedIndices();                        
                for(int x = iSel.length - 1; x >= 0; x--)
                {   
                    /*Objeto para obtener listener de la lista de usuarios*/
                    DefaultListModel m  = (DefaultListModel)jLiEstac.getModel();                     

                    /*Copia el archivo origén al servidor e inserta la petición*/
                    vCopFil(con, m.getElementAt(iSel[x]).toString());                                    
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();
                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Carga las peticiones pendientes de enviar*/        
                vTEnvi();                        
                
                /*Mensajea de éxito*/
                if(!bErr)
                    JOptionPane.showMessageDialog(null, "Peticion(es) enviada(s) con éxito.", "Peticion(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                
            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jBEnviActionPerformed


    /*Copia el archivo origén al servidror*/
    private synchronized void vCopFil(Connection con, String sEstac)
    {
        //Declara variables de la base de datos
        Statement   st;       
        String      sQ; 
        
        
        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las compartidos no existe entonces crea la carpeta*/
        sCarp                    += "\\Compartidos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Completa la ruta a dode se irá*/
        String sNomAp           = new File(jTRut.getText()).getName();
        sCarp                   += "\\" + sNomAp;                                        
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;        
        
        /*Inserta la petición en la base de datos*/
        try 
        {                
            sQ = "INSERT INTO petis(    nomarch,                                path,                                                           estac,                                          estacdestin,                     val,           sucu,                                           nocaj,                                     estad) " + 
                         "VALUES('" +   sNomAp.replace("'", "''") + "', '" +    sCarp.replace("\\", "\\\\").replace("'", "''") + "', '" +       Login.sUsrG.replace("'", "''") + "', '" +   sEstac.replace("'", "''") + "',  0,  '" +       Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "', 0)";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                    
        }    	                                                
                
        /*Si es un archivo entonces*/
        if(new File(jTRut.getText()).isFile())
        {
            /*Copia el archivo orgien al servidor*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(jTRut.getText()), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                                    
            }
        }
        /*Else es un directorio*/
        else
        {
            /*Copia el carpeta orgien al servidor*/
            try
            {
                org.apache.commons.io.FileUtils.copyDirectory(new File(jTRut.getText()), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                                    
            }
        }                
        
        //Termina la transacción
        Star.iTermTransCon(con);
                
    }/*Fin de private void vCopFil(Connection con)*/
            
                        
    /*Cuando se hace click en la lista de usuarios*/
    private void jLiEstacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLiEstacMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de chatear*/
        if(evt.getClickCount() == 2) 
            jBEnvi.doClick();
        
    }//GEN-LAST:event_jLiEstacMouseClicked

    
    /*Cuando se gana el foco del teclado en el campo de la ruta del archivo a enviar*/
    private void jTRutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRut.setSelectionStart(0);jTRut.setSelectionEnd(jTRut.getText().length());                
        
    }//GEN-LAST:event_jTRutFocusGained

    
    /*Cuando se presiona una tecla en el botón de bùscar ruta de archivo a enviar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la ruta a enviar*/
    private void jTRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRutKeyPressed

    
    /*Cuando se presiona el botón de bùscar archivo*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar");
        fc.setAcceptAllFileFilterUsed               (false);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTRut.setText(sRut);
        }
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona una tecla en la lista de por enviar*/
    private void jLiEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLiEnviKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLiEnviKeyPressed

    
    
    /*Cuando se presiona una tecla en la lista de por recibir*/
    private void jLiRecibKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLiRecibKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLiRecibKeyPressed

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBAcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAcepKeyPressed

    
    /*Cuando se presiona una tecla en el botón de denegar*/
    private void jBDenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDenKeyPressed

    
    /*Cuando se presiona el botón de aceptar*/
    private void jBAcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAcepActionPerformed
        
        /*Si no se a seleccionado por lo menos un registro entonces*/
        if(jLiRecib.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una petición de recepción de archivo(s)/carpeta.", "Petición(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la lista y regresa*/
            jLiRecib.grabFocus();
            return;
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc                       = new JFileChooser  ();
        fc.setDialogTitle                           ("Guardar");
        fc.setAcceptAllFileFilterUsed               (false);
        fc.setFileSelectionMode                     (JFileChooser.DIRECTORIES_ONLY);

        /*Si el usuario presiono aceptar entonces obtiene la ruta, caso contrario regresa*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
                sRut            = fc.getSelectedFile().getAbsolutePath();   
        else
            return;
        
        /*Declara variables final para el thread*/
        final String   sRutFi   = sRut;
        
        /*Crea el thread para guardar los archivos*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Muestra el loading
                Star.vMostLoading("");
                
                /*Recorre toda la selección del usuario*/        
                int iSel[]              = jLiRecib.getSelectedIndices();                        
                for(int x = iSel.length - 1; x >= 0; x--)
                {            
                    /*Muestra la forma con el nombre de El usuario*/
                    DefaultListModel m  = (DefaultListModel)jLiRecib.getModel();            

                    /*Obtiene solo el id de la cadena*/
                    StringTokenizer sk = new StringTokenizer(m.getElementAt(iSel[x]).toString(),":");
                    sk.nextToken();
                    String sID                 = sk.nextToken();           
                    
                    /*Descarga el archivo/directorio en el disco local*/
                    vDescFil(sID, con, sRutFi);                        
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();
                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Función para mostrar los pendientes por recibir*/
                vTRecib();

                /*Mensajea de éxito*/
                if(!bErr)
                    JOptionPane.showMessageDialog(null, "Descarga exitosa.", "Descarga con éxito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
                
            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jBAcepActionPerformed

    
    /*Descarga el archivo/directorio en el disco local*/
    private void vDescFil(String sID, Connection con, String sRutDestin)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }

        //Contiene el path y el nombre de la aplicación
        String sPath    = "";
        String sNomAp   = "";
        
        /*Obtiene algunos datos de la tabla de peticiones*/        
        try
        {
            sQ = "SELECT path, nomarch FROM petis WHERE id_id = " + sID;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                sNomAp       = rs.getString("nomarch");                                
                sPath        = rs.getString("path");                                
            }                        
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                        
        }
        
        /*Si la carpeta de las compartidos no existe entonces crea la carpeta*/
        sCarp                    += "\\Compartidos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Completa la ruta de destino*/
        sRutDestin              += "\\" + sNomAp;
                
        /*Si es un archivo entonces*/
        if(new File(sPath).isFile())
        {         
            /*Copia el archivo orgien al servidor*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sPath), new File(sRutDestin));
            }
            catch(IOException expnIO)
            {        
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                        
            }
        }
        /*Else es un directorio*/
        else
        {                       
            /*Copia el carpeta orgien al servidor*/
            try
            {
                org.apache.commons.io.FileUtils.copyDirectory(new File(sPath), new File(sRutDestin));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                        
            }
        }                

        /*Comprueba si hay mas peticiones para ese archivo o carpeta para no borrarlo*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT id_id FROM petis WHERE path = '" + sPath.replace("\\", "\\\\") + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si hay mas peticiones, entonces coloca la bandera*/
            if(rs.next())
                bSi = true;                       
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                        
        }
        
        /*Si no hay mas peticiones entonces*/
        if(!bSi)
        {
            /*Si es un archivo entonces borra el archivo*/
            if(new File(sPath).isFile())
                new File(sPath).delete();
            /*Else es un directorio entonces*/
            else
            {
                /*Borra la carpeta compartido en la red*/                        
                try
                {
                    /*Borra el contenido de toda la carpeta*/
                    FileUtils.cleanDirectory(new File(sPath));                                             

                    /*Borra el directorio en si ya vacio*/
                    new File(sPath).delete();
                }
                catch(IOException expnIO)
                {       
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                    return;                                        
                }                
            }                                
        }
        
        /*Borra la petición de la base de datos*/
        vDelPet(sID, false);                        
            
    }/*Fin de private void vDescFil(String sID, Connection con)*/
            
            
    /*Cuando se hace clic en el control de los pendientes por recibir*/
    private void jLiRecibMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLiRecibMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de modificar*/
        if(evt.getClickCount() == 2) 
            jBAcep.doClick();
        
    }//GEN-LAST:event_jLiRecibMouseClicked

    
    /*Cuando se presiona el botón de denegar*/
    private void jBDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDenActionPerformed
        
        /*Si no se a seleccionado por lo menos un registro entonces*/
        if(jLiRecib.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una petición de recepción de archivo(s)/carpeta.", "Petición(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la lista y regresa*/
            jLiRecib.grabFocus();
            return;
        }       
        
        /*Recorre toda la selección del usuario*/        
        int iSel[]              = jLiRecib.getSelectedIndices();                        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            /*Muestra la forma con el nombre de El usuario*/
            DefaultListModel m  = (DefaultListModel)jLiRecib.getModel();            
                        
            /*Obtiene solo el id de la cadena*/
            StringTokenizer sk = new StringTokenizer(m.getElementAt(iSel[x]).toString(),":");
            sk.nextToken();
            String sID                 = sk.nextToken();
                                                
            /*Borra la petición de la base de datos*/
            vDelPet(sID, true);
        }                        

        /*Función para mostrar los pendientes por recibir*/
        vTRecib();

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Petición(es) negada(s) con éxito.", "Petición(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDenActionPerformed

    
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

    
    /*Cuando se pierde el foco del teclado en el control de la ruta*/
    private void jTRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTRut.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTRut.getText().compareTo("")!=0)
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTRutFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAcepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAcep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAcepMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/    
    private void jBDenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDenMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDen.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDenMouseEntered

    
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAcepMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAcep.setBackground(colOri);
        
    }//GEN-LAST:event_jBAcepMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDenMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDen.setBackground(colOri);
        
    }//GEN-LAST:event_jBDenMouseExited

    
    /*Borra la petición estas peticiones*/
    private synchronized void vDelPet(String sID, boolean bDel)
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

        /*Obtiene la ruta para borrar los archivos en el servidor*/
        String sPath    = "";
        if(bDel)
        {
            try
            {
                sQ = "SELECT path FROM petis WHERE id_id = " + sID;
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sPath      = rs.getString("path");                                   
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
            }
        }            
        
        /*Borra la petición*/
        try 
        {                
            sQ = "DELETE FROM petis WHERE id_id = " + sID;                    
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

        /*Si se tienen que borrar los archivos en el servidor entonces*/
        if(bDel)
        {
            /*Si es archivo entonces borralo*/
            if(new File(sPath).isFile())
                new File(sPath).delete();
            /*Else es archivo entonces borralo del servidor*/
            else
            {                                
                try
                {
                    /*Borra el contenido de ordenes*/
                    FileUtils.cleanDirectory(new File(sPath));                                             
                    
                    /*Borra el directorio de ordenes*/
                    new File(sPath).delete();                                        
                }
                catch(IOException expnIO)
                {                       
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                          
                }                
            }
            
        }
        
    }/*Fin de private void vDelPet(String sID)*/
            
            
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBEnvi.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAcep;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDen;
    private javax.swing.JButton jBEnvi;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jLiEnvi;
    private javax.swing.JList jLiEstac;
    private javax.swing.JList jLiRecib;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTRut;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
