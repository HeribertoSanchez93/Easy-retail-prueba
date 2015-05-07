//Paquete
package report;

//Importaciones
import java.awt.Cursor;
import ptovta.Star;
import ptovta.Login;
import ptovta.Busc;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;




/*Clase para reportear a los clientes*/
public class RepCliens extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    
    
    
    
    /*Constructor sin argumentos*/
    public RepCliens() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBAbr);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Reporte clientes, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Oculta el campo de serie*/
        jTSer.setVisible(false);
        
        /*Pon el foco del teclado en el combo de los tipos de reportes*/
        jComRep.grabFocus();
                
    }/*Fin de public RepCliens() */
        
            
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jBAbr = new javax.swing.JButton();
        jBCli = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jComRep = new javax.swing.JComboBox();
        jLAyu = new javax.swing.JLabel();
        jBJera = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();

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

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jComRep);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 110, 30));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBCli);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCliFocusLost(evt);
            }
        });
        jTCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCliKeyTyped(evt);
            }
        });
        jP1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 170, 20));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbr.setForeground(new java.awt.Color(0, 102, 0));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr.png"))); // NOI18N
        jBAbr.setText("Abrir");
        jBAbr.setToolTipText("Abrir Reporte (Ctrl+A)");
        jBAbr.setNextFocusableComponent(jBSal);
        jBAbr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbrMouseExited(evt);
            }
        });
        jBAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrActionPerformed(evt);
            }
        });
        jBAbr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbrKeyPressed(evt);
            }
        });
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 110, 30));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente");
        jBCli.setNextFocusableComponent(jBAbr);
        jBCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCliMouseExited(evt);
            }
        });
        jBCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCliActionPerformed(evt);
            }
        });
        jBCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCliKeyPressed(evt);
            }
        });
        jP1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 30, 20));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, -1));

        jComRep.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depósitos en garantía", "Catálogo de clientes" }));
        jComRep.setNextFocusableComponent(jTJera);
        jComRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComRepKeyPressed(evt);
            }
        });
        jP1.add(jComRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 280, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 110, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar Jerárquia(s)");
        jBJera.setNextFocusableComponent(jTCli);
        jBJera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJeraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJeraMouseExited(evt);
            }
        });
        jBJera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJeraActionPerformed(evt);
            }
        });
        jBJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBJeraKeyPressed(evt);
            }
        });
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 30, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Jerarquía:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 130, -1));

        jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTJera.setNextFocusableComponent(jBJera);
        jTJera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTJeraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTJeraFocusLost(evt);
            }
        });
        jTJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTJeraKeyPressed(evt);
            }
        });
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 250, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
       
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

       
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cerrar el formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el control del cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());        
        
    }//GEN-LAST:event_jTCliFocusGained
            
        
    /*Cuando se tipea una tecla en el campo del cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si el cliente existe*/        
        try
        {
            sQ = "SELECT codemp FROM emps WHERE codemp = '" + jTCli.getText().replace(jTSer.getText(), "") + "' AND ser = '" + jTSer.getText() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(!rs.next() && jTCli.getText().replace(jTSer.getText(), "").compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTCli.grabFocus();             
                return;     
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                    
        }            

        /*Comprueba si la clasificación de la jerarquía existe*/        
        try
        {
            sQ = "SELECT rut FROM clasjeracli WHERE rut = '" + jTJera.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next() && jTJera.getText().trim().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La jerarquía: " + jTJera.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTJera.grabFocus();             
                return;     
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                    
        }            
        
        /*Determina el reporte que será*/
        int iRep    = 0;
        if(jComRep.getSelectedItem().toString().compareTo("Depósitos en garantía")==0)
            iRep    = 1;                                                
        else if(jComRep.getSelectedItem().toString().compareTo("Catálogo de clientes")==0)
            iRep    = 2;
        
        /*Declara variables final para el thread*/
        final String sEmpFi          = jTCli.getText().trim();
        final String sJeraFi         = jTJera.getText().trim();
        final int    iRepFi          = iRep;
                
        /*Crea el th para cargar el reporte en un hilo aparte*/
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

                /*Obtiene la fecha de hoy*/               
                DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dat        = new Date();                

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();             
                    pa.clear();
                    pa.put("CLI",           sEmpFi);
                    pa.put("JERA",          sJeraFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("FECH",          da.format(dat));                                        
                    
                    /*Si es el reporte de depósitos en garantías entonces*/                    
                    JasperReport ja = null;
                    if(iRepFi==1)                                            
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptDepGara.jrxml"));
                    /*Else if el reporte será de catálogo de clientes*/
                    else if(iRepFi==2)                                                                                                            
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptEmps.jrxml"));                                        
                                                
                    /*Llenalo y muestralo*/
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH); 
                           /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte de Clientes");
                    v.setIconImage(newimg);

                    v.setVisible(true);

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                }
                catch(JRException expnJASR)
                {                    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                    return;                                                                                                                    
                }

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void run()*/
        }).start();
        
        //Muestra el loading
        Star.vMostLoading("");
                    
    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAbrKeyPressed
        
    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se presiona el botón de buscar vendedor*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBCliActionPerformed
        
    
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

         
    /*Cuando se presiona una tecla en el combo de los tipos de reportes*/
    private void jComRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jComRepKeyPressed
                  
       
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

        
    /*Cuando se pierde el foco del teclado en el campo de la cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTCli.getText().compareTo("")!=0)
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered
            
    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(colOri);
        
    }//GEN-LAST:event_jBCliMouseExited

    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);
    }//GEN-LAST:event_jBJeraMouseEntered

    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(colOri);
    }//GEN-LAST:event_jBJeraMouseExited

    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis v = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "cli");
        v.setVisible(true);
    }//GEN-LAST:event_jBJeraActionPerformed

    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jBJeraKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());
        
    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTJeraKeyPressed
                      
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jComRep;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTSer;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
