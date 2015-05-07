//Paquete
package report;

//Importaciones
import java.awt.Cursor;
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
import ptovta.Busc;
import ptovta.Login;
import static ptovta.Princip.bIdle;
import ptovta.Star;




/*Clase para controlar el reporteador de todas las facturas*/
public class RepVtas extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Declara variables de instancia*/
    private static RepVtas        obj = null;      
    
    /*Bandera para seleccionar todos los check*/
    private boolean                 bSel    = false;
    
    
    
    /*Constructor sin argumentos*/
    public RepVtas() 
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
        this.setTitle("Reporte ventas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date fFIni = new Date();
        jDateDe.setDate (fFIni);
        jDateA.setDate  (fFIni);
        
        /*Pon el foco del teclado en el campo del cliente*/
        jTEmp.grabFocus();
                
        /*Oculta el campo de serie*/
        jTSer.setVisible            (false);
        
    }/*Fin de public ReportesFacturasCos() */
        
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static RepVtas getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new RepVtas();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Lineas getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTEmp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDateDe = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jCTFech = new javax.swing.JCheckBox();
        jBAbr = new javax.swing.JButton();
        jBCli = new javax.swing.JButton();
        jTSer = new javax.swing.JTextField();
        jCFac = new javax.swing.JCheckBox();
        jCTik = new javax.swing.JCheckBox();
        jCRem = new javax.swing.JCheckBox();
        jCDevP = new javax.swing.JCheckBox();
        jCCo = new javax.swing.JCheckBox();
        jCCa = new javax.swing.JCheckBox();
        jCDev = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();
        jCDesglo = new javax.swing.JCheckBox();
        jCNot = new javax.swing.JCheckBox();
        jBSelT2 = new javax.swing.JButton();
        jBSelT = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTSerProd = new javax.swing.JTextField();
        jBSer = new javax.swing.JButton();
        jCPtoVta = new javax.swing.JCheckBox();
        jCPe = new javax.swing.JCheckBox();
        jCVta = new javax.swing.JCheckBox();
        jCFactu = new javax.swing.JCheckBox();
        jCNoFactu = new javax.swing.JCheckBox();
        jBProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jBUsr = new javax.swing.JButton();
        jTUsr = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

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
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 110, 30));

        jTEmp.setBackground(new java.awt.Color(204, 255, 204));
        jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEmp.setNextFocusableComponent(jBCli);
        jTEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEmpFocusLost(evt);
            }
        });
        jTEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEmpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEmpKeyTyped(evt);
            }
        });
        jP1.add(jTEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Usuario:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 170, -1));

        jDateDe.setEnabled(false);
        jDateDe.setNextFocusableComponent(jDateA);
        jDateDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateDeKeyPressed(evt);
            }
        });
        jP1.add(jDateDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, -1));

        jDateA.setEnabled(false);
        jDateA.setNextFocusableComponent(jTEmp);
        jDateA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateAKeyPressed(evt);
            }
        });
        jP1.add(jDateA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 140, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("De:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 150, -1));

        jCTFech.setBackground(new java.awt.Color(255, 255, 255));
        jCTFech.setSelected(true);
        jCTFech.setText("Todas las Fechas");
        jCTFech.setNextFocusableComponent(jCDesglo);
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
        jP1.add(jCTFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 130, -1));

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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 110, 30));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jTSerProd);
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
        jP1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 30, 20));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 30, -1));

        jCFac.setBackground(new java.awt.Color(255, 255, 255));
        jCFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCFac.setSelected(true);
        jCFac.setText("Facturas");
        jCFac.setNextFocusableComponent(jCNot);
        jCFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFacKeyPressed(evt);
            }
        });
        jP1.add(jCFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 80, -1));

        jCTik.setBackground(new java.awt.Color(255, 255, 255));
        jCTik.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCTik.setSelected(true);
        jCTik.setText("Tickets");
        jCTik.setNextFocusableComponent(jCRem);
        jCTik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCTikKeyPressed(evt);
            }
        });
        jP1.add(jCTik, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 90, -1));

        jCRem.setBackground(new java.awt.Color(255, 255, 255));
        jCRem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCRem.setSelected(true);
        jCRem.setText("Remisiones");
        jCRem.setNextFocusableComponent(jCFac);
        jCRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCRemKeyPressed(evt);
            }
        });
        jP1.add(jCRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 90, -1));

        jCDevP.setBackground(new java.awt.Color(255, 255, 255));
        jCDevP.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDevP.setText("DEVP");
        jCDevP.setToolTipText("Devolución Parcial");
        jCDevP.setName(""); // NOI18N
        jCDevP.setNextFocusableComponent(jCPe);
        jCDevP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDevPKeyPressed(evt);
            }
        });
        jP1.add(jCDevP, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 90, -1));

        jCCo.setBackground(new java.awt.Color(255, 255, 255));
        jCCo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCo.setSelected(true);
        jCCo.setText("CO");
        jCCo.setToolTipText("Confirmadas");
        jCCo.setNextFocusableComponent(jCCa);
        jCCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCoKeyPressed(evt);
            }
        });
        jP1.add(jCCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jCCa.setBackground(new java.awt.Color(255, 255, 255));
        jCCa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCCa.setText("CA");
        jCCa.setToolTipText("Canceladas");
        jCCa.setNextFocusableComponent(jCDev);
        jCCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCaKeyPressed(evt);
            }
        });
        jP1.add(jCCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));

        jCDev.setBackground(new java.awt.Color(255, 255, 255));
        jCDev.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDev.setText("DEV");
        jCDev.setToolTipText("Devolución");
        jCDev.setNextFocusableComponent(jCDevP);
        jCDev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDevKeyPressed(evt);
            }
        });
        jP1.add(jCDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, -1, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 110, 20));

        jCDesglo.setBackground(new java.awt.Color(255, 255, 204));
        jCDesglo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCDesglo.setText("Desglosado");
        jCDesglo.setToolTipText("Desgloza el Reporte Generado Mostrando las Partidas");
        jCDesglo.setNextFocusableComponent(jCFactu);
        jCDesglo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCDesgloActionPerformed(evt);
            }
        });
        jCDesglo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDesgloKeyPressed(evt);
            }
        });
        jP1.add(jCDesglo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 80, -1));

        jCNot.setBackground(new java.awt.Color(255, 255, 255));
        jCNot.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCNot.setText("Notas de Crédito");
        jCNot.setNextFocusableComponent(jCCo);
        jCNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCNotKeyPressed(evt);
            }
        });
        jP1.add(jCNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 120, -1));

        jBSelT2.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT2.setToolTipText("Seleccionar/Deseleccionar Todos los Estados de Ventas");
        jBSelT2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jBSelT2.setNextFocusableComponent(jCTFech);
        jBSelT2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSelT2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSelT2MouseExited(evt);
            }
        });
        jBSelT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelT2ActionPerformed(evt);
            }
        });
        jBSelT2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSelT2KeyPressed(evt);
            }
        });
        jP1.add(jBSelT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 20, 10));

        jBSelT.setBackground(new java.awt.Color(255, 255, 255));
        jBSelT.setToolTipText("Seleccionar/DeseleccionarTodos los Tipos de Ventas");
        jBSelT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jBSelT.setNextFocusableComponent(jCCo);
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cliente:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 170, -1));

        jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSerProd.setNextFocusableComponent(jBSer);
        jTSerProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerProdFocusLost(evt);
            }
        });
        jTSerProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerProdKeyPressed(evt);
            }
        });
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 250, 20));

        jBSer.setBackground(new java.awt.Color(255, 255, 255));
        jBSer.setText("...");
        jBSer.setToolTipText("Buscar Cliente(s)");
        jBSer.setNextFocusableComponent(jTProd);
        jBSer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSerMouseExited(evt);
            }
        });
        jBSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSerActionPerformed(evt);
            }
        });
        jBSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSerKeyPressed(evt);
            }
        });
        jP1.add(jBSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, 30, 20));

        jCPtoVta.setBackground(new java.awt.Color(255, 255, 255));
        jCPtoVta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCPtoVta.setSelected(true);
        jCPtoVta.setText("Punto venta");
        jCPtoVta.setToolTipText("Ventas que se generarón en el punto de venta");
        jCPtoVta.setNextFocusableComponent(jDateDe);
        jCPtoVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPtoVtaKeyPressed(evt);
            }
        });
        jP1.add(jCPtoVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 100, -1));

        jCPe.setBackground(new java.awt.Color(255, 255, 255));
        jCPe.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCPe.setText("PE");
        jCPe.setToolTipText("Pendientes");
        jCPe.setNextFocusableComponent(jCTFech);
        jCPe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPeKeyPressed(evt);
            }
        });
        jP1.add(jCPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 110, -1));

        jCVta.setBackground(new java.awt.Color(255, 255, 255));
        jCVta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCVta.setSelected(true);
        jCVta.setText("Ventas");
        jCVta.setToolTipText("Ventas que se generarón fuera del punto de venta");
        jCVta.setNextFocusableComponent(jCPtoVta);
        jCVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVtaKeyPressed(evt);
            }
        });
        jP1.add(jCVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 80, -1));

        jCFactu.setBackground(new java.awt.Color(255, 255, 255));
        jCFactu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCFactu.setSelected(true);
        jCFactu.setText("Facturado");
        jCFactu.setToolTipText("Documentos que ya estan facturados");
        jCFactu.setNextFocusableComponent(jCNoFactu);
        jCFactu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFactuKeyPressed(evt);
            }
        });
        jP1.add(jCFactu, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 80, -1));

        jCNoFactu.setBackground(new java.awt.Color(255, 255, 255));
        jCNoFactu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCNoFactu.setText("No facturado");
        jCNoFactu.setToolTipText("Documentos que no estan facturados");
        jCNoFactu.setNextFocusableComponent(jCVta);
        jCNoFactu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCNoFactuKeyPressed(evt);
            }
        });
        jP1.add(jCNoFactu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTUsr);
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
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 30, 20));

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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 250, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Serie:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 170, -1));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Usuario(s)");
        jBUsr.setNextFocusableComponent(jBAbr);
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
        jP1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 30, 20));

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
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 250, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Producto:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 170, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
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

    
    /*Cuando se presiona una tecla en el campo de clientes*/
    private void jTEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTEmp.getText(), 1, jTEmp, jTSer, jTSer, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEmpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de cliente*/
    private void jTEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEmp.setSelectionStart(0);jTEmp.setSelectionEnd(jTEmp.getText().length());        
        
    }//GEN-LAST:event_jTEmpFocusGained
                    
    
    /*Cuando se tipea una tecla en el campo de descripción de la línea*/
    private void jTEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEmpKeyTyped

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
               
        /*Si no esta seleccionado por lo menos una opción en los check de ventas administrativas o de punto de venta*/
        if(!jCVta.isSelected() && !jCPtoVta.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una naturalez de venta. Ya sean ventas que son del punto de venta o fuera de el.", "Naturaleza venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el check de ventas y regresa*/
            jCVta.grabFocus();
            return;
        }
                                
        //Si no esta seleccionado por lo menos facturado o no facturado entonces
        if(!jCFactu.isSelected() && !jCNoFactu.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos facturados o no facturados.", "Naturaleza venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el check de ventas y regresa*/
            jCFactu.grabFocus();
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el cliente existe*/        
        try
        {
            sQ  = "SELECT codemp FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTEmp.getText().trim() + "'";                      
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos*/
            if(!rs.next() && jTEmp.getText().trim().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El cliente: " + jTEmp.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTEmp.grabFocus();               
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

        /*Crea la condición para el checkbox de punto de venta*/
        String sPto                 = "";
        if(jCPtoVta.isSelected() && jCVta.isSelected())
            sPto                    = "1";
        else if(jCVta.isSelected())
            sPto                    = "2";
        else if(jCPtoVta.isSelected())
            sPto                    = "3";
                
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
        /*Else si esta marcado todas las fechas entonces*/
        else
        {
            /*Coloca los campos de fecha con cadena vacia para que el reporete muestre todas las fechas*/
            sFA     = "";
            sFDe    = "";
        }
        
        /*Si no selecciono por lo menos un tipo de venta a ver entonces*/
        if(!jCTik.isSelected() && !jCRem.isSelected() && !jCFac.isSelected() && !jCNot.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un tipo de venta.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jCTik.grabFocus();
            return;
        }
        
        /*Si no selecciono por lo menos un estado de la venta entonces*/
        if(!jCDev.isSelected() && !jCCa.isSelected() && !jCCo.isSelected() && !jCDevP.isSelected() && !jCPe.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un estado de venta.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jCCo.grabFocus();
            return;
        }
        
        /*Obtiene los tipos de ventas que tiene que mostrar*/
        String sTipVta  = "";
        if(jCTik.isSelected())
            sTipVta += "'TIK',";
        if(jCRem.isSelected())
            sTipVta += "'REM',";
        if(jCFac.isSelected())
            sTipVta += "'FAC',";
        if(jCNot.isSelected())
            sTipVta += "'NOT',";
                
        /*Obtiene los tipos de estado que tiene que mostrar*/
        String sTipEstad  = "";
        if(jCCo.isSelected())
            sTipEstad += "'CO',";
        if(jCCa.isSelected())
            sTipEstad += "'CA',";
        if(jCDev.isSelected())
            sTipEstad += "'DEV',";
        if(jCDevP.isSelected())
            sTipEstad += "'DEVP',";
        if(jCPe.isSelected())
            sTipEstad += "'PE',";
        
        /*Reemplaza la última coma por vacio en los dos casos*/
        sTipVta         = sTipVta.substring(0,sTipVta.length() - 1);
        sTipEstad       = sTipEstad.substring(0,sTipEstad.length() - 1);

        //Determina los facturados o no facturados        
        String sFactu   = "1";
        if(jCFactu.isSelected() && jCNoFactu.isSelected())
            sFactu      = "1";
        else if(jCFactu.isSelected())
            sFactu      = "2";
        else if(jCNoFactu.isSelected())
            sFactu      = "3";
                
        /*Determina que reporte será*/
        final String sTip;
        if(jCDesglo.isSelected())
            sTip    = "/jasreport/rptVtaTDet.jrxml";
        else
            sTip    = "/jasreport/rptVtaT.jrxml";
        
        /*Declara las variable final para el thread*/
        final String sEmpFi         = jTEmp.getText();
        final String sFDeFi         = sFDe;
        final String sFAFi          = sFA;
        final String sTipVtaFi      = sTipVta;
        final String sTipEstadFi    = sTipEstad;
        final String sFactuFi       = sFactu;
        final String sPtoFi         = sPto;        
        final String sUsrFi         = jTUsr.getText().trim();        
        final String sProdFi        = jTProd.getText().trim();        
        final String sSerProdFi     = jTSerProd.getText().trim();
        
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
                    pa.put(Star.sSerMostG,  sEmpFi);
                    pa.put("F_D",           sFDeFi);
                    pa.put("F_A",           sFAFi);
                    pa.put("TIPVTA",        sTipVtaFi);
                    pa.put("TIPESTAD",      sTipEstadFi);
                    pa.put("SERPROD",       sSerProdFi);
                    pa.put("PTO",           sPtoFi);
                    pa.put("USR",           sUsrFi);
                    pa.put("PROD",          sProdFi);
                    pa.put("FACTU",         sFactuFi); 
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    
                    /*Compila el reporte y maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream(sTip));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Ventas");
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
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTEmp.getText(), 1, jTEmp, jTSer, jTSer, "", null);
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

    
    /*Cuando se mueve la rueda del ratón en la  forma*/
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

    
    /*Cuando se presiona una tecla en el checkbox de ticket*/
    private void jCTikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCTikKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCTikKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de remisiones*/
    private void jCRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCRemKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de factura*/
    private void jCFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFacKeyPressed
            
    
    /*Cuando se presiona una tecla en el checkbox de confirmadas*/
    private void jCCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cancelada*/
    private void jCCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de devolución*/
    private void jCDevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDevKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDevKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de devolución parcial*/
    private void jCDevPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDevPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDevPKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de la cliente*/
    private void jTEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEmp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTEmp.getText().compareTo("")!=0)
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTEmpFocusLost

    
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
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCliMouseEntered

        
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

    
    /*Cuando se presiona una tecla en el checkbox de desglozar*/
    private void jCDesgloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDesgloKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDesgloKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de notas de crédito*/
    private void jCNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCNotKeyPressed

    
    /*Cuando se presiona el botón de seleccionar todo 1*/
    private void jBSelTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelTActionPerformed
        
        /*Seleccioan o deselecciona todos los check 1*/
        if(bSel)
        {
            /*Deselecciona todos los check 1*/
            jCTik.setSelected(false);
            jCRem.setSelected(false);
            jCFac.setSelected(false);
            jCNot.setSelected(false);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCTik.setSelected(true);
            jCRem.setSelected(true);
            jCFac.setSelected(true);
            jCNot.setSelected(true);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }
        
    }//GEN-LAST:event_jBSelTActionPerformed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo en el botón 1*/
    private void jBSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSelTKeyPressed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo 2*/
    private void jBSelT2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSelT2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSelT2KeyPressed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo 2*/
    private void jBSelT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelT2ActionPerformed
        
        /*Seleccioan o deselecciona todos los check 1*/
        if(bSel)
        {
            /*Deselecciona todos los check 1*/
            jCCo.setSelected    (false);
            jCCa.setSelected    (false);
            jCDev.setSelected   (false);
            jCDevP.setSelected  (false);
            jCPe.setSelected    (false);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = false;
        }
        else
        {
            /*Selecciona todos los check 1*/
            jCCo.setSelected    (true);
            jCCa.setSelected    (true);
            jCDev.setSelected   (true);
            jCDevP.setSelected  (true);
            jCPe.setSelected    (true);
            
            /*Coloca la bandera para la próxima selección*/
            bSel    = true;
        }
        
    }//GEN-LAST:event_jBSelT2ActionPerformed

    
    /*Cuando el mouse entra en el botón de seleccionar todos los check 1*/
    private void jBSelTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSelT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSelTMouseEntered

    
    /*Cuando el mouse entra en el botón de seleccionar todos los check 2*/
    private void jBSelT2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelT2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSelT2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSelT2MouseEntered

    
    /*Cuando el mouse sale del botón de seleccionar todos los check 1*/
    private void jBSelTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSelT.setBackground(colOri);
        
    }//GEN-LAST:event_jBSelTMouseExited

    
    /*Cuando el mouse sale del botón de seleccionar todos los check 2*/
    private void jBSelT2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSelT2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSelT2.setBackground(colOri);
        
    }//GEN-LAST:event_jBSelT2MouseExited

    
    /*Cuando se gana el foco del teclado en el campo de la serie del producto*/
    private void jTSerProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSerProd.setSelectionStart(0);jTSerProd.setSelectionEnd(jTSerProd.getText().length());
        
    }//GEN-LAST:event_jTSerProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la serie del producto*/
    private void jTSerProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTSerProd.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerProdFocusLost

    
    /*Cuando se presiona una teclae en el campo de la serie del producto*/
    private void jTSerProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTSer.getText().trim(), 35, jTSerProd, null, null, "", null);
            b.setVisible(true);            
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerProdKeyPressed

    
    /*Cuando el mouse entra en el botón de la serie del producto*/
    private void jBSerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSerMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSerMouseEntered

    
    /*Cuando el mouse sale del botón de la serie del producto*/
    private void jBSerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSerMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSer.setBackground(colOri);
        
    }//GEN-LAST:event_jBSerMouseExited

    
    /*Cuando se presiona el botón de búscar serie del producto*/
    private void jBSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSerActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTSer.getText().trim(), 35, jTSerProd, null, null, "", null);
        b.setVisible(true);            

    }//GEN-LAST:event_jBSerActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar serie de producto*/
    private void jBSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSerKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox del punto de venta*/
    private void jCPtoVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPtoVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPtoVtaKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de pendiente*/
    private void jCPeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPeKeyPressed

    
    /*Cuando se presiona una tecla en el check de ventas fuera del punto de venta*/
    private void jCVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCVtaKeyPressed

    
    //Cuando se presiona una tecla en el check de facturados
    private void jCFactuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFactuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCFactuKeyPressed

    
    //Cuando se presiona una tecla en el check de no facturados
    private void jCNoFactuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCNoFactuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCNoFactuKeyPressed

    
    //Cuando el mouse entra en el botón del producto
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    //Cuando el mouse sale del botón del producto
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    //Cuando se presiona el botón de búscar producto
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBProdActionPerformed

    
    //Cuando se presiona una tecla en el botón del producto
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProdKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del producto
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    
    //Cuando se pierde el foco del rteclado en el campo del producto
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTProdFocusLost

    
    //Cuando se presiona una tecla en el campo del producto
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

    
    //Cuando se tipea una tecla en el campo del prodcuto
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTProdKeyTyped

    
    //Cuando el mouse entra en el botón de usuarios
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUsrMouseEntered

    
    //Cuando el usuario sale del botón de los usuarios
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(colOri);

    }//GEN-LAST:event_jBUsrMouseExited

    
    //Cuando se presiona el botón de búscar usuarios
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBUsrActionPerformed

    
    //Cuando se presiona uan tecla en el botón de los usuarios
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUsrKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de los usuarios
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());

    }//GEN-LAST:event_jTUsrFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de los usuarios
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUsr.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTUsrFocusLost

    
    //Cuando se presiona una tecla en el campo de los usuarios
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTUsr.getText(), 4, jTUsr, null, null, "", null);
            b.setVisible(true);
        }
        //Else, llama a la función para procesarlo normalmente
        else
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTUsrKeyPressed

    
    //Cuando se tipea una tecla en el campo del usuario
    private void jTUsrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTUsrKeyTyped

    private void jCDesgloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCDesgloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCDesgloActionPerformed
                         
       
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
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSelT;
    private javax.swing.JButton jBSelT2;
    private javax.swing.JButton jBSer;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCCa;
    private javax.swing.JCheckBox jCCo;
    private javax.swing.JCheckBox jCDesglo;
    private javax.swing.JCheckBox jCDev;
    private javax.swing.JCheckBox jCDevP;
    private javax.swing.JCheckBox jCFac;
    private javax.swing.JCheckBox jCFactu;
    private javax.swing.JCheckBox jCNoFactu;
    private javax.swing.JCheckBox jCNot;
    private javax.swing.JCheckBox jCPe;
    private javax.swing.JCheckBox jCPtoVta;
    private javax.swing.JCheckBox jCRem;
    private javax.swing.JCheckBox jCTFech;
    private javax.swing.JCheckBox jCTik;
    private javax.swing.JCheckBox jCVta;
    private com.toedter.calendar.JDateChooser jDateA;
    private com.toedter.calendar.JDateChooser jDateDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTEmp;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
