/*Paquete*/
package ptovta;

/*Importaciones*/
import java.awt.Cursor;
import java.awt.HeadlessException;
import static ptovta.Princip.bIdle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



/*Clase para hacer interfaz entre Contpaq i comercial y este sistema*/
public class InterCI extends javax.swing.JDialog 
{            
    /*Bandera para saber cuando hay error*/
    private boolean             bErr;   
    
    
    
    
    /*Constructor sin argumentos*/
    public InterCI() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        Star.lCargGral=null;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBSinc);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Pon el foco del teclado en el campo de la instancia*/
        jTInst.grabFocus();                                       
                
    }/*Fin de public InterCI() */        
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSinc = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTInst = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTBD = new javax.swing.JTextField();
        jTUsr = new javax.swing.JTextField();
        jPCont = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jBProb = new javax.swing.JButton();
        jCMosC = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jCCans = new javax.swing.JCheckBox();
        jCCliens = new javax.swing.JCheckBox();
        jCProvs = new javax.swing.JCheckBox();
        jCComps = new javax.swing.JCheckBox();
        jCFacs = new javax.swing.JCheckBox();
        jCProds = new javax.swing.JCheckBox();
        jCServs = new javax.swing.JCheckBox();
        jCDevs = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jTPort = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

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

        jBSinc.setBackground(new java.awt.Color(255, 255, 255));
        jBSinc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSinc.setForeground(new java.awt.Color(0, 102, 0));
        jBSinc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/transferencia.png"))); // NOI18N
        jBSinc.setToolTipText("Comenzar Sincronización (F6)");
        jBSinc.setNextFocusableComponent(jBSal);
        jBSinc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSincMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSincMouseExited(evt);
            }
        });
        jBSinc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSincActionPerformed(evt);
            }
        });
        jBSinc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSincKeyPressed(evt);
            }
        });
        jP1.add(jBSinc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jCCliens);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 110, 30));

        jTInst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTInst.setNextFocusableComponent(jTUsr);
        jTInst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTInstFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTInstFocusLost(evt);
            }
        });
        jTInst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTInstKeyPressed(evt);
            }
        });
        jP1.add(jTInst, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 220, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Comercial 1.0");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 370, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("*Usuario:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 220, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("*Contraseña:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 220, -1));

        jTBD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBD.setNextFocusableComponent(jTPort);
        jTBD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBDFocusLost(evt);
            }
        });
        jTBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBDKeyPressed(evt);
            }
        });
        jP1.add(jTBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 220, 20));

        jTUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jPCont);
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
        });
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 220, 20));

        jPCont.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPCont.setNextFocusableComponent(jTBD);
        jPCont.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContFocusLost(evt);
            }
        });
        jPCont.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContKeyPressed(evt);
            }
        });
        jP1.add(jPCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 220, 20));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("*Instancia:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, -1));

        jBProb.setBackground(new java.awt.Color(255, 255, 255));
        jBProb.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProb.setForeground(new java.awt.Color(0, 102, 0));
        jBProb.setText("Probar ");
        jBProb.setToolTipText("Probar Conexión con Comercial (F2)");
        jBProb.setName(""); // NOI18N
        jBProb.setNextFocusableComponent(jBSinc);
        jBProb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbMouseExited(evt);
            }
        });
        jBProb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbActionPerformed(evt);
            }
        });
        jBProb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbKeyPressed(evt);
            }
        });
        jP1.add(jBProb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 110, 30));

        jCMosC.setBackground(new java.awt.Color(255, 255, 255));
        jCMosC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosC.setText("Mostrar Contraseña F4");
        jCMosC.setNextFocusableComponent(jBProb);
        jCMosC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMosCActionPerformed(evt);
            }
        });
        jCMosC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosCKeyPressed(evt);
            }
        });
        jP1.add(jCMosC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 160, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("*Puerto:");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 110, -1));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 120, -1));

        jCCans.setBackground(new java.awt.Color(255, 255, 255));
        jCCans.setSelected(true);
        jCCans.setText("Cancelaciones");
        jCCans.setNextFocusableComponent(jCCans);
        jCCans.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCansKeyPressed(evt);
            }
        });
        jP1.add(jCCans, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 120, -1));

        jCCliens.setBackground(new java.awt.Color(255, 255, 255));
        jCCliens.setSelected(true);
        jCCliens.setText("Clientes");
        jCCliens.setNextFocusableComponent(jCProvs);
        jCCliens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCliensKeyPressed(evt);
            }
        });
        jP1.add(jCCliens, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 120, -1));

        jCProvs.setBackground(new java.awt.Color(255, 255, 255));
        jCProvs.setSelected(true);
        jCProvs.setText("Proveedores");
        jCProvs.setNextFocusableComponent(jCComps);
        jCProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCProvsKeyPressed(evt);
            }
        });
        jP1.add(jCProvs, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 120, -1));

        jCComps.setBackground(new java.awt.Color(255, 255, 255));
        jCComps.setSelected(true);
        jCComps.setText("Compras");
        jCComps.setNextFocusableComponent(jCComps);
        jCComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompsKeyPressed(evt);
            }
        });
        jP1.add(jCComps, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 120, -1));

        jCFacs.setBackground(new java.awt.Color(255, 255, 255));
        jCFacs.setSelected(true);
        jCFacs.setText("Facturas");
        jCFacs.setNextFocusableComponent(jCFacs);
        jCFacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFacsKeyPressed(evt);
            }
        });
        jP1.add(jCFacs, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 120, -1));

        jCProds.setBackground(new java.awt.Color(255, 255, 255));
        jCProds.setSelected(true);
        jCProds.setText("Productos");
        jCProds.setNextFocusableComponent(jCProds);
        jCProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCProdsKeyPressed(evt);
            }
        });
        jP1.add(jCProds, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 120, -1));

        jCServs.setBackground(new java.awt.Color(255, 255, 255));
        jCServs.setSelected(true);
        jCServs.setText("Servicios");
        jCServs.setNextFocusableComponent(jCServs);
        jCServs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCServsKeyPressed(evt);
            }
        });
        jP1.add(jCServs, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 120, -1));

        jCDevs.setBackground(new java.awt.Color(255, 255, 255));
        jCDevs.setSelected(true);
        jCDevs.setText("Devoluciones");
        jCDevs.setNextFocusableComponent(jCDevs);
        jCDevs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDevsKeyPressed(evt);
            }
        });
        jP1.add(jCDevs, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 120, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Interfaz CONTPAQ I");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 370, -1));

        jTPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTPort.setText("1433");
        jTPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPort.setNextFocusableComponent(jCMosC);
        jTPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPortFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPortFocusLost(evt);
            }
        });
        jTPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPortKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPortKeyTyped(evt);
            }
        });
        jP1.add(jTPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 110, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("*Base de Datos:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 220, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

                
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing
    
    
    /*Cuando se mueve el ratón en el dialogo*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando el mouse es arrrastrado en el diálogo*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando la rueda del ratón es movida en el diálogo*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando el mouse sale del campo del link de ayuda*/
    private void jLAyuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseExited

        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jLAyuMouseExited

    
    /*Cuando el mouse entra en el campo del link de ayuda*/
    private void jLAyuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseEntered

        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando se presiona una tecla en el checkbox de mostrar contraseña*/
    private void jCMosCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCMosCKeyPressed

    
    /*Cuando se presiona una tecla en el botón de probar conexión*/
    private void jBProbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProbKeyPressed

    
    /*Cuando se presiona el botón de probar conexión*/
    private void jBProbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbActionPerformed

        /*Lee la instancia*/
        final String sInst      = jTInst.getText().trim();

        /*Si el campo de instancia esta vacio no puede seguir*/
        if(sInst.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de instancia esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTInst.grabFocus();
            return;
        }

        /*Lee el usuario*/
        final String sUsr        = jTUsr.getText().trim();

        /*Si el campo de usuario esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();
            return;
        }

        /*Lee la contraseña*/
        final String sContra            = new String(jPCont.getPassword()).trim();

        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jPCont.grabFocus();
            return;
        }

        /*Lee la base de datos*/
        final String sBD         = jTBD.getText();

        /*Si el campo de bd esta vacio no puede seguir*/
        if(sBD.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de base de datos esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTBD.grabFocus();
            return;
        }       
         
        /*Registra el driver*/
        try            
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch(ClassNotFoundException e)
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Error en Class.forName() por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
            return;
        }
        
        /*Muestra la forma para simular que esta abriendo la base de datos*/
        Star.lCargGral = new LoadinGral("Probando Conexión...");
        Star.lCargGral.setVisible(true);

        /*Obtiene el password*/
        final String sPass = new String(jPCont.getPassword());
        
        /*Intenta conectar en un thread*/
        Thread th = new Thread()
        {
            @Override
            public void run()
            {                
                /*Abre la base de datos*/
                Connection  con;  
                try
                {
                    con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + ";user=" + sUsr + ";password=" + sPass + ";database=" + sBD);
                }
                catch(SQLException ex)
                {
                    /*Esconde la forma de loading*/
                    Star.lCargGral.setVisible(false);
                    
                    /*Agrega en el log*/
                    Login.vLog(ex.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "No se pudo abrir la base de datos. \nPosiblemente este mal escrito el nombre de la base de datos, usuario, puerto o contraseña. Error:\n" + ex.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }               
                
                /*Esconde la forma de probar conexión*/       
                Star.lCargGral.setVisible(false);

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Conexión exitosa.", "Conexión", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
            }
        };
        th.start();                        

    }//GEN-LAST:event_jBProbActionPerformed

    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jPContKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPContKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control de la contraseña */
    private void jPContFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jPCont.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jPCont.getText().compareTo("")!=0)
            jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jPContFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de contraseña*/
    private void jPContFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jPCont.setSelectionStart(0);jPCont.setSelectionEnd(jPCont.getText().length());        

    }//GEN-LAST:event_jPContFocusGained

    
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control del usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());

    }//GEN-LAST:event_jTUsrFocusGained

    
    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jTBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTBDKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control de la base de datos*/
    private void jTBDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBD.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTBD.getText().compareTo("")!=0)
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTBDFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de base de datos*/
    private void jTBDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTBD.setSelectionStart(0);jTBD.setSelectionEnd(jTBD.getText().length());

    }//GEN-LAST:event_jTBDFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTInstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTInstKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTInstKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control de la instancia*/
    private void jTInstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTInst.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTInst.getText().compareTo("")!=0)
            jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTInstFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de edición de línea*/
    private void jTInstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTInst.setSelectionStart(0);jTInst.setSelectionEnd(jTInst.getText().length());        

    }//GEN-LAST:event_jTInstFocusGained

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBSincKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSincKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSincKeyPressed

