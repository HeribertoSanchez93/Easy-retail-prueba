//Paquete
package report;

//Importaciones
import java.awt.Cursor;
import ptovta.Busc;
import ptovta.Login;
import ptovta.Star;
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




/*Clase para controlar el reporteador de todas las compras*/
public class RepOrd extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    /*Declara variables de instancia*/
    private static RepOrd   obj = null;      
    
    /*Bandera para seleccionar todos los check*/
    private boolean         bSel    = false;
    
    
    
    /*Constructor sin argumentos*/
    public RepOrd() 
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
        this.setTitle("Reporte Órdenes de compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFIni = new Date();
        jDateDe.setDate (fFIni);
        jDateA.setDate  (fFIni);
        
        /*Pon el foco del teclado en el campo de la empresa*/
        jTProv.grabFocus();
                
    }/*Fin de public ReportesFacturasCos() */
        
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static RepOrd getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new RepOrd();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Lineas getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTProv = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDateDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jCCo = new javax.swing.JCheckBox();
        jCCa = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();
        jCPe = new javax.swing.JCheckBox();
        jCDesglo = new javax.swing.JCheckBox();
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
        jBSal.setNextFocusableComponent(jCTFech);
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

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBBusc);
        jTProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvFocusLost(evt);
            }
        });
        jTProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProvKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProvKeyTyped(evt);
            }
        });
        jP1.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Proveedor:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 250, -1));

        jDateDe.setEnabled(false);
        jDateDe.setNextFocusableComponent(jDateA);
        jDateDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateDeKeyPressed(evt);
            }
        });
        jP1.add(jDateDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 150, -1));

        jDateA.setEnabled(false);
        jDateA.setNextFocusableComponent(jTProv);
        jDateA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateAKeyPressed(evt);
            }
        });
        jP1.add(jDateA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("De:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 150, -1));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setName(""); // NOI18N
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 140, -1));

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
        jBBusc.setToolTipText("Buscar Proveedor(es)");
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

        jCCo.setBackground(new java.awt.Color(255, 255, 255));
        jCCo.setSelected(true);
        jCCo.setText("CO");
        jCCo.setNextFocusableComponent(jCCa);
        jCCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCoKeyPressed(evt);
            }
        });
        jP1.add(jCCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jCCa.setBackground(new java.awt.Color(255, 255, 255));
        jCCa.setText("CA");
        jCCa.setNextFocusableComponent(jCPe);
        jCCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCaKeyPressed(evt);
            }
        });
        jP1.add(jCCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

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

        jCPe.setBackground(new java.awt.Color(255, 255, 255));
        jCPe.setText("PE");
        jCPe.setNextFocusableComponent(jCTFech);
        jCPe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPeKeyPressed(evt);
            }
        });
        jP1.add(jCPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, -1, -1));

        jCDesglo.setBackground(new java.awt.Color(255, 255, 204));
        jCDesglo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDesglo.setText("Desglosado");
        jCDesglo.setToolTipText("Desgloza el Reporte Generado Mostrando las Partidas");
        jCDesglo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDesgloKeyPressed(evt);
            }
        });
        jP1.add(jCDesglo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 90, -1));

        jBSelT.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT.setToolTipText("Seleccionar/Deseleccionar Todos los Estados de Orden de Compra");
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
        jP1.add(jBSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 20, 10));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
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
        obj = null;       
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProv.getText(), 3, jTProv, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());        
        
    }//GEN-LAST:event_jTProvFocusGained
                
    
    /*Cuando se tipea una tecla en el campo de descripción del proveedor*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
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

        /*Comprueba si el proveedor existe*/        
        try
        {
            sQ = "SELECT prov FROM provs WHERE prov <> '-' AND CONCAT_WS('', ser, prov ) = '" + jTProv.getText() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(!rs.next() && jTProv.getText().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTProv.grabFocus();               
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
        /*Else esta marcado el checkbox de todas las fechas*/
        else
        {
            /*Resetea las fechas*/
            sFA     = "";
            sFDe    = "";
        }

        /*Si por lo menos un estado de compra no esta seleccionado entonces*/
        if(!jCCo.isSelected() && !jCPe.isSelected() && !jCCa.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un estado.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jCCo.grabFocus();                
            return;
        }
        
        /*Obtiene los tipos de òrdenes de compra que tiene que mostrar*/
        String sTipCom  = "";
        if(jCCo.isSelected())
            sTipCom += "'CO',";
        if(jCCa.isSelected())
            sTipCom += "'CA',";
        if(jCPe.isSelected())
            sTipCom += "'PE',";
           
        /*Quita la última coma del tipo de compra*/
        sTipCom = sTipCom.substring(0, sTipCom.length() - 1);
        
        /*Determina si serà desglozado o no*/        
        String sTipRep  = "/jasreport/rptRepOrd.jrxml";
        if(jCDesglo.isSelected())
            sTipRep     = "/jasreport/rptRepOrdDet.jrxml";
        
        /*Declara variables final para el thread*/
        final String sCodProvFi      = jTProv.getText();
        final String sFAFi           = sFA;
        final String sFDeFi          = sFDe;
        final String sTipComFi       = sTipCom;
        final String sTipRepFi       = sTipRep;
        
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
                    pa.put("PROV",      sCodProvFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("F_D",       sFDeFi);
                    pa.put("F_A",       sFAFi);
                    pa.put("TIPESTAD",  sTipComFi);
                    
                    /*Compila el reporte y muestralo maximizado*/
                    JasperReport ja = JasperCompileManager.compileReport(getClass().getResourceAsStream(sTipRepFi));
                    JasperPrint pr  = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v  = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte De Órdenes de compras");
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

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, null, null, "", null);
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

    
    /*Cuando se presiona una tecla en el checkbox de confirmados*/
    private void jCCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cancelados*/
    private void jCCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCaKeyPressed

        
    
    /*Cuando el mouse entra en el campo del link de ayuda*/
    private void jLAyuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando el mouse sale del campo del link de ayuda*/
    private void jLAyuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));	
        
    }//GEN-LAST:event_jLAyuMouseExited

    
    /*Cuando se pierde el foco del teclado en el campo del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se presiona una tecla en el check de pendiente*/
    private void jCPeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPeKeyPressed

    
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
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
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
            jCCa.setSelected(false);
            jCPe.setSelected(false);
            jCCo.setSelected(false);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCCa.setSelected(true);
            jCPe.setSelected(true);
            jCCo.setSelected(true);

            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }

    }//GEN-LAST:event_jBSelTActionPerformed

    private void jBSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSelTKeyPressed
                      
       
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
    private com.toedter.calendar.JDateChooser jDateA;
    private com.toedter.calendar.JDateChooser jDateDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTProv;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
