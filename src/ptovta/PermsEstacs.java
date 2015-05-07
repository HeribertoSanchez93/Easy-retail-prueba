//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;




/*Clase para modificar los permisos de un usuario*/
public class PermsEstacs extends javax.swing.JFrame 
{                
    /*Consructor sin argumentos*/
    public PermsEstacs() 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Permisos usuarios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el combobox de usuarios*/
        jCEstacs.grabFocus();                
                        
    }/*Fin de public PermsEstacs() */

       
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jCEstacs = new javax.swing.JComboBox();
        jBGuar = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jCheckBox17 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox28 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jCheckBox30 = new javax.swing.JCheckBox();
        jCheckBox31 = new javax.swing.JCheckBox();
        jCheckBox32 = new javax.swing.JCheckBox();
        jCheckBox33 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox34 = new javax.swing.JCheckBox();
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 110, 30));

        jCEstacs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Usuario" }));
        jCEstacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEstacsKeyPressed(evt);
            }
        });
        jP1.add(jCEstacs, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 230, -1));

        jBGuar.setBackground(new java.awt.Color(204, 204, 204));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, 110, 30));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Modificar clientes.");
        jCheckBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox1KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 170, -1));

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Proveedores.");
        jCheckBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox2KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 170, -1));

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Ver clientes.");
        jCheckBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox3KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 160, -1));

        jCheckBox4.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setText("Borrar clientes.");
        jCheckBox4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox4KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, -1));

        jCheckBox5.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setText("Nuevos proveedores.");
        jCheckBox5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox5KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 200, -1));

        jCheckBox6.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox6.setText("Modificar proveedores.");
        jCheckBox6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox6KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 170, -1));

        jCheckBox7.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox7.setText("Borrar proveedores.");
        jCheckBox7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox7KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 160, -1));

        jCheckBox8.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox8.setText("Ver proveedores.");
        jCheckBox8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox8KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 160, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Clientes:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 90, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Proveedores:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 120, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cotizaciones:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 120, -1));

        jCheckBox9.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox9.setText("Nuevas cotizaciones.");
        jCheckBox9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox9KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 190, -1));

        jCheckBox10.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox10.setText("Abrir cotizaciones existentes.");
        jCheckBox10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox10KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 200, -1));

        jCheckBox11.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox11.setText("Modificar cotizaciones.");
        jCheckBox11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox11KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 160, -1));

        jCheckBox12.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox12.setText("Enviar cotizaciones.");
        jCheckBox12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox12KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 160, -1));

        jCheckBox13.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox13.setText("Nuevos clientes.");
        jCheckBox13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox13KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, -1));

        jCheckBox14.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox14.setText("Cotizaciones.");
        jCheckBox14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox14KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 160, -1));

        jCheckBox15.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox15.setText("Clientes.");
        jCheckBox15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox15KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 160, -1));

        jCheckBox16.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox16.setText("Borrar cotizaciones.");
        jCheckBox16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox16KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 160, -1));

        jCheckBox17.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox17.setText("Guardar cotizaciones.");
        jCheckBox17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox17KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 160, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Configuraciones:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 140, -1));

        jCheckBox18.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox18.setText("Configuraciones.");
        jCheckBox18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox18KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 170, -1));

        jCheckBox19.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox19.setText("Restaurar sistema.");
        jCheckBox19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox19KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 200, -1));

        jCheckBox20.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox20.setText("Correos electrónicos.");
        jCheckBox20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox20KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox20, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 200, -1));

        jCheckBox21.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox21.setText("Nuevos correos.");
        jCheckBox21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox21KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 200, -1));

        jCheckBox22.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox22.setText("Modificar correos.");
        jCheckBox22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox22KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 200, -1));

        jCheckBox23.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox23.setText("Ver correos.");
        jCheckBox23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox23KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox23, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 200, -1));

        jCheckBox24.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox24.setText("Borrar correos.");
        jCheckBox24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox24KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox24, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 200, -1));

        jCheckBox25.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox25.setText("Claves.");
        jCheckBox25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox25KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox25, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 200, -1));

        jCheckBox26.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox26.setText("Cambiar clave de seguridad 1.");
        jCheckBox26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox26KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 200, -1));

        jCheckBox28.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox28.setText("Respaldos.");
        jCheckBox28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox28KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 200, -1));

        jCheckBox29.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox29.setText("Respaldo manual.");
        jCheckBox29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox29KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox29, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 200, -1));

        jCheckBox30.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox30.setText("Respaldo automático.");
        jCheckBox30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox30KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox30, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, 200, -1));

        jCheckBox31.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox31.setText("Importar Respaldo.");
        jCheckBox31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox31KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox31, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, 200, -1));

        jCheckBox32.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox32.setText("Reparar.");
        jCheckBox32.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox32KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox32, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 200, -1));

        jCheckBox33.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox33.setText("Reparador de errores.");
        jCheckBox33.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox33KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox33, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 350, 200, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Inventarios:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 120, -1));

        jCheckBox34.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox34.setText("Inventarios.");
        jCheckBox34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox34KeyPressed(evt);
            }
        });
        jP1.add(jCheckBox34, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, 160, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 480, 210, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    /*Cuando se presiona el botón de cancelar*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Cierra el formulario*/
        this.setVisible(false);     
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cancelar*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                    
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el combobox de usuarios de trabajo*/
    private void jCEstacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEstacsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCEstacsKeyPressed

    
    /*Cuandose presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Preguntar al usuario si esta seguro de guardar la configuración*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Configuración", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {                        
            /*Pide la clave de seguridad 1*/
            JPasswordField jpf = new JPasswordField();
            JOptionPane.showConfirmDialog(null, jpf, "Clave de seguridad 1:", JOptionPane.OK_CANCEL_OPTION);
                        
            /*Mensajea de éxito*/
            JOptionPane.showMessageDialog(null, "Configuración guardada con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
        }
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed
    
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de clientes*/
    private void jCheckBox15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox15KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox15KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de nuevos clientes*/
    private void jCheckBox13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox13KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox13KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de modificar clientes*/
    private void jCheckBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox1KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de borrar clientes*/
    private void jCheckBox4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox4KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ver clientes*/
    private void jCheckBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox3KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cotizaciones*/
    private void jCheckBox14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox14KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox14KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de nuevas cotizaciones*/
    private void jCheckBox9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox9KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de abrir cotizaciones*/
    private void jCheckBox10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox10KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de modificar cotizaciones*/
    private void jCheckBox11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox11KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox11KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de borrar cotizaciones*/
    private void jCheckBox16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox16KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox16KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar cotizaciones*/
    private void jCheckBox17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox17KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox17KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de enviar cotizaciones*/
    private void jCheckBox12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox12KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox12KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de proveedres*/
    private void jCheckBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de nuevos proveedores*/
    private void jCheckBox5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox5KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de modificar proveedores*/
    private void jCheckBox6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox6KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de borrar proveedres*/
    private void jCheckBox7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox7KeyPressed

    
    /*Cuando se presina una tecla en el checkbox de ver proveedores*/
    private void jCheckBox8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox8KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de inventarios*/
    private void jCheckBox34KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox34KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox34KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de configuraciones*/
    private void jCheckBox18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox18KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox18KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de correos electrónicos*/
    private void jCheckBox20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox20KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox20KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de nuevos correos*/
    private void jCheckBox21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox21KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox21KeyPressed

    
    /*Cuando se presiona una tecla en modificar correos*/
    private void jCheckBox22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox22KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox22KeyPressed

    
    /*Cuando se presiona una tecla en ver correos*/
    private void jCheckBox23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox23KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox23KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de borrar correos*/
    private void jCheckBox24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox24KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox24KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de claves*/
    private void jCheckBox25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox25KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox25KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cambiar clave de seguridad 1*/
    private void jCheckBox26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox26KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox26KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de respaldos*/
    private void jCheckBox28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox28KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox28KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de respaldo manual*/
    private void jCheckBox29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox29KeyPressed
            
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox29KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de respaldo automático*/
    private void jCheckBox30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox30KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox30KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de importar respaldo*/
    private void jCheckBox31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox31KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox31KeyPressed

    
    /*Cuando se presiona una tecla en checkbox de reparar*/
    private void jCheckBox32KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox32KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox32KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de reparador de errores*/
    private void jCheckBox33KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox33KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox33KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de restarurar sistema*/
    private void jCheckBox19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox19KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCheckBox19KeyPressed

    
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
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
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
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jCEstacs;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox31;
    private javax.swing.JCheckBox jCheckBox32;
    private javax.swing.JCheckBox jCheckBox33;
    private javax.swing.JCheckBox jCheckBox34;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
