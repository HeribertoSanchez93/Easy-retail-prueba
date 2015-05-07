//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;




/*Clase para dar de alta un nuevo cliente express*/
public class NewClienExp extends javax.swing.JFrame 
{        
    /*Consructor con argumento*/
    public NewClienExp() 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Nuevo cliente express, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en jtextarea de observ que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo de edición del nom del cliente*/
        jTNomb.grabFocus();
        
        /*Crea el grupo para los radio buttons*/
        ButtonGroup  g  = new ButtonGroup();
        g.add(jRaMor);
        g.add(jRaFis);        
              
        /*Listener para el combobox de series*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {               
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Borra los items en el combobox de sers*/
                jComSer.removeAllItems();
                
                /*Agrega una ser vacia al combobox*/
                jComSer.addItem("");

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas las seriess de los clientes*/
                try
                {
                    sQ = "SELECT ser FROM consecs WHERE tip = 'EMP'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComSer.addItem(rs.getString("ser"));
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                            
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
    }/*Fin de public NewClienExp() */    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTNomb = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jTEstad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTRFC = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTTel = new javax.swing.JTextField();
        jTCo1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTPag1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jTPag2 = new javax.swing.JTextField();
        jTCo2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTTelPer1 = new javax.swing.JTextField();
        jTTelPers2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jTNoInt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jRaFis = new javax.swing.JRadioButton();
        jRaMor = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTCta = new javax.swing.JTextField();
        jTMetPag = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jTCodEmp = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jTLada = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTExten = new javax.swing.JTextField();
        jTCel = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cliente Express");
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

        jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomb.setNextFocusableComponent(jTLada);
        jTNomb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNombFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNombFocusLost(evt);
            }
        });
        jTNomb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNombKeyPressed(evt);
            }
        });
        jP1.add(jTNomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 230, 20));

        jLabel2.setText("*Razón Social:");
        jLabel2.setToolTipText("Nombre de la Empresa o Nombnre del Cliente");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jLabel3.setText("Calle:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTNoExt);
        jTCall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCallFocusLost(evt);
            }
        });
        jTCall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCallKeyPressed(evt);
            }
        });
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 230, 20));

        jLabel4.setText("Colonia:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTCP);
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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 230, 20));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTPai);
        jTEstad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstadFocusLost(evt);
            }
        });
        jTEstad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstadKeyPressed(evt);
            }
        });
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 230, 20));

        jLabel5.setText("Estado:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel6.setText("País:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTRFC);
        jTPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPaiFocusLost(evt);
            }
        });
        jTPai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPaiKeyPressed(evt);
            }
        });
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 230, 20));

        jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCiu.setNextFocusableComponent(jTEstad);
        jTCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCiuFocusLost(evt);
            }
        });
        jTCiu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCiuKeyPressed(evt);
            }
        });
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 230, 20));

        jLabel7.setText("Ciudad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel8.setText("Cuenta:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 130, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTCiu);
        jTCP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCPFocusLost(evt);
            }
        });
        jTCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCPKeyTyped(evt);
            }
        });
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 230, 20));

        jLabel9.setText("No. Interior:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 100, -1));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 120, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jRaMor);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 120, -1));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jTAObserv);
        jTRFC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRFCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRFCFocusLost(evt);
            }
        });
        jTRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRFCKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRFCKeyTyped(evt);
            }
        });
        jP1.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 230, 20));

        jLabel11.setText("RFC:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        jLabel13.setText("Teléfono Personal 2:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTExten);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelKeyTyped(evt);
            }
        });
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 130, 20));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.setNextFocusableComponent(jTCo2);
        jTCo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo1FocusLost(evt);
            }
        });
        jTCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo1KeyPressed(evt);
            }
        });
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 230, 20));

        jLabel15.setText("Página Web1:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, -1, -1));

        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag1.setNextFocusableComponent(jTPag2);
        jTPag1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag1FocusLost(evt);
            }
        });
        jTPag1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag1KeyPressed(evt);
            }
        });
        jP1.add(jTPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 230, 20));

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setBorder(null);
        jTAObserv.setNextFocusableComponent(jTMetPag);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAObservFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAObservFocusLost(evt);
            }
        });
        jTAObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAObservKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAObserv);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 230, 40));

        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag2.setNextFocusableComponent(jComSer);
        jTPag2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag2FocusLost(evt);
            }
        });
        jTPag2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag2KeyPressed(evt);
            }
        });
        jP1.add(jTPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 230, 20));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.setNextFocusableComponent(jTCo3);
        jTCo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo2FocusLost(evt);
            }
        });
        jTCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo2KeyPressed(evt);
            }
        });
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 230, 20));

        jLabel17.setText("Correo2:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, -1));

        jLabel18.setText("Correo3:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jTPag1);
        jTCo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo3FocusLost(evt);
            }
        });
        jTCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo3KeyPressed(evt);
            }
        });
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 230, 20));

        jLabel19.setText("Página Web2:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        jLabel20.setText("Teléfono:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel21.setText("Teléfono Personal 1:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jTTelPer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPer1.setNextFocusableComponent(jTTelPers2);
        jTTelPer1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPer1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPer1FocusLost(evt);
            }
        });
        jTTelPer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPer1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPer1KeyTyped(evt);
            }
        });
        jP1.add(jTTelPer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 230, 20));

        jTTelPers2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelPers2.setNextFocusableComponent(jTCall);
        jTTelPers2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelPers2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelPers2FocusLost(evt);
            }
        });
        jTTelPers2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelPers2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelPers2KeyTyped(evt);
            }
        });
        jP1.add(jTTelPers2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 230, 20));

        jLabel10.setText("CP:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel22.setText("No. Exterior:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, -1));

        jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoExt.setNextFocusableComponent(jTNoInt);
        jTNoExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoExtFocusLost(evt);
            }
        });
        jTNoExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoExtKeyPressed(evt);
            }
        });
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 230, 20));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCol);
        jTNoInt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoIntFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoIntFocusLost(evt);
            }
        });
        jTNoInt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoIntKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoIntKeyTyped(evt);
            }
        });
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 230, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRaFis.setBackground(new java.awt.Color(255, 255, 255));
        jRaFis.setText("Persona Física");
        jRaFis.setNextFocusableComponent(jTNomb);
        jRaFis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaFisKeyPressed(evt);
            }
        });
        jPanel2.add(jRaFis, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 130, -1));

        jRaMor.setBackground(new java.awt.Color(255, 255, 255));
        jRaMor.setSelected(true);
        jRaMor.setText("Persona Moral");
        jRaMor.setNextFocusableComponent(jRaFis);
        jRaMor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaMorKeyPressed(evt);
            }
        });
        jPanel2.add(jRaMor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, -1));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 230, 30));

        jLabel28.setText("Observaciones:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));

        jLabel29.setText("Método Pago:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 130, -1));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jTCo1);
        jTCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaFocusLost(evt);
            }
        });
        jTCta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCtaKeyTyped(evt);
            }
        });
        jP1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 130, 20));

        jTMetPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMetPag.setNextFocusableComponent(jTCta);
        jTMetPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMetPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMetPagFocusLost(evt);
            }
        });
        jTMetPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMetPagKeyPressed(evt);
            }
        });
        jP1.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 130, 20));

        jLabel30.setText("Correo1:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 54, -1, 10));

        jComSer.setNextFocusableComponent(jTCodEmp);
        jComSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFocusLost(evt);
            }
        });
        jComSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerKeyPressed(evt);
            }
        });
        jP1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 100, 20));

        jTCodEmp.setBackground(new java.awt.Color(204, 255, 204));
        jTCodEmp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));
        jTCodEmp.setNextFocusableComponent(jRaMor);
        jTCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusLost(evt);
            }
        });
        jTCodEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodEmpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodEmpKeyTyped(evt);
            }
        });
        jP1.add(jTCodEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 100, 20));

        jLabel35.setText("*Serie:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 100, -1));

        jLabel36.setText("Cod.Cliente:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 100, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 430, 150, 20));

        jTLada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLada.setNextFocusableComponent(jTTel);
        jTLada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLadaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLadaFocusLost(evt);
            }
        });
        jTLada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLadaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLadaKeyTyped(evt);
            }
        });
        jP1.add(jTLada, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 50, 20));

        jLabel31.setText("Lada:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 40, 20));

        jLabel23.setText("Extensión:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jLabel41.setText("Célular:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, -1));

        jTExten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTExten.setNextFocusableComponent(jTCel);
        jTExten.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTExtenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTExtenFocusLost(evt);
            }
        });
        jTExten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExtenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTExtenKeyTyped(evt);
            }
        });
        jP1.add(jTExten, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 230, 20));

        jTCel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCelFocusLost(evt);
            }
        });
        jTCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCelKeyPressed(evt);
            }
        });
        jP1.add(jTCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 230, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                                                     
        /*Si el campo de nombre de cliente esta vacio no puede seguir*/
        if(jTNomb.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del nombre del cliente esta vacio.", "Nuevo Cliente Express", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción de nom y regresa*/
            jTNomb.grabFocus();
            return;
        }
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String sQ;
                                
        /*Comprueba si el nombre de la cliente existe por ese RFC*/                
        try
        {
            sQ  = "SELECT nom FROM emps WHERE nom = '" + jTNomb.getText() + "' AND rfc = '" + jTRFC.getText() + "' AND '" + jTRFC.getText() + "' <> ''";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces*/
            if(rs.next())
            {         
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea solamente y regresa*/
                JOptionPane.showMessageDialog(null, " El nombre de cliente: " + jTNomb.getText() + " ya existe para el RFC: " + jTRFC.getText() + ". Cambia de nombre de Cliente o de RFC.", "Cliente Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }        
        
        /*Si el RFC no es cadena vacia entonces*/
        if(jTRFC.getText().compareTo("") != 0)
        {
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario sea válido o no*/
                Star.bValRFC   = false;
                Star.vValRFC(jTRFC.getText(), true, jTRFC, true);

                /*Si no es válido entonces*/
                if(!Star.bValRFC)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                    
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Coloca el foco del teclado en el campo del rfc y regresa*/
                    jTRFC.grabFocus();             
                    return;
                }            
            }
            /*Else, es física*/
            else
            {                
                /*Válida si el rfc introducido por el usuario sea válido o no*/
                Star.bValRFC   = false;
                Star.vValRFC(jTRFC.getText(), false, jTRFC, true);

                /*Si no es válido entonces*/
                if(!Star.bValRFC)
                {
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Coloca el foco del teclado en el campo del rfc y regresa*/
                    jTRFC.grabFocus();                   
                    return;
                }       
            }
                            
            /*Comprueba que el RFC no exista en la base de datos*/
            try
            {
                sQ  = "SELECT nom FROM emps WHERE rfc = '" + jTRFC.getText() + "'";
                st  = con.createStatement();
                rs  = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                    
                    /*Coloca el borde rojo*/                               
                    jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El RFC ya existe en la base de datos para: " + rs.getString("nom"), "RFC xistente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Pon el foco del teclado en el campo de RFC y regresa*/
                    jTRFC.grabFocus();                   
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
            }           
            
        }/*Fin de if(jTRFC.getText().compareTo("") != 0)*/                           

        /*Si no se selecciono serie entonces la serie va a ser la vacia, caso contrario obtiene la serie*/
        String sSer;
        if(jComSer.getSelectedIndex()== -1)
            sSer    = "";
        else
            sSer    = jComSer.getSelectedItem().toString();
            
        /*Si no selecciono una serie y tampoco un código de cliente entonces*/
        if(jTCodEmp.getText().compareTo("")==0 && sSer.compareTo("")==0)        
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Escoge una serie o un código de cliente.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el combobox de sers y regresa*/
            jComSer.grabFocus();
            return;
        }
                
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Nuevo Cliente Express", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                       
        }
        
        /*Lee si es persona física o moral*/
        String sPers    = "F";
        if(jRaMor.isSelected())
            sPers       = "M";        
        
        /*Obtiene el código de la empesa que ingreso el usuario*/
        String sEmpTmp  = jTCodEmp.getText();                        
        
        /*Obtiene el método de pago*/
        String sMetPag  = jTMetPag.getText();
        
        /*Si el método de pago es cadena vacia entonces el método de pago será no identificable*/
        if(sMetPag.compareTo("")==0)
            sMetPag     = "No identificable";
        
        /*Obtiene la cuenta de pago*/
        String sCta     = jTCta.getText();
        
        /*Si la cuenta de pago es cadena vacia entonces la cuenta de pago será 0000*/
        if(sCta.compareTo("")==0)
            sCta = "0000";
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Obtiene el consecutivo de la cliente*/                    
        String sConsec  = "";
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = 'EMP' AND ser = '" + sSer + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sConsec         = rs.getString("consec");                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }            

        /*Genera el código de la cliente ya sea el consecutivo o el personalizado*/        
        String sEmp;
        if(sEmpTmp.compareTo("")==0)        
            sEmp        = sConsec;         
        else        
            sEmp        = sEmpTmp;         
        
        /*Aumenta uno el consecutivo de la cliente*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'EMP' AND ser = '" + sSer.replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
         }                                                        

        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO emps(nom,                                             tel,                                            telper1,                                            telper2,                                            calle,                                          col,                                            CP,                                             ciu,                                            estad,                                              pai,                                            rfc,                                            co1,                                                co2,                                            co3,                                                pagweb1,                                            pagweb2,                                        observ,                                             codemp,                                 noext,                                              noint,                                      falt,       estac,                                          pers,                               metpag,                                 cta,                                sucu,                                           nocaj,                                              ser,                              codclas,   list,         lada,                                                    exten,                                              cel)  " + 
                     "VALUES('" + jTNomb.getText().replace("'", "''") + "','" +     jTTel.getText().replace("'", "''") + "','" +    jTTelPer1.getText().replace("'", "''") + "','" +    jTTelPers2.getText().replace("'", "''") + "','" +   jTCall.getText().replace("'", "''") + "','" +   jTCol.getText().replace("'", "''") + "','" +    jTCP.getText().replace("'", "''") + "','" +     jTCiu.getText().replace("'", "''") + "','" +    jTEstad.getText().replace("'", "''") + "','" +      jTPai.getText().replace("'", "''") + "','" +    jTRFC.getText().replace("'", "''") + "','" +    jTCo1.getText().replace("'", "''") + "','" +        jTCo2.getText().replace("'", "''") + "','" +    jTCo3.getText().replace("'", "''") + "','" +        jTPag1.getText().replace("'", "''") + "','" +       jTPag2.getText().replace("'", "''") + "','" +   jTAObserv.getText().replace("'", "''") + "','" +    sEmp.replace("'", "''") + "','" +       jTNoExt.getText().replace("'", "''") + "','" +      jTNoInt.getText().replace("'", "''") + "',  now(),'" + Login.sUsrG.replace("'", "''") + "', '" +    sPers.replace("'", "''") + "', '" + sMetPag.replace("'", "''") + "', '" +   sCta.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" +     sSer.replace("'", "''") + "',     '',             1,       '" + jTLada.getText().replace("'", "''") + "', '" +      jTExten.getText().replace("'", "''") + "', '" +     jTCel.getText().replace("'", "''") + "')";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
         }

        /*Inserta en log de clientes*/
        try 
        {            
            sQ = "INSERT INTO logemps(  emp,                                                                nom,                                                ser,                            accio,       estac,                                         sucu,                                           nocaj,                                          falt) " + 
                          "VALUES('" +  sSer.replace("'", "''") + sEmp.replace("'", "''") + "','" +         jTNomb.getText().replace("'", "''") + "',  '" +     sSer.replace("'", "''") + "',   'A', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
            
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cliente: " + jTNomb.getText() + " guardada con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Sal del formulario*/
        this.dispose();
        
        /*Llama al recolector de basura*/
        System.gc();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usuario si esta seguro de abandonar la alta del cliente*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la alta del cliente?", "Salir Alta Cliente", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();        
            this.dispose();
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de nom del cliente*/
    private void jTNombKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNombKeyPressed

    
    /*Cuando se presiona un botón en el campo de edición call*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de col*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de cp*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de ciu*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de estad*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de pai*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de RFC*/
    private void jTRFCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRFCKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

        
    /*Cuando se presiona una tecla en el campo de edición del teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de página web1*/
    private void jTPag1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de página web2*/
    private void jTPag2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de co3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el text area de observ*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de nom*/
    private void jTNombFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomb.setSelectionStart(0);jTNomb.setSelectionEnd(jTNomb.getText().length());        
        
    }//GEN-LAST:event_jTNombFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de call*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de col*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());        
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de estad*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de pai*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());        
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    
    /*Cuando se gana el foco del teclado en el campo de edición de co1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());        
        
    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de co2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());        
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de co3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());        
        
    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web 1*/
    private void jTPag1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag1.setSelectionStart(0);jTPag1.setSelectionEnd(jTPag1.getText().length());        
        
    }//GEN-LAST:event_jTPag1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web 2*/
    private void jTPag2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag2.setSelectionStart(0);jTPag2.setSelectionEnd(jTPag2.getText().length());        
        
    }//GEN-LAST:event_jTPag2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de area observ*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed
        
    
    /*Cuando se gana el foco del teclado en el campo de tel personal 1*/
    private void jTTelPer1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer1.setSelectionStart(0);jTTelPer1.setSelectionEnd(jTTelPer1.getText().length());        
        
    }//GEN-LAST:event_jTTelPer1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tel personal 2*/
    private void jTTelPers2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPers2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPers2.setSelectionStart(0);jTTelPers2.setSelectionEnd(jTTelPers2.getText().length());        
        
    }//GEN-LAST:event_jTTelPers2FocusGained

    
    /*Cuando se presiona una tecla en el campo de tel personal 1*/
    private void jTTelPer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPer1KeyPressed

    
    /*Cuando se presiona una tecla en el tel personal 2*/
    private void jTTelPers2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPers2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelPers2KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTRFC.setCaretPosition(0);
        
        /*Lee el texto introducido por el usurio*/
        String sLec = jTRFC.getText();
        
        /*Si es cadena vacia entonces solo regresar*/
        if(sLec.compareTo("")==0)
            return;
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sLec.length()> 21)
            jTRFC.setText(jTRFC.getText().substring(0, 21));
        
        /*Convierte los caes escritos en mayúsculas*/
        sLec = sLec.toUpperCase();
        
        /*Colocalos en el campo*/
        jTRFC.setText(sLec); 
        
        /*Si es persona moral entonces válida si es bueno el RFC*/
        if(jRaMor.isSelected())
            Star.vValRFC(sLec, true, jTRFC, true);                                    
        /*Else, es física igual validalo*/
        else
            Star.vValRFC(sLec, false, jTRFC, true);                        
               
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de nom*/
    private void jTNombFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNomb.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNomb.getText().compareTo("")!=0)
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNomb.getText().length()> 255)
            jTNomb.setText(jTNomb.getText().substring(0, 255));
      
    }//GEN-LAST:event_jTNombFocusLost
        
    
    /*Cuando se pierde el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel.getText().length()> 255)
            jTTel.setText(jTTel.getText().substring(0, 255));
                       
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de texto de teléfono personal 1*/
    private void jTTelPer1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPer1.setCaretPosition(0);
                     
    }//GEN-LAST:event_jTTelPer1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de texto de teléfono personal 2*/
    private void jTTelPers2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPers2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTelPers2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPers2.getText().length()> 255)
            jTTelPers2.setText(jTTelPers2.getText().substring(0, 255));
                
    }//GEN-LAST:event_jTTelPers2FocusLost

    
    /*Cuando se gana el foco del teclado en el campo de edición de call*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de col*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de eición de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCP.getText().length()> 6)
            jTCP.setText(jTCP.getText().substring(0, 6));
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCiu.getText().length()> 255)
            jTCiu.setText(jTCiu.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de estad*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEstad.getText().length()> 255)
            jTEstad.setText(jTEstad.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el campode edición de pai*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPai.getText().length()> 255)
            jTPai.setText(jTPai.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo de edición de co1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo1.getText().toLowerCase().contains("@") || !jTCo1.getText().toLowerCase().contains(".")) && jTCo1.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));                        
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo2.getText().toLowerCase().contains("@") || !jTCo2.getText().toLowerCase().contains(".")) && jTCo2.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));
                                
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                                       
        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo3.getText().toLowerCase().contains("@") || !jTCo3.getText().toLowerCase().contains(".")) && jTCo3.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));
                        
    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de página web*/
    private void jTPag1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTPag1.getText().contains("www") || !jTPag1.getText().toLowerCase().contains(".")) && jTPag1.getText().toLowerCase().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPag1.getText().length()> 255)
            jTPag1.setText(jTPag1.getText().substring(0, 255));
                                
    }//GEN-LAST:event_jTPag1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de página web 2*/
    private void jTPag2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPag2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTPag2.getText().contains("www") || !jTPag2.getText().toLowerCase().contains(".")) && jTPag2.getText().toLowerCase().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es una página valida.", "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el borde rojo*/                               
            jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
        }            
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPag2.getText().length()> 255)
            jTPag2.setText(jTPag2.getText().substring(0, 255));
                              
    }//GEN-LAST:event_jTPag2FocusLost
    
    
    /*Cuando se pierde el foco del teclado en el campo de área de observ*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTAObserv.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTAObserv.getText().length()> 255)
            jTAObserv.setText(jTAObserv.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAObservFocusLost
    
    
    /*Cuando se presiona una tecla en el campo de texto de número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo de texto de número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoIntKeyPressed

        
    /*Cuando se pierde el foco del teclado en el campo de número exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoExt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de número exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoInt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
               
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se presiona una tecla typed en el campo de RFC*/
    private void jTRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRFCKeyTyped
        
        
    
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono persola 1*/
    private void jTTelPer1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTelPer1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono personal 2*/
    private void jTTelPers2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPers2KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTelPers2KeyTyped

            
    /*Cuando se tipea una tecla en el campo de CP*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTCPKeyTyped

        
    /*Cuando se tipea una tecla en el campo de número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped
            
    
    /*Cuando se presiona una tecla en el control de pers física*/
    private void jRaFisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaFisKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaFisKeyPressed

    
    /*Cuando se presiona una tecla en el radio button de moral*/
    private void jRaMorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaMorKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaMorKeyPressed

    
    /*Cuando se presiona una tecla en el panel de los radio buttons*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanel2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPag.setSelectionStart(0);jTMetPag.setSelectionEnd(jTMetPag.getText().length());        
        
    }//GEN-LAST:event_jTMetPagFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());        
        
    }//GEN-LAST:event_jTCtaFocusGained

    
    /*Cuando se presiona una tecla en el campo de método de pago*/
    private void jTMetPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMetPagKeyPressed

    
    /*Cuando se presiona una tecla en el campo de cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de cuenta*/
    private void jTCtaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCtaKeyTyped
    
       
    /*Cuando se presiona una tecla en el combobox de las sers*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando se presiona una tecla en el campo del código de la cliente*/
    private void jTCodEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodEmpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código de la cliente*/
    private void jTCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodEmp.setSelectionStart(0);jTCodEmp.setSelectionEnd(jTCodEmp.getText().length());
        
    }//GEN-LAST:event_jTCodEmpFocusGained

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTCodEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodEmpKeyTyped

    
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

    
    /*Cuando se pierde el foco del teclado en el combo de la serie*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se gana el foco el teclado en el campo de la lada*/
    private void jTLadaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLada.setSelectionStart(0);jTLada.setSelectionEnd(jTLada.getText().length());
        
    }//GEN-LAST:event_jTLadaFocusGained

    
    /*Cuando se presiona una tecla en el campo de la lada*/
    private void jTLadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLadaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jTLadaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la lada*/
    private void jTLadaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLadaKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTLadaKeyTyped

    
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

    
    /*Cuando se gana el foco del teclado en el campo de la extensión*/
    private void jTExtenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTExten.setSelectionStart(0);
        jTExten.setSelectionEnd(jTExten.getText().length());

    }//GEN-LAST:event_jTExtenFocusGained

    
    /*Cuando se presiona una tecla en el campo de la extensión*/
    private void jTExtenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTExtenKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la extensión*/
    private void jTExtenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExtenKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTExtenKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del cálular*/
    private void jTCelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCel.setSelectionStart(0);
        jTCel.setSelectionEnd(jTCel.getText().length());

    }//GEN-LAST:event_jTCelFocusGained

    
    /*Cuando se presiona una tecla en el campo del cálular*/
    private void jTCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCelKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTLadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTLada.setCaretPosition(0);
        
    }//GEN-LAST:event_jTLadaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExtenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExten.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExtenFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMetPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodEmpFocusLost

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {                       
            /*Pregunta al usuario si esta seguro de abandonar la alta del cliente*/                
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la alta del cliente?", "Salir Alta Cliente", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)            
                dispose();
        }
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRaFis;
    private javax.swing.JRadioButton jRaMor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodEmp;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTExten;
    private javax.swing.JTextField jTLada;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNomb;
    private javax.swing.JTextField jTPag1;
    private javax.swing.JTextField jTPag2;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelPer1;
    private javax.swing.JTextField jTTelPers2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NewEmp extends javax.swing.JFrame */
