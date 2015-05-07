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




/*Clase para controlar el reporteador de log de correos*/
public class RepEstadiCor extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Bandera para seleccionar todos los check*/
    private boolean                 bSel    = false;
    
    
    
    
    /*Constructor sin argumentos*/
    public RepEstadiCor() 
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
        this.setTitle("Estadísticas de correos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFini = new Date();
        jDateDe.setDate (fFini);
        jDateA.setDate  (fFini);
        
        /*Pon el foco del teclado en el campo de la sucursal*/
        jTSuc.grabFocus();
                
    }/*Fin de public RepLogCor() */
                
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jDateDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateA = new com.toedter.calendar.JDateChooser();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jTSuc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTCaj = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTUsr = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jCOrd = new javax.swing.JCheckBox();
        jCFac = new javax.swing.JCheckBox();
        jCCot = new javax.swing.JCheckBox();
        jBSelT = new javax.swing.JButton();

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
        jBSal.setNextFocusableComponent(jTSuc);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 110, 30));

        jDateDe.setEnabled(false);
        jDateDe.setNextFocusableComponent(jDateA);
        jDateDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateDeKeyPressed(evt);
            }
        });
        jP1.add(jDateDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 140, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 150, 20));

        jDateA.setEnabled(false);
        jDateA.setNextFocusableComponent(jBAbr);
        jDateA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateAKeyPressed(evt);
            }
        });
        jP1.add(jDateA, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 140, 20));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setNextFocusableComponent(jDateDe);
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 130, 20));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, 30));

        jTSuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSuc.setNextFocusableComponent(jTCaj);
        jTSuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSucFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSucFocusLost(evt);
            }
        });
        jTSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSucKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSucKeyTyped(evt);
            }
        });
        jP1.add(jTSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 130, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("De:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 130, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Caja:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 130, -1));

        jTCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCaj.setNextFocusableComponent(jTUsr);
        jTCaj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCajFocusGained(evt);
            }
        });
        jTCaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCajKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCajKeyTyped(evt);
            }
        });
        jP1.add(jTCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 130, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Usuario:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 120, -1));

        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jBUsr);
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
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 90, 20));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Vendedor(es)");
        jBUsr.setNextFocusableComponent(jCTFech);
        jBUsr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUsrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUsrMouseExited(evt);
            }
        });
        jBUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsrActionPerformed(evt);
            }
        });
        jBUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUsrKeyPressed(evt);
            }
        });
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 30, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Sucursal:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 130, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 110, 20));

        jCOrd.setBackground(new java.awt.Color(255, 255, 255));
        jCOrd.setSelected(true);
        jCOrd.setText("Órdenes de Compra");
        jCOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCOrdActionPerformed(evt);
            }
        });
        jCOrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOrdKeyPressed(evt);
            }
        });
        jP1.add(jCOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, -1));

        jCFac.setBackground(new java.awt.Color(255, 255, 255));
        jCFac.setSelected(true);
        jCFac.setText("Facturas");
        jCFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFacKeyPressed(evt);
            }
        });
        jP1.add(jCFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 80, -1));

        jCCot.setBackground(new java.awt.Color(255, 255, 255));
        jCCot.setSelected(true);
        jCCot.setText("Cotizaciones");
        jCCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCotKeyPressed(evt);
            }
        });
        jP1.add(jCCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 100, -1));

        jBSelT.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT.setToolTipText("Seleccionar/DeseleccionarTodos los Tipos de Reporte");
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
        jP1.add(jBSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 20, 10));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();               
        this.dispose();       
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed
    
    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed

        /*Si no esta seleccionado por lo menos un tipo de reporte entonces*/
        if(!jCFac.isSelected() && !jCCot.isSelected() && !jCOrd.isSelected())
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Selecciona ya sea factura, cotización u órden de compra. Por lo menos una opción marcada.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el usuario existe*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next() && jTUsr.getText().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario: " + jTUsr.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTUsr.grabFocus();             
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
        Date f                      =  jDateA.getDate();
        SimpleDateFormat sdf        = new SimpleDateFormat("yyy-MM-dd");
        String sFA                  = sdf.format(f);   
        
        /*Lee la fecha de*/
        f                           =  jDateDe.getDate();
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
                jDateA.grabFocus();
                return;
            }
            
            /*Si la fecha a esta vacia entonces*/
            if(sFA.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una fecha.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jDateA.grabFocus();
                return;
            }
                        
        }/*Fin de if(!jCheckBoxTodasLasFechas.isSelected())*/                    
        /*Else si esta seleccionado el checkbox de todas las fechas entonces*/
        else
        {
            /*Resetea las fechas*/
            sFDe    = "";
            sFA     = "";
        }           

        /*Crea la condición del tipo de reporte*/
        String sCondTip = "'',";
        if(jCFac.isSelected())
            sCondTip    += "'FACTURA',";
        if(jCCot.isSelected())
            sCondTip    += "'COTIZACIÓN',";
        if(jCOrd.isSelected())
            sCondTip    += "'ORDENC',";
        
        /*Termina la condición del tipo de reporte*/
        sCondTip        = sCondTip.substring(0, sCondTip.length() - 1);        
        
        /*Declara variables final para el thread*/
        final String sCajFi         = jTCaj.getText();
        final String sSucuFi        = jTSuc.getText();
        final String sUsrFi         = jTUsr.getText();
        final String sFDeFi         = sFDe;
        final String sFAFi          = sFA;
        final String sCondTipFi     = sCondTip;
        
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

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();             
                    pa.clear();
                    pa.put("USR",           sUsrFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("SUC",           sSucuFi);
                    pa.put("CAJ",           sCajFi);
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);
                    pa.put("TIP",           sCondTipFi);
                    
                    /*Si es el reporte de ventas x vendedor entonces compila ese*/                    
                    JasperReport ja = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptEstadiCo.jrxml"));
                    
                    /*Llenalo y muestralo*/
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Estadísticas correos");
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
    private void jDateDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateDeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDateDeKeyPressed

    
    /*Cuando se presiona una tecla en el campo de fecha a*/
    private void jDateAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDateAKeyPressed
       
    
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
            jDateDe.setEnabled  (false);
            jDateA.setEnabled   (false);
        }
        /*Else, activa los controles de fecha*/
        else
        {
            jDateDe.setEnabled  (true);
            jDateA.setEnabled   (true);
            
            /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
            jDateDe.getDateEditor().setEnabled  (false);
            jDateA.getDateEditor().setEnabled   (false);            
        }
        
    }//GEN-LAST:event_jCTFechActionPerformed

    
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

    
    /*Cuando se presiona el botón de bùscar vendedor*/   
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de bùscar vendedor*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se presiona una tecla en el control del usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed
    
    
    /*Cuando se presiona una tecla en el campo de la sucursal*/
    private void jTSucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSucKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la caja*/
    private void jTCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCajKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la sucursal*/   
    private void jTSucFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSuc.setSelectionStart(0);jTSuc.setSelectionEnd(jTSuc.getText().length());                
        
    }//GEN-LAST:event_jTSucFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de caja*/
    private void jTCajFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCaj.setSelectionStart(0);jTCaj.setSelectionEnd(jTCaj.getText().length());                
        
    }//GEN-LAST:event_jTCajFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());        
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se tipea una tecla en el campo del usuario*/
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUsrKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la caja*/
    private void jTCajKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCajKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la sucursal*/
    private void jTSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucKeyTyped
                
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTSucKeyTyped

            
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

    
    /*Cuando se pierde el foco del teclado en el campo del usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUsrMouseEntered
        
    
    /*Cuando el mouse sale del botón específico*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited
            
    
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
           
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTSucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSuc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSucFocusLost

    
    /*Cuando se presiona una tecla en el checkbox de factura*/
    private void jCFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFacKeyPressed

    
    /*Cuando se presiona una tecla en el chec de cotizaciones*/
    private void jCCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCotKeyPressed

    
    /*Cuando se presiona una tecla en el check de órdenes de compra*/
    private void jCOrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOrdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCOrdKeyPressed

    
    /*Cuando el mouse entra en el botón de selecconar todos los check*/
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
            jCFac.setSelected(false);
            jCOrd.setSelected(false);
            jCCot.setSelected(false);

            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCFac.setSelected(true);
            jCOrd.setSelected(true);
            jCCot.setSelected(true);

            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }
        
    }//GEN-LAST:event_jBSelTActionPerformed

    
    /*Cuando se presiona una teclae en el botón de seleccionar todos los checkboxes*/
    private void jBSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSelTKeyPressed

    private void jCOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCOrdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCOrdActionPerformed
                                          
    
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
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSelT;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCCot;
    private javax.swing.JCheckBox jCFac;
    private javax.swing.JCheckBox jCOrd;
    private javax.swing.JCheckBox jCTFech;
    private com.toedter.calendar.JDateChooser jDateA;
    private com.toedter.calendar.JDateChooser jDateDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCaj;
    private javax.swing.JTextField jTSuc;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
