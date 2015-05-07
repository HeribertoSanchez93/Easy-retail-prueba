//Paquete
package report;

//Importaciones
import java.awt.Cursor;
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
import ptovta.Login;
import static ptovta.Princip.bIdle;
import ptovta.Star;
import ptovta.Busc;




/*Reporteador de contabilidad*/
public class RepConta extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Bandera para seleccionar todos los check*/
    private boolean                 bSel    = false;
    
    
    
    /*Constructor sin argumentos*/
    public RepConta() 
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
        this.setTitle("Reporte contabilidad, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFIni = new Date();
        jDtIni.setDate (fFIni);
        jDtFin.setDate  (fFIni);
        
        /*Pon el foco del teclado en control del producto*/
        jTProd.grabFocus();
                        
    }/*Fin de public RepConta() */
        
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visACotIng1 = new vis.VisACotIng();
        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jDtIni = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDtFin = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jComTip = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jTSerProd = new javax.swing.JTextField();
        jCComp = new javax.swing.JCheckBox();
        jCNew = new javax.swing.JCheckBox();
        jCPar = new javax.swing.JCheckBox();
        jTConcep = new javax.swing.JTextField();
        jBConcep = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jBSelT = new javax.swing.JButton();
        jCBaj = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jBLug = new javax.swing.JButton();
        jTLug = new javax.swing.JTextField();

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
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 110, 30));

        jDtIni.setEnabled(false);
        jDtIni.setNextFocusableComponent(jDtFin);
        jDtIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDtIniKeyPressed(evt);
            }
        });
        jP1.add(jDtIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Fin dep:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 150, -1));

        jDtFin.setEnabled(false);
        jDtFin.setNextFocusableComponent(jTProd);
        jDtFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDtFinKeyPressed(evt);
            }
        });
        jP1.add(jDtFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Inicio dep:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 120, -1));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setNextFocusableComponent(jDtIni);
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 130, -1));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 110, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, 110, 20));

        jComTip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activos fijos" }));
        jComTip.setNextFocusableComponent(jCNew);
        jComTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTipKeyPressed(evt);
            }
        });
        jP1.add(jComTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 270, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Serie:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 130, -1));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBProd);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProdFocusLost(evt);
            }
        });
        jTProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProdKeyTyped(evt);
            }
        });
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 110, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTSerProd);
        jBProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdMouseExited(evt);
            }
        });
        jBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdActionPerformed(evt);
            }
        });
        jBProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdKeyPressed(evt);
            }
        });
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 30, 20));

        jTSerProd.setEditable(false);
        jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSerProd.setNextFocusableComponent(jTConcep);
        jTSerProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerProdFocusLost(evt);
            }
        });
        jTSerProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerProdKeyPressed(evt);
            }
        });
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 140, 20));

        jCComp.setBackground(new java.awt.Color(255, 255, 255));
        jCComp.setSelected(true);
        jCComp.setText("Completo");
        jCComp.setNextFocusableComponent(jCBaj);
        jCComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompKeyPressed(evt);
            }
        });
        jP1.add(jCComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 90, -1));

        jCNew.setBackground(new java.awt.Color(255, 255, 255));
        jCNew.setSelected(true);
        jCNew.setText("Nuevo");
        jCNew.setNextFocusableComponent(jCPar);
        jCNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCNewKeyPressed(evt);
            }
        });
        jP1.add(jCNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 80, -1));

        jCPar.setBackground(new java.awt.Color(255, 255, 255));
        jCPar.setSelected(true);
        jCPar.setText("Parcial");
        jCPar.setNextFocusableComponent(jCComp);
        jCPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCParKeyPressed(evt);
            }
        });
        jP1.add(jCPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 80, -1));

        jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTConcep.setNextFocusableComponent(jBConcep);
        jTConcep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTConcepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTConcepFocusLost(evt);
            }
        });
        jTConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConcepKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTConcepKeyTyped(evt);
            }
        });
        jP1.add(jTConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 110, 20));

        jBConcep.setBackground(new java.awt.Color(255, 255, 255));
        jBConcep.setText("...");
        jBConcep.setToolTipText("Buscar Producto(s)");
        jBConcep.setName(""); // NOI18N
        jBConcep.setNextFocusableComponent(jTLug);
        jBConcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConcepMouseExited(evt);
            }
        });
        jBConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConcepActionPerformed(evt);
            }
        });
        jBConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConcepKeyPressed(evt);
            }
        });
        jP1.add(jBConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 30, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Producto:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 130, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Concepto:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 130, -1));

        jBSelT.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT.setToolTipText("Seleccionar/DeseleccionarTodos los Tipos de Log");
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
        jP1.add(jBSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 20, 10));

        jCBaj.setBackground(new java.awt.Color(255, 255, 255));
        jCBaj.setText("Baja");
        jCBaj.setNextFocusableComponent(jCTFech);
        jCBaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBajKeyPressed(evt);
            }
        });
        jP1.add(jCBaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 70, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Lugar:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 130, -1));

        jBLug.setBackground(new java.awt.Color(255, 255, 255));
        jBLug.setText("jButton1");
        jBLug.setToolTipText("Buscar Lugar(es)");
        jBLug.setNextFocusableComponent(jBAbr);
        jBLug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLugMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLugMouseExited(evt);
            }
        });
        jBLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLugActionPerformed(evt);
            }
        });
        jBLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLugKeyPressed(evt);
            }
        });
        jP1.add(jBLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 30, 20));

        jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug.setNextFocusableComponent(jBLug);
        jTLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLugFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLugFocusLost(evt);
            }
        });
        jTLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLugKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLugKeyTyped(evt);
            }
        });
        jP1.add(jTLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 100, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
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
        
        /*Llama al recolector de basura y cierra el formulario*/
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

        /*Si no a ingresado alguna opción de los check entonces*/
        if(!jCNew.isSelected() && !jCPar.isSelected() && !jCComp.isSelected() && !jCBaj.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una opción del estado del activo.", "Reporte contabilidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el primer check y regresa*/
            jCNew.grabFocus();
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
        
        //Comprueba si el producto existe                       
        int iRes        = Star.iExistProd(con, jTProd.getText().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si el producto no existe entonces
        if(iRes==0 && jTProd.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();               
            return;              
        }
        
        /*Comprueba si el concepto existe*/        
        try
        {
            sQ  = "SELECT concep FROM actfijcat WHERE concep = '" + jTConcep.getText().trim() + "'";                      
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next() && jTConcep.getText().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El concepto: " + jTConcep.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTConcep.grabFocus();               
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
        Date f                      =  jDtFin.getDate();
        SimpleDateFormat sdf        = new SimpleDateFormat("yyy-MM-dd");
        String sFA                  = sdf.format(f);   
        
        /*Lee la fecha de*/
        f                           =  jDtIni.getDate();        
        String sFDe                 = sdf.format(f);   
            
        /*Si el checkbox de todas las fechas no esta marcado entonces*/
        if(!jCTFech.isSelected())
        {
            /*Si la fecha de inicio de depreciacion esta vacia entonces*/
            if(sFA.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una fecha de inicio de depreciación.", "Inicio depreciación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jDtIni.grabFocus();                
                return;
            }
            
            /*Si la fecha de fin de depreciación esta vacia entonces*/
            if(sFA.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa una fecha de fin de depreciación.", "Fin depreciación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el control y regresa*/
                jDtFin.grabFocus();
                return;
            }
                        
        }/*Fin de if(!jCheckBoxTodasLasFechas.isSelected())*/                    
        /*Else si esta marcado todas las fechas entonces coloca en vacia las fechas*/
        else
        {            
            sFA     = "";
            sFDe    = "";
        }
        
        /*Crea las tres conbinaciones posibles para el estado del activo*/
        String sTip = "1";
        if(jCNew.isSelected() && jCPar.isSelected() && jCComp.isSelected())
            sTip    = "2";
        else if(jCNew.isSelected() && jCPar.isSelected())
            sTip    = "3";
        else if(jCNew.isSelected() && jCComp.isSelected())
            sTip    = "4";
        else if(jCPar.isSelected() && jCComp.isSelected())
            sTip    = "5";
        else if(jCNew.isSelected())
            sTip    = "6";
        else if(jCPar.isSelected())
            sTip    = "7";
        else if(jCComp.isSelected())
            sTip    = "8";
                    
        /*Determina si mostrar las bajas o no*/
        String sBaj = "0";
        if(jCBaj.isSelected())
            sBaj    = "1";
        
        /*Declara las variable final para el thread*/
        final String sProdFi        = jTProd.getText().trim();
        final String sConcepFi      = jTConcep.getText().trim();
        final String sLugFi         = jTLug.getText().trim();
        final String sTipFi         = sTip;
        final String sBajFi         = sBaj;
        final String sFDeFi         = sFDe;
        final String sFAFi          = sFA;        
        
        /*Crea el thread para cargar el reporte en un hilo aparte*/
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
                    pa.put("PROD",          sProdFi);
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);
                    pa.put("TIP",           sTipFi);
                    pa.put("BAJ",           sBajFi);
                    pa.put("LUG",           sLugFi);
                    pa.put("CONCEP",        sConcepFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());

                    /*Compila el reporte y maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptDepre.jrxml"));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                           /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte de Contabilidad");
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
    private void jDtIniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDtIniKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDtIniKeyPressed

    
    /*Cuando se presiona una tecla en el campo de fecha a*/
    private void jDtFinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDtFinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDtFinKeyPressed

    
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
            jDtIni.setEnabled  (false);
            jDtFin.setEnabled   (false);
        }
        /*Else, activa los controles de fecha*/
        else
        {
            jDtIni.setEnabled  (true);
            jDtFin.setEnabled   (true);
            
            /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
            jDtIni.getDateEditor().setEnabled  (false);
            jDtFin.getDateEditor().setEnabled   (false);           
        }
        
    }//GEN-LAST:event_jCTFechActionPerformed

    
    /*Cuando se mueve la rueda del ratón en la  forma*/
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

        
    /*Cuando se presiona una tecla en el combo del tipo de reporte*/
    private void jComTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTipKeyPressed

  
    /*Cuando se gana el foco del teclado en el campo del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Si el campo esta vacio entonces*/
        if(jTProd.getText().trim().compareTo("")==0)
        {
            /*Coloca cadena vacia en el campo de la serie y regresa*/
            jTSerProd.setText("");            
            return;
        }
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Comprueba si el producto existe        
        int iRes        = Star.iExistProd(con, jTProd.getText().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si el producto no existe entonces colca la serie del producto en cadena vacia
        if(iRes==0 && jTProd.getText().compareTo("")!=0)        
            jTSerProd.setText("");        
        
        //Cierra la base de datos
        Star.iCierrBas(con);                   

    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
            b.setVisible(true);
        }
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan v = new ptovta.BuscAvan(this, jTProd, null, null, jTSerProd);
            v.setVisible(true);

            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando el mouse entra en el botón del producto*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del botón del producto*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona una tecla en el botón de producto*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecal en el botón de producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la serie del producto*/
    private void jTSerProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSerProd.setSelectionStart(0);jTSerProd.setSelectionEnd(jTSerProd.getText().length());

    }//GEN-LAST:event_jTSerProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la serie del producto*/
    private void jTSerProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTSerProd.setCaretPosition(0);

    }//GEN-LAST:event_jTSerProdFocusLost

    
    /*Cuando se presiona una tecla en el campo de la serie del producto*/
    private void jTSerProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerProdKeyPressed

        /*Llama a la función escalable para procesar la operación*/
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTSerProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del concepto*/
    private void jTConcepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTConcep.setSelectionStart(0);jTConcep.setSelectionEnd(jTConcep.getText().length());
        
    }//GEN-LAST:event_jTConcepFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del concepto*/
    private void jTConcepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusLost
        
        /*Coloca el borde negro si tiene datos*/
        if(jTConcep.getText().compareTo("")!=0)
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTConcepFocusLost

    
    /*Cuando se presiona una tecla en el campo del concepto*/
    private void jTConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTConcep.getText(), 39, jTConcep, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTConcepKeyPressed

    
    /*Cuando se tipea una tecla en el campo del concepto*/
    private void jTConcepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTConcepKeyTyped

    
    /*Cuando el mouse entra en el botón de búscar concepto*/
    private void jBConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConcepMouseEntered

    
    /*Cuando el mouse sale del botón de concepto*/
    private void jBConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(colOri);
        
    }//GEN-LAST:event_jBConcepMouseExited

    
    /*Cuando se presiona el botón de búscar concepto*/
    private void jBConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConcepActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTConcep.getText(), 39, jTConcep, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del concepto*/
        jTConcep.grabFocus();
        
    }//GEN-LAST:event_jBConcepActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar concepto*/
    private void jBConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConcepKeyPressed

    
    /*Cuando el mouse entra en el botón de seleccionar todo*/
    private void jBSelTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseEntered

        /*Cambia el color del fondo del botón*/
        jBSelT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSelTMouseEntered

    
    /*Cuando el mouse sale del botón de seleccionar todo*/
    private void jBSelTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBSelT.setBackground(colOri);
        
    }//GEN-LAST:event_jBSelTMouseExited

    
    /*Cuando se presiona el botón de seleccionar todo*/
    private void jBSelTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelTActionPerformed

        /*Seleccioan o deselecciona todos los check 1*/
        if(bSel)
        {
            /*Deselecciona todos los check 1*/
            jCNew.setSelected   (false);
            jCPar.setSelected   (false);
            jCComp.setSelected  (false);
            jCBaj.setSelected   (false);

            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCNew.setSelected   (true);
            jCPar.setSelected   (true);
            jCComp.setSelected  (true);
            jCBaj.setSelected   (true);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }
        
    }//GEN-LAST:event_jBSelTActionPerformed

    
    /*Cuando se presiona una tecla en el boton de seleccionar todo*/
    private void jBSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSelTKeyPressed

    
    /*Cuando se presiona una tecla en el check de nuevo*/
    private void jCNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCNewKeyPressed

    
    /*Cuando se presiona una tecla en el check de parcial*/
    private void jCParKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCParKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCParKeyPressed

    
    /*Cuando se presiona una tecla en el check de completo*/
    private void jCCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCompKeyPressed

    
    /*Cuando se presiona una tecla en el check de baja*/
    private void jCBajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBajKeyPressed

    private void jBLugMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLugMouseEntered

        /*Cambia el color del fondo del botón*/
        jBLug.setBackground(Star.colBot);

    }//GEN-LAST:event_jBLugMouseEntered

    private void jBLugMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLugMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBLug.setBackground(colOri);

    }//GEN-LAST:event_jBLugMouseExited

    private void jBLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLugActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTLug.getText(), 16, jTLug, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBLugActionPerformed

    private void jBLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLugKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBLugKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del lugar*/
    private void jTLugFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLug.setSelectionStart(0);jTLug.setSelectionEnd(jTLug.getText().length());

    }//GEN-LAST:event_jTLugFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lugar*/
    private void jTLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTLug.getText().compareTo("")!=0)
            jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTLugFocusLost

    
    /*Cuando se presiona una tecla en el campo del lugar*/
    private void jTLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTLug.getText(), 16, jTLug, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLugKeyPressed

    
    /*Cuando se tipea una tecla en el campo del lugar*/
    private void jTLugKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTLugKeyTyped
                          
       
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
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBConcep;
    private javax.swing.JButton jBLug;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSelT;
    private javax.swing.JCheckBox jCBaj;
    private javax.swing.JCheckBox jCComp;
    private javax.swing.JCheckBox jCNew;
    private javax.swing.JCheckBox jCPar;
    private javax.swing.JCheckBox jCTFech;
    private javax.swing.JComboBox jComTip;
    private com.toedter.calendar.JDateChooser jDtFin;
    private com.toedter.calendar.JDateChooser jDtIni;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTLug;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTSerProd;
    private vis.VisACotIng visACotIng1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
