//Paquete
package cats;

//Importaciones
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.ImgVis;
import ptovta.Login;
import static ptovta.Princip.bIdle;




/*Clase para controlar los usuarios*/
public class Usrs extends javax.swing.JFrame 
{
    /*Almacena el borde del botón de búscar original*/
    private Border          bBordOri;

    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis          v;
    
    /*Declara variables de instancia*/        
    private int             iContFi;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara variables originales*/
    private String          sEstacOri;   
    private String          sNomOri;       

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    
    /*Constructor sin argumentos*/
    public Usrs() 
    {        
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);

        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(30);        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(200);                
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Usuarios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicialmente el label de imágen no será visible, se vuelve visible cuando se carga una imágen*/
        jLImg.setVisible(false);
        
        /*Pon el foco del teclado en el campo de edición de usuario*/
        jTEstac.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        /*Obtén de la base de datos todos los usuarios y cargalos en la tabla*/
        vCargEstacs();

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;

        /*Crea el listener para cuando se cambia de selección en la tabla de usuarios*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Obtiene la fila seleccionada*/                                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtiene la estación original de la fila*/
                sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                       
            }
        });        
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/               
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                                                                
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                        
                        sNomOri         = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sNomOri,        jTab.getSelectedRow(), 2);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Crea el listener para cuando se cambia de selección en la tabla de usuarios*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {
                /*Si no hay selección entonces regresa*/
                if(jTab.getSelectedRow() == -1)
                    return;

                /*Declara variables final para el thread*/
                final int rowFi = jTab.getSelectedRow();
                
                /*Haz este proceso en un thread*/
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

                        //Declara variables de la base de datos    
                        Statement   stt;
                        ResultSet   rs;                        
                        String      sQ;

                        /*Obtiene todos los datos del usuario*/
                        try
                        {
                            sQ  = "SELECT * FROM estacs WHERE estac = '" + jTab.getValueAt(rowFi, 1).toString() + "'";
                            stt = con.createStatement();
                            rs  = stt.executeQuery(sQ);
                            /*Si hay datos*/
                            if(rs.next())
                            {
                                /*Obtiene los datos y cargalos en sus campos*/
                                jTEstac.setText     (rs.getString("estac"));
                                jTDesc.setText      (rs.getString("descu"));
                                jTContra.setText    (Star.sDecryp(rs.getString("pass")));                
                                jTCall.setText      (rs.getString("calle"));                
                                jTCol.setText       (rs.getString("col"));                
                                jTCP.setText        (rs.getString("cp"));                
                                jTTel.setText       (rs.getString("tel"));                
                                jTCel.setText       (rs.getString("cel"));                
                                jTEstad.setText     (rs.getString("esta"));                
                                jTPai.setText       (rs.getString("pai"));                
                                jTNoExt.setText     (rs.getString("noext"));                
                                jTNoInt.setText     (rs.getString("noint"));                
                                jTNom.setText       (rs.getString("nom"));                
                                jTCiu.setText       (rs.getString("ciu"));                
                                jTComi.setText      (rs.getString("comi")); 
                                
                                /**
                                 * MODIFICACIONES 17/03/2015 FelipeRuiz FUNCION
                                 * PARA QUE EL USUARIO SUP SIEMPRE SEA
                                 * ADMINISTRADOR IRREVOCABLEMENTE
                                 */
                                String sEsta;

                                /*Recorre toda la selección del usuario*/
                                int iSel[] = jTab.getSelectedRows();
                                DefaultTableModel tm = (DefaultTableModel) jTab.getModel();
                                for (int x = iSel.length - 1; x >= 0; x--) {
                                    /*Obtén el usuario*/
                                    sEsta = jTab.getValueAt(iSel[x], 1).toString().trim();

                                    /*Si El usuario es SUP entonces*/
                                    if (sEsta.compareTo("SUP") == 0) {
                                        jCPto.setEnabled(false);
                                        jCAdmCaj.setSelected(true);
                                        jCAdmCaj.setEnabled(false);

                                    } else {
                                        jCPto.setEnabled(true);
                                        jCAdmCaj.setEnabled(true);
                                    }
                                }

                                /*Marca o desmarca si tiene o no descuento*/
                                if(rs.getString("habdesc").compareTo("1")==0)
                                    jCHabDesc.setSelected(true);
                                else
                                    jCHabDesc.setSelected(false);

                                /*Marca o desmarca si es un usuario solamente del punto de venta*/
                                if(rs.getString("ptovta").compareTo("1")==0)
                                    jCPto.setSelected(true);
                                else
                                    jCPto.setSelected(false);
                                
                                /*Marca o desmarca si es vendedor o no*/
                                if(rs.getString("vend").compareTo("1")==0)
                                    jCVend.setSelected(true);
                                else
                                    jCVend.setSelected(false);

                                /*Marca o desmarca si es administrador de caja o no*/
                                if(rs.getString("admcaj").compareTo("1")==0)
                                    jCAdmCaj.setSelected(true);
                                else
                                    jCAdmCaj.setSelected(false);
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

                        /*Si la carpeta de las imágenes no existe entonces creala*/
                        sCarp                    += "\\Imagenes";
                        if(!new File(sCarp).exists())
                            new File(sCarp).mkdir();

                        /*Si la carpeta de los usuarios no existe entonces creala*/
                        sCarp                    += "\\Usuarios";
                        if(!new File(sCarp).exists())
                            new File(sCarp).mkdir();

                        /*Si la carpeta de la aplicación no existe entonces creala*/
                        sCarp                    += "\\" + Login.sCodEmpBD;
                        if(!new File(sCarp).exists())
                            new File(sCarp).mkdir();

                        /*Si la carpeta de El usuario en específico no existe entonces creala*/
                        sCarp                    += "\\" + jTab.getValueAt(rowFi, 1).toString();
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

                                /*Carga la imágen en el panel*/
                                jLImg.setIcon(new ImageIcon(sCarp + "\\" + sArc[0]));

                                /*Que el label sea visible*/
                                jLImg.setVisible(true);
                            }
                            /*Else, no existe imágen entonces*/
                            else
                            {
                                /*Que el label con la imágen no sea visible*/
                                jLImg.setVisible(false);
                            }
                            
                        }/*Fin de if(new File(sCarp).exist())*/                                    
                        
                    }/*Fin de public void run()*/
                    
                }).start();
                
            }/*Fin de public void valueChanged(ListSelectionEvent lse) */
            
        });
    }/*Fin de public Usrs() */

    
    /*Obtén de la base de datos todos los usuarios y cargalos en la tabla*/
    private void vCargEstacs()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Trae todos los usuarios de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT estac, nom, descu FROM estacs";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[] = {iContFi, rs.getString("estac"), rs.getString("nom"), rs.getString("descu")};
                te.addRow(nu);
                
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
                        
    }/*Fin de private void vCargEstacs()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTEstac = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jTContra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMostT = new javax.swing.JButton();
        jBCargImg = new javax.swing.JButton();
        jBDelImg = new javax.swing.JButton();
        jTDesc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCHabDesc = new javax.swing.JCheckBox();
        jBVe = new javax.swing.JButton();
        jScrollImg = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTEstad = new javax.swing.JTextField();
        jTCall = new javax.swing.JTextField();
        jTCol = new javax.swing.JTextField();
        jTCP = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jTCel = new javax.swing.JTextField();
        jTPai = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTNoInt = new javax.swing.JTextField();
        jTNoExt = new javax.swing.JTextField();
        jCAdmCaj = new javax.swing.JCheckBox();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jCVend = new javax.swing.JCheckBox();
        jBLim = new javax.swing.JButton();
        jBTod = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTCiu = new javax.swing.JTextField();
        jTComi = new javax.swing.JTextField();
        jCPto = new javax.swing.JCheckBox();

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

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del5.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Usuario(s) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBSal);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("*Contraseña:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 190, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jPanImg);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 270, 120, 30));

        jTEstac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstac.setNextFocusableComponent(jTDesc);
        jTEstac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstacFocusLost(evt);
            }
        });
        jTEstac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstacKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstacKeyTyped(evt);
            }
        });
        jP1.add(jTEstac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 20));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Usuario (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTab);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 120, 30));

        jTContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTContra.setNextFocusableComponent(jTCall);
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
        jP1.add(jTContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 190, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Ciudad:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 190, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Desc. en ventas y comisión");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 190, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 640, 280));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMostT);
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 360, 20));

        jBMostT.setBackground(new java.awt.Color(255, 255, 255));
        jBMostT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMostT.setForeground(new java.awt.Color(0, 102, 0));
        jBMostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMostT.setText("Mostrar F4");
        jBMostT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMostT.setNextFocusableComponent(jBDel);
        jBMostT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMostTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMostTMouseExited(evt);
            }
        });
        jBMostT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMostTActionPerformed(evt);
            }
        });
        jBMostT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMostTKeyPressed(evt);
            }
        });
        jP1.add(jBMostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 140, 20));

        jBCargImg.setBackground(new java.awt.Color(255, 255, 255));
        jBCargImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargImg.setText("Cargar");
        jBCargImg.setToolTipText("Cargar Imágen");
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
        jP1.add(jBCargImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 490, 70, -1));

        jBDelImg.setBackground(new java.awt.Color(255, 255, 255));
        jBDelImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelImg.setText("Borrar");
        jBDelImg.setToolTipText("Borrar Imágen");
        jBDelImg.setNextFocusableComponent(jBVe);
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
        jP1.add(jBDelImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 490, 60, -1));

        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jTComi);
        jTDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescFocusLost(evt);
            }
        });
        jTDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescKeyTyped(evt);
            }
        });
        jP1.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 90, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("*Código usuario:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jCHabDesc.setBackground(new java.awt.Color(255, 255, 255));
        jCHabDesc.setSelected(true);
        jCHabDesc.setText("Hab. Descuento");
        jCHabDesc.setNextFocusableComponent(jCAdmCaj);
        jCHabDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCHabDescKeyPressed(evt);
            }
        });
        jP1.add(jCHabDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 120, -1));

        jBVe.setBackground(new java.awt.Color(255, 255, 255));
        jBVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVe.setToolTipText("Ver Imágen Completa");
        jBVe.setNextFocusableComponent(jTEstac);
        jBVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeMouseExited(evt);
            }
        });
        jBVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeActionPerformed(evt);
            }
        });
        jBVe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeKeyPressed(evt);
            }
        });
        jP1.add(jBVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 490, 20, 20));

        jScrollImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrollImgKeyPressed(evt);
            }
        });

        jPanImg.setNextFocusableComponent(jBCargImg);
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
                .addComponent(jLImg)
                .addGap(0, 148, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        jScrollImg.setViewportView(jPanImg);

        jP1.add(jScrollImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 300, 150, 190));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Teléfono:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 190, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Nombre de usuario:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 190, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Colonia:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 190, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Celular:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 190, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("CP:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 190, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("No. exterior:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTCP);
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
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 190, 20));

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
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 190, 20));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTTel);
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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 190, 20));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTNom);
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
        });
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 190, 20));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTCel);
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
        });
        jP1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 190, 20));

        jTCel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel.setNextFocusableComponent(jTPai);
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
        jP1.add(jTCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 190, 20));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTCiu);
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
        jP1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 190, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar Cambios (Ctrl+G)");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 120, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setText("Calle:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 190, -1));

        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jCHabDesc);
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
        });
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 390, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("País:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 190, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setText("No. interior:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 100, -1));

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
        });
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 90, 20));

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
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, 20));

        jCAdmCaj.setBackground(new java.awt.Color(255, 255, 255));
        jCAdmCaj.setText("Administrador");
        jCAdmCaj.setNextFocusableComponent(jCVend);
        jCAdmCaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAdmCajActionPerformed(evt);
            }
        });
        jCAdmCaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCAdmCajKeyPressed(evt);
            }
        });
        jP1.add(jCAdmCaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 110, -1));

        jBTab1.setBackground(new java.awt.Color(0, 153, 153));
        jBTab1.setToolTipText("Mostrar Tabla en Grande");
        jBTab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTab1ActionPerformed(evt);
            }
        });
        jBTab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTab1KeyPressed(evt);
            }
        });
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 510, 150, 20));

        jCVend.setBackground(new java.awt.Color(255, 255, 255));
        jCVend.setText("Vendedor");
        jCVend.setNextFocusableComponent(jCPto);
        jCVend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVendKeyPressed(evt);
            }
        });
        jP1.add(jCVend, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 100, -1));

        jBLim.setBackground(new java.awt.Color(255, 255, 255));
        jBLim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLim.setForeground(new java.awt.Color(0, 102, 0));
        jBLim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLim.setText("Limpiar");
        jBLim.setToolTipText("Limpiar todos los Campos (F6)");
        jBLim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLim.setNextFocusableComponent(jBNew);
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
        jP1.add(jBLim, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, 120, -1));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 192, 130, 18));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setText("Estado:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 190, -1));

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
        jP1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 190, 20));

        jTComi.setText("0");
        jTComi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTComi.setNextFocusableComponent(jTContra);
        jTComi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTComiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTComiFocusLost(evt);
            }
        });
        jTComi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTComiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTComiKeyTyped(evt);
            }
        });
        jP1.add(jTComi, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 90, 20));

        jCPto.setBackground(new java.awt.Color(255, 255, 255));
        jCPto.setText("Punto de venta");
        jCPto.setToolTipText("Este usuario solo usa el punto de venta");
        jCPto.setNextFocusableComponent(jTab);
        jCPto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPtoActionPerformed(evt);
            }
        });
        jCPto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPtoKeyPressed(evt);
            }
        });
        jP1.add(jCPto, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una usuario de la lista para borrar.", "Borrar usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar El usuario*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) usuario(es)?", "Borrar usuario", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene el usuario*/
        String sEsta;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            /*Obtén el usuario*/        
            sEsta               = jTab.getValueAt(iSel[x], 1).toString().trim();                  
            
            /*Si El usuario es SUP entonces*/
            if(sEsta.compareTo("SUP")==0)
            {
                /*Mensajea y continua la iteracción del ciclo*/                
                JOptionPane.showMessageDialog(null, "SUP es el administrador inicial del sistema y no se puede eliminar, solo cambiar su contraseña.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;                          
            }                

            //Declara variables de la base de datos    
            Statement   st;                
            String      sQ;

            /*Borra el usuario*/
            try 
            {                
                sQ = "DELETE FROM estacs WHERE estac = '" + sEsta + "'";                                
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
        JOptionPane.showMessageDialog(null, "Usuario(es) borrada(s) con éxito.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed

      
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed
   
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usuario si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();                    
            this.dispose();            
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTEstacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de línea*/
    private void jTEstacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstac.setSelectionStart(0);jTEstac.setSelectionEnd(jTEstac.getText().length());                
        
    }//GEN-LAST:event_jTEstacFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de edición de pass*/
    private void jTContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTContra.setSelectionStart(0);jTContra.setSelectionEnd(jTContra.getText().length()); 
        
    }//GEN-LAST:event_jTContraFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de pass*/
    private void jTContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTContraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTContraKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de estacs*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de usuario*/
    private void jTEstacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEstac.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEstac.getText().compareTo("")!=0)
            jTEstac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTEstac.getText().length()> 30)
            jTEstac.setText(jTEstac.getText().substring(0, 30));
                
    }//GEN-LAST:event_jTEstacFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de pass*/
    private void jTContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTContra.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTContra.getText().compareTo("")!=0)
            jTContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTContra.getText().length()> 30)
            jTContra.setText(jTContra.getText().substring(0, 30));
        
    }//GEN-LAST:event_jTContraFocusLost

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        //Limpia la selección de la tabla
        jTab.clearSelection();
        
        /*Si hay cadena vacia en el campo de edición de usuario no puede continuar*/
        if(jTEstac.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEstac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de usuario esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTEstac.grabFocus();            
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
        
        /*Consulta si el usuario ya existe en la base de datos*/
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTEstac.getText().replace(" ", "").trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEstac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Usuario " + jTEstac.getText().replace(" ", "").trim() + " ya existe en la base de datos", "Usuario Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Pon el foco del teclad en el campo de usuario y regresa*/
                jTEstac.grabFocus();                
                return;                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }                    
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar usuario", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        /*Lee la password introducida por el usuario*/
        String sCla    = Star.sEncrip(jTContra.getText().trim());
        
        //Abre la base de datos                             
        con             = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene todos los datos de la dirección*/
        String sCall    = jTCall.getText();
        String sCol     = jTCol.getText();
        String sCP      = jTCP.getText();
        String sTel     = jTTel.getText();
        String sCel     = jTCel.getText();
        String sPai     = jTPai.getText();        
        String sNoExt   = jTNoExt.getText();
        String sNoInt   = jTNoInt.getText();
        
        /*Obtiene el nombre para el usuario*/
        String  sNomb   = jTNom.getText();
                
        /*Obtiene si va a tener o no hablitado el descuento*/
        String  sHabDesc    = "0";
        if(jCHabDesc.isSelected())
            sHabDesc        = "1";        
        
        /*Obtiene si va a ser administrador de caja o no*/
        String  sAdmCaj     = "0";
        if(jCAdmCaj.isSelected())
            sAdmCaj         = "1";        
        
        /*Obtiene si va a ser vendedor o no*/
        String  sVend       = "0";
        if(jCVend.isSelected())
            sVend           = "1";        
        
        /*Obtiene si va a ser un usuario solamente de punto de venta*/
        String  sPto        = "0";
        if(jCPto.isSelected())
            sPto            = "1";        
        
        /*Obtiene la impresora por default*/
        PrintService se = PrintServiceLookup.lookupDefaultPrintService(); 
        
        /*Inserta los datos del usuario en la base de datos*/
        try 
        {        
            sQ = "INSERT INTO estacs (estac,                                                                    pass,            falt,       estacglo,                                  imptic,                     impfac,             52m,  cort,     descu,                   habdesc,         sucu,                       nocaj,                        nom,                                  calle,                                col,                                 cp,                               tel,                                cel,                                 pai,                                  esta,                                             noint,                               noext,                              admcaj,             vend,           ciu,                          comi,                            ptovta) " + 
                         "VALUES('" + jTEstac.getText().replace(" ", "").replace("'", "''").trim() + "','" +    sCla + "',       now(), '" + Login.sUsrG.replace("'", "''") + "', '" +  se.getName() + "', '" +     se.getName() + "',  0,    '0', " +  jTDesc.getText() + ", " +sHabDesc+ ",'" + Star.sSucu + "','" +  Star.sNoCaj + "', '" +  sNomb.replace("'", "''") + "','" +   sCall.replace("'", "''") + "','" +   sCol.replace("'", "''") + "','" +    sCP.replace("'", "''") + "','" +  sTel.replace("'", "''") + "','" +   sCel.replace("'", "''") + "','" +    sPai.replace("'", "''") + "','" +     jTEstac.getText().replace("'", "''") + "','" +    sNoInt.replace("'", "''") + "','" +  sNoExt.replace("'", "''") + "', " + sAdmCaj + ", " +   sVend + ", '" + jTCiu.getText().replace("'", "''") + "', " + jTComi.getText().trim() + ", " + sPto + ")";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
         }
        
        /*Inserta en la tabla de permisos, los permisos del nuevo usuario*/
        try 
        {        
            sQ = "INSERT INTO er_permisos (FKIdUsuario) SELECT id_id FROM estacs WHERE estac='"+jTEstac.getText().replace(" ", "").replace("'", "''").trim()+"'";
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

        /*Agrega el usuario en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTEstac.getText().replace(" ", "").trim(), sNomb};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas en uno*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo de usuario*/
        jTEstac.grabFocus();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Usuario: " + jTEstac.getText().replace(" ", "").trim() + " agregado con éxito.", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
        
    }//GEN-LAST:event_jBNewActionPerformed
           
    
    /*Cuando se tipea una tecla en el campo de usuario*/
    private void jTEstacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstacKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstacKeyTyped

    
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
    private void jBMostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMostTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMostTKeyPressed

        
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMostTActionPerformed
               
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtén de la base de datos todos los usuarios y cargalos en la tabla*/
        vCargEstacs();
            
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMostTActionPerformed

    
    /*Cuando se presiona el botón de estacs*/
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
        
        /*Borra todos los item en la tabla de estacs*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   stt;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los usuarios*/        
        try
        {                  
            sQ = "SELECT * FROM estacs WHERE estac LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR pass LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR falt LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fmod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            stt= con.createStatement();
            rs = stt.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos a la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("estac"), rs.getString("nom")};
                te.addRow(nu);

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

    
    /*Cuando se presiona una tecla en el panel de la imágen*/
    private void jPanImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanImgKeyPressed

    
    /*Cuando se presiona el botón de cargar imágen*/
    private void jBCargImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargImgActionPerformed

        /*Si no a seleccionado una usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una usuario.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de usuarios y regresa*/
            jTab.grabFocus();                        
            return;          
        }
        
        /*Si no hay selecciòn entonces regresa*/
        if(jTab.getSelectedRow()==-1)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
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

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";        
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de los usuarios no existe entonces crea la carpeta*/
        sCarp                    += "\\Usuarios";        
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de El usuario en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen para El usuario: " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + " en \"" + sCarp + "\".", "Empresas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Muestra el file choooser*/
        int iVal           = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(iVal           == JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut               = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut               += "\\" + fc.getSelectedFile().getName();                
            
            /*Si el nombre del archivo no termina con una extensión de imágen entonces*/
            if(!fc.getSelectedFile().getName().endsWith(".jpg") && !fc.getSelectedFile().getName().endsWith(".jpeg") && !fc.getSelectedFile().getName().endsWith(".bmp") && !fc.getSelectedFile().getName().endsWith(".gif") && !fc.getSelectedFile().getName().endsWith(".png"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El formato de la imágen debe ser un formato de imágen conocido.", "Imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp            +=  "\\" + fc.getSelectedFile().getName();  

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
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);
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

    
    /*Cuando se presiona una tecla en el botón de borrar imágen*/
    private void jBDelImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelImgActionPerformed
              
        /*Si no a seleccionado una usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una usuario.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;            
        }

        /*Obtiene El usuario*/        
        String sEsta    = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
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
        
        /*Si la carpeta de imágenes no existe entonces creala*/
        sCarp               += "\\Imagenes"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de usuarios no existe entonces creala*/
        sCarp               += "\\Usuarios";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp               += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp             += "\\" + sEsta;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El usuario: " + sEsta + " no contiene imágen para borrar en \"" + sCarp + "\".", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                    

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;

        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);            
            return;                                                
        }                       

        /*Que no sea visible el label del logotipo*/
        jLImg.setVisible(false);

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen borrada con éxito para El usuario \"" + sEsta + "\".", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBDelImgActionPerformed

    
    /*Cuandose presiona una tecla en el botón de borrar imágen*/
    private void jBDelImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelImgKeyPressed
    
    
    /*Cuando se tipea una tecla en el campo de descuento*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());
        
    }//GEN-LAST:event_jTDescFocusGained

        
    /*Cuando se presiona una tecla en el campo del descuento*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del descuento*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDesc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDesc.getText().compareTo("")!=0)
            jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo es cadena vacia entonces*/        
        if(jTDesc.getText().compareTo("")==0)
        {
            /*Que sea 0 y regresa*/
            jTDesc.setText("0");                        
            return;
        }
        
        /*Si el número es mayor a 100 o menor a 0 entonces que sea 0*/
        try
        {
            if(Double.parseDouble(jTDesc.getText())>100 || Double.parseDouble(jTDesc.getText())< 0)
                jTDesc.setText("0");                               
        }
        catch(NumberFormatException expnNumForm)
        {
            jTDesc.setText("0");
        }            
        
    }//GEN-LAST:event_jTDescFocusLost
        
    
    /*Cuando se presiona una tecla en el checkbox de habilitar descuento*/
    private void jCHabDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCHabDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCHabDescKeyPressed

    
    /*Cuando se presiona el botón de ver en grande*/
    private void jBVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeActionPerformed
                
        /*Si no a seleccionado una usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una usuario.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();           
            return;
        }

        /*Si no hay selección que regrese*/
        if(jTab.getSelectedRow()==-1)
            return;
        
        /*Obtiene el usuario de la fila*/
        String sEstac      = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
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

        /*Si la carpeta de los productos no existen entonces creala*/
        sCarp                    += "\\Usuarios";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existen entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp                    += "\\" + sEstac;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen para El usuario: " + sEstac + " en \"" + sCarp + "\".", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
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
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                    
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVeActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver en grande*/
    private void jBVeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVeKeyPressed

    
    /*Cuando se presiona una tecla en el panel scrollable de la imágen*/
    private void jScrollImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollImgKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jScrollImgKeyPressed

    
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
    
    
    /*Cuando se presiona una tecla en el campo de la calle*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de colonia*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo del código postal*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo del teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo del célular*/
    private void jTCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCelKeyPressed

    
    /*Cuando se presiona una tecla en el campo del pais*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se presiona una tecla en el campo del estado*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la calle*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la colonia*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del célular*/
    private void jTCelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCel.setSelectionStart(0);jTCel.setSelectionEnd(jTCel.getText().length());                
        
    }//GEN-LAST:event_jTCelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del pais*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de El usuario*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstac.setSelectionStart(0);jTEstac.setSelectionEnd(jTEstac.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del nombre*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona una tecla en el campo del número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo del número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoIntKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del número de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        /*Si no a seleccionado una usuario de la tabla entonces*/        
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un usuario de la tabla.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Modificar Usuario", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        ResultSet   rs;
        String      sQ; 

        /*Búsca si la estación ya existe en la base de datos*/
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + jTEstac.getText() + "' AND estac <> '" + sEstacOri + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe entonces*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensajea y rgeresa*/
                JOptionPane.showMessageDialog(null, "La estación: " + jTEstac.getText() + " ya existe.", "Registro Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }
        
        /*Determina si se habilita o no el descuento*/
        String sHabDesc;
        if(jCHabDesc.isSelected())
            sHabDesc    = "1";
        else
            sHabDesc    = "0";

        /*Determina si es administrador de caja o no*/
        String sAdmCaj;
        if(jCAdmCaj.isSelected())
            sAdmCaj     = "1";
        else
            sAdmCaj     = "0";
        
        /*Determina si es vendedor o no*/
        String sVend;
        if(jCVend.isSelected())
            sVend       = "1";
        else
            sVend       = "0";
        
        /*Obtiene si va a ser un usuario solamente de punto de venta*/
        String  sPto        = "0";
        if(jCPto.isSelected())
            sPto            = "1";        
        
        /*Encripta la password*/
        String sClav    = jTContra.getText();
        sClav           = Star.sEncrip(sClav);
        
        /*Actualiza todos los registros en la base de datos*/
        try
        {           
            sQ = "UPDATE estacs SET "
                    + "descu        = " + jTDesc.getText() + ", "
                    + "comi         = " + jTComi.getText() + ", "
                    + "noint        = '" + jTNoInt.getText().replace("'", "''") + "', "
                    + "noext        = '" + jTNoExt.getText().replace("'", "''") + "', "
                    + "pass         = '" + sClav.replace("'", "''") + "', "
                    + "habdesc      = " + sHabDesc + ", "
                    + "estacglo     = '" + Login.sUsrG.replace("'", "''") + "', "
                    + "calle        = '" + jTCall.getText().replace("'", "''") + "', "
                    + "col          = '" + jTCol.getText().replace("'", "''") + "', "
                    + "nom          = '" + jTNom.getText().replace("'", "''") + "', "
                    + "cp           = '" + jTCP.getText().replace("'", "''") + "', "
                    + "tel          = '" + jTTel.getText().replace("'", "''") + "', "
                    + "ptovta       = " + sPto + ", "
                    + "cel          = '" + jTCel.getText().replace("'", "''") + "', "
                    + "pai          = '" + jTPai.getText().replace("'", "''") + "', "
                    + "esta         = '" + jTEstad.getText().replace("'", "''") + "', "
                    + "fmod         = now(), "
                    + "admcaj       = " + sAdmCaj + ", "
                    + "vend         = " + sVend + ", "
                    + "estac        = '" + jTEstac.getText().replace("'", "''") + "', "
                    + "ciu          = '" + jTCiu.getText().replace("'", "''") + "' "
                    + "WHERE estac  = '" + sEstacOri.replace("'", "''") + "'";                                                                        
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

        /*Borra la tabla de usuarios*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Reinicia el contador de filas*/
        iContFi = 1;
        
        /*Carga nuevamente los usuarios*/
        vCargEstacs();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Usuario modificado con éxito.", "Usuarios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de administrador de caja entonces*/
    private void jCAdmCajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCAdmCajKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCAdmCajKeyPressed
    
    
    /*Cuando se presiona el botón de ver tabla*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       
        
    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de vendedor*/
    private void jCVendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVendKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCVendKeyPressed

    
    /*Cuando se presiona el botón de limpiar*/
    private void jBLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimActionPerformed
        
        /*Resetea todos los campos*/
        jTEstac.setText         ("");
        jTDesc.setText          ("0");
        jTContra.setText        ("");
        jTCall.setText          ("");
        jTCiu.setText           ("");
        jTCol.setText           ("");
        jTCP.setText            ("");
        jTTel.setText           ("");
        jTCel.setText           ("");
        jTEstad.setText         ("");
        jTPai.setText           ("");
        jTNoExt.setText         ("");
        jTNoInt.setText         ("");
        jTNom.setText           ("");
        jCHabDesc.setSelected   (true);
        jCAdmCaj.setSelected    (false);
        jCVend.setSelected      (false);
        jCPto.setSelected       (false);
        
        //Limpia la selección de la tabla
        jTab.clearSelection();
        
    }//GEN-LAST:event_jBLimActionPerformed

    
    /*Cuando se presiona una tecla en el botón de limpiar*/
    private void jBLimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimKeyPressed

    
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el área de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered
        
        /*Obtiene la fila seleccionada*/        
        if(jTab.getSelectedRow()==-1)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
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

        /*Si la carpeta de los usuarios no existe entonces creala*/
        sCarp                    += "\\Usuarios";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la aplicación no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp                    += "\\" + sEstacOri;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe en la ruta entonces*/                
        if(new File(sCarp).list().length > 0)
        {            
            /*Obtiene la lista de directorios*/
            String sArc [] = new File(sCarp).list();
            
            /*Muestra la forma para ver la imágen en otra vista*/
            v = new ImgVis(sCarp + "\\" + sArc[0]);            
            v.setVisible(true);
        }        
        
    }//GEN-LAST:event_jPanImgMouseEntered

    
    /*Cuando el mouse sale de la imágen*/
    private void jPanImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseExited
        
        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jPanImgMouseExited

    
    /*Cuando se gana el foco del teclado en el campo de la ciudad*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());        
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se presiona una tecla en el campo de la ciudad*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLim.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
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
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMostT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMostTMouseEntered

    
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
    private void jBVeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVe.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLim.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBLimMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
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
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMostT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMostTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargImg.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCargImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelImg.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVe.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVeMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNoInt.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNoExt.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se presiona una tecla en el botón de guardar cambios*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad*/
    private void jTComiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTComiFocusGained
        
       /*Selecciona todo el texto cuando gana el foco*/
        jTComi.setSelectionStart(0);jTComi.setSelectionEnd(jTComi.getText().length());
        
    }//GEN-LAST:event_jTComiFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la utilidad*/
    private void jTComiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTComiFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTComi.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTComi.getText().compareTo("")!=0)
            jTComi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo es cadena vacia entonces*/        
        if(jTComi.getText().compareTo("")==0)
        {
            /*Que sea 0 y regresa*/
            jTComi.setText("0");                        
            return;
        }
        
        /*Si el número es mayor a 100 o menor a 0 entonces que sea 0*/
        try
        {
            if(Double.parseDouble(jTComi.getText())>100 || Double.parseDouble(jTComi.getText())< 0)
                jTComi.setText("0");                               
        }
        catch(NumberFormatException expnNumForm)
        {
            jTComi.setText("0");
        }            
        
    }//GEN-LAST:event_jTComiFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad*/
    private void jTComiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTComiKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTComiKeyPressed

    
    /*Cuando se tipea una tecla en el campo de utilidad*/
    private void jTComiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTComiKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTComiKeyTyped

    
    /*Cuando se presiona una tecla en el check de punto de venta*/
    private void jCPtoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPtoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPtoKeyPressed

    
    /*Cuando sucede una acción en el check de punto de venta*/
    private void jCPtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPtoActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCPto.isSelected())
        {
			/** MODIFICACIONES 13/03/2015  FelipeRuiz*/
            jCAdmCaj.setSelected(false);
            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos    
            Statement   stt;
            ResultSet   rs;            
            String      sQ;

            /*Obtiene la cantidad de usuarios*/
            int iEstacs = 0;
            try
            {
                sQ  = "SELECT COUNT(estac) AS estac FROM estacs";
                stt = con.createStatement();
                rs  = stt.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    iEstacs = rs.getInt("estac");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                        
            }

            /*Obtiene la cantidad de usuarios que son de punto de venta*/
            int iPto = 0;
            try
            {
                sQ  = "SELECT COUNT(estac) AS estac FROM estacs WHERE ptovta = 1";
                stt = con.createStatement();
                rs  = stt.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    iPto = rs.getInt("estac");
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
            
            /*Obtiene la diferencia entre los usuarios y los que estan conectados*/
            iPto    = iEstacs - iPto;
            
            //Si no quedan disponibles entonces
            if(iPto==1 && jTab.getSelectedRow()!=-1)
            {
                /*Mensajea y coloca el campo en false*/
                JOptionPane.showMessageDialog(null, "No todos los usuarios pueden ser de punto de venta. Por lo menos uno tiene que quedar disponible.", "Punto de venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                jCPto.setSelected(false);
            }
            
        }/*Fin de if(jCPto.isSelected())*/
        
    }//GEN-LAST:event_jCPtoActionPerformed
/** MODIFICACIONES 13/03/2015  FelipeRuiz*/
    private void jCAdmCajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAdmCajActionPerformed
        
        if(jCAdmCaj.isSelected()){
            jCPto.setSelected(false);
        }
    }//GEN-LAST:event_jCAdmCajActionPerformed

   
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
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
            jBMostT.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
                        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCargImg;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDelImg;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBLim;
    private javax.swing.JButton jBMostT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVe;
    private javax.swing.JCheckBox jCAdmCaj;
    private javax.swing.JCheckBox jCHabDesc;
    private javax.swing.JCheckBox jCPto;
    private javax.swing.JCheckBox jCVend;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JScrollPane jScrollImg;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTComi;
    private javax.swing.JTextField jTContra;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTEstac;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
