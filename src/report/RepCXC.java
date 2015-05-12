//Paquete
package report;

//Importaciones
import ptovta.Login;
import java.awt.Cursor;
import ptovta.Star;
import ptovta.Busc;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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




/*Clase para controlar el reporteador de CXC*/
public class RepCXC extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Bandera para seleccionar todos los check*/
    private boolean                 bSel    = false;
    
    
    
    
    /*Constructor sin argumentos*/
    public RepCXC()
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
        this.setTitle("Reporte CXC, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFIni = new Date();
        jDDe.setDate (fFIni);
        jDA.setDate  (fFIni);
        
        /*Pon el foco del teclado en el campo del cliente*/
        jTCli.grabFocus();

        /*Esconde el control de la serie*/
        jTSer.setVisible(false);
        
    }/*Fin de public RepCXC()*/
               
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jCCo = new javax.swing.JCheckBox();
        jCPe = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();
        jCDesglo = new javax.swing.JCheckBox();
        jCVen = new javax.swing.JCheckBox();
        jComTip = new javax.swing.JComboBox();
        jBSelT = new javax.swing.JButton();
        jCCa = new javax.swing.JCheckBox();

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
        jBSal.setNextFocusableComponent(jComTip);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 110, 30));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBBusc);
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
        jP1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 170, -1));

        jDDe.setEnabled(false);
        jDDe.setNextFocusableComponent(jDDe);
        jDDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDDeKeyPressed(evt);
            }
        });
        jP1.add(jDDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 150, -1));

        jDA.setEnabled(false);
        jDA.setNextFocusableComponent(jTCli);
        jDA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDAKeyPressed(evt);
            }
        });
        jP1.add(jDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("De:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 150, -1));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setNextFocusableComponent(jCPe);
        jCTFech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCTFechActionPerformed(evt);
            }
        });
        jCTFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCTFechKeyPressed(evt);
            }
        });
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 130, -1));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 110, 30));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setText("...");
        jBBusc.setToolTipText("Buscar Cliente");
        jBBusc.setNextFocusableComponent(jBAbr);
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 30, 20));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 40, -1));

        jCCo.setBackground(new java.awt.Color(255, 255, 255));
        jCCo.setText("CO");
        jCCo.setToolTipText("Confirmados");
        jCCo.setNextFocusableComponent(jCCa);
        jCCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCoKeyPressed(evt);
            }
        });
        jP1.add(jCCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, -1, -1));

        jCPe.setBackground(new java.awt.Color(255, 255, 255));
        jCPe.setSelected(true);
        jCPe.setText("PE");
        jCPe.setToolTipText("Pendientes");
        jCPe.setNextFocusableComponent(jCCo);
        jCPe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPeKeyPressed(evt);
            }
        });
        jP1.add(jCPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 110, 30));

        jCDesglo.setBackground(new java.awt.Color(255, 255, 204));
        jCDesglo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDesglo.setText("Desglosado");
        jCDesglo.setToolTipText("Desgloza el Reporte Generado Mostrando las Partidas");
        jCDesglo.setNextFocusableComponent(jDDe);
        jCDesglo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDesgloKeyPressed(evt);
            }
        });
        jP1.add(jCDesglo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 90, -1));

        jCVen.setBackground(new java.awt.Color(255, 255, 255));
        jCVen.setText("Vencido");
        jCVen.setNextFocusableComponent(jCDesglo);
        jCVen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVenKeyPressed(evt);
            }
        });
        jP1.add(jCVen, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 80, -1));

        jComTip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Encabezados", "Antiguedad de saldos", "Abonos por conceptos" }));
        jComTip.setNextFocusableComponent(jCTFech);
        jComTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComTipActionPerformed(evt);
            }
        });
        jComTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTipKeyPressed(evt);
            }
        });
        jP1.add(jComTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 280, -1));

        jBSelT.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT.setToolTipText("Seleccionar/DeseleccionarTodos los Estados de CXC");
        jBSelT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jBSelT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSelTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSelTMouseExited(evt);
            }
        });
        jBSelT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelTActionPerformed(evt);
            }
        });
        jBSelT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSelTKeyPressed(evt);
            }
        });
        jP1.add(jBSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 20, 10));

        jCCa.setBackground(new java.awt.Color(255, 255, 255));
        jCCa.setText("CA");
        jCCa.setToolTipText("Cancelado");
        jCCa.setNextFocusableComponent(jCVen);
        jCCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCaKeyPressed(evt);
            }
        });
        jP1.add(jCCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 50, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
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

    
    /*Cuando se presiona una tecla en el campo de clientes*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de cliente*/
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
            sQ = "SELECT codemp FROM emps WHERE 1=1 AND codemp = '" + jTCli.getText().replace(jTSer.getText(), "") + "' AND ser= '" + jTSer.getText().trim() + "'";                       
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(!rs.next()&& jTCli.getText().replace(jTSer.getText(), "").compareTo("")!=0)
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
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Lee la fecha a*/
        Date f                      =  jDA.getDate();
        SimpleDateFormat sdf        = new SimpleDateFormat("yyy-MM-dd");
        String sFA                  = sdf.format(f);   
        
        /*Lee la fecha de*/
        f                           =  jDDe.getDate();
        sdf                         = new SimpleDateFormat("yyy-MM-dd");
        String sFDe                 = sdf.format(f);   
            
        /*Si el checkbox de todas las fechas no esta marcado entonces*/
        if(!jCTFech.isSelected())
        {
            /*Si la fecha a esta vacia entonces*/
            if(sFA.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una fecha.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jDA.grabFocus();                
                return;
            }
            
            /*Si la fecha a esta vacia entonces*/
            if(sFA.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una fecha.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jDA.grabFocus();                
                return;
            }
                        
        }/*Fin de if(!jCheckBoxTodasLasFechas.isSelected())*/   
        /*Else, esta seleccionado todas las fechas entonces establece las fechas en cadena vacia*/
        else
        {            
            sFA     = "";
            sFDe    = "";
        }
            
        /*Si no esta seleccionado por lo menos confirmados, pendientes o vencidos entonces*/
        if(!jCCa.isSelected() && !jCCo.isSelected() && !jCPe.isSelected() && !jCVen.isSelected() && jComTip.getSelectedItem().toString().compareTo("Abonos por conceptos")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos pendientes, confirmados o vencidos.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jCPe.grabFocus();                
            return;
        }
        //Else if el reporte será de abonos por concepto entonces
        else if(jComTip.getSelectedItem().toString().compareTo("Abonos por conceptos")==0)
        {
            //Si no esta seleccionado por lo menos algo valido entonces
            if(!jCCa.isSelected() && !jCCo.isSelected())
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Selecciona por lo menos confirmados o cancelados.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jCCo.grabFocus();                
                return;
            }
        }
        
        /*Determina el estado de los documentos*/        
        String sEst;        
        if(jCCo.isSelected() && jCPe.isSelected() && jCVen.isSelected() && jCCa.isSelected())
            sEst         = "1";
        else if(jCCo.isSelected() && jCPe.isSelected()&& jCCa.isSelected())
            sEst="8";
        else if(jCCo.isSelected() && jCVen.isSelected()&& jCCa.isSelected())
            sEst="9";
        else if(jCPe.isSelected() && jCVen.isSelected()&& jCCa.isSelected())
            sEst         = "10";
        else if(jCCo.isSelected() && jCPe.isSelected())
            sEst         = "2";               
        else if(jCPe.isSelected() && jCVen.isSelected())
            sEst         = "3";
        else if(jCCo.isSelected() && jCVen.isSelected())
            sEst         = "4";
        else if(jCCo.isSelected()&& jCCa.isSelected())
            sEst         = "11";
        else if(jCPe.isSelected()&& jCCa.isSelected())
            sEst         = "12";
        else if(jCVen.isSelected()&& jCCa.isSelected())
            sEst         = "13";
        else if(jCCo.isSelected())
            sEst         = "5";
        else if(jCPe.isSelected())
            sEst         = "6";
        else if(jCVen.isSelected())
            sEst         = "7";
        else if(jCCa.isSelected())
            sEst         = "14";
        else
            sEst         ="";
        /*Determina el reporte que será*/
        String sTipRep  = "";
        if(jComTip.getSelectedItem().toString().compareTo("Encabezados")==0)
        {
            /*Determina si será detallado o no*/        
            if(jCDesglo.isSelected())
                sTipRep = "/jasreport/rptCXCTDet.jrxml";
            else
                sTipRep = "/jasreport/rptCXCT.jrxml";
        }                        
        /*Else si es de antiguedad de saldos entonces*/
        else if(jComTip.getSelectedItem().toString().compareTo("Antiguedad de saldos")==0)        
        {
            /*El reporte será este*/
            sTipRep = "/jasreport/rptCXCAnti.jrxml";                    
            
            /*Este parámetro tendrá la fecha de hoy*/                        
            java.text.DateFormat da = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dat                = new Date();
            sFDe                    = da.format(dat);
        }
        /*Else si es de antiguedad de saldos entonces*/
        else if(jComTip.getSelectedItem().toString().compareTo("Abonos por conceptos")==0)        
        {
            //Si no a definido un cliente entonces
            if(jTCli.getText().trim().compareTo("")==0)
            {
                //Coloca el borde rojo del campo del cliente
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                //Mensajea
                JOptionPane.showMessageDialog(null, "Ingresa un cliente.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Pon el foco del teclado en el campo del cliente y regresa
                jTCli.grabFocus();
                return;
                
            }
            
            //Comprueba si el cliente existe
            int iRes    = Star.iExistCli(null, jTCli.getText().trim());
            
            //Si hubo error regresa
            if(iRes==-1)
                return;
            
            //Si el cliente no existe entonces
            if(iRes==0)
            {                
                //Mensajea
                JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " no existe.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
             
                //Coloca el borde rojo
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                                
                //Pon el foco del teclado en el campo del cliente y regresa
                jTCli.grabFocus();
                return;
            }
            
            //Establece que el reporte será este
            sTipRep = "/jasreport/rptAbonConcep.jrxml";                            
        }
        
        //Determina el estado del documento para abonos de conceptos 
        String sEstAbon      = "";
        String check         = "";
        if(jCCo.isSelected() && jCCa.isSelected())
        {
            sEstAbon         = "1";
            check            ="CO y CA";
        }
        else if(jCCo.isSelected())
        {
            sEstAbon         = "2";
            check            ="CO";
        }
        else if(jCCa.isSelected())
        {
            sEstAbon         = "3";
            check            ="CA";
        }
        
        /*Declara variables final para el thread*/
        final String sEstAbonFi     = sEstAbon;
        final String sEstFi         = sEst;        
        final String sFAFi          = sFA;
        final String sFDeFi         = sFDe;                             
        final String sTipRepFi      = sTipRep;                             
        final String sEmpFi         = jTCli.getText();
        final String sCheckFi         =check;
        
        /*Crea el nth para cargar el reporte en un hilo aparte*/
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

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();             
                    pa.clear();
                    pa.put("EMP",           sEmpFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);                    
                    pa.put("ESTADABON",     sEstAbonFi);
                    pa.put("ESTAD",         sEstFi);                    
                    pa.put("CHECK",         sCheckFi);
                    /*Compila el reporte y muestralo maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream(sTipRepFi));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);  
                           /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte CXC");
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

    
    /*Cuando se presiona una tecla en el campo de feche de*/
    private void jDDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDDeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDDeKeyPressed

    
    /*Cuando se presiona una tecla en el campo de fecha a*/
    private void jDAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDAKeyPressed

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona un botón en el checkbox de todas las fechas*/
    private void jCTFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCTFechKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCTFechKeyPressed

    
    /*Cuando sucede una acción en el checkbox de todas las fechas*/
    private void jCTFechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCTFechActionPerformed
        
        /*Si esta marcado entonces desactiva los dos controles de fecha*/
        if(jCTFech.isSelected())
        {
            jDDe.setEnabled  (false);
            jDA.setEnabled   (false);
        }
        /*Else, activa los controles de fecha*/
        else
        {
            jDDe.setEnabled  (true);
            jDA.setEnabled   (true);
            
            /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
            jDDe.getDateEditor().setEnabled  (false);
            jDA.getDateEditor().setEnabled   (false);           
        }
        
    }//GEN-LAST:event_jCTFechActionPerformed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de pendientes*/
    private void jCPeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPeKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de confirmados*/
    private void jCCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCoKeyPressed

    
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
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBAbrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        // TODO add your handling code here:
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando se presiona una tecla en el check de desglozado*/
    private void jCDesgloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDesgloKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDesgloKeyPressed

    
    /*Cuando se presiona una tecla en el check de los vencidos*/
    private void jCVenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCVenKeyPressed

    
    /*Cuando se presiona una tecla en el combo de tipo de reporte*/
    private void jComTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTipKeyPressed

    
    /*Cuando sucede una acción en el combo del tipo de reporte*/
    private void jComTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTipActionPerformed
        
        /*Si el seleccionado es el de antiguedad de saldos entonces*/
        if(jComTip.getSelectedItem().toString().compareTo("Antiguedad de saldos")==0)
        {
            /*Deshabilita todos los controles*/
            jCTFech.setEnabled  (false);
            jCPe.setEnabled     (false);
            jCCo.setEnabled     (false);
            jCCa.setEnabled     (false);
            jCVen.setEnabled    (false);
            jCDesglo.setEnabled (false);
            jDDe.setEnabled     (false);
            jDA.setEnabled      (false);            
            
            /*Que no puedan tener foco del teclado*/
            jCTFech.setFocusable    (false);
            jCPe.setFocusable       (false);
            jCCo.setFocusable       (false);
            jCCa.setFocusable       (false);
            jCVen.setFocusable      (false);
            jCDesglo.setFocusable   (false);
            jDDe.setFocusable       (false);
            jDA.setFocusable        (false);
        }
        //Si selecciono el reporte de abonos por conceptos entonces
        else if(jComTip.getSelectedItem().toString().compareTo("Abonos por conceptos")==0)
        {
            /*Deshabilita todos los controles*/
            jCTFech.setEnabled  (true);
            jCPe.setEnabled     (false);
            jCCo.setEnabled     (true);
            jCCa.setEnabled     (true);            
            jCVen.setEnabled    (false);
            jCDesglo.setEnabled (false);
            jDDe.setEnabled     (false);
            jDA.setEnabled      (false);            
            
            /*Que no puedan tener foco del teclado*/
            jCTFech.setFocusable    (true);
            jCPe.setFocusable       (false);
            jCCo.setFocusable       (true);
            jCCa.setFocusable       (true);            
            jCVen.setFocusable      (false);
            jCDesglo.setFocusable   (false);
            jDDe.setFocusable       (false);
            jDA.setFocusable        (false);
        }        
        /*Else no es ese reporte entonces*/
        else
        {
            /*Habilita todos los controles*/
            jCTFech.setEnabled  (true);
            jCPe.setEnabled     (true);
            jCCo.setEnabled     (true);
            jCCa.setEnabled     (true);
            jCVen.setEnabled    (true);
            jCDesglo.setEnabled (true);
            jDDe.setEnabled     (true);
            jDA.setEnabled      (true);            
            
            /*Que puedan tener foco del teclado*/
            jCTFech.setFocusable    (true);
            jCPe.setFocusable       (true);
            jCCo.setFocusable       (true);
            jCCa.setFocusable       (true);
            jCVen.setFocusable      (true);
            jCDesglo.setFocusable   (true);
            jDDe.setFocusable       (true);
            jDA.setFocusable        (true);
        }
        
        /*Establece el campo de fechas extra para que solo se pueda modificar con el botón*/
        jDDe.getDateEditor().setEnabled (false);
        jDA.getDateEditor().setEnabled  (false);
        
    }//GEN-LAST:event_jComTipActionPerformed


    /*Cuando el mouse entra en el botón de seleccionar todos los check*/
    private void jBSelTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseEntered

        /*Cambia el color del fondo del botón*/
        jBSelT.setBackground(Star.colBot);

    }//GEN-LAST:event_jBSelTMouseEntered

    
    /*Cuando el mouse sale del botón de seleccionar todos los check*/
    private void jBSelTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBSelT.setBackground(colOri);

    }//GEN-LAST:event_jBSelTMouseExited

    
    /*Cuando se presiona el botón de seleccionar todos los check*/
    private void jBSelTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelTActionPerformed

        /*Seleccioan o deselecciona todos los check 1*/
        if(bSel)
        {
            /*Deselecciona todos los check 1*/
            jCPe.setSelected    (false);
            jCCo.setSelected    (false);
            jCVen.setSelected   (false);
            jCCa.setSelected    (false);

            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCPe.setSelected    (true);
            jCCo.setSelected    (true);
            jCVen.setSelected   (true);            
            jCCa.setSelected    (true);            

            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }

    }//GEN-LAST:event_jBSelTActionPerformed

    
    /*Cuando se presiona una tecla en el botón de selecconar todos los checks*/
    private void jBSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSelTKeyPressed

    
    //Cuando se presiona una tecla en el check de cancelado
    private void jCCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCCaKeyPressed

                                 
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSelT;
    private javax.swing.JCheckBox jCCa;
    private javax.swing.JCheckBox jCCo;
    private javax.swing.JCheckBox jCDesglo;
    private javax.swing.JCheckBox jCPe;
    private javax.swing.JCheckBox jCTFech;
    private javax.swing.JCheckBox jCVen;
    private javax.swing.JComboBox jComTip;
    private com.toedter.calendar.JDateChooser jDA;
    private com.toedter.calendar.JDateChooser jDDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTSer;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
