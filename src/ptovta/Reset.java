//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;




/*Clase para controlar el reset*/
public class Reset extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Declara variables de clase*/
    static boolean                  bSi;
    
    
    
    /*Constructor sin argumentos*/
    public Reset() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicializa en false la bandera*/
        bSi = false;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Reiniciar, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Coloca el foco del teclado en el checkbox de marcar todos*/
        jCMarcT.grabFocus();
                                
    }/*Fin de public Pesos() */       
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBGuar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jCMarcT = new javax.swing.JCheckBox();
        jCFluj = new javax.swing.JCheckBox();
        jCEmp = new javax.swing.JCheckBox();
        jCProvs = new javax.swing.JCheckBox();
        jCVtas = new javax.swing.JCheckBox();
        jCComps = new javax.swing.JCheckBox();
        jCProds = new javax.swing.JCheckBox();
        jCCots = new javax.swing.JCheckBox();
        jCLins = new javax.swing.JCheckBox();
        jCMarcs = new javax.swing.JCheckBox();
        jCFabs = new javax.swing.JCheckBox();
        jCImps = new javax.swing.JCheckBox();
        jCConcep = new javax.swing.JCheckBox();
        jCCols = new javax.swing.JCheckBox();
        jCPes = new javax.swing.JCheckBox();
        jCUnids = new javax.swing.JCheckBox();
        jCAlmas = new javax.swing.JCheckBox();
        jCMons = new javax.swing.JCheckBox();
        jCAnaqs = new javax.swing.JCheckBox();
        jCLugs = new javax.swing.JCheckBox();
        jCCarps = new javax.swing.JCheckBox();
        jCOrds = new javax.swing.JCheckBox();
        jCConsecs = new javax.swing.JCheckBox();
        jCClasProv = new javax.swing.JCheckBox();
        jCClasEmp = new javax.swing.JCheckBox();
        jCBD = new javax.swing.JCheckBox();
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

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jCMarcT);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, 110, 30));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Reiniciar");
        jBGuar.setNextFocusableComponent(jBSal);
        jBGuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarMouseExited(evt);
            }
        });
        jBGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarActionPerformed(evt);
            }
        });
        jBGuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuarKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Reiniciar:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, -1));

        jCMarcT.setBackground(new java.awt.Color(255, 255, 255));
        jCMarcT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCMarcT.setSelected(true);
        jCMarcT.setText("Marcar T.");
        jCMarcT.setNextFocusableComponent(jCEmp);
        jCMarcT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMarcTActionPerformed(evt);
            }
        });
        jCMarcT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMarcTKeyPressed(evt);
            }
        });
        jP1.add(jCMarcT, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 90, -1));

        jCFluj.setBackground(new java.awt.Color(255, 255, 255));
        jCFluj.setSelected(true);
        jCFluj.setText("Flujo");
        jCFluj.setNextFocusableComponent(jCCots);
        jCFluj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFlujKeyPressed(evt);
            }
        });
        jP1.add(jCFluj, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, -1));

        jCEmp.setBackground(new java.awt.Color(255, 255, 255));
        jCEmp.setSelected(true);
        jCEmp.setText("Empresas");
        jCEmp.setNextFocusableComponent(jCProvs);
        jCEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEmpKeyPressed(evt);
            }
        });
        jP1.add(jCEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        jCProvs.setBackground(new java.awt.Color(255, 255, 255));
        jCProvs.setSelected(true);
        jCProvs.setText("Proveedores");
        jCProvs.setNextFocusableComponent(jCVtas);
        jCProvs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCProvsKeyPressed(evt);
            }
        });
        jP1.add(jCProvs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jCVtas.setBackground(new java.awt.Color(255, 255, 255));
        jCVtas.setSelected(true);
        jCVtas.setText("Ventas");
        jCVtas.setNextFocusableComponent(jCComps);
        jCVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVtasKeyPressed(evt);
            }
        });
        jP1.add(jCVtas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jCComps.setBackground(new java.awt.Color(255, 255, 255));
        jCComps.setSelected(true);
        jCComps.setText("Compras");
        jCComps.setName(""); // NOI18N
        jCComps.setNextFocusableComponent(jCProds);
        jCComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompsKeyPressed(evt);
            }
        });
        jP1.add(jCComps, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, -1));

        jCProds.setBackground(new java.awt.Color(255, 255, 255));
        jCProds.setSelected(true);
        jCProds.setText("Productos");
        jCProds.setNextFocusableComponent(jCFluj);
        jCProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCProdsKeyPressed(evt);
            }
        });
        jP1.add(jCProds, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 100, -1));

        jCCots.setBackground(new java.awt.Color(255, 255, 255));
        jCCots.setSelected(true);
        jCCots.setText("Cotizaciones");
        jCCots.setNextFocusableComponent(jCLins);
        jCCots.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCotsKeyPressed(evt);
            }
        });
        jP1.add(jCCots, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

        jCLins.setBackground(new java.awt.Color(255, 255, 255));
        jCLins.setSelected(true);
        jCLins.setText("Líneas");
        jCLins.setNextFocusableComponent(jCMarcs);
        jCLins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCLinsKeyPressed(evt);
            }
        });
        jP1.add(jCLins, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 100, -1));

        jCMarcs.setBackground(new java.awt.Color(255, 255, 255));
        jCMarcs.setSelected(true);
        jCMarcs.setText("Marcas");
        jCMarcs.setNextFocusableComponent(jCFabs);
        jCMarcs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMarcsKeyPressed(evt);
            }
        });
        jP1.add(jCMarcs, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 100, -1));

        jCFabs.setBackground(new java.awt.Color(255, 255, 255));
        jCFabs.setSelected(true);
        jCFabs.setText("Fabricantes");
        jCFabs.setNextFocusableComponent(jCImps);
        jCFabs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFabsKeyPressed(evt);
            }
        });
        jP1.add(jCFabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 100, -1));

        jCImps.setBackground(new java.awt.Color(255, 255, 255));
        jCImps.setSelected(true);
        jCImps.setText("Impuestos");
        jCImps.setNextFocusableComponent(jCConcep);
        jCImps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpsKeyPressed(evt);
            }
        });
        jP1.add(jCImps, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 100, -1));

        jCConcep.setBackground(new java.awt.Color(255, 255, 255));
        jCConcep.setSelected(true);
        jCConcep.setText("Conceptos");
        jCConcep.setNextFocusableComponent(jCCols);
        jCConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCConcepKeyPressed(evt);
            }
        });
        jP1.add(jCConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 100, -1));

        jCCols.setBackground(new java.awt.Color(255, 255, 255));
        jCCols.setSelected(true);
        jCCols.setText("Colores");
        jCCols.setNextFocusableComponent(jCPes);
        jCCols.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCColsKeyPressed(evt);
            }
        });
        jP1.add(jCCols, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 100, -1));

        jCPes.setBackground(new java.awt.Color(255, 255, 255));
        jCPes.setSelected(true);
        jCPes.setText("Pesos");
        jCPes.setNextFocusableComponent(jCUnids);
        jCPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPesKeyPressed(evt);
            }
        });
        jP1.add(jCPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 100, -1));

        jCUnids.setBackground(new java.awt.Color(255, 255, 255));
        jCUnids.setSelected(true);
        jCUnids.setText("Unidades");
        jCUnids.setNextFocusableComponent(jCAlmas);
        jCUnids.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCUnidsKeyPressed(evt);
            }
        });
        jP1.add(jCUnids, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 100, -1));

        jCAlmas.setBackground(new java.awt.Color(255, 255, 255));
        jCAlmas.setSelected(true);
        jCAlmas.setText("Almacenes");
        jCAlmas.setNextFocusableComponent(jCLugs);
        jCAlmas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAlmasKeyPressed(evt);
            }
        });
        jP1.add(jCAlmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 100, -1));

        jCMons.setBackground(new java.awt.Color(255, 255, 255));
        jCMons.setSelected(true);
        jCMons.setText("Monedas");
        jCMons.setNextFocusableComponent(jCClasEmp);
        jCMons.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMonsKeyPressed(evt);
            }
        });
        jP1.add(jCMons, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 100, -1));

        jCAnaqs.setBackground(new java.awt.Color(255, 255, 255));
        jCAnaqs.setSelected(true);
        jCAnaqs.setText("Anaqueles");
        jCAnaqs.setNextFocusableComponent(jCMons);
        jCAnaqs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAnaqsKeyPressed(evt);
            }
        });
        jP1.add(jCAnaqs, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 100, -1));

        jCLugs.setBackground(new java.awt.Color(255, 255, 255));
        jCLugs.setSelected(true);
        jCLugs.setText("Lugares");
        jCLugs.setNextFocusableComponent(jCAnaqs);
        jCLugs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCLugsKeyPressed(evt);
            }
        });
        jP1.add(jCLugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 100, -1));

        jCCarps.setBackground(new java.awt.Color(255, 255, 255));
        jCCarps.setSelected(true);
        jCCarps.setText("Carpetas");
        jCCarps.setNextFocusableComponent(jCOrds);
        jCCarps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCarpsKeyPressed(evt);
            }
        });
        jP1.add(jCCarps, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 90, -1));

        jCOrds.setBackground(new java.awt.Color(255, 255, 255));
        jCOrds.setSelected(true);
        jCOrds.setText("Órdenes de Compra");
        jCOrds.setNextFocusableComponent(jCBD);
        jCOrds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOrdsKeyPressed(evt);
            }
        });
        jP1.add(jCOrds, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 150, -1));

        jCConsecs.setBackground(new java.awt.Color(255, 255, 255));
        jCConsecs.setSelected(true);
        jCConsecs.setText("Consecutivos");
        jCConsecs.setNextFocusableComponent(jBGuar);
        jCConsecs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCConsecsKeyPressed(evt);
            }
        });
        jP1.add(jCConsecs, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 120, -1));

        jCClasProv.setBackground(new java.awt.Color(255, 255, 255));
        jCClasProv.setSelected(true);
        jCClasProv.setText("Clasif. Proveedores");
        jCClasProv.setNextFocusableComponent(jCCarps);
        jCClasProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCClasProvKeyPressed(evt);
            }
        });
        jP1.add(jCClasProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 130, -1));

        jCClasEmp.setBackground(new java.awt.Color(255, 255, 255));
        jCClasEmp.setSelected(true);
        jCClasEmp.setText("Clasif. Empresas");
        jCClasEmp.setNextFocusableComponent(jCClasProv);
        jCClasEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCClasEmpKeyPressed(evt);
            }
        });
        jP1.add(jCClasEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 120, -1));

        jCBD.setBackground(new java.awt.Color(255, 255, 255));
        jCBD.setText("Base de Datos");
        jCBD.setNextFocusableComponent(jCConsecs);
        jCBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBDKeyPressed(evt);
            }
        });
        jP1.add(jCBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 140, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 350, 220, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
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
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();                  
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed
                    
    
    /*Cuando se presiona el botón de borrar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Muestra la forma de clave de seguridad*/
        ClavMast cl = new ClavMast(this, 0);
        cl.setVisible(true);
        
        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)
            return;
        
        /*Pregunta al usuario si esta seguro de querer borrar lo seleccionado*/                
        Object[] options = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres resetear lo seleccionado?", "Resetear", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), options, options[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces mensajea*/
        if(sCarp.compareTo("")==0)
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        

        //Declara variables de la base de datos
        Statement   st;                
        String      sQ;                 
        
        /*Borra consecutivos*/
        if(jCConsecs.isSelected())
        {
            /*Resetea los consecutivos*/
            try 
            {                
                sQ = "UPDATE consecs SET "
                        + "consec   = 1, "
                        + "sucu     = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj    = '" + Star.sNoCaj.replace("'", "''") + "'";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                          
             }
        }                
                
        /*Borra clasificación de emps*/
        if(jCClasEmp.isSelected())
        {
            /*Resetea los consecutivos*/
            try 
            {                
                sQ = "DELETE FROM clasemp";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                         
             }
        }   
        
        /*Borra clasificación de provs*/
        if(jCClasProv.isSelected())
        {
            /*Resetea los consecutivos*/
            try 
            {                
                sQ = "DELETE FROM clasprov";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                         
             }
        } 
        
        /*Borra emps*/
        if(jCEmp.isSelected())
        {
            /*Borra los reigstro*/
            try 
            {                
                sQ = "DELETE FROM emps WHERE codemp <> 'SYS'";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        }                
        
        /*Borra proveedores*/
        if(jCProvs.isSelected())
        {
            /*Borra los reigstro*/
            try 
            {                
                sQ = "DELETE FROM provs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        }    
        
        /*Borra órdenes de compra*/
        if(jCOrds.isSelected())
        {
            /*Borra órdenes y sus partidas*/
            try 
            {                
                sQ = "DELETE FROM ords";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
                                
                sQ = "DELETE FROM partords";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
            
            /*Si la carpeta de la carpeta compartida esta definida entonces*/
            if(sCarp.compareTo("")!=0)
            {
                /*Borra la carpeta de ordenes con todo lo que contienen*/    
                try
                {
                    /*Borra el contenido de ordenes*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\Ordenes"));                                             
                    
                    /*Borra el directorio de ordenes*/
                    new File(sCarp + "\\Ordenes").delete();
                                        
                }
                catch(IOException expnIO)
                {    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                    return;                                                                                                              
                }                                                                
            }
            
        }/*Fin de if(jCOrds.isSelected())*/      
        
        /*Borra vtas*/
        if(jCVtas.isSelected())
        {
            /*Borra todas las vtas y sus partidas*/
            try 
            {                
                sQ = "DELETE FROM vtas";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                sQ = "DELETE FROM partvta";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                         
             }
            
            /*Si la carpeta de la carpeta compartida esta definida entonces*/
            if(sCarp.compareTo("")!=0)
            {
                /*Borra la carpeta de facturas y de tickets con todo lo que contienen*/    
                try
                {
                    /*Borra el contenido de facturas*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\Facturas"));                                             
                    
                    /*Borra el directorio de facturas*/
                    new File(sCarp + "\\Facturas").delete();
                    
                    /*Borra el contenido de tickets*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\Tickets"));                                             
                    
                    /*Borra la carpeta de tickets*/
                    new File(sCarp + "\\Tickets").delete();
                }
                catch(IOException expnIO)
                {                        
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                
                }                                                                
            }
            
        }/*Fin de if(jCVtas.isSelected())*/  
        
        /*Borra cotizaciones*/
        if(jCCots.isSelected())
        {
            /*Borra todas las cots y sus partidas*/
            try 
            {                
                sQ = "DELETE FROM cots";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                sQ = "DELETE FROM partcot";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
            
            /*Si la carpeta de la carpeta compartida esta definida entonces*/
            if(sCarp.compareTo("")!=0)
            {
                /*Borra la carpeta de cots con todo lo que contienen*/    
                try
                {
                    /*Borra el contenido de cots*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\Cotizaciones"));                                             
                    
                    /*Borra el directorio de cots*/
                    new File(sCarp + "\\Cotizaciones").delete();
                                        
                }
                catch(IOException expnIO)
                {                    
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                
                }                                                                
            }
            
        }/*Fin de if(jCCots.isSelected())*/  
        
        /*Borra líneas*/
        if(jCLins.isSelected())
        {
            /*Borra todas las líneas*/
            try 
            {                
                sQ = "DELETE FROM lins";                     
                st = con.createStatement();
                st.executeUpdate(sQ);                                
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
            
            /*Si la carpeta de la carpeta compartida esta definida entonces*/
            if(sCarp.compareTo("")!=0)
            {
                /*Borra la carpeta*/    
                try
                {
                    /*Borra el contenido de líneas*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\imgs\\Lineas"));                                             
                    
                    /*Borra el directorio de líneas*/
                    new File(sCarp + "\\imgs\\Lineas").delete();
                                        
                }
                catch(IOException expnIO)
                {                
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                    return;                                                                                          
                }                                                                
            }
            
        }/*Fin de if(jCLins.isSelected())*/  
        
        /*Borra los registros seleccionados*/
        if(jCComps.isSelected())
        {
            /*Borra comprs y sus partidas*/
            try 
            {                
                sQ = "DELETE FROM comprs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                sQ = "DELETE FROM partcomprs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                /*Si la carpeta de la carpeta compartida esta definida entonces*/
                if(sCarp.compareTo("")!=0)
                {
                    try
                    {
                        /*Borra el contenido de la carpeta de comprs*/
                        FileUtils.cleanDirectory(new File(sCarp + "\\Compras"));                                             

                        /*Borra la carpeta de comprs*/
                        new File(sCarp + "\\Compras").delete();
                    }                       
                    catch(IOException expnIO)
                    {                        
                        //Procesa el error
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                
                    }                                 
                }                
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        }  
        
        /*Borra productos*/
        if(jCProds.isSelected())
        {
            /*Borra todos los prods*/
            try 
            {                
                sQ = "DELETE FROM prods";                     
                st = con.createStatement();
                st.executeUpdate(sQ);                                
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
            
            /*Si la carpeta compartida esta definida entonces*/
            if(sCarp.compareTo("")!=0)
            {
                try
                {
                    /*Borra el contenido de la carpeta de prods*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\imgs\\Productos"));                                             
                        
                    /*Borra la carpeta de prods*/
                    new File(sCarp + "\\imgs\\Productos").delete();                                
                    
                    /*Borra el contenido de la carpeta de ficha técnica*/
                    FileUtils.cleanDirectory(new File(sCarp + "\\FTecnica"));                                             
                    
                    /*Borra la carpeta de ficha técnica*/
                    new File(sCarp + "\\FTecnica").delete();
                }   
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                    return;                                                                                                              
                }                                
            }
        } 
        
        /*Borra el flujo*/
        if(jCProvs.isSelected())
        {
            try 
            {                
                sQ = "DELETE FROM fluj";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        }  
        
        /*Si se tienen que borrar las marcas entonces*/
        if(jCMarcs.isSelected())
        {
            /*Borra las marcs*/
            try 
            {                
                sQ = "DELETE FROM marcs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los fabs entonces*/
        if(jCFabs.isSelected())
        {
            /*Borra los fabricantes*/
            try 
            {                
                sQ = "DELETE FROM fabs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los conceps entonces*/
        if(jCConcep.isSelected())
        {
            /*Borra los conceptos*/
            try 
            {                
                sQ = "DELETE FROM conceps";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los colos*/
        if(jCCols.isSelected())
        {
            /*Borra los colores*/
            try 
            {                
                sQ = "DELETE FROM colos";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los pesos entonces*/
        if(jCPes.isSelected())
        {
            /*Borra los pesos*/
            try 
            {                
                sQ = "DELETE FROM pes";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar las unidades entonces*/
        if(jCUnids.isSelected())
        {
            /*Borra el las unidades*/
            try 
            {                
                sQ = "DELETE FROM unids";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los almacenes entonces*/
        if(jCAlmas.isSelected())
        {
            /*Borra los alma*/
            try 
            {                
                sQ = "DELETE FROM alma";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los lugares entonces*/
        if(jCLugs.isSelected())
        {
            /*Borra los lugs*/
            try 
            {                
                sQ = "DELETE FROM lugs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar los anaqueles entonces*/
        if(jCAnaqs.isSelected())
        {
            try 
            {                
                sQ = "DELETE FROM anaqs";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Si se tienen que borrar las monedas entonces*/
        if(jCMons.isSelected())
        {
            /*Borra las monedas*/
            try 
            {                
                sQ = "DELETE FROM mons WHERE mon <> 'MN'";                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
        } 
        
        /*Borra las carpetas en la raíz*/
        if(jCCarps.isSelected())
        {
            /*Si la carpeta de la carpeta compartida esta definida entonces*/        
            if(sCarp.compareTo("")!=0)
            {
                /*Borra la carpeta de facturas y de tickets con todo lo que contienen*/    
                try
                {
                    /*Borra el contenido del directorio*/
                    FileUtils.cleanDirectory(new File(sCarp));                                                                 
                }
                catch(IOException expnIO)
                {    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                    return;                                                                                                                              
                }                
            }
        }
              
        /*Borra la base de datos completa*/
        if(jCBD.isSelected())
        {
            try 
            {                
                sQ = "DROP DATABASE " + Star.sBD;                     
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                          
             }
            
            /*Mensajea de éxito*/
            JOptionPane.showMessageDialog(null, "Base de datos borrada con éxito. El programa se cerrrara.", "Reseteo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Cierra la aplicación*/
            System.exit(0);
        }      
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Registros reseteados con éxito.", "Exito al resetear", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                   
         
    }//GEN-LAST:event_jBGuarActionPerformed

            
    /*Cuando se presiona una tecla en el checkbox de marcar todo*/
    private void jCMarcTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMarcTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMarcTKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de emps*/
    private void jCEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCEmpKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de prveedores*/
    private void jCProvsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProvsKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCProvsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vtas*/
    private void jCVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVtasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCVtasKeyPressed

    
    /*Cunado se presiona una tecla en el checkbox de comprs*/
    private void jCCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCompsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de prods*/
    private void jCProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCProdsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de fluj*/
    private void jCFlujKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFlujKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFlujKeyPressed

    
    /*Cuando sucede una accion en el checkbox de marcar todo*/
    private void jCMarcTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMarcTActionPerformed
        
        /*Si el checkbox esta marcado entonces*/
        if(!jCMarcT.isSelected())
        {
            /*Desmarca todo*/
            jCEmp.setSelected       (false);
            jCProvs.setSelected     (false);
            jCVtas.setSelected      (false);
            jCComps.setSelected     (false);
            jCProds.setSelected     (false);
            jCFluj.setSelected      (false);
            jCCots.setSelected      (false);
            jCLins.setSelected      (false);
            jCMarcs.setSelected     (false);
            jCFabs.setSelected      (false);
            jCImps.setSelected      (false);
            jCConcep.setSelected    (false);
            jCCols.setSelected      (false);
            jCPes.setSelected       (false);
            jCUnids.setSelected     (false);
            jCAlmas.setSelected     (false);
            jCLugs.setSelected      (false);
            jCAnaqs.setSelected     (false);
            jCMons.setSelected      (false);
            jCConsecs.setSelected   (false);
        }
        /*Else, marcalos todos*/
        else
        {
            /*Desmarca todo*/
            jCEmp.setSelected       (true);
            jCProvs.setSelected     (true);
            jCVtas.setSelected      (true);
            jCComps.setSelected     (true);
            jCProds.setSelected     (true);
            jCFluj.setSelected      (true);
            jCCots.setSelected      (true);
            jCLins.setSelected      (true);
            jCMarcs.setSelected     (true);
            jCFabs.setSelected      (true);
            jCImps.setSelected      (true);
            jCConcep.setSelected    (true);
            jCCols.setSelected      (true);
            jCPes.setSelected       (true);
            jCUnids.setSelected     (true);
            jCAlmas.setSelected     (true);
            jCLugs.setSelected      (true);
            jCAnaqs.setSelected     (true);
            jCMons.setSelected      (true);
            jCConsecs.setSelected   (true);
        }
        
    }//GEN-LAST:event_jCMarcTActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de cots*/
    private void jCCotsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCotsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCotsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de carpetas*/
    private void jCCarpsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCarpsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCarpsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mons*/
    private void jCMonsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMonsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMonsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de anaqs*/
    private void jCAnaqsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAnaqsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCAnaqsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de lugs*/
    private void jCLugsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCLugsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCLugsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de alma*/
    private void jCAlmasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAlmasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCAlmasKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de unids*/
    private void jCUnidsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCUnidsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCUnidsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de pes*/
    private void jCPesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPesKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPesKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de colos*/
    private void jCColsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCColsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCColsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de conceps*/
    private void jCConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCConcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCConcepKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de impuestos*/
    private void jCImpsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCImpsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de fabs*/
    private void jCFabsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFabsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFabsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de marcs*/
    private void jCMarcsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMarcsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMarcsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de líneas*/
    private void jCLinsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCLinsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCLinsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de órdenes de compra*/
    private void jCOrdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOrdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCOrdsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de consecutivos*/
    private void jCConsecsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCConsecsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCConsecsKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de clasificación de emps*/
    private void jCClasEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCClasEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCClasEmpKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de clasificación de provs*/
    private void jCClasProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCClasProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCClasProvKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de toda la base de datos*/
    private void jCBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBDKeyPressed

    
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
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
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
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCAlmas;
    private javax.swing.JCheckBox jCAnaqs;
    private javax.swing.JCheckBox jCBD;
    private javax.swing.JCheckBox jCCarps;
    private javax.swing.JCheckBox jCClasEmp;
    private javax.swing.JCheckBox jCClasProv;
    private javax.swing.JCheckBox jCCols;
    private javax.swing.JCheckBox jCComps;
    private javax.swing.JCheckBox jCConcep;
    private javax.swing.JCheckBox jCConsecs;
    private javax.swing.JCheckBox jCCots;
    private javax.swing.JCheckBox jCEmp;
    private javax.swing.JCheckBox jCFabs;
    private javax.swing.JCheckBox jCFluj;
    private javax.swing.JCheckBox jCImps;
    private javax.swing.JCheckBox jCLins;
    private javax.swing.JCheckBox jCLugs;
    private javax.swing.JCheckBox jCMarcT;
    private javax.swing.JCheckBox jCMarcs;
    private javax.swing.JCheckBox jCMons;
    private javax.swing.JCheckBox jCOrds;
    private javax.swing.JCheckBox jCPes;
    private javax.swing.JCheckBox jCProds;
    private javax.swing.JCheckBox jCProvs;
    private javax.swing.JCheckBox jCUnids;
    private javax.swing.JCheckBox jCVtas;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
