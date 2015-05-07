//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.awt.Cursor;




/*Clase para controlar los Contactos de los proveedores en la parte de la modificación*/
public class ContacsProvMod extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color           colOri;

    /*Variable que contiene la referencia al otro formulario para poder tener acceso al as variables*/
    private final Prov                     modProv;
    
    
    
    
    /*Constructor sin argumentos*/
    public ContacsProvMod(Prov modP) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Recibe la referencia del otro formulario*/
        modProv     = modP;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Contactos de empresa, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                
        /*Pon el foco del teclado en el primer control*/
        jTCon3.grabFocus();
                              
        /*Carga los Contactos del otro formulario en los controles de este*/
        jTCon3.setText(modProv.sContac3);
        jTCon4.setText(modProv.sContac4);
        jTCon5.setText(modProv.sContac5);
        jTCon6.setText(modProv.sContac6);
        jTCon7.setText(modProv.sContac7);
        jTCon8.setText(modProv.sContac8);
        jTCon9.setText(modProv.sContac9);
        jTCon10.setText(modProv.sContac10);
        
        /*Carga los teléfonos del otro formulario*/
        jTTel3.setText(modProv.sTelCon3);
        jTTel4.setText(modProv.sTelCon4);
        jTTel5.setText(modProv.sTelCon5);
        jTTel6.setText(modProv.sTelCon6);
        jTTel7.setText(modProv.sTelCon7);
        jTTel8.setText(modProv.sTelCon8);
        jTTel9.setText(modProv.sTelCon9);
        jTTel10.setText(modProv.sTelCon10);
        
        /*Carga los teléfonos personales del otro formulario*/
        jTTelPer3.setText(modProv.sTelPer3);
        jTTelPer4.setText(modProv.sTelPer4);
        jTTelPer5.setText(modProv.sTelPer5);
        jTTelPer6.setText(modProv.sTelPer6);
        jTTelPer7.setText(modProv.sTelPer7);
        jTTelPer8.setText(modProv.sTelPer8);
        jTTelPer9.setText(modProv.sTelPer9);
        jTTelPer10.setText(modProv.sTelPer10);
        
        /*Carga los teléfonos personales del otro forumario segunda parte*/
        jTTelPer23.setText(modProv.sTelPer33);
        jTTelPer24.setText(modProv.sTelPer44);
        jTTelPer25.setText(modProv.sTelPer55);
        jTTelPer26.setText(modProv.sTelPer66);
        jTTelPer27.setText(modProv.sTelPer77);
        jTTelPer28.setText(modProv.sTelPer88);
        jTTelPer29.setText(modProv.sTelPer99);
        jTTelPer30.setText(modProv.sTelPer100);        
        
    }/*Fin de public ContacEmp() */        
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTCon3 = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTCon5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTCon7 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTCon9 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTCon4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTCon6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTCon8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTCon10 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTTel3 = new javax.swing.JTextField();
        jTTel4 = new javax.swing.JTextField();
        jTTel5 = new javax.swing.JTextField();
        jTTel6 = new javax.swing.JTextField();
        jTTel7 = new javax.swing.JTextField();
        jTTel8 = new javax.swing.JTextField();
        jTTel9 = new javax.swing.JTextField();
        jTTel10 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTTelPer10 = new javax.swing.JTextField();
        jTTelPer3 = new javax.swing.JTextField();
        jTTelPer4 = new javax.swing.JTextField();
        jTTelPer5 = new javax.swing.JTextField();
        jTTelPer6 = new javax.swing.JTextField();
        jTTelPer7 = new javax.swing.JTextField();
        jTTelPer8 = new javax.swing.JTextField();
        jTTelPer9 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTTelPer23 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTTelPer24 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTTelPer25 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTTelPer26 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTTelPer27 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTTelPer28 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTTelPer29 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTTelPer30 = new javax.swing.JTextField();
        jLAyu = new javax.swing.JLabel();

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Utilidad %:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Teléfono:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 110, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTCon3);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 110, -1));

        jTCon3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon3.setNextFocusableComponent(jTTel3);
        jTCon3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon3FocusLost(evt);
            }
        });
        jTCon3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon3KeyPressed(evt);
            }
        });
        jP1.add(jTCon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Contacto 5:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, -1));

        jTCon5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon5.setNextFocusableComponent(jTTel5);
        jTCon5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon5FocusLost(evt);
            }
        });
        jTCon5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon5KeyPressed(evt);
            }
        });
        jP1.add(jTCon5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Contacto 7:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, -1));

        jTCon7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon7.setNextFocusableComponent(jTTel7);
        jTCon7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon7FocusLost(evt);
            }
        });
        jTCon7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon7KeyPressed(evt);
            }
        });
        jP1.add(jTCon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Contacto 9:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 150, -1));

        jTCon9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon9.setNextFocusableComponent(jTTel9);
        jTCon9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon9FocusLost(evt);
            }
        });
        jTCon9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon9KeyPressed(evt);
            }
        });
        jP1.add(jTCon9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 150, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Contacto 4:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, -1));

        jTCon4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon4.setNextFocusableComponent(jTTel4);
        jTCon4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon4FocusLost(evt);
            }
        });
        jTCon4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon4KeyPressed(evt);
            }
        });
        jP1.add(jTCon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Contacto 6:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, -1));

        jTCon6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon6.setNextFocusableComponent(jTTel6);
        jTCon6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon6FocusLost(evt);
            }
        });
        jTCon6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon6KeyPressed(evt);
            }
        });
        jP1.add(jTCon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Contacto 8:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, -1));

        jTCon8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon8.setNextFocusableComponent(jTTel8);
        jTCon8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon8FocusLost(evt);
            }
        });
        jTCon8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon8KeyPressed(evt);
            }
        });
        jP1.add(jTCon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 150, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Contacto 10:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 150, -1));

        jTCon10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon10.setNextFocusableComponent(jTTel10);
        jTCon10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon10FocusLost(evt);
            }
        });
        jTCon10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon10KeyPressed(evt);
            }
        });
        jP1.add(jTCon10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Contacto 3:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jTTel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel3.setNextFocusableComponent(jTTelPer3);
        jTTel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel3FocusLost(evt);
            }
        });
        jTTel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel3KeyPressed(evt);
            }
        });
        jP1.add(jTTel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 130, 20));

        jTTel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel4.setNextFocusableComponent(jTTelPer4);
        jTTel4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel4FocusLost(evt);
            }
        });
        jTTel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel4KeyPressed(evt);
            }
        });
        jP1.add(jTTel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 130, 20));

        jTTel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel5.setNextFocusableComponent(jTTelPer5);
        jTTel5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel5FocusLost(evt);
            }
        });
        jTTel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel5KeyPressed(evt);
            }
        });
        jP1.add(jTTel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 130, 20));

        jTTel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel6.setNextFocusableComponent(jTTelPer6);
        jTTel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel6FocusLost(evt);
            }
        });
        jTTel6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel6KeyPressed(evt);
            }
        });
        jP1.add(jTTel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 130, 20));

        jTTel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel7.setNextFocusableComponent(jTTelPer7);
        jTTel7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel7FocusLost(evt);
            }
        });
        jTTel7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel7KeyPressed(evt);
            }
        });
        jP1.add(jTTel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 130, 20));

        jTTel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel8.setNextFocusableComponent(jTTelPer8);
        jTTel8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel8FocusLost(evt);
            }
        });
        jTTel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel8KeyPressed(evt);
            }
        });
        jP1.add(jTTel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 130, 20));

        jTTel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel9.setNextFocusableComponent(jTTelPer9);
        jTTel9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel9FocusLost(evt);
            }
        });
        jTTel9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel9KeyPressed(evt);
            }
        });
        jP1.add(jTTel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 130, 20));

        jTTel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel10.setNextFocusableComponent(jTTelPer10);
        jTTel10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel10FocusLost(evt);
            }
        });
        jTTel10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel10KeyPressed(evt);
            }
        });
        jP1.add(jTTel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 130, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Tel. Personal 1:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 130, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Teléfono:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 110, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Teléfono:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 110, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Teléfono:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 110, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Teléfono:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 110, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Teléfono:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 110, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Teléfono:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 110, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Teléfono:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 110, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Tel. Personal 1:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 130, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Tel. Personal 1:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 130, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Tel. Personal 1:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 130, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Tel. Personal 1:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 130, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Tel. Personal 1:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 130, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Tel. Personal 1:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 130, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Tel. Personal 1:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 130, -1));

        jTTelPer10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer10.setNextFocusableComponent(jTTelPer30);
        jTTelPer10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer10FocusLost(evt);
            }
        });
        jTTelPer10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer10KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 130, 20));

        jTTelPer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer3.setNextFocusableComponent(jTTelPer23);
        jTTelPer3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer3FocusLost(evt);
            }
        });
        jTTelPer3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer3KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 130, 20));

        jTTelPer4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer4.setNextFocusableComponent(jTTelPer24);
        jTTelPer4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer4FocusLost(evt);
            }
        });
        jTTelPer4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer4KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 130, 20));

        jTTelPer5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer5.setNextFocusableComponent(jTTelPer25);
        jTTelPer5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer5FocusLost(evt);
            }
        });
        jTTelPer5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer5KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 130, 20));

        jTTelPer6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer6.setNextFocusableComponent(jTTelPer26);
        jTTelPer6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer6FocusLost(evt);
            }
        });
        jTTelPer6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer6KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 130, 20));

        jTTelPer7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer7.setNextFocusableComponent(jTTelPer27);
        jTTelPer7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer7FocusLost(evt);
            }
        });
        jTTelPer7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer7KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 130, 20));

        jTTelPer8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer8.setNextFocusableComponent(jTTelPer28);
        jTTelPer8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer8FocusLost(evt);
            }
        });
        jTTelPer8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer8KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 130, 20));

        jTTelPer9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer9.setNextFocusableComponent(jTTelPer29);
        jTTelPer9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer9FocusLost(evt);
            }
        });
        jTTelPer9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer9KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 130, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Tel. Personal 2:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 130, -1));

        jTTelPer23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer23.setNextFocusableComponent(jTCon4);
        jTTelPer23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer23FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer23FocusLost(evt);
            }
        });
        jTTelPer23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer23KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer23, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 130, 20));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Tel. Personal 2:");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 130, -1));

        jTTelPer24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer24.setNextFocusableComponent(jTCon5);
        jTTelPer24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer24FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer24FocusLost(evt);
            }
        });
        jTTelPer24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer24KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer24, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 130, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Tel. Personal 2:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 130, -1));

        jTTelPer25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer25.setNextFocusableComponent(jTCon6);
        jTTelPer25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer25FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer25FocusLost(evt);
            }
        });
        jTTelPer25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer25KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer25, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, 130, 20));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Tel. Personal 2:");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 130, -1));

        jTTelPer26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer26.setNextFocusableComponent(jTCon7);
        jTTelPer26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer26FocusLost(evt);
            }
        });
        jTTelPer26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer26KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer26, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 130, 20));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Tel. Personal 2:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 130, -1));

        jTTelPer27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer27.setNextFocusableComponent(jTCon8);
        jTTelPer27.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer27FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer27FocusLost(evt);
            }
        });
        jTTelPer27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer27KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer27, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 130, 20));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Tel. Personal 2:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 130, -1));

        jTTelPer28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer28.setNextFocusableComponent(jTCon9);
        jTTelPer28.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer28FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer28FocusLost(evt);
            }
        });
        jTTelPer28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer28KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer28, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, 130, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Tel. Personal 2:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 130, -1));

        jTTelPer29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer29.setNextFocusableComponent(jTCon10);
        jTTelPer29.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer29FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer29FocusLost(evt);
            }
        });
        jTTelPer29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer29KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer29, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 130, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Tel. Personal 2:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 130, -1));

        jTTelPer30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer30.setNextFocusableComponent(jBGuar);
        jTTelPer30.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer30FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer30FocusLost(evt);
            }
        });
        jTTelPer30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer30KeyPressed(evt);
            }
        });
        jP1.add(jTTelPer30, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, 130, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 380, 200, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
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

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
       
        /*Lee los Contactos introducidos por el usuario*/
        modProv.sContac3 = jTCon3.getText();        
        modProv.sContac4 = jTCon4.getText();
        modProv.sContac5 = jTCon5.getText();
        modProv.sContac6 = jTCon6.getText();
        modProv.sContac7 = jTCon7.getText();
        modProv.sContac8 = jTCon8.getText();
        modProv.sContac9 = jTCon9.getText();
        modProv.sContac10= jTCon10.getText();
        
        /*Lee los teléfonos que introdujo el usuario*/
        modProv.sTelCon3 = jTTel3.getText();
        modProv.sTelCon4 = jTTel4.getText();
        modProv.sTelCon5 = jTTel5.getText();
        modProv.sTelCon6 = jTTel6.getText();
        modProv.sTelCon7 = jTTel7.getText();
        modProv.sTelCon8 = jTTel8.getText();
        modProv.sTelCon9 = jTTel9.getText();
        modProv.sTelCon10= jTTel10.getText();
    
        /*Lee los teléfonos personales que introdujo el usuario perimera parte*/
        modProv.sTelPer3 = jTTelPer3.getText();
        modProv.sTelPer4 = jTTelPer4.getText();
        modProv.sTelPer5 = jTTelPer5.getText();
        modProv.sTelPer6 = jTTelPer6.getText();
        modProv.sTelPer7 = jTTelPer7.getText();
        modProv.sTelPer8 = jTTelPer8.getText();
        modProv.sTelPer9 = jTTelPer9.getText();
        modProv.sTelPer10= jTTelPer10.getText();
        
        /*Lee los teléfonos personales que introdujo el usuario segunda parte*/
        modProv.sTelPer33= jTTelPer23.getText();
        modProv.sTelPer44= jTTelPer24.getText();
        modProv.sTelPer55= jTTelPer25.getText();
        modProv.sTelPer66= jTTelPer26.getText();
        modProv.sTelPer77= jTTelPer27.getText();
        modProv.sTelPer88= jTTelPer28.getText();
        modProv.sTelPer99= jTTelPer29.getText();
        modProv.sTelPer100= jTTelPer30.getText();
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed

           
    /*Cuando se presiona una tecla en el campo de Contacto 3*/
    private void jTCon3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCon3KeyPressed
        
    
    /*Cuando se gana el foco del teclado en el campo de Contacto 3*/
    private void jTCon3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon3FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCon3.setSelectionStart(0);jTCon3.setSelectionEnd(jTCon3.getText().length());        

    }//GEN-LAST:event_jTCon3FocusGained

    
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
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el campo de Contacto 5*/
    private void jTCon5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon5FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon5.setSelectionStart(0);jTCon5.setSelectionEnd(jTCon5.getText().length());        
        
    }//GEN-LAST:event_jTCon5FocusGained
        
    
    /*Cuando se presiona una tecla en el campo del Contacto 5*/
    private void jTCon5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon5KeyPressed

                
    /*Cuando se gana el foco del teclado en el campo del Contacto 7*/
    private void jTCon7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon7FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon7.setSelectionStart(0);jTCon7.setSelectionEnd(jTCon7.getText().length());        
        
    }//GEN-LAST:event_jTCon7FocusGained

            
    /*Cuando se presiona una tecla en el campo del Contacto 7*/
    private void jTCon7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon7KeyPressed

                
    /*Cuando se gana el foco del teclado en el campo del Contacto 9*/
    private void jTCon9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon9FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon9.setSelectionStart(0);jTCon9.setSelectionEnd(jTCon9.getText().length());                
        
    }//GEN-LAST:event_jTCon9FocusGained
        
    
    /*Cuando se presiona una tecla en el campo del Contacto 9*/
    private void jTCon9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon9KeyPressed
        
    
    /*Cuando se presiona una tecla en el campo del Contacto 4*/
    private void jTCon4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon4FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon4.setSelectionStart(0);jTCon4.setSelectionEnd(jTCon4.getText().length());
        
    }//GEN-LAST:event_jTCon4FocusGained

                
    /*Cuando se presiona una tecla en el campo de Contacto 4*/
    private void jTCon4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon4KeyPressed

                
    /*Cuando se presiona una tecla en el campo de Contacto 6*/
    private void jTCon6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon6FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon6.setSelectionStart(0);jTCon6.setSelectionEnd(jTCon6.getText().length());
        
    }//GEN-LAST:event_jTCon6FocusGained

                
    /*Cuando se presiona una tecla en el campo del Contacto 6*/
    private void jTCon6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon6KeyPressed
           
    
    /*Cuando se gana el foco del teclado en el campo del Contacto 8*/
    private void jTCon8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon8FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon8.setSelectionStart(0);jTCon8.setSelectionEnd(jTCon8.getText().length());
        
    }//GEN-LAST:event_jTCon8FocusGained

            
    /*Cuando se presiona una tecla en el campo del Contacto 8*/
    private void jTCon8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon8KeyPressed
         
    
    /*Cuando se gana el foco del teclado en el campo del Contacto 10*/
    private void jTCon10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon10FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon10.setSelectionStart(0);jTCon10.setSelectionEnd(jTCon10.getText().length());        
        
    }//GEN-LAST:event_jTCon10FocusGained
        
    
    /*Cuando se presiona una tecla en el campo del Contacto 10*/
    private void jTCon10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon10KeyPressed
                                     
    
    /*Cuando se gana el foco del teclado en el campo de teléfono 3*/
    private void jTTel3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel3.setSelectionStart(0);jTTel3.setSelectionEnd(jTTel3.getText().length());        
        
    }//GEN-LAST:event_jTTel3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 4*/
    private void jTTel4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel4FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel4.setSelectionStart(0);jTTel4.setSelectionEnd(jTTel4.getText().length());
        
    }//GEN-LAST:event_jTTel4FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 5*/
    private void jTTel5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel5FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel5.setSelectionStart(0);jTTel5.setSelectionEnd(jTTel5.getText().length());
        
    }//GEN-LAST:event_jTTel5FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 6*/
    private void jTTel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel6FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel6.setSelectionStart(0);jTTel6.setSelectionEnd(jTTel6.getText().length());
        
    }//GEN-LAST:event_jTTel6FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono 7*/
    private void jTTel7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel7FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel7.setSelectionStart(0);jTTel7.setSelectionEnd(jTTel7.getText().length());
        
    }//GEN-LAST:event_jTTel7FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 8*/
    private void jTTel8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel8FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel8.setSelectionStart(0);jTTel8.setSelectionEnd(jTTel8.getText().length());
        
    }//GEN-LAST:event_jTTel8FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 9*/
    private void jTTel9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel9FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel9.setSelectionStart(0);jTTel9.setSelectionEnd(jTTel9.getText().length());
        
    }//GEN-LAST:event_jTTel9FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 10*/
    private void jTTel10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel10FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel10.setSelectionStart(0);jTTel10.setSelectionEnd(jTTel10.getText().length());
        
    }//GEN-LAST:event_jTTel10FocusGained

    
    /*Cuando se presiona una tecla en el campo de teléfono 3*/
    private void jTTel3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel3KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 4*/
    private void jTTel4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel4KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 5*/
    private void jTTel5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel5KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 6*/
    private void jTTel6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel6KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 7*/
    private void jTTel7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel7KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 8*/
    private void jTTel8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel8KeyPressed

    
    /*Cando se presiona una tecla en el campo de teléfono 9*/
    private void jTTel9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel9KeyPressed

    
    /*Cuando se presiona una tecla en le campo de teléfono 10*/
    private void jTTel10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTTel10KeyPressed
                           
    
    /*Cuando se gana el foco del teclado en el campo de teléfono personal 3*/
    private void jTTelPer3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer3.setSelectionStart(0);jTTelPer3.setSelectionEnd(jTTelPer3.getText().length());        
        
    }//GEN-LAST:event_jTTelPer3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 4*/
    private void jTTelPer4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer4FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer4.setSelectionStart(0);jTTelPer4.setSelectionEnd(jTTelPer4.getText().length());
        
    }//GEN-LAST:event_jTTelPer4FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 5*/
    private void jTTelPer5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer5FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer5.setSelectionStart(0);jTTelPer5.setSelectionEnd(jTTelPer5.getText().length());
        
    }//GEN-LAST:event_jTTelPer5FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 6*/
    private void jTTelPer6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer6FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer6.setSelectionStart(0);jTTelPer6.setSelectionEnd(jTTelPer6.getText().length());
        
    }//GEN-LAST:event_jTTelPer6FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 7*/
    private void jTTelPer7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer7FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer7.setSelectionStart(0);jTTelPer7.setSelectionEnd(jTTelPer7.getText().length());
        
    }//GEN-LAST:event_jTTelPer7FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 8*/
    private void jTTelPer8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer8FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer8.setSelectionStart(0);jTTelPer8.setSelectionEnd(jTTelPer8.getText().length());
        
    }//GEN-LAST:event_jTTelPer8FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 9*/
    private void jTTelPer9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer9FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer9.setSelectionStart(0);jTTelPer9.setSelectionEnd(jTTelPer9.getText().length());
        
    }//GEN-LAST:event_jTTelPer9FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 10*/
    private void jTTelPer10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer10FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer10.setSelectionStart(0);jTTelPer10.setSelectionEnd(jTTelPer10.getText().length());
        
    }//GEN-LAST:event_jTTelPer10FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 23*/
    private void jTTelPer23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer23FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer23.setSelectionStart(0);jTTelPer23.setSelectionEnd(jTTelPer23.getText().length());
        
    }//GEN-LAST:event_jTTelPer23FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 24*/
    private void jTTelPer24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer24FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer24.setSelectionStart(0);jTTelPer24.setSelectionEnd(jTTelPer24.getText().length());
        
    }//GEN-LAST:event_jTTelPer24FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 25*/
    private void jTTelPer25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer25FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer25.setSelectionStart(0);jTTelPer25.setSelectionEnd(jTTelPer25.getText().length());
        
    }//GEN-LAST:event_jTTelPer25FocusGained

    
    /*Cuando se gana el foco del teclado en el teléfono personal 26*/
    private void jTTelPer26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer26FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer26.setSelectionStart(0);jTTelPer26.setSelectionEnd(jTTelPer26.getText().length());        
        
    }//GEN-LAST:event_jTTelPer26FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 27*/
    private void jTTelPer27FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer27FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer27.setSelectionStart(0);jTTelPer27.setSelectionEnd(jTTelPer27.getText().length());
        
    }//GEN-LAST:event_jTTelPer27FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 28*/
    private void jTTelPer28FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer28FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer28.setSelectionStart(0);jTTelPer28.setSelectionEnd(jTTelPer28.getText().length());        
        
    }//GEN-LAST:event_jTTelPer28FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 29*/
    private void jTTelPer29FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer29FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer29.setSelectionStart(0);jTTelPer29.setSelectionEnd(jTTelPer29.getText().length());
        
    }//GEN-LAST:event_jTTelPer29FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 30*/
    private void jTTelPer30FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer30FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer30.setSelectionStart(0);jTTelPer30.setSelectionEnd(jTTelPer30.getText().length());
        
    }//GEN-LAST:event_jTTelPer30FocusGained

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 3*/
    private void jTTelPer3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer3KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 4*/
    private void jTTelPer4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer4KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 5*/
    private void jTTelPer5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer5KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 6*/
    private void jTTelPer6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer6KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 7*/
    private void jTTelPer7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer7KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 8*/
    private void jTTelPer8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer8KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 9*/
    private void jTTelPer9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer9KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 10*/
    private void jTTelPer10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer10KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 23*/
    private void jTTelPer23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer23KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer23KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono 24*/
    private void jTTelPer24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer24KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer24KeyPressed

    
    /*Cando se presiona una tecla en el campo del teléfono personal 25*/
    private void jTTelPer25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer25KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer25KeyPressed

    
    /*Cando se presiona una tecla en el campo del teléfono personal 26*/
    private void jTTelPer26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer26KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer26KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 27*/
    private void jTTelPer27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer27KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer27KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 28*/
    private void jTTelPer28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer28KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer28KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 29*/
    private void jTTelPer29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer29KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer29KeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 30*/
    private void jTTelPer30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer30KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer30KeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon3FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCon3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon4FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon4.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon4FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon5FocusLost
    
        /*Coloca el caret en la posiciòn 0*/
        jTCon5.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon5FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon6FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon6.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon6FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon7FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon7.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon7FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon8FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon8.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon8FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon9FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon9.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon9FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCon10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon10FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCon10.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCon10FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel4FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel4.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel4FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel5FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel5.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel5FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel6FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel6.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel6FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel7FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel7.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel7FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel8FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel8.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel8FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel9FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel9.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel9FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTel10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel10FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel10.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTel10FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer4FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer4.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer4FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer5FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer5.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer5FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer6FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer6.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer6FocusLost


    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer7FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer7.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer7FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer8FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer8.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer8FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer9FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer9.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer9FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer10FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer10.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer10FocusLost


    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer23FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer23.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer23FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer24FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer24.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer24FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer25FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer25.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer25FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer26FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer26.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer26FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer27FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer27FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer27.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer27FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer28FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer28FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer28.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer28FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer29FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer29FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer29.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer29FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelPer30FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer30FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer30.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelPer30FocusLost
              
      
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
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCon10;
    private javax.swing.JTextField jTCon3;
    private javax.swing.JTextField jTCon4;
    private javax.swing.JTextField jTCon5;
    private javax.swing.JTextField jTCon6;
    private javax.swing.JTextField jTCon7;
    private javax.swing.JTextField jTCon8;
    private javax.swing.JTextField jTCon9;
    private javax.swing.JTextField jTTel10;
    private javax.swing.JTextField jTTel3;
    private javax.swing.JTextField jTTel4;
    private javax.swing.JTextField jTTel5;
    private javax.swing.JTextField jTTel6;
    private javax.swing.JTextField jTTel7;
    private javax.swing.JTextField jTTel8;
    private javax.swing.JTextField jTTel9;
    private javax.swing.JTextField jTTelPer10;
    private javax.swing.JTextField jTTelPer23;
    private javax.swing.JTextField jTTelPer24;
    private javax.swing.JTextField jTTelPer25;
    private javax.swing.JTextField jTTelPer26;
    private javax.swing.JTextField jTTelPer27;
    private javax.swing.JTextField jTTelPer28;
    private javax.swing.JTextField jTTelPer29;
    private javax.swing.JTextField jTTelPer3;
    private javax.swing.JTextField jTTelPer30;
    private javax.swing.JTextField jTTelPer4;
    private javax.swing.JTextField jTTelPer5;
    private javax.swing.JTextField jTTelPer6;
    private javax.swing.JTextField jTTelPer7;
    private javax.swing.JTextField jTTelPer8;
    private javax.swing.JTextField jTTelPer9;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
