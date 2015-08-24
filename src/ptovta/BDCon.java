//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*26 05 2015*/
/*Felipe Ruiz Garcia*/
/*Clases para la expresion regular del correo*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/*Clase para crear o modificar el archivo de configuración*/
public class BDCon extends javax.swing.JDialog
{
    /*Declara variables de instancia de base de datos*/
    private Connection      conn;
    
    //Contiene los valores originales de conexión a la base de datos
    private String          sInstOri;
    private String          sUsrOri;
    private String          sContraOri;
    private String          sPortOri;
    private String          sBDOri;
    
    /*Variable que contendrá la variable para saber si cuando se presione el botón de salir se tiene que salir del sistema o solo de la forma*/
    private boolean         bSal;

    
    
    
    /*Constructor sin argumentos*/
    public BDCon(boolean bS) 
    {          
        /*Inicaliza los componentes gráficos*/
        initComponents();
      
        /*25 06 2015 Heriberto Daniel Sanchez Peña*/
        /*Se añadieron dos radio buttons para indicar si la terminal sera on-line u off-line*/
        /*Al cargar el formulario se dejan invisibles, ya que estos dependen del check box de estacion de trabajo*/
        
        rbOff.setToolTipText("El nombre de la empresa debe de ser el mismo que en el servidor");
        
        // 03 Julio 2015 // Heriberto // Configuracion de sistema
        jTNom.setEnabled(true); 
        rbOn.setEnabled(false);
        rbOff.setEnabled(false);
        rbOn.setSelected(false);
        rbOff.setSelected(false);
        jTNom.setToolTipText("");
           
        //Que sea modal
        this.setModal(true);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGua);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicializa las variables de la clase con los parámetros del constructor*/
        bSal    = bS;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo del nombre de la empresa*/
        jTNom.grabFocus();                
               
        /*Si el archivo de configuración no existe entonces regresa aquí*/        
        if(!new File(Star.sArchConf).exists())
            return;
        
