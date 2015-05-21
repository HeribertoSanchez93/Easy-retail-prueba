//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Desktop;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;




/*Clase para controlar los datos generales de la empresa*/
public class DatsGenEmp extends javax.swing.JFrame 
{        
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis                  v;
    
    /*Variables que contiene la dirección de entrega*/
    public String            sTelEn;
    public String            sLadaEn;
    public String            sExtEn;
    public String            sCelEn;
    public String            sTelPer1En;
    public String            sTelPer2En;
    public String            sCallEn;
    public String            sColEn;
    public String            sNoExtEn;
    public String            sIntEn;
    public String            sCPEn;
    public String            sCiuEn;
    public String            sEstadEn;
    public String            sPaiEn;
    public String            sCo1En;
    public String            sCo2En;
    public String            sCo3En;

    /*Valores originales de las rutas de los certificados*/
    private String          sCSDCerOri;
    private String          sCSDCerFOri;
    private String          sCSDKeyOri;
    private String          sCSDKeyFOri;
    
    /*Guarda la ubicación original que tiene el sistema para guardar todo*/
    private String           sRutOri;
    
    /*Contiene el grupo de los radio buttons*/
    private final javax.swing.ButtonGroup bG;
    
    
    
    
    
    /*Consructor con argumento*/
    public DatsGenEmp(JFrame jFrame) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
                
        /*Crea el grupo de los radio buttons de costeos*/
        bG  = new javax.swing.ButtonGroup();
        bG.add(jRUEPS);
        bG.add(jRPEPS);
        bG.add(jRUltCost);
        bG.add(jRProm);
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(500);
        
        /*Que la tabla de domicilios este oculta*/
        jScrTab.setVisible(false);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*La dirección de envio inicialmente estara en cadena vacia*/                                
        sTelEn          = "";
        sLadaEn         = "";
        sExtEn          = "";
        sCelEn          = "";
        sTelPer1En      = "";
        sTelPer2En      = "";
        sCallEn         = "";
        sColEn          = "";
        sNoExtEn        = "";
        sIntEn          = "";
        sCPEn           = "";
        sCiuEn          = "";
        sEstadEn        = "";
        sPaiEn          = "";
        sCo1En          = "";
        sCo2En          = "";
        sCo3En          = "";

        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Datos generales empresa, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente el label del logotipo de la imágen no será visible*/
        jLImg.setVisible(false);
        
        /*Pon el foco del teclado en el campo de edición del nom de la empresa*/
        jTNom.grabFocus();
        
        /*Crea el grupo para los radio buttons*/
        ButtonGroup  g  = new ButtonGroup();
        g.add(jRaMor);
        g.add(jRaFis);
        
        /*Obtiene todos los datos de la empresa y cargalos en sus campos*/
        vCargDat();
        
