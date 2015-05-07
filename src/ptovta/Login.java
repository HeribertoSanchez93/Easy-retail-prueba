//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;




/*Clase de login para el sistema*/
public class Login extends JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    //Contiene el contador de intentos de ingreso al sistema
    int iContEnt                    = 0;
            
    /*Para validar que la forma no se abra dos veces*/
    private static Login            obj = null;
    
    /*Declara variables globales*/
    public static String            sUsrG;
    public static String            sFLog;
    public static String            sEmpBD;
    public static String            sCodEmpBD;
    
    /*Esta variable determina si se debe de cerrar la forma o salir de la aplicación*/
    private final boolean           bCierr;
        
    
    
    
    
    /*Consructor sin argumentos*/
    public Login(boolean bCie) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();                                                                                                                                                                 
                    
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Al seleccionar los botones van a tener este color*/
        UIManager.put("Button.select", new java.awt.Color(153,255,204));                
        
        /*Para que el enter este en cada botón que se presiona*/
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Pon el foco del teclado en el campo de usuario*/
        jTEmp.grabFocus();              
        
        /*Obtiene los parámetros del otro formulario*/
        bCierr = bCie;
        
        /*Inicialmente el código de la empresa no será visible*/
        jTCodEmp.setVisible(false);
        
    }/*Fin de public Login() */

    
    /*Función estatica para escribir en el fichero log de la aplicación*/
    public static void vLog(String sErr)
    {
        //Declara variables locales
        String sFil         = "log.txt";
        
        
        
        
        /*Si el archivo log no existe entonces*/
        if(!new File(sFil).exists())
        {
            /*Crealo*/
            try
            {
                new File(sFil).createNewFile();
            }   
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(Login.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                
                return;                        
            }                        
        
            /*Obtiene la fecha actual*/
            DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dat        = new Date();

            /*Escribe en el el log*/
            try(BufferedWriter ou = new BufferedWriter(new FileWriter(sFil)))
            {                
                ou.write(da.format(dat));
                ou.newLine();
                ou.write(sErr);
                ou.newLine();
                ou.newLine();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(Login.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }            
        }
        /*Else, el archivo existe entonces*/
        else
        {
             /*Obtiene la fecha actual*/
            DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dat        = new Date();
            
            /*Agrega en el el log*/
            try(BufferedWriter ou = new BufferedWriter(new FileWriter(sFil, true)))
            {                
                ou.append(da.format(dat));
                ou.newLine();
                ou.append(sErr);
                ou.newLine();
                ou.newLine();                
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(Login.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                
            }            
        }
                
    }/*Fin de public static void vLog(String sErr)*/
    
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static Login getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Login(false);
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Login getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBIng = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jPContra = new javax.swing.JPasswordField();
        jTEmp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTEsta = new javax.swing.JTextField();
        jBEmp = new javax.swing.JButton();
        jTCodEmp = new javax.swing.JTextField();
        jCMostC = new javax.swing.JCheckBox();
        jLAyu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Inicio de Sesión");
        setResizable(false);
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

        jBIng.setBackground(new java.awt.Color(255, 255, 255));
        jBIng.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBIng.setForeground(new java.awt.Color(0, 102, 0));
        jBIng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ent.png"))); // NOI18N
        jBIng.setText("Entrar");
        jBIng.setToolTipText("Entrar al Sistema (ENTER)");
        jBIng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBIng.setNextFocusableComponent(jBSal);
        jBIng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBIngMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBIngMouseExited(evt);
            }
        });
        jBIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIngActionPerformed(evt);
            }
        });
        jBIng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBIngKeyPressed(evt);
            }
        });
        jP1.add(jBIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 180, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBSal.setNextFocusableComponent(jTEmp);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 110, 30));

        jPContra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPContra.setNextFocusableComponent(jCMostC);
        jPContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPContraFocusLost(evt);
            }
        });
        jPContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPContraKeyPressed(evt);
            }
        });
        jP1.add(jPContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 230, 20));

        jTEmp.setEditable(false);
        jTEmp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEmp.setFocusable(false);
        jTEmp.setNextFocusableComponent(jBEmp);
        jTEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEmpFocusLost(evt);
            }
        });
        jTEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTEmpMouseClicked(evt);
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
        jP1.add(jTEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 230, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contraseña:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 230, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Empresa:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 230, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 230, -1));

        jTEsta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTEsta.setText("SUP");
        jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEsta.setNextFocusableComponent(jPContra);
        jTEsta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstaFocusLost(evt);
            }
        });
        jTEsta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstaKeyTyped(evt);
            }
        });
        jP1.add(jTEsta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 230, 20));

        jBEmp.setBackground(new java.awt.Color(255, 255, 255));
        jBEmp.setText("...");
        jBEmp.setToolTipText("Buscar Empresa(s)");
        jBEmp.setNextFocusableComponent(jTEsta);
        jBEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jBEmpFocusLost(evt);
            }
        });
        jBEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEmpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEmpMouseExited(evt);
            }
        });
        jBEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEmpActionPerformed(evt);
            }
        });
        jBEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEmpKeyPressed(evt);
            }
        });
        jP1.add(jBEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 30, 20));

        jTCodEmp.setEditable(false);
        jTCodEmp.setFocusable(false);
        jP1.add(jTCodEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 30, -1));

        jCMostC.setBackground(new java.awt.Color(255, 255, 255));
        jCMostC.setText("Mostrar Contraseña F2");
        jCMostC.setNextFocusableComponent(jBIng);
        jCMostC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMostCActionPerformed(evt);
            }
        });
        jCMostC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMostCKeyPressed(evt);
            }
        });
        jP1.add(jCMostC, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 180, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 220, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    /*Cuando se presiona el botón de ingresar*/
    private void jBIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIngActionPerformed
        
        /*Si el nombre de la empresa es cadena vacia entonces*/
        if(jTEmp.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo el campo de la empresa*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La empresa esta vacia.", "Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el botón de búscar empresay regresa*/
            jBEmp.grabFocus();           
            return;
        }
        
        /*Lee el código que ingreso de la empresa*/
        sCodEmpBD  = jTCodEmp.getText();
        
        /*Comprueba si la empresa que introdujo el usuario existe*/
        if(sCodEmpBD.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La empresa: " + jTEmp.getText() + " no existe.", "Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo de contraseña y regresa*/
            jTEmp.grabFocus();           
            return;
        }
        
        /*Si no a escrito una usuario entonces*/
        if(jTEsta.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo y regresa*/
            jTEsta.grabFocus();           
            return;
        }
        
        //Comprueba si la configuración para multilogeo esta activada
        int iMulti  = Star.iGetMultiLog(null);
        
        //Si hubo error entonces regresa
        if(iMulti==-1)
            return;
        
        //Si esta activado el multilogeo entonces
        if(iMulti==0)
        {
            
            //Obtiene si el usuario esta actualmente logeado
            iMulti  = Star.iUsrCon(null, jTEsta.getText().trim());
            
            //Si el usuario actual ya esta logeado entonces
            if(iMulti==1)
            {
                //Mensajea y regresa
                JOptionPane.showMessageDialog(null, "El sistema esta configurado para no aceptar multilogeos.", "Multilogeo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }            
        }
        
        /*Si no a escrito un password entonces*/
        if(new String(jPContra.getPassword()).compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Password", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo y regresa*/
            jPContra.grabFocus();           
            return;
        }
        
        /*Contiene la clase del archivo de validación del sistema*/
        SerVali ser         = new SerVali();
        
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
        catch(UnknownHostException expnUnknowHos) 
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnUnknowHos.getMessage(), Star.sErrUnknowHos, expnUnknowHos.getStackTrace());            
            return;                                                
        }
        catch(SocketException expnSock)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSock.getMessage(), Star.sErrSock, expnSock.getStackTrace());            
            return;                        
        }

        /*Contador de intentos de validación con el web service*/
        int iCont   = 0;
                
        /*Si el archivo de validación del sistema no existe entonces*/
        if(!new File(Star.sArchVal).exists())
        {
            /*Mientras no se pueda salir del bucle entonces*/
            boolean bSa = false;
            do
            {
                /*Si el número de intentos ya llego a su límite entonces*/
                if(iCont>=2)
                {
                    /*Mensajea y sal de la aplicación*/
                    JOptionPane.showMessageDialog(null, "El número de intentos válidos a expirado. Se cerrará el sistema", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    System.exit(0);
                }
                
                /*Pidele al usuario que ingrese su llave nueva*/                
                String sKey     = JOptionPane.showInputDialog("El archvio de validación no existe. Ingresa tu código de revocación:");

                /*Si es nulo entonces regresa*/
                if(sKey==null)               
                    return;                

                /*Si es cadena vacia entonces*/
                if(sKey.compareTo("")==0)
                {                    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Ingresa un código válido.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                    /*Pon la bandera en falso y continua*/
                    bSa    = false;
                    continue;
                }
                
                /*Comprueba con el web service si los datos introducidos por el usuario son válidos*/
                String sResp;
                try
                {
                    sResp    = Star.vSerKeyU(Star.sEncyMy("" + Star.sCadVerif + Star.sNomProd), Star.sEncyMy(sKey), Star.sEncyMy(sMAC));
                }
                catch(Exception expnExcep)
                {                
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());                    
                    return;                        
                }                                                    
                
                /*Desencripta la respuesta del servidor*/
                sResp   = Star.sDencyMy(sResp);                                
                
                /*Si la respuesta fue negativa entonces*/
                if(sResp.contains("<ERROR>"))
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Error del servidor." + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    
                    /*Aumenta en uno el contador de intentos y continua*/
                    ++iCont;
                    continue;
                }
                
                /*Si la respuesta fue negativo entonces*/
                if(sResp.compareTo("0")==0)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Revocación inválida.", "Revocación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    
                    /*Aumenta en uno el contador de intentos y continua*/
                    ++iCont;
                    continue;
                }
                
                /*Tokeniza la respuesta del servidor para obtener los datos que mando*/
                java.util.StringTokenizer stk = new java.util.StringTokenizer(sResp, "|");
                
                /*Instancia la clase de validación para serializarla*/                               
                ser.sSer            = Star.sEncrip(stk.nextToken()); 
                ser.sFech           = Star.sEncrip(stk.nextToken());
                ser.sCont           = Star.sEncrip(stk.nextToken());
                ser.sKey            = Star.sEncrip(stk.nextToken());
                ser.sMac            = Star.sEncrip(sMAC);                                                                
                
                /*Serializa el objeto de validación en un archivo*/
                try(FileOutputStream fi     = new FileOutputStream(Star.sArchVal); ObjectOutputStream out  = new ObjectOutputStream(fi))
                {                    
                    out.writeObject(ser);                    
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                    
                    return;                        
                }                  
                
                /*Poner la bandera en true*/
                bSa        = true;                    
            }
            while(!bSa);                                   
                        
        }/*Fin de if(!new File(Star.sArchVal).exists())*/                

        /*Deserializamos el archivo de validación del sistema*/        
        try(FileInputStream fileIn   = new FileInputStream(Star.sArchVal); ObjectInputStream in     = new ObjectInputStream(fileIn))
        {           
           ser                      = (SerVali)in.readObject();           
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;                        
        } 
        catch(ClassNotFoundException expnClassNoF)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnClassNoF.getMessage(), Star.sErrClassNoF, expnClassNoF.getStackTrace());            
            return;                        
        }
        
        /*Si la MAC del equipo no es la misma que la del archivo de validación entonces*/
