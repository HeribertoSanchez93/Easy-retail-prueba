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




/*Clase para controlar el reporteador de ventas por producto*/
public class RepProds extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Declara variables de instancia*/
    private static RepProds         obj = null;      
    
    
    
    
    /*Constructor sin argumentos*/
    public RepProds() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
            
        /*Esconde el check de solo con existencias*/
        jCSolExis.setVisible    (false);
        jCSolExis.setFocusable  (false);
        
        /*Establece la selección inicial del combo de tipo de reportes*/
        jComRep.setSelectedItem("Ventas X Producto");
        
        /*Establece el campo de fecha den entrega para que solo se pueda modificar con el botón*/
        jDDe.getDateEditor().setEnabled(false);
        jDA.getDateEditor().setEnabled(false);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBAbr);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Reporte productos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Oculta el campo de serie*/
        jTSer.setVisible(false);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFini = new Date();
        jDDe.setDate (fFini);
        jDA.setDate  (fFini);
        
        /*Pon el foco del teclado en el combo de los tipos de reportes*/
        jComRep.grabFocus();
                
    }/*Fin de public ReportesFacturasCos() */
        
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static RepProds getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new RepProds();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Lineas getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDA = new com.toedter.calendar.JDateChooser();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jBCli = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jComRep = new javax.swing.JComboBox();
        jTSucu = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTCaj = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTUsr = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jBProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTAlma = new javax.swing.JTextField();
        jBAlma = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTAna = new javax.swing.JTextField();
        jBAna = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTLug = new javax.swing.JTextField();
        jBLug = new javax.swing.JButton();
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
        jLabel25 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jCSolExis = new javax.swing.JCheckBox();

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
        jBSal.setNextFocusableComponent(jComRep);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, 110, 30));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBCli);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCliFocusLost(evt);
            }
        });
        jTCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCliKeyTyped(evt);
            }
        });
        jP1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 170, 20));

        jDDe.setEnabled(false);
        jDDe.setNextFocusableComponent(jDA);
        jDDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDDeKeyPressed(evt);
            }
        });
        jP1.add(jDDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 140, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 150, 20));

        jDA.setEnabled(false);
        jDA.setNextFocusableComponent(jTCli);
        jDA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDAKeyPressed(evt);
            }
        });
        jP1.add(jDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 140, 20));

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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 130, 20));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 110, 30));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente");
        jBCli.setNextFocusableComponent(jBAbr);
        jBCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCliMouseExited(evt);
            }
        });
        jBCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCliActionPerformed(evt);
            }
        });
        jBCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCliKeyPressed(evt);
            }
        });
        jP1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 30, 20));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, -1));

        jComRep.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ventas X producto", "Existencias X almacén", "Listas de precio X producto", "Prioridad de compra", "Prioridad de venta", "Kits", "Servicios", "Existencias por series", "Valor de inventario", "Lotes y pedimentos" }));
        jComRep.setNextFocusableComponent(jCSolExis);
        jComRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComRepActionPerformed(evt);
            }
        });
        jComRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComRepKeyPressed(evt);
            }
        });
        jP1.add(jComRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 280, -1));

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
        jP1.add(jTSucu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 130, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("De:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 130, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Caja:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 130, -1));

        jTCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCaj.setNextFocusableComponent(jTUsr);
        jTCaj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCajFocusGained(evt);
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
        jP1.add(jTCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 130, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Usuario:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 130, -1));

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
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 90, 20));

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
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 30, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Sucursal:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 130, -1));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTAlma);
        jBProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdMouseExited(evt);
            }
        });
        jBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdActionPerformed(evt);
            }
        });
        jBProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdKeyPressed(evt);
            }
        });
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 30, 20));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBProd);
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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 100, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Producto:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 130, -1));

        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlma.setNextFocusableComponent(jBAlma);
        jTAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAlmaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAlmaFocusLost(evt);
            }
        });
        jTAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAlmaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAlmaKeyTyped(evt);
            }
        });
        jP1.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 100, 20));

        jBAlma.setBackground(new java.awt.Color(255, 255, 255));
        jBAlma.setText("jButton1");
        jBAlma.setToolTipText("Buscar Almacén(es)");
        jBAlma.setNextFocusableComponent(jTAna);
        jBAlma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAlmaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAlmaMouseExited(evt);
            }
        });
        jBAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlmaActionPerformed(evt);
            }
        });
        jBAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAlmaKeyPressed(evt);
            }
        });
        jP1.add(jBAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 30, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Almacén:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 130, -1));

        jTAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAna.setNextFocusableComponent(jBAna);
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
        jP1.add(jTAna, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 90, 20));

        jBAna.setBackground(new java.awt.Color(255, 255, 255));
        jBAna.setText("jButton1");
        jBAna.setToolTipText("Buscar Anaquel(es)");
        jBAna.setNextFocusableComponent(jTLug);
        jBAna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAnaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAnaMouseExited(evt);
            }
        });
        jBAna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAnaActionPerformed(evt);
            }
        });
        jBAna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAnaKeyPressed(evt);
            }
        });
        jP1.add(jBAna, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Anaquel:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 130, -1));

        jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug.setNextFocusableComponent(jBLug);
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
        jP1.add(jTLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 100, 20));

        jBLug.setBackground(new java.awt.Color(255, 255, 255));
        jBLug.setText("jButton1");
        jBLug.setToolTipText("Buscar Lugar(es)");
        jBLug.setNextFocusableComponent(jTUbiAd);
        jBLug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLugMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLugMouseExited(evt);
            }
        });
        jBLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLugActionPerformed(evt);
            }
        });
        jBLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLugKeyPressed(evt);
            }
        });
        jP1.add(jBLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 30, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Lugar:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 130, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Ubicación A:");
        jLabel14.setToolTipText("Ubicación Adicional");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 130, -1));

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
        jP1.add(jTLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 90, 20));

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
        jP1.add(jTMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 90, 20));

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
        jP1.add(jBLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 30, 20));

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
        jP1.add(jBMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 30, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Línea:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 120, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Clasificaciòn:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 130, -1));

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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, 20));

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
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 100, 20));

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
        jP1.add(jBImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 30, 20));

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
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 30, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Impuesto:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 130, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Unidad:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 130, -1));

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
        jP1.add(jTColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 100, 20));

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
        jP1.add(jTUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 90, 20));

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
        jP1.add(jTPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 100, 20));

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
        jP1.add(jBPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 30, 20));

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
        jP1.add(jBColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 30, 20));

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
        jP1.add(jBUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 30, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Color:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 130, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Marca:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 130, -1));

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
        jP1.add(jTUbiAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 100, 20));

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
        jP1.add(jBUbiAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 30, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Peso:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 130, -1));

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
        jP1.add(jTMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 100, 20));

        jBMed.setBackground(new java.awt.Color(255, 255, 255));
        jBMed.setText("jButton1");
        jBMed.setToolTipText("Buscar Medida(s)");
        jBMed.setNextFocusableComponent(jTFab);
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
        jP1.add(jBMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 30, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Medida:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 130, -1));

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
        jP1.add(jTFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 100, 20));

        jBFab.setBackground(new java.awt.Color(255, 255, 255));
        jBFab.setText("jButton1");
        jBFab.setNextFocusableComponent(jTJera);
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
        jP1.add(jBFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 30, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 110, 20));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Jerarquía:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 120, -1));

        jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTJera.setNextFocusableComponent(jBJera);
        jTJera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTJeraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTJeraFocusLost(evt);
            }
        });
        jTJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTJeraKeyPressed(evt);
            }
        });
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 90, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar Jerárquia(s)");
        jBJera.setNextFocusableComponent(jCTFech);
        jBJera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJeraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJeraMouseExited(evt);
            }
        });
        jBJera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJeraActionPerformed(evt);
            }
        });
        jBJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBJeraKeyPressed(evt);
            }
        });
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 30, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Fabricante:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 130, -1));

        jCSolExis.setBackground(new java.awt.Color(255, 255, 255));
        jCSolExis.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCSolExis.setSelected(true);
        jCSolExis.setText("Solo con existencia");
        jCSolExis.setNextFocusableComponent(jTSucu);
        jCSolExis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCSolExisKeyPressed(evt);
            }
        });
        jP1.add(jCSolExis, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 130, -1));

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
        obj = null;       
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de empesa*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());        
        
    }//GEN-LAST:event_jTCliFocusGained
            
        
    /*Cuando se tipea una tecla en el campo del cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped

    
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

        /*Comprueba si el cliente no existe*/        
        try
        {
            sQ = "SELECT codemp FROM emps WHERE codemp = '" + jTCli.getText().replace(jTSer.getText(), "") + "' AND ser = '" + jTSer.getText() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(!rs.next() && jTCli.getText().replace(jTSer.getText(), "").compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;                    

                /*Coloca el borde rojo*/                               
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El cliente: " + jTSer.getText() + jTCli.getText().replace(jTSer.getText(), "") + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTCli.grabFocus();             
                return;     
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }            

        /*Comprueba si el usuario existe*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTUsr.getText() + "'";                      
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
                JOptionPane.showMessageDialog(null, "El usuario: " + jTUsr.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

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
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();             
            return;                 
        }
                
        //Comprueba si existe el anaquel
        iRes    = Star.iExistAnaq(con, jTAna.getText().trim());
        
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
            JOptionPane.showMessageDialog(null, "El anaquel: " + jTAna.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAna.grabFocus();             
            return;     
        }
        
        //Comprueba si el lugar existe
        iRes        = Star.iExistLug(con, jTLug.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe el lugar entonces
        if(iRes==0 && jTLug.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    
            
            /*Coloca el borde rojo*/                               
            jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El lugar: " + jTLug.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTLug.grabFocus();             
            return;     
        }
        
        //Compreuba si la ubicacion existe
        iRes        = Star.iExistUbiAd(con, jTUbiAd.getText().trim());
        
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
            JOptionPane.showMessageDialog(null, "La ubicación adicional: " + jTUbiAd.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUbiAd.grabFocus();             
            return;     
        }
        
        //Comprueba si la marca existe
        iRes        = Star.iExistMarc(con, jTMarc.getText().trim());
        
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
            JOptionPane.showMessageDialog(null, "La marca: " + jTMarc.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTMarc.grabFocus();             
            return;     
        }
        
        //Comprueba si la medida existe
        iRes        = Star.iExistMed(con, jTMed.getText().trim());
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si la medida no existe entonces
        if(iRes==0 && jTMed.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    
            
            /*Coloca el borde rojo*/                               
            jTMed.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La medida: " + jTMarc.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTMed.grabFocus();             
            return;     
        }
        
        //Comprueba si existe una línea
        iRes            = Star.iExistLin(con, jTLin.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe la línea entonces
        if(iRes==0 && jTLin.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    
            
            /*Coloca el borde rojo*/                               
            jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La línea: " + jTLin.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTLin.grabFocus();             
            return;     
        }
        
        //Comprueba si el impuesto ya existe en la base de datos
        iRes        = Star.iExistImpue(con, jTImp.getText().trim());
        
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
            JOptionPane.showMessageDialog(null, "El impuesto: " + jTImp.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTImp.grabFocus();             
            return;     
        }
        
        //Comprueba si la clasificación del producto ya existe
        iRes        = Star.iExistClasProd(con, jTClas.getText().trim());
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si la clasificación no existe entonces
        if(iRes==0 && jTClas.getText().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    
            
            /*Coloca el borde rojo*/                               
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La clasificación: " + jTClas.getText().trim() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTClas.grabFocus();             
            return;     
        }
        
        //Comprueba si la unidad existe
        iRes        = Star.iExistUnid(con, jTUni.getText().trim());
        
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
        iRes        = Star.iExistPes(con, jTPes.getText().trim());
        
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
        iRes        = Star.iExisColo(con, jTColo.getText().trim());
        
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
        
        /*Comprueba si la jerarqía existe*/
        try
        {
            sQ = "SELECT clas FROM clasjeraprod WHERE rut = '" + jTJera.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next() && jTJera.getText().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;                    
            
                /*Coloca el borde rojo*/                               
                jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La jerarquía: " + jTJera.getText() + " no existe.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTJera.grabFocus();             
                return;     
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
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
        
        //Comprueba si existe el almacén
        iRes        = Star.iExistAlma(con, jTAlma.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no hay datos entonces
        if(iRes==0 && jTAlma.getText().trim().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    
            
            /*Coloca el borde rojo*/                               
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El almacén: " + jTAlma.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAlma.grabFocus();             
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
        /*Else si esta seleccionado el checkbox de todas las fechas entonces reseta las fechas*/
        else
        {            
            sFDe    = "";
            sFA     = "";
        }           
            
        /*Determina el reporte que será*/
        int iRep    = 0;
        if(jComRep.getSelectedItem().toString().compareTo("Existencias X almacén")==0)
            iRep    = 1;
        else if(jComRep.getSelectedItem().toString().compareTo("Ventas X producto")==0)
            iRep    = 2;
        else if(jComRep.getSelectedItem().toString().compareTo("Listas de precio X producto")==0)
            iRep    = 3;
        else if(jComRep.getSelectedItem().toString().compareTo("Prioridad de compra")==0)
            iRep    = 4;
        else if(jComRep.getSelectedItem().toString().compareTo("Prioridad de venta")==0)
            iRep    = 5;
        else if(jComRep.getSelectedItem().toString().compareTo("Kits")==0)
            iRep    = 6;
        else if(jComRep.getSelectedItem().toString().compareTo("Servicios")==0)
            iRep    = 7;
        else if(jComRep.getSelectedItem().toString().compareTo("Existencias por series")==0)
            iRep    = 8;
        else if(jComRep.getSelectedItem().toString().compareTo("Valor de inventario")==0)
            iRep    = 9;                                
        else if(jComRep.getSelectedItem().toString().compareTo("Lotes y pedimentos")==0)
            iRep    = 10;                                

        /*Determina si serán solo con existencias o no en caso de existencias por almacén*/
        String sExist   = "";
        if(jCSolExis.isSelected())
            sExist      = "1";
        
        /*Obtiene la cada y la sucursal*/
        final String sCajFi         = jTCaj.getText();
        final String sSucuFi        = jTSucu.getText();
        
        /*Declara variables final para el thread*/
        final String sEmpFi          = jTCli.getText().replace(jTSer.getText(), "");
        final String sFDeFi          = sFDe;
        final String sFAFi           = sFA;
        final String sAlmaFi         = jTAlma.getText().trim();
        final String sProdFi         = jTProd.getText();
        final String sMetCostFi      = sMetCost;
        final int    iRepFi          = iRep;
        final String sEstaFi         = jTUsr.getText();        
        final String sAnaqFi         = jTAna.getText();
        final String sExistFi        = sExist;
        final String sLugFi          = jTLug.getText();
        final String sMarcFi         = jTMarc.getText();
        final String sLinFi          = jTLin.getText();
        final String sClasFi         = jTClas.getText();
        final String sImpFi          = jTImp.getText();
        final String sJeraFi         = jTJera.getText();
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
                    pa.put("EMP",           sEmpFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);
                    pa.put("PROD",          sProdFi);
                    pa.put("ESTAC",         sEstaFi);
                    pa.put("NOCAJ",         sCajFi);
                    pa.put("SUCU",          sSucuFi);                    
                    pa.put("LUG",           sLugFi);
                    pa.put("ANAQ",          sAnaqFi);
                    pa.put("ALMA",          sAlmaFi);
                    pa.put("MARC",          sMarcFi);
                    pa.put("EXIST",         sExistFi);
                    pa.put("LIN",           sLinFi);
                    pa.put("CLAS",          sClasFi);
                    pa.put("JERA",          sJeraFi);
                    pa.put("IMP",           sImpFi);                    
                    pa.put("UNI",           sUniFi);                    
                    pa.put("MED",           sMedFi);                    
                    pa.put("COLO",          sColoFi);                    
                    pa.put("PES",           sPesFi);                    
                    pa.put("UBIAD",         sUbiAdFi);                    
                    pa.put("FECH",          da.format(dat));
                    
                    /*Determina el reporte que será*/
                    JasperReport ja = null;
                    /*Será reporte de existencias por almacén*/
                    if(iRepFi==1)                    
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptExistXAlm.jrxml"));                    
                    /*Será reporte de ventas por producto*/
                    else if(iRepFi==2)                   
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptVtaXProd.jrxml"));  
                    /*Será reporte de listas de precio por producto*/
                    else if(iRepFi==3)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptListPXProd.jrxml"));                    
                    /*Será reporte de prioridad de compra*/
                    else if(iRepFi==4)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPrioComProd.jrxml"));
                    /*Será reporte de prioridad de ventas*/
                    else if(iRepFi==5)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPrioVtaProd.jrxml"));                    
                    /*Será reporte de kits*/
                    else if(iRepFi==6)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptKits.jrxml"));                    
                    /*Será reporte de servicios */
                    else if(iRepFi==7)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptServ.jrxml"));                    
                    /*Será reporte de existencias por serie*/
                    else if(iRepFi==8)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptExistSer.jrxml"));                    
                    /*Será reporte de valor de inventario*/
                    else if(iRepFi==9)
                    {
                        /*Agrega el parámetro extra para el reporte de valor de inventario y compila el reporte*/
                        pa.put("METCOST",          sMetCostFi);
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptValInve.jrxml"));                    
                    }
                    /*Será reporte de lotes y pedimentos*/
                    else if(iRepFi==10)
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptLotPed.jrxml"));                    
                                        
                    /*Llenalo y muestralo*/
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH); 
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Productos");
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

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTSer, jTSer, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBCliActionPerformed

    
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
        {
            //Llama a la función escalable
            vKeyPreEsc(evt);
        }
        
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
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
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
    private void jBAnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAnaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAnaKeyPressed

    
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
    private void jBLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLugKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLugKeyPressed

    
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
    private void jBAnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnaActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAna.getText(), 15, jTAna, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBAnaActionPerformed

    
    /*Cuando se presiona el botón de lugar*/
    private void jBLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLugActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTLug.getText(), 16, jTLug, null, null, "", null);
        b.setVisible(true);
            
    }//GEN-LAST:event_jBLugActionPerformed

    
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
            vKeyPreEsc(evt);
        
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
            vKeyPreEsc(evt);
        
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
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTProdFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del anaquel*/
    private void jTAnaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnaFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTAna.getText().compareTo("")!=0)
            jTAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAnaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del lugar*/
    private void jTLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTLug.getText().compareTo("")!=0)
            jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTLugFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la ubicación adicional*/
    private void jTUbiAdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbiAdFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUbiAd.getText().compareTo("")!=0)
            jTUbiAd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUbiAdFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la línea*/
    private void jTLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLinFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTLin.getText().compareTo("")!=0)
            jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTLinFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTClas.getText().compareTo("")!=0)
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTImp.getText().compareTo("")!=0)
            jTImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la unidad*/
    private void jTUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUniFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTUni.getText().compareTo("")!=0)
            jTUni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUniFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del peso*/
    private void jTPesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTPes.getText().compareTo("")!=0)
            jTPes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTPesFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del color*/
    private void jTColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTColo.getText().compareTo("")!=0)
            jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTColoFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la marca*/
    private void jTMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTMarc.getText().compareTo("")!=0)
            jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTMarcFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la medida*/
    private void jTMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTMed.getText().compareTo("")!=0)
            jTMed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTMedFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del fabricante*/
    private void jTFabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFabFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTFab.getText().compareTo("")!=0)
            jTFab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTFabFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTCli.getText().compareTo("")!=0)
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    /*Cuando se presiona una tecla en el combo de los tipos de reportes*/
    private void jComRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComRepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComRepKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUsrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAnaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAnaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAna.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAnaMouseEntered

    
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
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLugMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLugMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLug.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLugMouseEntered

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAnaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAnaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAna.setBackground(colOri);
        
    }//GEN-LAST:event_jBAnaMouseExited

    
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
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(colOri);
        
    }//GEN-LAST:event_jBCliMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLugMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLugMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLug.setBackground(colOri);
        
    }//GEN-LAST:event_jBLugMouseExited

    
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

    
    /*Cuando sucede una acción en el combo del tipo de reportes*/
    private void jComRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComRepActionPerformed
        
        /*Habilita todos los controles*/
        jTSucu.setEnabled   (true);
        jTSucu.setFocusable (true);
        jTCaj.setEnabled    (true);
        jTCaj.setFocusable  (true);
        jTUsr.setEnabled    (true);
        jTCli.setFocusable  (true);
        jTCli.setEnabled    (true);
        jBCli.setFocusable  (true);
        jBCli.setEnabled    (true);
        jTUsr.setFocusable  (true);
        jDDe.setEnabled     (true);
        jDDe.setFocusable   (true);
        jDA.setEnabled      (true);
        jDA.setFocusable    (true);
        jBUsr.setEnabled    (true);
        jBUsr.setFocusable  (true);

        /*Establece el campo de fecha den entrega para que solo se pueda modificar con el botón*/
        jDDe.getDateEditor().setEnabled(false);
        jDA.getDateEditor().setEnabled(false);

        /*Esconde el check de mostra solo existencias*/
        jCSolExis.setVisible    (false);
        jCSolExis.setFocusable  (false);

        /*Si el reporte es de kits entonces*/
        if(jComRep.getSelectedItem().toString().compareTo("Kits")==0 || jComRep.getSelectedItem().toString().compareTo("Servicios")==0 || jComRep.getSelectedItem().toString().compareTo("Existencias X almacén")==0 || jComRep.getSelectedItem().toString().compareTo("Listas de precio X producto")==0 || jComRep.getSelectedItem().toString().compareTo("Existencias por series")==0)
        {
            /*Deshabilita los controles*/
            jTSucu.setEnabled   (false);
            jTSucu.setFocusable (false);
            jTCaj.setEnabled    (false);
            jTCaj.setFocusable  (false);
            jTUsr.setEnabled    (false);
            jTUsr.setFocusable  (false);
            jBUsr.setEnabled    (false);
            jBUsr.setFocusable  (false);
            jTCli.setFocusable  (false);
            jTCli.setEnabled    (false);
            jBCli.setFocusable  (false);
            jBCli.setEnabled    (false);            
            jDDe.setEnabled     (false);
            jDDe.setFocusable   (false);
            jDA.setEnabled      (false);
            jDA.setFocusable    (false);
        }
        
        /*Si se de existencias entonces*/
        if(jComRep.getSelectedItem().toString().trim().compareTo("Lotes y pedimentos")==0)
        {
            /*Muestra el check de mostra solo existencias*/
            jCSolExis.setVisible(true);
            jCSolExis.setFocusable(true);
        }                
        
    }//GEN-LAST:event_jComRepActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());

    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTJeraKeyPressed

    
    /*Cuando el mouse entra en el botón de la jerarquía*/
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBJeraMouseEntered

    
    /*Cuando el mouse sale del botón de jerarquía*/
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(colOri);
        
    }//GEN-LAST:event_jBJeraMouseExited

    
    /*Cuando se presiona el botón de jerarquía*/
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis v = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "prod");
        v.setVisible(true);
        
    }//GEN-LAST:event_jBJeraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de jerarquíia*/
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBJeraKeyPressed

    
    /*Cuando se presiona una tecla en el botón de búscar almacén*/
    private void jBAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAlmaKeyPressed

    
    /*Cuando se presiona el botón de búscar almacén*/
    private void jBAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlmaActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTAlma.getText(), 11, jTAlma, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBAlmaActionPerformed

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAlmaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBAlma.setBackground(colOri);

    }//GEN-LAST:event_jBAlmaMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAlmaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAlmaMouseEntered

        /*Cambia el color del fondo del botón*/
        jBAlma.setBackground(Star.colBot);

    }//GEN-LAST:event_jBAlmaMouseEntered

    
    /*Cuando se tipea una tecla en el campo del almacén*/
    private void jTAlmaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTAlmaKeyTyped

    
    /*Cuando se presiona una tecla en el campo del almacén*/
    private void jTAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTAlma.getText(), 11, jTAlma, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTAlmaKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTAlma.getText().compareTo("")!=0)
        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTAlmaFocusLost


    /*Cuando se gana el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTAlma.setSelectionStart(0);jTAlma.setSelectionEnd(jTAlma.getText().length());

    }//GEN-LAST:event_jTAlmaFocusGained

    
    /*Cuando se presiona una tecla en el check de solo existencias*/
    private void jCSolExisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCSolExisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCSolExisKeyPressed
                          
       
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
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBAlma;
    private javax.swing.JButton jBAna;
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBColo;
    private javax.swing.JButton jBFab;
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBLin;
    private javax.swing.JButton jBLug;
    private javax.swing.JButton jBMarc;
    private javax.swing.JButton jBMed;
    private javax.swing.JButton jBPes;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBUbiAd;
    private javax.swing.JButton jBUni;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCSolExis;
    private javax.swing.JCheckBox jCTFech;
    private javax.swing.JComboBox jComRep;
    private com.toedter.calendar.JDateChooser jDA;
    private com.toedter.calendar.JDateChooser jDDe;
    private javax.swing.JLabel jLAyu;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTAna;
    private javax.swing.JTextField jTCaj;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTColo;
    private javax.swing.JTextField jTFab;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTJera;
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
