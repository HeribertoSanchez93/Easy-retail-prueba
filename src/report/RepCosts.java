//Paquete
package report;

//Importaciones
import java.awt.Cursor;
import ptovta.Star;
import ptovta.Login;
import ptovta.Busc;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
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




/*Clase para controlar el reporteador de costeos*/
public class RepCosts extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color      colOri;
    
    
    
    
    
    /*Constructor sin argumentos*/
    public RepCosts() 
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
        this.setTitle("Reporte costeos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Oculta el campo de serie*/
        jTSer.setVisible(false);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFini = new Date();
        jDateDe.setDate (fFini);
        jDateA.setDate  (fFini);
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
               
        /*Habilita el control de fechas*/
        jDateDe.setEnabled                  (true);
        jDateA.setEnabled                   (true);        
        jDateDe.getDateEditor().setEnabled  (false);
        jDateA.getDateEditor().setEnabled   (false);            
        
    }/*Fin de public RepCosts() */
        
            
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jDateDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateA = new com.toedter.calendar.JDateChooser();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jTSucu = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTCaj = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTUsr = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jBBuscProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTAna = new javax.swing.JTextField();
        jBBusAna = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTLug = new javax.swing.JTextField();
        jBBusLug = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTLin = new javax.swing.JTextField();
        jTMarc = new javax.swing.JTextField();
        jBLin = new javax.swing.JButton();
        jBMarc = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jTClas = new javax.swing.JTextField();
        jBImp = new javax.swing.JButton();
        jBClas = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTColo = new javax.swing.JTextField();
        jTUni = new javax.swing.JTextField();
        jTPes = new javax.swing.JTextField();
        jBPes = new javax.swing.JButton();
        jBColo = new javax.swing.JButton();
        jBUni = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTUbiAd = new javax.swing.JTextField();
        jBUbiAd = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jTMed = new javax.swing.JTextField();
        jBMed = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jTFab = new javax.swing.JTextField();
        jBFab = new javax.swing.JButton();
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
        jBSal.setNextFocusableComponent(jTSucu);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 110, 30));

        jDateDe.setEnabled(false);
        jDateDe.setNextFocusableComponent(jDateA);
        jDateDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateDeKeyPressed(evt);
            }
        });
        jP1.add(jDateDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 140, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 150, 20));

        jDateA.setEnabled(false);
        jDateA.setNextFocusableComponent(jBAbr);
        jDateA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateAKeyPressed(evt);
            }
        });
        jP1.add(jDateA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 140, 20));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setText("Todas las Fechas");
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 130, 20));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 110, 30));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, -1));

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
        jP1.add(jTSucu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 130, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("De:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 130, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Caja:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 130, -1));

        jTCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCaj.setNextFocusableComponent(jTUsr);
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
        jP1.add(jTCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 130, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Usuario:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 130, -1));

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
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 90, 20));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Usuario(s)");
        jBUsr.setNextFocusableComponent(jTProd);
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
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 30, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Sucursal:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 130, -1));

        jBBuscProd.setBackground(new java.awt.Color(255, 255, 255));
        jBBuscProd.setText("...");
        jBBuscProd.setToolTipText("Buscar Producto(s)");
        jBBuscProd.setNextFocusableComponent(jTFab);
        jBBuscProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscProdMouseExited(evt);
            }
        });
        jBBuscProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscProdActionPerformed(evt);
            }
        });
        jBBuscProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscProdKeyPressed(evt);
            }
        });
        jP1.add(jBBuscProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 30, 20));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBBuscProd);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProdFocusLost(evt);
            }
        });
        jTProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProdKeyTyped(evt);
            }
        });
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 100, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Producto:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 130, -1));

        jTAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAna.setNextFocusableComponent(jBBusAna);
        jTAna.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAnaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAnaFocusLost(evt);
            }
        });
        jTAna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAnaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAnaKeyTyped(evt);
            }
        });
        jP1.add(jTAna, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 90, 20));

        jBBusAna.setBackground(new java.awt.Color(255, 255, 255));
        jBBusAna.setText("jButton1");
        jBBusAna.setToolTipText("Buscar Anaquel(es)");
        jBBusAna.setNextFocusableComponent(jTLug);
        jBBusAna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusAnaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusAnaMouseExited(evt);
            }
        });
        jBBusAna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusAnaActionPerformed(evt);
            }
        });
        jBBusAna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusAnaKeyPressed(evt);
            }
        });
        jP1.add(jBBusAna, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Anaquel:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 130, -1));

        jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug.setNextFocusableComponent(jBBusLug);
        jTLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLugFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLugFocusLost(evt);
            }
        });
        jTLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLugKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLugKeyTyped(evt);
            }
        });
        jP1.add(jTLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 100, 20));

        jBBusLug.setBackground(new java.awt.Color(255, 255, 255));
        jBBusLug.setText("jButton1");
        jBBusLug.setToolTipText("Buscar Lugar(es)");
        jBBusLug.setNextFocusableComponent(jTUbiAd);
        jBBusLug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusLugMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusLugMouseExited(evt);
            }
        });
        jBBusLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusLugActionPerformed(evt);
            }
        });
        jBBusLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusLugKeyPressed(evt);
            }
        });
        jP1.add(jBBusLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 30, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Lugar:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 130, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Ubicación A:");
        jLabel14.setToolTipText("Ubicación Adicional");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 130, -1));

        jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLin.setNextFocusableComponent(jBLin);
        jTLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLinFocusLost(evt);
            }
        });
        jTLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLinKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTLinKeyTyped(evt);
            }
        });
        jP1.add(jTLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 90, 20));

        jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMarc.setNextFocusableComponent(jBMarc);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMarcKeyTyped(evt);
            }
        });
        jP1.add(jTMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, 90, 20));

        jBLin.setBackground(new java.awt.Color(255, 255, 255));
        jBLin.setText("jButton1");
        jBLin.setToolTipText("Buscar Línea(s)");
        jBLin.setNextFocusableComponent(jTClas);
        jBLin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLinMouseExited(evt);
            }
        });
        jBLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLinActionPerformed(evt);
            }
        });
        jBLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLinKeyPressed(evt);
            }
        });
        jP1.add(jBLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 30, 20));

        jBMarc.setBackground(new java.awt.Color(255, 255, 255));
        jBMarc.setText("jButton2");
        jBMarc.setToolTipText("Buscar Marca(s)");
        jBMarc.setNextFocusableComponent(jTMed);
        jBMarc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMarcMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMarcMouseExited(evt);
            }
        });
        jBMarc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMarcActionPerformed(evt);
            }
        });
        jBMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMarcKeyPressed(evt);
            }
        });
        jP1.add(jBMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, 30, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Línea:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 120, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Clasificación:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 130, -1));

        jTImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTImp.setNextFocusableComponent(jBImp);
        jTImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpFocusLost(evt);
            }
        });
        jTImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTImpKeyTyped(evt);
            }
        });
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 100, 20));

        jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClas.setNextFocusableComponent(jBClas);
        jTClas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasFocusLost(evt);
            }
        });
        jTClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTClasKeyTyped(evt);
            }
        });
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 100, 20));

        jBImp.setBackground(new java.awt.Color(255, 255, 255));
        jBImp.setText("jButton1");
        jBImp.setToolTipText("Buscar Impuesto(s)");
        jBImp.setNextFocusableComponent(jTUni);
        jBImp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpMouseExited(evt);
            }
        });
        jBImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpActionPerformed(evt);
            }
        });
        jBImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpKeyPressed(evt);
            }
        });
        jP1.add(jBImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 30, 20));

        jBClas.setBackground(new java.awt.Color(255, 255, 255));
        jBClas.setText("jButton1");
        jBClas.setToolTipText("Buscar Clasificación(es)");
        jBClas.setNextFocusableComponent(jTImp);
        jBClas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBClasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBClasMouseExited(evt);
            }
        });
        jBClas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBClasActionPerformed(evt);
            }
        });
        jBClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBClasKeyPressed(evt);
            }
        });
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 30, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Impuesto:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 130, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Unidad:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 130, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Fabricante:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 130, -1));

        jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTColo.setNextFocusableComponent(jBColo);
        jTColo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTColoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTColoFocusLost(evt);
            }
        });
        jTColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTColoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTColoKeyTyped(evt);
            }
        });
        jP1.add(jTColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 100, 20));

        jTUni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUni.setNextFocusableComponent(jBUni);
        jTUni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUniFocusLost(evt);
            }
        });
        jTUni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUniKeyTyped(evt);
            }
        });
        jP1.add(jTUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 90, 20));

        jTPes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPes.setNextFocusableComponent(jBPes);
        jTPes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPesFocusLost(evt);
            }
        });
        jTPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPesKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPesKeyTyped(evt);
            }
        });
        jP1.add(jTPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 100, 20));

        jBPes.setBackground(new java.awt.Color(255, 255, 255));
        jBPes.setText("jButton1");
        jBPes.setToolTipText("Buscar Peso(s)");
        jBPes.setNextFocusableComponent(jTColo);
        jBPes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPesMouseExited(evt);
            }
        });
        jBPes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPesActionPerformed(evt);
            }
        });
        jBPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPesKeyPressed(evt);
            }
        });
        jP1.add(jBPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 30, 20));

        jBColo.setBackground(new java.awt.Color(255, 255, 255));
        jBColo.setText("jButton1");
        jBColo.setToolTipText("Buscar Color(es)");
        jBColo.setNextFocusableComponent(jTMarc);
        jBColo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBColoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBColoMouseExited(evt);
            }
        });
        jBColo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBColoActionPerformed(evt);
            }
        });
        jBColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBColoKeyPressed(evt);
            }
        });
        jP1.add(jBColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 30, 20));

        jBUni.setBackground(new java.awt.Color(255, 255, 255));
        jBUni.setText("jButton1");
        jBUni.setToolTipText("Buscar Unidad(es)");
        jBUni.setNextFocusableComponent(jTPes);
        jBUni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUniMouseExited(evt);
            }
        });
        jBUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUniActionPerformed(evt);
            }
        });
        jBUni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUniKeyPressed(evt);
            }
        });
        jP1.add(jBUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 30, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Color:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 130, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Marca:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 130, -1));

        jTUbiAd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUbiAd.setNextFocusableComponent(jBUbiAd);
        jTUbiAd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUbiAdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUbiAdFocusLost(evt);
            }
        });
        jTUbiAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUbiAdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUbiAdKeyTyped(evt);
            }
        });
        jP1.add(jTUbiAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 100, 20));

        jBUbiAd.setBackground(new java.awt.Color(255, 255, 255));
        jBUbiAd.setText("jButton1");
        jBUbiAd.setToolTipText("Buscar Ubicacion(es)");
        jBUbiAd.setNextFocusableComponent(jTLin);
        jBUbiAd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUbiAdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUbiAdMouseExited(evt);
            }
        });
        jBUbiAd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUbiAdActionPerformed(evt);
            }
        });
        jBUbiAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUbiAdKeyPressed(evt);
            }
        });
        jP1.add(jBUbiAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 30, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Peso:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 130, -1));

        jTMed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMed.setNextFocusableComponent(jBMed);
        jTMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMedFocusLost(evt);
            }
        });
        jTMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMedKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMedKeyTyped(evt);
            }
        });
        jP1.add(jTMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 100, 20));

        jBMed.setBackground(new java.awt.Color(255, 255, 255));
        jBMed.setText("jButton1");
        jBMed.setToolTipText("Buscar Medida(s)");
        jBMed.setNextFocusableComponent(jCTFech);
        jBMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMedMouseExited(evt);
            }
        });
        jBMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMedActionPerformed(evt);
            }
        });
        jBMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMedKeyPressed(evt);
            }
        });
        jP1.add(jBMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 30, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Medida:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 130, -1));

        jTFab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFab.setNextFocusableComponent(jBFab);
        jTFab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFabFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFabFocusLost(evt);
            }
        });
        jTFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFabKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFabKeyTyped(evt);
            }
        });
        jP1.add(jTFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 100, 20));

        jBFab.setBackground(new java.awt.Color(255, 255, 255));
        jBFab.setText("jButton1");
        jBFab.setNextFocusableComponent(jTAna);
        jBFab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFabMouseExited(evt);
            }
        });
        jBFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFabActionPerformed(evt);
            }
        });
        jBFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFabKeyPressed(evt);
            }
        });
        jP1.add(jBFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 30, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 110, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        /*Comprueba siel usuario existe*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next() && jTUsr.getText().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Coloca el borde rojo*/                               
                jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario: " + jTUsr.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

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
        
        //Comprueba si el producto existe                
        int iRes        = Star.iExistProd(con, jTProd.getText().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si el producto no existe entonces
        if(iRes==0 && jTProd.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();             
            return;     
            
        }//Fin de if(iRes==0 && jTProd.getText().compareTo("")!=0)
                
        //Comprueba si existe el anaquel
        iRes        = Star.iExiste(con, jTAna.getText().trim(), "anaqs", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el anaquel no existe entonces
        if(iRes==0 && jTAna.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTAna.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El anaquel: " + jTAna.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAna.grabFocus();             
            return;     
        }
        
        //Comprueba si el lugar existe
        iRes        = Star.iExiste(con, jTLug.getText().trim(), "lugs", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el lugar no existe entonces
        if(iRes==0 && jTLug.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El lugar: " + jTLug.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTLug.grabFocus();             
            return;     
        }
        
        //Compreuba si la ubicacion adicional existe
        iRes        = Star.iExiste(con, jTUbiAd.getText().trim(), "ubiad", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe la ubicación entonces
        if(iRes==0 && jTUbiAd.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTUbiAd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La ubicación adicional: " + jTUbiAd.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUbiAd.grabFocus();             
            return;     
        }
        
        //Comprueba si la marca existe
        iRes        = Star.iExiste(con, jTMarc.getText().trim(), "marcs", "cod");
        
        //Si hubo error entonces regreas
        if(iRes==-1)
            return;
        
        //Si la marca no existe entonces
        if(iRes==0 && jTMarc.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La marca: " + jTMarc.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTMarc.grabFocus();             
            return;     
        }
        
        //Comprueba si la medida existe
        iRes        = Star.iExiste(con, jTMed.getText().trim(), "meds", "cod");
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe la medida entonces
        if(iRes==0 && jTMed.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTMed.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La medida: " + jTMarc.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTMed.grabFocus();             
            return;     
        }
        
        //Comprueba si existe una línea
        iRes            = Star.iExiste(con, jTLin.getText().trim(), "lins", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si la línea no existe entonces
        if(iRes==0 && jTLin.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La línea: " + jTLin.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTLin.grabFocus();             
            return;     
        }
        
        //Comprueba si el impuesto ya existe en la base de datos
        iRes        = Star.iExiste(con, jTImp.getText().trim(), "impues", "codimpue");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el impuesto no existe entonces
        if(iRes==0 && jTImp.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTImp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El impuesto: " + jTImp.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTImp.grabFocus();             
            return;     
        }
        
        //Comprueba si la clasificación del producto ya existe
        iRes        = Star.iExiste(con, jTClas.getText().trim(), "clasprod", "cod");
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe la clasificación entonces
        if(iRes==0 && jTClas.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La clasificación: " + jTClas.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTClas.grabFocus();             
            return;     
        }
        
        //Comprueba si la unidad existe
        iRes            = Star.iExiste(con, jTUni.getText().trim(), "unids", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si la unidad no existe entonces
        if(iRes==0 && jTUni.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTUni.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La unidad: " + jTUni.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUni.grabFocus();             
            return;     
        }
        
        //Comprueba si el peso existe
        iRes        = Star.iExiste(con, jTPes.getText().trim(), "pes", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el peso no existe entonces
        if(iRes==0 && jTPes.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTPes.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El peso: " + jTPes.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPes.grabFocus();             
            return;     
        }
        
        //Comprueba si existe el color                
        iRes        = Star.iExiste(con, jTColo.getText().trim(), "colos", "cod");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el color no existe entonces
        if(iRes==0 && jTColo.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El color: " + jTColo.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTColo.grabFocus();             
            return;     
        }
        
        /*Obtiene el método de costeo de la empresa*/
        String sMetCost = "";
        try
        {
            sQ = "SELECT metcost FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces obtiene el resultado*/
            if(rs.next())
                sMetCost    = rs.getString("metcost");
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

        /*Determina el texto para el tipo de método de costeo*/
        if(sMetCost.compareTo("peps")==0)
            sMetCost    = "PEPS";
        else if(sMetCost.compareTo("ueps")==0)
            sMetCost    = "UEPS";
        else if(sMetCost.compareTo("ultcost")==0)
            sMetCost    = "ULTIMO COSTO";
        else if(sMetCost.compareTo("prom")==0)
            sMetCost    = "PROMEDIO";
        
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
        /*Else si esta seleccionado el checkbox de todas las fechas entonces resetea las fechas*/
        else
        {            
            sFDe    = "";
            sFA     = "";
        }           
            
        /*Obtiene la cada y la sucursal*/
        final String sCajFi         = jTCaj.getText();
        final String sSucuFi        = jTSucu.getText();
        
        /*Declara variables final para el thread*/        
        final String sFDeFi          = sFDe;
        final String sFAFi           = sFA;
        final String sProdFi         = jTProd.getText();        
        final String sEstaFi         = jTUsr.getText();        
        final String sAnaqFi         = jTAna.getText();
        final String sLugFi          = jTLug.getText();
        final String sMarcFi         = jTMarc.getText();
        final String sLinFi          = jTLin.getText();
        final String sClasFi         = jTClas.getText();
        final String sImpFi          = jTImp.getText();
        final String sMetCostFi      = sMetCost;
        final String sUniFi          = jTUni.getText();
        final String sColoFi         = jTColo.getText();
        final String sPesFi          = jTPes.getText();
        final String sMedFi          = jTMed.getText();
        final String sUbiAdFi        = jTUbiAd.getText();
        
        
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

                /*Obtiene la fecha de hoy*/               
                DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dat        = new Date();                

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();             
                    pa.clear();                    
                    pa.put("F_D",           sFDeFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("F_A",           sFAFi);
                    pa.put("PROD",          sProdFi);
                    pa.put("ESTAC",         sEstaFi);
                    pa.put("NOCAJ",         sCajFi);
                    pa.put("SUCU",          sSucuFi);                    
                    pa.put("LUG",           sLugFi);
                    pa.put("METCOST",       sMetCostFi);
                    pa.put("ANAQ",          sAnaqFi);
                    pa.put("MARC",          sMarcFi);
                    pa.put("LIN",           sLinFi);
                    pa.put("CLAS",          sClasFi);
                    pa.put("IMP",           sImpFi);                    
                    pa.put("UNI",           sUniFi);                    
                    pa.put("MED",           sMedFi);                    
                    pa.put("COLO",          sColoFi);                    
                    pa.put("PES",           sPesFi);                    
                    pa.put("UBIAD",         sUbiAdFi);                    
                    pa.put("FECH",          da.format(dat));

                    /*Determina el reporte que será*/
                    JasperReport ja;
                    
                    /*Compila el reporte de ventas por producto*/
                    ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptCosts.jrxml"));
                                        
                    /*Llenalo y muestralo*/
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                           /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Costos");
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

    
    /*Cuando se presiona el botón de bùscar estacs*/   
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de bùscar usuario*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se presiona una tecla en el control del usuario*/
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

    
    
    /*Cuando se presiona una tecla en el campo de la sucursal*/
    private void jTSucuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSucuKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la caja*/
    private void jTCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCajKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la sucursal*/   
    private void jTSucuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSucu.setSelectionStart(0);jTSucu.setSelectionEnd(jTSucu.getText().length());        
        
    }//GEN-LAST:event_jTSucuFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de caja*/
    private void jTCajFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCaj.setSelectionStart(0);jTCaj.setSelectionEnd(jTCaj.getText().length());        
        
    }//GEN-LAST:event_jTCajFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());        
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se tipea una tecla en el campo de usuario*/
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUsrKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la caja*/
    private void jTCajKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCajKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCajKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la sucursal*/
    private void jTSucuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucuKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTSucuKeyTyped

    
    /*Cuando se presiona el botón de búscar producto*/
    private void jBBuscProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBBuscProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar producto*/
    private void jBBuscProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained
        
    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
            b.setVisible(true);
        }
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan v = new ptovta.BuscAvan(this, jTProd, null, null, null);
            v.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }   
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              

    }//GEN-LAST:event_jTProdKeyTyped

    
    
    
    
    
    
    /*Cuando se presiona una tecla en el campo de anaquel*/
    private void jTAnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAnaKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTAna.getText(), 15, jTAna, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAnaKeyPressed

    
    /*Cuando se presiona una tecla en el botón de búscar anaquel*/
    private void jBBusAnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusAnaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBusAnaKeyPressed

    
    /*Cuando se presiona una tecla en el campo del lugar*/
    private void jTLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTLug.getText(), 16, jTLug, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLugKeyPressed

    
    /*Cuando se presiona una tecla en el botón de lugar*/
    private void jBBusLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusLugKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBusLugKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del anaquel*/
    private void jTAnaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAna.setSelectionStart(0);jTAna.setSelectionEnd(jTAna.getText().length());        
        
    }//GEN-LAST:event_jTAnaFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del lugar*/
    private void jTLugFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTLug.setSelectionStart(0);jTLug.setSelectionEnd(jTLug.getText().length());        
        
    }//GEN-LAST:event_jTLugFocusGained

    
    /*Cuando se presina el botón anaqs*/
    private void jBBusAnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusAnaActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAna.getText(), 15, jTAna, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBBusAnaActionPerformed

    
    /*Cuando se presiona el botón de lugar*/
    private void jBBusLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusLugActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTLug.getText(), 16, jTLug, null, null, "", null);
        b.setVisible(true);
            
    }//GEN-LAST:event_jBBusLugActionPerformed

    
    /*Cuando se presiona una tecla en el campo de la marca*/
    private void jTMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTMarc.getText(), 17, jTMarc, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
        {
            //Llama a la función escalable
            vKeyPreEsc(evt);
        }
        
    }//GEN-LAST:event_jTMarcKeyPressed

    
    /*Cuando se presiona una tecla en el botón de bùscar marcs*/
    private void jBMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMarcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMarcKeyPressed

    
    /*Cuando se presiona una tecla en el botón de bùscar líneas*/
    private void jBLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLinKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la línea*/
    private void jTLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLinKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTLin.getText(), 18, jTLin, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLinKeyPressed

    
    /*Cuandos se gana el foco del teclado en el campo de la marca*/
    private void jTMarcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMarc.setSelectionStart(0);jTMarc.setSelectionEnd(jTMarc.getText().length());
        
    }//GEN-LAST:event_jTMarcFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la línea*/
    private void jTLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLinFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTLin.setSelectionStart(0);jTLin.setSelectionEnd(jTLin.getText().length());
        
    }//GEN-LAST:event_jTLinFocusGained

    
    /*Cuando se presiona el botón de marcs*/
    private void jBMarcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMarcActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTMarc.getText(), 17, jTMarc, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBMarcActionPerformed

    
    /*Cuando se presiona el botón de líneas*/
    private void jBLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLinActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTLin.getText(), 18, jTLin, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBLinActionPerformed

    
    /*Cuando se presiona uan tecla en el botón de clasificación*/
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se presiona una tecla en el botón de impuesto*/
    private void jBImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el control de la clasificación*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());
        
    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del impuesto*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length());
        
    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se presiona una tecla en el campo del impuesto*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTImp.getText(), 20, jTImp, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
        {
            //Llama a la función escalable
            vKeyPreEsc(evt);
        }
        
    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se presionae el botón de impuesto*/
    private void jBImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTImp.getText(), 20, jTImp, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBImpActionPerformed

    
    /*Cuando se presiona una teclae n el campo de la clasificación*/
    private void jTClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTClas.getText(), 19, jTClas, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClasKeyPressed

    
    /*Cuando se presiona el botón de clasificación*/
    private void jBClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClasActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTClas.getText(), 19, jTClas, null, null, "", null);
        b.setVisible(true);
            
    }//GEN-LAST:event_jBClasActionPerformed
    
    
    /*Cuando se presiona una tecla en el botón de unids*/
    private void jBUniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUniKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUniKeyPressed

    
    /*Cuando se presiona una tecla en el botón de peso*/
    private void jBPesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPesKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPesKeyPressed

    
    /*Cuando se presiona una tecla en el botón de color*/
    private void jBColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBColoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBColoKeyPressed

    
    /*Cuandos se gana el foco del teclado en e lcampo de la unidad*/
    private void jTUniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUniFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUni.setSelectionStart(0);jTUni.setSelectionEnd(jTUni.getText().length());
        
    }//GEN-LAST:event_jTUniFocusGained

    
    /*Cuandos se gana el foco del teclado en el camo del peso*/
    private void jTPesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPes.setSelectionStart(0);jTPes.setSelectionEnd(jTPes.getText().length());
        
    }//GEN-LAST:event_jTPesFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del color*/
    private void jTColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTColo.setSelectionStart(0);jTColo.setSelectionEnd(jTColo.getText().length());
        
    }//GEN-LAST:event_jTColoFocusGained

    
    /*Cuando se presiona una tecla en el campo de la unidad*/
    private void jTUniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUniKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUni.getText(), 21, jTUni, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUniKeyPressed

    
    /*Cuando se presiona una tecla en el campo del peso*/
    private void jTPesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTPes.getText(), 22, jTPes, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPesKeyPressed

    
    /*Cuando se presiona una tecla en el campo del color*/
    private void jTColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColoKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTColo.getText(), 23, jTColo, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColoKeyPressed
    
    
    /*Cuando se presiona el botón de unidad*/
    private void jBUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUniActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUni.getText(), 21, jTUni, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUniActionPerformed

    
    /*Cuando se presiona el botón de peso*/
    private void jBPesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPesActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTPes.getText(), 22, jTPes, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBPesActionPerformed

    
    /*Cuando se presiona el botón de color*/
    private void jBColoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBColoActionPerformed
    
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTColo.getText(), 23, jTColo, null, null, "", null);
        b.setVisible(true);
            
    }//GEN-LAST:event_jBColoActionPerformed

    
    /*Cuando se tipea una tecla en el campo del anaquel*/
    private void jTAnaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAnaKeyTyped
                
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
                
    }//GEN-LAST:event_jTAnaKeyTyped

    
    /*Cuando se tipea una tecla en el campo del lugar*/
    private void jTLugKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTLugKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la marca*/
    private void jTMarcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTMarcKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la línea*/
    private void jTLinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLinKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTLinKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la clasificación*/
    private void jTClasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTClasKeyTyped

    
    /*Cuando se tipea una tecla en el campo del impuesto*/
    private void jTImpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTImpKeyTyped

    
    /*Cuando se tipea una tecla en el campo de la unidad*/
    private void jTUniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUniKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUniKeyTyped

    
    /*Cuando se tipea una tecla en el campo del peso*/
    private void jTPesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTPesKeyTyped

    
    /*Cuando se tipea una tecla en el campo del color*/
    private void jTColoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColoKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTColoKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la ubicación adicional*/
    private void jTUbiAdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbiAdFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUbiAd.setSelectionStart(0);jTUbiAd.setSelectionEnd(jTUbiAd.getText().length());
        
    }//GEN-LAST:event_jTUbiAdFocusGained

    
    /*Cuando se tipea una tecla en el campo de ubicación adicional*/
    private void jTUbiAdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbiAdKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTUbiAdKeyTyped

    
    /*Cuando se presiona una tecla en el campo de ubicación adicional*/
    private void jTUbiAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbiAdKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUbiAd.getText(), 24, jTUbiAd, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUbiAdKeyPressed

    
    /*Cuando se presiona el botón de búscar ubicación adicional*/
    private void jBUbiAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUbiAdActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUbiAd.getText(), 24, jTUbiAd, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBUbiAdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar ubicación adicional*/
    private void jBUbiAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUbiAdKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUbiAdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la medida*/
    private void jTMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMed.setSelectionStart(0);jTMed.setSelectionEnd(jTMed.getText().length());
        
    }//GEN-LAST:event_jTMedFocusGained

    
    /*Cuando se presiona una tecla en el campo de la medida*/
    private void jTMedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMedKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTMed.getText(), 25, jTMed, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMedKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la medida*/
    private void jTMedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMedKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTMedKeyTyped

    
    /*Cuando se presiona el botón de búscar medida*/
    private void jBMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMedActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTMed.getText(), 25, jTMed, null, null, "", null);
        b.setVisible(true);
            
    }//GEN-LAST:event_jBMedActionPerformed

    
    /*Cuando se presiona una tecla en el botón de medida*/
    private void jBMedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMedKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del fabricante*/
    private void jTFabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFabFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFab.setSelectionStart(0);jTFab.setSelectionEnd(jTFab.getText().length());
        
    }//GEN-LAST:event_jTFabFocusGained

    
    /*Cuando se presiona una tecla en el campo del fabricante*/
    private void jTFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFabKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTFab.getText(), 27, jTFab, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFabKeyPressed

    
    /*Cuando se presioan el botón de búscar fabricante*/
    private void jBFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFabActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTFab.getText(), 27, jTFab, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBFabActionPerformed

    
    /*Cuando se tipea una tecla en el campo del fabricante*/
    private void jTFabKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFabKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTFabKeyTyped

    
    /*Cuando se presiona uan tecla en el campo del fabricante*/
    private void jBFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBFabKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en l control de El usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTProdFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del anaquel*/
    private void jTAnaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTAna.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTAna.getText().compareTo("")!=0)
            jTAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAnaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del lugar*/
    private void jTLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLug.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTLug.getText().compareTo("")!=0)
            jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTLugFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la ubicación adicional*/
    private void jTUbiAdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbiAdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUbiAd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUbiAd.getText().compareTo("")!=0)
            jTUbiAd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUbiAdFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la línea*/
    private void jTLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLinFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLin.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTLin.getText().compareTo("")!=0)
            jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTLinFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTClas.getText().compareTo("")!=0)
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTImp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTImp.getText().compareTo("")!=0)
            jTImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la unidad*/
    private void jTUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUniFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUni.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUni.getText().compareTo("")!=0)
            jTUni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUniFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del peso*/
    private void jTPesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPes.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTPes.getText().compareTo("")!=0)
            jTPes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTPesFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del color*/
    private void jTColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTColo.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTColo.getText().compareTo("")!=0)
            jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTColoFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la marca*/
    private void jTMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMarc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTMarc.getText().compareTo("")!=0)
            jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTMarcFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la medida*/
    private void jTMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMed.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTMed.getText().compareTo("")!=0)
            jTMed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTMedFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del fabricante*/
    private void jTFabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFabFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFab.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTFab.getText().compareTo("")!=0)
            jTFab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTFabFocusLost

    
    
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
    private void jBBusAnaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusAnaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusAna.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusAnaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLinMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLin.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLinMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUniMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUni.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUniMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMarcMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMarcMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMarc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMarcMouseEntered

    
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
    private void jBUbiAdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUbiAdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUbiAd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUbiAdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBColoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBColoMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBColo.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBColoMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBFabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFabMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBFab.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBFabMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscProdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBuscProd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscProdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusLugMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusLugMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusLug.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusLugMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBClasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBClasMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBPesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPesMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPes.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPesMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMedMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMed.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMedMouseEntered

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusAnaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusAnaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusAna.setBackground(colOri);
        
    }//GEN-LAST:event_jBBusAnaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLinMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLin.setBackground(colOri);
        
    }//GEN-LAST:event_jBLinMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBUniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUniMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUni.setBackground(colOri);
        
    }//GEN-LAST:event_jBUniMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMarcMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMarcMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMarc.setBackground(colOri);
        
    }//GEN-LAST:event_jBMarcMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBUbiAdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUbiAdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUbiAd.setBackground(colOri);
        
    }//GEN-LAST:event_jBUbiAdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImp.setBackground(colOri);
        
    }//GEN-LAST:event_jBImpMouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBColoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBColoMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBColo.setBackground(colOri);
        
    }//GEN-LAST:event_jBColoMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBFabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFabMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBFab.setBackground(colOri);
        
    }//GEN-LAST:event_jBFabMouseExited

    
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
    private void jBBuscProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBuscProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusLugMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusLugMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusLug.setBackground(colOri);
        
    }//GEN-LAST:event_jBBusLugMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBClasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(colOri);
        
    }//GEN-LAST:event_jBClasMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPesMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPes.setBackground(colOri);
        
    }//GEN-LAST:event_jBPesMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMedMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMed.setBackground(colOri);
        
    }//GEN-LAST:event_jBMedMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTSucuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSucu.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSucuFocusLost

    private void jTCajFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCajFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCaj.setCaretPosition(0);

    }//GEN-LAST:event_jTCajFocusLost

    
                          
       
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBBusAna;
    private javax.swing.JButton jBBusLug;
    private javax.swing.JButton jBBuscProd;
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBColo;
    private javax.swing.JButton jBFab;
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBLin;
    private javax.swing.JButton jBMarc;
    private javax.swing.JButton jBMed;
    private javax.swing.JButton jBPes;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBUbiAd;
    private javax.swing.JButton jBUni;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCTFech;
    private com.toedter.calendar.JDateChooser jDateA;
    private com.toedter.calendar.JDateChooser jDateDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTAna;
    private javax.swing.JTextField jTCaj;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTColo;
    private javax.swing.JTextField jTFab;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTLin;
    private javax.swing.JTextField jTLug;
    private javax.swing.JTextField jTMarc;
    private javax.swing.JTextField jTMed;
    private javax.swing.JTextField jTPes;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSucu;
    private javax.swing.JTextField jTUbiAd;
    private javax.swing.JTextField jTUni;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
