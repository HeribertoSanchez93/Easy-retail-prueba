//Paquete
package ptovta;

//Importaciones
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static ptovta.Princip.bIdle;




/*Clase para guardar los datos de boleto perdido*/
public class PerdBol extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color colOri;
    
    //Contiene la dirección del otro formulario
    private final PtoVtaTou     jFram;
    
    //Contiene la venta a la que pudiera hacer referencia
    private final String        sVta;
    
    
    
    
    //Constructor
    public PerdBol(PtoVtaTou jFra, String sVtaC) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
                
        //Obtiene la venta del otro formulario
        sVta    = sVtaC;
        
        //Guarda la dirección del otro formulario
        jFram   = jFra;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGua);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Boleto perdido, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        //Pon el foco del teclado en el primero control
        jTRecib.grabFocus();
                                
        //Si la venta no es nula entonces carga los datos de esa venta
        if(sVta!=null)
            vCargInfo();
        else
        {
            //Obtiene del otro formulario los campos    
            jTRecib.setText     (jFram.sRecib);
            jTMarc.setText      (jFram.sMarc);
            jTMod.setText       (jFram.sMod);
            jTCol.setText       (jFram.sColo);
            jTPlac.setText      (jFram.sPlacs);
            jTNom.setText       (jFram.sNom);
            jTTarCirc.setText   (jFram.sTarCirc);
            jTNoLic.setText     (jFram.sNumLic);
            jTTel.setText       (jFram.sTel);
            jTDirPart.setText   (jFram.sDirPart);
            jTDirOfi.setText    (jFram.sDirOfi);
            jTTelOfi.setText    (jFram.sTelOfi);        
        }
        
    }/*Fin de public PerdBol() */
                
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGua = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTRecib = new javax.swing.JTextField();
        jTMarc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTMod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTPlac = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTTarCirc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTNoLic = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTTel = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTDirPart = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTDirOfi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTTelOfi = new javax.swing.JTextField();

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

        jBGua.setBackground(new java.awt.Color(255, 255, 255));
        jBGua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGua.setForeground(new java.awt.Color(0, 102, 0));
        jBGua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGua.setText("Guardar");
        jBGua.setToolTipText("Guardar cambios(Ctrl+G)");
        jBGua.setNextFocusableComponent(jTRecib);
        jBGua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuaMouseExited(evt);
            }
        });
        jBGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuaActionPerformed(evt);
            }
        });
        jBGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuaKeyPressed(evt);
            }
        });
        jP1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Marca:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 130, 20));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jBGua);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 120, 30));

        jTRecib.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRecib.setNextFocusableComponent(jTMarc);
        jTRecib.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRecibFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRecibFocusLost(evt);
            }
        });
        jTRecib.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRecibKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRecibKeyTyped(evt);
            }
        });
        jP1.add(jTRecib, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 310, 20));

        jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMarc.setNextFocusableComponent(jTMod);
        jTMarc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMarcFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMarcFocusLost(evt);
            }
        });
        jTMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMarcKeyPressed(evt);
            }
        });
        jP1.add(jTMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 310, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Recibí de:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 130, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Modelo:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, 20));

        jTMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMod.setNextFocusableComponent(jTCol);
        jTMod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTModFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTModFocusLost(evt);
            }
        });
        jTMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTModKeyPressed(evt);
            }
        });
        jP1.add(jTMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 310, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Color:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, 20));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTPlac);
        jTCol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTColFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTColFocusLost(evt);
            }
        });
        jTCol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTColKeyPressed(evt);
            }
        });
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 310, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Placas:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 130, 20));

        jTPlac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPlac.setNextFocusableComponent(jTNom);
        jTPlac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPlacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPlacFocusLost(evt);
            }
        });
        jTPlac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPlacKeyPressed(evt);
            }
        });
        jP1.add(jTPlac, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 310, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombre automóvil:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 130, 20));

        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTTarCirc);
        jTNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomFocusLost(evt);
            }
        });
        jTNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomKeyPressed(evt);
            }
        });
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 310, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tar. Circulación:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, 20));

        jTTarCirc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTarCirc.setNextFocusableComponent(jTNoLic);
        jTTarCirc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTarCircFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTarCircFocusLost(evt);
            }
        });
        jTTarCirc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTarCircKeyPressed(evt);
            }
        });
        jP1.add(jTTarCirc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 310, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("No. Licencia:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 130, 20));

        jTNoLic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoLic.setNextFocusableComponent(jTTel);
        jTNoLic.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoLicFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoLicFocusLost(evt);
            }
        });
        jTNoLic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoLicKeyPressed(evt);
            }
        });
        jP1.add(jTNoLic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 310, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Teléfono:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 130, 20));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTDirPart);
        jTTel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelFocusLost(evt);
            }
        });
        jTTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelKeyPressed(evt);
            }
        });
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 310, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Dir. Particular:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 130, 20));

        jTDirPart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDirPart.setNextFocusableComponent(jTDirOfi);
        jTDirPart.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDirPartFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDirPartFocusLost(evt);
            }
        });
        jTDirPart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDirPartKeyPressed(evt);
            }
        });
        jP1.add(jTDirPart, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 310, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Dir.Oficina:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 130, 20));

        jTDirOfi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDirOfi.setNextFocusableComponent(jTTelOfi);
        jTDirOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDirOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDirOfiFocusLost(evt);
            }
        });
        jTDirOfi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDirOfiKeyPressed(evt);
            }
        });
        jP1.add(jTDirOfi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 310, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Tel. Oficina:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 130, 20));

        jTTelOfi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelOfiFocusLost(evt);
            }
        });
        jTTelOfi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelOfiKeyPressed(evt);
            }
        });
        jP1.add(jTTelOfi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 310, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //Método para cargar la información de la venta en los controles
    private void vCargInfo()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene la información de esa venta para boleto perdido
        try
        {
            sQ = "SELECT autrecibde, autmarc, autmod, autcolo, autplacs, autnom, auttarcirc, autnumlic, auttel, autdirpart, autdirofi, auttelofi FROM vtas WHERE vta = " + this.sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces carga los datos en los controles
            if(rs.next())
            {
                jTRecib.setText     (rs.getString("autrecibde"));
                jTMarc.setText      (rs.getString("autmarc"));
                jTMod.setText       (rs.getString("autmod"));
                jTCol.setText       (rs.getString("autcolo"));
                jTPlac.setText      (rs.getString("autplacs"));
                jTNom.setText       (rs.getString("autnom"));
                jTTarCirc.setText   (rs.getString("auttarcirc"));
                jTNoLic.setText     (rs.getString("autnumlic"));
                jTTel.setText       (rs.getString("auttel"));
                jTDirPart.setText   (rs.getString("autdirpart"));
                jTDirOfi.setText    (rs.getString("autdirofi"));
                jTTelOfi.setText    (rs.getString("auttelofi"));
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//Fin de private void vCargInfo()
    
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed
                                                       
        /*Preguntar al usuario si esta seguro de querer guardar los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar los datos?", "Guardar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Si la venta no es nula entonces
        if(sVta!=null)
        {            
            //Abre la base de datos nuevamente
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;                        
            String      sQ; 

            //Actualiza los datos de la información del boleto perdido
            try 
            {                
                sQ = "UPDATE vtas SET "
                        + "autrecibde   = '" + jTRecib.getText().trim() + "', "
                        + "autmarc      = '" + jTMarc.getText().trim() + "', "
                        + "autmod       = '" + jTMod.getText().trim() + "', "
                        + "autcolo      = '" + jTCol.getText().trim() + "', "
                        + "autplacs     = '" + jTPlac.getText().trim() + "', "                    
                        + "autnom       = '" + jTNom.getText().trim() + "', "
                        + "auttarcirc   = '" + jTTarCirc.getText().trim() + "', "
                        + "autnumlic    = '" + jTNoLic.getText().trim() + "', "
                        + "auttel       = '" + jTTel.getText().trim() + "', "
                        + "autdirpart   = '" + jTDirPart.getText().trim() + "', "
                        + "autdirofi    = '" + jTDirOfi.getText().trim() + "', "
                        + "auttelofi    = '" + jTTelOfi.getText().trim() + "' "
                        + "WHERE vta    = " + sVta;                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                        
             }                               

            //Cierra la base de datos
            Star.iCierrBas(con);            
            
        }//Fin de if(sVta!=null)
        //Else no viene de una venta entonces
        else
        {
            //Guarda los datos del otro formulario
            jFram.sRecib    = jTRecib.getText().trim();
            jFram.sMarc     = jTMarc.getText().trim();
            jFram.sMod      = jTMod.getText().trim();
            jFram.sColo     = jTCol.getText().trim();
            jFram.sPlacs    = jTPlac.getText().trim();
            jFram.sNom      = jTNom.getText().trim();
            jFram.sTarCirc  = jTTarCirc.getText().trim();
            jFram.sNumLic   = jTNoLic.getText().trim();
            jFram.sTel      = jTTel.getText().trim();
            jFram.sDirPart  = jTDirPart.getText().trim();
            jFram.sDirOfi   = jTDirOfi.getText().trim();
            jFram.sTelOfi   = jTTelOfi.getText().trim();                    
        }

        //Mensajea de éxito
        JOptionPane.showMessageDialog(null, "Datos guardados con éxito.", "Boleto perdido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        //Cierra la forma
        System.gc();
        dispose();
        
    }//GEN-LAST:event_jBGuaActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaKeyPressed

        
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        //Cierra la forma
        System.gc();
        this.dispose();                    
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed
    
    
    /*Cuando se presiona una tecla en el campo de edición de recibí*/
    private void jTRecibKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRecibKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRecibKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de recibí*/
    private void jTRecibFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRecibFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRecib.setSelectionStart(0);jTRecib.setSelectionEnd(jTRecib.getText().length());        
        
    }//GEN-LAST:event_jTRecibFocusGained

    
    /*Cuando se presiona una tecla en el campo de descripción de marca*/
    private void jTMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMarcKeyPressed

       
    /*Cuando se gana el foco del teclado en el campo de marca*/
    private void jTMarcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMarc.setSelectionStart(0);jTMarc.setSelectionEnd(jTMarc.getText().length());        
        
    }//GEN-LAST:event_jTMarcFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de recibí*/
    private void jTRecibFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRecibFocusLost

        /*Coloca el cursor al principio del control*/
        jTRecib.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRecib.getText().compareTo("")!=0)
            jTRecib.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                        
    }//GEN-LAST:event_jTRecibFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de marca*/
    private void jTMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusLost

        /*Coloca el cursor al principio del control*/
        jTMarc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMarc.getText().compareTo("")!=0)
            jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                       
    }//GEN-LAST:event_jTMarcFocusLost
        
                
    /*Cuando se tipea una tecla en el campo de código de clasificación*/
    private void jTRecibKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRecibKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRecibKeyTyped
    
    
    /*Cuando la rueda del ratón se mueve*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el mouse en la forma*/
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
        
    
    /*Cuando el mouse entra en el botón de guardar*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered
    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

       
    /*Cuando el mouse sale del botón de guardar*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGua.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    //Cuando se gana el foco del teclado en el campo de modelo
    private void jTModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTModFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMod.setSelectionStart(0);jTMod.setSelectionEnd(jTMod.getText().length());        
        
    }//GEN-LAST:event_jTModFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del modelo
    private void jTModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTModFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMod.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMod.getText().compareTo("")!=0)
            jTMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTModFocusLost

    
    //Cuando se presiona una tecla en el campo del modelo
    private void jTModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTModKeyPressed
     
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTModKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la colonia
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del color
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCol.getText().compareTo("")!=0)
            jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTColFocusLost

    
    //Cuando se presiona una tecla en el campo del color
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de las placas
    private void jTPlacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPlacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPlac.setSelectionStart(0);jTPlac.setSelectionEnd(jTPlac.getText().length());        
        
    }//GEN-LAST:event_jTPlacFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de las placas
    private void jTPlacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPlacFocusLost
        
        /*Coloca el caret al principio del control*/
        jTPlac.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPlac.getText().compareTo("")!=0)
            jTPlac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTPlacFocusLost

    
    //Cuando se presiona una tecla en el campo de la placa
    private void jTPlacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPlacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPlacKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del nombre
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del nombre
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el caret al principio del control*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNom.getText().compareTo("")!=0)
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTNomFocusLost

    
    //Cuando se presiona una tecla en el campo del nombre
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la tarjeta de circulación
    private void jTTarCircFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarCircFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTarCirc.setSelectionStart(0);jTTarCirc.setSelectionEnd(jTTarCirc.getText().length());        
        
    }//GEN-LAST:event_jTTarCircFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la tarjeta de circulación
    private void jTTarCircFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTarCircFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTarCirc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTarCirc.getText().compareTo("")!=0)
            jTTarCirc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTTarCircFocusLost

    
    //Cuando se presiona una tecla en el campo de la tarjeta de circulación
    private void jTTarCircKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTarCircKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTarCircKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del número de licencia
    private void jTNoLicFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoLicFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoLic.setSelectionStart(0);jTNoLic.setSelectionEnd(jTNoLic.getText().length());        
        
    }//GEN-LAST:event_jTNoLicFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del número de licencia
    private void jTNoLicFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoLicFocusLost
        
        /*Coloca el caret al principio del control*/
        jTNoLic.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNoLic.getText().compareTo("")!=0)
            jTNoLic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTNoLicFocusLost

    
    //Cuando se presiona una tecla en el campo del número de licencia
    private void jTNoLicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoLicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoLicKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del teléfono
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del teléfono
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTel.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTel.getText().compareTo("")!=0)
            jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTTelFocusLost

    
    //Cuando se presiona una tecla en el campo del teléfono
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la dirección particular
    private void jTDirPartFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDirPartFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDirPart.setSelectionStart(0);jTDirPart.setSelectionEnd(jTDirPart.getText().length());        
        
    }//GEN-LAST:event_jTDirPartFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la dirección particular
    private void jTDirPartFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDirPartFocusLost
        
        /*Coloca el caret al principio del control*/
        jTDirPart.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDirPart.getText().compareTo("")!=0)
            jTDirPart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTDirPartFocusLost

    
    //Cuando se presiona una tecla en el campo de la dirección partícular
    private void jTDirPartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDirPartKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDirPartKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la dirección de oficina
    private void jTDirOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDirOfiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDirOfi.setSelectionStart(0);jTDirOfi.setSelectionEnd(jTDirOfi.getText().length());        
        
    }//GEN-LAST:event_jTDirOfiFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la dirección de oficina
    private void jTDirOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDirOfiFocusLost
        
        /*Coloca el caret al principio del control*/
        jTDirOfi.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDirOfi.getText().compareTo("")!=0)
            jTDirOfi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTDirOfiFocusLost

    
    //Cuando se presiona una tecla en el campo de la dirección de oficina
    private void jTDirOfiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDirOfiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDirOfiKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del teléfono de oficina
    private void jTTelOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelOfiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelOfi.setSelectionStart(0);jTTelOfi.setSelectionEnd(jTTelOfi.getText().length());        
        
    }//GEN-LAST:event_jTTelOfiFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del teléfono de oficina
    private void jTTelOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelOfiFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTelOfi.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTelOfi.getText().compareTo("")!=0)
            jTTelOfi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTTelOfiFocusLost

    
    //Cuando se presiona una tecla en el campo del teléfono de oficina
    private void jTTelOfiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelOfiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelOfiKeyPressed

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();            
        /*Si se presiona CTRL + G entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTDirOfi;
    private javax.swing.JTextField jTDirPart;
    private javax.swing.JTextField jTMarc;
    private javax.swing.JTextField jTMod;
    private javax.swing.JTextField jTNoLic;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPlac;
    private javax.swing.JTextField jTRecib;
    private javax.swing.JTextField jTTarCirc;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelOfi;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
