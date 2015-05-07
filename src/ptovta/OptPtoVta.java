//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;




/*Clase para controlar los opciones del punto de vta*/
public class OptPtoVta extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color        colOri;
    
    
    
    
    /*Constructor sin argumentos*/
    public OptPtoVta() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Opciones, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el primer botón que es cort x*/
        jBCortX.grabFocus();                        
    }

            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBCortZ = new javax.swing.JButton();
        jBCortX = new javax.swing.JButton();
        jBImpVtas = new javax.swing.JButton();
        jBCanVtas = new javax.swing.JButton();
        jBVtas = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBCompa = new javax.swing.JButton();
        jBDevP = new javax.swing.JButton();
        jBDev = new javax.swing.JButton();

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
        jBSal.setNextFocusableComponent(jBCortX);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 240, 110, 30));

        jBCortZ.setBackground(new java.awt.Color(255, 255, 255));
        jBCortZ.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCortZ.setForeground(new java.awt.Color(0, 102, 0));
        jBCortZ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tijera.gif"))); // NOI18N
        jBCortZ.setText("Corte Z");
        jBCortZ.setToolTipText("Generar corte de caja Z");
        jBCortZ.setNextFocusableComponent(jBImpVtas);
        jBCortZ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCortZMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCortZMouseExited(evt);
            }
        });
        jBCortZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCortZActionPerformed(evt);
            }
        });
        jBCortZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCortZKeyPressed(evt);
            }
        });
        jP1.add(jBCortZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 150, 50));

        jBCortX.setBackground(new java.awt.Color(255, 255, 255));
        jBCortX.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCortX.setForeground(new java.awt.Color(0, 102, 0));
        jBCortX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tijera.gif"))); // NOI18N
        jBCortX.setText("Corte X");
        jBCortX.setToolTipText("Ver corte de caja X");
        jBCortX.setNextFocusableComponent(jBCortZ);
        jBCortX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCortXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCortXMouseExited(evt);
            }
        });
        jBCortX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCortXActionPerformed(evt);
            }
        });
        jBCortX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCortXKeyPressed(evt);
            }
        });
        jP1.add(jBCortX, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 50));

        jBImpVtas.setBackground(new java.awt.Color(255, 255, 255));
        jBImpVtas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImpVtas.setForeground(new java.awt.Color(0, 102, 0));
        jBImpVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impres.png"))); // NOI18N
        jBImpVtas.setText("Imprimir");
        jBImpVtas.setToolTipText("Imprimir Venta(s) ");
        jBImpVtas.setNextFocusableComponent(jBVtas);
        jBImpVtas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpVtasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpVtasMouseExited(evt);
            }
        });
        jBImpVtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpVtasActionPerformed(evt);
            }
        });
        jBImpVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpVtasKeyPressed(evt);
            }
        });
        jP1.add(jBImpVtas, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 150, 50));

        jBCanVtas.setBackground(new java.awt.Color(255, 255, 255));
        jBCanVtas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCanVtas.setForeground(new java.awt.Color(0, 102, 0));
        jBCanVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBCanVtas.setText("Cancelar");
        jBCanVtas.setToolTipText("Cancelar venta(s) ");
        jBCanVtas.setNextFocusableComponent(jBDev);
        jBCanVtas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCanVtasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCanVtasMouseExited(evt);
            }
        });
        jBCanVtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCanVtasActionPerformed(evt);
            }
        });
        jBCanVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCanVtasKeyPressed(evt);
            }
        });
        jP1.add(jBCanVtas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 50));

        jBVtas.setBackground(new java.awt.Color(255, 255, 255));
        jBVtas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVtas.setForeground(new java.awt.Color(0, 102, 0));
        jBVtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/vtas.png"))); // NOI18N
        jBVtas.setText("Ventas");
        jBVtas.setToolTipText("Ver ventas");
        jBVtas.setNextFocusableComponent(jBCompa);
        jBVtas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVtasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVtasMouseExited(evt);
            }
        });
        jBVtas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVtasActionPerformed(evt);
            }
        });
        jBVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVtasKeyPressed(evt);
            }
        });
        jP1.add(jBVtas, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 150, 50));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 270, 110, 30));

        jBCompa.setBackground(new java.awt.Color(255, 255, 255));
        jBCompa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCompa.setForeground(new java.awt.Color(0, 102, 0));
        jBCompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/compa.png"))); // NOI18N
        jBCompa.setText("Comp.");
        jBCompa.setToolTipText("Compatibilidades de productos");
        jBCompa.setNextFocusableComponent(jBCanVtas);
        jBCompa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompaActionPerformed(evt);
            }
        });
        jBCompa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCompaKeyPressed(evt);
            }
        });
        jP1.add(jBCompa, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 150, 50));

        jBDevP.setBackground(new java.awt.Color(255, 255, 255));
        jBDevP.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDevP.setForeground(new java.awt.Color(0, 102, 0));
        jBDevP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devs.png"))); // NOI18N
        jBDevP.setText("Dev.Parcial");
        jBDevP.setToolTipText("Devolución parcial ventas");
        jBDevP.setNextFocusableComponent(jBSal);
        jBDevP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDevPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDevPMouseExited(evt);
            }
        });
        jBDevP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDevPActionPerformed(evt);
            }
        });
        jBDevP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDevPKeyPressed(evt);
            }
        });
        jP1.add(jBDevP, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 150, 50));

        jBDev.setBackground(new java.awt.Color(255, 255, 255));
        jBDev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDev.setForeground(new java.awt.Color(0, 102, 0));
        jBDev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devs.png"))); // NOI18N
        jBDev.setText("Devolución");
        jBDev.setToolTipText("Devolución ventas");
        jBDev.setNextFocusableComponent(jBDevP);
        jBDev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDevMouseExited(evt);
            }
        });
        jBDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDevActionPerformed(evt);
            }
        });
        jBDev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDevKeyPressed(evt);
            }
        });
        jP1.add(jBDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 150, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
           
    
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

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();        

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón del cort Z*/
    private void jBCortZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCortZActionPerformed

        /*Preguntar al usuario si esta seguro de querer imprimir el cort Z*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres generar el cort Z?", "Corte Z", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Cierra la forma*/
        dispose();       
        
        /*Llama a la función para hacer el corte Z*/
        Star.vCortX("Z","\\Cortes Z");
        
        /*Llama al recolector de basura*/
        System.gc();        

    }//GEN-LAST:event_jBCortZActionPerformed
               
    
    /*Cuando se presiona una tecla en el botón de cort Z*/
    private void jBCortZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCortZKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCortZKeyPressed
           
    
    /*Cuando se presiona el botón de cort X*/
    private void jBCortXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCortXActionPerformed

        /*Preguntar al usuario si esta seguro de querer imprimir el cort X*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres imprimir el cort X?", "Corte X", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Cierra la forma*/
        dispose();                
                  
        /*Llama a la función para hacer el corte X*/
        Star.vCortX("X","\\Cortes X");
        
        /*Llama al recolector de basura*/
        System.gc();
                
    }//GEN-LAST:event_jBCortXActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cort X*/
    private void jBCortXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCortXKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCortXKeyPressed

    
    /*Cuando se presiona el botón de imprimir vtas*/
    private void jBImpVtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpVtasActionPerformed

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Cierra esta forma*/
        this.dispose();

        /*Muestra la forma para imprimir vtas*/
        ImprVtas im = new ImprVtas();
        im.setVisible(true);

        /*Llama al recolector de basura*/
        System.gc();        
        
    }//GEN-LAST:event_jBImpVtasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de imprimir vtas*/
    private void jBImpVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpVtasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBImpVtasKeyPressed

    
    /*Cuando se presiona el botón de cancelar tickets*/
    private void jBCanVtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCanVtasActionPerformed

        /*Cierra el formulario*/
        dispose();

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Muestra la forma para cancelar tickets*/
        CanVtas ca = new CanVtas();
        ca.setVisible(true);       
        
    }//GEN-LAST:event_jBCanVtasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cancelar vtas*/
    private void jBCanVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCanVtasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCanVtasKeyPressed

    
    /*Cuando se presiona el botón de ver tickets*/
    private void jBVtasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVtasActionPerformed

        /*Muestra el formulario para ver las ventas*/
        VVtas ve = new VVtas();
        ve.setVisible(true);

    }//GEN-LAST:event_jBVtasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tickets*/
    private void jBVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVtasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVtasKeyPressed

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

    
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
    private void jBCortXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCortXMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCortX.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCortXMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCortZMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCortZMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCortZ.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCortZMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpVtasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpVtasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImpVtas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpVtasMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCanVtasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanVtasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCanVtas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCanVtasMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVtasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVtasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVtas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVtasMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCortXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCortXMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCortX.setBackground(colOri);
        
    }//GEN-LAST:event_jBCortXMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCortZMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCortZMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCortZ.setBackground(colOri);
        
    }//GEN-LAST:event_jBCortZMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpVtasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpVtasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImpVtas.setBackground(colOri);
        
    }//GEN-LAST:event_jBImpVtasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCanVtasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanVtasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCanVtas.setBackground(colOri);
        
    }//GEN-LAST:event_jBCanVtasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVtasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVtasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVtas.setBackground(colOri);
        
    }//GEN-LAST:event_jBVtasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el botón de ver compatibilidades*/
    private void jBCompaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCompaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCompaKeyPressed

    
    /*Cuando se presiona el botón de ver compatiblidades*/
    private void jBCompaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompaActionPerformed
        
        /*Cierra la forma actual*/
        dispose();
        
        /*Muestra la forma de compatibilidades*/
        TabCompa t = new TabCompa();
        t.setVisible(true);
        
    }//GEN-LAST:event_jBCompaActionPerformed

        
    /*Cuando el mouse entra en el botón de devolución parcial*/
    private void jBDevPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevPMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBDevP.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevPMouseEntered

    
    /*Cuando el mouse sale del botón de devolución parcial*/
    private void jBDevPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevPMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBDevP.setBackground(colOri);
        
    }//GEN-LAST:event_jBDevPMouseExited

    
    /*Cuando se presiona el botón de devolución parcial*/
    private void jBDevPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevPActionPerformed
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene si tiene habilitada la configuración para poder hacer devoluciones completas en el punto de venta*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'devvtaspto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si tiene permitido entonces coloca la bandera en true*/
                if(rs.getString("val").compareTo("1")==0)
                    bSi = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Si no tiene permido hacer devoluciones desde el punto de venta entonces*/
        if(!bSi)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se tiene permitido hacer devoluciones parciales desde el punto de venta.", "Devoluciones Parciales", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Cierra el formulario*/
        dispose();

        /*Llama al recolector de basura*/
        System.gc();

        /*Muestra la forma de devolución parcial*/
        DevPVtaPto d = new DevPVtaPto();
        d.setVisible(true);
        
    }//GEN-LAST:event_jBDevPActionPerformed

    
    /*Cuando se presiona una tecla en el botón de devolución parcial*/
    private void jBDevPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDevPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDevPKeyPressed

    
    /*Cuando el mouse entra en el botón de devolución*/
    private void jBDevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDev.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevMouseEntered

    
    /*Cuando el mouse sale del botón de devolución*/
    private void jBDevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBDev.setBackground(colOri);
        
    }//GEN-LAST:event_jBDevMouseExited

    
    /*Cuando se presiona el botón de devolución*/
    private void jBDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevActionPerformed
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene si tiene habilitada la configuración para poder hacer devoluciones completas en el punto de venta*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'devvtaspto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si tiene permitido entonces coloca la bandera en true*/
                if(rs.getString("val").compareTo("1")==0)
                    bSi = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Si no tiene permido hacer devoluciones desde el punto de venta entonces*/
        if(!bSi)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se tiene permitido hacer devoluciones desde el punto de venta.", "Devoluciones Completas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Cierra el formulario*/
        dispose();

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Abre la forma para poder hacer devoluciones de ventas*/
        DevVtaPto d = new DevVtaPto();
        d.setVisible(true);
        
    }//GEN-LAST:event_jBDevActionPerformed

    
    /*Cuando se presiona una tecla en el botón de devolución*/
    private void jBDevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDevKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDevKeyPressed

        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCanVtas;
    private javax.swing.JButton jBCompa;
    private javax.swing.JButton jBCortX;
    private javax.swing.JButton jBCortZ;
    private javax.swing.JButton jBDev;
    private javax.swing.JButton jBDevP;
    private javax.swing.JButton jBImpVtas;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBVtas;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JPanel jP1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
