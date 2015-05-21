//Paquete
package cats;

/*Importaciones*/
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Cursor;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import ptovta.Busc;
import ptovta.Star;
import ptovta.Login;
import static ptovta.Princip.bIdle;




/*Clase para controlar las almacenes*/
public class Almas extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables orignales*/    
    private String              sCodOriG;
    private String              sResponOriG;
    private String              sDescripOriG;
    
    /*Declara contadores*/
    private int                 iContCellEd;
    private int                 iContFi;            

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Variable que contiene el borde actual*/
    private Border              bBordOri;
    
    
    
    /*Constructor sin argumentos*/
    public Almas() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        Star.lCargGral=null;
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Deshabilita que no se mueven las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Establece el titulo del ventana con el usuario, la fecha y hora*/                
        this.setTitle("Catálogo de almacenes, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicializa en uno el contador de las filas*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de edición de código del almacén*/
        jTCod.grabFocus();
        
        /*Establece el tamaño dels columnas del tabla de almacenes*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(450);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(300);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
                
        /*Obtiene de la base de datos todas los almacenes y cargalas en la tabla*/
        vCarAlmas();
            
        /*Crea el listener para la tabla de almacenes, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {                    
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {                                        
                        /*Obtén algunos datos originales de la fila*/                        
                        sCodOriG            = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sDescripOriG        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                  
                        sResponOriG         = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sCodOriG,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sResponOriG,    jTab.getSelectedRow(), 3);                        
                        
                        /*Si es el almacén de activos fijos entonces*/
                        if(jTab.getValueAt(jTab.getSelectedRow(), 1).toString().compareTo("ACTFIJ")==0)
                        {
                            /*Coloca los valores orignales que tenía*/                            
                            jTab.setValueAt(sDescripOriG,   jTab.getSelectedRow(), 2);
                            jTab.setValueAt("",             jTab.getSelectedRow(), 3);
                            jTab.setValueAt("",             jTab.getSelectedRow(), 4);
                            jTab.setValueAt("",             jTab.getSelectedRow(), 5);
                            jTab.setValueAt("",             jTab.getSelectedRow(), 6);
                        
                            /*Mensajea y regresa*/
                            JOptionPane.showMessageDialog(null, "No se puede modificar el almacén de activo fijo.", "Almacenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                            return;
                        }
                        
                        /*Obtén la nueva descripción*/                                                
                        String sDescrip     = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                                                                   
                        /*Si la descrición es cadena vacia entonces que sea la que tenía antes*/
                        if(sDescrip.compareTo("")==0)
                            sDescrip        = sDescripOriG;
                        
                        /*Coloca la descripción en su campo*/
                        jTab.setValueAt(sDescrip, jTab.getSelectedRow(), 2);
                                                
                        //Abre la base de datos                             
                        Connection  con = Star.conAbrBas(true, true);
                        
                        //Si hubo error entonces regresa
                        if(con==null)
                            return;
                        
                        /*Declara variables del base de datos*/    
                        Statement   st;                        
                        String      sQ;                        
                        
                        /*Actualiza en la base de datos el registro*/
                        try 
                        {                                                        
                            sQ = "UPDATE almas SET "
                                    + "almadescrip      = '" + sDescrip.replace("'", "''").replace(",", "") + "', "
                                    + "dir1             = '" + jTab.getValueAt(jTab.getSelectedRow(), 4).toString().replace("'", "''").trim() + "', "
                                    + "dir2             = '" + jTab.getValueAt(jTab.getSelectedRow(), 5).toString().replace("'", "''").trim() + "', "
                                    + "dir3             = '" + jTab.getValueAt(jTab.getSelectedRow(), 6).toString().replace("'", "''").trim() + "', "
                                    + "fmod             = now(), "
                                    + "estac            = '" + Login.sUsrG.replace("'", "''") + "', "
                                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                                    + "WHERE alma       = '" + sCodOriG.replace("'", "''") + "'";                                                
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                            return;                        
                         }                                                                                     

                        /*Inserta en log*/
                        try 
                        {            
                            sQ = "INSERT INTO logalmas(cod,                                                                                                                             descrip,                            accio,               estac,                                         sucu,                                           nocaj,                                      falt) " + 
                                          "VALUES('" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString().replace(" ", "").replace(",", "").replace("'", "''").trim() + "','" +       sDescrip.replace("'", "''") + "',   'MODIFICAR', '" +    Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa this.getClass().getName()
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                            return;                        
                        }

                        //Termina la transacción
                        if(Star.iTermTransCon(con)==-1)
                            return;

                        //Cierra la base de datos
                        Star.iCierrBas(con);                           
                        
                    }/*Fin de else*/                                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de almacenes*/
        jTab.addPropertyChangeListener(pr);
        
    }/*Fin de public Almas() */

    
    /*Obtiene de la base de datos todas los almacenes y cargalas en la tabla*/
    private void vCarAlmas()
    {   
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        /*Declara variables del base de datos*/    
        Statement   st;
        ResultSet   rs;
        String      sQ;        
        
        /*Trae todas las almacenes del base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT alma, almadescrip, respon, dir1, dir2, dir3 FROM almas WHERE alma != '-'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]= {iContFi, rs.getString("alma"), rs.getString("almadescrip"), rs.getString("respon"), rs.getString("dir1"), rs.getString("dir2"), rs.getString("dir3")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de las filas*/
                ++iContFi;                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
            return;                        
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);            
        
    }/*Fin de private void vCarAlmas()*/
    
       
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTCod = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jTDescrip = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jBVis = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBResp = new javax.swing.JButton();
        jTRespon = new javax.swing.JTextField();
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTDir1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTDir2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTDir3 = new javax.swing.JTextField();

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
        jBDel.setToolTipText("Borrar Almacen(es) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBVis);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 140, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("*Descripción del almacen:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 190, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTCod);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 230, 120, 30));

        jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCod.setNextFocusableComponent(jTDir1);
        jTCod.setOpaque(false);
        jTCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodFocusLost(evt);
            }
        });
        jTCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodKeyTyped(evt);
            }
        });
        jP1.add(jTCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 190, 20));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Almacén (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTDescrip);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 90, 20));

        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jTDir2);
        jTDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripFocusLost(evt);
            }
        });
        jTDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescripKeyTyped(evt);
            }
        });
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Responsable:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*Código Almacen:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Código", "Descripción", "Responsable", "Dirección 1", "Dirección 2", "Dirección 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 760, 280));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 480, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBDel);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 420, 140, 20));

        jBVis.setBackground(new java.awt.Color(255, 255, 255));
        jBVis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVis.setForeground(new java.awt.Color(0, 102, 0));
        jBVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBVis.setText("Reporte");
        jBVis.setToolTipText("Ver Reporte (F6)");
        jBVis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVis.setNextFocusableComponent(jBActua);
        jBVis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVisMouseExited(evt);
            }
        });
        jBVis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVisActionPerformed(evt);
            }
        });
        jBVis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVisKeyPressed(evt);
            }
        });
        jP1.add(jBVis, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 170, 120, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBSal);
        jBActua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBActuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBActuaMouseExited(evt);
            }
        });
        jBActua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActuaActionPerformed(evt);
            }
        });
        jBActua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBActuaKeyPressed(evt);
            }
        });
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 200, 120, 30));

        jBResp.setBackground(new java.awt.Color(255, 255, 255));
        jBResp.setText("...");
        jBResp.setToolTipText("Buscar Responsable(s) como Usuario(s)");
        jBResp.setNextFocusableComponent(jTDir3);
        jBResp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRespMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRespMouseExited(evt);
            }
        });
        jBResp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRespActionPerformed(evt);
            }
        });
        jBResp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRespKeyPressed(evt);
            }
        });
        jP1.add(jBResp, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 30, 20));

        jTRespon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRespon.setNextFocusableComponent(jBResp);
        jTRespon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTResponFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTResponFocusLost(evt);
            }
        });
        jTRespon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTResponKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTResponKeyTyped(evt);
            }
        });
        jP1.add(jTRespon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 20));

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
        jP1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 290, 110, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 122, 130, 18));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Dirección 1:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 150, -1));

        jTDir1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDir1.setNextFocusableComponent(jBNew);
        jTDir1.setOpaque(false);
        jTDir1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDir1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDir1FocusLost(evt);
            }
        });
        jTDir1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDir1KeyPressed(evt);
            }
        });
        jP1.add(jTDir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 410, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Dirección 2:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 150, -1));

        jTDir2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDir2.setNextFocusableComponent(jTRespon);
        jTDir2.setOpaque(false);
        jTDir2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDir2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDir2FocusLost(evt);
            }
        });
        jTDir2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDir2KeyPressed(evt);
            }
        });
        jP1.add(jTDir2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 410, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Dirección 3:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 150, -1));

        jTDir3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDir3.setNextFocusableComponent(jTab);
        jTDir3.setOpaque(false);
        jTDir3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDir3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDir3FocusLost(evt);
            }
        });
        jTDir3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDir3KeyPressed(evt);
            }
        });
        jP1.add(jTDir3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 410, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un almacén del lista para borrar.", "Borrar Almacen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar el(los) almacen(es)?", "Borrar Almacen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        /*Bandera para saber si hubo movimientos*/
        boolean bMov    = false;
        
        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab.getSelectedRows();
        DefaultTableModel tm  = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Si es el almacén de activo fijo entonces*/
            if(jTab.getValueAt(iSel[x], 1).toString().trim().compareTo("ACTFIJ")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "No se puede eliminar el almacén de activos fijos.", "Almacenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
                continue;
            }
        
            /*Declara variables del base de datos*/    
            Statement   st;
            ResultSet   rs;
            String      sQ;        

            /*Comprueba si existen ya productos con este almacén*/        
            try
            {
                sQ = "SELECT alma FROM existalma WHERE alma = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces ya hay productos con este almacén*/
                if(rs.next())
                {
                    /*Mensajea y continua*/
                    JOptionPane.showMessageDialog(null, "Ya existen movimientos con este almacén: " + jTab.getValueAt(iSel[x], 1).toString().trim() + ", no se puede eliminar.", "Almacenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
                    continue;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                return;                        
            }  
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;

            /*Borra el almacén*/
            try 
            {
                sQ = "DELETE FROM almas WHERE alma = '" + jTab.getValueAt(iSel[x], 1).toString() + "'";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                return;                        
             }                        

            /*Inserta en log de almacenes*/
            try 
            {            
                sQ = "INSERT INTO logalmas(cod,                                                                     descrip,                                                            accio,           estac,                                         sucu,                                           nocaj,                                      falt) " + 
                              "VALUES('" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "','" +      jTab.getValueAt(iSel[x], 2).toString().replace("'", "''") + "',     'BORRAR', '" +   Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                return;                        
            }

            //Termina la transacción
//            if(Star.iTermTransCon(con)==-1)
//                return;

            /*Coloca la bandera para saber que si hubo movimientos*/
            bMov    = true;
            
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x++)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito si hubo miviminetos*/
        if(bMov)
            JOptionPane.showMessageDialog(null, "Almacén(s) borrado(s) con éxito.", "Almacenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
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
        
        /*Pregunta al usuario si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
            
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();
        this.dispose();                    
        
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

    
    /*Cuando se presiona una tecla en el campo de edición de almacenes*/
    private void jTCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de almacén*/
    private void jTCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCod.setSelectionStart(0);jTCod.setSelectionEnd(jTCod.getText().length());                                
        
    }//GEN-LAST:event_jTCodFocusGained

    
    /*Cuando se presiona una tecla en el campo de descripción de almacén*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de almacenes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de descripción del almacén*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());                                        
        
    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de almacén*/
    private void jTCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusLost

        /*Coloca el cursor al principio del control*/
        jTCod.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCod.getText().compareTo("")!=0)
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
                               
    }//GEN-LAST:event_jTCodFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de descripción*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost

        /*Coloca el cursor al principio del control*/
        jTDescrip.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDescrip.getText().compareTo("")!=0)
            jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
                        
    }//GEN-LAST:event_jTDescripFocusLost
    
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Si hay cadena vacia en el campo del código del almacén no puede continuar*/
        if(jTCod.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del almacen esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCod.grabFocus();            
            return; 
        }
        
        /*Si hay cadena vacia en el campo de descripción del almacén no puede continuar*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de descripción del almacen esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTDescrip.grabFocus();                        
            return;            
        }
        
        /*Obtiene el responsable*/
        String sRespon  = jTRespon.getText();                
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables del base de datos*/    
        Statement   st;
        ResultSet   rs;
        String      sQ;        
        
        /*Si El usuario no es cadena vacia entonces*/
        if(sRespon.compareTo("")!=0)
        {
            /*Comprueba si el usuario existe*/
            try
            {
                sQ = "SELECT estac FROM estacs WHERE estac = '" + sRespon + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*No hay datos entonces no existe*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El responsable como usuario \"" + sRespon + "\" no existe.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del teclado en el control y regresa*/
                    jTRespon.grabFocus();
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
                return;                        
            }
            
        }/*Fin de if(sRespon.compareTo("")!=0)*/
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar almacen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Regresa*/
            return;                
        }
                
        /*Checa si el código del almacén ya existe en la base de datos*/        
        iRes        = Star.iExiste(con, jTCod.getText().replace(" ", "").trim(), "almas", "alma");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si ya existe el almacén entonces
        if(iRes==1)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

             /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "El código del almacén: " + jTCod.getText().replace(" ", "").trim() + " ya existe.", "Registro existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo del código del almacén y regresa*/
            jTCod.grabFocus();                
            return;
            
        }//Fin de if(iRes==1)
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
           return;
        
        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO almas(    alma,                                                                   almadescrip,                                        falt,       estac,                                     sucu,                                     nocaj,                                    respon,            dir1,                                                 dir2,                                                 dir3) " + 
                        "VALUES('" +    jTCod.getText().replace(" ", "").trim().replace("'", "''") + "','" +   jTDescrip.getText().replace("'", "''") + "',        now(), '" + Login.sUsrG.replace("'", "''") + "', '" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "','" +  sRespon + "', '" + jTDir1.getText().replace("'", "''").trim() + "', '" + jTDir2.getText().replace("'", "''").trim() + "', '" + jTDir3.getText().replace("'", "''").trim() + "')";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
            return;                        
         }
        /*Inserta en log de almacenes*/
        try 
        {            
            sQ = "INSERT INTO logalmas(cod,                                                                    descrip,                                        accio,               estac,                                         sucu,                                           nocaj,                                          falt) " + 
                          "VALUES('" + jTCod.getText().replace(" ", "").trim().replace("'", "''") + "','" +    jTDescrip.getText().replace("'", "''") + "',    'AGREGAR', '" +      Login.sUsrG.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
            return;                        
        }
        
        //Termina la transacción
        //if(Star.iTermTransCon(con)==-1)
          //  return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Agrega el registro del almacén en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTCod.getText().replace(" ", "").trim(), jTDescrip.getText(), sRespon, jTDir1.getText().trim(), jTDir2.getText().trim(), jTDir3.getText().trim()};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de las filas*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo del código del almacén*/
        jTCod.grabFocus();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Almacén: " + jTDescrip.getText() + " agregado con éxito.", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
         
        /*Limpia los campos*/
        jTCod.setText       ("");
        jTDescrip.setText   ("");
        jTDir1.setText      ("");
        jTDir2.setText      ("");
        jTDir3.setText      ("");
        jTRespon.setText    ("");
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se tipea una tecla en el campo de código de almacén*/
    private void jTCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodKeyTyped

    
    /*Cuando se tipea una tecla en el campo de descripción de almacén*/
    private void jTDescripKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTDescripKeyTyped

    
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

    
    /*Función para cargar todos los elementos nuevamente en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene de la base de datos todas los almacenes y cargalas en la tabla*/
        vCarAlmas();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para cargar todos los elementos nuevamente en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
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
        
        /*Borra todos los item en la tabla de almacenes*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFi              = 1;
        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        /*Obtiene de la base de datos todos los almacenes*/        
        try
        {                  
            sQ = "SELECT * FROM almas WHERE alma LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR almadescrip LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR falt LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fmod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("alma"), rs.getString("almadescrip")};
                te.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;               
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, con);
            return;                        
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de ver reporte*/
    private void jBVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisActionPerformed

        /*Si no existen almacenes entonces*/
        if(jTab.getRowCount()==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen almacenes.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
        /*Crea el thread para cargar el reporte en un hilo aparte*/
        (new Thread()
        {
            @Override
            public void run()
            {   
                //Abre la base de datos                             
                Connection  con2 = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con2==null)
                    return;

                /*Crea los parámetros que se pasarán*/
                java.util.Map <String,String> pa = new java.util.HashMap<>();             
                pa.clear();                    
                pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    
                /*Muestra el formulario*/
                try
                {
                    /*Compila el reporte y muestralo maximizado*/                      
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptAlma.jrxml"));                                        
                    JasperPrint pr      = JasperFillManager.fillReport(ja, null, con2);                    
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Almacenes");
                    v.setIconImage(newimg);
                    v.setVisible(true);                    
                    
                    /*Esconde el loading*/
                    Star.lCargGral.setVisible(false);
                }
                catch(JRException expnJASR)
                {                    
                    /*Esconde el loading*/
                    Star.lCargGral.setVisible(false);                                            
                    
                    //Procesa el error y regresa
                    Star.iErrProc(expnJASR.getMessage(), Star.sErrJASR);
                    return;                        
                }

                //Cierra la base de datos
                Star.iCierrBas(con2);
                
            }/*Fin de public void run()*/
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jBVisActionPerformed

    
    /*Cuando se presiona una tecla en el botón del visor*/
    private void jBVisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVisKeyPressed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed
        
        /*Función para cargar todos los elementos nuevamente en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando el mosue es arrrastrado en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando el mouse se mueve en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando la rueda del ratón se mueve en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona el botón de búscar usuario*/
    private void jBRespActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRespActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTRespon.getText(), 4, jTRespon, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBRespActionPerformed

    
    /*Cuando se presiona una tecla en el campo de búsqueda de usuario*/
    private void jBRespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRespKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBRespKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del responsable*/
    private void jTResponFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResponFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTRespon.setSelectionStart(0);jTRespon.setSelectionEnd(jTRespon.getText().length());                

    }//GEN-LAST:event_jTResponFocusGained
            

    /*Cuando se presiona una tecla en el campo del responsable*/
    private void jTResponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResponKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTRespon.getText(), 4, jTRespon, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTResponKeyPressed

        
    /*Cuando se tipea una tecla en el campo de El usuario*/
    private void jTResponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResponKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              

    }//GEN-LAST:event_jTResponKeyTyped
        
    
    /*Cuando se presiona el botón de ver tabla en grande*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en la tabla*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando el mouse entrae en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBRespMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRespMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBResp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRespMouseEntered

    
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
    private void jBVisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVis.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVisMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    
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
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBRespMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRespMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBResp.setBackground(colOri);
        
    }//GEN-LAST:event_jBRespMouseExited

    
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
    private void jBVisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVis.setBackground(colOri);
        
    }//GEN-LAST:event_jBVisMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    
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
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el cursor al principio del control*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del responsable*/
    private void jTResponFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResponFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTRespon.setCaretPosition(0);
        
    }//GEN-LAST:event_jTResponFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de dirección 1*/
    private void jTDir1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDir1.setSelectionStart(0);jTDir1.setSelectionEnd(jTDir1.getText().length());                                
        
    }//GEN-LAST:event_jTDir1FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la dirección 1*/
    private void jTDir1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir1FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDir1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDir1.getText().compareTo("")!=0)
            jTDir1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTDir1FocusLost

    
    /*Cuando se presiona una tecla en el campo de la dirección 1*/
    private void jTDir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDir1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDir1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del directorio 2*/
    private void jTDir2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDir2.setSelectionStart(0);jTDir2.setSelectionEnd(jTDir2.getText().length());                                
        
    }//GEN-LAST:event_jTDir2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la dirección 2*/
    private void jTDir2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir2FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDir2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDir2.getText().compareTo("")!=0)
            jTDir2.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTDir2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la dirección 3*/
    private void jTDir2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDir2KeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDir2KeyPressed

    private void jTDir3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDir3.setSelectionStart(0);jTDir3.setSelectionEnd(jTDir3.getText().length());                                
        
    }//GEN-LAST:event_jTDir3FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la dirección 3*/
    private void jTDir3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDir3FocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDir3.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDir3.getText().compareTo("")!=0)
            jTDir3.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTDir3FocusLost

    
    /*Cuando se presiona una tecla en el campo de la dirección 3*/
    private void jTDir3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDir3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDir3KeyPressed

    
   
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
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
        /*Si se presiona F3 y presiona el botón de visor*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();        
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 y presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona F5 y presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        /*Si se presiona F6 y presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBVis.doClick();        
                
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBResp;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVis;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCod;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDir1;
    private javax.swing.JTextField jTDir2;
    private javax.swing.JTextField jTDir3;
    private javax.swing.JTextField jTRespon;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