//        if(sMAC.replace("-", "").compareTo(Star.sDecryp(ser.sMac).replace("-", ""))!=0)
//        {
//            /*Mensajea y regresa*/
//            JOptionPane.showMessageDialog(null, "El sistema esta incorrectamente validado y no puede continuar.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//            return;            
//        }
        
        /*Obtiene los datos de validación del archivo ya desencriptados*/
        String sKey     = Star.sDecryp(ser.sKey);                
        String sSer     = Star.sDecryp(ser.sSer);                
        String sCont    = Star.sDecryp(ser.sCont);
        String sFech    = Star.sDecryp(ser.sFech);        
        
        /*Crea el objeto de fecha con la fecha de la instalación*/
        Date datDat;
        try
        {
            SimpleDateFormat sdf    = new SimpleDateFormat("yyyy/MM/dd");
            datDat                  = sdf.parse(sFech.replace("-", "/"));
        }
        catch(ParseException expnPARS)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace(), Star.sErrPARS);                                
            return;                        
        }                    
        
        /*Suma los días del contador a la fecha*/
        Calendar calCal     = Calendar.getInstance();
        calCal.setTime(datDat);
        calCal.add(Calendar.DATE, Integer.parseInt(sCont));
        datDat              = calCal.getTime();
        
        /*Obtiene la fecha del sistema*/        
        Date datDatSis      = new Date();
        
        /*Obtiene la diferencia entre las dos fechas en milisegundos*/
        long loDif          = datDatSis.getTime() - datDat.getTime();
        
        /*Obtiene la diferencia entre las dos fechas en días*/
        long lodDifDay      = loDif / (24 * 60 * 60 * 1000);                
                
        /*Si el contador de días no es menor al contador original entonces crea el nuevo contador*/
        if((lodDifDay + Long.parseLong(sCont)) > Long.parseLong(sCont))                    
            sCont               = Long.toString(lodDifDay + Long.parseLong(sCont));                
        
        /*Borra el archivo de validación*/
        new File(Star.sArchVal).delete();

        /*Instancia la clase de validación para serializarla con los nuevos datos encriptados*/                       
        ser.sSer            = Star.sEncrip(sSer); 
        ser.sMac            = Star.sEncrip(sMAC); 
        ser.sFech           = Star.sEncrip(sFech);
        ser.sCont           = Star.sEncrip(sCont);
        ser.sKey            = Star.sEncrip(sKey);
        
        /*Serializa el objeto de validación en un archivo*/
        try(FileOutputStream fi     = new FileOutputStream(Star.sArchVal); ObjectOutputStream out  = new ObjectOutputStream(fi);)
        {            
            out.writeObject(ser);            
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;                        
        }   
        
        /*Resetea el contador de intentos*/
        iCont   = 0;
        