        /*Función escalable para saber si habilitar o no la fiel*/
        vProcRad();
        
    }/*Fin de public DatsGenEmp() */    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTNom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jTEst = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTRFC = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jTNoInt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jRaFis = new javax.swing.JRadioButton();
        jRaMor = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTPag = new javax.swing.JTextField();
        jTEmailCor = new javax.swing.JTextField();
        jBCargImg = new javax.swing.JButton();
        jBDelImg = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTCarp = new javax.swing.JTextField();
        jBCargCed = new javax.swing.JButton();
        jBDelCed = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jSImg1 = new javax.swing.JScrollPane();
        jPanImg1 = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jBVeGran1 = new javax.swing.JButton();
        jSImg2 = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg1 = new javax.swing.JLabel();
        jBVeGran2 = new javax.swing.JButton();
        jBDom = new javax.swing.JButton();
        jScrTab = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jRProm = new javax.swing.JRadioButton();
        jRPEPS = new javax.swing.JRadioButton();
        jRUEPS = new javax.swing.JRadioButton();
        jRUltCost = new javax.swing.JRadioButton();
        jCApli = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jPCSD = new javax.swing.JPanel();
        jL1 = new javax.swing.JLabel();
        jLMani = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTCSDFCer = new javax.swing.JTextField();
        jBCSDCF = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jTCSDFKey = new javax.swing.JTextField();
        jBCSDKF = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jBProbF = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTCSDCer = new javax.swing.JTextField();
        jBCSDC = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTCSDKey = new javax.swing.JTextField();
        jBCSDK = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jBProbC = new javax.swing.JButton();
        jTRegFis = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTLugExp = new javax.swing.JTextField();
        jPaLlav = new javax.swing.JPasswordField();
        jPaLlavF = new javax.swing.JPasswordField();

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

        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTTel);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNomKeyTyped(evt);
            }
        });
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 390, 20));

        jLabel2.setText("*Nombre de  Empresa:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jLabel3.setText("*Calle:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

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
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 220, 20));

        jLabel4.setText("*Colonia:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 220, 20));

        jTEst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEst.setNextFocusableComponent(jTPai);
        jTEst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstFocusLost(evt);
            }
        });
        jTEst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstKeyPressed(evt);
            }
        });
        jP1.add(jTEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 220, 20));

        jLabel5.setText("*Estado:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel6.setText("*País:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

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
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 220, 20));

        jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCiu.setNextFocusableComponent(jTEst);
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
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 220, 20));

        jLabel7.setText("*Ciudad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTNoExt);
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
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 220, 20));

        jLabel9.setText("No. Interior:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jL1);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 120, 30));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jTPag);
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
        jP1.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 220, 20));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTCall);
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
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 220, 20));

        jLabel20.setText("Teléfono:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel10.setText("*CP:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel22.setText("*No. Exterior:");
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoExtKeyTyped(evt);
            }
        });
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 220, 20));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCiu);
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
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 220, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRaFis.setBackground(new java.awt.Color(255, 255, 255));
        jRaFis.setText("Física");
        jRaFis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRaFisActionPerformed(evt);
            }
        });
        jRaFis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaFisKeyPressed(evt);
            }
        });
        jPanel2.add(jRaFis, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, -1, -1));

        jRaMor.setBackground(new java.awt.Color(255, 255, 255));
        jRaMor.setSelected(true);
        jRaMor.setText("Moral");
        jRaMor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRaMorActionPerformed(evt);
            }
        });
        jRaMor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRaMorKeyPressed(evt);
            }
        });
        jPanel2.add(jRaMor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, 70, -1));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 190, 30));

        jLabel28.setText("*RFC:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 120, -1));

        jLabel29.setText("Página Web:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 130, -1));

        jTPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag.setNextFocusableComponent(jTEmailCor);
        jTPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPagFocusLost(evt);
            }
        });
        jTPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPagKeyPressed(evt);
            }
        });
        jP1.add(jTPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 220, 20));

        jTEmailCor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEmailCor.setNextFocusableComponent(jTCarp);
        jTEmailCor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEmailCorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEmailCorFocusLost(evt);
            }
        });
        jTEmailCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEmailCorKeyPressed(evt);
            }
        });
        jP1.add(jTEmailCor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 220, 20));

        jBCargImg.setBackground(new java.awt.Color(255, 255, 255));
        jBCargImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargImg.setText("Cargar");
        jBCargImg.setNextFocusableComponent(jBDelImg);
        jBCargImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargImgMouseExited(evt);
            }
        });
        jBCargImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargImgActionPerformed(evt);
            }
        });
        jBCargImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargImgKeyPressed(evt);
            }
        });
        jP1.add(jBCargImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 70, -1));

        jBDelImg.setBackground(new java.awt.Color(255, 255, 255));
        jBDelImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelImg.setText("Borrar");
        jBDelImg.setNextFocusableComponent(jBVeGran1);
        jBDelImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelImgMouseExited(evt);
            }
        });
        jBDelImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelImgActionPerformed(evt);
            }
        });
        jBDelImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelImgKeyPressed(evt);
            }
        });
        jP1.add(jBDelImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 60, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Cédula Físcal:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 140, -1));

        jLabel12.setText("Email corporativo:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 130, -1));

        jTCarp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCarp.setNextFocusableComponent(jRPEPS);
        jTCarp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCarpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCarpFocusLost(evt);
            }
        });
        jTCarp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCarpKeyPressed(evt);
            }
        });
        jP1.add(jTCarp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 220, 20));

        jBCargCed.setBackground(new java.awt.Color(255, 255, 255));
        jBCargCed.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargCed.setText("Cargar");
        jBCargCed.setNextFocusableComponent(jBDelCed);
        jBCargCed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargCedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargCedMouseExited(evt);
            }
        });
        jBCargCed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargCedActionPerformed(evt);
            }
        });
        jBCargCed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargCedKeyPressed(evt);
            }
        });
        jP1.add(jBCargCed, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 70, 20));

        jBDelCed.setBackground(new java.awt.Color(255, 255, 255));
        jBDelCed.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelCed.setText("Borrar");
        jBDelCed.setName(""); // NOI18N
        jBDelCed.setNextFocusableComponent(jBVeGran2);
        jBDelCed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelCedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelCedMouseExited(evt);
            }
        });
        jBDelCed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelCedActionPerformed(evt);
            }
        });
        jBDelCed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelCedKeyPressed(evt);
            }
        });
        jP1.add(jBDelCed, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 60, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Logotipo:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 140, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 160, -1));

        jSImg1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSImg1KeyPressed(evt);
            }
        });

        jPanImg1.setBackground(new java.awt.Color(255, 255, 204));
        jPanImg1.setNextFocusableComponent(jBCargImg);
        jPanImg1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanImg1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanImg1MouseExited(evt);
            }
        });
        jPanImg1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanImg1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanImg1Layout = new javax.swing.GroupLayout(jPanImg1);
        jPanImg1.setLayout(jPanImg1Layout);
        jPanImg1Layout.setHorizontalGroup(
            jPanImg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImg1Layout.createSequentialGroup()
                .addComponent(jLImg)
                .addGap(0, 141, Short.MAX_VALUE))
        );
        jPanImg1Layout.setVerticalGroup(
            jPanImg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImg1Layout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jSImg1.setViewportView(jPanImg1);

        jP1.add(jSImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 160, 90));

        jBVeGran1.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran1.setToolTipText("Ver Imágen  de Producto Completa");
        jBVeGran1.setNextFocusableComponent(jPanImg);
        jBVeGran1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeGran1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeGran1MouseExited(evt);
            }
        });
        jBVeGran1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeGran1ActionPerformed(evt);
            }
        });
        jBVeGran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeGran1KeyPressed(evt);
            }
        });
        jP1.add(jBVeGran1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 30, 20));

        jSImg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSImg2KeyPressed(evt);
            }
        });

        jPanImg.setBackground(new java.awt.Color(255, 255, 204));
        jPanImg.setNextFocusableComponent(jBCargCed);
        jPanImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanImgMouseExited(evt);
            }
        });
        jPanImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanImgKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanImgLayout = new javax.swing.GroupLayout(jPanImg);
        jPanImg.setLayout(jPanImgLayout);
        jPanImgLayout.setHorizontalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg1)
                .addGap(0, 141, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg1)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jSImg2.setViewportView(jPanImg);

        jP1.add(jSImg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 160, 90));

        jBVeGran2.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran2.setToolTipText("Ver Imágen  de Producto Completa");
        jBVeGran2.setNextFocusableComponent(jTNom);
        jBVeGran2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeGran2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeGran2MouseExited(evt);
            }
        });
        jBVeGran2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeGran2ActionPerformed(evt);
            }
        });
        jBVeGran2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeGran2KeyPressed(evt);
            }
        });
        jP1.add(jBVeGran2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 30, 20));

        jBDom.setBackground(new java.awt.Color(255, 255, 255));
        jBDom.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDom.setForeground(new java.awt.Color(0, 102, 0));
        jBDom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/doment.png"))); // NOI18N
        jBDom.setText("Entrega");
        jBDom.setToolTipText("Domicilio de Entrega de la Empresa Actual");
        jBDom.setNextFocusableComponent(jBGuar);
        jBDom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDomMouseExited(evt);
            }
        });
        jBDom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDomActionPerformed(evt);
            }
        });
        jBDom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDomKeyPressed(evt);
            }
        });
        jP1.add(jBDom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 120, 30));

        jScrTab.setEnabled(false);
        jScrTab.setFocusable(false);

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "tel", "lada", "exten", "cel", "telper1", "telper2", "calle", "col", "noext", "noint", "cp", "ciu", "estad", "pais", "co1", "co2", "co3"
            }
        ));
        jTab.setFocusable(false);
        jScrTab.setViewportView(jTab);

        jP1.add(jScrTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, 80, 20));

        jRProm.setBackground(new java.awt.Color(255, 255, 255));
        jRProm.setSelected(true);
        jRProm.setText("Promedio");
        jRProm.setName(""); // NOI18N
        jRProm.setNextFocusableComponent(jCApli);
        jRProm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPromKeyPressed(evt);
            }
        });
        jP1.add(jRProm, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 100, -1));

        jRPEPS.setBackground(new java.awt.Color(255, 255, 255));
        jRPEPS.setText("PEPS");
        jRPEPS.setNextFocusableComponent(jRUEPS);
        jRPEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPEPSKeyPressed(evt);
            }
        });
        jP1.add(jRPEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 70, -1));

        jRUEPS.setBackground(new java.awt.Color(255, 255, 255));
        jRUEPS.setText("UEPS");
        jRUEPS.setNextFocusableComponent(jRUltCost);
        jRUEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRUEPSKeyPressed(evt);
            }
        });
        jP1.add(jRUEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 70, -1));

        jRUltCost.setBackground(new java.awt.Color(255, 255, 255));
        jRUltCost.setText("Ult.Costo");
        jRUltCost.setNextFocusableComponent(jRProm);
        jRUltCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRUltCostKeyPressed(evt);
            }
        });
        jP1.add(jRUltCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 90, -1));

        jCApli.setBackground(new java.awt.Color(255, 255, 255));
        jCApli.setSelected(true);
        jCApli.setText("Aplicar a productos");
        jCApli.setNextFocusableComponent(jBDom);
        jCApli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCApliKeyPressed(evt);
            }
        });
        jP1.add(jCApli, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 140, -1));

        jLabel13.setText("*Carpeta de aplicación:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 140, -1));

        jPCSD.setBackground(new java.awt.Color(255, 255, 255));
        jPCSD.setBorder(javax.swing.BorderFactory.createTitledBorder("Certificados Fiscales"));
        jPCSD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPCSDKeyPressed(evt);
            }
        });
        jPCSD.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jL1.setText("Manifiesto SAT:");
        jL1.setNextFocusableComponent(jTCSDFCer);
        jPCSD.add(jL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 20));

        jLMani.setForeground(new java.awt.Color(51, 51, 255));
        jLMani.setText("https://manifiesto.ecodex.com.mx");
        jLMani.setNextFocusableComponent(jTCSDFCer);
        jLMani.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLManiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLManiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLManiMouseExited(evt);
            }
        });
        jPCSD.add(jLMani, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, 20));

        jLabel16.setText("Certificado de Fiel .cer:");
        jPCSD.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 220, 20));

        jTCSDFCer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCSDFCer.setNextFocusableComponent(jBCSDCF);
        jTCSDFCer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCSDFCerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCSDFCerFocusLost(evt);
            }
        });
        jTCSDFCer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCSDFCerKeyPressed(evt);
            }
        });
        jPCSD.add(jTCSDFCer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 220, 20));

        jBCSDCF.setBackground(new java.awt.Color(255, 255, 255));
        jBCSDCF.setText("jButton1");
        jBCSDCF.setToolTipText("Buscar Ruta a Calculadora");
        jBCSDCF.setNextFocusableComponent(jTCSDFKey);
        jBCSDCF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCSDCFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCSDCFMouseExited(evt);
            }
        });
        jBCSDCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCSDCFActionPerformed(evt);
            }
        });
        jBCSDCF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCSDCFKeyPressed(evt);
            }
        });
        jPCSD.add(jBCSDCF, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 30, 20));

        jLabel17.setText("Llave privada de Fiel .key:");
        jPCSD.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 220, 20));

        jTCSDFKey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCSDFKey.setNextFocusableComponent(jBCSDKF);
        jTCSDFKey.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCSDFKeyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCSDFKeyFocusLost(evt);
            }
        });
        jTCSDFKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCSDFKeyKeyPressed(evt);
            }
        });
        jPCSD.add(jTCSDFKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 220, 20));

        jBCSDKF.setBackground(new java.awt.Color(255, 255, 255));
        jBCSDKF.setText("jButton1");
        jBCSDKF.setToolTipText("Buscar Ruta a Calculadora");
        jBCSDKF.setNextFocusableComponent(jPaLlavF);
        jBCSDKF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCSDKFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCSDKFMouseExited(evt);
            }
        });
        jBCSDKF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCSDKFActionPerformed(evt);
            }
        });
        jBCSDKF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCSDKFKeyPressed(evt);
            }
        });
        jPCSD.add(jBCSDKF, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 30, 20));

        jLabel18.setText("Contraseña de llave privada Fiel:");
        jPCSD.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 220, 20));

        jBProbF.setBackground(new java.awt.Color(255, 255, 255));
        jBProbF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProbF.setForeground(new java.awt.Color(0, 102, 0));
        jBProbF.setText("Probar");
        jBProbF.setToolTipText("Probar certificado");
        jBProbF.setNextFocusableComponent(jTCSDCer);
        jBProbF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbFMouseExited(evt);
            }
        });
        jBProbF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbFActionPerformed(evt);
            }
        });
        jBProbF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbFKeyPressed(evt);
            }
        });
        jPCSD.add(jBProbF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        jLabel11.setText("CSD .cer:");
        jPCSD.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 140, 20));

        jTCSDCer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCSDCer.setNextFocusableComponent(jBCSDC);
        jTCSDCer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCSDCerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCSDCerFocusLost(evt);
            }
        });
        jTCSDCer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCSDCerKeyPressed(evt);
            }
        });
        jPCSD.add(jTCSDCer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 220, 20));

        jBCSDC.setBackground(new java.awt.Color(255, 255, 255));
        jBCSDC.setText("jButton1");
        jBCSDC.setToolTipText("Buscar Ruta a Calculadora");
        jBCSDC.setNextFocusableComponent(jTCSDKey);
        jBCSDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCSDCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCSDCMouseExited(evt);
            }
        });
        jBCSDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCSDCActionPerformed(evt);
            }
        });
        jBCSDC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCSDCKeyPressed(evt);
            }
        });
        jPCSD.add(jBCSDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 30, 20));

        jLabel14.setText("Llave privada de CSD .key:");
        jPCSD.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 220, 20));

        jTCSDKey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCSDKey.setNextFocusableComponent(jBCSDK);
        jTCSDKey.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCSDKeyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCSDKeyFocusLost(evt);
            }
        });
        jTCSDKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCSDKeyKeyPressed(evt);
            }
        });
        jPCSD.add(jTCSDKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 220, 20));

        jBCSDK.setBackground(new java.awt.Color(255, 255, 255));
        jBCSDK.setText("jButton1");
        jBCSDK.setToolTipText("Buscar Ruta a Calculadora");
        jBCSDK.setNextFocusableComponent(jPaLlav);
        jBCSDK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCSDKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCSDKMouseExited(evt);
            }
        });
        jBCSDK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCSDKActionPerformed(evt);
            }
        });
        jBCSDK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCSDKKeyPressed(evt);
            }
        });
        jPCSD.add(jBCSDK, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 30, 20));

        jLabel19.setText("*Lugar expedición:");
        jPCSD.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 140, 20));

        jBProbC.setBackground(new java.awt.Color(255, 255, 255));
        jBProbC.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProbC.setForeground(new java.awt.Color(0, 102, 0));
        jBProbC.setText("Probar");
        jBProbC.setToolTipText("Probar certificado");
        jBProbC.setNextFocusableComponent(jTRegFis);
        jBProbC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbCMouseExited(evt);
            }
        });
        jBProbC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbCActionPerformed(evt);
            }
        });
        jBProbC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbCKeyPressed(evt);
            }
        });
        jPCSD.add(jBProbC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));

        jTRegFis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRegFis.setNextFocusableComponent(jTLugExp);
        jTRegFis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRegFisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRegFisFocusLost(evt);
            }
        });
        jTRegFis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRegFisKeyPressed(evt);
            }
        });
        jPCSD.add(jTRegFis, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 220, 20));

        jLabel21.setText("Contraseña de llave privada CSD:");
        jPCSD.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 220, 20));

        jLabel23.setText("*Régimen fiscal:");
        jPCSD.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 20));

        jTLugExp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLugExp.setNextFocusableComponent(jRaMor);
        jTLugExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLugExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLugExpFocusLost(evt);
            }
        });
        jTLugExp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLugExpKeyPressed(evt);
            }
        });
        jPCSD.add(jTLugExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 220, 20));

        jPaLlav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPaLlav.setNextFocusableComponent(jBProbC);
        jPaLlav.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPaLlavFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPaLlavFocusLost(evt);
            }
        });
        jPaLlav.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPaLlavKeyPressed(evt);
            }
        });
        jPCSD.add(jPaLlav, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 220, 20));

        jPaLlavF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPaLlavF.setNextFocusableComponent(jBProbF);
        jPaLlavF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPaLlavFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPaLlavFFocusLost(evt);
            }
        });
        jPaLlavF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPaLlavFKeyPressed(evt);
            }
        });
        jPCSD.add(jPaLlavF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 220, 20));

        jP1.add(jPCSD, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 270, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los datos de la empresa y cargalos en sus campos*/
    private void vCargDat()
    {
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }                

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene todas las direcciones del cliente existentes*/
        try
        {
            sQ = "SELECT * FROM domentcli WHERE codemp = '" + Login.sCodEmpBD + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entoncesa agrega los datos en la tabla*/
            while(rs.next())
            {                                
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {rs.getString("tel"), rs.getString("lada"), rs.getString("exten"), rs.getString("cel"), rs.getString("telper1"), rs.getString("telper2"), rs.getString("calle"), rs.getString("col"), rs.getString("noext"), rs.getString("noint"), rs.getString("cp"), rs.getString("ciu"), rs.getString("estad"), rs.getString("pai"), rs.getString("co1"), rs.getString("co2"), rs.getString("co3")};
                te.addRow(nu);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Trae todos los datos de la empresa actual*/
        try
        {
            sQ = "SELECT * FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos y colocalos en sus campos*/
                jTNom.setText(rs.getString          ("nom"));         
                jTTel.setText(rs.getString          ("tel"));         
                jTCall.setText(rs.getString         ("calle"));         
                jTCol.setText(rs.getString          ("col"));         
                jTCP.setText(rs.getString           ("cp"));         
                jTNoExt.setText(rs.getString        ("noext"));         
                jTNoInt.setText(rs.getString        ("noint"));         
                jTCiu.setText(rs.getString          ("ciu"));         
                jTEst.setText(rs.getString          ("estad"));         
                jTPai.setText(rs.getString          ("pai"));         
                jTRFC.setText(rs.getString          ("rfc"));         
                jTPag.setText(rs.getString          ("pagweb"));         
                jTEmailCor.setText(rs.getString     ("corr"));         
                jTCarp.setText(rs.getString         ("rutap"));  
            
                /*Coloca los datos de la fiel y CSD*/
                jTCSDFCer.setText(rs.getString      ("rutcerf"));  
                jTCSDFKey.setText(rs.getString      ("rutkeyf"));  
                jTCSDCer.setText(rs.getString       ("rutcer")); 
                jTCSDKey.setText(rs.getString       ("rutkey"));
                jTRegFis.setText(rs.getString       ("regfisc"));
                jTLugExp.setText(rs.getString       ("lugexp"));
                jPaLlavF.setText(Star.sDecryp(      rs.getString("passcerf")));  
                jPaLlav.setText(Star.sDecryp(       rs.getString("passcer")));                  
                
                /*Guarda los valores originales de los CSD*/
                sCSDCerOri                          = rs.getString("rutcer");
                sCSDCerFOri                         = rs.getString("rutcerf");
                sCSDKeyOri                          = rs.getString("rutkey");
                sCSDKeyFOri                         = rs.getString("rutkeyf");
                
                /*Coloca todos los campos al principio de los controles*/
                jTNom.setCaretPosition              (0);         
                jTTel.setCaretPosition              (0);         
                jTCall.setCaretPosition             (0);
                jTCol.setCaretPosition              (0);
                jTCP.setCaretPosition               (0);
                jTNoExt.setCaretPosition            (0);
                jTNoInt.setCaretPosition            (0);
                jTCiu.setCaretPosition              (0);
                jTEst.setCaretPosition              (0);
                jTRegFis.setCaretPosition           (0);
                jTLugExp.setCaretPosition           (0);
                jTPai.setCaretPosition              (0);
                jTRFC.setCaretPosition              (0);
                jTPag.setCaretPosition              (0);
                jTEmailCor.setCaretPosition         (0);
                jTCarp.setCaretPosition             (0);
                jTCSDFCer.setCaretPosition          (0);  
                jTCSDFKey.setCaretPosition          (0);  
                jTCSDCer.setCaretPosition           (0); 
                jTCSDKey.setCaretPosition           (0);  
                jPaLlavF.setCaretPosition           (0);  
                jPaLlav.setCaretPosition            (0);  
                                
                /*Selecciona el método de costeo correcto para la empresa*/
                if(rs.getString("metcost").compareTo("peps")==0)
                    bG.setSelected(jRPEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ueps")==0)
                    bG.setSelected(jRUEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ultcost")==0)
                    bG.setSelected(jRUltCost.getModel(), true);
                else if(rs.getString("metcost").compareTo("prom")==0)
                    bG.setSelected(jRProm.getModel(), true);
                
                /*Guarda la ubicación original que tiene la empresa para guardar todo*/
                sRutOri         = rs.getString("rutap");
                
                /*Obtiene todos los campos de la dirección de envio*/                                
                sTelEn          = rs.getString("telen");
                sLadaEn         = rs.getString("ladaen");
                sExtEn          = rs.getString("noextenen");
                sCelEn          = rs.getString("celen");
                sTelPer1En      = rs.getString("telper1en");
                sTelPer2En      = rs.getString("telper2en");
                sCallEn         = rs.getString("calleen");
                sColEn          = rs.getString("colen");
                sNoExtEn        = rs.getString("noextenen");
                sIntEn          = rs.getString("nointen");
                sCPEn           = rs.getString("cpen");
                sCiuEn          = rs.getString("ciuen");
                sEstadEn        = rs.getString("estaden");
                sPaiEn          = rs.getString("paien");
                sCo1En          = rs.getString("co1en");
                sCo2En          = rs.getString("co2en");
                sCo3En          = rs.getString("co3en");
                
                /*Coloca el cursor al principio del campo por que podría estar muy largo*/
                jTCarp.setCaretPosition(0);
                
                /*Marca si es persona física o moral*/
                if(rs.getString("pers").compareTo("F")==0)
                    jRaFis.setSelected  (true);
                else
                    jRaMor.setSelected   (true);
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return;                        
        }
        
        /*Gurda la ruta teporal para cargar tambián la imágen del logotipo*/
        String sTem             = sCarp;
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del logotipo de la empresa no existe entonces crea la ruta*/
        sCarp                    += "\\Cedula";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa en específico no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe en la ruta entonces*/                
        if(new File(sCarp).exists())
        {
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArchs [] = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg1.setIcon(new ImageIcon(sCarp + "\\" + sArchs[0]));

                /*Que el label sea visible*/
                jLImg1.setVisible(true);
            }
            /*Else, no existe imágen entonces que no sea visible el control de la imágen 1*/
            else
                jLImg1.setVisible(false);
        }            

        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sTem                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del logotipo de la empresa no existe entonces crea la ruta*/
        sTem                    += "\\Logotipo Empresa";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa en específico no existe entonces crea la ruta*/
        sTem                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si la imágen existe en la ruta entonces*/                
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArchs [] = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg.setIcon(new ImageIcon(sTem + "\\" + sArchs[0]));

                /*Que el label sea visible*/
                jLImg.setVisible(true);
            }
            /*Else, no existe imágen entonces que no sea visible el control de la imágen 1*/
            else
                jLImg.setVisible(false);
        }            
                
    }/*Fin de private void vCargDat()*/
    
        
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        //calle existe
        if(jTCall.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "Calle de la empresa necesaria para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCall.grabFocus();                   
                    return ;
        }
        //colonia existe
        if(jTCol.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "Colonia de la empresa necesaria para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCol.grabFocus();                   
                    return ;
        }
        //codigo postal existe
        if(jTCP.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "CP de la empresa necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCP.grabFocus();                   
                    return ;
        }
        //numero de exterior existe
        if(jTNoExt.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "Número exterior de la empresa necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTNoExt.grabFocus();                   
                    return ;
        }
        //cuidad existe
        if(jTCiu.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "Cuidad de la empresa necesaria para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTCiu.grabFocus();                   
                    return ;
        }
        // estado existe
        if(jTEst.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "Estado de la empresa necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTEst.grabFocus();                   
                    return ;
        }
        //pais existe
        if(jTPai.getText().compareTo("") != 0)
        {
        }
        else
        {   
            JOptionPane.showMessageDialog(null, "País de la empresa necesario para poder continuar" + "" + "", "Mensaje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            jTPai.grabFocus();                   
                    return ;
        }
        /*Si el RFC no es cadena vacia entonces*/
        if(jTRFC.getText().compareTo("")!=0)
        {
            /*Si es persona moral entonces*/
            if(jRaMor.isSelected())
            {
                /*Válida si el rfc introducido por el usuario sea válido o no*/
                Star.bValRFC   = false;
                Star.vValRFC(jTRFC.getText(), true, jTRFC, true);

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
            
        }/*Fin de if(jTRFC.getText().compareTo("")!=0)*/
        /*Else, es cadena vacia entonces*/
        else
        {
            /*Coloca el borde rojo*/                               
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El RFC no puede estar vacio.", "RFC", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRFC.grabFocus();                    
            return;
        }

        /*Si el nombre de la empresa esta vacio entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El nombre de la empresa no puede estar vacio.", "Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTNom.grabFocus();                    
            return;
        }

        /*Si el CSD fiel tenía datos y ya no los tiene entonces*/
        if(jTCSDFCer.getText().trim().compareTo("")==0 && sCSDCerFOri.compareTo("")!=0)
        {            
            /*Pregunta si esta seguro por que se va a borrar el CSD del disco*/
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "El CSD fiel se eliminara del disco si continua.¿Desea continuar?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
                return;                       
        }
        
        /*Si el CSD key fiel tenía datos y ya no los tiene entonces*/
        if(jTCSDFKey.getText().trim().compareTo("")==0 && sCSDKeyFOri.compareTo("")!=0)
        {            
            /*Pregunta si esta seguro por que se va a borrar el CSD del disco*/
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "El CSD key fiel se eliminara del disco si continua.¿Desea continuar?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
                return;                       
        }
        
        /*Si el CSD tenía datos y ya no los tiene entonces*/
        if(jTCSDCer.getText().trim().compareTo("")==0 && sCSDCerOri.compareTo("")!=0)
        {            
            /*Pregunta si esta seguro por que se va a borrar el CSD del disco*/
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "El CSD se eliminara del disco si continua.¿Desea continuar?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
                return;                       
        }
        
        /*Si el CSD key tenía datos y ya no los tiene entonces*/
        if(jTCSDKey.getText().trim().compareTo("")==0 && sCSDKeyOri.compareTo("")!=0)
        {            
            /*Pregunta si esta seguro por que se va a borrar el CSD del disco*/
            Object[] op = {"Si","No"};
            if((JOptionPane.showOptionDialog(this, "El CSD key se eliminara del disco si continua.¿Desea continuar?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
                return;                       
        }
        
        /*Si esta colocando CSD o fiel entoces*/
        if(jTCSDFCer.getText().trim().compareTo("")!=0 || jTCSDFKey.getText().trim().compareTo("")==0 || jTCSDCer.getText().trim().compareTo("")==0 || jTCSDKey.getText().trim().compareTo("")==0)        
        {
            /*Si no esta definido el regimén fiscal entonces*/
            if(jTRegFis.getText().trim().compareTo("")==0)
            {
                /*Coloca el borde rojo*/                               
                jTRegFis.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El regimén fiscal esta vacio.", "Regimén fiscal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Coloca el foco del teclado en el control y regresa*/
                jTRegFis.grabFocus();                    
                return;
            }
            
            /*Si no esta definido el lugar de expedición entonces*/
            if(jTLugExp.getText().trim().compareTo("")==0)
            {
                /*Coloca el borde rojo*/                               
                jTLugExp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El lugar de expedición esta vacio.", "Lugar expedición", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Coloca el foco del teclado en el control y regresa*/
                jTLugExp.grabFocus();                    
                return;
            }
            
        }/*Fin de if(jTCSDFCer.getText().trim().compareTo("")!=0 || jTCSDFKey.getText().trim().compareTo("")==0 || jTCSDCer.getText().trim().compareTo("")==0 || jTCSDKey.getText().trim().compareTo("")==0)        */
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;                       
                           
        /*Si el check de cambiar el método de costeo para todos los productos esta habilitado entonces*/
        if(jCApli.isSelected())
        {
            /*Preguntar al usuario si quiere continuar*/            
            if((JOptionPane.showOptionDialog(this, "Se aplicara el método de costeo para todos los productos. ¿Seguro que quieres continuar?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
                return;                       
        }
        
        /*Si el CSD fiel tenía datos y ya no los tiene entonces*/
        if(jTCSDFCer.getText().trim().compareTo("")==0 && sCSDCerFOri.compareTo("")!=0)                   
        {
            /*Borra lel CSD cer fiel*/
            try
            {
                new File(sCSDCerFOri).delete();                                                                         
            }
            catch(SecurityException expnSecuri)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace());                
                return;
            }
        }
        
        /*Si el CSD key fiel tenía datos y ya no los tiene entonces*/
        if(jTCSDFKey.getText().trim().compareTo("")==0 && sCSDKeyFOri.compareTo("")!=0)
        {           
            /*Borra lel CSD key fiel*/
            try
            {
                new File(sCSDKeyFOri).delete();                                                                         
            }
            catch(SecurityException expnSecuri)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace());                                
                return;
            }
        }
        
        /*Si el CSD tenía datos y ya no los tiene entonces*/
        if(jTCSDCer.getText().trim().compareTo("")==0 && sCSDCerOri.compareTo("")!=0)
        {   
            /*Borra lel CSD key fiel*/
            try
            {
                new File(sCSDCerOri).delete();                                                                         
            }
            catch(SecurityException expnSecuri)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace());                                
                return;
            }
        }
        
        /*Si el CSD key tenía datos y ya no los tiene entonces*/
        if(jTCSDKey.getText().trim().compareTo("")==0 && sCSDKeyOri.compareTo("")!=0)
        {                        
            /*Borra lel CSD key fiel*/
            try
            {
                new File(sCSDKeyOri).delete();                                                                         
            }
            catch(SecurityException expnSecuri)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace());                                
                return;
            }
        }
        
        /*Si la ruta original es distinta a la que se va a guardar entonces*/
        boolean bMov    = false;
        if(sRutOri.compareTo(jTCarp.getText())!=0)
        {
            /*Preguntar al usuario si desea mover todo el contenido de esa ubicación hacia la nueva ubicación*/            
            if((JOptionPane.showOptionDialog(this, "La ruta compartida a guardar es distinta de la que se tenía anteriormente." + System.getProperty( "line.separator" ) + "¿Deseas mover todo el contenido de esta carpeta a la nueva?", "Guardar datos empresa", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
                bMov    = true;                                           
        }                                
                
        /*Lee si es persona física o moral*/
        String sPer                 = "F";
        if(jRaMor.isSelected())
            sPer                    = "M";
                
        /*Abre la base de datos*/        
        Connection  con;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
            con.setAutoCommit(false);
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }
                
        /*Si la carpeta de la aplicación compartida no existe y no es cadena vacia entonces*/
        if(!new File(jTCarp.getText()).exists())
        {
            /*Intenta crear la ruta*/
            try
            {
                /*Creala*/
                boolean bRes = new File(jTCarp.getText()).mkdir();
                
                /*Si no se puedo crear entonces*/
                if(!bRes)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el borde rojo*/                               
                    jTCarp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                    /*Pon el foco del teclado en el campo*/
                    jTCarp.grabFocus();
            
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "No existe la ruta: " + jTCarp.getText() + " y el sistema no pudo crearla automáticamente.", "Datos Generales Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }
                /*Else si se pudo crear entonces mensajea*/
                else
                    JOptionPane.showMessageDialog(null, "No existía la ruta: " + jTCarp.getText() + " y el sistema la creó automáticamente.", "Datos Generales Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            }
            catch(SecurityException expnSecuri)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace());                                
                return;
            }
                                   
        }/*Fin de if(!new File(jTCarp.getText()).exists())*/
                
        /*Si la ruta original es diferente de la nueva entonces agrega la ruta de easy retail al final*/
        String sCarpAp = jTCarp.getText();
        if(sRutOri.compareTo(jTCarp.getText())!=0)                    
            sCarpAp         += "\\Easy Retail® Admin";        
                        
        /*Reemplaza los carácteres de barra invertida por 2 para que la base de datos los pueda guardar*/        
        sCarpAp = sCarpAp.replace("\\", "\\\\");                
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Determina el método de costeo seleccionado*/
        String sMetCost = "ultcost";
        if(jRPEPS.isSelected())
            sMetCost    = "peps";
        else if(jRUEPS.isSelected())
            sMetCost    = "ueps";
        else if(jRProm.isSelected())
            sMetCost    = "prom";

        //Declara variables de la base de datos    
        Statement   st;        
        String      sQ;                
        
        /*Actualiza los datos de la empresa en la base de datos*/
        try 
        {            
            sQ = "UPDATE basdats SET " + 
                            "nom        = '" + jTNom.getText().replace("'", "''") + "', " + 
                            "tel        = '" + jTTel.getText().replace("'", "''") + "', " + 
                            "telen      = '" + sTelEn.replace("'", "''") + "', " + 
                            "lugexp     = '" + jTLugExp.getText().trim() + "', " + 
                            "telper1en  = '" + sTelPer1En.replace("'", "''") + "', " + 
                            "regfisc    = '" + jTRegFis.getText().trim() + "', " + 
                            "telper2en  = '" + sTelPer2En.replace("'", "''") + "', " + 
                            "calle      = '" + jTCall.getText().replace("'", "''") + "', " + 
                            "celen      = '" + sCelEn.replace("'", "''") + "', " + 
                            "metcost    = '" + sMetCost + "', " + 
                            "passcerf   = '" + Star.sEncrip(jPaLlavF.getText().trim()) + "', " + 
                            "passcer    = '" + Star.sEncrip(jPaLlav.getText().trim()) + "', " + 
                            "calleen    = '" + sCallEn.replace("'", "''") + "', " + 
                            "col        = '" + jTCol.getText().replace("'", "''") + "', " + 
                            "colen      = '" + sColEn.replace("'", "''") + "', " + 
                            "ladaen     = '" + sLadaEn.replace("'", "''") + "', " + 
                            "cp         = '" + jTCP.getText().replace("'", "''") + "', " + 
                            "cpen       = '" + sCPEn.replace("'", "''") + "', " + 
                            "ciu        = '" + jTCiu.getText().replace("'", "''")  + "', " + 
                            "ciuen      = '" + sCiuEn.replace("'", "''") + "', " + 
                            "estad      = '" + jTEst.getText().replace("'", "''") + "', " + 
                            "co3en      = '" + sCo3En.replace("'", "''") + "', " + 
                            "co2en      = '" + sCo2En.replace("'", "''") + "', " + 
                            "co1en      = '" + sCo1En.replace("'", "''") + "', " + 
                            "estaden    = '" + sEstadEn.replace("'", "''") + "', " + 
                            "pai        = '" + jTPai.getText().replace("'", "''") + "', " + 
                            "paien      = '" + sPaiEn.replace("'", "''") + "', " + 
                            "rfc        = '" + jTRFC.getText().replace("'", "''") + "', " + 
                            "corr       = '" + jTEmailCor.getText().replace("'", "''") + "', " + 
                            "pagweb     = '" + jTPag.getText().replace("'", "''") + "', " + 
                            "noint      = '" + jTNoInt.getText().replace("'", "''") + "', " + 
                            "nointen    = '" + sIntEn.replace("'", "''") + "', " + 
                            "noext      = '" + jTNoExt.getText().replace("'", "''") + "', " + 
                            "rutcer     = '" + jTCSDCer.getText().trim() + "', " + 
                            "rutcerf    = '" + jTCSDFCer.getText().trim() + "', " + 
                            "noextenen  = '" + sNoExtEn.replace("'", "''") + "', " + 
                            "rutkeyf    = '" + jTCSDFKey.getText().trim() + "', " + 
                            "rutkey     = '" + jTCSDKey.getText().trim() + "', " + 
                            "pers       = '" + sPer.replace("'", "''") + "', " + 
                            "estac      = '" + Login.sUsrG.replace("'", "''") + "', " + 
                            "fmod       = now(), " + 
                            "rutap      = '" + sCarpAp.replace("'", "''") + "', " + 
                            "sucu       = '" + Star.sSucu.replace("'", "''") + "', " + 
                            "nocaj      = '" + Star.sNoCaj.replace("'", "''") + "' " + 
                            "WHERE codemp = '" + Login.sCodEmpBD.replace("'", "''") + "'";                                         
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Si tiene que actualizale a todos los productos el método de costeo entonces*/
        if(jCApli.isSelected())
        {
            /*Actualiza el método de costeo de todos los productos*/
            try 
            {            
                sQ = "UPDATE prods SET metcost = '" + sMetCost + "'";                                         
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
        }/*Fin de if(jCApli.isSelected())*/                    
        
        /*Borra toda la tabla de asociaciones de domicilios*/
        try 
        {            
            sQ = "DELETE FROM domentcli WHERE codemp = '" + Login.sCodEmpBD + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                                        

        /*Si la tabla de domicilios tiene datos entonces*/
        if(jTab.getRowCount()>0)
        {
            /*Recorre toda la tabla de domicilios*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Inserta en la base de datos el domicilio*/
                try 
                {            
                    sQ = "INSERT INTO domentcli (codemp,                        tel,                                                               lada,                                                             exten,                                                            cel,                                                              telper1,                                                          telper2,                                                          calle,                                                            col,                                                              noext,                                                            noint,                                                              cp,                                                                ciu,                                                                estad,                                                            pai,                                                               co1,                                                               co2,                                                               co3,                                                               estac,                 sucu,                 nocaj) "
                                  + "VALUES('" + Login.sCodEmpBD + "','" +      jTab.getValueAt(x, 0).toString().replace("'", "''") + "', '" +  jTab.getValueAt(x, 1).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 2).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 3).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 5).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 6).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 7).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 8).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 9).toString().replace("'", "''") + "', '" +   jTab.getValueAt(x, 10).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 11).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 12).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 13).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 15).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 16).toString().replace("'", "''") + "', '" + Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                                
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }                
            }                                    
        }
                
        /*Si hay archivo en el certificado de la fiel entonces*/
        if(jTCSDFCer.getText().trim().compareTo("")!=0)
        {            
            /*Si la fiel es valida entonces mueve el archivo de certificado y llave a la carpeta de la empresa*/
            if(iValCSD(jTCSDFCer, jTCSDFKey, jPaLlavF)==0)
            {
                vCopCer(con, jTCSDFCer.getText().trim(), "f", "rutcerf");
                vCopCer(con, jTCSDFKey.getText().trim(), "f", "rutkeyf");
            }
        }                    
                        
        /*Si hay archivo en el certificado entonces*/
        if(jTCSDCer.getText().trim().compareTo("")!=0)
        {            
            /*Si el certificado es valido entonces mueve el archivo de certificado y llave a la carpeta de la empresa*/
            if(iValCSD(jTCSDCer, jTCSDKey, jPaLlav)==0)
            {
                vCopCer(con, jTCSDCer.getText().trim(), "c", "rutcer");
                vCopCer(con, jTCSDKey.getText().trim(), "c", "rutkey");
            }
        }                    

        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si tiene que mover los archivos de la ruta anterior a la nueva ruta entonces hazlo todo en un thread*/
        if(bMov)
        {                
            /*Declara variables final para el thread*/
            final String sRutOriFi  = sRutOri;
            final String sCarpApFi  = sCarpAp;
            
            //Muestra el loading
            Star.vMostLoading("");

            /*Crea el thread*/
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Copia el carpeta orgien al servidor*/
                    try
                    {
                        org.apache.commons.io.FileUtils.copyDirectory(new File(sRutOriFi), new File(sCarpApFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                        
                        return;
                    }
                
                    /*La ruta original ahora sera la nueva*/
                    sRutOri         = sCarpApFi;
                        
                    /*Borra el contenido de la carpeta*/
                    try
                    {
                        /*Borra el directorio*/
                        FileUtils.cleanDirectory(new File(sRutOriFi));                                                                    
                        new File(sRutOriFi).delete();
                    }
                    catch(IOException expnIO)
                    {   
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }                
                    
                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                }
                
            }).start();
            
        }/*Fin de if(bMov)*/            
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Datos generales de la empresa guardados con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Define el nom de la empresa globalmente*/
        Star.sNombreEmpresa   = jTNom.getText();
        
        //cierra la ventana
        this.dispose();
        
        /*Llama al recolector de basura*/
        System.gc();
                                        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Método para copiar los archivos de certificado y fiel a la carpeta de la aplicación*/
    private void vCopCer(Connection con, String sSrc, String sTip, String sCamp)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        
        
        
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Determina la carpeta*/
        String sCaFK            = "\\CSD";
        if(sTip.compareTo("f")==0)
            sCaFK               = "\\FIEL";
        
        /*Si la carpeta de los CSD no existe entonces crea la carpeta*/
        sCarp                    += "\\CSD";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de las FIEL no existe entonces crea la carpeta*/
        sCarp                    += sCaFK;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Obtiene el nombre del archivo orígen*/
        String sNom = new File(sSrc).getName();
        
        /*Completa la ruta final con el mismo nombre de fichero*/
        sCarp                   += "\\" + sNom;
                
        /*Actualiza la ruta al CSD*/
        try 
        {            
            sQ = "UPDATE basdats SET "
                    + sCamp + " = '" + sCarp.replace("\\", "\\\\") + "' WHERE codemp = '" + Login.sCodEmpBD + "'";                                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Si la fuente y el destino son iguales entonces regresa*/
        if(sSrc.compareTo(sCarp)==0)
            return;
        
        /*Copia el archivo orgien al destino*/
        try
        {
            org.apache.commons.io.FileUtils.copyFile(new File(sSrc), new File(sCarp));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                            
        }                
        
    }/*Fin de private void vCopCer(Connection con, String sSrc, String sTip)*/
    
    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
 
        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de nom del cliente*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona un botón en el campo de edición calle*/
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
    private void jTEstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstKeyPressed

    
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

    
    /*Cuando se gana el foco del teclado en el campo de edición de nom*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());                
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de calle*/
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
    private void jTEstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEst.setSelectionStart(0);jTEst.setSelectionEnd(jTEst.getText().length());        
        
    }//GEN-LAST:event_jTEstFocusGained

    
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

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el cursor al principio del control*/
        jTRFC.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRFC.getText().compareTo("")!=0)
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usurio*/
        String sLec    = jTRFC.getText();
        
        /*Si es cadena vacia entonces solo regresar*/
        if(sLec.compareTo("")==0)
            return;
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sLec.length()> 21)
            jTRFC.setText(jTRFC.getText().substring(0, 21));
        
        /*Convierte los caes escritos en mayúsculas*/
        sLec    = sLec.toUpperCase();
        
        /*Colocalos en el campo*/
        jTRFC.setText(sLec); 
        
        /*Si es persona moral entonces válida el RFC*/
        if(jRaMor.isSelected())
            Star.vValRFC(sLec, true, jTRFC, true);            
        /*Else, es física*/
        else
            Star.vValRFC(sLec, false, jTRFC, true);                        
               
    }//GEN-LAST:event_jTRFCFocusLost
        
    
    /*Cuando se pierde el foco del teclado en el campo de edición de nom*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNom.getText().compareTo("")!=0)
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNom.getText().length()> 255)
            jTNom.setText(jTNom.getText().substring(0, 255));
      
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el cursor al principio del control*/
        jTTel.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel.getText().length()> 255)        
            jTTel.setText(jTTel.getText().substring(0, 255));        
                       
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de edición de calle*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el cursor al principio del control*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)        
            jTCall.setText(jTCall.getText().substring(0, 255));        
               
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de col*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el cursor al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)        
            jTCol.setText(jTCol.getText().substring(0, 255));        
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de eición de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el cursor al principio del control*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCP.getText().length()> 20)        
            jTCP.setText(jTCP.getText().substring(0, 20));        
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el cursor al principio del control*/
        jTCiu.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCiu.getText().length()> 255)        
            jTCiu.setText(jTCiu.getText().substring(0, 255));        
               
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de edición de estad*/
    private void jTEstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusLost

        /*Coloca el cursor al principio del control*/
        jTEst.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEst.getText().length()> 255)        
            jTEst.setText(jTEst.getText().substring(0, 255));        
        
    }//GEN-LAST:event_jTEstFocusLost

    
    /*Cuando se pierde el foco del teclado en el campode edición de pai*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el cursor al principio del control*/
        jTPai.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPai.getText().length()> 255)        
            jTPai.setText(jTPai.getText().substring(0, 255));        
        
    }//GEN-LAST:event_jTPaiFocusLost

    
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

        /*Coloca el cursor al principio del control*/
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

        /*Coloca el cursor al principio del control*/
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
                               
    
    /*Cuando se tipea una tecla en el campo de nom*/
    private void jTNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNomKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTelKeyTyped

            
    
    
    /*Cuando se tipea una tecla en el campo de CP*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCPKeyTyped

    
    /*Cuando se tipea una tecla en el campo de número de exterior*/
    private void jTNoExtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoExtKeyTyped

    
    /*Cuando se tipea una tecla en el campo de número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped

    
    
    
      
   
    /*Cuando se presiona una tecla en el radio button de física*/
    private void jRaFisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaFisKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaFisKeyPressed

    
    /*Cuando se presiona una tecla en el radio button de moral*/
    private void jRaMorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRaMorKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRaMorKeyPressed

    
    /*Cuando se presiona una tecla en el panel 2*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanel2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la página web*/
    private void jTPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag.setSelectionStart(0);jTPag.setSelectionEnd(jTPag.getText().length());      
        
    }//GEN-LAST:event_jTPagFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de email coorporativo*/
    private void jTEmailCorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmailCorFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEmailCor.setSelectionStart(-1);      
        
    }//GEN-LAST:event_jTEmailCorFocusGained
    
    
    /*Cuando se presiona el botón de cargar*/
    private void jBCargImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargImgActionPerformed
                
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor. Ingresa la ruta en el campo de 'Carpeta de Aplicación', guarda cambios y vuelve a intentarlo.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;
        }
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del logotipo de la empresa no existe entonces crea la ruta*/
        sCarp                    += "\\Logotipo Empresa";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa en específico no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen para la empresa \"" + Login.sCodEmpBD + "\" en \"" + sCarp + "\".", "Empresas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Logotipo");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Muestra el file choooser*/
        int iVal                = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(iVal                == JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nom del archivo a donde se copiara*/
            sCarp               +=  "\\" + "Logo.jpg";  

            /*Si el nombre del archivo no termina con una extensión de imágen entonces*/
            if(!fc.getSelectedFile().getName().endsWith(".jpg") && !fc.getSelectedFile().getName().endsWith(".jpeg") && !fc.getSelectedFile().getName().endsWith(".bmp") && !fc.getSelectedFile().getName().endsWith(".gif") && !fc.getSelectedFile().getName().endsWith(".png"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El formato de la imágen debe ser un formato de imágen conocido.", "Imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén: " + sRut + " no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;
            }

            /*Carga la imágen en el panel*/
            jLImg.setIcon(new ImageIcon(sRut));

            /*Que el label sea visible*/
            jLImg.setVisible(true);

        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCargImgActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cargar*/
    private void jBCargImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargImgKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelImgActionPerformed

        /*Preguntar al usuario si esta seguro de que quiere borrar el logotipo*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el logotipo?", "Borrar Logotipo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;
        
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor. Ingresa la ruta en el campo de 'Carpeta de Aplicación', guarda cambios y vuelve a intentarlo.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;
        }
        
        /*Si la carpeta de imágenes no existe entonces crea la ruta*/
        sCarp            += "\\Imagenes"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del logotipo de la empresa no existe entonces crea la ruta*/
        sCarp            += "\\Logotipo Empresa";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del logotipo de la empresa en específico no existe entonces crea la ruta*/
        sCarp             += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La empresa \"" + Login.sCodEmpBD + "\" no contiene imágen para borrar en \"" + sCarp + "\".", "Empresas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));       
                return;
            }
        }                    

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;
        }                               
        
        /*Que no sea visible la imágen ya*/
        jLImg.setVisible(false);
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Logotipo borrado con éxito.", "Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelImgActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelImgKeyPressed

        
    /*Cuando se gana el foco del teclado en el campo de la carpeta de la aplicación*/
    private void jTCarpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarpFocusGained
                
        /*Selecciona todo el texto cuando gana el foco*/
        jTCarp.setSelectionStart(0);jTCarp.setSelectionEnd(jTCarp.getText().length());        
        
    }//GEN-LAST:event_jTCarpFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de email coorporativo*/
    private void jTEmailCorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmailCorFocusLost

        /*Coloca el cursor al principio del control*/
        jTEmailCor.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEmailCor.getText().length()> 100)
            jTEmailCor.setText(jTEmailCor.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTEmailCorFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la carpeta de la aplicación*/
    private void jTCarpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarpFocusLost

        /*Coloca el cursor al principio del control*/
        jTCarp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCarp.getText().compareTo("")!=0)
            jTCarp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTCarpFocusLost

    
    /*Cuando se presiona una tecla en el campo de página web*/
    private void jTPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPagKeyPressed

    
    /*Cuando se presiona una tecla en el campo de email coorporativo*/
    private void jTEmailCorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmailCorKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEmailCorKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la carpeta de la aplición*/
    private void jTCarpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCarpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCarpKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la página web*/
    private void jTPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPagFocusLost

        /*Coloca el cursor al principio del control*/
        jTPag.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTPag.getText().length()> 255)
            jTPag.setText(jTPag.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTPagFocusLost

            
    /*Cuando se presiona una tecla en el botón de cargar cádula físcal*/
    private void jBCargCedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargCedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargCedKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar cádula físcal*/
    private void jBDelCedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelCedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelCedKeyPressed

    
    
    /*Cuando se presiona el botón de cargar RFC*/
    private void jBCargCedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargCedActionPerformed
        
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;
        }
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la cádula físcal no existe entonces crea la ruta*/
        sCarp                    += "\\Cedula";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa en específico no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una Cádula Físcal para la empresa: " + Login.sCodEmpBD + " en \"" + sCarp + "\".", "Cádula Físcal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar RFC");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Muestra el file choooser*/
        int iVal                = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(iVal                == JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nom del archivo a donde se copiara*/
            sCarp               +=  "\\" + "Cedula.jpg";  

            /*Si el nombre del archivo no termina con una extensión de imágen entonces*/
            if(!fc.getSelectedFile().getName().endsWith(".jpg") && !fc.getSelectedFile().getName().endsWith(".jpeg") && !fc.getSelectedFile().getName().endsWith(".bmp") && !fc.getSelectedFile().getName().endsWith(".gif") && !fc.getSelectedFile().getName().endsWith(".png"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El formato de la imágen debe ser un formato de imágen conocido.", "Imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                return;
            }           

            /*Carga la imágen en el panel*/
            jLImg1.setIcon(new ImageIcon(sRut));

            /*Que el label sea visible*/
            jLImg1.setVisible(true);
            
        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCargCedActionPerformed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelCedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelCedActionPerformed
        
        /*Preguntar al usuario si esta seguro de que quiere borrar la cádula físcal*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la cádula físcal?", "Borrar Cádula Físcal", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;
        
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;
        }
        
        /*Si la carpeta de imágenes no existe entonces crea la ruta*/
        sCarp            += "\\Imagenes"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la cádula de la empresa no existe entonces crea la ruta*/
        sCarp            += "\\Cedula";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
       
        /*Si la carpeta del logotipo de la empresa en específico no existe entonces crea la ruta*/
        sCarp             += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La empresa \"" + Login.sCodEmpBD + "\" no contiene cádula físcal para borrar en \"" + sCarp + "\".", "Cádula Físcal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    
        
        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;
        }                               

        /*Que no sea visible la imágen ya*/
        jLImg1.setVisible(false);
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Cádula Físcal borrada con éxito.", "Cádula Físcal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelCedActionPerformed

    
    
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

    
    /*Cunado el mouse entra en el control de la imágen 1*/
    private void jPanImg1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImg1MouseEntered

        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces regresa*/
        if(sCarp.compareTo("")==0)
            return;

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del logotipo de la empresa no existe entonces creala*/
        sCarp                    += "\\Logotipo Empresa";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if(new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista de directorios*/
                String sArc [] = new File(sCarp).list();
                
                /*Muestra la forma para ver la imágen en otra vista*/
                v = new ImgVis(sCarp + "\\" + sArc[0]);            
                v.setVisible(true);
            }
        }                    

    }//GEN-LAST:event_jPanImg1MouseEntered

    
    /*Cuando el mouse sale del control de imágen 1*/
    private void jPanImg1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImg1MouseExited

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jPanImg1MouseExited

    
    /*Cuando se presiona una tecla en el control de la imágen*/
    private void jPanImg1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImg1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanImg1KeyPressed

    
    /*Cuando se presiona una tecla en el scroll de la imágen 1*/
    private void jSImg1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSImg1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSImg1KeyPressed

    
    /*Cuando se presiona el botón de ver imágen 1 en grande*/
    private void jBVeGran1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGran1ActionPerformed

        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existen entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del logotipo de empresa no existen entonces creala*/
        sCarp                    += "\\Logotipo Empresa";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existen entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe logotipo para la empresa: " + Login.sCodEmpBD+ " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVeGran1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen 1 en grande*/
    private void jBVeGran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGran1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVeGran1KeyPressed

    
    /*Cuando el mouse entra en el control de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered

        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces regresa*/
        if(sCarp.compareTo("")==0)
            return;

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la cádula no existe entonces creala*/
        sCarp                    += "\\Cedula";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si la imágen existe en la ruta entonces*/
            if(new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista de directorios*/
                String sArc [] = new File(sCarp).list();

                /*Muestra la forma para ver la imágen en otra vista*/
                v = new ImgVis(sCarp + "\\" + sArc[0]);            
                v.setVisible(true);
            }
        }            

    }//GEN-LAST:event_jPanImgMouseEntered

    
    /*Cuando el mouse sale del control de la imágen*/
    private void jPanImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseExited

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jPanImgMouseExited

    
    /*Cuando se presiona una tecla en el control de la imágen*/
    private void jPanImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanImgKeyPressed

    
    /*Cuando se presiona una tecla en el scroll de la imágen 2*/
    private void jSImg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSImg2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSImg2KeyPressed

    
    /*Cuando se presiona el botón de ver imágen 2 en grande*/
    private void jBVeGran2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGran2ActionPerformed

        
        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existen entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de las cádulas no existen entonces creala*/
        sCarp                    += "\\Cedula";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existen entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe Cádula Fiscal para la empresa: " + Login.sCodEmpBD + " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVeGran2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen 2*/
    private void jBVeGran2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGran2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVeGran2KeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCargImg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelImg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGran1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGran1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeGran1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargCedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargCedMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCargCed.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargCedMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelCedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelCedMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelCed.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelCedMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGran2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGran2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeGran2MouseEntered

    
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
    private void jBCargImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargImg.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelImg.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGran1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGran1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran1.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGran1MouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBCargCedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargCedMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargCed.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargCedMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelCedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelCedMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelCed.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelCedMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGran2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGran2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran2.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGran2MouseExited

    
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

    
    /*Cuando se presiona una tecla en el botón de domicilio de entrega*/
    private void jBDomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDomKeyPressed

    
    /*Cuando se presiona una tecla en el botón de domicilio de entrega*/
    private void jBDomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDomActionPerformed
        
        /*Muestra la forma de domiclio de entrega*/
        DomEntLoc d = new DomEntLoc(jTab);
        d.setVisible(true);
        
    }//GEN-LAST:event_jBDomActionPerformed

    
    /*Cuando el mouse entra en el botón de domicilio*/
    private void jBDomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDom.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBDomMouseEntered

    
    /*Cuando el mouse sale del botón de domicilio*/
    private void jBDomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDom.setBackground(colOri);
        
    }//GEN-LAST:event_jBDomMouseExited
   
    
    /*Cuando se presiona una tecla en el radio de PEPS*/
    private void jRPEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPEPSKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRPEPSKeyPressed

    
    /*Cuando se presiona una tecla en el radio de UEPS*/
    private void jRUEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRUEPSKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRUEPSKeyPressed

    
    /*Cuando se presiona una tecal en el radio de último costo*/
    private void jRUltCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRUltCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRUltCostKeyPressed

    
    /*Cuando se presiona una tecla en el radio de promedio*/
    private void jRPromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPromKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRPromKeyPressed

    
    /*Cuando se presiona una tecla en el check de aplicar a todos los productos*/
    private void jCApliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCApliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCApliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del CSD cer*/
    private void jTCSDCerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDCerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCSDCer.setSelectionStart(0);jTCSDCer.setSelectionEnd(jTCSDCer.getText().length());        
        
    }//GEN-LAST:event_jTCSDCerFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CSD cer*/
    private void jTCSDCerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDCerFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCSDCer.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCSDCer.getText().compareTo("")!=0)
            jTCSDCer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTCSDCerFocusLost

    
    /*Cuando se presiona una tecla en el campo del CSD cer*/
    private void jTCSDCerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCSDCerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCSDCerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del CSD llave*/
    private void jTCSDKeyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDKeyFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCSDKey.setSelectionStart(0);jTCSDKey.setSelectionEnd(jTCSDKey.getText().length());        
        
    }//GEN-LAST:event_jTCSDKeyFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CSD key*/
    private void jTCSDKeyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDKeyFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCSDKey.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCSDKey.getText().compareTo("")!=0)
            jTCSDKey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTCSDKeyFocusLost

    
    /*Cuando se presiona una tecla en el campo del CSD key*/
    private void jTCSDKeyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCSDKeyKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCSDKeyKeyPressed

    
    
    
    
    /*Cuando se gana el foco del teclado en el campo del CSD cred fiel*/
    private void jTCSDFCerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDFCerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCSDFCer.setSelectionStart(0);jTCSDFCer.setSelectionEnd(jTCSDFCer.getText().length());        
        
    }//GEN-LAST:event_jTCSDFCerFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CSD ser fiel*/
    private void jTCSDFCerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDFCerFocusLost

        /*Coloca el cursor al principio del control*/
        jTCSDFCer.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCSDFCer.getText().compareTo("")!=0)
            jTCSDFCer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTCSDFCerFocusLost

    
    /*Cuando se presiona una tecla en el campo del CSD cer fiel*/
    private void jTCSDFCerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCSDFCerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCSDFCerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del CSD key fiel*/
    private void jTCSDFKeyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDFKeyFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCSDFKey.setSelectionStart(0);jTCSDFKey.setSelectionEnd(jTCSDFKey.getText().length());        
        
    }//GEN-LAST:event_jTCSDFKeyFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del CSD key fiel*/
    private void jTCSDFKeyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCSDFKeyFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCSDFKey.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCSDFKey.getText().compareTo("")!=0)
            jTCSDFKey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTCSDFKeyFocusLost

    
    /*Cuando se presiona una tecla en el campo del CSD key fiel*/
    private void jTCSDFKeyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCSDFKeyKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCSDFKeyKeyPressed

    
    
    
    
    /*Cuando se presiona una tecla en el botón de probar fiel*/
    private void jBProbFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbFKeyPressed

    
    /*Cuando se presiona el botón de probar CSD*/
    private void jBProbCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbCKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbCKeyPressed

    
    /*Cuando el mouse entra en el botón de probar fiel*/
    private void jBProbFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbFMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBProbF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbFMouseEntered

    
    /*Cuando el mouse entra en el botón de probar CSD*/
    private void jBProbCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbCMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbC.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbCMouseEntered

    
    /*Cuando el mouse sale del botón de probar fiel*/
    private void jBProbFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbFMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBProbF.setBackground(colOri);
        
    }//GEN-LAST:event_jBProbFMouseExited

    
    /*Cuando el mouse sale del botón de probar CSD*/
    private void jBProbCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbCMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBProbC.setBackground(colOri);
        
    }//GEN-LAST:event_jBProbCMouseExited

    
    /*Cuando sucede una acción en el radio de persona física*/
    private void jRaFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRaFisActionPerformed
        
        /*Función escalable para procesar una función*/
        vProcRad();
        
    }//GEN-LAST:event_jRaFisActionPerformed

    
    /*Cuando sucede una acción en el rado de moral*/
    private void jRaMorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRaMorActionPerformed
        
        /*Función escalable para procesar una función*/
        vProcRad();
        
    }//GEN-LAST:event_jRaMorActionPerformed

    
    /*Función escalable para probar la vaidez del certficiado*/
    private int iValCSD(javax.swing.JTextField jTex, javax.swing.JTextField jTexK, javax.swing.JTextField jTexL)
    {
        /*Si no hay archivo escrito entonces*/
        if(jTex.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la ruta al CSD.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTex.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTex.grabFocus();
            return -1;
        }
        
        /*Si no existe el archivo cer entonces*/
        if(!new File(jTex.getText().trim()).exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo: " + jTex.getText().trim() + " no existe.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTex.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTex.grabFocus();
            return -1;
        }
        
        /*Si el archivo no termina en cer entonces*/
        if(!jTex.getText().trim().toLowerCase().endsWith(".cer"))
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo no es un .CER", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTex.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTex.grabFocus();
            return -1;
        }
        
        /*Si no hay archivo key escrito entonces*/
        if(jTexK.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la ruta al KEY.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTexK.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTexK.grabFocus();
            return -1;
        }
        
        /*Si no existe el archivo key entonces*/
        if(!new File(jTexK.getText().trim()).exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo: " + jTexK.getText().trim() + " no existe.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTexK.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTexK.grabFocus();
            return -1;
        }
        
        /*Si el archivo no termina en cer entonces*/
        if(!jTexK.getText().trim().toLowerCase().endsWith(".key"))
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo no es un .KEY", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTexK.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTexK.grabFocus();
            return -1;
        }
        
        /*Si no hay contraseña escrito entonces*/
        if(jTexL.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la contraseña.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTexL.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                    
            /*Pon el foco del teclado en el control y regresa error*/
            jTexL.grabFocus();
            return -1;
        }
                
        /*Obtiene la fecha de terminación del certificado*/
        java.util.Date dtVal     = Star.sCrypto(jTex.getText().trim());
        
        /*Si hubo error regresa error*/
        if(dtVal==null)
            return -1;
        
        /*Objeto para obtener la fecha del día de hoy*/
        java.util.Date dtNow    = new java.util.Date();
        
        /*Si la fecha del fin del certificado es mayor a la fecha del día de hoy entonces*/
        if(dtVal.compareTo(dtNow)<0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "CSD no valido. ERROR 1.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTex.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa error*/
            jTex.grabFocus();
            return -1;
        }
        
        /*Valida la clave*/
        PrivateKey prKey    = Star.pkGetKey(new File(jTexK.getText().trim()), jTexL.getText().trim());
        
        /*Si hay error entonces regresa error*/
        if(prKey==null)
            return -1;
        
        /*Regresa que todo fue bien*/
        return 0;
        
    }/*Fin de private int iValCSD(javax.swing.JTextField jTex, javax.swing.JTextField jTexK, javax.swing.JTextField jTexL)*/
    
    
    /*Cuando se presiona el botón de validar CSD*/
    private void jBProbCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbCActionPerformed
                
        /*Prueba la validez del CSD*/
        if(iValCSD(jTCSDCer, jTCSDKey, jPaLlav)==-1)
            return;
        
        /*Mensajea de que el certificado es correcto*/
        JOptionPane.showMessageDialog(null, "El CSD es valido.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));        
        
    }//GEN-LAST:event_jBProbCActionPerformed

    
    /*Cuando se presiona el botón de probar CSD fiel*/
    private void jBProbFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbFActionPerformed
        
        /*Prueba la validez del CSD*/
        if(iValCSD(jTCSDFCer, jTCSDFKey, jPaLlavF)==-1)
            return;
        
        /*Mensajea de que el certificado es correcto*/
        JOptionPane.showMessageDialog(null, "El CSD es valido.", "CSD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));        
        
    }//GEN-LAST:event_jBProbFActionPerformed

    
    /*Cuando se presiona el botón de búscar CSD key*/
    private void jBCSDKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCSDKActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar CSD.key");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTCSDKey.setText(sRut);
        }

    }//GEN-LAST:event_jBCSDKActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar certificado*/
    private void jBCSDKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCSDKKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCSDKKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar certificado key*/
    private void jBCSDKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDKMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCSDK.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCSDKMouseEntered

    
    /*Cuando el mouse sale del botón de búscar llave key*/
    private void jBCSDKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDKMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBCSDK.setBackground(colOri);
        
    }//GEN-LAST:event_jBCSDKMouseExited

    
    /*Cuandoel mouse entra en el botón de búscar CSD cer*/
    private void jBCSDCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDCMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCSDC.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCSDCMouseEntered

    
    /*Cuando el mouse sale del botón de búscar CSD cer*/
    private void jBCSDCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDCMouseExited

        /*Cambia el color del fondo del botón*/
        jBCSDC.setBackground(colOri);

    }//GEN-LAST:event_jBCSDCMouseExited

    /*Cuando se presiona el botón de búscar CSD cer*/
    private void jBCSDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCSDCActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar CSD.cer");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTCSDCer.setText(sRut);
        }

    }//GEN-LAST:event_jBCSDCActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar CSD.cer*/
    private void jBCSDCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCSDCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCSDCKeyPressed

    
    
    /*Cuando el mouse entra en el botón de CSD key fiel*/
    private void jBCSDKFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDKFMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCSDKF.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCSDKFMouseEntered

    
    /*Cuando el mouse sale del botón de CSD key fiel*/
    private void jBCSDKFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDKFMouseExited

        /*Cambia el color del fondo del botón*/
        jBCSDKF.setBackground(colOri);

    }//GEN-LAST:event_jBCSDKFMouseExited

    
    /*Cuando el botón de búscar CSD key de fiel*/
    private void jBCSDKFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCSDKFActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar CSD.key fiel");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTCSDFKey.setText(sRut);
        }

    }//GEN-LAST:event_jBCSDKFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de CSD key fiel*/
    private void jBCSDKFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCSDKFKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCSDKFKeyPressed

    
    /*Cuando el mouse entra en el botón de CSD cer fiel*/
    private void jBCSDCFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDCFMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCSDCF.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCSDCFMouseEntered

    
    /*Cuando el mouse sale del botón de búscar CSD cer fiel*/
    private void jBCSDCFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCSDCFMouseExited

        /*Cambia el color del fondo del botón*/
        jBCSDCF.setBackground(colOri);

    }//GEN-LAST:event_jBCSDCFMouseExited

    
    /*Cuando se presiona el botón de búscar CSD cer de fiel*/
    private void jBCSDCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCSDCFActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar CSD.cer fiel");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTCSDFCer.setText(sRut);
        }

    }//GEN-LAST:event_jBCSDCFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar CSD cer fiel*/
    private void jBCSDCFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCSDCFKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCSDCFKeyPressed

    
    
    
    
    /*Cuando se hace clic en el manifiesto*/
    private void jLManiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLManiMouseClicked

        /*Abre la página web*/
        try 
        {        
            String url = "https://manifiesto.ecodex.com.mx";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch(IOException expnIO) 
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                            
        }
        
    }//GEN-LAST:event_jLManiMouseClicked

    
    /*Cuando el mouse entra en el manifiesto*/
    private void jLManiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLManiMouseEntered
        
        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));
        
    }//GEN-LAST:event_jLManiMouseEntered

    
    /*Cuando el mouse sale del manifiesto*/
    private void jLManiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLManiMouseExited
        
        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));	
        
    }//GEN-LAST:event_jLManiMouseExited

    
    /*Cuando se presiona una tecla en el panel de CSD*/
    private void jPCSDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPCSDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPCSDKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del regimén fiscal*/
    private void jTRegFisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRegFisFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRegFis.setSelectionStart(0);jTRegFis.setSelectionEnd(jTRegFis.getText().length());        
        
    }//GEN-LAST:event_jTRegFisFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del regimén físcal*/
    private void jTRegFisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRegFisFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTRegFis.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRegFis.getText().compareTo("")!=0)
            jTRegFis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jTRegFisFocusLost

    
    /*Cuando se presiona una tecla en el campo del regimén fiscal*/
    private void jTRegFisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRegFisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRegFisKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del lugar de expedición*/
    private void jTLugExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugExpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTLugExp.setSelectionStart(0);jTLugExp.setSelectionEnd(jTLugExp.getText().length());        
        
    }//GEN-LAST:event_jTLugExpFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lugar de expedición*/
    private void jTLugExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugExpFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTLugExp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTLugExp.getText().compareTo("")!=0)
            jTLugExp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               

        
    }//GEN-LAST:event_jTLugExpFocusLost

    
    /*Cuando se presiona una tecla en el lugar de expedición*/
    private void jTLugExpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugExpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLugExpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la contraseña de la llave de la fiel*/
    private void jPaLlavFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPaLlavFFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPaLlavF.setSelectionStart(0);jPaLlavF.setSelectionEnd(jPaLlavF.getText().length());        
        
    }//GEN-LAST:event_jPaLlavFFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la contraseña de la llave de CSD*/
    private void jPaLlavFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPaLlavFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPaLlav.setSelectionStart(0);jPaLlav.setSelectionEnd(jPaLlav.getText().length());        
        
    }//GEN-LAST:event_jPaLlavFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la contraseña de la llave fiel*/
    private void jPaLlavFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPaLlavFFocusLost
        
        /*Coloca el cursor al principio del control*/
        jPaLlavF.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPaLlavF.getText().compareTo("")!=0)
            jPaLlavF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jPaLlavFFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la contraseña de la llave CSD*/
    private void jPaLlavFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPaLlavFocusLost
 
        /*Coloca el cursor al principio del control*/
        jPaLlav.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPaLlav.getText().compareTo("")!=0)
            jPaLlav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));               
        
    }//GEN-LAST:event_jPaLlavFocusLost

    
    /*Cuando se presiona una tecla en el control de la contraseña de llave CSD*/
    private void jPaLlavKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPaLlavKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPaLlavKeyPressed

    
    /*Cuando se presiona una tecla en el control de la contraseña de fiel*/
    private void jPaLlavFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPaLlavFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPaLlavFKeyPressed

                   
    /*Método para procesar la acción en los radio de física y moral*/
    private void vProcRad()
    {
        /*Si física esta seleccionada entonces activa los controles de la fiel*/
        if(jRaFis.isSelected())
            vFisMor(true);
        /*Else desactiva los controles de la fiel*/
        else
            vFisMor(false);
    }
    
        
    /*Método para activar o desactivar los controles de física o moral*/
    private void vFisMor(boolean bVal)
    {
        /*Activa o desactiva los controles de la fiel*/
        jTCSDFCer.setEditable   (bVal);
        jTCSDFCer.setFocusable  (bVal);
        jTCSDFKey.setEditable   (bVal);
        jTCSDFKey.setFocusable  (bVal);
        jPaLlavF.setEditable    (bVal);
        jPaLlavF.setFocusable   (bVal);
        jBProbF.setEnabled      (bVal);
        jBProbF.setFocusable    (bVal);
        jBCSDCF.setEnabled      (bVal);
        jBCSDCF.setFocusable    (bVal);
        jBCSDKF.setEnabled      (bVal);
        jBCSDKF.setFocusable    (bVal);
    }
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCSDC;
    private javax.swing.JButton jBCSDCF;
    private javax.swing.JButton jBCSDK;
    private javax.swing.JButton jBCSDKF;
    private javax.swing.JButton jBCargCed;
    private javax.swing.JButton jBCargImg;
    private javax.swing.JButton jBDelCed;
    private javax.swing.JButton jBDelImg;
    private javax.swing.JButton jBDom;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBProbC;
    private javax.swing.JButton jBProbF;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBVeGran1;
    private javax.swing.JButton jBVeGran2;
    private javax.swing.JCheckBox jCApli;
    private javax.swing.JLabel jL1;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLImg1;
    private javax.swing.JLabel jLMani;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPCSD;
    private javax.swing.JPasswordField jPaLlav;
    private javax.swing.JPasswordField jPaLlavF;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JPanel jPanImg1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRPEPS;
    private javax.swing.JRadioButton jRProm;
    private javax.swing.JRadioButton jRUEPS;
    private javax.swing.JRadioButton jRUltCost;
    private javax.swing.JRadioButton jRaFis;
    private javax.swing.JRadioButton jRaMor;
    private javax.swing.JScrollPane jSImg1;
    private javax.swing.JScrollPane jSImg2;
    private javax.swing.JScrollPane jScrTab;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCSDCer;
    private javax.swing.JTextField jTCSDFCer;
    private javax.swing.JTextField jTCSDFKey;
    private javax.swing.JTextField jTCSDKey;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCarp;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTEmailCor;
    private javax.swing.JTextField jTEst;
    private javax.swing.JTextField jTLugExp;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPag;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTRegFis;
    private javax.swing.JTextField jTTel;
    public javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
