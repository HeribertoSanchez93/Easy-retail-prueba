//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static ptovta.Princip.bIdle;




/*Clase para administrar todo lo referente a las bases de datos*/
public class BDs extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/    
    private int                 iContFi;
    private int                 iContCellEd;

    /*Variable que contiene el borde del botón de búscar orginal*/
    private Border bBordOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Declara variables originales*/
    private String              sFCreaOri;
    private String              sFUltOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
        
    
    /*Constructor sin argumentos*/
    public BDs() 
    {        
        /*Inicializa los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCrea);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Bases de datos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en uno*/
        iContFi                  = 1;
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
                
        /*Establece el tamaño de las columnas*/
        //jTableBasesDatos.getColumnModel().getColumn(0).setPreferredWidth(2);
                
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
                
        /*Obtén todos los registros de bases de datos de la base de datos y cargalos en la tabla*/
        vCargBD();
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Mientras el código de la nueva empresa este repetido seguirá obteniendo uno que no este repetido*/
        String sEmp;
        boolean bSi;
        do
        {
            /*Inicialmente no existe el código de la factura*/
            bSi = false;
            
            /*Obtiene el código de la nueva factura*/
            sEmp                    = Star.sGenClavDia();

            //Declara variables de la base de datos    
            Statement   st;
            String      sQ;
            ResultSet   rs;                

            /*Obtiene si ya existe ese código de empresa en la base de datos*/
            try
            {                  
                sQ = "SELECT codemp FROM basdats WHERE codemp = '" + sEmp + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces coloca la bandera*/
                if(rs.next())
                    bSi = true;                    
            }
            catch(SQLException expnSQL)
            {    
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                               
            }
            
        }while(bSi);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el código de la empresa en su campo*/
        jTCodEmp.setText(sEmp);
        
        /*Crea el listener para la tabla de bases de datos, para cuando se modifique una celda guardarla en la base de datos el cambio*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                //Si no hay selecciòn entonces regresa
                if(jTab.getSelectedRow()==-1)
                    return;

                //Declara variables de la base de datos    
                Statement   st;
                String      sQ;
                ResultSet   rs;                

                /*Declara variables de bloque*/
                String      sCodE;
                String      sNomE;
                String      sServ;
                String      sUse;
                String      sContra;
                String      sBD;
                String      sFMod   = "";
                String      sEsta   = "";                                
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {              
                    //Obtiene la informaciòn original
                    sCodE               = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                    sNomE               = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();          
                    sServ               = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                    sUse                = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                    sContra             = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                    sBD                 = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                    
                    sFCreaOri           = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();                    
                    sFUltOri            = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                    
                    sSucOri             = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                    
                    sCajOri             = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();                    
                    sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                    
                    sNomEstacOri        = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                    
                                                            
                    /*Si el campo de nom de la empresa excede la cantidad de caes permitidos recortalo*/
                    if(sNomE.length()> 21)
                        sNomE = sNomE.substring(0, 255);
                    
                    /*Si el campo del serv excede la cantidad de caes permitidos recortalo*/
                    if(sServ.length()> 255)
                        sServ = sServ.substring(0, 255);
        
                    /*Si el campo del usr excede la cantidad de caes permitidos recortalo*/
                    if(sUse.length()> 255)
                        sUse = sUse.substring(0, 255);
                    
                    /*Si el campo de la contraseña excede la cantidad de caes permitidos recortalo*/
                    if(sContra.length()> 255)
                        sContra = sContra.substring(0, 255);
                    
                    /*Si el campo la base de datos excede la cantidad de caes permitidos recortalo*/
                    if(sBD.length()> 255)
                        sBD = sBD.substring(0, 255);
                    
                    //Abre la base de datos                             
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    /*Actualiza las bases de datos en la base de datos*/
                    try 
                    {                        
                        sQ = "UPDATE basdats SET "
                                + "nom          = '" + sNomE.replace("'", "''") + "', "
                                + "serv         = '" + Star.sEncrip(sServ) + "', "
                                + "usr          = '" + Star.sEncrip(sUse) + "', "
                                + "pass         = '" + Star.sEncrip(sContra) + "', "
                                + "bd           = '" + Star.sEncrip(sBD) + "', "
                                + "fmod         = now(), "
                                + "estac        = '" + Login.sUsrG.replace("'", "''") + "', "
                                + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                                + "nocaj        = '" + Star.sSucu.replace("'", "''") + "' "
                                + "WHERE codemp = '" + sCodE.replace("'", "''") + "'";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                        
                     }     
                    
                    /*Si el contador de cell editor va de salida entonces*/
                    if(iContCellEd>=2)
                    {
                        /*Coloca los valores originales que tenía*/
                        jTab.setValueAt(sFCreaOri,      jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sFUltOri,       jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 11);
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 12);
                                            
                        /*Obtiene la fecha de la última modificación y usuario de la fila del registro*/                        
                        try
                        {
                            sQ = "SELECT fmod, estac FROM basdats WHERE codemp = '" + sCodE + "'";                   
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos*/
                            if(rs.next())
                            {
                                /*Obtiene elcódigo del cliente*/
                                sFMod           = rs.getString("fmod");
                                
                                /*Obtiene El usuario que realizó el último cambio*/
                                sEsta           = rs.getString("estac");
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                           
                        }       
                        
                        /*Colca los valores obtenidos en la fila*/
                        jTab.setValueAt(sFMod, jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sEsta, jTab.getSelectedRow(), 9);
                        
                    }
                    /*Else, va de entrada entonces sumale uno*/
                    else
                        ++iContCellEd;                                                                                
                    
                    //Cierra la base de datos
                    Star.iCierrBas(con);
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
        };
        
        /*Establece el listener para la tabla de bases de datos*/
        jTab.addPropertyChangeListener(pr);
        
    }/*Fin de public CorreosElectronicos() */
    
    
    /*Obtén todos los reigstors de base de datos de la base de datos y cargalos en la tabla*/
    private void vCargBD()
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
        
        /*Obtiene todos las registros dados de alta*/
        try
        {
            sQ = "SELECT IFNULL(estacs.NOM, '') AS usrNom, basdats.CODEMP, basdats.NOM, basdats.SERV, basdats.USR, basdats.PASS, basdats.FALT, basdats.FMOD, basdats.BD, basdats.ESTAC, basdats.NOCAJ, basdats.SUCU FROM basdats LEFT OUTER JOIN estacs ON estacs.ESTAC = basdats.ESTAC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                                
                /*Agregalos en la tabla de bases de datos*/
                DefaultTableModel tm        = (DefaultTableModel)jTab.getModel();
                Object nu[]                 = {iContFi, rs.getString("basdats.CODEMP"), rs.getString("basdats.NOM"), Star.sDecryp(rs.getString("basdats.SERV")), Star.sDecryp(rs.getString("basdats.USR")), Star.sDecryp(rs.getString("basdats.PASS")), Star.sDecryp(rs.getString("basdats.BD")), rs.getString("basdats.FALT"), rs.getString("basdats.FMOD"), rs.getString("basdats.SUCU"), rs.getString("basdats.NOCAJ"), rs.getString("basdats.ESTAC"), rs.getString("usrNom")};
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
        
    }/*Fin de private void vCargBD()*/   
           
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBLimp = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTBD = new javax.swing.JTextField();
        jTContra = new javax.swing.JTextField();
        jTUsr = new javax.swing.JTextField();
        jTServ = new javax.swing.JTextField();
        jTCodEmp = new javax.swing.JTextField();
        jTNomEmp = new javax.swing.JTextField();
        jBProbCon = new javax.swing.JButton();
        jBCrea = new javax.swing.JButton();
        jBProb2 = new javax.swing.JButton();
        jCDelBD = new javax.swing.JCheckBox();
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBTod = new javax.swing.JButton();

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

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Base de Datos (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBLimp);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 240, 110, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Base(s) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 210, 110, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Bases de Datos:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 170, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTNomEmp);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 330, 110, 30));

        jBLimp.setBackground(new java.awt.Color(255, 255, 204));
        jBLimp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLimp.setForeground(new java.awt.Color(0, 102, 0));
        jBLimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLimp.setText("Limpiar");
        jBLimp.setToolTipText("Limpiar todos los Campos (F9)");
        jBLimp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLimp.setNextFocusableComponent(jBProb2);
        jBLimp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimpMouseExited(evt);
            }
        });
        jBLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpActionPerformed(evt);
            }
        });
        jBLimp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimpKeyPressed(evt);
            }
        });
        jP1.add(jBLimp, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 270, 110, 30));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosT);
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 520, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jCDelBD);
        jBMosT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMosTMouseExited(evt);
            }
        });
        jBMosT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMosTActionPerformed(evt);
            }
        });
        jBMosT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMosTKeyPressed(evt);
            }
        });
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 450, 140, 20));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("*Nombre Empresa:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, -1));

        jLabel8.setText("Cod. Empresa:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, -1));

        jLabel9.setText("*Servidor:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 170, -1));

        jLabel10.setText("*Usuario:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, -1));

        jLabel11.setText("*Contraseña:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 170, -1));

        jLabel6.setText("*BD:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 170, -1));

        jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBD.setNextFocusableComponent(jBProbCon);
        jTBD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBDFocusLost(evt);
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
        jPanel2.add(jTBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 130, 20));

        jTContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTContra.setNextFocusableComponent(jTBD);
        jTContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTContraFocusLost(evt);
            }
        });
        jTContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTContraKeyPressed(evt);
            }
        });
        jPanel2.add(jTContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 130, 20));

        jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUsr.setNextFocusableComponent(jTContra);
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
        jPanel2.add(jTUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 130, 20));

        jTServ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTServ.setNextFocusableComponent(jTUsr);
        jTServ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTServFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTServFocusLost(evt);
            }
        });
        jTServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTServKeyPressed(evt);
            }
        });
        jPanel2.add(jTServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 130, 20));

        jTCodEmp.setEditable(false);
        jTCodEmp.setForeground(new java.awt.Color(51, 51, 255));
        jTCodEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        });
        jPanel2.add(jTCodEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 130, 20));

        jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomEmp.setNextFocusableComponent(jTServ);
        jTNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomEmpFocusLost(evt);
            }
        });
        jTNomEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomEmpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNomEmpKeyTyped(evt);
            }
        });
        jPanel2.add(jTNomEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 250, 20));

        jBProbCon.setBackground(new java.awt.Color(255, 255, 255));
        jBProbCon.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProbCon.setForeground(new java.awt.Color(0, 102, 0));
        jBProbCon.setText("Probar F5");
        jBProbCon.setNextFocusableComponent(jBCrea);
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
        jPanel2.add(jBProbCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 110, 30));

        jBCrea.setBackground(new java.awt.Color(255, 255, 255));
        jBCrea.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCrea.setForeground(new java.awt.Color(0, 102, 0));
        jBCrea.setText("Crear F7");
        jBCrea.setNextFocusableComponent(jTab);
        jBCrea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCreaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCreaMouseExited(evt);
            }
        });
        jBCrea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCreaActionPerformed(evt);
            }
        });
        jBCrea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCreaKeyPressed(evt);
            }
        });
        jPanel2.add(jBCrea, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 110, 30));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 470, 140));

        jBProb2.setBackground(new java.awt.Color(255, 255, 255));
        jBProb2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBProb2.setForeground(new java.awt.Color(0, 102, 0));
        jBProb2.setText("Probar");
        jBProb2.setToolTipText("Probar Conexión a la Base de Datos (F6)");
        jBProb2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBProb2.setNextFocusableComponent(jBSal);
        jBProb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProb2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProb2MouseExited(evt);
            }
        });
        jBProb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProb2ActionPerformed(evt);
            }
        });
        jBProb2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProb2KeyPressed(evt);
            }
        });
        jP1.add(jBProb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 300, 110, 30));

        jCDelBD.setBackground(new java.awt.Color(255, 255, 255));
        jCDelBD.setSelected(true);
        jCDelBD.setText("Borrar BD");
        jCDelBD.setNextFocusableComponent(jBDel);
        jCDelBD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCDelBDKeyPressed(evt);
            }
        });
        jP1.add(jCDelBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 180, 110, -1));

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
        jP1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(816, 460, 120, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Empresa", "Nom.Empresa", "Servidor", "Usuario", "Contrasenia", "BD", "F. Creación", "F.Última Modificación", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 800, 240));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 192, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        /*Lee el código de la empresa*/
        String sEmp            = jTCodEmp.getText().trim();
        
        /*Lee el nom de la empresa*/
        String sNomEmp         = jTNomEmp.getText().trim();
        
        /*Si el campo del nom de la empresa esta vacio no puede seguir*/
        if(sNomEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del nombre de la empresa esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTNomEmp.grabFocus();           
            return;
        }
        
        /*Lee el servidor*/
        String sServ   = jTServ.getText().trim();
        
        /*Si el campo de serv esta vacio no puede seguir*/
        if(sServ.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTServ.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del servidor esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTServ.grabFocus();           
            return;
        }
        
        /*Lee el usuario*/
        String sUsr    = jTUsr.getText().trim();
        
        /*Si el campo de usr esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTUsr.grabFocus();           
            return;
        }
        
        /*Lee la contraseña*/
        String sContra    = jTContra.getText().trim();
        
        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTContra.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTContra.grabFocus();           
            return;
        }
        
        /*Lee la BD*/
        String sBD             = jTBD.getText().trim();
        
        /*Si el campo de bd esta vacio no puede seguir*/
        if(sBD.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de BD esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTBD.grabFocus();
            return;
        }
        
        /*Recorre toda la tabla de registros para saber si ya estan usando este nom de base de datos*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la base de datos del registro*/
            String sBas   = jTab.getValueAt(x, 6).toString();
            
            /*Si la base de datos es igual a la que el usr quiere ingresar entonces*/
            if(sBD.compareTo(sBas)==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La base de datos \"" + sBD + "' ya esta en uso.", "Base de Datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTBD.grabFocus();               
                return;
            }
            
        }/*Fin de for(int x = 0; x < jTableBasesDatos.getRowCount(); x++)*/
        
        //Abre la base de datos                             
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Pregunta al usr si esta seguro de agregar la conexión*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Nuevo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        /*Cambia el cursor del mouse en espera*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        /*Encripta los datos de la conexión a la base de datos*/
        sServ           = Star.sEncrip(sServ);                                                        
        sContra         = Star.sEncrip(sContra);                                                        
        sUsr            = Star.sEncrip(sUsr);                                                        
        sBD             = Star.sEncrip(sBD);                                                        
        
        //Declara variables de la base de datos    
        Statement   st;
        String      sQ;
        ResultSet   rs;        
        
        /*Inserta el registro en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO basdats(      codemp,                             nom,                                            serv,                                       usr,                                    pass,                                       bd,                                     estac,                                      falt,           sucu,                                           nocaj)" + 
                              "VALUES('" +  sEmp.replace("'", "''") + "','" +   sNomEmp.replace("'", "''") + "', '" +           sServ.replace("'", "''") + "', '" +         sUsr.replace("'", "''") + "', '" +      sContra.replace("'", "''") + "', '" +       sBD.replace("'", "''") + "',   '" +     Login.sUsrG.replace("'", "''") + "',    now(),'" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                        
         }
                
        /*Obtiene la fecha de creación del registro*/
        String sFAlta   = "";
        try
        {
            sQ = "SELECT falt FROM basdats ORDER BY id_basdat DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtén la consulta*/
            if(rs.next())
                 sFAlta     = rs.getString("falt");   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                        
        }            
        
        /*Obtiene el nom de la estación*/
        String sNomb    = "";
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtén la consulta*/
            if(rs.next())
                 sNomb  = rs.getString("nom");   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                        
        }            
        
        /*Agregalos en la tabla de bases de datos*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, sEmp, sNomEmp, Star.sDecryp(sServ), Star.sDecryp(sUsr), Star.sDecryp(sContra), Star.sDecryp(sBD), sFAlta, sFAlta, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};
        tm.addRow(nu);

        /*Aumenta en uno el contador de filas en uno*/
        ++iContFi;
        
        /*Recorre toda la tabla de registros para sincronizar con todas las bases de datos los registros*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Declara variables de bloque*/
            String  sServ1;
            String  sUser;
            String  sContra1;            
            String  sBas;
            
            /*Obtiene todos los datos de la fila*/
            sServ1      = jTab.getValueAt(x, 3).toString();
            sUser       = jTab.getValueAt(x, 4).toString();
            sContra1    = jTab.getValueAt(x, 5).toString();
            sBas        = jTab.getValueAt(x, 6).toString();
                        
            /*Sincroniza en esta base de datos todos los registros de la tabla*/
            vSincroniza(sServ1, sUser, sContra1, sBas);
            
        }/*Fin de for(int x = 0; x < jTableBasesDatos.getRowCount(); x++)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Cambia el cursor del mouse a normal*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Registro agregado con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Sincroniza en esta base de datos todos los registros de la tabla*/
    private void vSincroniza(String sServ, String sUser, String sContra, String sBas)
    {                
        /*Declara variables de bloque*/
        String      sCodE;
        String      sNomb;
        String      sSer;
        String      sUse;
        String      sCont;
        String      sBD;
        String      sFC;
        String      sFM;
        String      sEsta;
        
        
        /*Recorre toda la tabla de registros para sincronizar con esta base de datos los registros*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Lee todos los datos de la fila*/
            sCodE       = jTab.getValueAt(x, 1).toString();
            sNomb       = jTab.getValueAt(x, 2).toString();
            sSer        = jTab.getValueAt(x, 3).toString();
            sUse        = jTab.getValueAt(x, 4).toString();
            sCont       = jTab.getValueAt(x, 5).toString();
            sBD         = jTab.getValueAt(x, 6).toString();
            sFC         = jTab.getValueAt(x, 7).toString();
            sFM         = jTab.getValueAt(x, 8).toString();
            sEsta       = jTab.getValueAt(x, 9).toString();
                        
            /*Abre la base de datos distinta*/        
            Connection con;
            try 
            {
                con = DriverManager.getConnection("jdbc:mysql://" + sServ + "/" + sBas + "?user=" + sUser + "&password=" + sContra );               
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

            /*Comprueba si este registro ya existe en esta base de datos en la tabla bases datos*/            
            try
            {
                sQ = "SELECT codemp FROM basdats WHERE codemp = '" + sCodE + "'";                   
                st   = con.createStatement();
                rs     = st.executeQuery(sQ);
                /*Si hay datos entonces continua*/
                if(rs.next())
                    continue;
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                               
            }          
                    
            /*Encripta los datos de la conexión a la base de datos*/
            sSer            = Star.sEncrip(sSer);                                                        
            sUse            = Star.sEncrip(sUse);                                                        
            sCont           = Star.sEncrip(sCont);                                                        
            sBD             = Star.sEncrip(sBD);                                                        
        
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO basdats (      codemp,                                    nom,                                            serv,                                       usr,                                    pass,                                       bd,                                     estac,                                      falt,                               fmod,                                       sucu,                                           nocaj)" + 
                                 "VALUES('" +   sCodE.replace("'", "''") + "','" +          sNomb.replace("'", "''") + "', '" +             sSer.replace("'", "''")+ "', '" +           sUse.replace("'", "''") + "', '" +      sCont.replace("'", "''") + "', '" +         sBD.replace("'", "''") + "',   '" +     sEsta.replace("'", "''") + "',        '" +  sFC.replace("'", "''") + "','" +    sFM.replace("'", "''") + "', '" +           Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                                
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
            
        }/*Fin de for(int x = 0; x < jTableBasesDatos.getRowCount(); x++)*/
        
    }/*Fin de private void vSincroniza(String sServ, String sUser, String sContra, String sBas)*/
    
            
    /*Cuando se presiona una tecla en el botón de nu*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        //Declara variables locales
        String      sEmp;        
        String      sBD;
                        
                
                
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un registro de la lista para borrar.", "Borrar Registro", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
        
        /*Obtén la fila seleccionada*/
        int row         = jTab.getSelectedRow();        
        
        /*Si el registro que quiere eliminar es la conexión actual entonces*/
        if(jTab.getValueAt(row, 3).toString().compareTo(Star.sInstancia)==0 && jTab.getValueAt(row, 4).toString().compareTo(Star.sUsuario)==0 && jTab.getValueAt(row, 5).toString().compareTo(Star.sContrasenia)==0 && jTab.getValueAt(row, 6).toString().compareTo(Star.sBD)==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se puede eliminar este registro ya que es la conexión actual.", "Borrar Registro", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
        
        /*Pregunta si esta seguro de borrar el registro*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el registro?", "Borrar Registro", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
             
        /*Cambia el cursor del mouse en espera*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        /*Obtiene el código de la empresa y la base de datos*/
        sEmp     = jTab.getValueAt(row, 1).toString();                          
        sBD      = jTab.getValueAt(row, 6).toString();   
                
        /*Recorre toda la tabla de registros para sincronizar el borrado con todas las bases de datos los registros*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            //Declara variables locales            
            String sServ;
            String sUser;
            String sContra;
            String sB;
            
            /*Obtiene todos los registros de la fila*/            
            sServ       = jTab.getValueAt(x, 3).toString();
            sUser       = jTab.getValueAt(x, 4).toString();
            sContra     = jTab.getValueAt(x, 5).toString();
            sB          = jTab.getValueAt(x, 6).toString();                    
            
            /*Borra de esta base de datos el registro*/
            vDelRegSinc(sEmp, sServ, sUser, sContra, sB);                        
            
        }/*Fin de for(int x = 0; x < jTableBasesDatos.getRowCount(); x++)*/
        
        
        /*Si esta seleccionado que también borre la base de datos entonces*/
        if(jCDelBD.isSelected())
        {
            //Abre la base de datos                             
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos    
            Statement   st;
            String      sQ;               

            /*Borra el esquema en mysql*/
            try 
            {                
                sQ = "DROP DATABASE " + sBD;                                
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
                       
        }/*Fin de if(jCDelBD.isSelected())*/                    
        
        /*Borralo de la tabla*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();
        tm.removeRow(row);
        
        /*Resta en uno el contador de filas en uno*/
        --iContFi;
        
        /*Cambia el cursor del mouse al normal*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Éxito al borrar registro \"" + sBD + "\".", "Borrar Registro", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Borra de esta base de datos el registro*/
    private void vDelRegSinc(String sCodE, String sServ, String sUser, String sContra, String sBD)
    {
        /*Abre la base de datos distinta*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + sServ + "/" + sBD + "?user=" + sUser + "&password=" + sContra );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;                                                                        
        }

        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        /*Borra el registro en la base de datos*/
        try 
        {            
            sQ = "DELETE FROM basdats WHERE codemp = '" + sCodE + "'";                                
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
        
    }/*Fin de private void vDelRegSinc(String sCodE, String sServ, String sUser, String sContra, String sBD)*/
           
            
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
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();       
            this.dispose();                        
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el campo de nom de empresa*/
    private void jTNomEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomEmpKeyPressed

        
    /*Cuando se gana el foco del teclado en el campo de nom de empresa*/
    private void jTNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomEmp.setSelectionStart(0);jTNomEmp.setSelectionEnd(jTNomEmp.getText().length());        
        
    }//GEN-LAST:event_jTNomEmpFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de nom de empresa*/
    private void jTNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusLost

        /*Coloca el cursor al principio del control*/
        jTNomEmp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNomEmp.getText().compareTo("")!=0)
            jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        else
            jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNomEmp.getText().length()> 255)
            jTNomEmp.setText(jTNomEmp.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTNomEmpFocusLost

    
    /*Cuando se presiona una tecla typed en el campo de nom de empresa*/
    private void jTNomEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNomEmpKeyTyped

    
    /*Cuando se presiona una tecla en el botón de probar conexión*/
    private void jBProbConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbConKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbConKeyPressed

    
    /*Cuando se presiona el botón de probar conexión con la base de datos*/
    private void jBProbConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbConActionPerformed
        
        //Declara variables locales
        String      sServ;
        String      sUsr;
        String      sContra;
        String      sBD;
        
        //Declara variables de la base de datos
        Connection  con;          

        
        
        
        /*Lee el serv*/
        sServ   = jTServ.getText().trim();
        
        /*Si el campo de serv esta vacio no puede seguir*/
        if(sServ.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del serv esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            jTServ.grabFocus();

            /*Regresa*/
            return;

        }
        
        /*Lee el usr*/
        sUsr    = jTUsr.getText().trim();
        
        /*Si el campo de usr esta vacio no puede seguir*/
        if(sUsr.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usr esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            jTUsr.grabFocus();

            /*Regresa*/
            return;

        }
        
        /*Lee la contraseña*/
        sContra    = jTContra.getText().trim();
        
        /*Si el campo de contraseña esta vacio no puede seguir*/
        if(sContra.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de contraseña esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo*/
            jTContra.grabFocus();

            /*Regresa*/
            return;

        }
        
        /*Lee la BD*/
        sBD             = jTBD.getText().trim();
        
        /*Si el campo de bd esta vacio no puede seguir*/
        if(sBD.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de BD esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTBD.grabFocus();           
            return;
        }
        
        /*Abre la base de datos para probar la conexión*/        
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + sServ + "/" + sBD + "?user=" + sUsr + "&password=" + sContra );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;                                                                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
            
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Conexión exitosa.", "Base de Datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBProbConActionPerformed
            
    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing
            
    
    /*Cuando se presiona una tecla en el botón de limpiar*/
    private void jBLimpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimpKeyPressed

    
    /*Cuando se presiona el botón de limpiar controles*/
    private void jBLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpActionPerformed
        
        /*Limpia todos los controles*/
        jTNomEmp.setText        ("");
        jTCodEmp.setText        ("");
        jTServ.setText          ("");
        jTUsr.setText           ("");
        jTContra.setText        ("");
        jTBD.setText            ("");
        jCDelBD.setSelected     (true);
                
    }//GEN-LAST:event_jBLimpActionPerformed

    
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
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtén todos los registros de la base de datos y cargalos en la tabla*/
        vCargBD();
        
        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();           
            return;
        }                      
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de bases de datos*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        /*Obtiene de la base de datos todos los registros*/        
        try
        {                  
            sQ = "SELECT estacs.NOM, basdats.CODEMP, basdats.NOM, basdats.USR, basdats.PASS, basdats.BD, basdats.FALT, basdats.FMOD  FROM basdats LEFT OUTER JOIN estacs ON estacs.ESTAC = basdats.ESTAC WHERE codemp LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.SERV LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.PASS LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.USR LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.BD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR basdats.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                DefaultTableModel tm = (DefaultTableModel)jTab.getModel();
                Object nu[]          = {iContFi, rs.getString("basdats.CODEMP"), rs.getString("basdats.NOM"), Star.sDecryp(rs.getString("basdats.SERV")), Star.sDecryp(rs.getString("basdats.USR")), Star.sDecryp(rs.getString("basdats.PASS")), Star.sDecryp(rs.getString("basdats.BD")), rs.getString("basdats.FALT"), rs.getString("basdats.FMOD"), rs.getString("basdats.SUCU"), rs.getString("basdats.NOCAJ"), rs.getString("basdats.ESTAC"), rs.getString("estacs.NOM")};
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

    
    /*Cuando se gana el foco del teclado en el campo de código de empresa*/
    private void jTCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodEmp.setSelectionStart(0);jTCodEmp.setSelectionEnd(jTCodEmp.getText().length());                
        
    }//GEN-LAST:event_jTCodEmpFocusGained
    
    
    /*Cuando se presiona una tecla en el campo del código de la empresa*/
    private void jTCodEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodEmpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del serv*/
    private void jTServFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTServFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTServ.setSelectionStart(0);jTServ.setSelectionEnd(jTServ.getText().length());                
        
    }//GEN-LAST:event_jTServFocusGained

    
    /*Cuando se gan el foco del teclado en el campo de usr*/
    private void jTUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUsr.setSelectionStart(0);jTUsr.setSelectionEnd(jTUsr.getText().length());        
        
    }//GEN-LAST:event_jTUsrFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de contraseña*/
    private void jTContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTContra.setSelectionStart(0);jTContra.setSelectionEnd(jTContra.getText().length());                
        
    }//GEN-LAST:event_jTContraFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de base de datos*/
    private void jTBDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBD.setSelectionStart(0);jTBD.setSelectionEnd(jTBD.getText().length());                
        
    }//GEN-LAST:event_jTBDFocusGained

    
    /*Cuando se presiona una tecla en el campo el serv*/
    private void jTServKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTServKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTServKeyPressed

    
    /*Cuando se presiona una tecla en el campo de usr*/
    private void jTUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUsrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUsrKeyPressed

    
    /*Cuando se presiona una tecla en el campo de contraseña*/
    private void jTContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTContraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTContraKeyPressed

    
    /*Cuando se presiona una tecla en el campo de BD*/
    private void jTBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBDKeyPressed

    
    /*Cuando se presiona el botón de probar 2*/
    private void jBProb2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProb2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProb2KeyPressed

    
    /*Cuando se presiona el botón de probar 2*/
    private void jBProb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProb2ActionPerformed
        
        //Declara variables locales
        String      sServ;
        String      sUsr;
        String      sContra;
        String      sBD;
        String      sNomEmp;
        
        //Declara variables de la base de datos
        Connection  con;          

        
        
        
        /*Si no hay selección en la tabla de reigstros no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un registro de la lista para probar.", "Probar Registro", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla de registros y regresa*/
            jTab.grabFocus();                        
            return;            
        }
        
        /*Obtén los datos para conexión de la fila seleccionada*/
        int row         = jTab.getSelectedRow();        
        sNomEmp         = jTab.getValueAt(row, 2).toString();          
        sServ           = jTab.getValueAt(row, 3).toString();          
        sUsr            = jTab.getValueAt(row, 4).toString();          
        sContra         = jTab.getValueAt(row, 5).toString();          
        sBD             = jTab.getValueAt(row, 6).toString();          
                        
        /*Abre la base de datos para probar la conexión*/        
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + sServ + "/" + sBD + "?user=" + sUsr + "&password=" + sContra );               
        } 
        catch(SQLException expnSQL) 
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace());                                
            return;                                                                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Conexión exitosa para empresa: " + sNomEmp + ".", "Base de Datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBProb2ActionPerformed

            
    /*Cuando se presiona una tecla en el botón de crear*/
    private void jBCreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCreaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCreaKeyPressed

    
    /*Cuando se presiona el botón de crear*/
    private void jBCreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCreaActionPerformed
        
        //Declara variables locales
        String sBD;
        String sNomEmp;
        String sEmp;
        
        
        
        /*Lee el código de la empresa que se generó automáticamente*/
        sEmp  =jTCodEmp.getText().trim();
        
        /*Si el códigode la empresa que ingreso es cadena vacia entonces*/
        if(sEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCodEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del código de la empresa esta vacia.", "Cod. Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTCodEmp.grabFocus();                        
            return;
        }
        
        /*Lee el nom de la empresa que escribió el usr*/
        sNomEmp  =jTNomEmp.getText().trim();
        
        /*Si el nom de la empresa que ingreso es cadena vacia entonces*/
        if(sNomEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del nom de la empresa esta vacia.", "Nombre Empresa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTNomEmp.grabFocus();                        
            return;
        }
        
        /*Lee la base de datos que escribió el usr*/
        sBD =jTBD.getText().trim();
        
        /*Si la base de datos que ingreso es cadena vacia entonces*/
        if(sBD.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de bases de datos esta vacia.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTBD.grabFocus();                        
            return;
        }
        
        /*Recorre toda la tabla de registros para saber si ya estan usando este nom de base de datos*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la base de datos del registro*/
            String sBas   = jTab.getValueAt(x, 6).toString();
            
            /*Si la base de datos es igual a la que el usr quiere ingresar entonces*/
            if(sBD.compareTo(sBas)==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La base de datos \"" + sBD + "' ya esta en uso.", "Base de Datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTBD.grabFocus();           
                return;
            }            
        }
        
        /*Preguntar al usr si esta seguro de crear la base de datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la base de datos?", "Crear BD", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Cambia el cursor del mouse en espera*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Cambia el cursor del mouse al normal*/
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
        /*Coloca el nu código de la empresa*/
        jTCodEmp.setText(Star.sGenClavDia());
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito al crear la base de datos \"" + sBD + "\".", "Base de Datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
    }//GEN-LAST:event_jBCreaActionPerformed

    
    /*Cuando se tipea una tecla en el campo de base de datos*/
    private void jTBDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBDKeyTyped
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTBD.getText().length()> 255)        
            jTBD.setText(jTBD.getText().substring(0, 255));        
        
        /*Si el carácter escrito no es un ca imprimible regresar*/
        if(!(evt.getKeyChar() >= 32 && evt.getKeyChar() <= 126))
            return;
                
        /*Lee los caes introducidos anteriormente por el usr*/
        String sCade = jTBD.getText();
                
        /*Si el carácter introducido es minúscula entonces conviertelo a mayúsculas*/
        char ca         = evt.getKeyChar();
        if(Character.isLowerCase(ca))                    
            ca = Character.toUpperCase(ca);                                
        
        /*No escribas la letra*/
        evt.consume();        
            
        /*Si no es back space*/
        if(ca != '\b')
        {
            /*Forma la cadena*/
            sCade += Character.toString(ca);
            
            /*Coloca la cadena en el campo*/
            jTBD.setText(sCade);
            
        }
        
    }//GEN-LAST:event_jTBDKeyTyped

    
    /*Cuando se presiona una tecla en el check box de borrar base de datos*/
    private void jCDelBDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCDelBDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCDelBDKeyPressed
                         
    
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

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona el botón de ver la tabla en grande*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver la tabla en grande*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
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

    
    /*Cuando se pierde el foco del teclado en el campo del servidor*/
    private void jTServFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTServFocusLost

        /*Coloca el cursor al principio del control*/
        jTServ.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTServ.getText().compareTo("")!=0)
            jTServ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        else
            jTServ.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
        
    }//GEN-LAST:event_jTServFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del usuario*/
    private void jTUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsrFocusLost

        /*Coloca el cursor al principio del control*/
        jTUsr.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTUsr.getText().compareTo("")!=0)
            jTUsr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTUsrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la contraseña*/
    private void jTContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContraFocusLost

        /*Coloca el cursor al principio del control*/
        jTContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTContra.getText().compareTo("")!=0)
            jTContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTContraFocusLost

    
    /*Cuando se pierde el foco del teclado ene l control de la base de datos*/
    private void jTBDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBDFocusLost

        /*Coloca el cursor al principio del control*/
        jTBD.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBD.getText().compareTo("")!=0)
            jTBD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTBDFocusLost

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbConMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbConMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbCon.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbConMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCreaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCreaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCrea.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCreaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
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
    private void jBLimpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLimp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimpMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProb2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProb2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProb2MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered


    /*Cuando el mouse sale del botón específico*/
    private void jBProbConMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbConMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProbCon.setBackground(colOri);
        
    }//GEN-LAST:event_jBProbConMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCreaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCreaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCrea.setBackground(colOri);
        
    }//GEN-LAST:event_jBCreaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLimp.setBackground(colOri);
        
    }//GEN-LAST:event_jBLimpMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProb2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProb2.setBackground(colOri);
        
    }//GEN-LAST:event_jBProb2MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCrea);
        
        /*Coloca el cursor al principio del control*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del còdigo de la empresa*/
    private void jTCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCodEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodEmpFocusLost
                    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona F5 presiona el botón de probar conexión*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBProbCon.doClick();
        /*Si se presiona F6 presiona el botón de probar conexión desde la tabla*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBProb2.doClick();
        /*Si se presiona F7 presiona el botón de crear*/
        else if(evt.getKeyCode() == KeyEvent.VK_F7)
            jBCrea.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F9 presiona el botón de limpiar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F9)
            jBLimp.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCrea;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBLimp;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProb2;
    private javax.swing.JButton jBProbCon;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JButton jBTod;
    private javax.swing.JCheckBox jCDelBD;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBD;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCodEmp;
    private javax.swing.JTextField jTContra;
    private javax.swing.JTextField jTNomEmp;
    private javax.swing.JTextField jTServ;
    private javax.swing.JTextField jTUsr;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class CorreosElectronicos extends javax.swing.JFrame*/
