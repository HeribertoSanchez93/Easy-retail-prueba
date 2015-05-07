//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;




/*Clase para convertir un archivo PDF a otros formatos*/
public class EasyConv extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    
    
    /*Constructor sin argumentos*/
    public EasyConv() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBConv);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Easy convertidor, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo de la ruta*/
        jTRut.grabFocus();
                
    }/*Fin de public EasyConv() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLAyu = new javax.swing.JLabel();
        jBRut = new javax.swing.JButton();
        jTRut = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCTex = new javax.swing.JCheckBox();
        jCExc = new javax.swing.JCheckBox();
        jCWor = new javax.swing.JCheckBox();
        jBConv = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jBExpo = new javax.swing.JButton();
        jTExpo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 120, 20));

        jBRut.setBackground(new java.awt.Color(255, 255, 255));
        jBRut.setText("..");
        jBRut.setToolTipText("Buscar Archivo .PDF de Cuentas Contables");
        jBRut.setNextFocusableComponent(jTRut);
        jBRut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRutMouseExited(evt);
            }
        });
        jBRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRutActionPerformed(evt);
            }
        });
        jBRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRutKeyPressed(evt);
            }
        });
        jP1.add(jBRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 30, 20));

        jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRut.setNextFocusableComponent(jCWor);
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
        jP1.add(jTRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 190, 20));

        jLabel4.setText("Archivo PDF a Convertir:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 180, -1));

        jCTex.setBackground(new java.awt.Color(255, 255, 255));
        jCTex.setText("Texto Plano");
        jCTex.setNextFocusableComponent(jCExc);
        jCTex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCTexKeyPressed(evt);
            }
        });
        jP1.add(jCTex, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 120, -1));

        jCExc.setBackground(new java.awt.Color(255, 255, 255));
        jCExc.setText("Excel");
        jCExc.setNextFocusableComponent(jBExpo);
        jCExc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCExcKeyPressed(evt);
            }
        });
        jP1.add(jCExc, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 120, -1));

        jCWor.setBackground(new java.awt.Color(255, 255, 255));
        jCWor.setText("Word");
        jCWor.setNextFocusableComponent(jCTex);
        jCWor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCWorKeyPressed(evt);
            }
        });
        jP1.add(jCWor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 120, -1));

        jBConv.setBackground(new java.awt.Color(255, 255, 255));
        jBConv.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBConv.setForeground(new java.awt.Color(0, 102, 0));
        jBConv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/convepdf.png"))); // NOI18N
        jBConv.setText("Convertir");
        jBConv.setToolTipText("Convertir el Archivo");
        jBConv.setNextFocusableComponent(jBSal);
        jBConv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConvMouseExited(evt);
            }
        });
        jBConv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConvActionPerformed(evt);
            }
        });
        jBConv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConvKeyPressed(evt);
            }
        });
        jP1.add(jBConv, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jBRut);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 110, 30));

        jBExpo.setBackground(new java.awt.Color(255, 255, 255));
        jBExpo.setText("..");
        jBExpo.setToolTipText("Buscar Archivo .PDF de Cuentas Contables");
        jBExpo.setNextFocusableComponent(jTExpo);
        jBExpo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBExpoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBExpoMouseExited(evt);
            }
        });
        jBExpo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExpoActionPerformed(evt);
            }
        });
        jBExpo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExpoKeyPressed(evt);
            }
        });
        jP1.add(jBExpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 30, 20));

        jTExpo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTExpo.setNextFocusableComponent(jBConv);
        jTExpo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTExpoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTExpoFocusLost(evt);
            }
        });
        jTExpo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExpoKeyPressed(evt);
            }
        });
        jP1.add(jTExpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 190, 20));

        jLabel5.setText("Ruta de exportación:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 180, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
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

    
    /*Cuando el mouse entra en el botón de búscar ruta*/
    private void jBRutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRutMouseEntered

        /*Cambia el color del fondo del botón*/
        jBRut.setBackground(Star.colBot);

    }//GEN-LAST:event_jBRutMouseEntered

    
    /*Cuando el mouse sale del botón de búscar ruta*/
    private void jBRutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRutMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBRut.setBackground(colOri);

    }//GEN-LAST:event_jBRutMouseExited

    
    /*Cuando se presiona el botón de búscar archivo a convertir*/
    private void jBRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRutActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final javax.swing.JFileChooser fc   = new javax.swing.JFileChooser  ();
        fc.setDialogTitle                   ("Buscar PDF");
        fc.setAcceptAllFileFilterUsed       (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)!=javax.swing.JFileChooser.APPROVE_OPTION)
            return;

        /*Si el archivo no es PDF entonces*/
        if(!fc.getSelectedFile().getName().toLowerCase().endsWith(".pdf"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo PDF.", "PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Coloca la ruta completa en el control de la cuenta*/
        jTRut.setText(fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName());
            jTRut.setCaretPosition(0);

    }//GEN-LAST:event_jBRutActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar ruta*/
    private void jBRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRutKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBRutKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la ruta*/
    private void jTRutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTRut.setSelectionStart(0);jTRut.setSelectionEnd(jTRut.getText().length());

    }//GEN-LAST:event_jTRutFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la ruta*/
    private void jTRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTRut.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTRut.getText().compareTo("")!=0)
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTRutFocusLost

    
    /*Cuando se presiona una tecla en el campo de la ruta*/
    private void jTRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTRutKeyPressed

    
    /*Cuando el mouse entra en el botón de convertir*/
    private void jBConvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConvMouseEntered

        /*Cambia el color del fondo del botón*/
        jBConv.setBackground(Star.colBot);

    }//GEN-LAST:event_jBConvMouseEntered

    
    /*Cuando el mouse sale del botón de convertir*/
    private void jBConvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConvMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBConv.setBackground(colOri);

    }//GEN-LAST:event_jBConvMouseExited

    
    /*Cuando se presiona el botón de convertir*/
    private void jBConvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConvActionPerformed

        /*Si hay cadena vacia en el campo de la ruta entonces*/
        if(jTRut.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La ruta esta vacia.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTRut.grabFocus();
            return;
        }

        /*Si hay cadena vacia en el campo de la ruta a exportar entonces*/
        if(jTExpo.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTExpo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La ruta a donde exportar esta vacia.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTExpo.grabFocus();
            return;
        }
        
        /*Si el archivo no existe entonces*/
        if(!new File(jTRut.getText().trim()).exists())
        {
            /*Coloca el borde rojo*/
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La ruta al archivo no existe.", "PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTRut.grabFocus();
            return;
        }
        
        /*Si la carpeta a donde exportar no existe entonces*/
        if(!new File(jTExpo.getText().trim()).exists())
        {
            /*Coloca el borde rojo*/
            jTExpo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La ruta a donde exportar no existe.", "PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTExpo.grabFocus();
            return;
        }
        
        /*Si no ha seleccionado ninguna opción para convertir entonces*/
        if(!jCWor.isSelected() && !jCTex.isSelected() && !jCExc.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un formato a convertir.", "Formato", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jCWor.grabFocus();
            return;
        }
        
        /*Pregunta al usuario si esta seguro*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la conversión?", "Conversión a Formatos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Determina a que formatos convertirlos*/
        boolean bWor    = false;
        if(jCWor.isSelected())
            bWor        = true;
        boolean bTex    = false;
        if(jCTex.isSelected())
            bTex        = true;
        boolean bExc    = false;
        if(jCExc.isSelected())
            bExc        = true;
        
        /*Declara variables final para el thread*/
        final String sRutFi   = jTRut.getText().trim();                
        final String sExpoFi  = jTExpo.getText().trim();                
        final boolean bWorFi  = bWor;
        final boolean bTexFi  = bTex;
        final boolean bExcFi  = bExc;
        
        /*Realiza todo el proceso en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Carga el documento del archivo*/
                PDDocument p;        
                try
                {
                    p = PDDocument.load(new File(sRutFi));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                        
                }            

                /*Objeto para leer los datos del PDF*/
                PDFTextStripper stripper;        
                try
                {
                    stripper = new PDFTextStripper();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                        
                }

                /*Leera desde la primera hoja hasta la última*/
                stripper.setStartPage(1);
                stripper.setEndPage(p.getNumberOfPages());

                /*Si tiene que crear un archivo de word entonces*/ 
                if(bWorFi)
                {
                    /*Abre el archivo de word*/
                    java.io.BufferedWriter wr;                
                    try
                    {
                        wr = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(new File(sExpoFi + "\\exportacion.doc"))));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }

                    /*Escribe el buffer del PDF en el fichero abierto*/
                    try
                    {
                        stripper.writeText(p, wr);
                        wr.close();
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }
                    
                }/*Fin de if(bWorFi)*/                    

                /*Si tiene que crear un archivo de texto entonces*/ 
                if(bTexFi)
                {
                    /*Abre el archivo de texto*/
                    java.io.BufferedWriter wr;                
                    try
                    {
                        wr = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(new File(sExpoFi + "\\exportacion.txt"))));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }

                    /*Escribe el buffer del PDF en el fichero abierto*/
                    try
                    {
                        stripper.writeText(p, wr);
                        wr.close();
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }
                    
                }/*Fin de if(bTexFi)*/                    
                
                /*Si tiene que crear un archivo de excel entonces*/ 
                if(bExcFi)
                {
                    /*Abre el archivo de excel*/
                    java.io.BufferedWriter wr;                
                    try
                    {
                        wr = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(new File(sExpoFi + "\\exportacion.xls"))));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }

                    /*Escribe el buffer del PDF en el fichero abierto*/
                    try
                    {
                        stripper.writeText(p, wr);
                        wr.close();
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                        
                    }
                    
                }/*Fin de if(bExcFi)*/                    
                
                /*Cierra el documento PDF*/
                try
                {
                    p.close();
                }   
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                        
                }   

                /*Mensajea de éxito*/
                JOptionPane.showMessageDialog(null, "Conversión(es) terminada(s) con éxito.", "Exito en Conversión(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            }/*Fin de public void run()*/
            
        }).start();

    }//GEN-LAST:event_jBConvActionPerformed

    
    /*Cuando se presiona una tecla en el botón de convertir*/
    private void jBConvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBConvKeyPressed

    
    /*Cuando se presiona uan tecla en el check de word*/
    private void jCWorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCWorKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCWorKeyPressed

    
    /*Cuando se presiona una tecla en el check de texto plano*/
    private void jCTexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCTexKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCTexKeyPressed

    
    /*Cuando se presiona una tecla en el check de excel*/
    private void jCExcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCExcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCExcKeyPressed

    
    /*Cuando el mouse entra en el botón de salir*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered

        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);

    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón de salir*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);

    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura y cierra la forma*/
        System.gc();
        this.dispose();        

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar exportación*/
    private void jBExpoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExpoMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBExpo.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBExpoMouseEntered

    
    /*Cuando el mouse sale del botón de búscar ruta a exportar*/
    private void jBExpoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExpoMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBExpo.setBackground(colOri);
        
    }//GEN-LAST:event_jBExpoMouseExited

    
    /*Cuando se presiona el botón de ruta de exportación*/
    private void jBExpoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExpoActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final javax.swing.JFileChooser fc   = new javax.swing.JFileChooser();
        fc.setDialogTitle                   ("Guardar en...");        
        fc.setFileSelectionMode             (javax.swing.JFileChooser.DIRECTORIES_ONLY);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)!=javax.swing.JFileChooser.APPROVE_OPTION)
            return;

        /*Coloca la ruta completa en el control de la ruta a exportar*/
        jTExpo.setText(fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName());
            jTExpo.setCaretPosition(0);
        
    }//GEN-LAST:event_jBExpoActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar la ruta de exportación*/
    private void jBExpoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExpoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBExpoKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de exportación*/
    private void jTExpoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExpoFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTExpo.setSelectionStart(0);jTExpo.setSelectionEnd(jTExpo.getText().length());
        
    }//GEN-LAST:event_jTExpoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la exportación*/
    private void jTExpoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExpoFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExpo.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTExpo.getText().compareTo("")!=0)
            jTExpo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTExpoFocusLost

    
    /*Cuando se presiona una tecla en el campo de la exportación*/
    private void jTExpoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExpoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExpoKeyPressed
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();       
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBConv;
    private javax.swing.JButton jBExpo;
    private javax.swing.JButton jBRut;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCExc;
    private javax.swing.JCheckBox jCTex;
    private javax.swing.JCheckBox jCWor;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTExpo;
    private javax.swing.JTextField jTRut;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