//GEN-FIRST:event_jBGuaActionPerformed
 
//GEN-LAST:event_jBGuaActionPerformed

    
    /*Sincroniza clientes*/
    private synchronized void vSinCliens(String sInst, String sUsr, String sPass, String sBD)
    {        
        /*Abre la base de datos*/        
        Connection  con;  
        try
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + ";user=" + sUsr + ";password=" + sPass + ";database=" + sBD);
        }
        catch(SQLException ex)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde la forma de loading*/
            Star.lCargGral.setVisible(false);

            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " vSin() No se pudo abrir la base de datos. \nPosiblemente este mal escrito el nombre de la base de datos, usuario, puerto o contraseña. Error:\n" + ex.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ              = ""; 
        
        /*Obtiene todos los clientes de contpaq*/
        try
        {
            sQ = "SELECT admclientes.CCODIGOCLIENTE, admclientes.CRAZONSOCIAL, admclientes.CRFC, admclientes.CLIMITECREDITOCLIENTE, admclientes.CDIASCREDITOCLIENTE, admclientes.CDIAPAGO, admclientes.CEMAIL1, admclientes.CEMAIL2, admclientes.CEMAIL3, admclientes.CLISTAPRECIOCLIENTE, admclientes.CCURP, admclientes.CIDAGENTEVENTA, CNOMBRECALLE, CNUMEROEXTERIOR, CNUMEROINTERIOR, CCOLONIA, CCODIGOPOSTAL, CTELEFONO1, CTELEFONO2, CDIRECCIONWEB, CPAIS, CESTADO, CCIUDAD, CMUNICIPIO FROM admclientes LEFT OUTER JOIN admdomicilios ON CIDCATALOGO = admclientes.CIDCLIENTEPROVEEDOR WHERE CTIPODIRECCION = 0 AND (ctipocliente = 1 OR ctipocliente = 2 )";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Insertalos en la base de datos del sistema*/
                vInsB(rs.getString("ccodigocliente"), rs.getString("crazonsocial"), rs.getString("crfc"), rs.getString("climitecreditocliente"), rs.getString("cdiascreditocliente"), rs.getString("cdiapago"), rs.getString("cemail1"), rs.getString("cemail2"), rs.getString("cemail3"), rs.getString("clistapreciocliente"), rs.getString("ccurp"), rs.getString("cidagenteventa"), rs.getString("CNOMBRECALLE"), rs.getString("CNUMEROEXTERIOR"), rs.getString("CNUMEROINTERIOR"), rs.getString("CCOLONIA"), rs.getString("CCODIGOPOSTAL"), rs.getString("CTELEFONO1"), rs.getString("CTELEFONO2"), rs.getString("CTELEFONO2"), rs.getString("CDIRECCIONWEB"), rs.getString("CPAIS"), rs.getString("CESTADO"),  rs.getString("CMUNICIPIO"));                
                
                /*Si hay error entonces*/
                if(bErr)
                {
                    /*Esconde la forma de loading*/
                    Star.lCargGral.setVisible(false);
                        
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
                }
            }
        }
        catch(SQLException e)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde la forma de loading*/
            Star.lCargGral.setVisible(false);
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        /*Esconde la forma de loading*/
        Star.lCargGral.setVisible(false);
            
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private synchronized void vSinCliens(String sInst, String sUsr, String sPass, String sBD)*/
    
    
    /*Sincroniza proveedores*/
    private synchronized void vSinProvs(String sInst, String sUsr, String sPass, String sBD)
    {        
        /*Abre la base de datos*/        
        Connection  con;  
        try
        {
            con = DriverManager.getConnection("jdbc:sqlserver://" + sInst + ";user=" + sUsr + ";password=" + sPass + ";database=" + sBD);
        }
        catch(SQLException ex)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde la forma de loading*/
            Star.lCargGral.setVisible(false);

            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " vSinProvs() No se pudo abrir la base de datos. \nPosiblemente este mal escrito el nombre de la base de datos, usuario, puerto o contraseña. Error:\n" + ex.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ              = ""; 
        
        /*Obtiene todos los proveedores de contpaq*/
        try
        {
            sQ = "SELECT * FROM adm";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Insertalos en la base de datos del sistema*/
                //vInsB(rs.getString("ccodigocliente"), rs.getString("crazonsocial"), rs.getString("crfc"), rs.getString("climitecreditocliente"), rs.getString("cdiascreditocliente"), rs.getString("cdiapago"), rs.getString("cemail1"), rs.getString("cemail2"), rs.getString("cemail3"), rs.getString("clistapreciocliente"), rs.getString("ccurp"));                
                
                /*Si hay error entonces*/
                if(bErr)
                {
                    /*Esconde la forma de loading*/
                    Star.lCargGral.setVisible(false);
            
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
                }
            }
        }
        catch(SQLException e)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde la forma de loading*/
            Star.lCargGral.setVisible(false);
            
            //Cierra la base de datos
            Star.iCierrBas(con);
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }/*Fin de private synchronized void vSinProvs(String sInst, String sUsr, String sPass, String sBD)*/
    
    
    /*Sincroniza los clientes*/
    private synchronized void vSin(String sInst, String sUsr, String sPass, String sBD, boolean bCliens, boolean bProvs, boolean bComps, boolean bFacs, boolean bProds, boolean bServs, boolean bDevs, boolean bCans)
    {
        /*Inicialmente no hay error*/
        bErr    = false;
        
        /*Si tiene que sincronizar los clientes entonces*/
        if(bCliens)
            vSinCliens(sInst, sUsr, sPass, sBD);                
        /*Si tiene que sincronizar los proveedores entonces*/        
        
    }/*Fin de private synchronized void vSin()*/
    
    
    /*Insertalos en la base de datos del sistema*/                          
    private synchronized void vInsB(String sCod, String sNom,  String sRFC, String sLimCred, String sDiaCred, String sDiaPag, String sCo1, String sCo2, String sCo3, String sList, String sCURP, String sVend, String sCall, String sNumExt, String sNumInt, String sCol, String sCP, String sTel1, String sTel2, String sTel3, String sPagWeb, String sPai, String sEstad, String sMun)
    {
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException | HeadlessException e) 
        {   
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde l forma de loading*/
            Star.lCargGral.setVisible(false);
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " vInsB() Error al abrir la base de datos en jButtonSalirActionPerformed() por " + e.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ              = ""; 
        
        /*Comprueba si el cliente ya existe*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT codemp FROM emps WHERE '" + sCod + "' = codemp";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe, coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException e)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde l forma de loading*/
            Star.lCargGral.setVisible(false);
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                    
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
	    /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }

        /*Si la lista no es válida entonces que sea 1*/
        if(Integer.parseInt(sList)<=0 || Integer.parseInt(sList)>10)
            sList   = "1";
        
        /*Si ya existe el cliente entonces la consulta será esta*/        
        if(bSi)
        {
            sQ  =   "UPDATE emps SET " +
                    "nom        = '" + sNom.replace("'", "''") + "', " +                   
                    "rfc        = '" + sRFC.replace("'", "''") + "', " +
                    "limtcred   = " + sLimCred.replace("'", "''") + ", " + 
                    "diapag     = " + sDiaPag.replace("'", "''") + ", " +
                    "co1        = '" + sCo1.replace("'", "''") + ", " +
                    "co2        = '" + sCo2.replace("'", "''") + ", " +
                    "co3        = '" + sCo3.replace("'", "''") + "', " +
                    "vend       = '" + sVend.replace("'", "''") + "', " +
                    "calle      = '" + sCall.replace("'", "''") + "', " +
                    "noext      = '" + sNumExt.replace("'", "''") + "', " +
                    "noint      = '" + sNumInt.replace("'", "''") + "', " +
                    "col        = '" + sCol.replace("'", "''") + "', " +
                    "cp         = '" + sCP.replace("'", "''") + "', " +
                    "tel        = '" + sTel1.replace("'", "''") + "', " +
                    "telper1    = '" + sTel2.replace("'", "''") + "', " +
                    "telper2    = '" + sTel3.replace("'", "''") + "', " +
                    "pagweb1    = '" + sPagWeb.replace("'", "''") + "', " +
                    "pai        = '" + sPai.replace("'", "''") + "', " +
                    "estad      = '" + sEstad.replace("'", "''") + "', " +
                    "ciu        = '" + sMun.replace("'", "''") + "', " +
                    "list       = " + sList.replace("'", "''") +
                    " WHERE codemp = '" + sCod.replace("'", "''") + "'";
        }
        /*Else si existe entonces la consulta será esta*/
        else
        {
            sQ  =   "INSERT INTO emps(codemp,                               nom,                                    rfc,                                limtcred,          diapag,                                  co1,                                co2,                                co3,                          ser,    codclas,         estac,                                           sucu,                                           nocaj,                                      falt,           fmod,      list,            curp,                               vend,                               calle,                              noext,                                  noint,                                  col,                                    cp,                                 tel,                                    telper1,                            telper2,                            pagweb1,                                pai,                                estad,                                  ciu) " + 
                         "VALUES('" + sCod.replace("'", "''") + "', '" +    sNom.replace("'", "''") + "', '" +      sRFC.replace("'", "''") + "', " +   sLimCred + ",  " + sDiaPag.replace("'", "''") + ", '" +     sCo1.replace("'", "''") + "', '" +  sCo2.replace("'", "''") + "', '" +  sCo3.replace("'", "''") + "', '',      '',         '" + Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now(),          now(), " + sList + ", '" + sCURP.replace("'", "''") + "', '" +  sVend.replace("'", "''") + "', '" + sCall.replace("'", "''") + "', '" + sNumExt.replace("'", "''") + "', '" +   sNumInt.replace("'", "''") + "', '" +   sCol.replace("'", "''") + "', '" +      sCP.replace("'", "''") + "', '" +   sTel1.replace("'", "''") + "', '" +     sTel2.replace("'", "''") + "', '" + sTel3.replace("'", "''") + "', '" + sPagWeb.replace("'", "''") + "', '" +   sPai.replace("'", "''") + "', '" +  sEstad.replace("'", "''") + "', '" +    sMun.replace("'", "''") + "')";
        }
        
        /*Inserta o actualiza el cliente*/
        try 
        {                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            /*Coloca la bandera de error*/
            bErr   = true;
             
            /*Esconde l forma de loading*/
            Star.lCargGral.setVisible(false);
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }  
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
        {
            /*Coloca la bandera de error*/
            bErr   = true;
            
            /*Esconde l forma de loading*/
            Star.lCargGral.setVisible(false);                        
        }
                
    }/*Fin de private synchronized void vInsB(Connection con, String sCod, String sNom,  String sRFC, String sLimCred, String sDiaCred, String sDiaPag, String sCo1, String sCo2, String sCo3 )*/                

    
    /*Cuando se presiona el botón de sincronizar*/
    private void jBSincActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSincActionPerformed
       
        /*Si no a seleccionado por lo menos una opción a sincronizar entonces*/
        if(!jCCliens.isSelected() && !jCProvs.isSelected() && !jCComps.isSelected() && !jCFacs.isSelected() && !jCProds.isSelected() && !jCServs.isSelected() && !jCDevs.isSelected() && !jCCans.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una opción para sincronizar.", "Sincronizar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el primer checkbox y regresa*/
            jCCliens.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer hacer la interfaz*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro de querer hacer la interfaz?", "Interfaz", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
        
        /*Variables final de banderas para saber que sincronizar*/
        final boolean bCliens;
        final boolean bProvs;
        final boolean bComps;
        final boolean bFacs ;
        final boolean bProds;
        final boolean bServs;
        final boolean bDevs;
        final boolean bCans;
        
        /*Coloca las banderas de lo que se tiene que sincronizar*/        
        if(jCCliens.isSelected())
            bCliens     = true;
        else
            bCliens     = false;
        if(jCProvs.isSelected())
            bProvs      = true;
        else
            bProvs      = false;
        if(jCComps.isSelected())
            bComps      = true;
        else
            bComps      = false;
        if(jCFacs.isSelected())
            bFacs       = true;
        else
            bFacs       = false;
        if(jCProds.isSelected())
            bProds      = true;
        else
            bProds      = false;
        if(jCServs.isSelected())
            bServs      = true;
        else
            bServs      = false;
        if(jCDevs.isSelected())
            bDevs       = true;
        else
            bDevs       = false;
        if(jCCans.isSelected())
            bCans       = true;
        else
            bCans       = false;
                            
        /*Obtiene los parámetros de conexión a la base de datos*/
        final String sInst  = jTInst.getText();
        final String sUsr   = jTUsr.getText();
        final String sPass  = new String(jPCont.getText());
        final String sBD    = jTBD.getText();
        
        /*Muestra la forma para simular que esta sincronizando la información*/
        Star.lCargGral = new LoadinGral("Sincronizando...");
        Star.lCargGral.setVisible(true);
                            
        /*Thread para hacer la sincronización*/
        Thread th = new Thread()
        {
            @Override
            public void run()
            {
                /*Sincroniza todo lo que se tenga que sincronizar*/
                vSin(sInst, sUsr, sPass, sBD, bCliens, bProvs, bComps, bFacs, bProds, bServs, bDevs, bCans);
                
                /*Esconde el loading*/
                Star.lCargGral.setVisible(false);
                
                /*Mensajea de éxito*/
                if(!bErr)
                    JOptionPane.showMessageDialog(null, "Sincronización Exitosa", "Sincronización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            }
        };
        th.start();        
        
    }//GEN-LAST:event_jBSincActionPerformed

    
    /*Cuando se gana el foco del teclado en el control del puerto*/
    private void jTPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPort.setSelectionStart(0);jTPort.setSelectionEnd(jTPort.getText().length());        
        
    }//GEN-LAST:event_jTPortFocusGained

    
    /*Cuando se presiona una tecla en el campo del puerto*/
    private void jTPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPortKeyPressed

        
    /*Cuando se tipea una tecla en el campo del puerto*/
    private void jTPortKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTPortKeyTyped

    
    /*Cuando se pierde el foco del teclado en el control del puerto*/
    private void jTPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPort.setCaretPosition(0);
        
        /*Si es cadena vacia entonces*/
        if(jTPort.getText().compareTo("")==0)
            jTPort.setText("1433");
        
    }//GEN-LAST:event_jTPortFocusLost

    
    /*Cuando se presiona una tecla en el check de clientes*/
    private void jCCliensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCliensKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCliensKeyPressed

    
    /*Cuando se presiona una tecla en el check de proveedores*/
    private void jCProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProvsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCProvsKeyPressed

    
    /*Cuando se presiona una tecla en el check de compras*/
    private void jCCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCompsKeyPressed

    
    /*Cuando se presiona un tecla en el check de facturas*/
    private void jCFacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFacsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFacsKeyPressed

    
    /*Cuando se presiona una tecla en el check de productos*/
    private void jCProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCProdsKeyPressed


    /*Cuando se presiona una tecla en el check de servicios*/
    private void jCServsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCServsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCServsKeyPressed

    
    /*Cuando se presiona una tecla en el check de devoluciones*/
    private void jCDevsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDevsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDevsKeyPressed

    
    /*Cuando se presiona una tecla en el check de cancelados*/
    private void jCCansKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCansKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCansKeyPressed

    
    /*Cuando sucede una acción en el check de mostrar contraseña*/
    private void jCMosCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMosCActionPerformed
        
        /*Si esta marcado entonces muestra la contraseña*/
        if(jCMosC.isSelected())
            jPCont.setEchoChar((char)0);
        /*Else, ocultala*/
        else
            jPCont.setEchoChar('*');
        
    }//GEN-LAST:event_jCMosCActionPerformed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProb.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSincMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSincMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSinc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSincMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProb.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSincMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSincMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSinc.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSincMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona F1 presiona el botón de guardar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F1)
            jBSinc.doClick();
        /*Si se presiona F2 presiona el botón de probar conexión*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBProb.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
        {
            /*Si el checkbox de mostrar contraseña esta marcado entonces*/
            if(jCMosC.isSelected())
            {
                /*Desmarcalo y muestra la contraseña*/
                jCMosC.setSelected(false);
                jPCont.setEchoChar('*');        
            }
            else
            {
                /*Marcalo y muestra la contraseña*/
                jCMosC.setSelected(true);
                jPCont.setEchoChar((char)0);                                
            }                        
        }                           
        /*Fin de else if(evt.getKeyCode() == KeyEvent.VK_F4)*/
        /*Si se presiona F6 presiona el botón de sincronizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBSinc.doClick();
                
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBProb;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSinc;
    private javax.swing.JCheckBox jCCans;
    private javax.swing.JCheckBox jCCliens;
    private javax.swing.JCheckBox jCComps;
    private javax.swing.JCheckBox jCDevs;
    private javax.swing.JCheckBox jCFacs;
    private javax.swing.JCheckBox jCMosC;
    private javax.swing.JCheckBox jCProds;
    private javax.swing.JCheckBox jCProvs;
    private javax.swing.JCheckBox jCServs;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPCont;
    private javax.swing.JTextField jTBD;
    private javax.swing.JTextField jTInst;
    private javax.swing.JTextField jTPort;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */