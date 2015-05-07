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




/*Clase para controlar el reporteador del log de almas*/
public class RepLogGral extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Bandera para seleccionar todos los check*/
    private boolean bSel    = false;
    
    /*Contiene la descripción del objeto*/
    private final String sObjec;
    
    /*Contiene el nombre de la tabla*/
    private final String sTab;
    
    /*Contiene el nombre del campo*/
    private final String sCod;
    
    /*Contiene el nombre del reporte*/
    private final String sReport;
    
    /*Contiene el número para el buscador*/
    private final int    iNum;
    
    /*Constructor sin argumentos*/
    public RepLogGral(String sTa, String sObj, String sCo, String sTit, String sRepo, String sLab, int iNu)
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Obtiene los datos del otro formulario*/
        sTab    = sTa;
        sObjec  = sObj;
        sCod    = sCo;
        sReport = sRepo;
        iNum    = iNu;
        
        /*Coloca el títlo del label de búsqueda*/
        jLGral.setText(sLab);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBAbr);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle(sTit + ", Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFIni = new Date();
        jDDe.setDate (fFIni);
        jDA.setDate  (fFIni);
        
        /*Pon el foco del teclado en el campo de El usuario*/
        jTUsr.grabFocus();
        
    }/*Fin de public RepLogGral()*/
        
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jDDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jCDel = new javax.swing.JCheckBox();
        jCAgre = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jTUsr = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTCaj = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTSucu = new javax.swing.JTextField();
        jCMod = new javax.swing.JCheckBox();
        jLGral = new javax.swing.JLabel();
        jTCod = new javax.swing.JTextField();
        jBCod = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
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
        jBSal.setNextFocusableComponent(jCAgre);
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

        jDDe.setEnabled(false);
        jDDe.setNextFocusableComponent(jDA);
        jDDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDDeKeyPressed(evt);
            }
        });
        jP1.add(jDDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 150, -1));

        jDA.setEnabled(false);
        jDA.setNextFocusableComponent(jBAbr);
        jDA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDAKeyPressed(evt);
            }
        });
        jP1.add(jDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("De:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 150, -1));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setNextFocusableComponent(jDDe);
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 130, -1));

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

        jCDel.setBackground(new java.awt.Color(255, 255, 255));
        jCDel.setText("Borrar");
        jCDel.setToolTipText("Confirmados");
        jCDel.setNextFocusableComponent(jCMod);
        jCDel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDelKeyPressed(evt);
            }
        });
        jP1.add(jCDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 80, -1));

        jCAgre.setBackground(new java.awt.Color(255, 255, 255));
        jCAgre.setSelected(true);
        jCAgre.setText("Agregar");
        jCAgre.setToolTipText("Pendientes");
        jCAgre.setNextFocusableComponent(jCDel);
        jCAgre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAgreKeyPressed(evt);
            }
        });
        jP1.add(jCAgre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 80, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Usuario:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 130, -1));

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
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 100, 20));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Usuario(s)");
        jBUsr.setNextFocusableComponent(jTSucu);
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
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 30, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Sucursal:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 130, -1));

        jTCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCaj.setNextFocusableComponent(jTCod);
        jTCaj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCajFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCajFocusLost(evt);
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
        jP1.add(jTCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 130, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Caja:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 130, -1));

        jTSucu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSucu.setNextFocusableComponent(jTCaj);
        jTSucu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSucuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSucuFocusLost(evt);
            }
        });
        jTSucu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSucuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSucuKeyTyped(evt);
            }
        });
        jP1.add(jTSucu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 130, 20));

        jCMod.setBackground(new java.awt.Color(255, 255, 255));
        jCMod.setText("Modificar");
        jCMod.setName(""); // NOI18N
        jCMod.setNextFocusableComponent(jTUsr);
        jCMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCModKeyPressed(evt);
            }
        });
        jP1.add(jCMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 100, -1));

        jLGral.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLGral.setText("GENERAL:");
        jP1.add(jLGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 130, -1));

        jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCod.setNextFocusableComponent(jBCod);
        jTCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodFocusLost(evt);
            }
        });
        jTCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodKeyTyped(evt);
            }
        });
        jP1.add(jTCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 100, 20));

        jBCod.setBackground(new java.awt.Color(255, 255, 255));
        jBCod.setText("jButton1");
        jBCod.setToolTipText("Buscar Almacén(es)");
        jBCod.setName(""); // NOI18N
        jBCod.setNextFocusableComponent(jCTFech);
        jBCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCodMouseExited(evt);
            }
        });
        jBCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCodActionPerformed(evt);
            }
        });
        jBCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCodKeyPressed(evt);
            }
        });
        jP1.add(jBCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 30, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 110, 20));

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
        jP1.add(jBSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 20, 10));

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
        
        /*Cerrar el formulario*/
        this.dispose();              
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

                                  
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

        /*Comprueba si el usuario existe*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText().replace(" ", "").trim() + "'";                       
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next()&& jTUsr.getText().replace(" ", "").compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario: " + jTUsr.getText().replace(" ", "") + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

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
                
        /*Comprueba si el código existe*/        
        try
        {
            sQ = "SELECT " + sCod + " FROM " + sTab + " WHERE " + sCod + " = '" + jTCod.getText().replace(" ", "").trim() + "'";                       
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next()&& jTCod.getText().replace(" ", "").compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, sObjec  + ": " + jTCod.getText().replace(" ", "") + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTCod.grabFocus();               
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
        /*Else, esta seleccionado todas las fechas entonces*/
        else
        {
            /*Establece las fechas en cadena vacia*/
            sFA     = "";
            sFDe    = "";
        }
            
        /*Si no esta seleccionado por lo menos agregar, modificar o borrar entonces*/
        if(!jCDel.isSelected() && !jCAgre.isSelected() && !jCMod.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos agregar, modificar o borrar.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jCAgre.grabFocus();                
            return;
        }
        
        /*Determina si mostrarán los agregados*/
        String sEst      = "1";
        if(jCAgre.isSelected())
            sEst         = "0";
        
        /*Determina si mostrarán los modificador*/        
        if(jCMod.isSelected())
            sEst         = "1";
        
        /*Determina si mostrarán los borrados*/        
        if(jCDel.isSelected())
            sEst         = "2";
                
        /*Determina si mostrarán todos*/        
        if(jCDel.isSelected() && jCAgre.isSelected() && jCMod.isSelected())
            sEst         = "3";
        
        /*Obtiene la caja y la sucursal*/
        final String sSucuFi        = jTSucu.getText();
        final String sNoCajFi       = jTCaj.getText();
        
        /*Declara variables final para el thread*/
        final String sEstFi         = sEst;        
        final String sFAFi          = sFA;
        final String sFDeFi         = sFDe;                                    
        final String sEstacFi       = jTUsr.getText().replace(" ", "");              
        final String sCodFi         = jTCod.getText().replace(" ", "");                          
        
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
                    pa.put("F_D",           sFDeFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("F_A",           sFAFi);                    
                    pa.put("ESTAD",         sEstFi);
                    pa.put("NOCAJ",         sNoCajFi);
                    pa.put("SUC",           sSucuFi);
                    pa.put("ESTAC",         sEstacFi);                    
                    pa.put("COD",           sCodFi);                    
                    
                    /*Compila el reporte y muestralo maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream(sReport));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte log general");
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

    
    /*Cuando se presiona una tecla en el checkbox de agregar*/
    private void jCAgreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAgreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCAgreKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de borrar*/
    private void jCDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDelKeyPressed

    
    /*Cuando se gana el foco del teclado en el control de El usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());

    }//GEN-LAST:event_jTUsrFocusGained
    
    
    /*Cuando se presiona una tecla en el control de El usuario*/
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

    
    /*Cuando se tipea una tecla en el campo de El usuario*/
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUsrKeyTyped

    
    /*Cuando se presiona el botón de búscar usuario*/
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar usuario*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la caja*/
    private void jTCajFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCaj.setSelectionStart(0);jTCaj.setSelectionEnd(jTCaj.getText().length());

    }//GEN-LAST:event_jTCajFocusGained

    
    /*Cuando se presiona una tecla en el campo de la caja*/
    private void jTCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCajKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la caja*/
    private void jTCajKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCajKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de sucursal*/
    private void jTSucuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucuFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSucu.setSelectionStart(0);jTSucu.setSelectionEnd(jTSucu.getText().length());

    }//GEN-LAST:event_jTSucuFocusGained

        
    /*Cuando se presiona una tecla en el campo de la sucursal*/
    private void jTSucuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucuKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTSucuKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la sucursal*/
    private void jTSucuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucuKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTSucuKeyTyped

    
    /*Cuando se presiona una tecla en el checkbox de modificar*/
    private void jCModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCModKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código*/
    private void jTCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCod.setSelectionStart(0);jTCod.setSelectionEnd(jTCod.getText().length());

    }//GEN-LAST:event_jTCodFocusGained

    
    /*Cuando se presiona una tecla en el campo del código*/
    private void jTCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar código*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)                    
            jBCod.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código*/
    private void jTCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodKeyTyped

    
    /*Cuando se presiona el botón de búscar código*/
    private void jBCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCodActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCod.getText(), iNum, jTCod, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBCodActionPerformed

    
    /*Cuando se presiona una tecla en el botón de código*/
    private void jBCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCodKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de El usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jTCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCod.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTCod.getText().compareTo("")!=0)
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCodFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUsrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCodMouseEntered

    
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
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCod.setBackground(colOri);
        
    }//GEN-LAST:event_jBCodMouseExited

    
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
    private void jTSucuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSucu.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSucuFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCajFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCaj.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCajFocusLost

    
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
            jCDel.setSelected   (false);
            jCMod.setSelected   (false);            
            jCAgre.setSelected  (false);            

            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCDel.setSelected   (true);
            jCMod.setSelected   (true);            
            jCAgre.setSelected  (true);            

            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }

    }//GEN-LAST:event_jBSelTActionPerformed

    
    /*Cuando se presiona uan tecla en el botón de seleccionar*/
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
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBCod;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSelT;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCAgre;
    private javax.swing.JCheckBox jCDel;
    private javax.swing.JCheckBox jCMod;
    private javax.swing.JCheckBox jCTFech;
    private com.toedter.calendar.JDateChooser jDA;
    private com.toedter.calendar.JDateChooser jDDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLGral;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCaj;
    private javax.swing.JTextField jTCod;
    private javax.swing.JTextField jTSucu;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
