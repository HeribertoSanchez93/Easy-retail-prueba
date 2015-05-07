//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;




/*Clase para modificar la dirección de envio*/
public class DomEntProv extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color      colOri;
    
    /*Contiene la referencia del otro formulario para poder accesar a las variables*/
    private final Prov newProv;
    
    
    /*Constructor sin argumentos*/
    public DomEntProv(Prov newPr) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Inicializa la referencia del otro formulario*/
        newProv = newPr;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Dirección de envio, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                        
        /*Pon el foco del teclado en el primer control*/
        jTLada.grabFocus();

        /*Inicializa los controles con los públicos del otro formulario*/                                
        jTTel.setText       (newProv.sTelEn);
        jTLada.setText      (newProv.sLadaEn);        
        jTExten.setText     (newProv.sExtEn);                          
        jTCel.setText       (newProv.sCelEn);                                  
        jTTelPer1.setText   (newProv.sTelPer1En);                                          
        jTTelPers2.setText  (newProv.sTelPer2En);                                                  
        jTCall.setText      (newProv.sCallEn);                                                          
        jTCol.setText       (newProv.sColEn);        
        jTNoExt.setText     (newProv.sNoExtEn);        
        jTNoInt.setText     (newProv.sIntEn);
        jTCP.setText        (newProv.sCPEn);        
        jTCiu.setText       (newProv.sCiuEn);                
        jTEstad.setText     (newProv.sEstadEn);                        
        jTPai.setText       (newProv.sPaiEn);                        
        jTCo1.setText       (newProv.sCo1En);                        
        jTCo2.setText       (newProv.sCo2En);                        
        jTCo3.setText       (newProv.sCo3En);                        
        
    }/*Fin de public LPrecs() */
               
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jP1 = new javax.swing.JPanel();
        jLAyu = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTLada = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTExten = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTCel = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTTelPer1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTTelPers2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTNoInt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTCiu = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTEstad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTCo1 = new javax.swing.JTextField();
        jLCo1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTCo2 = new javax.swing.JTextField();
        jLCo2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jLCo3 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Utilidad %:");

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 220, 20));

        jLabel40.setText("Teléfono:");
        jP1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel39.setText("Lada:");
        jP1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 40, 20));

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
        jP1.add(jTLada, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 50, 20));

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
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 130, 20));

        jLabel20.setText("Extensión:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

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
        jP1.add(jTExten, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 230, 20));

        jLabel41.setText("Celular:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        jTCel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel.setNextFocusableComponent(jTTelPer1);
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
        jP1.add(jTCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 230, 20));

        jLabel21.setText("Teléfono Personal 1:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

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
        jP1.add(jTTelPer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 230, 20));

        jLabel13.setText("Teléfono Personal 2:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

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
        jP1.add(jTTelPers2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 230, 20));

        jLabel3.setText("Calle:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTCol);
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
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 230, 20));

        jLabel4.setText("Colonia:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTNoExt);
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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 230, 20));

        jLabel22.setText("No. Exterior:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, -1));

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
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 230, 20));

        jLabel9.setText("No. Interior:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCP);
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
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 230, 20));

        jLabel10.setText("CP:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

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
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 230, 20));

        jLabel7.setText("Ciudad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

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
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 230, 20));

        jLabel5.setText("Estado:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

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
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 230, 20));

        jLabel6.setText("País:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTCo1);
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
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 230, 20));

        jLabel30.setText("Correo1:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, 10));

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
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 230, 20));

        jLCo1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo1.setForeground(new java.awt.Color(51, 51, 255));
        jLCo1.setText("-");
        jLCo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo1MouseExited(evt);
            }
        });
        jP1.add(jLCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 230, -1));

        jLabel17.setText("Correo2:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

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
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 230, 20));

        jLCo2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo2.setForeground(new java.awt.Color(51, 51, 255));
        jLCo2.setText("-");
        jLCo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo2MouseExited(evt);
            }
        });
        jP1.add(jLCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 230, -1));

        jLabel18.setText("Correo3:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jBGuar);
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
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 230, 20));

        jLCo3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 11)); // NOI18N
        jLCo3.setForeground(new java.awt.Color(51, 51, 255));
        jLCo3.setText("-");
        jLCo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLCo3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLCo3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLCo3MouseExited(evt);
            }
        });
        jP1.add(jLCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 230, -1));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 110, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTLada);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
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
           
       
    /*Cuando se mueve la rueda del mouse en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando el mouse se arrastra en la forma*/
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

    
    /*Cuando se gana el foco del teclado en el campo de la lada*/
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

    
    /*Cuando se gana el foco del teclado en el campo del teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);
        jTTel.setSelectionEnd(jTTel.getText().length());

    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el cursor al principio del control*/
        jTTel.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel.getText().length()> 255)
            jTTel.setText(jTTel.getText().substring(0, 255));

    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se presiona un tecla en el campo del teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se tipea una tecla en el campo del teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))
            evt.consume();

    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se gana el foco del teclaod en el campo de la extensión*/
    private void jTExtenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTExten.setSelectionStart(0);
        jTExten.setSelectionEnd(jTExten.getText().length());

    }//GEN-LAST:event_jTExtenFocusGained

    
    /*Cuando se presiona un tecla en el campo de la extensión*/
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

    
    /*Cuando se gana el foco del teclado en el campo del célular*/
    private void jTCelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCel.setSelectionStart(0);
        jTCel.setSelectionEnd(jTCel.getText().length());

    }//GEN-LAST:event_jTCelFocusGained

    
    /*Cuando se presiona una tecla en el campo del célular*/
    private void jTCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCelKeyPressed

    
    /*Cuadno se gana el foco del teclado en el campo del teléfono personal 1*/
    private void jTTelPer1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPer1.setSelectionStart(0);jTTelPer1.setSelectionEnd(jTTelPer1.getText().length());                

    }//GEN-LAST:event_jTTelPer1FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del teléfono personal 1*/
    private void jTTelPer1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPer1FocusLost

        /*Coloca el cursor al principio del control*/
        jTTelPer1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPer1.getText().length()> 255)
            jTTelPer1.setText(jTTelPer1.getText().substring(0, 255));

    }//GEN-LAST:event_jTTelPer1FocusLost

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 1*/
    private void jTTelPer1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTelPer1KeyPressed

    
    /*Cuando se tipea una tecla en el campo del teléfono personal 1*/
    private void jTTelPer1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPer1KeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));        

    }//GEN-LAST:event_jTTelPer1KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del teléfono personal 2*/
    private void jTTelPers2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPers2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTelPers2.setSelectionStart(0);jTTelPers2.setSelectionEnd(jTTelPers2.getText().length());
                        
    }//GEN-LAST:event_jTTelPers2FocusGained
    
    /*Cuando se pierde el foco del teclado en el campo del teléfono personal 2*/
    private void jTTelPers2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelPers2FocusLost

        /*Coloca el cursor al principio del control*/
        jTTelPers2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelPers2.getText().length()> 255)
            jTTelPers2.setText(jTTelPers2.getText().substring(0, 255));

    }//GEN-LAST:event_jTTelPers2FocusLost

    
    /*Cuando se presiona una tecla en el campo del teléfono personal 2*/
    private void jTTelPers2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPers2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTelPers2KeyPressed

    
    /*Cuando se tipea una tecla en el campo del teléfono personal 2*/
    private void jTTelPers2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelPers2KeyTyped

        /*Si el carácter introducido es minúscula entonces*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTTelPers2KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la calle*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());

    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la calle*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el cursor al principio del control*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));

    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se presiona una tecla en el campo de la calle*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la colonia*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());

    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la colonia*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el cursor al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));

    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se presiona una tecla en el campo de la colonia*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del número de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());                

    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del número de exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoExt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));

    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se presiona una tecla en el campo del número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());                   

    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoInt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));

    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se presiona una tecla en el campo del número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNoIntKeyPressed

    
    /*Cuando se tipea una tecla en el campo del número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped

        /*Si el carácter introducido es minúscula entonces*/
        if(Character.isLowerCase(evt.getKeyChar()))        
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));        

    }//GEN-LAST:event_jTNoIntKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());

    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el cursor al principio del control*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCP.getText().length()> 6)
            jTCP.setText(jTCP.getText().substring(0, 6));

    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se presiona una tecla en el campo del CP*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se tipea una tecla en el campo del CP*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTCPKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la ciudad*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());

    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la ciudad*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el cursor al principio del control*/
        jTCiu.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCiu.getText().length()> 255)
            jTCiu.setText(jTCiu.getText().substring(0, 255));

    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se presiona una tecla en el campo de la ciudad*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo el estado*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());                

    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del estado*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el cursor al principio del control*/
        jTEstad.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEstad.getText().length()> 255)
            jTEstad.setText(jTEstad.getText().substring(0, 255));

    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se presiona una tecla en el campo del estado*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del pais*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());

    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del pais*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el cursor al principio del control*/
        jTPai.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPai.getText().length()> 255)
            jTPai.setText(jTPai.getText().substring(0, 255));

    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se presiona una tecla en el campo del pais*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del correo 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());

    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del correo 1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el cursor al principio del control*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Valida el correo electrónico que sea válido*/
        if((!jTCo1.getText().toLowerCase().contains("@") || !jTCo1.getText().toLowerCase().contains(".")) && jTCo1.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
        }

        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));

        /*Coloca en el label de correo 1 el correo que el usuario escribió*/
        jLCo1.setText(jTCo1.getText());

        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo1.getText().compareTo("")==0)
            jLCo1.setText("-");

    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se presiona una tecla en el campo del correo 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona con el mouse en label de correo 1*/
    private void jLCo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseClicked

        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        java.awt.Desktop desk;
        if(java.awt.Desktop.isDesktopSupported() && (desk = java.awt.Desktop.getDesktop()).isSupported(java.awt.Desktop.Action.MAIL))
        {
            java.net.URI mailto;
            try
            {
                mailto = new java.net.URI("mailto:" + jLCo1.getText() + "?subject=");
            }
            catch(URISyntaxException expnUriSynta)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());                                                       
                return;                                        
            }

            try
            {
                desk.mail(mailto);
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                       
            }
        }

    }//GEN-LAST:event_jLCo1MouseClicked

    
    /*Cuando el mouse entra en el label de correo 1*/
    private void jLCo1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseEntered

        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLCo1MouseEntered

    
    /*Cuando el mouse sale del label de correo 1*/
    private void jLCo1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo1MouseExited

        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jLCo1MouseExited

    
    /*Cuando se gana el foco del teclado en el campo del correo 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());

    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el cursor al principio del control*/
        jTCo2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Valida el correo electrónico que sea válido*/
        if((!jTCo2.getText().toLowerCase().contains("@") || !jTCo2.getText().toLowerCase().contains(".")) && jTCo2.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
        }

        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));

        /*Coloca en el label de correo 2 el correo que el usuario escribió*/
        jLCo2.setText(jTCo2.getText());

        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo2.getText().compareTo("")==0)
            jLCo2.setText("-");

    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se presiona una tecla en el campo del correo 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se hace click con el mouse en el label de correo 2*/
    private void jLCo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseClicked

        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        java.awt.Desktop de;
        if(java.awt.Desktop.isDesktopSupported() && (de = java.awt.Desktop.getDesktop()).isSupported(java.awt.Desktop.Action.MAIL))
        {
            java.net.URI mailto;
            try
            {
                mailto = new java.net.URI("mailto:" + jLCo2.getText() + "?subject=");
            }
            catch(URISyntaxException expnUriSynta)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());                                                       
                return;                                        
            }

            try
            {
                de.mail(mailto);
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                      
            }
        }

    }//GEN-LAST:event_jLCo2MouseClicked

    
    /*Cuando el mouse entra en el label del correo 2*/
    private void jLCo2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseEntered

        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLCo2MouseEntered

    
    /*Cuando el mouse sale del label de correo 2*/
    private void jLCo2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo2MouseExited

        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jLCo2MouseExited

    
    /*Cuando se gana el foco del teclado en el campo del correo 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());

    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el cursor al principio del control*/
        jTCo3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/
        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Valida el correo electrónico que sea válido*/
        if((!jTCo3.getText().toLowerCase().contains("@") || !jTCo3.getText().toLowerCase().contains(".")) && jTCo3.getText().compareTo("")!=0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
        }

        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));

        /*Coloca en el label de correo 3 el correo que el usuario escribió*/
        jLCo3.setText(jTCo3.getText());

        /*Si el usuario no escribió nada entonces solo poner el guión*/
        if(jTCo3.getText().compareTo("")==0)
            jLCo3.setText("-");

    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se presiona una tecla en el campo del correo 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se hace clic con el mouse en el label de correo 3*/
    private void jLCo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseClicked

        /*Abre la aplicación de correo por default del usuario con el correo electrónico clickeado*/
        java.awt.Desktop de;
        if(java.awt.Desktop.isDesktopSupported() && (de = java.awt.Desktop.getDesktop()).isSupported(java.awt.Desktop.Action.MAIL))
        {
            java.net.URI mailto;
            try
            {
                mailto = new java.net.URI("mailto:" + jLCo1.getText() + "?subject=");
            }
            catch(URISyntaxException expnUriSynta)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());                                                       
                return;                                        
            }

            try
            {
                de.mail(mailto);
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                       
            }
        }

    }//GEN-LAST:event_jLCo3MouseClicked

    
    /*Cuando el mouse entra en el label de correo 3*/
    private void jLCo3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseEntered

        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLCo3MouseEntered

    
    /*Cuando el mouse sale del label de correo 3*/
    private void jLCo3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLCo3MouseExited

        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jLCo3MouseExited

    
    /*Cuando el mouse entra en el botón de guaradar*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);

    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuadno el mouse sale del botón de guardar*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);

    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Valida el correo electrónico que sea válido*/
        if((!jTCo1.getText().toLowerCase().contains("@") || !jTCo1.getText().toLowerCase().contains(".")) && jTCo1.getText().compareTo("")!=0)
        {
            /*Coloca el borde rojo y el foco en el control*/                               
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
            jTCo1.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }            
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo2.getText().toLowerCase().contains("@") || !jTCo2.getText().toLowerCase().contains(".")) && jTCo2.getText().compareTo("")!=0)
        {
            /*Coloca el borde rojo y el foco en el control*/                               
            jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
            jTCo2.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }            
        
        /*Valida el correo electrónico que sea válido*/
        if((!jTCo3.getText().toLowerCase().contains("@") || !jTCo3.getText().toLowerCase().contains(".")) && jTCo3.getText().compareTo("")!=0)
        {
            /*Coloca el borde rojo y el foco en el control*/                               
            jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));                
            jTCo3.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No es un correo electrónico valido.", "Nuevo Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }            
        
        /*Lee todos los campos escritos en las variables del otro formulario*/
        newProv.sTelEn       = jTTel.getText();
        newProv.sLadaEn      = jTLada.getText();
        newProv.sExtEn       = jTExten.getText();
        newProv.sCelEn       = jTCel.getText();
        newProv.sTelPer1En   = jTTelPer1.getText();
        newProv.sTelPer2En   = jTTelPers2.getText();
        newProv.sCallEn      = jTCall.getText();
        newProv.sColEn       = jTCol.getText();
        newProv.sNoExtEn     = jTNoExt.getText();
        newProv.sIntEn       = jTNoInt.getText();
        newProv.sCPEn        = jTCP.getText();
        newProv.sCiuEn       = jTCiu.getText();
        newProv.sEstadEn     = jTEstad.getText();
        newProv.sPaiEn       = jTPai.getText();
        newProv.sCo1En       = jTCo1.getText();
        newProv.sCo2En       = jTCo2.getText();
        newProv.sCo3En       = jTCo3.getText();
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuadno se presiona una tecla en el botón de guaradr*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de la lada*/
    private void jTLadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLadaFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTLada.setCaretPosition(0);
        
    }//GEN-LAST:event_jTLadaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la extensiòn*/
    private void jTExtenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExtenFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTExten.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExtenFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del célular*/
    private void jTCelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCelFocusLost
    
      
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
    private javax.swing.JLabel jLCo1;
    private javax.swing.JLabel jLCo2;
    private javax.swing.JLabel jLCo3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTExten;
    private javax.swing.JTextField jTLada;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelPer1;
    private javax.swing.JTextField jTTelPers2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Proveedors extends javax.swing.JFrame */