//        /*Si la diferencia desde que se instalo hasta el día de hoy es mayor a 30 entonces o menor a 30 y si la llave es cadena vacia entonces*/
//        if(sKey.compareTo("")==0 && ((lodDifDay + Integer.parseInt(sCont))>30 || (lodDifDay + Long.parseLong(sCont)) < Long.parseLong(sCont)))
//        {
//            /*Mientras no se pueda salir del bucle entonces*/
//            boolean bSa = false;
//            do
//            {
//                /*Si el número de intentos ya llego a su límite entonces*/
//                if(iCont>=2)
//                {
//                    /*Mensajea y sal de la aplicación*/
//                    JOptionPane.showMessageDialog(null, "El número de intentos válidos a expirado. Se cerrará el sistema", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                    System.exit(0);
//                }
//                
//                /*Pidele al usuario que ingrese su llave nueva*/                
//                sKey    = JOptionPane.showInputDialog("El periodo de prueba de 30 días a concluido. Inserta tu llave o código de revocación:", sSer);
//
//                /*Si es nulo entonces regresa*/
//                if(sKey==null)               
//                    return;                
//
//                /*Si es cadena vacia entonces*/
//                if(sKey.compareTo("")==0)
//                {                    
//                    /*Mensajea*/
//                    JOptionPane.showMessageDialog(null, "Ingresa una llave o código de revocación válida.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
//
//                    /*Pon la bandera en falso y continua*/
//                    bSa    = false;
//                    continue;
//                }
//
//                /*Comprueba con el web service si los datos introducidos por el usuario son válidos*/
//                String sResp;
//                try
//                {
//                    sResp    = Star.vSerKeyU(Star.sEncyMy(sSer+ Star.sCadVerif + Star.sNomProd), Star.sEncyMy(sKey), Star.sEncyMy(sMAC));
//                }
//                catch(Exception expnExcep)
//                {                
//                    //Procesa el error y regresa
//                    Star.iErrProc(this.getClass().getName() + " " + expnExcep.getMessage(), Star.sErrExcep, expnExcep.getStackTrace());                                
//                    return;                                            
//                }                                                    
//                
//                /*Desencripta la respuesta del servidor*/
//                sResp   = Star.sDencyMy(sResp);
//                
//                /*Si la respuesta fue negativa entonces*/
//                if(sResp.contains("<ERROR>"))
//                {
//                    /*Mensajea*/
//                    JOptionPane.showMessageDialog(null, "Error del servidor." + sResp, "Error Servidor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                    
//                    /*Aumenta en uno el contador de intentos y continua*/
//                    ++iCont;
//                    continue;
//                }
//                
//                /*Si la respuesta fue negativo entonces*/
//                if(sResp.compareTo("0")==0)
//                {
//                    /*Mensajea*/
//                    JOptionPane.showMessageDialog(null, "Datos de validación incorrectos.", "Validación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//                    
//                    /*Aumenta en uno el contador de intentos y continua*/
//                    ++iCont;
//                    continue;
//                }
//                
//                /*Borra el archivo de validación*/
//                new File(Star.sArchVal).delete();
//
//                /*Instancia la clase de validación para serializarla*/                       
//                ser.sSer            = Star.sEncrip(sSer); 
//                ser.sMac            = Star.sEncrip(sMAC); 
//                ser.sFech           = Star.sEncrip(sFech);
//                ser.sCont           = Star.sEncrip(sCont);
//                ser.sKey            = Star.sEncrip(sKey);               
//            
//                /*Serializa el objeto de validación en un archivo*/
//                try(FileOutputStream fi     = new FileOutputStream(Star.sArchVal); ObjectOutputStream out  = new ObjectOutputStream(fi))
//                {                    
//                    out.writeObject(ser);                    
//                }
//                catch(IOException expnIO)
//                {
//                    //Procesa el error y regresa
//                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
//                    return;                        
//                }   
//
//                /*Poner la bandera en true*/
//                bSa        = true;                    
//            }
//            while(!bSa);                                   
//                    
//        }/*Fin de if((lodDifDay + Integer.parseInt(sCont))>30 || (lodDifDay + Integer.parseInt(sCont)) < Integer.parseInt(sCont))*/                                    
//                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene los datos de conexión a la base de datos en base a la empresa seleccionada y la cantidad de usuarios validos*/        
        if(Star.sEstacTrab  == "0")
        {
        try
        {
            sQ = "SELECT * FROM basdats WHERE codemp = '" + sCodEmpBD + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene los resultados
            if(rs.next())
            {                
                Star.sInstancia   = Star.sDecryp(rs.getString("serv"));   
                Star.sUsuario     = Star.sDecryp(rs.getString("usr"));   
                Star.sContrasenia = Star.sDecryp(rs.getString("pass"));   
                Star.sBD          = Star.sDecryp(rs.getString("bd"));                                                                                          
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }   
        }       
        //Abre la base de datos y prueba los nuevos datos de conexión de la empresa*/                 
        Connection con2 = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con2==null)
            return;
        
        /*Obtiene si se tiene que mostrar o no el punto de venta inicial*/
        boolean bPvta = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE conf = 'initpvta' AND clasif = 'vtas'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene el resultado y coloca la bandera correcta*/
                if(rs.getString("val").compareTo("1")==0)                                 
                    bPvta = true;                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
        
        /*Si no se tiene que mostrar el punto de venta entonces*/
        boolean bUsrPto = false;
        if(!bPvta)
        {
            /*Comprueba si el usuario es usuario de punto de venta*/        
            try
            {
                sQ = "SELECT ptovta FROM estacs WHERE estac = '" + jTEsta.getText().trim() + "'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {
                    /*Si es usuario de punto de venta entonces*/
                    if(rs.getString("ptovta").compareTo("1")==0)                                 
                    {
                        /*Coloca la bandera para que el punto de venta inicie y para saber que es usuario de punto de venta*/
                        bPvta   = true;
                        bUsrPto = true;
                    }
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                       
            }
            
        }/*Fin de if(!bPvta)*/                    
                
        /*Lee el el usuario que se escribió*/
        sUsrG           = jTEsta.getText();  
        
        /*Obtiene la fecha del sistema para registrar la hora en que se logeo*/
        DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dat        = new Date();
        sFLog           = da.format(dat);
        
        /*Lee la password que escribio el usuario*/
        String sClav    = new String(jPContra.getPassword());
        
        /*Define el nombre de la empresa globalmente*/
        Star.sNombreEmpresa   = jTEmp.getText();
        
        /*Consulta si el usuario que se ingreso existe en la base de datos, y si existe si la contraseña es correcta*/             
        boolean bSi = false;
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + sUsrG + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoa la bandera*/
            if(rs.next())
                bSi   = true;                                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }                                      
        
        /*Si no existe el usuario entonces mensajea y retorna*/
        if(!bSi)
        {
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                System.exit(0);                
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
                    
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos.", "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo del usuario y regresa*/
            jTEsta.grabFocus();                        
            return;            
        }
        
        /*Obtiene el password del usuario*/        
        String sClavOri = "";
        try
        {
            sQ = "SELECT pass FROM estacs WHERE estac = '" + sUsrG + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene la clave y desencriptala*/
            if(rs.next())
                sClavOri      = Star.sDecryp(rs.getString("pass"));                                                         
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

        /*Compara el password introducido por el usuario con la establecida en la base de datos, si no es entonces*/
        if(sClavOri.compareTo(sClav)!=0)
        {
            //Si ya se ingresarón el número máximo de intentos entonces
            if(iContEnt>3)
            {
                //Mensajea y sal del sistema
                JOptionPane.showMessageDialog(null, "Límite de intentos de ingreso alcanzado, el sistema se va a cerrar.", "Ingresar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                System.exit(0);                
            }
            
            //Aumenta el contador de intentos de ingreso            
            iContEnt   += 1;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Usuario/contraseña incorrectos." , "Datos incorrectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo de pass y regresa*/
            jPContra.grabFocus();                        
            return;            
        }
                
        /*Recorre todas las ventanas abiertas entonces cierralas*/
        java.awt.Window win[] = java.awt.Window.getWindows(); 
        for (Window win1 : win) 
            win1.dispose();
        
        /*Si se tiene que mostrar el punto de venta entonces*/
        if(bPvta)
        {                
            /*Muestra el punto de venta*/
            PtoVtaTou p = new PtoVtaTou(null);
            p.setVisible(true);
        }               
        
        /*Si no es usuario de punto de venta entonces*/
        if(!bUsrPto)
        {
            /*Muestra el gráfico principal*/
            Princip pr = new Princip();                
            pr.setVisible(true);                                                           
        }   
        //Else si es usuario de punto de venta entonces
        else
        {
            //Registra en el log la entrada del usuario
            Star.iRegUsr(null, "0");                   
            
            //Inicia los threads de los sockets 
            Star.vIniSocks();
        }
        
    }//GEN-LAST:event_jBIngActionPerformed
                
        
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si tiene que cerrar la forma nada mas entonces solo cierrala*/
        if(bCierr)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();        
            dispose();
        }
        /*Else cierra la aplicación*/
        else                
            System.exit(0);
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ingresar*/
    private void jBIngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBIngKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBIngKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de empresa*/
    private void jTEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyPressed

        /*Llama a la función para que lea la empresa correctamente*/
        vFLo();
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            Busc b = new Busc(this, jTEmp.getText(), 8, jTCodEmp, jTEmp, null, "", null);
            b.setVisible(true);
            
        }
        /*Else if el botón de enter se presiona entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            /*Si alguno de estos controles tiene el foco del teclado en entonces presona el botón de cargar*/
            if(jTEsta.hasFocus() || jPContra.hasFocus() || jCMostC.hasFocus())
                jBIng.doClick();
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else                    
            vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTEmpKeyPressed

    
    /*Cuando se presiona una tecla en el camo de edición de password*/
    private void jPContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContraKeyPressed

        //Declara variables locales
        String sPassword;
        
        
        
        
        /*Lee el password introducido por el usr*/
        sPassword = new String(jPContra.getPassword());
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(sPassword.length()> 255)
            jPContra.setText(sPassword.substring(0, 255));
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContraKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de empresa*/
    private void jTEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEmp.setSelectionStart(0);jTEmp.setSelectionEnd(jTEmp.getText().length());        
        
    }//GEN-LAST:event_jTEmpFocusGained

    
    /*Cuando se ganael foco del teclado en el campo de edición de password*/
    private void jPContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPContra.setSelectionStart(0);jPContra.setSelectionEnd(jPContra.getText().length());        
        
    }//GEN-LAST:event_jPContraFocusGained
        
    
        
    /*Función que procesa la perdida del foco del teclado*/
    private void vFLo()
    {        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Declara variables locales        
        String      sCodEmp     = "";        
        
        /*Obtiene el código de la empresa en base a su nom y si no existe activa la bandera*/
        boolean bSiExiste   = false;
        try
        {                  
            sQ = "SELECT codemp FROM basdats WHERE nom = '" + jTEmp.getText().trim() + "' LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera para saber que si existe esta empresa en las bases de datos*/
                bSiExiste   = true;
                
                /*Obtiene el código*/
                sCodEmp     = rs.getString("codemp");                                                                
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

        //Si el nombre de la empresa no existe entonces resetea el campo
        if(!bSiExiste)
            jTCodEmp.setText("");                                                
        //Else coloca el código de la empresa en el campo
        else
            jTCodEmp.setText(sCodEmp);        
        
    }/*Fin de private void vFLo()*/
        
        
    /*Cuando se presiona una tecla typed en el campo de empresa*/
    private void jTEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEmpKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de contraseña*/
    private void jPContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jPContra.setCaretPosition(0);
        
        /*Crea el borde negro y rojo*/
        javax.swing.border.Border bNeg = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,254));        
            
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPContra.getText().compareTo("")!=0)
            jPContra.setBorder(javax.swing.BorderFactory.createTitledBorder(bNeg));        
        
        /*Lee el password introducido por el usr*/
        String sPassword = new String(jPContra.getPassword());
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(sPassword.length()> 255)
            jPContra.setText(sPassword.substring(0, 255));
        
    }//GEN-LAST:event_jPContraFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTEstaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEsta.setSelectionStart(0);jTEsta.setSelectionEnd(jTEsta.getText().length());        
        jTEsta.setSelectionEnd(jTEsta.getText().length());
        
    }//GEN-LAST:event_jTEstaFocusGained
        
    
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTEstaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstaKeyPressed

        
    /*Cuando se tipea una tecla en el campo de usuario*/
    private void jTEstaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstaKeyTyped

    
    /*Cuando se presiona el botón de buscar coincidencia de empresas*/
    private void jBEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEmpActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTEmp.getText().trim(), 8, jTCodEmp, jTEmp, null, "", null);
        b.setVisible(true);
        
        /*Función que procesa la perdida del foco del teclado*/
        vFLo();
        
    }//GEN-LAST:event_jBEmpActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEmpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEmpKeyPressed

    
    /*Cuando sucede una acción en el checkbox de mostrar contraseña*/
    private void jCMostCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMostCActionPerformed

        /*Si esta marcado entonces muestra la contraseña*/
        if(jCMostC.isSelected())
            jPContra.setEchoChar((char)0);
        /*Else, ocultala*/
        else
            jPContra.setEchoChar('*');

    }//GEN-LAST:event_jCMostCActionPerformed

    
    /*Cuando se presiona un tecla en el campo de mostrar contraseña*/
    private void jCMostCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCMostCKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de empresa*/
    private void jTEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEmp.setCaretPosition(0);
        
        /*Crea el borde negro y rojo*/
        javax.swing.border.Border bNeg = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,254));        
            
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEmp.getText().compareTo("")!=0)
            jTEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(bNeg));        
        
        /*Función que procesa la perdida del foco del teclado*/
        vFLo();

    }//GEN-LAST:event_jTEmpFocusLost

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de El usuario*/
    private void jTEstaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEsta.setCaretPosition(0);
        
        /*Crea el borde negro y rojo*/
        javax.swing.border.Border bNeg = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,254));        
            
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEsta.getText().compareTo("")!=0)
            jTEsta.setBorder(javax.swing.BorderFactory.createTitledBorder(bNeg));        
        
    }//GEN-LAST:event_jTEstaFocusLost

    
    /*Cuando se hace clic en el control del nombre de la empresa*/
    private void jTEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTEmpMouseClicked
        
        /*Presiona el botón de búscar empresa*/
        jBEmp.doClick();
        
    }//GEN-LAST:event_jTEmpMouseClicked

    
    /*Cuando se pierde el foco del teclado en el botón de búscar empresa*/
    private void jBEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBEmpFocusLost
        
        /*Crea el borde negro y rojo*/
        javax.swing.border.Border bNeg = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,254));        
            
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEmp.getText().compareTo("")!=0)
            jTEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(bNeg));        
        
    }//GEN-LAST:event_jBEmpFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBIngMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBIngMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBIng.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBIngMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBIngMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBIngMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBIng.setBackground(colOri);
        
    }//GEN-LAST:event_jBIngMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse entra en el botón de búscar empresa*/
    private void jBEmpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEmpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEmp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEmpMouseEntered

    
    /*Cuando el mouse sale del botón de búscar empresa*/
    private void jBEmpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEmpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEmp.setBackground(colOri);
        
    }//GEN-LAST:event_jBEmpMouseExited
       
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona F2*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Si esta seleccionado entonces*/
            if(jCMostC.isSelected())            
            {
                /*Deseleccionalo y muestra el asterisco*/
                jCMostC.setSelected(false);
                jPContra.setEchoChar('*');
            }
            else
            {                
                /*Else, desmarcalo y muestra los caracteres*/
                jCMostC.setSelected(true);            
                jPContra.setEchoChar((char)0);
            }            
        }
    
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/

    
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jBEmp;
    private javax.swing.JButton jBIng;
    private javax.swing.JButton jBSal;
    private javax.swing.JCheckBox jCMostC;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPContra;
    private javax.swing.JTextField jTCodEmp;
    private javax.swing.JTextField jTEmp;
    private javax.swing.JTextField jTEsta;
    // End of variables declaration                                  
    
}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
