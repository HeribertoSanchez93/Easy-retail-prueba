//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static ptovta.Princip.bIdle;




/*Clase para administrar todo lo referente a los correos electrónicos*/
public class CorrElecs extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private int             iContFi;

    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean         bSel;
    
    /*Declara las variables originales de la tabla*/
    private String          sIDOri;
    private String          sEstacOri;
    private String          sNomOri;
    private String          sCoOri;
    private String          sCoAlterOri;
    
        
    
    /*Constructor sin argumentos*/
    public CorrElecs() 
    {                       
        
        /*Inicializa los componentes gráficos*/
        initComponents();

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el título de los tags*/
        jTabPan1.setTitleAt(0, "Correos");
        
        /*Establece el foco del teclado en el primer campo*/
        jTServSMTPSal.grabFocus();
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Correos electrónicos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                     
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en uno*/
        iContFi                  = 1;
        
        /*Establece el tamaño de las columnas de la tabla de correos*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(1);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(10);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nomre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Deshabilita el boton de guardar cambios ya que inicialmente no hay nada seleccionado en la tabla*/
        jBGuar.setEnabled(false);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
                
        /*Obtén todos los correos de la base de datos y cargalos en la tabla*/
        vCargCorre();                    
        
        /*Crea el listener para cuando se cambia de selección en la tabla de correos*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Si la fila no es válida entonces solo regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                                                               
                /*Obtiene el id de la fila*/
                String      sId     = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();   

                //Declara variables locales
                String      sServ;
                String      sPort;
                String      sUsr;
                String      sCoAlter;
                String      sEsta;                
                String      sContra;
                String      sSSL;
                                              
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Declara variables de la base de datos    
                Statement   st;
                String      sQ;
                ResultSet   rs;                
                
                /*Trae todos los datos del registro del id del correo*/
                try
                {
                    sQ = "SELECT * FROM corrselec WHERE id_id = '" + sId + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene el servidor smtp*/
                        sServ                   = rs.getString("srvsmtpsal");

                        /*Obtiene el puerto SMTP*/
                        sPort                   = rs.getString("portsmtp");                                 

                        /*Obtiene si es SSL*/
                        sSSL                    = rs.getString("actslenlog");                                                                                         

                        /*Obtiene el usr*/
                        sUsr                    = rs.getString("usr");                                 

                        /*Obtiene la contraseña*/
                        sContra                 = rs.getString("pass");   

                        /*Desencripta la clave del correo*/
                        sContra                 = Star.sDecryp(sContra);                                

                        /*Obtiene el correo alternativo*/
                        sCoAlter                = rs.getString("corralter");                                 

                        /*Obtiene El usuario*/
                        sEsta                   = rs.getString("estac");                                                                                                                                                                               

                        /*Coloca todos los asuntos en su lugar*/
                        jTAsunFac.setText               (rs.getString("asunfac"));                        
                        jTAsunFac.setCaretPosition      (0);
                        jTAsunCot.setText               (rs.getString("asuncot"));                        
                        jTAsunCot.setCaretPosition      (0);
                        jTAsunContra.setText            (rs.getString("asuncontra"));                        
                        jTAsunContra.setCaretPosition   (0);
                        jTAsunOrd.setText               (rs.getString("asunord"));                        
                        jTAsunOrd.setCaretPosition      (0);
                        jTAsunCXC1.setText              (rs.getString("asunrec1"));                        
                        jTAsunCXC1.setCaretPosition     (0);
                        jTAsunCXC2.setText              (rs.getString("asunrec2"));                        
                        jTAsunCXC2.setCaretPosition     (0);
                        jTAsunCXC3.setText              (rs.getString("asunrec3"));                        
                        jTAsunCXC3.setCaretPosition      (0);
                        
                        /*Coloca todos los cuerpos en su lugar*/
                        jTCuerFac.setText               (rs.getString("cuerpfac"));                        
                        jTCuerFac.setCaretPosition      (0);
                        jTCuerCot.setText               (rs.getString("cuerpcot"));                       
                        jTCuerCot.setCaretPosition      (0);
                        jTCuerContr.setText             (rs.getString("cuerpcontra"));                        
                        jTCuerContr.setCaretPosition    (0);
                        jTCuerOrd.setText               (rs.getString("cuerpord"));                                                
                        jTCuerOrd.setCaretPosition      (0);
                        jTCuerCXC1.setText              (rs.getString("cuerprec1"));                                                
                        jTCuerCXC1.setCaretPosition     (0);
                        jTCuerCXC2.setText              (rs.getString("cuerprec2"));                                                
                        jTCuerCXC2.setCaretPosition     (0);
                        jTCuerCXC3.setText              (rs.getString("cuerprec3"));                                                
                        jTCuerCXC3.setCaretPosition     (0);
                        
                        /*Establece El usuario en el control*/
                        jTEsta.setText                  (sEsta);                        
                        jTEsta.setCaretPosition         (0);
                        
                        /*Establece el control de servidor smtp*/                            
                        jTServSMTPSal.setText           (sServ);                        
                        jTServSMTPSal.setCaretPosition  (0);

                        /*Establece el control de puerto smtp*/
                        jTPortSMTP.setText              (sPort);                                                
                        jTPortSMTP.setCaretPosition     (0);

                        /*Marca el checkbox de SSL*/
                        if(sSSL.compareTo("1")==0)
                            jCActSSLLog.setSelected(true);
                        else
                            jCActSSLLog.setSelected(false);

                        /*Establece el control de usr*/
                        jTUsr.setText                   (sUsr);                        
                        jTUsr.setCaretPosition          (0);

                        /*Establece el control de contraseña*/
                        jPContra.setText                (sContra);                        
                        jPContra.setCaretPosition       (0);

                        /*Establece el control de correo alternativo*/
                        jTCorAlter.setText              (sCoAlter);                        
                        jTCorAlter.setCaretPosition     (0);

                        /*Establece El usuario en el combobox de estacs*/
                        jTEsta.grabFocus();

                        /*Establece el id en el control*/
                        jTID.setText                    (sId);                        
                        jTID.setCaretPosition           (0);

                        /*Habilita el boton de guardar cambios*/
                        jBGuar.setEnabled(true);

                    }/*Fin de if(rs.next())*/
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }            

                //Cierra la base de datos
                Star.iCierrBas(con);                
            }
        });
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para la tabla de correos, para cuando se modifique una celda guardarla en la base de datos el cambio*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                //Declara variables de la base de datos    
                Statement   st;
                String      sQ;
                Connection  con;
                                                
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Obtén el id del correo seleccionado, el correo y el correo alternativo*/                    
                    if(jTab.getSelectedRow()==-1)
                        return;
                    
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {                        
                        /*Obtiene todos los datos originales*/
                        sIDOri              = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sNomOri             = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sCoOri              = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCoAlterOri         = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                        

                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sNomOri,        jTab.getSelectedRow(), 3);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                        
                        /*Si el campo de correo excede la cantidad de caes permitidos recortalo*/
                        if(sCoOri.length()> 21)
                            sCoOri = sCoOri.substring(0, 21);

                        /*Si el campo de correo alternativo excede la cantidad de caes permitidos recortalo*/
                        if(sCoAlterOri.length()> 21)
                            sCoAlterOri = sCoAlterOri.substring(0, 21);
                        
                        //Abre la base de datos                             
                        con = Star.conAbrBas(true, false);

                        //Si hubo error entonces regresa
                        if(con==null)
                            return;

                        /*Actualiza los correos en la base de datos*/
                        try 
                        {                        
                            sQ = "UPDATE corrselec SET "
                                    + "usr              = '" + sCoOri.replace("'", "''") + "', "
                                    + "corralter        = '" + sCoAlterOri.replace("'", "''") + "', "
                                    + "fmod             = now(), "
                                    + "estacglo         = '" + Login.sUsrG.replace("'", "''") + "', "
                                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                                    + "WHERE id_id      = '" + sIDOri + "'";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                             //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;
                        }     

                        //Cierra la base de datos
                        Star.iCierrBas(con);                        
                    }                                                                                                                    
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
        };
        
        /*Establece el listener para la tabla de correos*/
        jTab.addPropertyChangeListener(pr);
        
        /*Comprueba si existen estacs dadas de alta*/
        vCompEsta();
        
    }/*Fin de public CorrElecs() */
    
    
    /*Comprueba si existen estacs dadas de alta*/
    private void vCompEsta()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        ResultSet   rs;
       
        /*Comprueba si existen estacs dadas de alta*/
        try
        {
            sQ = "SELECT estac FROM estacs";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existen solo mensajea*/
            if(!rs.next())
                JOptionPane.showMessageDialog(null, "No existen usuarios dados de alta.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCompEsta()*/
    
    
    /*Obtén todos los correos de la base de datos y cargalos en la tabla*/
    private void vCargCorre()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        ResultSet   rs;        
        
        /*Obtiene todos las correos dados de alta*/
        try
        {
            sQ = "SELECT corrselec.ID_ID, corrselec.ESTAC, estacs.NOM, corrselec.USR, corrselec.CORRALTER FROM corrselec LEFT OUTER JOIN estacs ON estacs.ESTAC = corrselec.ESTAC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos en la tabla de correos*/
                DefaultTableModel tm= (DefaultTableModel)jTab.getModel();
                Object nu[]         = {iContFi, rs.getString("id_id"), rs.getString("estac"), rs.getString("nom"), rs.getString("usr"), rs.getString("corralter")};
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas en uno*/
                ++iContFi;        
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }     
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargCorre()*/   
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTabPan1 = new javax.swing.JTabbedPane();
        jPan1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBProbCon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBDel = new javax.swing.JButton();
        jBGuar = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBBusc = new javax.swing.JButton();
        jBMosrT = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jBLim = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTServSMTPSal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTPortSMTP = new javax.swing.JTextField();
        jCActSSLLog = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPContra = new javax.swing.JPasswordField();
        jTUsr = new javax.swing.JTextField();
        jTCorAlter = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTEsta = new javax.swing.JTextField();
        jBUsr = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTAsunOrd = new javax.swing.JTextField();
        jTCuerOrd = new javax.swing.JTextField();
        jTAsunCot = new javax.swing.JTextField();
        jTAsunContra = new javax.swing.JTextField();
        jTAsunFac = new javax.swing.JTextField();
        jTCuerFac = new javax.swing.JTextField();
        jTCuerCot = new javax.swing.JTextField();
        jTCuerContr = new javax.swing.JTextField();
        jBTabG = new javax.swing.JButton();
        jBTod = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTAsunCXC3 = new javax.swing.JTextField();
        jTCuerCXC3 = new javax.swing.JTextField();
        jTAsunCXC2 = new javax.swing.JTextField();
        jTAsunCXC1 = new javax.swing.JTextField();
        jTCuerCXC1 = new javax.swing.JTextField();
        jTCuerCXC2 = new javax.swing.JTextField();
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
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
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

        jTabPan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabPan1KeyPressed(evt);
            }
        });

        jPan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPan1KeyPressed(evt);
            }
        });
        jPan1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "ID", "Usuario", "Nombre Usuario", "Correo Electrónico", "Correo Alternativo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabMouseClicked(evt);
            }
        });
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jPan1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 620, 180));

        jBProbCon.setBackground(new java.awt.Color(255, 255, 255));
        jBProbCon.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProbCon.setForeground(new java.awt.Color(0, 102, 0));
        jBProbCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prov.png"))); // NOI18N
        jBProbCon.setText("Probar");
        jBProbCon.setToolTipText("Probar Conexiòn de Correo");
        jBProbCon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBProbCon.setNextFocusableComponent(jBSal);
        jBProbCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbConMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbConMouseExited(evt);
            }
        });
        jBProbCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbConActionPerformed(evt);
            }
        });
        jBProbCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbConKeyPressed(evt);
            }
        });
        jPan1.add(jBProbCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 460, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Correos:");
        jPan1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 160, -1));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Cuenta(s) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBNew);
        jBDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelMouseExited(evt);
            }
        });
        jBDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelActionPerformed(evt);
            }
        });
        jBDel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelKeyPressed(evt);
            }
        });
        jPan1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 120, 30));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar cambios sobre Cuenta de Correo de Usuario");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGuar.setNextFocusableComponent(jBDel);
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
        jPan1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 340, 120, 30));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosrT);
        jTBusc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBuscFocusLost(evt);
            }
        });
        jTBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscKeyPressed(evt);
            }
        });
        jPan1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 350, 20));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/busc5.png"))); // NOI18N
        jBBusc.setText("Buscar F3");
        jBBusc.setNextFocusableComponent(jTBusc);
        jBBusc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscMouseExited(evt);
            }
        });
        jBBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscActionPerformed(evt);
            }
        });
        jBBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscKeyPressed(evt);
            }
        });
        jPan1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 130, 20));

        jBMosrT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosrT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosrT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosrT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosrT.setText("Mostrar F4");
        jBMosrT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosrT.setNextFocusableComponent(jBGuar);
        jBMosrT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosrTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMosrTMouseExited(evt);
            }
        });
        jBMosrT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMosrTActionPerformed(evt);
            }
        });
        jBMosrT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMosrTKeyPressed(evt);
            }
        });
        jPan1.add(jBMosrT, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 520, 140, 20));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTServSMTPSal);
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
        jPan1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, 120, 30));

        jBLim.setBackground(new java.awt.Color(255, 255, 255));
        jBLim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLim.setForeground(new java.awt.Color(0, 102, 0));
        jBLim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLim.setText("Limpiar");
        jBLim.setToolTipText("Limpiar Todos los Controles (F4)");
        jBLim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLim.setNextFocusableComponent(jBProbCon);
        jBLim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimMouseExited(evt);
            }
        });
        jBLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimActionPerformed(evt);
            }
        });
        jBLim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimKeyPressed(evt);
            }
        });
        jPan1.add(jBLim, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 120, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Cuenta (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBLim);
        jBNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewMouseExited(evt);
            }
        });
        jBNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewActionPerformed(evt);
            }
        });
        jBNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewKeyPressed(evt);
            }
        });
        jPan1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 400, 120, 30));

        jLabel2.setText("*Servidor de Correo Saliente SMTP:");
        jPan1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 210, -1));

        jTServSMTPSal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTServSMTPSal.setNextFocusableComponent(jTPortSMTP);
        jTServSMTPSal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTServSMTPSalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTServSMTPSalFocusLost(evt);
            }
        });
        jTServSMTPSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTServSMTPSalKeyPressed(evt);
            }
        });
        jPan1.add(jTServSMTPSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 250, 20));

        jLabel3.setText("*Puerto SMTP:");
        jPan1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 160, -1));

        jTPortSMTP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPortSMTP.setNextFocusableComponent(jCActSSLLog);
        jTPortSMTP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPortSMTPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPortSMTPFocusLost(evt);
            }
        });
        jTPortSMTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPortSMTPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPortSMTPKeyTyped(evt);
            }
        });
        jPan1.add(jTPortSMTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 160, 20));

        jCActSSLLog.setText("Activar SSL en Login");
        jCActSSLLog.setNextFocusableComponent(jTUsr);
        jCActSSLLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCActSSLLogKeyPressed(evt);
            }
        });
        jPan1.add(jCActSSLLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 160, -1));

        jLabel4.setText("*Contraseña:");
        jPan1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 120, -1));

        jLabel5.setText("*Usuario:");
        jPan1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 170, -1));

        jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPContra.setNextFocusableComponent(jTCorAlter);
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
        jPan1.add(jPContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 250, 20));

        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jPContra);
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
        jPan1.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 250, 20));

        jTCorAlter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCorAlter.setNextFocusableComponent(jTAsunFac);
        jTCorAlter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCorAlterFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCorAlterFocusLost(evt);
            }
        });
        jTCorAlter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCorAlterKeyPressed(evt);
            }
        });
        jPan1.add(jTCorAlter, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 250, 20));

        jLabel6.setText("Correo Alternativo:");
        jPan1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, -1));

        jLabel7.setText("*Usuario:");
        jPan1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 110, -1));

        jTID.setEditable(false);
        jTID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPan1.add(jTID, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 60, 20));

        jLabel8.setText("ID:");
        jPan1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEsta.setNextFocusableComponent(jBBusc);
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
        jPan1.add(jTEsta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 220, 20));

        jBUsr.setBackground(new java.awt.Color(255, 255, 255));
        jBUsr.setText("...");
        jBUsr.setToolTipText("Buscar Usuario(s)");
        jBUsr.setNextFocusableComponent(jTab);
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
        jPan1.add(jBUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, 30, 20));

        jLabel9.setText("*Asunto/Cuerpo Recordatorio 3 CXC:");
        jPan1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 200, 20));

        jLabel10.setText("*Asunto/Cuerpo Factura:");
        jPan1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 210, 20));

        jLabel11.setText("*Asunto/Cuerpo Cotizaciòn:");
        jPan1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 200, 20));

        jLabel12.setText("*Asunto/Cuerpo Contrarecibo:");
        jPan1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 200, 20));

        jTAsunOrd.setText("Envío de orden de compra.");
        jTAsunOrd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunOrd.setNextFocusableComponent(jTCuerOrd);
        jTAsunOrd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunOrdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunOrdFocusLost(evt);
            }
        });
        jTAsunOrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunOrdKeyPressed(evt);
            }
        });
        jPan1.add(jTAsunOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 250, 20));

        jTCuerOrd.setText("Orden de compra.");
        jTCuerOrd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerOrd.setNextFocusableComponent(jTAsunCXC1);
        jTCuerOrd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerOrdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerOrdFocusLost(evt);
            }
        });
        jTCuerOrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerOrdKeyPressed(evt);
            }
        });
        jPan1.add(jTCuerOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, 280, 20));

        jTAsunCot.setText("Envío de cotización.");
        jTAsunCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunCot.setNextFocusableComponent(jTCuerCot);
        jTAsunCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunCotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunCotFocusLost(evt);
            }
        });
        jTAsunCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTAsunCotActionPerformed(evt);
            }
        });
        jTAsunCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunCotKeyPressed(evt);
            }
        });
        jPan1.add(jTAsunCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 250, 20));

        jTAsunContra.setText("Envío de contra recibo.");
        jTAsunContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunContra.setNextFocusableComponent(jTCuerContr);
        jTAsunContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunContraFocusLost(evt);
            }
        });
        jTAsunContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTAsunContraActionPerformed(evt);
            }
        });
        jTAsunContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunContraKeyPressed(evt);
            }
        });
        jPan1.add(jTAsunContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 250, 20));

        jTAsunFac.setText("Envío de CFDI.");
        jTAsunFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunFac.setNextFocusableComponent(jTCuerFac);
        jTAsunFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunFacFocusLost(evt);
            }
        });
        jTAsunFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunFacKeyPressed(evt);
            }
        });
        jPan1.add(jTAsunFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 250, 20));

        jTCuerFac.setText("Comprobaten físcal digital.");
        jTCuerFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerFac.setNextFocusableComponent(jTAsunCot);
        jTCuerFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerFacFocusLost(evt);
            }
        });
        jTCuerFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerFacKeyPressed(evt);
            }
        });
        jPan1.add(jTCuerFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 280, 20));

        jTCuerCot.setText("Cotización.");
        jTCuerCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerCot.setNextFocusableComponent(jTAsunContra);
        jTCuerCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerCotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerCotFocusLost(evt);
            }
        });
        jTCuerCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerCotKeyPressed(evt);
            }
        });
        jPan1.add(jTCuerCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 280, 20));

        jTCuerContr.setText("Contra recibo.");
        jTCuerContr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerContr.setNextFocusableComponent(jTAsunOrd);
        jTCuerContr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerContrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerContrFocusLost(evt);
            }
        });
        jTCuerContr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerContrKeyPressed(evt);
            }
        });
        jPan1.add(jTCuerContr, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 280, 20));

        jBTabG.setBackground(new java.awt.Color(0, 153, 153));
        jBTabG.setToolTipText("Mostrar Tabla en Grande");
        jBTabG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTabGActionPerformed(evt);
            }
        });
        jBTabG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTabGKeyPressed(evt);
            }
        });
        jPan1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 10, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jTab);
        jBTod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTodMouseExited(evt);
            }
        });
        jBTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTodActionPerformed(evt);
            }
        });
        jBTod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTodKeyPressed(evt);
            }
        });
        jPan1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 322, 130, 18));

        jLabel13.setText("*Asunto/Cuerpo Orden Compra:");
        jPan1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 200, 20));

        jLabel14.setText("*Asunto/Cuerpo Recordatorio 1 CXC:");
        jPan1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 200, 20));

        jLabel15.setText("*Asunto/Cuerpo Recordatorio 2 CXC:");
        jPan1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 200, 20));

        jTAsunCXC3.setText("Envío de recordatorio de pago tercer aviso.");
        jTAsunCXC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunCXC3.setNextFocusableComponent(jTCuerCXC3);
        jTAsunCXC3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunCXC3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunCXC3FocusLost(evt);
            }
        });
        jTAsunCXC3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunCXC3KeyPressed(evt);
            }
        });
        jPan1.add(jTAsunCXC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 250, 20));

        jTCuerCXC3.setText("Existen facturas vencidas y esto es el tercer aviso.");
        jTCuerCXC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerCXC3.setNextFocusableComponent(jTEsta);
        jTCuerCXC3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerCXC3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerCXC3FocusLost(evt);
            }
        });
        jTCuerCXC3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerCXC3KeyPressed(evt);
            }
        });
        jPan1.add(jTCuerCXC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 280, 20));

        jTAsunCXC2.setText("Envío de recordatorio de pago segundo aviso.");
        jTAsunCXC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunCXC2.setNextFocusableComponent(jTCuerCXC2);
        jTAsunCXC2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunCXC2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunCXC2FocusLost(evt);
            }
        });
        jTAsunCXC2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunCXC2KeyPressed(evt);
            }
        });
        jPan1.add(jTAsunCXC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 250, 20));

        jTAsunCXC1.setText("Envío de recordatorio de pago primer aviso.");
        jTAsunCXC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAsunCXC1.setNextFocusableComponent(jTCuerCXC1);
        jTAsunCXC1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAsunCXC1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAsunCXC1FocusLost(evt);
            }
        });
        jTAsunCXC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAsunCXC1KeyPressed(evt);
            }
        });
        jPan1.add(jTAsunCXC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 250, 20));

        jTCuerCXC1.setText("Existen facturas vencidas y esto es el primer aviso.");
        jTCuerCXC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerCXC1.setNextFocusableComponent(jTAsunCXC2);
        jTCuerCXC1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerCXC1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerCXC1FocusLost(evt);
            }
        });
        jTCuerCXC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerCXC1KeyPressed(evt);
            }
        });
        jPan1.add(jTCuerCXC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 280, 20));

        jTCuerCXC2.setText("Existen facturas vencidas y esto es el segundo aviso.");
        jTCuerCXC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCuerCXC2.setNextFocusableComponent(jTAsunCXC3);
        jTCuerCXC2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCuerCXC2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCuerCXC2FocusLost(evt);
            }
        });
        jTCuerCXC2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCuerCXC2KeyPressed(evt);
            }
        });
        jPan1.add(jTCuerCXC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 280, 20));

        jTabPan1.addTab("tab1", jPan1);

        jP1.add(jTabPan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 770, 580));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(616, 590, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        //Declara variables locales
        String      sServSMTPSal;
        String      sSMTPort;
        String      sUsr;
        String      sContra;
        String      sCoAlter;        
        int         iActSSL;
        String      sEsta;
        String      sId                   = "";
        
        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        ResultSet   rs;
        Connection  con;
        
        
        
        
        
        
        /*Lee El usuario que selecciono el usuario*/
        sEsta           = jTEsta.getText();
        
        /*Si El usuario es cadena vacia entonces no puede seguir*/        
        if(sEsta.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una usuario.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el combobox y regresa*/
            jTEsta.grabFocus();           
            return;
        }                
        
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Comprueba si el código de El usuario existe y obtiene su nome*/        
        String sNomb;
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac <> '-' AND estac = '" + sEsta + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario: " + sEsta + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTEsta.grabFocus();               
                return;
            }
            /*Else is hay datos entonces obtiene la consulta*/
            else
                sNomb = rs.getString("nom");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Obtiene si el usuario tiene correo asociado
        int iRes    = Star.iUsrConfCorr(con, sEsta);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si no tiene correo asociado entonces solamente mensajea
        if(iRes==1)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Coloca el borde rojo*/                               
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El usuario: " + sEsta + " ya tiene un correo asociado.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTEsta.grabFocus();               
            return;                                                
        }
            
        /*Lee el servidor SMTP de correo saliente*/
        sServSMTPSal = jTServSMTPSal.getText().trim();
        
        /*Si el campo de servidor SMTP saliente esta vacio no puede seguir*/
        if(sServSMTPSal.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTServSMTPSal.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del servidor de correo saliente SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTServSMTPSal.grabFocus();            
            return;
        }
        
        /*Lee el puerto SMTP*/
        sSMTPort = jTPortSMTP.getText().trim();
        
        /*Si el campo de puerto SMTP esta vacio no puede seguir*/
        if(sSMTPort.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTPortSMTP.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPortSMTP.grabFocus();           
            return;
        }
        
        /*Si el checkbox de activar SSL en login esta activado entonces almacenalo en la variable*/
        if(jCActSSLLog.isSelected())
            iActSSL = 1;
        else
            iActSSL = 0;
            
        /*Lee el usuario*/
        sUsr = jTUsr.getText().trim();
        
        /*Si el campo de usr esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }
        
        /*Leela contraseña*/
        sContra = new String(jPContra.getPassword()).trim();
        
        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jPContra.grabFocus();                        
            return;
        }
        
        /*Lee el correo alternativo*/
        sCoAlter  = jTCorAlter.getText().trim();
        
        /*Si el correo alternativo es cadena vacia entonces no puede seguir*/        
        if(sCoAlter.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCorAlter.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un correo alternativo en caso de fallos al envio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCorAlter.grabFocus();           
            return;
        }
        
        /*Lee el asunto de la factura*/
        String sAsunFac = jTAsunFac.getText();
        
        /*Si el asunto de la factura es cadena vacia entonces*/        
        if(sAsunFac.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTAsunFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la factura.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunFac.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo de la factura*/
        String sCuerFac = jTCuerFac.getText();
        
        /*Si el cuerpo de la factura es cadena vacia entonces*/        
        if(sCuerFac.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCuerFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la factura.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerFac.grabFocus();           
            return;
        }
        
        /*Lee el asunto de la cotización*/
        String sAsunCot = jTAsunCot.getText();
        
        /*Si el asunto de la cotización es cadena vacia entonces*/        
        if(sAsunCot.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTAsunCot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la cotización.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunCot.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo de la cotización*/
        String sCuerCot = jTCuerCot.getText();
        
        /*Si el cuerpo de la cotización es cadena vacia entonces*/        
        if(sCuerCot.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCuerCot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la cotización.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerCot.grabFocus();           
            return;
        }
        
        /*Lee el asunto del contrarecibo*/
        String sAsunContra = jTAsunContra.getText();
        
        /*Si el asunto del contrarecibo es cadena vacia entonces*/        
        if(sAsunContra.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTAsunContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo del contrarecibo.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunContra.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo del contrarecibo*/
        String sCuerContra = jTCuerContr.getText();
        
        /*Si el cuerpo del contrarecibo es cadena vacia entonces*/        
        if(sCuerContra.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCuerContr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo del contrarecibo.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerContr.grabFocus();
            return;
        }
        
        /*Lee el asunto de la órden de compra*/
        String sAsunOrd = jTAsunOrd.getText();
        
        /*Si el asunto de la órden de compra es cadena vacia entonces*/        
        if(sAsunOrd.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTAsunOrd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la orden de compra.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunOrd.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo de la órden de compra*/
        String sCuerOrd = jTCuerOrd.getText();
        
        /*Si el cuerpo de la órden de compra es cadena vacia entonces*/        
        if(sCuerOrd.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCuerOrd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la orden de compra.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerOrd.grabFocus();
            return;
        }
        
        /*Pregunta al usr si esta seguro de agregar el correo electrónico*/                
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Nuevo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);            
            return;
        }
        
        /*Encripta la clave del correo*/
        sContra = Star.sEncrip(sContra);                                                        
        
        /*Inserta el registro en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO corrselec (srvsmtpsal,                                portsmtp,           actslenlog,         usr,                                pass,                                   corralter,                              estac,                              falt,       estacglo,                                       asunfac,                                asuncot,                                asuncontra,                                 asunord,                                cuerpfac,                               cuerpcot,                               cuerpcontra,                                cuerpord,                               sucu,                                           nocaj,                          asunrec1,                                          asunrec2,                                          asunrec3,                                          cuerprec1,                                         cuerprec2,                                         cuerprec3)" + 
                           " VALUES('" + sServSMTPSal.replace("'", "''") + "','" +  sSMTPort + "',"+    iActSSL + " ,'" +   sUsr.replace("'", "''") + "','" +   sContra.replace("'", "''") + "','" +    sCoAlter.replace("'", "''") + "','" +   sEsta.replace("'", "''") + "',      now(), '" + Login.sUsrG.replace("'", "''") + "','" +    sAsunFac.replace("'", "''") + "','" +   sAsunCot.replace("'", "''") + "','" +   sAsunContra.replace("'", "''") + "','" +    sAsunOrd.replace("'", "''") + "','" +   sCuerFac.replace("'", "''") + "','" +   sCuerCot.replace("'", "''") + "','" +   sCuerContra.replace("'", "''") + "','" +    sCuerOrd.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" + jTAsunCXC1.getText().replace("'", "''") + "', '" + jTAsunCXC2.getText().replace("'", "''") + "', '" + jTAsunCXC3.getText().replace("'", "''") + "', '" + jTCuerCXC1.getText().replace("'", "''") + "', '" + jTCuerCXC2.getText().replace("'", "''") + "', '" + jTCuerCXC3.getText().replace("'", "''") + "')";                          
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
                
        /*Obtiene el último id insertado de correo*/
        try
        {
            sQ = "SELECT id_id FROM corrselec ORDER BY id_id DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos obtiene la consulta*/
            if(rs.next())
                sId = rs.getString("id_id");   
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

        /*Agregalos en la tabla de correos electrónicos*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, sId, sEsta, sNomb, sUsr, sCoAlter};
        tm.addRow(nu);

        /*Aumenta en uno el contador de filas en uno*/
        ++iContFi;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Correo electrónico guardado con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de nu*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        //Declara variables locales
        String      sId;
        
        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        Connection  con;
        
        
        
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un correo de la lista para borrar.", "Borrar Correo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();          
            return;
        }
             
        /*Pregunta si esta seguro de borrar el correo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) correo(s)?", "Borrar Correo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
   
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usr*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x++)
        {
            /*Obtén el id del correo seleccionado y El usuario*/        
            sId             = jTab.getValueAt(iSel[x], 1).toString();                              
            
            /*Borra el correo de la base de datos*/
            try 
            {                
                sQ = "DELETE FROM corrselec WHERE id_id = '" + sId + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }
             
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        }                                                                                
         
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Correo(s) borrado(s) con éxito.", "Correos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de correos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

        
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usr si esta seguro de salir de la ventana*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y sal de la forma*/
            System.gc();        
            this.dispose();
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el campo de servidor smtp saliente*/
    private void jTServSMTPSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTServSMTPSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTServSMTPSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de puerto smtp*/
    private void jTPortSMTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortSMTPKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPortSMTPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de activar SSL en login*/
    private void jCActSSLLogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCActSSLLogKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCActSSLLogKeyPressed

    
    /*Cuando se presiona una tecla en el campo de usr*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jPContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPContraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPContraKeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo alternativo*/
    private void jTCorAlterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCorAlterKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCorAlterKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de servidor SMTP saliente*/
    private void jTServSMTPSalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTServSMTPSalFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTServSMTPSal.setSelectionStart(0);jTServSMTPSal.setSelectionEnd(jTServSMTPSal.getText().length());                
        
    }//GEN-LAST:event_jTServSMTPSalFocusGained

    
    /*Cuando se gana el foco del teclado en el puerto SMTP*/
    private void jTPortSMTPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortSMTPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPortSMTP.setSelectionStart(0);jTPortSMTP.setSelectionEnd(jTPortSMTP.getText().length());                
        
    }//GEN-LAST:event_jTPortSMTPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de usr*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());                
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de contraseña*/
    private void jPContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jPContra.setSelectionStart(0);jPContra.setSelectionEnd(jPContra.getText().length());        
        
    }//GEN-LAST:event_jPContraFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de correo alternativo*/
    private void jTCorAlterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCorAlterFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCorAlter.setSelectionStart(0);jTCorAlter.setSelectionEnd(jTCorAlter.getText().length());                
        
    }//GEN-LAST:event_jTCorAlterFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de puerto SMTP*/
    private void jTPortSMTPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPortSMTPFocusLost

        /*Coloca el cursor al principio del control*/
        jTPortSMTP.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPortSMTP.getText().compareTo("")!=0)
            jTPortSMTP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usr*/
        String sTex = jTPortSMTP.getText();
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a int colocar cadena vacia y regresar*/
        try  
        {  
            int d = Integer.parseInt(sTex);  
        }  
        catch(NumberFormatException expnNumForm)  
        {              
            jTPortSMTP.setText("");
            return;
        }  
        
        /*Colocalo en el campo*/
        jTPortSMTP.setText              (sTex);                        
        jTPortSMTP.setCaretPosition     (0);
        
    }//GEN-LAST:event_jTPortSMTPFocusLost
    
    
    /*Cuando se presiona una tecla typed en el campo de puerto SMTP*/
    private void jTPortSMTPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPortSMTPKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPortSMTPKeyTyped

        
    /*Cuando se pierde el foco del teclado en el campo de servidor smtp saliente*/
    private void jTServSMTPSalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTServSMTPSalFocusLost

        /*Coloca el cursor al principio del control*/
        jTServSMTPSal.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTServSMTPSal.getText().compareTo("")!=0)
            jTServSMTPSal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                      
    }//GEN-LAST:event_jTServSMTPSalFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de usr*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de contraseña*/
    private void jPContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPContraFocusLost

        /*Coloca el cursor al principio del control*/
        jPContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jPContra.getText().compareTo("")!=0)
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el password introducido por el usr*/
        String sPass = new String(jPContra.getPassword());
                
    }//GEN-LAST:event_jPContraFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de correo alternativo*/
    private void jTCorAlterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCorAlterFocusLost

        /*Coloca el cursor al principio del control*/
        jTCorAlter.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCorAlter.getText().compareTo("")!=0)
            jTCorAlter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTCorAlterFocusLost

      
    /*Cuando se presiona una tecla en el botón de probar conexión*/
    private void jBProbConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbConKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbConKeyPressed

    
    /*Cuando se presiona el botón de probar conexión*/
    private void jBProbConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbConActionPerformed
        
        /*Lee el servidor SMTP de correo saliente*/
        final String  sServSMTPSal = jTServSMTPSal.getText().trim();
        
        /*Si el campo de servidor SMTP saliente esta vacio no puede seguir*/
        if(sServSMTPSal.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del servidor de correo saliente SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTServSMTPSal.grabFocus();
            return;
        }
        
        /*Lee el puerto SMTP*/
        final String  sSMTPort = jTPortSMTP.getText().trim();
        
        /*Si el campo de puerto SMTP esta vacio no puede seguir*/
        if(sSMTPort.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPortSMTP.grabFocus();           
            return;
        }
        
        /*Si el checkbox de activar SSL en login esta activado entonces almacenalo en la variable*/
        final String sActSSL;
        if(jCActSSLLog.isSelected())
            sActSSL = "true";
        else
            sActSSL = "false";
            
        /*Lee el usuario*/
        final String sUsr = jTUsr.getText().trim();
        
        /*Si el campo de usr esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }
        
        /*Leela contraseña*/
        final String sContra = new String(jPContra.getPassword()).trim();
        
        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jPContra.grabFocus();                        
            return;
        }
        System.out.println(sUsr);
        System.out.println(sContra);
                final String username = sUsr;
                final String password = sContra;
        /*Manda un correo de pruebas para saber que la conexion esta bien en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Intenta mandar el correo de prueba*/
                try
                {                    
                    Properties props = System.getProperties();
                    props.setProperty("mail.smtp.host", sServSMTPSal);
                    props.put("mail.smtp.starttls.enable", sActSSL);
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.debug", "true");
                    props.put("mail.smtp.port", sSMTPort);
                    props.put("mail.store.protocol", "pop3");
                    props.put("mail.transport.protocol", "smtp");
                    Session session = Session.getDefaultInstance(props,
                            new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username,password);
                                }
                            });

                    // -- Create a new message --
                    Message msg = new MimeMessage(session);                    
                    msg.setFrom(new InternetAddress(username));
                    msg.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(username, false));
                    msg.setSubject("Prueba");
                    msg.setContent("<h1>Prueba exitosa</h1>", "text/html");
                    msg.setSentDate(new java.util.Date());

                    // **************** Without Attachments ******************
                    msg.setText("Prueba exitosa");
                    Transport.send(msg);                    
                }
                catch(MessagingException expnMessag)
                {
                    /*Registra en log correos que no tuvo éxito el envio*/
                    vRegLog(false, sUsr, expnMessag.getMessage());
                    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnMessag.getMessage(), Star.sErrMessag, expnMessag.getStackTrace());                    
                    return;
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();
                
                /*Registra en log correos que tuvo éxito el envio*/
                vRegLog(true, sUsr, "");
                                        
                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Conexión exitosa.", "Correo electrónico", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                 
                                    
            }/*Fin de public void run()*/
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
                
    }//GEN-LAST:event_jBProbConActionPerformed
        

    /*Registra en log correos que no tuvo éxito el envio*/
    private void vRegLog(boolean bSi, String sUsr, String sErr)
    {
        /*Si el error es nulo entonces regresa*/
        if(sErr==null)
            return;
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Determina la consulta*/
        String      sQ; 
        if(bSi)
            sQ = "INSERT INTO logcorrs(    corr,                              nodoc,     estad,          motiv,                                estac,                                  falt,       corrde,                         tipdoc,             sucu,                                     nocaj) " + 
                              "VALUES('" +  sUsr.replace("'", "''") + "',    '',         'EXITO','" +    sErr.replace("'", "''") + "','" +     Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'PRUEBA','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
        else
            sQ = "INSERT INTO logcorrs(    corr,                              nodoc,     estad,          motiv,                                estac,                                  falt,       corrde,                         tipdoc,             sucu,                                     nocaj) " + 
                              "VALUES('" +  sUsr.replace("'", "''") + "',    '',         'FALLO','" +    sErr.replace("'", "''") + "','" +     Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'PRUEBA','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
        
        /*Ingresa en la base de datos el registro*/
        Statement   st;                    
        try 
        {                                
            st = con.createStatement();
            st.executeUpdate(sQ);                           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        //Cierra la base de datos
        Star.iCierrBas(con);
                    
    }/*Fin de private void vRegLog(boolean bSi)*/
                    
                    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing
            
    
    /*Cuando se presiona una tecla en el botón de limpiar*/
    private void jBLimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimKeyPressed

    
    /*Cuando se presiona el botón de limpiar controles*/
    private void jBLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimActionPerformed
        
        /*Limpia todos los controles*/
        jTServSMTPSal.setText           ("");
        jTPortSMTP.setText              ("");
        jCActSSLLog.setSelected         (false);
        jTUsr.setText                   ("");
        jPContra.setText                ("");
        jTCorAlter.setText              ("");
        jTID.setText                    ("");
        jTEsta.setText                  ("");
        
        /*Deshabilita el boton de guardar cambios ya que no se pueden guardar cambios para un nu contacto*/
        jBGuar.setEnabled               (false);
        
        /*Coloca los valores por default en los controles de asunto y cuerpo de correo*/
        jTAsunFac.setText               ("Envío de CFDI.");
        jTAsunFac.setCaretPosition      (0);
        jTCuerFac.setText               ("Comprobante fiscal digital.");
        jTCuerFac.setCaretPosition      (0);
        jTAsunCot.setText               ("Envío de cotización.");
        jTAsunCot.setCaretPosition      (0);
        jTCuerCot.setText               ("Cotización.");
        jTCuerCot.setCaretPosition      (0);
        jTAsunContra.setText            ("Envío de contrarecibo.");
        jTAsunContra.setCaretPosition   (0);
        jTCuerContr.setText             ("Contrarecibo.");
        jTCuerContr.setCaretPosition    (0);
        jTAsunOrd.setText               ("Envío de orden de compra.");
        jTAsunOrd.setCaretPosition      (0);
        jTCuerOrd.setText               ("Orden de compra.");
        jTCuerOrd.setCaretPosition      (0);
        jTAsunCXC1.setText              ("Envío de recordatorio de pago primer aviso.");
        jTAsunCXC1.setCaretPosition     (0);
        jTCuerCXC1.setText              ("Existen facturas vencidas y esto es el primer aviso.");
        jTCuerCXC1.setCaretPosition     (0);
        jTAsunCXC2.setText              ("Envío de recordatorio de pago segundo aviso.");
        jTAsunCXC2.setCaretPosition     (0);
        jTCuerCXC2.setText              ("Existen facturas vencidas y esto es el segundo aviso.");
        jTCuerCXC2.setCaretPosition     (0);
        jTAsunCXC3.setText              ("Envío de recordatorio de pago tercer aviso.");
        jTAsunCXC3.setCaretPosition     (0);
        jTCuerCXC3.setText              ("Existen facturas vencidas y esto es el tercer aviso.");
        jTCuerCXC3.setCaretPosition     (0);
        
    }//GEN-LAST:event_jBLimActionPerformed

    
    /*Cuando se presiona el boton de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        //Declara variables locales
        String      sServSMTPSal;
        String      sSMTPort;
        String      sUsr;
        String      sContra;
        String      sCoAlter;        
        int         iActSSL;
        String      sEsta;
        String      sId;
        
        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        ResultSet   rs;
        Connection  con;
        
        
        
        
        /*Si el usr no a seleccionado un correo para modificar no puede avanzar*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un elemento de la tabla para modificar.", "Modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de correos y regresa*/
            jTab.grabFocus();                        
            return;           
        }
        
        /*Lee el servidor SMTP de correo saliente*/
        sServSMTPSal = jTServSMTPSal.getText().trim();
        
        /*Si el campo de servidor SMTP saliente esta vacio no puede seguir*/
        if(sServSMTPSal.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTServSMTPSal.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del servidor de correo saliente SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTServSMTPSal.grabFocus();           
            return;
        }
        
        /*Lee el puerto SMTP*/
        sSMTPort = jTPortSMTP.getText().trim();
        
        /*Si el campo de puerto SMTP esta vacio no puede seguir*/
        if(sSMTPort.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTPortSMTP.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del puerto SMTP esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTPortSMTP.grabFocus();           
            return;
        }
        
        /*Si el checkbox de activar SSL en login esta activado entonces almacenalo en la variable*/
        if(jCActSSLLog.isSelected())
            iActSSL = 1;
        else
            iActSSL = 0;
            
        /*Lee el usuario*/
        sUsr = jTUsr.getText().trim();
        
        /*Si el campo de usuario esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usr esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }
        
        /*Lee la contraseña*/
        sContra = new String(jPContra.getPassword()).trim();                
        
        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jPContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jPContra.grabFocus();                        
            return;
        }
        
        /*Lee el correo alternativo*/
        sCoAlter    = jTCorAlter.getText().trim();
        
        /*Si el correo alternativo es cadena vacia entonces no puede seguir*/        
        if(sCoAlter.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCorAlter.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un correo alternativo en caso de fallos al envio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCorAlter.grabFocus();           
            return;
        }
        
        /*Lee El usuario que selecciono el usr*/
        sEsta       = jTEsta.getText();
        
        /*Si El usuario es cadena vacia entonces no puede seguir*/        
        if(sEsta.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una usuario.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTEsta.grabFocus();           
            return;
        }
        
        /*Lee el asunto de la factura*/
        String sAsunFac = jTAsunFac.getText();
        
        /*Si el asunto de la factura es cadena vacia entonces*/        
        if(sAsunFac.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTAsunFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la factura.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunFac.grabFocus();            
            return;
        }
        
        /*Lee el cuerpo de la factura*/
        String sCuerFac = jTCuerFac.getText();
        
        /*Si el cuerpo de la factura es cadena vacia entonces*/        
        if(sCuerFac.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCuerFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la factura.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerFac.grabFocus();           
            return;
        }
        
        /*Lee el asunto de la cotización*/
        String sAsunCot = jTAsunCot.getText();
        
        /*Si el asunto de la cotización es cadena vacia entonces*/        
        if(sAsunCot.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTAsunCot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la cotización.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunCot.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo de la cotización*/
        String sCuerCot = jTCuerCot.getText();
        
        /*Si el cuerpo de la cotización es cadena vacia entonces*/        
        if(sCuerCot.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCuerCot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la cotización.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerCot.grabFocus();           
            return;
        }
        
        /*Lee el asunto del contrarecibo*/
        String sAsunContra = jTAsunContra.getText();
        
        /*Si el asunto del contrarecibo es cadena vacia entonces*/        
        if(sAsunContra.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTAsunContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo del contrarecibo.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunContra.grabFocus();            
            return;
        }
        
        /*Lee el cuerpo del contrarecibo*/
        String sCuerContra = jTCuerContr.getText();
        
        /*Si el cuerpo del contrarecibo es cadena vacia entonces*/        
        if(sCuerContra.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCuerContr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo del contrarecibo.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerContr.grabFocus();          
            return;
        }
        
        /*Lee el asunto de la órden de compra*/
        String sAsunOrd = jTAsunOrd.getText();
        
        /*Si el asunto de la órden de compra es cadena vacia entonces*/        
        if(sAsunOrd.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTAsunOrd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un asunto para el correo de la orden de compra.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTAsunOrd.grabFocus();           
            return;
        }
        
        /*Lee el cuerpo de la órden de compra*/
        String sCuerOrd = jTCuerOrd.getText();
        
        /*Si el cuerpo de la órden de compra es cadena vacia entonces*/        
        if(sCuerOrd.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCuerOrd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has ingresado un cuerpo para el correo de la orden de compra.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCuerOrd.grabFocus();           
            return;
        }
        
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Comprueba si EL usuario existe y obtiene el nomre*/
        String sNomb;
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac = '" + jTEsta.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El usuario \"" + sEsta + "\" no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTEsta.grabFocus();               
                return;                                                                                  
            }
            /*Else si hay datos entonces obtiene la consulta*/
            else
                sNomb   = rs.getString("nom");                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }            
        
        /*Lee el id*/
        sId                   = jTID.getText();
        
        /*Pregunta al usr si esta seguro de modificar el correo electrónico*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Modificar Correo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            Star.iCierrBas(con);
            return;
        }
        
        /*Encripta la clave del correo*/
        sContra = Star.sEncrip(sContra);                                                
        
        /*Actualiza en la base de datos el registro*/
        try 
        {            
            sQ = "UPDATE corrselec SET " +
                     "srvsmtpsal            = '" + sServSMTPSal.replace("'", "''") + "',"+
                     "portsmtp              = " + sSMTPort + "," +
                     "actslenlog            = " + iActSSL + "," +
                     "usr                   = '" + sUsr.replace("'", "''") + "'," +
                     "pass                  = '" + sContra.replace("'", "''") + "'," +
                     "corralter             = '" + sCoAlter.replace("'", "''") + "'," +
                     "estac                 = '" + sEsta.replace("'", "''") + "'," +
                     "fmod                  = now(), " + 
                     "asunfac               = '" + sAsunFac.replace("'", "''") + "', " + 
                     "asuncot               = '" + jTAsunCot.getText().replace("'", "''") + "', " + 
                     "asuncontra            = '" + jTAsunContra.getText().replace("'", "''") + "', " + 
                     "asunord               = '" + jTAsunOrd.getText().replace("'", "''") + "', " + 
                     "asunrec1              = '" + jTAsunCXC1.getText().replace("'", "''") + "', " + 
                     "asunrec2              = '" + jTAsunCXC2.getText().replace("'", "''") + "', " + 
                     "asunrec3              = '" + jTAsunCXC3.getText().replace("'", "''") + "', " + 
                     "cuerpfac              = '" + jTCuerFac.getText().replace("'", "''") + "', " + 
                     "cuerpcot              = '" + jTCuerCot.getText().replace("'", "''") + "', " + 
                     "cuerpcontra           = '" + jTCuerContr.getText().replace("'", "''") + "', " + 
                     "cuerpord              = '" + jTCuerOrd.getText().replace("'", "''") + "', " + 
                     "cuerprec1             = '" + jTCuerCXC3.getText().replace("'", "''") + "', " + 
                     "cuerprec2             = '" + jTCuerCXC3.getText().replace("'", "''") + "', " + 
                     "cuerprec3             = '" + jTCuerCXC3.getText().replace("'", "''") + "', " + 
                     "sucu                  = '" + Star.sSucu.replace("'", "''") + "', " +
                     "nocaj                 = '" + Star.sNoCaj.replace("'", "''") + "', " +
                     "estacglo = '" + Login.sUsrG.replace("'", "''") + "'";                          
            st = con.createStatement();
            st.executeUpdate(sQ);
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

        /*Obtén el id del correo seleccionado*/
        int row         = jTab.getSelectedRow();        
        
        /*Agregalos en la tabla de correos electrónicos*/        
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();                        
        Object nu[]             = {sId, sEsta, sNomb, sUsr, sCoAlter};
        tm.addRow(nu);
        
        /*Borralo de la tabla*/
        tm.removeRow(row);
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Correo electrónico modificado con éxito.", "Éxito al modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se hace clic en la tabla de correos*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de probar conexión*/
        if(evt.getClickCount() == 2) 
            jBProbCon.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());
        
    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jTBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscKeyPressed

        /*Si se presionó enter entonces presiona el botón de búsqueda y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBBusc.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBuscKeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosrTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosrTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosrTKeyPressed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosrTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosrTActionPerformed
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtén todos los correos de la base de datos y cargalos en la tabla*/
        vCargCorre();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMosrTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();           
            return;
        }                      
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de correos*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        /*Obtiene de la base de datos todos los correos*/        
        try
        {                  
           sQ = "SELECT corrselec.CORRALTER, corrselec.ID_ID, corrselec.ESTAC, estacs.NOM, corrselec.USR FROM corrselec LEFT OUTER JOIN estacs ON estacs.ESTAC = corrselec.ESTAC WHERE corrselec.ID_ID LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR srvsmtpsal LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR portsmtp LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.USR LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.PASS LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.CORRALTER LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.ESTACGLO LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR corrselec.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Cargalos en la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("corrselec.ID_ID"), rs.getString("corrselec.ESTAC"), rs.getString("estacs.NOM"), rs.getString("corrselec.USR"), rs.getString("corrselec.CORRALTER")};
                tm.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;                                       
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

        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTEstaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEsta.setSelectionStart(0);jTEsta.setSelectionEnd(jTEsta.getText().length());        
        
    }//GEN-LAST:event_jTEstaFocusGained
        
    
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTEstaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            Busc b = new Busc(this, jTEsta.getText(), 4, jTEsta, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else                    
            vKeyPreEsc(evt);        
                
    }//GEN-LAST:event_jTEstaKeyPressed

    
    
    /*Cuando se tipea una tecla en el campo de El usuario*/
    private void jTEstaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstaKeyTyped

    
    /*Cuando se presiona el botón de buscar coincidencias*/
    private void jBUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsrActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTEsta.getText(), 4, jTEsta, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBUsrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUsrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUsrKeyPressed

    
    /*Cuando se presiona una tecla en el tabbed paned*/
    private void jTabPan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabPan1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabPan1KeyPressed

    
    /*Cuando se presiona una tecla en el panel 1*/
    private void jPan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPan1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPan1KeyPressed

    
    /*Cuando se presiona una tecla en el campo del asunto de la factura*/
    private void jTAsunFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunFacKeyPressed

    
    /*Cuando se presiona una tecla en el campo del asunto de la cotización*/
    private void jTAsunCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunCotKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la asunto del contrareicibo*/
    private void jTAsunContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunContraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunContraKeyPressed

    
    /*Cuando se presiona una tecla en el campo del asunto de la ordán de compra*/
    private void jTAsunOrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunOrdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunOrdKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de la factura*/
    private void jTCuerFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerFacKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de la cotización*/
    private void jTCuerCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerCotKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo del contrarecibo*/
    private void jTCuerContrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerContrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerContrKeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de la órden de compra*/
    private void jTCuerOrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerOrdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerOrdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del asunto de la factura*/
    private void jTAsunFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunFacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunFac.setSelectionStart(0);jTAsunFac.setSelectionEnd(jTAsunFac.getText().length());                
        
    }//GEN-LAST:event_jTAsunFacFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del asunto de la cotización*/
    private void jTAsunCotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunCot.setSelectionStart(0);jTAsunCot.setSelectionEnd(jTAsunCot.getText().length());                
        
    }//GEN-LAST:event_jTAsunCotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del asunto del contrarecibo*/
    private void jTAsunContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunContra.setSelectionStart(0);jTAsunContra.setSelectionEnd(jTAsunContra.getText().length());                
        
    }//GEN-LAST:event_jTAsunContraFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del asunto de la órden de compra*/
    private void jTAsunOrdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunOrdFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunOrd.setSelectionStart(0);jTAsunOrd.setSelectionEnd(jTAsunOrd.getText().length());                
        
    }//GEN-LAST:event_jTAsunOrdFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de la factura*/
    private void jTCuerFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerFacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerFac.setSelectionStart(0);jTCuerFac.setSelectionEnd(jTCuerFac.getText().length());                
        
    }//GEN-LAST:event_jTCuerFacFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de la cotización*/
    private void jTCuerCotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerCot.setSelectionStart(0);jTCuerCot.setSelectionEnd(jTCuerCot.getText().length());                
        
    }//GEN-LAST:event_jTCuerCotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo del contrarecibo*/
    private void jTCuerContrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerContrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerContr.setSelectionStart(0);jTCuerContr.setSelectionEnd(jTCuerContr.getText().length());         
        
    }//GEN-LAST:event_jTCuerContrFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de la órden de compra*/
    private void jTCuerOrdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerOrdFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerOrd.setSelectionStart(0);jTCuerOrd.setSelectionEnd(jTCuerOrd.getText().length());             
        
    }//GEN-LAST:event_jTCuerOrdFocusGained

    
    /*Cuando la rueda del ratón se mueve en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando el ratón se arrastra en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de mostrar tabla*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar tabla*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
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

    
    /*Cuando se presiona el botón de seleccionar todo*/
    private void jBTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTodActionPerformed

        /*Si la tabla no contiene elementos entonces regresa*/
        if(jTab.getRowCount()==0)
            return;
        
        /*Si están seleccionados los elementos en la tabla entonces*/
        if(bSel)
        {
            /*Coloca la bandera y deseleccionalos*/
            bSel    = false;
            jTab.clearSelection();
        }
        /*Else deseleccionalos y coloca la bandera*/
        else
        {
            bSel    = true;
            jTab.setRowSelectionInterval(0, jTab.getModel().getRowCount()-1);
        }

    }//GEN-LAST:event_jBTodActionPerformed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTodKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la estación*/
    private void jTEstaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusLost

        /*Coloca el cursor al principio del control*/
        jTEsta.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEsta.getText().compareTo("")!=0)
            jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTEstaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del asunto de correo de factura*/
    private void jTAsunFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunFacFocusLost

        /*Coloca el cursor al principio del control*/
        jTAsunFac.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunFac.getText().compareTo("")!=0)
            jTAsunFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunFacFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del asunto de la cotiazión*/
    private void jTAsunCotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCotFocusLost

        /*Coloca el cursor al principio del control*/
        jTAsunCot.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunCot.getText().compareTo("")!=0)
            jTAsunCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunCotFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del asunto del contrarecibo*/
    private void jTAsunContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunContraFocusLost

        /*Coloca el cursor al principio del control*/
        jTAsunContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunContra.getText().compareTo("")!=0)
            jTAsunContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunContraFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del asunto de la órden*/
    private void jTAsunOrdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunOrdFocusLost

        /*Coloca el cursor al principio del control*/
        jTAsunOrd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunOrd.getText().compareTo("")!=0)
            jTAsunOrd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunOrdFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cuerpo de la factura*/
    private void jTCuerFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerFacFocusLost

        /*Coloca el cursor al principio del control*/
        jTCuerFac.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerFac.getText().compareTo("")!=0)
            jTCuerFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerFacFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cuerpo de la cotización*/
    private void jTCuerCotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCotFocusLost

        /*Coloca el cursor al principio del control*/
        jTCuerCot.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerCot.getText().compareTo("")!=0)
            jTCuerCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerCotFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo del contrarecibo*/
    private void jTCuerContrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerContrFocusLost

        /*Coloca el cursor al principio del control*/
        jTCuerContr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerContr.getText().compareTo("")!=0)
            jTCuerContr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerContrFocusLost

    
    /*Cuando se pierde el foco del teclado en el control del cuerpo de la órden*/
    private void jTCuerOrdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerOrdFocusLost

        /*Coloca el cursor al principio del control*/
        jTCuerOrd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerOrd.getText().compareTo("")!=0)
            jTCuerOrd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerOrdFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBUsrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUsr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUsrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLim.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbConMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbConMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbCon.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbConMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosrTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosrTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosrT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosrTMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBUsrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUsrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUsr.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBUsrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLim.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBLimMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbConMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbConMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProbCon.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbConMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosrTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosrTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosrT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosrTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Coloca el cursor al principio del control*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se presiona una tecla en el campo del asunto de CXC1*/
    private void jTAsunCXC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunCXC1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunCXC1KeyPressed

    
    /*Cuando se presiona una tecla en el campo del asunto de CXC2*/
    private void jTAsunCXC2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunCXC2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunCXC2KeyPressed

    
    /*Cuando se presiona una tecla en el campo del asunto de CXC3*/
    private void jTAsunCXC3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAsunCXC3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAsunCXC3KeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de CXC1*/
    private void jTCuerCXC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerCXC1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerCXC1KeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de CXC2*/
    private void jTCuerCXC2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerCXC2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerCXC2KeyPressed

    
    /*Cuando se presiona una tecla en el campo del cuerpo de CXC3*/
    private void jTCuerCXC3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCuerCXC3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCuerCXC3KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del asunto de CXC1*/
    private void jTAsunCXC1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunCXC1.setSelectionStart(0);jTAsunCXC1.setSelectionEnd(jTAsunCXC1.getText().length());                
        
    }//GEN-LAST:event_jTAsunCXC1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del asunto de CXC2*/
    private void jTAsunCXC2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunCXC2.setSelectionStart(0);jTAsunCXC2.setSelectionEnd(jTAsunCXC2.getText().length());                
        
    }//GEN-LAST:event_jTAsunCXC2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del asunto de CXC3*/
    private void jTAsunCXC3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAsunCXC3.setSelectionStart(0);jTAsunCXC3.setSelectionEnd(jTAsunCXC3.getText().length());                
        
    }//GEN-LAST:event_jTAsunCXC3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de CXC1*/
    private void jTCuerCXC1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerCXC1.setSelectionStart(0);jTCuerCXC1.setSelectionEnd(jTCuerCXC1.getText().length());                
        
    }//GEN-LAST:event_jTCuerCXC1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de CXC2*/
    private void jTCuerCXC2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerCXC2.setSelectionStart(0);jTCuerCXC2.setSelectionEnd(jTCuerCXC2.getText().length());                
        
    }//GEN-LAST:event_jTCuerCXC2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cuerpo de CXC3*/
    private void jTCuerCXC3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCuerCXC3.setSelectionStart(0);jTCuerCXC3.setSelectionEnd(jTCuerCXC3.getText().length());                
        
    }//GEN-LAST:event_jTCuerCXC3FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del asunto de CXC1*/
    private void jTAsunCXC1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC1FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTAsunCXC1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunCXC1.getText().compareTo("")!=0)
            jTAsunCXC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunCXC1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del asunto de CXC2*/
    private void jTAsunCXC2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC2FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTAsunCXC2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunCXC2.getText().compareTo("")!=0)
            jTAsunCXC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunCXC2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del asunto de CXC3*/
    private void jTAsunCXC3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAsunCXC3FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTAsunCXC3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAsunCXC3.getText().compareTo("")!=0)
            jTAsunCXC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAsunCXC3FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cuerpo de CXC1*/
    private void jTCuerCXC1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC1FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCuerCXC1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerCXC1.getText().compareTo("")!=0)
            jTCuerCXC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerCXC1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cuerpo de CXC2*/
    private void jTCuerCXC2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC2FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCuerCXC2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerCXC2.getText().compareTo("")!=0)
            jTCuerCXC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerCXC2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del cuerpo de CXC3*/
    private void jTCuerCXC3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCuerCXC3FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCuerCXC3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCuerCXC3.getText().compareTo("")!=0)
            jTCuerCXC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCuerCXC3FocusLost

    
    /*Cuando la forma se activa */
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Coloca todos los campos al principio*/
        jTAsunCXC1.setCaretPosition     (0);
        jTAsunCXC2.setCaretPosition     (0);
        jTAsunCXC3.setCaretPosition     (0);
        jTAsunContra.setCaretPosition   (0);
        jTAsunCot.setCaretPosition      (0);
        jTAsunFac.setCaretPosition      (0);
        jTAsunOrd.setCaretPosition      (0);
        jTBusc.setCaretPosition         (0);
        jTCorAlter.setCaretPosition     (0);
        jTCuerCXC1.setCaretPosition     (0);
        jTCuerCXC2.setCaretPosition     (0);
        jTCuerCXC3.setCaretPosition     (0);
        jTCuerContr.setCaretPosition    (0);
        jTCuerCot.setCaretPosition      (0);
        jTCuerFac.setCaretPosition      (0);
        jTCuerOrd.setCaretPosition      (0);
        jTEsta.setCaretPosition         (0);
        jTID.setCaretPosition           (0);
        jTPortSMTP.setCaretPosition     (0);
        jTServSMTPSal.setCaretPosition  (0);
        jTUsr.setCaretPosition          (0);
    
    }//GEN-LAST:event_formWindowActivated

    private void jTAsunCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTAsunCotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTAsunCotActionPerformed

    private void jTAsunContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTAsunContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTAsunContraActionPerformed

        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosrT.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F7 presiona el botón de limpiar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F7)
            jBLim.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBLim;
    private javax.swing.JButton jBMosrT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProbCon;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBUsr;
    private javax.swing.JCheckBox jCActSSLLog;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPasswordField jPContra;
    private javax.swing.JPanel jPan1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTAsunCXC1;
    private javax.swing.JTextField jTAsunCXC2;
    private javax.swing.JTextField jTAsunCXC3;
    private javax.swing.JTextField jTAsunContra;
    private javax.swing.JTextField jTAsunCot;
    private javax.swing.JTextField jTAsunFac;
    private javax.swing.JTextField jTAsunOrd;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCorAlter;
    private javax.swing.JTextField jTCuerCXC1;
    private javax.swing.JTextField jTCuerCXC2;
    private javax.swing.JTextField jTCuerCXC3;
    private javax.swing.JTextField jTCuerContr;
    private javax.swing.JTextField jTCuerCot;
    private javax.swing.JTextField jTCuerFac;
    private javax.swing.JTextField jTCuerOrd;
    private javax.swing.JTextField jTEsta;
    private javax.swing.JTextField jTID;
    private javax.swing.JTextField jTPortSMTP;
    private javax.swing.JTextField jTServSMTPSal;
    private javax.swing.JTextField jTUsr;
    private javax.swing.JTable jTab;
    private javax.swing.JTabbedPane jTabPan1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class CorrElecs extends javax.swing.JFrame*/