        /*Desserializamos el archivo*/
        BDConf bd;
        try(FileInputStream fileIn   = new FileInputStream(Star.sArchConf); ObjectInputStream in = new ObjectInputStream(fileIn);)
        {           
           bd               = (BDConf)in.readObject();           
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrCompa, expnIO.getStackTrace());
            return;                                                
        } 
        catch(ClassNotFoundException expnClassNotF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNotF.getMessage(), Star.sErrClassNoF, expnClassNotF.getStackTrace());
            return;                                                
        }

        /*Obtiene la instancia y desencriptala*/
        Star.sInstancia          = bd.sInst;                
        Star.sInstancia          = Star.sDecryp(Star.sInstancia);

        /*Obtiene el usuario y desencriptalo*/
        Star.sUsuario             = bd.sUsr;
        Star.sUsuario             = Star.sDecryp(Star.sUsuario);

        /*Obtiene la contraseña y desencriptala*/
        Star.sContrasenia         = bd.sContra;
        Star.sContrasenia         = Star.sDecryp(Star.sContrasenia);

        /*Obtiene la base de datos y desencriptala*/
        Star.sBD                  = bd.sBD;
        Star.sBD                  = Star.sDecryp(Star.sBD);

        /*Obtiene el puerto y desencriptalo*/
        Star.sPort                = bd.sPort;
        Star.sPort                = Star.sDecryp(Star.sPort);
                
        /*Obtiene la sucursal y desencriptala*/
        Star.sSucu                = bd.sSucur;
        Star.sSucu                = Star.sDecryp(Star.sSucu);

        /*Obtiene el nùmero de caja y desencriptala*/
        Star.sNoCaj               = bd.sNumCaj;
        Star.sNoCaj               = Star.sDecryp(Star.sNoCaj);
        
        /*Obtiene el nombre de la empresa y desencriptala*/
        Star.sNombreEmpresa       = bd.sNombEmp;
        Star.sNombreEmpresa       = Star.sDecryp(Star.sNombreEmpresa);
        
        /*Obtiene si es usuario de trabajo o no y desencriptala*/
        Star.sEstacTrab           = bd.sEstacTrab;
        Star.sEstacTrab           = Star.sDecryp(Star.sEstacTrab);
        
        /*Obtiene los datos de la conexión local y desencriptalos*/
        Star.sContraLoc           = bd.sContraLoc;
        Star.sContraLoc           = Star.sDecryp(Star.sContraLoc);
        Star.sUsrLoc              = bd.sUsrLoc;
        Star.sUsrLoc              = Star.sDecryp(Star.sUsrLoc);
        Star.sInstLoc             = bd.sInstLoc;
        Star.sInstLoc             = Star.sDecryp(Star.sInstLoc);
        
        /*Obtiene el puerto local y desencriptalo*/
        Star.sPortLoc             = bd.sPortLoc;
        Star.sPortLoc             = Star.sDecryp(Star.sPortLoc);
        
        /*Coloca los datos en sus lugares correspondientes*/
        jTInst.setText                  (Star.sInstancia);
        jTUsr.setText                   (Star.sUsuario);
        jPCont.setText                  (Star.sContrasenia);
        jTBD.setText                    (Star.sBD);
        jTSuc.setText                   (Star.sSucu);
        jTNoCaj.setText                 (Star.sNoCaj);
        jTNom.setText                   (Star.sNombreEmpresa);
        jTPort.setText                  (Star.sPort);
        
        //Guarda los valores originales de las conexiones
        sInstOri                    = Star.sInstancia;
        sUsrOri                     = Star.sUsuario;
        sContraOri                  = Star.sContrasenia;
        sBDOri                      = Star.sBD;
        sPortOri                    = Star.sPort;
        
        /*Coloca en su lugar correspondiente los datos de conexión local*/
        jTInstLoc.setText               (Star.sInstLoc);
        jTUsrLoc.setText                (Star.sUsrLoc);
        jPContLoc.setText               (Star.sContraLoc);
        jTPortLoc.setText               (Star.sPortLoc);
        
        /*Coloca si es usuario de trabajo o no*/
        if(Star.sEstacTrab.compareTo("1")==0)
            jCEstaT.setSelected(true);
        else
            jCEstaT.setSelected(false);    
        
    }/*Fin de public BDCon() */

    /*Clase para guardar los datos de la conexión local*/
    protected class ConLocInter
    {
        /*Declara las variables de la conexión local*/
        public String sUsrLoc;
        public String sInstLoc;        
        public String sContraLoc;
        
        /*Constructor sin argumentos*/
        public void ConLocInter()
        {
            /*Inicializa en cadena vacia los campos*/
            sUsrLoc     = "";
            sInstLoc    = "";
            sContraLoc  = "";
        }
    }    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jP1 = new javax.swing.JPanel();
        jBGua = new javax.swing.JButton();
        labelEmail = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTInst = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTBD = new javax.swing.JTextField();
        jTUsr = new javax.swing.JTextField();
        jPCont = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jBProb = new javax.swing.JButton();
        jCEstaT = new javax.swing.JCheckBox();
        jCMosC = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTSuc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTNoCaj = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTInstLoc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTUsrLoc = new javax.swing.JTextField();
        jPContLoc = new javax.swing.JPasswordField();
        jBProbLoc = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTPort = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTPortLoc = new javax.swing.JTextField();
        textEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rbOff = new javax.swing.JRadioButton();
        rbOn = new javax.swing.JRadioButton();

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
        jBGua.setToolTipText("Guardar");
        jBGua.setNextFocusableComponent(jBSal);
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
        jP1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 120, 30));

        labelEmail.setBackground(new java.awt.Color(255, 255, 255));
        labelEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelEmail.setForeground(new java.awt.Color(51, 51, 51));
        labelEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmail.setText("*Correo Electrónico:");
        labelEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(labelEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 220, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jBProb);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 110, 30));

        jTInst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTInst.setNextFocusableComponent(jTInstLoc);
        jTInst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTInstFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTInstFocusLost(evt);
            }
        });
        jTInst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTInstKeyPressed(evt);
            }
        });
        jP1.add(jTInst, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 220, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CONFIGURACIÓN INICIAL");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 470, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("*Usuario:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 220, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("*Contraseña:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 220, -1));

        jTBD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBD.setNextFocusableComponent(jTPortLoc);
        jTBD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBDFocusLost(evt);
            }
        });
        jTBD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTBDMouseClicked(evt);
            }
        });
        jTBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBDActionPerformed(evt);
            }
        });
        jTBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBDKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTBDKeyTyped(evt);
            }
        });
        jP1.add(jTBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 220, 20));

        jTUsr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jTUsrLoc);
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
        });
        jP1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 220, 20));

        jPCont.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPCont.setNextFocusableComponent(jPContLoc);
        jPCont.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContFocusLost(evt);
            }
        });
        jPCont.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContKeyPressed(evt);
            }
        });
        jP1.add(jPCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 220, 20));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("*Instancia:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, -1));

        jTNom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTInst);
        jTNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomFocusLost(evt);
            }
        });
        jTNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTNomActionPerformed(evt);
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
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 450, 20));

        jBProb.setBackground(new java.awt.Color(255, 255, 255));
        jBProb.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProb.setForeground(new java.awt.Color(0, 102, 0));
        jBProb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prov.png"))); // NOI18N
        jBProb.setText("1");
        jBProb.setToolTipText("Probar Conexión (F2)");
        jBProb.setNextFocusableComponent(jBProbLoc);
        jBProb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbMouseExited(evt);
            }
        });
        jBProb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbActionPerformed(evt);
            }
        });
        jBProb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbKeyPressed(evt);
            }
        });
        jP1.add(jBProb, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 110, 30));

        jCEstaT.setBackground(new java.awt.Color(255, 255, 255));
        jCEstaT.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCEstaT.setText("Estación de trabajo F5");
        jCEstaT.setNextFocusableComponent(jTNom);
        jCEstaT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCEstaTActionPerformed(evt);
            }
        });
        jCEstaT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEstaTKeyPressed(evt);
            }
        });
        jP1.add(jCEstaT, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 130, -1));

        jCMosC.setBackground(new java.awt.Color(255, 255, 255));
        jCMosC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosC.setText("Mostrar contraseña F4");
        jCMosC.setNextFocusableComponent(jCEstaT);
        jCMosC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMosCActionPerformed(evt);
            }
        });
        jCMosC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosCKeyPressed(evt);
            }
        });
        jP1.add(jCMosC, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 140, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("*Base de Datos:");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 220, -1));

        jTSuc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTSuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSuc.setNextFocusableComponent(jTNoCaj);
        jTSuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSucFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSucFocusLost(evt);
            }
        });
        jTSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSucKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSucKeyTyped(evt);
            }
        });
        jP1.add(jTSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 220, 20));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("*Nombre Sucursal:");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 220, -1));

        jTNoCaj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTNoCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoCaj.setNextFocusableComponent(jBGua);
        jTNoCaj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoCajFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoCajFocusLost(evt);
            }
        });
        jTNoCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTNoCajActionPerformed(evt);
            }
        });
        jTNoCaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoCajKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoCajKeyTyped(evt);
            }
        });
        jP1.add(jTNoCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 220, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("*Empresa:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 450, -1));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("*Instancia Local:");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 230, -1));

        jTInstLoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInstLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTInstLoc.setNextFocusableComponent(jTUsr);
        jTInstLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTInstLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTInstLocFocusLost(evt);
            }
        });
        jTInstLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTInstLocKeyPressed(evt);
            }
        });
        jP1.add(jTInstLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 230, 20));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("*Usuario Local:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 230, -1));

        jTUsrLoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTUsrLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsrLoc.setNextFocusableComponent(jPCont);
        jTUsrLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUsrLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsrLocFocusLost(evt);
            }
        });
        jTUsrLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUsrLocKeyPressed(evt);
            }
        });
        jP1.add(jTUsrLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 230, 20));

        jPContLoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPContLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPContLoc.setNextFocusableComponent(jTBD);
        jPContLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContLocFocusLost(evt);
            }
        });
        jPContLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContLocKeyPressed(evt);
            }
        });
        jP1.add(jPContLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 230, 20));

        jBProbLoc.setBackground(new java.awt.Color(255, 255, 255));
        jBProbLoc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProbLoc.setForeground(new java.awt.Color(0, 102, 0));
        jBProbLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prov.png"))); // NOI18N
        jBProbLoc.setText("2");
        jBProbLoc.setToolTipText("Probar Conexión (F3)");
        jBProbLoc.setNextFocusableComponent(jCMosC);
        jBProbLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbLocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbLocMouseExited(evt);
            }
        });
        jBProbLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbLocActionPerformed(evt);
            }
        });
        jBProbLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbLocKeyPressed(evt);
            }
        });
        jP1.add(jBProbLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 120, 30));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 120, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("*Contraseña Local:");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 230, -1));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("*Puerto:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 220, -1));

        jTPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTPort.setText("3306");
        jTPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPort.setNextFocusableComponent(jTSuc);
        jTPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPortFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPortFocusLost(evt);
            }
        });
        jTPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPortKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPortKeyTyped(evt);
            }
        });
        jP1.add(jTPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 220, 20));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("*Puerto Local:");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 220, -1));

        jTPortLoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTPortLoc.setText("3306");
        jTPortLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPortLoc.setNextFocusableComponent(jTPort);
        jTPortLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPortLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPortLocFocusLost(evt);
            }
        });
        jTPortLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPortLocKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPortLocKeyTyped(evt);
            }
        });
        jP1.add(jTPortLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 230, 20));

        textEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        textEmail.setNextFocusableComponent(jBGua);
        textEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textEmailFocusLost(evt);
            }
        });
        textEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textEmailMouseClicked(evt);
            }
        });
        textEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEmailActionPerformed(evt);
            }
        });
        textEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textEmailKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textEmailKeyTyped(evt);
            }
        });
        jP1.add(textEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 220, 20));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("*Numero Caja:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 220, -1));

        rbOff.setBackground(new java.awt.Color(255, 255, 255));
        rbOff.setText("Off-line");
        rbOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbOffActionPerformed(evt);
            }
        });
        jP1.add(rbOff, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, -1, -1));

        rbOn.setBackground(new java.awt.Color(255, 255, 255));
        rbOn.setText("On-line");
        rbOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbOnActionPerformed(evt);
            }
        });
        rbOn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                rbOnPropertyChange(evt);
            }
        });
        jP1.add(rbOn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed
 /* Felipe Ruiz Garcia 29 05 2015 */
        /* se activa la alerta al momento de dar click con el mensaje de que es necesario un email valido */
        /*Mensajea*/
        JOptionPane.showMessageDialog(null, "Es necesario que proporcione un correo electrónico valido al cual se le enviara la contraseña para que pueda iniciar el sistema", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        // 13 Julio 2015 Felipe Ruiz Garcia
        // Creacion de la base de datos test
        // Metodo crea table test
        Star.vCreaBDTest(jTInstLoc.getText().trim(),  jTPort.getText().trim(), jTUsr.getText().trim(), jPCont.getText().trim());
        
        /*Lee el nombre de la empresa*/
        Star.sNombreEmpresa = jTNom.getText().trim();
        
        // THIS IS TEST -- Felipe Ruiz Garcia / Carlos Ramirez Ramirez
            
            int z=Star.sNombreEmpresa.length();
            z--;
            for(;z>=0;z--)
            if((Star.sNombreEmpresa.charAt(z) == '|')  || (Star.sNombreEmpresa.charAt(z) == '¬') )         
            {
                JOptionPane.showMessageDialog(null, "El nombre de la empresa no puede contener caracteres especiales." + "" + "", "Atención", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jTNom.grabFocus();                   
                    return ;
            }            
        
        /*Si el checkbox de usuario de trabajo no esta marcado entonces*/
        if(!jCEstaT.isSelected())
        {
            /*Si el campo del nombre de la empresa esta vacio no puede seguir*/
            if(Star.sNombreEmpresa.compareTo("")==0)
            { 
                /*Coloca el borde rojo*/                               
                jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                        
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo del nombre de la empresa esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTNom.grabFocus();
                return;
            }
        }
        else if(jCEstaT.isSelected()&& rbOff.isSelected())
        {
             /*Si el campo del nombre de la empresa esta vacio no puede seguir*/
            if(Star.sNombreEmpresa.compareTo("")==0)
            { 
                /*Coloca el borde rojo*/                               
                jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                        
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo del nombre de la empresa esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTNom.grabFocus();
                return;
            }
        }
        
        /*Lee la instancia*/
        Star.sInstancia  = jTInst.getText().trim();

        /*Si el campo de instancia esta vacio no puede seguir*/
        if(Star.sInstancia.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de instancia esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTInst.grabFocus();           
            return;
        }
        
        /*26 05 2015 Felipe Ruiz Garcia*/
        /* Obtengo el campo Correo Electronico de la forma y lo guardo */
        Star.correoRegistro  =  textEmail.getText().trim();
        
        /*Si el campo Email esta vacio*/
        if(Star.correoRegistro.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            textEmail.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de correo electrónico esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            textEmail.grabFocus();           
            return;
        } 
        /*Si el campo Email no esta vacio analizamos si es valido */
        else {
            
        /*Expresion regular*/ /* felipe.ruiz@sos-soft.com */
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9|-]+(\\.[A-Za-z0-9|-]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(Star.correoRegistro);
        
        /* si el Email NO es valido */
        /* se le hace saber al usuario */
        if (!mat.find()) {
            
            
            /*Coloca el borde rojo*/                               
            textEmail.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El correo electrónico es invalido.\nPor favor ingresa un correo electrónico valido.", "Correo electrónico invalido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            textEmail.grabFocus();           
            return;   
            } 
        /* Si el Email es valido entonces */
        else {
            
            /*Restablecemos color del borde*/
            textEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
            
            }   
        }
        
        /*Lee el usuario*/
        Star.sUsuario    = jTUsr.getText().trim();
        
        /*Si el campo de usuario esta vacio no puede seguir*/
        if(Star.sUsuario.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();            
            return;
        }
        
        /*Lee la contraseña*/
        Star.sContrasenia    = new String(jPCont.getPassword()).trim();        
        
        /*Lee la base de datos*/
        Star.sBD     = jTBD.getText().trim();
        
        /*Si el campo de base de datos esta vacio no puede seguir*/
        if(Star.sBD.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de base de datos esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTBD.grabFocus();            
            return;
        }
               
        /*Lee la sucursal*/
        Star.sSucu     = jTSuc.getText().trim();
        
        /*Si el campo de la sucursal esta vacio no puede seguir*/
        if(Star.sSucu.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTSuc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de base de la sucursal esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTSuc.grabFocus();            
            return;
        }
        
        /*Lee el nùmero de caja*/
        Star.sNoCaj     = jTNoCaj.getText().trim();
        
        /*Si el campo del nùmero de caja esta vacio no puede seguir*/
        if(Star.sNoCaj.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNoCaj.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del número de caja esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTNoCaj.grabFocus();            
            return;
        }
        
        /*Lee el puerto*/
        Star.sPort          = jTPort.getText().trim();

        /*Si el campo del puerto esta vacio no puede seguir*/
        if(Star.sPort.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTPort.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            jTPort.grabFocus();           
            return;
        }       
        
        /*Lee la instancia local*/
        Star.sInstLoc     = jTInstLoc.getText().trim();

        /*Si el campo de la instancia esta vacio no puede seguir*/
        if(Star.sInstLoc.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTInstLoc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de la instancia local esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTInstLoc.grabFocus();            
            return;
        }

        /*Lee el usuario local*/
        Star.sUsrLoc      = jTUsrLoc.getText().trim();

        /*Si el campo de del usuario local esta vacio no puede seguir*/
        if(Star.sUsrLoc.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsrLoc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del usuario local esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsrLoc.grabFocus();            
            return;
        }

        /*Lee la contraseña local*/
        Star.sContraLoc     = new String(jPContLoc.getPassword()).trim();        

        /*Lee el puerto local*/
        Star.sPortLoc      = jTPortLoc.getText().trim();

        /*Si el campo del puerto local esta vacio no puede seguir*/
        if(Star.sPortLoc.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTPortLoc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto local esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            jTPortLoc.grabFocus();           
            return;
        }                           
                
        //Si es estación de trabajo on-line entonces
        if(jCEstaT.isSelected()&&!rbOff.isSelected())
        {
            //Coloca la bandera
            Star.sEstacTrab  = "1";
            
            //Abre la base de datos                             
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Cierra la base de datos
            Star.iCierrBas(con);                                 
        }
        //Else es conexión servidora o es terminal off-line entonces
        else
        {
            //Coloca la bandera
            Star.sEstacTrab  = "0";
            
            //Abre la base de datos                             
            Connection con = Star.conAbrBas(true, false, Star.sInstancia, Star.sPort, "test", Star.sUsuario, Star.sContrasenia);

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar conexión", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
           
        /*Si esta abierta la forma desde antes del sistema entonces*/
        if(bSal)
        {
            /*Contiene la MAC del equipo*/
            String sMAC;
            
            /*Obtiene la MAC del equipo*/
            InetAddress ip;
            try 
            {
                /*Obtiene la ip local del equipo*/
                ip = InetAddress.getLocalHost();                

                /*Obtiene otros parámetros de conexión*/
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                /*Obtiene en bytes la MAC*/
                byte[] mac = network.getHardwareAddress();

                /*Dale formato a la MAC*/
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < mac.length; i++)                 
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));                                        

                /*Inicia la MAC*/
                sMAC    = sb.toString();
            }
            catch(UnknownHostException | SocketException e) 
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null); 
                return;
            }
            System.out.println(sMAC);
            
                
            /*Manda a pedir al servidor la serie, la fecha de instalación y el contador de días de este usuario, ya sea un nuevo registro o no*/
            String sSer;
            System.out.println(sMAC);
            System.out.println(Star.sCadVerif);
            System.out.println(Star.sNomProd);
            String test=Star.sEncyMy(sMAC + Star.sCadVerif + Star.sNomProd);
          
           
            System.out.println(test);
            try
            {
                sSer    = vGivMac(test);                                                
            }
            catch(Exception e)
            {                
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Se necesita conexión a internet para poder continuar con la instalación. Por favor revisa tu conexión para continuar. Error: " + e.getMessage(), "Instalación", JOptionPane.ERROR_MESSAGE, null); 
                return;
            }
            System.out.println("llego");
            /*Desencripta la serie recibida*/
            sSer    = Star.sDencyMy(sSer);
            
            /*Si hubo error entonces*/
            if(sSer.contains("<ERROR>"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Error del servidor: " + sSer, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Tokeniza la serie para obtener la serie correcta, la fecha de instalación y los días que mando el servidor como contador*/            
            java.util.StringTokenizer stk = new java.util.StringTokenizer(sSer, "|");
            sSer        = stk.nextToken();
            String sCont= stk.nextToken();
            String sFAlt= stk.nextToken();
            
            /*Instancia la clase de validación para serializarla*/               
            SerVali ser         = new SerVali();
            ser.sSer            = Star.sEncrip(sSer); 
            ser.sMac            = Star.sEncrip(sMAC); 
            ser.sFech           = Star.sEncrip(sFAlt);
            ser.sCont           = Star.sEncrip(sCont);
            ser.sKey            = Star.sEncrip("");

            /*Serializa el objeto de validación en un archivo*/
            try
            {
                FileOutputStream fi     = new FileOutputStream(Star.sArchVal);
                ObjectOutputStream out  = new ObjectOutputStream(fi);
                out.writeObject(ser);
                out.close();
                fi.close();
            }
            catch(IOException ex)
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
            }               
            /*Si no es estación de trabajo entonces*/
            if(Star.sEstacTrab.compareTo("0")==0)
            {               
                //Abre la base de datos                             
                Connection con = Star.conAbrBas(true, false, Star.sInstancia, Star.sPort, "test", Star.sUsuario, Star.sContrasenia);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;            
                String      sQ; 

                /*Comprueba si la base de datos servidora ya existe*/
                try
                {
                    sQ  = "SELECT * FROM information_schema.tables WHERE table_schema = '" + Star.sBD + "'";
                    st  = con.createStatement();
                    rs  = st.executeQuery(sQ);
                    /*Si hay datos entonces si existe*/
                    if(rs.next())
                    {          
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "Ya existe la base de datos servidora, solo puedes conectar como usuario de trabajo.", "Conexión", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        return;
                    }
                    /*Else, no existe entonces crea la base de datos remota si no existe*/
                    else
                        Star.vCreElecs(Star.sBD, con);
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y sal del sistema
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                    
                    System.exit(1);
                }                

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                {
                    //Función para borrar la base de datos servidora y regresa
                    vDelBD      (Star.sBD);                
                    return;
                }

                //Abre la base de datos                             
                con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Crea la base de datos remota si no existe*/
                Star.vCreElecs(Star.sBD + "_tmp", con);

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                {
                    //Función para borrar la base de datos servidora y la local y regresa
                    vDelBD      (Star.sBD);
                    vDelBDLoc   (Star.sBD + "_tmp");
                    return;
                }
                
                /*25 06 2015 Heriberto Daniel Sanchez Peña*/
                /*Coloca una bandera para indicar si sera un servidor o sera una estacion de trabajo*/
                /*Si es una estacion de trabajo tambien indica si sera en versión on-line u off-line*/
                
                if(jCEstaT.isSelected())
                {
                    Star.bEstacTrab = 1;
                    if(rbOff.isSelected())
                        Star.tTipoDeEsta=1;
                }
                else
                {
                    Star.bEstacTrab = 0; 
                }
        
                
                /*Crea toda la estructura de la base de datos servidora por medio de esta forma*/
                LoadinBD lo     = new LoadinBD("Iniciando creación de estructura de base de datos servidora...", "Creando estructura de base de datos servidora...", Star.sBD, Star.sInstancia, Star.sUsuario, Star.sContrasenia, Star.sPort);                
                lo.setVisible   (true);                                                         

                /*Crea toda la estructura de la base de datos local por medio de esta forma*/
                lo              = new LoadinBD("Iniciando creación de estructura de base de datos local...", "Creando estructura de base de datos local...", Star.sBD + "_tmp", Star.sInstLoc, Star.sUsrLoc, Star.sContraLoc, Star.sPort);
                lo.setVisible   (true);

            }/*Fin de if(Star.sEstacTrab.compareTo("0")==0)*/
            
        }/*Fin de if(!bSal)*/                    
        
        /*Encripta la instancia*/        
        Star.sInstancia     = Star.sEncrip(Star.sInstancia);
        
        /*Encripta el usuario*/        
        Star.sUsuario       = Star.sEncrip(Star.sUsuario);
        
        /*Encripta la contraseña*/        
        Star.sContrasenia   = Star.sEncrip(Star.sContrasenia);
        
        /*Encripta la base de datos*/        
        Star.sBD            = Star.sEncrip(Star.sBD);
        
        /*Encripta el puerto*/        
        Star.sPort          = Star.sEncrip(Star.sPort);
        
        /*Encripta la sucursal*/        
        Star.sSucu          = Star.sEncrip(Star.sSucu);
        
        /*Encripta el nùmero de caja*/        
        Star.sNoCaj         = Star.sEncrip(Star.sNoCaj);
        
        /*Encripta el nombre de la empresa*/        
        Star.sNombreEmpresa = Star.sEncrip(Star.sNombreEmpresa);
        
        /*Encripta si es usuario de trabajo o no*/        
        Star.sEstacTrab     = Star.sEncrip(Star.sEstacTrab);

        /*Encripta los valores de la conexión local*/
        Star.sContraLoc     = Star.sEncrip(Star.sContraLoc);
        Star.sUsrLoc        = Star.sEncrip(Star.sUsrLoc);
        Star.sInstLoc       = Star.sEncrip(Star.sInstLoc);        
        Star.sPortLoc       = Star.sEncrip(Star.sPortLoc);
        
        /*Instancia la clase de la base de datos para serializarla*/               
        BDConf bdConf       = new BDConf();
        bdConf.sInst        = Star.sInstancia;
        bdConf.sUsr         = Star.sUsuario;
        bdConf.sContra      = Star.sContrasenia;
        bdConf.sBD          = Star.sBD;
        bdConf.sPort        = Star.sPort;
        bdConf.sSucur       = Star.sSucu;
        bdConf.sNumCaj      = Star.sNoCaj;
        bdConf.sNombEmp     = Star.sNombreEmpresa;
        bdConf.sEstacTrab   = Star.sEstacTrab;
        bdConf.sInstLoc     = Star.sInstLoc;
        bdConf.sUsrLoc      = Star.sUsrLoc;
        bdConf.sContraLoc   = Star.sContraLoc;
        bdConf.sPortLoc     = Star.sPortLoc;
        
        /*Si el archivo de configuración existe entonces*/
        if(new File(Star.sArchConf).exists())
        {
           /*Intenta borrar el archivo, si no es posible entonces*/ 
           if(!new File(Star.sArchConf).delete())
           {
               /*Mensajea y regresa*/
               JOptionPane.showMessageDialog(null, "No se pudo borrar el archivo existente de configuración.", "Archivo de Configuración", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
               return;
           }
        }
        
        /*Serializa el objeto en un archivo*/
        try
        {
            FileOutputStream fi;
            fi              = new FileOutputStream(Star.sArchConf);
            ObjectOutputStream out;
            out             = new ObjectOutputStream(fi);
            out.writeObject(bdConf);
            out.close();
            fi.close();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;
        }   
        
        /*Si es usuario de trabajo entonces marca la bandera del otro formulario*/
       
        //Si el usuario entro desde la forma principal entonces
        if(!bSal)
        {
            //Mensajea 
            JOptionPane.showMessageDialog(null, "La aplicación se va a cerrar.", "Aplicación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                        
            //Inicia los vaores de la conexión orignal para hacer la salida
            Star.sInstancia     = sInstOri;
            Star.sPort          = sPortOri;
            Star.sContrasenia   = sContraOri;
            Star.sBD            = sBDOri;
            Star.sUsuario       = sUsrOri;
            
            /*Termina el thread del socket TCP servidor*/
            if(Star.thServ!=null)
                Star.thServ.interrupt();

            /*Termina el thread del socket UDP servidor*/
            if(Star.thServUDP!=null)
                Star.thServUDP.interrupt();

            //Registra el deslog del actual usuario
            if(Star.iRegUsr(null, "1")==-1)
                return;

            //Quita el registro del usuario en la tabla de logestac
            if(Star.iDesLog()==-1)
                return;  
            
            //Sal de la aplicación
            System.exit(0);
        }
            
        /*Cierra el formulario*/
        this.dispose();                        
        
        /*Si se abrió desde adentro del sistma entonces muestra el mensaje de éxito en la serialización del archivo*/
        if(!bSal)        
            JOptionPane.showMessageDialog(null, "Exito en la creación del nuevo archivo de configuración.", "Archivo de Configuración", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));         
                
    }//GEN-LAST:event_jBGuaActionPerformed
      

    /*Función para borrar la base de datos principal*/
    public static void vDelBD(String sBD)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        /*Borra la base de datos*/
        try
        {                       
            sQ = "DROP DATABASE " + sBD;                                                
            st = con.createStatement();
            st.executeUpdate(sQ);            
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y sal del sistema
            Star.iErrProc(BDCon.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            System.exit(1);
         }        
            
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de void vDelBD(String sBD)*/            
    
    
    /*Función para borrar la base de datos local*/
    public static void vDelBDLoc(String sBD)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        /*Borra la base de datos*/
        try
        {                       
            sQ = "DROP DATABASE " + sBD;                                                
            st = con.createStatement();
            st.executeUpdate(sQ);            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y sal del sistema
            Star.iErrProc(BDCon.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            System.exit(1);
        }        
            
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de void vDelBDLoc(String sBD)*/            
    
    
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

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usuario si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir Aplicación", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;   
        
        /*Si se tiene que salir de la aplicación entonces sal*/
        if(bSal)                            
            System.exit(0);            
        /*Else, solo sal de la forma*/
        else            
            dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTInstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTInstKeyPressed
            
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTInstKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de línea*/
    private void jTInstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTInst.setSelectionStart(0);        
        jTInst.setSelectionEnd(jTInst.getText().length());        
        
    }//GEN-LAST:event_jTInstFocusGained

    
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jTBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBDKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);        
        jTUsr.setSelectionEnd(jTUsr.getText().length());        
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de base de datos*/
    private void jTBDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBD.setSelectionStart(0);        
        jTBD.setSelectionEnd(jTBD.getText().length());        
        
    }//GEN-LAST:event_jTBDFocusGained

    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jPContKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de contraseña*/
    private void jPContFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPCont.setSelectionStart(0);        
        jPCont.setSelectionEnd(jPCont.getText().length());        
        
    }//GEN-LAST:event_jPContFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del nombre de la empresa*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);        
        jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el camo del nombre de la empresa*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

            
    /*Cuando se tipea una tecla en el campo de nombre de la empresa*/
    private void jTNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
        /* 29 05 2015 Felipe Ruiz Garcia / Carlos Ramirez Ramirez */
        /* No permite la inserccion de los simbolos | ¬  \b */
        if((evt.getKeyChar() != '\b') &&(evt.getKeyChar() != '|') && (evt.getKeyChar() != '¬'))         
        {}
        else
            evt.consume();
        
    }//GEN-LAST:event_jTNomKeyTyped

    
    /*Cuando se tipea una tecla en el campo de base de datos*/
    private void jTBDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTBDKeyTyped

    
    /*Cuando se presiona una tecla en el botón de probar conexión*/
    private void jBProbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbKeyPressed

    
    /*Cuando se presiona el botón de probar conexión*/
    private void jBProbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbActionPerformed
        // 13 Julio 2015 Felipe Ruiz Garcia
        // Creacion de la base de datos test
        // Metodo crea table test
        Star.vCreaBDTest(jTInstLoc.getText().trim(),  jTPort.getText().trim(), jTUsr.getText().trim(), jPCont.getText().trim());
        
        /*Si el campo de instancia esta vacio no puede seguir*/
        if(jTInst.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de instancia esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTInst.grabFocus();           
            return;
        }
        
        /*Si el campo de usuario esta vacio no puede seguir*/
        if(jTUsr.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }
        
        /*Si el puerto esta vacio entonces*/
        if(jTPort.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPort.grabFocus();           
            return;
        }
        
        /*Si el checkbox de usuario de trabajo esta marcado entonces*/
        String sBD;
        if(jCEstaT.isSelected())
        {
            /*Lee la base de datos*/
            sBD         = jTBD.getText();

            /*Si el campo de bd esta vacio no puede seguir*/
            if(sBD.compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo de base de datos esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTBD.grabFocus();               
                return;
            }
                        
            /*Abre la base de datos*/        
            try 
            {
                conn = DriverManager.getConnection("jdbc:mysql://" + jTInst.getText().trim() + ":" + jTPort.getText().trim() + "/" + sBD + "?user=" + jTUsr.getText().trim() + "&password=" + new String(jPCont.getPassword()).trim() );               
            } 
            catch(SQLException expnSQL) 
            {    
                //Procesa el error y regresa
                Star.iErrProc(BDCon.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), conn);                                
                return;
            }                

            //Cierra la base de datos
            if(Star.iCierrBas(conn)==-1)
                return;
            
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Conexión exitosa desde términal a la base de datos central.", "Conexión", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
        }/*Fin de if(jCheckBoxEstacionT.isSelected())*/   
        else
        {
            /*Abre la base de datos*/        
            try 
            {
                conn = DriverManager.getConnection("jdbc:mysql://" + jTInst.getText().trim() + ":" + jTPort.getText().trim() + "/test?user=" + jTUsr.getText().trim() + "&password=" + new String(jPCont.getPassword()).trim() );               
            } 
            catch(SQLException expnSQL) 
            {    
                //Procesa el error y regresa
                Star.iErrProc(BDCon.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), conn);                                
                return;
            }                

            //Cierra la base de datos
            if(Star.iCierrBas(conn)==-1)
                return;
            
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Conexión exitosa al servidor.", "Conexión", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        }/*Fin de else*/                                   
        
    }//GEN-LAST:event_jBProbActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de usuario de trabajo*/
    private void jCEstaTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEstaTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCEstaTKeyPressed

    
    /*Cuando sucede una acción en el checkbox de usuario de trabajo*/
    private void jCEstaTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCEstaTActionPerformed
        
        /*Si esta marcado entonces deshabilita algunos controles*/
        if(jCEstaT.isSelected())
        {
            jTNom.setEnabled(false);
            rbOn.setEnabled(true);
            rbOff.setEnabled(true);
            rbOn.setSelected(true);
        }
        else
        {
           jTNom.setEnabled(true); 
           rbOn.setEnabled(false);
           rbOff.setEnabled(false);
           rbOn.setSelected(false);
           rbOff.setSelected(false);
           jTNom.setToolTipText("");
        }
            
        
    }//GEN-LAST:event_jCEstaTActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar contraseña*/
    private void jCMosCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMosCKeyPressed

    
    /*Cuando sucede una acción en el checkbox de mostrar contraseña*/
    private void jCMosCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMosCActionPerformed
        
        /*Si esta marcado entonces muestra la contraseña remota y la local*/
        if(jCMosC.isSelected())
        {
            jPCont.setEchoChar((char)0);
            jPContLoc.setEchoChar((char)0);
        }
        /*Else, ocultalas*/
        else
        {
            jPCont.setEchoChar('*');
            jPContLoc.setEchoChar('*');
        }
                
    }//GEN-LAST:event_jCMosCActionPerformed
    
       
    /*Cuando se gana el foco del teclado en el campo de nombre de sucursal*/
    private void jTSucFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSuc.setSelectionStart(0);        
        jTSuc.setSelectionEnd(jTSuc.getText().length());        
        
    }//GEN-LAST:event_jTSucFocusGained

    
    /*Cuando se ganae l foco del teclado en el campo del nùmero de caja*/
    private void jTNoCajFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoCajFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoCaj.setSelectionStart(0);        
        jTNoCaj.setSelectionEnd(jTNoCaj.getText().length());        
        
    }//GEN-LAST:event_jTNoCajFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre de la sucursal*/
    private void jTSucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSucKeyPressed

    
    /*Cuando se presiona una tecla en el campo del nùmero de caja*/
    private void jTNoCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoCajKeyPressed

    
    /*Cuando se tipea una tecla en el control de la sucursal*/
    private void jTSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSucKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTSucKeyTyped

    
    /*Cuando se tipea una tecla en el campo del nùmero de caja*/
    private void jTNoCajKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoCajKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoCajKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la instancia local*/
    private void jTInstLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstLocFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTInstLoc.setSelectionStart(0);        
        jTInstLoc.setSelectionEnd(jTInstLoc.getText().length());        
        
    }//GEN-LAST:event_jTInstLocFocusGained
   
    
    /*Cuando se presiona una tecla en el campo de la instancia*/
    private void jTInstLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTInstLocKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jTInstLocKeyPressed

    
    /*Cuando se gan el foco del teclado en el campo del usuario local*/
    private void jTUsrLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrLocFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUsrLoc.setSelectionStart(0);        
        jTUsrLoc.setSelectionEnd(jTUsrLoc.getText().length());        
        
    }//GEN-LAST:event_jTUsrLocFocusGained

       
    /*Cuando se presiona una tecla en el campo de usuario local*/
    private void jTUsrLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrLocKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jTUsrLocKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la contraseña local*/
    private void jPContLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContLocFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jPContLoc.setSelectionStart(0);        
        jPContLoc.setSelectionEnd(jPContLoc.getText().length());        
        
    }//GEN-LAST:event_jPContLocFocusGained
   
    
    /*Cuando se presiona una tecla en el campo de la contraseña local*/
    private void jPContLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContLocKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContLocKeyPressed

    
    /*Cuando se presiona el botón de probar conexión local*/
    private void jBProbLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbLocActionPerformed
        // 13 Julio 2015 Felipe Ruiz Garcia
        // Creacion de la base de datos test
        // Metodo crea table test
        Star.vCreaBDTest(jTInstLoc.getText().trim(),  jTPort.getText().trim(), jTUsr.getText().trim(), jPCont.getText().trim());
        
        /*Si el campo de instancia esta vacio no puede seguir*/
        if(jTInstLoc.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de instancia esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTInstLoc.grabFocus();
            return;
        }

        /*Si el campo de usuario esta vacio no puede seguir*/
        if(jTUsrLoc.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsrLoc.grabFocus();
            return;
        }

        /*Si el campo de puerto esta vacio no puede seguir*/
        if(jTPortLoc.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de puerto local esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPortLoc.grabFocus();           
            return;
        }
        
        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + jTInstLoc.getText().trim() + ":" + jTPort.getText().trim() + "/test?user=" + jTUsrLoc.getText().trim() + "&password=" + new String(jPContLoc.getPassword()).trim());            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(BDCon.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Conexión exitosa al servidor local.", "Conexión Local", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBProbLocActionPerformed

    
    /*Cuando se presiona una tecla en el botón de probar conexión local*/    
    private void jBProbLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbLocKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jBProbLocKeyPressed

    
    /*Cuando se mueve el ratón en el dialogo*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando el mouse es arrrastrado en el diálogo*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando la rueda del ratón es movida en el diálogo*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
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

    
    /*Cuando se pierde el foco del teclado en el control del nombre de la empresa*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTNom.getText().compareTo("")!=0)
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se pierde el foco del teclado en el control de la instancia*/
    private void jTInstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstFocusLost

        /*Coloca el cursor al principio del control*/
        jTInst.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTInst.getText().compareTo("")!=0)
            jTInst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTInstFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el control de la contraseña */
    private void jPContFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContFocusLost

        /*Coloca el cursor al principio del control*/
        jPCont.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPCont.getText().compareTo("")!=0)
            jPCont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jPContFocusLost

    
    /*Cuando se pierde el foco del teclado en el control de la base de datos*/
    private void jTBDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusLost

        /*Coloca el cursor al principio del control*/
        jTBD.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBD.getText().compareTo("")!=0){
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
            
            /* Felipe Ruiz Garcia 29 05 2015 */
            /* Valida que el nombre de la base de datos no comienze con un numero y no contenga caracteres especiales */
            
        String r =  jTBD.getText();
        
        if( !analizaInicio(r)){
    
            /* mensaje de alerta si el nombre de la base de datos inicia con numero */
            JOptionPane.showMessageDialog(null, "El nombre base de datos no puede iniciar con números, operadores o caracteres especiales.\n\tPor favor ingrese un nombre valido.", "Base de datos invalida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
           jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el campo*/
            jTBD.grabFocus();    
            }
        /*ELSE IF para que muestre un error por intento, y no los dos de una sola vez */
        else if(!analizaToda(r)){
            
            /* mensaje de alerta si el nombre de la base de datos contiene caracteres especiales no permitidos*/
            JOptionPane.showMessageDialog(null, "El nombre base de datos no puede contener operadores o caracteres especiales.\n\tPor favor ingrese un nombre valido.", "Base de datos invalida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
             /*Coloca el borde rojo*/                               
           jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el campo*/
            jTBD.grabFocus();    
            
        }
        }
    }//GEN-LAST:event_jTBDFocusLost

    /*29 05 2015 Felipe Ruiz Garcia */
    /*Esta funcion recibe una cadena y analiza si el primer caracter es digito, operador, o caracter especial */
    /*regresa true si si comienza con letra... *//* de lo contrario regresa false */
    
    boolean analizaInicio(String cadena){
        
        /* guardamos el primer caracter de la cadena */
        char x = cadena.charAt(0);
        
        /* retornamos false si no es letra */
        return Character.isLetter(x);
    }
    
    /*29 05 2015 Felipe Ruiz Garcia */
    /*Esta funcion recibe una cadena y analiza que todos sus caracteres sean letras y numero */
    /*regresa true si solo contiene letras y numeros ... *//* de lo contrario regresa false */
    
    boolean analizaToda(String cadena){
        
        /*analizamos toda la cadena*/
        for( char x : cadena.toCharArray()){
            if(!Character.isDigit(x) && !Character.isLetter(x)){
                return false;
            }
        }
        return true;
    }
    
    /*Cuando se pierde el foco del teclado en el control de sucursal*/
    private void jTSucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSucFocusLost

        /*Coloca el cursor al principio del control*/
        jTSuc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTSuc.getText().compareTo("")!=0)
            jTSuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTSucFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del número de caja*/
    private void jTNoCajFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoCajFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoCaj.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNoCaj.getText().compareTo("")!=0)
            jTNoCaj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTNoCajFocusLost

    
    /*Cuando se pierde el foco del teclado en el control de instancia local*/
    private void jTInstLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTInstLocFocusLost

        /*Coloca el cursor al principio del control*/
        jTInstLoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTInstLoc.getText().compareTo("")!=0)
            jTInstLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTInstLocFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del usuario local*/
    private void jTUsrLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrLocFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsrLoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsrLoc.getText().compareTo("")!=0)
            jTUsrLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrLocFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del a contraseña local*/
    private void jPContLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContLocFocusLost

        /*Coloca el cursor al principio del control*/
        jPContLoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPContLoc.getText().compareTo("")!=0)
            jPContLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jPContLocFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProb.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbLocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbLocMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbLoc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbLocMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProb.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbLocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbLocMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProbLoc.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbLocMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se gana el foco del teclado en el campo del puerto*/
    private void jTPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPort.setSelectionStart(0);        
        jTPort.setSelectionEnd(jTPort.getText().length());        
        
    }//GEN-LAST:event_jTPortFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del puerto*/
    private void jTPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortFocusLost
                
        /*Coloca el cursor al principio del control*/
        jTPort.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPort.getText().compareTo("")!=0)
            jTPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTPortFocusLost

    
    /*Cuando se presiona una tecla en el campo del puerto*/
    private void jTPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPortKeyPressed

    
    /*Cuando se tipea una tecla en el campo del puerto*/
    private void jTPortKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTPortKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del puerto local*/
    private void jTPortLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortLocFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPortLoc.setSelectionStart(0);        
        jTPortLoc.setSelectionEnd(jTPortLoc.getText().length());        
        
    }//GEN-LAST:event_jTPortLocFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del puerto local*/
    private void jTPortLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortLocFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTPortLoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPortLoc.getText().compareTo("")!=0)
            jTPortLoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTPortLocFocusLost

    
    /*Cuando se presiona una tecla en el campo del puerto local*/
    private void jTPortLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortLocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPortLocKeyPressed

    
    /*Cuando se tipea una tecla en el campo del puerto local*/
    private void jTPortLocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortLocKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();
        
    }//GEN-LAST:event_jTPortLocKeyTyped

    private void textEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textEmailFocusGained
        
    }//GEN-LAST:event_textEmailFocusGained

    private void textEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textEmailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailFocusLost

    private void textEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailKeyPressed

    private void textEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailKeyTyped

    private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEmailActionPerformed
        
        
    }//GEN-LAST:event_textEmailActionPerformed

    private void jTNoCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTNoCajActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTNoCajActionPerformed

    private void textEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textEmailMouseClicked
    
       
    }//GEN-LAST:event_textEmailMouseClicked

    private void jTBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTBDMouseClicked


    }//GEN-LAST:event_jTBDMouseClicked

    private void jTBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBDActionPerformed

    private void jTNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTNomActionPerformed

    private void rbOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbOffActionPerformed
        if(rbOff.isSelected())
        {
            jTNom.setEnabled(true);
            jTNom.setToolTipText("El nombre de la empresa debe de ser el mismo que en el servidor");
            rbOn.setSelected(false);
            
        }
        else
        {
            rbOff.setSelected(true);
        }
    }//GEN-LAST:event_rbOffActionPerformed

    private void rbOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbOnActionPerformed
       if(rbOn.isSelected())
       {
           jTNom.setEnabled(false);
           rbOff.setSelected(false);
           jTNom.setToolTipText("");
       }
       else
       {
           rbOn.setSelected(true);
       }
    }//GEN-LAST:event_rbOnActionPerformed

    private void rbOnPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_rbOnPropertyChange
        // TODO add your handling code here:
    
    }//GEN-LAST:event_rbOnPropertyChange
        
           
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
        /*Si se presiona F2 presiona el botón de probar conexión*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBProb.doClick();
        /*Si se presiona F3 presiona el botón de probar conexión local*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBProbLoc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
        {
            /*Si el checkbox de mostrar contraseña esta marcado entonces*/
            if(jCMosC.isSelected())
            {
                /*Desmarcalo y muestra la contraseña*/
                jCMosC.setSelected(false);
                jPCont.setEchoChar('*');
                jPContLoc.setEchoChar('*');                
            }
            else
            {
                /*Marcalo y muestra la contraseña*/
                jCMosC.setSelected(true);
                jPCont.setEchoChar((char)0);                
                jPContLoc.setEchoChar((char)0);                                
            }                        
        }                           
        /*Si se presiona F5*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
        {
            /*Si el checkbox de usuario de trabajo esta marcado entonces*/
            if(jCEstaT.isSelected())
            {
                /*Desmarcalo*/
                jCEstaT.setSelected(false);                
                
                /*Habilita el control del nombre de la empresa*/
                jTNom.setEnabled(true);
            }
            else
            {
                /*Marcalo*/
                jCEstaT.setSelected(true);                
                
                /*Deshabilita el control del nombre de la empresa*/
                jTNom.setEnabled(false);
            }            
            
        }/*Fin de else if(evt.getKeyCode() == KeyEvent.VK_F4)*/
        /*Si se presiona F5 presiona el botón de probar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBProb.doClick();
                
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    

    /*Método que manda al web service la MAC para obtener el serial y los días transcurridos*/
    private static String givmac(java.lang.String mac) 
    {
        princip.ServLic_Service service = new princip.ServLic_Service();
        princip.ServLic port = service.getServLicPort();
        return port.givmac(mac);
    }
   
    
    /*Método que manda al web service la MAC para obtener el serial y los días transcurridos*/
    private static String vGivMac(java.lang.String sMacP) 
    {
        erws.ERWSServicio service = new erws.ERWSServicio();
        erws.ERWSPort port = service.getERWSPort();
        return port.vGivMac(sMacP);
    }
   
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBProb;
    private javax.swing.JButton jBProbLoc;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCEstaT;
    private javax.swing.JCheckBox jCMosC;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPCont;
    private javax.swing.JPasswordField jPContLoc;
    private javax.swing.JTextField jTBD;
    private javax.swing.JTextField jTInst;
    private javax.swing.JTextField jTInstLoc;
    private javax.swing.JTextField jTNoCaj;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPort;
    private javax.swing.JTextField jTPortLoc;
    private javax.swing.JTextField jTSuc;
    private javax.swing.JTextField jTUsr;
    private javax.swing.JTextField jTUsrLoc;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JRadioButton rbOff;
    private javax.swing.JRadioButton rbOn;
    private javax.swing.JTextField textEmail;
    // End of variables declaration//GEN-END:variables
                
}/*Fin de public class Clientes extends javax.swing.JFrame */